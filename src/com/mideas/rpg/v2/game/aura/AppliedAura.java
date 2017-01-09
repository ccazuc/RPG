package com.mideas.rpg.v2.game.aura;

import com.mideas.rpg.v2.Mideas;

public class AppliedAura {

	private final Aura aura;
	private long endTimer;
	private byte numberStack;
	private long lastTick;
	
	public AppliedAura(Aura aura) {
		this.aura = aura;
		this.endTimer = Mideas.getLoopTickTimer()+aura.getDuration();
		this.numberStack = aura.getDefaultNumberStack();
	}
	
	public AppliedAura(Aura aura, long endTimer, byte numberStack) {
		this.aura = aura;
		this.endTimer = endTimer;
		this.numberStack = numberStack;
	}
	
	public Aura getAura() {
		return this.aura;
	}
	
	public long getEndTimer() {
		return this.endTimer;
	}
	
	public int getNumberStack() {
		return this.numberStack;
	}
	
	public void setNumberStack(byte number) {
		this.numberStack = number;
	}
}
