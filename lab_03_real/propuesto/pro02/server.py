import socket
import threading
import pickle
from datetime import datetime

# Lista de clientes, hilos en realidad
clients = []
# Para la sincronizacion del acceso a la lista de clientes
lock = threading.Lock()

# Tipos de mensajes
WHOISIN = 0
MESSAGE = 1
LOGOUT = 2

# Clase para cada cliente
class ClientThread(threading.Thread):
    def __init__(self, conn, addr):
        threading.Thread.__init__(self)
        self.conn = conn        # Con para el socket
        self.addr = addr        # dir IP para el cliente
        self.username = ""      # el nombre del usuario
        self.active = True      # Estado
        self.start_time = datetime.now().strftime("%H:%M:%S")   # La hora de la conexion

    def run(self):
        try:
            # Se recibira el nombre del usuario como el primer mensaje
            # Hasta 1024 bytes de datos
            self.username = self.conn.recv(1024).decode()
            print(f"{self.username} se ha conectado desde {self.addr}")
            broadcast(f"---- {self.username} ha entrado al chat -----", self)

            # Se agrega este cliente a la lista global
            with lock:
                clients.append(self)
            
            # Bucle del hilo para recibir los mensajes
            while self.active:
                data = self.conn.recv(4096) # Esto recibe un objeto serializado
                if not data:
                    break
                message_obj = pickle.loads(data) # Para deserializar

                if message_obj["type"] == MESSAGE:
                    msg = message_obj["content"]
                    if msg.startswith("@"):     # Para los mensajes privados
                        parts = msg.split(" ", 1)
                        target_user = parts[0][1:]
                        private_msg = parts[1] if len(parts) >  1 else ""
                        send_private(f"{self.username} (privado): {private_msg}", target_user)
                    else:
                        # Para los mensajes publicos
                        broadcast(f"{self.username}: {msg}", self)

                elif message_obj["type"] == WHOISIN:
                    # Lista de usuarios conectados
                    self.send(f"Usuarios conectados a las {datetime.now().strftime('%H:%M:%S')}")
                    with lock:
                        for c in clients:
                            self.send(f"- {c.username} desde {c.start_time}")
                
                elif message_obj["type"] == LOGOUT:
                    # DESCONECTAR
                    print(f"{self.username} se ha desconectado")
                    self.active = False
                    break
        except Exception as e:
            print(f"Error con {self.username}: {e}")

        finally:
            # Para desconectar y limpiar
            self.disconnect()
    
    # Para enviar el mensaje SERIALIZADO al cliente
    def send(self, message):
        try:
            self.conn.send(pickle.dumps(message))
        except:
            self.active = False
    
    # Eliminar cliente de la lista y cerrar la conexion
    def disconnect(self):      
        with lock:
            if self in clients:
                clients.remove(self)
        self.conn.close()
        broadcast(f"--- {self.username} ha salido del chat ---", self)

# Para enviar un mensaje a todos, excepto al propio
def broadcast(message, sender=None):
    print(message)
    with lock:
        for client in clients:
            if client != sender and client.active:
                client.send(message)

# Enviar un mensaje privado
def send_private(message, target_username):
    found = False
    with lock:
        for client in clients:
            if client.username == target_username:
                client.send(message)
                found = True   
                break
    if not found and sender:
        sender.send("--- Usuario no encontrado ---")

# Principal para el servidor
def main():
    host = "localhost"
    port = 1500
    server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    server_socket.bind((host, port))
    server_socket.listen(10)
    print(f"[{datetime.now().strftime('%H:%M:%S')}] Servidor esperando a los clientes en el puerto {port}")
    
    try:
        while True:
            conn, addr = server_socket.accept()
            new_thread = ClientThread(conn, addr)
            new_thread.start()
    except KeyboardInterrupt:
        print("\nServidor pausado manualmente")
    finally:
        server_socket.close()
    
if __name__ == "__main__":
    main()
            
                    
                    
