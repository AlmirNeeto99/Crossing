package View;

import Model.Car.Car;
import Model.Car.Start_Positions;
import Model.Limits.End_Limits;
import Model.Limits.Start_Limits;
import Model.Limits.Stop_Limits;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.Timer;

/**
 *
 * @author Almir
 */
public class main2 implements ActionListener {

    public Crossing crossing;
    public Car car_1, car_2, car_3, car_4;

    public static main2 m;

    public final int HEIGHT = 600, WIDTH = 600;
    public final int street_length = 80;

    public main2() {
        crossing = new Crossing();
        JFrame frame = new JFrame();
        Timer timer = new Timer(20, this);

        frame.add(crossing);
        frame.setSize(HEIGHT, WIDTH);
        frame.setTitle("Crossing");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);

        int[] pos = new Start_Positions().get_right();
        car_1 = new Car(pos[0], pos[1]); //Right
        pos = new Start_Positions().get_left();
        car_2 = new Car(pos[0], pos[1]); // Left
        pos = new Start_Positions().get_up();
        car_3 = new Car(pos[0], pos[1]); // Up
        pos = new Start_Positions().get_down();
        car_4 = new Car(pos[0], pos[1]); // Down

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
        // Cars start

        //Road start
        new RoadBuilder().draw_road(g2d, 210, 381, 10, 150, 0);
        new RoadBuilder().draw_road(g2d, 220, 210, 10, 150, 90);
        new RoadBuilder().draw_road(g2d, 391, 220, 10, 150, 180);
        new RoadBuilder().draw_road(g2d, 381, 391, 10, 150, 270);
        //Road end

        // Lines start
        new RoadBuilder().draw_lines(g2d, 200, HEIGHT / 2 - 5, 20, 10, 0);
        new RoadBuilder().draw_lines(g2d, 540, HEIGHT / 2 - 5, 20, 10, 0);
        new RoadBuilder().draw_lines(g2d, WIDTH / 2 - 5, 200, 10, 20, 1);
        new RoadBuilder().draw_lines(g2d, WIDTH / 2 - 5, 540, 10, 20, 1);
        // Lines end

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        crossing.repaint();
    }

}
