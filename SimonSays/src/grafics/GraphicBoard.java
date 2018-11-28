package grafics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

import logics.BoardInterface;
import logics.Calculator;
import logics.Calculator.Difficulty;

public class GraphicBoard extends JPanel implements BoardInterface {

	/**
	 * 
	 */
	ImagesHandler imagesHandler;
	SoundHandler soundHandler;
	Calculator calculator;
	boolean pressed = false;
	int score = 0;
	int pressedColor = ImagesHandler.NON_ACTIVE;
	Thread gameThread;

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

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		imagesHandler.draw(g);
		g.drawString("" + score, imagesHandler.size, imagesHandler.size);
	}

	@Override
	public void activeLight(int light) {
		imagesHandler.selectImage(light);
		soundHandler.playColorSound(light);
		repaint();
	}

	@Override
	public void deactiveAll() {
		imagesHandler.deactiveAll();
		repaint();
	}

	@Override
	public void activeAll() {
		imagesHandler.activeAll();
		repaint();
	}

	@Override
	public void setScore(int score) {
		this.score = score;
		repaint();
	}

	@Override
	public boolean isPressed() {
		if (pressed) {
			pressed = false;
			return true;
		}
		return false;
	}

	@Override
	public int getPressedLight() {
		return pressedColor;
	}

	@Override
	public void victory() {
		soundHandler.playVictory();
	}

	@Override
	public void defeat() {
		soundHandler.playDeafet();
	}
}
