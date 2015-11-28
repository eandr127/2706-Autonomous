package ca.team2706.frc.autonomous.commands;

import org.junit.Assert;
import org.junit.Test;
import org.strongback.command.Command;
import org.strongback.command.CommandTester;
import org.strongback.mock.MockAngleSensor;
import org.strongback.mock.MockGyroscope;

import ca.team2706.frc.robot.MockSubsystems;
import ca.team2706.frc.robot.Subsystems;

public class DriveStraightAndTurnTest {
	
	@Test
	public void shouldTurn45DegreesClockwiseAndDrive14Feet() {
		// Reset subsystem with mock components
		MockSubsystems.initialize();
		
		// Create Command to run
		Command command = new DriveStraightAndTurn(10, 10);
		double speed;
		
		// Create a CommandTester to run the Command
		CommandTester runner = new CommandTester(command);
		
		long startTime = System.currentTimeMillis();
		
		// Loop while the command is not ready to be terminated
		while(!runner.step(System.currentTimeMillis() - startTime)) {
			speed = Subsystems.leftDrive.getSpeed();

			// If the motors are running in the opposite direction then the robot still is turning
			if(Subsystems.leftDrive.getSpeed() == -Subsystems.rightDrive.getSpeed())
				// The angle that the Gyroscope returns should be affected by the motor speed
				((MockGyroscope)Subsystems.gyroSensor).setAngle(Subsystems.gyroSensor.getAngle() + speed);
			// Otherwise it has moved on to driving 
			else {
				// The encoder distance should be affected by the motor speed
				((MockAngleSensor)Subsystems.rightDriveEncoder).setAngle(Subsystems.rightDriveEncoder.getAngle() + speed);
				((MockAngleSensor)Subsystems.leftDriveEncoder).setAngle(Subsystems.leftDriveEncoder.getAngle() + speed);
			}
		}
		// The Command is done, so the CommandTester should be cancelled
		runner.cancel();
		
		// Make sure the Gyroscope value is about were it should be
		Assert.assertTrue(Math.round(Subsystems.gyroSensor.getAngle()) > 40 && Math.round(Subsystems.gyroSensor.getAngle()) < 50);
		// Make sure that the PID loop actually finished
		Assert.assertTrue(Subsystems.gyroPID.isDone());
		
		// Make sure the encoder values are about were they should be
		Assert.assertTrue(Math.round(Subsystems.leftDriveEncoder.getAngle()) == Math.round(Math.sqrt(200)) && Math.round(Subsystems.rightDriveEncoder.getAngle()) == Math.round(Math.sqrt(200)));
		// Make sure that the PID loop actually finished
		Assert.assertTrue(Subsystems.encoderPID.isDone());
		
		// Make sure the motors are stopped
		Assert.assertTrue(Subsystems.leftDrive.getSpeed() == 0.0 && Subsystems.leftDrive.getSpeed() == 0.0);
    }
	
	@Test
	public void shouldTurn135DegreesClockwiseAndDrive14Feet() {
		MockSubsystems.initialize();
		
		// Only difference from the first method
		Command command = new DriveStraightAndTurn(10, -10);
		double speed;
		
		CommandTester runner = new CommandTester(command);
		
		long startTime = System.currentTimeMillis();
		
		while(!runner.step(System.currentTimeMillis() - startTime)) {
			speed = Subsystems.leftDrive.getSpeed();
			if(Subsystems.leftDrive.getSpeed() == -Subsystems.rightDrive.getSpeed())
				((MockGyroscope)Subsystems.gyroSensor).setAngle(Subsystems.gyroSensor.getAngle() + speed);
			else {
				((MockAngleSensor)Subsystems.rightDriveEncoder).setAngle(Subsystems.rightDriveEncoder.getAngle() + speed);
				((MockAngleSensor)Subsystems.leftDriveEncoder).setAngle(Subsystems.leftDriveEncoder.getAngle() + speed);
			}
		}
		runner.cancel();
		
		Assert.assertTrue(Math.round(Subsystems.gyroSensor.getAngle()) > 130 && Math.round(Subsystems.gyroSensor.getAngle()) < 140);
		Assert.assertTrue(Subsystems.gyroPID.isDone());
		
		Assert.assertTrue(Math.round(Subsystems.leftDriveEncoder.getAngle()) == Math.round(Math.sqrt(200)) && Math.round(Subsystems.rightDriveEncoder.getAngle()) == Math.round(Math.sqrt(200)));
		Assert.assertTrue(Subsystems.encoderPID.isDone());
		
		Assert.assertTrue(Subsystems.leftDrive.getSpeed() == 0.0 && Subsystems.leftDrive.getSpeed() == 0.0);
    }
	
