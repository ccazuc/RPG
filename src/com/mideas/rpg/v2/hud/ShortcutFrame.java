package com.mideas.rpg.v2.hud;

import java.util.Arrays;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import com.mideas.rpg.v2.Interface;
import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.Sprites;
import com.mideas.rpg.v2.utils.Draw;

public class ShortcutFrame {
	
	private final static int LENGTH = 8;
	private static int hover = -1;
	private static int down = -1;
	private static int x = 46;
	private static int xShift = 30;
	private static int y = -43;

	public static void draw() {
		if(Interface.isTalentFrameActive()) {
			Draw.drawQuad(Sprites.shortcut[2], Display.getWidth()/2+(x+3+2*xShift)*Mideas.getDisplayXFactor(), Display.getHeight()+(y+4)*Mideas.getDisplayYFactor());
		}
		if(Interface.isSpellBookFrameActive()) {
			Draw.drawQuad(Sprites.shortcut[1], Display.getWidth()/2+(x+3+xShift)*Mideas.getDisplayXFactor(), Display.getHeight()+(y+4)*Mideas.getDisplayYFactor());
		}
		if(Interface.getEscapeFrameStatus()) {
			Draw.drawQuad(Sprites.shortcut[6], Display.getWidth()/2+(x+3+6*xShift)*Mideas.getDisplayXFactor(), Display.getHeight()+(y+4)*Mideas.getDisplayYFactor());
		}
		if(Interface.getCharacterFrameStatus()) {
			Draw.drawQuad(Sprites.shortcut[0], Display.getWidth()/2+(x+3)*Mideas.getDisplayXFactor(), Display.getHeight()+(y+4)*Mideas.getDisplayYFactor());
		}
		int i = 0;
		while(i < LENGTH) {
			if(i == down) {
				Draw.drawQuad(Sprites.shortcut[down], Display.getWidth()/2+(x+3+i*xShift)*Mideas.getDisplayXFactor(), Display.getHeight()+(y+4)*Mideas.getDisplayYFactor());
			}
			if(i == hover) {
				Draw.drawQuad(Sprites.shortcut_hover, Display.getWidth()/2+(x+3+i*xShift)*Mideas.getDisplayXFactor(), Display.getHeight()+(y+4)*Mideas.getDisplayYFactor());
				break;
			}
			i++;
		}
	}
	
	public static boolean mouseEvent() {
			hover = -1;
			int i = 0;
			while(i < LENGTH) {
				if(isHover((x+i*xShift)*Mideas.getDisplayXFactor(), y*Mideas.getDisplayYFactor())) {
					if(Mouse.getEventButtonState()) {
						if(Mouse.getEventButton() == 0 || Mouse.getEventButton() == 1) {
							down = i;
						}
					}
					hover = i;
					break;
				}
				i++;
			}
			if(Mouse.getEventButton() == 0 || Mouse.getEventButton() == 1) {
				if(!Mouse.getEventButtonState()) {
					down = -1;
					if(hover == 0) {																		//character menu
						Interface.setCharacterFrameStatus(!Interface.getCharacterFrameStatus());
						Interface.closeShopFrame();
						Interface.closeSpellBookFrame();
						Interface.closeAdminPanelFrame();
						Interface.closeEscapeFrame();
						Interface.closeTalentFrame();
						Arrays.fill(SpellBookFrame.getHoverBook(), false);
						CharacterFrame.setHoverFalse();
						return true;
					}
					else if(hover == 1) {																		//spellbook
						Interface.setSpellBookFrameStatus(!Interface.isSpellBookFrameActive());
						Interface.closeTalentFrame();
						Interface.closeShopFrame();
						Interface.closeCharacterFrame();
						Interface.closeEscapeFrame();
						Interface.closeAdminPanelFrame();
						CharacterFrame.setHoverFalse();
						Arrays.fill(SpellBookFrame.getHoverBook(), false);
						return true;
					}
					else if(hover == 2) { 																			//talent
						Interface.setTalentFrameStatus(!Interface.isTalentFrameActive());
						Interface.closeCharacterFrame();
						Interface.closeShopFrame();
						Interface.closeSpellBookFrame();
						Interface.closeEscapeFrame();
						Interface.closeAdminPanelFrame();
						CharacterFrame.setHoverFalse();
						Arrays.fill(SpellBookFrame.getHoverBook(), false);
						return true;
					}
					else if(hover == 6) {																		//escape menu
						Interface.setEscapeFrameStatus(!Interface.getEscapeFrameStatus());
						Interface.closeCharacterFrame();
						Interface.closeContainerFrame();
						Interface.closeShopFrame();
						Interface.closeTalentFrame();
						Interface.closeSpellBookFrame();
						Interface.closeAdminPanelFrame();
						ContainerFrame.setHoverFalse();
						CharacterFrame.setHoverFalse();
						Arrays.fill(SpellBookFrame.getHoverBook(), false);
						return true;
					}
				}
			}
		return false;
	}
	
	private static boolean isHover(float x, float y) {
		if(Mideas.mouseX() >= Display.getWidth()/2+x && Mideas.mouseX() <= Display.getWidth()/2+x+(Sprites.shortcut_hover.getImageWidth()+3)*Mideas.getDisplayXFactor() && Mideas.mouseY() >= Display.getHeight()+y && Mideas.mouseY() <= Display.getHeight()+y+Sprites.shortcut_hover.getImageHeight()) {
			return true;
		}
		return false;
	}
}
