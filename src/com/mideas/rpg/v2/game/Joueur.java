package com.mideas.rpg.v2.game;

import java.sql.SQLException;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.game.item.Item;
import com.mideas.rpg.v2.game.item.ItemType;
import com.mideas.rpg.v2.game.item.stuff.Stuff;
import com.mideas.rpg.v2.game.item.stuff.WeaponType;
import com.mideas.rpg.v2.game.item.stuff.Wear;
import com.mideas.rpg.v2.game.shortcut.Shortcut;
import com.mideas.rpg.v2.game.spell.Spell;
import com.mideas.rpg.v2.game.spell.SpellType;
import com.mideas.rpg.v2.hud.LogChat;
import com.mideas.rpg.v2.hud.SpellBarFrame;

public class Joueur {
	
	private Shortcut[] spells;
	private Spell[] spellUnlocked;
	private Stuff[] stuff;
	private WeaponType[] weaponType;
	private Shortcut[] shortcut;
	private int maxStamina;
	private int expGained;
	private int critical;
	public int stamina;
	private int maxMana;
	private int baseExp;
	private int strength;
	private float armor;
	private Wear wear;
	private int mana;
	private int exp;
	private int gold;
	private int goldGained;
	private int defaultArmor;
	//private int tailorExp;
	private String id;
	public int x;
	public static int y;
	public static int z;
	
	/*public Joueur(int stamina, int strength, float armor, int defaultArmor, int defaultStuffArmor, int critical, int mana, Shortcut[] spells, Spell[] spellUnlocked, Stuff[] stuff, String classe, int id, int maxStamina, int maxMana, int isHealer, int expGained, int goldGained, int tailorExp) {
		this.maxStamina = maxStamina;
		this.expGained = expGained;
		this.goldGained = goldGained;
		this.isHealer = isHealer;
		this.critical = critical;
		//this.tailorExp = tailorExp;
		this.stamina = stamina;
		this.maxMana = maxMana;
		this.strength = strength;
		this.armor = armor;
		this.mana = mana;
		this.defaultArmor = defaultArmor;
		this.spells = spells;
		this.spellUnlocked = spellUnlocked;
		this.stuff = stuff;
		this.classe = classe;
	}*/
	
	public Joueur(String id, Wear wear, WeaponType[] weaponType, int stamina, int mana, int strength, int armor, int defaultArmor, int critical, int maxStamina, int maxMana, int expGained, int goldGained, Shortcut[] spells, Spell[] spellUnlocked, Stuff[] stuff) {
		this.id = id;
		this.stamina = stamina;
		this.mana = mana;
		this.strength = strength;
		this.armor = armor;
		this.defaultArmor = defaultArmor;
		this.critical = critical;
		this.maxStamina = maxStamina;
		this.maxMana = maxMana;
		this.expGained = expGained;
		this.goldGained = goldGained;
		this.spells = spells;
		this.spellUnlocked = spellUnlocked;
		this.weaponType = weaponType;
		this.stuff = stuff;
		this.wear = wear;
	}
	
	public Joueur(Joueur joueur) {
		this.id = joueur.id;
		this.weaponType = joueur.weaponType;
		this.stamina = joueur.stamina;
		this.mana = joueur.mana;
		this.strength = joueur.strength;
		this.armor = joueur.armor;
		this.defaultArmor = joueur.defaultArmor;
		this.critical = joueur.critical;
		this.maxStamina = joueur.maxStamina;
		this.maxMana = joueur.maxMana;
		this.expGained = joueur.expGained;
		this.goldGained = joueur.goldGained;
		this.spells = joueur.spells;
		this.spellUnlocked = joueur.spellUnlocked;
		this.stuff = joueur.stuff;
		this.wear = joueur.wear;
	}
	
	public void tick() throws SQLException {
		if(Mideas.getCurrentPlayer()) {
			attack(Mideas.joueur2());
			SpellBarFrame.setIsCastingSpell(false);
		}
	}
	
