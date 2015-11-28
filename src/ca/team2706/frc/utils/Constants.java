package ca.team2706.frc.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Constants
{
	private static final String CONSTANTS_FILE_NAME = 			"/home/lvuser/constants.properties";
	
	private static Properties defaults = new Properties();
	private static Properties constants = new Properties();
	
	/* Property names */
	// Motor Types
	public static final String MOTOR_TYPE_DRIVE =				"MOTOR_TYPE_DRIVE";
	
	// PWM
	public static final String PWM_LEFT_DRIVE =					"PWM_LEFT_DRIVE";
	public static final String PWM_RIGHT_DRIVE =				"PWM_RIGHT_DRIVE";
	
	// DIO
	public static final String DIO_RIGHT_ENCODER_A =			"DIO_RIGHT_ENCODER_A";
	public static final String DIO_RIGHT_ENCODER_B =			"DIO_RIGHT_ENCODER_B";
	public static final String DIO_LEFT_ENCODER_A =				"DIO_LEFT_ENCODER_A";
	public static final String DIO_LEFT_ENCODER_B =				"DIO_LEFT_ENCODER_B";
	
	// Analog IO
	public static final String AIO_GYRO_SENSOR =				"AIO_GYRO_SENSOR";

	// USB
	public static final String USB_DRIVE_STICK =				"USB_RIGHT_STICK";
	public static final String USB_CONTROL_GAMEPAD =			"USB_CONTROL_GAMEPAD";
	
	//PID
	public static final String GYRO_PID_P = 					"GYRO_PID_P";
	public static final String GYRO_PID_I = 					"GYRO_PID_I";
	public static final String GYRO_PID_D = 					"GYRO_PID_D";
	public static final String GYRO_PID_E = 					"GYRO_PID_E";
	public static final String GYRO_PID_MAX = 					"GYRO_PID_MAX";
	
	public static final String ENCODER_PID_P = 					"ENCODER_PID_P";
	public static final String ENCODER_PID_I = 					"ENCODER_PID_I";
	public static final String ENCODER_PID_D = 					"ENCODER_PID_D";
	public static final String ENCODER_PID_E = 					"ENCODER_PID_E";
	public static final String ENCODER_PID_MAX = 				"ENCODER_PID_MAX";
	
	public static final String CALIBRATION_FILE_LOC =			"CALIBRATION_FILE_LOC";
	
	// Digital Inputs
	public static final String NUM_AUTO_SELECT =				"NUM_INPUTS";
	public static final String FIRST_DIGITAL_SELECT = 			"FIRST_DIGITAL_SELECT";
	public static final String SECOND_DIGITAL_SELECT = 			"SECOND_DIGITAL_SELECT";
	
	//Joystick Buttons
	public static final String JOYSTICK_CALIBRATE  =			"JOYSTICK_CALIBRATE";
	
	//Other
	public static final String DEBUG_LOGGING = 					"DEBUG_LOGGING";
	public static final String RASPBERRY_PI_IP = 				"RASPBERRY_PI_IP";

	static {
		// Motor Types
		defaults.put(MOTOR_TYPE_DRIVE, "0");
		
		// PWM
		defaults.put(PWM_LEFT_DRIVE, "3");
		defaults.put(PWM_RIGHT_DRIVE, "2");
		
		// DIO
		defaults.put(DIO_RIGHT_ENCODER_A, "9");
		defaults.put(DIO_RIGHT_ENCODER_B, "8");
		defaults.put(DIO_LEFT_ENCODER_A, "7");
		defaults.put(DIO_LEFT_ENCODER_B, "6");
		
		// Analog IO
		defaults.put(AIO_GYRO_SENSOR, "0");

		// USB
		defaults.put(USB_DRIVE_STICK, "0");
		defaults.put(USB_CONTROL_GAMEPAD, "1");
		
		//PID TODO: Recreate values
		defaults.put(GYRO_PID_P, "0.05");
		defaults.put(GYRO_PID_I, "0.01");
		defaults.put(GYRO_PID_D, "0.01");
		defaults.put(GYRO_PID_E, "5.0");
		defaults.put(GYRO_PID_MAX, "0.4");

		defaults.put(ENCODER_PID_P, "0.5");
		defaults.put(ENCODER_PID_I, "0.0"); 
		defaults.put(ENCODER_PID_D, "0.0");
		defaults.put(ENCODER_PID_E, "0.1");
		defaults.put(ENCODER_PID_MAX, "0.2");

		defaults.put(CALIBRATION_FILE_LOC, "/home/lvuser/calibration.txt");
		
		// Digital Inputs
		// Allows for 8
		defaults.put(NUM_AUTO_SELECT, "3");
		defaults.put(FIRST_DIGITAL_SELECT, "3");
		defaults.put(SECOND_DIGITAL_SELECT, "5");
		
		//Joystick Buttons
		defaults.put(JOYSTICK_CALIBRATE, "2");
		
		//Other
		defaults.put(DEBUG_LOGGING, "true");
		defaults.put(RASPBERRY_PI_IP, "10.27.06.2");
		
		constants.putAll(defaults);
	}
	
	/**
	 * Returns constant as a String
	 * @param constant name
	 * @return
	 */
	public static String getConstant(String name) {
		return constants.getProperty(name);
	}

	public static void setConstant(String name, String value) {
		constants.setProperty(name, value);
	}

	/**
	 * Returns constant as an int
	 * @param constant name
	 * @return
	 */
	public static int getConstantAsInt(String name) {
		return Integer.parseInt(constants.getProperty(name));
	}
	
	/**
	 * Returns constant as a double
	 * @param constant name
	 * @return
	 */
	public static double getConstantAsDouble(String name) {
		return Double.parseDouble(constants.getProperty(name));
	}

	public static void readConstantPropertiesFromFile() {
		Properties defaultsFromFile = new Properties();
		FileInputStream in = null;
		try {
			in = new FileInputStream(CONSTANTS_FILE_NAME);			
			defaultsFromFile.load(in);
		} catch (IOException e) {
			System.out.println("Warning: Unable to load properties file " + CONSTANTS_FILE_NAME);
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
				System.out.println("Error: Unable to close properties file " + CONSTANTS_FILE_NAME);
				e.printStackTrace();
			}			
		}
		
		constants.putAll(defaultsFromFile);
	}
}
