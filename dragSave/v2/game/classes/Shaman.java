package com.mideas.rpg.v2.game.classes;

import com.mideas.rpg.v2.game.Joueur;
import com.mideas.rpg.v2.game.item.stuff.Stuff;
import com.mideas.rpg.v2.game.shortcut.SpellShortcut;
import com.mideas.rpg.v2.game.spell.Spell;
import com.mideas.rpg.v2.game.spell.SpellManager;
import com.mideas.rpg.v2.game.spell.list.ChainLightning;
import com.mideas.rpg.v2.game.spell.list.HealingSurge;
import com.mideas.rpg.v2.game.spell.list.LightningBolt;

public class Shaman extends Joueur {

	public static final int MAX_HEALTH = 8000;
	public static final int MAX_MANA = 4000;
	
	public Shaman() {
		super(8000, 400, 80, 80, 80, 20, 4000, new SpellShortcut[49], new Spell[49], new Stuff[21], "Shaman", 4, 8000, 4000, 1, 1000, 1000, 0);
		//setSpells(0, new HealingSurge());
		//setSpells(1, new ChainLightning());
		//setSpells(2, new LightningBolt());
		setSpells(0, SpellManager.getShortcutSpell(801));
		setSpells(1, SpellManager.getShortcutSpell(802));
		setSpells(2, SpellManager.getShortcutSpell(803));
		setSpellUnlocked(0, new HealingSurge());
		setSpellUnlocked(1, new ChainLightning());
		setSpellUnlocked(2, new LightningBolt());
	}
}