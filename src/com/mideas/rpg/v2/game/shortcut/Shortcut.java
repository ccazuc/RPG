package com.mideas.rpg.v2.game.shortcut;

import java.io.FileNotFoundException;
import java.sql.SQLException;

import com.mideas.rpg.v2.utils.Texture;

public interface Shortcut {

	abstract Texture getSprite();
	abstract boolean use(Shortcut shortcut) throws SQLException, FileNotFoundException;
	abstract void setCd(int id, int cd);
	abstract ShortcutType getShortcutType();
	abstract int getId();
	
}
