package com.mideas.rpg.v2.game.spell;

import com.mideas.rpg.v2.Sprites;
import com.mideas.rpg.v2.game.spell.Spell;

public class Corruption extends Spell {
	
	public Corruption() {
		super(2000, 2000, 1000, 0, 0, 0, 2, 0, 901);
		name = "Corruption";
		sprite = Sprites.spell_corruption;
	}
	
}