/*
 * Strongback
 * Copyright 2015, Strongback and individual contributors by the @authors tag.
 * See the COPYRIGHT.txt in the distribution for a full listing of individual
 * contributors.
 *
 * Licensed under the MIT License; you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://opensource.org/licenses/MIT
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ca.team2706.frc.autonomous;

import org.strongback.annotation.NotThreadSafe;
import org.strongback.command.Command;

/**
 * A {@link ECommandGroup} is a series of {@link Command}s executed in sequence or in parallel. A command group is created by
 * extending {@link ECommandGroup}, and then specifying in the constructor of the subclass whether the {@link Command}s should be
 * executed {@link #sequentially(Command...) sequential} or {@link #simultaneously(Command...) simultaneously}. The commands
 * passed into these methods can be any {@link Command} subclass, {@link ECommandGroup} subclass, or a group of sequential,
 * simultaneous, or forked commands.
 * <p>
 * Commands executed one after the other are <em>sequential</em>, and are defined with a call to
 * {@link #sequentially(Command...)} and the list of {@link Command} instances, in order. The whole block finishes only when the
 * last {@link Command} is done.
 *
 * <p>Commands executed at the same time (in parallel) are <em>simultaneous</em>, and are defined with a call to
 * {@link #simultaneously(Command...)} and a list of the {@link Command} instances. Order is not important, as they are all are
 * executed at the same time. The whole block finishes only when all {@link Command}s have completed.
 *
 * <p>When a command is to be executed completely independently of the command group, then that command (or group of commands)
 * can be <em>forked</em>. A command (or group) is forked by calling {@link #fork(Command)} with the one {@link Command}, a
 * {@link ECommandGroup} subclass, or a group created with {@link #sequentially(Command...)} or
 * {@link #simultaneously(Command...)}. For the purposes of {@link #sequentially(Command...)} the block finishes instantly.
 *
 * <h2>Sequential example</h2>
 * <p>
 * The following code shows a complete CommandGroup subclass that, when executed, first executes a {@code CommandA}, then a
 * {@code CommandB}, and then finally a {@code CommandC}:
 *
 * <pre>
 * public class MySequentialCommands extends CommandGroup {
 *     public MySequentialCommands() {
 *         sequentially(new CommandA(), new CommandB(), new CommandC());
 *     }
 * }
 * </pre>
 *
 * The {@code MySequentialCommands} will complete when {@code CommandC} instance completes.
 *
 * <h2>Simultaneous example</h2>
 * <p>
 * The following code shows a complete CommandGroup subclass that, when executed, executes three commands (a {@code CommandA}, a
 * {@code CommandB}, and a {@code CommandC}) all at the same time:
 *
 * <pre>
 * public class MySimultaneousCommands extends CommandGroup {
 *     public MySimultaneousCommands() {
 *         simultaneously(new CommandA(), new CommandB(), new CommandC());
 *     }
 * }
 * </pre>
 *
 * The {@code MySimultaneousCommands} will complete when the last of the three commands completes.
 *
 * <h2>Mixture of sequential and simultaneous</h2>
 * <p>
 * The following code shows a complete CommandGroup subclass that, when executed, executes two commands (a {@code CommandA} and
 * a {@code CommandB}) at the same time and after both are finished a third command, {@code CommandC}:
 *
 * <pre>
 * public class MyMixedCommands extends CommandGroup {
 *     public MyMixedCommands() {
 *         sequentially(simultaneously(new CommandA(), new CommandB()), new CommandC());
 *     }
 * }
 * </pre>
 *
 * The {@code MyMixedCommands} will complete when the last of the three commands ({@code CommandC}) completes.
 *
 * <h2>Complex example</h2>
 * <p>
 * The following code shows a complete but more complex CommandGroup subclass that, when executed, first executes
 * {@code CommandA}, then executes {@code CommandB}, then forks off {@code CommandC} and immediately executes {@code CommandD},
 * and finally executes both {@code CommandE} and {@code CommandF} in parallel:
 *
 * <pre>
 * public class MyForkCommands extends CommandGroup {
 *     public MyForkCommands() {
 *         sequentially(new CommandA(),
 *                      new CommandB(),
 *                      fork(new Command C()),
 *                      new CommandD());
 *                      new sequentially( new CommandE(), new CommandF()));
 *     }
 * }
 * </pre>
 *
 * The {@code MyForkCommands} will complete when the last of the two final commands ({@code CommandE} or {@code CommandF})
 * completes. Note that this is true even if {@code CommandC} is still running.
 */
