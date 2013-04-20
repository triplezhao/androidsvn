package com.fgh.demos.pageturner;

import android.view.View;


/**
 * hmg25's android Type
 *
 *负责给内容分页的类。按照pageWidget想要的格式给它。
 *@author ztw
 *
 */
public interface PageAdapterImp {
	
	public View getPreView(View convertView );
	public View getCurrView(View convertView );
	public View getNextView(View convertView );
	
	public void loadPrePage();
	public void loadNextPage();
	
	public void onPageTurnFinished(int flipType) ;
	
	public boolean isFirstPage();
	public boolean isLastPage();
	
}