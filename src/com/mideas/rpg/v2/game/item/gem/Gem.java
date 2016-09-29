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
	protected boolean isLoaded;
	private String gemStatsString;
	
	private final static String armorString = "Armor";
	private final static String criticalString = "Critical";
	private final static String manaString = "Mana";
	private final static String staminaString = "Stamina";
	private final static String strengthString = "Strength";
	
	private final static String gemStatsArmor = " armor";
	private final static String gemStatsCritical = " critical";
	private final static String gemStatsMana = " mana";
	private final static String gemStatsStamina = " stamina";
	private final static String gemStatsStrength = " strength";
	
	private final static String blue = "Blue";
	private final static String red = "Red";
	private final static String green = "Green";
	private final static String purple = "Purple";
	private final static String yellow = "Yellow";
	private final static String orange = "Orange";
	private final static String meta = "Meta";
	
	private final static String empty = "";
	private final static String plus = "+";
	private final static String and = " and ";
	
	public Gem(int id) {
		super(id, empty, ItemType.GEM, empty, 0, 0, 1);
		this.id = id;
	}

	public Gem(Gem gem) {
		super(gem.id, gem.sprite_id, gem.itemType, gem.name, gem.quality, gem.sellPrice, 1);
		this.gemStatsString = gem.gemStatsString;
		this.strength = gem.strength;
		this.critical = gem.critical;
		this.stamina = gem.stamina;
		this.armor = gem.armor;
		this.color = gem.color;
		this.mana = gem.mana;
		this.isLoaded = true;
	}
	
	public Gem(int id, String sprite_id, String name, int quality, GemColor color, int strength, int stamina, int armor, int mana, int critical, int sellPrice) {
		super(id, sprite_id, ItemType.GEM, name, quality, sellPrice, 1);
		this.strength = strength;
		this.critical = critical;
		this.stamina = stamina;
		this.armor = armor;
		this.color = color;
		this.mana = mana;
		buildGemStatsString();
		this.isLoaded = true;
	}

	public static String convGemBonusTypeToString(GemBonusType bonus) {
		if(bonus == GemBonusType.ARMOR) {
			return armorString;
		}
		if(bonus == GemBonusType.CRITICAL) {
			return criticalString;
		}
		if(bonus == GemBonusType.MANA) {
			return manaString;
		}
		if(bonus == GemBonusType.STAMINA) {
			return staminaString;
		}
		if(bonus == GemBonusType.STRENGTH) {
			return strengthString;
		}
		return null;
	}
	
	public void buildGemStatsString() {
		boolean stats = false;
		StringBuilder result = new StringBuilder();
		if(this.armor > 0) {
			result.append(plus+this.armor+gemStatsArmor);
			stats = true;
		}
		if(this.critical > 0) {
			if(stats) {
				result.append(and);
			}
			result.append(plus+this.critical+gemStatsCritical);
			stats = true;
		}
		if(this.stamina > 0) {
			if(stats) {
				result.append(and);
			}
			result.append(plus+this.stamina+gemStatsStamina);
			stats = true;
		}
		if(this.strength > 0) {
			if(stats) {
				result.append(and);
			}
			result.append(plus+this.strength+gemStatsStrength);
			stats = true;
		}
		if(this.mana > 0) {
			if(stats) {
				result.append(and);
			}
			result.append(plus+this.mana+gemStatsMana);
			stats = true;
		}
		this.gemStatsString = result.toString();
	}
	
	public String getGemStatsString() {
		return this.gemStatsString;
	}

	public static String convGemColorToString(GemColor color) {
		if(color == GemColor.BLUE) {
			return blue;
		}
		if(color == GemColor.GREEN) {
			return green;
		}
		if(color == GemColor.META) {
			return meta;
		}
		if(color == GemColor.ORANGE) {
			return orange;
		}
		if(color == GemColor.PURPLE) {
			return purple;
		}
		if(color == GemColor.RED) {
			return red;
		}
		if(color == GemColor.YELLOW) {
			return yellow;
		}
		return null;
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
