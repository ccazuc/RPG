package com.mideas.rpg.v2.game.shortcut;

import com.mideas.rpg.v2.utils.Texture;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.game.IconsManager;
import com.mideas.rpg.v2.game.item.potion.Potion;
import com.mideas.rpg.v2.hud.SpellBarFrame;

public class PotionShortcut implements Shortcut {
	
	private Potion potion;
	private ShortcutType type;
	private boolean isLoaded;
	
	public PotionShortcut(Potion potion) {
		this.potion = potion;
		this.type = ShortcutType.POTION;
	}
	
	@Override
	public boolean use(Shortcut potion) {
		int i = 0;
		while(i < Mideas.joueur1().bag().getBag().length) {
			if(Mideas.joueur1().bag().getBag(i) != null && Mideas.joueur1().bag().getBag(i).getId() == ((PotionShortcut)potion).getId()) {
				SpellBarFrame.doHealingPotion((Potion)Mideas.joueur1().bag().getBag(i));
				return true;
			}
			i++;
		}
		return true;
	}
	
	@Override
	public Texture getSprite() {
		return IconsManager.getSprite37(this.potion.getSpriteId());
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
	}
	
	@Override
	public int getId() {
		return this.potion.getId();
	}
	
	public Potion getPotion() {
		return this.potion;
	}
	
	@Override
	public ShortcutType getShortcutType() {
		return this.type;
	}
}
