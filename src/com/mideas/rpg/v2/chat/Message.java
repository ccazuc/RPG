package com.mideas.rpg.v2.chat;

import java.util.Calendar;

import com.mideas.rpg.v2.chat.channel.ChannelMgr;
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
	private int channelHeaderWidth;
	private boolean tabOnNewLine;
	private final static String gmLogoSpace = "            ";
	private final static Calendar calendar = Calendar.getInstance();
	private final static StringBuilder builder = new StringBuilder();
	
	public Message(String message, boolean displayHour, MessageType type, boolean tabOnNewLine) { //used for self message
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
		this.tabOnNewLine = tabOnNewLine;
	}
	
	public Message(String message, String channelName, String author, boolean displayHour, boolean isGM, boolean isMessage, boolean tabOnNewLine) {
		this.timer = System.currentTimeMillis();
		this.lastSeenTimer = this.timer;
		this.message = message;
		this.hour = getMessageHour(this.timer);
		this.minute = getMessageMinute(this.timer);
		this.second = getMessageSecond(this.timer);
		this.color = MessageType.CHANNEL.getColor();
		this.displayHour = displayHour;
		this.opacity = 1;
		this.author = author;
		this.type = MessageType.CHANNEL;
		this.isGM = isGM;
		if(this.author.length() == 0) { //Debug
			this.authorText = ChannelMgr.getChannelHeader(channelName).concat(" ");
		}
		else if(isGM) {
			builder.setLength(0);
			this.authorText = builder.append(ChannelMgr.getChannelHeader(channelName)).append(gmLogoSpace).append("[").append(author).append(']').toString();
			this.channelHeaderWidth = ChannelMgr.getChannelHeaderWidth(channelName);
			this.author = null;
		}
		else {
			builder.setLength(0);
			this.authorText = builder.append(ChannelMgr.getChannelHeader(channelName)).append(" [").append(author).append(']').toString();
		}
		if(isMessage) {
			this.authorText+= " : ";
		}
		this.tabOnNewLine = tabOnNewLine;
		/*if(this.author.length() == 0) {
			this.authorText = '['+StringUtils.value[ChannelMgr.getChannelIndex(channelName)]+". "+channelName+']';
		}
		else if(isGM) {
			String tmp = '['+StringUtils.value[ChannelMgr.getChannelIndex(channelName)]+". "+channelName+']';
			this.authorText = tmp+gmLogoSpace+" ["+author+"] : ";
			this.channelHeaderWidth = FontManager.chat.getWidth(tmp);
			this.author = null;
		}
		else {
			this.authorText = '['+StringUtils.value[ChannelMgr.getChannelIndex(channelName)]+". "+channelName+"] ["+author+"] : ";
		}*/
		
	}
	
	public Message(String message, String author, String authorText, boolean displayHour, MessageType type, boolean isGM, boolean tabOnNewLine) { //used for self message
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
		this.tabOnNewLine = tabOnNewLine;
	}
	
	public Message(String message, boolean displayHour, MessageType type, Color color, boolean tabOnNewLine) { //used for self message with different color
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
		this.tabOnNewLine = tabOnNewLine;
		if(type == MessageType.ANNOUNCE || type == MessageType.GM_ANNOUNCE) {
			if(this.author == null) {
				this.authorText = type.getChatDisplay();
			}
			else {
				builder.setLength(0);
				this.authorText = builder.append('[').append(this.author).append("] ").append(type.getChatDisplay()).toString();
			}
		}
		this.tabOnNewLine = tabOnNewLine;
	}
	
	public Message(String message, String author, boolean displayHour, MessageType type, boolean isGM, boolean tabOnNewLine) { //used for all except whispers
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
		builder.setLength(0);
		this.tabOnNewLine = tabOnNewLine;
		if(type == MessageType.SAY || type == MessageType.YELL) {
			if(isGM)
				this.authorText = builder.append(gmLogoSpace).append("[").append(author).append("]").append(type.getChatDisplay()).toString();
			else
				this.authorText = builder.append("[").append(author).append("]").append(type.getChatDisplay()).toString();
		}
		else if(type != MessageType.SELF) {
			if(isGM)
				this.authorText = builder.append(type.getChatDisplay()).append(gmLogoSpace).append("[").append(author).append("] : ").toString();
			else
				this.authorText = builder.append(type.getChatDisplay()).append("[").append(author).append("] : ").toString();
		}
		else if(isGM)
			this.authorText = builder.append(gmLogoSpace).append("[").append(author).append(']').toString();
		else
			this.authorText = builder.append('[').append(author).append(']').toString();
		if(isGM)
			this.author = null;
	}
	
	public Message(String message, String author, boolean displayHour, MessageType type, Color color, boolean isGM, boolean tabOnNewLine) { //used for all except whispers with different color
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
		builder.setLength(0);
		if(type == MessageType.ANNOUNCE || type == MessageType.GM_ANNOUNCE) {
			if(this.author == null)
				this.authorText = type.getChatDisplay();
			else
				this.authorText = builder.append('[').append(this.author).append("] ").append(type.getChatDisplay()).toString();
			this.isGM = false;
		}
		else if(type == MessageType.SAY || type == MessageType.YELL) {
			if(isGM)
				this.authorText = builder.append(gmLogoSpace).append("[").append(author).append("]").append(type.getChatDisplay()).toString();
			else
				this.authorText = builder.append("[").append(author).append("]").append(type.getChatDisplay()).toString();
		}
		else if(type != MessageType.SELF) {
			if(isGM)
				this.authorText = builder.append(type.getChatDisplay()).append(gmLogoSpace).append("[").append(author).append("] : ").toString();
		}
		else if(isGM)
			this.authorText = builder.append(gmLogoSpace).append("[").append(author).append(']').toString();
		else
			this.authorText = builder.append('[').append(author).append(']').toString();
		if(isGM)
			this.author = null;
		this.tabOnNewLine = tabOnNewLine;
	}
	
	public Message(String message, String author, boolean displayHour, MessageType type, boolean isTarget, boolean isGM, boolean tabOnNewLine) { //used for whispers
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
		builder.setLength(0);
		if(isTarget) {
			if(isGM)
				this.authorText = builder.append(gmLogoSpace).append("[").append(this.author).append("] whispers : ").toString();
			else
				this.authorText = builder.append('[').append(this.author).append("] whispers : ").toString();
		}
		else {
			if(isGM)
				this.authorText = builder.append("To").append(gmLogoSpace).append("[").append(this.author).append("] : ").toString();
			else
				this.authorText = builder.append("To [").append(this.author).append("] : ").toString();
		}
		this.tabOnNewLine = tabOnNewLine;
	}
	
	public boolean getTabOnNewLine()
	{
		return (this.tabOnNewLine);
	}
	
	public boolean isGM() {
		return this.isGM;
	}
	
	public int getNumberLine() {
		return this.numberLine;
	}
	
	public int getChannelHeaderWidth() {
		return this.channelHeaderWidth;
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
		calendar.setTimeInMillis(time);
		int hour = (byte)calendar.get(Calendar.HOUR_OF_DAY);
		return hour;
	}
	
	private static int getMessageMinute(long time) {
		calendar.setTimeInMillis(time);
		int minute = (byte)calendar.get(Calendar.MINUTE);
		return minute;
	}
	
	private static int getMessageSecond(long time) {
		calendar.setTimeInMillis(time);
		int second = (byte)calendar.get(Calendar.SECOND);
		return second;
	}
}
