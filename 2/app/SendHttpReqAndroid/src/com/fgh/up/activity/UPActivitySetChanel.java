package com.fgh.up.activity;


import pigframe.PigBaseActivity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ListView;

import com.fgh.up.R;
import com.fgh.up.adapter.ChanelAdapter;
import com.fgh.up.database.ChanelBean;
import com.fgh.up.database.ChanelDao;

public class UPActivitySetChanel extends PigBaseActivity{

	private ListView lv_chanellist;
	private ChanelAdapter chanelAdapter;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_set);
		initPre();
		initFindViews();
		initListeners();
		initPost();
	}
	
	
	@Override
	public void initFindViews() {
		// TODO Auto-generated method stub
		lv_chanellist=(ListView) findViewById(R.id.lv_sets);
	}

	@Override
	public void initListeners() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initPost() {
		// TODO Auto-generated method stub
		chanelAdapter=new ChanelAdapter(this);
		lv_chanellist.setAdapter(chanelAdapter);
		
	}

	@Override
	public void initPre() {
		// TODO Auto-generated method stub
		initTitleBar("主页", "返回","添加渠道",
				0,0,
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
						showAddChanelDialog();
					}
				});
		
	}
	
	private void showAddChanelDialog(){
		
		final EditText et_name=new EditText(this);
		et_name.setHint("渠道名字");
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this); 
		builder.setMessage("添加渠道名") 
		       .setCancelable(false) 
		       .setPositiveButton("确定", new DialogInterface.OnClickListener() { 
		           public void onClick(DialogInterface dialog, int id) { 
		        	
		        	ChanelDao chaneldao=ChanelDao.getInstance(getThisActivity());
		       		ChanelBean chanel=new ChanelBean();
		       		chanel.setChanelName(et_name.getText().toString());
		       		chaneldao.insert(chanel);
		       		chanelAdapter.refreshUI();
		           } 
		       }) 
		       .setNegativeButton("取消", new DialogInterface.OnClickListener() { 
		           public void onClick(DialogInterface dialog, int id) { 
		                dialog.cancel(); 
		           } 
		       })
		       .setView(et_name); 
		
		AlertDialog addDialog = builder.create(); 
		addDialog.show();
		
	}

}
