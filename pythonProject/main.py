import base64
import decimal
import json
from datetime import datetime, date

import flask
import pymysql
import requests
import underthesea
from bs4 import BeautifulSoup
from flask import Flask, render_template, request
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.model_selection import train_test_split, GridSearchCV
from sklearn.svm import LinearSVC
from sklearn.metrics import classification_report, accuracy_score
import openpyxl
import pandas as pd
import numpy as np
import preprocess_kgptalkie as ps
import re
from flask_cors import CORS
import glob
import os
from underthesea import sentiment, pos_tag, word_tokenize
from system_recomendation import CF
from flask import g

app = Flask(__name__)
CORS(app)


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


class NpEncoder(json.JSONEncoder):
    def default(self, obj):
        if isinstance(obj, np.integer):
            return int(obj)
        if isinstance(obj, np.floating):
            return float(obj)
        if isinstance(obj, np.ndarray):
            return obj.tolist()
        if isinstance(obj, decimal.Decimal):
            return float(obj)
        if isinstance(obj, (datetime, date)):
            return obj.isoformat()
        if isinstance(obj, bytes):
            return obj.decode("utf8")
        if isinstance(obj, bool):
            return 1 if obj else 0
        return super(NpEncoder, self).default(obj)


def get_clean(x):
    x = str(x).lower().replace('\\', '').replace('_', ' ')
    x = ps.cont_exp(x)
    x = ps.remove_emails(x)
    x = ps.remove_urls(x)
    x = ps.remove_html_tags(x)
    # x = ps.remove_accented_chars(x)
    # x = ps.remove_special_chars(x)
    x = re.sub("(.)\\1{2,}", "\\1", x)
    return x


