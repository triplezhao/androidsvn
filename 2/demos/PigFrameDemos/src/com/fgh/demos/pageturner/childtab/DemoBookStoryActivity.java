package com.fgh.demos.pageturner.childtab;

import java.util.List;

import pigframe.PigBaseActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.ListView;

import com.fgh.demos.R;
import com.fgh.demos.pageturner.sqlite.BookStoryAdapter;
import com.fgh.demos.sqlite.UserAdapter;

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
		
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		adapter.refreshUI();
	}

}