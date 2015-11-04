package com.owcrobots.frc.autonomous.modes;

import com.owcrobots.frc.autonomous.AutoBuilder;
import com.owcrobots.frc.autonomous.AutoCommand;
import com.owcrobots.frc.autonomous.AutoMode;
import com.owcrobots.frc.autonomous.commands.DriveStraight;
import com.owcrobots.frc.autonomous.commands.GearShift;

public class DriveForwardMode extends AutoMode {
	
	//TODO: Test this on practice field. These are bogus values.
	private static final double STRAIGHT_DISTANCE = 9.45; // TODO Change this number!!!!!
//	private static final int SIMPLE_FORKLIFT_COUNTER = 50;
	
	@Override
	protected AutoCommand[] initializeCommands() {
		// Alignment: Back of Robot with Staging Zone 
		AutoBuilder builder = new AutoBuilder();
		
		// Shift to low gear
		builder.add(new GearShift(false));
		
		// Drive forward into the zone.
		builder.add(new DriveStraight(STRAIGHT_DISTANCE));
		
		return builder.toArray();
	}
}
