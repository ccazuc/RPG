package com.mideas.rpg.v2.game.aura;

import java.util.HashMap;

public class AuraMgr {
	
	public static HashMap<Integer, Aura> auraMap = new HashMap<Integer, Aura>();
	
	public static Aura getAura(int auraID) {
		return auraMap.get(auraID);
	}
	
	public static void storeAura(Aura aura) {
		auraMap.put(aura.getId(), aura);
	}
	
	public static AuraEffect convStringToAuraEffect(String aura) {
		if(aura.equals("REDUCE_STAMINA")) {
			return AuraEffect.REDUCE_STAMINA;
		}
		if(aura.equals("REDUCE_MAX_STAMINA")) {
			return AuraEffect.REDUCE_MAX_STAMINA;
		}
		if(aura.equals("REDUCE_MANA")) {
			return AuraEffect.REDUCE_MANA;
		}
		if(aura.equals("REDUCE_MAX_MANA")) {
			return AuraEffect.REDUCE_MAX_MANA;
		}
		if(aura.equals("REDUCE_ARMOR")) {
			return AuraEffect.REDUCE_ARMOR;
		}
		if(aura.equals("REDUCE_CARAC")) {
			return AuraEffect.REDUCE_CARAC;
		}
		if(aura.equals("REDUCE_STRENGTH")) {
			return AuraEffect.REDUCE_STRENGTH;
		}
		if(aura.equals("REDUCE_AGILITY")) {
			return AuraEffect.REDUCE_AGILITY;
		}
		if(aura.equals("REDUCE_ATTACK_POWER")) {
			return AuraEffect.REDUCE_ATTACK_POWER;
		}
		if(aura.equals("REDUCE_INTELLIGENCE")) {
			return AuraEffect.REDUCE_INTELLIGENCE;
		}
		if(aura.equals("REDUCE_SPELL_POWER")) {
			return AuraEffect.REDUCE_SPELL_POWER;
		}
		if(aura.equals("REDUCE_ATTACK_SPEED")) {
			return AuraEffect.REDUCE_ATTACK_SPEED;
		}
		if(aura.equals("REDUCE_HASTE")) {
			return AuraEffect.REDUCE_HASTE;
		}
		if(aura.equals("REDUCE_SPELL_HASTE")) {
			return AuraEffect.REDUCE_SPELL_HASTE;
		}
		if(aura.equals("REDUCE_CRITICAL")) {
			return AuraEffect.REDUCE_CRITICAL;
		}
		if(aura.equals("REDUCE_SPELL_CRITICAL")) {
			return AuraEffect.REDUCE_SPELL_CRITICAL;
		}
		if(aura.equals("REDUCE_HEALING_POWER")) {
			return AuraEffect.REDUCE_HEALING_POWER;
		}
		if(aura.equals("REDUCE_HEALING_TAKEN")) {
			return AuraEffect.REDUCE_HEALING_TAKEN;
		}
		if(aura.equals("INCREASE_STAMINA")) {
			return AuraEffect.INCREASE_STAMINA;
		}
		if(aura.equals("INCREASE_MAX_STAMINA")) {
			return AuraEffect.INCREASE_MAX_STAMINA;
		}
		if(aura.equals("INCREASE_MANA")) {
			return AuraEffect.INCREASE_MANA;
		}
		if(aura.equals("INCREASE_MAX_MANA")) {
			return AuraEffect.INCREASE_MAX_MANA;
		}
		if(aura.equals("INCREASE_CARAC")) {
			return AuraEffect.INCREASE_CARAC;
		}
		if(aura.equals("INCREASE_STRENGTH")) {
			return AuraEffect.INCREASE_STRENGTH;
		}
		if(aura.equals("INCREASE_AGILITY")) {
			return AuraEffect.INCREASE_AGILITY;
		}
		if(aura.equals("INCREASE_ATTACK_POWER")) {
			return AuraEffect.INCREASE_ATTACK_POWER;
		}
		if(aura.equals("INCREASE_INTELLIGENCE")) {
			return AuraEffect.INCREASE_INTELLIGENCE;
		}
		if(aura.equals("INCREASE_SPELL_POWER")) {
			return AuraEffect.INCREASE_SPELL_POWER;
		}
		if(aura.equals("INCREASE_ATTACK_SPEED")) {
			return AuraEffect.INCREASE_ATTACK_SPEED;
		}
		if(aura.equals("INCREASE_HASTE")) {
			return AuraEffect.INCREASE_HASTE;
		}
		if(aura.equals("INCREASE_SPELL_HASTE")) {
			return AuraEffect.INCREASE_SPELL_HASTE;
		}
		if(aura.equals("INCREASE_CRITICAL")) {
			return AuraEffect.INCREASE_CRITICAL;
		}
		if(aura.equals("INCREASE_SPELL_CRITICAL")) {
			return AuraEffect.INCREASE_SPELL_CRITICAL;
		}
		if(aura.equals("INCREASE_HEALING_POWER")) {
			return AuraEffect.INCREASE_HEALING_POWER;
		}
		if(aura.equals("INCREASE_HEALING_TAKEN")) {
			return AuraEffect.INCREASE_HEALING_TAKEN;
		}
		if(aura.equals("MOUNT")) {
			return AuraEffect.MOUNT;
		}
		if(aura.equals("NONE")) {
			return AuraEffect.NONE;
		}
		if(aura.equals("SPELL_MODIFIER")) {
			return AuraEffect.SPELL_MODIFIER;
		}
		System.out.println("Error : Unknown AuraEffect AuraMgr:convStringToAuraEffect : "+aura);
		return null;
	} 
}
