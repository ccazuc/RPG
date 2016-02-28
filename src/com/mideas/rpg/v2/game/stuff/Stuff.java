package com.mideas.rpg.v2.game.stuff;

public class Stuff implements Cloneable {

	protected StuffType type;
	protected String classe;
	protected String classe2 = "";
	protected String classe3 = "";
	protected String classe4 = "";
	protected int critical;
	protected int sellPrice;
	protected String name;
	protected int strength;
	protected int stamina;
	protected int price;
	protected int armor;
	protected int mana;
	protected String slot;
	private static String[] slotStuff = new String[19];
	protected int id;

	public Stuff(Stuff stuff) {
		this.type = stuff.type;
		this.classe = stuff.classe;
		this.critical = stuff.critical;
		this.sellPrice = stuff.sellPrice;
		this.name = stuff.name;
		this.strength = stuff.strength;
		this.stamina = stuff.stamina;
		this.armor = stuff.armor;
		this.mana = stuff.mana;
		this.id = stuff.id;
	}
	
	public Stuff(int id, String name, int critical, int strength, int stamina, int armor, int mana, String classe, String classe2, String classe3, String classe4, int price, int sellPrice, int expCraftGiven, String slot) {
		this.critical = critical;
		this.sellPrice = sellPrice;
		this.stamina = stamina;
		if(slot.equals("Head")) {
			this.type = StuffType.HEAD;
		}
		if(slot.equals("Necklace")) {
			this.type = StuffType.NECKLACE;
		}
		if(slot.equals("Shoulders")) {
			this.type = StuffType.SHOULDERS;
		}
		if(slot.equals("Chest")) {
			this.type = StuffType.CHEST;
		}
		if(slot.equals("Back")) {
			this.type = StuffType.BACK;
		}
		if(slot.equals("Wrists")) {
			this.type = StuffType.WRISTS;
		}
		if(slot.equals("Gloves")) {
			this.type = StuffType.GLOVES;
		}
		if(slot.equals("Belt")) {
			this.type = StuffType.BELT;
		}
		if(slot.equals("Leggings")) {
			this.type = StuffType.LEGGINGS;
		}
		if(slot.equals("Boots")) {
			this.type = StuffType.BOOTS;
		}
		if(slot.equals("Ring")) {
			this.type = StuffType.RING;
		}
		if(slot.equals("Trinket")) {
			this.type = StuffType.TRINKET;
		}
		if(slot.equals("MainHand")) {
			this.type = StuffType.MAINHAND;
		}
		if(slot.equals("OffHand")) {
			this.type = StuffType.OFFHAND;
		}
		if(slot.equals("Ranged")) {
			this.type = StuffType.RANGED;
		}
		this.slot = slot;
		this.strength = strength;
		this.classe2 = classe2;
		this.classe3 = classe3;
		this.classe4 = classe4;
		this.classe = classe;
		this.price = price;
		this.armor = armor;
		this.mana = mana;
		this.name = name;
		this.id = id;
	}	
	
