package com.mideas.rpg.v2.game.spell;

import com.mideas.rpg.v2.Sprites;
import com.mideas.rpg.v2.game.spell.Spell;

public class Corruption extends Spell {

	private static int cd;
	
	public Corruption() {
		super(2000, 2000, 1000, 0, 0, 0, 2, 0, 901);
		name = "Corruption";
		sprite = Sprites.spell_corruption;
	}

	public void setSpellCd(int number) {
		cd = number;
	}
	
	public int getSpellCd() {
		return cd;
	}
}