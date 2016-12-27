package com.mideas.rpg.v2.game.classes;

import com.mideas.rpg.v2.game.Joueur;
import com.mideas.rpg.v2.game.item.stuff.Stuff;
import com.mideas.rpg.v2.game.shortcut.SpellShortcut;
import com.mideas.rpg.v2.game.spell.Spell;
import com.mideas.rpg.v2.game.spell.SpellManager;
import com.mideas.rpg.v2.game.spell.list.ArcaneShot;
import com.mideas.rpg.v2.game.spell.list.MultiShot;
import com.mideas.rpg.v2.game.spell.list.SteadyShot;

public class Hunter extends Joueur {

	public static final int MAX_HEALTH = 7000;
	public static final int MAX_MANA = 6000;
	
	public Hunter() {
		super(7000, 100, 150, 150, 150, 5, 6000, new SpellShortcut[49], new Spell[49], new Stuff[21], "Hunter", 6, 7000, 6000, 0, 1100, 950, 0);
		setSpells(0, SpellManager.getShortcutSpell(201));
		setSpells(1, SpellManager.getShortcutSpell(203));
		setSpells(2, SpellManager.getShortcutSpell(202));
		setSpellUnlocked(0, new ArcaneShot());
		setSpellUnlocked(1, new SteadyShot());
		setSpellUnlocked(2, new MultiShot());
	}
}
