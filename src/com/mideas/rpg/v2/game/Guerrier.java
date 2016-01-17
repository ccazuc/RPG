package com.mideas.rpg.v2.game;

import com.mideas.rpg.v2.game.spell.HeroicStrike;
import com.mideas.rpg.v2.game.spell.Spell;
import com.mideas.rpg.v2.game.stuff.Stuff;

public class Guerrier extends Joueur {
	
	public static final int MAX_HEALTH = 10000;
	public static final int MAX_MANA = 1500;
	
	public Guerrier() {
		super(10000, 10, 200, 200, 200, 5, 1500, /*new Spell[]{new HeroicStrike(), new Charge(), new MortalStrike()}*/ new Spell[49], new Spell[49], new Stuff[21], "Guerrier", 1, 10000, 1500, 0, 800, 1100);
		setSpells(0, new HeroicStrike());
		//spells[1] = new Charge();
		//spells[2] = new MortalStrike();
		setSpellUnlocked(0, new HeroicStrike());
		//spellUnlocked[1] = new Charge();
		//spellUnlocked[2] = new MortalStrike();
	}	
}