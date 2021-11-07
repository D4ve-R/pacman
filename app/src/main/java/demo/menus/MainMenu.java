/**
 * Project Mac-Pan
 * Author david rechkemmer
 */
package demo.menus;

import demo.game.Game;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridBagLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.BoxLayout;
import javax.swing.BorderFactory;
import java.io.InputStream;
import java.nio.file.Paths;
import javax.imageio.ImageIO;

public class MainMenu extends JPanel implements ActionListener {
    JPanel group = new JPanel();
    JLabel label;
    private JButton playBtn = new JButton("Spiel starten");
    private JButton loadBtn = new JButton("Spiel laden");
    private JButton scoreBtn = new JButton("HighScores");
    private JButton optionBtn = new JButton("Optionen");
    private JButton editBtn = new JButton("Level Editor");
    private JButton exitBtn = new JButton("Spiel beenden");
    private Image img;
    private JFrame f; //parent frame

    public MainMenu(JFrame f){
        this.f = f;
        //setBackground(Color.decode("#cccccc"));
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        try{
            img = ImageIO.read(getFileResourcesAsStream("images/LoST_Gruen.png"));
            img = img.getScaledInstance(200, 200, Image.SCALE_DEFAULT);
        }catch(Exception e){
            System.out.println("MainMenu Logo couldn't be found");
            e.printStackTrace();
        }

        group.setLayout(new BoxLayout(group, BoxLayout.Y_AXIS));

        if(img != null) {
            label = new JLabel(new ImageIcon(img));
            group.add(label);
        }

        JPanel buttons = new JPanel();
        buttons.setLayout(new BoxLayout(buttons, 1));
        buttons.add(playBtn);
        buttons.add(loadBtn);
        buttons.add(scoreBtn);
        buttons.add(optionBtn);
        buttons.add(editBtn);
        buttons.add(exitBtn);

        playBtn.addActionListener(this);
        loadBtn.addActionListener(this);
        scoreBtn.addActionListener(this);
        optionBtn.addActionListener(this);
        editBtn.addActionListener(this);
        exitBtn.addActionListener(this);

        group.add(buttons);
        add(group);
    }

    @Override
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
            ScoreBoard board = new ScoreBoard(f);
            f.setContentPane(board);
            f.revalidate();
        }
        else if(e.getSource() == optionBtn){
            OptionMenu optM = new OptionMenu(f);
            f.setContentPane(optM);
            f.revalidate();
        }
        else if(e.getSource() == editBtn){
            FileEditor editor = new FileEditor(f);
            f.setContentPane(editor);
            f.revalidate();
        }
        else if(e.getSource() == exitBtn){
            f.dispose();
        }
    }

    private InputStream getFileResourcesAsStream(String filename){
        ClassLoader cl = getClass().getClassLoader();
        InputStream in = cl.getResourceAsStream(filename);
        if(in == null){
            throw new IllegalArgumentException("File not found: " + filename);
        }
        else{
            return in;
        }
    }
}