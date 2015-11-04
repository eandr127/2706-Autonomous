package com.owcrobots.frc.autonomous.modes;

import com.owcrobots.frc.autonomous.AutoBuilder;
import com.owcrobots.frc.autonomous.AutoCommand;
import com.owcrobots.frc.autonomous.AutoMode;

public class EmptyMode extends AutoMode {

	@Override
	protected AutoCommand[] initializeCommands() {
		// This assumes that we start in the staging zone parallel to the closest scoring platform
		AutoBuilder builder = new AutoBuilder();
		
		//Do nothing
		return builder.toArray();
	}
}
