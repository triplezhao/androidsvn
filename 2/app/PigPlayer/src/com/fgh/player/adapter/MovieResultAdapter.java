package com.fgh.player.adapter;

import java.util.List;

import pigframe.PigApplication;
import pigframe.httpFrame.network.AsyncImageLoader;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.fgh.pigframe.R;
import com.fgh.player.bean.BeanMovie;
import com.fgh.player.control.LogicControl;

public class MovieResultAdapter extends BaseAdapter{

	 	private Context mContext;
        private LayoutInflater mInflater;
        private List<BeanMovie> movielist;
        
        public MovieResultAdapter(Context context) {
            mContext = context;
            mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        
        public void setMovieList(List<BeanMovie> movielist){
        	this.movielist=movielist;
        }

       

    	@Override
    	public int getCount() {
    		// TODO Auto-generated method stub
    		int count = 0;//添加新账户
    		if(null != movielist){
    			count = movielist.size();
    		}
    		return count;
    	}

    	@Override
    	public Object getItem(int position) {
    		// TODO Auto-generated method stub
    		if(null != movielist && position < movielist.size()){
    			return movielist.get(position);
    			
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
		ViewHolder holder = null;
		final BeanMovie movie=movielist.get(position);
        if (convertView == null) {
        	holder = new ViewHolder();
        	convertView = mInflater.inflate(com.fgh.player.R.layout.item_movie, parent, false);
        	holder.iv_movie_img=(ImageView) convertView.findViewById(R.id.iv_movie_img);
        	holder.tv_movie_title=(TextView) convertView.findViewById(R.id.tv_movie_title);
        	convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        
         holder.tv_movie_title.setText(movie.getTitle());
         new AsyncImageLoader().loadDrawable(movie.getImg(), holder.iv_movie_img);
        
         convertView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				LogicControl.gotoPlayMovie(PigApplication.getInstance().getCurrentActivity(),
						 movie.getVideoId());
			}
		});
         return convertView;
	}
	
	static class ViewHolder {
        public ImageView iv_movie_img;
        public TextView tv_movie_title;
    }

}
