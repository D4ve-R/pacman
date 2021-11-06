package demo.utils;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.net.URL;
import java.nio.file.Paths;

/**
 * Sound Class to play sounds
 * only tested on .wav files
 */
public class SoundPlayer implements Runnable{
    private URL url;
    private Clip clip;
    private Thread thread;
    private boolean forever;
    private File file;

    /**
     * Constructor method, init new Thread to play audio clip
     * @param filename input audiofile
     */
    public SoundPlayer(String filename, boolean forever) {
        this.forever = forever;
        try {
            file = new File(filename);
        }catch(Exception e){
            e.printStackTrace();
        }
        thread = new Thread(this);
    }


    public SoundPlayer(URL url, boolean forever){
        try{
            file = Paths.get(url.toURI()).toFile();
        }catch(Exception ex){
            ex.printStackTrace();
        }
        thread = new Thread(this);
    }

    public void run(){
        if(file != null) {
            try {
                clip = AudioSystem.getClip();
                AudioInputStream inStream = AudioSystem.getAudioInputStream(file);
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