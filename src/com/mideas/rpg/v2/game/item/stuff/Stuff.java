package com.mideas.rpg.v2.game.item.stuff;

import com.mideas.rpg.v2.utils.Texture;
import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.Sprites;
import com.mideas.rpg.v2.game.classes.Wear;
import com.mideas.rpg.v2.game.item.Item;
import com.mideas.rpg.v2.game.item.ItemType;
import com.mideas.rpg.v2.game.item.gem.Gem;
import com.mideas.rpg.v2.game.item.gem.GemBonusType;
import com.mideas.rpg.v2.game.item.gem.GemColor;
import com.mideas.rpg.v2.game.item.weapon.WeaponSlot;
import com.mideas.rpg.v2.game.item.weapon.WeaponType;
import com.mideas.rpg.v2.game.unit.ClassType;

public class Stuff extends Item {

	private GemColor[] gemColor = new GemColor[3];
	private Gem[] equippedGem = new Gem[3];
	private boolean gemBonusActivated;
	private GemBonusType gemBonusType;
	private String socketBonusString;
	private ClassType[] classType;
	private WeaponType weaponType;
	private WeaponSlot weaponSlot;
	private String classRequirement;
	private String sellPriceString;
	private String strengthString;
	private String criticalString;
	private String staminaString;
	private String armorString;
	private String levelString;
	private String gem1String;
	private String gem2String;
	private String gem3String;
	private String manaString;
	private int numberGemSlot;
	private int gemBonusValue;
	private StuffType type;
	private int critical;
	private int strength;
	private int stamina;
	private Wear wear;
	private int price;
	private int armor;
	private int mana;
	
	private final static String head = "Head";
	private final static String necklace = "Necklace";
	private final static String shoulders = "Shoulders";
	private final static String back = "Back";
	private final static String chest = "Chest";
	private final static String wrists = "Wrists";
	private final static String gloves = "Gloves";
	private final static String belt = "Belt";
	private final static String leggings = "Leggings";
	private final static String boots = "Boots";
	private final static String ring = "Ring";
	private final static String trinket = "Trinket";
	private final static String mainHand = "MainHand";
	private final static String offHand = "OffHand";
	private final static String ranged = "Ranged";
	
	private final static String bow = "Bow";
	private final static String crossbow = "Crossbow";
	private final static String dagger = "Dagger";
	private final static String fistWeapon = "Fist Weapon";
	private final static String gun = "Gun";
	private final static String oneHandedAxe = "One Handed Axe";
	private final static String oneHandedMace = "One Handed Mace";
	private final static String oneHandedSword = "One Handed Sword";
	private final static String polearm = "Polearm";
	private final static String staff = "Staff";
	private final static String thrown = "Thrown";
	private final static String twoHandedAxe = "Two Handed Axe";
	private final static String twoHandedMace = "Two Handed Mace";
	private final static String twoHandedSword = "Two Handed Sword";
	private final static String wand = "Wand";
	
	private final static String cloth = "Cloth";
	private final static String leather = "Leather";
	private final static String mail = "Mail";
	private final static String plate = "Plate";

	private final static String warrior = "Warrior";
	private final static String hunter = "Hunter";
	private final static String mage = "Mage";
	private final static String paladin = "Paladin";
	private final static String priest = "Priest";
	private final static String rogue = "Rogue";
	private final static String shaman = "Shaman";
	private final static String warlock = "Warlock";
	private final static String druid = "Warlock";
	
	private final static String socketBonus = "Socket Bonues: +";
	private final static String socket = " Socket";
	private final static String classes = "Classes: ";
	private final static String comma = ", ";
	private final static String plus = "+";
	private final static String empty = "";
	private final static String levels = "Level ";
	private final static String required = " required";
	
	private final static String armorStrings = "Armor : ";
	private final static String criticalStrings = " Critical";
	private final static String manaStrings = " Mana";
	private final static String staminaStrings = " Stamina";
	private final static String strengthStrings = " Strength";
	
	public Stuff(int id) { //stuff created when waiting for the server to send stuff
		super(id, empty, ItemType.STUFF, empty, (byte)1, (byte)0, 0, 1, 1);
		this.sprite_id = empty;
	}
	
