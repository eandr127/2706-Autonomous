package ca.team2706.frc.autonomous;

import org.strongback.Strongback;
import org.strongback.command.Command;

public abstract class AutoMode {
	
	private Command[] commands;

	protected abstract Command[] initializeCommands();
	
	public void initialize() {
		commands = initializeCommands();
		for(Command command : commands)
			Strongback.submit(command);
		
	}
}
