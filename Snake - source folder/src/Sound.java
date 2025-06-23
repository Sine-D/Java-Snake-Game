import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Sound {
    public static synchronized void play(String soundFile) {
        new Thread(() -> {
            try (AudioInputStream ais = AudioSystem.getAudioInputStream(new File(soundFile))) {
                Clip clip = AudioSystem.getClip();
                clip.open(ais);
                clip.start();
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                System.err.println("Sound error: " + e.getMessage());
            }
        }).start();
    }
}
