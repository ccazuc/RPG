package com.mideas.rpg.v2.game.classes;

import com.mideas.rpg.v2.game.Joueur;
import com.mideas.rpg.v2.game.item.stuff.Stuff;
import com.mideas.rpg.v2.game.spell.ArcaneShot;
import com.mideas.rpg.v2.game.spell.MultiShot;
import com.mideas.rpg.v2.game.spell.Spell;
import com.mideas.rpg.v2.game.spell.SteadyShot;

public class Illidan extends Joueur {

	public static final int MAX_HEALTH = 70000;
	public static final int MAX_MANA = 60000;
	
	public Illidan() {
		super(70000, 1500, 150, 150, 150, 5, 60000, new Spell[49], new Spell[49], new Stuff[21], "Illidan", 6, 70000, 60000, 0, 20000, 40050, 0);
		setSpells(0, new ArcaneShot());
		setSpells(1, new SteadyShot());
		setSpells(2, new MultiShot());
	}
}
