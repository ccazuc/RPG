package com.mideas.rpg.v2.game.spell;

import com.mideas.rpg.v2.Sprites;
import com.mideas.rpg.v2.game.spell.Spell;

public class Rend extends Spell {
	
	public Rend() {
		super(1000, 1000, 500, 0, 0, 0, 2, 0, 104);
		name = "Rend";
		sprite = Sprites.spell_rend;
	}
	
}