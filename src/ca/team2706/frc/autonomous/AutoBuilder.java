package ca.team2706.frc.autonomous;

import java.util.ArrayList;
import java.util.List;

import org.strongback.command.Command;

public class AutoBuilder {
	private List<Command> commands = new ArrayList<Command>();

	public void add(Command autoCommand) {
		commands.add(autoCommand);
	}
	
	public Command[] toArray() {
		return commands.toArray(new Command[commands.size()]);
	}
}