	public Stuff(int id, String name, int critical, int strength, int stamina, int armor, int mana, String classe, String classe2, String classe3, int price, int sellPrice, int expCraftGiven, String slot) {
		this.critical = critical;
		if(slot.equals("Head")) {
			this.type = StuffType.HEAD;
		}
		if(slot.equals("Necklace")) {
			this.type = StuffType.NECKLACE;
		}
		if(slot.equals("Shoulders")) {
			this.type = StuffType.SHOULDERS;
		}
		if(slot.equals("Chest")) {
			this.type = StuffType.CHEST;
		}
		if(slot.equals("Back")) {
			this.type = StuffType.BACK;
		}
		if(slot.equals("Wrists")) {
			this.type = StuffType.WRISTS;
		}
		if(slot.equals("Gloves")) {
			this.type = StuffType.GLOVES;
		}
		if(slot.equals("Belt")) {
			this.type = StuffType.BELT;
		}
		if(slot.equals("Leggings")) {
			this.type = StuffType.LEGGINGS;
		}
		if(slot.equals("Boots")) {
			this.type = StuffType.BOOTS;
		}
		if(slot.equals("Ring")) {
			this.type = StuffType.RING;
		}
		if(slot.equals("Trinket")) {
			this.type = StuffType.TRINKET;
		}
		if(slot.equals("MainHand")) {
			this.type = StuffType.MAINHAND;
		}
		if(slot.equals("OffHand")) {
			this.type = StuffType.OFFHAND;
		}
		if(slot.equals("Ranged")) {
			this.type = StuffType.RANGED;
		}
		this.stamina = stamina;
		this.strength = strength;
		this.sellPrice = sellPrice;
		this.classe2 = classe2;
		this.classe3 = classe3;
		this.classe = classe;
		this.price = price;
		this.slot = slot;
		this.armor = armor;
		this.mana = mana;
		this.name = name;
		this.id = id;
	}
	
	public Stuff(int id, String name, int critical, int strength, int stamina, int armor, int mana, String classe, String classe2, int price, int sellPrice,  int expCraftGiven, String slot) {
		this.critical = critical;
		if(slot.equals("Head")) {
			this.type = StuffType.HEAD;
		}
		if(slot.equals("Necklace")) {
			this.type = StuffType.NECKLACE;
		}
		if(slot.equals("Shoulders")) {
			this.type = StuffType.SHOULDERS;
		}
		if(slot.equals("Chest")) {
			this.type = StuffType.CHEST;
		}
		if(slot.equals("Back")) {
			this.type = StuffType.BACK;
		}
		if(slot.equals("Wrists")) {
			this.type = StuffType.WRISTS;
		}
		if(slot.equals("Gloves")) {
			this.type = StuffType.GLOVES;
		}
		if(slot.equals("Belt")) {
			this.type = StuffType.BELT;
		}
		if(slot.equals("Leggings")) {
			this.type = StuffType.LEGGINGS;
		}
		if(slot.equals("Boots")) {
			this.type = StuffType.BOOTS;
		}
		if(slot.equals("Ring")) {
			this.type = StuffType.RING;
		}
		if(slot.equals("Trinket")) {
			this.type = StuffType.TRINKET;
		}
		if(slot.equals("MainHand")) {
			this.type = StuffType.MAINHAND;
		}
		if(slot.equals("OffHand")) {
			this.type = StuffType.OFFHAND;
		}
		if(slot.equals("Ranged")) {
			this.type = StuffType.RANGED;
		}
		this.stamina = stamina;
		this.strength = strength;
		this.sellPrice = sellPrice;
		this.classe2 = classe2;
		this.classe = classe;
		this.price = price;
		this.slot = slot;
		this.armor = armor;
		this.mana = mana;
		this.name = name;
		this.id = id;
	}
	
	public Stuff(int id, String name, int critical, int strength, int stamina, int armor, int mana, String classe, int price, int sellPrice,  int expCraftGiven, String slot) {
		this.sellPrice = sellPrice;
		if(slot.equals("Head")) {
			this.type = StuffType.HEAD;
		}
		if(slot.equals("Necklace")) {
			this.type = StuffType.NECKLACE;
		}
		if(slot.equals("Shoulders")) {
			this.type = StuffType.SHOULDERS;
		}
		if(slot.equals("Chest")) {
			this.type = StuffType.CHEST;
		}
		if(slot.equals("Back")) {
			this.type = StuffType.BACK;
		}
		if(slot.equals("Wrists")) {
			this.type = StuffType.WRISTS;
		}
		if(slot.equals("Gloves")) {
			this.type = StuffType.GLOVES;
		}
		if(slot.equals("Belt")) {
			this.type = StuffType.BELT;
		}
		if(slot.equals("Leggings")) {
			this.type = StuffType.LEGGINGS;
		}
		if(slot.equals("Boots")) {
			this.type = StuffType.BOOTS;
		}
		if(slot.equals("Ring")) {
			this.type = StuffType.RING;
		}
		if(slot.equals("Trinket")) {
			this.type = StuffType.TRINKET;
		}
		if(slot.equals("MainHand")) {
			this.type = StuffType.MAINHAND;
		}
		if(slot.equals("OffHand")) {
			this.type = StuffType.OFFHAND;
		}
		if(slot.equals("Ranged")) {
			this.type = StuffType.RANGED;
		}
		this.critical = critical;
		this.strength = strength;
		this.stamina = stamina;
		this.classe = classe;
		this.price = price;
		this.slot = slot;
		this.armor = armor;
		this.mana = mana;
		this.name = name;
		this.id = id;
	}
	
