package com.mideas.rpg.v2.game.spell.list;

import com.mideas.rpg.v2.Sprites;
import com.mideas.rpg.v2.game.spell.Spell;

public class Penance extends Spell {

	private static int cd;
	
	public Penance() {
		super(3000, 3000, 2000, 0, 0, 0, 2, 0, 603);
		name = "Penance";
		sprite = Sprites.spell_penance;
	}

	public void setSpellCd(int number) {
		cd = number;
	}
	
	public int getSpellCd() {
		return cd;
	}
}