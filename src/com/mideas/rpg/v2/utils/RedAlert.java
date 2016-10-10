package com.mideas.rpg.v2.utils;

public class RedAlert {

	private String message;
	private float opacity;
	private long timer;
	
	public RedAlert(String message) {
		this.message = message;
		this.opacity = 1;
		this.timer = System.currentTimeMillis();
	}
	
	public String getMessage() {
		return this.message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public float getOpacity() {
		return this.opacity;
	}
	
	public void decreaseOpacity(float opacity) {
		this.opacity = this.opacity+opacity;
	}
	
	public long getTimer() {
		return this.timer;
	}
	
	public void setTimer(long timer) {
		this.timer = timer;
	}
}
