package com.mideas.rpg.v2.game.spell;

import com.mideas.rpg.v2.Sprites;
import com.mideas.rpg.v2.game.spell.Spell;

public class TigerPalm extends Spell {
	
	public TigerPalm() {
		super(2000, 2000,  1000, 0, 0, 0, 2, 0, 401);
		name = "TigerPalm";
		sprite = Sprites.spell_tiger_palm;
	}
	
}