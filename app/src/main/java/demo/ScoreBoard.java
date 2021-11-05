/**
 * Project Mac-Pan
 * Author david rechkemmer
 */
package demo;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ScoreBoard extends JPanel implements ActionListener {
    private JButton backBtn = new JButton("zurück");
    private JLabel label = new JLabel("HighScores");
    JFrame f;

    public ScoreBoard(JFrame f){
        this.f = f;
        backBtn.addActionListener(this);
        add(backBtn);
        add(label);
    }

    private void checkScores(int currentScore){

    }

    public void actionPerformed(ActionEvent e){
        if(e.getSource() == backBtn){
            MainMenu mM = new MainMenu(f);
            f.setContentPane(mM);
            f.revalidate();
        }
    }
}