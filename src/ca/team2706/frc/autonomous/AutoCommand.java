package ca.team2706.frc.autonomous;

import org.strongback.command.Command;

public abstract class AutoCommand extends Command {
	
	@Override
	public boolean execute() {
		if(tick()) {
			cleanup();
			return true;
		}
		else {
			return false;
		}
	}

	public abstract void initialize();
	public abstract boolean tick();
	public abstract void cleanup();
}
