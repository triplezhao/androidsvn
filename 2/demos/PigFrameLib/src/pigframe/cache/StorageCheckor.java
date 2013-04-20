package pigframe.cache;

import java.io.File;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import android.widget.Toast;

/**
 * Utility for Check Storage.
 * 
 * <p/>Convenience for check and get data from Storage ExternalMemory (SD card).
 * 
 * @since 1.0, 04.23.2011
 * @version 1.0, 04.23.2011
 */
public class StorageCheckor {
	
	/**
	 * Check sdcard.
	 * 
	 * @param context
	 * @return
	 */
	public static boolean checkSdcard(Context context){
		boolean isToast = (null != context);
		if(Utils.isEmpty(getExternalMemoryPath())) {
			if(isToast)
				Toast.makeText(context, "没有找到SD卡", 1).show();
			return false;
		}
		if(getAvailableExternalMemorySize() <= 0) {
			if(isToast)
				Toast.makeText(context, "SD卡已无剩余空间", 1).show();
			return false;
		}
		return true;
	}
	
	/**
	 * Get ExternalMemory file Path.
	 * 
	 * @return
	 */
	public static String getExternalMemoryPath() {
		String state = Environment.getExternalStorageState();
		if(Environment.MEDIA_MOUNTED.equals(state)) {  
	        File file = Environment.getExternalStorageDirectory();
	        if(null == file)
	        	return "";
	        if(file.exists()) 
	        	return file.getAbsolutePath();
		}
		return "";
	}
	
	/**
	 * GetTotalInternalMemorySize.
	 * 
	 * @return
	 */
	public static long getTotalInternalMemorySize() {  
        File path = Environment.getDataDirectory();  
        StatFs stat = new StatFs(path.getPath());  
        long blockSize = stat.getBlockSize();  
        long totalBlocks = stat.getBlockCount();  
        return totalBlocks * blockSize;
    }  

	/**
	 * GetAvailableInternalMemorySize.
	 * 
	 * @return
	 */
    public static long getAvailableInternalMemorySize() {  
        File path = Environment.getDataDirectory();  
        StatFs stat = new StatFs(path.getPath());  
        long blockSize = stat.getBlockSize();  
        long availableBlocks = stat.getAvailableBlocks();  
        return availableBlocks * blockSize;  
    }  
    
    /**
     * GetAvailableExternalMemorySize.
     * 
     * @return
     */
    public static long getAvailableExternalMemorySize() {  
        long availableExternalMemorySize = 0;  
        if (Environment.getExternalStorageState().equals(  
                Environment.MEDIA_MOUNTED)) {  
            File path = Environment.getExternalStorageDirectory();  
            StatFs stat = new StatFs(path.getPath());  
            long blockSize = stat.getBlockSize();  
            long availableBlocks = stat.getAvailableBlocks();  
            availableExternalMemorySize = availableBlocks * blockSize;  
        } else if (Environment.getExternalStorageState().equals(  
                Environment.MEDIA_REMOVED)) {  
            availableExternalMemorySize = -1;  
        }
        return availableExternalMemorySize;  
    }  
  
    /**
     * GetTotalExternalMemorySize.
     * 
     * @return
     */
    public static long getTotalExternalMemorySize() {  
        long totalExternalMemorySize = 0;  
        if (Environment.getExternalStorageState().equals(  
                Environment.MEDIA_MOUNTED)) {  
            File path = Environment.getExternalStorageDirectory();  
            StatFs stat = new StatFs(path.getPath());  
            long blockSize = stat.getBlockSize();  
            long totalBlocks = stat.getBlockCount();  
            totalExternalMemorySize = totalBlocks * blockSize;  
        } else if (Environment.getExternalStorageState().equals(  
                Environment.MEDIA_REMOVED)) {  
            totalExternalMemorySize = -1;  
        }
        return totalExternalMemorySize;  
    }  
}