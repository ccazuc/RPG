package com.mideas.rpg.v2.game.item.stuff;

import com.mideas.rpg.v2.game.item.Item;

public class Weapon extends Item {

	/*protected ClassType[] classType;
	protected WeaponType weaponType;
	protected WeaponSlot weaponSlot;
	protected int critical;
	protected int strength;
	protected int stamina;
	protected int price;
	protected int armor;
	protected int mana;
	protected int level;

	public Weapon(Weapon weapon) {
		super(weapon.id, weapon.sprite_id, weapon.itemType, weapon.name, weapon.quality, weapon.sellPrice, weapon.maxStack);
		this.classType = weapon.classType;
		this.weaponType = weapon.weaponType;
		this.weaponSlot = weapon.weaponSlot;
		this.critical = weapon.critical;
		this.level = weapon.level;
		this.strength = weapon.strength;
		this.stamina = weapon.stamina;
		this.armor = weapon.armor;
		this.mana = weapon.mana;
	}
	
	public Weapon(int id, String name, String sprite_id, ClassType[] classType, WeaponType weaponType, WeaponSlot weaponSlot, int quality, int level, int armor, int stamina, int mana, int critical, int strength, int sellPrice) {
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
	
	public ClassType[] getClassType() {
		return classType;
	}
	
	public ClassType getClassType(int i) {
		if(i < classType.length) {
			return classType[i];
		}
		return null;
	}
	
	public WeaponSlot getWeaponSlot() {
		return weaponSlot;
	}
	
	public String convSlotToString() {
		if(weaponSlot == WeaponSlot.MAINHAND) {
			return "Main Hand";
		}
		if(weaponSlot == WeaponSlot.OFFHAND) {
			return "Off Hand";
		}
		if(weaponSlot == WeaponSlot.RANGED) {
			return "Ranged";
		}
 		return "";
 	}
	
	public String convTypeToString() {
		if(weaponType == WeaponType.BOW) {
			return "Bow";
		}
		if(weaponType == WeaponType.CROSSBOW) {
			return "Crossbow";
		}
		if(weaponType == WeaponType.DAGGER) {
			return "Dagger";
		}
		if(weaponType == WeaponType.FISTWEAPON) {
			return "Fist weapon";
		}
		if(weaponType == WeaponType.GUN) {
			return "Gun";
		}
		if(weaponType == WeaponType.ONEHANDEDAXE) {
			return "One handed axe";
		}
		if(weaponType == WeaponType.ONEHANDEDMACE) {
			return "One handed mace";
		}
		if(weaponType == WeaponType.ONEHANDEDSWORD) {
			return "One handed sword";
		}
		if(weaponType == WeaponType.POLEARM) {
			return "Polearm";
		}
		if(weaponType == WeaponType.STAFF) {
			return "Staff";
		}
		if(weaponType == WeaponType.THROWN) {
			return "Thrown";
		}
		if(weaponType == WeaponType.TWOHANDEDAXE) {
			return "Two handed axe";
		}
		if(weaponType == WeaponType.TWOHANDEDMACE) {
			return "Two handed mace";
		}
		if(weaponType == WeaponType.TWOHANDEDSWORD) {
			return "Two handed sword";
		}
		if(weaponType == WeaponType.WAND) {
			return "Wand";
		}
		return "";
	}
	
	public String convClassTypeToString(int i) {
		if(i < classType.length) {
			if(classType[i] == ClassType.DEATHKNIGHT) {
				return "DeathKnight";
			}
			if(classType[i] == ClassType.GUERRIER) {
				return "Warrior";
			}
			if(classType[i] == ClassType.HUNTER) {
				return "Hunter";
			}
			if(classType[i] == ClassType.MAGE) {
				return "Mage";
			}
			if(classType[i] == ClassType.MONK) {
				return "MonK";
			}
			if(classType[i] == ClassType.PALADIN) {
				return "Paladin";
			}
			if(classType[i] == ClassType.PRIEST) {
				return "Priest";
			}
			if(classType[i] == ClassType.ROGUE) {
				return "Rogue";
			}
			if(classType[i] == ClassType.SHAMAN) {
				return "Shaman";
			}
			return "Warlock";
		}
		return null;
	}
	
	public WeaponType getWeaponType() {
		return weaponType;
	}
	
	public int getArmor() {
		return armor;
	}
	
	public int getStamina() {
		return stamina;
	}
	
	public int getMana() {
		return mana;
	}
	
	public int getStrength() {
		return strength;
	}
	
	public int getCritical() {
		return critical;
	}
	
	public int getLevel() {
		return level;
	}
	
	public boolean canEquipTo(ClassType type) {
		int i = 0;
		while(i < classType.length) {
			if(type == classType[i]) {
				return true;
			}
			i++;
		}
		return false;
	}
	
	public boolean canWear() {
		int i = 0;
		while(i < Mideas.joueur1().getWeaponType().length) {
			if(Mideas.joueur1().getweaponType(i) == weaponType) {
				return true;
			}
			i++;
		}
		return false;
	}*/
}
