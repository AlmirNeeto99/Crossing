package View;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class HomePage extends JPanel {

    public HomePage() {
        this.setSize(600, 600);

        GridBagLayout grid = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        this.setLayout(grid);

        JLabel lab = new JLabel("<html><font color='#4287f5' size='10'>Crossing</font></html>");
        lab.setFont(Font.getFont("Comic Sans MS"));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        this.add(lab, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(50, 0, 0, 0);  //top padding
        c.gridx = 0;
        c.gridy = 1;

        JButton start = new JButton("Start");
        start.addActionListener((ActionEvent ae) -> {
            String val = (String) JOptionPane.showInputDialog("Type a port to listen to: ");
            if (val != null) {
                try {
                    int port = Integer.parseInt(val);
                    Main.m.change("Cross");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this.getParent(), "Please, type a number!", "Erro", JOptionPane.ERROR_MESSAGE);
                }

            }

            // call main function to start server
        });

        this.add(start, c);
    }
}
