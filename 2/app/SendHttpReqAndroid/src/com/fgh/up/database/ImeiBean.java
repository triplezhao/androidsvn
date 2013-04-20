package com.fgh.up.database;

import java.io.Serializable;
import java.text.DecimalFormat;

import com.fgh.up.util.ImeiDateUtil;

/**
 * 本地保存的用户信息
 * @author ztw
 *
 */
public class ImeiBean implements Serializable {

	/**
	 * 版本号
	 */
	private static final long serialVersionUID = 1L;
	
	
	private String id; 
	private String imei_value="";
	private String imei_chanel="";
	private long imei_date=0;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getImei_value() {
		return imei_value;
	}
	public void setImei_value(String imeiValue) {
		imei_value = imeiValue;
	}
	public String getImei_chanel() {
		return imei_chanel;
	}
	public void setImei_chanel(String imeiChanel) {
		imei_chanel = imeiChanel;
	}
	public long getImei_date() {
		return imei_date;
	}
	public void setImei_date(long imeiDate) {
		imei_date = imeiDate;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String tostring="ImeiBean:"+
		"imei_value:"+imei_value+
		"imei_chanel:"+imei_chanel+
		"imei_date:"+ImeiDateUtil.dateLong2String(imei_date);
		return tostring;
	}
	
	

}
