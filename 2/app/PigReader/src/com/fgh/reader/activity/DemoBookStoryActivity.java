package com.fgh.reader.activity;

import pigframe.PigBaseActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.widget.ListView;
import android.widget.Toast;

import com.fgh.reader.R;
import com.fgh.reader.adapter.BookStoryAdapter;
import com.umeng.analytics.MobclickAgent;
import com.umeng.update.UmengUpdateAgent;
import com.umeng.update.UmengUpdateListener;
import com.umeng.update.UpdateResponse;


public class DemoBookStoryActivity extends PigBaseActivity {
	private final static String TAG = "DemoBookStoryActivity";
	private ListView lv_book_story;
	private BookStoryAdapter  adapter;


	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.demo_activity_bookstory);
		initPre();
		initFindViews();
		initListeners();
		initPost();
	
	}


	@Override
	public void initFindViews() {
		// TODO Auto-generated method stub
		lv_book_story=(ListView) findViewById(R.id.lv_book_story);
	}

	@Override
	public void initListeners() {
		// TODO Auto-generated method stub
	}

	@Override
	public void initPost() {
		// TODO Auto-generated method stub
		adapter=new BookStoryAdapter(this);
		lv_book_story.setAdapter(adapter);
	}

	@Override
	public void initPre() {
		// TODO Auto-generated method stub
		initTitleBar("阅读历史", "","",
				0,0,
				0, 0,
				null,null);
		setTitleBarBg(R.drawable.bar1);
//		final Context mContext=this.getParent();
//		UmengUpdateAgent.update(this);
//		UmengUpdateAgent.setUpdateAutoPopup(false);
//		UmengUpdateAgent.setUpdateListener(new UmengUpdateListener() {
//		        @Override
//		        public void onUpdateReturned(int updateStatus,UpdateResponse updateInfo) {
//		            switch (updateStatus) {
//		            case 0: // has update
//		                UmengUpdateAgent.showUpdateDialog(mContext, updateInfo);
//		                break;
//		            case 1: // has no update
////		                Toast.makeText(mContext, "没有更新", Toast.LENGTH_SHORT)
////		                        .show();
//		                break;
//		            case 2: // none wifi
////		                Toast.makeText(mContext, "没有wifi连接， 只在wifi下更新", Toast.LENGTH_SHORT)
////		                        .show();
//		                break;
//		            case 3: // time out
//		                Toast.makeText(mContext, "超时", Toast.LENGTH_SHORT)
//		                        .show();
//		                break;
//		            }
//		        }
//		});
		
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		adapter.refreshUI();
		 MobclickAgent.onResume(this);
	}

	public void onPause() {
	    super.onPause();
	    MobclickAgent.onPause(this);
	}
}