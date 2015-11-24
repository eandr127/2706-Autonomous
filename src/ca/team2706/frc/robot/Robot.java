
package ca.team2706.frc.robot;

import ca.team2706.frc.autonomous.AutoMode;
import ca.team2706.frc.autonomous.AutoModeSelector;
import ca.team2706.frc.utils.Constants;
import ca.team2706.frc.utils.Logging;
import edu.wpi.first.wpilibj.IterativeRobot;

import org.strongback.Strongback;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	public AutoMode currentAutoMode;
	private AutoModeSelector selector;
	
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
    	Logging.setupLogger(new Boolean(Constants.getConstant(Constants.DEBUG_LOGGING)));
    	
    	Constants.readConstantPropertiesFromFile();
    	Subsystems.initialize();
    	
    	Subsystems.leftDriveEncoder.reset();
    	Subsystems.rightDriveEncoder.reset();
    	
    	selector = new AutoModeSelector();
    }

    /**
     * This function is run when the autonomous mode is first started up and should be
     *  used for any initialization code.
     */
    public void autonomousInit() {
    	// Select an autonomous mode! :) Uses DIO array from Subsystems. See 
    	// initialize for how it's initialized. See docs for selectMode to see
    	// how they're used.
    	currentAutoMode = selector.selectMode(Subsystems.inputs); //new TestAutoMode(); TODO FIX DIPSWITCH ISSUE
    	currentAutoMode.initialize();
    }
    
    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
    	currentAutoMode.tick();
    }
    
    @Override
    public void teleopInit() {
        // Start Strongback functions ...
        Strongback.start();
    }

    @Override
    public void teleopPeriodic() {
    }

    @Override
    public void disabledInit() {
        // Tell Strongback that the robot is disabled so it can flush and kill commands.
        Strongback.disable();
    }
}
