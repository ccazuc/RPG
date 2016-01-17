package com.mideas.rpg.v2.game.spell;

import com.mideas.rpg.v2.Sprites;
import com.mideas.rpg.v2.game.spell.Spell;

public class CrusaderStrike extends Spell {
	
	public CrusaderStrike() {
		super(1800, 1800, 600, 0, 0, 0, 2, 0, 501);
		name = "CrusaderStrike";
		sprite = Sprites.spell_crusader_strike;
	}
	
}