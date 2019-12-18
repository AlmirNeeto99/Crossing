package Model.Peer;

import java.util.ArrayList;

/**
 *
 * @author Almir
 */
public class PeerClientContainer {

    public ArrayList<PeerClient> clients;

    public PeerClientContainer() {
        this.clients = new ArrayList();
    }

    public void addPeer(PeerClient client) {
        this.clients.add(client);
    }

    public void notifyAll(String message) {
        for (PeerClient c : this.clients) {
            c.send(message);
        }
    }

    public PeerClient getClient(int index) {
        return this.clients.get(index);
    }
}
