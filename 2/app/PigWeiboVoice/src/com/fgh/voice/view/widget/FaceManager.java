package com.fgh.voice.view.widget;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.Log;

import com.fgh.voice.R;

public class FaceManager {
	private static String TAG = "FaceManager";

	private static FaceManager instance = null;
	private ArrayList<HashMap<String, Object>> lstImageItem = new ArrayList<HashMap<String, Object>>();
	public final String RES_KEY = "res";
	public final String PHRASE_KEY = "phrase";
	
	public FaceManager(){
		loadFace();
	}
	public static synchronized FaceManager getInstance(){
		if (instance == null)
			instance= new FaceManager();
		return instance;
	}
	
	public int getFaceCount() {
		return lstImageItem.size();
	}

	/**
	 * 加载表情
	 */
	void loadFace() {
		HashMap<String, Object> faceList = null;
		int i = 0;

		// / 加载哈希表
		faceList = new HashMap<String, Object>();
		faceList.put(PHRASE_KEY, "[呵呵]");
		faceList.put(RES_KEY, R.drawable.smile);
		lstImageItem.add(i++, faceList);
		faceList = new HashMap<String, Object>();
		faceList.put(PHRASE_KEY, "[嘻嘻]");
		faceList.put(RES_KEY, R.drawable.tooth);
		lstImageItem.add(i++, faceList);
		faceList = new HashMap<String, Object>();
		faceList.put(PHRASE_KEY, "[哈哈]");
		faceList.put(RES_KEY, R.drawable.laugh);
		lstImageItem.add(i++, faceList);
		faceList = new HashMap<String, Object>();
		faceList.put(PHRASE_KEY, "[爱你]");
		faceList.put(RES_KEY, R.drawable.love);
		lstImageItem.add(i++, faceList);
		faceList = new HashMap<String, Object>();
		faceList.put(PHRASE_KEY, "[晕]");
		faceList.put(RES_KEY, R.drawable.dizzy);
		lstImageItem.add(i++, faceList);
		faceList = new HashMap<String, Object>();
		faceList.put(PHRASE_KEY, "[泪]");
		faceList.put(RES_KEY, R.drawable.sad);
		lstImageItem.add(i++, faceList);
		faceList = new HashMap<String, Object>();
		faceList.put(PHRASE_KEY, "[馋嘴]");
		faceList.put(RES_KEY, R.drawable.cz_org);
		lstImageItem.add(i++, faceList);
		faceList = new HashMap<String, Object>();
		faceList.put(PHRASE_KEY, "[抓狂]");
		faceList.put(RES_KEY, R.drawable.crazy);
		lstImageItem.add(i++, faceList);
		faceList = new HashMap<String, Object>();
		faceList.put(PHRASE_KEY, "[哼]");
		faceList.put(RES_KEY, R.drawable.hate);
		lstImageItem.add(i++, faceList);
		faceList = new HashMap<String, Object>();
		faceList.put(PHRASE_KEY, "[抱抱]");
		faceList.put(RES_KEY, R.drawable.bb_org);
		lstImageItem.add(i++, faceList);
		faceList = new HashMap<String, Object>();
		faceList.put(PHRASE_KEY, "[可爱]");
		faceList.put(RES_KEY, R.drawable.tz_org);
		lstImageItem.add(i++, faceList);
		faceList = new HashMap<String, Object>();
		faceList.put(PHRASE_KEY, "[怒]");
		faceList.put(RES_KEY, R.drawable.angry);
		lstImageItem.add(i++, faceList);
		faceList = new HashMap<String, Object>();
		faceList.put(PHRASE_KEY, "[汗]");
		faceList.put(RES_KEY, R.drawable.sweat);
		lstImageItem.add(i++, faceList);
		faceList = new HashMap<String, Object>();
		faceList.put(PHRASE_KEY, "[困]");
		faceList.put(RES_KEY, R.drawable.sleepy);
		lstImageItem.add(i++, faceList);
		faceList = new HashMap<String, Object>();
		faceList.put(PHRASE_KEY, "[害羞]");
		faceList.put(RES_KEY, R.drawable.shame_org);
		lstImageItem.add(i++, faceList);
		faceList = new HashMap<String, Object>();
		faceList.put(PHRASE_KEY, "[睡觉]");
		faceList.put(RES_KEY, R.drawable.sleep_org);
		lstImageItem.add(i++, faceList);
		faceList = new HashMap<String, Object>();
		faceList.put(PHRASE_KEY, "[钱]");
		faceList.put(RES_KEY, R.drawable.money_org);
		lstImageItem.add(i++, faceList);
		faceList = new HashMap<String, Object>();
		faceList.put(PHRASE_KEY, "[偷笑]");
		faceList.put(RES_KEY, R.drawable.hei_org);
		lstImageItem.add(i++, faceList);
		faceList = new HashMap<String, Object>();
		faceList.put(PHRASE_KEY, "[酷]");
		faceList.put(RES_KEY, R.drawable.cool_org);
		lstImageItem.add(i++, faceList);
		faceList = new HashMap<String, Object>();
		faceList.put(PHRASE_KEY, "[衰]");
		faceList.put(RES_KEY, R.drawable.cry);
		lstImageItem.add(i++, faceList);
		faceList = new HashMap<String, Object>();
		faceList.put(PHRASE_KEY, "[吃惊]");
		faceList.put(RES_KEY, R.drawable.cj_org);
		lstImageItem.add(i++, faceList);
		faceList = new HashMap<String, Object>();
		faceList.put(PHRASE_KEY, "[闭嘴]");
		faceList.put(RES_KEY, R.drawable.bz_org);
		lstImageItem.add(i++, faceList);
		faceList = new HashMap<String, Object>();
		faceList.put(PHRASE_KEY, "[鄙视]");
		faceList.put(RES_KEY, R.drawable.bs2_org);
		lstImageItem.add(i++, faceList);
		faceList = new HashMap<String, Object>();
		faceList.put(PHRASE_KEY, "[挖鼻屎]");
		faceList.put(RES_KEY, R.drawable.kbs_org);
		lstImageItem.add(i++, faceList);
		faceList = new HashMap<String, Object>();
		faceList.put(PHRASE_KEY, "[花心]");
		faceList.put(RES_KEY, R.drawable.hs_org);
		lstImageItem.add(i++, faceList);
		faceList = new HashMap<String, Object>();
		faceList.put(PHRASE_KEY, "[鼓掌]");
		faceList.put(RES_KEY, R.drawable.gz_org);
		lstImageItem.add(i++, faceList);
		faceList = new HashMap<String, Object>();
		faceList.put(PHRASE_KEY, "[失望]");
		faceList.put(RES_KEY, R.drawable.sw_org);
		lstImageItem.add(i++, faceList);
		faceList = new HashMap<String, Object>();
		faceList.put(PHRASE_KEY, "[思考]");
		faceList.put(RES_KEY, R.drawable.sk_org);
		lstImageItem.add(i++, faceList);
		faceList = new HashMap<String, Object>();
		faceList.put(PHRASE_KEY, "[生病]");
		faceList.put(RES_KEY, R.drawable.sb_org);
		lstImageItem.add(i++, faceList);
		faceList = new HashMap<String, Object>();
		faceList.put(PHRASE_KEY, "[亲亲]");
		faceList.put(RES_KEY, R.drawable.qq_org);
		lstImageItem.add(i++, faceList);
		faceList = new HashMap<String, Object>();
		faceList.put(PHRASE_KEY, "[怒骂]");
		faceList.put(RES_KEY, R.drawable.nm_org);
		lstImageItem.add(i++, faceList);
		faceList = new HashMap<String, Object>();
		faceList.put(PHRASE_KEY, "[太开心]");
		faceList.put(RES_KEY, R.drawable.mb_org);
		lstImageItem.add(i++, faceList);
		faceList = new HashMap<String, Object>();
		faceList.put(PHRASE_KEY, "[懒得理你]");
		faceList.put(RES_KEY, R.drawable.ldln_org);
		lstImageItem.add(i++, faceList);
		faceList = new HashMap<String, Object>();
		faceList.put(PHRASE_KEY, "[右哼哼]");
		faceList.put(RES_KEY, R.drawable.yhh_org);
		lstImageItem.add(i++, faceList);
		faceList = new HashMap<String, Object>();
		faceList.put(PHRASE_KEY, "[左哼哼]");
		faceList.put(RES_KEY, R.drawable.zhh_org);
		lstImageItem.add(i++, faceList);
		faceList = new HashMap<String, Object>();
		faceList.put(PHRASE_KEY, "[嘘]");
		faceList.put(RES_KEY, R.drawable.x_org);
		lstImageItem.add(i++, faceList);
		faceList = new HashMap<String, Object>();
		faceList.put(PHRASE_KEY, "[委屈]");
		faceList.put(RES_KEY, R.drawable.wq_org);
		lstImageItem.add(i++, faceList);
		faceList = new HashMap<String, Object>();
		faceList.put(PHRASE_KEY, "[吐]");
		faceList.put(RES_KEY, R.drawable.t_org);
		lstImageItem.add(i++, faceList);
		faceList = new HashMap<String, Object>();
		faceList.put(PHRASE_KEY, "[可怜]");
		faceList.put(RES_KEY, R.drawable.kl_org);
		lstImageItem.add(i++, faceList);
		faceList = new HashMap<String, Object>();
		faceList.put(PHRASE_KEY, "[打哈气]");
		faceList.put(RES_KEY, R.drawable.k_org);
		lstImageItem.add(i++, faceList);
		faceList = new HashMap<String, Object>();
		faceList.put(PHRASE_KEY, "[顶]");
		faceList.put(RES_KEY, R.drawable.d_org);
		lstImageItem.add(i++, faceList);
		faceList = new HashMap<String, Object>();
		faceList.put(PHRASE_KEY, "[疑问]");
		faceList.put(RES_KEY, R.drawable.yw_org);
		lstImageItem.add(i++, faceList);
		faceList = new HashMap<String, Object>();
		faceList.put(PHRASE_KEY, "[握手]");
		faceList.put(RES_KEY, R.drawable.ws_org);
		lstImageItem.add(i++, faceList);
		faceList = new HashMap<String, Object>();
		faceList.put(PHRASE_KEY, "[耶]");
		faceList.put(RES_KEY, R.drawable.ye_org);
		lstImageItem.add(i++, faceList);
		faceList = new HashMap<String, Object>();
		faceList.put(PHRASE_KEY, "[good]");
		faceList.put(RES_KEY, R.drawable.good_org);
		lstImageItem.add(i++, faceList);
		faceList = new HashMap<String, Object>();
		faceList.put(PHRASE_KEY, "[弱]");
		faceList.put(RES_KEY, R.drawable.sad_org);
		lstImageItem.add(i++, faceList);
		faceList = new HashMap<String, Object>();
		faceList.put(PHRASE_KEY, "[不要]");
		faceList.put(RES_KEY, R.drawable.no_org);
		lstImageItem.add(i++, faceList);
		faceList = new HashMap<String, Object>();
		faceList.put(PHRASE_KEY, "[ok]");
		faceList.put(RES_KEY, R.drawable.ok_org);
		lstImageItem.add(i++, faceList);
		faceList = new HashMap<String, Object>();
		faceList.put(PHRASE_KEY, "[赞]");
		faceList.put(RES_KEY, R.drawable.z2_org);
		lstImageItem.add(i++, faceList);
		faceList = new HashMap<String, Object>();
		faceList.put(PHRASE_KEY, "[来]");
		faceList.put(RES_KEY, R.drawable.come_org);
		lstImageItem.add(i++, faceList);
		faceList = new HashMap<String, Object>();
		faceList.put(PHRASE_KEY, "[蛋糕]");
		faceList.put(RES_KEY, R.drawable.cake);
		lstImageItem.add(i++, faceList);
		faceList = new HashMap<String, Object>();
		faceList.put(PHRASE_KEY, "[心]");
		faceList.put(RES_KEY, R.drawable.heart);
		lstImageItem.add(i++, faceList);
		faceList = new HashMap<String, Object>();
		faceList.put(PHRASE_KEY, "[伤心]");
		faceList.put(RES_KEY, R.drawable.unheart);
		lstImageItem.add(i++, faceList);
		faceList = new HashMap<String, Object>();
		faceList.put(PHRASE_KEY, "[钟]");
		faceList.put(RES_KEY, R.drawable.clock_org);
		lstImageItem.add(i++, faceList);
		faceList = new HashMap<String, Object>();
		faceList.put(PHRASE_KEY, "[猪头]");
		faceList.put(RES_KEY, R.drawable.pig);
		lstImageItem.add(i++, faceList);
		faceList = new HashMap<String, Object>();
		faceList.put(PHRASE_KEY, "[话筒]");
		faceList.put(RES_KEY, R.drawable.m_org);
		lstImageItem.add(i++, faceList);
		faceList = new HashMap<String, Object>();
		faceList.put(PHRASE_KEY, "[月亮]");
		faceList.put(RES_KEY, R.drawable.moon);
		lstImageItem.add(i++, faceList);
		faceList = new HashMap<String, Object>();
		faceList.put(PHRASE_KEY, "[下雨]");
		faceList.put(RES_KEY, R.drawable.rain);
		lstImageItem.add(i++, faceList);
		faceList = new HashMap<String, Object>();
		faceList.put(PHRASE_KEY, "[太阳]");
		faceList.put(RES_KEY, R.drawable.sun);
		lstImageItem.add(i++, faceList);
		faceList = new HashMap<String, Object>();
		faceList.put(PHRASE_KEY, "[悼念乔布斯]");
		faceList.put(RES_KEY, R.drawable.jobs_org);
		lstImageItem.add(i++, faceList);
		faceList = new HashMap<String, Object>();
		faceList.put(PHRASE_KEY, "[iPhone]");
		faceList.put(RES_KEY, R.drawable.iphone_org);
		lstImageItem.add(i++, faceList);
		faceList = new HashMap<String, Object>();
		faceList.put(PHRASE_KEY, "[蜡烛]");
		faceList.put(RES_KEY, R.drawable.lazu_org);
		lstImageItem.add(i++, faceList);
	}
	
