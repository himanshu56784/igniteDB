import java.io.IOException;

public class IgniteDBApplication {
    public static void main(String[] args) {
        Server server = new Server(6379);
        try {
            server.start();
        } catch (IOException e) {
            System.err.println("Failed to start the server: " + e.getMessage());
        }
    }
}
