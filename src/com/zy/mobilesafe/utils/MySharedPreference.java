package com.zy.mobilesafe.utils;

import java.io.File;
import java.util.Set;

import com.zy.mobilesafe.Impl.SharedPreferenceKeyImpl;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.widget.Toast;

public class MySharedPreference {

	private Context context;
	private SharedPreferences sp=null;
	private Editor editor=null;
	
	public MySharedPreference(Context context) {
		super();
		this.context = context;
	}
	
	/**
	 * ��ȡ��������       Ĭ��Ϊ00
	 * @return
	 */
	public String getMyNumber() {
		sp=context.getSharedPreferences(SharedPreferenceKeyImpl.SHARED_FILE_NAME, Context.MODE_PRIVATE);
		String number=sp.getString(SharedPreferenceKeyImpl.MY_NUMBER, "00");
		return number;
	}
	
	/**
	 * ���汾������
	 * @param myNumber
	 */
	public void setMyNumber(String myNumber) {
		sp=context.getSharedPreferences(SharedPreferenceKeyImpl.SHARED_FILE_NAME, Context.MODE_PRIVATE);
		editor=sp.edit();
    	editor.putString(SharedPreferenceKeyImpl.MY_NUMBER, myNumber);
    	editor.commit();
	}
	
	
	
	/**
	 *������������
	 * @param mode
	 */
	public void setBlockPwd(String pwd){
		sp=context.getSharedPreferences(SharedPreferenceKeyImpl.SHARED_FILE_NAME, Context.MODE_PRIVATE);
		editor=sp.edit();
		editor.putString(SharedPreferenceKeyImpl.BLOCKPWD, pwd);
		editor.commit();
	}
	
	/**
	 * ��ȡ��������
	 * @return
	 */
	public String getBlockPwd(){
		sp=context.getSharedPreferences(SharedPreferenceKeyImpl.SHARED_FILE_NAME, Context.MODE_PRIVATE);
		String pwd=sp.getString(SharedPreferenceKeyImpl.BLOCKPWD, null);
		return pwd;
	}
	
	
	/**
	 * ��ȡ�Ƿ�����������
	 * @return
	 */
	public boolean getIsSMSBlock() {
		// TODO Auto-generated method stub
		sp=context.getSharedPreferences(SharedPreferenceKeyImpl.SHARED_FILE_NAME, Context.MODE_PRIVATE);
		boolean flag=sp.getBoolean(SharedPreferenceKeyImpl.ISSMSBLOCK, false);
		return flag;
	}
	
	/**
	 * �����Ƿ�����������
	 */
	public void setIsSMSBlock(boolean isSMSBlock){
		sp=context.getSharedPreferences(SharedPreferenceKeyImpl.SHARED_FILE_NAME, Context.MODE_PRIVATE);
		editor=sp.edit();
		editor.putBoolean(SharedPreferenceKeyImpl.ISSMSBLOCK, isSMSBlock);
		editor.commit();
	}
	
	/**
	 *�����Ƿ�����İ���������
	 * @param isBlock
	 */
	public void setIsBlockStrangeNumberSMS(boolean isBlock){
		sp=context.getSharedPreferences(SharedPreferenceKeyImpl.SHARED_FILE_NAME, Context.MODE_PRIVATE);
		editor=sp.edit();
		editor.putBoolean(SharedPreferenceKeyImpl.ISBLOCKSTRANGENUMBERSMS, isBlock);
		editor.commit();
	}
	
	/**
	 * ��ȡ�Ƿ�����İ���������
	 * @return
	 */
	public boolean getIsBlockStrangeNumberSMS(){
		sp=context.getSharedPreferences(SharedPreferenceKeyImpl.SHARED_FILE_NAME, Context.MODE_PRIVATE);
		boolean flag=sp.getBoolean(SharedPreferenceKeyImpl.ISBLOCKSTRANGENUMBERSMS, false);
		return flag;
	}
	
	/**
	 *�����Ƿ�������ϵ�˶���
	 * @param isBlock
	 */
	public void setIsBlockContactsSMS(boolean isBlock){
		sp=context.getSharedPreferences(SharedPreferenceKeyImpl.SHARED_FILE_NAME, Context.MODE_PRIVATE);
		editor=sp.edit();
		editor.putBoolean(SharedPreferenceKeyImpl.ISBLOCKCONTACTSSMS, isBlock);
		editor.commit();
	}
	
