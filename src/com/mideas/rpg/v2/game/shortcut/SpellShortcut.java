package com.mideas.rpg.v2.game.shortcut;

import java.sql.SQLException;

import org.newdawn.slick.opengl.Texture;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.game.IconsManager;
import com.mideas.rpg.v2.game.Joueur;
import com.mideas.rpg.v2.game.spell.Spell;
import com.mideas.rpg.v2.game.spell.SpellManager;
import com.mideas.rpg.v2.game.spell.SpellType;
import com.mideas.rpg.v2.hud.SpellBarFrame;

public class SpellShortcut implements Shortcut {
	
	private Spell spell;
	
	public SpellShortcut(Spell spell) {
		this.spell = spell;
		
	}
	
	public Spell getSpell() {
		return spell;
	}
	
	public boolean use(Shortcut spell) throws SQLException {
		if(Mideas.joueur1().cast(((SpellShortcut)spell).getSpell())) {
			return true;
		}
		return false;
	}
	
	public Texture getSprite() {
		return IconsManager.getSprite54(spell.getSpriteId());
	}
	
	public void setCd(int id, int cd) {
		SpellManager.setCd(id, cd);
	}
}
