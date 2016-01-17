package com.mideas.rpg.v2.game.spell;

import com.mideas.rpg.v2.Sprites;

public class Pyroblast extends Spell {
	
	public Pyroblast() {
		super(4500, 4500, 3000, 0, 0, 0, 3, 0, 303);
		name = "Pyroblast";
		sprite = Sprites.spell_pyroblast;
	}
	
}