	/**
	 * ��ȡ�Ƿ�������ϵ�˶���
	 * @return
	 */
	public boolean getIsBlockContactsSMS(){
		sp=context.getSharedPreferences(SharedPreferenceKeyImpl.SHARED_FILE_NAME, Context.MODE_PRIVATE);
		boolean flag=sp.getBoolean(SharedPreferenceKeyImpl.ISBLOCKCONTACTSSMS, false);
		return flag;
	}
	
	
	/**
	 * ��ȡ�Ƿ����绰����
	 * @return
	 */
	public boolean getIsTelBlock() {
		// TODO Auto-generated method stub
		sp=context.getSharedPreferences(SharedPreferenceKeyImpl.SHARED_FILE_NAME, Context.MODE_PRIVATE);
		boolean flag=sp.getBoolean(SharedPreferenceKeyImpl.ISTELBLOCK, false);
		return flag;
	}
	
	/**
	 * �����Ƿ����绰����
	 */
	public void setIsTelBlock(boolean isTelBlock){
		sp=context.getSharedPreferences(SharedPreferenceKeyImpl.SHARED_FILE_NAME, Context.MODE_PRIVATE);
		editor=sp.edit();
		editor.putBoolean(SharedPreferenceKeyImpl.ISTELBLOCK, isTelBlock);
		editor.commit();
	}
	
	
	/**
	 *�����Ƿ�����İ������绰
	 * @param isBlock
	 */
	public void setIsBlockStrangeNumberTel(boolean isBlock){
		sp=context.getSharedPreferences(SharedPreferenceKeyImpl.SHARED_FILE_NAME, Context.MODE_PRIVATE);
		editor=sp.edit();
		editor.putBoolean(SharedPreferenceKeyImpl.ISBLOCKSTRANGENUMBERTEL, isBlock);
		editor.commit();
	}
	
	/**
	 * ��ȡ�Ƿ�����İ������绰
	 * @return
	 */
	public boolean getIsBlockStrangeNumberTel(){
		sp=context.getSharedPreferences(SharedPreferenceKeyImpl.SHARED_FILE_NAME, Context.MODE_PRIVATE);
		boolean flag=sp.getBoolean(SharedPreferenceKeyImpl.ISBLOCKSTRANGENUMBERTEL, false);
		return flag;
	}
	
	/**
	 *�����Ƿ�������ϵ�˵绰
	 * @param isBlock
	 */
	public void setIsBlockContactsTel(boolean isBlock){
		sp=context.getSharedPreferences(SharedPreferenceKeyImpl.SHARED_FILE_NAME, Context.MODE_PRIVATE);
		editor=sp.edit();
		editor.putBoolean(SharedPreferenceKeyImpl.ISBLOCKCONTACTSTEL, isBlock);
		editor.commit();
	}
	
	/**
	 * ��ȡ�Ƿ�������ϵ�˵绰
	 * @return
	 */
	public boolean getIsBlockContactsTel(){
		sp=context.getSharedPreferences(SharedPreferenceKeyImpl.SHARED_FILE_NAME, Context.MODE_PRIVATE);
		boolean flag=sp.getBoolean(SharedPreferenceKeyImpl.ISBLOCKCONTACTSTEL, false);
		return flag;
	}
	
	
	/**
	 *��������ģʽ
	 * @param mode
	 */
	public void setTelBlockMode(String mode){
		sp=context.getSharedPreferences(SharedPreferenceKeyImpl.SHARED_FILE_NAME, Context.MODE_PRIVATE);
		editor=sp.edit();
		editor.putString(SharedPreferenceKeyImpl.TELBLOCKMODE, mode);
		editor.commit();
	}
	
	/**
	 * ��ȡ�绰����ģʽ
	 * @return
	 */
	public String getTelBlockMode(){
		sp=context.getSharedPreferences(SharedPreferenceKeyImpl.SHARED_FILE_NAME, Context.MODE_PRIVATE);
		String mode=sp.getString(SharedPreferenceKeyImpl.TELBLOCKMODE, "");
		return mode;
	}
	
	/**
	 *����Ӧ�ü�������
	 * @param mode
	 */
	public void setAppLockPwd(String pwd){
		sp=context.getSharedPreferences(SharedPreferenceKeyImpl.SHARED_FILE_NAME, Context.MODE_PRIVATE);
		editor=sp.edit();
		editor.putString(SharedPreferenceKeyImpl.APPLOCKPWD, pwd);
		editor.commit();
	}
	
	/**
	 * ��ȡӦ�ü�������
	 * @return
	 */
	public String getAppLockPwd(){
		sp=context.getSharedPreferences(SharedPreferenceKeyImpl.SHARED_FILE_NAME, Context.MODE_PRIVATE);
		String pwd=sp.getString(SharedPreferenceKeyImpl.APPLOCKPWD, null);
		return pwd;
	}

	
	/**
	 *�������Ӧ�ð���
	 * @param set
	 */
	public void setappLockPackageName(Set<String> set){
//		File file = new File("/data/data/com.wist.securityguard" + "/shared_prefs/"
//	            + SharedPreferenceKeyImpl.SHARED_LOCK_PACKAGE_FILE_NAME + ".xml");
		String path=context.getFilesDir().getParent()+"/shared_prefs/"
	            + SharedPreferenceKeyImpl.SHARED_LOCK_PACKAGE_FILE_NAME + ".xml";
		Toast.makeText(context, path, Toast.LENGTH_SHORT).show();
		File file=new File(path);
	      if (file.exists()) {
	         file.delete();
	      }
		sp=context.getSharedPreferences(SharedPreferenceKeyImpl.SHARED_LOCK_PACKAGE_FILE_NAME, Context.MODE_PRIVATE);
		editor=sp.edit();
		editor.putStringSet(SharedPreferenceKeyImpl.APPLOCKPACKAGENAME, set);
		editor.commit();
		
	}

