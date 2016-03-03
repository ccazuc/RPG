package com.mideas.rpg.v2.game.spell.list;

import com.mideas.rpg.v2.Sprites;
import com.mideas.rpg.v2.game.spell.Spell;

public class ShadowBolt extends Spell {

	private static int cd;
	
	public ShadowBolt() {
		super(4000, 4000, 2000, 0, 0, 0, 3, 0, 903);
		name = "ShadowBolt";
		sprite = Sprites.spell_shadow_bolt;
	}

	public void setSpellCd(int number) {
		cd = number;
	}
	
	public int getSpellCd() {
		return cd;
	}
}
