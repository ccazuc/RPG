package com.mideas.rpg.v2.game.spell.list;

import com.mideas.rpg.v2.Sprites;
import com.mideas.rpg.v2.game.spell.Spell;

public class CrusaderStrike extends Spell {

	private static int cd;
	
	public CrusaderStrike() {
		super(1800, 1800, 600, 0, 0, 0, 2, 0, 501);
		name = "CrusaderStrike";
		sprite = Sprites.spell_crusader_strike;
	}

	public void setSpellCd(int number) {
		cd = number;
	}
	
	public int getSpellCd() {
		return cd;
	}
}