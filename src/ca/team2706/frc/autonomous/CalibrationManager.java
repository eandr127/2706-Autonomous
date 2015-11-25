package ca.team2706.frc.autonomous;

import java.io.File;

import ca.team2706.frc.robot.Subsystems;
import ca.team2706.frc.utils.Constants;

public class CalibrationManager {
	
	private boolean calibrationDone = false;
	
	public void calibrateInit() {
		// Reset encoders
    	Subsystems.leftDriveEncoder.zero();
    	Subsystems.rightDriveEncoder.zero();
	}
	
	public void calibrateTick() {
		// Check if the calibration button is pressed
		if(Subsystems.driveJoystick.getButton(Constants.getConstantAsInt(Constants.JOYSTICK_CALIBRATE)).isTriggered()) {
			// If not continue to drive
    		Subsystems.robotDrive.arcade(Subsystems.driveJoystick.getPitch().read(), Subsystems.driveJoystick.getRoll().read());
    	}
		else if(!calibrationDone) {
			// If button was just released, stop
			Subsystems.robotDrive.stop();
	    	
			// Get encoder ticks traveled while button was held
	    	int encoderAValue = (int) Subsystems.leftDriveEncoder.getAngle();
	    	int encoderBValue = (int) Subsystems.rightDriveEncoder.getAngle();
	    			
	    	double encoderAConstant = 0;
	    	double encoderBConstant = 0;
	    	
	    	// Get feet to encoder tick ratio
	    	if(encoderAValue != 0) {
	    		encoderAConstant = 5.0 / encoderAValue;
	    	}
	    	
	    	if(encoderBValue != 0) {
	    		encoderBConstant = 5.0 / encoderBValue;
	    	}
	    	
	    	// Write ratio to file for reading from later
	    	String value = "//Encoder A (Left), Distance Travelled: 5ft, Number of encoder ticks: " + encoderAValue
	    			+ ", Calibration constant: " + encoderAConstant;
	    	System.out.println(value);
			AutoHelper.writeLineToFile(value, new File(Constants.getConstant(Constants.CALIBRATION_FILE_LOC)));
	    	
			value = "//Encoder B (Right), Distance Travelled: 5ft, Number of encoder ticks: " + encoderBValue
	    			+ ", Calibration constant: " + encoderBConstant;
			System.out.println(value);
			AutoHelper.writeLineToFile(value, new File(Constants.getConstant(Constants.CALIBRATION_FILE_LOC)));
	    	
			AutoHelper.writeLineToFile(encoderAConstant + ", " + encoderBConstant, new File(Constants.getConstant(Constants.CALIBRATION_FILE_LOC)));
			
			calibrationDone = true;
		}
	}
}
