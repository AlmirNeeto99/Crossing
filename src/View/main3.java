//package View;
//
//import Model.Peer.PeerClient;
////import Model.Peer.PeerClientContainer;
//import Model.Peer.PeerServer;
//import java.io.IOException;
//import java.util.Scanner;
//
///**
// *
// * @author Almir
// */
//public class main3 {
//
//    public static void main(String[] args) throws IOException {
//        int port = Integer.parseInt(args[0]);
//
//        PeerServer ps = new PeerServer(port); // Used to read messages from others PC...
//        //ps.start_listen();
//
//        Scanner wait = new Scanner(System.in);
//        System.out.println("Press any key to continue...");
//        String w = wait.nextLine();
//        //PeerClientContainer hosts = new PeerClientContainer(); //Hosts are used to send messages to other PC...
//        for (int i = 1; i < 4; i++) {
//            hosts.addPeer(new PeerClient("",Integer.parseInt(args[i])));
//        }
//        while (true) {
//            String msg = wait.nextLine();
//            hosts.notifyAll(msg);
//        }
//    }
//
//}
