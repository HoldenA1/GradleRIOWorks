package frc.team972.util;

public class Logger {
	
	public static void log(String message) {
		System.out.println("[LOG] " + message);
	}
	
	public static void logError(String errorMessage) {
		System.err.println("[ERROR] " + errorMessage);
	}

}
