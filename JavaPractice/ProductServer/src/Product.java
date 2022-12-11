import org.json.JSONObject;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Product {
    ProductServer productServer;
    Socket socket;
    DataInputStream dis;
    DataOutputStream dos;
    int no;
    String name;
    int price;
    int stock;

    public void product(ProductServer productServer, Socket socket) {
        try {
            this.productServer = productServer;
            this.socket = socket;
            this.dis = new DataInputStream(socket.getInputStream());
            this.dos = new DataOutputStream(socket.getOutputStream());
            receive();
        } catch (IOException e) {}
    }

    public void receive() {
        productServer.threadPool.execute(() ->{
            try {
                while (true) {
                    String receiveJson = dis.readUTF();

                    JSONObject jsonObject = new JSONObject(receiveJson);
                }
            } catch (IOException e) {

            }
        });
    }

    public void jsonInsert () {

    }
}
