package com.mideas.rpg.v2.game;

public class Unit {

	private int level;
	private String name;
	private int maxStamina;
	private int stamina;
	private int maxMana;
	private int mana;
	
	public Unit(int stamina, int maxStamina, int mana, int maxMana, int level, String name) {
		this.stamina = stamina;
		this.maxStamina = maxStamina;
		this.mana = mana;
		this.maxMana = maxMana;
		this.level = level;
		this.name = name;
	}
	
	public int getStamina() {
		return this.stamina;
	}
	
	public void setStamina(int stamina) {
		this.stamina = stamina;
	}
	
	public int getMaxStamina() {
		return this.maxStamina;
	}
	
	public void setMaxStamina(int maxStamina) {
		this.maxStamina = maxStamina;
	}
	
	public int getMana() {
		return this.mana;
	}
	
	public void setMana(int mana) {
		this.mana = mana;
	}
	
	public int getMaxMana() {
		return this.maxMana;
	}
	
	public void setMaxMana(int maxMana) {
		this.maxMana = maxMana;
	}
	
	public int getLevel() {
		return this.level;
	}
	
	public void setLevel(int level) {
		this.level = level;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}