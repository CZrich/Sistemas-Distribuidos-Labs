import mysql.connector
#from mysql.connector import Error

def crear_base_de_datos_y_tabla():
    try:
        # Conectar al servidor MySQL
        conexion = mysql.connector.connect(
            host='localhost',
            user='root',
            password=''
        )

        if conexion.is_connected():
            print("Conexión exitosa al servidor MySQL")

            cursor = conexion.cursor()

            # Crear base de datos si no existe
            cursor.execute("CREATE DATABASE IF NOT EXISTS colegio")
            cursor.execute("USE colegio")

            # Crear tabla estudiantes si no existe
            cursor.execute("""
                CREATE TABLE IF NOT EXISTS estudiantes (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    nombre VARCHAR(100) NOT NULL,
                    apellido VARCHAR(100) NOT NULL,
                    edad INT NOT NULL,
                    grado VARCHAR(50) NOT NULL
                )
            """)
            print("Base de datos y tabla creadas o ya existen")

    except Error as e:
        print(f"Error: {e}")

    finally:
        if cursor:
            cursor.close()
        if conexion.is_connected():
            conexion.close()
            print("Conexión cerrada")

def realizar_transaccion():
    try:
        # Conectar a la base de datos
        conexion = mysql.connector.connect(
            host='localhost',
            database='colegio',
            user='root',
            password=''
        )

        if conexion.is_connected():
            print("Conexión exitosa a la base de datos")

            # Crear un cursor
            cursor = conexion.cursor()

            # Iniciar la transacción
            conexion.start_transaction()

            # Ejemplo de inserción de datos
            sql_insert_1 = "INSERT INTO estudiantes (nombre, apellido, edad, grado) VALUES (%s, %s, %s, %s)"
            valores_1 = ('Juan', 'Pérez', 15, '10mo')
            cursor.execute(sql_insert_1, valores_1)

            sql_insert_2 = "INSERT INTO estudiantes (nombre, apellido, edad, grado) VALUES (%s, %s, %s, %s)"
            valores_2 = ('María', 'Gómez', 14, '9no')
            #raise Exception("Simulación de error")
            cursor.execute(sql_insert_2, valores_2)

            # Confirmar la transacción
            conexion.commit()
            print("Transacción completada con éxito")

    except Exception as e:
        print(f"Error: {e}")
        # Revertir la transacción en caso de error
        if conexion.is_connected():
            print("Transacción revertida")
            conexion.rollback()
            

    finally:
        # Cerrar el cursor y la conexión
        if cursor:
            cursor.close()
        if conexion.is_connected():
            conexion.close()
            print("Conexión cerrada")

# Crear la base de datos y la tabla
crear_base_de_datos_y_tabla()

# Llamar a la función para realizar la transacción
realizar_transaccion()
