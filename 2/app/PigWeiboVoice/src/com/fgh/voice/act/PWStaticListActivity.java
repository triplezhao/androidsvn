package com.fgh.voice.act;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.fgh.voice.R;
import com.fgh.voice.adapter.StaticUserAdapter;
import com.fgh.voice.xmlparse.ParseXmlService;
import com.fgh.voice.xmlparse.StaticUserBean;

import pigframe.PigBaseActivity;
import pigframe.views.TagCloudyView;

public class PWStaticListActivity extends PW_BaseActivity {

//	private ListView lv_userlist;
//	private StaticUserAdapter adapter_userlist;
	private List<StaticUserBean> userlist;
	AssetManager asm = null;
	TagCloudyView layoutmain;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_staticlist_layout);
		  setContentView(R.layout.activity_staticlist_layout); 
		initPre();
		initFindViews();
		initListeners();
		initPost();
	}
	
	
	@Override
	public void initFindViews() {
		// TODO Auto-generated method stub
//		lv_userlist=(ListView) findViewById(R.id.lv_userlist);
	}

	@Override
	public void initListeners() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initPost() {
		// TODO Auto-generated method stub
//		adapter_userlist=new StaticUserAdapter(this);
//		adapter_userlist.setList(userlist);
//		lv_userlist.setAdapter(adapter_userlist);
		
		userlist=new ParseXmlService().getStaticUsers(this);
		add2CloudyView();
		layoutmain.initCloud();

	}

	@Override
	public void initPre() {
		// TODO Auto-generated method stub
		
	     layoutmain=(TagCloudyView) findViewById(R.id.tcv_tagcloudy);
	     
	     String title=getResources().getString(R.string.PIG_FRAME_STATIC_LIST_TITLE);
	     initTitleBar(title, null,null,
					0,0,
					0, 0,
					null,null);
	     setTitleBarBg(R.color.transparent);
	     asm=getAssets();
	}
	
	
 private void add2CloudyView(){
		 
		 LayoutInflater inflater = LayoutInflater.from(this); 
		 
		 for (StaticUserBean info : userlist) {
			 View  convertView = inflater.inflate(R.layout.pig_apps_item, null);  
	         ImageView icon= (ImageView) convertView  
	                 .findViewById(R.id.apps_image);  
	         TextView  label = (TextView) convertView  
	                 .findViewById(R.id.apps_textview);  
	         if(info.pic==null){
	 				icon.setImageResource(R.drawable.icon);
	 			}else{
	 				 InputStream is;
	 		 		try {
 		 				is = asm.open(info.pic);
 			 			Drawable da = Drawable.createFromStream(is, null);
 			 			icon.setImageDrawable(da);
	 		 			
	 		 		} catch (IOException e) {
	 		 			// TODO Auto-generated catch block
	 		 			e.printStackTrace();
	 		 		}
	 			}
	        
	         
	         final String labelText=info.name;
	         final String nikename=info.nickname;
	         label.setText(labelText);  
	         
	         convertView.setOnClickListener(new OnClickListener() {
	 			
	 			@Override
	 			public void onClick(View v) {
	 				// TODO Auto-generated method stub
	 			showToast(labelText);
	 			PWTimeLineActivity.open(getThisActivity(),nikename,false);
	 			}
	         });
	         layoutmain.addCloudy(convertView);
		}
	    
	 }
	
}
