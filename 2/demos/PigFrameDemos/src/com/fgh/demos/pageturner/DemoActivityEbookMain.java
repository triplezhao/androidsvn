package com.fgh.demos.pageturner;

import pigframe.PigMainTabAct;
import android.os.Bundle;

import com.fgh.demos.R;
import com.fgh.demos.pageturner.childtab.DemoBookSelectActivity;
import com.fgh.demos.pageturner.childtab.DemoBookStoryActivity;
import com.fgh.demos.pageturner.childtab.DemoDownBookActivity;

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
		AddTab(R.drawable.tab_home, "阅读历史", DemoBookStoryActivity.class);
		AddTab(R.drawable.tab_info,"本地书库", DemoBookSelectActivity.class);
		AddTab(R.drawable.icon,"网上搜书", DemoDownBookActivity.class);
		setTabWidgetBg(R.drawable.topbar_bg);
	}
	
}
