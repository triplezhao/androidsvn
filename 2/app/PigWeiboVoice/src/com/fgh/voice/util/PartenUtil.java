package com.fgh.voice.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fgh.voice.view.widget.FaceManager;

import android.content.Intent;
import android.net.Uri;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.util.Linkify;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


public class PartenUtil {
	
	
	
	/**
	 * @param tv
	 * @param isClickAble  是否设置为可点击。list列表中不要设置点击，会有冲突
	 */
	public static void setAllParten(final TextView tv,boolean isClickAble){ 
		final CharSequence content=tv.getText();
		if(!TextUtils.isEmpty(content)){
		
		      SpannableString ss = new SpannableString(content);
		      
			  String str = getPunctuation();
			  
			  setEmotionParten(tv, ss, content.toString());
			  /**
			   * 设置话题
				 */
					 Pattern p1 = Pattern.compile(String.format("#[^#]+?#", str));
			         final Matcher m1 = p1.matcher(ss); 
				        while(m1.find()){ 
			//	        	ss.setSpan(new ForegroundColorSpan(Color.YELLOW), m.start(), m.end(),Spanned.SPAN_MARK_MARK);
				        	final int start=m1.start();
				        	final int end=m1.end();
				        	ss.setSpan(new ClickableSpan() {
								@Override
								public void onClick(View widget) {
									// TODO Auto-generated method stub
			//						Toast.makeText(tv.getContext(), "onClick##",Toast.LENGTH_SHORT).show();
									Log.i("onClick##", start+1+"");
//									CnTrendDetailAct.trendDetailActCreator(tv.getContext(),
//											content.subSequence(start+1, end-1).toString());
								}
							}, m1.start(), m1.end(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
				        
				        }
				        
			        /**
					   * 设置@
						 */
		        
			         Pattern p2 = Pattern.compile(String.format("@[[^@\\s%s]0-9]{1,20}", str));
					 Matcher m2 = p2.matcher(ss); 
					 while(m2.find()){ 
						    final int start=m2.start();
				        	final int end=m2.end();
	//			        	ss.setSpan(new ForegroundColorSpan(Color.YELLOW), m.start(), m.end(),Spanned.SPAN_MARK_MARK);
						ss.setSpan(new ClickableSpan() {
							
							@Override
							public void onClick(View widget) {
								// TODO Auto-generated method stub
								Log.i("onClick@", start+1+"");
//								CnUserInformationAct.userInfoActivityCreatorFromName(tv.getContext(),
//										content.subSequence(start+1, end).toString());
							}
						}, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
						
					}
					 
					 
					 /**
					   * 设置url
						 */
					 
					 Pattern p3 = getWebPattern2();
					 Matcher m3 = p3.matcher(ss); 
					 while(m3.find()){ 
						 //			        	ss.setSpan(new ForegroundColorSpan(Color.YELLOW), m.start(), m.end(),Spanned.SPAN_MARK_MARK);
						   final int start=m3.start();
				        	final int end=m3.end();
						 ss.setSpan(new ClickableSpan() {
							 
							 @Override
							 public void onClick(View widget) {
								 // TODO Auto-generated method stub
								 Intent intent= new Intent();        
								    intent.setAction("android.intent.action.VIEW");    
								    Uri content_url = Uri.parse(content.subSequence(start, end).toString());   
								    intent.setData(content_url);  
								    widget.getContext().startActivity(intent);
							 }
						 }, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
						 
					 }
					 
			tv.setText(ss);
			if(isClickAble){
				tv.setAutoLinkMask(Linkify.WEB_URLS);
	             tv.setMovementMethod(LinkMovementMethod.getInstance());
			}
		}
	}
	
	
	
	
	
	
	
//	void setTrendParten(SpannableString ss,String content){
//	        String str = getPunctuation();
//			Pattern p = Pattern.compile(String.format("#[^#]+?#", str));
//	         Matcher m = p.matcher(ss); 
//	        while(m.find()){ 
////	        	ss.setSpan(new ForegroundColorSpan(Color.YELLOW), m.start(), m.end(),Spanned.SPAN_MARK_MARK);
//	        	ss.setSpan(new ClickableSpan() {
//					
//					@Override
//					public void onClick(View widget) {
//						// TODO Auto-generated method stub
////						Toast.makeText(tv.getContext(), "onClick##",Toast.LENGTH_SHORT).show();
//					}
//				}, m.start(), m.end(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//	        
//	        }
//	        
////	        tv.setAutoLinkMask(Linkify.WEB_URLS);
////	        tv.setMovementMethod(LinkMovementMethod.getInstance());
//	}
//	 void setAtParten(SpannableString ss,String content){
//		 String str = getPunctuation();
//		 Pattern p = Pattern.compile(String.format("@[[^@\\s%s]0-9]{1,20}", str));
//		 Matcher m = p.matcher(ss); 
//		while(m.find()){ 
////	        	ss.setSpan(new ForegroundColorSpan(Color.YELLOW), m.start(), m.end(),Spanned.SPAN_MARK_MARK);
//			ss.setSpan(new ClickableSpan() {
//				
//				@Override
//				public void onClick(View widget) {
//					// TODO Auto-generated method stub
//				}
//			}, m.start(), m.end(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//			
//		}
////	        tv.setMovementMethod(LinkMovementMethod.getInstance());
//	}
	public static void setWebParten(TextView tv,final SpannableString ss){
		 /**
		   * 设置url
			 */
		 
		 Pattern p3 = getWebPattern2();
		 Matcher m3 = p3.matcher(ss); 
		 while(m3.find()){ 
			 //			        	ss.setSpan(new ForegroundColorSpan(Color.YELLOW), m.start(), m.end(),Spanned.SPAN_MARK_MARK);
			   final int start=m3.start();
	        	final int end=m3.end();
			 ss.setSpan(new ClickableSpan() {
				 
				 @Override
				 public void onClick(View widget) {
					 // TODO Auto-generated method stub
					    Intent intent= new Intent();        
					    intent.setAction("android.intent.action.VIEW");    
					    Uri content_url = Uri.parse(ss.subSequence(start, end).toString());   
					    intent.setData(content_url);  
					    widget.getContext().startActivity(intent);
				 }
			 }, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
			 
		 }
			tv.setText(ss);
	        tv.setMovementMethod(LinkMovementMethod.getInstance());
	}
	public static void setEmotionParten(TextView tv,SpannableString ss,String content){
		
//			FaceManager.getInstance().convertTextToEmotion(tv.getContext(), ss, content);
//	        	ss.setSpan(new ForegroundColorSpan(Color.YELLOW), m.start(), m.end(),Spanned.SPAN_MARK_MARK);
			
//	        tv.setMovementMethod(LinkMovementMethod.getInstance());
	}
	
	
	
	
		private static String getPunctuation()
	    {
	      return "`~!@#\\$%\\^&*()=+\\[\\]{}\\|/\\?<>,\\.:\\u00D7\\u00B7\\u2014-\\u2026\\u3001-\\u3011\\uFE30-\\uFFE5";
	    }
	
		public static Pattern getWebPattern2()
	  {
		  
		  Pattern webpattern2 = Pattern.compile("((?:(http|https|Http|Https|rtsp|Rtsp):\\/\\/(?:(?:[a-zA-Z0-9\\$\\-\\_\\.\\+\\!\\*\\'\\(\\)\\,\\;\\?\\&\\=]|(?:\\%[a-fA-F0-9]{2})){1,64}(?:\\:(?:[a-zA-Z0-9\\$\\-\\_\\.\\+\\!\\*\\'\\(\\)\\,\\;\\?\\&\\=]|(?:\\%[a-fA-F0-9]{2})){1,25})?\\@)?)?((?:(?:[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}\\.)+(?:(?:aero|arpa|asia|a[cdefgilmnoqrstuwxz])|(?:biz|b[abdefghijmnorstvwyz])|(?:cat|com|coop|c[acdfghiklmnoruvxyz])|d[ejkmoz]|(?:edu|e[cegrstu])|f[ijkmor]|(?:gov|g[abdefghilmnpqrstuwy])|h[kmnrtu]|(?:info|int|i[delmnoqrst])|(?:jobs|j[emop])|k[eghimnrwyz]|l[abcikrstuvy]|(?:mil|mobi|museum|m[acdghklmnopqrstuvwxyz])|(?:name|net|n[acefgilopruz])|(?:org|om)|(?:pro|p[aefghklmnrstwy])|qa|r[eouw]|s[abcdeghijklmnortuvyz]|(?:tel|travel|t[cdfghjklmnoprtvwz])|u[agkmsyz]|v[aceginu]|w[fs]|y[etu]|z[amw]))|(?:(?:25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9])\\.(?:25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9]|0)\\.(?:25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9]|0)\\.(?:25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}|[1-9][0-9]|[0-9])))(?:\\:\\d{1,5})?)(\\/(?:(?:[a-zA-Z0-9\\;\\/\\?\\:\\@\\&\\=\\#\\~\\-\\.\\+\\!\\*\\'\\(\\)\\,\\_])|(?:\\%[a-fA-F0-9]{2}))*)?(?:\\b|$)");
	    return webpattern2;
	  }
}
