package com.fgh.up.activity;


import pigframe.PigBaseActivity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.fgh.up.R;
import com.fgh.up.adapter.ProxyAdapter;
import com.fgh.up.database.ProxyBean;
import com.fgh.up.database.ProxyDao;

public class UPActivitySetProxy extends PigBaseActivity{

	private ListView lv_chanellist;
	private ProxyAdapter proxyAdapter;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_proxy);
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
		proxyAdapter=new ProxyAdapter(this);
		lv_chanellist.setAdapter(proxyAdapter);
		
	}

	@Override
	public void initPre() {
		// TODO Auto-generated method stub
		initTitleBar("主页", "返回","添加代理",
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
		  /**参数设置：宽度、高度及权重值*/
        LinearLayout.LayoutParams lp_param=
                        new LinearLayout.LayoutParams(
                                        LinearLayout.LayoutParams.FILL_PARENT, 
                                        LinearLayout.LayoutParams.WRAP_CONTENT,
                                        1);
		LinearLayout ll=new LinearLayout(this);
		ll.setOrientation(LinearLayout.VERTICAL);
		
		final EditText et_name=new EditText(this);
		et_name.setHint("地区");
		ll.addView(et_name,lp_param);
		final EditText et_value=new EditText(this);
		et_value.setHint("代理ip");
		ll.addView(et_value,lp_param);
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this); 
		builder.setMessage("添加代理") 
		       .setCancelable(false) 
		       .setPositiveButton("确定", new DialogInterface.OnClickListener() { 
		           public void onClick(DialogInterface dialog, int id) { 
		        	
		        	ProxyDao proxyDao=ProxyDao.getInstance(getThisActivity());
		       		ProxyBean proxy=new ProxyBean();
		       		proxy.setProxyName(et_name.getText().toString());
		       		proxy.setProxyValue(et_value.getText().toString());
		       		proxyDao.insert(proxy);
		       		proxyAdapter.refreshUI();
		           } 
		       }) 
		       .setNegativeButton("取消", new DialogInterface.OnClickListener() { 
		           public void onClick(DialogInterface dialog, int id) { 
		                dialog.cancel(); 
		           } 
		       })
		       .setView(ll); 
		
		AlertDialog addDialog = builder.create(); 
		addDialog.show();
		
	}

}
