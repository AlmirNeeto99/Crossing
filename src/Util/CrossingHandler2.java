package Util;

import Model.Car.Car;
import Model.Peer.PeerClient;
import Model.Peer.PeerServer;
import java.util.ArrayList;
import java.util.HashMap;

public class CrossingHandler2 {

    private Car car;
    private PeerServer server;
    private HashMap<String, PeerClient> peers;

    private int my_number = -1;

    private static boolean must_stop;

    private int pos = 0;
    private ArrayList<CrossingEntity> prio = new ArrayList();

    public CrossingHandler2(Car local_car, PeerServer server, HashMap peers) {
        this.car = local_car;
        this.server = server;
        this.peers = peers;
    }

    public boolean stop() {
        return CrossingHandler2.must_stop;
    }

    public void crossed_complete(String hand) {
        CrossingEntity first = prio.get(0);
        if (first.hand.equals(hand)) {
            prio.remove(0);
        }
        if (prio.get(0).hand.equals(server.get_address() + ":" + server.getLocalPort())) {
            must_stop = false;
        } else {
            must_stop = true;
        }
    }

    public void number_received(String hand, int priority) {
        CrossingEntity c = new CrossingEntity(hand, priority);
        if (my_number == -1) {
            must_stop = true;
            return;
        }
        int size = prio.size();
        for (int i = 0; i < size; i++) {
            CrossingEntity e = prio.get(i);
            if (c.num <= e.num) {
                prio.add(i, c);
            } else {
                prio.add(c);
            }
        }
        if (prio.get(0).hand.equals(server.get_address() + ":" + server.getLocalPort())) {
            must_stop = false;
        } else {
            must_stop = true;
        }
    }

    public void setMyNumber(int priority) {
        prio.add(new CrossingEntity(server.get_address() + ":" + server.getLocalPort(), priority));
        this.my_number = priority;
    }

    private class CrossingEntity {

        private String hand;
        private int num;

        public CrossingEntity(String h, int n) {
            this.hand = h;
            this.num = n;
        }
    }
}
