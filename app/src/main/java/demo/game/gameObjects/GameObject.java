package demo.game.gameObjects;

import demo.utils.ResourceHandler;

import java.awt.*;
import javax.swing.ImageIcon;
import javax.imageio.ImageIO;
import java.awt.image.ImageObserver;

/**
 * GameObject class handles resources
 */
public class GameObject implements ResourceHandler {
    private String uriPrefix = "images";
    private String uri;
    private Image img;

    /**
     * Constructor for GameObject Class
     * @param uri
     */
    public GameObject(String uri){
        loadImage(uriPrefix + "/" + uri);
    }

    /**
     * loadImage() calls getFileResourcesAsStream()
     * and loads the image from uriPrefix + uri
     * @param uri
     */
    private void loadImage(String uri){
        try {
            img = new ImageIcon(ImageIO.read(getFileResourcesAsStream(uri))).getImage();
        } catch(Exception e) { e.printStackTrace();}
    }

    /**
     * drawObject() draws Object on Graphics Object g
     * @param g Graphics Object to be drawn on
     * @param x position of top left corner
     * @param y position of top left corner
     * @param imageObserver
     */
    public void drawObject(Graphics2D g, int x, int y, ImageObserver imageObserver){
        g.drawImage(img, x, y, imageObserver);
    }
}
