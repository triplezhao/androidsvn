package com.fgh.voice.db.bean;

import java.io.Serializable;

/**
 * 保存绑定的用户信息
 * @author Administrator
 *
 */
public class UserBean implements Serializable {

	public static final String ID = "_id";
	public static final String USER = "user";
	public static final String USERID = "userId";
	public static final String TOKEN = "token";
	public static final String TOKENSECRET = "tokenSecret";
	public static final String ISREMEMBER = "isRemember";
	public static final String ISAUTOLOGIN = "isAutoLogin";
	public static final String ISPUBLIC = "isPublic";
	
	/**
	 * 版本号
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String user;
	private long userId;
	private String token;
	private String tokenSecret;
	private boolean isRemember;
	private boolean isAutoLogin;
	private boolean isPublic;

	public boolean isPublic() {
		return isPublic;
	}

	public void setPublic(boolean isPublic) {
		this.isPublic = isPublic;
	}

	public String getId(){
		return id;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getUser(){
		return user;
	}

	public void setUser(String name){
		this.user = name;
	}

	public long getUserId(){
		return userId;
	}

	public void setUserId(long userId){
		this.userId = userId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getTokenSecret() {
		return tokenSecret;
	}

	public void setTokenSecret(String tokenSecret) {
		this.tokenSecret = tokenSecret;
	}

	public boolean getIsRemember() {
		return isRemember;
	}

	public void setIsRemember(boolean isRemember) {
		this.isRemember = isRemember;
	}

	public boolean getIsAutoLogin() {
		return isAutoLogin;
	}

	public void setIsAutoLogin(boolean isAutoLogin) {
		this.isAutoLogin = isAutoLogin;
	}
}