class Sentiment(object):
    def __init__(self):
        self.SIZE_DEMO=10000
        self.count = 1
        self.existing_file = 'Book2.xlsx'
        self.existing_file2 = 'system_recommendation2.xlsx'
        self.df = pd.read_excel(self.existing_file)
        self.df_rec = pd.read_excel(self.existing_file2)
        self.df = self.df[:self.SIZE_DEMO]
        self.df['Review'] = self.df['Review'].apply(lambda x: get_clean(x))
        self.tfidf = TfidfVectorizer(analyzer="word", tokenizer=underthesea.word_tokenize, token_pattern=None, lowercase=False)
        self.X = self.tfidf.fit_transform(self.df['Review'])
        self.y = self.df['Sentiment']
        self.X_train, self.X_test, self.y_train, self.y_test = train_test_split(self.X, self.y, test_size=0.2,
                                                                                random_state=0)
        self.X_train, self.X_val, self.y_train, self.y_val = train_test_split(self.X_train, self.y_train, test_size=0.25,
                                                                            random_state=0)
        self.clf = LinearSVC().fit(self.X_train, self.y_train)
        self.y_pred = self.clf.predict(self.X_test)

    def print_classification_report(self):
        print(classification_report(self.y_test, self.y_pred))

    def val_process(self):
        val_predictions = self.clf.predict(self.X_val)
        val_accuracy = accuracy_score(self.y_val, val_predictions)
        print(f"Validation Accuracy: {val_accuracy:.4f}")
        print(classification_report(self.y_val, val_predictions))

        # docs_new = ['God is love', 'OpenGL on the GPU is fast']
        # X_new_tfidf = tfidf.transform(docs_new)

        # y_pred = clf.predict(X_new_tfidf)

        # for doc, category in zip(docs_new, y_pred):
        #     print('%r => %s' % (doc, category))

        # X_new_tfidf = tfidf.transform(test_texts)

        # predicted = clf.predict(X_new_tfidf)

        # np.mean(predicted == test_labels)

        param_grid = {
            'C': [0.1, 1, 10, 100, 1000],
            # 'max_iter': [1000, 5000, 10000],
        }

        # tree_param = {'criterion':['gini','entropy'],'max_depth':[4,5,6,7,8,9,10,11,12,15,20,30,40,50,70,90,120,150]}

        # parameters = {
        #     'alpha': (1, 0.1, 0.01, 0.001, 0.0001, 0.00001)
        # }

        # parameters = {
        #     'max_depth':[3,5,10,None],
        #               'n_estimators':[10,100,200],
        #               'max_features':[1,3,5,7],
        #               'min_samples_leaf':[1,2,3],
        #               'min_samples_split':[1,2,3]
        # }

        # Initialize GridSearchCV
        grid_search = GridSearchCV(LinearSVC(), param_grid, cv=3, refit=True, scoring='accuracy')

        # grid_search = GridSearchCV(tree.DecisionTreeClassifier(), tree_param, cv=3, refit = True, scoring='accuracy')

        # grid_search = GridSearchCV(MultinomialNB(), parameters, cv=3, refit = True, scoring='accuracy')

        # grid_search = GridSearchCV(RandomForestClassifier(), parameters, cv=3, refit = True, scoring='accuracy')

        # Fit the grid search
        grid_search.fit(self.X_train, self.y_train)

        # Best parameters
        best_params = grid_search.best_params_
        print(f"Best Parameters: {best_params}")

        # Evaluate best model on validation set
        best_model = grid_search.best_estimator_
        val_predictions = best_model.predict(self.X_val)
        val_accuracy = accuracy_score(self.y_val, val_predictions)
        print(f"Validation Accuracy with Best Model: {val_accuracy:.4f}")
        print(classification_report(self.y_val, val_predictions))


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

    def get_last_rec_comment(self):
        conn = get_conn()
        rows = None
        try:
            if conn.open:
                with conn.cursor() as cursor:
                    # Read data from database
                    sql = "SELECT * FROM `danh_gia` WHERE id=(SELECT max(id) FROM `danh_gia`)"
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

    def find_comment_id(self, idComment):
        cms = self.select_comment()

        for c in cms:
            if c['id'] == idComment:
                return c

    def get_data_from_database(self):
        danh_gias = self.select_comment()

        for dg in danh_gias:
            x = dg['noi_dung']
            print(x)
            x = get_clean(x)
            vec = self.tfidf.transform([x])
            vec.shape
            print(self.clf.predict(vec))
            tmp = str(self.clf.predict(vec)).strip('[]\'')

            # New data to append
            new_data = {'Review': [x], 'Sentiment': [tmp]}
            df_new = pd.DataFrame(new_data)

            # Append new data
            df_combined = pd.concat([self.df, df_new], ignore_index=True)

        # Save the combined data to Excel
        df_combined.to_excel(self.existing_file, index=False)
        self.df = df_combined

    def phan_loai_sentiment(self, noiDung, idThucAn, idNguoiDung, rec):
        x = noiDung
        print(x)
        x = get_clean(x)
        vec = self.tfidf.transform([x])
        vec.shape
        print(self.clf.predict(vec))
        tmp = str(self.clf.predict(vec)).strip('[]\'')

        # New data to append
        new_data = {'Review': [x], 'Sentiment': [tmp], 'idThucAn': idThucAn, 'idNguoiDung': idNguoiDung, 'idComment': self.get_last_rec_comment()[0]['id']}
        df_new = pd.DataFrame(new_data)

        # Append new data
        df_combined = pd.concat([self.df, df_new], ignore_index=True)

        # Save the combined data to Excel
        df_combined.to_excel(self.existing_file, index=False)
        self.df = df_combined
        if idNguoiDung > 0:
            rec.write_sentiment_to_rec(tmp, self, idThucAn, idNguoiDung)
        return tmp

    def countif_excel(self, pos_neg_neutral):
        df2 = self.df.copy()
        df2 = df2.loc[9:]
        df3 = df2.copy()
        df3 = df3[df3['idComment'] > 0]
        df2 = df2[df2['Sentiment'].__eq__(pos_neg_neutral)]
        df2 = df2[df2['idComment'] > 0]
        list_comment = []
        for index, row in df2.iterrows():
            if row['Sentiment'].__eq__(pos_neg_neutral):
                c = self.find_comment_id(row['idComment'])
                if c not in list_comment:
                    list_comment.append(c)

        s = {"rate": str((df2[df2.columns[0]].count() / df3[df3.columns[0]].count()) * 100), "list_comment": list_comment}
        return s


