package com.mideas.rpg.v2.game.spell;

import com.mideas.rpg.v2.Sprites;
import com.mideas.rpg.v2.game.spell.Spell;

public class Judgment extends Spell {
	
	public Judgment() {
		super(2000, 2000, 1000, 0, 0, 0, 2, 0, 502);
		name = "Judgment";
		sprite = Sprites.spell_judgment;
	}
	
}