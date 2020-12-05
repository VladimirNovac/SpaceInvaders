package com.company;

import java.io.*;
import java.net.URL;
import javax.sound.sampled.*;
import javax.swing.*;

public class Sound extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Sound(URL location){

        try{
            //File soundFile = new File(String.valueOf(location));
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(location);
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();

        } catch (UnsupportedAudioFileException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        } catch (LineUnavailableException e){
            e.printStackTrace();
        }
    }


}
