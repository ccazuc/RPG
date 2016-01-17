package com.mideas.rpg.v2.game.spell;

import com.mideas.rpg.v2.Sprites;

public class FlameShock extends Spell {
	
	public FlameShock() {
		super(1500, 1500, 750, 0, 0, 0, 2, 0, 302);
		name = "FlameShock";
		sprite = Sprites.spell_flame_shock;
	}
	
}