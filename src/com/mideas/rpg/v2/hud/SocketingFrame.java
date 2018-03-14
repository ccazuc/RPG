package com.mideas.rpg.v2.hud;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import com.mideas.rpg.v2.Interface;
import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.game.IconsManager;
import com.mideas.rpg.v2.game.item.gem.Gem;
import com.mideas.rpg.v2.game.item.gem.GemColor;
import com.mideas.rpg.v2.game.item.stuff.Stuff;
import com.mideas.rpg.v2.render.Draw;
import com.mideas.rpg.v2.render.Sprites;
import com.mideas.rpg.v2.render.Texture;
import com.mideas.rpg.v2.utils.Button;
import com.mideas.rpg.v2.utils.CrossButton;

public class SocketingFrame {

	private static boolean shouldUpdateSize;
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
	private static CrossButton closeSocketingFrame = new CrossButton(Display.getWidth()/2+66*Mideas.getDisplayXFactor()+CharacterFrame.getMouseX(), Display.getHeight()/2-365*Mideas.getDisplayYFactor()+CharacterFrame.getMouseY(), CrossButton.DEFAULT_WIDTH*Mideas.getDisplayXFactor(), CrossButton.DEFAULT_HEIGHT*Mideas.getDisplayXFactor()) {
		@Override
		protected void eventButtonClick() {
			Interface.setSocketingFrameStatus(false);
			CharacterFrame.setGemFrame(0);
			CharacterFrame.updateButton();
			stuff = null;
		}
	};
	private static Button putGem = new Button(Display.getWidth()/2-100*Mideas.getDisplayXFactor()+CharacterFrame.getMouseX(), 620*Mideas.getDisplayYFactor(), (short)184, (short)23, "Sertir les gemmes", 15, 1) {
		@Override
		protected boolean activateCondition() {
			return tempGem1 != null || tempGem2 != null || tempGem3 != null;
		}
		
		@Override
		protected void onLeftClickUp() {
			if(tempGem1 != null) {
				stuff.setEquippedGem(0, tempGem1);
				DragManager.deleteBagItem(tempGem1);
				tempGem1 = null;
			}
			if(tempGem2 != null) {
				stuff.setEquippedGem(1, tempGem2);
				DragManager.deleteBagItem(tempGem2);
				tempGem2 = null;
			}
			if(tempGem3 != null) {
				stuff.setEquippedGem(2, tempGem3);
				DragManager.deleteBagItem(tempGem3);
				tempGem3 = null;
			}
		}
	};
	
	public static void draw() {
		updateSize();
		Draw.drawQuad(Sprites.socketing_frame, Display.getWidth()/2+xFrame+CharacterFrame.getMouseX(), Display.getHeight()/2+yFrame+CharacterFrame.getMouseY());
		closeSocketingFrame.draw();
		putGem.draw();
		drawGems();
	}
	
