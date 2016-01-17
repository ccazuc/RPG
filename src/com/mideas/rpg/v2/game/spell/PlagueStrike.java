package com.mideas.rpg.v2.game.spell;

import com.mideas.rpg.v2.Sprites;
import com.mideas.rpg.v2.game.spell.Spell;

public class PlagueStrike extends Spell {
	
	public PlagueStrike() {
		super(3000, 3000, 600, 0, 0, 0, 3, 0, 3);
		name = "PlagueStrike";
		sprite = Sprites.spell_plague_strike;
	}
	
}