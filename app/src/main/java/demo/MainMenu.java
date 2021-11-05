/**
 * Project Mac-Pan
 * Author david rechkemmer
 */
package demo;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Component;
import java.awt.GridBagLayout;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.ImageIcon;
import javax.swing.BoxLayout;
import javax.swing.BorderFactory;
import java.io.File;
import javax.imageio.ImageIO;

public class MainMenu extends JPanel implements ActionListener {
    JPanel group = new JPanel();
    private JButton playBtn = new JButton("Spiel starten");
    private JButton loadBtn = new JButton("Spiel laden");
    private JButton scoreBtn = new JButton("HighScores");
    private JButton optionBtn = new JButton("Optionen");
    private JButton exitBtn = new JButton("Spiel beenden");
    private Image img;
    private JFrame f; //parent frame

    public MainMenu(JFrame f){
        this.f = f;
        //setBackground(Color.decode("#cccccc"));
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        try{
            img = ImageIO.read(new File("./ressources/LoST_Gruen.png"));
            img = img.getScaledInstance(200, 200, Image.SCALE_DEFAULT);
        }catch(Exception e){
            e.printStackTrace();
        }

        group.setLayout(new BoxLayout(group, BoxLayout.Y_AXIS));


        JLabel label = new JLabel(new ImageIcon(img));
        group.add(label);

        JPanel buttons = new JPanel();
        buttons.setLayout(new BoxLayout(buttons, 1));
        buttons.add(playBtn);
        buttons.add(loadBtn);
        buttons.add(scoreBtn);
        buttons.add(optionBtn);
        buttons.add(exitBtn);

        playBtn.addActionListener(this);
        loadBtn.addActionListener(this);
        scoreBtn.addActionListener(this);
        optionBtn.addActionListener(this);
        exitBtn.addActionListener(this);

        group.add(buttons);

        add(group);
    }

    public void actionPerformed(ActionEvent e){
        if(e.getSource() == playBtn){
            Game game = new Game();
            f.setContentPane(game);
            f.revalidate();
            game.start();
        }
        else if(e.getSource() == loadBtn){

        }
        else if(e.getSource() == scoreBtn){
            FileEditor fe = new FileEditor();
            f.setContentPane(fe);
            f.revalidate();
        }
        else if(e.getSource() == optionBtn){
            OptionMenu optM = new OptionMenu();
            f.setContentPane(optM);
            f.revalidate();
        }
        else if(e.getSource() == exitBtn){
            f.dispose();
        }
    }
}