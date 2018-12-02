package graphics;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;

import logics.Calculator.Difficulty;

/**
 * This class is the menu that let you pick difficulty, set to inverted mode,
 * see the score board, read the instructions or exit the game.
 * 
 * @author omri
 *
 */
public class Menu extends JPanel {
	JFrame frame;
	JRadioButton easy;
	JRadioButton medium;
	JRadioButton hard;
	JButton playButton;
	JButton exitButton;
	JButton helpButton;
	JButton scoreButton;
	JCheckBox invertedButton;
	ButtonGroup difficultyGroup;
	JPanel settingsPanel;

	/**
	 * Makes a new menu.
	 */
	public Menu() {
		initButtons();
		initSettings();

		this.setLayout(new GridLayout(5, 1));
		this.add(playButton);
		this.add(settingsPanel);
		this.add(scoreButton);
		this.add(helpButton);
		this.add(exitButton);
		initFrame();

		playButton.addActionListener((ActionEvent e) -> {
			GraphicBoard board = new GraphicBoard(invertedButton.isSelected(), getDifficulty());
			frame.add(board, BorderLayout.CENTER);
			frame.remove(this);
			frame.setVisible(true);
		});
		helpButton.addActionListener(this::openHelpFrame);
		exitButton.addActionListener((ActionEvent e) -> {
			frame.dispose();
		});
	}

	/**
	 * Opens a new frame with the content of the help file.
	 */
	private void openHelpFrame(ActionEvent e) {
		JFrame helpFrame = new JFrame("Help");
		helpFrame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		helpFrame.setVisible(true);
		JTextArea tarea = new JTextArea();
		File file = new File("help.txt");
		helpFrame.add(tarea);
		try {
			BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			tarea.read(input, null);
		} catch (Exception exc) {
			exc.printStackTrace();
		}
	}

	/**
	 * Make the new frame and add the menu to it.
	 */
	private void initFrame() {
		frame = new JFrame("Simon");
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		frame.setBounds(0, 0, 500, 500);
		frame.setMinimumSize(new Dimension(300, 300));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.add(this);
	}

	/**
	 * make all of the button.
	 */
	private void initButtons() {
		playButton = new JButton("START!");
		exitButton = new JButton("Exit Game");
		helpButton = new JButton("Help");
		scoreButton = new JButton("Score Board");
		invertedButton = new JCheckBox("Inverted");
		easy = new JRadioButton("easy", false);
		medium = new JRadioButton("medium", true);
		hard = new JRadioButton("hard", false);

		difficultyGroup = new ButtonGroup();

		difficultyGroup.add(easy);
		difficultyGroup.add(medium);
		difficultyGroup.add(hard);
	}

	/**
	 * Add the setting related button to a new panel.
	 */
	private void initSettings() {
		settingsPanel = new JPanel();
		settingsPanel.add(invertedButton);
		settingsPanel.add(easy);
		settingsPanel.add(medium);
		settingsPanel.add(hard);
	}

	/**
	 * @return the difficulty.
	 */
	public Difficulty getDifficulty() {
		if (easy.isSelected())
			return Difficulty.EASY;
		else if (hard.isSelected())
			return Difficulty.HARD;
		return Difficulty.MEDIUM;
	}

	/**
	 * Start the game.
	 */

	public static void main(String[] args) {
		new Menu();
	}

}
