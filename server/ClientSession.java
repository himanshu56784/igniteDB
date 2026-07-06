import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class ClientSession {
    private final SocketChannel clientChannel;
    private final ByteBuffer buffer;

    public ClientSession(SocketChannel clientChannel) {
        this.clientChannel = clientChannel;
        this.buffer = ByteBuffer.allocate(1024);
    }

    public SocketChannel getClientChannel() {
        return clientChannel;
    }

    public ByteBuffer getBuffer() {
        return buffer;
    }

}
