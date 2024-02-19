import java.io.*;
import java.net.*;

public class Servidor {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(55555);
        System.out.println("Esperando jugadores...");
        
        try {
            Socket jugador1 = serverSocket.accept();
            System.out.println("Jugador 1 conectado.");
            Socket jugador2 = serverSocket.accept();
            System.out.println("Jugador 2 conectado.");
            
            jugar(jugador1, jugador2);
        } finally {
            serverSocket.close();
        }
    }

    private static void jugar(Socket jugador1, Socket jugador2) throws IOException {
        BufferedReader in1 = new BufferedReader(new InputStreamReader(jugador1.getInputStream()));
        PrintWriter out1 = new PrintWriter(jugador1.getOutputStream(), true);
        BufferedReader in2 = new BufferedReader(new InputStreamReader(jugador2.getInputStream()));
        PrintWriter out2 = new PrintWriter(jugador2.getOutputStream(), true);
        
        int contadorJugador1 = 0;
        int contadorJugador2 = 0;
        
        String inputLine1, inputLine2;
        
        while (contadorJugador1 < 5 && contadorJugador2 < 5) {
            // Turno del Jugador 1
            out1.println("Tu turno.");
            inputLine1 = in1.readLine();
            System.out.println("Jugador 1: " + inputLine1);
            if (inputLine1.equals("tocado")) {
                contadorJugador1++;
                if (contadorJugador1 == 5) {
                    out1.println("Tocado y hundido! ¡Felicidades, has ganado!");
                    out2.println("Tocado y hundido! ¡Lo siento, has perdido!");
                    break;
                }
            } else {
                contadorJugador1 = 0;
            }
            
            // Turno del Jugador 2
            out2.println("Tu turno.");
            inputLine2 = in2.readLine();
            System.out.println("Jugador 2: " + inputLine2);
            if (inputLine2.equals("tocado")) {
                contadorJugador2++;
                if (contadorJugador2 == 5) {
                    out2.println("Tocado y hundido! ¡Felicidades, has ganado!");
                    out1.println("Tocado y hundido! ¡Lo siento, has perdido!");
                    break;
                }
            } else {
                contadorJugador2 = 0;
            }
        }
        
        // Cerrar conexiones
        jugador1.close();
        jugador2.close();
    }
}
