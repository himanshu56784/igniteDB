package client;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class TestClient {
    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("TestClient is running...");
        // You can add code here to test the client functionality

        try (Socket socket = new Socket("localhost", 6379)) {
            System.out.println("Connected to ignite on port 6379");

            OutputStream outputStream = socket.getOutputStream();

            outputStream.write("PING".getBytes(StandardCharsets.UTF_8));

            outputStream.flush();

            System.out.println("PING sent");
            // You can add code here to send requests to the server and read responses

            Thread.sleep(5000);
        } catch (IOException e) {
            System.err.println("Failed to connect to ignite: " + e.getMessage());

        }
    }
}