	public Stuff(int id, int i) {
		super(id, empty, ItemType.WEAPON, empty, (byte)1, (byte)0, 0, 1, 1);
	}

	public Stuff(Stuff stuff) {
		super(stuff.id, stuff.sprite_id, stuff.itemType, stuff.name, stuff.level, stuff.quality, stuff.sellPrice, 1, 1);
		this.socketBonusString = stuff.socketBonusString;
		this.classRequirement = stuff.classRequirement;
		this.criticalString = stuff.criticalString;
		this.strengthString = stuff.strengthString;
		this.staminaString = stuff.staminaString;
		this.gemBonusValue = stuff.gemBonusValue;
		this.numberGemSlot = stuff.numberGemSlot;
		this.gemBonusType = stuff.gemBonusType;
		this.armorString = stuff.armorString;
		this.levelString = stuff.levelString;
		this.gem1String = stuff.gem1String;
		this.gem2String = stuff.gem2String;
		this.gem3String = stuff.gem3String;
		this.manaString = stuff.manaString;
		this.classType = stuff.classType;
		this.critical = stuff.critical;
		this.strength = stuff.strength;
		this.gemColor = stuff.gemColor;
		this.stamina = stuff.stamina;
		this.armor = stuff.armor;
		this.type = stuff.type;
		this.wear = stuff.wear;
		this.mana = stuff.mana;
		buildAllString();
		this.isLoaded = true;
	}
	
	public Stuff(StuffType type, ClassType[] classType, String sprite_id, int id, String name, byte quality, GemColor color1, GemColor color2, GemColor color3, GemBonusType gemBonusType, int gemBonusValue, byte level, Wear wear, int critical, int strength, int stamina, int armor, int mana, int sellPrice) {
		super(id, sprite_id, ItemType.STUFF, name, level, quality, sellPrice, 1, 1);
		this.gemBonusValue = gemBonusValue;
		this.gemBonusType = gemBonusType;
		this.classType = classType;
		this.critical = critical;
		this.strength = strength;
		this.stamina = stamina;
		this.gemColor[0] = color1;
		this.gemColor[1] = color2;
		this.gemColor[2] = color3;
		this.armor = armor;
		this.type = type;
		this.wear = wear;
		this.mana = mana;
		this.isLoaded = true;
		buildAllString();
	}

	public Stuff(Stuff weapon, int i) { //weapon constructor
		super(weapon.id, weapon.sprite_id, weapon.itemType, weapon.name, weapon.level, weapon.quality, weapon.sellPrice, 1, 1);
		this.socketBonusString = weapon.socketBonusString;
		this.classRequirement = weapon.classRequirement;
		this.strengthString = weapon.strengthString;
		this.criticalString = weapon.criticalString;
		this.gemBonusValue = weapon.gemBonusValue;
		this.numberGemSlot = weapon.numberGemSlot;
		this.staminaString = weapon.staminaString;
		this.gemBonusType = weapon.gemBonusType;
		this.levelString = weapon.levelString;
		this.armorString = weapon.armorString;
		this.manaString = weapon.manaString;
		this.gem1String = weapon.gem1String;
		this.gem2String = weapon.gem2String;
		this.gem3String = weapon.gem3String;
		this.weaponType = weapon.weaponType;
		this.weaponSlot = weapon.weaponSlot;
		this.classType = weapon.classType;
		this.critical = weapon.critical;
		this.strength = weapon.strength;
		this.stamina = weapon.stamina;
		this.gemColor = weapon.gemColor;
		this.armor = weapon.armor;
		this.type = weapon.type;
		this.mana = weapon.mana;
		this.wear = weapon.wear;
		this.isLoaded = true;
	}
	
	public Stuff(int id, String name, String sprite_id, ClassType[] classType, WeaponType weaponType, WeaponSlot weaponSlot, byte quality, GemColor color1, GemColor color2, GemColor color3, GemBonusType gemBonusType, int gemBonusValue, byte level, int armor, int stamina, int mana, int critical, int strength, int sellPrice) {
		super(id, sprite_id, ItemType.WEAPON, name, level, quality, sellPrice, 1, 1);
		this.gemBonusValue = gemBonusValue;
		this.gemBonusType = gemBonusType;
		this.weaponType = weaponType;
		this.weaponSlot = weaponSlot;
		this.classType = classType;
		this.critical = critical;
		this.strength = strength;
		this.stamina = stamina;
		this.gemColor[0] = color1;
		this.gemColor[1] = color2;
		this.gemColor[2] = color3;
		this.armor = armor;
		this.mana = mana;
		this.isLoaded = true;
		buildAllString();
	}
	
