package pigframe.cache;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import android.graphics.drawable.Drawable;

/**
 * Manage for image-cache.
 * 
 * <p/>Cache data to memory and disk based on strategy.
 * 
 * @since 1.0, 04.23.2011
 * @version 1.0, 04.23.2011
 */
public class ImageCacheManager implements ICacheManager<Drawable> {
	
	/** Singleton intance. */
	private static ImageCacheManager _manager;

	/** Map for key (url) and value(SoftReference of drawable). */
	private static Map<String, SoftReference<Drawable>> _imageCache;
	
	/** Disk cache intance.*/
	private static DiskImageCacheManager diskManager;
	
	/** Whether already init. */
	private static boolean isInit = false;
	
	/**
	 * Initialize app and load relation data.
	 */
	static {
		configure();
	}
	
	/**
	 * Construct func.
	 */
	private ImageCacheManager() {
		if(!isInit) {
			configure();
		}
	}
	
	/**
	 * Singleton Construct.
	 * 
	 * @return
	 */
	public static synchronized ImageCacheManager getInstance() {
		if(null == _manager) {
			_manager = new ImageCacheManager();
		}
		return _manager;
	}
	
	/*
	 * 
	 * (non-Javadoc)
	 * @see com.qiyi.qitan.cache.ICacheManager#putCache(java.lang.String, java.lang.Object)
	 */
	public void putCache(String keyUrl, Drawable drawable) {
		if(Utils.isEmpty(keyUrl) || null == drawable)
			return;
		_imageCache.put(keyUrl, new SoftReference<Drawable>(drawable));
		boolean r = diskManager.putDiskImage(keyUrl);
		System.out.println("ImageCacheManager " + keyUrl + " write image info to disk-cache result:" + r);
		r = diskManager.writeImageToDisk(keyUrl, drawable);
		System.out.println("ImageCacheManager " + keyUrl + " write image file to disk result:" + r);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.qiyi.qitan.cache.ICacheManager#getCache(java.lang.String)
	 */
	public Drawable getCache(String keyUrl) {
		if(Utils.isEmpty(keyUrl))
			return null;
		SoftReference<Drawable> softReference = _imageCache.get(keyUrl);
		Drawable drawable = null;
		if(null != softReference) {
			drawable = softReference.get();
			if(null != drawable)
				return drawable;
		}
		return diskManager.getDrawable(keyUrl);
	}

	/**
	 * Doing configure.
	 */
	private static void configure() {
		// TODO Auto-generated method stub
		_imageCache = new HashMap<String, SoftReference<Drawable>>();
		diskManager = DiskImageCacheManager.getInstance();
		isInit = true;
	}
	
	/**
	 * Clear and write data to disk.
	 */
	public void clearAndWriteDisk() {
		// TODO Auto-generated method stub
		diskManager.writeImageCacheToDisk(_manager);
		diskManager.clear();
		_imageCache.clear();
		_imageCache = null;
		_manager = null;
		isInit = false;
	}
	
	/**
	 * KeySet.
	 * 
	 * @return
	 */
	Set<String> ketSet() {
		return _imageCache.keySet();
	}
	
	public static void reset() {
		
		isInit = false;
		
		if(null != _imageCache){
			_imageCache.clear();
		}
		
		DiskImageCacheManager.reset();
		
		configure();
		
	}
	
}