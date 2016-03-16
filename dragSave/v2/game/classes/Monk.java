package com.mideas.rpg.v2.game.classes;

import com.mideas.rpg.v2.game.Joueur;
import com.mideas.rpg.v2.game.item.stuff.Stuff;
import com.mideas.rpg.v2.game.shortcut.SpellShortcut;
import com.mideas.rpg.v2.game.spell.Spell;
import com.mideas.rpg.v2.game.spell.SpellManager;
import com.mideas.rpg.v2.game.spell.list.TigerPalm;
import com.mideas.rpg.v2.game.spell.list.TigerStrikes;
import com.mideas.rpg.v2.game.spell.list.TouchOfDeath;

public class Monk extends Joueur {

	public static final int MAX_HEALTH = 9000;
	public static final int MAX_MANA = 3000;
	
	public Monk() {
		super(9000, 100, 220, 220, 220, 5, 3000, new SpellShortcut[49], new Spell[49], new Stuff[21], "Monk", 7, 9000, 3000, 0, 1000, 1320, 0);
		//setSpells(0, new TouchOfDeath());
		//setSpells(1, new TigerStrikes());
		//setSpells(2, new TigerPalm());
		setSpells(0, SpellManager.getShortcutSpell(401));
		setSpells(1, SpellManager.getShortcutSpell(402));
		setSpells(2, SpellManager.getShortcutSpell(403));
		setSpellUnlocked(0, new TouchOfDeath());
		setSpellUnlocked(1, new TigerStrikes());
		setSpellUnlocked(2, new TigerPalm());
	}
}
