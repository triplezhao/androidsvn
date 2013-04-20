package com.fgh.player.activity;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import pigframe.download.DownLoadOneFile;
import pigframe.httpFrame.PigHttpBaseActivity;
import pigframe.httpFrame.PigHttpCallBackListener;
import pigframe.httpFrame.PigHttpRequest;
import pigframe.httpFrame.PigHttpResponse;
import pigframe.util.PigAndroidUtil;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.MediaController;
import android.widget.VideoView;

import com.fgh.pigframe.R;
import com.fgh.player.apiaction.PlayMovieAction;
import com.fgh.player.apiaction.PlayerActionConstants;
import com.fgh.player.bean.BeanMovieInfo;
import com.fgh.player.control.LogicControl;


/**
 * 
 * 演示利用pigframe获取网络数据的过程。
 * 1、定义一个action继承自HTTPAction。设置url、http参数、post/get、usecache等
 * 2、调用action的doaction方法。
 * 3、实现一个ActionListener接口。来接受、处理返回来的结果。
 * 
 * @author ztw
 *
 */
public  class PlayMovieHttpActivity extends PigHttpBaseActivity{
	
	
	
	private String m_video_id="";
	private VideoView vv_play;
	
	private String url1;
	private String url2;
	private String title;
	
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		initPre();
		initFindViews();
		initListeners();
		initPost();
		
	}


	@Override
	public void initFindViews() {
		// TODO Auto-generated method stub
		vv_play=(VideoView) findViewById(com.fgh.player.R.id.vv_play);
	}

	@Override
	public void initListeners() {
		// TODO Auto-generated method stub
		initTitleBar("播放页面", "返回","下载到本地",
				R.drawable.topbar_back_selector,R.drawable.topbar_btnbg,
				0, 0,
				new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						finish();
					}
				},new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						url2=	getRealUrl(url1);
						startdownload(url2);
					}
				});
	}

	
	 private void startdownload(String url){
	     File dir=new File( Environment.getExternalStorageDirectory().getPath()+"/"+"PigFrame");
		 DownLoadOneFile dfl=new DownLoadOneFile(this,url, dir,title+".mp4");
		 dfl.download();
	  
   }
	@Override
	public void initPost() {
		// TODO Auto-generated method stub
		if(!m_video_id.equals("")){
			PlayMovieAction playaction=new PlayMovieAction(this,m_video_id,false);
			playaction.doRequest(new PigHttpCallBackListener() {

				@Override
				public void onHttpCallBackListener(PigHttpRequest actioned,
						PigHttpResponse response) {
					// TODO Auto-generated method stub
					int actionid=actioned.getActionId();
					
					if (actionid==PlayerActionConstants.Action_Id_playmovie) {
						if(response.getRet_HttpStatusCode()==200){
//							if(parseDataErrorCode==OK){
							String json=response.getRet_String();
							BeanMovieInfo bminfo=new BeanMovieInfo(json);
							url1=bminfo.getPlayurl();
							title=bminfo.getTitle();
							play(url1);
					       	PigAndroidUtil.showToast(getThisActivity(), bminfo.getPlayurl(), 3000);
//						 	Intent intent= new Intent();        
//						    intent.setAction("android.intent.action.VIEW");    
//						    Uri content_url = Uri.parse(bminfo.getPlayurl());   
//						    intent.setData(content_url);  
//						    getThisActivity().startActivity(intent);
							}
						}
				}
				
			});
		}
		
		
		
	}

	@Override
	public void initPre() {
		// TODO Auto-generated method stub
		
		setContentView(R.layout.activity_play_layout);
		m_video_id=getIntent().getExtras().getString(LogicControl.key_video_id);
		
	}

	

	@Override
	public void onHttpCallBackListener(PigHttpRequest actioned,PigHttpResponse response) {
		// TODO Auto-generated method stub
	}

	public void play(String url){
//		Uri uri = Uri.parse("rtsp://v2.cache2.c.youtube.com/CjgLENy73wIaLwm3JbT_%ED%AF%80%ED%B0%819HqWohMYESARFEIJbXYtZ29vZ2xlSARSB3Jlc3VsdHNg_vSmsbeSyd5JDA==/0/0/0/video.3gp");
		Uri uri = Uri.parse(url);
		vv_play.setMediaController(new MediaController(this));
		vv_play.setVideoURI(uri);
		vv_play.start();
		vv_play.requestFocus();
	}
	
	public String  getRealUrl(String str){
//		String str="http://f.youku.com/player/getFlvPath/sid/130258503437697_01/st/flv/fileid/03000201004DA277E47AC503E2C7936B543E15-DFED-EE85-31E0-869A7BE2F5B8?K=457eb55affadc5c5161b9af9&hd=0";
		URL url=null;
		String realUrl=null;
		HttpURLConnection conn=null;
		try {
			url = new URL(str);
		    conn=(HttpURLConnection)url.openConnection();
		    conn.getResponseCode();
		    realUrl=conn.getURL().toString();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		conn.disconnect();
		System.out.println("真实URL:"+realUrl);
		return realUrl;
	}
	
}
