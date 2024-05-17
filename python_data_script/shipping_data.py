import mysql.connector
from mysql.connector import Error
import random

try:
    connection = mysql.connector.connect(user='root', password='011035788Aa-+-',
                              host='localhost',
                              database='csit')

    if connection.is_connected():
        cursor = connection.cursor()
        cursor.execute("SELECT ID FROM orders WHERE payment_status = 'paid'")
        order_ids = cursor.fetchall()

        shipping_methods = ["Fast", "Normal"]
        shipping_statuses = ["Failed", "Failed", "Succeeded"]

        for order_id in order_ids:
            for status in shipping_statuses:
                method = random.choice(shipping_methods)
                query = f"""INSERT INTO shipping (ID_Order, Method, Status)
                            VALUES ({order_id[0]}, '{method}', '{status}')"""
                cursor.execute(query)

        connection.commit()

except Error as e:
    print("Error while connecting to MySQL", e)

finally:
    if connection.is_connected():
        cursor.close()
        connection.close()
        print("MySQL connection is closed")