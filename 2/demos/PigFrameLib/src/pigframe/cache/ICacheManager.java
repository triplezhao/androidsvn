package pigframe.cache;

/**
 * Interface for cache manager.
 * 
 * <p/>Operator for get put clear data.
 * 
 * @since 1.0, 04.23.2011
 * @version 1.0, 04.23.2011
 */
public interface ICacheManager<T> {
	
	/*
	 * Each delete data PERCENT (Count / DELETE_PERCENT).
	 */
	public static final int DELETE_PERCENT = 2;
	
	/*
	 * Put data to cache.
	 */
	public void putCache(String keyUrl, T obj);
	
	/**
	 * Get data from cache.
	 * 
	 * @param keyUrl
	 * @return
	 */
	public T getCache(String keyUrl);
	
	/**
	 * Clear and write data to disk.
	 */
	public void clearAndWriteDisk();
	
//	public static void reset();
}