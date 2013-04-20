package com.fgh.reader.pageturner;

import java.text.DecimalFormat;

public class PageUtil {
	public static String getOffsetOf(int bookOffset,int bookSize){
		 double offset=bookOffset;
         double size=bookSize;
         String str_offsetof="0.00%";
         if(size>0){
	       	 double offsetof=offset/size;
	         DecimalFormat df2 = new DecimalFormat("0.00%");
	       	 str_offsetof=df2.format(offsetof);
         }
        return str_offsetof;
	}
}
