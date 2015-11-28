package ca.team2706.frc.mechanism.raspberrypi;

/**
 * TODO: Someone implement this!
 * 
 * This class is an abstraction layer for the Raspberry Pi, 
 * it only needs to get the location relative from the robot of an object
 * 
 * @author eandr127
 *
 */
public abstract class RaspberryPi {
	
	/**
	 * The IP address of the Raspberry Pi
	 */
	protected final String ip;
	
	/**
	 * Creates a new Raspberry Pi instance that takes an IP address
	 * @param ip the IP address of the Raspberry Pi
	 */
	public RaspberryPi(String ip) {
		this.ip = ip;
	}
	
	/**
	 * This should get the X coordinate on a bird's eye view grid
	 *  of the location of the target relative to the robot
	 *  
	 * @param o this method can take anything if you want!
	 * @return The X coordinate of the object
	 */
	public abstract double getObjectX(Object ... o);
	
	/**
	 * This should get the Y coordinate on a bird's eye view grid
	 *  of the location of the target relative to the robot
	 *  
	 * @param o this method can take anything if you want!
	 * @return The Y coordinate of the object
	 */
	public abstract double getObjectY(Object ... o);
}
