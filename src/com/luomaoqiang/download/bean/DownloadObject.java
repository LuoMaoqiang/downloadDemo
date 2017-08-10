package com.luomaoqiang.download.bean;

public class DownloadObject {

	private String fileName;
	
	private String fileSource;
	
	private long fileLength;
	
	private long fileTotalLength;
	
	private boolean isChangeTotalLength;
	
	public DownloadObject(String fileName, String fileSource){
		this.fileName = fileName;
		this.fileSource = fileSource;
	}

	public DownloadObject(String fileName, String fileSource, long fileLength,
			long fileTotalLength, boolean isChangeTotalLength) {
		super();
		this.fileName = fileName;
		this.fileSource = fileSource;
		this.fileLength = fileLength;
		this.fileTotalLength = fileTotalLength;
		this.isChangeTotalLength = isChangeTotalLength;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileSource() {
		return fileSource;
	}

	public void setFileSource(String fileSource) {
		this.fileSource = fileSource;
	}

	public long getFileLength() {
		return fileLength;
	}

	public void setFileLength(long fileLength) {
		this.fileLength = fileLength;
	}

	public long getFileTotalLength() {
		return fileTotalLength;
	}

	public void setFileTotalLength(long fileTotalLength) {
		this.fileTotalLength = fileTotalLength;
	}

	public boolean isChangeTotalLength() {
		return isChangeTotalLength;
	}

	public void setChangeTotalLength(boolean isChangeTotalLength) {
		this.isChangeTotalLength = isChangeTotalLength;
	}

	@Override
	public String toString() {
		return "DownloadObject [fileName=" + fileName + ", fileSource="
				+ fileSource + ", fileLength=" + fileLength
				+ ", fileTotalLength=" + fileTotalLength + ", isChangeTotalLength=" + isChangeTotalLength
				+ "]";
	}
	
	
}
