package com.fgh.smshelp.database;

import java.io.Serializable;
import java.text.DecimalFormat;


/**
 * 本地保存的用户信息
 * @author ztw
 *
 */
public class IndexBean implements Serializable {

	/**
	 * 版本号
	 */
	private static final long serialVersionUID = 1L;
	
	
	private String id; 
	private String index_cmd="";
	private String index_name="";
	private String image="";
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIndex_cmd() {
		return index_cmd;
	}
	public void setIndex_cmd(String index_cmd) {
		this.index_cmd = index_cmd;
	}
	public String getIndex_name() {
		return index_name;
	}
	public void setIndex_name(String index_name) {
		this.index_name = index_name;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	
	

}
