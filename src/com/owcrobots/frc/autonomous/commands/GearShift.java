package com.owcrobots.frc.autonomous.commands;

import com.owcrobots.frc.autonomous.AutoCommand;
import com.owcrobots.frc.robot.Subsystems;

public class GearShift implements AutoCommand {

	private final boolean highGear;
	
	public GearShift(boolean highGear) {
		this.highGear = highGear;
	}
	
	@Override
	public void initialize() {
		Subsystems.robotDrive.gearControl(highGear);
		
	}

	@Override
	public boolean tick() {
		// Doesn't need a tick
		return false;
	}

	@Override
	public void cleanup() {
		// No cleanup necessary
	}

}
