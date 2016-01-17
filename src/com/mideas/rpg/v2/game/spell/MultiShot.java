package com.mideas.rpg.v2.game.spell;

import com.mideas.rpg.v2.Sprites;

public class MultiShot extends Spell {
	
	public MultiShot() {
		super(4000, 4000, 2000, 0, 0, 0, 4, 0, 202);
		name = "MultiShot";
		sprite = Sprites.spell_multi_shot;
	}
	
}
