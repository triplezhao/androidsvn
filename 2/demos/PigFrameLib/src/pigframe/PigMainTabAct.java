package pigframe;



import android.app.AlertDialog;
import android.app.TabActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

import com.fgh.pigframe.R;


public class PigMainTabAct extends TabActivity
{
	private TabHost mTabHost;
	private TabWidget mTabWidget;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pig_main_tab);

		// TODO Auto-generated method stub
//		mTabHost = getTabHost();
//		mTabWidget = mTabHost.getTabWidget();
//		AddTab(R.drawable.tab_square, R.string.square, CnSquareAct.class);
//		AddTab(R.drawable.tab_info, R.string.info, CnInfoAct.class);
//		AddTab(R.drawable.tab_home, R.string.home, CnHomeAct.class);
//		AddTab(R.drawable.tab_my_info, R.string.myinfo, CnUserInformationAct.class);
//		AddTab(R.drawable.tab_more, R.string.more, CnMoreAct.class);
//		AddTab(R.drawable.tab_more, R.string.more, CnTest_ApiDemoActivity.class);
//		mTabHost.setOnTabChangedListener(this);
//		mTabHost.setCurrentTabByTag(getString(R.string.home));
//		android:background="@drawable/pig_bottombar_bg" 
		setTabWidgetBg(R.drawable.toolbar_bg_middle_nor);
	}
	
	
	/**
	 * @param icon
	 * @param title
	 * @param cls
	 * 添加tab子activity。设置tabicon和tabtext
	 */
	protected void AddTab(int icon, String title, Class<?> cls) {
		 TabHost mTabHost = getTabHost();
		View localView = LayoutInflater.from(mTabHost.getContext()).inflate(
				R.layout.pig_bottom_tab_item, null);
		((ImageView) localView.findViewById(R.id.main_tab_image))
				.setBackgroundResource(icon);
		((TextView) localView.findViewById(R.id.main_tab_text)).setText(title);
		TabHost.TabSpec localTabSpec = mTabHost.newTabSpec(title)
				.setIndicator(localView).setContent(new Intent(this, cls));
		mTabHost.addTab(localTabSpec);

	}
	protected void AddTab(int icon, String title,int textcolor ,Class<?> cls) {
		TabHost mTabHost = getTabHost();
		View localView = LayoutInflater.from(mTabHost.getContext()).inflate(
				R.layout.pig_bottom_tab_item, null);
		((ImageView) localView.findViewById(R.id.main_tab_image))
		.setBackgroundResource(icon);
		((TextView) localView.findViewById(R.id.main_tab_text)).setText(title);
		TabHost.TabSpec localTabSpec = mTabHost.newTabSpec(title)
				.setIndicator(localView).setContent(new Intent(this, cls));
		mTabHost.addTab(localTabSpec);
		
	}
	/**
	 * 设置TabWidget的背景
	 * @param resid
	 */
	protected void setTabWidgetBg(int resid) {
		getTabHost().getTabWidget().setBackgroundResource(resid);
		
	}
	protected void setTabHostBg(int resid) {
		getTabHost().setBackgroundResource(resid);
		
	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		// TODO Auto-generated method stub
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) { 
	        if (event.getAction() == KeyEvent.ACTION_DOWN 
	                && event.getRepeatCount() == 0) { 
	        	if(getTabHost().getCurrentTab()!=0){
	        		getTabHost().setCurrentTab(0);
	        	}else{
	        		 AlertDialog.Builder builder = new AlertDialog.Builder(
								PigMainTabAct.this);
						builder.setTitle("确认退出?");
						builder.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int which) {
										PigMainTabAct.this.finish();
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
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}


	@Override
	protected void onNewIntent(Intent intent) {
		// TODO Auto-generated method stub
		
		super.onNewIntent(intent);
		
	}
	
}
