package com.luomaoqiang.download.utl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import android.content.Context;
import android.os.Environment;

public class FileUtl {

	
	public static boolean hasSDCard(Context context) {

		return !Environment.getExternalStorageState().equals("");
	}

	public static String getSDRootPath(Context context) {

		if (!hasSDCard(context)) {
			return null;
		}

		return Environment.getExternalStorageDirectory().getAbsolutePath();
	}

	public static File createFile(String mPath){
		boolean succ = false;
		File file = new File(mPath);

		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		if (!file.exists() || !file.isFile()) {
		
			try {
				succ = file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("createFile succ:" + succ);
		}
		return file;
	}
	public static File createDirectory(String mPath){

		File file = new File(mPath);

		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		if (!file.exists() || !file.isDirectory()) {
			boolean succ = file.mkdir();
			System.out.println("createFile succ:" + succ);
		}
		return file;
	}

	public static boolean fileExists(String mPath) {

		File file = new File(mPath);

		return file.exists();
	}

	public static void deleteFile(String mPath){
		File file = new File(mPath);
		if (file.exists()) {
			file.delete();
		}
	}
	
	public static int getDownloadSize(String mPath){
		
		if (!fileExists(mPath)) {
			
			try {
				createFile(mPath);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("file not exists");
			return 0;
		}
		BufferedReader br = null;
		int result = 0;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(mPath)));
			String content = null;
			String downString = "";
			while ((content = br.readLine()) != null) {
				downString += content;	
			}	
			System.out.println("downStringL:"+downString);
			result = Integer.valueOf(downString);
		} catch (Exception e) {
			// TODO: handle exception
			return result;
		}
		
		System.out.println("result:"+result);
		return result;
	}
	
	public static void write(String mPath,String content){
	
		try {
			if (!fileExists(mPath)) {
				createFile(mPath);
			}	
			PrintWriter writer = new PrintWriter(new FileOutputStream(mPath));
			writer.append(content);
			writer.flush();
			writer.close();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("write catch...");
		}
		
	}
	
	public static long getFileLength(String path){
		if (!fileExists(path)) {
			return 0;
		}
		File file = new File(path);
		return file.length();
	}
}