	private void buildAllString() {
		checkNumberGemSlot();
		buildGem1String();
		buildGem2String();
		buildGem3String();
		buildClassRequirementString();
		buildSocketBonusString();
		buildArmorString();
		buildStaminaString();
		buildManaString();
		buildStrengthString();
		buildCriticalString();
		buildLevelString();
		buildSellPriceString();
	}
	
	public void updateStuff(Stuff stuff) {
		this.id = stuff.id;
		this.sprite_id = stuff.sprite_id;
		this.name = stuff.name;
		this.level = stuff.level;
		this.quality = stuff.quality;
		this.sellPrice = stuff.sellPrice;
		this.nameColor = getItemNameColor(this);
		if(stuff.isStuff()) {
			this.socketBonusString = stuff.socketBonusString;
			this.classRequirement = stuff.classRequirement;
			this.criticalString = stuff.criticalString;
			this.strengthString = stuff.strengthString;
			this.staminaString = stuff.staminaString;
			this.gemBonusValue = stuff.gemBonusValue;
			this.numberGemSlot = stuff.numberGemSlot;
			this.gemBonusType = stuff.gemBonusType;
			this.armorString = stuff.armorString;
			this.levelString = stuff.levelString;
			this.gem1String = stuff.gem1String;
			this.gem2String = stuff.gem2String;
			this.gem3String = stuff.gem3String;
			this.manaString = stuff.manaString;
			this.classType = stuff.classType;
			this.critical = stuff.critical;
			this.strength = stuff.strength;
			this.gemColor = stuff.gemColor;
			this.stamina = stuff.stamina;
			this.armor = stuff.armor;
			this.type = stuff.type;
			this.wear = stuff.wear;
			this.mana = stuff.mana;
			this.isLoaded = true;
			buildAllString();
		}
		else if(stuff.isWeapon()) {
			this.socketBonusString = stuff.socketBonusString;
			this.classRequirement = stuff.classRequirement;
			this.strengthString = stuff.strengthString;
			this.criticalString = stuff.criticalString;
			this.gemBonusValue = stuff.gemBonusValue;
			this.numberGemSlot = stuff.numberGemSlot;
			this.staminaString = stuff.staminaString;
			this.gemBonusType = stuff.gemBonusType;
			this.levelString = stuff.levelString;
			this.armorString = stuff.armorString;
			this.manaString = stuff.manaString;
			this.gem1String = stuff.gem1String;
			this.gem2String = stuff.gem2String;
			this.gem3String = stuff.gem3String;
			this.weaponType = stuff.weaponType;
			this.weaponSlot = stuff.weaponSlot;
			this.classType = stuff.classType;
			this.critical = stuff.critical;
			this.strength = stuff.strength;
			this.stamina = stuff.stamina;
			this.gemColor = stuff.gemColor;
			this.armor = stuff.armor;
			this.type = stuff.type;
			this.mana = stuff.mana;
			this.wear = stuff.wear;
			this.isLoaded = true;
		}
	}

	public boolean canWearWeapon() {
		int i = 0;
		while(i < Mideas.joueur1().getWeaponType().length) {
			if(Mideas.joueur1().getweaponType(i) == this.weaponType) {
				return true;
			}
			i++;
		}
		return false;
	}
	
	public boolean checkBonusTypeActivated() {
		if(this.gemColor[0] != GemColor.NONE || this.gemColor[1] != GemColor.NONE || this.gemColor[2] != GemColor.NONE) {
			if(this.equippedGem[0] != null && isBonusActivated(this.equippedGem[0].getColor(), this.gemColor[0])) {
				if(this.gemColor[1] != GemColor.NONE) {
					if(this.equippedGem[1] != null && isBonusActivated(this.equippedGem[1].getColor(), this.gemColor[1])) {
						if(this.gemColor[2] != GemColor.NONE) {
							if(this.equippedGem[2] != null && isBonusActivated(this.equippedGem[2].getColor(), this.gemColor[2])) {
								this.gemBonusActivated = true;
								return true;
							}
						}
						else {
							this.gemBonusActivated = true;
							return true;
						}
					}
				}
				else {
					this.gemBonusActivated = true;
					return true;
				}
			}
		}
		this.gemBonusActivated = false;
		return false;
	}
	
