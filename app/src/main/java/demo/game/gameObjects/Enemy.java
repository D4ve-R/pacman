package demo.game.gameObjects;

/**
 * Enemy Class handles all Enemy resources
 */
public class Enemy extends GameObject {
    private static final String uri = "Gegner.png";

    /**
     * Constructor for Enemy Class
     * calls Constructor of GameObject with given uri
     */
    public Enemy(){
        super(uri);
    }
}
