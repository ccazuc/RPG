package com.mideas.rpg.v2.game.shortcut;

import java.sql.SQLException;

import org.newdawn.slick.opengl.Texture;

import com.mideas.rpg.v2.game.Joueur;

public interface Shortcut {

	abstract Texture getSprite();
	abstract boolean use(Shortcut shortcut) throws SQLException;
	abstract void setCd(int id, int cd);
	
}
