/**
 * Project Mac-Pan
 * Author david rechkemmer
 */
package demo;

import demo.menus.MainMenu;

import java.awt.Dimension;
import javax.swing.JFrame;



public class App extends JFrame {
    public App(){
        setTitle("Mac-Pan");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(new MainMenu(this));
        setMinimumSize(new Dimension(950, 700));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }
}