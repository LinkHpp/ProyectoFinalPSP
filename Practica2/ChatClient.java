import java.io.*;
import java.net.*;

public class ChatClient {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 5555);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in))) {

            System.out.println("Connected to Chat Server. Start chatting!");

            Thread receiveThread = new Thread(() -> {
                String message;
                try {
                    while ((message = in.readLine()) != null) {
                        System.out.println(message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            receiveThread.start();

            Thread sendThread = new Thread(() -> {
                String userInput;
                try {
                    while ((userInput = stdIn.readLine()) != null) {
                        out.println(userInput);
                        if ("Quiero cerrar el chat".equals(userInput)) {
                            break;
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            sendThread.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
