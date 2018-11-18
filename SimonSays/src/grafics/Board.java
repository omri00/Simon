package grafics;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

import logics.BoardInterface;
import logics.Calculator;

public class Board extends JPanel implements BoardInterface {

	ImagesHandler imagesHandler;
	SoundHandler soundHandler;
	Calculator calculator;
	boolean pressed = false;
	int score = 0;
	int pressedColor = ImagesHandler.NON_ACTIVE;
	Thread gameThread;
	JFrame frame;

	public Board(boolean inverted) {
		soundHandler = new SoundHandler();
		imagesHandler = new ImagesHandler(inverted);
		calculator = new Calculator(4, this);
		gameThread = new Thread(calculator, "game");
		frame = new JFrame("Simon");
		frame.setBounds(0, 0, 500, 500);
		frame.setMinimumSize(new Dimension(300, 300));
		frame.add(this, BorderLayout.CENTER);
		addMouseListener(new MouseAdapter() {

			public void mousePressed(MouseEvent e) {
				if (!gameThread.isAlive()) {
					gameThread.start();
				} else if(!calculator.isPrinting()){
					pressedColor = imagesHandler.checkColor(e.getX(), e.getY());
					if (pressedColor != ImagesHandler.NON_ACTIVE) {
						pressed = true;
					}
				}
			}
		});

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBackground(Color.gray);
		frame.setVisible(true);

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
	public int getPressedColor() {
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

	public static void main(String[] args) {
		Board board = new Board(false);

	}
}
