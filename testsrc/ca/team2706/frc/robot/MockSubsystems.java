package ca.team2706.frc.robot;

import org.strongback.components.Switch;
import org.strongback.drive.TankDrive;
import org.strongback.mock.Mock;

import ca.team2706.frc.controls.MockFlightStick;
import ca.team2706.frc.controls.MockGamepad;
import ca.team2706.frc.controls.MockPowerPanel;
import ca.team2706.frc.utils.Constants;

public class MockSubsystems extends Subsystems {
	
	/**
	 * Initialize all of the subsystems, assumes that the constants file has been read already
	 */
	public static void initialize() {
		// Motors
		rightDrive = Mock.runningMotor(0.0);
		leftDrive = Mock.runningMotor(0.0);
		
		// Drive
		robotDrive = new TankDrive(leftDrive, rightDrive);
		
		// Encoders
		rightDriveEncoder = Mock.angleSensor();
		leftDriveEncoder = Mock.angleSensor();

		// Power Panel
		powerPanel = new MockPowerPanel(1);
		
		// Sensors
		gyroSensor = Mock.gyroscope();
		
		// USB
		driveJoystick = new MockFlightStick();
		controlGamepad = new MockGamepad();
		
		initPID();

		inputs = new Switch[Constants.getConstantAsInt(Constants.NUM_AUTO_SELECT)];

		for(int i = 0; i < Constants.getConstantAsInt(Constants.NUM_AUTO_SELECT); ++i) {
			inputs[i] = Mock.notTriggeredSwitch();
		}
	}
}
