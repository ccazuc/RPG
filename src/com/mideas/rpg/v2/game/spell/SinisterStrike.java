package com.mideas.rpg.v2.game.spell;

import com.mideas.rpg.v2.Sprites;
import com.mideas.rpg.v2.game.spell.Spell;

public class SinisterStrike extends Spell {
	
	public SinisterStrike() {
		super(3000, 3000, 1500, 0, 0, 0, 2, 0, 703);
		name = "Sinister Strike";
		sprite = Sprites.spell_sinister_strike;
	}
	
}