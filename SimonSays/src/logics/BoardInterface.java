package logics;

public interface BoardInterface {
	
	public void activeLight(int light);
	public void deactiveAll();
	public void activeAll();
	public void setScore(int score);
	public boolean isPressed();
	public int getPressedColor();
	public void victory();
	public void defeat();

}
