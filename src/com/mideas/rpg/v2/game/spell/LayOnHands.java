package com.mideas.rpg.v2.game.spell;

import com.mideas.rpg.v2.Sprites;

public class LayOnHands extends SpellHeal {
	
	public LayOnHands() {
		super(0, 0, 6000, 0, 0, 2000, 2, 0, 503);
		name = "LayOnHands";
		sprite = Sprites.spell_lay_on_hands;
	}
	
}
