package com.mideas.rpg.v2.game.shortcut;

import com.mideas.rpg.v2.game.item.Item;
import com.mideas.rpg.v2.render.Texture;

public interface Shortcut {

	abstract Texture getSprite();
	abstract boolean use();
	abstract void setCd(int id, int cd);
	abstract ShortcutType getShortcutType();
	abstract int getId();
	abstract boolean getIsLoaded();	
	abstract void setIsLoaded(boolean we);	
	abstract Item getItem();
}
