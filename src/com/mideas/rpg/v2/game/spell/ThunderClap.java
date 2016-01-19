package com.mideas.rpg.v2.game.spell;

import com.mideas.rpg.v2.Sprites;

public class ThunderClap extends Spell {

	private static int cd;
	
	public ThunderClap() {
		super(900, 900, 400, 0, 0, 0, 2, 0, 105);
		name = "Thunder Clap";
		sprite = Sprites.spell_thunder_clap;
	}

	public void setSpellCd(int number) {
		cd = number;
	}
	
	public int getSpellCd() {
		return cd;
	}
}
