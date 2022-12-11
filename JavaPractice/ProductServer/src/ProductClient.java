import org.json.JSONObject;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ProductClient {
    Socket socket;
    DataInputStream dis;
    DataOutputStream dos;

    public static void main(String[] args) {
        try {
            ProductClient productClient = new ProductClient();
            productClient.connect();


        } catch (IOException e) {}
    }


    private void unconnect() throws IOException {
        socket.close();
    }

    private void receive() {
        Thread thread = new Thread(() -> {
            try {
                while (true) {
                    String json = dis.readUTF();
                    JSONObject root = new JSONObject(json);

                }
            } catch(IOException e){}
        });
        thread.start();
    }
    private void send(String json) throws IOException {
        dos.writeUTF(json);
        dos.flush();
    }


    private void connect() throws IOException {
        socket = new Socket("localhost", 50002);
        dis = new DataInputStream(socket.getInputStream());
        dos = new DataOutputStream((socket.getOutputStream()));
        System.out.println("[클라이언트] 서버에 연결 됨");
    }
}
