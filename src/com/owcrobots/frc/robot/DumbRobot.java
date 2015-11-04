
package com.owcrobots.frc.robot;

import com.owcrobots.frc.autonomous.AutoMode;
import com.owcrobots.frc.autonomous.AutoModeSelector;
import com.owcrobots.frc.utils.Constants;

import edu.wpi.first.wpilibj.IterativeRobot;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class DumbRobot extends IterativeRobot {
	public AutoMode currentAutoMode;
	private AutoModeSelector selector;
	
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
    	Constants.readConstantPropertiesFromFile();
    	Subsystems.initialize();
    	
    	Subsystems.leftDriveEncoder.reset();
    	Subsystems.rightDriveEncoder.reset();
    	Subsystems.forkliftEncoder.reset();
    	
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
    	//TODO: Make this = selector.selectMode(Subsystems.inputs) when we're sure that it works
    	// Currently we just initialize it to TestAutoMode.
    	currentAutoMode = selector.selectMode(Subsystems.inputs); //new TestAutoMode(); TODO FIX DIPSWITCH ISSUE
    	currentAutoMode.initialize();
    }
    
    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
    	currentAutoMode.tick();
    }
}