	public ArrayList<HashMap<String, Object>> getFaceList() {
		return lstImageItem;
	}
	
	/**
	 * 卸载表情
	 */
	void unloadFace() {
		lstImageItem.clear();
	}
	
	/**
	 * 根据索引号获取表情图片
	 * @param index 索引号
	 * @return 位图资源
	 */
	public int getFaceDrawableRes(int index) {
		HashMap<String, Object> faceList = null;
		
		if (index >= lstImageItem.size()) {
			return -1;
		}
		
		faceList = lstImageItem.get(index);
		
		Log.i(TAG, "res: " + faceList.get(RES_KEY));
		
		return (Integer)faceList.get(RES_KEY);
	}
	
	/**
	 * 根据索引号获取表情短语
	 * @param index 索引号
	 * @return 短语
	 */
	public String getFacePhrase(int index) {
		HashMap<String, Object> faceList = null;
		
		if (index >= lstImageItem.size()) {
			return null;
		}
		
		faceList = lstImageItem.get(index);
		
		Log.i(TAG, "phrase: " + faceList.get(PHRASE_KEY));
		
		return (String)faceList.get(PHRASE_KEY);
	}
	
	/**
	 * 根据短语获取表情
	 * @param strPhrase 短语
	 * @return 表情资源
	 */
	public int getFaceDrawablResByPhrase(String strPhrase) {
		for (HashMap<String, Object> faceList : lstImageItem) {
			if (faceList.get(PHRASE_KEY).equals(strPhrase)) {
				return (Integer)faceList.get(RES_KEY);
			}
		}
		
		return -1;
	}

