package com.mideas.rpg.v2.game.spell;

import com.mideas.rpg.v2.Sprites;
import com.mideas.rpg.v2.game.spell.Spell;

public class Ambush extends Spell {
	
	public Ambush() {
		super(6000, 6000, 3000, 0, 0, 0, 4, 0, 701);
		name = "Ambush";
		sprite = Sprites.spell_ambush;
	}
	
}