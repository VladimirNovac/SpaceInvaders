package com.company;

import javax.sound.sampled.*;
import java.io.*;
import java.net.URL;


public class music {

   private Clip clip;

    public music(URL location) {

        try{
            //File soundFile = new File(location);
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(location);
            clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY);

        } catch (UnsupportedAudioFileException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        } catch (LineUnavailableException e){
            e.printStackTrace();
        }
    }

    public void stopMusic(){
        clip.stop();
    }
}
