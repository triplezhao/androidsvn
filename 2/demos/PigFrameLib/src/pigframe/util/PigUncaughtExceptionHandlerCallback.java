package pigframe.util;

public interface PigUncaughtExceptionHandlerCallback {
	
	public boolean handleException(Throwable ex);
}