@NotThreadSafe
public class ECommandGroup extends Command {

    /**
     * Creates a single {@link ECommandGroup} that executes several {@link Command}s in sequential order.
     *
     * @param commands the {@link Command}s to be executed
     * @return the {@link ECommandGroup} wrapping the {@link Command}s
     */
    public static ECommandGroup runSequentially( Command ... commands ) {
        return new ECommandGroup(commands,Type.SEQUENTIAL);
    }

    /**
     * Creates a single {@link ECommandGroup} that executes several {@link Command}s simultaneously.
     *
     * @param commands the {@link Command}s to be executed
     * @return the {@link ECommandGroup} wrapping the {@link Command}s
     */
    public static ECommandGroup runSimultaneously( Command ... commands ) {
        return new ECommandGroup(commands,Type.PARRALLEL);
    }

    static enum Type {
        SEQUENTIAL, PARRALLEL, FORK;
    }

    private Command root;
    private  Command[] commands;
    private final Type type;
    private int commandIdx = 0;

    /**
     * Create a new command group. Typically, subclass constructors call this constructor (perhaps implicitly) and then add one
     * or more commands using {@link #fork(Command)}, {@link #sequentially(Command...)}, and {@link #simultaneously(Command...)}
     * .
     */
    protected ECommandGroup() {
        commands = null;
        type = Type.SEQUENTIAL;
    }

    private ECommandGroup(Command[] commands, Type type) {
        this.commands = commands;
        this.type = type;
    }

    Type getType() {
        return type;
    }

    Command[] getCommands() {
        return commands;
    }

    Command getRoot() {
        return root;
    }

    /**
     * Wraps several {@link Commands}s in a single {@link ECommandGroup} that executes them simultaneously.
     *
     * @param commands the {@link CommandRunner}s to wrap
     * @return the {@link ECommandGroup} wrapping the {@link Command}s
     */
    public ECommandGroup simultaneously(Command... commands) {
        ECommandGroup cg = new ECommandGroup(commands, Type.PARRALLEL);
        root = cg;
        return cg;
    }

    /**
     * Creates a single {@link ECommandGroup} that executes several {@link Command}s in sequential order.
     *
     * @param commands the {@link Command}s to be executed
     * @return the {@link ECommandGroup} wrapping the {@link Command}s
     */
    public ECommandGroup sequentially(Command... commands) {
        ECommandGroup cg = new ECommandGroup(commands, Type.SEQUENTIAL);
        root = cg;
        this.commands = commands;
        return cg;
    }

    /**
     * Add a {@link Command} that executes independently of any other {@link Command}s in this group.
     *
     * @param forked the {@link Command} to fork
     * @return the forked {@link ECommandGroup}
     */
    public ECommandGroup fork(Command forked) {
        ECommandGroup cg = new ECommandGroup(new Command[] { forked }, Type.FORK);
        root = cg;
        return cg;
    }

    @Override
    public void initialize() {
    	commands[0].initialize();
    }
    
    @Override
    public boolean execute() {  
    	if (commandIdx < commands.length) {  //Still commands
			if (commands[commandIdx].execute()) { //This command is done
				commands[commandIdx].end(); // cleanup command
				commandIdx++; //increment index
				if (commandIdx < commands.length) { // still commands after incrementation
					commands[commandIdx].initialize(); // Init next command
				}
			}
		}
    	else
    		return true;
    	
    	return false;
    }

}