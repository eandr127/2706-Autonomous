package ca.team2706.frc.utils;

import java.io.IOException;
import java.io.PrintStream;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Logging {
	
	private static final Logger logger = LogManager.getRootLogger();
	
	@SuppressWarnings("unused")
	private static final PrintStream OLD_SYSTEM_OUT = System.out;
	@SuppressWarnings("unused")
	private static final PrintStream OLD_SYSTEM_ERR = System.err;

	/**
	 * Forward all System.out/err.print(ln) calls to Log4j
	 * 
	 * @param debug Whether to enable printing to stdout or not
	 */
	public static void setupLogger(boolean debug) {
		System.setOut(createLoggingProxy(System.out, Level.INFO, debug));
		System.setErr(createLoggingProxy(System.err, Level.ERROR, true));		
	}
	
	private static PrintStream createLoggingProxy(final PrintStream realPrintStream, Level level, boolean enabled) {
		return new PrintStream(realPrintStream) {
			public void write(byte[] b) throws IOException {
			    String string = new String(b);
			    if (!string.trim().isEmpty() && enabled)
			        logger.log(level, string);
			}

			public void write(byte[] b, int off, int len) {
			    String string = new String(b, off, len);
			    if (!string.trim().isEmpty() && enabled)
			        logger.log(level, string);
			}

			public void write(int b) {
			    String string = String.valueOf((char) b);
			    if (!string.trim().isEmpty() && enabled)
			        logger.log(level, string);
			}
		};
	}
}
