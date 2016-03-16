package com.mideas.rpg.v2.game.spell.list;

import com.mideas.rpg.v2.Sprites;
import com.mideas.rpg.v2.game.spell.Spell;

public class ArcaneShot extends Spell {

	private static int cd;
	
	public ArcaneShot() {
		super(4000, 4000, 3000, 0, 0, 0, 4, 0, 201);
		name = "ArcaneShot";
		sprite = Sprites.spell_arcane_shot;
	}

	public void setSpellCd(int number) {
		cd = number;
	}
	
	public int getSpellCd() {
		return cd;
	}
}
