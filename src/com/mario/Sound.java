package com.mario;

import javax.sound.sampled.*;
import java.io.IOException;

public class Sound {

    private Clip clip;
    private long clipTime;

    public Sound(String path) throws IOException, UnsupportedAudioFileException, LineUnavailableException {

        AudioInputStream inputStream = AudioSystem.getAudioInputStream(getClass().getResource(path));
        AudioFormat baseFormat = inputStream.getFormat();
        AudioFormat decodeFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
                                                    baseFormat.getSampleRate(),
                                                    16,
                                                    baseFormat.getChannels(),
                                                    baseFormat.getChannels()*2,
                                                    baseFormat.getFrameRate(),
                                                    false);
        AudioInputStream decodedInputStream = AudioSystem.getAudioInputStream(decodeFormat,inputStream);
        clip = AudioSystem.getClip();
        clip.open(decodedInputStream);
    }

    public void play(){
        if(clip==null) return;
        stop();
        clip.setFramePosition(0);
        clip.start();
        Game.themePlay=true;
    }

    public void close(){
        stop();
        clip.close();
    }

    public void stop(){
        if(clip.isRunning()) {
            clip.stop();
            Game.themePlay=false;
        }
    }

    public void pause(){
        if(Game.themePlay || clip.isRunning()) {
            if (clip.isRunning()) {
                clipTime = clip.getMicrosecondPosition();
                clip.stop();
                clip.setMicrosecondPosition(clipTime);
                Game.themePlay=false;
            }
        }
        else{
            clip.start();
            Game.themePlay=true;
        }
    }

}
