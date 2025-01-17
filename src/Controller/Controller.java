package Controller;

import Model.Car.Enums.Path;
import Model.Car.Enums.Status;
import Model.Car.Util.HandleCarMovements;
import Model.Car.Positions.CrossingPositions;
import Model.Car.*;
import Model.Peer.*;
import Util.CrossingHandler;
import java.awt.Color;
import java.awt.Graphics2D;
import java.io.IOException;
import java.net.InetAddress;
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

public class Controller implements CrossingPositions {

    private PeerServer server;
    private final HashMap<String, Car> cars = new HashMap();
    private final HashMap<String, PeerClient> peers = new HashMap();
    private CrossingHandler crossing;

    private String server_address = "";

    public void create_server(int port) throws IOException {
        server = new PeerServer(port, server.getInetAddress());
        start_server();
        /*Create an instance for local car*/
        Path[] path = HandleCarMovements.get_path();
        int[] start = HandleCarMovements.get_start_position(path[0]);
        Car c = new Car(start[0], start[1], path[0], path[1], new Plate(server.get_address(), port), create_color());
        crossing = new CrossingHandler(c, server, peers);
        HandleCarMovements.set_car_direction(c, path[0]);
        c.setStatus(Status.MOVING);
        cars.put(server.get_address() + ":" + port, c);
        this.server_address = server.get_address() + ":" + port;
        System.out.println("Creating server cars:" + cars.entrySet());

        Thread t = new Thread() {
            public void run() {
                while (true) {
                    try {
                        send_local_car_position();
                    } catch (IOException ex) {
                        Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        };
        t.start();

        Thread move_car = new Thread() {
            public void run() {
                Car car = cars.get(server.get_address() + ":" + server.getLocalPort());

                while (true) {
                    switch (car.getFrom()) {
                        case UP:
                            HandleCarMovements.car_from_up(car);
                            break;
                        case DOWN:
                            HandleCarMovements.car_from_down(car);
                            break;
                        case LEFT:
                            HandleCarMovements.car_from_left(car);
                            break;
                        case RIGHT:
                            HandleCarMovements.car_from_right(car);
                            break;
                    }
                    if (car.getStatus() != Status.STOPPED) {
                        car.move();
                    }
                    if (HandleCarMovements.check_if_reached_stop_line(car)) {
                        if (crossing.status()) { // If is locked stop the car
                            car.setStatus(Status.STOPPED);
                        }
                    }
                    if (!peers.isEmpty()) {
                        crossing.ask_to_cross();
                    }
                    if (HandleCarMovements.check_if_reached_end(car)) {
                        JSONObject jo = new JSONObject();
                        jo.put("data_type", "crossed");
                        try {
                            notifyAllPeers(jo.toString());
                        } catch (IOException ex) {
                            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }

                    try {
                        Thread.sleep(150);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        };
        move_car.start();
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

    synchronized public Car connect_peer(String host, int port) throws IOException {
        PeerClient peer = new PeerClient(host, port);
        send_connection_information(peer);
        send_known_peers(peer);
        Car c = new Car(0, 0, new Plate(host, port), create_color());
        cars.put(host + ":" + port, c);
        peers.put(host + ":" + port, peer);

        return c;
    }

    private void send_connection_information(PeerClient p) throws IOException {
        JSONObject jo = new JSONObject();
        // Sending local ip and port to remote peer
        jo.put("data_type", "connection_data");
        jo.put("host", server.get_address());
        jo.put("port", server.getLocalPort());
        jo.put("car_x", ((Car) cars.get(((server.get_address() + ":" + server.getLocalPort())))).x);
        jo.put("car_y", ((Car) cars.get(((server.get_address() + ":" + server.getLocalPort())))).y);
        jo.put("from", "" + ((Car) cars.get(((server.get_address() + ":" + server.getLocalPort())))).getFrom());
        jo.put("to", "" + ((Car) cars.get(((server.get_address() + ":" + server.getLocalPort())))).getTo());

        p.send(jo.toJSONString());

        jo.clear();
    }

    private void notifyAllPeers(String msg) throws IOException {
        for (Map.Entry<String, PeerClient> entry : peers.entrySet()) {
            PeerClient p = entry.getValue();
            p.send(msg);
        }
    }

    private void send_local_car_position() throws IOException {
        JSONObject jo = new JSONObject();
        Car c = cars.get(server.get_address() + ":" + server.getLocalPort());
        jo.put("data_type", "car_position");
        jo.put("car_x", c.x);
        jo.put("car_y", c.y);
        jo.put("status", "" + c.status + "");

        notifyAllPeers(jo.toJSONString());
    }

    private void send_known_peers(PeerClient p) throws IOException {
        JSONObject jo = new JSONObject();
        JSONArray ja = new JSONArray();

        if (peers.size() > 0) {
            Map m = new LinkedHashMap();

            for (Map.Entry<String, PeerClient> entry : peers.entrySet()) {
                String key = entry.getKey();
                String split[] = key.split(":");
                String host = split[0];
                String port = split[1];
                m.put("host", host);
                m.put("port", Integer.parseInt(port));
            }
            ja.add(m);
        }
        jo.put("data_type", "more_peers");
        jo.put("peers", ja);
        p.send(jo.toJSONString());
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

    private Color create_color() {
        Random rand = new Random();
        return new Color(rand.nextFloat(), rand.nextFloat(), rand.nextFloat());
    }

    private void read_peer(PeerHandler hand) {
        while (true) {
            String data = null;
            try {
                data = hand.read();
                System.out.println(data);
                if (data != null) {
                    Object obj = null;
                    try {
                        obj = new JSONParser().parse(data);
                        JSONObject jo = (JSONObject) obj;
                        String type = (String) jo.get("data_type");
                        if (type.equals("connection_data")) {
                            String host = (String) jo.get("host");
                            int port = Integer.parseInt(jo.get("port") + "");
                            hand.set_host(host);
                            hand.set_port(port);
                            if (!peers.containsKey(host + ":" + port)) {
                                Car c = connect_peer(host, port);
                                c.x = Integer.parseInt(jo.get("car_x") + "");
                                c.y = Integer.parseInt(jo.get("car_y") + "");
                                String from = jo.get("from") + "";
                                String to = jo.get("to") + "";
                                switch (from) {
                                    case "UP":
                                        c.setFrom(Path.UP);
                                        break;
                                    case "DOWN":
                                        c.setFrom(Path.DOWN);
                                        break;
                                    case "LEFT":
                                        c.setFrom(Path.LEFT);
                                        break;
                                    case "RIGHT":
                                        c.setFrom(Path.RIGHT);
                                        break;
                                }
                                switch (to) {
                                    case "UP":
                                        c.setTo(Path.UP);
                                        break;
                                    case "DOWN":
                                        c.setTo(Path.DOWN);
                                        break;
                                    case "LEFT":
                                        c.setTo(Path.LEFT);
                                        break;
                                    case "RIGHT":
                                        c.setTo(Path.RIGHT);
                                        break;
                                }
                            }
                        } else if (type.equals("car_position")) {
                            int x = Integer.parseInt(jo.get("car_x") + "");
                            int y = Integer.parseInt(jo.get("car_y") + "");
                            String status = jo.get("status") + "";
                            if (hand.get_host() != null) {
                                Car c = cars.get(hand.get_host() + ":" + hand.get_port());
                                c.x = x;
                                c.y = y;
                                switch (status) {
                                    case "MOVING":
                                        c.setStatus(Status.MOVING);
                                        break;
                                    case "STOPPED":
                                        c.setStatus(Status.STOPPED);
                                        break;
                                }
                                Car car = cars.get(server.get_address() + ":" + server.getLocalPort());
//                                if (car.intersects(c)) {
//                                    switch (car.getFrom()) {
//                                        case UP:
//                                            car.y += 25;
//                                            break;
//                                        case DOWN:
//                                            car.y -= 25;
//                                            break;
//                                        case LEFT:
//                                            car.x += 25;
//                                            break;
//                                        case RIGHT:
//                                            car.x -= 25;
//                                            break;
//                                    }
//                                }
                            }
                        } else if (type.equals("more_peers")) {
                            JSONArray arr = (JSONArray) jo.get("peers");
                            Iterator it = arr.iterator();
                            while (it.hasNext()) {
                                JSONObject ob = (JSONObject) it.next();
                                String host = (String) ob.get("host");
                                int port = Integer.parseInt(ob.get("port") + "");
                                if (!server_address.equals(host + ":" + port) && !peers.containsKey(host + ":" + port)) {
                                    connect_peer(host, port);
                                }
                            }
                        } else if (type.equals("crossing_request")) {
                            String com = jo.get("command") + "";
                            switch (com) {
                                case "can_cross":
                                    crossing.crossing_request(hand.get_host() + ":" + hand.get_port());
                                    break;
                            }
                        } else if (type.equals("movement_command")) {
                            String command = (String) jo.get("command");
                            if (command.equals("stop")) {
                                cars.get(server_address).setStatus(Status.STOPPED);
                            }
                        } else if (type.equals("crossed")) {
                            crossing.crossed_complete(hand.get_host() + ":" + hand.get_port());
                            cars.get(server_address).setStatus(Status.MOVING);
                        }
                        else if(type.equals("solicitation_response")){
                            
                        }
                    } catch (ParseException ex) {
                        Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                peers.remove(hand.get_host() + ":" + hand.get_port());
                cars.remove(hand.get_host() + ":" + hand.get_port());
                int rmv = server.get_peer_index(hand.get_host() + ":" + hand.get_port());
                if (rmv != -1) {
                    server.remove(rmv);
                }
                break;
            }
        }
    }
}