	public int getCritical() {
		return critical;
	}
	
	public int getStrength() {
		return strength;
	}
	
	public int getStamina() {
		return stamina;
	}
	
	public int getArmor() {
		return armor;
	}
	
	public int getMana() {
		return mana;
	}
	
	public int getId() {
		return id;
	}
	
	public String getSlot() {
		return slot;
	}
	
	public StuffType getType() {
		return this.type;
	}
	
	public static void loadSlotStuff() {
		slotStuff[0] = "Head";
		slotStuff[1] = "Necklace";
		slotStuff[2] = "Shoulders";
		slotStuff[3] = "Back";
		slotStuff[4] = "Chest";
		slotStuff[5] = "Head";
		slotStuff[6] = "Head";
		slotStuff[7] = "Wrists";
		slotStuff[8] = "Gloves";
		slotStuff[9] = "Belt";
		slotStuff[10] = "Leggings";
		slotStuff[11] = "Boots";
		slotStuff[12] = "Ring";
		slotStuff[13] = "Ring2";
		slotStuff[14] = "Trinket";
		slotStuff[15] = "Trinket2";
		slotStuff[16] = "MainHand";
		slotStuff[17] = "OffHand";
		slotStuff[18] = "Ranged";
	}
	
	public boolean isHead() {
		return type == StuffType.HEAD;
	}
	
	public boolean isNecklace() {
		return type == StuffType.NECKLACE;
	}
	
	public boolean isShoulders() {
		return type == StuffType.SHOULDERS;
	}
	
	public boolean isChest() {
		return type == StuffType.CHEST;
	}
	
	public boolean isBack() {
		return type == StuffType.BACK;
	}
	
	public boolean isWrists() {
		return type == StuffType.WRISTS;
	}
	
	public boolean isGloves() {
		return type == StuffType.GLOVES;
	}
	
	public boolean isBelt() {
		return type == StuffType.BELT;
	}
	
	public boolean isLeggings() {
		return type == StuffType.LEGGINGS;
	}
	
	public boolean isBoots() {
		return type == StuffType.BOOTS;
	}
	
	public boolean isRing() {
		return type == StuffType.RING;
	}
	
	public boolean isTrinket() {
		return type == StuffType.TRINKET;
	}
	
	public boolean isMainHand() {
		return type == StuffType.MAINHAND;
	}
	
	public boolean isOffHand() {
		return type == StuffType.OFFHAND;
	}
	
	public boolean isRanged() {
		return type == StuffType.RANGED;
	}
	
	public static String getSlot(int i) {
		return slotStuff[i];
	}
	
	public boolean equals(Stuff item) {
		return item != null && item.getId() == id;
	}
	
	public String getStuffName() {
		return name;
	}
	
	public String getClasse() {
		return classe;
	}

	public String getSecondClasse() {
		return classe2;
	}
	
	public String getThirdClasse() {
		return classe3;
	}
	
	public String getFourthClasse() {
		return classe4;
	}
	
	public int getPrice() {
		return price;
	}
	
	public int getSellPrice() {
		return sellPrice;
	}
}
