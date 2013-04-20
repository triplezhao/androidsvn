package com.fgh.demos.maintab;

import pigframe.PigMainTabAct;
import android.os.Bundle;

import com.fgh.demos.R;
import com.fgh.demos.actionbar.DemoActionBarActivity;
import com.fgh.demos.httpDemo.DemoHttpActivity;
import com.fgh.demos.sqlite.DemoCURDActivity;
import com.fgh.demos.tagcloudy.Demo_TagCloudy_Activity;
import com.fgh.demos.zoompic.DemoPicZoomActivity;

/**
 * 修改tab样式的例子
 * @author ztw
 *
 */
public class DemoMainTabActivity extends PigMainTabAct{

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		AddTab(R.drawable.tab_home, "云组件", Demo_TagCloudy_Activity.class);
		AddTab(R.drawable.tab_info,"网络数据", DemoHttpActivity.class);
		AddTab(R.drawable.icon,"网络图片", DemoPicZoomActivity.class);
		AddTab(R.drawable.icon,"sqlite", DemoCURDActivity.class);
		AddTab(R.drawable.icon,"actionBar", DemoActionBarActivity.class);
		setTabWidgetBg(R.drawable.topbar_bg);
	}
	
}
