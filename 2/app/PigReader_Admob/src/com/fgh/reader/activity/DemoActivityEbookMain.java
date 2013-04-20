package com.fgh.reader.activity;



import pigframe.PigMainTabAct;
import android.os.Bundle;

import com.fgh.reader.R;


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
	}

	
}
