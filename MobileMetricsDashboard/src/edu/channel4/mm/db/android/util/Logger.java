package edu.channel4.mm.db.android.util;

import android.util.Log;

public class Logger {

	public static void v(String message) {
		Log.v(Logger.getTag(), message);
	}

	public static void d(String message) {
		Log.d(Logger.getTag(), message);
	}

	public static void i(String message) {
		Log.i(Logger.getTag(), message);
	}

	public static void w(String message) {
		Log.w(Logger.getTag(), message);
	}

	public static void e(String message) {
		Log.e(Logger.getTag(), message);
	}

	/**
	 * Use Java reflection to autogenerate the TAG for this log. TAGs look like:
	 * 
	 * "ClassName.methodCalled()"
	 */
	private static String getTag() {
		// Grab the StackTraceElement of the method that's calling this Log.
		final StackTraceElement stackTraceElement = Thread.currentThread()
				.getStackTrace()[4];

		// Parse out the short version of the class name from the Class
		final String shortClassName = stackTraceElement.getClassName()
				.substring(
						stackTraceElement.getClassName().lastIndexOf('.') + 1);

		// Pull out the Method name and class names into a String.
		return shortClassName + "." + stackTraceElement.getMethodName();
	}
}
