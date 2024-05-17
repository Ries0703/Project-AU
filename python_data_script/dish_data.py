import mysql.connector
from mysql.connector import Error
from faker import Faker
from PIL import Image
import io
import random

try:
    connection = mysql.connector.connect(user='root', password='011035788Aa-+-',
                              host='localhost',
                              database='csit')

    if connection.is_connected():
        cursor = connection.cursor()
        cursor.execute("SELECT ID FROM restaurants")
        restaurant_ids = cursor.fetchall()

        # Create a Faker instance
        fake = Faker()

        # Read the image file
        with Image.open('food.jpg') as img:
            img_byte_arr = io.BytesIO()
            img.save(img_byte_arr, format='JPEG', quality=20)
            img_byte_arr = img_byte_arr.getvalue()
        food_names = ["Pizza", "Burger", "Pasta", "Steak", "Salad", "Sushi", "Ramen", "Tacos", "Fish and Chips", "Chicken Curry"]
        for restaurant_id in restaurant_ids:
            for i in range(20):
                # Generate random data
                dish_name = random.choice(food_names)
                dish_price = round(fake.random.uniform(5, 50), 2)
                dish_description = fake.sentence()
                dish_bestseller_status = 0

                query = f"""INSERT INTO dish (ID_restaurant, name, price, image, description, bestseller_status)
                            VALUES ({restaurant_id[0]}, '{dish_name}', {dish_price}, %s, '{dish_description}', {dish_bestseller_status})"""
                cursor.execute(query, (img_byte_arr,))

        connection.commit()

except Error as e:
    print("Error while connecting to MySQL", e)

finally:
    if connection.is_connected():
        cursor.close()
        connection.close()
        print("MySQL connection is closed")