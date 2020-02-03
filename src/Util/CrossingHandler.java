package Util;

import Model.Car.Car;
import Model.Peer.PeerClient;
import Model.Peer.PeerServer;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONObject;

public class CrossingHandler {

    private Car car;
    private boolean locked = false, asked = false;
    private PeerServer server;
    private HashMap<String, PeerClient> peers;

    private int messages_sent = 0;

    public CrossingHandler(Car local_car, PeerServer server, HashMap peers) {
        this.car = local_car;
        this.server = server;
        this.peers = peers;
    }

    public void crossing_request(String peer) {
        if (locked) {
            try {
                JSONObject jo = new JSONObject();
                // Sending local ip and port to remote peer

                // Advice crossing requerer that someone is already crossing...
                jo.put("data_type", "movement_command");
                jo.put("command", "stop");
                peers.get(peer).send(jo.toString());
            } catch (IOException ex) {
                Logger.getLogger(CrossingHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            if (!asked) {
                locked = true;
                JSONObject jo = new JSONObject();
                // Sending local ip and port to remote peer

                // Advice crossing requerer that it can cross...
                jo.put("data_type", "solicitation_response");
                jo.put("command", "free_to_go");
                try {
                    peers.get(peer).send(jo.toString());
                } catch (IOException ex) {
                    Logger.getLogger(CrossingHandler.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public void crossed_complete(String peer) {
        locked = false;
    }
    
    public void can_cross(){
        messages_sent--;
    }

    public void ask_to_cross() {
        if (!asked) {
            System.out.println("Asked to cross");
            JSONObject jo = new JSONObject();

            // Advice crossing requerer that someone is already crossing...
            jo.put("data_type", "crossing_request");
            jo.put("command", "can_cross");

            for (PeerClient c : peers.values()) {
                messages_sent++;
                try {
                    c.send(jo.toString());
                } catch (IOException ex) {
                    Logger.getLogger(CrossingHandler.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            asked = true;
        }
    }

    public boolean status() {
        return locked;
    }
}
