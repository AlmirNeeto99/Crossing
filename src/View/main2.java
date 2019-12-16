package View;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.Timer;

/**
 *
 * @author Almir
 */
public class main2 implements ActionListener {

    public Crossing crossing;
    public Rectangle car_1, car_2, car_3, car_4;

    public static main2 m;

    public main2() {
        crossing = new Crossing();
        JFrame frame = new JFrame();
        Timer timer = new Timer(20, this);

        frame.add(crossing);
        frame.setSize(600, 600);
        frame.setTitle("Crossing");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);

        car_1 = new Rectangle(480, 210, 20, 20); //Right
        car_2 = new Rectangle(60, 300, 20, 20); // Left
        car_3 = new Rectangle(230, 60, 20, 20); // Up
        car_4 = new Rectangle(320, 480, 20, 20); // Down

        Thread t = new Thread() {
            public void run() {
                while (true) {
                    car_1.x -= 10;
                    if (car_1.x >= 600) {
                        car_1.x = 480;
                    }
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(main2.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        };
        t.start();

        Random r = new Random();

        timer.start();
    }

    public static void main(String[] args) {
        m = new main2();
    }

    public void repaint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        // Cars start
        g2d.setColor(Color.MAGENTA);
        g2d.fillRect(car_1.x, car_1.y, car_1.width, car_1.height);
        g2d.setColor(Color.CYAN);
        g2d.fillRect(car_2.x, car_2.y, car_2.width, car_2.height);
        g2d.setColor(Color.PINK);
        g2d.fillRect(car_3.x, car_3.y, car_3.width, car_3.height);
        g2d.setColor(Color.ORANGE);
        g2d.fillRect(car_4.x, car_4.y, car_4.width, car_4.height);
        // Cars end

        //Road start
        new RoadBuilder().draw_road(g2d, 195, 350, 10, 150, 0);
        new RoadBuilder().draw_road(g2d, 205, 175, 10, 150, 90);
        new RoadBuilder().draw_road(g2d, 370, 185, 10, 150, 180);
        new RoadBuilder().draw_road(g2d, 360, 360, 10, 150, 270);
        //Road end

        // Lines start
        new RoadBuilder().draw_lines(g2d, 184, 260, 20, 10, 0);
        new RoadBuilder().draw_lines(g2d, 480, 260, 20, 10, 0);
        new RoadBuilder().draw_lines(g2d, 278, 165, 10, 20, 1);
        new RoadBuilder().draw_lines(g2d, 278, 475, 10, 20, 1);
        // Lines end
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        crossing.repaint();
    }

}
