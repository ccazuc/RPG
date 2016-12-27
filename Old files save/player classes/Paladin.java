package com.mideas.rpg.v2.game.classes;

import com.mideas.rpg.v2.game.Joueur;
import com.mideas.rpg.v2.game.item.stuff.Stuff;
import com.mideas.rpg.v2.game.shortcut.SpellShortcut;
import com.mideas.rpg.v2.game.spell.Spell;
import com.mideas.rpg.v2.game.spell.SpellManager;
import com.mideas.rpg.v2.game.spell.list.CrusaderStrike;
import com.mideas.rpg.v2.game.spell.list.Judgment;
import com.mideas.rpg.v2.game.spell.list.LayOnHands;

public class Paladin extends Joueur {

	public static final int MAX_HEALTH = 16000;
	public static final int MAX_MANA = 3000;
	
	public Paladin() {
		super(16000, 450, 190, 190, 190, 5, 3000, new SpellShortcut[49], new Spell[49], new Stuff[21], "Paladin", 8, 16000, 3000, 1, 2000, 1450, 0);
		//setSpells(0, new LayOnHands());
		//setSpells(1, new CrusaderStrike());
		//setSpells(2, new Judgment());
		setSpells(0, SpellManager.getShortcutSpell(501));
		setSpells(1, SpellManager.getShortcutSpell(502));
		setSpells(2, SpellManager.getShortcutSpell(503));
		setSpellUnlocked(0, new LayOnHands());
		setSpellUnlocked(1, new CrusaderStrike());
		setSpellUnlocked(2, new Judgment());
	}	
}
