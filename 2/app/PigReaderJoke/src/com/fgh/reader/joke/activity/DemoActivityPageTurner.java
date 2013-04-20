package com.fgh.reader.joke.activity;



import pigframe.PigBaseActivity;
import pigframe.util.PigAndroidUtil;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Toast;
import android.widget.ZoomControls;

import com.fgh.reader.joke.R;
import com.fgh.reader.joke.database.BookStoryBean;
import com.fgh.reader.joke.database.BookStoryDao;
import com.fgh.reader.joke.pageturner.PageAdapter;
import com.fgh.reader.joke.pageturner.PageAdapter.LoadContentFinishListener;
import com.fgh.reader.joke.pageturner.PageFileFactory;
import com.fgh.reader.joke.pageturner.PageUtil;
import com.fgh.reader.joke.pageturner.PageWidget;


public class DemoActivityPageTurner extends PigBaseActivity 
									 {
	/** Called when the activity is first created. */
	private PageWidget mPageWidget;
	private PageAdapter mPageAdapter;
	private BookStoryBean bookstroy;
	private PageFileFactory bookFactory;
	public static final String  BOOKSTORYEXTRA="BOOKSTORYEXTRA";
	
	private ZoomControls zoomControls;
	private SeekBar seekbar;
	private Handler mHandler;
	private View rl_menu;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.demo_activity_page_layout);
		
		initPre();
		initFindViews();
		initListeners();
		initPost();
	}

	@Override
	public void initFindViews() {
		// TODO Auto-generated method stub
		mPageWidget=(PageWidget) findViewById(R.id.pw_page);
		zoomControls = (ZoomControls) findViewById(R.id.zc_tool);
		seekbar = (SeekBar) findViewById(R.id.pageSeekBar);
		rl_menu=findViewById(R.id.rl_memu);
	}

	@Override
	public void initListeners() {
		// TODO Auto-generated method stub
		
		mPageAdapter.setOnLoadContentFinishListener(new LoadContentFinishListener() {
			
			@Override
			public void onContentFinishListener(int offset) {
				// TODO Auto-generated method stub
				seekbar.setProgress(offset);
			}
		});
		
		zoomControls.setOnZoomInClickListener(new OnClickListener() {
			@Override
				public void onClick(View v) {
				bookFactory.setTextSize(bookFactory.m_fontSize+4);
				mPageAdapter.jump2Offset(mPageAdapter.getOffset());
				mPageWidget.inflate3PageView();
				}
			});
		zoomControls.setOnZoomOutClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				bookFactory.setTextSize(bookFactory.m_fontSize-4);
				mPageAdapter.jump2Offset(mPageAdapter.getOffset());
				mPageWidget.inflate3PageView();
			}
		});
		
		
		 
		seekbar.setMax(bookFactory.getFileSize());
		seekbar.setProgress(bookstroy.getBookOffset());
		seekbar.setOnSeekBarChangeListener( new OnSeekBarChangeListener(){

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
				if(fromUser){
					PigAndroidUtil.showToast(getThisActivity(), 
							PageUtil.getOffsetOf(progress, bookFactory.getFileSize()), 3000);
					}
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				PigAndroidUtil.showToast(getThisActivity(), 
						PageUtil.getOffsetOf(seekBar.getProgress(), bookFactory.getFileSize()), 3000);
			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				seekBarToOffset(seekBar.getProgress());
			}}
		);
		rl_menu.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				hideMenu();
			}
		});
