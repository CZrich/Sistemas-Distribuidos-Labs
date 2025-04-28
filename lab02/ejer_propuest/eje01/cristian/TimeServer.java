package lab02.ejer_propuest.eje01.cristian;

import java.net.*;
import java.io.*;

public class TimeServer {

    private static final int PORT = 5000; // Puerto donde el servidor escuchará

    public static void main(String[] args) {
        DatagramSocket socket = null;
        try {
            // Crear socket UDP en el puerto especificado
            socket = new DatagramSocket(PORT);
            System.out.println("Servidor de tiempo iniciado en el puerto " + PORT);

            byte[] receiveBuffer = new byte[1024];

            while (true) {
                // Paquete para recibir datos del cliente
                DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);

                // Esperar y recibir la solicitud del cliente
                socket.receive(receivePacket);

                // Obtener la dirección y el puerto del cliente para enviar la respuesta
                InetAddress clientAddress = receivePacket.getAddress();
                int clientPort = receivePacket.getPort();

                // Obtener la hora actual del servidor en milisegundos
                long serverTime = System.currentTimeMillis();

                // Preparar la respuesta con la hora del servidor (convertir long a String y luego a bytes)
                String timeString = String.valueOf(serverTime);
                byte[] sendBuffer = timeString.getBytes();

                // Crear paquete para enviar al cliente
                DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, clientAddress, clientPort);

                // Enviar la hora del servidor de vuelta al cliente
                socket.send(sendPacket);

                // Opcional: imprimir que se sirvió una solicitud
                System.out.println("Solicitud de tiempo servida para " + clientAddress + ":" + clientPort + " con hora=" + serverTime);
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
