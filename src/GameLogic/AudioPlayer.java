package GameLogic;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

/**
 * Simple audio player using Java Sound API.
 * Supports WAV and AIFF files natively.
 * For MP3 support, convert to WAV format first.
 */
public class AudioPlayer {
    private Clip clip;
    private FloatControl volumeControl;

    public void loadAndPlay(String filePath) {
        try {
            // Stop any existing playback
            stop();

            File audioFile = new File(filePath);
            if (!audioFile.exists()) {
                System.err.println("Audio file not found: " + filePath);
                return;
            }

            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            clip = AudioSystem.getClip();
            clip.open(audioStream);

            // Get volume control if available
            if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                setVolume(0.5f); // 50% volume
            }

            // Loop continuously
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            System.out.println("Playing audio: " + filePath);

        } catch (UnsupportedAudioFileException e) {
            System.err.println("Unsupported audio format: " + filePath);
            System.err.println("Convert MP3 to WAV format using: ffmpeg -i input.mp3 output.wav");
            e.printStackTrace();
        } catch (IOException | LineUnavailableException e) {
            System.err.println("Error playing audio: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void play() {
        if (clip != null && !clip.isRunning()) {
            clip.start();
        }
    }

    public void pause() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }

    public void stop() {
        if (clip != null) {
            clip.stop();
            clip.close();
            clip = null;
        }
    }

    /**
     * Set volume from 0.0 (silent) to 1.0 (full volume)
     */
    public void setVolume(float volume) {
        if (volumeControl != null) {
            // Convert 0-1 range to decibels
            float min = volumeControl.getMinimum();
            float max = volumeControl.getMaximum();
            float dB = min + (max - min) * volume;
            volumeControl.setValue(dB);
        }
    }

    public boolean isPlaying() {
        return clip != null && clip.isRunning();
    }
}
