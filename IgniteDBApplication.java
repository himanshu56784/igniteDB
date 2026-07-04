public class IgniteDBApplication {
    public static void main(String[] args) {
        Server server = new Server(6379);
        server.start();
    }
}