sentiment_object = Sentiment()
rec = CF(data_matrix=sentiment_object.df_rec, k=4)
rec.normalize_matrix()
rec.similarity()


@app.route("/getsentiment/", methods=['POST'])
def get_sentiment():
    # response = flask.jsonify({'some': 'data'})
    # response.headers.add('Access-Control-Allow-Origin', '*')
    if request.method == "POST":
        tmp = request.get_json()
        s = tmp['noiDung']
        idThucAn = tmp['idThucAn']
        idNguoiDung = tmp['idNguoiDung']
        sentiment_object.phan_loai_sentiment(str(s), idThucAn, idNguoiDung, rec)
    return str(s)


@app.route("/getcountsentiment/", methods=['GET'])
def get_count_sentiment():
    # response = flask.jsonify({'some': 'data'})
    # response.headers.add('Access-Control-Allow-Origin', '*')
    list_count = []
    if request.method == "GET":
        pos = sentiment_object.countif_excel('positive')
        neg = sentiment_object.countif_excel('negative')
        neu = sentiment_object.countif_excel('neutral')
        list_count = {'positive': pos, 'negative': neg, 'neutral': neu}
    return json.dumps(list_count, cls=NpEncoder, ensure_ascii=False).encode('utf8')


@app.route("/valprocess/", methods=['GET'])
def get_val_process():
    sentiment_object.val_process()
    return "hello"


@app.route("/getrec/", methods=['POST'])
def get_rec():
    # response = flask.jsonify({'some': 'data'})
    # response.headers.add('Access-Control-Allow-Origin', '*')
    list_rec = []
    if request.method == "POST":
        tmp = request.get_json()
        s = tmp['idNguoiDung']
        list_rec = rec.recommend_top(s, 0, sentiment_object.df_rec)
        list_f = []
        for r in list_rec:
            if r['similar'] >= 0:
                f = sentiment_object.find_food_id(r['id'])
                list_f.append(f)

    return json.dumps(list_f, cls=NpEncoder, ensure_ascii=False).encode('utf8')


if __name__ == "__main__":
    app.run(debug=True)

# files_content = []
#
# emoji_pattern = re.compile("["
#                            u"\U0001F600-\U0001F64F"  # emoticons
#                            u"\U0001F300-\U0001F5FF"  # symbols & pictographs
#                            u"\U0001F680-\U0001F6FF"  # transport & map symbols
#                            u"\U0001F1E0-\U0001F1FF"  # flags (iOS)
#                            "]+", flags=re.UNICODE)


# try:
#     for i in range(1, 10):
#         path = './dataset{}/train/1/'.format(i)
#         for filename in filter(lambda p: p.endswith("txt"), os.listdir(path)):
#             filepath = os.path.join(path, filename)
#             with open(filepath, mode='r', encoding="utf8") as f:
#                 tmp = f.read()
#                 # print(tmp)
#                 files_content.append(tmp)
#                 sentiment_object.phan_loai_sentiment(tmp)
#
#     print(files_content)
# except:
#     print(files_content)

# sentiment_object.phan_loai_sentiment("Đây là quán đẹp nhất!")

# get all y.txt files from all subdirectories
def load_all_sentiment_from_txt():
    all_files = glob.glob('/pythonProject/dataset/**/test/**/*.txt')
    print(all_files)

    for file in all_files:
        with open(file, mode='r', encoding="utf8") as f:
            tmp = f.read()
            sentiment_object.phan_loai_sentiment(tmp)


