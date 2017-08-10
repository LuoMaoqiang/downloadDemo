package com.luomaoqiang.download.db;

import com.luomaoqiang.download.bean.DownloadObject;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Looper;

public class DBManager {

	private static DBManager mDBManager;
	private Context mContext;
	private SQLiteDatabase dbWriter;

	private static String deleteData = "delete from " + DBHelper.TABLE_NAME
			+ " where " + DBHelper.FILE_NAME;
	private static String selectData = "select * from " + DBHelper.TABLE_NAME;
	private static String updateData = "update " + DBHelper.TABLE_NAME
			+ " set ";

	private DBManager(Context context) {
		mContext = context;
		initDB(context);
	}

	public static DBManager init(Context context) {

		if (mDBManager == null)
			mDBManager = new DBManager(context);
		return mDBManager;
	}

	private void initDB(Context context) {
		DBHelper mHelper = new DBHelper(context);
		dbWriter = mHelper.getWritableDatabase();

	}

	/** 插入数据* */
	public void insertData(DownloadObject dObject) {

		ContentValues values = new ContentValues();
		values.put(DBHelper.FILE_NAME, dObject.getFileName());
		values.put(DBHelper.FILE_SOURCE, dObject.getFileSource());
		values.put(DBHelper.FILE_LENGTH, dObject.getFileLength());
		values.put(DBHelper.FILE_TOTAL_LENGTH, dObject.getFileTotalLength());
		dbWriter.insert(DBHelper.TABLE_NAME, null, values);
	}

	/** 删除具体行 **/
	public void deleteCurrentData(String fileName) {

		dbWriter.execSQL(deleteData + " = " + fileName);
	}

	/** 查询数据* */
	public DownloadObject queryData(String fileName) {

		Cursor c = dbWriter.rawQuery(selectData, null);
		while (c.moveToNext()) {
			String dbfileName = c.getString(c
					.getColumnIndex(DBHelper.FILE_NAME));
			if (dbfileName.equals(fileName)) {
				// 数据库里面有记录 说明还没有下载完
				String dbfileSource = c.getString(c
						.getColumnIndex(DBHelper.FILE_SOURCE));
				long dbfileLength = c.getLong(c
						.getColumnIndex(DBHelper.FILE_LENGTH));
				long dbfileTotalLength = c.getLong(c
						.getColumnIndex(DBHelper.FILE_TOTAL_LENGTH));
				c.close();
				return new DownloadObject(dbfileName, dbfileSource,
						dbfileLength, dbfileTotalLength, false);
			}
		}

		c.close();
		return null;
	}

	/** 更新数据* */

	public void updateData(String filename, long fileLength) {
		// dbWriter.update(DBHelper.TABLE_NAME, values, whereClause, whereArgs)
		// dbWriter.execSQL(updateData+DBHelper.FILE_LENGTH+" = "+fileLength+" where "+DBHelper.FILE_NAME+" = "+filename);
		ContentValues contentValues = new ContentValues();

		contentValues.put(DBHelper.FILE_LENGTH, fileLength);

		String whereClause = DBHelper.FILE_NAME + "=?";
		String[] whereArgs = { String.valueOf(filename) };
		dbWriter.update(DBHelper.TABLE_NAME, contentValues, whereClause,
				whereArgs);
	}

	/** 更新数据* */

	public void updateDataTotalLength(String filename, long fileTotalLength) {
		// dbWriter.update(DBHelper.TABLE_NAME, values, whereClause, whereArgs)
		// dbWriter.execSQL(updateData+DBHelper.FILE_LENGTH+" = "+fileLength+" where "+DBHelper.FILE_NAME+" = "+filename);
		ContentValues contentValues = new ContentValues();

		contentValues.put(DBHelper.FILE_TOTAL_LENGTH, fileTotalLength);

		String whereClause = DBHelper.FILE_NAME + "=?";
		String[] whereArgs = { String.valueOf(filename) };
		dbWriter.update(DBHelper.TABLE_NAME, contentValues, whereClause,
				whereArgs);
	}

	
	public void close() {
		if (dbWriter != null)
			dbWriter.close();
	}

}
