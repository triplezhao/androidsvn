package com.fgh.up.database;

import java.io.Serializable;
import java.text.DecimalFormat;

/**
 * 本地保存的用户信息
 * @author ztw
 *
 */
public class ProxyBean implements Serializable {

	/**
	 * 版本号
	 */
	private static final long serialVersionUID = 1L;
	
	
	private String id; 
	private String proxyName="";
	private String proxyValue="";
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getProxyName() {
		return proxyName;
	}
	public void setProxyName(String proxyName) {
		this.proxyName = proxyName;
	}
	public String getProxyValue() {
		return proxyValue;
	}
	public void setProxyValue(String proxyValue) {
		this.proxyValue = proxyValue;
	}

}
