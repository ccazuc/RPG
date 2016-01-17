package com.mideas.rpg.v2.game.spell;

import org.newdawn.slick.opengl.Texture;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.game.Joueur;

public class Spell {
	
	protected Texture sprite;
	protected String name = "";
	private int baseDamage;
	private int manaCost;
	private int stunRate;
	private int stunDuration;
	private int baseHeal;
	private int baseCd;
	private int defaultDamage;
	private int cd;
	private int id;
	
	public Spell(int baseDamage, int manaCost, int baseHeal) {
		this(baseDamage, manaCost, 0, 0, baseHeal);
	}
	
	public Spell(int baseDamage, int manaCost, int stunRate, int stunDuration, int baseHeal) {
		this.baseDamage = baseDamage;
		this.manaCost = manaCost;
		this.stunRate = stunRate;
		this.stunDuration = stunDuration;
		this.baseHeal = baseHeal;
	}
	
	public Spell(int defaultDamage, int baseDamage, int manaCost, int stunRate, int stunDuration, int baseHeal, int baseCd, int cd, int id) {
		this.defaultDamage = defaultDamage;
		this.baseDamage = baseDamage;
		this.manaCost = manaCost;
		this.stunRate = stunRate;
		this.stunDuration = stunDuration;
		this.baseHeal = baseHeal;
		this.baseCd = baseCd;
		this.cd = cd;
		this.id = id;
	}
	
	public boolean cast(Joueur joueur, Joueur joueur2, Spell spell) {
		if(hasMana()) {
			//tryStun(joueur);
			doDamage(joueur);
			useMana(joueur2, spell);
			return true;
		}
		
		return false;
	}
	
	public boolean heal(Joueur joueur, Spell spell) {
		if(hasMana()) {
			doHeal(joueur);
			useMana(joueur, spell);
			return true;
		}
		return false;
	}
	
	public boolean healMax(Joueur joueur, Spell spell) {
		if(hasMana()) {
			doHealMax(joueur);
			useMana(joueur, spell);
			return true;
		}
		return false;
	}
	
	public int getManaCost() {
		return manaCost;
	}
	
	public boolean hasMana() {
		return Mideas.joueur1().getMana() >= manaCost;
	}
	
	public void doDamage(Joueur joueur) {
		joueur.setStamina(joueur.getStamina()-getDamage());
	}
	
	public void doHeal(Joueur joueur) {
		joueur.setStamina(joueur.getStamina()+getHeal());
	}
	
	public void doHealMax(Joueur joueur) {
		joueur.setStamina(joueur.getMaxStamina());
	}
	
	public int getDamage() {
		return (int)(Mideas.joueur1().getStrength()+baseDamage*(Math.random()*.1+.95));
	}
	
	public int getBaseDamage() {
		return baseDamage;
	}
	
	public int getDefaultDamage() {
		return defaultDamage;
	}
	
	public int getHeal() {
		return baseHeal;
	}
	
	public void useMana(Joueur joueur, Spell spell) {
		joueur.setMana(joueur.getMana()-spell.manaCost);
	}
	
	public void tryStun(Joueur joueur) {
		if(Math.random() < stunRate/100.) {
			joueur.setStun(stunDuration);
		}
	}
	
	public String getName() {
		return name;
	}
	
	public Texture getSprite() {
		return sprite;
	}
	
	public void setDamage(Spell spell, int number) {
		this.baseDamage = baseDamage+number;
	}
	
	public int getSpellBaseCd() {
		return baseCd;
	}
	
	public static int getSpellCd(Spell spell) {
		return spell.cd;
	}
	
	public void setSpellCd(Spell spell, int number) {
		this.cd = number;
	}
	
	public void setDamage(float number) {
		this.baseDamage = (int)(baseDamage+number);
	}
	
	public int getId() {
		return this.id;
	}
	
	public static Spell getRandomSpell() {
		float rand = (float) Math.random();
		if(rand <= 1/3f) {
			return Mideas.joueur2().getSpells()[0];
		}
		else if(rand <= 2/3f) {
			if(Mideas.joueur2().getSpells()[1] != null) {
				return Mideas.joueur2().getSpells()[1];
			}
		}
		else if(Mideas.joueur2().getSpells()[2] != null) {
			return Mideas.joueur2().getSpells()[2];
		}
		return Mideas.joueur2().getSpells()[0];
	}
}

