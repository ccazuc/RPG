package com.mideas.rpg.v2.game.spell;

import com.mideas.rpg.v2.Sprites;

public class MortalStrike extends Spell {
	
	public MortalStrike() {
		super(20000, 2000, 750, 0, 0, 0, 4, 0, 103);
		name = "Mortal Strike";
		sprite = Sprites.spell_mortal_strike;
	}
	
}