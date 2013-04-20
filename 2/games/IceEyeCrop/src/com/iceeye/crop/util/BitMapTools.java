package com.iceeye.crop.util;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.Bitmap.Config;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.iceeye.crop.service.TopViewService;

public class BitMapTools {

	public static Bitmap getBitMapFromRawFile(String rawname, int width,
			int height) {
		InputStream ras;
		try {
			File sdDir = new File(TopViewService.tmp_path + rawname);
			if (sdDir.exists()) {
				ras = new FileInputStream(sdDir);
				int total = ras.available();
				byte[] filebytes = new byte[total];
				ras.read(filebytes);

				Bitmap bitmap = Bitmap.createBitmap(width, height,
						Bitmap.Config.RGB_565);
				ByteBuffer buffer = ByteBuffer.wrap(filebytes);
				bitmap.copyPixelsFromBuffer(buffer);
				ras.close();
				return bitmap;
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @param path     存储的位置
	 * @param bitName  文件的名字
	 * @param mBitmap  要存储的图片
	 */
	public static int saveBitmapAsPNG(String path,String bitName, Bitmap mBitmap) {
		File f = new File(path + bitName);
		try {
			f.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return 0;
		}
		FileOutputStream fOut = null;
		  try {
				fOut = new FileOutputStream(f);
				if(mBitmap!=null){
					mBitmap.compress(Bitmap.CompressFormat.JPEG, 80, fOut);
					fOut.flush();
					fOut.close();
					return 1;
			      }
			fOut.flush();
			fOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		return 0;
	}
	 public static int cropScreenToRaw(){
		 //检查目录是否存在
		if( createSDCardDir(TopViewService.tmp_path)&&
				    createSDCardDir(TopViewService.save_path)){
				 
		    	 String cmd = String.format("%s > %s\n", "cat /dev/graphics/fb0", TopViewService.tmp_path+TopViewService.tmp_rawname);
		//    	 String cmd2 = String.format("%s\n", "dd bs=307200 count=1 if=/sdcard/frame.raw of=frame1.raw");
		         try {
		//             Process exeEcho = Runtime.getRuntime().exec("su");
		             Process exeEcho = Runtime.getRuntime().exec("sh");
		             exeEcho.getOutputStream().write(cmd.getBytes());
		//             exeEcho.getOutputStream().write(cmd2.getBytes());
		             exeEcho.getOutputStream().flush();
		             return 1;
		         } catch (IOException e) {
		         	e.printStackTrace();
		         	return 0;
		//             showMessage("Excute exception: " + e.getMessage());
		         }
		}else{
			return 0;
		}
    }
	 
   private Uri getImageUri(String path) {
    	   
    	   return Uri.fromFile(new File(path));
    	   
        }

//     private Bitmap getBitmap(String path) {
//    	Uri uri = getImageUri(path);
//    	InputStream in = null;
//    	try {
//    	    in = mContentResolver.openInputStream(uri);
//    	    return BitmapFactory.decodeStream(in);
//    	} catch (FileNotFoundException e) {
////    	    Log.e(TAG, "file " + path + " not found");
//    	}
//    	return null;
//      }
   
 //在SD卡上创建一个文件夹
   /**
 * @param dirname  检查路径是否存在，不存在则创建
 */
 public static boolean createSDCardDir(String dirname){
    if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
            File path = new File(dirname);
            if(path.exists()){
            	return true;
            }else{
            	//若不存在，创建目录，可以在应用启动的时候创建
                return  path.mkdirs();
            }
     }else{
            return false;
          }
   }
 
 /**
  * 在给定的图片的右上角加上联系人数量。数量用红色表示
  * @param icon 给定的图片
  * @return 带联系人数量的图片
  */
 public static  Bitmap generatorContactCountIcon(Bitmap icon){
	 int iconW=icon.getWidth();
	 int iconH=icon.getHeight();
 	//初始化画布
 	Bitmap contactIcon=Bitmap.createBitmap(iconW, iconH, Config.RGB_565);
 	Canvas canvas=new Canvas(contactIcon);
 	
 	//拷贝图片
 	Paint iconPaint=new Paint();
 	iconPaint.setDither(true);//防抖动
 	iconPaint.setFilterBitmap(true);//用来对Bitmap进行滤波处理，这样，当你选择Drawable时，会有抗锯齿的效果
 	Rect src=new Rect(0, 0, icon.getWidth(), icon.getHeight());
 	Rect dst=new Rect(0, 0, iconW, iconH);
 	canvas.drawBitmap(icon, src, dst, iconPaint);
 	
 	//在图片上创建一个覆盖的联系人个数
 	//启用抗锯齿和使用设备的文本字距
 	Paint countPaint=new Paint(Paint.ANTI_ALIAS_FLAG|Paint.DEV_KERN_TEXT_FLAG);
 	countPaint.setColor(Color.RED);
 	countPaint.setTextSize(iconW/22);
 	countPaint.setTypeface(Typeface.DEFAULT_BOLD);
 	canvas.drawText("冰眼截图:www.91dota.com/?p=220",iconW/22,iconH-5, countPaint);
 	return contactIcon;
 }
}
