package com.fgh.reader.database;

import java.io.Serializable;
import java.text.DecimalFormat;

/**
 * 本地保存的用户信息
 * @author ztw
 *
 */
public class BookStoryBean implements Serializable {

	/**
	 * 版本号
	 */
	private static final long serialVersionUID = 1L;
	
	
	private String id; 
	private String bookId="";
	private String bookPath="";
	
	private String bookName="";
	private int bookOffset=0;
	private long time;
	private long bookSize;
	
	
	public long getBookSize() {
		return bookSize;
	}
	public void setBookSize(long bookSize) {
		this.bookSize = bookSize;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getBookId() {
		return bookId;
	}
	public void setBookId(String bookId) {
		this.bookId = bookId;
	}
	public String getBookName() {
		return bookName;
	}
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
	public String getBookPath() {
		return bookPath;
	}
	public void setBookPath(String bookPath) {
		this.bookPath = bookPath;
	}
	public int getBookOffset() {
		return bookOffset;
	}
	public void setBookOffset(int bookOffset) {
		this.bookOffset = bookOffset;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}


}
