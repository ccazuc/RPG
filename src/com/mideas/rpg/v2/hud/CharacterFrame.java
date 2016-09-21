package com.mideas.rpg.v2.hud;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;

import com.mideas.rpg.v2.Interface;
import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.Sprites;
import com.mideas.rpg.v2.TTF2;
import com.mideas.rpg.v2.game.IconsManager;
import com.mideas.rpg.v2.game.item.ItemType;
import com.mideas.rpg.v2.game.item.gem.GemBonusType;
import com.mideas.rpg.v2.game.item.gem.GemColor;
import com.mideas.rpg.v2.game.item.gem.GemManager;
import com.mideas.rpg.v2.game.item.stuff.Stuff;
import com.mideas.rpg.v2.utils.Draw;

public class CharacterFrame {
	
	private static boolean[] hoverCharacterFrame = new boolean[20];
	private static boolean hoverCloseButton;
	private static boolean hoverCloseGemFrameButton;
	private static boolean closeButtonDown;
	private static Color bgColor = new Color(0, 0, 0, .6f); 
	private static Color borderColor = Color.decode("#494D4B");
	private static int hover;
	private static int gemFrame;
	private static int xMouseShift;
	private static int yMouseShift;
	private static boolean hoverMove;
	private static boolean moving;
	private static int baseMouseX;
	private static int baseMouseY;
	private static int lastMouseX;
	private static int lastMouseY;
	private static boolean gemFrameOpen;
	private static int hoveredSlot;

