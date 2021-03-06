package demo.utils;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.InputStream;

/**
 * Sound Class to play sounds
 * only tested on .wav files
 */
public class SoundPlayer implements Runnable, ResourceHandler{
    private Clip clip;
    private Thread thread;
    private boolean forever;
    private InputStream in;

    /**
     * Constructor method, init new Thread to play audio clip
     * @param filename input audiofile
     */
    public SoundPlayer(String filename, boolean forever) {
        this.forever = forever;
        try {
            in = getFileResourcesAsStream(filename);
        }catch(Exception e){
            e.printStackTrace();
        }
        thread = new Thread(this);
    }


    public void run(){
        if(in != null) {
            try {
                clip = AudioSystem.getClip();
                AudioInputStream inStream = AudioSystem.getAudioInputStream(in);
                clip.open(inStream);
                if (forever) {
                    clip.loop(Clip.LOOP_CONTINUOUSLY);
                }
                clip.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * method starts sound playing thread
     */
    public void play(){
        thread.start();
    }

}