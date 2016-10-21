package com.zy.mobilesafe.bean;


/**
 * 联系人类
 * @author Administrator
 *
 */
public class Contact {

	private int contact_id;			//联系人id
	private String contact_name;	//联系人姓名
	private String contact_number;	//联系人号码
	public Contact(int contact_id, String contact_name, String contact_number) {
		super();
		this.contact_id = contact_id;
		this.contact_name = contact_name;
		this.contact_number = contact_number;
	}
	public int getContact_id() {
		return contact_id;
	}
	public void setContact_id(int contact_id) {
		this.contact_id = contact_id;
	}
	public String getContact_name() {
		return contact_name;
	}
	public void setContact_name(String contact_name) {
		this.contact_name = contact_name;
	}
	public String getContact_number() {
		return contact_number;
	}
	public void setContact_number(String contact_number) {
		this.contact_number = contact_number;
	}
	
}
