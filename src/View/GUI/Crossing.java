package View.GUI;

import View.Main;
import java.awt.Graphics;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Crossing extends JPanel {

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Main.m.repaint(g);
    }

    public void connect() {
        JTextField host = new JTextField();
        JTextField port = new JTextField();
        Object[] message = {
            "Hostname:", host,
            "Port:", port
        };
        int option = JOptionPane.showConfirmDialog(null, message, "Connect", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            try {
                int port_number = Integer.parseInt(port.getText());
                try {
                    Main.controller.connect_peer(host.getText(), port_number);
                    JOptionPane.showMessageDialog(this.getParent(), "A new peer is connected!", "Info", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this.getParent(), "Sorry, unable to connect!", "Error", JOptionPane.ERROR_MESSAGE);
                }

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this.getParent(), "Please, type a number as port!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void has_new_peer() {
        JOptionPane.showMessageDialog(this.getParent(), "A new peer has connected!", "Info", JOptionPane.INFORMATION_MESSAGE);
    }
    
}
