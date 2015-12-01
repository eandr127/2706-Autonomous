package ca.team2706.frc.autonomous.commands;

import ca.team2706.frc.autonomous.AutoCommand;
import ca.team2706.frc.robot.Subsystems;
import ca.team2706.frc.utils.Constants;
import ca.team2706.frc.utils.SimLib;

public class DriveStraightAndTurn implements AutoCommand {

	private final double x;
	private final double y;
	
	private boolean stage2 = false;
	
	public DriveStraightAndTurn(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	@Override
	public void initialize() {
		//Calculate angle to turn so that the robot can drive 
		//along the hypotenuse of the triangle created with x and y
		int angle = (int) Math.toDegrees(Math.atan2(x, y));

		Subsystems.gyroPID.setDesiredValue(angle);
		Subsystems.gyroSensor.reset(0);
		// Reset the gyro PID to a reasonable state.
		Subsystems.gyroPID.resetErrorSum();
		Subsystems.gyroPID.resetPreviousVal();
		// Used to make sure that the PID doesn't bail out as done
		// right away (we know the gyro angle is zero from the above
		// reset).
		Subsystems.gyroPID.calcPID(0);
	}

	@Override
	public boolean tick() {
		//Rotating phase 
		if (!Subsystems.gyroPID.isDone()) {
			System.out.println("gyro.getAngle() = " + Subsystems.gyroSensor.getAngle());
			
			//Use PID to calculate how fast to turn the robot
			double driveVal = Subsystems.gyroPID.calcPID(Subsystems.gyroSensor.getAngle());
			
			//Limit the speed for safety (at least before everything has been tested)
			double limitVal = SimLib.limitValue(driveVal, Constants.getConstantAsDouble(Constants.GYRO_PID_MAX));
			System.out.println("limitVal = " + limitVal);
			
			//Rotate
			Subsystems.robotDrive.setLeftRightMotorOutputs(limitVal, -limitVal);

			return true;
		}
		//Driving phase
		else if (!Subsystems.encoderPID.isDone()) {
			if(!stage2) {
				stage2Init();
				stage2 = true;
			}
			
			//Calculate how fast to drive with PID
			double driveVal = Subsystems.encoderPID
					.calcPID((Subsystems.leftDriveEncoder.getDistance() + Subsystems.rightDriveEncoder
							.getDistance()) / 2.0);

			//Limit value again
			double limitVal = SimLib.limitValue(driveVal, Constants.getConstantAsDouble(Constants.ENCODER_PID_MAX));

			//Drive in a straight line
			Subsystems.robotDrive.setLeftRightMotorOutputs(limitVal, limitVal);
			return true;
		}
		return false;
	}

	@Override
	public void cleanup() {
		Subsystems.robotDrive.drive(0.0, 0.0);
	}
	
	private void stage2Init() {
		double distance = Math.sqrt((x * x) + (y * y));
		// Reset the encoders (encoder.get(Distance|)() == 0)
		Subsystems.leftDriveEncoder.reset();
		Subsystems.rightDriveEncoder.reset();
		// Set up the desired number of units.
		Subsystems.encoderPID.setDesiredValue(distance);
		// Reset the encoder PID to a reasonable state.
		Subsystems.encoderPID.resetErrorSum();
		Subsystems.encoderPID.resetPreviousVal();
		// Used to make sure that the PID doesn't bail out as done
		// right away (we know both the distances are zero from the
		// above reset).
		Subsystems.encoderPID.calcPID(0);
	}

}