	public static void draw() {
		hover = 0;
		Draw.drawQuad(Sprites.character_frame, Display.getWidth()/2-300+xMouseShift+gemFrame, Display.getHeight()/2-380+yMouseShift);
		if(gemFrameOpen) {
			Draw.drawQuad(Sprites.gem_frame, Display.getWidth()/2-300+xMouseShift, Display.getHeight()/2-380+yMouseShift);
		}
		/*Draw.drawQuad(Sprites.character_frame_stats, Display.getWidth()/2-224+xMouseShift+gemFrame, Display.getHeight()/2-60+yMouseShift);
		TTF2.characterFrameStats.drawStringShadow(Display.getWidth()/2-215+xMouseShift+gemFrame, Display.getHeight()/2-27+yMouseShift, "Strength:", Color.yellow, Color.black, 1, 1, 1);
		TTF2.characterFrameStats.drawStringShadow(Display.getWidth()/2-88+xMouseShift+gemFrame-TTF2.characterFrameStats.getWidth(String.valueOf(Mideas.joueur1().getStrength())), Display.getHeight()/2-27+yMouseShift, String.valueOf(Mideas.joueur1().getStrength()), Color.white, Color.black, 1, 1, 1);
		TTF2.characterFrameStats.drawStringShadow(Display.getWidth()/2-215+xMouseShift+gemFrame, Display.getHeight()/2-15+yMouseShift, "Stamina:", Color.yellow, Color.black, 1, 1, 1);
		TTF2.characterFrameStats.drawStringShadow(Display.getWidth()/2-88+xMouseShift+gemFrame-TTF2.characterFrameStats.getWidth(String.valueOf(Mideas.joueur1().getMaxStamina()/10)), Display.getHeight()/2-15+yMouseShift, String.valueOf(Mideas.joueur1().getMaxStamina()/10), Color.white, Color.black, 1, 1, 1);
		TTF2.characterFrameStats.drawStringShadow(Display.getWidth()/2-215+xMouseShift+gemFrame, Display.getHeight()/2-3+yMouseShift, "Armor:", Color.yellow, Color.black, 1, 1, 1);
		TTF2.characterFrameStats.drawStringShadow(Display.getWidth()/2-88+xMouseShift+gemFrame-TTF2.characterFrameStats.getWidth(String.valueOf((int)Mideas.joueur1().getArmor())), Display.getHeight()/2-3+yMouseShift, String.valueOf((int)Mideas.joueur1().getArmor()), Color.white, Color.black, 1, 1, 1);
		TTF2.characterFrameStats.drawStringShadow(Display.getWidth()/2-215+xMouseShift+gemFrame, Display.getHeight()/2+9+yMouseShift, "Critical:", Color.yellow, Color.black, 1, 1, 1);
		TTF2.characterFrameStats.drawStringShadow(Display.getWidth()/2-88+xMouseShift+gemFrame-TTF2.characterFrameStats.getWidth(String.valueOf(Mideas.joueur1().getCritical())+"%"), Display.getHeight()/2+9+yMouseShift, String.valueOf(Mideas.joueur1().getCritical())+"%", Color.white, Color.black, 1, 1, 1);
		TTF2.characterFrameStats.drawStringShadow(Display.getWidth()/2-215+xMouseShift+gemFrame, Display.getHeight()/2+21+yMouseShift, "Mana:", Color.yellow, Color.black, 1, 1, 1);
		TTF2.characterFrameStats.drawStringShadow(Display.getWidth()/2-88+xMouseShift+gemFrame-TTF2.characterFrameStats.getWidth(String.valueOf(Mideas.joueur1().getMana())), Display.getHeight()/2+21+yMouseShift, String.valueOf(Mideas.joueur1().getMana()), Color.white, Color.black, 1, 1, 1);*/
		int xStuff = -280+xMouseShift+gemFrame;
		int i = 0;
		int j = 0;
		int z = 0;
		int yStuff = -302+yMouseShift;
		float yShift = 43.8f;
		float xShift = 47.5f;
		while(i < Mideas.joueur1().getStuff().length) {
			drawCharacterItems(i, xStuff+z*xShift, yStuff+j*yShift);
			i++;
			if(i <= 16) {
				j++;
			}
			else {
				z++;
			}
			if(i == 8) {
				xStuff = 43+xMouseShift+gemFrame;
				j = 0;
			}
			else if(i == 16) {
				xStuff = -166+xMouseShift+gemFrame;
				yStuff = 31+yMouseShift;
				j = 0;
			}
		}
		if(hoverCloseButton) {
			if(closeButtonDown) {
				Draw.drawQuad(Sprites.close_button_inventory_down_hover, Display.getWidth()/2+70+xMouseShift+gemFrame, Display.getHeight()/2-366+yMouseShift);
			}
			else {
				Draw.drawQuad(Sprites.close_button_inventory_hover, Display.getWidth()/2+70+xMouseShift+gemFrame, Display.getHeight()/2-366+yMouseShift);
			}
		}
		else if(closeButtonDown) {
			Draw.drawQuad(Sprites.close_button_inventory_down, Display.getWidth()/2+70+xMouseShift+gemFrame, Display.getHeight()/2-366+yMouseShift);
		}
		if(DragManager.isHoverCharacterFrame()) {
			i = 0;
			int x_hover = -236+xMouseShift+gemFrame;
			int y_hover = -300+yMouseShift;
			z = 0;
			j = 0;
			while(i < Mideas.joueur1().getStuff().length) {
				characterItemHover(i, x_hover+z*(int)yShift, y_hover+j*(int)yShift);
				i++;
				if(i < 16) {
					j++;
				}
				else {
					j = 0;
					x_hover = -205+xMouseShift+gemFrame;
					y_hover = 80+yMouseShift;
					z++;
				}
				if(i == 8) {
					j = 0;
					x_hover = 87+xMouseShift+gemFrame;
				}
				else if(i == 17) {
					yShift = 51;
				}
			}
		}
	}

