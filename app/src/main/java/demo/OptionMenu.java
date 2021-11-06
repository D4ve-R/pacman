/**
 * Project Mac-Pan
 * Author david rechkemmer
 */
package demo;

import java.awt.Font;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.BoxLayout;

public class OptionMenu extends JPanel implements ActionListener {
    private JPanel group = new JPanel();
    private JLabel label = new JLabel("Optionen");
    private JLabel info = new JLabel();
    private JButton keyBtn = new JButton("Tastenbelegung");
    private JButton descrpBtn = new JButton("Spielbeschreibung");
    private JButton backBtn = new JButton("zurück");
    JFrame f;

    public OptionMenu(JFrame f) {
        this.f = f;
        setLayout(new BorderLayout());

        JPanel btns = new JPanel();
        btns.add(keyBtn);
        btns.add(descrpBtn);

        label.setFont(new Font("Arial", Font.BOLD, 20));

        group.setLayout(new BoxLayout(group, BoxLayout.Y_AXIS));
        group.add(label);
        group.add(btns);

        add(backBtn, BorderLayout.PAGE_END);
        add(group, BorderLayout.NORTH);
        add(info);

        keyBtn.addActionListener(this);
        descrpBtn.addActionListener(this);
        backBtn.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == backBtn){
            MainMenu mM = new MainMenu(f);
            f.setContentPane(mM);
            f.revalidate();
        }
        else if(e.getSource() == keyBtn){
            info.setText("<html>Das hier sind <br>die Key bindings</html>");
        }
        else if(e.getSource() == descrpBtn){
            info.setText("<html>Das hier ist ein anderer <br> mutliline text <br> mal gucken ob das klappt</html>");
        }
    }
}