package grafics;
import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class SoundHandler {

	private Clip[] colors = new Clip[4];
	private Clip victory;
	private Clip defeat;

	public SoundHandler() {
		try {
			AudioInputStream inputStream;
			for (int i = 0; i < colors.length; i++) {
				inputStream = AudioSystem.getAudioInputStream(new File("sounds/" + i + ".wav"));
				colors[i] = AudioSystem.getClip();
				colors[i].open(inputStream);
			}
			victory = AudioSystem.getClip();
			inputStream = AudioSystem.getAudioInputStream(new File("sounds/victory.wav"));
			victory.open(inputStream);

			defeat = AudioSystem.getClip();
			inputStream = AudioSystem.getAudioInputStream(new File("sounds/defeat.wav"));
			defeat.open(inputStream);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	public void playColorSound(int colorNum) {
		colors[colorNum].setFramePosition(0);
		colors[colorNum].start();
	}

	public void playVictory() {
		victory.setFramePosition(0);
		victory.start();
	}

	public void playDeafet() {
		defeat.setFramePosition(0);
		defeat.start();
	}

}
