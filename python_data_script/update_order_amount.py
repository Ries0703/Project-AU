import mysql.connector
from mysql.connector import Error

try:
    connection = mysql.connector.connect(user='root', password='011035788Aa-+-',
                              host='localhost',
                              database='csit')

    if connection.is_connected():
        cursor = connection.cursor()

        cursor.execute("SELECT ID, PostCode_address, ID_restaurant FROM orders")
        order_data = cursor.fetchall()

        for order in order_data:
            order_id, order_postcode, restaurant_id = order

            cursor.execute(f"SELECT Post_Code FROM restaurants WHERE ID = {restaurant_id}")
            restaurant_postcode = cursor.fetchone()[0]

            shipping_fee = abs(int(order_postcode) - int(restaurant_postcode)) * 0.002

            cursor.execute(f"""
                SELECT SUM(c.price * oc.quantity)
                FROM combo c
                JOIN orderlines_combo oc ON c.ID = oc.ID_combo
                WHERE oc.ID_order = {order_id}
            """)
            combo_total = cursor.fetchone()[0] or 0

            cursor.execute(f"""
                SELECT SUM(d.price * od.quantity)
                FROM dish d
                JOIN orderlines_dish od ON d.ID = od.ID_dish
                WHERE od.ID_order = {order_id}
            """)
            dish_total = cursor.fetchone()[0] or 0

            total = float(combo_total) + float(dish_total) + float(shipping_fee)

            cursor.execute(f"""
                UPDATE orders
                SET amount = {total}
                WHERE ID = {order_id}
            """)

        connection.commit()

except Error as e:
    print("Error while connecting to MySQL", e)

finally:
    if connection.is_connected():
        cursor.close()
        connection.close()
        print("MySQL connection is closed")