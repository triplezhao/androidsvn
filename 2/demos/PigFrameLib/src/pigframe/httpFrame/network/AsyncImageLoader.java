package pigframe.httpFrame.network;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;


import pigframe.cache.CacheFactory;
import pigframe.cache.ICacheManager;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

public class AsyncImageLoader {
	private static String TAG = "AsyncImageLoader";
    //SoftReference是软引用，是为了更好的为了系统回收变量
    public static ICacheManager<Drawable> manager = CacheFactory.creator(CacheFactory.CACHE_TYPE_IMAGE);
    public AsyncImageLoader() {
    	
    }
    
    public void loadDrawable(final String imageUrl,final ImageView imageView){
    	Drawable drawable=manager.getCache(imageUrl);
    	if (drawable!=null) {
    		if (drawable != null) {
    			imageView.setBackgroundDrawable(drawable);
    			return ;
    		}
    	}
    	
    	final Handler handler = new Handler() {
    		public void handleMessage(Message message) {
    			/// 可能会造成obj == null 
    			if (message.obj == null) {
    				Log.w(TAG, "obj is null, return.");
    				return;
    			}
    			imageView.setBackgroundDrawable((Drawable) message.obj);
    		}
    	};
    	//建立新一个新的线程下载图片
    	new Thread() {
    		@Override
    		public void run() {
    			Drawable drawable = loadImageFromUrl(imageUrl);
    			manager.putCache(imageUrl, drawable);
    			Message message = handler.obtainMessage(0, drawable);
    			handler.sendMessage(message);
    		}
    	}.start();
    	return ;
    }
    /**
     * @param usecache 是否使用缓存
     * @param imageUrl  图片网络地址
     * @param imageView  要设置的图片的控件
     * @param imageCallback 设置默认图片返回数据后的处理方式
     */
    public void loadDrawable(boolean usecache,final String imageUrl,final ImageView imageView, final ImageCallback imageCallback
    			){
    	//是否使用缓存
    	if(usecache){
		    	Drawable drawable=manager.getCache(imageUrl);
		    	if (drawable!=null) {
		            if (drawable != null) {
		            	imageCallback.postImageLoad(drawable, imageView,imageUrl);
		                return ;
		            }
		        }
    	}
    	//缓存没有，则设置个 默认图片
        imageCallback.preImageLoad(imageView,imageUrl);
        
        
        //然后去网络下载图片
        final Handler handler = new Handler() {
            public void handleMessage(Message message) {
            	/// 可能会造成obj == null 
            	if (message.obj == null) {
            		Log.w(TAG, "obj is null, return.");
            		return;
            	}
                imageCallback.postImageLoad((Drawable) message.obj, imageView,imageUrl);
            }
        };
        //建立新一个新的线程下载图片
        new Thread() {
            @Override
            public void run() {
                Drawable drawable = loadImageFromUrl(imageUrl);
                manager.putCache(imageUrl, drawable);
                Message message = handler.obtainMessage(0, drawable);
                handler.sendMessage(message);
            }
        }.start();
        return ;
    }
    
    private static Drawable loadImageFromUrl(String url){
    	
    	
        URL m;
        InputStream i = null;
        if (url == null) {
        	return null;
        }
        
        try {
        	
        	Log.d(AsyncImageLoader.class.getName(), "start loadImage:(" + url + ") ");
            m = new URL(url);
            i = (InputStream) m.getContent();
        } catch (MalformedURLException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Drawable d = Drawable.createFromStream(i, "src");
        return d;
    }
	public Drawable loadImageFromUrlNoError(String imageUrl) {
		Log.d(AsyncImageLoader.class.getName(), "start loadImage:(" + imageUrl + ") ");
		ByteArrayOutputStream out = null;
		Drawable drawable = null;
		int BUFFER_SIZE = 1024*16;
		try {
			BufferedInputStream in = new BufferedInputStream(new URL(imageUrl).openStream(),BUFFER_SIZE);
			out = new ByteArrayOutputStream(BUFFER_SIZE);
			int length = 0;
			byte[] tem = new byte[BUFFER_SIZE];
			length = in.read(tem);
			while(length != -1){
				out.write(tem, 0, length);
				//Log.d(AsyncImageLoader.class.getName(), "size:"+out.size()+",url:"+imageUrl);
				length = in.read(tem);
			}
			in.close();
			drawable = Drawable.createFromStream(new ByteArrayInputStream(out.toByteArray()), "src");
			
		} catch (Exception e) {
			Log.e(this.getClass().getName(), ""+e.getClass().getName()+":"+e.getLocalizedMessage());
		}
		return drawable;
	}    
	
	 public Drawable loadHtmlDrawable(boolean usecache,final String imageUrl,final TextView tv, final HtmlImageCallback imageCallback
	    ){
	    	
	    	Drawable drawable=null;
			
	    	//是否使用缓存
	    	if(usecache){
	    		 drawable=manager.getCache(imageUrl);
	    		if (drawable!=null) {
	    			if (drawable != null) {
	    				return drawable;
	    			}
	    		}
	    	}
	    	
	    	//缓存没有，则设置个 默认图片
	    	drawable=imageCallback.preImageLoad();
			
			
	        	
	        	//然后去网络下载图片
	        	final Handler handler = new Handler() {
	        		public void handleMessage(Message message) {
	        			/// 可能会造成obj == null 
	        			if (message.obj == null) {
	        				Log.w(TAG+imageUrl, " \n obj is null, return.");
	        				return;
	        			}
	        			imageCallback.postImageLoad((Drawable) message.obj, tv,imageUrl);
	        		}
	        	};
	        	//建立新一个新的线程下载图片
	        	new Thread() {
	        		@Override
	        		public void run() {
	        			Drawable drawable = loadImageFromUrl(imageUrl);
	        			manager.putCache(imageUrl, drawable);
	        			Message message = handler.obtainMessage(0, drawable);
	        			handler.sendMessage(message);
	        		}
	        	}.start();
	        	
	    	
			return drawable;
	    	
	    	
	    	
	    }
	 //回调接口
	    public interface HtmlImageCallback {
	    	public void postImageLoad(Drawable imageDrawable,TextView tv, String imageUrl);
	    	public Drawable preImageLoad();
	    }
	    
	
    //回调接口
    public interface ImageCallback {
        public void postImageLoad(Drawable imageDrawable,ImageView imageView, String imageUrl);
        public void preImageLoad(ImageView imageView, String imageUrl);
    }
}