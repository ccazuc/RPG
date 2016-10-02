package com.mideas.rpg.v2.hud;

import java.sql.SQLException;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import com.mideas.rpg.v2.Interface;
import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.Sprites;
import com.mideas.rpg.v2.game.IconsManager;
import com.mideas.rpg.v2.game.item.gem.Gem;
import com.mideas.rpg.v2.game.item.gem.GemColor;
import com.mideas.rpg.v2.game.item.stuff.Stuff;
import com.mideas.rpg.v2.utils.Button;
import com.mideas.rpg.v2.utils.CrossButton;
import com.mideas.rpg.v2.utils.Draw;
import com.mideas.rpg.v2.utils.Texture;

public class SocketingFrame {

	static Stuff stuff;
	static Gem tempGem1;
	static Gem tempGem2;
	static Gem tempGem3;
	private static int hoveredGem = -1;
	private static int leftButtonDown = -1;
	private static int rightButtonDown = -1;
	private static int mouseX;
	private static int mouseY;
	private static float yFrame = -380*Mideas.getDisplayYFactor();
	private static float xFrame = -306*Mideas.getDisplayXFactor();
	private static CrossButton closeSocketingFrame = new CrossButton(Display.getWidth()/2+67+CharacterFrame.getMouseX(), Display.getHeight()/2-365+CharacterFrame.getMouseY()) {
		@Override
		protected void eventButtonClick() throws SQLException {
			Interface.setSocketingFrameStatus(false);
			CharacterFrame.setGemFrame(0);
			CharacterFrame.updateButton();
			stuff = null;
		}
	};
	private static Button putGem = new Button(Display.getWidth()/2+150+CharacterFrame.getMouseX(), Display.getHeight()/2+50+CharacterFrame.getMouseY(), 200, 23, "Sertir les gemmes", 15, 1) {
		@Override
		protected boolean activateCondition() {
			return tempGem1 != null || tempGem2 != null || tempGem3 != null;
		}
	};
	
	public static void draw() {
		Draw.drawQuad(Sprites.socketing_frame, Display.getWidth()/2+xFrame+CharacterFrame.getMouseX(), Display.getHeight()/2+yFrame+CharacterFrame.getMouseY());
		closeSocketingFrame.draw();
		putGem.draw();
		drawGems();
	}
	
	public static boolean mouseEvent() throws SQLException {
		hoveredGem = -1;
		gemHover();
		closeSocketingFrame.event();
		putGem.event();
		if(Mouse.getEventButtonState()) {
			if(Mouse.getEventButton() == 0) {
				leftButtonDown = hoveredGem;
				mouseX = Mideas.mouseX();
				mouseY = Mideas.mouseY();
			}
			else if(Mouse.getEventButton() == 1) {
				rightButtonDown = hoveredGem;
				mouseX = Mideas.mouseX();
				mouseY = Mideas.mouseY();
			}
		}
		else {
			if(Mouse.getEventButton() == 0) {
				if(leftButtonDown != -1 && DragManager.getDraggedItem() == null) {
					pickGem();
				}
				else if(DragManager.getDraggedItem() != null && DragManager.getDraggedItem().isGem()) {
					dropGem();
				}
				leftButtonDown = -1;
				rightButtonDown = -1;
				mouseX = 0;
				mouseY = 0;
			}
		}
		if(Math.abs(Math.abs(Mideas.mouseX())-Math.abs(mouseX)) >= 15 || Math.abs(Math.abs(Mideas.mouseY())-Math.abs(mouseY)) >= 15) {
			if(DragManager.getDraggedItem() == null) {
				if(leftButtonDown != -1) {
					setDraggedItem(leftButtonDown);
				}
				if(rightButtonDown != -1) {
					leftButtonDown = -1;
					rightButtonDown = -1;
				}
			}
			else {
				leftButtonDown = -1;
				rightButtonDown = -1;
				mouseX = 0;
				mouseY = 0;
			}
		}
		return false;
	}
	
	private static void gemHover() {
		if(stuff.getNumberGemSlot() == 1) {
			gem1Hover();
		}
		else if(stuff.getNumberGemSlot() == 2) {
			gem2Hover();
		}
		else if(stuff.getNumberGemSlot() == 3) {
			gem3Hover();
		}
	}
	
	private static void drawGems() {
		if(stuff.getNumberGemSlot() == 1) {
			drawGem1();
		}
		else if(stuff.getNumberGemSlot() == 2) {
			drawGem2();
		}
		else if(stuff.getNumberGemSlot() == 3) {
			drawGem3();
		}
	}
	