	public static boolean mouseEvent() {
		hoverMove = false;
		hoverCloseButton = false;
		hoverCloseGemFrameButton = false;
		hoverCharacterFrame[hoveredSlot] = false;
		if(Mideas.getHover() && Mideas.mouseX() >= Display.getWidth()/2+70+xMouseShift+gemFrame && Mideas.mouseX() <= Display.getWidth()/2+70+Sprites.close_button_inventory_down.getImageWidth()+xMouseShift+gemFrame && Mideas.mouseY() >= Display.getHeight()/2-366+yMouseShift && Mideas.mouseY() <= Display.getHeight()/2-366+Sprites.close_button_inventory_down.getImageHeight()+yMouseShift) {
			hoverCloseButton = true;
			Mideas.setHover(false);
		}
		else if(Mideas.getHover() && gemFrameOpen && Mideas.mouseX() >= Display.getWidth()/2+70+xMouseShift && Mideas.mouseX() <= Display.getWidth()/2+70+Sprites.close_button_inventory_down.getImageWidth()+xMouseShift && Mideas.mouseY() >= Display.getHeight()/2-366+yMouseShift && Mideas.mouseY() <= Display.getHeight()/2-366+Sprites.close_button_inventory_down.getImageHeight()+yMouseShift) {
			hoverCloseGemFrameButton = true;
			Mideas.setHover(false);
		}
		else if(Mideas.getHover() && Mideas.mouseX()>= Display.getWidth()/2-300+xMouseShift+gemFrame && Mideas.mouseX() <= Display.getWidth()/2-300+xMouseShift+gemFrame+Sprites.character_frame.getImageWidth() && Mideas.mouseY() >= Display.getHeight()/2-368+yMouseShift && Mideas.mouseY() <= Display.getHeight()/2-368+yMouseShift+8) {
			hoverMove = true;
			Mideas.setHover(false);
		}
		if(moving) {
			yMouseShift = Mideas.mouseY()-baseMouseY+lastMouseY;
			xMouseShift = Mideas.mouseX()-baseMouseX+lastMouseX;
		}
		if(Mouse.getEventButtonState()) {
			if(Mouse.getEventButton() == 0) {
				if(hoverMove && !moving) {
					moving = true;
					baseMouseY = Mideas.mouseY();
					baseMouseX = Mideas.mouseX();
					return true;
				}
				if(hoverCloseButton) {
					closeButtonDown = true;
				}
			}
		}
		if(!Mouse.getEventButtonState()) {
			if(Mouse.getEventButton() == 0) {
				if(hoverCloseButton && closeButtonDown) {
					Interface.closeCharacterFrame();
				}
				else if(hoverCloseGemFrameButton) {
					gemFrameOpen = false;
					gemFrame = 0;
				}
				else if(moving) {
					moving = false;
					lastMouseX = xMouseShift;
					lastMouseY = yMouseShift;
				}
				closeButtonDown = false;
			}
			if(Mouse.getEventButton() == 1) {
				closeButtonDown = false;
			}
		}
		if(DragManager.isHoverCharacterFrame()) {
			int xStuff = -280+xMouseShift+gemFrame;
			int i = 0;
			int j = 0;
			int z = 0;
			int yStuff = -302+yMouseShift;
			float yShift = 43.8f;
			float xShift = 47.5f;
			while(i < Mideas.joueur1().getStuff().length) {
				isHover(i, xStuff+z*xShift, yStuff+j*yShift);
				i++;
				if(i <= 16) {
					j++;
				}
				else {
					z++;
				}
				if(i == 8) {
					xStuff = 43+xMouseShift+gemFrame;
					j = 0;
				}
				else if(i == 16) {
					xStuff = -166+xMouseShift+gemFrame;
					yStuff = 31+yMouseShift;
					j = 0;
				}
			}
			i = 0;
			if(!Mouse.getEventButtonState()) {
				if(Mouse.getEventButton() == 1) {
					while(i < Mideas.joueur1().getStuff().length) {
						if(hoverCharacterFrame[i] && Mideas.joueur1().getStuff(i) != null && Mideas.joueur1().getStuff(i).getGemSlot1() != GemColor.NONE) {
							gemFrameOpen = true;
							gemFrame = Sprites.gem_frame.getImageWidth();
							break;
						}
						i++;
					}
				}
			}
		}
		return false;
	}
	
