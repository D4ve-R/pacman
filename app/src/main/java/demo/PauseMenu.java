package demo;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class PauseMenu extends JPanel implements ActionListener{
    private JButton playBtn = new JButton("Spiel weiter");
    private JButton loadBtn = new JButton("Spiel laden");
    private JButton scoreBtn = new JButton("HighScores");
    private JButton optionBtn = new JButton("Optionen");
    private JButton exitBtn = new JButton("Spiel beenden");

    public PauseMenu(){
        setLayout(new BorderLayout());

        JPanel btns = new JPanel();
        btns.setLayout(new BoxLayout(btns, BoxLayout.Y_AXIS));
        btns.add(playBtn);
        btns.add(loadBtn);
        btns.add(scoreBtn);
        btns.add(optionBtn);
        btns.add(exitBtn);

        add(btns);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e){

    }
}
