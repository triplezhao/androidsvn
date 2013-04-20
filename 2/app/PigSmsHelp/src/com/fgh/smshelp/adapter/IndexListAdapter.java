package com.fgh.smshelp.adapter;



import java.util.ArrayList;
import java.util.List;

import pigframe.util.PigAndroidUtil;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.fgh.smshelp.R;
import com.fgh.smshelp.database.IndexBean;
import com.fgh.smshelp.util.SmsSender;
/**
 * 一种简单的列表形式的数据适配器
 * 要求给出一个数据集合ArrayList,还有求使用者Activity自己继承一个SimpleListViewDrawable接口以实现
 * getView(int position, View convertView, ViewGroup parent)这个方法
 * @author GaoLei by 2009-12-10
 */
public class IndexListAdapter extends BaseAdapter
{
	protected List<IndexBean> indexList;
		
	public IndexListAdapter()
	{
		indexList=new ArrayList<IndexBean>();
	}

	/**
	 * 清除数据列表中所有数据
	 */
	public void clear()
	{
		indexList.clear();
	}

	public void setArrayListDatas(List<IndexBean> data)
	{
		indexList=data;
		notifyDataSetInvalidated();
	}
	/**
	 * 获得全部元素
	 * @return
	 */
	public Object[] getAllChild()
	{
		if( indexList != null )
		{
			return indexList.toArray();
		}
		else
		{
			return null;
		}
	}
	
	/**
	 * 批量添加列表选项
	 * @param child
	 */
	public void addChild(Object[] child)
	{
		for(Object myChild : child)
		{
			indexList.add((IndexBean)myChild);
		}
	}
	/**
	 * 添加1个列表选项
	 */
	public void addChild(Object child)
	{
		if( child != null )
			indexList.add((IndexBean)child);
	}
	
	@Override
	public int getCount()
	{
		return indexList==null?0:indexList.size();
	}

	@Override
	public Object getItem(int position)
	{
		if(position>getCount()||position<0)
			return null;
		return indexList.get(position);
	}

	@Override
	public long getItemId(int position)
	{
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		 final IndexBean topic= indexList.get(position);
         final ViewHolder holder;

        if (convertView == null) {
        	convertView = PigAndroidUtil.getLayoutInflater().inflate(R.layout.item_send, null);
            holder = new ViewHolder();
            holder.imageView = (ImageView) convertView.findViewById(R.id.iv_pic);
            holder.textView = (TextView) convertView.findViewById(R.id.tv_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.imageView.setBackgroundResource(R.drawable.ic_launcher);
        holder.textView.setText(topic.getIndex_name());
        
        
        convertView.setOnClickListener(new OnClickListener() {
//			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//send 10086 sms
				SmsSender.send(v.getContext(), topic.getIndex_cmd());
			}
		});
        
		return convertView;
	}
	
	   static class ViewHolder {
           public ImageView imageView;
           public TextView textView;
       }
}
