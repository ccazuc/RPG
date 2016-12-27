package com.mideas.rpg.v2.hud;

import java.util.Arrays;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import com.mideas.rpg.v2.Interface;
import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.Sprites;
import com.mideas.rpg.v2.utils.Draw;

public class ShortcutFrame {
	
	private static boolean hoverTalent;
	private static boolean hoverSpellBook;
	private static boolean hoverEscape;
	private static boolean hoverCharacter;

	public static void draw() {
		if(Interface.isTalentFrameActive()) {
			Draw.drawQuad(Sprites.talent_frame_open, Display.getWidth()/2+119, Display.getHeight()-50);
		}
		if(Interface.isSpellBookFrameActive()) {
			Draw.drawQuad(Sprites.spellbook_frame_open, Display.getWidth()/2+84, Display.getHeight()-50);
		}
		if(Interface.getEscapeFrameStatus()) {
			Draw.drawQuad(Sprites.escape_frame_open, Display.getWidth()/2+319, Display.getHeight()-50);
		}
		if(Interface.getCharacterFrameStatus()) {
			Draw.drawQuad(Sprites.character_frame_open, Display.getWidth()/2+51, Display.getHeight()-49);
		}
	}
	
	public static boolean mouseEvent() {
		hoverTalent = false;
		hoverSpellBook = false;
		hoverEscape = false;
		hoverCharacter = false;
		if(Mideas.mouseX() >= Display.getWidth()/2+119 && Mideas.mouseX() <= Display.getWidth()/2+150 && Mideas.mouseY() >= Display.getHeight()-50 && Mideas.mouseY() <= Display.getHeight()-5) {
			hoverTalent = true;
		}
		else if(Mideas.mouseX() >= Display.getWidth()/2+84 && Mideas.mouseX() <= Display.getWidth()/2+117 && Mideas.mouseY() >= Display.getHeight()-50 && Mideas.mouseY() <= Display.getHeight()-5) {
			hoverSpellBook = true;
		}
		else if(Mideas.mouseX() >= Display.getWidth()/2+319 && Mideas.mouseX() <= Display.getWidth()/2+353 && Mideas.mouseY() >= Display.getHeight()-50 && Mideas.mouseY() <= Display.getHeight()-5) {
			hoverEscape = true;
		}
		else if(Mideas.mouseX() >= Display.getWidth()/2+51 && Mideas.mouseX() <= Display.getWidth()/2+82 && Mideas.mouseY() >= Display.getHeight()-49 && Mideas.mouseY() <= Display.getHeight()-4) {
			hoverCharacter = true;
		}
		if(Mouse.getEventButton() == 0) {
			if(!Mouse.getEventButtonState()) {
				if(hoverTalent) {
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
					EscapeFrame.setHoverFalse();
					return true;
				}
				else if(hoverSpellBook) {
					Interface.setSpellBookFrameStatus(!Interface.isSpellBookFrameActive());
					Interface.closeTalentFrame();
					Interface.closeShopFrame();
					Interface.closeCharacterFrame();
					Interface.closeEscapeFrame();
					Interface.closeAdminPanelFrame();
					Arrays.fill(CharacterFrame.getHoverCharacterFrame(), false);
					Arrays.fill(ShopFrame.getShopHover(), false);
					Arrays.fill(SpellBookFrame.getHoverBook(), false);
					EscapeFrame.setHoverFalse();
					return true;
				}
				else if(hoverEscape) {
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
					EscapeFrame.setHoverFalse();
					return true;
				}
				else if(hoverCharacter) {
					Interface.setCharacterFrameStatus(!Interface.getCharacterFrameStatus());
					Interface.closeShopFrame();
					Interface.closeSpellBookFrame();
					Interface.closeAdminPanelFrame();
					Interface.closeEscapeFrame();
					Interface.closeTalentFrame();
					Arrays.fill(ShopFrame.getShopHover(), false);
					Arrays.fill(SpellBookFrame.getHoverBook(), false);
					Arrays.fill(CharacterFrame.getHoverCharacterFrame(), false);
					EscapeFrame.setHoverFalse();
					return true;
				}
			}
		}
		return false;
	}
}
