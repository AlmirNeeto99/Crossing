package Model;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Almir
 */
public class PeerServer {

    private final ServerSocket server;
    private Thread listening;
    public Thread[] peers = new Thread[3];
    private int size = 0;

    public PeerServer(int port) throws IOException {
        this.server = new ServerSocket(port);
    }

    public PeerServer start_listen() {
        listening = new Thread() {
            public void run() {
                while (true) {
                    try {
                        Socket s = server.accept();
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
        listening.start();
        return this;
    }

    public void stop_listening() {
        listening.interrupt();
    }
    
    public void kill_peers(){
        for(Thread t : peers){
            t.interrupt();
        }
    }

    public Thread[] getPeers() {
        return this.peers;
    }
}
