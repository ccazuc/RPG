package com.mideas.rpg.v2.game.spell;

import com.mideas.rpg.v2.Sprites;
import com.mideas.rpg.v2.game.spell.Spell;

public class TigerStrikes extends Spell {
	
	public TigerStrikes() {
		super(2000, 2000, 8000, 0, 0, 0, 2, 0, 402);
		name = "TigerStrikes";
		sprite = Sprites.spell_tiger_strikes;
	}
	
}