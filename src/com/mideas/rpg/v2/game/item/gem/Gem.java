package com.mideas.rpg.v2.game.item.gem;

import com.mideas.rpg.v2.game.item.Item;
import com.mideas.rpg.v2.game.item.ItemType;

public class Gem extends Item {
	
	private GemColor color;
	private GemBonusType stat1Type;
	private int stat1Value;
	private GemBonusType stat2Type;
	private int stat2Value;
	private GemBonusType stat3Type;
	private int stat3Value;
	private String gemStatsString;
	private String gemSlotString;
	
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
		super(id, empty, ItemType.GEM, empty, (byte)1, (byte)0, 0, 1, 1);
		this.isLoaded = false;
	}

	public Gem(Gem gem) {
		super(gem.id, gem.sprite_id, ItemType.GEM, gem.name, gem.level, gem.quality, gem.sellPrice, 1, 1);
		this.gemStatsString = gem.gemStatsString;
		this.stat1Type = gem.stat1Type;
		this.stat1Value = gem.stat1Value;
		this.stat2Type = gem.stat2Type;
		this.stat2Value = gem.stat2Value;
		this.stat3Type = gem.stat3Type;
		this.stat3Value = gem.stat3Value;
		this.color = gem.color;
		buildGemStatsString();
		buildGemColorString();
		this.isLoaded = true;
	}
	
	public Gem(int id, String sprite_id, String name, byte quality, GemColor color, int sellPrice, GemBonusType stat1Type, int stat1Value, GemBonusType stat2Type, int stat2Value, GemBonusType stat3Type, int stat3Value) {
		super(id, sprite_id, ItemType.GEM, name, (byte)1, quality, sellPrice, 1, 1);
		this.color = color;
		this.stat1Type = stat1Type;
		this.stat1Value = stat1Value;
		this.stat2Type = stat2Type;
		this.stat2Value = stat2Value;
		this.stat3Type = stat3Type;
		this.stat3Value = stat3Value;
		buildGemStatsString();
		buildGemColorString();
		this.isLoaded = true;
	}
	
	public void updateGem(Gem gem) {
		this.id = gem.id;
		this.sprite_id = gem.sprite_id;
		this.name = gem.name;
		this.quality = gem.quality;
		this.sellPrice = gem.sellPrice;
		this.color = gem.color;
		this.gemStatsString = gem.gemStatsString;
		this.stat1Type = gem.stat1Type;
		this.stat1Value = gem.stat1Value;
		this.stat2Type = gem.stat2Type;
		this.stat2Value = gem.stat2Value;
		this.stat3Type = gem.stat3Type;
		this.stat3Value = gem.stat3Value;
		buildGemStatsString();
		buildGemColorString();
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
	
	public GemBonusType getBonus1Type() {
		return this.stat1Type;
	}
	
	public int getBonus1Value() {
		return this.stat1Value;
	}
	
	public GemBonusType getBonus2Type() {
		return this.stat2Type;
	}
	
	public int getBonus2Value() {
		return this.stat2Value;
	}
	
	public GemBonusType getBonus3Type() {
		return this.stat3Type;
	}
	
	public int getBonus3Value() {
		return this.stat3Value;
	}
	
	public String getGemSlotString() {
		return this.gemSlotString;
	}
	
	private void buildGemColorString() {
		this.gemSlotString = "Correspond to a "+getCorrespondingColor(this.color);
	}
	
	private static String getCorrespondingColor(GemColor type) {
		if(type == GemColor.BLUE) {
			return "blue gem.";
		}
		if(type == GemColor.GREEN) {
			return "yellow or blue gem.";
		}
		if(type == GemColor.ORANGE) {
			return "red or yellow gem.";
		}
		if(type == GemColor.PURPLE) {
			return "red or blue gem.";
		}
		if(type == GemColor.RED) {
			return "red gem.";
		}
		if(type == GemColor.YELLOW) {
			return "yellow gem.";
		}
		return "";
	}
	
	private void buildGemStatsString() {
		boolean stats = false;
		StringBuilder result = new StringBuilder();
		/*if(this.armor > 0) {
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
		}*/
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
	
	public GemColor getColor() {
		return this.color;
	}
}
