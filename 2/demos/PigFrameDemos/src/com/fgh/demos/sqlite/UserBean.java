package com.fgh.demos.sqlite;

import java.io.Serializable;

/**
 * 本地保存的用户信息
 * @author ztw
 *
 */
public class UserBean implements Serializable {

	/**
	 * 版本号
	 */
	private static final long serialVersionUID = 1L;
	
	
	private String id;
	private String user;
	private long userId;
	private String token;
	private String tokenSecret;
	private int isLogin;

	public int getIsLogin() {
		return isLogin;
	}

	public void setIsLogin(int isLogin) {
		this.isLogin = isLogin;
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

	public void setUser(String username){
		this.user = username;
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


}
