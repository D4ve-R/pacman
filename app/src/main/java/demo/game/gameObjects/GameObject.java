package demo.game.gameObjects;

import demo.utils.ResourceHandler;

import java.awt.*;
import javax.swing.ImageIcon;
import javax.imageio.ImageIO;
import java.awt.image.ImageObserver;
import java.io.InputStream;

/**
 *
 */
public abstract class GameObject implements ResourceHandler {
    private String uriPrefix = "images";
    private String uri;
    private Image img;

    /**
     *
     * @param uri
     */
    public GameObject(String uri){
        loadImage(uriPrefix + "/" + uri);
    }

    /**
     *
     * @param uri
     */
    private void loadImage(String uri){
        try {
            img = new ImageIcon(ImageIO.read(getFileResourcesAsStream(uri))).getImage();
        } catch(Exception e) { e.printStackTrace();}
    }

    /**
     *
     * @param g
     * @param x
     * @param y
     * @param imageObserver
     */
    public void drawObject(Graphics2D g, int x, int y, ImageObserver imageObserver){
        g.drawImage(img, x, y, imageObserver);
    }

    @Override
    public InputStream getFileResourcesAsStream(String filename){
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
