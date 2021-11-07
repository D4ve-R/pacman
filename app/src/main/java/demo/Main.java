/**
 * Project Mac-Pan
 * Author david rechkemmer
 */
package demo;

public class Main {
    public static void main(String[] args){
        java.awt.EventQueue.invokeLater(new Runnable(){
            public void run() {
                App app = new App();
            }
        });
    }
}