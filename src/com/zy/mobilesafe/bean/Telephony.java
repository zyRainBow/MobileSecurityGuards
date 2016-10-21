package com.zy.mobilesafe.bean;


/**
 * µÁª∞¿‡
 * @author Administrator
 *
 */
public class Telephony {

	private String name;
	private String number;
	private String time;
	public Telephony() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Telephony(String name, String number, String time) {
		super();
		this.name = name;
		this.number = number;
		this.time = time;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	@Override
	public String toString() {
		return "MyTelphone [name=" + name + ", number=" + number + ", time="
				+ time + "]";
	}
	
}
