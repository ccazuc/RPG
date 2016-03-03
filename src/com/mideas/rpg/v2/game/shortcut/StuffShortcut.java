package com.mideas.rpg.v2.game.shortcut;

import org.newdawn.slick.opengl.Texture;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.game.IconsManager;
import com.mideas.rpg.v2.game.item.stuff.Stuff;
import com.mideas.rpg.v2.hud.DragManager;

public class StuffShortcut implements Shortcut {

	private Stuff stuff;
	
	public Texture getSprite() {
		return IconsManager.getSprite42(stuff.getSpriteId());
	}
	
	public void use() {
		int i = 0;
		while(i < Mideas.joueur1().getStuff().length) {
			if(stuff.getType() == DragManager.getStuffType(i) && Mideas.joueur1().getStuff(i) == null) {
				Mideas.joueur1().setStuff(i, stuff);
			}
			i++;
		}
	}
	
	public void setCd(int id, int cd) {
	}
}
