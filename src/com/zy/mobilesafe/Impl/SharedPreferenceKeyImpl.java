package com.zy.mobilesafe.Impl;

public interface SharedPreferenceKeyImpl {

	/*****�����ļ���****/
	public static final String SHARED_FILE_NAME="config";	
	
	/*****����Ӧ���ļ���****/
	public static final String SHARED_LOCK_PACKAGE_FILE_NAME="lock_package";	//����Ӧ���ļ���
	
	/***��������(��)***/
	public static final String MY_NUMBER="myNumber";	
	
	/*****��������*****/
	public static final String BLOCKPWD = "blockPwd";
	
	/***�Ƿ�����������***/
	public static final String ISSMSBLOCK = "isSMSBlock";						//�Ƿ�����������
	
	/***�Ƿ�����İ���������***/
	public static final String ISBLOCKSTRANGENUMBERSMS="IsBlockStrangeNumberSMS";	//�Ƿ�����İ���������
	
	/***�Ƿ�������ϵ�˶���***/
	public static final String ISBLOCKCONTACTSSMS="IsBlockContactsSMS";			//�Ƿ�������ϵ�˶���
	
	/***�������عؼ���***/
	public static final String SMSBLOCKKEYWORDS = "smsBlockKeywords";			//�������عؼ���
	
	/***�Ƿ����绰����***/
	public static final String ISTELBLOCK = "isTelBlock";						//�Ƿ����绰����
	
	/***�Ƿ�����İ������绰***/
	public static final String ISBLOCKSTRANGENUMBERTEL="IsBlockStrangeNumberTel";	//�Ƿ�����İ������绰
	
	/***�Ƿ�������ϵ�˵绰***/
	public static final String ISBLOCKCONTACTSTEL="IsBlockContactsTel";			//�Ƿ�������ϵ�˵绰
	
	/***�Ƿ����������֪ͨ***/
	public static final String ISFLOWSMONITORNOTIFICATION="IsFlowsMonitorNotification";	//�Ƿ����������֪ͨ
	
	/***�绰����ģʽ***/
	public static final String TELBLOCKMODE="TelBlockMode";						//�绰����ģʽ
	
	/***����Ӧ��***/
	public static final String APPLOCKPACKAGENAME="appLockPackageName";			//����Ӧ��
	/***����Ӧ������***/
	public static final String APPLOCKPWD="appLockPwd";							//����Ӧ������
	
	/*****�������****/
	public static final String MAXFLOWS = "maxFlows";
	
	/*****�ֻ��ܽ�������****/
	public static final String MOBILERXTOTALFLOWS = "mobileRxTotalFlows";
	
	/*****�ֻ��ܷ�������****/
	public static final String MOBILETXTOTALFLOWS = "mobileTxTotalFlows";
	
	/*****wifi�ܽ�������****/
	public static final String WIFIRXTOTALFLOWS = "wifiRxTotalFlows";
	
	/*****wifi�ܷ�������****/
	public static final String WIFITXTOTALFLOWS = "wifiTxTotalFlows";
	
	/****count����*****/
	public static final String NUM="num";
	
	/****�Ƿ����****/
	public static final String ISPREVENTTHELF="isPreventThelf";					//�Ƿ����

	/****��������***/
	public static final String PREVENT_THELF_NUMBER="preventThelfNumber";		//��������
	
	/*****��������****/
	public static final String PREVENTTHELFPWD = "preventThelfPwd";				//��������
	
}
