package com.luomaoqiang.download;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

import com.luomaoqiang.download.bean.DownloadObject;
import com.luomaoqiang.download.db.DBManager;
import com.luomaoqiang.download.utl.FileUtl;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.widget.ProgressBar;

public class DownloadTask extends AsyncTask<Void, Long, Boolean> {

	private String mUrl;
	private String fileName;
	private DownloadObject dlObject;
	private String mRootPath;
	private Context context;
	private long start = 0;// 下载起始位置
	private DBManager mDBManager;
	private ProgressDialog progressDialog;
	private RandomAccessFile randomAccessFile;

	public DownloadTask(Context context, String mUrl) {
		super();
		this.mUrl = mUrl;
		this.context = context;
		mDBManager = DBManager.init(context);
		initFiles();
	}

	private void initFiles() {
		// TODO Auto-generated method stub
		mRootPath = FileUtl.getSDRootPath(context) + "/"
				+ context.getPackageName() + "/download";
		if (!FileUtl.fileExists(mRootPath)) {
			FileUtl.createDirectory(mRootPath);
		}
		if (TextUtils.isEmpty(mUrl)) {
			try {
				throw new Exception("download url is empty");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		fileName = mUrl.substring(mUrl.lastIndexOf("/") + 1, mUrl.length());
		if (fileName.length() >= 64) {
			fileName = fileName.substring(0, 16)+fileName.substring(fileName.lastIndexOf(".")-1);
		}
		String filePath = mRootPath + "/" + fileName;
		if (!FileUtl.fileExists(filePath)) {
			FileUtl.createFile(filePath);
			return;
		}
		start = FileUtl.getFileLength(filePath);
	}

	public void fileInDB(){
		dlObject = mDBManager.queryData(fileName);
		if (dlObject == null) {
			System.out.println("file not exists in db");
			dlObject = new DownloadObject(fileName, mUrl);
			mDBManager.insertData(dlObject);
		    return;
		}
		
	}
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		fileInDB();
		progressDialog = ProgressDialog.show(context, "提示", "下载中...");
		try {
			randomAccessFile = new RandomAccessFile(new File(mRootPath + "/" + fileName),
					"rwd");
			System.out.println("start:" + start);
			randomAccessFile.seek(start);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	protected Boolean doInBackground(Void... params) {
		// TODO Auto-generated method stub
		URL url = null;
		HttpURLConnection c = null;
		InputStream is = null;
		try {
			url = new URL(mUrl);
			c = (HttpURLConnection) url.openConnection();
			c.setRequestMethod("GET");
			c.setReadTimeout(5000);
			c.setConnectTimeout(5000);
			c.setRequestProperty("Range", "bytes=" + start + "-");
			is = c.getInputStream();
			if (start == 0) {
				//防止 断点下载总大小变化
				dlObject.setFileTotalLength(c.getContentLength());
			}
			byte[] temp = new byte[1024 * 4];
			int len = -1;
			while ((len = is.read(temp)) != -1) {
				dlObject.setFileLength(dlObject.getFileLength() + len);
				randomAccessFile.write(temp, 0, len);
				publishProgress(dlObject.getFileLength());
			}	
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("e:" + e.toString());
		} finally {
			try {
				if(randomAccessFile != null)
				randomAccessFile.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}

		}

		return true;
	}

	@Override
	protected void onProgressUpdate(Long... values) {
		// TODO Auto-generated method stub
		System.out.println("values:" + values[0]);
		mDBManager.updateData(fileName, values[0]);
		if (!dlObject.isChangeTotalLength()) {
			mDBManager.updateDataTotalLength(fileName, dlObject.getFileTotalLength());
			dlObject.setChangeTotalLength(true);
		}
		
	}

	@Override
	protected void onPostExecute(Boolean result) {
		// TODO Auto-generated method stub
		System.out.println("下载完了...onPostExecute");
	
		progressDialog.dismiss();

	}

	@Override
	protected void onCancelled() {
		// TODO Auto-generated method stub
		super.onCancelled();
	}

}
