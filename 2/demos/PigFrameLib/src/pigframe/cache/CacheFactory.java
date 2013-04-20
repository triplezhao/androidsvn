package pigframe.cache;



import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;


/**
 * Factory for cache (image and text).
 * 
 * <p/>Convenience for create cache-instance based on different type.
 * 
 * @since 1.0, 04.23.2011
 * @version 1.0, 04.23.2011
 */
public class CacheFactory {
	
	/** Type for text cache. */
	public static final byte CACHE_TYPE_COMMON = 0x01;
	
	/** Type for image cache. */
	public static final byte CACHE_TYPE_IMAGE = 0x02;

	/**
	 * Create a intance based on different type 
	 * (CacheFactory.CACHE_TYPE_COMMON and CacheFactory.CACHE_TYPE_IMAGE).
	 * 
	 * @param <T>
	 * @param type
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> ICacheManager<T> creator(byte type) {
		switch(type) {
		case CACHE_TYPE_IMAGE:
			return (ICacheManager<T>) ImageCacheManager.getInstance();
		case CACHE_TYPE_COMMON:
			return (ICacheManager<T>) CommonCacheManager.getInstance();
		}
		return null;
	}
	
	public static void reset() {
		CommonCacheManager.reset();
		ImageCacheManager.reset();
		
	}

	public static void test() {
		/**
		 * Example for common (String) cache. 
		 */
		ICacheManager<String> manager = CacheFactory.creator(CacheFactory.CACHE_TYPE_COMMON);
		for(int i = 1; i <= 20; i++) {
			manager.putCache(Utils.INTERFACE_URL + "/a/b/c/1234567890_" + i + ".jpg", new String("json-data_" + i));
		}
		
		for(int i = 1; i <= 20; i++) {
			String key = Utils.INTERFACE_URL + "/a/b/c/1234567890_" + i + ".jpg";
			String value = manager.getCache(key);
			System.out.println(key + " " + value);
		}
		
		manager.clearAndWriteDisk();
		
		/**
		 * Example for image (Drawable) cache. 
		 */
		ICacheManager<Drawable> manager2 = CacheFactory.creator(CacheFactory.CACHE_TYPE_IMAGE);
		Bitmap bitmap = Utils.readBitmapFile("/mnt/sdcard/appstore/IMAGECACHE/_lanmupic_2010111917390203936.jpg");
		if(null == bitmap)
			return;
		Drawable d = Utils.bitmap2Drawable(bitmap);
		Utils.recycleBitmap(bitmap);
		
		for(int i = 1; i <= 20; i++) {
			manager2.putCache(Utils.INTERFACE_URL + "/a/b/c/1234567890_" + i + ".jpg", d);
		}
		
		for(int i = 1; i <= 20; i++) {
			String key = Utils.INTERFACE_URL + "/a/b/c/1234567890_" + i + ".jpg";
			Drawable value = (Drawable)manager2.getCache(key);
			System.out.println(key + " " + value);
		}
		manager2.clearAndWriteDisk();
		
	}
}