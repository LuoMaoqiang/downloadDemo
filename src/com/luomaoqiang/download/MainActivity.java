package com.luomaoqiang.download;

import com.luomaoqiang.download.utl.FileUtl;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends Activity {

	private String url = "http://220.170.49.111/1/u/i/v/m/uivmcmiiwgddknrpltuvfepmvgijlz/he.yinyuetai.com/88CE01595A940BC83C7AB2C616308D62.mp4";
    String sec_url = "http://shouji.360tpcdn.com/170626/3f72146832f2f1d2bb7b93a2df09a287/com.mt.mtxx.mtxx_6751.apk";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		findViewById(R.id.button1).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				DownloadTask downloadTask = new DownloadTask(MainActivity.this,
						url);
				downloadTask.execute();
			}
		});
		findViewById(R.id.button2).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String mRootPath = FileUtl.getSDRootPath(MainActivity.this)+"/"+ MainActivity.this.getPackageName()+"/download";
				String fileName = url.substring(url.lastIndexOf("/")+1,url.length());
				if (fileName.length() >=64) {
					fileName = fileName.substring(0,16);
				}
				String filePath = mRootPath+"/"+fileName;
				FileUtl.deleteFile(filePath);
			}
		});
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
}