def load_all_sentiment_from_txt2(pos, sen, st):
    s = '/pythonProject/dataset/**/test/{}/*.txt'.format(pos)
    all_files = glob.glob(s)

    df_combined = sen.df

    for file in all_files:
        with open(file, mode='r', encoding="utf8") as f:
            tmp = f.read()
            tmp = get_clean(tmp)
            print(file)
            print(tmp)
            new_data = {'Review': [tmp], 'Sentiment': [st], 'idThucAn': 0, 'idNguoiDung': 0, 'idComment': 0}
            df_new = pd.DataFrame(new_data)

            # Append new data
            df_combined = pd.concat([sen.df, df_new], ignore_index=True)

            # Save the combined data to Excel
            # df_combined.to_excel(sen.existing_file, index=False)
            sen.df = df_combined
    df_combined.to_excel(sen.existing_file, index=False)


# load_all_sentiment_from_txt2(1, sentiment_object, 'positive')
# load_all_sentiment_from_txt2(-1, sentiment_object, 'negative')
# load_all_sentiment_from_txt2(2, sentiment_object, 'neutral')

# print(sentiment_object.phan_loai_sentiment("day la commen test", 0, 0))


# load_all_sentiment_from_txt()

def write_to_txt(noiDung):
    path = ''
    if sentiment_object.phan_loai_sentiment(noiDung).__eq__('positive'):
        path = '/pythonProject/dataset_test/1/' + str(sentiment_object.count) + '.txt'
    else:
        path = '/pythonProject/dataset_test/-1/' + str(sentiment_object.count) + '.txt'
    with open(path, 'w', encoding="utf8") as f:
        f.write(noiDung)
        sentiment_object.count = sentiment_object.count + 1


def excel_to_txt(df):
    path = ''
    for index, row in df.iterrows():
        if row['Sentiment'].__eq__('positive'):
            path = '/pythonProject/dataset_test/1/' + str(sentiment_object.count) + '.txt'
        elif row['Sentiment'].__eq__('neutral'):
            path = '/pythonProject/dataset_test/2/' + str(sentiment_object.count) + '.txt'
        else:
            path = '/pythonProject/dataset_test/-1/' + str(sentiment_object.count) + '.txt'
        with open(path, 'w', encoding="utf8") as f:
            f.write(str(row['Review']))
        sentiment_object.count = sentiment_object.count + 1


# write_to_txt("Quán tệ")


# excel_to_txt(sentiment_object.df)


def read_sentiment():
    all_files = glob.glob('/pythonProject/dataset_test/**/*.txt')
    print(all_files)

    for file in all_files:
        with open(file, mode='r', encoding="utf8") as f:
            tmp = f.read()
            print(tmp)
            print(pos_tag(tmp))

# read_sentiment()



# import base64
# import decimal
# import json
# from datetime import datetime, date

# import flask
# import pymysql
# import requests
# from bs4 import BeautifulSoup
# from flask import Flask, render_template, request
# from sklearn.feature_extraction.text import TfidfVectorizer
# from sklearn.model_selection import train_test_split
# from sklearn.svm import LinearSVC
# from sklearn.metrics import classification_report
# import openpyxl
# import pandas as pd
# import numpy as np
# import preprocess_kgptalkie as ps
# import re
# from flask_cors import CORS
# import glob
# import os
# from underthesea import sentiment, pos_tag, word_tokenize
# from system_recomendation import CF
# from flask import g

# app = Flask(__name__)
# CORS(app)


# def get_conn():
#     conn = pymysql.connect(
#         host='localhost',
#         user='root',
#         password='123456',
#         db='quanan',
#         charset='utf8mb4',
#         cursorclass=pymysql.cursors.DictCursor
#     )
#     return conn


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


# def get_clean(x):
#     x = str(x).lower().replace('\\', '').replace('_', ' ')
#     x = ps.cont_exp(x)
#     x = ps.remove_emails(x)
#     x = ps.remove_urls(x)
#     x = ps.remove_html_tags(x)
#     # x = ps.remove_accented_chars(x)
#     x = ps.remove_special_chars(x)
#     x = re.sub("(.)\\1{2,}", "\\1", x)
#     return x


