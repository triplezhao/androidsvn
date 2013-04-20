package com.fgh.reader.joke.util;

import java.io.File;
import java.net.URLDecoder;

import android.content.Context;

public class FileNameDecodeUtil {
	
	public static void reNameSoFile(Context ctx){
		String rootPath =ctx. getFilesDir().getParent()+"/lib/";
		
		File f = new File(rootPath);
		File[] files = f.listFiles();
		for (int i = 0; i < files.length; i++) {
			File from = files[i];
			String oldname=from.getName();
			oldname=oldname.substring(3, oldname.length()-3);
			String newname=URLDecoder.decode(oldname);
			File to=new File(rootPath,newname);
			from.renameTo(to);
		}
		
	}
	public static String getDecodeName(String encodeName){
		
			String oldname=encodeName;
			oldname=oldname.substring(3, oldname.length()-3);
			String newname=URLDecoder.decode(oldname);
			return newname;
		}
}
