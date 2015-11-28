package ca.team2706.frc.robot;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import com.google.common.base.Charsets;
import com.google.common.collect.Iterables;

import ca.team2706.frc.autonomous.AutoHelper;
import ca.team2706.frc.controls.EGamepad;
import ca.team2706.frc.controls.EJoystick;
import ca.team2706.frc.controls.Motor;
import ca.team2706.frc.mechanism.SimGyro;
import ca.team2706.frc.mechanism.raspberrypi.RaspberryPi;
import ca.team2706.frc.utils.Constants;
import ca.team2706.frc.utils.SimPID;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.RobotDrive;


public class Subsystems {	
	
	// Motors
	public static Motor rightDrive;
	public static Motor leftDrive;
	
	// Drive
	public static RobotDrive robotDrive;
	
	// Encoders
	public static Encoder rightDriveEncoder;
	public static Encoder leftDriveEncoder;
	
	// Sensor
	public static SimGyro gyroSensor;
	
	// USB
	public static EJoystick	driveJoystick;
	public static EGamepad controlGamepad;
	
	// Power Panel
	public static PowerDistributionPanel powerPanel;
	
	public static Compressor compressor;
 	
 	// PIDs
 	public static SimPID gyroPID;
 	public static SimPID encoderPID;
 	
 	public static DigitalInput[] inputs;
 	
 	public static RaspberryPi pi;

	/**
	 * Initialize all of the subsystems, assumes that the constants file has been read already
	 */
	public static void initialize()
	{
		
		// Encoders
		rightDriveEncoder = new Encoder(Constants.getConstantAsInt(Constants.DIO_RIGHT_ENCODER_A), Constants.getConstantAsInt(Constants.DIO_RIGHT_ENCODER_B), true);
		leftDriveEncoder = new Encoder(Constants.getConstantAsInt(Constants.DIO_LEFT_ENCODER_A), Constants.getConstantAsInt(Constants.DIO_LEFT_ENCODER_B), true);

		// Power Panel
		powerPanel = new PowerDistributionPanel();
		
		//Compressor
		compressor = new Compressor();
		
		// Sensors
		gyroSensor = new SimGyro(Constants.getConstantAsInt(Constants.AIO_GYRO_SENSOR));
		gyroSensor.initGyro();
		
		initPID();

		inputs = new DigitalInput[Constants.getConstantAsInt(Constants.NUM_AUTO_SELECT)];

		for(int i = 0; i < Constants.getConstantAsInt(Constants.NUM_AUTO_SELECT); ++i) {
			inputs[i] = new DigitalInput(i + Constants.getConstantAsInt(Constants.FIRST_DIGITAL_SELECT));
		}
		
		pi = new RaspberryPi(Constants.getConstant(Constants.RASPBERRY_PI_IP));
	}
	
	/**
	 * Public for testing purposes. Initializes the PID controllers.
	 */
	public static void initPID() {
		//PIDs
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
			//Read values from file
			List<String> guavaResult = Files.readAllLines(new File(Constants.getConstant(Constants.CALIBRATION_FILE_LOC)).toPath(), Charsets.UTF_8);
			Iterable<String> guavaResultFiltered = Iterables.filter(guavaResult, AutoHelper.SKIP_COMMENTS);

			String[] s = Iterables.toArray(AutoHelper.SPLITTER.split(guavaResultFiltered.iterator().next()), String.class);
			
			if (encoderADistancePerPulse == 0) {
				encoderADistancePerPulse = Double.parseDouble(s[0]);
			}
			if (encoderBDistancePerPulse == 0) {
				encoderBDistancePerPulse = Double.parseDouble(s[1]);
			} 
			
			//Set feet to encoder tick ratios from file
			System.out.println("EncoderADistancePerPulse:" + encoderADistancePerPulse + ", EncoderBDistancePerPulse:" + encoderBDistancePerPulse);
			leftDriveEncoder.setDistancePerPulse(encoderADistancePerPulse);
			rightDriveEncoder.setDistancePerPulse(encoderBDistancePerPulse);
		} catch (IOException e) {
			System.out.println("Calibration file read error!");
			//Set to backup values
			leftDriveEncoder.setDistancePerPulse(encoderADistancePerPulseOverride);
			rightDriveEncoder.setDistancePerPulse(encoderBDistancePerPulseOverride);
		}
	}
}
