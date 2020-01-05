package View.GUI;

import View.Main;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class HomePage extends JPanel {

    public HomePage() {
        this.setSize(600, 600);

        GridBagLayout grid = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        setLayout(grid);

        JLabel lab = new JLabel("<html><font color='#4287f5' size='10'>Crossing</font></html>");
        lab.setFont(Font.getFont("Comic Sans MS"));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        add(lab, c);

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
                    JOptionPane.showMessageDialog(this.getParent(), "Press \"Crtl+h\" at any time to connect to a new peer.", "Info", JOptionPane.INFORMATION_MESSAGE);
                    try {
                        Main.controller.create_server(port);
                    } catch (IOException e) {
                        System.out.println(e);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this.getParent(), "Please, type a number!", "Error", JOptionPane.ERROR_MESSAGE);
                }

            }
            // call main function to start server
        });
        add(start, c);
    }
}