	@Test
	public void shouldTurn45DegreesCounterClockwiseAndDrive14Feet() {
		MockSubsystems.initialize();
		
		// Only difference from the first method
		Command command = new DriveStraightAndTurn(-10, 10);
		double speed;
		
		CommandTester runner = new CommandTester(command);
		
		long startTime = System.currentTimeMillis();
		
		while(!runner.step(System.currentTimeMillis() - startTime)) {
			speed = Subsystems.leftDrive.getSpeed();
			if(Subsystems.leftDrive.getSpeed() == -Subsystems.rightDrive.getSpeed())
				((MockGyroscope)Subsystems.gyroSensor).setAngle(Subsystems.gyroSensor.getAngle() + speed);
			else {
				((MockAngleSensor)Subsystems.rightDriveEncoder).setAngle(Subsystems.rightDriveEncoder.getAngle() + speed);
				((MockAngleSensor)Subsystems.leftDriveEncoder).setAngle(Subsystems.leftDriveEncoder.getAngle() + speed);
			}
		}
		runner.cancel();
		
		Assert.assertTrue(Math.round(Subsystems.gyroSensor.getAngle()) < -40 && Math.round(Subsystems.gyroSensor.getAngle()) > -50);
		Assert.assertTrue(Subsystems.gyroPID.isDone());
		
		Assert.assertTrue(Math.round(Subsystems.leftDriveEncoder.getAngle()) == Math.round(Math.sqrt(200)) && Math.round(Subsystems.rightDriveEncoder.getAngle()) == Math.round(Math.sqrt(200)));
		Assert.assertTrue(Subsystems.encoderPID.isDone());
		
		Assert.assertTrue(Subsystems.leftDrive.getSpeed() == 0.0 && Subsystems.leftDrive.getSpeed() == 0.0);
    }
	
	@Test
	public void shouldTurn135DegreesCounterClockwiseAndDrive14Feet() {
		MockSubsystems.initialize();
		
		// Only difference from the first method
		Command command = new DriveStraightAndTurn(-10, -10);
		double speed;
		
		CommandTester runner = new CommandTester(command);
		
		long startTime = System.currentTimeMillis();
		
		while(!runner.step(System.currentTimeMillis() - startTime)) {
			speed = Subsystems.leftDrive.getSpeed();
			if(Subsystems.leftDrive.getSpeed() == -Subsystems.rightDrive.getSpeed())
				((MockGyroscope)Subsystems.gyroSensor).setAngle(Subsystems.gyroSensor.getAngle() + speed);
			else {
				((MockAngleSensor)Subsystems.rightDriveEncoder).setAngle(Subsystems.rightDriveEncoder.getAngle() + speed);
				((MockAngleSensor)Subsystems.leftDriveEncoder).setAngle(Subsystems.leftDriveEncoder.getAngle() + speed);
			}
		}
		runner.cancel();
		
		Assert.assertTrue(Math.round(Subsystems.gyroSensor.getAngle()) < -130 && Math.round(Subsystems.gyroSensor.getAngle()) > -140);
		Assert.assertTrue(Subsystems.gyroPID.isDone());
		
		Assert.assertTrue(Math.round(Subsystems.leftDriveEncoder.getAngle()) == Math.round(Math.sqrt(200)) && Math.round(Subsystems.rightDriveEncoder.getAngle()) == Math.round(Math.sqrt(200)));
		Assert.assertTrue(Subsystems.encoderPID.isDone());
		
		Assert.assertTrue(Subsystems.leftDrive.getSpeed() == 0.0 && Subsystems.leftDrive.getSpeed() == 0.0);
    }
}
