package View;

import java.awt.Graphics;
import javax.swing.JPanel;

/**
 *
 * @author Almir
 */
public class Crossing extends JPanel{

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        Main.m.repaint(g);
    }        
}
