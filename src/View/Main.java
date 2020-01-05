package View;

import View.GUI.Crossing;
import View.GUI.HomePage;
import Controller.Controller;
import java.awt.CardLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author Almir
 */
public class Main extends JFrame implements ActionListener {

    public static Controller controller;

    private static Crossing crossing;
    private static HomePage home;

    public static Main m;

    private JPanel cards;

    private final int HEIGHT = 600, WIDTH = 600;
    //private final int street_length = 80;

    public Main() {
        controller = new Controller();
        crossing = new Crossing();
        home = new HomePage();
        cards = new JPanel(new CardLayout());
        Timer timer = new Timer(20, this);

        cards.add(home, "Home");
        cards.add(crossing, "Cross");

        add(cards);

        setSize(HEIGHT, WIDTH);
        setTitle("Crossing");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);

        timer.start();

        KeyListener key = new KeyListener() {
            private Set<Integer> keys = new HashSet();

            @Override
            public void keyTyped(KeyEvent ke) {
                //System.out.println(ke);
            }

            @Override
            public void keyPressed(KeyEvent ke) {
                if (ke.getKeyCode() == 17) {
                    keys.add(ke.getKeyCode());
                }
                if (keys.size() > 0) {
                    if (ke.getKeyCode() == 72) {
                        crossing.connect();
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent ke) {
                keys.remove(ke.getKeyCode());
            }
        };
        addKeyListener(key);

    }

    public static void main(String[] args) {
        m = new Main();
        Thread check_for_new_connection = new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        if (controller.has_new_peer()) {
                            crossing.has_new_peer();
                        }
                        Thread.sleep(250);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        };
        check_for_new_connection.start();
    }

    public void repaint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        //Road start
        RoadBuilder.draw_road(g2d, 210, 381, 10, 150, 0);
        RoadBuilder.draw_road(g2d, 220, 210, 10, 150, 90);
        RoadBuilder.draw_road(g2d, 391, 220, 10, 150, 180);
        RoadBuilder.draw_road(g2d, 381, 391, 10, 150, 270);
        //Road end

        // Lines start
        RoadBuilder.draw_lines(g2d, 200, HEIGHT / 2 - 5, 20, 10, 0);
        RoadBuilder.draw_lines(g2d, 540, HEIGHT / 2 - 5, 20, 10, 0);
        RoadBuilder.draw_lines(g2d, WIDTH / 2 - 5, 200, 10, 20, 1);
        RoadBuilder.draw_lines(g2d, WIDTH / 2 - 5, 540, 10, 20, 1);
        // Lines end

//        g2d.setColor(Color.yellow);
//        g2d.drawRect(250, 250, 20, 20); //second quad 
//        g2d.setColor(Color.red);
//        g2d.drawRect(330, 330, 20, 20); //forth quad
//        g2d.setColor(Color.black); 
//        g2d.drawRect(250, 330, 20, 20);//third quad
//        g2d.setColor(Color.blue);
//        g2d.drawRect(330, 250, 20, 20); //first quad
        controller.refresh_cars(g2d);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        crossing.repaint();
    }

    public void change(String card) {
        CardLayout cardLayout = (CardLayout) cards.getLayout();
        cardLayout.show(cards, card);
    }
}
