package ca.team2706.frc.autonomous.commands;

import ca.team2706.frc.autonomous.ECommandGroup;

public class DriveStraightAndTurn extends ECommandGroup {
	
	public DriveStraightAndTurn(double x, double y) {
		super();
		
		// Calculate the angle to turn so that the robot will 
		// be along the hypotenuse the the rectangle that is
		// created with with x and y
		int angle = (int) Math.toDegrees(Math.atan2(x, y));
		
		// Calculate the distance of the hypotenuse
		double distance = Math.sqrt((x * x) + (y * y));
		
		// Execute turn and drive
		sequentially(new DriveTurn(angle), new DriveStraight(distance));
	}
}
