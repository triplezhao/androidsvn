package com.fgh.demos.pageturner;

import pigframe.util.PigAndroidUtil;
import pigframe.util.PigDateUtil;
import pigframe.util.PigLog;
import android.content.Context;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fgh.demos.R;

/**
 * hmg25's android Type
 *
 *负责给内容分页的类。按照pageWidget想要的格式给它。
 *1.这个类负责内容的分页。从文件或者其他地方获取内容，分为三页给pageWidget。
 *2.负责一页的layout布局。类似于listview的adapter。
 *@author ztw
 *
 */
public class PageAdapter implements PageAdapterImp{
	
	private LayoutInflater mInflater;
	private Context mContext;
	
	private PageFileFactory m_bookFactory = null;
	
	private PageContent pre_content;
	private PageContent curr_content;
	private PageContent next_content;
	private LoadContentFinishListener loadContentFinishListener;
	
	public PageAdapter(Context context,PageFileFactory bookFactory,int offset) {
		this.mContext = context;
		this.mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		m_bookFactory=bookFactory;
		this.curr_content=m_bookFactory.getCurrPageContent(offset);
		this.next_content=m_bookFactory.getNextPageContent(curr_content);
		this.pre_content=m_bookFactory.getPrePageContent(curr_content);
	}
	
	public void setBookFactory(PageFileFactory bookFactory){
		m_bookFactory=bookFactory;
		this.curr_content=m_bookFactory.getCurrPageContent(0);
		this.next_content=m_bookFactory.getNextPageContent(curr_content);
		this.pre_content=m_bookFactory.getPrePageContent(curr_content);
	}
	public void jump2Offset(int offset){
		if(offset<=0){
			return ;
		}
		this.curr_content=m_bookFactory.getCurrPageContent(offset);
		this.next_content=m_bookFactory.getNextPageContent(curr_content);
		this.pre_content=m_bookFactory.getPrePageContent(curr_content);
	}
	public void seekBarToOffset(int offset){
		if(offset<=0){
			return ;
		}
		this.curr_content=getNoErrorCurrContent(offset);
		this.next_content=m_bookFactory.getNextPageContent(curr_content);
		this.pre_content=m_bookFactory.getPrePageContent(curr_content);
	}
	private PageContent getNoErrorCurrContent(int offset){
		PageContent temp_curr=m_bookFactory.getCurrPageContent(offset);
		PageContent temp_next=m_bookFactory.getNextPageContent(temp_curr);
		return m_bookFactory.getPrePageContent(temp_next);
	}
	private void exchangePre(PageContent newpreContent) {
		this.next_content = curr_content;
		this.curr_content=this.pre_content;
		this.pre_content=newpreContent;
		
		
	}
	private void exchangeNext(PageContent newnextContent) {
		this.pre_content=curr_content;
		this.curr_content=this.next_content;
		this.next_content = newnextContent;
		
	}
	
	
  private View getPageView(View convertView,PageContent pc) {
		
		if(convertView==null){
			PigLog.i("getCurrView", "inflate demo_page_item");
			convertView=mInflater.inflate(R.layout.demo_page_item, null, false);
			convertView.setDrawingCacheEnabled(true);
		}
		final TextView page=(TextView) convertView.findViewById(R.id.tv_page);
		page.setTextSize(TypedValue.COMPLEX_UNIT_PX,m_bookFactory.m_fontSize);
		page.setText(pc.mPageContent);
		convertView.destroyDrawingCache();
		convertView.buildDrawingCache();
		
		Button btn_tag=(Button) convertView.findViewById(R.id.btn_tag);
		TextView tv_offset=(TextView)convertView.findViewById(R.id.tv_offset);
		TextView tv_bookname=(TextView) convertView.findViewById(R.id.tv_bookname);
		TextView tv_time=(TextView) convertView.findViewById(R.id.tv_time);
		
		btn_tag.getBackground().setAlpha(180);
		tv_offset.setText(PageUtil.getOffsetOf(pc.mOffsetHead,m_bookFactory.getFileSize()));
		tv_bookname.setText(m_bookFactory.getFile().getName());
		tv_time.setText(PigDateUtil.getFormatDate(System.currentTimeMillis(), mContext));
		return convertView;
	}
	
	@Override
	public void loadNextPage() {
		// TODO Auto-generated method stub
		if(curr_content.isLastPage){
			PigAndroidUtil.showToast(mContext, "以是最后一页：", 3000);
		}else{
			exchangeNext(m_bookFactory.getNextPageContent(next_content));
		}
	}


	@Override
	public void loadPrePage() {
		// TODO Auto-generated method stub
		
		if(curr_content.isFirstPage){
			PigAndroidUtil.showToast(mContext, "以是第一页：", 3000);
		}else{
			exchangePre(m_bookFactory.getPrePageContent(pre_content));
		}
	}
	
	
	@Override
	public View getCurrView(View convertView) {
		// TODO Auto-generated method stub
		if(curr_content!=null){
			convertView=getPageView(convertView,curr_content);
		}
		return convertView;
	}

	@Override
	public View getNextView(View convertView) {
		// TODO Auto-generated method stub
		
		if(next_content!=null){
			convertView=getPageView(convertView,next_content);
		}
		return convertView;
	}


	@Override
	public View getPreView(View convertView) {
		// TODO Auto-generated method stub
		if(pre_content!=null){
			convertView=getPageView(convertView,pre_content);
		}
		return convertView;
	}


	
	@Override
	public void onPageTurnFinished(int flipType) {
		// TODO Auto-generated method stub
		switch (flipType) {
		case PageWidget.PRE:
			loadPrePage();
			Log.v("Callback3", "PRE");
			break;
		case PageWidget.KEEP:
			
			
			break;
		case PageWidget.NEXT:
			loadNextPage();
			Log.v("Callback3", "NEXT");
			break;

		default:
			break;
		}
		loadContentFinishListener.onContentFinishListener(getOffset());
		
	}
	@Override
	 public boolean isFirstPage(){
		 return curr_content.isFirstPage;
	 }
	@Override
	 public boolean isLastPage(){
		 return curr_content.isLastPage;
	 }
	
	public int getOffset(){
		return curr_content.mOffsetHead;
	}
	public void setOnLoadContentFinishListener(LoadContentFinishListener loadContentFinishListener){
		this.loadContentFinishListener=loadContentFinishListener;
	}
	
    public  interface LoadContentFinishListener{
    	 public void onContentFinishListener(int offset);
     }   
}