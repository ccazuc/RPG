package com.mideas.rpg.v2.game.shortcut;

import com.mideas.rpg.v2.command.spell.CommandCast;
import com.mideas.rpg.v2.game.IconsManager;
import com.mideas.rpg.v2.game.item.Item;
import com.mideas.rpg.v2.game.spell.Spell;
import com.mideas.rpg.v2.game.spell.SpellManager;
import com.mideas.rpg.v2.render.Texture;

public class SpellShortcut implements Shortcut {
	
	private Spell spell;
	private ShortcutType type;
	private boolean isLoaded;
	
	public SpellShortcut(Spell spell) {
		this.spell = spell;
		this.type = ShortcutType.SPELL;
	}
	
	@Override
	public boolean use() {
		//(((SpellShortcut)spell).getSpell()).action(Mideas.joueur1(), Mideas.target());
		CommandCast.cast(this.spell.getSpellId());
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
		//SpellManager.setCd(id, cd);
	}
	
	@Override
	public int getId() {
		return this.spell.getSpellId();
	}
	
	@Override
	public Item getItem() {
		return null;
	}
	
	public Spell getSpell() {
		return this.spell;
	}
	
	@Override
	public ShortcutType getShortcutType() {
		return this.type;
	}
}
