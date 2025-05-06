import socket
import threading
import pickle # Para la serialización y desearialización de los objetos

# Tipos de mensaje validos
WHOISIN = 0
MESSAGE = 1
LOGOUT = 2

class Client:
    def __init__(self, host='localhost', port=1500):
        # Definimos la dirrecion y el puerto
        self.host = host
        self.port = port
        # Configurando el Socket para la conexion con el servidor
        # AF_INET -> IPv4
        # SOCK_STREAM -> usara TCP (Cliente-Servidor)
        self.sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    
    def start(self):
        # Intento de conexión con el servidor
        try:
            self.sock.connect((self.host, self.port))
        except Exception as e:
            print(f"No se pudo conectar al servidor: {e}")
            return
        
        # Envio del nombre de usuario como primer mensaje ( NO serializado)
        self.username = input("Ingrese nombre de usuario: ")
        self.sock.send(self.username.encode())

        # Intrucciones
        print("\n¡Bienvenido al chat EPIS!!")
        print("Lista de comandos disponibles:")
        print("1. Escribe un mensaje y presiona Enter para enviarlo a todos.")
        print("2. Escribe '@usuario mensaje' para enviar un mensaje privado.")
        print("3. Escribe 'WHOISIN' para ver quiénes están conectados.")
        print("4. Escribe 'LOGOUT' para salir del chat.\n")

        # Hilo para los mensajes de servidor en segundo plano

        receive_thread = threading.Thread(target=self.receive_messages)
        receive_thread.daemon = True # El hilo cierra si el programa principal igual
        receive_thread.start()

        # Bucle para los mensajes desde consola

        while True:
            msg = input("> ")
            if msg.upper == "LOGOUT":
                self.send.message(LOGOUT, "")
                break # Cortamos el bucle y cerramos el socket
            elif msg.upper() == "WHOISIN":
                self.send_message(WHOISIN, "")
            else:
                self.send_message(MESSAGE, msg)

        self.sock.close() # Cierre de conexion
    
    def send_message(self, msg_type, content):
        # Diccionario para representar el ChatMessage
        message = {
            "type": msg_type,
            "content": content

        }
        try:
            # Serializamos el mensaje y lo enviamos
            self.sock.send(pickle.dumps(message))
        except:
            print("Error al enviar el mensaje")
            self.sock.close()
    
    def receive_messages(self):
        # Hilo que esta atento al servidor
        while True:
            try:
                data = self.sock.recv(4096)
                if not data:
                    break
                message = pickle.loads(data)
                print("\n"+message+"\n> ",end="")
            except:
                print("\n[Conexion cerrada por el servidor]")
                break
if __name__ == "__main__":
    # Cliente con valores por defecto
    client = Client()
    client.start()
        
