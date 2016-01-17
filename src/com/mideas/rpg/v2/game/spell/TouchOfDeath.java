package com.mideas.rpg.v2.game.spell;

import com.mideas.rpg.v2.Sprites;
import com.mideas.rpg.v2.game.spell.Spell;

public class TouchOfDeath extends Spell {
	
	public TouchOfDeath() {
		super(7000, 7000, 2000, 0, 0, 0, 5, 0, 403);
		name = "TouchOfDeath";
		sprite = Sprites.spell_touch_of_death;
	}
	
}