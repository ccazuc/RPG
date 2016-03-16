package com.mideas.rpg.v2.game.spell.list;

import com.mideas.rpg.v2.Sprites;
import com.mideas.rpg.v2.game.spell.Spell;

public class Immolation extends Spell {

	private int cd;
	
	public Immolation() {
		super(2000, 2000, 800, 0, 0, 0, 3, 0, 902);
		name = "Immolation";
		sprite = Sprites.spell_immolation;
	}

	public void setSpellCd(int number) {
		cd = number;
	}
	
	public int getSpellCd() {
		return cd;
	}
}