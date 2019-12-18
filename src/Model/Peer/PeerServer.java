package Model.Peer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * PeerServer is an object that hold all clients that are connected to it.
 *
 * @author Almir
 */
public class PeerServer {

    private final ServerSocket server;
    private Thread listening; //Main thread of this class, responsable of wait for new connections.
    public Thread[] peers;
    private int size = 0; //Actual amount of peers connected to this 'host'

    public PeerServer(int port) throws IOException {
        this.server = new ServerSocket(port);
        this.peers = new Thread[3];
    }

    public PeerServer(int size, int port) throws IOException {
        this.server = new ServerSocket(port);
        this.peers = new Thread[size];
    }

    /*Start listening for incoming connections...*/
    public PeerServer start_listen() {
        listening = new Thread() {
            public void run() {
                while (true) {
                    try {
                        Socket s = server.accept();
                        System.out.println("A client's connected...");
                        PeerHandler hand = new PeerHandler(s);
                        Thread peer = new Thread(hand);
                        peers[size++] = peer;
                        peers[size - 1].start();
                    } catch (IOException ex) {
                        System.out.println("Trouble when accepting Socket into server...");
                        Logger.getLogger(PeerServer.class.getName()).log(Level.SEVERE, null, ex);
                        System.exit(0);
                    }
                }
            }
        };
        System.out.println("Server is listening to port: " + this.server.getLocalPort());
        listening.start();
        return this;
    }

    public void stop_listening() {
        listening.interrupt();
    }

    public void kill_peers() {
        for (int i = 0; i < peers.length; i++) {
            peers[i].interrupt();
            peers[i] = null;
        }
        this.size = 0;
        System.gc();
    }

    public Thread[] getPeers() {
        return this.peers;
    }
}
