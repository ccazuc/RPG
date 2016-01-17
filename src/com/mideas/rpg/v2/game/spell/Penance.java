package com.mideas.rpg.v2.game.spell;

import com.mideas.rpg.v2.Sprites;

public class Penance extends Spell {

	public Penance() {
		super(3000, 3000, 2000, 0, 0, 0, 2, 0, 603);
		name = "Penance";
		sprite = Sprites.spell_penance;
	}
}