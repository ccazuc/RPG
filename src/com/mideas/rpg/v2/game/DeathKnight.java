package com.mideas.rpg.v2.game;

import com.mideas.rpg.v2.game.spell.DeathCoil;
import com.mideas.rpg.v2.game.spell.DeathStrike;
import com.mideas.rpg.v2.game.spell.PlagueStrike;
import com.mideas.rpg.v2.game.spell.Spell;
import com.mideas.rpg.v2.game.stuff.Stuff;

public class DeathKnight extends Joueur {

	public static final int MAX_HEALTH = 15000;
	public static final int MAX_MANA = 2000;
	
	public DeathKnight() {
		super(15000, 300, 100, 100, 100, 5, 2000, new Spell[49], new Spell[49], new Stuff[21], "DeathKnight", 5, 15000, 2000, 0, 1500, 1550);
		setSpells(0, new DeathStrike());
		setSpells(1, new PlagueStrike());
		setSpells(2, new DeathCoil());
		setSpellUnlocked(0, new DeathStrike());
		setSpellUnlocked(1, new PlagueStrike());
		setSpellUnlocked(2, new DeathCoil());
	}
	
}
