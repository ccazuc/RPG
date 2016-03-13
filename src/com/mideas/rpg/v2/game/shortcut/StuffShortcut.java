package com.mideas.rpg.v2.game.shortcut;

import java.io.FileNotFoundException;
import java.sql.SQLException;

import org.newdawn.slick.opengl.Texture;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.game.CharacterStuff;
import com.mideas.rpg.v2.game.IconsManager;
import com.mideas.rpg.v2.game.item.stuff.Stuff;
import com.mideas.rpg.v2.hud.DragManager;

public class StuffShortcut implements Shortcut {

	private Stuff stuff;
	private ShortcutType type;

	public StuffShortcut(Stuff stuff) {
		this.stuff = stuff;
		this.type = ShortcutType.STUFF;
	}
	
	public boolean use(Shortcut shortcut) throws FileNotFoundException, SQLException {
		int i = 0;
		while(i < Mideas.joueur1().getStuff().length) {
			if(stuff.getType() == DragManager.getStuffType(i) && Mideas.joueur1().getStuff(i) == null) {
				Mideas.joueur1().setStuff(i, stuff);
				DragManager.calcStats(stuff);
				DragManager.setNullContainer(stuff);
				CharacterStuff.setBagItems();
				CharacterStuff.setEquippedItems();
				return true;
			}
			i++;
		}
		return false;
	}
	
	
	public Texture getSprite() {
		return IconsManager.getSprite47(stuff.getSpriteId());
	}

	public void setCd(int id, int cd) {
	}
	
	public Stuff getStuff() {
		return stuff;
	}
	
	public ShortcutType getShortcutType() {
		return type;
	}
}
