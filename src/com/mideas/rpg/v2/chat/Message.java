package com.mideas.rpg.v2.chat;

import java.util.Calendar;

import org.newdawn.slick.Color;


public class Message {

	private String message;
	private boolean displayHour;
	private int hour;
	private int minute;
	private int second;
	private Color color;
	private String author;
	private String authorText = "";
	private MessageType type;
	
	public Message(String message, boolean displayHour, MessageType type) {
		long time = System.currentTimeMillis();
		this.message = message;
		this.hour = getMessageHour(time);
		this.minute = getMessageMinute(time);
		this.second = getMessageSecond(time);
		this.color = type.getColor();
		this.displayHour = displayHour;
		this.type = type;
	}
	
	public Message(String message, boolean displayHour, MessageType type, Color color) {
		long time = System.currentTimeMillis();
		this.message = message;
		this.hour = getMessageHour(time);
		this.minute = getMessageMinute(time);
		this.second = getMessageSecond(time);
		this.color = color;
		this.displayHour = displayHour;
		this.type = type;
	}
	
	public Message(String message, String author, boolean displayHour, MessageType type) {
		long time = System.currentTimeMillis();
		this.message = message;
		this.hour = getMessageHour(time);
		this.minute = getMessageMinute(time);
		this.second = getMessageSecond(time);
		this.color = type.getColor();
		this.type = type;
		this.displayHour = displayHour;
		this.author = author;
		if(type == MessageType.SAY || type == MessageType.YELL) {
			this.authorText = "["+author+"]"+type.getChatDisplay();
		}
		else if(type == MessageType.WHISPER) {
			this.authorText = "To "+author+" : ";
		}
		else {
			this.authorText = type.getChatDisplay()+"["+author+"] : ";
		}
	}
	
	public Message(String message, String author, boolean displayHour, MessageType type, boolean isTarget) {
		long time = System.currentTimeMillis();
		this.message = message;
		this.hour = getMessageHour(time);
		this.minute = getMessageMinute(time);
		this.second = getMessageSecond(time);
		this.color = type.getColor();
		this.type = type;
		this.displayHour = displayHour;
		this.author = author;
		if(isTarget) {
			this.authorText = "["+this.author+"] whisper : ";
		}
		else {
			this.authorText = "To ["+this.author+"] : ";
		}
	}
	
	public MessageType getType() {
		return this.type;
	}
	
	public String getAuthorText() {
		return this.authorText;
	}
	
	public String getAuthor() {
		return this.author;
	}
	
	public boolean getDisplayHour() {
		return this.displayHour;
	}
	
	public String getMessage() {
		return this.message;
	}
	
	public int getHour() {
		return this.hour;
	}
	
	public int getMinute() {
		return this.minute;
	}
	
	public int getSecond() {
		return this.second;
	}
	
	public Color getColor() {
		return this.color;
	}

	private static int getMessageHour(long time) {
		int hour;
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(time);
		hour = (byte)calendar.get(Calendar.HOUR_OF_DAY);
		return hour;
	}
	
	private static int getMessageMinute(long time) {
		int minute;
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(time);
		minute = (byte)calendar.get(Calendar.MINUTE);
		return minute;
	}
	
	private static int getMessageSecond(long time) {
		int second;
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(time);
		second = (byte)calendar.get(Calendar.SECOND);
		return second;
	}
}
