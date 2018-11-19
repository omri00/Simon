package logics;
import java.util.ArrayList;

public class Calculator implements Runnable {
	public static final int MAX_TURNS = 20;
	public static final int VICTORY_NUM = 2;
	public static final int DEFEAT_NUM = 0;
	public static final int CURRECT_NUM = 1;
	public static final int END_NUM = -1;
	private int buttonNum;
	private ArrayList<Integer> colorOrder;
	private int placeInTurn;
	private boolean printing;
	private BoardInterface board;

	public Calculator(int buttonNum, BoardInterface board) {
		this.buttonNum = buttonNum;
		this.board = board;
		colorOrder = new ArrayList<>();
		chooseNextColor();
		placeInTurn = 0;
		printing = true;
	}

	public int getScore() {
		return colorOrder.size() - 1;
	}

	public int getNextColor() {
		if (colorOrder.size() == placeInTurn) {
			placeInTurn = 0;
			printing = false;
			return END_NUM;
		}
		placeInTurn++;
		return colorOrder.get(placeInTurn - 1);
	}

	public int checkNextColor(int color) {
		int returnValue = DEFEAT_NUM;
		if (colorOrder.get(placeInTurn) == color) {
			placeInTurn++;
			returnValue = CURRECT_NUM;
		}
		if (colorOrder.size() == placeInTurn) {
			if (colorOrder.size() == MAX_TURNS) {
				returnValue = VICTORY_NUM;
			}
			placeInTurn = 0;
			printing = true;
			chooseNextColor();
			returnValue = END_NUM;
		}
		if (returnValue == DEFEAT_NUM) {
		}
		return returnValue;
	}

	public void chooseNextColor() {
		colorOrder.add((int) (Math.random() * buttonNum));
	}

	public boolean isPrinting() {
		return printing;
	}

	public void resetGame() {
		colorOrder = new ArrayList<Integer>();
		printing = true;
		placeInTurn = 0;
		chooseNextColor();
	}

	@Override
	public void run() {
		resetGame();
		boolean gameRunning = true;
		board.setScore(0);
		while (gameRunning) {
			int nextColor;
			if (isPrinting()) {
				nextColor = getNextColor();
				if (nextColor != END_NUM) {
					board.activeLight(nextColor);
				}
				sleep(500);
			}
			else {
				while (!board.isPressed()) {
					sleep(1);
				}
				int action;
				nextColor = board.getPressedColor();
				board.activeLight(nextColor);
				action = checkNextColor(nextColor);
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
		board.activeAll();
		sleep(250);
		board.deactiveAll();
		sleep(250);
		board.activeAll();
		sleep(250);
		board.deactiveAll();
	}

	private void sleep(int time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			System.out.println("Sleep Error");
		}
	}

}
