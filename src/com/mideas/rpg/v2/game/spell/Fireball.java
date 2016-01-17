package com.mideas.rpg.v2.game.spell;

import com.mideas.rpg.v2.Sprites;

public class Fireball extends Spell {
	
	public Fireball() {
		super(500, 500, 300, 0, 0, 0, 2, 0, 301);
		name = "Fireball";
		sprite = Sprites.spell_fireball;
	}
	
} 