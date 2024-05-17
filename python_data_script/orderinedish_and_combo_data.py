import mysql.connector
from mysql.connector import Error
import random

try:
    connection = mysql.connector.connect(user='root', password='011035788Aa-+-',
                              host='localhost',
                              database='csit')

    if connection.is_connected():
        cursor = connection.cursor()
        cursor.execute("SELECT ID FROM orders")
        order_ids = cursor.fetchall()

        cursor.execute("SELECT ID FROM dish")
        dish_ids = cursor.fetchall()

        cursor.execute("SELECT ID FROM combo")
        combo_ids = cursor.fetchall()

        for order_id in order_ids:
            chosen_dish_ids = random.sample(dish_ids, 3)
            for dish_id in chosen_dish_ids:
                query = f"""INSERT INTO orderlines_dish (ID_order, ID_dish, quantity)
                            VALUES ({order_id[0]}, {dish_id[0]}, 1)"""
                cursor.execute(query)

            chosen_combo_ids = random.sample(combo_ids, 2)
            for combo_id in chosen_combo_ids:
                query = f"""INSERT INTO orderlines_combo (ID_order, ID_combo, quantity)
                            VALUES ({order_id[0]}, {combo_id[0]}, 1)"""
                cursor.execute(query)

        connection.commit()

except Error as e:
    print("Error while connecting to MySQL", e)

finally:
    if connection.is_connected():
        cursor.close()
        connection.close()
        print("MySQL connection is closed")