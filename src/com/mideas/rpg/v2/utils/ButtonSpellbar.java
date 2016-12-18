package com.mideas.rpg.v2.utils;

import org.lwjgl.input.Keyboard;

import com.mideas.rpg.v2.FontManager;
import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.Sprites;
import com.mideas.rpg.v2.TTF;
import com.mideas.rpg.v2.game.shortcut.PotionShortcut;
import com.mideas.rpg.v2.game.shortcut.Shortcut;
import com.mideas.rpg.v2.game.shortcut.ShortcutType;
import com.mideas.rpg.v2.hud.SpellBarFrame;

public class ButtonSpellbar {

	private int x;
	private int y;
	private boolean buttonHover;
	private Shortcut shortcut;
	private boolean itemIsEquipped;
	private int numberItem;
	private String numberItemString;
	private int numberItemStringWidth;
	private int spellCooldownStart;
	private int spellCooldownEnd;
	private String keyBindString;
	private int keyBindStringWidth;
	private final static TTF numberItemFont = FontManager.get("FRIZQT", 15);
	private final static TTF keyBindFont = FontManager.get("FRIZQT", 15);
	private int keyBind;
	
	public ButtonSpellbar(float x, float y) {
		this.x = (int)x;
		this.y = (int)y;
	}
	
	public void draw() {
		if(this.shortcut == null) {
			return;
		}
		Draw.drawQuad(this.shortcut.getSprite(), this.x, this.y);
		if(this.itemIsEquipped) {
			Draw.drawQuad(Sprites.spell_hover, this.x, this.y-1, 39*Mideas.getDisplayXFactor(), 37*Mideas.getDisplayYFactor(), Color.GREEN);
		}
		if(this.numberItemString != null) {
			keyBindFont.drawStringShadow(this.x+30*Mideas.getDisplayXFactor()-this.keyBindStringWidth, this.y, this.numberItemString, Color.WHITE, Color.BLACK, 1, 0, 0);
		}
		if(this.numberItem >= 0) {
			numberItemFont.drawStringShadow(this.x+37*Mideas.getDisplayXFactor()-this.numberItemStringWidth, this.y+16*Mideas.getDisplayYFactor(), this.numberItemString, Color.WHITE, Color.BLACK, 1, 0, 0);
		}
	}
	
	public boolean mouseEvent() {
		if(Mideas.getHover() && Mideas.mouseX() >= this.x && Mideas.mouseX() <= this.x+this.shortcut.getSprite().getImageWidth()*Mideas.getDisplayXFactor() && Mideas.mouseY() >= this.y && Mideas.mouseY() <= this.y+this.shortcut.getSprite().getImageHeight()*Mideas.getDisplayYFactor()) {
			this.buttonHover = true;
			Mideas.setHover(false);
		}
		else {
			this.buttonHover = false;
		}
		return false;
	}
	
	public boolean keyEvent() {
		if(Keyboard.getEventKey() == this.keyBind) {
			this.shortcut.use();
			return true;
		}
		return false;
	}
	
	public void setKeyBind(int value) {
		this.keyBind = value;
		this.keyBindString = Keyboard.getKeyName(this.keyBind);
		this.keyBindStringWidth = keyBindFont.getWidth(this.keyBindString);
	}
	
	public void setShortcut(Shortcut shortcut) {
		this.shortcut = shortcut;
		if(this.shortcut == null) {
			return;
		}
		if(this.shortcut.getShortcutType() == ShortcutType.STUFF) {
			this.itemIsEquipped = SpellBarFrame.isEquippedItem(this.shortcut.getId());
			return;
		}
		this.itemIsEquipped = false;
		if(this.shortcut.getShortcutType() == ShortcutType.POTION) {
			this.numberItem = ((PotionShortcut)this.shortcut).getPotion().getAmount();
			this.numberItemString = ((PotionShortcut)this.shortcut).getPotion().getAmountString();
			this.numberItemStringWidth = numberItemFont.getWidth(this.numberItemString);
			return;
		}
		this.numberItem = -1;
	}
	
	public Shortcut getShortcut() {
		return this.shortcut;
	}
}
