/**
 * 
 */
package com.fgh.player.apiaction.parse;


import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.fgh.player.bean.BeanMovie;
import com.fgh.player.bean.BeanMovieInfo;


/**
 * @author sina
 *
 */
public class ParseMovie {

	
	/**
	 * 影片搜索列表
	 * 
	 * {"status":"success","pz":30,"total":197526,"pg":1,
	 * "results":[
	 * {"duration":"08:54","reputation":6.419161328535531,"videoid":"XNDI0NTI5Njk2","img":"http://g3.ykimg.com/0100641F464FF728122630021E04E8CCBDD690-57D9-D2E7-02AC-A2145A46E119","title":"\u201c\u81ea\u4e60\u5ba4\u5973\u751f\u201d\u5468\u5b50\u7430\u300a\u6211\u662f\u4f20\u5947\u300b\u4f18\u9177\u725b\u4eba\u76db\u51782012"}
	 * ,{"duration":"55:01","reputation":8.941761385721229,"videoid":"XNDEzNjIyODUy","img":"http://g4.ykimg.com/0100641F464FDB38DF50EA0052DFF109CE52C9-8DFA-9AE4-EE01-B8252E980096","title":"\u6211\u662f\u4f20\u5947\u7b2c\u56db\u671f"},
	 * @param res
	 * @return
	 */
	public static List<BeanMovie> parse_SearchBeanMovie(String res)  {
		JSONObject jsonobj=null;
		List<BeanMovie> beanMovieList=null;
		try {
			jsonobj = new JSONObject(res);
			JSONArray resultslist=jsonobj.optJSONArray("results");
			beanMovieList=BeanMovie.createFromJarray(resultslist);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return beanMovieList;
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
	public static BeanMovieInfo parse_getMovieInfo(String res)  {
		JSONObject jsonobj=null;
		BeanMovieInfo movieinfo=null;
		try {
			jsonobj = new JSONObject(res);
			movieinfo=new BeanMovieInfo(jsonobj);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return movieinfo;
	}
}
