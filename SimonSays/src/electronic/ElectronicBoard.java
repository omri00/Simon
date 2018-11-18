package electronic;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;
import logics.BoardInterface;

public class ElectronicBoard implements BoardInterface{
	private Solenoid[] lights = new Solenoid[RobotMap.SIMON.LIGHTS.length];
	private DigitalInput[] buttons = new DigitalInput[RobotMap.SIMON.BUTTONS.length];
	
	public ElectronicBoard() {
		for (int i = 0; i < lights.length; i++) {
			lights[i] = new Solenoid(RobotMap.SIMON.LIGHTS[i]);
			buttons[i] = new DigitalInput(RobotMap.SIMON.BUTTONS[i]);
		}
	}

	@Override
	public void activeLight(int light) {
		lights[light].set(true);
	}

	@Override
	public void deactiveAll() {
		for (int i = 0; i < lights.length; i++) {
			lights[i].set(false);
		}
	}

	@Override
	public void activeAll() {
		for (int i = 0; i < lights.length; i++) {
			lights[i].set(true);
		}
	}

	@Override
	public void setScore(int score) {
	}

	@Override
	public boolean isPressed() {
		return getPressedColor() != -1;
	}

	@Override
	public int getPressedColor() {
		for (int i = 0; i < buttons.length; i++) {
			if (buttons[i].get())
				return i;
		}
		return -1;
	}

	@Override
	public void victory() {		
	}

	@Override
	public void defeat() {
	}

}
