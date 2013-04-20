package com.fgh.demos.tagcloudy;

import java.util.ArrayList;
import java.util.List;

import pigframe.PigBaseActivity;
import pigframe.views.TagCloudyView;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fgh.demos.R;

public class Demo_TagCloudy_Activity extends PigBaseActivity {
	
	
	TagCloudyView layoutmain;
	private ArrayList<LauncherItem> lvalue;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
     // 通过代码创建一个linearlayout并将它设为activity的内容 
//        layoutmain = new TagCloudyView(this); 
//        layoutmain.setLayoutParams(new FrameLayout.LayoutParams( 
//                RelativeLayout.LayoutParams.FILL_PARENT, 
//                RelativeLayout.LayoutParams.FILL_PARENT));
//        layoutmain.setBackgroundResource(R.drawable.topbar_bg);
        initPre();
		initFindViews();
		initListeners();
		initPost();
        
    }
    
    private void TestAddChild(final String text){
    	   Button  tv=new Button(this);
           tv.setText(text);
           tv.setBackgroundResource(R.drawable.icon);
           tv.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			showToast(text);
			}
		});
           layoutmain.addCloudy(tv);
    }

	@Override
	public void initFindViews() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initListeners() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initPost() {
		// TODO Auto-generated method stub
			getAllApps();
	        add2CloudyView();
	        layoutmain.initCloud();
	}

	@Override
	public void initPre() {
		// TODO Auto-generated method stub
		
	     setContentView(R.layout.demo_activity_tagcloudy_layout); 
	     layoutmain=(TagCloudyView) findViewById(R.id.tcv_tagcloudy);
	     initTitleBar("应用标签云", "返回","按钮",
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
						}
					});
	}
    
	
	 private  void getAllApps() {  
			lvalue = new ArrayList<LauncherItem>();
	        PackageManager pManager = getPackageManager();  
	        // 获取手机内所有应用  
	        List<PackageInfo> packlist = pManager.getInstalledPackages(0); 
	        
	        for (int i = 0; i < packlist.size(); i++) {  
	            PackageInfo pak = (PackageInfo) packlist.get(i);  
	  
	            // 判断是否为非系统预装的应用程序  
	            // 这里还可以添加系统自带的，这里就先不添加了，如果有需要可以自己添加  
	            // if()里的值如果<=0则为自己装的程序，否则为系统工程自带  
	            if ((pak.applicationInfo.flags & pak.applicationInfo.FLAG_SYSTEM) <= 0) {  
	                // 添加自己已经安装的应用程序  
	                Drawable icon =pManager.getApplicationIcon(pak.applicationInfo); 
		        	String label = pManager.getApplicationLabel(pak.applicationInfo).toString();
		        	Intent it = pManager.getLaunchIntentForPackage(pak.applicationInfo.packageName);
		        	LauncherItem item = new LauncherItem(icon,label,it);
		        	lvalue.add(item);
	            }  
	  
	        }  
	    }  
	 private void add2CloudyView(){
		 
		 LayoutInflater inflater = LayoutInflater.from(this); 
		 
		 for (LauncherItem info : lvalue) {
			 View  convertView = inflater.inflate(R.layout.pig_apps_item, null);  
	         ImageView icon= (ImageView) convertView  
	                 .findViewById(R.id.apps_image);  
	         TextView  label = (TextView) convertView  
	                 .findViewById(R.id.apps_textview);  
	         icon.setImageDrawable(info.icon);  
	         final String labelText=info.name;
	         label.setText(labelText);  
	         final Intent it =info.it;
	         
	         convertView.setOnClickListener(new OnClickListener() {
	 			
	 			@Override
	 			public void onClick(View v) {
	 				// TODO Auto-generated method stub
	 			showToast(labelText);
//	 			Intent intent =new Intent(Intent.ACTION_VIEW); 
//	 		    intent.setComponent(c);
	 		    startActivity(it);
	 			}
	         });
	         layoutmain.addCloudy(convertView);
		}
	    
	 }
	 private class LauncherItem {
			Drawable icon;
			String name;
			Intent it;
			
			LauncherItem(Drawable d, String s,Intent it){
				this.icon = d;
				this.name = s;
				this.it = it;
			}
		};

}