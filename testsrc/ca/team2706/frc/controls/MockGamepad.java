package ca.team2706.frc.controls;

import org.strongback.components.Switch;
import org.strongback.components.ui.ContinuousRange;
import org.strongback.components.ui.Gamepad;

public class MockGamepad extends MockInputDevice implements Gamepad {
	
	private ContinuousRange leftX;
	private ContinuousRange leftY;
	
	private ContinuousRange rightX;
	private ContinuousRange rightY;
	
	private ContinuousRange leftTrigger;
	private ContinuousRange rightTrigger;
	
	private double leftXVal;
	private double leftYVal;
	
	private double rightXVal;
	private double rightYVal;
	
	private double leftTriggerVal;
	private double rightTriggerVal;
	
	public MockGamepad() {
		super();
		
		leftX = createContinuousRange(leftXVal);
		leftY = createContinuousRange(leftYVal);
		
		rightX = createContinuousRange(rightXVal);
		rightY = createContinuousRange(rightYVal);
		
		leftTrigger = createContinuousRange(leftTriggerVal);
		rightTrigger = createContinuousRange(rightTriggerVal);
		
	}

	@Override
	public ContinuousRange getLeftX() {
		return leftX;
	}
	
	public void setLeftX(ContinuousRange leftX) {
		leftXVal = leftX.read();
	}

	@Override
	public ContinuousRange getLeftY() {
		return leftY;
	}
	
	public void setLeftY(ContinuousRange leftY) {
		leftYVal = leftY.read();
	}

	@Override
	public ContinuousRange getRightX() {
		return rightX;
	}
	
	public void setRightX(ContinuousRange rightX) {
		rightXVal = rightX.read();
	}
	
	@Override
	public ContinuousRange getRightY() {
		return rightY;
	}
	
	public void setRightY(ContinuousRange rightY) {
		rightYVal = rightY.read();
	}

	@Override
	public ContinuousRange getLeftTrigger() {
		return leftTrigger;
	}
	
	public void setLeftTrigger(ContinuousRange leftTrigger) {
		leftTriggerVal = leftTrigger.read();
	}

	@Override
	public ContinuousRange getRightTrigger() {
		return rightTrigger;
	}
	
	public void setRightTrigger(ContinuousRange rightTrigger) {
		rightTriggerVal = rightTrigger.read();
	}

	@Override
	public Switch getLeftBumper() {
		return buttons.get(0);
	}

	@Override
	public Switch getRightBumper() {
		return buttons.get(1);
	}

	@Override
	public Switch getA() {
		return buttons.get(2);
	}

	@Override
	public Switch getB() {
		return buttons.get(3);
	}

	@Override
	public Switch getX() {
		return buttons.get(4);
	}

	@Override
	public Switch getY() {
		return buttons.get(5);
	}

	@Override
	public Switch getStart() {
		return buttons.get(6);
	}

	@Override
	public Switch getSelect() {
		return buttons.get(7);
	}

	@Override
	public Switch getLeftStick() {
		return buttons.get(8);
	}

	@Override
	public Switch getRightStick() {
		return buttons.get(9);
	}
}
