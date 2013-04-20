package pigframe.cache;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

/**
 * Disk Storage for recently used of image data.
 * 
 * <p/>Recently used of Image cache data write to disk.
 * 
 * @since 1.0, 04.23.2011
 * @version 1.0, 04.23.2011
 */
class DiskImageCacheManager {

	/** This dir of disk allow used max bytes size. */
	private static final int diskImageCacheMaxSize = 20 * (1 << 20);

	/** Current disk used bytes size. */
	private static int currentDiskImageCountSize;

	/** Singleton intance. */
	private static DiskImageCacheManager _manager;

	/** Map for key (url) and value(true-filepath and create time). */
	private static Map<String, REntry> _diskImageCache;

	/** True-filepath for disk cache. */
	private static String diskImageCachePath;

	/** Whether already init. */
	private static boolean isInit = false;
	
	/**
	 * Initialize app and load relation data.
	 */
	static {
		configure();
	}
	
	/**
	 * Configure.
	 * @return 
	 */
	private static void configure() {
		_diskImageCache = new HashMap<String, REntry>();
		diskImageCachePath = Utils.getDiskCachePath(null,
				Utils.IMAGE_CACHE_FILE_PATH);
		System.out.println("diskImageCachePath:" + diskImageCachePath);
		File f = new File(diskImageCachePath);
		File[] files = f.listFiles();
		if (null != files && files.length > 0) {
			for (int i = 0; i < files.length; i++) {
				currentDiskImageCountSize += files[i].length();
				_diskImageCache.put(files[i].getName(),
						new REntry(files[i].getPath(), files[i].lastModified()));
			}
		}
		isInit = true;
	}
	
	/**
	 * Construct func.
	 */
	private DiskImageCacheManager() {
		if(!isInit) {
			configure();
		}
	}
	
	public static void reset() {
		// TODO Auto-generated method stub
		if(null != _diskImageCache){
			_diskImageCache.clear();
		}
		isInit = false;
		configure();
		
	}

	/**
	 * Singleton Construct.
	 * 
	 * @return
	 */
	static synchronized DiskImageCacheManager getInstance() {
		if (null == _manager) {
			_manager = new DiskImageCacheManager();
		}
		return _manager;
	}

	/**
	 * Put relation-data.
	 * 
	 * @param keyUrl
	 * @return
	 */
	boolean putDiskImage(String keyUrl) {
		String fileName = netUrl2fileName(keyUrl);
		if (Utils.isEmpty(fileName))
			return false;
		File file = new File(diskImageCachePath + Utils.ROOT_FILE_PATH
				+ fileName);
		if (null == _diskImageCache.get(file.getName()))
			_diskImageCache.put(file.getName(), new REntry(file.getPath(),
					System.currentTimeMillis()));
		return true;
	}

	/**
	 * Get Drawable-value based on key.
	 * 
	 * @param keyUrl
	 * @return
	 */
	Drawable getDrawable(String keyUrl) {
		Bitmap bitmap = getBitmap(keyUrl);
		Drawable drawable = null;
		
		if(null != bitmap)
		{
			drawable = Utils.bitmap2Drawable(bitmap);
//			GUtils.recycleBitmap(bitmap);
		}
		
		return drawable;
	}
	
	/**
	 * Get getBitmap-value based on key.
	 * 
	 * @param keyUrl
	 * @return
	 */
	Bitmap getBitmap(String keyUrl) {
		String key = netUrl2fileName(keyUrl);
		if (Utils.isEmpty(key))
			return null;
		REntry entry = _diskImageCache.get(key);
		if (null == entry)
			return null;
		String filePath = entry.name;
		if (Utils.isEmpty(filePath))
			return null;
		System.out.println("DiskImageCacheManager getImageUri ok. " + keyUrl);
		return Utils.readBitmapFile(filePath);
	}
	
	/**
	 * Net url convert local file name.
	 * 
	 * @param keyUrl
	 * @return
	 */
	private String netUrl2fileName(String keyUrl) {
//		if (null == keyUrl || !keyUrl.startsWith(Utils.INTERFACE_URL))
//			return "";
		if (null == keyUrl)
			return "";
		String key = keyUrl.substring(Utils.INTERFACE_URL.length());
		return key = key.replaceAll("/", "_");
	}

	/**
	 * Check use current disk size exceed allow max-size.
	 * 
	 * @return
	 */
	private boolean checkCountSizeExceedMaxSize() {
		return currentDiskImageCountSize >= diskImageCacheMaxSize;
	}

