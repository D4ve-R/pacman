/**
 * Project Mac-Pan
 * Author david rechkemmer
 */
package demo;

import java.awt.Dimension;
import javax.swing.JFrame;


public class App extends JFrame {
    public App(){
        setTitle("Mac-Pan");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(new MainMenu(this));
        setMinimumSize(new Dimension(800, 600));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}