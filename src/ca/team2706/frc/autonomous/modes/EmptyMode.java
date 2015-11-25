package ca.team2706.frc.autonomous.modes;

import org.strongback.command.Command;

import ca.team2706.frc.autonomous.AutoBuilder;
import ca.team2706.frc.autonomous.AutoMode;

public class EmptyMode extends AutoMode {

	@Override
	protected Command[] initializeCommands() {
		AutoBuilder builder = new AutoBuilder();
		
		// Do nothing
		return builder.toArray();
	}
}
