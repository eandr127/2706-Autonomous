package ca.team2706.frc.autonomous.modes;

import ca.team2706.frc.autonomous.AutoBuilder;
import ca.team2706.frc.autonomous.AutoCommand;
import ca.team2706.frc.autonomous.AutoMode;
import ca.team2706.frc.autonomous.commands.DriveStraight;

public class DriveForwardMode extends AutoMode {
	
	//TODO: Test this on practice field. These are bogus values.
	private static final double STRAIGHT_DISTANCE = 9.45; // TODO Change this number!!!!!
	
	@Override
	protected AutoCommand[] initializeCommands() {
		// Alignment: Back of Robot with Staging Zone 
		AutoBuilder builder = new AutoBuilder();
		
		// Drive forward
		builder.add(new DriveStraight(STRAIGHT_DISTANCE));
		
		return builder.toArray();
	}
}
