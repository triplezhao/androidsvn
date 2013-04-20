package com.fgh.voice.db.bean;

import java.io.Serializable;

/**
 * 保存分类下面的sina用户信息
 * @author ztw
 *
 */
public class CatalogUserBean implements Serializable {
	
	/**
	 * 版本号
	 */
	private static final long serialVersionUID = 1L;

	public static final String ID = "_id";
	//分类id
	public static final String CATALOG_ID = "catalog_id";
	//分类下的users
	public static final String CHILD_SINA_UID = "child_sina_uid";
	//分类下的users
	public static final String CHILD_SINA_UNAME = "child_sina_uname";
	

	private String id;
	private String catalog_id;
	private String child_sina_uid;
	private String child_sina_uname;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCatalog_id() {
		return catalog_id;
	}
	public void setCatalog_id(String catalog_id) {
		this.catalog_id = catalog_id;
	}
	public String getChild_sina_uid() {
		return child_sina_uid;
	}
	public void setChild_sina_uid(String child_sina_uid) {
		this.child_sina_uid = child_sina_uid;
	}
	public String getChild_sina_uname() {
		return child_sina_uname;
	}
	public void setChild_sina_uname(String child_sina_uname) {
		this.child_sina_uname = child_sina_uname;
	}
	

}
