package com.mideas.rpg.v2.game;

import com.mideas.rpg.v2.game.spell.Spell;
import com.mideas.rpg.v2.game.spell.TigerPalm;
import com.mideas.rpg.v2.game.spell.TigerStrikes;
import com.mideas.rpg.v2.game.spell.TouchOfDeath;
import com.mideas.rpg.v2.game.stuff.Stuff;

public class Monk extends Joueur {

	public static final int MAX_HEALTH = 9000;
	public static final int MAX_MANA = 3000;
	
	public Monk() {
		super(9000, 100, 220, 220, 220, 5, 3000, new Spell[49], new Spell[49], new Stuff[21], "Monk", 7, 9000, 3000, 0, 1000, 1320, 0);
		setSpells(0, new TouchOfDeath());
		setSpells(1, new TigerStrikes());
		setSpells(2, new TigerPalm());
		setSpellUnlocked(0, new TouchOfDeath());
		setSpellUnlocked(1, new TigerStrikes());
		setSpellUnlocked(2, new TigerPalm());
	}
	
}