	private void buildClassRequirementString() {
		if(this.classType.length < 10) {
			StringBuilder builder = new StringBuilder();
			builder.append(classes);
			int k = 0;
			while(k < this.classType.length) {
				if(k == this.classType.length-1) {
					builder.append(convClassTypeToString(k));
				}
				else {
					builder.append(convClassTypeToString(k)+comma);
				}
				k++;
			}
			this.classRequirement = builder.toString();
		}
	}
	
	public int getNumberGemSlot() {
		return this.numberGemSlot;
	}
	
	private void buildArmorString() {
		this.armorString = armorStrings+this.armor;
	}
	
	private void buildStaminaString() {
		this.staminaString = plus+this.stamina+staminaStrings;
	}
	
	private void buildManaString() {
		this.manaString = plus+this.mana+manaStrings;
	}
	
	private void buildCriticalString() {
		this.criticalString = plus+this.critical+criticalStrings;
	}
	
	private void buildStrengthString() {
		this.strengthString = plus+this.strength+strengthStrings;
	}
	
	private void buildSellPriceString() {
		this.sellPriceString = String.valueOf(this.sellPrice);
	}
	
	private void buildLevelString() {
		this.levelString = levels+this.level+required;
	}
	
	private void buildSocketBonusString() {
		if(this.gemBonusType != GemBonusType.NONE) {
			this.socketBonusString = socketBonus+this.gemBonusValue+" "+Gem.convGemBonusTypeToString(this.gemBonusType);
		}
	}
	
	private void buildGem1String() {
		if(this.gemColor[0] != GemColor.NONE) {
			this.gem1String = Gem.convGemColorToString(this.gemColor[0])+socket;
		}
	}
	
	private void buildGem2String() {
		if(this.gemColor[1] != GemColor.NONE) {
			this.gem2String = Gem.convGemColorToString(this.gemColor[1])+socket;
		}
	}
	
	private void buildGem3String() {
		if(this.gemColor[2] != GemColor.NONE) {
			this.gem3String = Gem.convGemColorToString(this.gemColor[2])+socket;
		}
	}
	
	public Gem[] getEquippedGems() {
		return this.equippedGem;
	}
	
	public String getSellPriceString() {
		return this.sellPriceString;
	}
	
	public String getLevelString() {
		return this.levelString;
	}
	
	public String getStaminaString() {
		return this.staminaString;
	}
	
	public String getManaString() {
		return this.manaString;
	}
	
	public String getCriticalString() {
		return this.criticalString;
	}
	
	public String getArmorString() {
		return this.armorString;
	}
	
	public String getStrengthString() {
		return this.strengthString;
	}
	
	public String getGem1String() {
		return this.gem1String;
	}
	
	public String getGem2String() {
		return this.gem2String;
	}
	
	public String getGem3String() {
		return this.gem3String;
	}
	
	private void checkNumberGemSlot() {
		if(this.gemColor[0] != GemColor.NONE) {
			this.numberGemSlot++;
		}
		if(this.gemColor[1] != GemColor.NONE) {
			this.numberGemSlot++;
		}
		if(this.gemColor[2] != GemColor.NONE) {
			this.numberGemSlot++;
		}
	}
	
	public String getSocketBonusString() {
		return this.socketBonusString;
	}
	
	public String getClassRequirements() {
		return this.classRequirement;
	}
	
	public int gemNumberGemSlot() {
		return this.numberGemSlot;
	}
	
	public boolean getGemBonusActivated() {
		return this.gemBonusActivated;
	}
	
	public WeaponSlot getWeaponSlot() {
		return this.weaponSlot;
	}

	public WeaponType getWeaponType() {
		return this.weaponType;
	}
	
	public GemColor[] getGemColor() {
		return this.gemColor;
	}
	
