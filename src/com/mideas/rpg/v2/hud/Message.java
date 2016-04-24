package com.mideas.rpg.v2.hud;

import org.newdawn.slick.Color;

public class Message {

	private String message;
	private boolean displayHour;
	private int hour;
	private int minute;
	private int second;
	private Color color;
	
	public Message(String message, boolean displayHour, int hour, int minute, int second, Color color) {
		this.message = message;
		this.hour = hour;
		this.minute = minute;
		this.second = second;
		this.color = color;
		this.displayHour = displayHour;
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
}
