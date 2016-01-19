package com.mideas.rpg.v2.game.spell;

import com.mideas.rpg.v2.Sprites;

public class LayOnHands extends SpellHeal {

	private static int cd;
	
	public LayOnHands() {
		super(0, 0, 6000, 0, 0, 2000, 2, 0, 503);
		name = "LayOnHands";
		sprite = Sprites.spell_lay_on_hands;
	}

	public void setSpellCd(int number) {
		cd = number;
	}
	
	public int getSpellCd() {
		return cd;
	}
}
