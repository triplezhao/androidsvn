package com.fgh.demos.sqlite;

import java.util.List;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.fgh.demos.R;

public class UserAdapter extends BaseAdapter{

	 	private Context mContext;
        private LayoutInflater mInflater;
        private List<UserBean> userlist;
        
        public UserAdapter(Context context) {
            mContext = context;
            mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            initDataFromDB();
        }

       

    	@Override
    	public int getCount() {
    		// TODO Auto-generated method stub
    		int count = 0;//添加新账户
    		if(null != userlist){
    			count = userlist.size();
    		}
    		return count;
    	}

    	@Override
    	public Object getItem(int position) {
    		// TODO Auto-generated method stub
    		if(null != userlist && position < userlist.size()){
    			return userlist.get(position);
    			
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
		 TextView text;
        final UserBean user=userlist.get(position);
         if (convertView == null) {
             text = (TextView)mInflater.inflate(android.R.layout.simple_list_item_1, parent, false);
         } else {
             text = (TextView)convertView;
         }
         text.setText(user.getUser());
         text.setTextColor(R.color.blue);
         text.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				CharSequence[] items=new CharSequence[]{"修改","删除"};
				AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
				builder.setTitle(user.getUser());
				builder.setItems(items, new android.content.DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						UserDao userdao=UserDao.getInstance(mContext);
						switch (which) {
						case 0:
							user.setUser(user.getUser()+":updated");
							userdao.update(user);
							refreshUI();
							break;
						case 1:
							userdao.deleteByUid(String.valueOf(user.getUserId()));
							refreshUI();
							break;
							
						default:
							break;
						}
					}
				});
				builder.create().show();
			}
		});
         return text;
	}
	
	public void initDataFromDB(){
		UserDao userdao=UserDao.getInstance(mContext);
		userlist=userdao.queryAll();
	}
	public void refreshUI(){
		initDataFromDB();
		notifyDataSetChanged();
	}

}
