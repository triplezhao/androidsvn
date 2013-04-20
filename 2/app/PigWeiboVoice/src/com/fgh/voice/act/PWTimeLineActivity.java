package com.fgh.voice.act;

import java.util.List;

import pigframe.control.PigController;
import pigframe.httpFrame.PigHttpBaseActivity;
import pigframe.httpFrame.PigHttpRequest;
import pigframe.httpFrame.PigHttpResponse;
import pigframe.views.widet.PullToRefreshListView;
import pigframe.views.widet.PullToRefreshListView.OnRefreshListener;
import weibo4android.Paging;
import weibo4android.Status;
import weibo4android.Weibo;
import weibo4android.WeiboException;
import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.fgh.voice.R;
import com.fgh.voice.PWApplication;
import com.fgh.voice.adapter.StatusAdapter;
import com.fgh.voice.util.PigAsyncTask;



public class PWTimeLineActivity extends PW_BaseActivity {

	private static final String TAG = "PWTimeLine_Activity";
	private PullToRefreshListView lv_weibolist;
	private StatusAdapter adapter_weibolist;
	private List<Status> weibolist;
	
	
	private LinearLayout mLoadView = null;
	private LayoutInflater mInflater = null;
	private ProgressBar mLoadViewProgress = null;
	private TextView mLoadViewText = null;
	
	
	
	private String nick_name = null;
	private boolean istrend = false;
	public static String EXTRA_STR_NICK_NAME = "EXTRA_STR_NICK_NAME";
	public static String EXTRA_STR_IS_TREND = "EXTRA_STR_IS_TREND";
	
	
    
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline_layout);
		initPre();
		if(nick_name==null){
			finish();
			return ;
		}
		initFindViews();
		initListeners();
		initPost();
	}
	
	@Override
	public void initFindViews() {
		lv_weibolist=(PullToRefreshListView) findViewById(R.id.lv_weibolist);
		mLoadView = (LinearLayout) mInflater.inflate(
				R.layout.pull_to_load_footer, null);
		mLoadViewProgress = (ProgressBar) mLoadView
				.findViewById(R.id.pull_to_refresh_progress);
		mLoadViewText = (TextView) mLoadView
				.findViewById(R.id.pull_to_refresh_text);
		lv_weibolist.addFooterView(mLoadView);
		
		
	}

	@Override
	public void initListeners() {
	}

	@Override
	public void initPost() {
		// TODO Auto-generated method stub
		
		lv_weibolist.setAdapter(adapter_weibolist);
		
		new HeaderTask().execute();
		
		
		lv_weibolist.setOnRefreshListener(new OnRefreshListener() {
			
			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				new HeaderTask().execute();
			}
		});
		
		mLoadView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View paramView) {
				new FooterTask().execute();
			}
		});
		
	}

	
	@Override
	public void initPre() {
		// TODO Auto-generated method stub
		mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		adapter_weibolist=new StatusAdapter(getThisActivity());
		nick_name=getIntent().getStringExtra(EXTRA_STR_NICK_NAME);
		istrend=getIntent().getBooleanExtra(EXTRA_STR_IS_TREND,false);
		
		
	}

	
	private class HeaderTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			lv_weibolist.prepareForRefresh();
		}

		@Override
		protected Void doInBackground(Void... params) {
			// Simulates a background job.
//        	getAndShowNewWeiboListHandler();
			List<weibo4android.Status> tmpList = null;
			/// 从最老的微博开始往前再获取20条微博
			tmpList = getWeiboList(new Paging(getMaxReadWeiboId()));
			/// 获取微博列表
	        if (tmpList != null) {
	            if (weibolist == null) {
	            	weibolist = tmpList;
	            } else {
	            	weibolist.addAll( 0,tmpList);
	            }
	            adapter_weibolist.addNewStatus(tmpList);
	        }
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			
			adapter_weibolist.notifyDataSetChanged();
			
			lv_weibolist.onRefreshComplete();
		}

	}
	private class FooterTask extends AsyncTask<Void, Void, Void> {
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			mLoadViewProgress.setVisibility(View.VISIBLE);
			mLoadViewText.setText(R.string.pull_to_refresh_refreshing_label);
		}
		
		@Override
		protected Void doInBackground(Void... params) {
			// Simulates a background job.
			
			List<weibo4android.Status> tmpList = null;

			/// 从最老的微博开始往前再获取20条微博
			tmpList = getWeiboList( new Paging(1, 20, 1, getMinReadWeiboId() - 1));
			
			/// 获取微博列表
	        if (tmpList != null) {
	            if (weibolist == null) {
	            	weibolist = tmpList;
	            } else {
	            	weibolist.addAll( tmpList);
	            	
	            }
	            adapter_weibolist.addOldStatus(tmpList);
	        }
			
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			adapter_weibolist.notifyDataSetChanged();
			mLoadViewProgress.setVisibility(View.INVISIBLE);
			mLoadViewText.setText(R.string.pull_to_refresh_tap_label);
//			showToast(PWApplication.getUser().getUser());
		}
		
	}
	
	public  long getMaxReadWeiboId() {
		long maxReadWeiboId = 1; ///<读到的最大微博ID，用于下次更新
		if (weibolist != null) {
			for (Status wb : weibolist) {
				if (wb.getId() > maxReadWeiboId) {
					maxReadWeiboId = wb.getId();
				}
			}
		}
		
		return maxReadWeiboId;
	}
	public  long getMinReadWeiboId() {
		long maxReadWeiboId = Long.MAX_VALUE; ; ///<读到的最大微博ID，用于下次更新
		if (weibolist != null) {
			for (Status wb : weibolist) {
				if (wb.getId() < maxReadWeiboId) {
					maxReadWeiboId = wb.getId();
				}
			}
		}
		
		return maxReadWeiboId;
	}
	public  List<Status> getWeiboList(Paging page) {
		Weibo weibo = new Weibo();
		weibo.setToken(PWApplication.getDefUser().getToken(),
				PWApplication.getDefUser().getTokenSecret());
//		 page=new Paging(2);
		try {
			//这里控制返回的内容是什么类型，3代表视频
			if(istrend){
				return weibo.getTrendStatus(nick_name, page);
			}else{
				return weibo.getUserTimeline(nick_name,page);
			}
//			return weibo.getUserTimeline("中国好声音",null,3,page);
		} catch (WeiboException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	
	public static void open(Activity from,String nickname,boolean isTrend){
		Bundle data=new Bundle();
		data.putString(EXTRA_STR_NICK_NAME, nickname);
		data.putBoolean(EXTRA_STR_IS_TREND, isTrend);
		PigController.startAct(PWTimeLineActivity.class, from, false, data);
	}
	
}
