package com.zy.mobilesafe.bean;


/**
 * ������
 * @author Administrator
 *
 */
public class Message {
	private String name;		//����
	private String number;		//����
	private String content;		//����
	private String time;		//ʱ��
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
