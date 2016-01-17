package com.mideas.rpg.v2.game.spell;

import com.mideas.rpg.v2.Sprites;
import com.mideas.rpg.v2.game.spell.Spell;

public class LightningBolt extends Spell {
	
	public LightningBolt() {
		super(1200, 1200, 300, 0, 0, 0, 2, 0, 803);
		name = "LightningBolt";
		sprite = Sprites.spell_lightning_bolt;
	}
	
}