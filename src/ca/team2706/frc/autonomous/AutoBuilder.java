package ca.team2706.frc.autonomous;

import java.util.ArrayList;
import java.util.List;

public class AutoBuilder {
	private List<AutoCommand> commands = new ArrayList<AutoCommand>();

	public void add(AutoCommand autoCommand) {
		commands.add(autoCommand);
	}
	
	public AutoCommand[] toArray() {
		return commands.toArray(new AutoCommand[commands.size()]);
	}
}
