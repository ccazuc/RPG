package com.mideas.rpg.v2.utils;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import com.mideas.rpg.v2.FontManager;
import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.Sprites;
import com.mideas.rpg.v2.TTF;
import com.mideas.rpg.v2.game.item.potion.Potion;
import com.mideas.rpg.v2.game.item.stuff.Stuff;
import com.mideas.rpg.v2.game.shortcut.PotionShortcut;
import com.mideas.rpg.v2.game.shortcut.Shortcut;
import com.mideas.rpg.v2.game.shortcut.ShortcutType;
import com.mideas.rpg.v2.game.shortcut.SpellShortcut;
import com.mideas.rpg.v2.game.shortcut.StuffShortcut;
import com.mideas.rpg.v2.hud.DragManager;
import com.mideas.rpg.v2.hud.DragSpellManager;
import com.mideas.rpg.v2.hud.SpellBarFrame;

public class ButtonSpellbar {

	private int x;
	private int y;
	private boolean buttonHover;
	private boolean buttonDown;
	private int buttonDownX;
	private int buttonDownY;
	private boolean keyDown;
	private Shortcut shortcut;
	private boolean itemIsEquipped;
	private boolean itemIsInBag;
	private int numberItem;
	private String numberItemString;
	private int numberItemStringWidth;
	private int spellCooldownStart;
	private int spellCooldownEnd;
	private boolean isInFirstBar;
	private String keyBindString;
	private int keyBindStringWidth;
	private final static TTF numberItemFont = FontManager.get("FRIZQT", 15);
	private final static TTF keyBindFont = FontManager.get("FRIZQT", 15);
	private final static int MOUSE_MOVE_TRIGGER_RANGE = 15;
	private int keyBind;
	private static int borderWidth = (int)(40*Mideas.getDisplayXFactor());
	private static int borderHeight = (int)(39*Mideas.getDisplayYFactor());
	
	public ButtonSpellbar(float x, float y, boolean isInFirstBar) {
		this.x = (int)x;
		this.y = (int)y;
		this.isInFirstBar = false;
	}
	
	public ButtonSpellbar(float x, float y, Shortcut shortcut, boolean isInFirstBar) {
		this.x = (int)x;
		this.y = (int)y;
		this.isInFirstBar = false;
		setShortcut(shortcut);
	}
	
	public void draw() {
		if(this.shortcut != null) {
			Draw.drawQuad(this.shortcut.getSprite(), this.x+3, this.y+1);
			Draw.drawQuad(Sprites.spell_border, this.x+3, this.y+1, borderWidth, borderHeight);
			if(this.itemIsEquipped) {
				Draw.drawQuadBlend(Sprites.button_hover_spellbar, this.x+3, this.y+1, borderWidth, borderHeight, Color.GREEN);
			}
			if(this.numberItemString != null) {
				keyBindFont.drawStringShadow(this.x+30*Mideas.getDisplayXFactor()-this.keyBindStringWidth, this.y, this.numberItemString, Color.WHITE, Color.BLACK, 1, 0, 0);
			}
			if(this.numberItem >= 0) {
				numberItemFont.drawStringShadow(this.x+37*Mideas.getDisplayXFactor()-this.numberItemStringWidth, this.y+16*Mideas.getDisplayYFactor(), this.numberItemString, Color.WHITE, Color.BLACK, 1, 0, 0);
			}
		}
		if((this.shortcut == null && !this.isInFirstBar) || (this.shortcut != null)) {
			if(this.buttonDown || this.keyDown) {
				Draw.drawQuad(Sprites.button_down_spellbar, this.x+3, this.y+1, borderWidth, borderHeight);
			}
			if(this.buttonHover) {
				Draw.drawQuadBlend(Sprites.button_hover_spellbar, this.x+3, this.y+1, borderWidth, borderHeight);
			}
			if(!this.isInFirstBar && !this.buttonDown && !this.keyDown) {
	        		Draw.drawQuad(Sprites.spellbar_case, this.x, this.y-1, 45*Mideas.getDisplayXFactor(), 43*Mideas.getDisplayYFactor(), .6f);
			}
		}
	}
	
