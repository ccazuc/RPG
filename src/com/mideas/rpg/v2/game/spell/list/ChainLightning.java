package com.mideas.rpg.v2.game.spell.list;

import com.mideas.rpg.v2.Sprites;
import com.mideas.rpg.v2.game.spell.Spell;

public class ChainLightning extends Spell {

	private static int cd;
	
	public ChainLightning() {
		super(1800, 1800, 600, 0, 0, 0, 2, 0, 801);
		name = "ChainLightning";
		sprite = Sprites.spell_chain_lightning;
	}

	public void setSpellCd(int number) {
		cd = number;
	}
	
	public int getSpellCd() {
		return cd;
	}
}
