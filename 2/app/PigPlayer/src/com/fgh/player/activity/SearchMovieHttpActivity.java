package com.fgh.player.activity;

import java.util.List;

import pigframe.httpFrame.PigHttpBaseActivity;
import pigframe.httpFrame.PigHttpCallBackListener;
import pigframe.httpFrame.PigHttpRequest;
import pigframe.httpFrame.PigHttpResponse;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.fgh.pigframe.R;
import com.fgh.player.adapter.MovieResultAdapter;
import com.fgh.player.apiaction.PlayerActionConstants;
import com.fgh.player.apiaction.SearchMovieAction;
import com.fgh.player.apiaction.parse.ParseMovie;
import com.fgh.player.bean.BeanMovie;


/**
 * 
 * 演示利用pigframe获取网络数据的过程。
 * 1、定义一个action继承自HTTPAction。设置url、http参数、post/get、usecache等
 * 2、调用action的doaction方法。
 * 3、实现一个ActionListener接口。来接受、处理返回来的结果。
 * 
 * @author ztw
 *
 */
public  class SearchMovieHttpActivity extends PigHttpBaseActivity{
	
	
	private ListView lv_movie;
	private MovieResultAdapter adapter;
	
	private Button   bt_search;
	private EditText et_search;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		initPre();
		initFindViews();
		initListeners();
		initPost();
		
		
	}


	@Override
	public void initFindViews() {
		// TODO Auto-generated method stub
		lv_movie=(ListView) findViewById(com.fgh.player.R.id.lv_movie);
		bt_search=(Button) findViewById(R.id.bt_search);
		et_search=(EditText) findViewById(R.id.et_search);
	}

	@Override
	public void initListeners() {
		// TODO Auto-generated method stub
		initTitleBar("搜索视频", "","",
				R.drawable.topbar_back_selector,R.drawable.topbar_btnbg,
				0, 0,
				null,null);
		
		bt_search.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(!et_search.getText().toString().equals("")){
					dosearch(et_search.getText().toString());
					collapseSoftInputMethod(bt_search);
				}else{
					showToast("请输入关键词");
				}
				
			}
		});
	}

	@Override
	public void initPost() {
		// TODO Auto-generated method stub
		adapter=new MovieResultAdapter(this);
		
	}

	@Override
	public void initPre() {
		// TODO Auto-generated method stub
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
		setContentView(R.layout.activity_search_layout);
	}

	

	@Override
	public void onHttpCallBackListener(PigHttpRequest actioned,PigHttpResponse response) {
		// TODO Auto-generated method stub
		}

	public void dosearch(String keyword){
		showToast("获取数据，优先使用本地缓存");
		showProgressDialog("获取数据。。。");
		SearchMovieAction demoaction=new SearchMovieAction(
				getThisActivity(),keyword, false);
		demoaction.doRequest(new PigHttpCallBackListener() {
			
			@Override
			public void onHttpCallBackListener(PigHttpRequest actioned,
					PigHttpResponse response) {
				// TODO Auto-generated method stub
				int actionid=actioned.getActionId();
				
				if (actionid==PlayerActionConstants.Action_Id_serchmovie) {
					if(response.getRet_HttpStatusCode()==200){
//						if(parseDataErrorCode==OK){
						String json=response.getRet_String();
						List<BeanMovie> movie_list=ParseMovie.parse_SearchBeanMovie(json);
						adapter.setMovieList(movie_list);
						lv_movie.setAdapter(adapter);
						}
					}
					cancelProgressDialog();
			}
		});
		
		}
}
	
