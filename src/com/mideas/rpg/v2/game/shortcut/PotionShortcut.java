package com.mideas.rpg.v2.game.shortcut;

import java.io.FileNotFoundException;
import java.sql.SQLException;

import org.newdawn.slick.opengl.Texture;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.game.IconsManager;
import com.mideas.rpg.v2.game.item.potion.Potion;
import com.mideas.rpg.v2.hud.SpellBarFrame;

public class PotionShortcut implements Shortcut {
	
	private Potion potion;
	private ShortcutType type;
	
	public PotionShortcut(Potion potion) {
		this.potion = potion;
		this.type = ShortcutType.POTION;
	}
	
	public boolean use(Shortcut potion) throws SQLException, FileNotFoundException {
		//SpellBarFrame.doHealingPotion(((PotionShortcut)potion).getPotion());
		int i = 0;
		while(i < Mideas.bag().getBag().length) {
			if(Mideas.bag().getBag(i) != null && Mideas.bag().getBag(i).getId() == ((PotionShortcut)potion).getId()) {
				SpellBarFrame.doHealingPotion((Potion)Mideas.bag().getBag(i));
				return true;
			}
			i++;
		}
		return true;
	}
	
	public Texture getSprite() {
		return IconsManager.getSprite47(potion.getSpriteId());
	}
	
	public void setCd(int id, int cd) {
	}
	
	public int getId() {
		return potion.getId();
	}
	
	public Potion getPotion() {
		return potion;
	}
	
	public ShortcutType getShortcutType() {
		return type;
	}
}
