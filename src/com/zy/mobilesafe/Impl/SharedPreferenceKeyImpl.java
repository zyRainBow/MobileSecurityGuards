package com.zy.mobilesafe.Impl;

public interface SharedPreferenceKeyImpl {

	/*****配置文件名****/
	public static final String SHARED_FILE_NAME="config";	
	
	/*****加锁应用文件名****/
	public static final String SHARED_LOCK_PACKAGE_FILE_NAME="lock_package";	//加锁应用文件名
	
	/***本机号码(键)***/
	public static final String MY_NUMBER="myNumber";	
	
	/*****拦截密码*****/
	public static final String BLOCKPWD = "blockPwd";
	
	/***是否开启短信拦截***/
	public static final String ISSMSBLOCK = "isSMSBlock";						//是否开启短信拦截
	
	/***是否拦截陌生号码短信***/
	public static final String ISBLOCKSTRANGENUMBERSMS="IsBlockStrangeNumberSMS";	//是否拦截陌生号码短信
	
	/***是否拦截联系人短信***/
	public static final String ISBLOCKCONTACTSSMS="IsBlockContactsSMS";			//是否拦截联系人短信
	
	/***短信拦截关键字***/
	public static final String SMSBLOCKKEYWORDS = "smsBlockKeywords";			//短信拦截关键字
	
	/***是否开启电话拦截***/
	public static final String ISTELBLOCK = "isTelBlock";						//是否开启电话拦截
	
	/***是否拦截陌生号码电话***/
	public static final String ISBLOCKSTRANGENUMBERTEL="IsBlockStrangeNumberTel";	//是否拦截陌生号码电话
	
	/***是否拦截联系人电话***/
	public static final String ISBLOCKCONTACTSTEL="IsBlockContactsTel";			//是否拦截联系人电话
	
	/***是否开启流量监控通知***/
	public static final String ISFLOWSMONITORNOTIFICATION="IsFlowsMonitorNotification";	//是否开启流量监控通知
	
	/***电话拦截模式***/
	public static final String TELBLOCKMODE="TelBlockMode";						//电话拦截模式
	
	/***加锁应用***/
	public static final String APPLOCKPACKAGENAME="appLockPackageName";			//加锁应用
	/***加锁应用密码***/
	public static final String APPLOCKPWD="appLockPwd";							//加锁应用密码
	
	/*****最大流量****/
	public static final String MAXFLOWS = "maxFlows";
	
	/*****手机总接收流量****/
	public static final String MOBILERXTOTALFLOWS = "mobileRxTotalFlows";
	
	/*****手机总发送流量****/
	public static final String MOBILETXTOTALFLOWS = "mobileTxTotalFlows";
	
	/*****wifi总接收流量****/
	public static final String WIFIRXTOTALFLOWS = "wifiRxTotalFlows";
	
	/*****wifi总发送流量****/
	public static final String WIFITXTOTALFLOWS = "wifiTxTotalFlows";
	
	/****count计数*****/
	public static final String NUM="num";
	
	/****是否防盗****/
	public static final String ISPREVENTTHELF="isPreventThelf";					//是否防盗

	/****防盗号码***/
	public static final String PREVENT_THELF_NUMBER="preventThelfNumber";		//防盗号码
	
	/*****防盗密码****/
	public static final String PREVENTTHELFPWD = "preventThelfPwd";				//防盗密码
	
}