	private static void characterItemHover(int i, int x, int z) {
		Color temp = null;
		int xShift = 237;
		int shift = 45;
		int yAnchor = 0;
		String classe = "";
		Stuff item = Mideas.joueur1().getStuff(i);
		if(item != null && hoverCharacterFrame[i]) {
			if(item.getClassType().length < 10) {
				classe = "Classes: ";
				int k = 0;
				while(k < item.getClassType().length) {
					if(k == item.getClassType().length-1) {
						classe+= item.convClassTypeToString(k);
					}
					else {
						classe+= item.convClassTypeToString(k)+", ";
					}
					k++;
				}
			}
			xShift = Math.max(TTF2.itemName.getWidth(item.getStuffName()), TTF2.statsName.getWidth(classe))+15;
			int y = -75-TTF2.statsName.getLineHeight()*ContainerFrame.getNumberStats(item);
			if(Display.getHeight()/2+z-2+y < 0) {
				yAnchor = -(Display.getHeight()/2+z-2+y)+3;
			}
			Draw.drawColorQuad(Display.getWidth()/2+x-1, Display.getHeight()/2+z-2+yAnchor, 5+xShift, y, bgColor);
			Draw.drawColorQuadBorder(Display.getWidth()/2+x-1, Display.getHeight()/2+z-2+yAnchor, 6+xShift, y, borderColor);
			TTF2.itemName.drawStringShadow(Display.getWidth()/2+x+5, Display.getHeight()/2+z+y+yAnchor, item.getStuffName(), ContainerFrame.getItemNameColor(item), Color.black, 1);
			TTF2.statsName.drawStringShadow(Display.getWidth()/2+x+5, Display.getHeight()/2+z+y+23+yAnchor, item.convStuffTypeToString(), Color.white, Color.black, 1);
			if(DragManager.canWear(item)) {
				temp = Color.white;
			}
			else {
				temp = Color.red;
			}
			TTF2.statsName.drawStringShadow(Display.getWidth()/2+x-10+xShift-TTF2.font4.getWidth(item.convWearToString()), Display.getHeight()/2+y+23+z+yAnchor, item.convWearToString(), temp, Color.black, 1);
			if(item.getArmor() > 0) {
				TTF2.statsName.drawStringShadow(Display.getWidth()/2+x+5, Display.getHeight()/2+y+shift+z+yAnchor, "Armor : "+item.getArmor(), Color.white, Color.black, 1);
				shift+= 20;
			}
			if(item.getStrength() > 0) {
				TTF2.statsName.drawStringShadow(Display.getWidth()/2+x+5, Display.getHeight()/2+y+shift+z+yAnchor, "+"+item.getStrength()+" Strengh", Color.white, Color.black, 1);
				shift+= 20;
			}
			if(item.getMana() > 0) {
				TTF2.statsName.drawStringShadow(Display.getWidth()/2+x+5, Display.getHeight()/2+y+shift+z+yAnchor, "+"+item.getMana()+" Mana", Color.white, Color.black, 1);
				shift+= 20;
			}
			if(item.getStamina() > 0) {
				TTF2.statsName.drawStringShadow(Display.getWidth()/2+x+5, Display.getHeight()/2+y+shift+z+yAnchor, "+"+item.getStamina()+" Stamina", Color.white, Color.black, 1);
				shift+= 20;
			}
			if(item.getCritical() > 0) {
				TTF2.statsName.drawStringShadow(Display.getWidth()/2+x+5, Display.getHeight()/2+y+shift+z+yAnchor, "+"+item.getCritical()+" Critical", Color.white, Color.black, 1);
				shift+= 20;
			}
			if(item.getGemSlot1() != GemColor.NONE) {
				if(item.getEquippedGem1() != null) {
					Draw.drawQuad(GemManager.getGemSprite(item.getEquippedGem1().getId()), Display.getWidth()/2+x-2-xShift, Display.getHeight()/2+y+shift+z+yAnchor);
					TTF2.statsName.drawStringShadow(Display.getWidth()/2+x+15, Display.getHeight()/2+y+shift+z+yAnchor-3, ContainerFrame.writeGemStats(item.getEquippedGem1()), Color.white, Color.black, 1);
				}
				else {
					Draw.drawQuad(item.getFreeSlotGemSprite1(), Display.getWidth()/2+x+2, Display.getHeight()/2+y+shift+z+yAnchor);
					TTF2.statsName.drawStringShadow(Display.getWidth()/2+x+26, Display.getHeight()/2+y+shift+z+yAnchor-3, ContainerFrame.convGemColorToString(item.getGemSlot1())+" Socket", Color.gray, Color.black, 1);
				}
				Draw.drawQuad(Sprites.cursor, -5000, -5000);
				shift+= 20;
			}
			if(item.getGemSlot2() != GemColor.NONE) {
				if(item.getEquippedGem2() != null) {
					Draw.drawQuad(GemManager.getGemSprite(item.getEquippedGem2().getId()), Display.getWidth()/2+x+2, Display.getHeight()/2+y+shift+z+yAnchor);
					TTF2.statsName.drawStringShadow(Display.getWidth()/2+x+15, Display.getHeight()/2+y+shift+z+yAnchor-3, ContainerFrame.writeGemStats(item.getEquippedGem2()), Color.white, Color.black, 1);
				}
				else {
					Draw.drawQuad(item.getFreeSlotGemSprite2(), Display.getWidth()/2+x+2, Display.getHeight()/2+y+shift+z+yAnchor);
					TTF2.statsName.drawStringShadow(Display.getWidth()/2+x+26, Display.getHeight()/2+y+shift+z+yAnchor-3, ContainerFrame.convGemColorToString(item.getGemSlot2())+" Socket", Color.gray, Color.black, 1);
				}
				Draw.drawQuad(Sprites.cursor, -5000, -5000);
				shift+= 20;
			}
			if(item.getGemSlot3() != GemColor.NONE) {
				if(item.getEquippedGem3() != null) {
					Draw.drawQuad(GemManager.getGemSprite(item.getEquippedGem3().getId()), Display.getWidth()/2+x+2, Display.getHeight()/2+y+shift+z+yAnchor);
					TTF2.statsName.drawStringShadow(Display.getWidth()/2+x+15, Display.getHeight()/2+y+shift+z+yAnchor-3, ContainerFrame.writeGemStats(item.getEquippedGem3()), Color.white, Color.black, 1);
				}
				else {
					Draw.drawQuad(item.getFreeSlotGemSprite3(), Display.getWidth()/2+x+2, Display.getHeight()/2+y+shift+z+yAnchor);
					TTF2.statsName.drawStringShadow(Display.getWidth()/2+x+26, Display.getHeight()/2+y+shift+z+yAnchor-3, ContainerFrame.convGemColorToString(item.getGemSlot3())+" Socket", Color.gray, Color.black, 1);
				}
				Draw.drawQuad(Sprites.cursor, -5000, -5000);
				shift+= 20;
			}
			if(item.getGemBonusType() != GemBonusType.NONE) {
				if(item.getGemBonusActivated()) {
					TTF2.statsName.drawStringShadow(Display.getWidth()/2+x+5, Display.getHeight()/2+y+shift+z+yAnchor-3, "Socket bonus: +"+item.getGemBonusValue()+" "+ContainerFrame.convGemBonusTypeToString(item.getGemBonusType()), Color.white, Color.black, 1);
				}
				else {
					TTF2.statsName.drawStringShadow(Display.getWidth()/2+x+5, Display.getHeight()/2+y+shift+z-3+yAnchor, "Socket Bonus: +"+item.getGemBonusValue()+" "+ContainerFrame.convGemBonusTypeToString(item.getGemBonusType()), Color.gray, Color.black, 1);
				}
				shift+= 20;
			}
			if(item.canEquipTo(DragManager.convClassType())) {
				temp = Color.white;
			}
			else {
				temp = Color.red;
			}
			if(!classe.equals("")) {
				TTF2.statsName.drawStringShadow(Display.getWidth()/2+x+5, Display.getHeight()/2+y+shift+z+yAnchor, classe, temp, Color.black, 1);
				shift+= 20;
			}
			if(Mideas.getLevel() >= item.getLevel()) {
				TTF2.statsName.drawStringShadow(Display.getWidth()/2+x+5, Display.getHeight()/2+y+shift+z+yAnchor, "Level "+item.getLevel()+" required", Color.white, Color.black, 1);
			}
			else {
				TTF2.statsName.drawStringShadow(Display.getWidth()/2+x+5, Display.getHeight()/2+y+shift+z+yAnchor, "Level "+item.getLevel()+" required", Color.red, Color.black, 1);
			}
		}
	}
	
