import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServer {
    private static List<PrintWriter> clients = new ArrayList<>();

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(5555)) {
            System.out.println("Chat Server is running...");
            while (true) {
                new ClientHandler(serverSocket.accept()).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class ClientHandler extends Thread {
        private Socket clientSocket;
        private PrintWriter out;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        public void run() {
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                synchronized (clients) {
                    clients.add(out);
                }

                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    if ("Quiero cerrar el chat".equals(inputLine)) {
                        break;
                    }
                    sendMessageToAll(inputLine);
                }

                synchronized (clients) {
                    clients.remove(out);
                }
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void sendMessageToAll(String message) {
            synchronized (clients) {
                for (PrintWriter client : clients) {
                    client.println(message);
                }
            }
        }
    }
}
