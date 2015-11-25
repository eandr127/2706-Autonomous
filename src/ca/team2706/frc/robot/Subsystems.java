package ca.team2706.frc.robot;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import org.strongback.components.AngleSensor;
import org.strongback.components.Gyroscope;
import org.strongback.components.Motor;
import org.strongback.components.PowerPanel;
import org.strongback.components.Switch;
import org.strongback.components.ui.FlightStick;
import org.strongback.components.ui.Gamepad;
import org.strongback.drive.TankDrive;
import org.strongback.hardware.Hardware;
import com.google.common.base.Charsets;
import com.google.common.collect.Iterables;

import ca.team2706.frc.autonomous.AutoHelper;
import ca.team2706.frc.utils.Constants;
import ca.team2706.frc.utils.SimPID;


public class Subsystems {	
	
	// Motors
	public static Motor rightDrive;
	public static Motor leftDrive;
	
	// Drive
	public static TankDrive robotDrive;
	
	// Encoders
	public static AngleSensor rightDriveEncoder;
	public static AngleSensor leftDriveEncoder;
	
	// Sensor
	public static Gyroscope gyroSensor;
	
	// USB
	public static FlightStick driveJoystick;
	public static Gamepad controlGamepad;
	
	// Power Panel
	public static PowerPanel powerPanel;
 	
 	// PIDs
	// TODO: Use Strongback's PID when 1.1.0 comes out
 	public static SimPID gyroPID;
 	public static SimPID encoderPID;
 	
 	public static Switch[] inputs;

	/**
	 * Initialize all of the subsystems, assumes that the constants file has been read already
	 */
	public static void initialize() {
		// Motors
		rightDrive = Hardware.Motors.talon(Constants.getConstantAsInt(Constants.PWM_RIGHT_DRIVE));
		leftDrive = Hardware.Motors.talon(Constants.getConstantAsInt(Constants.PWM_LEFT_DRIVE));
		
		// Drive
		robotDrive = new TankDrive(leftDrive, rightDrive);
		
		// Encoders
		rightDriveEncoder = Hardware.AngleSensors.encoder(Constants.getConstantAsInt(Constants.DIO_RIGHT_ENCODER_A), 
				Constants.getConstantAsInt(Constants.DIO_RIGHT_ENCODER_B), 1);
		leftDriveEncoder = Hardware.AngleSensors.encoder(Constants.getConstantAsInt(Constants.DIO_LEFT_ENCODER_A), 
				Constants.getConstantAsInt(Constants.DIO_LEFT_ENCODER_B), 1);

		// Power Panel
		powerPanel = Hardware.powerPanel();
		
		// Sensors
		gyroSensor = Hardware.AngleSensors.gyroscope(Constants.getConstantAsInt(Constants.AIO_GYRO_SENSOR));
		
		// USB
		driveJoystick = Hardware.HumanInterfaceDevices.logitechAttack3D(Constants.getConstantAsInt(Constants.USB_DRIVE_STICK));
		controlGamepad = Hardware.HumanInterfaceDevices.logitechDualAction(Constants.getConstantAsInt(Constants.USB_CONTROL_GAMEPAD));
		
		initPID();

		inputs = new Switch[Constants.getConstantAsInt(Constants.NUM_AUTO_SELECT)];

		for(int i = 0; i < Constants.getConstantAsInt(Constants.NUM_AUTO_SELECT); ++i) {
			inputs[i] = Hardware.Switches.normallyOpen(i + Constants.getConstantAsInt(Constants.FIRST_DIGITAL_SELECT));
		}
	}
	
	/**
	 * Public for testing purposes. Initializes the PID controllers.
	 */
	public static void initPID() {
		// PIDs
		gyroPID = new SimPID(
				Constants.getConstantAsDouble(Constants.GYRO_PID_P),
				Constants.getConstantAsDouble(Constants.GYRO_PID_I),
				Constants.getConstantAsDouble(Constants.GYRO_PID_D),
				Constants.getConstantAsDouble(Constants.GYRO_PID_E)
				);

		encoderPID = new SimPID(
				Constants.getConstantAsDouble(Constants.ENCODER_PID_P),
				Constants.getConstantAsDouble(Constants.ENCODER_PID_I),
				Constants.getConstantAsDouble(Constants.ENCODER_PID_D),
				Constants.getConstantAsDouble(Constants.ENCODER_PID_E)
				);
	}
	
	/**
	 * Read in the encoder values from the autonomous config file.
	 */
	public static void readEncoderValues(double encoderADistancePerPulseOverride, double encoderBDistancePerPulseOverride) {
		double encoderADistancePerPulse = encoderADistancePerPulseOverride;
		double encoderBDistancePerPulse = encoderBDistancePerPulseOverride;
		
		try {
			// Read values from file
			List<String> guavaResult = Files.readAllLines(new File(Constants.getConstant(Constants.CALIBRATION_FILE_LOC)).toPath(), Charsets.UTF_8);
			Iterable<String> guavaResultFiltered = Iterables.filter(guavaResult, AutoHelper.SKIP_COMMENTS);

			String[] s = Iterables.toArray(AutoHelper.SPLITTER.split(guavaResultFiltered.iterator().next()), String.class);
			
			if (encoderADistancePerPulse == 0) {
				encoderADistancePerPulse = Double.parseDouble(s[0]);
			}
			if (encoderBDistancePerPulse == 0) {
				encoderBDistancePerPulse = Double.parseDouble(s[1]);
			} 
			
			// Set feet to encoder tick ratios from file
			System.out.println("EncoderADistancePerPulse:" + encoderADistancePerPulse + ", EncoderBDistancePerPulse:" + encoderBDistancePerPulse);
			
			leftDriveEncoder = Hardware.AngleSensors.encoder(Constants.getConstantAsInt(Constants.DIO_LEFT_ENCODER_A), 
					Constants.getConstantAsInt(Constants.DIO_LEFT_ENCODER_B), encoderADistancePerPulse);
			rightDriveEncoder = Hardware.AngleSensors.encoder(Constants.getConstantAsInt(Constants.DIO_RIGHT_ENCODER_A), 
					Constants.getConstantAsInt(Constants.DIO_RIGHT_ENCODER_B), encoderBDistancePerPulse);
		} catch (IOException e) {
			System.out.println("Calibration file read error!");
			// Set to backup values
			leftDriveEncoder = Hardware.AngleSensors.encoder(Constants.getConstantAsInt(Constants.DIO_LEFT_ENCODER_A), 
					Constants.getConstantAsInt(Constants.DIO_LEFT_ENCODER_B), encoderADistancePerPulseOverride);
			rightDriveEncoder = Hardware.AngleSensors.encoder(Constants.getConstantAsInt(Constants.DIO_RIGHT_ENCODER_A), 
					Constants.getConstantAsInt(Constants.DIO_RIGHT_ENCODER_B), encoderBDistancePerPulseOverride);
		}
	}
}
