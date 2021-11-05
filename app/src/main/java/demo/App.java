/**
 * Project Mac-Pan
 * Author david rechkemmer
 */
package demo;

import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class App extends JFrame {
    public App(){
        setTitle("test");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(new MainMenu(this));
        setMinimumSize(new Dimension(800, 600));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}