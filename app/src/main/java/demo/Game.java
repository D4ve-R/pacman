/**
 * Project Mac-Pan
 * Author david rechkemmer
 */
package demo;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;

/**
 * The Game Class handles all the logic, input and output
 * It inherits from @see <a href="https://docs.oracle.com/javase/7/docs/api/javax/swing/JPanel.html">JPanel</a>
 * It implements @see <a href="https://docs.oracle.com/javase/7/docs/api/java/awt/event/ActionListener.html">ActionListener</a>
 */
public class Game extends JPanel implements Runnable{

    private Thread thread;
    private int panelWidth;
    private int panelHeight;
    private boolean running;
    private boolean loop;
    private int speed = 10;

    //pacman attributes
    private int xPos, yPos;     //figure position top left corner
    private int dx, dy;         // figure dimension
    private int pacmanAngle;
    private boolean bite;       //toggles to animate biting

    //ghosts attributes
    private int[] ghostsX;
    private int[] ghostsY;

    /**
     * Constructor method for Game
     * Calls @see initPanel()
     * Calls @see initVars()
     * @param d Dimension of parent JFrame
     */
    public Game(){
        panelWidth = 500;
        panelHeight = 500;
        addKeyListener(new KeyHandler());
        setFocusable(true);
        setBackground(Color.black);
        initVars();
    }

    /**
     * Method to initialize xPos, yPos, dx, dy
     */
    public void initVars(){
        xPos = panelWidth / 2 - 15;
        yPos = panelHeight / 2 - 15;
        dx = 30;
        dy = 30;

        ghostsX = new int[]{25, panelWidth / 2 + 50 , panelWidth - 2 * dx};
        ghostsY = new int[]{25, panelHeight / 2 + 50, panelHeight - 2 * dy};
    }

    /**
     * Method starts new Thread, sets running to true
     */
    public void start(){
        requestFocus();
        loop = true;
        running = true;
        thread = new Thread(this);
        thread.start();
        System.out.println("\u2705" + " Thread with ID: " + thread.getId() + " running!");

        SoundPlayer soundtrack = new SoundPlayer("./ressources/audio/file_example_WAV.wav", true);
        soundtrack.play();
    }


    /**
     * Method stops thread, sets running to false
     */
    private void stop(){
        try{
            loop = false;
            System.out.println("\u274c" + " Thread with ID: " + thread.getId() + " stopped!");
            thread.join();
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }

    /**
     * will be executed when start() is called
     * implements basic game loop 60 fps
     */
    public void run(){
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0 ;
        while(loop) {
            try {
                thread.sleep(150);
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("Loop still running");

            while (running) {
                long now = System.nanoTime();
                delta += (now - lastTime) / ns;
                lastTime = now;
                if (running)
                    update();
                frames++;

                if (System.currentTimeMillis() - timer > 1000) {
                    timer += 1000;
                    System.out.println("fps: " + frames);
                    frames = 0;
                    bite = !bite;     //toggle
                }

                try {
                    thread.sleep(14);     // roughly 60 fps
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        stop();
    }

    /**
     * overridden paintComponent method
     * @param g Graphics
     */
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        //g2d.setPaint(Color.red);
        //Rectangle2D rec = new Rectangle(xPos, yPos, dx, dy);
        //g2d.fill(rec);

        //draw pacman
        g2d.setPaint(Color.yellow);
        g2d.fillArc(xPos, yPos, dx, dy,bite ? pacmanAngle : (pacmanAngle - 20), bite ? 300 : 340);

        //draw ghosts
        g2d.setPaint(Color.white);
        for(int i = 0; i < ghostsX.length; i++){
            g2d.fillRect(ghostsX[i], ghostsY[i], dx, dy);
        }

        //draw borders
        g2d.setPaint(Color.green);
        g2d.drawLine(0,0,0,panelHeight);
        g2d.drawLine(0, 0, panelWidth, 0);
        g2d.drawLine(0, panelHeight, panelWidth, panelHeight);
        g2d.drawLine(panelWidth, 0, panelWidth, panelHeight);
        g2d.setFont(new Font("Arial", Font.BOLD, 18));
        String s = "Score: ";
        g2d.drawString(s, panelHeight / 2 + 10, panelHeight + 40);
    }

    /**
     * calls checkCollsion and repaint
     */
    private void update(){
        checkCollision();
        repaint();
    }

    /**
     * collision handler
     */
    private void collision(){
        System.out.println("collision");
        //Sound effect = new Sound("./ressources/audio/pacman_eatghost.wav", false);
        //effect.play();
    }

    /**
     * checks for collsion between pacman and ghosts
     */
    private void checkCollision(){
        for(int i = 0; i < ghostsX.length; i++){
            if(((xPos <= ghostsX[i] + dx) && (xPos >= ghostsX[i])) || ((xPos + dx >= ghostsX[i]) && (xPos + dx <= ghostsX[i] + dx))){
                if(((yPos >= ghostsY[i]) && (yPos <= ghostsY[i] + dy)) || ((yPos + dy >= ghostsY[i]) && yPos + dy <= ghostsY[i] + dy))
                    collision();
            }
        }
    }

    /**
     * The KeyHandler Class handles all keyboard input
     * It inherit from @see <a href="https://docs.oracle.com/javase/7/docs/api/java/awt/event/KeyAdapter.html">KeyAdapter</a>
     */
    class KeyHandler extends KeyAdapter{

        /**
         * handles if key is pressed down and updates Game attributes
         * @param e KeyEvent
         */
        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();

            if (key == KeyEvent.VK_P) {
                if (running) {
                    running = false;
                } else {
                    running = true;
                }
            }

            if (running) {
                if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) {
                    pacmanAngle = 210;
                    xPos -= speed;
                    if (xPos < 0)
                        xPos = 1;
                } else if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) {
                    pacmanAngle = 30;
                    xPos += speed;
                    if (xPos + dx > panelWidth)
                        xPos = panelWidth - dx;
                } else if (key == KeyEvent.VK_UP || key == KeyEvent.VK_W) {
                    pacmanAngle = 120;
                    yPos -= speed;
                    if (yPos < 0)
                        yPos = 1;
                } else if (key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) {
                    pacmanAngle = 300;
                    yPos += speed;
                    if (yPos + dy > panelHeight)
                        yPos = panelHeight - dy;
                }
            }
        }
    }
}
