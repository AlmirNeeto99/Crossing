package Model.Peer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Almir
 */
public class PeerHandler implements Runnable {

    private final Socket client;
    public ArrayList<String> data_queue = new ArrayList();

    public PeerHandler(Socket client) {
        this.client = client;
    }

    @Override
    public void run() {
        while (true) {
            ObjectInputStream in = null;
            try {
                String message;
                in = new ObjectInputStream(this.client.getInputStream());
                if ((message = (String) in.readObject()) != null) {
                    this.data_queue.add(message);
                }
            } catch (IOException | ClassNotFoundException ex) {
                Logger.getLogger(PeerHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public String getLastData() {
        return this.data_queue.remove(0);
    }
}
