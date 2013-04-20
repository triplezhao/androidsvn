package pigframe.cache;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import pigframe.util.PigLog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;

/**
 * Utility for package of cache.
 * 
 * <p/>Convenience for check string, rw file, image type convert.
 * 
 * @since 1.0, 04.23.2011
 * @version 1.0, 04.23.2011
 */
public final class Utils {

	/** File path split-char. */
	public static final String ROOT_FILE_PATH = "/";
	
	/** Base file path label of project app cache. */
	public static final String CACHE_FILE_PATH = "pkm_cache";
	
	/** Image file path label. */
	public static final String IMAGE_CACHE_FILE_PATH = "image_cache";
	
	/** Image file path label. */
	public static final String COMMON_CACHE_FILE_PATH = "common_cache";
	
	/** Service interface url string-prefix. */
	public final static String INTERFACE_URL = "http://";//img1.qiyipic.com/farm
	
	/** App file path prefix. */
	public final static String APP_FILE_PATH_START = "/data/data/";
	
	/** Package name of cache. */
	public final static String APP_CACHE_PACKAGENAME_LABEL = "cache";
	
	/**
	 * String Utility
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		return (null == str || "".equals(str.trim())) ? true : false;
	}
	
	/**
	 * File path of disk cache.
	 * 
	 * @param context
	 * @param cacheLabel (Utils.IMAGE_CACHE_FILE_PATH and Utils.COMMON_CACHE_FILE_PATH)
	 * @return
	 */
	public static String getDiskCachePath(Context context, String cacheLabel) {
		if(!StorageCheckor.checkSdcard(context)) {
			return "";
		}
//		
//		String sdPath = StorageCheckor.getExternalMemoryPath();
		
		
		String startFilePath = DataFile.getCachePath();
		if(DataFile.isBlank(DataFile.getCachePath()))
		{
			String packageName = Utils.class.getPackage().getName();
			String tempLabel = "." + APP_CACHE_PACKAGENAME_LABEL;
			if(Utils.isEmpty(packageName))
				return "";
			if(packageName.endsWith(tempLabel)) {
				packageName = packageName.substring(0, packageName.length() - tempLabel.length());
			}
			startFilePath = APP_FILE_PATH_START + packageName + "/" + "cache" + "/";
		}
		else
		{
			startFilePath = DataFile.getCachePath();
		}
		System.out.println("----------------------startFilePath:" + startFilePath);
		
		checkAndCreateCachePath(startFilePath + ROOT_FILE_PATH);
    	startFilePath += ROOT_FILE_PATH + CACHE_FILE_PATH + ROOT_FILE_PATH + cacheLabel;
    	PigLog.d("startFilePath");
		return startFilePath;
	}
	
	/**
	 * Check and mkdir file path.
	 * 
	 * @param startFilePath
	 */
	static void checkAndCreateCachePath(String startFilePath) {
    	File file = new File(startFilePath);
    	if(!file.exists()) {
    		file.mkdir();
    	} 
    	startFilePath += CACHE_FILE_PATH;
    	file = new File(startFilePath);
    	if(!file.exists()) {
    		file.mkdir();
    	}
    	String[] cachePath = new String[]{
    			IMAGE_CACHE_FILE_PATH, COMMON_CACHE_FILE_PATH
    	};
    	
    	for(int i = 0; i < cachePath.length; i++) {
    		file = new File(startFilePath + ROOT_FILE_PATH + cachePath[i]);
    		if(!file.exists()) {
    			file.mkdir();
    		}
    	}
    }

	/**
	 * Bitmap convert for ByteArrayOutputStream.
	 * 
	 * @param bitmap
	 * @param toBaos
	 * @return
	 */
	public static boolean Bitmap2ByteArrayStreamOutputStream(Bitmap bitmap, ByteArrayOutputStream toBaos) {
		if(null == toBaos) 
			toBaos = new ByteArrayOutputStream();
       return bitmap.compress(Bitmap.CompressFormat.PNG, 100, toBaos);
	}
	
	/**
	 * Drawable convert for Bitmap.
	 * 
	 * @param drawable
	 * @return
	 */
	public static Bitmap drawable2Bitmap(Drawable drawable) {
		return null == drawable ? null :((android.graphics.drawable.BitmapDrawable) drawable).getBitmap(); 
	}
	
	/**
	 * Bitmap convert for Drawable
	 * 
	 * @param bitmap
	 * @return
	 */
	public static Drawable bitmap2Drawable(Bitmap bitmap) {
		return null == bitmap ? null : new android.graphics.drawable.BitmapDrawable(null, bitmap);
	}
	
	/**
	 * Write file with buffer.
	 * 
	 * @param buffer
	 * @param file
	 */
	public static void writeFile(byte[] buffer, File file) {
		FileOutputStream out = null;
        try {
        	out = new FileOutputStream(file);
            out.write(buffer);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace(); 
        } finally{
        	if(null != out)
				try {
					out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	out = null;
        }
	}
	
	/**
	 * Read Bitmap from local file.
	 * 
	 * @param filePath
	 * @return
	 */
	public static Bitmap readBitmapFile(String filePath) {
		File f = new File(filePath);
		if(!f.exists()) {
			return null;
		}
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		Bitmap bitmap = null;
		try {
			fis = new FileInputStream(f);
			bis = new BufferedInputStream(fis);
			try {
				bitmap = BitmapFactory.decodeStream(bis);
//				bitmap = XBitmapFactory.createScaledBitmapFromStream(is, srcName, maxW, maxH, pad);
			} catch (OutOfMemoryError e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				PigLog.d("OutOfMemoryError");
				System.gc();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != bis) {
				try {
					bis.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				bis = null;
			}
			if (null != fis) {
				try {
					fis.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				fis = null;
			}
		}
		return bitmap;
	}
	public static void recycleBitmap(Bitmap bitmap)
	{
		if(null != bitmap && !bitmap.isRecycled())
		{
			bitmap.recycle();
		}
		
	}
	

	public static int getRatingTextStrId(int rate, String cid)
	{
		
		return 0;
	}
}
