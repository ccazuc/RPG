package com.mideas.rpg.v2.game.spell;

import com.mideas.rpg.v2.Sprites;

public class ThunderClap extends Spell {
	
	public ThunderClap() {
		super(900, 900, 400, 0, 0, 0, 2, 0, 105);
		name = "Thunder Clap";
		sprite = Sprites.spell_thunder_clap;
	}
	
}
