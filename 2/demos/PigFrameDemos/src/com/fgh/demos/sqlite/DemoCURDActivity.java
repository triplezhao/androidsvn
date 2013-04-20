package com.fgh.demos.sqlite;

import java.util.List;

import pigframe.PigBaseActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;

import com.fgh.demos.R;

public class DemoCURDActivity extends PigBaseActivity{

	private ListView lv_sqlite;
	private UserAdapter userAdapter;

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
		lv_sqlite=(ListView) findViewById(R.id.lv_sqlite);
	}

	@Override
	public void initListeners() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initPost() {
		// TODO Auto-generated method stub
		userAdapter=new UserAdapter(this);
		lv_sqlite.setAdapter(userAdapter);
	}

	@Override
	public void initPre() {
		// TODO Auto-generated method stub
		setContentView(R.layout.demo_activity_sqlite_layout);
		
		initTitleBar("数据库操作", "返回","添加",
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
						UserDao userdao=UserDao.getInstance(getThisActivity());
						UserBean user=new UserBean();
						user.setUser("aaaaa");
						user.setUserId(System.currentTimeMillis());
						userdao.insert(user);
						
						userAdapter.refreshUI();
//						user.setUser("bbbbb");
//						userdao.insert(user);
//						user.setUser("ccccc");
//						userdao.insert(user);
					}
				});
	}
	
}
