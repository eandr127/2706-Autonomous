package ca.team2706.frc.controls;

import org.strongback.components.Switch;
import org.strongback.components.ui.ContinuousRange;
import org.strongback.components.ui.FlightStick;
import org.strongback.mock.Mock;

public class MockFlightStick extends MockInputDevice implements FlightStick {

	private ContinuousRange pitch;
	private ContinuousRange yaw;
	private ContinuousRange roll;
	
	private double pitchVal;
	private double yawVal;
	private double rollVal;
	
	private ContinuousRange throttle;
	
	private double throttleVal;
	
	private Switch trigger;
	private Switch thumb;
	
	public MockFlightStick() {
		super();
		
		pitch = createContinuousRange(pitchVal);
		yaw = createContinuousRange(yawVal);
		roll = createContinuousRange(rollVal);
		
		throttle = createContinuousRange(throttleVal);
		
		trigger = Mock.notTriggeredSwitch();
		thumb = Mock.notTriggeredSwitch();
	}

	@Override
	public ContinuousRange getPitch() {
		return pitch;
	}
	
	public void setPitch(ContinuousRange pitch) {
		pitchVal = pitch.read();
	}

	@Override
	public ContinuousRange getYaw() {
		return yaw;
	}
	
	public void setYaw(ContinuousRange yaw) {
		yawVal = yaw.read();
	}
	
	@Override
	public ContinuousRange getRoll() {
		return roll;
	}
	
	public void setRoll(ContinuousRange roll) {
		rollVal = roll.read();
	}	

	@Override
	public ContinuousRange getThrottle() {
		return throttle;
	}
	
	public void setThrottle(ContinuousRange throttle) {
		this.throttleVal = throttle.read();
	}
	
	@Override
	public Switch getTrigger() {
		return trigger;
	}

	public void setTrigger(Switch trigger) {
		this.trigger = trigger;
	}
	
	@Override
	public Switch getThumb() {
		return thumb;
	}

	public void setThumb(Switch thumb) {
		this.thumb = thumb;
	}
}
