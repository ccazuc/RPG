package com.mideas.rpg.v2.game.spell;

import com.mideas.rpg.v2.Sprites;
import com.mideas.rpg.v2.game.spell.Spell;

public class Charge extends Spell {
	
	public Charge() {
		super(800, 800, 400, 0, 0, 0, 3, 0, 101);
		name = "Charge";
		sprite = Sprites.spell_charge;
	}
	
}