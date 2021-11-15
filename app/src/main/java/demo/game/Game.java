/**
 * Project Mac-Pan
 * Author david rechkemmer
 */
package demo.game;

import java.awt.Font;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.Reader;
import java.io.InputStreamReader;
import javax.swing.JPanel;

import demo.game.gameObjects.GameObject;
import demo.game.gameObjects.Enemy;
import demo.utils.ResourceHandler;
import demo.utils.SoundPlayer;
import demo.menus.PauseMenu;

/**
 * The Game Class handles all the logic, input and output
 * It inherits from @see <a href="https://docs.oracle.com/javase/7/docs/api/javax/swing/JPanel.html">JPanel</a>
 * It implements @see <a href="https://docs.oracle.com/javase/7/docs/api/java/awt/event/ActionListener.html">ActionListener</a>
 */
public class Game extends JPanel implements Runnable, ResourceHandler {

    private Thread thread;
    private final int panelWidth;
    private final int panelHeight;
    private boolean running;
    private boolean loop;
    private boolean startPositions = true;
    private int speed = 30;
    public int score;

    private GameObject ghost;
    private GameObject item;
    private GameObject wall;
    private GameObject floor;
    private GameObject door;
    private GameObject key;
    private GameObject coin;
    private GameObject life;

    private char[][] data = new char[20][30];

    // pacman attributes
    private int lives;
    private int xPos, yPos;     //figure position top left corner
    private int dx, dy;         // figure dimension
    private int pacmanAngle;
    private boolean bite;       //toggles to animate biting