	/**
	 * 根据GridView的索引号获取图片
	 * @param index 索引号
	 * @return 返回存放文字和图片的控件
	 */
	public SpannableString getImageByIndex(Context context, int index) {
		String content = getFacePhrase(index);
		SpannableString ss = new SpannableString(content);
		int starts = 0;
		int end = 0;
		
		if(content.indexOf("[", starts) != -1 && content.indexOf("]", end) != -1){
			starts = content.indexOf("[", starts);
			end = content.indexOf("]", end);
			String phrase = content.substring(starts,end + 1);
			int resId = getFaceDrawablResByPhrase(phrase);
			
			try {
				Drawable drawable = context.getResources().getDrawable(resId);  
				if (drawable != null) {
					drawable.setBounds(0, 0, drawable.getIntrinsicWidth() + 10, drawable.getIntrinsicHeight() + 10); 
			        ImageSpan span = new ImageSpan(drawable, ImageSpan.ALIGN_BASELINE);  
			        ss.setSpan(span, starts,end + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);  
				}
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			}

		}
		return ss;
	}
	
	public SpannableString convertTextToEmotion(Context context, String content) {
		SpannableString ss = new SpannableString(content);  
        int starts = 0;  
        int end = 0;  
          
        while (content.indexOf("[", starts) != -1 && content.indexOf("]", end) != -1) {  
            starts = content.indexOf("[", starts);  
            end = content.indexOf("]", end);  
            String phrase = content.substring(starts, end + 1);   

            int resId = getFaceDrawablResByPhrase(phrase);
            if (resId < 0) {
            	return null;
            }
              
            try { 
                Drawable drawable = context.getResources().getDrawable(resId);    
                if (drawable != null) {  
                    drawable.setBounds(0, 0, drawable.getIntrinsicWidth() + 10, drawable.getIntrinsicHeight() + 10);   
                    ImageSpan span = new ImageSpan(drawable, ImageSpan.ALIGN_BASELINE);    
                    ss.setSpan(span, starts,end + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);    
                }  
            } catch (SecurityException e) {  
                e.printStackTrace();  
            } catch (IllegalArgumentException e) {  
                e.printStackTrace();  
            }
  
            starts++;
            end++;
        }  
        
        return ss;  
	}
}
