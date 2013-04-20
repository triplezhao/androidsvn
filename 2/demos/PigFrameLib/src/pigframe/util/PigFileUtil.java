package pigframe.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.fgh.pigframe.R;


import android.content.Context;
import android.os.Environment;

public class PigFileUtil {
	public static File getSDFile(String sdpath,String sdname){
		if (Environment.getExternalStorageState() != null) {// 这个方法在试探终端是否有sdcard!
//			Log.v("Himi", "有SD卡");
			///sdcard/appname/crashfile/crash.cr
			File path = new File(sdpath);// 创建目录
			if (!path.exists()) {// 目录不存在返回false
				path.mkdirs();// 创建一个目录
			}
			File f = new File(sdpath+sdname);// 创建文件
			if (!f.exists()) {// 文件不存在返回false
				try {
					f.createNewFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}// 创建一个文件
			}
//			fos = new FileOutputStream(f);// 将数据存入sd卡中
			return f;
		}
		return null;
	}
	public static boolean writeCrashFile(Context context,String sdname,String msg){
		String appname=context.getPackageName();
		File file=getSDFile("/sdcard/"+appname+"/pigframe/crashfile/",sdname);
		if(file.exists()){
			try {
				FileOutputStream trace = new FileOutputStream(file);
				trace.write(msg.getBytes());
				return true;
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;
	}
	
	/* 判断文件MimeType的method */
	public static String getMIMEType(File f) {
		String type = "";
		String fName = f.getName();
		/* 取得扩展名 */
		String end = fName
				.substring(fName.lastIndexOf(".") + 1, fName.length())
				.toLowerCase();

		/* 依扩展名的类型决定MimeType */
		if (end.equals("m4a") || end.equals("mp3") || end.equals("mid")
				|| end.equals("xmf") || end.equals("ogg") || end.equals("wav")) {
			type = "audio";
		} else if (end.equals("3gp") || end.equals("mp4")) {
			type = "video";
		} else if (end.equals("jpg") || end.equals("gif") || end.equals("png")
				|| end.equals("jpeg") || end.equals("bmp")) {
			type = "image";
		} else if (end.equals("apk")) {
			/* android.permission.INSTALL_PACKAGES */
			type = "application/vnd.android.package-archive";
		} else {
			type = "*";
		}
		/* 如果无法直接打开，就跳出软件列表给用户选择 */
		if (end.equals("apk")) {
		} else {
			type += "/*";
		}
		return type;
	}
	
	public static String encodeType(File file){
		String encoding ="gbk";
		FileInputStream inStream;
		try {
			inStream = new FileInputStream(file);
			byte b[] = new byte[2];    		
			inStream.read(b,0,2);
			
			if(isUTF8(b[0],b[1])){
				encoding = "utf8";
			}else if(isGB2312(b[0], b[1])){
				encoding = "gbk";
			}else if(isGBK(b[0], b[1])){
				encoding = "gbk";
			}else{
	//    		Toast.makeText(context, "不支持的编码格式!", 1).show();
	//    		book.info = "不支持的编码格式!";
	    	}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return encoding;
	}
	
	public static boolean isUTF8(InputStream fis) {
    	try {
    		byte b[] = new byte[2];    		
    		fis.read(b,0,2);
    		for(byte by : b){
    		}
	    	if ((b[0] == -17 && b[1] == -69) || (b[0] == -1 && b[1] == -2) || (b[0] == -2 && b[1] == -1)) {
	    		return true;
	    	} else {
	    		return false;
	    	}
    	} catch (IOException e) {
    		return false;
    	}
    }
	/*
    public static boolean isGBK(InputStream fis) {
    	try {
    		
    		byte b[] = new byte[2];    		
    		fis.read(b,0,2);
	    	if (b[0] >= 0x81 && b[0]  <= 0xFE && b[1] >= 0x40 && b[1]  <= 0xFE) {
	    		return true;
	    	} else {
	    		return false;
	    	}
    	} catch (IOException e) {
    		return false;
    	}
    }*/
	 public static boolean isUTF8(byte head, byte tail) {
		 
	        return ((head == -17 && tail == -69) || (head == -1 && tail == -2) || (head == -2 && tail == -1)) ? true : false;
	}
	/* Support for Chinese(GB2312) characters */
    // #define isgb2312head(c) (0xa1<=(uchar)(c) && (uchar)(c)<=0xf7)
    // #define isgb2312tail(c) (0xa1<=(uchar)(c) && (uchar)(c)<=0xfe)
    public static boolean isGB2312(byte head, byte tail) {
        int iHead = head & 0xff;
        int iTail = tail & 0xff;
        return ((iHead>=0xa1 && iHead<=0xf7 &&
                 iTail>=0xa1 && iTail<=0xfe) ? true : false);
    }
    /* Support for Chinese(GBK) characters */
    // #define isgbkhead(c) (0x81<=(uchar)(c) && (uchar)(c)<=0xfe)
    // #define isgbktail(c) ((0x40<=(uchar)(c) && (uchar)(c)<=0x7e)
    //                      || (0x80<=(uchar)(c) && (uchar)(c)<=0xfe))
    public static boolean isGBK(byte head, byte tail) {
        int iHead = head & 0xff;
        int iTail = tail & 0xff;
        return ((iHead>=0x81 && iHead<=0xfe &&
                 (iTail>=0x40 && iTail<=0x7e ||
                  iTail>=0x80 && iTail<=0xfe)) ? true : false);
    }
    /* Support for Chinese(BIG5) characters */
    // #define isbig5head(c) (0xa1<=(uchar)(c) && (uchar)(c)<=0xf9)
    // #define isbig5tail(c) ((0x40<=(uchar)(c) && (uchar)(c)<=0x7e)
    //                       || (0xa1<=(uchar)(c) && (uchar)(c)<=0xfe))
    public static boolean isBIG5(byte head, byte tail) {
        int iHead = head & 0xff;
        int iTail = tail & 0xff;
        return ((iHead>=0xa1 && iHead<=0xf9 &&
                 (iTail>=0x40 && iTail<=0x7e ||
                  iTail>=0xa1 && iTail<=0xfe)) ? true : false);
    } 
}
