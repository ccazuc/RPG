package com.mideas.rpg.v2.game.spell.list;

import com.mideas.rpg.v2.Sprites;
import com.mideas.rpg.v2.game.spell.Spell;

public class DeathStrike extends Spell {

	private static int cd;
	
	public DeathStrike() {
		super(3000, 3000, 600, 0, 0, 0, 3, 0, 2);
		name = "DeathStrike";
		sprite = Sprites.spell_death_strike;
	}

	public void setSpellCd(int number) {
		cd = number;
	}
	
	public int getSpellCd() {
		return cd;
	}
}