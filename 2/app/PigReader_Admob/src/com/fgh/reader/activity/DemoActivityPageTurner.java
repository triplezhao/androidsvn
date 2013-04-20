package com.fgh.reader.activity;



import pigframe.PigBaseActivity;
import pigframe.util.PigAndroidUtil;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PowerManager;
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

import com.fgh.reader.R;
import com.fgh.reader.database.BookStoryBean;
import com.fgh.reader.database.BookStoryDao;
import com.fgh.reader.pageturner.PageAdapter;
import com.fgh.reader.pageturner.PageAdapter.LoadContentFinishListener;
import com.fgh.reader.pageturner.PageFileFactory;
import com.fgh.reader.pageturner.PageUtil;
import com.fgh.reader.pageturner.PageWidget;


public class DemoActivityPageTurner extends PigBaseActivity {
	/** Called when the activity is first created. */
	private PageWidget mPageWidget;
	private PageAdapter mPageAdapter;
	private BookStoryBean bookstroy;
	private PageFileFactory bookFactory;
	public static final String  BOOKSTORYEXTRA="BOOKSTORYEXTRA";
	
	private ZoomControls zoomControls;
	private SeekBar seekbar;
	private PowerManager.WakeLock wakeLock;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.demo_activity_page_layout);
		
		PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
		
		wakeLock = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "goodev");
		wakeLock.acquire();

		
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
			
	}

	@Override
	public void initPost() {
		// TODO Auto-generated method stub
		
		mPageWidget.setPageAdapter(mPageAdapter);
		zoomControls.hide();
		seekbar.setVisibility(View.INVISIBLE);
	}

	@Override
	public void initPre() {
		// TODO Auto-generated method stub
		getWindow (). setFlags ( WindowManager . LayoutParams . FLAG_KEEP_SCREEN_ON , WindowManager . LayoutParams . FLAG_KEEP_SCREEN_ON );
		
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
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
				MenuItem item;
				item = menu.add(0, 11, 0, "字体大小");
				item.setIcon(R.drawable.btn_a);
				item = menu.add(0, 12, 0, "章节跳转");
				item.setIcon(R.drawable.icon_goto);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case 11:
			// 添加要处理的内容
			Toast.makeText(this, "字体大小", Toast.LENGTH_SHORT).show();
			if(zoomControls.isShown()){
				zoomControls.hide();
			}else{
				zoomControls.show();
			}
			break;
		case 12:
			// 添加要处理的内容
			Toast.makeText(this, "章节跳转", Toast.LENGTH_SHORT).show();
			if(seekbar.isShown()){
				seekbar.setVisibility(View.INVISIBLE);
			}else{
				seekbar.setVisibility(View.VISIBLE);
			}
			break;
		}
		return false;
		
	}
	
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
	        		if(zoomControls.isShown()){
						zoomControls.hide();
						needBack= false; 
					}
					if(seekbar.isShown()){
						seekbar.setVisibility(View.INVISIBLE);
						needBack= false; 
					}
					if(!needBack){
						return true;
					}
	        	}
	        } 
		return super.dispatchKeyEvent(event);
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		//在释放wakeLock之前 屏幕会保持常亮
		wakeLock.release();
	}
	
	
}