	public boolean mouseEvent() {
		if(Mideas.getHover() && Mideas.mouseX() >= this.x && Mideas.mouseX() <= this.x+borderWidth && Mideas.mouseY() >= this.y && Mideas.mouseY() <= this.y+borderHeight) {
			this.buttonHover = true;
			Mideas.setHover(false);
		}
		else {
			this.buttonHover = false;
		}
		if(this.buttonDown && (Math.abs(Math.abs(Mideas.mouseX())-Math.abs(this.buttonDownX)) >= MOUSE_MOVE_TRIGGER_RANGE || Math.abs(Math.abs(Mideas.mouseY())-Math.abs(this.buttonDownY)) >= MOUSE_MOVE_TRIGGER_RANGE)) {
			this.buttonDown = false;
			System.out.println("TRIGGER");
			if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) {
				Shortcut tmp = this.shortcut;
				setShortcut(getDraggedSpell());
				DragSpellManager.setDraggedSpell(tmp);
				return true;
			}
			return false;
		}
		if(this.buttonHover) {
			if(Mouse.getEventButton() == 0 || Mouse.getEventButton() == 1) {
				if(Mouse.getEventButtonState()) {
					this.buttonDown = true;
					this.buttonDownX = Mideas.mouseX();
					this.buttonDownY = Mideas.mouseY();
				}
				else if(this.buttonDown) {
					this.buttonDown = false;
					Shortcut shortcut = getDraggedSpell();
					if(shortcut != null) {
						System.out.println("Before : "+this.shortcut+" "+DragSpellManager.getDraggedSpell());
						Shortcut tmp = this.shortcut;
						setShortcut(shortcut);
						DragSpellManager.setDraggedSpell(tmp);
						System.out.println("After : "+this.shortcut+" "+DragSpellManager.getDraggedSpell());
						return true;
					}
					System.out.println("BUTTON DOWN FALSE B");
					if(this.shortcut != null) {
						this.shortcut.use();
					}
					return true;
				}
			}
		}
		else if(!Mouse.getEventButtonState()) {
			if(Mouse.getEventButton() == 0 || Mouse.getEventButton() == 1) {
				this.buttonDown = false;
				System.out.println("BUTTON DOWN FALSE");
			}
		}
		return false;
	}
	
	public boolean keyEvent() {
		if(Keyboard.getEventKey() == this.keyBind) {
			if(Keyboard.getEventKeyState()) {
				this.keyDown = true;
				return true;
			}
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
		this.numberItem = -1;
		this.itemIsEquipped = false;
		if(this.shortcut == null) {
			return;
		}
		if(this.shortcut.getShortcutType() == ShortcutType.SPELL) {
			return;
		}
		if(this.shortcut.getShortcutType() == ShortcutType.STUFF) {
			this.itemIsEquipped = SpellBarFrame.isEquippedItem(this.shortcut.getId());
			return;
		}
		if(this.shortcut.getShortcutType() == ShortcutType.POTION) {
			this.numberItem = ((PotionShortcut)this.shortcut).getPotion().getAmount();
			this.numberItemString = ((PotionShortcut)this.shortcut).getPotion().getAmountString();
			this.numberItemStringWidth = numberItemFont.getWidth(this.numberItemString);
			return;
		}
	}
	
	private static Shortcut getDraggedSpell() {
		if(DragManager.getDraggedItem() != null) {
			if(DragManager.getDraggedItem().isStuff() || DragManager.getDraggedItem().isWeapon()) {
				return new StuffShortcut((Stuff)DragManager.getDraggedItem());
			}
			if(DragManager.getDraggedItem().isPotion()) {
				return new PotionShortcut((Potion)DragManager.getDraggedItem());
			}
		}
		if(DragSpellManager.getDraggedSpell() != null) {
			return DragSpellManager.getDraggedSpell();
		}
		if(DragSpellManager.getDraggedSpellBook() != null) {
			return new SpellShortcut(DragSpellManager.getDraggedSpellBook());
		}
		return null;
	}
	
	public Shortcut getShortcut() {
		return this.shortcut;
	}
}
