import mysql.connector
from mysql.connector import Error

try:
    connection = mysql.connector.connect(user='root', password='011035788Aa-+-',
                              host='localhost',
                              database='csit')

    if connection.is_connected():
        cursor = connection.cursor()
        cursor.execute("SELECT ID, ID_restaurant FROM combo")
        combo_ids = cursor.fetchall()

        for combo_id, restaurant_id in combo_ids:
            cursor.execute(f"SELECT ID FROM dish WHERE ID_restaurant = {restaurant_id} ORDER BY RAND() LIMIT 3")
            dish_ids = cursor.fetchall()

            for dish_id in dish_ids:
                query = f"""INSERT INTO dish_combo (ID_combo, ID_dish)
                            VALUES ({combo_id}, {dish_id[0]})"""
                cursor.execute(query)

        connection.commit()

except Error as e:
    print("Error while connecting to MySQL", e)

finally:
    if connection.is_connected():
        cursor.close()
        connection.close()
        print("MySQL connection is closed")