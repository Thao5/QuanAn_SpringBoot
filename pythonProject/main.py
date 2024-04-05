import json

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
import preprocess_kgptalkie as ps
import re
from flask_cors import CORS


app = Flask(__name__)
CORS(app)

conn = pymysql.connect(
    host='localhost',
    user='root',
    password='123456',
    db='quanan',
    charset='utf8mb4',
    cursorclass=pymysql.cursors.DictCursor
)


class NpEncoder(json.JSONEncoder):
    def default(self, obj):
        if isinstance(obj, np.integer):
            return int(obj)
        if isinstance(obj, np.floating):
            return float(obj)
        if isinstance(obj, np.ndarray):
            return obj.tolist()
        return super(NpEncoder, self).default(obj)



def select_comment():
    rows = None
    try:
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


def get_clean(x):
    x = str(x).lower().replace('\\', '').replace('_', ' ')
    x = ps.cont_exp(x)
    x = ps.remove_emails(x)
    x = ps.remove_urls(x)
    x = ps.remove_html_tags(x)
    # x = ps.remove_accented_chars(x)
    x = ps.remove_special_chars(x)
    x = re.sub("(.)\\1{2,}", "\\1", x)
    return x


def get_data_from_database():
    df = pd.read_excel("Book2.xlsx")
    df['Review'] = df['Review'].apply(lambda x: get_clean(x))
    tfidf = TfidfVectorizer(max_features=100)
    X = df['Review']
    y = df['Sentiment']
    X = tfidf.fit_transform(X)
    # print(X)

    X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, random_state=0)

    clf = LinearSVC()
    clf.fit(X_train, y_train)

    y_pred = clf.predict(X_test)
    print(classification_report(y_test, y_pred))

    danh_gias = select_comment()
    # Existing Excel file
    existing_file = 'Book2.xlsx'
    # Read existing data
    df_existing = pd.read_excel(existing_file)

    for dg in danh_gias:
        x = dg['noi_dung']
        print(x)
        x = get_clean(x)
        vec = tfidf.transform([x])
        vec.shape
        print(clf.predict(vec))
        tmp = str(clf.predict(vec)).strip('[]\'')

        # New data to append
        new_data = {'Review': [x], 'Sentiment': [tmp]}
        df_new = pd.DataFrame(new_data)

        # Append new data
        df_combined = pd.concat([df, df_new], ignore_index=True)

    # Save the combined data to Excel
    df_combined.to_excel(existing_file, index=False)

# get_data_from_database()


# @app.route("/")
# def main():
#     return render_template("index.html")

def phan_loai_sentiment(noiDung):
    df = pd.read_excel("Book2.xlsx")
    df['Review'] = df['Review'].apply(lambda x: get_clean(x))
    tfidf = TfidfVectorizer(max_features=100)
    X = df['Review']
    y = df['Sentiment']
    X = tfidf.fit_transform(X)
    # print(X)

    X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, random_state=0)

    clf = LinearSVC()
    clf.fit(X_train, y_train)

    y_pred = clf.predict(X_test)
    print(classification_report(y_test, y_pred))

    # Existing Excel file
    existing_file = 'Book2.xlsx'
    # Read existing data
    df_existing = pd.read_excel(existing_file)

    x = noiDung
    print(x)
    x = get_clean(x)
    vec = tfidf.transform([x])
    vec.shape
    print(clf.predict(vec))
    tmp = str(clf.predict(vec)).strip('[]\'')

    # New data to append
    new_data = {'Review': [x], 'Sentiment': [tmp]}
    df_new = pd.DataFrame(new_data)

    # Append new data
    df_combined = pd.concat([df, df_new], ignore_index=True)

    # Save the combined data to Excel
    df_combined.to_excel(existing_file, index=False)


# get_data_from_database()

def countif_excel(pos_neg_neutral):
    df = pd.read_excel("Book2.xlsx")
    df2 = df.copy()
    df2 = df2.loc[9:]
    df3 = df2.copy()
    df2 = df2[df2['Sentiment'].__eq__(pos_neg_neutral)]
    return str("{:.2f}%").format((df2[df2.columns[0]].count()/df3[df3.columns[0]].count())*100)


@app.route("/getsentiment/", methods=['POST'])
def get_sentiment():
    # response = flask.jsonify({'some': 'data'})
    # response.headers.add('Access-Control-Allow-Origin', '*')
    if request.method == "POST":
        tmp = request.get_json()
        sentiment = tmp['noiDung']
        phan_loai_sentiment(str(sentiment))
    return str(sentiment)


@app.route("/getcountsentiment/", methods=['GET'])
def get_count_sentiment():
    # response = flask.jsonify({'some': 'data'})
    # response.headers.add('Access-Control-Allow-Origin', '*')
    list_count = []
    if request.method == "GET":
        pos = countif_excel('positive')
        neg = countif_excel('negative')
        neu = countif_excel('neutral')
        list_count = {'positive': pos, 'negative': neg, 'neutral': neu}
    return json.dumps(list_count, cls=NpEncoder)


if __name__ == "__main__":
    app.run(debug=True)
