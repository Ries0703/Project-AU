import mysql.connector
from mysql.connector import Error
import random
from PIL import Image
import io

try:
    connection = mysql.connector.connect(user='root', password='011035788Aa-+-',
                              host='localhost',
                              database='csit')

    if connection.is_connected():
        cursor = connection.cursor()
        cursor.execute("SELECT ID FROM restaurants")
        restaurant_ids = cursor.fetchall()

        # List of combo names
        combo_names = ["Combo 1", "Combo 2", "Combo 3", "Combo 4", "Combo 5", "Combo 6", "Combo 7", "Combo 8", "Combo 9", "Combo 10"]

        # Read and compress the image file
        with Image.open('food.jpg') as img:
            img_byte_arr = io.BytesIO()
            img.save(img_byte_arr, format='JPEG', quality=20)
            img_byte_arr = img_byte_arr.getvalue()

        for restaurant_id in restaurant_ids:
            for i in range(10):
                # Generate random data
                combo_name = random.choice(combo_names)
                combo_price = round(random.uniform(5, 50), 2)
                combo_description = f"This is {combo_name}"
                combo_bestseller_status = 0

                query = f"""INSERT INTO combo (ID_restaurant, name, price, image, description, bestseller_status)
                            VALUES ({restaurant_id[0]}, '{combo_name}', {combo_price}, %s, '{combo_description}', {combo_bestseller_status})"""
                cursor.execute(query, (img_byte_arr,))

        connection.commit()

except Error as e:
    print("Error while connecting to MySQL", e)

finally:
    if connection.is_connected():
        cursor.close()
        connection.close()
        print("MySQL connection is closed")