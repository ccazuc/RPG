package com.mideas.rpg.v2.game;

public class Unit {

	protected boolean hasManaChanged = true;
	protected boolean hasHpChanged = true;
	protected int level;
	protected String name;
	protected String classString = "";
	protected int maxStamina;
	protected int stamina;
	protected int maxMana;
	protected int mana;
	protected int id;
	protected String healthText = "";
	protected String manaText = "";
	
	public Unit(int id, int stamina, int maxStamina, int mana, int maxMana, int level, String name) {
		this.stamina = stamina;
		this.maxStamina = maxStamina;
		this.mana = mana;
		this.id = id;
		this.maxMana = maxMana;
		this.level = level;
		this.name = name;
	}
	
	public String getHealthText() {
		return this.healthText;
	}
	
	public void setHealthText(String text) {
		this.healthText = text;
	}
	
	public String getManaText() {
		return this.manaText;
	}
	
	public String getClasseString() {
		return this.classString;
	}
	
	public void setManaText(String text) {
		this.manaText = text;
	}
	
	public boolean getHasHpChanged() {
		return this.hasHpChanged;
	}
	
	public void setHasHpChanged(boolean we) {
		this.hasHpChanged = we;
	}
	
	public boolean getHasManaChanged() {
		return this.hasManaChanged;
	}
	
	public void setHasManaChanged(boolean we) {
		this.hasManaChanged = we;
	}
	
	public int getStamina() {
		return this.stamina;
	}
	
	public void setStamina(double d) {
		this.stamina = (int)Math.max(d, 0);
		this.hasHpChanged = true;
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
		this.mana = Math.max(mana, 0);
		this.hasManaChanged = true;
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
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return this.id;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}