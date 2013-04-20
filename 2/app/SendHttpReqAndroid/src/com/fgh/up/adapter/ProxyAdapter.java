package com.fgh.up.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.fgh.up.R;
import com.fgh.up.activity.UPActivitySend;
import com.fgh.up.database.ProxyBean;
import com.fgh.up.database.ProxyDao;

public class ProxyAdapter extends BaseAdapter{

	 	private Activity mContext;
        private LayoutInflater mInflater;
        private List<ProxyBean> proxylist;
        
        public ProxyAdapter(Activity context) {
            mContext = context;
            mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            initDataFromDB();
        }

    	@Override
    	public int getCount() {
    		// TODO Auto-generated method stub
    		int count = 0;//添加新账户
    		if(null != proxylist){
    			count = proxylist.size();
    		}
    		return count;
    	}

    	@Override
    	public Object getItem(int position) {
    		// TODO Auto-generated method stub
    		if(null != proxylist && position < proxylist.size()){
    			return proxylist.get(position);
    			
    		}
    		return null;
    	}

    	@Override
    	public long getItemId(int position) {
    		// TODO Auto-generated method stub
    		return position;
    	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
         final ProxyBean proxyBean=proxylist.get(position);
         if (convertView == null) {
        	 convertView = mInflater.inflate(R.layout.item_proxy, null);
         }
         Button bt_choose=(Button) convertView.findViewById(R.id.bt_choose);
         Button bt_del=(Button) convertView.findViewById(R.id.bt_del);
         TextView tv_name=(TextView) convertView.findViewById(R.id.tv_name);
         TextView tv_value=(TextView) convertView.findViewById(R.id.tv_value);
         tv_name.setText(proxyBean.getProxyName());
         tv_value.setText(proxyBean.getProxyValue());
         
         bt_choose.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent it=new Intent();
				it.putExtra(UPActivitySend.str_bundle_return,
						proxyBean.getProxyValue());
				mContext.setResult(mContext.RESULT_OK, it);
				mContext.finish();
			}
		});
         bt_del.setOnClickListener(new OnClickListener() {
        	 
        	 @Override
        	 public void onClick(View arg0) {
        		 // TODO Auto-generated method stub
        		 ProxyDao chaneldao=ProxyDao.getInstance(mContext);
        		 chaneldao.deleteByBean(proxyBean);
        		 chaneldao.close();
        		 refreshUI();
        	 }
         });
         
         
         return convertView;
	}
	
	public void initDataFromDB(){
		ProxyDao chaneldao=ProxyDao.getInstance(mContext);
		proxylist=chaneldao.queryAllchanelStory();
//		chaneldao.close();
	}
	public void refreshUI(){
		initDataFromDB();
		notifyDataSetChanged();
	}
}