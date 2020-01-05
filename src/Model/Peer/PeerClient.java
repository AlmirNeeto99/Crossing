package Model.Peer;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class PeerClient {

    private final Socket client;
    private final ObjectOutputStream out;

    public PeerClient(String host, int port) throws IOException {
        this.client = new Socket(host, port);
        out = new ObjectOutputStream(this.client.getOutputStream());
    }

    public void send(String message) throws IOException {
        out.writeObject(message);
        out.flush();
    }
}
