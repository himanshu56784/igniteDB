
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class Server {

    private final int port;
    private ServerSocketChannel serverChannel;
    private Selector selector;
    private boolean running;

    public Server(int port) {
        this.port = port;
        this.running = false;
    }

    public void start() throws IOException {
        initialize();
        running = true;
        eventLoop();
    }

    private void initialize() throws IOException {
        // Open a selector for handling multiple channels
        selector = Selector.open();
        // Create a TCP server socket
        this.serverChannel = ServerSocketChannel.open();
        // Make it non-blocking
        serverChannel.configureBlocking(false);
        // Tell the OS: "I want to listen on port: 6379"
        serverChannel.bind(new InetSocketAddress(port));

        serverChannel.register(selector, SelectionKey.OP_ACCEPT);

    }

    private void handleAccept(SelectionKey key) throws IOException {
        // Accept the new client connection
        ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();

        SocketChannel clientChannel = serverChannel.accept();

        clientChannel.configureBlocking(false);
        
        // Create a new ClientSession for the accepted client
        ClientSession clientSession = new ClientSession(clientChannel);
        
        // Register the new client channel with the selector for reading
        clientChannel.register(selector, SelectionKey.OP_READ, clientSession);
    }

    private void handleRead(SelectionKey key) throws IOException {
        // Read data from the client channel
        ClientSession clientSession = (ClientSession) key.attachment();
        SocketChannel clientChannel = clientSession.getClientChannel();
        // Create a buffer to read data into
        ByteBuffer buffer = clientSession.getBuffer();
        // Read data from the client channel into the buffer
        int bytesRead = clientChannel.read(buffer);
        if (bytesRead == -1) {
            // Client has closed the connection
            clientChannel.close();
            key.cancel();
            return;
        } else if (bytesRead > 0) {
            // Process the data read from the client
            buffer.flip();
            
            byte[] data = new byte[buffer.remaining()];
            buffer.get(data);
            String receivedMessage = new String(data);
            System.out.println("Received from client: " + receivedMessage);
        } else if (bytesRead == 0) {
            // No data read, do nothing
            System.out.println("No data read from client");
        }
    }

    void processClientRequest() throws IOException {
        // Placeholder for processing client requests
        // This method would contain the logic to read from and write to client channels
        Set<SelectionKey> selectedKeys = selector.selectedKeys();

        Iterator<SelectionKey> iterator = selectedKeys.iterator();
        while (iterator.hasNext()) {
            SelectionKey key = iterator.next();
            if (key.isAcceptable()) {
                // Handle new client connection
                handleAccept(key);

            } else if (key.isReadable()) {
                // Handle reading from a client
                handleRead(key);

            } else if (key.isWritable()) {
                // Handle writing to a client
                // Write data to the client channel
            }
            iterator.remove();
        }

    }

    private void eventLoop() {
        while (running) {
            try {
                // Wait for events on the registered channels
                selector.select();
                // Process the events
                processClientRequest();
                // Handle the events (accept, read, write)
                // This is where you would implement your logic for handling client connections
                // and requests
            } catch (IOException e) {
                System.err.println("Error in event loop: " + e.getMessage());
            }
        }
    }

    private void stop() {
        running = false;
    }
}