	public boolean cast(Spell spell) throws SQLException {
		if(spell.getType() == SpellType.DAMAGE) {
			SpellBarFrame.setIsCastingSpell(false);
			if(!spell.hasMana()) {
				attack(Mideas.joueur2());
			}
			else {
				spell.cast(Mideas.joueur2(), Mideas.joueur1(), spell);
				LogChat.setStatusText("Le joueur 1 a enlevée "+spell.getDamage()+" hp au "+Mideas.joueur2().getClasse()+", "+Mideas.joueur2().getStamina()+" hp restant");
				return true;
			}
		}
		else if(spell.getType() == SpellType.HEAL) {
			SpellBarFrame.setIsCastingSpell(false);
			if(!spell.hasMana()) {
				attack(Mideas.joueur2());
			}
			else {
				if(Mideas.joueur1().getStamina()+spell.getHeal() >= Mideas.joueur1().getMaxStamina()) {
					int diff = Mideas.joueur1().getMaxStamina()-Mideas.joueur1().getStamina();
					spell.healMax(Mideas.joueur1(), spell);
					LogChat.setStatusText("Vous vous êtes rendu "+diff+" hp, vous avez maintenant "+Mideas.joueur1().getStamina()+" hp");	
					return true;
				}
				else {
					spell.heal(Mideas.joueur1(), spell);
					LogChat.setStatusText("Vous vous êtes rendu "+spell.getHeal()+" hp, vous avez maintenant "+Mideas.joueur1().getStamina()+" hp");
					return true;
				}
			}
		}
		return false;
	}
	
	public void attack(Joueur joueur) throws SQLException {
		double damage = Mideas.joueur1().getStrength()+Math.random()*100;
		SpellBarFrame.setIsCastingSpell(false);
		if(Math.random() < critical/100.) {
			damage*= 2;
		}
		joueur.setStamina(joueur.getStamina()-damage);
		LogChat.setStatusText("Le joueur 1 a enlevé "+Math.round(damage)+" hp au "+joueur.getClasse()+", "+joueur.getStamina()+" hp restant"); //and "+Mideas.joueur2.getMana()+" mana left");
		if(Mideas.joueur1().getStamina() <= 0) {
			LogChat.setStatusText("Le joueur 2 a gagné !");
			LogChat.setStatusText2(""); 
			y = 1;
			return;
		}
		else if(Mideas.joueur2().getStamina() <= 0) {
			LogChat.setStatusText("Le joueur 1 a gagné !");
			LogChat.setStatusText2("");
			z = 1;
			return;
		}
	}
	
	public void attackUI(Spell spell) throws SQLException {
		double damage = Mideas.joueur2().getStrength()+Math.random()*100;
		float rand = (float)Math.random();
		if(rand < Mideas.joueur2().getCritical()/100.) {
			damage*= 2;
		}
		if(spell.getType() == SpellType.HEAL && spell.hasMana()) {
			if(Mideas.joueur2().getStamina() < Mideas.joueur2().getMaxStamina()) {
				if(Mideas.joueur2().getStamina()+spell.getHeal() >= Mideas.joueur2().getMaxStamina()) {
					int diff = Mideas.joueur2().getMaxStamina()-Mideas.joueur2().getStamina();
					spell.healMax(Mideas.joueur2(), spell);
					LogChat.setStatusText2("Le joueur2 s'est rendu "+diff+" hp, il a maintenant "+Mideas.joueur2().getStamina()+" hp");
				}
				else {
					spell.heal(Mideas.joueur2(), spell);
					LogChat.setStatusText2("Le joueur2 s'est rendu "+spell.getHeal()+" hp, il a maintenant "+Mideas.joueur2().getStamina()+" hp");	
				}
			}
		}
		else if(spell.getType() == SpellType.DAMAGE) {
			Spell cast = Spell.getRandomSpell();
			if(rand > .2 && rand <= .4 && cast.hasMana()) {	
				cast.cast(Mideas.joueur1(), Mideas.joueur2(), spell);
				LogChat.setStatusText2("Le joueur 2 a enlevée "+cast.getDamage()+" hp au "+Mideas.joueur1().getClasse()+", "+Mideas.joueur1().getStamina()+" hp restant");
			}
			else {
				Mideas.joueur1().setStamina(Mideas.joueur1().getStamina()-damage);
				LogChat.setStatusText2("Le joueur 2 a enlevée "+Math.round(damage)+" hp au "+Mideas.joueur1().getClasse()+", "+Mideas.joueur1().getStamina()+" hp restant");	
			}
		}
	}
	
