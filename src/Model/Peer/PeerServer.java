package Model.Peer;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * PeerServer is an object that hold all clients that are connected to it.
 *
 * @author Almir
 */
public class PeerServer extends ServerSocket {

    private ArrayList<PeerHandler> peers;

    private boolean new_peer = false;

    public PeerServer(int port) throws IOException {
        super(port, 3, InetAddress.getLocalHost());
        this.peers = new ArrayList();
    }

    public PeerServer(int size, int port) throws IOException {
        super(port, size, InetAddress.getLocalHost());
        this.peers = new ArrayList(size);
    }

    public PeerHandler add_peer(Socket s) throws IOException {
        new_peer = true;
        PeerHandler hand = new PeerHandler(s);
        this.peers.add(hand);
        return hand;
    }

    public void kill_peers() {
        this.peers.clear();
        System.gc();
    }

    public ArrayList<PeerHandler> getPeers() {
        return this.peers;
    }

    public boolean has_new_peer() {
        if (new_peer) {
            new_peer = false;
            return true;
        }
        return false;
    }

    public String get_address() {
        return this.getInetAddress().getHostAddress();
    }

    public int get_peer_index(String address) {
        int idx = 0;
        for (PeerHandler p : peers) {
            if (address.equals(p.get_host() + ":" + p.get_port())) {
                return idx;
            }
            idx++;
        }
        return -1;
    }

    public void remove(int idx) {
        this.peers.remove(idx);
    }
}
