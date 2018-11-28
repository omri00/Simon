package electronic;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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
		SmartDashboard.putNumber("Score", score);
	}

	@Override
	public boolean isPressed() {
		return getPressedLight() != -1;
	}

	@Override
	public int getPressedLight() {
		for (int i = 0; i < buttons.length; i++) {
			if (buttons[i].get())
				return i;
		}
		return -1;
	}

	@Override
	public void victory() {		
		SmartDashboard.putString("Game state:", "Victory");
	}

	@Override
	public void defeat() {
		SmartDashboard.putString("Game state:", "Defeat");
	}

}