	public void setStuffArmor(int armor) {
		this.armor+= armor;
	}

	public void setStuffStrength(int strengh) {
		this.strength+= strengh;
	}
	
	public void setStuffStamina(int stamina) {
		this.maxStamina+= stamina;
		this.stamina+= stamina;
	}
	
	public void setStuffCritical(int critical) {
		this.critical+= critical;
	}
	
	public void setStuffMana(int mana) {
		this.maxMana+= mana;
		this.mana+= mana;
	}
	
	public String getClasse() {
		return id;
	}
	
	public float getArmor() {
		return armor;
	}
	
	public void setStamina(double d) {
		this.stamina = (int) Math.max(d, 0);
	}
	
	public int getMaxStamina() {
		return maxStamina;
	}
	
	public int getStamina() {
		return stamina;
	}
	
	public Wear getWear() {
		return wear;
	}
	
	public int getCritical() {
		return critical;
	}
	
	public int getStrength() {
		return strength;
	}
	
	public void setMana(int mana) {
		this.mana = Math.max(mana, 0);
	}
	
	public int getMana() {
		return mana;
	}
	
	public int getMaxMana() {
		return maxMana;
	}
	
	public int getX() {
		return x;
	}

	public Shortcut[] getSpells() {
		return spells;
	}

	public Shortcut getSpells(int i) {
		return this.spells[i];
	}
	
	public void setSpells(int i, Shortcut spell) {
		this.spells[i] = spell;
	}
	
	public Spell[] getSpellUnlocked() {
		return spellUnlocked;
	}
	
	public Spell getSpellUnlocked(int i) {
		return spellUnlocked[i];
	}
	
	public Stuff[] getStuff() {
		return stuff;
	}
	
	public Stuff getStuff(int i) {
		return stuff[i];
	}
	
	public void setStuff(int i, Item tempItem) {
		if(tempItem == null) {
			this.stuff[i] = null;
		}
		else if(tempItem.getItemType() == ItemType.STUFF || tempItem.getItemType() == ItemType.WEAPON) {
			this.stuff[i] = (Stuff)tempItem;
		}
	}
	
	public Shortcut getShortcut(int i) {
		return shortcut[i];
	}
	
	public Shortcut[] getShortcut() {
		return shortcut;
	}

	public void setSpellUnlocked(int i, Spell spell) {
		this.spellUnlocked[i] = spell;
	}
	
	public int getExp() {
		return exp;
	}
	
	public int getBaseExp() {
		return baseExp;
	}
	
	public int getExpGained() {
		return expGained;
	}
	
	public void setArmor(float number) {
		this.armor = (Math.round(100*(armor+number))/100.f);
	}
	
	public int getDefaultArmor() {
		return defaultArmor;
	}
	
	public int getGold() {
		return gold;
	}
	
	public int getGoldGained() {
		return goldGained;
	}
	
	public void setExp(int baseExp, int expGained ) {
		exp = baseExp+expGained;
	}
	
	public void setMaxStamina(int stamina) {
		maxStamina = stamina;
	}
	
	public void setMaxMana(int mana) {
		maxMana = mana;
	}
	
	public int getNumberItem(Item item) {
		if(item.getItemType() == ItemType.ITEM || item.getItemType() == ItemType.POTION && Mideas.bag().getNumberStack().containsKey(item)) {
			return Mideas.bag().getNumberStack().get(item);
		}
		return 0;
	}
	
	public void setNumberItem(Item potion, int number) {
		if(potion.getItemType() == ItemType.ITEM || potion.getItemType() == ItemType.POTION) {
			Mideas.bag().getNumberStack().put(potion, number);
		}
	}
	
	public WeaponType[] getWeaponType() {
		return weaponType;
	}
	
	public WeaponType getweaponType(int i) {
		if(i < weaponType.length) {
			return weaponType[i];
		}
		return null;
	}
} 