	/**
	 * Write image-cache data to disk on exit app.
	 * 
	 * @param _manager2
	 */
	void writeImageCacheToDisk(ImageCacheManager _manager2) {
		Iterator<String> iterator = _manager2.ketSet().iterator();
		String keyUrl = null;
		Drawable drawable = null;
		while (iterator.hasNext()) {
			keyUrl = iterator.next();
			drawable = _manager2.getCache(keyUrl);
			if (!Utils.isEmpty(keyUrl) && null != drawable) {
				writeImageToDisk(keyUrl, drawable);
			}
		}
		while (checkCountSizeExceedMaxSize()) {
			deleteFIFOData(_diskImageCache.size() / ICacheManager.DELETE_PERCENT);
		}
	}

	/**
	 * Clear used intance.
	 */
	void clear() {
		_diskImageCache.clear();
		_diskImageCache = null;
		_manager = null;
		isInit = false;
	}

	/**
	 * Sort for Map.
	 * 
	 * @param uHashMap
	 * @return
	 */
	static List<Map.Entry<String, REntry>> sortMap(Map<String, REntry> uHashMap) {
		List<Map.Entry<String, REntry>> sortedList = new ArrayList<Map.Entry<String, REntry>>(
				(uHashMap.entrySet()));
		Collections.sort(sortedList, new MapSorter());
		return sortedList;
	}

	/**
	 * Delete disk file based on FIFO.
	 * 
	 * @param deleteCount
	 */
	void deleteFIFOData(int deleteCount) {
		if (_diskImageCache.size() < deleteCount)
			return;
		List<Map.Entry<String, REntry>> list = sortMap(_diskImageCache);
		int size = list.size();
		String temp = null;
		File file = null;
		long length = 0L;
		for (int i = 0; i < size; i++) {
			Map.Entry<String, REntry> entry = list.get(i);
			if (null == entry)
				continue;
			temp = entry.getValue().name;
			if (Utils.isEmpty(temp))
				continue;
			file = new File(temp);
			length = file.length();
			if (!file.exists() || file.isDirectory())
				continue;
			file.delete();
			_diskImageCache.remove(entry.getKey());
			currentDiskImageCountSize -= length;
			if ((i + 1) == deleteCount)
				break;
		}
	}

	/**
	 * Write image to disk.
	 * 
	 * @param keyUrl
	 * @param drawable
	 * @return
	 */
	boolean writeImageToDisk(String keyUrl, Drawable drawable) {
		if (null == drawable)
			return false;
		while (checkCountSizeExceedMaxSize()) {
			deleteFIFOData(_diskImageCache.size() / ICacheManager.DELETE_PERCENT);
		}

		String fileName = netUrl2fileName(keyUrl);
		if (Utils.isEmpty(fileName) || null == drawable)
			return false;
		File file = new File(diskImageCachePath + Utils.ROOT_FILE_PATH
				+ fileName);
		System.out.println("image w filepath:" + file.getPath());
		if (file.exists())
			return false;

		Bitmap bitmap = Utils.drawable2Bitmap(drawable);
		if(null == bitmap)
		{
			return false;
		}

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		boolean b = Utils.Bitmap2ByteArrayStreamOutputStream(bitmap, baos);
		System.out.println("keyUrl b:" + keyUrl + "," + b + "," + baos.size());

		if (baos.size() < 1)
			return false;

		currentDiskImageCountSize += baos.size();
		Utils.writeFile(baos.toByteArray(), file);
		bitmap = null;
		baos = null;
		return true;
	}
}

/**
 * Entity bean of cache-value data.
 * 
 * <p/>Used for Comparable and Serializable.
 * 
 * @author Mayue (ymma317@gmail.com)
 * @since 1.0, 04.23.2011
 * @version 1.0, 04.23.2011
 */
class REntry implements Comparable<REntry>, Serializable  {

	private static final long serialVersionUID = 1L;
	
	String name;
	long date;

	public REntry(String name, long date) {
		this.name = name;
		this.date = date;
	}

	public String toString() {
		return name + " " + date;
	}

	public int compareTo(REntry another) {
		// TODO Auto-generated method stub
		return (int) (date - another.date);
	}
}

/**
 * Sort and compare func.
 * 
 * <p/>Used for Comparable object.
 * 
 * @author Mayue (ymma317@gmail.com)
 * @since 1.0, 04.23.2011
 * @version 1.0, 04.23.2011
 */
class MapSorter implements Comparator<Map.Entry<String, REntry>> {
	public int compare(Entry<String, REntry> r1, Entry<String, REntry> r2) {
		// TODO Auto-generated method stub
		return r1.getValue().compareTo(r2.getValue());
	}
}
