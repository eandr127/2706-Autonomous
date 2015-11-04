package com.owcrobots.frc.autonomous.modes;

import com.owcrobots.frc.autonomous.AutoBuilder;
import com.owcrobots.frc.autonomous.AutoCommand;
import com.owcrobots.frc.autonomous.AutoMode;
import com.owcrobots.frc.autonomous.commands.GearShift;

public class BasicRobotSetMode extends AutoMode {
	//TODO: Test this on practice field. These are bogus values.

	@Override
	protected AutoCommand[] initializeCommands() {
		// This assumes that we start in the staging zone parallel to the closest scoring platform
		AutoBuilder builder = new AutoBuilder();

		// Shift to low gear
		builder.add(new GearShift(false));
		
		return builder.toArray();
	}
}
