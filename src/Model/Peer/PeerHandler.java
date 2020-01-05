package Model.Peer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

/*PeerHandler handles the incoming connection from another device.
 (If a new Peer connects to PeerServer this object is created.)

 OBS.: Incoming messages are received here.
 */
public class PeerHandler {

    private final Socket client;
    private String remote_host;
    private int remote_port;
    private final ObjectInputStream in;

    public PeerHandler(Socket client) throws IOException {
        this.client = client;
        in = new ObjectInputStream(this.client.getInputStream());
    }

    public String read() throws IOException, ClassNotFoundException {
        String message;
        if ((message = (String) in.readObject()) != null) {
            return message;
        }
        return null;
    }

    @Override
    public String toString() {
        return this.remote_host + ":" + this.remote_port;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof PeerHandler) {
            PeerHandler p = (PeerHandler) o;
            if (p.toString().equals(this.toString())) {
                return true;
            }
        }
        return false;
    }

    public void set_host(String host) {
        this.remote_host = host;
    }

    public void set_port(int port) {
        this.remote_port = port;
    }

    public String get_host() {
        return this.remote_host;
    }

    public int get_port() {
        return this.remote_port;
    }
}
