package com.mideas.rpg.v2.game.spell;

import com.mideas.rpg.v2.Sprites;

public class ArcaneShot extends Spell {
	
	public ArcaneShot() {
		super(4000, 4000, 3000, 0, 0, 0, 4, 0, 201);
		name = "ArcaneShot";
		sprite = Sprites.spell_arcane_shot;
	}
	
}
