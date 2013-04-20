package com.fgh.smshelp.activity;

import java.util.ArrayList;
import java.util.List;

import pigframe.PigBaseActivity;
import android.os.Bundle;
import android.widget.GridView;

import com.fgh.smshelp.R;
import com.fgh.smshelp.adapter.IndexListAdapter;
import com.fgh.smshelp.database.IndexBean;
import com.fgh.smshelp.database.IndexDao;

public class SHInboxActivity extends PigBaseActivity{

	private GridView gv_index;
	private IndexListAdapter adapter;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ofen);
		initPre();
		initFindViews();
		initListeners();
		initPost();
	}
	
	@Override
	public void initPre() {
		// TODO Auto-generated method stub
		adapter=new IndexListAdapter();
		
		List<IndexBean> data=new ArrayList<IndexBean>();
		IndexDao dao=IndexDao.getInstance(this);
		data=dao.queryAll();
		adapter.setArrayListDatas(data);
		
	}

	@Override
	public void initFindViews() {
		// TODO Auto-generated method stub
		gv_index=(GridView) findViewById(R.id.gv_send_list);
	}

	@Override
	public void initListeners() {
		// TODO Auto-generated method stub
	}

	@Override
	public void initPost() {
		// TODO Auto-generated method stub
		gv_index.setAdapter(adapter);
	}

}
