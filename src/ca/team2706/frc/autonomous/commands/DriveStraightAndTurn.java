package ca.team2706.frc.autonomous.commands;

import org.strongback.command.CommandGroup;

public class DriveStraightAndTurn extends CommandGroup {
	
	public DriveStraightAndTurn(double x, double y) {
		super();
		
		// Calculate the angle to turn so that the robot will 
		// be along the hypotenuse the the rectangle that is
		// created with with x and y
		int theta = (int) Math.toDegrees(Math.atan2(x, y));
		int angle;
		if(x > 0 && y > 0) {
			angle = theta;
		}
		else if(x > 0 && y < 0) {
			angle = 180 - theta;
		}
		else if(x < 0 && y < 0) {
			angle = -180 + theta;
		}
		else if(x < 0 && y > 0) {
			angle = -theta;
		}
		else {
			angle = 0;
		}
		
		// Calculate the distance of the hypotenuse
		double distance = Math.sqrt((x * x) + (y * y));
		
		// Execute turn and drive
		sequentially(new DriveTurn(angle), new DriveStraight(distance));
	}
}