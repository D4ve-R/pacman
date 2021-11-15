package demo.game;

public class Player extends GameObject {
    private int lives = 3;

    public Player(){
        super("images/Gegner.png");
    }

    public int getLives(){
        return lives;
    }
}