	public static boolean mouseEvent() {
		hoveredGem = -1;
		gemsHover();
		if(closeSocketingFrame.event()) return true;
		if(putGem.event()) return true;
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
	
	private static void gemsHover() {
		if(stuff.getNumberGemSlot() == 1) {
			gemHover(-127*Mideas.getDisplayXFactor(), 8, 1);
		}
		else if(stuff.getNumberGemSlot() == 2) {
			
		}
		else if(stuff.getNumberGemSlot() == 3) {
			
		}
	}
	
	private static void drawGems() {
		float x;
		float y;
		if(stuff.getNumberGemSlot() == 1) {
			x = -127*Mideas.getDisplayXFactor();
			y = 8;
			drawGem(stuff.getEquippedGem(0), stuff.getGemColor(0), tempGem1, Display.getWidth()/2+x+CharacterFrame.getMouseX(), Display.getHeight()/2+y+CharacterFrame.getMouseY());
		}
		else if(stuff.getNumberGemSlot() == 2) {
		}
		else if(stuff.getNumberGemSlot() == 3) {
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
	
	private static void gemHover(float x, float y, int index) {
		if(Mideas.mouseX() >= Display.getWidth()/2+x+CharacterFrame.getMouseX() && Mideas.mouseX() <= Display.getWidth()/2+x+CharacterFrame.getMouseX()+Sprites.red_socket_background.getImageWidth()*Mideas.getDisplayXFactor() && Mideas.mouseY() >= Display.getHeight()/2+y+CharacterFrame.getMouseY() && Mideas.mouseY() <= Display.getHeight()/2+y+CharacterFrame.getMouseY()+Sprites.red_socket_background.getImageHeight()*Mideas.getDisplayXFactor()) {
			hoveredGem = index;
		}
	}
	
	private static void drawGem(Gem gem, GemColor slotColor, Gem tempGem, float x, float y) {
		Draw.drawQuad(getBackgroundSprite(slotColor), x, y);
		if(tempGem1 != null) {
			Draw.drawQuad(IconsManager.getSprite37(tempGem.getSpriteId()), x+4, y+4, (Sprites.red_socket_background.getImageWidth()-8)*Mideas.getDisplayXFactor(), (Sprites.red_socket_background.getImageHeight()-7)*Mideas.getDisplayXFactor());
			Draw.drawQuad(getEmptyCrossSprite(tempGem.getColor()), x-7, y-5);
			Draw.drawQuad(Sprites.border, x+2, y+1, (Sprites.red_socket_background.getImageWidth()-3)*Mideas.getDisplayXFactor(), (Sprites.red_socket_background.getImageHeight()-2)*Mideas.getDisplayXFactor());
		}
		else if(stuff.getEquippedGem(1) == null) {
			Draw.drawQuad(getEmptyCrossSprite(slotColor), x-7, y-5);
		}
		else {
			Draw.drawQuad(IconsManager.getSprite37(gem.getSpriteId()), x+4, y+4, (Sprites.red_socket_background.getImageWidth()-8)*Mideas.getDisplayXFactor(), (Sprites.red_socket_background.getImageHeight()-7)*Mideas.getDisplayXFactor());
			Draw.drawQuad(getFilledCrossSprite(gem.getColor()), x+1, y-3);
		}
		if(hoveredGem == 1) {
			Draw.drawQuad(Sprites.hover, x+2, y+2, (Sprites.red_socket_background.getImageWidth()-4)*Mideas.getDisplayXFactor(), (Sprites.red_socket_background.getImageHeight()-4)*Mideas.getDisplayXFactor());
		}
		if(rightButtonDown == 1 || leftButtonDown == 1) {
			Draw.drawQuad(Sprites.inventory_click_hover, x+2, y+2, (Sprites.red_socket_background.getImageWidth()-4)*Mideas.getDisplayXFactor(), (Sprites.red_socket_background.getImageHeight()-4)*Mideas.getDisplayXFactor());
		}
	}
	
	public static void updateSize() {
		if(!shouldUpdateSize) {
			return;
		}
		yFrame = -380*Mideas.getDisplayYFactor();
		xFrame = -306*Mideas.getDisplayXFactor();
		shouldUpdateSize = false;
	}
	
	public static void shouldUpdate() {
		shouldUpdateSize = true;
	}
	
	private static void setDraggedItem(int mouse)  {
		if(mouse == 1) {
			if(tempGem1 != null) {
				DragManager.setDraggedItem(tempGem1);
				tempGem1 = null;
			}
			else {
				leftButtonDown = -1;
				rightButtonDown = -1;
			}
		}
		else if(mouse == 2) {
			if(tempGem2 != null) {
				DragManager.setDraggedItem(tempGem2);
				tempGem2 = null;
			}
			else {
				leftButtonDown = -1;
				rightButtonDown = -1;
			}
		}
		else if(mouse == 3) {
			if(tempGem3 != null) {
				DragManager.setDraggedItem(tempGem3);
				tempGem3 = null;
			}
			else {
				leftButtonDown = -1;
				rightButtonDown = -1;
			}
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
