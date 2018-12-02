package graphics;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 * This class handle the sound of the game.
 * 
 * @author omri
 */
public class SoundHandler {

	private Clip[] colors = new Clip[4];
	private Clip victory;
	private Clip defeat;

	/**
	 * Makes a new SoundHanler.
	 */
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

	/**
	 * Plays the sound of a wanted color.
	 * 
	 * @param colorNum
	 *            the wanted color.
	 */
	public void playColorSound(int colorNum) {
		colors[colorNum].stop();
		colors[colorNum].setFramePosition(0);
		colors[colorNum].start();
	}

	/**
	 * Plays the victory sound.
	 */
	public void playVictory() {
		victory.setFramePosition(0);
		victory.start();
	}

	/**
	 * Plays the defeat sound.
	 */
	public void playDefeat() {
		defeat.setFramePosition(0);
		defeat.start();
	}

}
