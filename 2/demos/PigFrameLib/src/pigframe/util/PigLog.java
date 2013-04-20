package pigframe.util;

import android.text.TextUtils;



public class PigLog {

	private static final boolean logable = true;

	public PigLog() {
	}
	
	public static String tag(Object obj) {
	    
	    if(null != obj)
	    {
	        return obj.getClass().getSimpleName();
	    }
	    
	    return "unkown";
	}
	
	public static void out(String msg) {
		if (logable) {
			if (TextUtils.isEmpty(msg)) {
				System.out.println("msg is none.");
			} else {
				System.out.println(msg);
			}
		}
	}
	
	public static void out_v(String tag, String msg) {
		if (logable) {
			StackTraceElement trace = new Throwable().getStackTrace()[1];
			
			System.out.println(
					"---TAG:" + tag + "---begin---\n" 
					+ "TRACE:\n" + trace.toString() + "\n" 
					+ "MSG:\n" + msg + "\n"
					+"---TAG:" + tag + "---end---\n");
//			new EOFException("log-stack").printStackTrace();
		}
	}

	public static void err(String msg) {
		if (logable) {
			if (TextUtils.isEmpty(msg)) {
				System.err.println("msg is none.");
			} else {
				System.err.println(msg);
			}
		}
	}
	
	/**
	 * Send a VERBOSE log message.
	 * 
	 * @param msg
	 *            The message you would like logged.
	 */
	public static void v(String TAG, String msg) {
		if (logable)
			android.util.Log.v(TAG, msg);
	}

	/**
	 * Send a DEBUG log message.
	 * 
	 * @param msg
	 */
	public static void d(String TAG, String msg) {
		if (logable)
		{
		    android.util.Log.d(TAG, msg);
		    out_v(TAG, msg);
		}
			
	}

	public static void d(String msg) {

		if (logable) {
			StackTraceElement trace = new Throwable().getStackTrace()[1];
			android.util.Log.d(trace.getFileName(),
			        "C:"+trace.getClassName()+"\n"
					+"M: " + trace.getMethodName() + "\n" + "L: "
							+ trace.getLineNumber() + "\n" + msg);
//			new EOFException("log-stack").printStackTrace();
		}

	}
	
	public static int e(String tag, String msg, Throwable tr) {
		
		if(logable)
		{
			return android.util.Log.e(tag, msg, tr);
		}
		
        return 0;
    }

	/**
	 * Send an INFO log message.
	 * 
	 * @param msg
	 *            The message you would like logged.
	 */
	public static void i(String TAG, String msg) {
		if (logable)
			android.util.Log.i(TAG, msg);
	}

	/**
	 * Send an ERROR log message.
	 * 
	 * @param msg
	 *            The message you would like logged.
	 */
	public static void e(String TAG, String msg) {
		if (logable)
			android.util.Log.e(TAG, msg);
	}

	/**
	 * Send a WARN log message
	 * 
	 * @param msg
	 *            The message you would like logged.
	 */
	public static void w(String TAG, String msg) {
		if (logable)
			android.util.Log.w(TAG, msg);
	}

	/**
	 * Send an empty WARN log message and log the exception.
	 * 
	 * @param thr
	 *            An exception to log
	 */
	// public static void w(Throwable thr) {
	// android.util.Log.w(TAG, buildMessage(""), thr);
	// }

	public static boolean isLogable() {
		return logable;
	}

	// public static void setLogable(boolean logable) {
	// Log.logable = logable;
	// }

	/**
	 * Building Message
	 * 
	 * @param msg
	 *            The message you would like logged.
	 * @return Message String
	 */
	protected static String buildMessage(String msg) {
		StackTraceElement caller = new Throwable().fillInStackTrace()
				.getStackTrace()[2];

		return new StringBuilder().append(caller.getClassName()).append(".")
				.append(caller.getMethodName()).append("(): ").append(msg)
				.toString();
	}

}