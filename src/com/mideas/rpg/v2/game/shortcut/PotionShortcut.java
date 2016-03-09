package com.mideas.rpg.v2.game.shortcut;

import java.io.FileNotFoundException;
import java.sql.SQLException;

import org.newdawn.slick.opengl.Texture;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.game.IconsManager;
import com.mideas.rpg.v2.game.item.potion.Potion;
import com.mideas.rpg.v2.hud.SpellBarFrame;

public class PotionShortcut {
	
	Potion potion;
	
	public void use() throws SQLException, FileNotFoundException {
		SpellBarFrame.doHealingPotion(potion, Mideas.joueur1().getNumberItem(potion));
	}
	
	public Texture getSprite() {
		return IconsManager.getSprite42(potion.getSpriteId());
	}
	
	public void setCd(int id, int cd) {
	}
}
