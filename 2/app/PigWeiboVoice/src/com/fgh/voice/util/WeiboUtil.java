package com.fgh.voice.util;

public class WeiboUtil {
	/**
		 * 从xml数据中获取内容（微博来源）
		 * @param source 原始xml数据包
		 * @return 微博来源内容
		 */
		public static String getSourceByXml(String source) {
			int start = source.indexOf(">");
			int end = source.lastIndexOf("</a>");
			
			return source.substring(start + 1, end);
		}
}

