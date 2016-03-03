package com.mideas.rpg.v2.game.shortcut;

import java.sql.SQLException;

import org.newdawn.slick.opengl.Texture;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.game.IconsManager;
import com.mideas.rpg.v2.game.Joueur;
import com.mideas.rpg.v2.game.spell.SpellManager;
import com.mideas.rpg.v2.game.spell.SpellType;
import com.mideas.rpg.v2.hud.SpellBarFrame;

public class SpellShortcut implements Shortcut {
	
	private SpellShortcut spell;
	protected Texture sprite;
	private String sprite_id;
	private SpellType type;
	protected String name = "";
	private int baseDamage;
	private int manaCost;
	private int baseHeal;
	private int baseCd;
	private int defaultDamage;
	private int id;
	
	public SpellShortcut(int id, String sprite_id, String name, SpellType type, int baseDamage, int manaCost, int baseHeal, int baseCd, int cd) {
		this.id = id;
		this.sprite_id = sprite_id;
		this.name = name;
		this.type = type;
		this.baseDamage = baseDamage;
		this.manaCost = manaCost;
		this.baseHeal = baseHeal;
		this.baseCd = baseCd;
	}
	
	public SpellShortcut(SpellShortcut spellShortcut) {
		this.id = spellShortcut.id;
		this.sprite_id = spellShortcut.sprite_id;
		this.name = spellShortcut.name;
		this.type = spellShortcut.type;
		this.baseDamage = spellShortcut.baseDamage;
		this.manaCost = spellShortcut.manaCost;
		this.baseHeal = spellShortcut.baseHeal;
		this.baseCd = spellShortcut.baseCd;
	}
	
	public boolean cast(Joueur joueur, Joueur joueur2, SpellShortcut spell) {
		if(hasMana()) {
			doDamage(joueur);
			useMana(joueur2, spell);
			return true;
		}
		
		return false;
	}
	
	public boolean heal(Joueur joueur, SpellShortcut spell) {
		if(hasMana()) {
			doHeal(joueur);
			useMana(joueur, spell);
			return true;
		}
		return false;
	}
	
	public boolean healMax(Joueur joueur, SpellShortcut spell) {
		if(hasMana()) {
			doHealMax(joueur);
			useMana(joueur, spell);
			return true;
		}
		return false;
	}
	
	public boolean hasMana() {
		return Mideas.joueur1().getMana() >= manaCost;
	}
	
	public int getHeal() {
		return baseHeal;
	}

	public void useMana(Joueur joueur, SpellShortcut spell) {
		joueur.setMana(joueur.getMana()-spell.manaCost);
	}
	
	public String getName() {
		return name;
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
	
	public void use() throws SQLException {
		SpellBarFrame.keyboardAttack(spell);
	}
	
	public Texture getSprite() {
		return IconsManager.getSprite54(sprite_id);
	}
	
	public void setCd(int id, int cd) {
		SpellManager.setCd(id, cd);
	}
	
	public boolean equal(SpellShortcut spell) {
		if(this.id == spell.getSpellId()) {
			return true;
		}
		return false;
	}
	
	public int getSpellCd(int id) {
		return SpellManager.getCd(id);
	}
	
	public int getBaseDamage() {
		return baseDamage;
	}
	
	public void setDamage(float number) {
		this.baseDamage = (int)(baseDamage+number);
	}
	
	public int getManaCost() {
		return manaCost;
	}
	
	public String getSpriteId() {
		return sprite_id;
	}
	
	public SpellType getSpellType() {
		return type;
	}
	
	public int getSpellBaseHeal() {
		return baseHeal;
	}
	
	public int getSpellBaseCd() {
		return baseCd;
	}
	
	public int getSpellId() {
		return id;
	}
	
	public int getDefaultDamage() {
		return defaultDamage;
	}
}
