package ca.team2706.frc.utils;

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
	 */
	public static void setupLogger() {
		System.setOut(createLoggingProxy(System.out, Level.INFO));
		System.setErr(createLoggingProxy(System.err, Level.ERROR));
	}
	
	private static PrintStream createLoggingProxy(final PrintStream realPrintStream, Level level) {
		return new PrintStream(realPrintStream) {
			//Implement all print and println methods in PrintStream
			//and log the parameter passed in
	        public void print(final String string) {
	        	logger.log(level, string);
	        }
	        public void print(final boolean b) {
	        	logger.log(level, b+"");
	        }
	        public void print(final char c) {
	        	logger.log(level, c+"");
	        }
	        public void print(final char[] s) {
	        	logger.log(level, s.toString());
	        }
	        public void print(final double d) {
	        	logger.log(level, d+"");
	        }
	        public void print(final float f) {
	        	logger.log(level, f+"");
	        }
	        public void print(final int i) {
	        	logger.log(level, i+"");
	        }
	        public void print(final long l) {
	        	logger.log(level, l+"");
	        }
	        public void print(final Object o) {
	        	logger.log(level, o+"");
	        }
	        public void println(final String x) {
	        	logger.log(level, x+"\n");
	        }
	        public void println(final boolean x) {
	        	logger.log(level, x+"\n");
	        }
	        public void println(final char x) {
	        	logger.log(level, x+"\n");
	        }
	        public void println(final char[] x) {
	        	logger.log(level, x.toString()+"\n");
	        }
	        public void println(final double x) {
	        	logger.log(level, x+"\n");
	        }
	        public void println(final float x) {
	        	logger.log(level, x+"\n");
	        }
	        public void println(final int x) {
	        	logger.log(level, x+"\n");
	        }
	        public void println(final long x) {
	        	logger.log(level, x+"\n");
	        }
	        public void println(final Object x) {
	        	logger.log(level, x+"\n");
	        }
	        public void println() {
	        	logger.log(level, "\n");
	        }
		};
	}
}
