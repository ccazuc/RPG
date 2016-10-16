package com.mideas.rpg.v2.game.shortcut;

import com.mideas.rpg.v2.utils.Texture;

import com.mideas.rpg.v2.command.CommandSpellCast;
import com.mideas.rpg.v2.game.IconsManager;
import com.mideas.rpg.v2.game.spell.Spell;
import com.mideas.rpg.v2.game.spell.SpellManager;

public class SpellShortcut implements Shortcut {
	
	private Spell spell;
	private ShortcutType type;
	private boolean isLoaded;
	
	public SpellShortcut(Spell spell) {
		this.spell = spell;
		this.type = ShortcutType.SPELL;
	}
	
	
	@Override
	public boolean use(Shortcut spell) {
		//(((SpellShortcut)spell).getSpell()).action(Mideas.joueur1(), Mideas.target());
		CommandSpellCast.write(((SpellShortcut)spell).getSpell());
		return true;
	}
	
	@Override
	public Texture getSprite() {
		return IconsManager.getSprite37(this.spell.getSpriteId());
	}
	
	@Override
	public boolean getIsLoaded() {
		return this.isLoaded;
	}
	
	@Override
	public void setIsLoaded(boolean we) {
		this.isLoaded = we;
	}
	
	@Override
	public void setCd(int id, int cd) {
		SpellManager.setCd(id, cd);
	}
	
	@Override
	public int getId() {
		return this.spell.getSpellId();
	}
	
	public Spell getSpell() {
		return this.spell;
	}
	
	@Override
	public ShortcutType getShortcutType() {
		return this.type;
	}
}
