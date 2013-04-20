package com.fgh.voice.db.bean;

import java.io.Serializable;
import java.util.Date;

import android.graphics.Bitmap;

@SuppressWarnings("serial")
public class WeiboBean implements Serializable {
	public static final String ID = "_id";
	public static final String USER = "user";
	public static final String PROFILE = "profile";
	public static final String WEIBOID = "weiboId";
	public static final String TEXT = "text";
	public static final String EXISTICON = "existIcon";
	public static final String EXISTLOCATION = "existLocation";
	public static final String ICON = "icon";
	public static final String LATITUDE = "latitude";
	public static final String LONGITUDE = "longitude";
	public static final String CREATEAT = "createAt";
	public static final String ORIGINALPIC = "Original_pic";
	
	/**
	 * 版本号
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private long weiboId;
	private Bitmap profileBitmap;
	private String profileUrl;
	private Bitmap iconBitmap;
	private String iconUrl;
	private String Original_pic;
	private String userString;
	private Date timeDate;
	private String timeDifference;
	private String textString;
	private WeiboBean retweet;
	private boolean existImage;
	private boolean existLocation;
	private double latitude;
	private double longitude;
	private int commentCount;
	private int repostCount;
	private String comeString;

	public String getOriginal_pic() {
		return Original_pic;
	}

	public void setOriginal_pic(String originalPic) {
		Original_pic = originalPic;
	}

	public void setId(String id){
		this.id = id;
	}

	public void setWeiboId(long id){
		weiboId = id;
	}

	public void setProfile(Bitmap profile){
		profileBitmap = profile;
	}

	public void setProfileUrl(String url){
		profileUrl = url;
	}
	
	public void setIcon(Bitmap icon){
		iconBitmap = icon;
	}
	
	public void setIconUrl(String url){
		iconUrl = url;
	}
	
	public void setUser(String user){
		userString = user;
	}
	
	public void setTime(Date time){
		timeDate = time;
	}
	
	public void setTimeDifference(String difference){
		timeDifference = difference;
	}
	
	public void setText(String text){
		textString = text;
	}
	
	public void setRetweet(WeiboBean retweet){
		this.retweet = retweet;
	}
	
	public void setExistImage(boolean flag){
		existImage = flag;
	}
	
	public void setExistLocation(boolean flag){
		existLocation = flag;
	}
	
	public void setLatitude(double latitude){
		this.latitude = latitude;
	}
	
	public void setLongitude(double longitude){
		this.longitude = longitude;
	}
	
	public void setCommentCount(int count){
		commentCount = count;
	}
	
	public void setRepostCount(int count){
		repostCount = count;
	}
	
	public void setComeString(String comeString){
		this.comeString = comeString;
	}
	
	public String getId(){
		return id;
	}

	public long getWeiboId(){
		return weiboId;
	}

	public Bitmap getProfile(){
		return profileBitmap;
	}

	public String getProfileUrl(){
		return profileUrl;
	}
	
	public Bitmap getIcon(){
		return iconBitmap;
	}
	
	public String getIconUrl(){
		return iconUrl;
	}
	
	public String getUser(){
		return userString;
	}
	
	public Date getTime(){
		return timeDate;
	}
	
	public String getTimeDifference(){
		return timeDifference;
	}
	
	public String getText(){
		return textString;
	}
	
	public WeiboBean getRetweet(){
		return retweet;
	}
	
	public boolean getExistImage(){
		return existImage;
	}
	
	public boolean getExistLocation(){
		return existLocation;
	}
	
	public double getLatitude(){
		return latitude;
	}
	
	public double getLongitude(){
		return longitude;
	}

	public int getCommentCount(){
		return commentCount;
	}

	public int getRepostCount(){
		return repostCount;
	}

	public String getComeString(){
		return comeString;
	}
}
