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
	 * @param path     �洢��λ��
	 * @param bitName  �ļ�������
	 * @param mBitmap  Ҫ�洢��ͼƬ
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
		 //���Ŀ¼�Ƿ����
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
   
 //��SD���ϴ���һ���ļ���
   /**
 * @param dirname  ���·���Ƿ���ڣ��������򴴽�
 */
 public static boolean createSDCardDir(String dirname){
    if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
            File path = new File(dirname);
            if(path.exists()){
            	return true;
            }else{
            	//�������ڣ�����Ŀ¼��������Ӧ��������ʱ�򴴽�
                return  path.mkdirs();
            }
     }else{
            return false;
          }
   }
 
 /**
  * �ڸ�����ͼƬ�����ϽǼ�����ϵ�������������ú�ɫ��ʾ
  * @param icon ������ͼƬ
  * @return ����ϵ��������ͼƬ
  */
 public static  Bitmap generatorContactCountIcon(Bitmap icon){
	 int iconW=icon.getWidth();
	 int iconH=icon.getHeight();
 	//��ʼ������
 	Bitmap contactIcon=Bitmap.createBitmap(iconW, iconH, Config.RGB_565);
 	Canvas canvas=new Canvas(contactIcon);
 	
 	//����ͼƬ
 	Paint iconPaint=new Paint();
 	iconPaint.setDither(true);//������
 	iconPaint.setFilterBitmap(true);//������Bitmap�����˲���������������ѡ��Drawableʱ�����п���ݵ�Ч��
 	Rect src=new Rect(0, 0, icon.getWidth(), icon.getHeight());
 	Rect dst=new Rect(0, 0, iconW, iconH);
 	canvas.drawBitmap(icon, src, dst, iconPaint);
 	
 	//��ͼƬ�ϴ���һ�����ǵ���ϵ�˸���
 	//���ÿ���ݺ�ʹ���豸���ı��־�
 	Paint countPaint=new Paint(Paint.ANTI_ALIAS_FLAG|Paint.DEV_KERN_TEXT_FLAG);
 	countPaint.setColor(Color.RED);
 	countPaint.setTextSize(iconW/22);
 	countPaint.setTypeface(Typeface.DEFAULT_BOLD);
 	canvas.drawText("���۽�ͼ:www.91dota.com/?p=220",iconW/22,iconH-5, countPaint);
 	return contactIcon;
 }
}
