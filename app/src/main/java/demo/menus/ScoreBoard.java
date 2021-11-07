/**
 * Project Mac-Pan
 * Author david rechkemmer
 */
package demo.menus;

import demo.utils.ResourceHandler;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.Font;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;

public class ScoreBoard extends JPanel implements ActionListener, ResourceHandler {
    private JButton backBtn = new JButton("zurück");
    private JLabel label = new JLabel("HighScores :");
    JFrame f;

    public ScoreBoard(JFrame f){
        this.f = f;
        setLayout(new BorderLayout());
        label.setFont(new Font("Arial", Font.BOLD, 20));
        backBtn.addActionListener(this);
        add(backBtn, BorderLayout.PAGE_END);
        add(label, BorderLayout.NORTH);
    }

    private void checkScores(int currentScore){

    }

    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == backBtn){
            MainMenu mM = new MainMenu(f);
            f.setContentPane(mM);
            f.revalidate();
        }
    }

    @Override
    public InputStream getFileResourcesAsStream(String filename) {
        return null;
    }
}