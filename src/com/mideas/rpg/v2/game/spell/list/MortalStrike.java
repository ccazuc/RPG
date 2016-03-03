package com.mideas.rpg.v2.game.spell.list;

import com.mideas.rpg.v2.Sprites;
import com.mideas.rpg.v2.game.spell.Spell;

public class MortalStrike extends Spell {
	
	private static int cd;
	
	public MortalStrike() {
		super(20000, 2000, 750, 0, 0, 0, 4, 0, 103);
		name = "Mortal Strike";
		sprite = Sprites.spell_mortal_strike;
	}

	public void setSpellCd(int number) {
		cd = number;
	}
	
	public int getSpellCd() {
		return cd;
	}
}