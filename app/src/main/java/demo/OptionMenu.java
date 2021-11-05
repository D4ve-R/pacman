/**
 * Project Mac-Pan
 * Author david rechkemmer
 */
package demo;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.BoxLayout;

public class OptionMenu extends JPanel implements ActionListener {
    private JPanel group = new JPanel();
    private JLabel label = new JLabel("Optionen");
    private JButton keyBtn = new JButton("Tastenbelegung");
    private JButton descrpBtn = new JButton("Spielbeschreibung");
    private JButton backBtn = new JButton("zurück");
    JFrame f;

    public OptionMenu(JFrame f) {
        this.f = f;
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GridBagConstraints c = new GridBagConstraints();

        JPanel btns = new JPanel();
        btns.add(keyBtn);
        btns.add(descrpBtn);

        label.setFont(new Font("Arial", Font.BOLD, 20));

        group.setLayout(new BoxLayout(group, BoxLayout.Y_AXIS));
        group.add(label);
        group.add(btns);

        backBtn.addActionListener(this);
        add(backBtn, c);
        add(group);
    }

    public void actionPerformed(ActionEvent e){
        if(e.getSource() == backBtn){
            MainMenu mM = new MainMenu(f);
            f.setContentPane(mM);
            f.revalidate();
        }
    }
}