	private static void dropGem() {
		if(hoveredGem == 1) {
			Gem temp = tempGem1;
			tempGem1 = (Gem)DragManager.getDraggedItem();
			DragManager.setDraggedItem(temp);
		}
		else if(hoveredGem == 2) {
			
		}
		else if(hoveredGem == 3) {
			
		}
	}
	
	private static void pickGem() {
		if(hoveredGem == 1) {
			DragManager.setDraggedItem(tempGem1);
			tempGem1 = null;
		}
		else if(hoveredGem == 2) {
			DragManager.setDraggedItem(tempGem2);
			tempGem2 = null;
		}
		else if(hoveredGem == 3) {
			DragManager.setDraggedItem(tempGem3);
			tempGem3 = null;
		}
	}
	
	private static void gem1Hover() {
		int x = -120;
		int y = 8;
		if(Mideas.mouseX() >= Display.getWidth()/2+x+CharacterFrame.getMouseX() && Mideas.mouseX() <= Display.getWidth()/2+x+CharacterFrame.getMouseX()+Sprites.red_socket_background.getImageWidth()*Mideas.getDisplayXFactor() && Mideas.mouseY() >= Display.getHeight()/2+y+CharacterFrame.getMouseY() && Mideas.mouseY() <= Display.getHeight()/2+y+CharacterFrame.getMouseY()+Sprites.red_socket_background.getImageHeight()*Mideas.getDisplayXFactor()) {
			hoveredGem = 1;
		}
	}
	
	private static void gem2Hover() {
		
	}
	
	private static void gem3Hover() {
		
	}
	
	private static void drawGem1() {
		float x = -127*Mideas.getDisplayXFactor();
		float y = 8;
		Draw.drawQuad(getBackgroundSprite(stuff.getGemSlot1()), Display.getWidth()/2+x+CharacterFrame.getMouseX(), Display.getHeight()/2+y+CharacterFrame.getMouseY());
		if(tempGem1 != null) {
			Draw.drawQuad(IconsManager.getSprite37(tempGem1.getSpriteId()), Display.getWidth()/2+x+4+CharacterFrame.getMouseX(), Display.getHeight()/2+y+4+CharacterFrame.getMouseY(), (Sprites.red_socket_background.getImageWidth()-8)*Mideas.getDisplayXFactor(), (Sprites.red_socket_background.getImageHeight()-7)*Mideas.getDisplayXFactor());
			Draw.drawQuad(getEmptyCrossSprite(tempGem1.getColor()), Display.getWidth()/2+x-7+CharacterFrame.getMouseX(), Display.getHeight()/2+y-5+CharacterFrame.getMouseY());
			Draw.drawQuad(Sprites.border, Display.getWidth()/2+x+2+CharacterFrame.getMouseX(), Display.getHeight()/2+y+1+CharacterFrame.getMouseY(), (Sprites.red_socket_background.getImageWidth()-3)*Mideas.getDisplayXFactor(), (Sprites.red_socket_background.getImageHeight()-2)*Mideas.getDisplayXFactor());
		}
		else if(stuff.getEquippedGem1() == null) {
			Draw.drawQuad(getEmptyCrossSprite(stuff.getGemSlot1()), Display.getWidth()/2+x-7+CharacterFrame.getMouseX(), Display.getHeight()/2+y-5+CharacterFrame.getMouseY());
		}
		else {
			Draw.drawQuad(IconsManager.getSprite37(stuff.getEquippedGem1().getSpriteId()), Display.getWidth()/2+x+CharacterFrame.getMouseX(), Display.getHeight()/2+y+CharacterFrame.getMouseY());
			Draw.drawQuad(getFilledCrossSprite(stuff.getGemSlot1()), Display.getWidth()/2+x+CharacterFrame.getMouseX(), Display.getHeight()/2+y+CharacterFrame.getMouseY());
		}
		if(hoveredGem == 1) {
			Draw.drawQuad(Sprites.hover, Display.getWidth()/2+x+2+CharacterFrame.getMouseX(), Display.getHeight()/2+y+2+CharacterFrame.getMouseY(), (Sprites.red_socket_background.getImageWidth()-4)*Mideas.getDisplayXFactor(), (Sprites.red_socket_background.getImageHeight()-4)*Mideas.getDisplayXFactor());
		}
		if(rightButtonDown == 1 || leftButtonDown == 1) {
			Draw.drawQuad(Sprites.inventory_click_hover, Display.getWidth()/2+x+2+CharacterFrame.getMouseX(), Display.getHeight()/2+y+2+CharacterFrame.getMouseY(), (Sprites.red_socket_background.getImageWidth()-4)*Mideas.getDisplayXFactor(), (Sprites.red_socket_background.getImageHeight()-4)*Mideas.getDisplayXFactor());
		}
	}
	
