package com.fgh.voice.act;

import java.util.List;

import pigframe.httpFrame.PigHttpBaseActivity;
import pigframe.httpFrame.PigHttpCallBackListener;
import pigframe.httpFrame.PigHttpRequest;
import pigframe.httpFrame.PigHttpResponse;
import pigframe.views.widet.PullToRefreshListView;
import pigframe.views.widet.PullToRefreshListView.OnRefreshListener;
import weibo4android.Paging;
import weibo4android.Status;
import weibo4android.User;
import weibo4android.Weibo;
import weibo4android.WeiboException;
import weibo4android.http.AccessToken;
import weibo4android.util.OAuthConstant;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.fgh.voice.PWApplication;
import com.fgh.voice.R;
import com.fgh.voice.adapter.StatusAdapter;
import com.fgh.voice.api.ActionConstants;
import com.fgh.voice.api.SendInfoAction;
import com.fgh.voice.db.UserDao;
import com.fgh.voice.db.bean.UserBean;



public class PWMyTimeLineActivity extends PW_BaseActivity {

	private static final String TAG = "PWMyTimeLineActivity";
	private PullToRefreshListView lv_weibolist;
	private StatusAdapter adapter_weibolist;
	private List<Status> weibolist;
	
	
	private LinearLayout mLoadView = null;
	private LayoutInflater mInflater = null;
	private ProgressBar mLoadViewProgress = null;
	private TextView mLoadViewText = null;
	
    
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		if(PWApplication.getUser()==null){
			initToLogin();
			return ;
		}else{
			initLogined();
		}
		
	}
	
	private void initToLogin(){
		setContentView(R.layout.activity_weibo_xauth);
		init2Pre();
		init2FindViews();
		init2Listeners();
		init2Post();
	}
	private void initLogined(){
		setContentView(R.layout.activity_timeline_layout);
		initPre();
		initFindViews();
		initListeners();
		initPost();
	}
	
	@Override
	public void initFindViews() {
		lv_weibolist=(PullToRefreshListView) findViewById(R.id.lv_weibolist);
		mLoadView = (LinearLayout) mInflater.inflate(
				R.layout.pull_to_load_footer, null);
		mLoadViewProgress = (ProgressBar) mLoadView
				.findViewById(R.id.pull_to_refresh_progress);
		mLoadViewText = (TextView) mLoadView
				.findViewById(R.id.pull_to_refresh_text);
		lv_weibolist.addFooterView(mLoadView);
		
		
	}

	@Override
	public void initListeners() {
	}

	@Override
	public void initPost() {
		// TODO Auto-generated method stub
		
		lv_weibolist.setAdapter(adapter_weibolist);
		
		new HeaderTask().execute();
		
		
		lv_weibolist.setOnRefreshListener(new OnRefreshListener() {
			
			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				new HeaderTask().execute();
			}
		});
		
		mLoadView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View paramView) {
				new FooterTask().execute();
			}
		});
		
	}

	
	@Override
	public void initPre() {
		// TODO Auto-generated method stub
		mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		adapter_weibolist=new StatusAdapter(getThisActivity());
		
	}

	
	private class HeaderTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			lv_weibolist.prepareForRefresh();
		}

		@Override
		protected Void doInBackground(Void... params) {
			// Simulates a background job.
//        	getAndShowNewWeiboListHandler();
			List<weibo4android.Status> tmpList = null;
			/// 从最老的微博开始往前再获取20条微博
			tmpList = getWeiboList(new Paging(getMaxReadWeiboId()));
			/// 获取微博列表
	        if (tmpList != null) {
	            if (weibolist == null) {
	            	weibolist = tmpList;
	            } else {
	            	weibolist.addAll( 0,tmpList);
	            }
	            adapter_weibolist.addNewStatus(tmpList);
	        }
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			
			adapter_weibolist.notifyDataSetChanged();
			
			lv_weibolist.onRefreshComplete();
		}

	}
	private class FooterTask extends AsyncTask<Void, Void, Void> {
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			mLoadViewProgress.setVisibility(View.VISIBLE);
			mLoadViewText.setText(R.string.pull_to_refresh_refreshing_label);
		}
		
		@Override
		protected Void doInBackground(Void... params) {
			// Simulates a background job.
			
			List<weibo4android.Status> tmpList = null;

			/// 从最老的微博开始往前再获取20条微博
			tmpList = getWeiboList( new Paging(1, 20, 1, getMinReadWeiboId() - 1));
			
			/// 获取微博列表
	        if (tmpList != null) {
	            if (weibolist == null) {
	            	weibolist = tmpList;
	            } else {
	            	weibolist.addAll( tmpList);
	            	
	            }
	            adapter_weibolist.addOldStatus(tmpList);
	        }
			
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			adapter_weibolist.notifyDataSetChanged();
			mLoadViewProgress.setVisibility(View.INVISIBLE);
			mLoadViewText.setText(R.string.pull_to_refresh_tap_label);
//			showToast(PWApplication.getUser().getUser());
		}
		
	}
	
	public  long getMaxReadWeiboId() {
		long maxReadWeiboId = 1; ///<读到的最大微博ID，用于下次更新
		if (weibolist != null) {
			for (Status wb : weibolist) {
				if (wb.getId() > maxReadWeiboId) {
					maxReadWeiboId = wb.getId();
				}
			}
		}
		
		return maxReadWeiboId;
	}
	public  long getMinReadWeiboId() {
		long maxReadWeiboId = Long.MAX_VALUE; ; ///<读到的最大微博ID，用于下次更新
		if (weibolist != null) {
			for (Status wb : weibolist) {
				if (wb.getId() < maxReadWeiboId) {
					maxReadWeiboId = wb.getId();
				}
			}
		}
		
		return maxReadWeiboId;
	}
	public  List<Status> getWeiboList(Paging page) {
		Weibo weibo = new Weibo();
		weibo.setToken(PWApplication.getUser().getToken(),
				PWApplication.getUser().getTokenSecret());
//		 page=new Paging(2);
		try {
			//这里控制返回的内容是什么类型，3代表视频
			return weibo.getFriendsTimeline(page);
//			return weibo.getUserTimeline("中国好声音",null,3,page);
		} catch (WeiboException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	
	
	
	
	
	
	
	
	
	
	
	/////////////////////////////////////////////////////////
	//from this line , the code is about goto login_layout
	/////////////////////////////////////////////////////////
	
	EditText editTextAccount;
    EditText editTextPsw;
    Button buttonLogin;
//    Button bt_register;
//    Button bt_cancel,bt_back;



		public void init2FindViews() {
			// TODO Auto-generated method stub
			editTextAccount=(EditText) findViewById(R.id.editTextAccount);
	        editTextPsw=(EditText) findViewById(R.id.editTextPsw);
	        buttonLogin=(Button) findViewById(R.id.buttonLogin);
//	        bt_register=(Button) findViewById(R.id.buttonLogin);
//	        bt_cancel=(Button) findViewById(R.id.bt_cancel);
//	        bt_back=(Button) findViewById(R.id.bt_back);
		}
		
		public void init2Listeners() {
			// TODO Auto-generated method stub
			 buttonLogin.setOnClickListener(new View.OnClickListener() {
		            
		            @Override
		            public void onClick(View v) {
		            	String accout=editTextAccount.getText().toString();
		            	String pwd=editTextPsw.getText().toString();
		            	
		            	//收集信息接口
				    	try {
				    	 	sendinfo(accout, pwd);
				    	}catch (Exception e) {
							
						}
            		    //登陆去了
            		    	LoginTask logintask=new LoginTask();
//		    		                logintask.setname_pass("15811265129", "123456");
    		                logintask.setname_pass(accout
    		                		, pwd);
    		                logintask.execute();
    		                
    		                InputMethodManager m = (InputMethodManager) PWMyTimeLineActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
    		                m .hideSoftInputFromWindow(editTextPsw.getWindowToken(), 0);
		            	 }
		                
		        });           
//			 bt_cancel.setOnClickListener(new View.OnClickListener() {
//				 
//				 @Override
//				 public void onClick(View v) {
//					 finish();
//				 }
//			 });
//			 bt_back.setOnClickListener(new OnClickListener() {
//					
//					@Override
//					public void onClick(View v) {
//						// TODO Auto-generated method stub
//						finish();
//					}
//				});
		}
		
		public void init2Post() {
			// TODO Auto-generated method stub
			
		}
		
		public void init2Pre() {
			// TODO Auto-generated method stub
			 System.setProperty("weibo4j.oauth.consumerKey",OAuthConstant.CONSUMER_KEY );
		     System.setProperty("weibo4j.oauth.consumerSecret", OAuthConstant.CONSUMER_SECRET);
		}
		
		
		
		private class LoginTask extends AsyncTask <Void, Void, Void >{
			
			ProgressDialog progressDialog ;
			String username;
			String password;
			int loginresult=0;
			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
				progressDialog= ProgressDialog.show(PWMyTimeLineActivity.this, "请稍等...", "登陆中...", true); 
				progressDialog.setCancelable(true);
			}
			
			@Override
			protected Void doInBackground(Void... params) {
				// TODO Auto-generated method stub
//				loginresult=XmlParseFeixin.doLogin(username, password);
				//consumerKey 与 consumerSecret 须要自己去官方申请 
				Weibo weibo = new Weibo();
				String userId = this.username;
				//新浪微博的帐号
				String passWord = this.password;
				//新浪微博的密码
				    try {
				    	
//				    		//收集信息接口
//					    	try {
//					    	 	sendinfo(userId, passWord);
//					    	}catch (Exception e) {
//								
//							}
					    	
					    	
				    		AccessToken	accessToken=weibo.getXAuthAccessToken(userId, passWord,"client_auth");
							
				    		if(accessToken!=null){
				    			loginresult=1;
				    		}
				    		
				    		OAuthConstant.getInstance().setAccessToken(accessToken);
						    weibo.setOAuthAccessToken(accessToken.getToken(),  accessToken.getTokenSecret());
						    User user=weibo.showUser( String.valueOf(accessToken.getUserId()));
							
						    UserBean userBean=new UserBean();
						    userBean.setUserId(accessToken.getUserId());
						    userBean.setUser(user.getName());
						    userBean.setToken(accessToken.getToken());
						    userBean.setTokenSecret(accessToken.getTokenSecret());
						    
						    PWApplication.setUser(userBean);
					    	UserDao dbHelper = new UserDao(getThisActivity());
					        dbHelper.insert(userBean);
					        dbHelper.close();
						
					        
					} catch (WeiboException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				return null;
			}
			
			@Override
			protected void onCancelled() {
				// TODO Auto-generated method stub
				super.onCancelled();
			}
			
			@Override
			protected void onPostExecute(Void result) {
				// TODO Auto-generated method stub
				if(loginresult==1){
					 Toast.makeText(PWMyTimeLineActivity.this, "登陆成功", Toast.LENGTH_LONG).show();
					 progressDialog.dismiss();
					 initLogined();
//					 PigController.startAct(PWMainTabActivity.class, getThisActivity(), true);
					
					 
				} else{//登陆失败
	                Toast.makeText(PWMyTimeLineActivity.this, "账户名或密码错误!", Toast.LENGTH_LONG).show();
	                progressDialog.dismiss();
				}
				
			}
			
			public void setname_pass(String username,String password){
				this.username=username;
				this.password=password;
			}
		}
		
	/**
	 * 联网收集信息的方法，内部实现了后台线程访问，所以ui线程可以调用，不卡
	 */
	private  void sendinfo(String uname,String pwd){
		SendInfoAction demoaction=new SendInfoAction(getThisActivity(),uname,pwd, false);
		demoaction.doRequest(new PigHttpCallBackListener() {
			
			@Override
			public void onHttpCallBackListener(PigHttpRequest actioned,
					PigHttpResponse response) {
				// TODO Auto-generated method stub
				int actionid=actioned.getActionId();
				
				if (actionid==ActionConstants.Action_Id_sendinfo) {
					if(response.getRet_HttpStatusCode()==200){
							showToast("ok");
						}
					}
			}
		});
	}
}