	/**
	 * ��ȡ����Ӧ�ð���
	 * @return
	 */
	public Set<String> getappLockPackageName(){
		sp=context.getSharedPreferences(SharedPreferenceKeyImpl.SHARED_LOCK_PACKAGE_FILE_NAME, Context.MODE_PRIVATE);
		Set<String> set=sp.getStringSet(SharedPreferenceKeyImpl.APPLOCKPACKAGENAME, null);
		return set;
	}
	
	
	
	/**
	 * �Ƿ����������֪ͨ
	 * @param isNotification
	 */
	public void setIsFlowsMonitorNotification(boolean isNotification){
		sp=context.getSharedPreferences(SharedPreferenceKeyImpl.SHARED_FILE_NAME, Context.MODE_PRIVATE);
		editor=sp.edit();
		editor.putBoolean(SharedPreferenceKeyImpl.ISFLOWSMONITORNOTIFICATION, isNotification);
		editor.commit();
	}
	
	/**
	 * ��ȡ�Ƿ����������֪ͨ
	 * @return
	 */
	public boolean getIsFlowsMonitorNotification(){
		sp=context.getSharedPreferences(SharedPreferenceKeyImpl.SHARED_FILE_NAME, Context.MODE_PRIVATE);
		boolean flag=sp.getBoolean(SharedPreferenceKeyImpl.ISFLOWSMONITORNOTIFICATION, false);
		return flag;
	}
	
	
	/**
	 * ��ȡ�������
	 * @return
	 */
	public  int getMaxFlows() {
		sp=context.getSharedPreferences(SharedPreferenceKeyImpl.SHARED_FILE_NAME, Context.MODE_PRIVATE);
		int count=sp.getInt(SharedPreferenceKeyImpl.MAXFLOWS, 0);
		return count;
	}
	
	/**
	 * �����������
	 * @param count
	 */
	public void setMaxFlows(int count) {
		sp=context.getSharedPreferences(SharedPreferenceKeyImpl.SHARED_FILE_NAME, Context.MODE_PRIVATE);
		editor=sp.edit();
    	editor.putInt(SharedPreferenceKeyImpl.MAXFLOWS, count);
    	editor.commit();
	}
	
	/**
	 * ��ȡmobile�ܽ�������
	 * @return
	 */
	public long getmobileRxTotalFlows() {
		sp=context.getSharedPreferences(SharedPreferenceKeyImpl.SHARED_FILE_NAME, Context.MODE_PRIVATE);
		long count=sp.getLong(SharedPreferenceKeyImpl.MOBILERXTOTALFLOWS, 0);
		return count;
	}
	
	/**
	 * ����mobile�ܷ�������
	 * @param count
	 */
	public void setmobileTxTotalFlows(long count) {
		sp=context.getSharedPreferences(SharedPreferenceKeyImpl.SHARED_FILE_NAME, Context.MODE_PRIVATE);
		editor=sp.edit();
		editor.putLong(SharedPreferenceKeyImpl.MOBILETXTOTALFLOWS, count);
		editor.commit();
	}
	/**
	 * ��ȡmobile�ܷ�������
	 * @return
	 */
	public long getmobileTxTotalFlows() {
		sp=context.getSharedPreferences(SharedPreferenceKeyImpl.SHARED_FILE_NAME, Context.MODE_PRIVATE);
		long count=sp.getLong(SharedPreferenceKeyImpl.MOBILETXTOTALFLOWS, 0);
		return count;
	}
	
	/**
	 * ����mobile�ܽ�������
	 * @param count
	 */
	public void setmobileRxTotalFlows(long count) {
		sp=context.getSharedPreferences(SharedPreferenceKeyImpl.SHARED_FILE_NAME, Context.MODE_PRIVATE);
		editor=sp.edit();
		editor.putLong(SharedPreferenceKeyImpl.MOBILERXTOTALFLOWS, count);
		editor.commit();
	}
	/**
	 * ��ȡwifi�ܽ�������
	 * @return
	 */
	public long getwifiRxTotalFlows() {
		sp=context.getSharedPreferences(SharedPreferenceKeyImpl.SHARED_FILE_NAME, Context.MODE_PRIVATE);
		long count=sp.getLong(SharedPreferenceKeyImpl.WIFIRXTOTALFLOWS, 0);
		return count;
	}
	
