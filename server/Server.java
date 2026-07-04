public class Server {

    private final int port;
    private ServerSocketChannel serverChannel;
    private Selector selector;
    private boolean running;

    public Server(int port) {
        this.port = port;
        this.running = false;
    }

    public void start(){
        // TODO: Implement the server start logic
    }

    private void initialize(){

    }

    private void eventLoop(){
    }

    private void stop(){
    }
}