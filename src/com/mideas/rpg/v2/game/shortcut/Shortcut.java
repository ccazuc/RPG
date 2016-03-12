package com.mideas.rpg.v2.game.shortcut;

import java.io.FileNotFoundException;
import java.sql.SQLException;

import org.newdawn.slick.opengl.Texture;

public interface Shortcut {

	abstract Texture getSprite();
	abstract boolean use(Shortcut shortcut) throws SQLException, FileNotFoundException;
	abstract void setCd(int id, int cd);
	abstract ShortcutType getShortcutType();
	
}
