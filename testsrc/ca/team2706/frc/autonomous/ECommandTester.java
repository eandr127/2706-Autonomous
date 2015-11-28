package ca.team2706.frc.autonomous;

import org.strongback.command.Command;
import org.strongback.command.CommandTester;

public class ECommandTester extends CommandTester {
	
    private CommandTester[] runners;
    private int current = 0;

	public ECommandTester(Command command) {
		this(command, null);
	}
	
	private ECommandTester(Command command, ECommandTester last) {
		super(command);

		if (command != null) {
			if (command instanceof ECommandGroup) {
				command = ((ECommandGroup) command);
				ECommandGroup cg = (ECommandGroup) command;
				if(cg.getCommandType() != ECommandGroup.Type.SEQUENTIAL) {
					throw new IllegalArgumentException();
				}
				
				Command[] commands = cg.getCommandArray();
				
				runners = new CommandTester[commands.length];
				int i = 0;
				for(Command c : commands)
					runners[i++] = new CommandTester(c);
			}
		}
	}

    /**
     * Steps through all of the state logic for its {@link Command}.
     *
     * @param timeInMillis the current system time in milliseconds
     * @return {@code true} if this {@link CommandRunner} is ready to be terminated; {@code false} otherwise
     */
    public boolean step(long timeInMillis) {
		if (current < runners.length) {  //Still commands
			if (runners[current].step(timeInMillis)) { //This command is done
				runners[current].cancel(); // cleanup command
				current++; //increment index
				if (current < runners.length) { // still commands after incrementation
					return false;
				}
				else {
					return true;
				}
			}
			else
				return false;
		}		
        return true;
    }

    /**
     * Schedules its {@link Command} to be canceled next iteration.
     */
    public void cancel() {
    	if(current >= runners.length)
    		runners[runners.length - 1].cancel();
    	else
    		runners[current].cancel();
    }

    public boolean isCancelled() {
        return runners[current].isCancelled();
    }

    @Override
    public String toString() {
        return runners[current].toString();
    }
}
