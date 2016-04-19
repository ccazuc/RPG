package com.mideas.rpg.v2.game.shortcut;

import java.sql.SQLException;

import org.newdawn.slick.opengl.Texture;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.game.IconsManager;
import com.mideas.rpg.v2.game.spell.Spell;
import com.mideas.rpg.v2.game.spell.SpellManager;
import com.mideas.rpg.v2.hud.SpellBarFrame;

public class SpellShortcut implements Shortcut {
	
	private Spell spell;
	private ShortcutType type;
	
	public SpellShortcut(Spell spell) {
		this.spell = spell;
		this.type = ShortcutType.SPELL;
	}
	
	
	public boolean use(Shortcut spell) throws SQLException {
		if(Mideas.joueur1().cast(((SpellShortcut)spell).getSpell())) {
			SpellBarFrame.setIsCastingSpell(false);
			return true;
		}
		return false;
	}
	
	public Texture getSprite() {
		return IconsManager.getSprite47(spell.getSpriteId());
	}
	
	public void setCd(int id, int cd) {
		SpellManager.setCd(id, cd);
	}
	
	public int getId() {
		return spell.getSpellId();
	}
	
	public Spell getSpell() {
		return spell;
	}
	
	public ShortcutType getShortcutType() {
		return type;
	}
}
