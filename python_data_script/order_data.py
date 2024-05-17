import mysql.connector
from mysql.connector import Error
import random
from datetime import datetime, timedelta

try:
    connection = mysql.connector.connect(user='root', password='011035788Aa-+-',
                              host='localhost',
                              database='csit')

    if connection.is_connected():
        cursor = connection.cursor()
        cursor.execute("SELECT ID FROM customers")
        customer_ids = cursor.fetchall()

        cursor.execute("SELECT ID FROM restaurants")
        restaurant_ids = cursor.fetchall()

        for customer_id in customer_ids:
            for _ in range(5):
                restaurant_id = random.choice(restaurant_ids)[0]
                customer_address = "123 Main St"
                postcode_address = "12345"
                card_number = "".join([str(random.randint(0, 9)) for _ in range(16)])
                ccv = "".join([str(random.randint(0, 9)) for _ in range(3)])
                expiration = datetime.now() + timedelta(days=365)
                order_status = 1
                amount = round(random.uniform(5, 50), 2)
                payment_status = "Paid"

                query = f"""INSERT INTO orders (ID_customer, ID_restaurant, Customer_address, PostCode_address, Card_number, CCV, Expiration, order_status, amount, payment_status)
                            VALUES ({customer_id[0]}, {restaurant_id}, '{customer_address}', '{postcode_address}', '{card_number}', '{ccv}', '{expiration.strftime('%Y-%m-%d')}', {order_status}, {amount}, '{payment_status}')"""
                cursor.execute(query)

        connection.commit()

except Error as e:
    print("Error while connecting to MySQL", e)

finally:
    if connection.is_connected():
        cursor.close()
        connection.close()
        print("MySQL connection is closed")