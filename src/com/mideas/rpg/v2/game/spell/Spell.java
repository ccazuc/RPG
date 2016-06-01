package com.mideas.rpg.v2.game.spell;

import java.util.concurrent.ThreadLocalRandom;

import com.mideas.rpg.v2.utils.Texture;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.game.IconsManager;
import com.mideas.rpg.v2.game.Joueur;
import com.mideas.rpg.v2.game.shortcut.SpellShortcut;

public class Spell {
	
	protected Texture sprite;
	private SpellType type;
	protected String name = "";
	private int baseDamage;
	private int manaCost;
	private int baseHeal;
	private int baseCd;
	private int defaultDamage;
	private String sprite_id;
	private int id;
	private int castTime;
	
	public Spell(int defaultDamage, int baseDamage, int manaCost, int stunRate, int stunDuration, int baseHeal, int baseCd, int cd, int id) {
		this.defaultDamage = defaultDamage;
		this.baseDamage = baseDamage;
		this.manaCost = manaCost;
		this.baseHeal = baseHeal;
		this.baseCd = baseCd;
		this.id = id;
	}
	
	public Spell(int id, String sprite_id, String name, SpellType type, int baseDamage, int manaCost, int baseHeal, int baseCd, int cd, int castTime) {
		this.id = id;
		this.sprite_id = sprite_id;
		this.name = name;
		this.type = type;
		this.baseDamage = baseDamage;
		this.manaCost = manaCost;
		this.baseHeal = baseHeal;
		this.baseCd = baseCd;
		this.castTime = castTime;
	}
	
	public boolean cast(Joueur joueur2, Joueur joueur, Spell spell) {
		if(hasMana()) {
			doDamage(joueur2, joueur);
			useMana(joueur, spell);
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
		return this.manaCost;
	}
	
	public boolean hasMana() {
		return Mideas.joueur1().getMana() >= this.manaCost;
	}
	
	public String getSpriteId() {
		return this.sprite_id;
	}
	
	public SpellType getType() {
		return this.type;
	}
	
	public void doDamage(Joueur joueur2, Joueur joueur) {
		joueur2.setStamina(joueur2.getStamina()-(getDamage()+joueur.getStrength())*ThreadLocalRandom.current().nextDouble(.9, 1.1));
	}
	
	public void doHeal(Joueur joueur) {
		joueur.setStamina(joueur.getStamina()+getHeal());
	}
	
	public void doHealMax(Joueur joueur) {
		joueur.setStamina(joueur.getMaxStamina());
	}
	
	public int getDamage() {
		return (int)(Mideas.joueur1().getStrength()+this.baseDamage*(Math.random()*.1+.95));
	}
	
	public int getBaseDamage() {
		return this.baseDamage;
	}
	
	public int getDefaultDamage() {
		return this.defaultDamage;
	}
	
	
	public int getCastTime() {
		return this.castTime;
	}
	
	public int getHeal() {
		return this.baseHeal;
	}
	
	public void useMana(Joueur joueur, Spell spell) {
		joueur.setMana(joueur.getMana()-spell.manaCost);
	}
	
	public String getName() {
		return this.name;
	}
	public Texture getSprite() {
		return IconsManager.getSprite47(this.sprite_id);
	}
	
	
	public void setDamage(Spell spell, int number) {
		this.baseDamage = this.baseDamage+number;
	}
	
	public int getSpellBaseCd() {
		return this.baseCd;
	}
	
	public int getSpellCd() {
		return 0;
	}
	
	public boolean equal(Spell spell) {
		if(this.id == spell.getSpellId()) {
			return true;
		}
		return false;
	}
	
	public void setSpellCd(int number) {
	}

	public static void checkKeyboardCd(Spell spell) {
		if(spell != null) {
			spell.setSpellCd(spell.getSpellBaseCd());
		}
	}
	
	public void setDamage(float number) {
		this.baseDamage = (int)(this.baseDamage+number);
	}
	
	public int getSpellId() {
		return this.id;
	}
	
	public static Spell getRandomSpell() {
		float rand = (float) Math.random();
		if(rand <= 1/3f && Mideas.joueur2().getSpells(0) != null) {
			return ((SpellShortcut)Mideas.joueur2().getSpells(0)).getSpell();
		}
		else if(rand <= 2/3f && Mideas.joueur2().getSpells(1) != null) {
			return ((SpellShortcut)Mideas.joueur2().getSpells(1)).getSpell();
		}
		else if(Mideas.joueur2().getSpells(2) != null) {
			return ((SpellShortcut)Mideas.joueur2().getSpells(2)).getSpell();
		}
		return ((SpellShortcut)Mideas.joueur2().getSpells(0)).getSpell();
	}
}

