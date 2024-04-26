import decimal
import json
from datetime import datetime, date

import flask
import pymysql
from flask import Flask, render_template, request
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.model_selection import train_test_split
from sklearn.svm import LinearSVC
from sklearn.metrics import classification_report
import openpyxl
import pandas as pd
import numpy as np

from sklearn.metrics.pairwise import cosine_similarity
from scipy import sparse

existing_file = "system_recommendation2.xlsx"
df = pd.read_excel(existing_file)
df_book2 = pd.read_excel("Book2.xlsx")


def get_conn():
    conn = pymysql.connect(
        host='localhost',
        user='root',
        password='123456',
        db='quanan',
        charset='utf8mb4',
        cursorclass=pymysql.cursors.DictCursor
    )
    return conn


# class NpEncoder(json.JSONEncoder):
#     def default(self, obj):
#         if isinstance(obj, np.integer):
#             return int(obj)
#         if isinstance(obj, np.floating):
#             return float(obj)
#         if isinstance(obj, np.ndarray):
#             return obj.tolist()
#         if isinstance(obj, decimal.Decimal):
#             return float(obj)
#         if isinstance(obj, (datetime, date)):
#             return obj.isoformat()
#         if isinstance(obj, bytes):
#             return obj.decode("utf8")
#         if isinstance(obj, bool):
#             return 1 if obj else 0
#         return super(NpEncoder, self).default(obj)


