package Model.Peer;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * PeerServer is an object that hold all clients that are connected to it.
 *
 * @author Almir
 */
public class PeerServer extends ServerSocket {

    private PeerHandler[] peers;
    private int size = 0; //Actual amount of peers connected to this 'host'

    private boolean new_peer = false;

    public PeerServer(int port) throws IOException {
        super(port, 3, InetAddress.getLocalHost());
        this.peers = new PeerHandler[3];
    }

    public PeerServer(int size, int port) throws IOException {
        super(port, size, InetAddress.getLocalHost());
        this.peers = new PeerHandler[size];
    }

    public PeerHandler add_peer(Socket s) {
        new_peer = true;
        PeerHandler hand = new PeerHandler(s);
        peers[size++] = hand;
        return hand;
    }

    public void kill_peers() {
        for (int i = 0; i < peers.length; i++) {
            peers[i] = null;
        }
        this.size = 0;
        System.gc();
    }

    public PeerHandler[] getPeers() {
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
}
