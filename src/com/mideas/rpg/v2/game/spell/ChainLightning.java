package com.mideas.rpg.v2.game.spell;

import com.mideas.rpg.v2.Sprites;

public class ChainLightning extends Spell {
	
	public ChainLightning() {
		super(1800, 1800, 600, 0, 0, 0, 2, 0, 801);
		name = "ChainLightning";
		sprite = Sprites.spell_chain_lightning;
	}
	
}
