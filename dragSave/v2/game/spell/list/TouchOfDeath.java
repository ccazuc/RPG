package com.mideas.rpg.v2.game.spell.list;

import com.mideas.rpg.v2.Sprites;
import com.mideas.rpg.v2.game.spell.Spell;

public class TouchOfDeath extends Spell {

	private static int cd;
	
	public TouchOfDeath() {
		super(7000, 7000, 2000, 0, 0, 0, 5, 0, 403);
		name = "TouchOfDeath";
		sprite = Sprites.spell_touch_of_death;
	}

	public void setSpellCd(int number) {
		cd = number;
	}
	
	public int getSpellCd() {
		return cd;
	}
}