class CF(object):
    def __init__(self, data_matrix, k, dist_func=cosine_similarity, uuCF=1):
        # self.existing_file = 'Book2.xlsx'
        # self.existing_file2 = 'system_recommendation.xlsx'
        # self.df_book2 = pd.read_excel(self.existing_file)
        # self.df = pd.read_excel(self.existing_file2)
        self.uuCF = uuCF  # user-user (1) or item-item (0) CF
        self.Y_data = data_matrix if uuCF else data_matrix.iloc[:, [1, 0, 2]]
        self.k = k
        self.dist_func = dist_func
        self.Ybar_data = None
        # số lượng user và item, +1 vì mảng bắt đầu từ 0
        self.n_users = int(np.max(self.Y_data.iloc[:, 0])) + 1
        self.n_items = int(np.max(self.Y_data.iloc[:, 1])) + 1

    def select_user(self):
        conn = get_conn()
        rows = None
        try:
            if conn.open:
                with conn.cursor() as cursor:
                    # Read data from database
                    sql = "SELECT * FROM `nguoi_dung`"
                    cursor.execute(sql)

                    # Fetch all rows
                    rows = cursor.fetchall()

                    # # Print results
                    # for row in rows:
                    #     print(row['noi_dung'])
        finally:
            conn.close()
            return rows

    def select_food(self):
        conn = get_conn()
        rows = None
        try:
            if conn.open:
                with conn.cursor() as cursor:
                    # Read data from database
                    sql = "SELECT * FROM `thuc_an`"
                    cursor.execute(sql)

                    # Fetch all rows
                    rows = cursor.fetchall()

                    # # Print results
                    # for row in rows:
                    #     print(row['noi_dung'])
        finally:
            conn.close()
            return rows

    def select_comment(self):
        conn = get_conn()
        rows = None
        try:
            if conn.open:
                with conn.cursor() as cursor:
                    # Read data from database
                    sql = "SELECT * FROM `danh_gia`"
                    cursor.execute(sql)

                    # Fetch all rows
                    rows = cursor.fetchall()

                    # # Print results
                    # for row in rows:
                    #     print(row['noi_dung'])
        finally:
            conn.close()
            return rows

    def find_food_id(self, idThucAn):
        foods = self.select_food()

        for f in foods:
            if f['id'] == idThucAn:
                return f

    def find_user_id(self, idUser):
        users = self.select_food()

        for u in users:
            if u['id'] == idUser:
                return u

    def normalize_matrix(self):
        """
        Tính similarity giữa các items bằng cách tính trung bình cộng ratings giữa các items.
        Sau đó thực hiện chuẩn hóa bằng cách trừ các ratings đã biết của item cho trung bình cộng
        ratings tương ứng của item đó, đồng thời thay các ratings chưa biết bằng 0.
        """
        users = self.Y_data.iloc[:, 0]
        self.Ybar_data = self.Y_data.copy()
        self.mu = np.zeros((self.n_users,))
        for n in range(self.n_users):
            ids = np.where(users == n)[0].astype(np.int32)
            item_ids = self.Y_data.iloc[ids, 1]
            ratings = self.Y_data.iloc[ids, 2]
            # take mean
            m = np.mean(ratings)
            if np.isnan(m):
                m = 0  # để tránh mảng trống và nan value
            self.mu[n] = m
            # chuẩn hóa
            self.Ybar_data.iloc[ids, 2] = ratings - self.mu[n]
        self.Ybar = sparse.coo_matrix((self.Ybar_data.iloc[:, 2],
                                       (self.Ybar_data.iloc[:, 1], self.Ybar_data.iloc[:, 0])),
                                      (self.n_items, self.n_users))
        self.Ybar = self.Ybar.tocsr()

    def similarity(self):
        """
        Tính độ tương đồng giữa các user và các item
        """
        eps = 1e-6
        self.S = self.dist_func(self.Ybar.T, self.Ybar.T)

    def __pred(self, u, i, normalized=1):
        """
        Dự đoán ra ratings của các users với mỗi items.
        """
        # tìm tất cả user đã rate item i
        ids = np.where(self.Y_data.iloc[:, 1] == i)[0].astype(np.int32)
        users_rated_i = (self.Y_data.iloc[ids, 0]).reset_index(drop=True).astype(np.int32)
        sim = self.S[u, users_rated_i]
        a = np.argsort(sim)[-self.k:]
        nearest_s = sim[a]
        r = self.Ybar[i, users_rated_i[a]]
        if normalized:
            # cộng với 1e-8, để tránh chia cho 0
            return (r * nearest_s)[0] / (np.abs(nearest_s).sum() + 1e-8)

        return (r * nearest_s)[0] / (np.abs(nearest_s).sum() + 1e-8) + self.mu[u]

    def pred(self, u, i, normalized=1):
        """
        Xét xem phương pháp cần áp dùng là uuCF hay iiCF
        """
        if self.uuCF: return self.__pred(u, i, normalized)
        return self.__pred(i, u, normalized)

    def recommend_top(self, u, top_x, df):
        """
        Determine top 10 items should be recommended for user u.
        . Suppose we are considering items which
        have not been rated by u yet.
        """
        ids = np.where(self.Y_data.iloc[:, 0] == u)[0]
        items_rated_by_u = self.Y_data.iloc[ids, 1].tolist()
        item = {'id': None, 'similar': None}
        list_items = []

        def take_similar(elem):
            return elem['similar']

        # for i in range(self.n_items):
        #     if i not in items_rated_by_u:
        #         rating = self.__pred(u, i)
        #         item['id'] = i
        #         item['similar'] = rating
        #         list_items.append(item.copy())

        for index, row in df.iterrows():
            i = row['item_id']
            if i not in items_rated_by_u:
                rating = self.__pred(u, i)
                item['id'] = i
                item['similar'] = rating
                if item not in list_items:
                    list_items.append(item.copy())

        sorted_items = sorted(list_items, key=take_similar, reverse=True)
        # sorted_items.pop(top_x)
        return sorted_items

    def write_sentiment_to_rec(self, s, sen, idThucAn, idNguoiDung):
        if s.__eq__('positive'):
            new_data = {'user_id': [idNguoiDung], 'item_id': [idThucAn], 'rating': 5}
        elif s.__eq__('neutral'):
            new_data = {'user_id': [idNguoiDung], 'item_id': [idThucAn], 'rating': 3}
        else:
            new_data = {'user_id': [idNguoiDung], 'item_id': [idThucAn], 'rating': 1}
        df_new = pd.DataFrame(new_data)
        # Append new data
        df_combined = pd.concat([sen.df_rec, df_new], ignore_index=True)
        # Save the combined data to Excel
        df_combined.to_excel(sen.existing_file2, index=False)
        sen.df_rec = df_combined


def write_excel_to_excel(df, df2):
    for index, row in df.iterrows():
        if row['idNguoiDung'] > 0:
            if row['Sentiment'].__eq__('positive'):
                new_data = {'user_id': [row['idNguoiDung']], 'item_id': [row['idThucAn']], 'rating': 5}
            elif row['Sentiment'].__eq__('neutral'):
                new_data = {'user_id': [row['idNguoiDung']], 'item_id': [row['idThucAn']], 'rating': 3}
            else:
                new_data = {'user_id': [row['idNguoiDung']], 'item_id': [row['idThucAn']], 'rating': 1}
            df_new = pd.DataFrame(new_data)
            # Append new data
            df_combined = pd.concat([df2, df_new], ignore_index=True)

            # Save the combined data to Excel
            df_combined.to_excel(existing_file, index=False)
            df2 = df_combined


test = CF(data_matrix=df, k=4)
test.normalize_matrix()
test.similarity()
# print(test.Y_data)
# print(test.Ybar)
# print(test.S)
# for i in range(1,6):
#     for j in range(1,4):
#         print("i {}".format(i))
#         print("j {}".format(j))
#         print(test.pred(i,j))
# print(test.recommend_top(3, 0, df))

# write_excel_to_excel(df_book2, df)
