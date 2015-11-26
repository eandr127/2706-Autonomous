package ca.team2706.frc.autonomous.commands;

import org.junit.Assert;
import org.junit.Test;
import org.strongback.command.Command;
import org.strongback.command.CommandTester;
import org.strongback.mock.MockAngleSensor;

import ca.team2706.frc.robot.MockSubsystems;
import ca.team2706.frc.robot.Subsystems;

public class DriveStraightTest {

	@Test
	public void shouldDriveForward5Feet() {
		// Reset subsystem with mock components
		MockSubsystems.initialize();
		
		// Create Command to run
		Command command = new DriveStraight(5);
		
		double speed;
		
		// Create a CommandTester to run the Command
		CommandTester runner = new CommandTester(command);
		
		long startTime = System.currentTimeMillis();
		
		// Loop while the command is not ready to be terminated
		while(!runner.step(System.currentTimeMillis() - startTime)) {
			speed = Subsystems.leftDrive.getSpeed();
			// The encoder distance should be affected by the motor speed
			((MockAngleSensor)Subsystems.rightDriveEncoder).setAngle(Subsystems.rightDriveEncoder.getAngle() + speed);
			((MockAngleSensor)Subsystems.leftDriveEncoder).setAngle(Subsystems.leftDriveEncoder.getAngle() + speed);
		}
		// The Command is done, so the CommandTester should be cancelled
		runner.cancel();
		
		// Make sure the encoder values are about were they should be
		Assert.assertTrue(Math.round(Subsystems.leftDriveEncoder.getAngle()) == 5.0 && Math.round(Subsystems.rightDriveEncoder.getAngle()) == 5.0);
		// Make sure that the PID loop actually finished
		Assert.assertTrue(Subsystems.encoderPID.isDone());
		
		// Make sure the motors are stopped
		Assert.assertTrue(Subsystems.leftDrive.getSpeed() == 0.0 && Subsystems.leftDrive.getSpeed() == 0.0);
    }
	
	@Test
	public void shouldDriveBackwards5Feet() {
		MockSubsystems.initialize();
		
		// Only difference from the first method
		Command command = new DriveStraight(-5);
		double speed;
		
		CommandTester runner = new CommandTester(command);
		
		long startTime = System.currentTimeMillis();
		
		while(!runner.step(System.currentTimeMillis() - startTime)) {
			speed = Subsystems.leftDrive.getSpeed();
			((MockAngleSensor)Subsystems.rightDriveEncoder).setAngle(Subsystems.rightDriveEncoder.getAngle() + speed);
			((MockAngleSensor)Subsystems.leftDriveEncoder).setAngle(Subsystems.leftDriveEncoder.getAngle() + speed);
		}
		runner.cancel();
		
		Assert.assertTrue(Math.round(Subsystems.leftDriveEncoder.getAngle()) == -5.0 && Math.round(Subsystems.rightDriveEncoder.getAngle()) == -5.0);
		Assert.assertTrue(Subsystems.encoderPID.isDone());
		
		Assert.assertTrue(Subsystems.leftDrive.getSpeed() == 0.0 && Subsystems.leftDrive.getSpeed() == 0.0);
    }
}