	public GemColor getGemColor(int index) {
		if(index >= 0 && index < this.gemColor.length) {
			return this.gemColor[index];
		}
		return null;
	}
	
	public GemBonusType getGemBonusType() {
		return this.gemBonusType;
	}
	
	public int getGemBonusValue() {
		return this.gemBonusValue;
	}
	public void setEquippedGem(int slot, Gem gem) {
		if(slot >= this.numberGemSlot) {
			return;
		}
		this.equippedGem[slot] = gem;
		checkBonusTypeActivated();
	}
	
	public Gem getEquippedGem(int slot) {
		if(slot >= 0 && slot < this.equippedGem.length) {
			return this.equippedGem[slot];
		}
		System.out.println("ERROR getEquippedGem slot "+slot);
		return null;
	}
	
	public Texture getFreeSlotGemSprite1() {
		return convColor(this.gemColor[0]);
	}
	
	public Texture getFreeSlotGemSprite2() {
		return convColor(this.gemColor[1]);
	}
	
	public Texture getFreeSlotGemSprite3() {
		return convColor(this.gemColor[2]);
	}
	
	public boolean isBonusActivated(GemColor gemColor, GemColor slotColor) {
		if(gemColor == GemColor.BLUE && slotColor == GemColor.BLUE) {
			return true;
		}
		if(gemColor == GemColor.YELLOW && slotColor == GemColor.YELLOW)  {
			return true;
		}
		if(gemColor == GemColor.RED && slotColor == GemColor.RED) {
			return true;
		}
		if(gemColor == GemColor.GREEN && (slotColor == GemColor.BLUE || slotColor == GemColor.YELLOW)) {
			return true;
		}
		if(gemColor == GemColor.ORANGE && (slotColor == GemColor.RED || slotColor == GemColor.YELLOW)) {
			return true;
		}
		if(gemColor == GemColor.PURPLE && (slotColor == GemColor.RED || slotColor == GemColor.BLUE)) {
			return true;
		}
		return false;
	}
	
 	public Texture convColor(GemColor color) {
		if(color == GemColor.BLUE) {
			return Sprites.gem_blue;
		}
		if(color == GemColor.RED) {
			return Sprites.gem_red;
		}
		if(color == GemColor.YELLOW) {
			return Sprites.gem_yellow;
		}
		if(color == GemColor.META) {
			return Sprites.gem_meta;
		}
		return null;
	}
	
	public String convTypeToString() {
		if(this.weaponType == WeaponType.BOW) {
			return bow;
		}
		if(this.weaponType == WeaponType.CROSSBOW) {
			return crossbow;
		}
		if(this.weaponType == WeaponType.DAGGER) {
			return dagger;
		}
		if(this.weaponType == WeaponType.FISTWEAPON) {
			return fistWeapon;
		}
		if(this.weaponType == WeaponType.GUN) {
			return gun;
		}
		if(this.weaponType == WeaponType.ONEHANDEDAXE) {
			return oneHandedAxe;
		}
		if(this.weaponType == WeaponType.ONEHANDEDMACE) {
			return oneHandedMace;
		}
		if(this.weaponType == WeaponType.ONEHANDEDSWORD) {
			return oneHandedSword;
		}
		if(this.weaponType == WeaponType.POLEARM) {
			return polearm;
		}
		if(this.weaponType == WeaponType.STAFF) {
			return staff;
		}
		if(this.weaponType == WeaponType.THROWN) {
			return thrown;
		}
		if(this.weaponType == WeaponType.TWOHANDEDAXE) {
			return twoHandedAxe;
		}
		if(this.weaponType == WeaponType.TWOHANDEDMACE) {
			return twoHandedMace;
		}
		if(this.weaponType == WeaponType.TWOHANDEDSWORD) {
			return twoHandedSword;
		}
		return wand;
	}
	
