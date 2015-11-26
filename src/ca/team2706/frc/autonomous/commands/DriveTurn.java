package ca.team2706.frc.autonomous.commands;

import org.strongback.command.Command;

import ca.team2706.frc.robot.Subsystems;
import ca.team2706.frc.utils.SimLib;

public class DriveTurn extends Command {

	private final int angle;
	
	public DriveTurn(int angle) {
		super();
		
		this.angle = angle;
	}
	
	@Override
	public void initialize() {
		Subsystems.gyroPID.setDesiredValue(angle);
		Subsystems.gyroSensor.zero();
		// Reset the gyro PID to a reasonable state.
		Subsystems.gyroPID.resetErrorSum();
		Subsystems.gyroPID.resetPreviousVal();
		// Used to make sure that the PID doesn't bail out as done
		// right away (we know the gyro angle is zero from the above
		// reset).
		Subsystems.gyroPID.calcPID(0);
		System.out.println("DriveTurn Init:" + angle);
	}
	
	@Override
	public boolean execute() {
		if (!Subsystems.gyroPID.isDone()) {
			// Angle needs to be positive
			// Calculate how fast to turn using PID
			double driveVal = Subsystems.gyroPID.calcPID(Subsystems.gyroSensor.getAngle());
			
			// Rotate at a safe speed
			double limitVal = SimLib.limitValue(driveVal, 0.9);
			System.out.println("gyro.getAngle() = " + Subsystems.gyroSensor.getAngle()+",limitVal = " + limitVal);
			
			// Rotate robot
			Subsystems.robotDrive.tank(limitVal, -limitVal);
			System.out.println("Right: " + Subsystems.rightDrive.getSpeed() + " Left: " + Subsystems.leftDrive.getSpeed());
			return false;
		}
		return true;
		
	}

	@Override
	public void interrupted() {
		end();
	}
	
	@Override
	public void end() {
		System.out.println("DriveTurn Cleanup");
		
		//Stop
		Subsystems.robotDrive.stop();
	}
	
}
