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
import com.fgh.up.database.ChanelBean;
import com.fgh.up.database.ChanelDao;

public class ChanelAdapter extends BaseAdapter{

	 	private Activity mContext;
        private LayoutInflater mInflater;
        private List<ChanelBean> chanellist;
        
        public ChanelAdapter(Activity context) {
            mContext = context;
            mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            initDataFromDB();
        }

       

    	@Override
    	public int getCount() {
    		// TODO Auto-generated method stub
    		int count = 0;//添加新账户
    		if(null != chanellist){
    			count = chanellist.size();
    		}
    		return count;
    	}

    	@Override
    	public Object getItem(int position) {
    		// TODO Auto-generated method stub
    		if(null != chanellist && position < chanellist.size()){
    			return chanellist.get(position);
    			
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
         final ChanelBean chanelBean=chanellist.get(position);
         if (convertView == null) {
        	 convertView = mInflater.inflate(R.layout.item_chanel, null);
         }
         Button bt_choose=(Button) convertView.findViewById(R.id.bt_choose);
         Button bt_del=(Button) convertView.findViewById(R.id.bt_del);
         TextView tv_name=(TextView) convertView.findViewById(R.id.tv_name);
         tv_name.setText(chanelBean.getChanelName());
         
         bt_choose.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent it=new Intent();
				it.putExtra(UPActivitySend.str_bundle_return,
						chanelBean.getChanelName());
				mContext.setResult(mContext.RESULT_OK, it);
				mContext.finish();
			}
		});
         bt_del.setOnClickListener(new OnClickListener() {
        	 
        	 @Override
        	 public void onClick(View arg0) {
        		 // TODO Auto-generated method stub
        		 ChanelDao chaneldao=ChanelDao.getInstance(mContext);
        		 chaneldao.deleteByBean(chanelBean);
        		 chaneldao.close();
        		 refreshUI();
        	 }
         });
         
         
         return convertView;
	}
	
	public void initDataFromDB(){
		ChanelDao chaneldao=ChanelDao.getInstance(mContext);
		chanellist=chaneldao.queryAllchanelStory();
//		chaneldao.close();
	}
	public void refreshUI(){
		initDataFromDB();
		notifyDataSetChanged();
	}
}