package com.owcrobots.frc.autonomous;

import java.util.ArrayList;
import java.util.List;

import com.owcrobots.frc.autonomous.modes.BasicRobotSetMode;
import com.owcrobots.frc.autonomous.modes.DriveForwardMode;
import com.owcrobots.frc.autonomous.modes.EmptyMode;

import edu.wpi.first.wpilibj.DigitalInput;

public class AutoModeSelector {
	private static List<Class<? extends AutoMode>> modes = new ArrayList<>();
	
	static {
		//TODO: Add all autonomous modes here.
		
		// 000
		modes.add(BasicRobotSetMode.class);
		// 001
		modes.add(DriveForwardMode.class);
		// 010
		modes.add(EmptyMode.class);
		// 011
		modes.add(EmptyMode.class);
		// 100
		modes.add(EmptyMode.class);
		// 101
		modes.add(EmptyMode.class);		
		// 110
		modes.add(EmptyMode.class);
		// 111
		modes.add(EmptyMode.class);
		
		/**
		 * We need:
		 * 000 - Do nothing
		 * 001 - Drive to auto zone
		 * 010 - Drive to auto zone on top of tote
		 * 011 - Drive to auto zone on top of bin
		 */
	}
	
	/**
	 * Selects a mode using the give array of DigitalInputs.
	 * 
	 * Treats inputs as an array of binary numbers. inputs[0] is treated
	 * as the least significant digit (2^0 = 1) and inputs[inputs.length-1] 
	 * is treated as the most significant digit (2^(inputs.length-1)).
	 * 
	 * @param inputs The list of inputs.
	 * @return The AutoMode to use.
	 */
	public AutoMode selectMode(DigitalInput[] inputs) {
		try {
			boolean[] newInputs = new boolean[inputs.length];
			
			for (int i = 0; i < inputs.length; i++) {
				// A 0 from the switch means that that switch is turned on.
				newInputs[i] = !inputs[i].get();
				System.out.println("Input " + i + " = " + newInputs[i]);
			}
			// TODO: encodeBools() works. Verified by test. So that means there's 
			// something funny with the switches. Figure it out next time!
			// Make a new instance of the value at the index from the binary!
			int encodeBool = encodeBools(newInputs);
			
			if (encodeBool >= modes.size()) {
				encodeBool = 0;
			}
			
			System.out.println("Selected mode is: " + modes.get(encodeBool).getName());
			
			return (modes.get(encodeBool).newInstance());
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public int encodeBools(boolean[] inputs) {
		// Keeps the power of 2
		int power = 1;

		// Keeps the index into the modes array.
		int modeIndex = 0;
		for (int i = 0; i < inputs.length; i++) {
			if (inputs[i]) {
				modeIndex += power; 
			}
			power *= 2;
		}
		
		return modeIndex;
	}
}
