package pigframe.cache;

import java.io.File;
import java.net.URI;

import pigframe.util.PigLog;
import android.content.Context;
import android.os.StatFs;

public class DataFile extends File {
	
/**
	 * 
	 */
	private static final long serialVersionUID = -6060225585308779771L;
//	private static String sDataPath = "/data/data/com.fgh.demo";
//	private static String sCachePath = "/data/data/com.fgh.demo/cache";
	private static String sDataPath = "";
	private static String sCachePath = "";
	
	public DataFile(String path) {
		super(path);
		// TODO Auto-generated constructor stub
	}

	public DataFile(URI uri) {
		super(uri);
		// TODO Auto-generated constructor stub
	}

	public DataFile(File dir, String name) {
		super(dir, name);
		// TODO Auto-generated constructor stub
	}

	public DataFile(String dirPath, String name) {
		super(dirPath, name);
		// TODO Auto-generated constructor stub
	}
	
	public static String initDataPath(Context context) {
		if(null != context)
		{
			sDataPath = context.getFilesDir().getParentFile().getAbsolutePath();
			sCachePath = context.getCacheDir().getAbsolutePath();
		}
		
		return sDataPath;
	}
	
	public static String getDataPath() {
		return sDataPath;
	}
	
	public static String getCachePath() {
		return sCachePath;
	}
	
	public static int getDirSize(String dirPath) {
		if(isBlank(dirPath))
		{
			return 0;
		}
		StatFs fs = new StatFs(dirPath);
//		File dirFile = new File(dirPath);
		float M_FLOAT = 1<<20;
		PigLog.d("dirPath:"+dirPath+"\n"
				+"getAvailableBlocks:"+fs.getAvailableBlocks()+"\n"
				+"getFreeBlocks:"+fs.getFreeBlocks()+"\n"
				+"getBlockCount:"+fs.getBlockCount()+"\n"
				+"getBlockSize"+fs.getBlockSize()+"\n"
				+"getBlockCount="+((fs.getBlockCount()*fs.getBlockSize())/M_FLOAT)+"\n"
				+"getAvailableBlocks="+((fs.getAvailableBlocks()*fs.getBlockSize())/M_FLOAT)+"\n"
				+"getFreeBlocks="+((fs.getFreeBlocks()*fs.getBlockSize())/M_FLOAT)+"\n");
		return 0;
	}
	
	public static boolean isBlank(String text) {
		
		return (null == text || 0 == text.trim().length());
	}
	
	public static boolean deleteDirPath(String dirPath) {
		if(isBlank(dirPath))
		{
			return false;
		}
		File dirFile = new File(dirPath);
		deleteDirPath(dirFile, true, true);
		
		return false;
	}
	
	public static boolean deleteDirPath(String dirPath, boolean keepRootDir, boolean keepDir) {
		if(isBlank(dirPath))
		{
			return false;
		}
		File dirFile = new File(dirPath);
		deleteDirPath(dirFile, keepRootDir, keepDir);
		
		return false;
	}
	
	public static boolean deleteDirPath(File dirFile, boolean keepRootDir, boolean keepDir) {
		
		if(null == dirFile || !dirFile.exists())
		{
			return false;
		}
		if(dirFile.isDirectory())
		{
			File[] files = dirFile.listFiles();
			if(null != files)
			{
				int len = files.length;
				for(int i = 0; i < len; i++)
				{
					deleteDirPath(files[i], keepDir, keepDir);
				}
			}
		}
		if(dirFile.isFile() || !keepRootDir)
		{
			dirFile.delete();
		}
		
		
		return false;
	}
	

}
