package com.mideas.rpg.v2.game.spell.list;

import com.mideas.rpg.v2.Sprites;
import com.mideas.rpg.v2.game.spell.Spell;

public class Charge extends Spell {

	private static int cd;
	
	public Charge() {
		super(800, 800, 400, 0, 0, 0, 3, 0, 101);
		name = "Charge";
		sprite = Sprites.spell_charge;
	}

	public void setSpellCd(int number) {
		cd = number;
	}
	
	public int getSpellCd() {
		return cd;
	}
}