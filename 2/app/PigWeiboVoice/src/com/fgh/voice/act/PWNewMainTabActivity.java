package com.fgh.voice.act;

import pigframe.PigBaseActivity;
import pigframe.PigMainTabAct;
import pigframe.views.widet.PigComposeWidget;
import android.app.ActivityGroup;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TabHost;
import android.widget.Toast;

import com.fgh.voice.R;

public class PWNewMainTabActivity extends ActivityGroup  {
	
	private PigComposeWidget pcv_compose;
	private  TabHost tabhost;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		
		   setContentView(R.layout.new_main_tab);
		   
		   
		  tabhost = (TabHost) findViewById(R.id.tabhost);
//		   tabhost.setup(); // tab的内容是杂物的话 tabhost就是一个盒子 先要一个盒子
		   tabhost.setup(this.getLocalActivityManager());
 
		   
		   //首页微博列表
			   TabHost.TabSpec tab_1 = tabhost.newTabSpec("tab_1");// 里面的string 就是一个tag
			   tab_1.setIndicator("tab_1");
			   Intent it=   new Intent(this, PWTimeLineActivity.class);
			   it.putExtra(PWTimeLineActivity.EXTRA_STR_NICK_NAME, getResources().getString(R.string.STR_WEIBO_SUBJECT_NAME));
			   tab_1.setContent(it);
			   tabhost.addTab(tab_1);// 然后将物品 1 放进去
		    //静态学员列表 
			   TabHost.TabSpec tab_2 = tabhost.newTabSpec("tab_2");
			   tab_2.setIndicator("tab_2");
			   Intent it2=   new Intent(this, PWStaticListActivity.class);
//			   it2.putExtra(PWTimeLineActivity.EXTRA_STR_NICK_NAME, "谁是张伟");
			   tab_2.setContent(it2);;// 物品 2 的布局设置
			   tabhost.addTab(tab_2);// 然后放物品 2
			   
			 //新浪登陆，和新浪微博列表    
			   TabHost.TabSpec tab_3 = tabhost.newTabSpec("tab_3");
			   tab_3.setIndicator("tab_3");
			   Intent it3=   new Intent(this, PWMyTimeLineActivity.class);
			   tab_3.setContent(it3);;// 物品 2 的布局设置
			   tabhost.addTab(tab_3);// 然后放物品 2
			   
			 //热门关键词应用推荐列表
//			   TabHost.TabSpec tab_4 = tabhost.newTabSpec("tab_4");
//			   tab_4.setIndicator("tab_4");
//			   Intent it4=   new Intent(this, PWRecommendAPPAct.class);
//			   tab_4.setContent(it4);;// 物品 2 的布局设置
//			   tabhost.addTab(tab_4);// 然后放物品 2
			   TabHost.TabSpec tab_4 = tabhost.newTabSpec("tab_4");
			   tab_4.setIndicator("tab_4");
			   Intent it4=   new Intent(this, PWHotTrendsAct.class);
			   tab_4.setContent(it4);;// 物品 2 的布局设置
			   tabhost.addTab(tab_4);// 然后放物品 2
			   //关于
			   TabHost.TabSpec tab_5 = tabhost.newTabSpec("tab_5");
			   tab_5.setIndicator("tab_5");
			   Intent it5=   new Intent(this, Pw_About_Activity.class);
			   tab_5.setContent(it5);;// 物品 2 的布局设置
			   tabhost.addTab(tab_5);// 然后放物品 2
		  
			   
			   
		   /* 将这个盒子排在第一位,有可能你有几个盒子 里面分类装了不同的东西,按什么顺序来排列盒子就在这里设置 */
		   tabhost.setCurrentTab(0);
		   
		   pcv_compose=(PigComposeWidget) findViewById(R.id.pcv_compose);
		
		   pcv_compose.setAction(0, R.drawable.tab_home, new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					 tabhost.setCurrentTab(0);
//					Toast.makeText(PWNewMainTabActivity.this, "0", 1000).show();
				}
			});
		   pcv_compose.setAction(1, R.drawable.tab_static_users, new OnClickListener() {
			   
			   @Override
			   public void onClick(View v) {
				   // TODO Auto-generated method stub
				   tabhost.setCurrentTab(1);
				   Toast.makeText(PWNewMainTabActivity.this, getResources().getString(R.string.PIG_FRAME_STATIC_LIST_TITLE), 2000).show();
			   }
		   });
		   pcv_compose.setAction(2, R.drawable.tab_sina, new OnClickListener() {
			   
			   @Override
			   public void onClick(View v) {
				   // TODO Auto-generated method stub
				   tabhost.setCurrentTab(2);
//				   Toast.makeText(PWNewMainTabActivity.this, "2", 1000).show();
			   }
		   });
		   pcv_compose.setAction(3, R.drawable.tab_hot, new OnClickListener() {
			   
			   @Override
			   public void onClick(View v) {
				   // TODO Auto-generated method stub
				   tabhost.setCurrentTab(3);
				   Toast.makeText(PWNewMainTabActivity.this, 
						   getResources().getString(R.string.PIG_FRAME_APP_CLOUDY), 2000).show();
			   }
		   });
		   pcv_compose.setAction(4, R.drawable.tab_about, new OnClickListener() {
			   
			   @Override
			   public void onClick(View v) {
				   // TODO Auto-generated method stub
				   tabhost.setCurrentTab(4);
				   Toast.makeText(PWNewMainTabActivity.this, 
						   getResources().getString(R.string.PIG_FRAME_APP_ABOUT), 2000).show();
			   }
		   });
	}
	
	
	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		// TODO Auto-generated method stub
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) { 
	        if (event.getAction() == KeyEvent.ACTION_DOWN 
	                && event.getRepeatCount() == 0) { 
	        	if(tabhost.getCurrentTab()!=0){
	        		tabhost.setCurrentTab(0);
	        	}else{
	        		 AlertDialog.Builder builder = new AlertDialog.Builder(
								PWNewMainTabActivity.this);
						builder.setTitle("确认退出?");
						builder.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int which) {
										PWNewMainTabActivity.this.finish();
									}
								});
						builder.setNegativeButton("取消",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int which) {
										// finish();
									}
								});
						builder.show();
	        	}
	        	
	            return true; 
	        } 
	    } 
		return super.dispatchKeyEvent(event);
	}

	  
   
}