package ca.team2706.frc.autonomous.commands;

import ca.team2706.frc.autonomous.AutoCommand;
import ca.team2706.frc.robot.Subsystems;
import ca.team2706.frc.utils.Constants;
import ca.team2706.frc.utils.SimLib;

public class DriveStraight implements AutoCommand {
	
	private final double distance;

	public DriveStraight(double distance) {
		this.distance = distance;
	}
	
	@Override
	public void initialize() {
		// Reset the encoders (encoder.get(Distance|)() == 0)
		Subsystems.leftDriveEncoder.zero();
		Subsystems.rightDriveEncoder.zero();
		// Set up the desired number of units.
		Subsystems.encoderPID.setDesiredValue(distance);
		// Reset the encoder PID to a reasonable state.
		Subsystems.encoderPID.resetErrorSum();
		Subsystems.encoderPID.resetPreviousVal();
		// Used to make sure that the PID doesn't bail out as done
		// right away (we know both the distances are zero from the
		// above reset).
		Subsystems.encoderPID.calcPID(0);
		System.out.println("DriveStraight Init");
	}
	
	@Override
	public boolean tick() {
		if (!Subsystems.encoderPID.isDone()) {
			// Calculate the speed to drive at using PID
			double driveVal = Subsystems.encoderPID.calcPID((Subsystems.leftDriveEncoder.getAngle() + Subsystems.rightDriveEncoder.getAngle()) / 2.0);
			// Limit the value so that the robot doesn't hit anything too hard
			double limitVal = SimLib.limitValue(driveVal, Constants.getConstantAsDouble(Constants.ENCODER_PID_MAX));

			// Drive straight at speed that was previously calculated
			Subsystems.robotDrive.tank(limitVal, limitVal);
			System.out.println("LEV:" + Subsystems.leftDriveEncoder.getAngle() + ",REV:" + Subsystems.rightDriveEncoder.getAngle()+
					",LED:" + Subsystems.leftDriveEncoder.getAngle() + ",RED:" + Subsystems.rightDriveEncoder.getAngle()+",DV:"+driveVal);
			return true;
		}
		return false;
		
	}

	@Override
	public void cleanup() {
		//Stop driving
		Subsystems.robotDrive.stop();	
	}

}
