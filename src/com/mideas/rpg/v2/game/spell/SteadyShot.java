package com.mideas.rpg.v2.game.spell;

import com.mideas.rpg.v2.Sprites;

public class SteadyShot extends Spell {

	private static int cd;
	
	public SteadyShot() {
		super(900, 900, 400, 0, 0, 0, 1, 0, 203);
		name = "SteadyShot";
		sprite = Sprites.spell_steady_shot;
	}

	public void setSpellCd(int number) {
		cd = number;
	}
	
	public int getSpellCd() {
		return cd;
	}
}
