package com.mideas.rpg.v2.game.spell.list;

import com.mideas.rpg.v2.Sprites;
import com.mideas.rpg.v2.game.spell.Spell;

public class Ambush extends Spell {

	private static int cd;
	
	public Ambush() {
		super(6000, 6000, 3000, 0, 0, 0, 4, 0, 701);
		name = "Ambush";
		sprite = Sprites.spell_ambush;
	}
	
	public void setSpellCd(int number) {
		cd = number;
	}
	
	public int getSpellCd() {
		return cd;
	}
}