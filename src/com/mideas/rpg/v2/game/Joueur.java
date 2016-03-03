package com.mideas.rpg.v2.game;

import java.sql.SQLException;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.game.item.Item;
import com.mideas.rpg.v2.game.item.ItemType;
import com.mideas.rpg.v2.game.item.stuff.Stuff;
import com.mideas.rpg.v2.game.shortcut.Shortcut;
import com.mideas.rpg.v2.game.shortcut.SpellShortcut;
import com.mideas.rpg.v2.game.spell.Spell;
import com.mideas.rpg.v2.game.spell.SpellType;
import com.mideas.rpg.v2.hud.LogChat;

public class Joueur {
	
	private String classe;
	private SpellShortcut[] spells;
	private Spell[] spellUnlocked;
	private Stuff[] stuff;
	private Shortcut[] shortcut;
	private int maxStamina;
	private int expGained;
	private int isHealer;
	private int critical;
	public int stamina;
	private int maxMana;
	private int baseExp;
	private int strength;
	private float armor;
	private int mana;
	private int stun;
	private int exp;
	private int gold;
	private int goldGained;
	private int defaultArmor;
	private int defaultStuffArmor;
	//private int tailorExp;
	public int x;
	public static int y;
	public static int z;
	
	public Joueur(int stamina, int strength, float armor, int defaultArmor, int defaultStuffArmor, int critical, int mana, SpellShortcut[] spells, Spell[] spellUnlocked, Stuff[] stuff, String classe, int id, int maxStamina, int maxMana, int isHealer, int expGained, int goldGained, int tailorExp) {
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
		this.defaultStuffArmor = defaultStuffArmor;
		this.spells = spells;
		this.spellUnlocked = spellUnlocked;
		this.stuff = stuff;
		this.classe = classe;
	}
	
	public void tick() throws SQLException {
		if(stun > 0) {
			System.out.println("Le joueur "+(Mideas.joueur1().equals(this)?1:2)+" est stun");
			stun--;
		}
		else {
			if(Mideas.getCurrentPlayer()) {
				attack(Mideas.joueur2());
			}
		}
		
	}
	
	public boolean cast(SpellShortcut spell) throws SQLException {
		if(spell.getSpellType() == SpellType.DAMAGE) {
			if(!spell.hasMana()) {
				attack(Mideas.joueur2());
			}
			else {
				spell.cast(Mideas.joueur2(), Mideas.joueur1(), spell);
				LogChat.setStatusText("Le joueur 1 a enlevée "+spell.getDamage()+" hp au "+Mideas.joueur2().getClasse()+", "+Mideas.joueur2().getStamina()+" hp restant");
				return true;
			}
		}
		else if(spell.getSpellType() == SpellType.HEAL) {
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
	
	public void attackUI(SpellShortcut spell) throws SQLException {
		double damage = Mideas.joueur2().getStrength()+Math.random()*100;
		float rand = (float) Math.random();
		if(rand < Mideas.joueur2().getCritical()/100.) {
			damage*= 2;
		}
		if(spell.getSpellType() == SpellType.HEAL && spell.hasMana()) {
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
		else {
			SpellShortcut cast = Spell.getRandomSpell();
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
	
	public int getDefaultStuffArmor() {
		return defaultStuffArmor;
	}
	
	public String getClasse() {
		return classe;
	}
	
	public float getArmor() {
		return armor;
	}
	
	public int getIsHealer() {
		return isHealer;
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
	
	public void setStun(int stun) {
		this.stun = stun;
	}
	
	public int getX() {
		return x;
	}

	public SpellShortcut[] getSpells() {
		return spells;
	}

	public SpellShortcut getSpells(int i) {
		return spells[i];
	}
	
	public void setSpells(int i, SpellShortcut spell) {
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
		else if(tempItem.getItemType() == ItemType.STUFF) {
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
	
	public int getNumberItem(Item item, int i) {
		if(item.getItemType() == ItemType.ITEM || item.getItemType() == ItemType.POTION) {
			return Mideas.bag().getNumberBagItem(i);
		}
		return 0;
	}
	
	public void setNumberItem(Item potion, int i, int number) {
		if(potion.getItemType() == ItemType.ITEM || potion.getItemType() == ItemType.POTION) {
			Mideas.bag().setNumberBagItem(i, number);;
		}
	}
} 
