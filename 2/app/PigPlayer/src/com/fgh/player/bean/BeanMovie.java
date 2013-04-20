package com.fgh.player.bean;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class BeanMovie {
	private String title ="";
	private String videoid ="";
	private String img ="";
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getVideoId() {
		return videoid;
	}
	public void setVideoId(String videoid) {
		this.videoid = videoid;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	
	/**
	 * 
	 * {"status":"success"," pz":30,"total":197526,"pg":1,
        "results":[
        {"duration":"08:54","reputation":6.419161328535531,
	        "videoid ":"XNDAyNTY5MzY0",
	        "img":"http://g3.ykimg.com/0100641F464FF728122630021E04E8CCBDD690 -57D9- D2E7-02AC -A2145A46E119",
	        "title":"\u201c\u81ea\u4e60\u5ba4\u5973\u751f\u201d\u5468\u5b50\u7430\u300a\u6211\u662f\u4f20\u5947\u300b\u4f18\u9177\u725b\u4eba\u76db\u51782012"}
        ,{"duration":"55:01","reputation":8.941761385721229," videoid":.....

	 * @param json
	 */
	public BeanMovie(JSONObject json){
		 this.title = json.optString("title");
	     this.img = json.optString("img");
	     this.videoid = json.optString("videoid");
	}
	
	/**
	 * create list  form JSONArray
	 * @param array
	 * @return
	 */
	public static ArrayList<BeanMovie> createFromJarray(JSONArray array){
		ArrayList<BeanMovie> atts=new ArrayList<BeanMovie>();
		    int size=array.length();
			for (int i=0;i<size;i++) {
					JSONObject job;
					try {
						job = array.getJSONObject(i);
						atts.add(new BeanMovie(job));
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		return atts;
	}
}
