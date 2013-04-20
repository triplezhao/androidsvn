package com.fgh.reader.joke.adapter;

import java.util.List;

import pigframe.util.PigDateUtil;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.fgh.reader.joke.R;
import com.fgh.reader.joke.activity.DemoActivityPageTurner;
import com.fgh.reader.joke.database.BookStoryBean;
import com.fgh.reader.joke.database.BookStoryDao;
import com.fgh.reader.joke.pageturner.PageUtil;
import com.fgh.reader.joke.util.FileNameDecodeUtil;


public class BookStoryAdapter extends BaseAdapter{

	 	private Context mContext;
        private LayoutInflater mInflater;
        private List<BookStoryBean> bookStoryList;
        
        public BookStoryAdapter(Context context) {
            mContext = context;
            mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

    	@Override
    	public int getCount() {
    		// TODO Auto-generated method stub
    		int count = 0;//添加新账户
    		if(null != bookStoryList){
    			count = bookStoryList.size();
    		}
    		return count;
    	}

    	@Override
    	public Object getItem(int position) {
    		// TODO Auto-generated method stub
    		if(null != bookStoryList && position < bookStoryList.size()){
    			return bookStoryList.get(position);
    			
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
		final BookStoryBean bookStory=bookStoryList.get(position);
		ViewHolder viewHolder=null;
         if (convertView == null) {
        	 convertView = mInflater.inflate(R.layout.demo_item_bookstory, parent, false);
        	 viewHolder = new ViewHolder();
        	 viewHolder.bookname=(TextView) convertView.findViewById(R.id.tv_bookname);
        	 viewHolder.time=(TextView) convertView.findViewById(R.id.tv_time);
        	 viewHolder.offset=(TextView) convertView.findViewById(R.id.tv_offset);
        	 convertView.setTag(viewHolder);
         } else {
        	 viewHolder = (ViewHolder) convertView.getTag();
         }
         viewHolder.bookname.setText(
        		FileNameDecodeUtil. getDecodeName(bookStory.getBookName()));
         viewHolder.time.setText(PigDateUtil.getFormatDate(bookStory.getTime(), mContext));
         viewHolder.offset.setText(PageUtil.getOffsetOf(bookStory.getBookOffset(), (int)bookStory.getBookSize()));
         convertView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				  openTxtReader(bookStory);
			}
		});
       
         
//         text.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				CharSequence[] items=new CharSequence[]{"修改","删除"};
//				AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
//				builder.setTitle(user.getUser());
//				builder.setItems(items, new android.content.DialogInterface.OnClickListener() {
//					
//					@Override
//					public void onClick(DialogInterface dialog, int which) {
//						// TODO Auto-generated method stub
//						UserDao userdao=UserDao.getInstance(mContext);
//						switch (which) {
//						case 0:
//							user.setUser(user.getUser()+":updated");
//							userdao.update(user);
//							refreshUI();
//							break;
//						case 1:
//							userdao.deleteByUid(String.valueOf(user.getUserId()));
//							refreshUI();
//							break;
//							
//						default:
//							break;
//						}
//					}
//				});
//				builder.create().show();
//			}
//		});
         return convertView;
	}
	
	class ViewHolder{
		TextView bookname;
		TextView time;
		TextView offset;
		
	}
	
	public void initDataFromDB(){
		BookStoryDao dao=BookStoryDao.getInstance(mContext);
		bookStoryList=dao.queryAllBookStory();
	}
	public void refreshUI(){
		initDataFromDB();
		notifyDataSetChanged();
	}
	 private void openTxtReader(BookStoryBean bsb) {
  		
  		DemoActivityPageTurner.gotoDemoActivityPageTurner(mContext, false, bsb);
  	}

}
