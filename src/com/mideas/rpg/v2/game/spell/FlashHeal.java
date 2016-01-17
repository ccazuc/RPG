package com.mideas.rpg.v2.game.spell;

import com.mideas.rpg.v2.Sprites;

public class FlashHeal extends SpellHeal {
	
	public FlashHeal() {
		super(0, 0, 750, 0, 0, 5000, 3, 0, 601);
		name = "FlashHeal";
		sprite = Sprites.spell_flash_heal;
	}
	
}
