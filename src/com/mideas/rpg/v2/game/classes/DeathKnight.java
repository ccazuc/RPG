package com.mideas.rpg.v2.game.classes;

import com.mideas.rpg.v2.game.Joueur;
import com.mideas.rpg.v2.game.item.stuff.Stuff;
import com.mideas.rpg.v2.game.shortcut.SpellShortcut;
import com.mideas.rpg.v2.game.spell.Spell;
import com.mideas.rpg.v2.game.spell.SpellManager;
import com.mideas.rpg.v2.game.spell.list.DeathCoil;
import com.mideas.rpg.v2.game.spell.list.DeathStrike;
import com.mideas.rpg.v2.game.spell.list.PlagueStrike;

public class DeathKnight extends Joueur {

	public static final int MAX_HEALTH = 15000;
	public static final int MAX_MANA = 2000;
	
	public DeathKnight() {
		super(15000, 300, 100, 100, 100, 5, 2000, new SpellShortcut[49], new Spell[49], new Stuff[21], "DeathKnight", 5, 15000, 2000, 0, 1500, 1550, 0);
		setSpells(0, SpellManager.getShortcutSpell(2));
		setSpells(1, SpellManager.getShortcutSpell(3));
		setSpells(2, SpellManager.getShortcutSpell(1));
		setSpellUnlocked(0, new DeathStrike());
		setSpellUnlocked(1, new PlagueStrike());
		setSpellUnlocked(2, new DeathCoil());
	}
}