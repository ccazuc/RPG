package com.mideas.rpg.v2.game.spell;

import com.mideas.rpg.v2.Sprites;

public class DeathCoil extends Spell {
	
	public DeathCoil() {
		super(3800, 3800, 800, 0, 0, 0, 5, 0, 1);
		name = "DeathCoil";
		sprite = Sprites.spell_death_coil;
	}
	
}
