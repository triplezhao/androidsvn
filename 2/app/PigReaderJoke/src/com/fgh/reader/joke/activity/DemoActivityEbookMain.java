package com.fgh.reader.joke.activity;



import pigframe.PigMainTabAct;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.fgh.reader.joke.R;


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
		AddTab(R.drawable.file_doc,"目录", DemoBookSelectActivity.class);
		AddTab(R.drawable.page_mark, "书签", DemoBookStoryActivity.class);
		AddTab(R.drawable.tab_local_book,"本地书库", DemoBookLocalActivity.class);
		setTabHostBg(R.drawable.backgroup_cooper);
//		getTabHost().getTabWidget().setBackgroundResource(R.drawable.tabbar);
		getTabHost().getTabWidget().setBackgroundDrawable(null);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
				MenuItem item;
				item = menu.add(0, 11, 0, "帮助");
				item.setIcon(R.drawable.menu_info);
				item = menu.add(0, 12, 0, "分享");
				item.setIcon(R.drawable.menu_share);
				item = menu.add(0, 13, 0, "退出");
				item.setIcon(R.drawable.menu_exit);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case 11:
			// 添加要处理的内容
			break;
		case 12:
			// 添加要处理的内容
			break;
		case 13:
			// 添加要处理的内容
			break;
		}
		return false;
		
	}
	
}
