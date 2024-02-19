import java.io.*;
import java.net.*;

public class Cliente {
    public static void main(String[] args) throws IOException {
        
        try (Socket clienteSocket = new Socket("localhost", 55555);
             PrintWriter out = new PrintWriter(clienteSocket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(clienteSocket.getInputStream()));
             BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in))) {
            
            String fromServer, fromUser;
            
            while ((fromServer = in.readLine()) != null) {
                System.out.println("Servidor: " + fromServer);
                if (fromServer.equals("Tocado y hundido!")) {
                    break;
                }
                
                fromUser = stdIn.readLine();
                if (fromUser != null) {
                    System.out.println("Cliente: " + fromUser);
                    out.println(fromUser);
                }
            }
        } catch (UnknownHostException e) {
            System.exit(1);
        } catch (IOException e) {
            System.exit(1);
        }
    }
}
