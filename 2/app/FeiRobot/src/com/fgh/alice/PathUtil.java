package com.fgh.alice;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;

/**
 * 普通JAVA获取   WEB项目下的WEB-INF目录
 * @author wang
 *
 */
public class PathUtil {
	public static void main(String[] args) {
		PathUtil pathUtil = new PathUtil();
		System.out.println("path=="+pathUtil.getWebInfPath());
	}
	
	public String getWebInfPath(){
		URL url = getClass().getProtectionDomain().getCodeSource().getLocation();
		String path = url.toString();
		int index = path.indexOf("WEB-INF");
		
		if(index == -1){
			index = path.indexOf("classes");
		}
		
		if(index == -1){
			index = path.indexOf("bin");
		}
		
		path = path.substring(0, index);
		
		if(path.startsWith("zip")){//当class文件在war中时，此时返回zip:D:/...这样的路径
			path = path.substring(4);
		}else if(path.startsWith("file")){//当class文件在class文件中时，此时返回file:/D:/...这样的路径
			path = path.substring(6);
		}else if(path.startsWith("jar")){//当class文件在jar文件里面时，此时返回jar:file:/D:/...这样的路径
			path = path.substring(10); 
		}
		try {
			path =  URLDecoder.decode(path, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return path;
	}
}
