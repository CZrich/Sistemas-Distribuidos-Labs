import java.io.*;
import java.net.*;

public class Servidor {
    // Puerto en el que el servidor escucha las conexiones entrantes
    static final int PUERTO = 5000;

    public Servidor() {
        try {
            // 1. Se crea un ServerSocket para escuchar conexiones en el puerto especificado
            ServerSocket skServidor = new ServerSocket(PUERTO);
            System.out.println("Escucho el puerto " + PUERTO);

            // 2. Se limita la atención a 3 clientes utilizando un bucle for
            for (int numCli = 0; numCli < 3; numCli++) {
                // 3. El método accept() espera a que un cliente se conecte, y devuelve un nuevo socket
                Socket skCliente = skServidor.accept(); 
                System.out.println("Sirvo al cliente " + numCli);

                // 4. Se obtiene el flujo de salida del socket para enviar datos al cliente
                OutputStream aux = skCliente.getOutputStream();
                DataOutputStream flujo = new DataOutputStream(aux);

                // 5. Se envía un mensaje al cliente utilizando el flujo de salida
                flujo.writeUTF("Hola cliente " + numCli);

                // 6. Se cierra el socket del cliente una vez enviada la respuesta
                skCliente.close();
            }

            // 7. Mensaje de finalización luego de atender a 3 clientes
            System.out.println("Demasiados clientes por hoy");

        } catch (Exception e) {
            // 8. Captura de excepciones y muestra del mensaje de error
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] arg) {
        new Servidor(); // Se instancia el servidor para iniciar la escucha
    }
}
