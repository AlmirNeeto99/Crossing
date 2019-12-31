package Controller;

import Model.Car.Car;
import Model.Car.Plate;
import Model.Car.StartPositions;
import Model.Enums.Path;
import Model.Peer.PeerClient;
import Model.Peer.PeerHandler;
import Model.Peer.PeerServer;
import java.awt.Color;
import java.awt.Graphics2D;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Controller {

    private PeerServer server;
    private final HashMap<String, Car> cars = new HashMap();
    private final HashMap<String, PeerClient> peers = new HashMap();

    public void create_server(int port) throws IOException {
        server = new PeerServer(port);
        start_server();
        /*Create an instance for local car*/
        Path[] path = get_path();
        int[] start = get_start_position(path[0]);
        cars.put(server.get_address() + ":" + port, new Car(start[0], start[1], path[0], path[1], new Plate(server.get_address(), port), create_color()));
    }

    private void start_server() {
        Thread listen = new Thread() {
            @Override
            public void run() {
                Socket s;
                while (true) {
                    try {
                        s = server.accept();
                        PeerHandler hand = server.add_peer(s);
                        Thread read_peer_thread = new Thread() {
                            @Override
                            public void run() {
                                read_peer(hand);
                            }
                        };
                        read_peer_thread.start();
                    } catch (IOException ex) {
                        Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        };
        listen.start();
    }

    public void connect_peer(String host, int port) throws IOException {
        PeerClient peer = new PeerClient(host, port);
        peers.put(host + ":" + port, peer);
        System.out.println(peers.size());
        send_connect_information();
        send_local_car_position();
        send_known_peers();
    }

    private void peer_handshake(String host, int port) {
        PeerClient peer = null;
        try {
            peer = new PeerClient(host, port);
            peers.put(host + ":" + port, peer);
            System.out.println(peers.size());
            send_local_car_position();
            send_known_peers();
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    private void notifyAllPeers(String msg) {
        for (Map.Entry<String, PeerClient> entry : peers.entrySet()) {
            PeerClient p = entry.getValue();
            p.send(msg);
        }
    }

    private void send_connect_information() {
        JSONObject jo = new JSONObject();
        jo.put("data_type", "handshake");
        jo.put("host", server.get_address());
        jo.put("port", server.getLocalPort());
        notifyAllPeers(jo.toJSONString());
    }

    private void send_local_car_position() {
        JSONObject jo = new JSONObject();
        Car c = cars.get(server.get_address() + ":" + server.getLocalPort());
        jo.put("data_type", "car_position");
        jo.put("car_x", c.x);
        jo.put("car_y", c.y);
        notifyAllPeers(jo.toJSONString());
    }

    private void send_known_peers() {
        JSONObject jo = new JSONObject();
        JSONArray ja = new JSONArray();

        Map m = new LinkedHashMap(server.getPeers().length);

        for (PeerHandler p : server.getPeers()) {
            m.put("host", p.get_host());
            m.put("port", p.get_port());
        }
        ja.add(m);
        jo.put("peers", ja);
        notifyAllPeers(jo.toJSONString());
    }

    public boolean has_new_peer() {
        if (server != null) {
            return server.has_new_peer();
        }
        return false;
    }

    public void refresh_cars(Graphics2D g2d) {
        for (Map.Entry<String, Car> entry : cars.entrySet()) {
            Car c = entry.getValue();
            g2d.setColor(c.getColor());
            g2d.fillRect(c.x, c.y, c.height, c.width);
        }
    }

    private Path[] get_path() {
        Path path[] = new Path[2];
        Random rand = new Random();
        int start = rand.nextInt(4);
        switch (start) {
            case 0:
                path[0] = Path.UP;
                break;
            case 1:
                path[0] = Path.RIGHT;
                break;
            case 2:
                path[0] = Path.DOWN;
                break;
            case 3:
                path[0] = Path.LEFT;
                break;
        }
        int stop = rand.nextInt(4);
        while (start == stop) {
            stop = rand.nextInt(4);
        }
        switch (stop) {
            case 0:
                path[1] = Path.UP;
                break;
            case 1:
                path[1] = Path.RIGHT;
                break;
            case 2:
                path[1] = Path.DOWN;
                break;
            case 3:
                path[1] = Path.LEFT;
                break;
        }
        return path;
    }

    private int[] get_start_position(Path where) {
        StartPositions start = new StartPositions();
        if (where == Path.UP) {
            return start.get_up();
        } else if (where == Path.DOWN) {
            return start.get_down();
        } else if (where == Path.RIGHT) {
            return start.get_right();
        }
        return start.get_left();
    }

    private Color create_color() {
        Random rand = new Random();
        return new Color(rand.nextFloat(), rand.nextFloat(), rand.nextFloat());
    }

    private void read_peer(PeerHandler hand) {
        while (true) {
            String data = hand.read();
            if (data != null) {
                Object obj = null;
                try {
                    obj = new JSONParser().parse(data);
                    JSONObject jo = (JSONObject) obj;
                    String type = (String) jo.get("data_type");
                    System.out.println(jo.toJSONString());
                    if (type.equals("car_position")) {
                        String x = jo.get("car_x") + "";
                        String y = jo.get("car_y") + "";
                        int x_int = Integer.parseInt(x);
                        int y_int = Integer.parseInt(y);
                        cars.put(hand.get_host() + ":" + hand.get_port(), new Car(x_int, y_int, new Plate(hand.get_host(), hand.get_port()), create_color()));
                    } else if (type.equals("more_peers")) {
                        JSONArray ja = (JSONArray) jo.get("peers");

                        Iterator itr = ja.iterator();

                        while (itr.hasNext()) {
                            System.out.println(itr);
                        }
                    } else if (type.equals("handshake")) {
                        String host = jo.get("host") + "";
                        String y = jo.get("port") + "";
                        int port = Integer.parseInt(y);
                        peer_handshake(host, port);
                    }
                } catch (ParseException ex) {
                    Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
