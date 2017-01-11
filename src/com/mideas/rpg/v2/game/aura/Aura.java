package com.mideas.rpg.v2.game.aura;

import com.mideas.rpg.v2.game.IconsManager;
import com.mideas.rpg.v2.utils.Texture;

public class Aura {

	private final int id;
	private final int duration;
	private final byte defaultNumberStack;
	private final byte maximumStack;
	private final AuraEffect effect1;
	private final int effectValue1;
	private final AuraEffect effect2;
	private final int effectValue2;
	private final AuraEffect effect3;
	private final int effectValue3;
	private final boolean lowDispellable;
	private final boolean highDispellable;
	private final boolean isVisible;
	private final String name;
	private final String sprite_id;
	protected final int spellTriggeredOnFade;
	private final boolean isBuff;
	private final int tickRate;
	private final boolean isStackable;
	private final boolean isMagical;
	private final boolean dupliFromDifferentSource;
	private final Texture texture;
	
	public Aura(int id, String name, String sprite_id, int spellTriggeredOnFade, int duration, boolean isStackable, byte defaultNumberStack, byte maximumStack, int tickRate, boolean lowDispellable, boolean highDispellable, AuraEffect effect1, int effectValue1, AuraEffect effect2, int effectValue2, AuraEffect effect3, int effectValue3, boolean isBuff, boolean isVisible, boolean isMagical, boolean dupliFromDifferentSource) {
		this.id = id;
		this.name = name;
		this.sprite_id = sprite_id;
		this.duration = duration;
		this.defaultNumberStack = defaultNumberStack;
		this.maximumStack = maximumStack;
		this.effect1 = effect1;
		this.effectValue1 = effectValue1;
		this.effect2 = effect2;
		this.effectValue2 = effectValue2;
		this.effect3 = effect3;
		this.effectValue3 = effectValue3;
		this.highDispellable = highDispellable;
		this.lowDispellable = lowDispellable;
		this.isVisible = isVisible;
		this.spellTriggeredOnFade = spellTriggeredOnFade;
		this.isBuff = isBuff;
		this.tickRate = tickRate;
		this.isStackable = isStackable;
		this.isMagical = isMagical;
		this.dupliFromDifferentSource = dupliFromDifferentSource;
		this.texture = IconsManager.getSprite37(sprite_id);
	}
	
	public int getId() {
		return this.id;
	}
	
	public String getName() {
		return this.name;
	}
	
	public Texture getTexture() {
		return this.texture;
	}
	
	public int getDuration() {
		return this.duration;
	}
	
	public byte getDefaultNumberStack() {
		return this.defaultNumberStack;
	}
	
	public boolean isHighDispellable() {
		return this.highDispellable;
	}
	
	public boolean isLowDispellable() {
		return this.lowDispellable;
	}
	
	public boolean isVisible() {
		return this.isVisible;
	}
	
	public boolean isStackable() {
		return this.isStackable;
	}
	
	public boolean isMagical() {
		return this.isMagical;
	}
	
	public boolean isBuff() {
		return this.isBuff;
	}
}
