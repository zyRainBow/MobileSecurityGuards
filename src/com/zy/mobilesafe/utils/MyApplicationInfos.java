package com.zy.mobilesafe.utils;

import java.util.ArrayList;
import java.util.List;

import com.zy.mobilesafe.bean.AppInfo;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;


public class MyApplicationInfos {

	private Context context;

	public MyApplicationInfos(Context context) {
		super();
		this.context = context;
	}
	
	/**
	 * 获取本机应用程序
	 * @param appInfoList
	 */
	public void getApplicationInfo(ArrayList<AppInfo> appInfoList){
		List<PackageInfo> packages=context.getPackageManager().getInstalledPackages(0);
		for (int i = 0; i < packages.size(); i++) {
			PackageInfo  packageInfo=packages.get(i);
			AppInfo appInfo =new AppInfo();
			appInfo.setAppIcon(packageInfo.applicationInfo.loadIcon(context.getPackageManager()));
			appInfo.setAppName(packageInfo.applicationInfo.loadLabel(context.getPackageManager()).toString());
			appInfo.setAppVersionCode(packageInfo.versionCode);
			appInfo.setAppVersionName(packageInfo.versionName);
			appInfo.setPackageName(packageInfo.packageName);
			appInfoList.add(appInfo);
			if((packageInfo.applicationInfo.flags&ApplicationInfo.FLAG_SYSTEM)==0){
//				Toast.makeText(context, appInfo.getAppName()+"是用户安装应用", Toast.LENGTH_SHORT).show();
			}else{
//				Toast.makeText(context, appInfo.getAppName()+"是系统应用", Toast.LENGTH_SHORT).show();
			}
		}
		
	}
}
