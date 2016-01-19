package com.mideas.rpg.v2.game.spell;

import com.mideas.rpg.v2.Sprites;

public class DeathCoil extends Spell {

	private static int cd;
	
	public DeathCoil() {
		super(3800, 3800, 800, 0, 0, 0, 5, 0, 1);
		name = "DeathCoil";
		sprite = Sprites.spell_death_coil;
	}

	public void setSpellCd(int number) {
		cd = number;
	}
	
	public int getSpellCd() {
		return cd;
	}
}
