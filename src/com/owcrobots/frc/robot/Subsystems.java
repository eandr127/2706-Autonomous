package com.owcrobots.frc.robot;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import com.google.common.base.Charsets;
import com.google.common.collect.Iterables;
import com.owcrobots.frc.autonomous.AutoHelper;
import com.owcrobots.frc.controls.EEncoder;
import com.owcrobots.frc.controls.EGamepad;
import com.owcrobots.frc.controls.EJoystick;
import com.owcrobots.frc.controls.ERobotDrive;
import com.owcrobots.frc.controls.Motor;
import com.owcrobots.frc.mechanism.SimGyro;
import com.owcrobots.frc.utils.Constants;
import com.owcrobots.frc.utils.SimPID;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.SerialPort.Port;


public class Subsystems {	
	
	// Motors
	public static Motor rightFrontDrive;
	public static Motor rightRearDrive;
	public static Motor leftFrontDrive;
	public static Motor leftRearDrive;
	public static Motor rightArmMotor;
	public static Motor leftArmMotor;
	public static Motor forkliftMotor;
	public static Motor conveyorMotor;
	
	// Drive
	public static ERobotDrive robotDrive;
	
	// Encoders
	public static Encoder rightDriveEncoder;
	public static Encoder leftDriveEncoder;
	public static EEncoder forkliftEncoder;
	
	// Sensor
	public static DigitalInput toteDetectionSensor;
	public static SimGyro gyroSensor;
	
	//Solenoid - Gear control
	public static DoubleSolenoid gearShiftSolenoid;
	// USB
	public static EJoystick	driveJoystick;
	public static EGamepad controlGamepad;
	
	// Power Panel
	public static PowerDistributionPanel powerPanel;
	
	// Bling
	public static SerialPort blingPort;
	
	public static Compressor compressor;
 	
 	// PIDs
 	public static SimPID forkliftPID;
 	public static SimPID gyroPID;
 	public static SimPID encoderPID;
 	
 	public static DigitalInput[] inputs;

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

		
		//Solenoid - Gear shift
		gearShiftSolenoid = new DoubleSolenoid(Constants.getConstantAsInt(Constants.COMPRESSOR_CHANNEL), 
				Constants.getConstantAsInt(Constants.SOLENOID_SHIFTER_CHANNEL1),
				Constants.getConstantAsInt(Constants.SOLENOID_SHIFTER_CHANNEL2));
		
		// Sensors
		toteDetectionSensor = new DigitalInput(Constants.getConstantAsInt(Constants.DIO_TOTE_DETECT_SENSOR));
		gyroSensor = new SimGyro(Constants.getConstantAsInt(Constants.AIO_GYRO_SENSOR));
		gyroSensor.initGyro();

		// Bling
		if (Constants.getConstantAsInt(Constants.BLING_ENABLED) > 0) {
			blingPort = new SerialPort(9600, Port.kMXP);
		}
		
		initPID();

		inputs = new DigitalInput[Constants.getConstantAsInt(Constants.NUM_AUTO_SELECT)];

		for(int i = 0; i < Constants.getConstantAsInt(Constants.NUM_AUTO_SELECT); ++i) {
			inputs[i] = new DigitalInput(i + Constants.getConstantAsInt(Constants.FIRST_DIGITAL_SELECT));
		}
	}
	
	/**
	 * Public for testing purposes. Initializes the PID controllers.
	 */
	public static void initPID() {
		//PIDs
		forkliftPID = new SimPID(
				Constants.getConstantAsDouble(Constants.FORKLIFT_PID_P),
				Constants.getConstantAsDouble(Constants.FORKLIFT_PID_I),
				Constants.getConstantAsDouble(Constants.FORKLIFT_PID_D),
				Constants.getConstantAsDouble(Constants.FORKLIFT_PID_E)
				);

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
	 * Read in the encoder values from the autonomous config file. TODO:
	 * Integrate this with Georges' Constants class.
	 */
	public static void readEncoderValues(double encoderADistancePerPulseOverride, double encoderBDistancePerPulseOverride) {
		double encoderADistancePerPulse = encoderADistancePerPulseOverride;
		double encoderBDistancePerPulse = encoderBDistancePerPulseOverride;
		
		try {
			List<String> guavaResult = Files.readAllLines(new File(Constants.getConstant(Constants.CALIBRATION_FILE_LOC)).toPath(), Charsets.UTF_8);
			Iterable<String> guavaResultFiltered = Iterables.filter(guavaResult, AutoHelper.SKIP_COMMENTS);

			String[] s = Iterables.toArray(AutoHelper.SPLITTER.split(guavaResultFiltered.iterator().next()), String.class);

			if (encoderADistancePerPulse == 0) {
				encoderADistancePerPulse = Double.parseDouble(s[0]);
			}
			if (encoderBDistancePerPulse == 0) {
				encoderBDistancePerPulse = Double.parseDouble(s[1]);
			} 

			System.out.println("EncoderADistancePerPulse:" + encoderADistancePerPulse + ", EncoderBDistancePerPulse:" + encoderBDistancePerPulse);
			leftDriveEncoder.setDistancePerPulse(encoderADistancePerPulse);
			rightDriveEncoder.setDistancePerPulse(encoderBDistancePerPulse);
		} catch (IOException e) {
			System.out.println("Calibration file read error!");
			leftDriveEncoder.setDistancePerPulse(encoderADistancePerPulseOverride);
			rightDriveEncoder.setDistancePerPulse(encoderBDistancePerPulseOverride);
		}
	}
}
