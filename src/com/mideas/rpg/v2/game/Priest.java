package com.mideas.rpg.v2.game;

import com.mideas.rpg.v2.game.spell.FlashHeal;
import com.mideas.rpg.v2.game.spell.HolyNova;
import com.mideas.rpg.v2.game.spell.Penance;
import com.mideas.rpg.v2.game.spell.Spell;
import com.mideas.rpg.v2.game.stuff.Stuff;

public class Priest extends Joueur {

	public static final int MAX_HEALTH = 6000;
	public static final int MAX_MANA = 8000;
	
	public Priest() {
		super(6000, 100, 70, 70, 70, 5, 8000, new Spell[49], new Spell[49], new Stuff[21], "Priest", 2, 6000, 8000, 1, 700, 850);
		setSpells(0, new FlashHeal());
		setSpells(1, new HolyNova());
		setSpells(2, new Penance());
		setSpellUnlocked(0, new FlashHeal());
		setSpellUnlocked(1, new HolyNova());
		setSpellUnlocked(2, new Penance());
	}
	
}
