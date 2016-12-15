package com.mideas.rpg.v2.game.task;

import com.mideas.rpg.v2.Mideas;

public class Task {

	private int frequence;
	private long lastTick;
	
	public Task(int frequence) {
		this.frequence = frequence;
		TaskManager.addTask(this);
	}
	
	public final void event() {
		if(condition() && Mideas.getLoopTickTimer()-getLastTickTimer() >= this.frequence) {
			action();
			this.lastTick = Mideas.getLoopTickTimer();
		}
	}
	
	public long getLastTickTimer() {
		return this.lastTick;
	}
	
	public void setLastTickTimer(long timer) {
		this.lastTick = timer;
	}
	
	public boolean condition() {
		return true;
	}
	
	public void action() {}
}
