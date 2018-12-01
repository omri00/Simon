package logics;

import java.util.ArrayList;

/**
 * This class does all of the logic and calculations of the Simon game.
 * 
 * @author omri
 */
public class Calculator implements Runnable {
	public static final int MAX_TURNS = 20;
	public static final int VICTORY_NUM = 2;
	public static final int DEFEAT_NUM = 0;
	public static final int CURRECT_NUM = 1;
	public static final int END_NUM = -1;
	private int buttonNum;
	private ArrayList<Integer> lightOrder;
	private int placeInTurn;
	private boolean printing;
	private BoardInterface board;
	private Difficulty difficulty;

	/**
	 * This determines the duration of time each light light up when printing.
	 */
	public enum Difficulty {
		EASY(750), MEDIUM(500), HARD(250);

		private final int printSleepTime;

		private Difficulty(int printSleepTime) {
			this.printSleepTime = printSleepTime;
		}

		public int getPrintSleepTime() {
			return printSleepTime;
		}
	}

	/**
	 * Construct a new Calculator using the number of light, the difficulty and the
	 * {@link BoardInterface} that shows the game.
	 * 
	 * @param lightNum
	 *            the number of lights.
	 * @param board
	 *            the {@link BoardInterface} the this show the result on.
	 * @param difficulty
	 *            the {@link Difficulty} of the game.
	 */
	public Calculator(int lightNum, BoardInterface board, Difficulty difficulty) {
		this.buttonNum = lightNum;
		this.board = board;
		this.difficulty = difficulty;
		lightOrder = new ArrayList<>();
		chooseNextLight();
		placeInTurn = 0;
		printing = true;
	}

	/**
	 * @return the score which is the size of the order -1.
	 */
	public int getScore() {
		return lightOrder.size() - 1;
	}

	/**
	 * Return the next light in the order, if the order has ended return END_NUM and
	 * make printing false.
	 * 
	 * @return the next light in the order.
	 */
	public int getNextLight() {
		if (lightOrder.size() == placeInTurn) {
			placeInTurn = 0;
			printing = false;
			return END_NUM;
		}
		placeInTurn++;
		return lightOrder.get(placeInTurn - 1);
	}

	/**
	 * Checks if the light that was given match the next light in the order. If the
	 * order has ended picks a new light and make printing true.
	 * 
	 * @param light
	 *            The light the player has pressed.
	 * @return DEFEAT_NUM if it is wrong, CURRECT_NUM if it isn't the last light in
	 *         the order and if it's correct, END_NUM if its the last one and
	 *         VICTORY_NUm if the player won.
	 */
	public int checkNextLight(int light) {
		int returnValue = DEFEAT_NUM;
		if (lightOrder.get(placeInTurn) == light) { // The player was correct.
			placeInTurn++;
			returnValue = CURRECT_NUM;
		}
		if (lightOrder.size() == placeInTurn) { // The player won.
			if (lightOrder.size() == MAX_TURNS) {
				return returnValue = VICTORY_NUM;
			}
			placeInTurn = 0; // End of order.
			printing = true;
			chooseNextLight();
			returnValue = END_NUM;
		}
		return returnValue;
	}

	/**
	 * Random a new num and adds it to the end of the order.
	 */
	public void chooseNextLight() {
		lightOrder.add((int) (Math.random() * buttonNum));
	}

	/**
	 * @return printings.
	 */
	public boolean isPrinting() {
		return printing;
	}

	/**
	 * reset the game.
	 */
	public void resetGame() {
		lightOrder = new ArrayList<Integer>();
		printing = true;
		placeInTurn = 0;
		chooseNextLight();
	}

	/**
	 * Start the game.
	 */
	@Override
	public void run() {
		resetGame();
		boolean gameRunning = true;
		board.setScore(0);
		while (gameRunning) {
			int nextLight;
			if (isPrinting()) { // Print the next light.
				nextLight = getNextLight();
				if (nextLight != END_NUM) {
					board.activeLight(nextLight);
				}
				sleep(difficulty.printSleepTime);
			} else {
				while (!board.isPressed()) { // Wait for the player to press something.
					sleep(1);
				}
				int action;
				nextLight = board.getPressedLight();
				board.activeLight(nextLight);
				action = checkNextLight(nextLight);
				if (action == Calculator.VICTORY_NUM) {
					board.victory();
					gameRunning = false;
				} else if (action == Calculator.DEFEAT_NUM) {
					board.defeat();
					gameRunning = false;
				}
				sleep(250);
			}
			board.setScore(getScore());
			board.deactiveAll();
			sleep(250);
		}
		board.activeAll(); // blinking to show the end of the game.
		sleep(250);
		board.deactiveAll();
		sleep(250);
		board.activeAll();
		sleep(250);
		board.deactiveAll();
	}

	/**
	 * sleeps for the wanted time.
	 * 
	 * @param time
	 *            in milliseconds.
	 */
	private void sleep(int time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			System.out.println("Sleep Error");
		}
	}

}
