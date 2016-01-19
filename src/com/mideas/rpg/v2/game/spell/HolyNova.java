package com.mideas.rpg.v2.game.spell;

import com.mideas.rpg.v2.Sprites;

public class HolyNova extends Spell {

	private static int cd;
	
	public HolyNova() {
		super(800, 800, 750, 0, 0, 0, 2, 0, 602);
		name = "Holy Nova";
		sprite = Sprites.spell_holy_nova;
	}

	public void setSpellCd(int number) {
		cd = number;
	}
	
	public int getSpellCd() {
		return cd;
	}
}
