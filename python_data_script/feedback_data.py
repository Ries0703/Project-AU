import mysql.connector
from mysql.connector import Error
import random

try:
    connection = mysql.connector.connect(user='root', password='011035788Aa-+-',
                              host='localhost',
                              database='csit')

    if connection.is_connected():
        cursor = connection.cursor()
        cursor.execute("SELECT ID FROM restaurants")
        restaurant_ids = cursor.fetchall()

        cursor.execute("SELECT ID FROM customers")
        customer_ids = cursor.fetchall()

        feedback_contents = ["Great!", "Good", "Average", "Bad", "Terrible"]

        for restaurant_id in restaurant_ids:
            for _ in range(10):
                customer_id = random.choice(customer_ids)[0]
                stars = random.randint(1, 5)
                content = random.choice(feedback_contents)
                query = f"""INSERT INTO feedback (ID_customer, ID_restaurant, Stars, content)
                            VALUES ({customer_id}, {restaurant_id[0]}, {stars}, '{content}')"""
                cursor.execute(query)

        connection.commit()

except Error as e:
    print("Error while connecting to MySQL", e)

finally:
    if connection.is_connected():
        cursor.close()
        connection.close()
        print("MySQL connection is closed")