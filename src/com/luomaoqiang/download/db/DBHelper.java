package com.luomaoqiang.download.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper{

	private static final int VERSION = 1;
	private static final String DB_NAME = "dlInfo.db";
	
	protected static final String TABLE_NAME = "downloadInfo";
	
	protected static final String FILE_NAME = "fileName";
	
	protected static final String FILE_LENGTH = "fileLength";
	
	protected static final String FILE_SOURCE = "fileSource";
	
	protected static final String FILE_TOTAL_LENGTH = "fileTotalLength";

	private static final String DROP_TABLE = "drop table if exists "+TABLE_NAME;

	
	
	public DBHelper(Context context) {
		super(context, DB_NAME, null, VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		String createTable = "create table if not exists "
		+TABLE_NAME+
		" ("+FILE_NAME+" varchar(128) primary key not null, "
		+FILE_LENGTH+" Long, "
		+FILE_TOTAL_LENGTH+" Long, "
		+FILE_SOURCE+" text"+")";
		db.execSQL(createTable);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL(DROP_TABLE);
		onCreate(db);
	}

}