# class Sentiment(object):
#     def __init__(self):
#         self.count = 1
#         self.existing_file = 'Book2.xlsx'
#         self.existing_file2 = 'system_recommendation2.xlsx'
#         self.df = pd.read_excel(self.existing_file)
#         self.df_rec = pd.read_excel(self.existing_file2)
#         self.df['Review'] = self.df['Review'].apply(lambda x: get_clean(x))
#         self.tfidf = TfidfVectorizer(max_features=100)
#         self.X = self.tfidf.fit_transform(self.df['Review'])
#         self.y = self.df['Sentiment']
#         self.X_train, self.X_test, self.y_train, self.y_test = train_test_split(self.X, self.y, test_size=0.2,
#                                                                                 random_state=0)
#         self.clf = LinearSVC().fit(self.X_train, self.y_train)
#         self.y_pred = self.clf.predict(self.X_test)

#     def print_classification_report(self):
#         print(classification_report(self.y_test, self.y_pred))

#     def select_comment(self):
#         conn = get_conn()
#         rows = None
#         try:
#             if conn.open:
#                 with conn.cursor() as cursor:
#                     # Read data from database
#                     sql = "SELECT * FROM `danh_gia`"
#                     cursor.execute(sql)

#                     # Fetch all rows
#                     rows = cursor.fetchall()

#                     # # Print results
#                     # for row in rows:
#                     #     print(row['noi_dung'])
#         finally:
#             conn.close()
#             return rows

#     def select_food(self):
#         conn = get_conn()
#         rows = None
#         try:
#             if conn.open:
#                 with conn.cursor() as cursor:
#                     # Read data from database
#                     sql = "SELECT * FROM `thuc_an`"
#                     cursor.execute(sql)

#                     # Fetch all rows
#                     rows = cursor.fetchall()

#                     # # Print results
#                     # for row in rows:
#                     #     print(row['noi_dung'])
#         finally:
#             conn.close()
#             return rows

#     def get_last_rec_comment(self):
#         conn = get_conn()
#         rows = None
#         try:
#             if conn.open:
#                 with conn.cursor() as cursor:
#                     # Read data from database
#                     sql = "SELECT * FROM `danh_gia` WHERE id=(SELECT max(id) FROM `danh_gia`)"
#                     cursor.execute(sql)

#                     # Fetch all rows
#                     rows = cursor.fetchall()

#                     # # Print results
#                     # for row in rows:
#                     #     print(row['noi_dung'])
#         finally:
#             conn.close()
#             return rows

#     def find_food_id(self, idThucAn):
#         foods = self.select_food()

#         for f in foods:
#             if f['id'] == idThucAn:
#                 return f

#     def find_comment_id(self, idComment):
#         cms = self.select_comment()

#         for c in cms:
#             if c['id'] == idComment:
#                 return c

#     def get_data_from_database(self):
#         danh_gias = self.select_comment()

#         for dg in danh_gias:
#             x = dg['noi_dung']
#             print(x)
#             x = get_clean(x)
#             vec = self.tfidf.transform([x])
#             vec.shape
#             print(self.clf.predict(vec))
#             tmp = str(self.clf.predict(vec)).strip('[]\'')

#             # New data to append
#             new_data = {'Review': [x], 'Sentiment': [tmp]}
#             df_new = pd.DataFrame(new_data)

#             # Append new data
#             df_combined = pd.concat([self.df, df_new], ignore_index=True)

#         # Save the combined data to Excel
#         df_combined.to_excel(self.existing_file, index=False)
#         self.df = df_combined

#     def phan_loai_sentiment(self, noiDung, idThucAn, idNguoiDung, rec):
#         x = noiDung
#         print(x)
#         x = get_clean(x)
#         vec = self.tfidf.transform([x])
#         vec.shape
#         print(self.clf.predict(vec))
#         tmp = str(self.clf.predict(vec)).strip('[]\'')

