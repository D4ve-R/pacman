/**
 * Project Mac-Pan
 * Author david rechkemmer
 */
package demo;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.*;

/**
 * The Game Class handles all the logic, input and output
 * It inherits from @see <a href="https://docs.oracle.com/javase/7/docs/api/javax/swing/JPanel.html">JPanel</a>
 * It implements @see <a href="https://docs.oracle.com/javase/7/docs/api/java/awt/event/ActionListener.html">ActionListener</a>
 */
public class Game extends JPanel implements Runnable{

    private Thread thread;
    private final int panelWidth;
    private final int panelHeight;
    private boolean running;
    private boolean loop;
    private boolean once = true;
    private int speed = 10;

    private Image ghost;
    private Image item;
    private Image wall;
    private Image floor;
    private Image door;
    private Image key;
    private Image coin;

    private char[][] data = new char[20][30];

    //pacman attributes
    private int xPos, yPos;     //figure position top left corner
    private int dx, dy;         // figure dimension
    private int pacmanAngle;
    private boolean bite;       //toggles to animate biting

    //ghosts attributes
    private int[] ghostsX;
    private int[] ghostsY;
    private int ghostCount;


    /**
     * Constructor method for Game
     * Calls @see initVars()
     */
    public Game(){
        panelWidth = 900;
        panelHeight = 600;
        addKeyListener(new KeyHandler());
        setFocusable(true);
        setBackground(Color.black);
        initVars();
        initFromFile("./ressources/levels/test.txt");
        
        loadImages();
    }

    /**
     * loads Images from sources
     */
    private void loadImages() {
        ghost = new ImageIcon("./ressources/images/Gegner.png").getImage();
        item = new ImageIcon("./ressources/images/Item.png").getImage();
        wall = new ImageIcon("./ressources/images/Wand.png").getImage();
        floor = new ImageIcon("./ressources/images/Boden.png").getImage();
        door = new ImageIcon("./ressources/images/Door.png").getImage();
        key = new ImageIcon("./ressources/images/Key.png").getImage();
        coin = new ImageIcon("./ressources/images/Muenze.png").getImage();
    }

    /**
     * Method to initialize xPos, yPos, dx, dy
     */
    private void initVars(){
        xPos = panelWidth / 2 - 15;
        yPos = panelHeight / 2 - 15;
        dx = 30;
        dy = 30;

        ghostsX = new int[10];
        ghostsY = new int[10];
        ghostCount = 0;
    }

    /**
     * init data[][] from level.txt file
     * @param filename of file to be processed
     */
    private void initFromFile(String filename){
        try {
            FileReader reader = new FileReader(filename);
            int in;
            int i = 0;
            int j = 0;
            int count = 0;
            while((in = reader.read()) != -1) {
                if(in < 32 || in > 122) continue;
                if (j == 20) break;

                data[j][i] = (char) in;
                if (i == 29) {
                    i = 0;
                    ++j;
                    continue;
                }
                ++i;
            }
        }catch(IOException e){
            e.printStackTrace();
        }

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
    @Override
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
                once = false;
            }
        }
        stop();
    }

    private void drawAll(Graphics2D g, char[][] arr){
        //draw borders
        drawBorders(g);

        for(int i = 0; i < arr.length; i++){
            for(int j = 0; j < arr[0].length; j++){
                switch (arr[i][j]){
                    case 'h':
                        drawWall(g, j * 30, i * 30);
                        break;
                    case '*':
                        drawItem(g, j * 30, i * 30);
                        break;
                    case 'g':
                        drawGhosts(g, j * 30, i * 30);
                        ghostsX[ghostCount] = j * 30;
                        ghostsY[ghostCount] = i * 30;
                        ghostCount++;
                        break;
                    case 'p':
                        if(once) {
                            drawPacMan(g, j * 30, i * 30);
                            xPos = j * 30;
                            yPos = i * 30;
                        }
                        drawFloor(g, j * 30, i * 30);
                        break;
                    case 'd':
                        drawDoor(g, j * 30, i * 30);
                        break;
                    case 'k':
                        drawKey(g, j * 30, i * 30);
                        break;
                }
            }
        }
    }

    private void drawKey(Graphics g, int x, int y){
        g.drawImage(key, x, y, this);
    }

    private void drawDoor(Graphics2D g, int x, int y){
        g.drawImage(door, x, y, this);
    }

    private void drawFloor(Graphics2D g, int x, int y){
        g.drawImage(floor, x, y, this);
    }

    private void drawWall(Graphics2D g, int x, int y){
        g.drawImage(wall, x, y, this);
    }

    private void drawItem(Graphics2D g, int x, int y){
        g.drawImage(item, x, y, this);
    }

    private void drawPacMan(Graphics2D g2d, int x, int y){
        g2d.setPaint(Color.yellow);
        g2d.fillArc(x, y, dx, dy,bite ? pacmanAngle : (pacmanAngle - 20), bite ? 300 : 340);
    }

    private void drawGhosts(Graphics2D g2d, int x, int y){
        g2d.drawImage(ghost, x, y, this);
    }

    private void drawBorders(Graphics2D g2d){
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
     * overridden paintComponent method
     * @param g Graphics
     */
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        drawAll(g2d, data);

        drawPacMan(g2d, xPos, yPos);
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
                    add(new PauseMenu());
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

    public void swap(char[][]arr, int i, int j, int k, int l){
        try{
            char tmp = arr[i][j];
            arr[i][j] = arr[k][l];
            arr[k][l] = tmp;
        }catch(ArrayIndexOutOfBoundsException e){
            e.printStackTrace();
        }
    }
}
