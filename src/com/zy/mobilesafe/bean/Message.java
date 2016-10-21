package com.zy.mobilesafe.bean;


/**
 * 短信类
 * @author Administrator
 *
 */
public class Message {
	private String name;		//名称
	private String number;		//号码
	private String content;		//内容
	private String time;		//时间
	public Message() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Message(String name, String number, String content, String time) {
		super();
		this.name = name;
		this.number = number;
		this.content = content;
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
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	@Override
	public String toString() {
		return "MyMessage [name=" + name + ", number=" + number + ", content="
				+ content + ", time=" + time + "]";
	}
}
