package com.mideas.rpg.v2.game.spell;

import com.mideas.rpg.v2.Sprites;
import com.mideas.rpg.v2.game.spell.Spell;

public class ShadowBolt extends Spell {
	
	public ShadowBolt() {
		super(4000, 4000, 2000, 0, 0, 0, 3, 0, 903);
		name = "ShadowBolt";
		sprite = Sprites.spell_shadow_bolt;
	}
	
}