    // ghost attributes
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
        initFromFile("levels/test.txt");
        loadGameObjects();
    }

    /**
     * loads GameObjects
     */
    private void loadGameObjects() {
        ghost = new Enemy();
        item = new GameObject("Item.png");
        wall = new GameObject("Wand.png");
        floor = new GameObject("Boden.png");
        door = new GameObject("Door.png");
        key = new GameObject("Key.png");
        coin = new GameObject("Muenze.png");
        life = new GameObject("Leben.png");
    }

    /**
     * Method to initialize xPos, yPos, dx, dy
     */
    private void initVars(){
        dx = 30;
        dy = 30;
        xPos = panelWidth/(2 - (dx/2));
        yPos = panelHeight/(2 - (dy/2));

        ghostsX = new int[10];
        ghostsY = new int[10];
        ghostCount = 0;
        score  = 0;
        lives = 3;
    }

    /**
     * init data[][] from level.txt file
     * @param filename of file to be processed
     */
    private void initFromFile(String filename){
        try {
            Reader reader = new InputStreamReader(getFileResourcesAsStream(filename));
            int in;
            int i = 0;
            int j = 0;
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
        }catch(Exception e){
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

        SoundPlayer soundtrack = new SoundPlayer("sound/file_example_WAV.wav", true);
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

            while (running) {
                long now = System.nanoTime();
                delta += (now - lastTime) / ns;
                lastTime = now;
                if (running)
                    startPositions = false;
                    update();
                frames++;

                // prints out framerate to terminal
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
        drawAll(g2d, data);
        drawPacMan(g2d, xPos, yPos);
        for(int i = 0; i < ghostCount; i++){
            ghost.drawObject(g2d, ghostsX[i], ghostsY[i], this);
        }
    }

    private void drawAll(Graphics2D g, char[][] arr){
        //draw borders
        drawBorders(g);

        for(int i = 0; i < arr.length; i++){
            for(int j = 0; j < arr[0].length; j++){
                switch (arr[i][j]){
                    case 'h':
                        wall.drawObject(g, j * 30, i * 30, this);
                        //drawWall(g, j * 30, i * 30);
                        break;
                    case '*':
                        coin.drawObject(g, j * 30, i * 30, this);
                        break;
                    case 'g':
                        if(startPositions) {
                            ghost.drawObject(g, ghostsX[i], ghostsY[i], this);
                            //drawGhosts(g, j * 30, i * 30);
                            ghostsX[ghostCount] = j * 30;
                            ghostsY[ghostCount] = i * 30;
                            ghostCount++;
                        }
                        break;
                    case 'p':
                        if(startPositions) {
                            drawPacMan(g, j * 30, i * 30);
                            xPos = j * 30;
                            yPos = i * 30;
                        }
                        floor.drawObject(g, j * 30, i * 30, this);
                        break;
                    case 'x':
                        door.drawObject(g, j * 30, i * 30, this);
                        break;
                    case 'k':
                        key.drawObject(g, j * 30, i * 30, this);
                        break;
                    case '.':
                        floor.drawObject(g, j * 30, i *30, this);
                        break;
                    default:
                        item.drawObject(g, j * 30, i * 30, this);
                        break;
                }
            }
        }
    }

    private void drawPacMan(Graphics2D g2d, int x, int y){
        g2d.setPaint(Color.yellow);
        g2d.fillArc(x, y, dx, dy,bite ? pacmanAngle : (pacmanAngle - 20), bite ? 300 : 340);
    }

    private void drawBorders(Graphics2D g2d){
        g2d.setPaint(Color.white);
        g2d.drawLine(0,0,0,panelHeight);
        g2d.drawLine(0, 0, panelWidth, 0);
        g2d.drawLine(0, panelHeight, panelWidth, panelHeight);
        g2d.drawLine(panelWidth, 0, panelWidth, panelHeight);
        g2d.setFont(new Font("Arial", Font.BOLD, 20));
        g2d.drawString("Score: " + score, panelHeight / 2 + 10, panelHeight + 40);
        for(int i = 0; i < lives; i++){
            life.drawObject(g2d, (i * 30) + (30 * i),panelHeight + 10, this);
        }
    }

    private void drawGameOver(){
        System.out.println("Game Over");
    }

    /**
     * calls checkCollision and repaint
     */
    private void update(){
        checkCollision();
        repaint();
    }

    /**
     * checks for collision between pacman and ghosts
     */
    private void checkCollision(){
        SoundPlayer effect = new SoundPlayer("sound/pacman_chomp.wav", false);

        // detect collision with item
        if(data[yPos/30][xPos/30] == '*'){
            data[yPos/30][xPos/30] = '.';
            score += 10;
            effect.play();
        }
        else if(data[yPos/30][(xPos + dx - 1)/30] == '*'){
            data[yPos/30][(xPos + dx)/30] = '.';
            score += 10;
            effect.play();
        }
        else if(data[(yPos + dy - 1)/30][xPos/30] == '*'){
            data[(yPos + dy)/30][xPos/30] = '.';
            score += 10;
            effect.play();
        }
        else if(data[(yPos + dy - 1)/30][(xPos + dx - 1)/30] == '*'){
            data[(yPos +dy)/30][(xPos + dx)/30] = '.';
            score += 10;
            effect.play();
        }

        // check collision with ghosts
        for(int i = 0; i < ghostsX.length; i++){
            if(((xPos < ghostsX[i] + dx) && (xPos >= ghostsX[i])) || ((xPos + dx > ghostsX[i]) && (xPos + dx <= ghostsX[i] + dx))){
                if(((yPos >= ghostsY[i]) && (yPos < ghostsY[i] + dy)) || ((yPos + dy > ghostsY[i]) && yPos + dy <= ghostsY[i] + dy))
                    collision();
            }
        }
    }

    /**
     * collision handler
     */
    private void collision(){
        lives--;
        if(lives == 0){
            drawGameOver();
            running = false;
        }
        startPositions = true;
        System.out.println("collision");
        SoundPlayer effect = new SoundPlayer("sound/pacman_eatghost.wav", false);
        effect.play();
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
                    if(data[yPos/30][xPos/30] == 'h'){
                        xPos += speed;
                    }
                } else if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) {
                    pacmanAngle = 30;
                    xPos += speed;
                    if(data[yPos/30][(xPos + dx - 1)/30] == 'h'){
                        xPos -= speed;
                    }
                } else if (key == KeyEvent.VK_UP || key == KeyEvent.VK_W) {
                    pacmanAngle = 120;
                    yPos -= speed;
                    if(data[(yPos )/30][(xPos + dx - 1)/30] == 'h'){
                        yPos += speed;
                    }
                } else if (key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) {
                    pacmanAngle = 300;
                    yPos += speed;
                    if(data[(yPos + dy - 1)/30][xPos/30] == 'h'){
                        yPos -= speed;
                    }
                }
            }
        }
    }
}
