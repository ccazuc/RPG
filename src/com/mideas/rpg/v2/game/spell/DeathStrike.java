package com.mideas.rpg.v2.game.spell;

import com.mideas.rpg.v2.Sprites;
import com.mideas.rpg.v2.game.spell.Spell;

public class DeathStrike extends Spell {
	
	public DeathStrike() {
		super(3000, 3000, 600, 0, 0, 0, 3, 0, 2);
		name = "DeathStrike";
		sprite = Sprites.spell_death_strike;
	}
	
}