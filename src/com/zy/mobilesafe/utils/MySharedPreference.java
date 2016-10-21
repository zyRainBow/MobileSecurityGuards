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
	 * 获取本机号码       默认为00
	 * @return
	 */
	public String getMyNumber() {
		sp=context.getSharedPreferences(SharedPreferenceKeyImpl.SHARED_FILE_NAME, Context.MODE_PRIVATE);
		String number=sp.getString(SharedPreferenceKeyImpl.MY_NUMBER, "00");
		return number;
	}
	
	/**
	 * 保存本机号码
	 * @param myNumber
	 */
	public void setMyNumber(String myNumber) {
		sp=context.getSharedPreferences(SharedPreferenceKeyImpl.SHARED_FILE_NAME, Context.MODE_PRIVATE);
		editor=sp.edit();
    	editor.putString(SharedPreferenceKeyImpl.MY_NUMBER, myNumber);
    	editor.commit();
	}
	
	
	
	/**
	 *保存拦截密码
	 * @param mode
	 */
	public void setBlockPwd(String pwd){
		sp=context.getSharedPreferences(SharedPreferenceKeyImpl.SHARED_FILE_NAME, Context.MODE_PRIVATE);
		editor=sp.edit();
		editor.putString(SharedPreferenceKeyImpl.BLOCKPWD, pwd);
		editor.commit();
	}
	
	/**
	 * 获取拦截密码
	 * @return
	 */
	public String getBlockPwd(){
		sp=context.getSharedPreferences(SharedPreferenceKeyImpl.SHARED_FILE_NAME, Context.MODE_PRIVATE);
		String pwd=sp.getString(SharedPreferenceKeyImpl.BLOCKPWD, null);
		return pwd;
	}
	
	
	/**
	 * 获取是否开启短信拦截
	 * @return
	 */
	public boolean getIsSMSBlock() {
		// TODO Auto-generated method stub
		sp=context.getSharedPreferences(SharedPreferenceKeyImpl.SHARED_FILE_NAME, Context.MODE_PRIVATE);
		boolean flag=sp.getBoolean(SharedPreferenceKeyImpl.ISSMSBLOCK, false);
		return flag;
	}
	
	/**
	 * 保存是否开启短信拦截
	 */
	public void setIsSMSBlock(boolean isSMSBlock){
		sp=context.getSharedPreferences(SharedPreferenceKeyImpl.SHARED_FILE_NAME, Context.MODE_PRIVATE);
		editor=sp.edit();
		editor.putBoolean(SharedPreferenceKeyImpl.ISSMSBLOCK, isSMSBlock);
		editor.commit();
	}
	
	/**
	 *保存是否拦截陌生号码短信
	 * @param isBlock
	 */
	public void setIsBlockStrangeNumberSMS(boolean isBlock){
		sp=context.getSharedPreferences(SharedPreferenceKeyImpl.SHARED_FILE_NAME, Context.MODE_PRIVATE);
		editor=sp.edit();
		editor.putBoolean(SharedPreferenceKeyImpl.ISBLOCKSTRANGENUMBERSMS, isBlock);
		editor.commit();
	}
	
	/**
	 * 获取是否拦截陌生号码短信
	 * @return
	 */
	public boolean getIsBlockStrangeNumberSMS(){
		sp=context.getSharedPreferences(SharedPreferenceKeyImpl.SHARED_FILE_NAME, Context.MODE_PRIVATE);
		boolean flag=sp.getBoolean(SharedPreferenceKeyImpl.ISBLOCKSTRANGENUMBERSMS, false);
		return flag;
	}
	
	/**
	 *保存是否拦截联系人短信
	 * @param isBlock
	 */
	public void setIsBlockContactsSMS(boolean isBlock){
		sp=context.getSharedPreferences(SharedPreferenceKeyImpl.SHARED_FILE_NAME, Context.MODE_PRIVATE);
		editor=sp.edit();
		editor.putBoolean(SharedPreferenceKeyImpl.ISBLOCKCONTACTSSMS, isBlock);
		editor.commit();
	}
	
	/**
	 * 获取是否拦截联系人短信
	 * @return
	 */
	public boolean getIsBlockContactsSMS(){
		sp=context.getSharedPreferences(SharedPreferenceKeyImpl.SHARED_FILE_NAME, Context.MODE_PRIVATE);
		boolean flag=sp.getBoolean(SharedPreferenceKeyImpl.ISBLOCKCONTACTSSMS, false);
		return flag;
	}
	
	
	/**
	 * 获取是否开启电话拦截
	 * @return
	 */
	public boolean getIsTelBlock() {
		// TODO Auto-generated method stub
		sp=context.getSharedPreferences(SharedPreferenceKeyImpl.SHARED_FILE_NAME, Context.MODE_PRIVATE);
		boolean flag=sp.getBoolean(SharedPreferenceKeyImpl.ISTELBLOCK, false);
		return flag;
	}
	
	/**
	 * 保存是否开启电话拦截
	 */
	public void setIsTelBlock(boolean isTelBlock){
		sp=context.getSharedPreferences(SharedPreferenceKeyImpl.SHARED_FILE_NAME, Context.MODE_PRIVATE);
		editor=sp.edit();
		editor.putBoolean(SharedPreferenceKeyImpl.ISTELBLOCK, isTelBlock);
		editor.commit();
	}
	
	
	/**
	 *保存是否拦截陌生号码电话
	 * @param isBlock
	 */
	public void setIsBlockStrangeNumberTel(boolean isBlock){
		sp=context.getSharedPreferences(SharedPreferenceKeyImpl.SHARED_FILE_NAME, Context.MODE_PRIVATE);
		editor=sp.edit();
		editor.putBoolean(SharedPreferenceKeyImpl.ISBLOCKSTRANGENUMBERTEL, isBlock);
		editor.commit();
	}
	
	/**
	 * 获取是否拦截陌生号码电话
	 * @return
	 */
	public boolean getIsBlockStrangeNumberTel(){
		sp=context.getSharedPreferences(SharedPreferenceKeyImpl.SHARED_FILE_NAME, Context.MODE_PRIVATE);
		boolean flag=sp.getBoolean(SharedPreferenceKeyImpl.ISBLOCKSTRANGENUMBERTEL, false);
		return flag;
	}
	
	/**
	 *保存是否拦截联系人电话
	 * @param isBlock
	 */
	public void setIsBlockContactsTel(boolean isBlock){
		sp=context.getSharedPreferences(SharedPreferenceKeyImpl.SHARED_FILE_NAME, Context.MODE_PRIVATE);
		editor=sp.edit();
		editor.putBoolean(SharedPreferenceKeyImpl.ISBLOCKCONTACTSTEL, isBlock);
		editor.commit();
	}
	
	/**
	 * 获取是否拦截联系人电话
	 * @return
	 */
	public boolean getIsBlockContactsTel(){
		sp=context.getSharedPreferences(SharedPreferenceKeyImpl.SHARED_FILE_NAME, Context.MODE_PRIVATE);
		boolean flag=sp.getBoolean(SharedPreferenceKeyImpl.ISBLOCKCONTACTSTEL, false);
		return flag;
	}
	
	
	/**
	 *保存拦截模式
	 * @param mode
	 */
	public void setTelBlockMode(String mode){
		sp=context.getSharedPreferences(SharedPreferenceKeyImpl.SHARED_FILE_NAME, Context.MODE_PRIVATE);
		editor=sp.edit();
		editor.putString(SharedPreferenceKeyImpl.TELBLOCKMODE, mode);
		editor.commit();
	}
	
	/**
	 * 获取电话拦截模式
	 * @return
	 */
	public String getTelBlockMode(){
		sp=context.getSharedPreferences(SharedPreferenceKeyImpl.SHARED_FILE_NAME, Context.MODE_PRIVATE);
		String mode=sp.getString(SharedPreferenceKeyImpl.TELBLOCKMODE, "");
		return mode;
	}
	
	/**
	 *保存应用加锁密码
	 * @param mode
	 */
	public void setAppLockPwd(String pwd){
		sp=context.getSharedPreferences(SharedPreferenceKeyImpl.SHARED_FILE_NAME, Context.MODE_PRIVATE);
		editor=sp.edit();
		editor.putString(SharedPreferenceKeyImpl.APPLOCKPWD, pwd);
		editor.commit();
	}
	
	/**
	 * 获取应用加锁密码
	 * @return
	 */
	public String getAppLockPwd(){
		sp=context.getSharedPreferences(SharedPreferenceKeyImpl.SHARED_FILE_NAME, Context.MODE_PRIVATE);
		String pwd=sp.getString(SharedPreferenceKeyImpl.APPLOCKPWD, null);
		return pwd;
	}

	
	/**
	 *保存加锁应用包名
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
	 * 获取加锁应用包名
	 * @return
	 */
	public Set<String> getappLockPackageName(){
		sp=context.getSharedPreferences(SharedPreferenceKeyImpl.SHARED_LOCK_PACKAGE_FILE_NAME, Context.MODE_PRIVATE);
		Set<String> set=sp.getStringSet(SharedPreferenceKeyImpl.APPLOCKPACKAGENAME, null);
		return set;
	}
	
	
	
	/**
	 * 是否开启流量监控通知
	 * @param isNotification
	 */
	public void setIsFlowsMonitorNotification(boolean isNotification){
		sp=context.getSharedPreferences(SharedPreferenceKeyImpl.SHARED_FILE_NAME, Context.MODE_PRIVATE);
		editor=sp.edit();
		editor.putBoolean(SharedPreferenceKeyImpl.ISFLOWSMONITORNOTIFICATION, isNotification);
		editor.commit();
	}
	
	/**
	 * 获取是否开启流量监控通知
	 * @return
	 */
	public boolean getIsFlowsMonitorNotification(){
		sp=context.getSharedPreferences(SharedPreferenceKeyImpl.SHARED_FILE_NAME, Context.MODE_PRIVATE);
		boolean flag=sp.getBoolean(SharedPreferenceKeyImpl.ISFLOWSMONITORNOTIFICATION, false);
		return flag;
	}
	
	
	/**
	 * 获取最大流量
	 * @return
	 */
	public  int getMaxFlows() {
		sp=context.getSharedPreferences(SharedPreferenceKeyImpl.SHARED_FILE_NAME, Context.MODE_PRIVATE);
		int count=sp.getInt(SharedPreferenceKeyImpl.MAXFLOWS, 0);
		return count;
	}
	
	/**
	 * 保存最大流量
	 * @param count
	 */
	public void setMaxFlows(int count) {
		sp=context.getSharedPreferences(SharedPreferenceKeyImpl.SHARED_FILE_NAME, Context.MODE_PRIVATE);
		editor=sp.edit();
    	editor.putInt(SharedPreferenceKeyImpl.MAXFLOWS, count);
    	editor.commit();
	}
	
	/**
	 * 获取mobile总接收流量
	 * @return
	 */
	public long getmobileRxTotalFlows() {
		sp=context.getSharedPreferences(SharedPreferenceKeyImpl.SHARED_FILE_NAME, Context.MODE_PRIVATE);
		long count=sp.getLong(SharedPreferenceKeyImpl.MOBILERXTOTALFLOWS, 0);
		return count;
	}
	
	/**
	 * 保存mobile总发送流量
	 * @param count
	 */
	public void setmobileTxTotalFlows(long count) {
		sp=context.getSharedPreferences(SharedPreferenceKeyImpl.SHARED_FILE_NAME, Context.MODE_PRIVATE);
		editor=sp.edit();
		editor.putLong(SharedPreferenceKeyImpl.MOBILETXTOTALFLOWS, count);
		editor.commit();
	}
	/**
	 * 获取mobile总发送流量
	 * @return
	 */
	public long getmobileTxTotalFlows() {
		sp=context.getSharedPreferences(SharedPreferenceKeyImpl.SHARED_FILE_NAME, Context.MODE_PRIVATE);
		long count=sp.getLong(SharedPreferenceKeyImpl.MOBILETXTOTALFLOWS, 0);
		return count;
	}
	
	/**
	 * 保存mobile总接收流量
	 * @param count
	 */
	public void setmobileRxTotalFlows(long count) {
		sp=context.getSharedPreferences(SharedPreferenceKeyImpl.SHARED_FILE_NAME, Context.MODE_PRIVATE);
		editor=sp.edit();
		editor.putLong(SharedPreferenceKeyImpl.MOBILERXTOTALFLOWS, count);
		editor.commit();
	}
	/**
	 * 获取wifi总接收流量
	 * @return
	 */
	public long getwifiRxTotalFlows() {
		sp=context.getSharedPreferences(SharedPreferenceKeyImpl.SHARED_FILE_NAME, Context.MODE_PRIVATE);
		long count=sp.getLong(SharedPreferenceKeyImpl.WIFIRXTOTALFLOWS, 0);
		return count;
	}
	
	/**
	 * 保存wifi总发送流量
	 * @param count
	 */
	public void setwifiTxTotalFlows(long count) {
		sp=context.getSharedPreferences(SharedPreferenceKeyImpl.SHARED_FILE_NAME, Context.MODE_PRIVATE);
		editor=sp.edit();
		editor.putLong(SharedPreferenceKeyImpl.WIFITXTOTALFLOWS, count);
		editor.commit();
	}
	/**
	 * 获取wifi总发送流量
	 * @return
	 */
	public long getwifiTxTotalFlows() {
		sp=context.getSharedPreferences(SharedPreferenceKeyImpl.SHARED_FILE_NAME, Context.MODE_PRIVATE);
		long count=sp.getLong(SharedPreferenceKeyImpl.WIFITXTOTALFLOWS, 0);
		return count;
	}
	
	/**
	 * 保存wifi总接收流量
	 * @param count
	 */
	public void setwifiRxTotalFlows(long count) {
		sp=context.getSharedPreferences(SharedPreferenceKeyImpl.SHARED_FILE_NAME, Context.MODE_PRIVATE);
		editor=sp.edit();
		editor.putLong(SharedPreferenceKeyImpl.WIFIRXTOTALFLOWS, count);
		editor.commit();
	}
	
	/**
	 * 保存流量通知计数次数
	 */
	public void setNum(int num){
		sp=context.getSharedPreferences(SharedPreferenceKeyImpl.SHARED_FILE_NAME,  Context.MODE_PRIVATE);
		editor=sp.edit();
		editor.putInt(SharedPreferenceKeyImpl.NUM, num);
		editor.commit();
	}
	
	/***
	 * 获取流量通知计数次数
	 */
	public int getNum(){
		sp=context.getSharedPreferences(SharedPreferenceKeyImpl.SHARED_FILE_NAME, Context.MODE_PRIVATE);
		int num=sp.getInt(SharedPreferenceKeyImpl.NUM, 0);
		return num;
	}
	
	
	/**
	 * 保存是否防盗    
	 * @param isPreventThelf
	 */
	public void setIsPreventThelf(boolean isPreventThelf){
		sp=context.getSharedPreferences(SharedPreferenceKeyImpl.SHARED_FILE_NAME, Context.MODE_PRIVATE);
		editor=sp.edit();
    	editor.putBoolean(SharedPreferenceKeyImpl.ISPREVENTTHELF, isPreventThelf);
    	editor.commit();
	}
	
	/**
	 * 获取是否防盗    默认为false
	 * @return 
	 */
	public boolean getIsPreventThelf(){
		sp=context.getSharedPreferences(SharedPreferenceKeyImpl.SHARED_FILE_NAME, Context.MODE_PRIVATE);
		return sp.getBoolean(SharedPreferenceKeyImpl.ISPREVENTTHELF, false);
	}
	
	/**
	 *保存防盗密码
	 * @param mode
	 */
	public void setPreventThelfPwd(String pwd){
		sp=context.getSharedPreferences(SharedPreferenceKeyImpl.SHARED_FILE_NAME, Context.MODE_PRIVATE);
		editor=sp.edit();
		editor.putString(SharedPreferenceKeyImpl.PREVENTTHELFPWD, pwd);
		editor.commit();
	}
	
	/**
	 * 获取防盗密码
	 * @return
	 */
	public String getPreventThelfPwd(){
		sp=context.getSharedPreferences(SharedPreferenceKeyImpl.SHARED_FILE_NAME, Context.MODE_PRIVATE);
		String pwd=sp.getString(SharedPreferenceKeyImpl.PREVENTTHELFPWD, null);
		return pwd;
	}
	
	
	
	/**
	 * 保存防盗号码
	 * @param preventThelfNumber
	 */
	public void setPreventThelfNumber(String preventThelfNumber){
		sp=context.getSharedPreferences(SharedPreferenceKeyImpl.SHARED_FILE_NAME, Context.MODE_PRIVATE);
		editor=sp.edit();
    	editor.putString(SharedPreferenceKeyImpl.PREVENT_THELF_NUMBER, preventThelfNumber);
    	editor.commit();
	}
	
	/**
	 * 获取防盗号码       默认为空
	 * @return
	 */
	public String getPreventThelfNumber(){
		sp=context.getSharedPreferences(SharedPreferenceKeyImpl.SHARED_FILE_NAME, Context.MODE_PRIVATE);
		String number=sp.getString(SharedPreferenceKeyImpl.PREVENT_THELF_NUMBER, null);
		return number;
	}
	
}