	private static void drawGem2() {
		Draw.drawQuad(getBackgroundSprite(stuff.getGemSlot2()), Display.getWidth()/2-220+CharacterFrame.getMouseX(), Display.getHeight()/2-50+CharacterFrame.getMouseY());
		if(stuff.getEquippedGem2() == null) {
			Draw.drawQuad(getEmptyCrossSprite(stuff.getGemSlot2()), Display.getWidth()/2-220+CharacterFrame.getMouseX(), Display.getHeight()/2-50+CharacterFrame.getMouseY());
		}
		else {
			Draw.drawQuad(IconsManager.getSprite37(stuff.getEquippedGem2().getSpriteId()), Display.getWidth()/2-220+CharacterFrame.getMouseX(), Display.getHeight()/2-50+CharacterFrame.getMouseY());
			Draw.drawQuad(getFilledCrossSprite(stuff.getGemSlot2()), Display.getWidth()/2-220+CharacterFrame.getMouseX(), Display.getHeight()/2-50+CharacterFrame.getMouseY());
		}
	}
	
	private static void drawGem3() {
		Draw.drawQuad(getBackgroundSprite(stuff.getGemSlot3()), Display.getWidth()/2-220+CharacterFrame.getMouseX(), Display.getHeight()/2-50+CharacterFrame.getMouseY());
		if(stuff.getEquippedGem3() == null) {
			Draw.drawQuad(getEmptyCrossSprite(stuff.getGemSlot3()), Display.getWidth()/2-220+CharacterFrame.getMouseX(), Display.getHeight()/2-50+CharacterFrame.getMouseY());
		}
		else {
			Draw.drawQuad(IconsManager.getSprite37(stuff.getEquippedGem3().getSpriteId()), Display.getWidth()/2-220+CharacterFrame.getMouseX(), Display.getHeight()/2-50+CharacterFrame.getMouseY());
			Draw.drawQuad(getFilledCrossSprite(stuff.getGemSlot3()), Display.getWidth()/2-220+CharacterFrame.getMouseX(), Display.getHeight()/2-50+CharacterFrame.getMouseY());
		}
	}
	
	public static void updateSize() {
		yFrame = -380*Mideas.getDisplayYFactor();
		xFrame = -306*Mideas.getDisplayXFactor();
	}
	
	private static void setDraggedItem(int mouse)  {
		if(mouse == 1 && tempGem1 != null) {
			DragManager.setDraggedItem(tempGem1);
		}
		else if(mouse == 2 && tempGem1 != null) {
			DragManager.setDraggedItem(tempGem2);
		}
		else if(mouse == 3 && tempGem1 != null) {
			DragManager.setDraggedItem(tempGem3);
		}
	}
	
	private static Texture getEmptyCrossSprite(GemColor color) {
		if(color == GemColor.RED) {
			return Sprites.empty_red_cross;
		}
		if(color == GemColor.BLUE) {
			return Sprites.empty_blue_cross;
		}
		if(color == GemColor.YELLOW) {
			return Sprites.empty_yellow_cross;
		}
		return null;
	}
	
	private static Texture getFilledCrossSprite(GemColor color) {
		if(color == GemColor.RED) {
			return Sprites.filled_red_cross;
		}
		if(color == GemColor.BLUE) {
			return Sprites.filled_blue_cross;
		}
		if(color == GemColor.YELLOW) {
			return Sprites.filled_yellow_cross;
		}
		return null;
	}
	
	private static Texture getBackgroundSprite(GemColor color) {
		if(color == GemColor.RED) {
			return Sprites.red_socket_background;
		}
		if(color == GemColor.BLUE) {
			return Sprites.blue_socket_background;
		}
		if(color == GemColor.YELLOW) {
			return Sprites.yellow_socket_background;
		}
		return null;
	}
	
	public static CrossButton getCrossButton() {
		return closeSocketingFrame;
	}
	
	public static Button getButton() {
		return putGem;
	}
	
	public static Stuff getStuff() {
		return stuff;
	}
	
	public static void setStuff(Stuff stuffs) {
		stuff = stuffs;
	}
}
