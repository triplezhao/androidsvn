package com.fgh.voice.adapter;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.fgh.voice.R;
import com.fgh.voice.xmlparse.StaticUserBean;



public final class StaticUserAdapter extends BaseAdapter {

	private Context mContext;
	private List<StaticUserBean> mlist;
	AssetManager asm;
	public StaticUserAdapter(Context context) {
		super();
		this.mContext = context;
		 asm=mContext.getAssets();
	}
	
	public void setList(List<StaticUserBean> mlist){
		this.mlist=mlist;
	}
	

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		int count = 0;//添加新账户
		if(null != mlist){
			count = mlist.size();
		}
		return count;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		if(null != mlist && position < mlist.size()){
			return mlist.get(position);
			
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
	         已将图片保存到assest目录下，知道图片的名称，通过inputstream获得图片Drawabl
		
		或者 Bitmap
		
		AssetManager asm=getAssetMg();
		
		InputStream is=asm.open(name);//name:图片的名称
		
		（1）获得Drawable
		Drawable da = Drawable.createFromStream(is, null);
		
		（2）获得Bitmap
		Bitmap bitmap=BitmapFactory.decodeStream(is);
	 *
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View view = null;
		if(null == convertView){
			view = LayoutInflater.from(mContext).inflate(R.layout.item_static_user, null);
		} else {
			view = convertView;
		}
		ImageView iv_pic = (ImageView) view.findViewById(R.id.iv_pic);
		TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
			
		final StaticUserBean static_user = mlist.get(position);
		
		tv_name.setText(static_user.name);
		
		InputStream is;
		try {
			is = asm.open(static_user.pic);
			Drawable da = Drawable.createFromStream(is, null);
			iv_pic.setImageDrawable(da);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return view;
	}
	

}