	private static void drawCharacterItems(int i, float x, float y) {
		Stuff stuff = Mideas.joueur1().getStuff(i);
		if(stuff != null) {
			Draw.drawQuad(IconsManager.getSprite37((stuff.getSpriteId())), Display.getWidth()/2+x, Display.getHeight()/2+y);
			Draw.drawQuad(Sprites.spell_border, Display.getWidth()/2+x-2, Display.getHeight()/2+y-2);
			if(stuff == DragManager.getDraggedItem()) {
				Draw.drawColorQuad(Display.getWidth()/2+x, Display.getHeight()/2+y, 37, 35, bgColor);
			}
			if(DragManager.getDraggedItem() != null && !hoverCharacterFrame[i] && DragManager.getDraggedItem().getItemType() == ItemType.STUFF && ((Stuff)DragManager.getDraggedItem()).getType() == stuff.getType()) {
				if(hover%2 == 0) {
					Draw.drawQuad(Sprites.spell_hover, Display.getWidth()/2+x-3, Display.getHeight()/2+y-3);
				}
				else {
					Draw.drawQuad(Sprites.spell_hover2, Display.getWidth()/2+x-3, Display.getHeight()/2+y-3);
				}
			}
		}
		if(hoverCharacterFrame[i]) {
			Draw.drawQuad(Sprites.spell_hover, Display.getWidth()/2+x-2, Display.getHeight()/2+y-2);
		}
		if(DragManager.getClickInventory(i) && DragManager.getDraggedItem() == null) {
			Draw.drawQuad(Sprites.inventory_click_hover, Display.getWidth()/2+x-3, Display.getHeight()/2+y-3);
		}
		hover++;
	}
	
	private static void isHover(int i, float x, float y) {
		if(Mideas.mouseX() >= Display.getWidth()/2+x && Mideas.mouseX() <= Display.getWidth()/2+x+37 && Mideas.mouseY() >= Display.getHeight()/2+y && Mideas.mouseY() <= Display.getHeight()/2+y+38) {
			hoverCharacterFrame[i] = true;
			hoveredSlot = i;
		}
	}
	
	public static void setMouseX(int x) {
		xMouseShift = x;
		lastMouseX = x;
	}
	
	public static int getMouseX() {
		return xMouseShift;
	}
	
	public static void setMouseY(int y) {
		yMouseShift = y;
		lastMouseY = y;
	}
	
	public static int getMouseY() {
		return yMouseShift;
	}
	
	public static boolean getHoverCharacterFrame(int i) {
		if(i < hoverCharacterFrame.length) {
			return hoverCharacterFrame[i];
		}
		return false;
	}
	
	public static boolean[] getHoverCharacterFrame() {
		return hoverCharacterFrame;
	}
}
