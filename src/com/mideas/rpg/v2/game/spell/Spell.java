package com.mideas.rpg.v2.game.spell;

import com.mideas.rpg.v2.utils.Texture;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.game.IconsManager;

public class Spell {
	
	private final int id;
	private final String sprite_id;
	private final SpellType type;
	private final String name;
	private final String tooltip;
	private String formatedTooltip;
	private final int manaCost;
	private final int cdLength;
	private long cdStart;
	private long cdEnd;
	private final int effectValue;
	private final int baseCD;
	private final int castTime;
	private final float stunRate;
	private final int stunDuration;
	private final SpellMagicalSchool magicalSchool;
	private final boolean isMagical;
	private final boolean triggerGCD;
	private final byte rank;
	
	/*public Spell(int id, String sprite_id, String name, String tooltip, SpellType type, int damage, int manaCost, float stunRate, int stunDuration, int cd, int castTime) { //Damage spells
		this.sprite_id = sprite_id;
		this.name = name;
		this.type = type;
		this.damage = damage;
		this.defaultDamage = damage;
		this.manaCost = manaCost;
		this.stunRate = stunRate;
		this.stunDuration = stunDuration;
		this.baseCD = cd;
		this.castTime = castTime;
		this.id = id;
	}
	
	public Spell(int id, String sprite_id, String name, SpellType type, int manaCost, int heal, int cd, int castTime) { //Heal spells
		this.id = id;
		this.sprite_id = sprite_id;
		this.name = name;
		this.type = type;
		this.heal = heal;
		this.baseCD = cd;
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
		this.baseCD = cd;
		this.castTime = castTime;
		this.id = id;
		this.heal = heal;
	}*/
	
	public Spell(int id, String sprite_id, String name, String tooltip, byte rank, int effectValue, int manaCost, float stunRate, int stunDuration, int cd, int castTime, boolean triggerGCD, SpellMagicalSchool magicalSchool, boolean isMagical) { //Damage spells
		this.sprite_id = sprite_id;
		this.name = name;
		this.manaCost = manaCost;
		this.stunRate = stunRate;
		this.stunDuration = stunDuration;
		this.tooltip = tooltip;
		this.baseCD = cd;
		this.castTime = castTime;
		this.id = id;
		this.isMagical = isMagical;
		this.rank = rank;
		this.effectValue = effectValue;
		this.triggerGCD = triggerGCD;
		this.magicalSchool = magicalSchool;
	}
	
	/*public boolean cast(Joueur joueur2, Joueur joueur, Spell spell) {
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
	}*/
	
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
	
	public int getCastTime() {
		return this.castTime;
	}
	
	public String getName() {
		return this.name;
	}
	public Texture getSprite() {
		return IconsManager.getSprite37(this.sprite_id);
	}
	
	public int getSpellBaseCd() {
		return this.baseCD;
	}
	
	public int getSpellCDLength() {
		return this.cdLength;
	}
	
	public long getSpellCDEnd() {
		return this.cdEnd;
	}
	
	public long getSpellCDStart() {
		return this.cdStart;
	}
	
	public boolean equal(Spell spell) {
		if(this.id == spell.getSpellId()) {
			return true;
		}
		return false;
	}
	
	public void setSpellCd(long startCD, int cd) {
		this.cdStart = startCD;
		this.cdEnd = startCD+cd;
		this.cdLength = cd;
	}

	public static void checkKeyboardCd(Spell spell) {
		if(spell != null) {
			//spell.setSpellCd(spell.getSpellBaseCd());
		}
	}
	
	public int getSpellId() {
		return this.id;
	}
}

