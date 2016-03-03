package com.mideas.rpg.v2.game.spell.list;

import com.mideas.rpg.v2.Sprites;
import com.mideas.rpg.v2.game.spell.SpellHeal;

public class HealingSurge extends SpellHeal {

	private static int cd;
	
	public HealingSurge() {
		super(0, 0, 1000, 0, 0, 3000, 2, 0, 802);
		name = "HealingSurge";
		sprite = Sprites.spell_healing_surge;
	}

	public void setSpellCd(int number) {
		cd = number;
	}
	
	public int getSpellCd() {
		return cd;
	}
}