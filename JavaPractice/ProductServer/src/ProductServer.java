import org.json.JSONObject;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.Map;
import java.io.InputStream;
import java.io.OutputStream;

public class ProductServer {

    ServerSocket serverSocket;

    ExecutorService threadPool = Executors.newFixedThreadPool(5);

    Map<String, Product> productRoom = Collections.synchronizedMap(new HashMap<>());

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

    public void stop() {
        try {
            serverSocket.close();
            threadPool.shutdownNow();
            productRoom.values().stream().close();
        } catch (IOException e) {}
    }



    public class SocketCLient {
        ProductServer productServer;
        Socket socket;
        DataInputStream dis;
        DataOutputStream dos;

        public SocketCLient(ProductServer productServer, Socket socket) {
            try {
                this.productServer = productServer;
                this.socket = socket;
               // this.dis = new DataInputStream();
              //  this.dos = new DataOutputStream();
                InetSocketAddress isa = (InetSocketAddress) socket.getRemoteSocketAddress();
                recieve();
            } catch (Exception e) {}
        }

        public void recieve() {
            productServer.threadPool.execute(() -> {
                try {
                    while(true) {
                        String recieveJson = dis.readUTF();

                        JSONObject jsonObject = new JSONObject(recieveJson);
                    }
                } catch (IOException e) {}
            });
        }
        public void close() {
            try {
                socket.close();
            } catch (IOException e) {}
        }

    }

    public static void main(String[] args) {
        try {
            ProductServer productServer = new ProductServer();
            productServer.start();

            System.out.println("-----------------------------------------------");
            System.out.println("no  | name           | price        | stock    ");
            System.out.println("-----------------------------------------------");




            System.out.println("-----------------------------------------------");
            System.out.println("메뉴 1: Create | 2: Update | 3: Delete | 4: Exit");

            Scanner scanner = new Scanner(System.in);
            while (true) {
                String key = scanner.nextLine();
                if (key.equals("4")) break;
            }
            scanner.close();
            productServer.stop();
        } catch (IOException e) {
            System.out.println("[product server closed]");
        }
    }




}

