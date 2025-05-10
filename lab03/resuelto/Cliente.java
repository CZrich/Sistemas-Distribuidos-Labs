import java.io.*;
import java.net.*;

public class Cliente {
    // Definimos la dirección del servidor y el puerto al que se conectará el cliente
    static final String HOST = "localhost";
    static final int PUERTO = 5000;

    public Cliente() {
        try {
            // 1. Se crea un socket cliente que se conecta al servidor en HOST y PUERTO
            Socket skCliente = new Socket(HOST, PUERTO);

            // 2. Se obtiene el flujo de entrada (input stream) desde el socket
            InputStream aux = skCliente.getInputStream();

            // 3. Se envuelve el flujo en un DataInputStream para leer datos en formato UTF
            DataInputStream flujo = new DataInputStream(aux);

            // 4. Se lee una cadena enviada por el servidor y se imprime en pantalla
            System.out.println(flujo.readUTF());

            // 5. Se cierra el socket una vez que se termina la comunicación
            skCliente.close();

        } catch (Exception e) {
            // 6. Se captura cualquier excepción que ocurra en la conexión o lectura
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] arg) {
        new Cliente(); // Se crea una instancia del cliente para ejecutar la conexión
    }
}