//		findViewById(com.fgh.pigframe.R.id.rl_page).setOnTouchListener(this);
	}

	@Override
	public void initPost() {
		// TODO Auto-generated method stub
		
		mPageWidget.setPageAdapter(mPageAdapter);
//		zoomControls.hide();
//		seekbar.setVisibility(View.INVISIBLE);
	}

	@Override
	public void initPre() {
		// TODO Auto-generated method stub
		getWindow (). setFlags ( WindowManager . LayoutParams . FLAG_KEEP_SCREEN_ON , WindowManager . LayoutParams . FLAG_KEEP_SCREEN_ON );
//		mGesture = new GestureDetector(this, new GestureListener());  
		initHandler();
		Intent intent = this.getIntent();
		if(null != intent){
			bookstroy=(BookStoryBean) intent.getSerializableExtra(BOOKSTORYEXTRA);
		}
		if(bookstroy==null){
			bookstroy=new BookStoryBean();
			bookstroy.setBookId("/sdcard/test.txt");
			bookstroy.setBookName("test");
			bookstroy.setBookPath("/sdcard/test.txt");
			bookstroy.setBookOffset(0);
		}
		int pageW=PigAndroidUtil.getW()-2*PigAndroidUtil.convertDipOrPx(this, 10);
		int pageH=PigAndroidUtil.getH()
				  -2*PigAndroidUtil.convertDipOrPx(this, 10)
				  -PigAndroidUtil.convertDipOrPx(this, 30);
		 bookFactory=new PageFileFactory(
				 pageW, pageH,
					24,bookstroy.getBookPath());
		 mPageAdapter=new PageAdapter(this,bookFactory,bookstroy.getBookOffset());
		 mPageAdapter.setHandler(mHandler);
	}

	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		updateBookStory();
	}
	
	private void updateBookStory(){
		BookStoryDao dao=BookStoryDao.getInstance(this);
		BookStoryBean bean=dao.queryOneBookByBookPath(bookstroy.getBookPath());
		bookstroy.setBookOffset(mPageAdapter.getOffset());
		bookstroy.setBookSize((long)bookFactory.getFileSize());
		if(bean!=null){
			dao.update(bookstroy);
		}else{
			dao.insert(bookstroy);
		}
	}
	public static void gotoDemoActivityPageTurner(Activity from, boolean finish,
			BookStoryBean bsb) {
		
		Bundle data = new Bundle();
		if(bsb!=null){
			data.putSerializable(BOOKSTORYEXTRA, bsb);
		}
		PigAndroidUtil.start(from, DemoActivityPageTurner.class, finish, "", data);
	}
	public static void gotoDemoActivityPageTurner(Context from, boolean finish,
			BookStoryBean bsb) {
		
		Bundle data = new Bundle();
		if(bsb!=null){
			data.putSerializable(BOOKSTORYEXTRA, bsb);
		}
		PigAndroidUtil.start(from, DemoActivityPageTurner.class, finish, "", data);
	}
	
	
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//				MenuItem item;
//				item = menu.add(0, 11, 0, "字体大小");
//				item.setIcon(R.drawable.btn_a);
//				item = menu.add(0, 12, 0, "章节跳转");
//				item.setIcon(R.drawable.icon_goto);
//		        showMenu();
//		return super.onCreateOptionsMenu(menu);
//	}
//	
//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//		switch (item.getItemId()) {
//		case 11:
//			// 添加要处理的内容
//			Toast.makeText(this, "字体大小", Toast.LENGTH_SHORT).show();
//			if(zoomControls.isShown()){
//				zoomControls.hide();
//			}else{
//				zoomControls.show();
//			}
//			break;
//		case 12:
//			// 添加要处理的内容
//			Toast.makeText(this, "章节跳转", Toast.LENGTH_SHORT).show();
//			if(seekbar.isShown()){
//				seekbar.setVisibility(View.INVISIBLE);
//			}else{
//				seekbar.setVisibility(View.VISIBLE);
//			}
//			break;
//		}
//		return false;
//		
//	}
	
	private void seekBarToOffset(int offset){
		mPageAdapter.seekBarToOffset(offset);
		mPageWidget.inflate3PageView();
	}
	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		// TODO Auto-generated method stub
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) { 
	        if (event.getAction() == KeyEvent.ACTION_DOWN 
	                && event.getRepeatCount() == 0) { 
	        		
	        		boolean needBack=true;
	        		if(rl_menu.isShown()){
		        		hideMenu();
						needBack= false; 
					}
					if(!needBack){
						return true;
					}
	        	}
	        }else if (event.getKeyCode() == KeyEvent.KEYCODE_MENU) {
	        	 if (event.getAction() == KeyEvent.ACTION_DOWN 
	 	                && event.getRepeatCount() == 0) { 
			        	if(rl_menu.isShown()){
			        		hideMenu();
			        	}else{
			        		showMenu();
			        	}
	        	 }
	        } 
		return super.dispatchKeyEvent(event);
	}
	
	private void initHandler(){
		mHandler= new Handler() {
		   public void handleMessage(Message msg) {
		        	showMenu();
		      }
		};
	}
	
	private void showMenu(){
		rl_menu.setVisibility(View.VISIBLE);
	}
	private void hideMenu(){
		rl_menu.setVisibility(View.GONE);
	}
}