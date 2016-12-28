package com.mideas.rpg.v2.chat;

import java.util.Calendar;

import com.mideas.rpg.v2.utils.Color;

public class Message {

	private String drawMessage;
	private String message;
	private boolean displayHour;
	private int hour;
	private int minute;
	private int second;
	private Color color;
	private String author;
	private String authorText = "";
	private MessageType type;
	private float opacity;
	private long timer;
	private long lastSeenTimer;
	private int numberLine;
	
	public Message(String message, boolean displayHour, MessageType type) { //used for self message
		this.timer = System.currentTimeMillis();
		this.lastSeenTimer = this.timer;
		this.message = message;
		this.hour = getMessageHour(this.timer);
		this.minute = getMessageMinute(this.timer);
		this.second = getMessageSecond(this.timer);
		this.color = type.getColor();
		this.displayHour = displayHour;
		this.type = type;
		this.opacity = 1;
	}
	
	public Message(String message, String author, String authorText, boolean displayHour, MessageType type) { //used for self message
		this.timer = System.currentTimeMillis();
		this.lastSeenTimer = this.timer;
		this.message = message;
		this.hour = getMessageHour(this.timer);
		this.minute = getMessageMinute(this.timer);
		this.second = getMessageSecond(this.timer);
		this.color = type.getColor();
		this.displayHour = displayHour;
		this.type = type;
		this.opacity = 1;
		this.author = author;
		this.authorText = authorText;
	}
	
	public Message(String message, boolean displayHour, MessageType type, Color color) { //used for self message with different color
		this.timer = System.currentTimeMillis();
		this.lastSeenTimer = this.timer;
		this.message = message;
		this.hour = getMessageHour(this.timer);
		this.minute = getMessageMinute(this.timer);
		this.second = getMessageSecond(this.timer);
		this.color = color;
		this.displayHour = displayHour;
		this.type = type;
		this.opacity = 1;
	}
	
	public Message(String message, String author, boolean displayHour, MessageType type) { //used for all except whispers
		this.timer = System.currentTimeMillis();
		this.lastSeenTimer = this.timer;
		this.message = message;
		this.hour = getMessageHour(this.timer);
		this.minute = getMessageMinute(this.timer);
		this.second = getMessageSecond(this.timer);
		this.color = type.getColor();
		this.type = type;
		this.displayHour = displayHour;
		this.author = author;
		if(type == MessageType.SAY || type == MessageType.YELL) {
			this.authorText = "["+author+"]"+type.getChatDisplay();
		}
		else if(type != MessageType.SELF) {
			this.authorText = type.getChatDisplay()+"["+author+"] : ";
		}
		else {
			this.authorText = '['+author+']';
		}
		this.opacity = 1;
	}
	
	public Message(String message, String author, boolean displayHour, MessageType type, Color color) { //used for all except whispers
		this.timer = System.currentTimeMillis();
		this.lastSeenTimer = this.timer;
		this.message = message;
		this.hour = getMessageHour(this.timer);
		this.minute = getMessageMinute(this.timer);
		this.second = getMessageSecond(this.timer);
		this.color = color;
		this.type = type;
		this.displayHour = displayHour;
		this.author = author;
		if(type == MessageType.SAY || type == MessageType.YELL) {
			this.authorText = "["+author+"]"+type.getChatDisplay();
		}
		else if(type != MessageType.SELF) {
			this.authorText = type.getChatDisplay()+"["+author+"] : ";
		}
		else {
			this.authorText = '['+author+']';
		}
		this.opacity = 1;
	}
	
	public Message(String message, String author, boolean displayHour, MessageType type, boolean isTarget) { //used for whispers
		this.timer = System.currentTimeMillis();
		this.lastSeenTimer = this.timer;
		this.message = message;
		this.hour = getMessageHour(this.timer);
		this.minute = getMessageMinute(this.timer);
		this.second = getMessageSecond(this.timer);
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
		this.opacity = 1;
	}
	
	public int getNumberLine() {
		return this.numberLine;
	}
	
	public void setNumberLine(int number) {
		this.numberLine = number;
	}
	
	public float getOpacity() {
		return this.opacity;
	}
	
	public void decreaseOpacity(float opacity) {
		this.opacity = this.opacity+opacity;
	}
	
	public void setOpacity(float opacity) {
		this.opacity = opacity;
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
	
	public void setLastSeenTimer(long timer) {
		this.lastSeenTimer = timer;
	}
	
	public long lastSeenTimer() {
		return this.lastSeenTimer;
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
	
	public String getDrawMessage() {
		return this.drawMessage;
	}
	
	public void setDrawMessage(String message) {
		this.drawMessage = message;
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
