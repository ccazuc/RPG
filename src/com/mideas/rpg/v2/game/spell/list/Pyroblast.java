package com.mideas.rpg.v2.game.spell.list;

import com.mideas.rpg.v2.Sprites;
import com.mideas.rpg.v2.game.spell.Spell;

public class Pyroblast extends Spell {

	private static int cd;
	
	public Pyroblast() {
		super(4500, 4500, 3000, 0, 0, 0, 3, 0, 303);
		name = "Pyroblast";
		sprite = Sprites.spell_pyroblast;
	}

	public void setSpellCd(int number) {
		cd = number;
	}
	
	public int getSpellCd() {
		return cd;
	}
}