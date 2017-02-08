package com.mideas.rpg.v2.utils;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import com.mideas.rpg.v2.FontManager;
import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.Sprites;
import com.mideas.rpg.v2.TTF;
import com.mideas.rpg.v2.game.item.Item;
import com.mideas.rpg.v2.game.item.potion.Potion;
import com.mideas.rpg.v2.game.item.stuff.Stuff;
import com.mideas.rpg.v2.game.shortcut.PotionShortcut;
import com.mideas.rpg.v2.game.shortcut.Shortcut;
import com.mideas.rpg.v2.game.shortcut.ShortcutType;
import com.mideas.rpg.v2.game.shortcut.StuffShortcut;
import com.mideas.rpg.v2.game.spell.SpellManager;
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
	private boolean isInFirstBar;
	private String keyBindString;
	private int keyBindStringWidth;
	private final static TTF numberItemFont = FontManager.get("FRIZQT", 15);
	private final static TTF keyBindFont = FontManager.get("FRIZQT", 15);
	private final static Color BACKGROUND_COLOR = new Color(0, 0, 0, .65f);
	private final static Color CD_COLOR = new Color(0, 0, 0, .8f);
	private final static int MOUSE_MOVE_TRIGGER_RANGE = 15;
	private int keyBind;
	private static int borderWidth = (int)(44*Mideas.getDisplayXFactor());
	private static int borderHeight = (int)(41*Mideas.getDisplayYFactor());
	
	public ButtonSpellbar(float x, float y, int keyBind, boolean isInFirstBar) {
		this.x = (int)x;
		this.y = (int)y;
		this.isInFirstBar = isInFirstBar;
		setShortcut(null);
		setKeyBind(keyBind);
	}
	
	public ButtonSpellbar(float x, float y, int keyBind, Shortcut shortcut, boolean isInFirstBar) {
		this.x = (int)x;
		this.y = (int)y;
		this.isInFirstBar = isInFirstBar;
		setShortcut(shortcut);
		setKeyBind(keyBind);
	}
	
	public void draw() {
		if(Display.wasResized() && this.keyBind >= 0) {
			setKeyBind(this.keyBind);
		}
		if((this.shortcut == null && !this.isInFirstBar) || (this.shortcut != null && !this.isInFirstBar && !this.buttonDown && !this.keyDown)) {
        		Draw.drawQuad(Sprites.spellbar_case, this.x, this.y-1, 44*Mideas.getDisplayXFactor(), 41*Mideas.getDisplayYFactor(), .5f);
		}
		if(this.shortcut != null) {
			Draw.drawQuad(this.shortcut.getSprite(), this.x+3, this.y); //TODO: check la bordure sur wow
			//Draw.drawQuad(Sprites.spell_border, this.x+3, this.y+1, borderWidth, borderHeight);
			if(this.itemIsEquipped) {
				Draw.drawQuadBlend(Sprites.button_hover_spellbar, this.x+3, this.y+1, borderWidth, borderHeight, Color.GREEN);
			}
			if(this.numberItem >= 0) {
				numberItemFont.drawStringShadow(this.x+40*Mideas.getDisplayXFactor()-this.numberItemStringWidth, this.y+16*Mideas.getDisplayYFactor(), this.numberItemString, Color.WHITE, Color.BLACK, 1, 0, 0);
			}
			if(this.shortcut.getShortcutType() == ShortcutType.STUFF && !this.itemIsInBag && !this.itemIsEquipped) {
				Draw.drawColorQuad(this.x+3, this.y+1, 37*Mideas.getDisplayXFactor(), 35*Mideas.getDisplayYFactor(), BACKGROUND_COLOR);
			}
			if(this.shortcut.getShortcutType() == ShortcutType.SPELL) {
				if(Mideas.joueur1().getGCDEndTimer() > Mideas.getLoopTickTimer()) {
					Draw.glScissorBegin(this.x+3, Display.getHeight()-this.y-37*Mideas.getDisplayYFactor(), 38*Mideas.getDisplayXFactor(), 37*Mideas.getDisplayYFactor());
					if(SpellManager.getSpell(this.shortcut.getId()).getSpellCDEnd() >= Mideas.joueur1().getGCDEndTimer()) {
						Draw.drawCircleFinal(this.x+20*Mideas.getDisplayXFactor(), this.y+20*Mideas.getDisplayYFactor(), 35*Mideas.getDisplayXFactor(), ((Mideas.getLoopTickTimer()-SpellManager.getSpell(this.shortcut.getId()).getSpellCDStart())/(double)SpellManager.getSpell(this.shortcut.getId()).getSpellCDLength()), -90, CD_COLOR);
					}
					else {
						Draw.drawCircleFinal(this.x+20*Mideas.getDisplayXFactor(), this.y+20*Mideas.getDisplayYFactor(), 35*Mideas.getDisplayXFactor(), ((Mideas.getLoopTickTimer()-Mideas.joueur1().getGCDStartTimer())/1500d), -90, CD_COLOR);
					}
					Draw.glScissorEnd();
				}
				else if(SpellManager.getSpell(this.shortcut.getId()).getSpellCDEnd() >= Mideas.joueur1().getGCDEndTimer()) {
					Draw.glScissorBegin(this.x+3, Display.getHeight()-this.y-37*Mideas.getDisplayYFactor(), 38*Mideas.getDisplayXFactor(), 37*Mideas.getDisplayYFactor());
					Draw.drawCircleFinal(this.x+20*Mideas.getDisplayXFactor(), this.y+20*Mideas.getDisplayYFactor(), 35*Mideas.getDisplayXFactor(), ((Mideas.getLoopTickTimer()-SpellManager.getSpell(this.shortcut.getId()).getSpellCDStart())/(double)SpellManager.getSpell(this.shortcut.getId()).getSpellCDLength()), -90, CD_COLOR);
					Draw.glScissorEnd();
				}
			}
		}
		if((this.shortcut == null && !this.isInFirstBar) || this.shortcut != null || DragManager.getDraggedItem() != null || DragSpellManager.getDraggedSpell() != null) {
			if(this.buttonDown || this.keyDown) {
				Draw.drawQuad(Sprites.button_down_spellbar, this.x+1, this.y, borderWidth, borderHeight);
			}
			if(this.buttonHover) {
				Draw.drawQuadBlend(Sprites.button_hover_spellbar, this.x+1, this.y, borderWidth, borderHeight);
			}
			if(this.keyBind >= 0) {
				keyBindFont.drawStringShadow(this.x+40*Mideas.getDisplayXFactor()-this.keyBindStringWidth, this.y, this.keyBindString, Color.GREY, Color.BLACK, 1, 0, 0);
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
			if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) {
				if(DragManager.getDraggedItem() != null && (DragManager.getDraggedItem().isPotion() || DragManager.getDraggedItem().isStuff() || DragManager.getDraggedItem().isWeapon())) {
					Shortcut shortcut = Item.createShortcut(DragManager.getDraggedItem());
					Shortcut tmp = this.shortcut;
					setShortcut(shortcut);
					DragManager.setDraggedItem(null);
					DragSpellManager.setDraggedSpell(tmp);
					return true;
				}
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
				else {
					this.buttonDown = false;
					if(DragManager.getDraggedItem() != null && (DragManager.getDraggedItem().isPotion() || DragManager.getDraggedItem().isStuff() || DragManager.getDraggedItem().isWeapon())) {
						Shortcut shortcut = Item.createShortcut(DragManager.getDraggedItem());
						Shortcut tmp = this.shortcut;
						setShortcut(shortcut);
						DragManager.setDraggedItem(null);
						DragSpellManager.setDraggedSpell(tmp);
						return true;
					}
					if(DragSpellManager.getDraggedSpell() != null) {
						Shortcut tmp = this.shortcut;
						setShortcut(DragSpellManager.getDraggedSpell());
						DragSpellManager.setDraggedSpell(tmp);
						return true;
					}
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
			this.keyDown = false;
			if(this.shortcut != null) {
				this.shortcut.use();
			}
			return true;
		}
		return false;
	}
	
	public void update(float x, float y) {
		this.x = (int)x;
		this.y = (int)y;
	}
	
	public void setKeyBind(int value) {
		this.keyBind = value;
		if(this.keyBind < 0) {
			return;
		}
		this.keyBindString = formatKeyBind(Keyboard.getKeyName(this.keyBind));
		this.keyBindStringWidth = keyBindFont.getWidth(this.keyBindString);
	}
	
	private static String formatKeyBind(String bind) {
		int i = 0;
		int x = keyBindFont.getWidth("...");
		while(i < bind.length()) {
			x+= keyBindFont.getWidth(bind.charAt(i));
			if(x >= 45*Mideas.getDisplayXFactor()) {
				if(i >= 1) {
					return bind.substring(0, i-1)+"...";
				}
				return "...";
			}
			i++;
		}
		return bind;
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
			setNumberItem(Mideas.joueur1().bag().getNumberItemInBags(this.shortcut.getId()));
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
		return null;
	}
	
	public void setNumberItem(int number) {
		this.numberItem = number;
		this.numberItemString = Integer.toString(number);
		this.numberItemStringWidth = numberItemFont.getWidth(this.numberItemString);
	}
	
	public void setItemIsEquipped(boolean we) {
		this.itemIsEquipped = we;
	}
	
	public void setIsInBag(boolean we) {
		this.itemIsInBag = we;
	}
	
	public Shortcut getShortcut() {
		return this.shortcut;
	}
}
