package com.mideas.rpg.v2.game;

import com.mideas.rpg.v2.game.spell.Ambush;
import com.mideas.rpg.v2.game.spell.Eviscerate;
import com.mideas.rpg.v2.game.spell.SinisterStrike;
import com.mideas.rpg.v2.game.spell.Spell;
import com.mideas.rpg.v2.game.stuff.Stuff;

public class Rogue extends Joueur {

	public static final int MAX_HEALTH = 6000;
	public static final int MAX_MANA = 4000;
	
	public Rogue() {
		super(6000, 700, 120, 120, 120, 20, 4000, new Spell[49], new Spell[49], new Stuff[21], "Rogue", 9, 6000, 4000, 0, 1500, 1050, 0);
		setSpells(0, new Ambush());
		setSpells(1, new Eviscerate());
		setSpells(2, new SinisterStrike());
		setSpellUnlocked(0, new Ambush());
		setSpellUnlocked(1, new Eviscerate());
		setSpellUnlocked(2, new SinisterStrike());
	}
	
}