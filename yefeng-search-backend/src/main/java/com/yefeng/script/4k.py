# @作者 : 叶枫
# @文件 : 4k壁纸大全.py
# @时间 : 2021/10/25 21:18
# @版本 ：1.0
# @功能描述: 爬取4k图片
import argparse
import datetime
import os.path
import pymysql
import requests
from lxml import etree


def download():
    cursor, conn = connect()
    response = requests.get(url, headers=headers)
    if response.status_code != 200:
        print("异常")
        return
    response.encoding = "GBK"
    tree = etree.HTML(response.text)
    list_img = tree.xpath('//div[@class="slist"]/ul/li/a/@href')
    list_title = tree.xpath('//div[@class="slist"]/ul/li/a/b/text()')
    for item in range(len(list_title)):
        new_url = child_url + list_img[item]
        resp = requests.get(new_url, headers=headers).text
        tre = etree.HTML(resp)
        child = tre.xpath('//*[@id="img"]/img/@src')[0]
        img_url = child_url + child
        today_time = datetime.datetime.now()  # 获取当前时间
        today_time_str = today_time.strftime('%Y-%m-%d %H:%M:%S')
        sql = ('insert into picture (title, urlImg, createTime) values("{}", "{}", "{}")'
               .format(list_title[item], img_url, today_time_str))
        try:
            cursor.execute(sql)
            conn.commit()
        except Exception as e:
            print(e)
            conn.rollback()
    conn.close()


def connect():
    # 建立数据库连接
    conn = pymysql.connect(
        host='101.35.87.146',  # 主机名（或IP地址）
        port=3306,  # 端口号，默认为3306
        user='root',  # 用户名
        password='031214t+',  # 密码
        charset='utf8mb4'  # 设置字符编码
    )
    # 选择数据库
    conn.select_db("es")
    # 创建游标对象
    cursor = conn.cursor()
    return cursor, conn


if __name__ == "__main__":
    parser = argparse.ArgumentParser()
    parser.add_argument('--page', type=int, default=None)
    parser.add_argument('--pagesize', type=int, default=32)
    args = parser.parse_args()
    # 地址
    child_url = "https://pic.netbian.com"

    headers = {
        'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.183 Safari/537.36',
        "referer": "https://pic.netbian.com/4kmeinv/"
    }

    if not os.path.exists("img_src"):
        os.mkdir("img_src")
    i = args.page
    url = "https://pic.netbian.com/4kmeinv"
    if i > 1:
        suffix = f"/index_{i}.html"
        url = "https://pic.netbian.com/4kmeinv" + suffix
    download()
