import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.Map;

public class ProductServer {

    ServerSocket serverSocket;

    ExecutorService threadpool = Executors.newFixedThreadPool(5);

    Map<String, SocketCLient> productRoom = Collections.synchronizedMap(new HashMap<>());

    public void start() throws IOException {
        serverSocket = new ServerSocket(50002);
        System.out.println("[서버] 시작");

        Thread thread = new Thread(() -> {
           try {
               while (true) {
                   Socket socket = serverSocket.accept();
                   SocketCLient sc = new SocketCLient(this, socket);
               }
           } catch (IOException e) {}
        });
        thread.start();
    }



    public class SocketCLient {

    }

}

