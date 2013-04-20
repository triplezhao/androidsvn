package com.fgh.voice.db.bean;

import java.io.Serializable;

/**
 * 保存微博分类
 * @author ztw
 *
 */
public class CatalogBean implements Serializable {
	
	/**
	 * 版本号
	 */
	private static final long serialVersionUID = 1L;


	public static final String ID = "_id";
	//谁的分类
	public static final String SINA_UID = "sina_uid";
	//分类id
	public static final String CATALOG_ID = "catalog_id";
	//分类名字
	public static final String CATALOG_NAME = "catalog_name";
	

	private String id;
	private String sina_uid;
	private String catalog_id;
	private String catalog_name;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSina_uid() {
		return sina_uid;
	}
	public void setSina_uid(String sina_uid) {
		this.sina_uid = sina_uid;
	}
	public String getCatalog_id() {
		return catalog_id;
	}
	public void setCatalog_id(String catalog_id) {
		this.catalog_id = catalog_id;
	}
	public String getCatalog_name() {
		return catalog_name;
	}
	public void setCatalog_name(String catalog_name) {
		this.catalog_name = catalog_name;
	}

}