	public int getCritical() {
		return this.critical;
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
	
	public int getMana() {
		return this.mana;
	}
	
	public Wear getWear() {
		return this.wear;
	}
	
	public StuffType getType() {
		return this.type;
	}
	
	public ClassType[] getClassType() {
		return this.classType;
	}
	
	public ClassType getClassType(int i) {
		return this.classType[i];
	}
	
	public String convStuffTypeToString() {
		if(this.type == StuffType.HEAD) {
			return head;
		}
		if(this.type == StuffType.NECKLACE) {
			return necklace;
		}
		if(this.type == StuffType.SHOULDERS) {
			return shoulders;
		}
		if(this.type == StuffType.BACK) {
			return back;
		}
		if(this.type == StuffType.CHEST) {
			return chest;
		}
		if(this.type == StuffType.WRISTS) {
			return wrists;
		}
		if(this.type == StuffType.GLOVES) {
			return gloves;
		}
		if(this.type == StuffType.BELT) {
			return belt;
		}
		if(this.type == StuffType.LEGGINGS) {
			return leggings;
		}
		if(this.type == StuffType.BOOTS) {
			return boots;
		}
		if(this.type == StuffType.RING) {
			return ring;
		}
		if(this.type == StuffType.TRINKET) {
			return trinket;
		}
		if(this.type == StuffType.MAINHAND) {
			return mainHand;
		}
		if(this.type == StuffType.OFFHAND) {
			return offHand;
		}
		if(this.type == StuffType.RANGED) {
			return ranged;
		}
		return "";
	}
	
	public String convClassTypeToString(int i) {
		if(i < this.classType.length) {
			if(this.classType[i] == ClassType.GUERRIER) {
				return warrior;
			}
			if(this.classType[i] == ClassType.HUNTER) {
				return hunter;
			}
			if(this.classType[i] == ClassType.MAGE) {
				return mage;
			}
			if(this.classType[i] == ClassType.PALADIN) {
				return paladin;
			}
			if(this.classType[i] == ClassType.PRIEST) {
				return priest;
			}
			if(this.classType[i] == ClassType.ROGUE) {
				return rogue;
			}
			if(this.classType[i] == ClassType.SHAMAN) {
				return shaman;
			}
			if(this.classType[i] == ClassType.DRUID) {
				return druid;
			}
			return warlock;
		}
		return null;
	}
	
	public String convWearToString() {
		if(this.wear == Wear.CLOTH) {
			return cloth;
		}
		if(this.wear == Wear.LEATHER) {
			return leather;
		}
		if(this.wear == Wear.MAIL) {
			return mail;
		}
		if(this.wear == Wear.PLATE) {
			return plate;
		}
		return "";
	}
	
	public boolean canEquipTo(ClassType type) {
		int i = 0;
		while(i < this.classType.length) {
			if(type == this.classType[i]) {
				return true;
			}
			i++;
		}
		return false;
	}
	
	public String convSlotToString() {
		if(this.weaponSlot == WeaponSlot.MAINHAND) {
			return mainHand;
		}
		if(this.weaponSlot == WeaponSlot.OFFHAND) {
			return offHand;
		}
		return ranged;
 	}
	
	public boolean isHead() {
		return this.type == StuffType.HEAD;
	}
	
	public boolean isNecklace() {
		return this.type == StuffType.NECKLACE;
	}
	
	public boolean isShoulders() {
		return this.type == StuffType.SHOULDERS;
	}
	
	public boolean isChest() {
		return this.type == StuffType.CHEST;
	}
	
	public boolean isBack() {
		return this.type == StuffType.BACK;
	}
	
	public boolean isWrists() {
		return this.type == StuffType.WRISTS;
	}
	
	public boolean isGloves() {
		return this.type == StuffType.GLOVES;
	}
	
	public boolean isBelt() {
		return this.type == StuffType.BELT;
	}
	
	public boolean isLeggings() {
		return this.type == StuffType.LEGGINGS;
	}
	
	public boolean isBoots() {
		return this.type == StuffType.BOOTS;
	}
	
	public boolean isRing() {
		return this.type == StuffType.RING;
	}
	
	public boolean isTrinket() {
		return this.type == StuffType.TRINKET;
	}
	
	public boolean isMainHand() {
		return this.weaponSlot == WeaponSlot.MAINHAND;
	}
	
	public boolean isOffHand() {
		return this.weaponSlot == WeaponSlot.OFFHAND;
	}
	
	public boolean isRanged() {
		return this.weaponSlot == WeaponSlot.RANGED;
	}
	
	public boolean equals(Stuff item) {
		return item != null && item.getId() == this.id;
	}
	
	public int getPrice() {
		return this.price;
	}
}
