package com.mideas.rpg.v2.game.item.stuff;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.game.ClassType;
import com.mideas.rpg.v2.game.item.Item;
import com.mideas.rpg.v2.game.item.ItemType;

public class Stuff extends Item {

	protected StuffType type;
	protected ClassType[] classType;
	protected WeaponType weaponType;
	protected WeaponSlot weaponSlot;
	protected Wear wear;
	protected int critical;
	protected int strength;
	protected int stamina;
	protected int price;
	protected int armor;
	protected int mana;
	protected int level;

	public Stuff(Stuff stuff) {
		super(stuff.id, stuff.sprite_id, stuff.itemType, stuff.name, stuff.quality, stuff.sellPrice, stuff.maxStack);
		this.type = stuff.type;
		this.classType = stuff.classType;
		this.wear = stuff.wear;
		this.critical = stuff.critical;
		this.level = stuff.level;
		this.strength = stuff.strength;
		this.stamina = stuff.stamina;
		this.armor = stuff.armor;
		this.mana = stuff.mana;
	}
	
	public Stuff(StuffType type, ClassType[] classType, String sprite_id, int id, String name, int quality, int level, Wear wear, int critical, int strength, int stamina, int armor, int mana, int sellPrice) {
		super(id, sprite_id, ItemType.STUFF, name, quality, sellPrice, 1);
		this.type = type;
		this.wear = wear;
		this.classType = classType;
		this.critical = critical;
		this.strength = strength;
		this.level = level;
		this.stamina = stamina;
		this.armor = armor;
		this.mana = mana;
	}

	public Stuff(Stuff stuff, int i) { //weapon constructor
		super(stuff.id, stuff.sprite_id, stuff.itemType, stuff.name, stuff.quality, stuff.sellPrice, stuff.maxStack);
		this.classType = stuff.classType;
		this.weaponType = stuff.weaponType;
		this.weaponSlot = stuff.weaponSlot;
		this.critical = stuff.critical;
		this.level = stuff.level;
		this.strength = stuff.strength;
		this.stamina = stuff.stamina;
		this.armor = stuff.armor;
		this.mana = stuff.mana;
	}
	
