package View;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;

/**
 *
 * @author Almir
 */
public final class RoadBuilder {

    /* Draw a square L and  then rotate it... */
    public void draw_road(Graphics2D g, int x, int y, int width, int height, int rotate) {
        Rectangle first = new Rectangle(x, y, width, height);
        Rectangle second = new Rectangle(x, y, width, height - 10);

        //Now rotate both rectangles
        AffineTransform affine_first = new AffineTransform();
        affine_first.rotate(rotate * (Math.PI / 180), first.getX(), first.getY());

        Shape s_first = affine_first.createTransformedShape(first);

        AffineTransform affine_second = new AffineTransform();
        affine_second.rotate((90 + rotate) * (Math.PI / 180), second.getX(), second.getY());

        Shape s_second = affine_second.createTransformedShape(second);

        g.setColor(Color.BLACK);
        g.fill(s_first);
        g.fill(s_second);
    }

    public void draw_lines(Graphics2D g, int starting_x, int starting_y, int width, int height, int direction) {
        g.setColor(Color.gray);
        for (int i = 0; i < 4; i++) {
            if (direction == 0) {
                g.fillRect(starting_x - (40 * (i)), starting_y, width, height);
            } else {
                g.fillRect(starting_x, starting_y - (40 * (i)), width, height);
            }
        }
    }
}
