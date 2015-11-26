package ca.team2706.frc.autonomous.commands;

import org.junit.Assert;
import org.junit.Test;
import org.strongback.command.Command;
import org.strongback.command.CommandTester;
import org.strongback.mock.MockGyroscope;

import ca.team2706.frc.robot.MockSubsystems;
import ca.team2706.frc.robot.Subsystems;

public class DriveTurnTest {

	@Test
    public void shouldTurn180DegreesClockwise() {
		// Reset subsystem with mock components
		MockSubsystems.initialize();
		
		// Create Command to run
		Command command = new DriveTurn(180);
		double speed;
		
		// Create a CommandTester to run the Command
		CommandTester runner = new CommandTester(command);
		
		long startTime = System.currentTimeMillis();
		
		// Loop while the command is not ready to be terminated
		while(!runner.step(System.currentTimeMillis() - startTime)) {
			speed = Subsystems.leftDrive.getSpeed();
			// The angle that the Gyroscope returns should be affected by the motor speed
			((MockGyroscope)Subsystems.gyroSensor).setAngle(Subsystems.gyroSensor.getAngle() + speed);
		}
		// The Command is done, so the CommandTester should be cancelled
		runner.cancel();
		
		// Make sure the Gyroscope value is about were it should be
		Assert.assertTrue(Math.round(Subsystems.gyroSensor.getAngle()) > 175.0 && Math.round(Subsystems.gyroSensor.getAngle()) < 185.0);
		// Make sure that the PID loop actually finished
		Assert.assertTrue(Subsystems.gyroPID.isDone());
		
		// Make sure the motors are stopped
		Assert.assertTrue(Subsystems.leftDrive.getSpeed() == 0.0 && Subsystems.leftDrive.getSpeed() == 0.0);
    }
	
	@Test
    public void shouldTurn180DegreesCounterClockwise() {
		MockSubsystems.initialize();
		
		// Only difference from the first method
		Command command = new DriveTurn(-180);
		double speed;
		
		CommandTester runner = new CommandTester(command);
		
		long startTime = System.currentTimeMillis();
		
		while(!runner.step(System.currentTimeMillis() - startTime)) {
			speed = Subsystems.leftDrive.getSpeed();
			((MockGyroscope)Subsystems.gyroSensor).setAngle(Subsystems.gyroSensor.getAngle() + speed);
		}
		runner.cancel();
		
		Assert.assertTrue(Math.round(Subsystems.gyroSensor.getAngle()) < -175.0 && Math.round(Subsystems.gyroSensor.getAngle()) > -185.0);
		Assert.assertTrue(Subsystems.gyroPID.isDone());
		
		Assert.assertTrue(Subsystems.leftDrive.getSpeed() == 0.0 && Subsystems.leftDrive.getSpeed() == 0.0);
    }
}