	public Stuff(int id, String name, String sprite_id, ClassType[] classType, WeaponType weaponType, WeaponSlot weaponSlot, int quality, int level, int armor, int stamina, int mana, int critical, int strength, int sellPrice) {
		super(id, sprite_id, ItemType.WEAPON, name, quality, sellPrice, 1);
		this.classType = classType;
		this.weaponType = weaponType;
		this.weaponSlot = weaponSlot;
		this.critical = critical;
		this.strength = strength;
		this.level = level;
		this.stamina = stamina;
		this.armor = armor;
		this.mana = mana;
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
	
	public WeaponSlot getWeaponSlot() {
		return this.weaponSlot;
	}

	public WeaponType getWeaponType() {
		return this.weaponType;
	}
	
	public String convTypeToString() {
		if(this.weaponType == WeaponType.BOW) {
			return "Bow";
		}
		if(this.weaponType == WeaponType.CROSSBOW) {
			return "Crossbow";
		}
		if(this.weaponType == WeaponType.DAGGER) {
			return "Dagger";
		}
		if(this.weaponType == WeaponType.FISTWEAPON) {
			return "Fist weapon";
		}
		if(this.weaponType == WeaponType.GUN) {
			return "Gun";
		}
		if(this.weaponType == WeaponType.ONEHANDEDAXE) {
			return "One handed axe";
		}
		if(this.weaponType == WeaponType.ONEHANDEDMACE) {
			return "One handed mace";
		}
		if(this.weaponType == WeaponType.ONEHANDEDSWORD) {
			return "One handed sword";
		}
		if(this.weaponType == WeaponType.POLEARM) {
			return "Polearm";
		}
		if(this.weaponType == WeaponType.STAFF) {
			return "Staff";
		}
		if(this.weaponType == WeaponType.THROWN) {
			return "Thrown";
		}
		if(this.weaponType == WeaponType.TWOHANDEDAXE) {
			return "Two handed axe";
		}
		if(this.weaponType == WeaponType.TWOHANDEDMACE) {
			return "Two handed mace";
		}
		if(this.weaponType == WeaponType.TWOHANDEDSWORD) {
			return "Two handed sword";
		}
		if(this.weaponType == WeaponType.WAND) {
			return "Wand";
		}
		return "";
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
	
	@Override
	public int getId() {
		return this.id;
	}

	@Override
	public String getSpriteId() {
		return this.sprite_id;
	}
	
	public int getLevel() {
		return this.level;
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
			return "Head";
		}
		if(this.type == StuffType.NECKLACE) {
			return "Necklace";
		}
		if(this.type == StuffType.SHOULDERS) {
			return "Shoulders";
		}
		if(this.type == StuffType.BACK) {
			return "Back";
		}
		if(this.type == StuffType.CHEST) {
			return "Chest";
		}
		if(this.type == StuffType.WRISTS) {
			return "Wrists";
		}
		if(this.type == StuffType.GLOVES) {
			return "Gloves";
		}
		if(this.type == StuffType.BELT) {
			return "Belt";
		}
		if(this.type == StuffType.LEGGINGS) {
			return "Leggings";
		}
		if(this.type == StuffType.BOOTS) {
			return "Boots";
		}
		if(this.type == StuffType.RING) {
			return "Ring";
		}
		if(this.type == StuffType.TRINKET) {
			return "Trinket";
		}
		if(this.type == StuffType.MAINHAND) {
			return "MainHand";
		}
		if(this.type == StuffType.OFFHAND) {
			return "OffHand";
		}
		return "Ranged";
	}
	
	public String convClassTypeToString(int i) {
		if(i < this.classType.length) {
			if(this.classType[i] == ClassType.DEATHKNIGHT) {
				return "DeathKnight";
			}
			if(this.classType[i] == ClassType.GUERRIER) {
				return "Warrior";
			}
			if(this.classType[i] == ClassType.HUNTER) {
				return "Hunter";
			}
			if(this.classType[i] == ClassType.MAGE) {
				return "Mage";
			}
			if(this.classType[i] == ClassType.MONK) {
				return "MonK";
			}
			if(this.classType[i] == ClassType.PALADIN) {
				return "Paladin";
			}
			if(this.classType[i] == ClassType.PRIEST) {
				return "Priest";
			}
			if(this.classType[i] == ClassType.ROGUE) {
				return "Rogue";
			}
			if(this.classType[i] == ClassType.SHAMAN) {
				return "Shaman";
			}
			return "Warlock";
		}
		return null;
	}
	
	public String convWearToString() {
		if(this.wear == Wear.CLOTH) {
			return "Cloth";
		}
		if(this.wear == Wear.LEATHER) {
			return "Leather";
		}
		if(this.wear == Wear.MAIL) {
			return "Mail";
		}
		if(this.wear == Wear.PLATE) {
			return "Plate";
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
			return "Main Hand";
		}
		if(this.weaponSlot == WeaponSlot.OFFHAND) {
			return "Off Hand";
		}
		if(this.weaponSlot == WeaponSlot.RANGED) {
			return "Ranged";
		}
 		return "";
 	}
	
	public String convWeaponTypeToString() {
		if(this.weaponType == WeaponType.BOW) {
			return "Bow";
		}
		if(this.weaponType == WeaponType.CROSSBOW) {
			return "Crossbow";
		}
		if(this.weaponType == WeaponType.DAGGER) {
			return "Dagger";
		}
		if(this.weaponType == WeaponType.FISTWEAPON) {
			return "Fist weapon";
		}
		if(this.weaponType == WeaponType.GUN) {
			return "Gun";
		}
		if(this.weaponType == WeaponType.ONEHANDEDAXE) {
			return "One handed axe";
		}
		if(this.weaponType == WeaponType.ONEHANDEDMACE) {
			return "One handed mace";
		}
		if(this.weaponType == WeaponType.ONEHANDEDSWORD) {
			return "One handed sword";
		}
		if(this.weaponType == WeaponType.POLEARM) {
			return "Polearm";
		}
		if(this.weaponType == WeaponType.STAFF) {
			return "Staff";
		}
		if(this.weaponType == WeaponType.THROWN) {
			return "Thrown";
		}
		if(this.weaponType == WeaponType.TWOHANDEDAXE) {
			return "Two handed axe";
		}
		if(this.weaponType == WeaponType.TWOHANDEDMACE) {
			return "Two handed mace";
		}
		if(this.weaponType == WeaponType.TWOHANDEDSWORD) {
			return "Two handed sword";
		}
		if(this.weaponType == WeaponType.WAND) {
			return "Wand";
		}
		return "";
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
		return this.type == StuffType.MAINHAND;
	}
	
	public boolean isOffHand() {
		return this.type == StuffType.OFFHAND;
	}
	
	public boolean isRanged() {
		return this.type == StuffType.RANGED;
	}
	
	public boolean equals(Stuff item) {
		return item != null && item.getId() == this.id;
	}
	
	public String getStuffName() {
		return this.name;
	}
	
	public int getPrice() {
		return this.price;
	}

	@Override
	public int getSellPrice() {
		return this.sellPrice;
	}
}
