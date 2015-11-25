package ca.team2706.frc.autonomous;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import com.google.common.base.Predicate;
import com.google.common.base.Splitter;

public class AutoHelper {
	
	/**
	 * A splitter to use when splitting strings by commas
	 */
	public static final Splitter SPLITTER = Splitter.on(',').trimResults().omitEmptyStrings();

	/**
	 * A filter to skip comments
	 */
	public final static Predicate<String> SKIP_COMMENTS = new Predicate<String>() {
		public boolean apply(String str) {
			return !str.startsWith("//");
		}
	};	
	
	/**
	 * Writes a String to the file specified
	 * @param line The String to write to the file
	 * @param file The file to write to
	 * @return Whether the operation was successful or not
	 */
	public static boolean writeLineToFile(String line, File file) {
		// Declare streams
		PrintStream stream = null;
		FileOutputStream fileStream = null;
		
		try {
			// Create file in case it does not exist
			file.createNewFile();
			
			// Initialize streams
			fileStream = new FileOutputStream(file, false);
			stream = new PrintStream(fileStream);
		} catch (IOException e) {
			e.printStackTrace();
			return false;	
		}
		
		// Print line to file
		stream.println(line);
		
		try {
			// Close streams
			fileStream.close();
			stream.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}
}
