package com.mideas.rpg.v2.game.spell.list;

import com.mideas.rpg.v2.Sprites;
import com.mideas.rpg.v2.game.spell.Spell;

public class Eviscerate extends Spell {

	private static int cd;
	
	public Eviscerate() {
		super(4000, 4000, 2500, 0, 0, 0, 2, 0, 702);
		name = "Eviscerate";
		sprite = Sprites.spell_eviscerate;
	}

	public void setSpellCd(int number) {
		cd = number;
	}
	
	public int getSpellCd() {
		return cd;
	}
}