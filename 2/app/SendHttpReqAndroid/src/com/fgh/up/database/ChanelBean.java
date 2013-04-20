package com.fgh.up.database;

import java.io.Serializable;
import java.text.DecimalFormat;

/**
 * 本地保存的用户信息
 * @author ztw
 *
 */
public class ChanelBean implements Serializable {

	/**
	 * 版本号
	 */
	private static final long serialVersionUID = 1L;
	
	
	private String id; 
	private String chanelName="";
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getChanelName() {
		return chanelName;
	}
	public void setChanelName(String chanelName) {
		this.chanelName = chanelName;
	}
	
	


}
