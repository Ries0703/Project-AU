import mysql.connector
import random
from datetime import datetime, timedelta

# Establish the connection
cnx = mysql.connector.connect(user='root', password='011035788Aa-+-',
                              host='localhost',
                              database='csit')

# Create a cursor object
cursor = cnx.cursor()

# List of restaurant categories
categories = ["asian", "fast_food", "cafes", "milk_tea", "mexican", "barbecue", "dessert", "healthy"]

# SQL query for inserting data
add_restaurant = ("INSERT INTO restaurants "
                  "(Email, Password, res_name, res_category, res_address, start_time, end_time, Phone, post_code) "
                  "VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s)")

# Insert 50 records
for i in range(1, 51):
    # Generate data
    email = f"restaurant_{i}@gmail.com"
    password = "123456"  # Replace with actual password
    res_name = f"Restaurant {i}"
    res_category = random.choice(categories)
    res_address = f"address_{i}"
    end_time = (datetime.now() + timedelta(hours=1)).time()  # Ensure start_time < end_time
    start_time = datetime.now().time()
    phone = "1234567890"  # Replace with actual phone number
    post_code = str(i * 1000)

    # Data for SQL query
    data_restaurant = (email, password, res_name, res_category, res_address, start_time, end_time, phone, post_code)

    # Execute the query
    cursor.execute(add_restaurant, data_restaurant)

# Commit the changes
cnx.commit()

# Close cursor and connection
cursor.close()
cnx.close()