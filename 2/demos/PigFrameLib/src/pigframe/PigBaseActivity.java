package pigframe;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.fgh.pigframe.R;



public abstract class PigBaseActivity extends Activity {
   
	private ProgressDialog mProgressDialog;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.main);
        
    }
    
    
	 /**
     * 接收传递过来的数据
     * 以及一些对象、资源的初始化
     */
    public abstract void initPre();
    
    /**
     * find各种view
     */
    public abstract void initFindViews();
    
    /**
     * 设置各种view的监听
     */
    public abstract void initListeners();
    
   
    /**
     * 传递过来的数据在这里做处理
     */
    public abstract void initPost();
	
    
    protected PigApplication getApp() {
		return PigApplication.getInstance();
	}
	protected Handler getUIHandler() {
		return getApp().getHandler();
	}
	/**
	 */
	public void doAddActivity()
	{
		if(PigApplication.getInstance()!=null){
			PigApplication.getInstance().setCurrentActivity(this);
		}
		
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		this.doAddActivity();
		
		super.onResume();
		
	}
	
	
	
	 public void showProgressDialog(String message)
		{
			if(this.isFinishing() || this.isRestricted())
			{
				return;
			}
			if(null == mProgressDialog || !mProgressDialog.isShowing())
			{
				if(this.isChild())
				{
					mProgressDialog = new ProgressDialog(this.getParent());
				}
				else
				{
					mProgressDialog = new ProgressDialog(this);
				}
				mProgressDialog.setMax(10000);
				mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
				
			}
			if(!TextUtils.isEmpty(message)){
				mProgressDialog.setMessage(message);
			}
			
//			if(this.isFinishing() || this.isRestricted())
			Activity act = this.isChild()?this.getParent():this;
			if(act.isFinishing() || act.isRestricted())
			{
				
			}
			else
			{
				mProgressDialog.show();
			}
			
		}
		
		protected void cancelProgressDialog()
		{
			if(null != mProgressDialog && mProgressDialog.isShowing())
			{
				mProgressDialog.cancel();
			}
		}
		
		
		
		public void setTitleName(String titlename){
	    	TextView titleTv = (TextView) findViewById(R.id.titleTv);
	    	titleTv.setText(titlename);
	    }
	    
	    protected void initTitleBar(int titleStrId
	    		, int leftStrId, int rightStrId
	    		, int leftBgId, int rightBgId
	    		, int leftImgId, int rightImgId
	    		, OnClickListener leftBtnListener
	    		, OnClickListener rightBtnListener) {
	    	
	    	TextView titleTv = (TextView) findViewById(R.id.titleTv);
	    	if(null != titleTv){
	    		if(0 < titleStrId){
	    			titleTv.setText(titleStrId);
	    			titleTv.setVisibility(View.VISIBLE);
	    		} else {
	    			titleTv.setVisibility(View.GONE);
	    		}
	    		titleTv.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent intent= new Intent();        
					    intent.setAction("android.intent.action.VIEW");    
					    Uri content_url = Uri.parse("http://www.91dota.com");   
					    intent.setData(content_url);  
					    v.getContext().startActivity(intent);
					}
				});
	    		
	    	}
	    	
	    	Button leftBtn = (Button) findViewById(R.id.leftBtn);
	    	if(null != leftBtn){
	    		if(0 < leftStrId){
	    			leftBtn.setText(leftStrId);
	    			if(0 < leftBgId){
	        			leftBtn.setBackgroundResource(leftBgId);
	            	}
	    			if(null != leftBtnListener){
	    				leftBtn.setOnClickListener(leftBtnListener);
	    			}
	    			leftBtn.setVisibility(View.VISIBLE);
	        	} else {
	        		leftBtn.setVisibility(View.GONE);
	        	}
	    		
	    		
	    	}
	    	
	    	Button rightBtn = (Button) findViewById(R.id.rightBtn);
	    	if(null != rightBtn){
	    		if(0 < rightStrId){
	    			rightBtn.setText(rightStrId);
	    			if(0 < rightBgId){
	        			rightBtn.setBackgroundResource(rightBgId);
	            	}
	    			if(null != rightBtnListener){
	    				rightBtn.setOnClickListener(rightBtnListener);
	    			}
	    			rightBtn.setVisibility(View.VISIBLE);
	        	} else {
	        		rightBtn.setVisibility(View.GONE);
	        	}
	    	}
	    	
	    	ImageView leftImgBtn = (ImageView) findViewById(R.id.leftImgBtn);
	    	if(null != leftImgBtn){
	    		if(0 < leftImgId){
	    			leftImgBtn.setImageResource(leftImgId);
	    			if(0 < leftBgId){
	    				leftImgBtn.setBackgroundResource(leftBgId);
	            	}
	    			if(null != leftBtnListener){
	    				leftImgBtn.setOnClickListener(leftBtnListener);
	    			}
	    			leftImgBtn.setVisibility(View.VISIBLE);
	        	} else {
	        		leftImgBtn.setVisibility(View.GONE);
	        	}
	    		
	    		
	    	}
	    	
	    	ImageView rightImgBtn = (ImageView) findViewById(R.id.rightImgBtn);
	    	if(null != rightImgBtn){
	    		if(0 < rightImgId){
	    			rightImgBtn.setImageResource(rightImgId);
	    			if(0 < rightBgId){
	    				rightImgBtn.setBackgroundResource(rightBgId);
	            	}
	    			if(null != rightBtnListener){
	    				rightImgBtn.setOnClickListener(rightBtnListener);
	    			}
	    			rightImgBtn.setVisibility(View.VISIBLE);
	        	} else {
	        		rightImgBtn.setVisibility(View.GONE);
	        	}
	    	}
	    	
		}
	    protected void initTitleBar(String titleStrId
	    		, String leftStrId, String rightStrId
	    		, int leftBgId, int rightBgId
	    		, int leftImgId, int rightImgId
	    		, OnClickListener leftBtnListener
	    		, OnClickListener rightBtnListener) {
	    	
	    	TextView titleTv = (TextView) findViewById(R.id.titleTv);
	    	if(null != titleTv){
	    		if(!TextUtils.isEmpty(titleStrId)){
	    			titleTv.setText(titleStrId);
	    			titleTv.setVisibility(View.VISIBLE);
	    		} else {
	    			titleTv.setVisibility(View.GONE);
	    		}
	    		titleTv.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent intent= new Intent();        
					    intent.setAction("android.intent.action.VIEW");    
					    Uri content_url = Uri.parse("http://www.91dota.com");   
					    intent.setData(content_url);  
					    v.getContext().startActivity(intent);
					}
				});
	    	}
	    	
	    	Button leftBtn = (Button) findViewById(R.id.leftBtn);
	    	if(null != leftBtn){
	    		if(!TextUtils.isEmpty( leftStrId)){
	    			leftBtn.setText(leftStrId);
	    			if(0 < leftBgId){
	    				leftBtn.setBackgroundResource(leftBgId);
	    			}
	    			if(null != leftBtnListener){
	    				leftBtn.setOnClickListener(leftBtnListener);
	    			}
	    			leftBtn.setVisibility(View.VISIBLE);
	    		} else {
	    			leftBtn.setVisibility(View.GONE);
	    		}
	    		
	    		
	    	}
	    	
	    	Button rightBtn = (Button) findViewById(R.id.rightBtn);
	    	if(null != rightBtn){
	    		if(!TextUtils.isEmpty( rightStrId)){
	    			rightBtn.setText(rightStrId);
	    			if(0 < rightBgId){
	    				rightBtn.setBackgroundResource(rightBgId);
	    			}
	    			if(null != rightBtnListener){
	    				rightBtn.setOnClickListener(rightBtnListener);
	    			}
	    			rightBtn.setVisibility(View.VISIBLE);
	    		} else {
	    			rightBtn.setVisibility(View.GONE);
	    		}
	    	}
	    	
	    	ImageView leftImgBtn = (ImageView) findViewById(R.id.leftImgBtn);
	    	if(null != leftImgBtn){
	    		if(0 < leftImgId){
	    			leftImgBtn.setImageResource(leftImgId);
	    			if(0 < leftBgId){
	    				leftImgBtn.setBackgroundResource(leftBgId);
	    			}
	    			if(null != leftBtnListener){
	    				leftImgBtn.setOnClickListener(leftBtnListener);
	    			}
	    			leftImgBtn.setVisibility(View.VISIBLE);
	    		} else {
	    			leftImgBtn.setVisibility(View.GONE);
	    		}
	    		
	    		
	    	}
	    	
	    	ImageView rightImgBtn = (ImageView) findViewById(R.id.rightImgBtn);
	    	if(null != rightImgBtn){
	    		if(0 < rightImgId){
	    			rightImgBtn.setImageResource(rightImgId);
	    			if(0 < rightBgId){
	    				rightImgBtn.setBackgroundResource(rightBgId);
	    			}
	    			if(null != rightBtnListener){
	    				rightImgBtn.setOnClickListener(rightBtnListener);
	    			}
	    			rightImgBtn.setVisibility(View.VISIBLE);
	    		} else {
	    			rightImgBtn.setVisibility(View.GONE);
	    		}
	    	}
	    	
	    }
	    
	    protected void setTitleBarBg(int bgresid) {
	    	View top_title_bar=findViewById(R.id.top_title_bar);
	    	top_title_bar.setBackgroundResource(bgresid);
		}
	    protected TextView getTitleBarTitle() {
	    	TextView titleTv = (TextView) findViewById(R.id.titleTv);
	    	return titleTv;
	    }
	    
	    public Activity getThisActivity(){
	    	return this;
	    }
	    
	    public void showToast(String text){
			PigApplication.getInstance().makeToast(text);
		};
		
		/**
		 * 收起软键盘并设置提示文字
		 */
		public void collapseSoftInputMethod(TextView tv){
		 InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		 imm.hideSoftInputFromWindow(tv.getWindowToken(), 0);
		}
}
