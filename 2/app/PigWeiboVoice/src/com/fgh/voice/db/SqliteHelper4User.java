package com.fgh.voice.db;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;

import com.fgh.voice.db.bean.UserBean;

public class SqliteHelper4User extends SQLiteOpenHelper {
	private static final String TAG = "SqliteHelper4User";
	public static final String TB_NAME = "sinauser_tb";

	public SqliteHelper4User(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	/// 创建表
	@Override
	public void onCreate(SQLiteDatabase arg0) {
		arg0.execSQL("CREATE TABLE IF NOT EXISTS "+
	                TB_NAME+"(" +
	                UserBean.ID + " integer primary key,"+
	                UserBean.USER + " varchar,"+
	                UserBean.USERID + " long,"+
	                UserBean.TOKEN + " varchar,"+
	                UserBean.TOKENSECRET + " varchar,"+
	                UserBean.ISREMEMBER + " short," +
	                UserBean.ISAUTOLOGIN + " short," +
	                UserBean.ISPUBLIC + " short" +
	                ")"
	                );
		Log.i(TAG, "onCreate");
	}

	/// 更新表
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TB_NAME);
        onCreate(db);
        Log.i(TAG, "onUpgrade");
	}

	/// 更新列
	public void updateColumn(SQLiteDatabase db, String oldColumn, String newColumn, String typeColumn){
        try{
            db.execSQL("ALTER TABLE " +
                    TB_NAME + " CHANGE " +
                    oldColumn + " "+ newColumn +
                    " " + typeColumn
            );
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