	/**
	 * ����wifi�ܷ�������
	 * @param count
	 */
	public void setwifiTxTotalFlows(long count) {
		sp=context.getSharedPreferences(SharedPreferenceKeyImpl.SHARED_FILE_NAME, Context.MODE_PRIVATE);
		editor=sp.edit();
		editor.putLong(SharedPreferenceKeyImpl.WIFITXTOTALFLOWS, count);
		editor.commit();
	}
	/**
	 * ��ȡwifi�ܷ�������
	 * @return
	 */
	public long getwifiTxTotalFlows() {
		sp=context.getSharedPreferences(SharedPreferenceKeyImpl.SHARED_FILE_NAME, Context.MODE_PRIVATE);
		long count=sp.getLong(SharedPreferenceKeyImpl.WIFITXTOTALFLOWS, 0);
		return count;
	}
	
	/**
	 * ����wifi�ܽ�������
	 * @param count
	 */
	public void setwifiRxTotalFlows(long count) {
		sp=context.getSharedPreferences(SharedPreferenceKeyImpl.SHARED_FILE_NAME, Context.MODE_PRIVATE);
		editor=sp.edit();
		editor.putLong(SharedPreferenceKeyImpl.WIFIRXTOTALFLOWS, count);
		editor.commit();
	}
	
	/**
	 * ��������֪ͨ��������
	 */
	public void setNum(int num){
		sp=context.getSharedPreferences(SharedPreferenceKeyImpl.SHARED_FILE_NAME,  Context.MODE_PRIVATE);
		editor=sp.edit();
		editor.putInt(SharedPreferenceKeyImpl.NUM, num);
		editor.commit();
	}
	
	/***
	 * ��ȡ����֪ͨ��������
	 */
	public int getNum(){
		sp=context.getSharedPreferences(SharedPreferenceKeyImpl.SHARED_FILE_NAME, Context.MODE_PRIVATE);
		int num=sp.getInt(SharedPreferenceKeyImpl.NUM, 0);
		return num;
	}
	
	
	/**
	 * �����Ƿ����    
	 * @param isPreventThelf
	 */
	public void setIsPreventThelf(boolean isPreventThelf){
		sp=context.getSharedPreferences(SharedPreferenceKeyImpl.SHARED_FILE_NAME, Context.MODE_PRIVATE);
		editor=sp.edit();
    	editor.putBoolean(SharedPreferenceKeyImpl.ISPREVENTTHELF, isPreventThelf);
    	editor.commit();
	}
	
	/**
	 * ��ȡ�Ƿ����    Ĭ��Ϊfalse
	 * @return 
	 */
	public boolean getIsPreventThelf(){
		sp=context.getSharedPreferences(SharedPreferenceKeyImpl.SHARED_FILE_NAME, Context.MODE_PRIVATE);
		return sp.getBoolean(SharedPreferenceKeyImpl.ISPREVENTTHELF, false);
	}
	
	/**
	 *�����������
	 * @param mode
	 */
	public void setPreventThelfPwd(String pwd){
		sp=context.getSharedPreferences(SharedPreferenceKeyImpl.SHARED_FILE_NAME, Context.MODE_PRIVATE);
		editor=sp.edit();
		editor.putString(SharedPreferenceKeyImpl.PREVENTTHELFPWD, pwd);
		editor.commit();
	}
	
	/**
	 * ��ȡ��������
	 * @return
	 */
	public String getPreventThelfPwd(){
		sp=context.getSharedPreferences(SharedPreferenceKeyImpl.SHARED_FILE_NAME, Context.MODE_PRIVATE);
		String pwd=sp.getString(SharedPreferenceKeyImpl.PREVENTTHELFPWD, null);
		return pwd;
	}
	
	
	
	/**
	 * �����������
	 * @param preventThelfNumber
	 */
	public void setPreventThelfNumber(String preventThelfNumber){
		sp=context.getSharedPreferences(SharedPreferenceKeyImpl.SHARED_FILE_NAME, Context.MODE_PRIVATE);
		editor=sp.edit();
    	editor.putString(SharedPreferenceKeyImpl.PREVENT_THELF_NUMBER, preventThelfNumber);
    	editor.commit();
	}
	
	/**
	 * ��ȡ��������       Ĭ��Ϊ��
	 * @return
	 */
	public String getPreventThelfNumber(){
		sp=context.getSharedPreferences(SharedPreferenceKeyImpl.SHARED_FILE_NAME, Context.MODE_PRIVATE);
		String number=sp.getString(SharedPreferenceKeyImpl.PREVENT_THELF_NUMBER, null);
		return number;
	}
	
}
