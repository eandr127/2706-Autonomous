package ca.team2706.frc.autonomous.modes;

import ca.team2706.frc.autonomous.AutoBuilder;
import ca.team2706.frc.autonomous.AutoCommand;
import ca.team2706.frc.autonomous.AutoMode;

public class EmptyMode extends AutoMode {

	@Override
	protected AutoCommand[] initializeCommands() {
		// This assumes that we start in the staging zone parallel to the closest scoring platform
		AutoBuilder builder = new AutoBuilder();
		
		//Do nothing
		return builder.toArray();
	}
}
