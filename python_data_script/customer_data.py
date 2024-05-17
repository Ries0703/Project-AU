import mysql.connector

# Establish the connection
cnx = mysql.connector.connect(user='root', password='011035788Aa-+-',
                              host='localhost',
                              database='csit')

# Create a cursor object
cursor = cnx.cursor()

# SQL query string
query = ("INSERT INTO customers "
         "(Email, Password, First_name, Last_name, Phone_number) "
         "VALUES (%s, %s, %s, %s, %s)")

# Generate 20 users
for i in range(21, 51):
    email = f"example_{i}@gmail.com"
    password = "123456"
    first_name = f"First_{i}"
    last_name = f"Last_{i}"
    phone_number = f"123456789{i}"

    # Execute the query
    cursor.execute(query, (email, password, first_name, last_name, phone_number))

# Commit the transaction
cnx.commit()

# Close the cursor and connection
cursor.close()
cnx.close()