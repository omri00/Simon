package graphics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

import logics.BoardInterface;
import logics.Calculator;
import logics.Calculator.Difficulty;

/**
 * This {@link JPanel} act as a {@link BoardInterface} that uses swing for
 * graphic gui.
 * 
 * @author omri
 *
 */
public class GraphicBoard extends JPanel implements BoardInterface {
	ImagesHandler imagesHandler;
	SoundHandler soundHandler;
	Calculator calculator;
	boolean pressed = false;
	int score = 0;
	int pressedColor = ImagesHandler.NON_ACTIVE;
	Thread gameThread;

	/**
	 * Makes a new GraphicBaord.
	 * 
	 * @param inverted
	 *            If the game is in inverted mode.
	 * @param difficulty
	 *            The {@link Difficulty} of the game.
	 */
	public GraphicBoard(boolean inverted, Difficulty difficulty) {
		soundHandler = new SoundHandler();
		imagesHandler = new ImagesHandler(inverted);
		calculator = new Calculator(4, this, difficulty);
		gameThread = new Thread(calculator, "game");
		addMouseListener(new MouseAdapter() {

			public void mousePressed(MouseEvent e) {
				if (!gameThread.isAlive()) {
					pressed = false;
					gameThread.interrupt();
					gameThread = new Thread(calculator, "game");
					gameThread.start();
				} else if (!calculator.isPrinting()) {
					pressedColor = imagesHandler.checkColor(e.getX(), e.getY());
					if (pressedColor != ImagesHandler.NON_ACTIVE) {
						pressed = true;
					}
				}
			}
		});

		setBackground(Color.gray);

	}

	/**
	 * Draws everything on the screen.
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		imagesHandler.draw(g);
		g.drawString("" + score, imagesHandler.size, imagesHandler.size);
	}

	/**
	 * active the image and sound.
	 */
	@Override
	public void activeLight(int light) {
		imagesHandler.activeColor(light);
		soundHandler.playColorSound(light);
		repaint();
	}

	/**
	 * deactivate images.
	 */
	@Override
	public void deactiveAll() {
		imagesHandler.deactiveAll();
		repaint();
	}

	/**
	 * Activate images.
	 */
	@Override
	public void activeAll() {
		imagesHandler.activeAll();
		repaint();
	}

	/**
	 * Set the score.
	 */
	@Override
	public void setScore(int score) {
		this.score = score;
		repaint();
	}

	/**
	 * Return if the player has pressed on a light.
	 */
	@Override
	public boolean isPressed() {
		if (pressed) {
			pressed = false;
			return true;
		}
		return false;
	}

	/**
	 * Returns which light was pressed.
	 */
	@Override
	public int getPressedLight() {
		return pressedColor;
	}

	/**
	 * Plays victory sound.
	 */
	@Override
	public void victory() {
		soundHandler.playVictory();
	}

	/**
	 * Plays defeat sound.
	 */
	@Override
	public void defeat() {
		soundHandler.playDefeat();
	}
}
