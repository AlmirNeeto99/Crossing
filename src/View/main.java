package View;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Almir
 */
public class main {

    public static void main(String[] args) throws IOException {

        int listen_port = Integer.parseInt(args[0]);

        ServerSocket server = new ServerSocket(listen_port);
        System.out.println("Server is listening...");
        Scanner scan = new Scanner(System.in);
        int intrada = scan.nextInt();

        Thread con = new Thread() {
            public void run() {
                while (true) {
                    Socket s;
                    try {
                        s = server.accept();
                        System.out.println("Client connected " + s);
                        Thread a = new Thread() {
                            public void run() {
                                while (true) {
                                    try {
                                        ObjectInputStream in = new ObjectInputStream(s.getInputStream());
                                        String message;
                                        if ((message = (String) in.readObject()) != null) {
                                            //System.out.println("Message received from" + s);
                                            System.out.println(message);
                                        }
                                        Thread.sleep(1500);
                                    } catch (IOException | ClassNotFoundException | InterruptedException ex) {
                                        Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                            }
                        };
                        a.start();
                    } catch (IOException ex) {
                        Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        };
        con.start();

        intrada = scan.nextInt();
        List<Socket> cons = new ArrayList();

        for (int i = 1; i < 4; i++) {
            Socket sock = new Socket("localhost", Integer.parseInt(args[i]));
            cons.add(sock);
        }
        System.out.println("All clients connected...");
        while (true) {
            String message = scan.nextLine();
            for (Socket s : cons) {
                ObjectOutputStream out;
                try {
                    out = new ObjectOutputStream(s.getOutputStream());
                    out.writeObject(message);
                    out.flush();
                } catch (IOException ex) {
                    Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
