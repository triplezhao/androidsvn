package com.fgh.voice.act;

import java.util.ArrayList;
import java.util.List;

import pigframe.PigBaseActivity;
import pigframe.control.PigController;
import pigframe.views.TagCloudyView;
import weibo4android.Paging;
import weibo4android.Trend;
import weibo4android.Trends;
import weibo4android.Weibo;
import weibo4android.WeiboException;
import android.app.Activity;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.fgh.voice.PWApplication;
import com.fgh.voice.R;

public class PWHotTrendsAct extends PW_BaseActivity{

	private List<Trend> trendslist;
	
	AssetManager asm = null;
	TagCloudyView layoutmain;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_staticlist_layout);
		  setContentView(R.layout.activity_staticlist_layout); 
		initPre();
		initFindViews();
		initListeners();
		initPost();
	}
	
	
	@Override
	public void initFindViews() {
		// TODO Auto-generated method stub
//		lv_trendslist=(ListView) findViewById(R.id.lv_trendslist);
	}

	@Override
	public void initListeners() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initPost() {
		// TODO Auto-generated method stub
//		adapter_trendslist=new StaticUserAdapter(this);
//		adapter_trendslist.setList(trendslist);
//		lv_trendslist.setAdapter(adapter_trendslist);
		
//		trendslist=new ParseReAppXml().getReApps(this);
		
		trendslist=new ArrayList<Trend>();
		new LoadTrendsTask().execute();
		
	}

	@Override
	public void initPre() {
		// TODO Auto-generated method stub
		
	     layoutmain=(TagCloudyView) findViewById(R.id.tcv_tagcloudy);
	     String title=getResources().getString(R.string.PIG_FRAME_APP_CLOUDY);
	     initTitleBar(title, null,null,
					0,0,
					0, 0,
					null,null);
	     setTitleBarBg(R.color.transparent);
	     asm=getAssets();
	}
	
	
 private void add2CloudyView(){
		 
		 LayoutInflater inflater = LayoutInflater.from(this); 
		 
		 for (Trend trend : trendslist) {
			 View  convertView = inflater.inflate(R.layout.hotwords_item, null);  
	         TextView  label = (TextView) convertView  
	                 .findViewById(R.id.apps_textview);  
//	         label.setTextColor(R.color.red);
//	            if(trend.pic==null){
//	 				icon.setImageResource(R.drawable.icon);
//	 			}else{
//	 		 		pigframe.util.PigImageUtil.showImage(true,icon, trend.pic, R.drawable.icon);
//	 		 			
//	 			}
	        
	         
	         final String labelText=trend.getName();
	         label.setText(labelText);  
	         
	         convertView.setOnClickListener(new OnClickListener() {
	 			
	 			@Override
	 			public void onClick(View v) {
	 				// TODO Auto-generated method stub
	 			showToast(labelText);
	 			PWTimeLineActivity.open(getThisActivity(),labelText,true);
	 			}
	         });
	         layoutmain.addCloudy(convertView);
		}
	    
	 }
 
 
 private class LoadTrendsTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected Void doInBackground(Void... params) {
			// Simulates a background job.
			Weibo weibo = new Weibo();
			weibo.setToken(PWApplication.getDefUser().getToken(),
					PWApplication.getDefUser().getTokenSecret());
			List<Trends> trends;
			try {
				trends = weibo.getTrendsDaily(null);
				Trend[] trendarray=trends.get(0).getTrends();
				for (Trend trend : trendarray) {
					trendslist.add(trend);
				}
				
			} catch (WeiboException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			add2CloudyView();
			layoutmain.initCloud();

		}

	}
}
