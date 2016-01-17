package com.mideas.rpg.v2.game.spell;

import com.mideas.rpg.v2.Sprites;

public class HealingSurge extends SpellHeal {
	
	public HealingSurge() {
		super(0, 0, 1000, 0, 0, 3000, 2, 0, 802);
		name = "HealingSurge";
		sprite = Sprites.spell_healing_surge;
	}
	
}