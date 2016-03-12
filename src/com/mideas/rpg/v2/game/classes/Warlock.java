package com.mideas.rpg.v2.game.classes;

import com.mideas.rpg.v2.game.Joueur;
import com.mideas.rpg.v2.game.item.stuff.Stuff;
import com.mideas.rpg.v2.game.shortcut.SpellShortcut;
import com.mideas.rpg.v2.game.spell.Spell;
import com.mideas.rpg.v2.game.spell.SpellManager;
import com.mideas.rpg.v2.game.spell.list.Corruption;
import com.mideas.rpg.v2.game.spell.list.Immolation;
import com.mideas.rpg.v2.game.spell.list.ShadowBolt;

public class Warlock extends Joueur {

	public static final int MAX_HEALTH = 9000;
	public static final int MAX_MANA = 8000;
	
	public Warlock() {
		super(9000, 100, 60, 60, 60, 5, 8000, new SpellShortcut[49], new Spell[49], new Stuff[21], "Warlock", 10, 9000, 8000, 0, 900, 1050, 0);
		//setSpells(0, new Corruption());
		//setSpells(1, new ShadowBolt());
		//setSpells(2, new Immolation());
		setSpells(0, SpellManager.getShortcutSpell(901));
		setSpells(1, SpellManager.getShortcutSpell(902));
		setSpells(2, SpellManager.getShortcutSpell(903));
		setSpellUnlocked(0, new Corruption());
		setSpellUnlocked(1, new ShadowBolt());
		setSpellUnlocked(2, new Immolation());
	}
}
