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
	private boolean isTarget;
	private boolean isGM;
	private final static String gmLogoSpace = "            ";
	
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
	
	public Message(String message, String author, String authorText, boolean displayHour, MessageType type, boolean isGM) { //used for self message
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
		this.isGM = isGM;
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
		if(type == MessageType.ANNOUNCE || type == MessageType.GM_ANNOUNCE) {
			if(this.author == null) {
				this.authorText = type.getChatDisplay();
			}
			else {
				this.authorText = '['+this.author+"] "+type.getChatDisplay();
			}
		}
	}
	
	public Message(String message, String author, boolean displayHour, MessageType type, boolean isGM) { //used for all except whispers
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
		this.isGM = isGM;
		this.opacity = 1;
		if(type == MessageType.SAY || type == MessageType.YELL) {
			if(isGM) {
				this.authorText = gmLogoSpace+"["+author+"]"+type.getChatDisplay();
			}
			else {
				this.authorText = "["+author+"]"+type.getChatDisplay();
			}
		}
		else if(type != MessageType.SELF) {
			if(isGM) {
				this.authorText = type.getChatDisplay()+gmLogoSpace+"["+author+"] : ";
			}
		}
		else if(isGM) {
			this.authorText = gmLogoSpace+"["+author+']';
		}
		else {
			this.author = '['+author+']';
		}
		if(isGM) {
			this.author = null;
		}
	}
	
	public Message(String message, String author, boolean displayHour, MessageType type, Color color, boolean isGM) { //used for all except whispers with different color
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
		this.isGM = isGM;
		this.opacity = 1;
		if(type == MessageType.ANNOUNCE || type == MessageType.GM_ANNOUNCE) {
			if(this.author == null) {
				this.authorText = type.getChatDisplay();
			}
			else {
				this.authorText = '['+this.author+"] "+type.getChatDisplay();
			}
			this.isGM = false;
		}
		else if(type == MessageType.SAY || type == MessageType.YELL) {
			if(isGM) {
				this.authorText = gmLogoSpace+"["+author+"]"+type.getChatDisplay();
			}
			else {
				this.authorText = "["+author+"]"+type.getChatDisplay();
			}
		}
		else if(type != MessageType.SELF) {
			if(isGM) {
				this.authorText = type.getChatDisplay()+gmLogoSpace+"["+author+"] : ";
			}
		}
		else if(isGM) {
			this.authorText = gmLogoSpace+"["+author+']';
		}
		else {
			this.author = '['+author+']';
		}
		if(isGM) {
			this.author = null;
		}
	}
	
	public Message(String message, String author, boolean displayHour, MessageType type, boolean isTarget, boolean isGM) { //used for whispers
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
		this.opacity = 1;
		this.isGM = isGM;
		this.isTarget = isTarget;
		if(isTarget) {
			if(isGM) {
				this.authorText = gmLogoSpace+"["+this.author+"] whispers : ";
			}
			else {
				this.authorText = '['+this.author+"] whispers : ";
			}
		}
		else {
			if(isGM) {
				this.authorText = "To"+gmLogoSpace+"["+this.author+"] : ";
			}
			else {
				this.authorText = "To ["+this.author+"] : ";
			}
		}
	}
	
	public boolean isGM() {
		return this.isGM;
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
	
	public boolean isTarget() {
		return this.isTarget;
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
