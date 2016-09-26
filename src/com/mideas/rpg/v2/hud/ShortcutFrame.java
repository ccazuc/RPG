package com.mideas.rpg.v2.hud;

import java.util.Arrays;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import com.mideas.rpg.v2.Interface;
import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.Sprites;
import com.mideas.rpg.v2.utils.Draw;

public class ShortcutFrame {
	
	private static boolean[] hover = new boolean[8];
	private static int x = 45;
	private static int xShift = 30;
	private static int y = -43;
	

	public static void draw() {
		if(Interface.isTalentFrameActive()) {
			Draw.drawQuad(Sprites.talent_frame_open, Display.getWidth()/2+106, Display.getHeight()-41);
		}
		if(Interface.isSpellBookFrameActive()) {
			Draw.drawQuad(Sprites.spellbook_frame_open, Display.getWidth()/2+76.5f, Display.getHeight()-41);
		}
		if(Interface.getEscapeFrameStatus()) {
			Draw.drawQuad(Sprites.escape_frame_open, Display.getWidth()/2+319, Display.getHeight()-50);
		}
		if(Interface.getCharacterFrameStatus()) {
			Draw.drawQuad(Sprites.character_frame_open, Display.getWidth()/2+51, Display.getHeight()-49);
		}
		if(Mideas.mouseX() >= Display.getWidth()/2+x*Mideas.getDisplayXFactor() && Mideas.mouseX() <= Display.getWidth()/2+(x+8*xShift)*Mideas.getDisplayXFactor() && Mideas.mouseY() >= Display.getHeight()+y*Mideas.getDisplayYFactor() && Mideas.mouseY() <= Display.getHeight()-4) {
			int i = 0;
			while(i < hover.length) {
				if(hover[i]) {
					Draw.drawQuad(Sprites.shortcut_hover, Display.getWidth()/2+(x+3+i*xShift)*Mideas.getDisplayXFactor(), Display.getHeight()+(y+4)*Mideas.getDisplayYFactor());
					break;
				}
				i++;
			}
		}
	}
	
	public static boolean mouseEvent() {
		if(Mideas.mouseX() >= Display.getWidth()/2+x*Mideas.getDisplayXFactor() && Mideas.mouseX() <= Display.getWidth()/2+(x+8*xShift)*Mideas.getDisplayXFactor() && Mideas.mouseY() >= Display.getHeight()+y*Mideas.getDisplayYFactor() && Mideas.mouseY() <= Display.getHeight()-4) {
			Arrays.fill(hover, false);
			int i = 0;
			while(i < hover.length) {
				if(isHover((x+i*xShift)*Mideas.getDisplayXFactor(), y*Mideas.getDisplayYFactor())) {
					hover[i] = true;
					break;
				}
				i++;
			}
			if(Mouse.getEventButton() == 0) {
				if(!Mouse.getEventButtonState()) {
					if(hover[2]) { 																			//talent
						Interface.setTalentFrameStatus(!Interface.isTalentFrameActive());
						Interface.closeCharacterFrame();
						Interface.closeShopFrame();
						Interface.closeSpellBookFrame();
						Interface.closeEscapeFrame();
						Interface.closeAdminPanelFrame();
						Arrays.fill(CharacterFrame.getHoverCharacterFrame(), false);
						Arrays.fill(ShopFrame.getShopHover(), false);
						Arrays.fill(SpellBookFrame.getHoverBook(), false);
						Arrays.fill(ShopFrame.getShopHover(), false);
						ShopFrame.setHoverShopFalse();
						return true;
					}
					else if(hover[1]) {																		//spellbook
						Interface.setSpellBookFrameStatus(!Interface.isSpellBookFrameActive());
						Interface.closeTalentFrame();
						Interface.closeShopFrame();
						Interface.closeCharacterFrame();
						Interface.closeEscapeFrame();
						Interface.closeAdminPanelFrame();
						Arrays.fill(CharacterFrame.getHoverCharacterFrame(), false);
						Arrays.fill(ShopFrame.getShopHover(), false);
						Arrays.fill(SpellBookFrame.getHoverBook(), false);
						return true;
					}
					else if(hover[6]) {																		//escape menu
						Interface.setEscapeFrameStatus(!Interface.getEscapeFrameStatus());
						Interface.closeCharacterFrame();
						Interface.closeContainerFrame();
						Interface.closeShopFrame();
						Interface.closeTalentFrame();
						Interface.closeSpellBookFrame();
						Interface.closeAdminPanelFrame();
						Arrays.fill(ContainerFrame.getContainerFrameSlotHover(), false);
						Arrays.fill(CharacterFrame.getHoverCharacterFrame(), false);
						Arrays.fill(ShopFrame.getShopHover(), false);
						Arrays.fill(SpellBookFrame.getHoverBook(), false);
						ShopFrame.setHoverShopFalse();
						return true;
					}
					else if(hover[0]) {																		//character menu
						Interface.setCharacterFrameStatus(!Interface.getCharacterFrameStatus());
						Interface.closeShopFrame();
						Interface.closeSpellBookFrame();
						Interface.closeAdminPanelFrame();
						Interface.closeEscapeFrame();
						Interface.closeTalentFrame();
						Arrays.fill(ShopFrame.getShopHover(), false);
						Arrays.fill(SpellBookFrame.getHoverBook(), false);
						Arrays.fill(CharacterFrame.getHoverCharacterFrame(), false);
						return true;
					}
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
