package com.mideas.rpg.v2.game.classes;

import com.mideas.rpg.v2.game.Joueur;
import com.mideas.rpg.v2.game.item.stuff.Stuff;
import com.mideas.rpg.v2.game.shortcut.SpellShortcut;
import com.mideas.rpg.v2.game.spell.Spell;
import com.mideas.rpg.v2.game.spell.SpellManager;
import com.mideas.rpg.v2.game.spell.list.Ambush;
import com.mideas.rpg.v2.game.spell.list.Eviscerate;
import com.mideas.rpg.v2.game.spell.list.SinisterStrike;

public class Rogue extends Joueur {

	public static final int MAX_HEALTH = 6000;
	public static final int MAX_MANA = 4000;
	
	public Rogue() {
		super(6000, 700, 120, 120, 120, 20, 4000, new SpellShortcut[49], new Spell[49], new Stuff[21], "Rogue", 9, 6000, 4000, 0, 1500, 1050, 0);
		//setSpells(0, new Ambush());
		//setSpells(1, new Eviscerate());
		//setSpells(2, new SinisterStrike());
		setSpells(0, SpellManager.getShortcutSpell(701));
		setSpells(1, SpellManager.getShortcutSpell(702));
		setSpells(2, SpellManager.getShortcutSpell(703));
		setSpellUnlocked(0, new Ambush());
		setSpellUnlocked(1, new Eviscerate());
		setSpellUnlocked(2, new SinisterStrike());
	}
}