import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpServer {
    private final int port;
    private ServerSocket serverSocket;
    private ExecutorService executor;
    private boolean isRunning;

    public HttpServer(int port) {
        this.port = port;
        this.executor = Executors.newFixedThreadPool(10);
    }
    public void start() throws IOException {
        serverSocket = new ServerSocket(port);
        isRunning = true;

        System.out.println("Servidor iniciado na Porta " + port);
        while (isRunning) {
            Socket clientSocket = serverSocket.accept();
            executor.execute(new Routes(clientSocket));
        }
    }

    public void stop() throws IOException {
        isRunning = false;
        executor.shutdown();
        serverSocket.close();
        System.out.println("Servidor encerrado na Porta " + port);
    }
}
