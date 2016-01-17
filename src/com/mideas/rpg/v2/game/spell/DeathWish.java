package com.mideas.rpg.v2.game.spell;

import com.mideas.rpg.v2.Sprites;

public class DeathWish extends Spell {
	public DeathWish() {
		super(4000, 2500, 0, 0, 0);
		name = "Eviscerate";
		sprite = Sprites.spell_eviscerate;
	}
}
