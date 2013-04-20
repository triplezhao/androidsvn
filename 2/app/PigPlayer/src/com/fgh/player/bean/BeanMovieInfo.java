package com.fgh.player.bean;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class BeanMovieInfo {
	private String title ="";
	private String showid ="";
	private String weburl ="";
	private String playurl ="";
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getShowid() {
		return showid;
	}

	public void setShowid(String showid) {
		this.showid = showid;
	}

	public String getWeburl() {
		return weburl;
	}

	public void setWeburl(String weburl) {
		this.weburl = weburl;
	}

	public String getPlayurl() {
		return playurl;
	}

	public void setPlayurl(String playurl) {
		this.playurl = playurl;
	}

	/**
	 * 
	 * {"status":"success","lang":"","showid":"d260435481ee11e1b52a",
	 * "title":"\u6211\u662f\u4f20\u5947 \u7b2c\u4e00\u671f",
	 * "weburl":"http://v.youku.com/v_show/id_XNDAyNTY5MzY0.html",
	 * "results":{
	 * 		"3gphd":[{"seconds":2706,"url":"http://f.youku.com/player/getFlvPath/sid/134182875323084_01/st/mp4/fileid/03002001004FC32877D77F0052DFF16521DA97-3BF2-2599-894A-F01511978DE3?K=eed5e40d5398a03e2827cfd7&hd=0","id":1,"size":96051979}],

	 * @param json
	 */
	public BeanMovieInfo(String json){
		JSONObject jsonobj;
		try {
			jsonobj = new JSONObject(json);
			this.title = jsonobj.optString("title");
			this.showid = jsonobj.optString("showid");
			this.weburl = jsonobj.optString("weburl");
		    JSONObject json_result_obj;
			json_result_obj = jsonobj.getJSONObject("results");
			JSONArray resultslist=json_result_obj.optJSONArray("3gphd");
			JSONObject firest_json=resultslist.getJSONObject(0);
			
			this.playurl=firest_json.optString("url");
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public BeanMovieInfo(JSONObject json){
		
			 this.title = json.optString("title");
		     this.showid = json.optString("showid");
		     this.weburl = json.optString("weburl");
		
			JSONObject json_result_obj;
			
			try {
				json_result_obj = json.getJSONObject("results");
				JSONArray resultslist=json_result_obj.optJSONArray("3gphd");
				JSONObject firest_json=resultslist.getJSONObject(0);
				
			    this.playurl=firest_json.optString("url");
			    
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
}
