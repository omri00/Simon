package grafics;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import logics.Calculator.Difficulty;

public class Menu extends JPanel {
	JFrame frame;
	JRadioButton easy;
	JRadioButton medium;
	JRadioButton hard;

	public Menu() {
		JButton playButton = new JButton("START!");
		JButton exitButton = new JButton("Exit Game");
		JCheckBox invertedButton = new JCheckBox("Inverted");
		JPanel settingsPanel = new JPanel();
		easy = new JRadioButton("easy", false);
		medium = new JRadioButton("medium", true);
		hard = new JRadioButton("hard", false);
		ButtonGroup difficultyGroup = new ButtonGroup();
		difficultyGroup.add(easy);
		difficultyGroup.add(medium);
		difficultyGroup.add(hard);
		settingsPanel.add(invertedButton);
		settingsPanel.add(easy);
		settingsPanel.add(medium);
		settingsPanel.add(hard);
		playButton.addActionListener((ActionEvent e) -> {
			frame.add(new GraphicBoard(invertedButton.isSelected(), getDifficulty()));
			frame.remove(this);
		});
		exitButton.addActionListener((ActionEvent e) -> {
			frame.dispose();
		});
		frame = new JFrame("Simon");
		frame.setBounds(0, 0, 500, 500);
		frame.setMinimumSize(new Dimension(300, 300));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		this.setLayout(new GridLayout(3, 3));
		this.add(playButton);
		this.add(settingsPanel);
		this.add(exitButton);
		frame.add(this);
		frame.add(new GraphicBoard(false, Difficulty.EASY));
	}

	public Difficulty getDifficulty() {
		if (easy.isSelected())
			return Difficulty.EASY;
		else if (hard.isSelected())
			return Difficulty.HARD;
		return Difficulty.MEDIUM;
	}

	public static void main(String[] args) {
		new Menu();
	}

}
