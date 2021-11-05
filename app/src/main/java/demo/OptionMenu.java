/**
 * Project Mac-Pan
 * Author david rechkemmer
 */
package demo;

import java.awt.Font;
import java.awt.GridBagLayout;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.BoxLayout;

public class OptionMenu extends JPanel {
    private JPanel group = new JPanel();
    private JLabel label = new JLabel("Optionen");
    private JButton keyBtn = new JButton("Tastenbelegung");
    private JButton descrpBtn = new JButton("Spielbeschreibung");

    public OptionMenu(){
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel btns = new JPanel();
        btns.add(keyBtn);
        btns.add(descrpBtn);

        label.setFont(new Font("Arial", Font.BOLD, 20));

        group.setLayout(new BoxLayout(group, BoxLayout.Y_AXIS));
        group.add(label);
        group.add(btns);

        add(group);
    }
}