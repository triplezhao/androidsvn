package com.fgh.up.pc;


public abstract class PigBaseActivity {
	
	//
	public abstract void onCreate();
	 /**
     * 接收传递过来的数据
     * 以及一些对象、资源的初始化
     */
    public abstract void initPre();
    
    /**
     * find各种view
     */
    public abstract void initFindViews();
    
    /**
     * 设置各种view的监听
     */
    public abstract void initListeners();
    
   
    /**
     * 传递过来的数据在这里做处理
     */
    public abstract void initPost();
}
