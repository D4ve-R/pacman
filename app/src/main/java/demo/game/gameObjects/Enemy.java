package demo.game.gameObjects;

/**
 *
 */
public class Enemy extends GameObject {
    private static final String uri = "Gegner.png";
    private int lives = 3;

    /**
     *
     */
    public Enemy(){
        super(uri);
    }

    /**
     * gets Players lives
     * @return
     */
    public int getLives(){
        return lives;
    }

    /**
     * sets Players lives
     * lives must be an int between 0 and 3
     * @param lives
     */
    public void setLives(int lives) {
        if(lives > 3) this.lives = 3;
        else if(lives < 0) this.lives = 0;
        else this.lives = lives;
    }
}
