package pigframe.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import pigframe.httpFrame.network.AsyncImageLoader;
import pigframe.httpFrame.network.AsyncImageLoader.ImageCallback;



import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;

public class PigImageUtil {
	private static String TAG = "BitmapGet";

	/**
	* 加载本地图片
	* http://bbs.3gstdy.com
	* @param url
	* @return
	*/
	public static Bitmap getLocalBitmap(String url) {
	     try {
	          FileInputStream fis = new FileInputStream(url);
	          return BitmapFactory.decodeStream(fis);
	     } catch (FileNotFoundException e) {
	          e.printStackTrace();
	          return null;
	     }
	}

	/**
	* 从服务器取图片
	* http://adhere.haliyoo.com
	* @param url
	* @return
	*/
	public static Bitmap getHttpBitmap(URL url) {
	     URL myFileUrl = null;
	     Bitmap bitmap = null;
	     Log.d(TAG, url.toString());
		 myFileUrl = url;
	     try {
	          HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
	          conn.setConnectTimeout(0);
	          conn.setDoInput(true);
	          conn.connect();
	          InputStream is = conn.getInputStream();
	          bitmap = BitmapFactory.decodeStream(is);
	          is.close();
	     } catch (IOException e) {
	          e.printStackTrace();
	     }
	     return bitmap;
	}
	

	
	/**
	 * 等比例缩放
	 * @param bitmap 原始图片
	 * @param height 缩放后的高度
	 */
	public static Bitmap imageZoomByHeight(Bitmap bitmap, int height)
	{
        //获取这个图片的宽和高
        int owidth = bitmap.getWidth();
        int oheight = bitmap.getHeight();
         
        //定义预转换成的图片的宽和高
        int newHight = height;
        int newWidth = (newHight * owidth) / oheight;
         
        //计算缩放率，新尺寸除原尺寸
        float scaleWidth = (float)newWidth/owidth;
        float scaleHeight = (float)newHight/oheight;
         
        //创建操作图片用的matrix对象
        Matrix matrix = new Matrix();
         
        //缩放图片动作
        matrix.postScale(scaleWidth, scaleHeight);
         
        //旋转图片动作
        //matrix.postRotate(45);
         
        //创建新的图片
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, owidth, oheight, matrix, true);
		
        return resizedBitmap;
	}

	/**
	 * 等比例缩放
	 * @param bitmap 原始图片
	 * @param height 缩放后的宽度
	 */
	public static Bitmap imageZoomByWidth(Bitmap bitmap, int width)
	{
        //获取这个图片的宽和高
        int owidth = bitmap.getWidth();
        int oheight = bitmap.getHeight();
         
        //定义预转换成的图片的宽和高
        int newWidth = width;
        int newHight = (newWidth * oheight) / owidth;
         
        //计算缩放率，新尺寸除原尺寸
        float scaleWidth = (float)newWidth/owidth;
        float scaleHeight = (float)newHight/oheight;
         
        //创建操作图片用的matrix对象
        Matrix matrix = new Matrix();
         
        //缩放图片动作
        matrix.postScale(scaleWidth, scaleHeight);
         
        //旋转图片动作
        //matrix.postRotate(45);
         
        //创建新的图片
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, owidth, oheight, matrix, true);
		
        return resizedBitmap;
	}

	public static Bitmap getBitmapFromFile(File mCurrentPhotoFile) {
		// TODO Auto-generated method stub
		BitmapFactory.Options options = new BitmapFactory.Options();
		String path = mCurrentPhotoFile.getPath();
		
		return BitmapFactory.decodeFile(path, options);
	}

	/**
	 * 位图转字节码
	 * @param bm 位图
	 * @return 字节码
	 */
	private static byte[] bitmap2Bytes(Bitmap bm) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.PNG, 50, baos);
		return baos.toByteArray();
	}
	
	/**
	 * drawable转bitmap
	 * @param drawable 
	 * @return Bitmap
	 */
	public static Bitmap drawable2Bitmap(Drawable drawable) {

		Bitmap bitmap = Bitmap
				.createBitmap(
						drawable.getIntrinsicWidth(),
						drawable.getIntrinsicHeight(),
						drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
								: Bitmap.Config.RGB_565);
		Canvas canvas = new Canvas(bitmap);
		// canvas.setBitmap(bitmap);
		drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable
				.getIntrinsicHeight());
		drawable.draw(canvas);
		return bitmap;
	}
	
	/**
	 * 显示图片
	 * @param view 显示控件
	 * @param img 图片
	 * @param width 最大宽度
	 */
	public static void showImgByWidth(ImageView view, Drawable img, int width){
        int w = img.getIntrinsicWidth();
        int h = img.getIntrinsicHeight();
        Log.i(TAG, w + "/" + h);
        if (w > width) {
            int hh = width * h / w;
            Log.i(TAG, hh + "");
            LayoutParams para = (LayoutParams) view.getLayoutParams();
            para.width = width;
            para.height = hh;
            view.setLayoutParams(para);
        }
        view.setImageDrawable(img);
    }
	
	/**
	 * 显示图片
	 * @param view 显示控件
	 * @param strUrl 图片URL
	 * @param bitmap 图片位图
	 */
	public static void showImage(boolean usecache, ImageView view, String strUrl, final int resid) {
			
		new AsyncImageLoader().loadDrawable(usecache,strUrl,
					view, new ImageCallback() {

				@Override
				public void postImageLoad(Drawable imageDrawable,
						ImageView imageView, String imageUrl) {
					// TODO Auto-generated method stub
					imageView.setImageDrawable(imageDrawable);
				}

				@Override
				public void preImageLoad(ImageView imageView, String imageUrl) {
					// TODO Auto-generated method stub
//					imageView.setImageResource(resid);
				}
			});
	}
}
