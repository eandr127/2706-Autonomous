package ca.team2706.frc.mechanism;

import edu.wpi.first.wpilibj.DigitalOutput;

public class FakeForklift {

	private final DigitalOutput[] leds;
	private int currentLevel = 0;
	
	public FakeForklift(int[] channels) {
		//Make sure that the amount of channels given was correct
		if(channels.length != 4)
			System.err.println("There was an incorrect number of channels given");
		
		//Create a DigitalOutput for each channel
		DigitalOutput[] leds = new DigitalOutput[4];
		for(int i = 0; i < 4; i++) {
			leds[i] = new DigitalOutput(channels[i]);
		}
		this.leds = leds;
	}
	
	/**
	 * Raise the forklift level by one
	 * @return true unless at the highest forklift level
	 */
	public boolean raiseForklift() {
		//Make sure forklift isn't at its highest level already
		if(currentLevel == 3)
			return false;
		
		//Increment the current level, then 
		//set the forklift to that level
		setLevel(++currentLevel);
		return true;
	}
	
	/**
	 * Lower the forklift level by one
	 * @return true unless at the lowest forklift level
	 */
	public boolean lowerForlift() {
		//Make sure forklift isn't at its lowest level already
		if(currentLevel == 0)
			return false;
		
		//Decrement the current level, then 
		//set the forklift to that level
		setLevel(--currentLevel);
		return true;
		
	}
	
	private void setLevel(int level) {
		//Turn off all LEDs
		for(DigitalOutput led : leds)
			led.set(false);
		
		//Turn the one needed on
		leds[level].set(true);
	}
}
