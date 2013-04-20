package pigframe.cache;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Manage for text-cache.
 * 
 * <p/>Cache data to memory and disk based on strategy.
 * 
 * @since 1.0, 04.23.2011
 * @version 1.0, 04.23.2011
 */
public class CommonCacheManager implements ICacheManager<String>{
	
	/** File name of cache.*/
	private static final String CACHE_FILE_NAME = "cache.dat";
	
	/** True-filepath for disk cache. */
	private static String diskCommonCachePath;
	
	/** Singleton intance. */
	private static CommonCacheManager _manager;
	
	/** Map for key (url) and value(true-filepath and create time). */
	public static HashMap<String, REntry> _commonCache;
	
	/** Cached datas max count on disk. */
	private static int diskCommonCacheMaxCount = 100;
	
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
	 */
	private static void configure() {
		_commonCache = new HashMap<String, REntry>();
		diskCommonCachePath = Utils.getDiskCachePath(null,
				Utils.COMMON_CACHE_FILE_PATH);
//		System.out.println("diskCommonCachePath:" + diskCommonCachePath);
		deserialize();
//		System.out.println("_commonCache:" + _commonCache);
		isInit = true;
	}
	
	/**
	 * Construct func.
	 */
	private CommonCacheManager() {
		if(!isInit) {
			configure();
		}
	}
	
	/**
	 * Singleton Construct.
	 * 
	 * @return
	 */
	public static synchronized CommonCacheManager getInstance() {
		if(null == _manager) {
			_manager = new CommonCacheManager();
		}
		return _manager;
	}

	/**
	 * Delete disk file based on FIFO.
	 * 
	 * @param deleteCount
	 */
	void deleteFIFOData(int deleteCount) {
		if (_commonCache.size() < deleteCount)
			return;
		List<Map.Entry<String, REntry>> list = DiskImageCacheManager.sortMap(_commonCache);
		int size = list.size();
		String temp = null;
		for (int i = 0; i < size; i++) {
			Map.Entry<String, REntry> entry = list.get(i);
			if (null == entry)
				continue;
			temp = entry.getValue().name;
			if (Utils.isEmpty(temp))
				continue;
			_commonCache.remove(entry.getKey());
			if ((i + 1) == deleteCount)
				break;
		}
	}
	
	/*
	 * 
	 * (non-Javadoc)
	 * @see com.qiyi.qitan.cache.ICacheManager#putCache(java.lang.String, java.lang.Object)
	 */
	public void putCache(String keyUrl, String obj) {
		// TODO Auto-generated method stub
		if(Utils.isEmpty(keyUrl) || Utils.isEmpty(obj) || null == _commonCache)
			return;
		if(checkCountExceedMaxCount()) {
			deleteFIFOData(diskCommonCacheMaxCount / ICacheManager.DELETE_PERCENT);
			serialize();
		}
		_commonCache.put(keyUrl, new REntry(obj, System.currentTimeMillis()));
		//add by wangsen
		serialize();
	}
	
	/**
	 * Check use current count items exceed allow max count items.
	 * 
	 * @return
	 */
	private boolean checkCountExceedMaxCount() {
		try {
			return _commonCache.size() >= diskCommonCacheMaxCount;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.qiyi.qitan.cache.ICacheManager#getCache(java.lang.String)
	 */
	public String getCache(String keyUrl) {
		// TODO Auto-generated method stub
		if(Utils.isEmpty(keyUrl) || null == _commonCache)
			return "";
		REntry rEntry = _commonCache.get(keyUrl);
		return null == rEntry ? "" : rEntry.name;
	}

	/*
	 * (non-Javadoc)
	 * @see com.qiyi.qitan.cache.ICacheManager#clearAndWriteDisk()
	 */
	public void clearAndWriteDisk() {
		serialize();
		if(null != _commonCache)
		{
			_commonCache.clear();
			_commonCache = null;
		}
		_manager = null;
		isInit = false;
	}
	
	/**
	 * Serialize memory map.
	 */
	private void serialize() {
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		try {
			fos = new FileOutputStream(diskCommonCachePath + Utils.ROOT_FILE_PATH + CACHE_FILE_NAME);
			oos = new ObjectOutputStream(fos);
			oos.writeObject(_commonCache);
			oos.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(null != oos) {
				try {
					oos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(null != fos) {
				try {
					fos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}  
	}
	
	/**
	 * Deserialize memory map.
	 */
	@SuppressWarnings("unchecked")
	private static void deserialize() {
		File file = new File(diskCommonCachePath + Utils.ROOT_FILE_PATH + CACHE_FILE_NAME);
		if(!file.exists())
			return;
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		try {
			fis = new FileInputStream(file);
			ois = new ObjectInputStream(fis);
			_commonCache = (HashMap<String, REntry>)ois.readObject();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(null != ois) {
				try {
					ois.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(null != fis) {
				try {
					fis.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}  
	}

	public static void reset() {
		
		isInit = false;
		
		if(null != _commonCache){
			_commonCache.clear();
		}
		
		configure();
		
	}
}
