package Model.Peer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/*PeerHandler handles the incoming connection from another device.
 (If a new Peer connects to PeerServer this object is created.)

 OBS.: Incoming messages are received here.
 */
public class PeerHandler {

    private final Socket client;

    public PeerHandler(Socket client) {
        this.client = client;
    }

    public String read() {
        ObjectInputStream in = null;
        try {
            String message;
            in = new ObjectInputStream(this.client.getInputStream());
            if ((message = (String) in.readObject()) != null) {
                return message;
            }
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(PeerHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public String toString() {
        return this.client.getInetAddress().getHostAddress() + ":" + this.client.getPort();
    }

    public String get_host() {
        return this.client.getInetAddress().getHostAddress();
    }

    public int get_port() {
        return this.client.getPort();
    }
}