#         # New data to append
#         new_data = {'Review': [x], 'Sentiment': [tmp], 'idThucAn': idThucAn, 'idNguoiDung': idNguoiDung, 'idComment': self.get_last_rec_comment()[0]['id']}
#         df_new = pd.DataFrame(new_data)

#         # Append new data
#         df_combined = pd.concat([self.df, df_new], ignore_index=True)

#         # Save the combined data to Excel
#         df_combined.to_excel(self.existing_file, index=False)
#         self.df = df_combined
#         if idNguoiDung > 0:
#             rec.write_sentiment_to_rec(tmp, self, idThucAn, idNguoiDung)
#         return tmp

#     def countif_excel(self, pos_neg_neutral):
#         df2 = self.df.copy()
#         df2 = df2.loc[9:]
#         df3 = df2.copy()
#         df3 = df3[df3['idComment'] > 0]
#         df2 = df2[df2['Sentiment'].__eq__(pos_neg_neutral)]
#         df2 = df2[df2['idComment'] > 0]
#         list_comment = []
#         for index, row in df2.iterrows():
#             if row['Sentiment'].__eq__(pos_neg_neutral):
#                 c = self.find_comment_id(row['idComment'])
#                 if c not in list_comment:
#                     list_comment.append(c)

#         s = {"rate": str((df2[df2.columns[0]].count() / df3[df3.columns[0]].count()) * 100), "list_comment": list_comment}
#         return s


# sentiment_object = Sentiment()
# rec = CF(data_matrix=sentiment_object.df_rec, k=4)
# rec.normalize_matrix()
# rec.similarity()


# @app.route("/getsentiment/", methods=['POST'])
# def get_sentiment():
#     # response = flask.jsonify({'some': 'data'})
#     # response.headers.add('Access-Control-Allow-Origin', '*')
#     if request.method == "POST":
#         tmp = request.get_json()
#         s = tmp['noiDung']
#         idThucAn = tmp['idThucAn']
#         idNguoiDung = tmp['idNguoiDung']
#         sentiment_object.phan_loai_sentiment(str(s), idThucAn, idNguoiDung, rec)
#     return str(s)


# @app.route("/getcountsentiment/", methods=['GET'])
# def get_count_sentiment():
#     # response = flask.jsonify({'some': 'data'})
#     # response.headers.add('Access-Control-Allow-Origin', '*')
#     list_count = []
#     if request.method == "GET":
#         pos = sentiment_object.countif_excel('positive')
#         neg = sentiment_object.countif_excel('negative')
#         neu = sentiment_object.countif_excel('neutral')
#         list_count = {'positive': pos, 'negative': neg, 'neutral': neu}
#     return json.dumps(list_count, cls=NpEncoder, ensure_ascii=False).encode('utf8')


# @app.route("/getrec/", methods=['POST'])
# def get_rec():
#     # response = flask.jsonify({'some': 'data'})
#     # response.headers.add('Access-Control-Allow-Origin', '*')
#     list_rec = []
#     if request.method == "POST":
#         tmp = request.get_json()
#         s = tmp['idNguoiDung']
#         list_rec = rec.recommend_top(s, 0, sentiment_object.df_rec)
#         list_f = []
#         for r in list_rec:
#             if r['similar'] >= 0:
#                 f = sentiment_object.find_food_id(r['id'])
#                 list_f.append(f)

#     return json.dumps(list_f, cls=NpEncoder, ensure_ascii=False).encode('utf8')


# if __name__ == "__main__":
#     app.run(debug=True)

# # files_content = []
# #
# # emoji_pattern = re.compile("["
# #                            u"\U0001F600-\U0001F64F"  # emoticons
# #                            u"\U0001F300-\U0001F5FF"  # symbols & pictographs
# #                            u"\U0001F680-\U0001F6FF"  # transport & map symbols
# #                            u"\U0001F1E0-\U0001F1FF"  # flags (iOS)
# #                            "]+", flags=re.UNICODE)


