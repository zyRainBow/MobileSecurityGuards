package com.zy.mobilesafe.bean;

import android.graphics.drawable.Drawable;

public class AppInfo {

	private String appName;				//应用名称
	private String appVersionName;		//版本名称
	private int appVersionCode;			//版本号
	private Drawable appIcon=null;		//应用图标
	private String packageName;			//包名
	public AppInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public String getAppVersionName() {
		return appVersionName;
	}
	public void setAppVersionName(String appVersionName) {
		this.appVersionName = appVersionName;
	}
	public int getAppVersionCode() {
		return appVersionCode;
	}
	public void setAppVersionCode(int appVersionCode) {
		this.appVersionCode = appVersionCode;
	}
	public Drawable getAppIcon() {
		return appIcon;
	}
	public void setAppIcon(Drawable appIcon) {
		this.appIcon = appIcon;
	}
}
