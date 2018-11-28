package logics;

/**
 * This interface represent a generic board for the Simon game.
 * 
 * @author omri
 */
public interface BoardInterface {

	/**
	 * Activate 1 specific light.
	 * 
	 * @param light
	 *            the light to activate.
	 */
	public void activeLight(int light);

	/**
	 * Deactivate all of the lights.
	 */
	public void deactiveAll();

	/**
	 * Activate all of the lights.
	 */
	public void activeAll();

	/**
	 * Sets the score to the given number.
	 * 
	 * @param score
	 *            the new score.
	 */
	public void setScore(int score);

	/**
	 * @return if the player has pressed on any button.
	 */
	public boolean isPressed();

	/**
	 * @return the number of light that was pressed by the player.
	 */
	public int getPressedLight();

	/**
	 * This runs when the player wins.
	 */
	public void victory();

	/**
	 * This runs when the player loses.
	 */
	public void defeat();

}
