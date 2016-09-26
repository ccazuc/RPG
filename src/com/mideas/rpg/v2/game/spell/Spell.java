package com.mideas.rpg.v2.game.spell;

import com.mideas.rpg.v2.utils.Texture;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.game.IconsManager;
import com.mideas.rpg.v2.game.Joueur;
import com.mideas.rpg.v2.game.shortcut.SpellShortcut;

public class Spell {
	
	private int id;
	private String sprite_id;
	private SpellType type;
	protected String name;
	private int damage;
	private int defaultDamage;
	private int manaCost;
	private int heal;
	private int defaultHeal;
	private int cd;
	private int castTime;
	private float stunRate;
	private int stunDuration;
	
	public Spell(int id, String sprite_id, String name, SpellType type, int damage, int manaCost, float stunRate, int stunDuration, int cd, int castTime) { //Damage spells
		this.sprite_id = sprite_id;
		this.name = name;
		this.type = type;
		this.damage = damage;
		this.defaultDamage = damage;
		this.manaCost = manaCost;
		this.stunRate = stunRate;
		this.stunDuration = stunDuration;
		this.cd = cd;
		this.castTime = castTime;
		this.id = id;
	}
	
	public Spell(int id, String sprite_id, String name, SpellType type, int manaCost, int heal, int cd, int castTime) { //Heal spells
		this.id = id;
		this.sprite_id = sprite_id;
		this.name = name;
		this.type = type;
		this.heal = heal;
		this.cd = cd;
		this.manaCost = manaCost;
		this.castTime = castTime;
	}

	public Spell(int id, String sprite_id, String name, SpellType type, int damage, int manaCost, float stunRate, int stunDuration, int cd, int heal, int castTime) { //Damage and heal spells
		this.sprite_id = sprite_id;
		this.name = name;
		this.type = type;
		this.damage = damage;
		this.manaCost = manaCost;
		this.stunRate = stunRate;
		this.stunDuration = stunDuration;
		this.cd = cd;
		this.castTime = castTime;
		this.id = id;
		this.heal = heal;
	}
	
	public void action(Joueur caster, Joueur target) {}
	
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
	
	public void doDamage(Joueur caster, Joueur target) {
		if(caster.getMana() >= this.manaCost) {
			//if(!caster.isStun()) {
				if(caster.getStamina() > 0 && target.getStamina() > 0) {
					target.setStamina(Math.max(target.getStamina()-getDamage(), 0));
				}
			//}
		}
	}
	
	public void doHeal(Joueur caster, Joueur target) {
		if(caster.getMana() >= this.manaCost) {
			//if(!caster.isStun()) {
			if(caster.getStamina() > 0 && target.getStamina() > 0) {
				target.setStamina(Math.min(target.getStamina()+getHeal(), target.getMaxStamina()));
			}
			//}
		}
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
	
	public void doHeal(Joueur joueur) {
		joueur.setStamina(joueur.getStamina()+getHeal());
	}
	
	public void doHealMax(Joueur joueur) {
		joueur.setStamina(joueur.getMaxStamina());
	}
	
	public int getDamage() {
		return (int)(Mideas.joueur1().getStrength()+this.defaultDamage*(Math.random()*.1+.95));
	}
	
	public int getHeal() {
		return (int)(this.heal*(1+Math.random()));
	}
	
	public int getDefaultamage() {
		return this.defaultDamage;
	}
	
	public int getDefaultDamage() {
		return this.defaultDamage;
	}
	
	
	public int getCastTime() {
		return this.castTime;
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
	
	public int getSpellBaseCd() {
		return this.cd;
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
		return null;
	}
}

