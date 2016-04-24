package com.mideas.rpg.v2.game.item.gem;

import com.mideas.rpg.v2.game.item.Item;
import com.mideas.rpg.v2.game.item.ItemType;

public class Gem extends Item {
	
	protected int strength;
	protected int stamina;
	protected int mana;
	protected int armor;
	protected int critical;
	protected GemColor color;

	public Gem(Gem gem) {
		super(gem.id, gem.sprite_id, gem.itemType, gem.name, gem.quality, gem.sellPrice, 1);
		this.strength = gem.strength;
		this.stamina = gem.stamina;
		this.armor = gem.armor;
		this.critical = gem.critical;
		this.mana = gem.mana;
		this.color = gem.color;
	}
	
	public Gem(int id, String sprite_id, String name, int quality, GemColor color, int strength, int stamina, int armor, int mana, int critical, int sellPrice) {
		super(id, sprite_id, ItemType.GEM, name, quality, sellPrice, 1);
		this.strength = strength;
		this.stamina = stamina;
		this.mana = mana;
		this.armor = armor;
		this.critical = critical;
		this.color = color;
	}
	
	public int getQuality() {
		return this.quality;
	}
	
	public int getStrength() {
		return this.strength;
	}
	
	public int getStamina() {
		return this.stamina;
	}
	
	public int getArmor() {
		return this.armor;
	}
	
	public int getCritical() {
		return this.critical;
	}
	
	public GemColor getColor() {
		return this.color;
	}
}
