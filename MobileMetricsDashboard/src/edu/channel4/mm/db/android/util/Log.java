package edu.channel4.mm.db.android.util;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.widget.Toast;

import com.google.inject.Inject;

/**
 * Pieces of this class taken from <a
 * href="https://github.com/roboguice/roboguice/wiki/Logging">RoboGuice's Ln
 * class</a>, with source available <a href=
 * "https://code.google.com/p/roboguice/source/browse/roboguice/src/main/java/roboguice/util/Ln.java?r=be2a3e4b82419dcc221e38067cfc1092dfc6bab5"
 * >here</a>.
 * 
 * <br />
 * <br />
 * Some notes:
 * <ul>
 * <li>TAG is automatically generated for you, as ClassName.methodName</li>
 * <li>Debug and verbose logging are automatically disabled for release builds.</li>
 * <li>
 * Performance of disabled logging is faster than Log due to the use of the
 * varargs. Since your most expensive logging will often be debug or verbose
 * logging, this can lead to a minor performance win.</li>
 * <li>You can override where the logs are written to and the format of the
 * logging.
 * </ul>
 * 
 * @author girum
 */
// TODO: Inject the Context needed in Log.toast()?
public class Log {

   /**
    * config is initially set to BaseConfig() with sensible defaults, then
    * replaced by BaseConfig(ContextSingleton) during guice static injection
    * pass.
    */
   @Inject protected static BaseConfig config = new BaseConfig();

   /**
    * print is initially set to Print(), then replaced by guice during static
    * injection pass. This allows overriding where the log message is delivered
    * to.
    */
   @Inject protected static Print print = new Print();

   private Log() {
   }

   public static void v(String message) {
      if (config.minimumLogLevel <= android.util.Log.VERBOSE)
         android.util.Log.v(Log.getTag(), message);
   }

   public static void d(String message) {
      if (config.minimumLogLevel <= android.util.Log.VERBOSE)
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

   public static void e(String message, Throwable exception) {
      android.util.Log.e(Log.getTag(), message, exception);
   }

   public static void toastD(Context context, String message) {
      android.util.Log.d(Log.getTag(), message);
      Toast.makeText(context, message, Toast.LENGTH_LONG).show();
   }

   public static void toastE(Context context, String message) {
      android.util.Log.e(Log.getTag(), message);
      Toast.makeText(context, "ERROR: " + message, Toast.LENGTH_LONG).show();
   }

   public static void toastE(Context context, Exception e) {
      android.util.Log.e(Log.getTag(), e.getMessage(), e);
      Toast.makeText(context, "ERROR: " + e.getMessage(), Toast.LENGTH_LONG)
               .show();
   }

   /**
    * Use Java reflection to autogenerate the TAG for this log. TAGs look like:
    * "ClassName.methodCalled()"
    */
   private static String getTag() {
      // Grab the StackTraceElement of the method that's calling this Log.
      final StackTraceElement stackTraceElement = Thread.currentThread()
               .getStackTrace()[4];

      // Parse out the short version of the class name from the Class
      final String shortClassName = stackTraceElement.getClassName().substring(
               stackTraceElement.getClassName().lastIndexOf('.') + 1);

      // Pull out the Method name and class names into a String.
      return shortClassName + "." + stackTraceElement.getMethodName();
   }

   public static Config getConfig() {
      return config;
   }

   public static interface Config {
      public int getLoggingLevel();

      public void setLoggingLevel(int level);
   }

   @SuppressLint("DefaultLocale")
   public static class BaseConfig implements Config {
      protected int minimumLogLevel = android.util.Log.VERBOSE;
      protected String packageName = "";
      protected String scope = "";

      protected BaseConfig() {
      }

      @Inject
      public BaseConfig(Application context) {
         try {
            packageName = context.getPackageName();
            final int flags = context.getPackageManager().getApplicationInfo(
                     packageName, 0).flags;
            minimumLogLevel = (flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0 ? android.util.Log.VERBOSE
                     : android.util.Log.INFO;
            scope = packageName.toUpperCase();

            android.util.Log.d("Log.java",
                     "Configuring Logging, minimum log level is "
                              + logLevelToString(minimumLogLevel));
         }
         catch (Exception e) {
            android.util.Log.e(packageName, "Error configuring logger", e);
         }
      }

      public int getLoggingLevel() {
         return minimumLogLevel;
      }

      public void setLoggingLevel(int level) {
         minimumLogLevel = level;
      }
   }

   public static String logLevelToString(int loglevel) {
      switch (loglevel) {
         case android.util.Log.VERBOSE:
            return "VERBOSE";
         case android.util.Log.DEBUG:
            return "DEBUG";
         case android.util.Log.INFO:
            return "INFO";
         case android.util.Log.WARN:
            return "WARN";
         case android.util.Log.ERROR:
            return "ERROR";
         case android.util.Log.ASSERT:
            return "ASSERT";
      }

      return "UNKNOWN";
   }

   /** Default implementation logs to android.util.Log */
   public static class Print {
      public int println(int priority, String msg) {
         return android.util.Log.println(priority, getScope(5),
                  processMessage(msg));
      }

      protected String processMessage(String msg) {
         if (config.minimumLogLevel <= android.util.Log.DEBUG)
            msg = String.format("%s %s", Thread.currentThread().getName(), msg);
         return msg;
      }

      protected static String getScope(int skipDepth) {
         if (config.minimumLogLevel <= android.util.Log.DEBUG) {
            final StackTraceElement trace = Thread.currentThread()
                     .getStackTrace()[skipDepth];
            return config.scope + "/" + trace.getFileName() + ":"
                     + trace.getLineNumber();
         }

         return config.scope;
      }

   }
}
