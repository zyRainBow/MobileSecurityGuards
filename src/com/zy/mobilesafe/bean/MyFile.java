package com.zy.mobilesafe.bean;

public class MyFile {

	private String fileName;		//文件名
	private long fileSize;		//文件大小
	
	public MyFile(){
		
	}
	
	public MyFile(String fileName, long fileSize) {
		super();
		this.fileName = fileName;
		this.fileSize = fileSize;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public long getFileSize() {
		return fileSize;
	}
	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}
}
