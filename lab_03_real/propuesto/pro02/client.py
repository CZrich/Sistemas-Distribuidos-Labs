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
        try