# # try:
# #     for i in range(1, 10):
# #         path = './dataset{}/train/1/'.format(i)
# #         for filename in filter(lambda p: p.endswith("txt"), os.listdir(path)):
# #             filepath = os.path.join(path, filename)
# #             with open(filepath, mode='r', encoding="utf8") as f:
# #                 tmp = f.read()
# #                 # print(tmp)
# #                 files_content.append(tmp)
# #                 sentiment_object.phan_loai_sentiment(tmp)
# #
# #     print(files_content)
# # except:
# #     print(files_content)

# # sentiment_object.phan_loai_sentiment("Đây là quán đẹp nhất!")

# # get all y.txt files from all subdirectories
# def load_all_sentiment_from_txt():
#     all_files = glob.glob('/pythonProject/dataset/**/test/**/*.txt')
#     print(all_files)

#     for file in all_files:
#         with open(file, mode='r', encoding="utf8") as f:
#             tmp = f.read()
#             sentiment_object.phan_loai_sentiment(tmp)


# def load_all_sentiment_from_txt2(pos, sen, st):
#     s = '/pythonProject/dataset/**/test/{}/*.txt'.format(pos)
#     all_files = glob.glob(s)

#     df_combined = sen.df

#     for file in all_files:
#         with open(file, mode='r', encoding="utf8") as f:
#             tmp = f.read()
#             tmp = get_clean(tmp)
#             print(file)
#             print(tmp)
#             new_data = {'Review': [tmp], 'Sentiment': [st], 'idThucAn': 0, 'idNguoiDung': 0, 'idComment': 0}
#             df_new = pd.DataFrame(new_data)

#             # Append new data
#             df_combined = pd.concat([sen.df, df_new], ignore_index=True)

#             # Save the combined data to Excel
#             # df_combined.to_excel(sen.existing_file, index=False)
#             sen.df = df_combined
#     df_combined.to_excel(sen.existing_file, index=False)


# # load_all_sentiment_from_txt2(1, sentiment_object, 'positive')
# # load_all_sentiment_from_txt2(-1, sentiment_object, 'negative')
# # load_all_sentiment_from_txt2(2, sentiment_object, 'neutral')

# # print(sentiment_object.phan_loai_sentiment("day la commen test", 0, 0))


# # load_all_sentiment_from_txt()

# def write_to_txt(noiDung):
#     path = ''
#     if sentiment_object.phan_loai_sentiment(noiDung).__eq__('positive'):
#         path = '/pythonProject/dataset_test/1/' + str(sentiment_object.count) + '.txt'
#     else:
#         path = '/pythonProject/dataset_test/-1/' + str(sentiment_object.count) + '.txt'
#     with open(path, 'w', encoding="utf8") as f:
#         f.write(noiDung)
#         sentiment_object.count = sentiment_object.count + 1


# def excel_to_txt(df):
#     path = ''
#     for index, row in df.iterrows():
#         if row['Sentiment'].__eq__('positive'):
#             path = '/pythonProject/dataset_test/1/' + str(sentiment_object.count) + '.txt'
#         elif row['Sentiment'].__eq__('neutral'):
#             path = '/pythonProject/dataset_test/2/' + str(sentiment_object.count) + '.txt'
#         else:
#             path = '/pythonProject/dataset_test/-1/' + str(sentiment_object.count) + '.txt'
#         with open(path, 'w', encoding="utf8") as f:
#             f.write(str(row['Review']))
#         sentiment_object.count = sentiment_object.count + 1


# # write_to_txt("Quán tệ")


# # excel_to_txt(sentiment_object.df)


# def read_sentiment():
#     all_files = glob.glob('/pythonProject/dataset_test/**/*.txt')
#     print(all_files)

#     for file in all_files:
#         with open(file, mode='r', encoding="utf8") as f:
#             tmp = f.read()
#             print(tmp)
#             print(pos_tag(tmp))

# # read_sentiment()
