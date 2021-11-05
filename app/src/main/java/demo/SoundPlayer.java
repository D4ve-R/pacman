package demo;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

/**
 * Sound Class to play sounds
 * only tested on .wav files
 */
public class SoundPlayer implements Runnable{
    private String filename;         //!! depends on execution location / Pacman.java must be executed from /src
    private Clip clip;
    private Thread thread;
    private boolean forever;

    /**
     * Constructor method, init new Thread to play audio clip
     * @param filename input audiofile
     */
    public SoundPlayer(String filename, boolean forever) {
        this.filename = filename;
        this.forever = forever;
        thread = new Thread(this);
    }

    public void run(){
        try {
            clip = AudioSystem.getClip();
            AudioInputStream inStream = AudioSystem.getAudioInputStream(new File(filename));
            clip.open(inStream);
            if(forever){
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            }
            clip.start();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * method starts sound playing thread
     */
    public void play(){
        thread.start();
    }

}