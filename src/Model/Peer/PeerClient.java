package Model.Peer;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PeerClient {

    private Socket client;

    public PeerClient(String host,int port) throws IOException {
        this.client = new Socket(host, port);
    }

    public void send(String message) {
        ObjectOutputStream out;
        try {
            out = new ObjectOutputStream(this.client.getOutputStream());
            out.writeObject(message);
            out.flush();
        } catch (IOException ex) {
            Logger.getLogger(PeerClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
