package View.GUI;

import View.Main;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

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
            /* Select */
            String[] start_position = new String[]{"UP", "RIGHT", "DOWN",
                "LEFT"};
            JComboBox<String> positions_to_start = new JComboBox<>(start_position);
            Object[] options = new Object[]{};
            JOptionPane sp = new JOptionPane("Select a starting point:",
                    JOptionPane.QUESTION_MESSAGE,
                    JOptionPane.DEFAULT_OPTION,
                    null, options, null);
            sp.add(positions_to_start);
            /* Select */

            /* Input */
            JLabel label = new JLabel("Type a port to listen to: ");
            JTextField port_field = new JTextField(16);

            /* Input */
            /* Button */
            JButton b = new JButton("OK");
            sp.add(label);
            sp.add(port_field);
            sp.add(b);

            JDialog dia = new JDialog();
            dia.add(sp);
            //dia.add(port_field);
            dia.setSize(450, 450);
            dia.setLocation(500, 350);
            dia.pack();
            dia.setVisible(true);
            b.addActionListener((ActionEvent av) -> {
                String val = port_field.getText();
                if (val != null) {
                    try {
                        int port = Integer.parseInt(val);
                        int selected_start_position = positions_to_start.getSelectedIndex();
                        Main.m.change("Cross");
                        dia.setVisible(false);
                        JOptionPane.showMessageDialog(this.getParent(), "Press \"Crtl+h\" at any time to connect to a new peer.\n Press \"Crtl+s\" to start", "Info", JOptionPane.INFORMATION_MESSAGE);
                        try {
                            Main.controller.create_server(port, selected_start_position);
                        } catch (IOException e) {
                            System.out.println(e);
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(this.getParent(), "Please, type a number!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });
            /* Button */            
        });
        add(start, c);
    }
}
