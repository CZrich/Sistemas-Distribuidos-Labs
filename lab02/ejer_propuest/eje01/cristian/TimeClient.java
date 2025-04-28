package lab02.ejer_propuest.eje01.cristian;


import java.net.*;
import java.io.*;
import java.util.concurrent.TimeUnit; 

public class TimeClient {

    private static final String SERVER_ADDRESS = "localhost"; // Dirección del servidor
    private static final int SERVER_PORT = 5000;             // Puerto del servidor
    private static final int TIMEOUT_MS = 3000;              // Tiempo de espera para la respuesta (3 segundos)

    // Simulamos el reloj del cliente con una variable
    // Inicialmente, lo desfasamos intencionalmente del reloj del sistema
    private static long clientClock = System.currentTimeMillis() - 5000; // Cliente 5 segundos atrás

    public static void main(String[] args) {
        DatagramSocket socket = null;
        try {
            socket = new DatagramSocket();
            socket.setSoTimeout(TIMEOUT_MS); // Establecer tiempo de espera para recibir

            InetAddress serverAddress = InetAddress.getByName(SERVER_ADDRESS);

            // Realizar varias sincronizaciones para observar el ajuste
            for (int i = 0; i < 5; i++) {
                System.out.println("--- Intento de Sincronización " + (i + 1) + " ---");
                System.out.println("Reloj local (simulado) ANTES: " + clientClock + " (" + new java.util.Date(clientClock) + ")");
                System.out.println("Reloj del sistema ANTES:     " + System.currentTimeMillis() + " (" + new java.util.Date(System.currentTimeMillis()) + ")");


                // 1. Registrar tiempo de envío (T0)
                long T0 = System.currentTimeMillis();

                // Preparar y enviar solicitud (puede ser un mensaje simple)
                String request = "GET_TIME";
                byte[] sendBuffer = request.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, serverAddress, SERVER_PORT);

                socket.send(sendPacket);
                System.out.println("Solicitud enviada al servidor.");

                // Preparar para recibir respuesta
                byte[] receiveBuffer = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);

                try {
                    // 2. Recibir respuesta y registrar tiempo de recepción (T1)
                    socket.receive(receivePacket);
                    long T1 = System.currentTimeMillis();
                    System.out.println("Respuesta recibida del servidor.");

                    // Extraer la hora del servidor (Ts) de la respuesta
                    String timeString = new String(receivePacket.getData(), 0, receivePacket.getLength());
                    long Ts = Long.parseLong(timeString);

                    // 3. Calcular RTT
                    long RTT = T1 - T0;

                    // 4. Calcular tiempo estimado del servidor al momento de llegada
                    long estimatedServerTime = Ts + RTT / 2;

                    // 5. Calcular el desplazamiento y ajustar el reloj local (simulado)
                    long offset = estimatedServerTime - clientClock;

                    System.out.println("  T0 (Envío):           " + T0);
                    System.out.println("  T1 (Recepción):       " + T1);
                    System.out.println("  RTT (T1 - T0):        " + RTT + " ms");
                    System.out.println("  Ts (Hora Servidor):   " + Ts);
                    System.out.println("  Hora Estimada (Ts + RTT/2): " + estimatedServerTime);
                    System.out.println("  Offset (Estimada - Local): " + offset + " ms");


                    // Aplicar la regla: solo avanzar el reloj (o ajustarlo si el desfase es positivo)
                    if (estimatedServerTime > clientClock) {
                         clientClock = estimatedServerTime;
                         System.out.println("Reloj local (simulado) AJUSTADO a: " + clientClock + " (" + new java.util.Date(clientClock) + ")");
                    } else {
                         System.out.println("El reloj local (simulado) ya está adelantado o igual. No se necesita ajustar hacia atrás.");
                         // Aunque no ajustamos hacia atrás en la simulación simple,
                         // en sistemas reales a veces se reduce la velocidad del reloj
                         // para que converja gradualmente, en lugar de saltar hacia atrás.
                    }

                } catch (SocketTimeoutException e) {
                    System.err.println("Tiempo de espera agotado. No se recibió respuesta del servidor.");
                } catch (NumberFormatException e) {
                    System.err.println("Error al parsear la hora del servidor: " + e.getMessage());
                }

                // Pequeña pausa antes del siguiente intento de sincronización
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                System.out.println(); // Línea en blanco para separar intentos
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (socket != null) {
                socket.close();
            }
        }
    }
}
