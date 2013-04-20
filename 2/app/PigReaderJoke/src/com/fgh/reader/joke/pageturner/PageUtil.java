package com.fgh.reader.joke.pageturner;

import java.text.DecimalFormat;

public class PageUtil {
	public static String getOffsetOf(int bookOffset,int bookSize){
		 double offset=bookOffset;
         double size=bookSize;
         String str_offsetof="0.00%";
         if(size>0){
	       	 double offsetof=offset/size*100;
	       	 DecimalFormat df2  = new DecimalFormat("###.00");
	       	 str_offsetof=df2.format(offsetof)+"%";
         }
        return str_offsetof;
	}
}
