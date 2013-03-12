package edu.channel4.mm.db.android.util;


// TODO: I should bake in some "Log with a Toast too" functionality into this.
public class Log {

	public static void v(String message) {
		android.util.Log.v(Log.getTag(), message);
	}

	public static void d(String message) {
		android.util.Log.d(Log.getTag(), message);
	}

	public static void i(String message) {
		android.util.Log.i(Log.getTag(), message);
	}

	public static void w(String message) {
		android.util.Log.w(Log.getTag(), message);
	}

	public static void e(String message) {
		android.util.Log.e(Log.getTag(), message);
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
