package com.mideas.rpg.v2.game.spell.list;

import com.mideas.rpg.v2.Sprites;
import com.mideas.rpg.v2.game.spell.Spell;

public class Fireball extends Spell {

	private static int cd;
	
	public Fireball() {
		super(500, 500, 300, 0, 0, 0, 2, 0, 301);
		name = "Fireball";
		sprite = Sprites.spell_fireball;
	}

	public void setSpellCd(int number) {
		cd = number;
	}
	
	public int getSpellCd() {
		return cd;
	}
} 