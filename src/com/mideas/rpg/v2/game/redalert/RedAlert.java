package com.mideas.rpg.v2.game.redalert;

import com.mideas.rpg.v2.FontManager;
import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.hud.RedAlertFrame;

public class RedAlert {

	private String message;
	private int messageWidth;
	private float opacity;
	private long timer;
	
	public RedAlert(String message) {
		this.message = message;
		this.messageWidth = RedAlertFrame.FONT.getWidth(this.message);
		this.opacity = 1;
		this.timer = Mideas.getLoopTickTimer();
	}
	
	public String getMessage() {
		return this.message;
	}
	
	public int getMessageWidth() {
		return this.messageWidth;
	}
	
	public void setMessage(String message) {
		this.message = message;
		this.messageWidth = FontManager.get("ARIALN", 21).getWidth(this.message);
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
