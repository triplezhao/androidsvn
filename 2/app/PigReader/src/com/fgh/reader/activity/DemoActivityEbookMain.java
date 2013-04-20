package com.fgh.reader.activity;



import pigframe.PigMainTabAct;
import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import com.fgh.reader.R;
import com.umeng.update.UmengUpdateAgent;
import com.umeng.update.UmengUpdateListener;
import com.umeng.update.UpdateResponse;


/**
 * 修改tab样式的例子
 * @author ztw
 *
 */
public class DemoActivityEbookMain extends PigMainTabAct{

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		AddTab(R.drawable.page_mark, "阅读历史", DemoBookStoryActivity.class);
		AddTab(R.drawable.tab_local_book,"本地书库", DemoBookSelectActivity.class);
		AddTab(R.drawable.bt_search_selector,"网上搜书", DemoDownBookActivity.class);
		setTabHostBg(R.drawable.backgroup_cooper);
//		getTabHost().getTabWidget().setBackgroundResource(R.drawable.bar1);
		getTabHost().getTabWidget().setBackgroundDrawable(null);
		
		final Context mContext=DemoActivityEbookMain.this;
		UmengUpdateAgent.update(this);
		UmengUpdateAgent.setUpdateAutoPopup(false);
		UmengUpdateAgent.setUpdateListener(new UmengUpdateListener() {
		        @Override
		        public void onUpdateReturned(int updateStatus,UpdateResponse updateInfo) {
		            switch (updateStatus) {
		            case 0: // has update
		                UmengUpdateAgent.showUpdateDialog(mContext, updateInfo);
		                break;
		            case 1: // has no update
//		                Toast.makeText(mContext, "没有更新", Toast.LENGTH_SHORT)
//		                        .show();
		                break;
		            case 2: // none wifi
//		                Toast.makeText(mContext, "没有wifi连接， 只在wifi下更新", Toast.LENGTH_SHORT)
//		                        .show();
		                break;
		            case 3: // time out
//		                Toast.makeText(mContext, "超时", Toast.LENGTH_SHORT)
//		                        .show();
		                break;
		            }
		        }
		});
		
	}

	
}
