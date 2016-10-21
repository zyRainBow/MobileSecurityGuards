package com.zy.mobilesafe.bean;

import android.graphics.drawable.Drawable;

public class RunningAppInfo {

	private Drawable icon;
	private int uid;
	private String name;			//包名
	private String version;
	private String appName;			//应用名
	private long RxBytes;
	private long TxBytes;

	
	
	public RunningAppInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RunningAppInfo(int uid, String name, long rxBytes, long txBytes) {
		super();
		this.uid = uid;
		this.name = name;
		RxBytes = rxBytes;
		TxBytes = txBytes;
	}
	
	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getRxBytes() {
		return RxBytes;
	}
	public void setRxBytes(long rxBytes) {
		RxBytes = rxBytes;
	}
	public long getTxBytes() {
		return TxBytes;
	}
	public void setTxBytes(long txBytes) {
		TxBytes = txBytes;
	}
	public Drawable getIcon() {
		return icon;
	}
	public void setIcon(Drawable icon) {
		this.icon = icon;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
}
