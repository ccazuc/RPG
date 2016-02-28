package com.mideas.rpg.v2.game;

import com.mideas.rpg.v2.game.spell.ArcaneShot;
import com.mideas.rpg.v2.game.spell.MultiShot;
import com.mideas.rpg.v2.game.spell.Spell;
import com.mideas.rpg.v2.game.spell.SteadyShot;
import com.mideas.rpg.v2.game.stuff.Stuff;

public class Hunter extends Joueur {

	public static final int MAX_HEALTH = 7000;
	public static final int MAX_MANA = 6000;
	
	public Hunter() {
		super(7000, 100, 150, 150, 150, 5, 6000, new Spell[49], new Spell[49], new Stuff[21], "Hunter", 6, 7000, 6000, 0, 1100, 950, 0);
		setSpells(0, new ArcaneShot());
		setSpells(1, new SteadyShot());
		setSpells(2, new MultiShot());
		setSpellUnlocked(0, new ArcaneShot());
		setSpellUnlocked(1, new SteadyShot());
		setSpellUnlocked(2, new MultiShot());
	}
	
}
