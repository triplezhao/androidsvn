package com.fgh.reader.joke.database;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class PigBookOpenHelper extends SQLiteOpenHelper {
	private static final String TAG = "DBOpenHelper";
	
	public static final String DB_NAME = "db_pig_book";
	private static final int DATABASE_VERSION = 4;

	public PigBookOpenHelper(Context context) {
		super(context, DB_NAME, null, DATABASE_VERSION);
	}
	public PigBookOpenHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	/// 创建表
	@Override
	public void onCreate(SQLiteDatabase arg0) {
		arg0.execSQL("CREATE TABLE IF NOT EXISTS "+
				    BookStoryDao.TB_NAME+"(" +
	                BookStoryDao.ID + " integer primary key,"+
	                BookStoryDao.BOOKID + " varchar,"+
	                BookStoryDao.BOOKPATH + " varchar,"+
	                BookStoryDao.BOOKNAME + " varchar,"+
	                BookStoryDao.BOOKOFFSET + " integer,"+
	                BookStoryDao.TIME + " long," +
	                BookStoryDao.BOOKSIZE + " long"+
	                ")"
	                );
		Log.i(TAG, "onCreate");
	}

	/// 更新表
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + BookStoryDao.TB_NAME);
        onCreate(db);
        Log.i(TAG, "onUpgrade");
	}


}
