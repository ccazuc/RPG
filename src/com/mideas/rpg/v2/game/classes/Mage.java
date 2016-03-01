package com.mideas.rpg.v2.game.classes;

import com.mideas.rpg.v2.game.Joueur;
import com.mideas.rpg.v2.game.item.stuff.Stuff;
import com.mideas.rpg.v2.game.spell.Fireball;
import com.mideas.rpg.v2.game.spell.FlameShock;
import com.mideas.rpg.v2.game.spell.Pyroblast;
import com.mideas.rpg.v2.game.spell.Spell;

public class Mage extends Joueur {

	public static final int MAX_HEALTH = 7000;
	public static final int MAX_MANA = 8000;
	
	public Mage() {
		super(7000, 300, 80, 80, 80, 5, 8000, new Spell[49], new Spell[49], new Stuff[21], "Mage", 3, 7000, 8000, 0, 1200, 1250, 0);
		setSpells(0, new Fireball());
		setSpells(1, new FlameShock());
		setSpells(2, new Pyroblast());
		setSpellUnlocked(0, new Fireball());
		setSpellUnlocked(1, new FlameShock());
		setSpellUnlocked(2, new Pyroblast());
	}
	
}
