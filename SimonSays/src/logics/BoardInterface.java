package logics;

public interface BoardInterface {
	
	public void activeColor(int color);
	public void deactiveAll();
	public void activeAll();
	public void setScore(int score);
	public boolean isPressed();
	public int getPressedColor();
	public void victory();
	public void defeat();

}
