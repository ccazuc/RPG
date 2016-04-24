package com.mideas.rpg.v2.hud;

import java.util.Arrays;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;

import com.mideas.rpg.v2.Interface;
import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.Sprites;
import com.mideas.rpg.v2.TTF2;
import com.mideas.rpg.v2.game.IconsManager;
import com.mideas.rpg.v2.game.item.ItemType;
import com.mideas.rpg.v2.game.item.stuff.Stuff;
import com.mideas.rpg.v2.utils.Draw;

public class CharacterFrame {
	
	private static boolean[] hoverCharacterFrame = new boolean[20];
	private static boolean hoverCloseButton;
	private static boolean closeButtonDown;
	private static Color bgColor = new Color(0, 0, 0, .6f); 
	private static Color borderColor = Color.decode("#494D4B");
	private static int hover;
	private static int xMouseShift;
	private static int yMouseShift;
	private static boolean hoverMove;
	private static boolean moving;
	private static int baseMouseX;
	private static int baseMouseY;
	private static int lastMouseX;
	private static int lastMouseY;

	public static void draw() {
		hover = 0;
		Draw.drawQuad(Sprites.character_frame, Display.getWidth()/2-300+xMouseShift, Display.getHeight()/2-380+yMouseShift);
		Draw.drawQuad(Sprites.character_frame_stats, Display.getWidth()/2-224+xMouseShift, Display.getHeight()/2-60+yMouseShift);
		TTF2.characterFrameStats.drawStringShadow(Display.getWidth()/2-215+xMouseShift, Display.getHeight()/2-27+yMouseShift, "Strength:", Color.yellow, Color.black, 1, 1, 1);
		TTF2.characterFrameStats.drawStringShadow(Display.getWidth()/2-88+xMouseShift-TTF2.characterFrameStats.getWidth(String.valueOf(Mideas.joueur1().getStrength())), Display.getHeight()/2-27+yMouseShift, String.valueOf(Mideas.joueur1().getStrength()), Color.white, Color.black, 1, 1, 1);
		TTF2.characterFrameStats.drawStringShadow(Display.getWidth()/2-215+xMouseShift, Display.getHeight()/2-15+yMouseShift, "Stamina:", Color.yellow, Color.black, 1, 1, 1);
		TTF2.characterFrameStats.drawStringShadow(Display.getWidth()/2-88+xMouseShift-TTF2.characterFrameStats.getWidth(String.valueOf(Mideas.joueur1().getMaxStamina()/10)), Display.getHeight()/2-15+yMouseShift, String.valueOf(Mideas.joueur1().getMaxStamina()/10), Color.white, Color.black, 1, 1, 1);
		TTF2.characterFrameStats.drawStringShadow(Display.getWidth()/2-215+xMouseShift, Display.getHeight()/2-3+yMouseShift, "Armor:", Color.yellow, Color.black, 1, 1, 1);
		TTF2.characterFrameStats.drawStringShadow(Display.getWidth()/2-88+xMouseShift-TTF2.characterFrameStats.getWidth(String.valueOf((int)Mideas.joueur1().getArmor())), Display.getHeight()/2-3+yMouseShift, String.valueOf((int)Mideas.joueur1().getArmor()), Color.white, Color.black, 1, 1, 1);
		TTF2.characterFrameStats.drawStringShadow(Display.getWidth()/2-215+xMouseShift, Display.getHeight()/2+9+yMouseShift, "Critical:", Color.yellow, Color.black, 1, 1, 1);
		TTF2.characterFrameStats.drawStringShadow(Display.getWidth()/2-88+xMouseShift-TTF2.characterFrameStats.getWidth(String.valueOf(Mideas.joueur1().getCritical())+"%"), Display.getHeight()/2+9+yMouseShift, String.valueOf(Mideas.joueur1().getCritical())+"%", Color.white, Color.black, 1, 1, 1);
		TTF2.characterFrameStats.drawStringShadow(Display.getWidth()/2-215+xMouseShift, Display.getHeight()/2+21+yMouseShift, "Mana:", Color.yellow, Color.black, 1, 1, 1);
		TTF2.characterFrameStats.drawStringShadow(Display.getWidth()/2-88+xMouseShift-TTF2.characterFrameStats.getWidth(String.valueOf(Mideas.joueur1().getMana())), Display.getHeight()/2+21+yMouseShift, String.valueOf(Mideas.joueur1().getMana()), Color.white, Color.black, 1, 1, 1);
		int xStuff = -281;
		/*drawCharacterItems(Mideas.joueur1().getStuff(0), xStuff+xMouseShift, -288+yMouseShift);
		drawCharacterItems(Mideas.joueur1().getStuff(1), xStuff+xMouseShift, -240+yMouseShift);
		drawCharacterItems(Mideas.joueur1().getStuff(2), xStuff+xMouseShift, -192+yMouseShift);
		drawCharacterItems(Mideas.joueur1().getStuff(3), xStuff+xMouseShift, -144+yMouseShift);
		drawCharacterItems(Mideas.joueur1().getStuff(4), xStuff+xMouseShift, -93+yMouseShift);
		//drawCharacterItems(Mideas.joueur1().getNecklace(), Sprites.stuff_border6, -281, -194);
		//drawCharacterItems(Mideas.joueur1().getChest(), Sprites.stuff_border7, -281, -194);
		drawCharacterItems(Mideas.joueur1().getStuff(7), xStuff+xMouseShift, 54+yMouseShift);
		xStuff = 79;
		drawCharacterItems(Mideas.joueur1().getStuff(8), xStuff+xMouseShift, -288+yMouseShift);
		drawCharacterItems(Mideas.joueur1().getStuff(9), xStuff+xMouseShift, -240+yMouseShift);
		drawCharacterItems(Mideas.joueur1().getStuff(10), xStuff+xMouseShift, -192+yMouseShift);
		drawCharacterItems(Mideas.joueur1().getStuff(11), xStuff+xMouseShift, -144+yMouseShift);
		drawCharacterItems(Mideas.joueur1().getStuff(12), xStuff+xMouseShift, -93+yMouseShift);
		drawCharacterItems(Mideas.joueur1().getStuff(13), xStuff+xMouseShift, -42+yMouseShift);
		drawCharacterItems(Mideas.joueur1().getStuff(14), xStuff+xMouseShift, 6+yMouseShift);
		drawCharacterItems(Mideas.joueur1().getStuff(15), xStuff+xMouseShift, 56+yMouseShift);*/
		int i = 0;
		int j = 0;
		int z = 0;
		int yStuff = -288;
		float yShift = 48.8f;
		int xShift = 53;
		while(i < Mideas.joueur1().getStuff().length) {
			drawCharacterItems(Mideas.joueur1().getStuff(i), xStuff+z*xShift+xMouseShift, yStuff+j*yShift+yMouseShift);
			i++;
			if(i <= 16) {
				j++;
			}
			else {
				z++;
			}
			if(i == 8) {
				xStuff = 79;
				j = 0;
			}
			else if(i == 16) {
				xStuff = -154;
				yStuff = 85;
				j = 0;
			}
		}
		/*drawCharacterItems(Mideas.joueur1().getStuff(16), -154+xMouseShift, 85+yMouseShift);
		drawCharacterItems(Mideas.joueur1().getStuff(17), -101+xMouseShift, 85+yMouseShift);
		drawCharacterItems(Mideas.joueur1().getStuff(18), -48+xMouseShift, 85+yMouseShift);*/
		if(hoverCloseButton) {
			if(closeButtonDown) {
				Draw.drawQuad(Sprites.close_button_inventory_down_hover, Display.getWidth()/2+109+xMouseShift, Display.getHeight()/2-362+yMouseShift);
			}
			else {
				Draw.drawQuad(Sprites.close_button_inventory_hover, Display.getWidth()/2+109+xMouseShift, Display.getHeight()/2-362+yMouseShift);
			}
		}
		else if(closeButtonDown) {
			Draw.drawQuad(Sprites.close_button_inventory_down, Display.getWidth()/2+109+xMouseShift, Display.getHeight()/2-362+yMouseShift);
		}
		//int x_hoverLeft = -284;
		//int x_hoverRight = 76;
		//int x = -568;
		//int y = -277;
		if(DragManager.isHoverCharacterFrame()) {
			i = 0;
			int x_item = -580;
			int y_item = -380;
			int x_hover = -285;
			int y_hover = -290;
			z = 0;
			j = 0;
			while(i < Mideas.joueur1().getStuff().length) {
				characterItemHover(i, x_item+z*yShift+xMouseShift, y_item+j*yShift+yMouseShift, x_hover+z*yShift+xMouseShift, y_hover+j*yShift+yMouseShift);
				i++;
				if(i < 16) {
					j++;
				}
				else {
					j = 0;
					x_hover = -205;
					x_item = -270;
					y_item = 115;
					y_hover = 80;
					z++;
				}
				if(i == 17) {
					yShift = 51;
				}
				if(i == 8) {
					j = 0;
					x_item = 140;
					x_hover = 75;
				}
			}
		}
	}

	public static boolean mouseEvent() {
		int xLeft = -283+xMouseShift;
		int xRight = 78+xMouseShift;
		int y = -290;
		int yShift = 49;
		hoverMove = false;
		hoverCloseButton = false;
		Arrays.fill(hoverCharacterFrame, false);
		if(Mideas.mouseX() >= Display.getWidth()/2+112+xMouseShift && Mideas.mouseX() <= Display.getWidth()/2+132+xMouseShift && Mideas.mouseY() >= Display.getHeight()/2-360+yMouseShift && Mideas.mouseY() <= Display.getHeight()/2-342+yMouseShift) {
			hoverCloseButton = true;
		}
		else if(Mideas.mouseX()>= Display.getWidth()/2-300+xMouseShift && Mideas.mouseX() <= Display.getWidth()/2-300+xMouseShift+Sprites.character_frame.getImageWidth() && Mideas.mouseY() >= Display.getHeight()/2-360+yMouseShift && Mideas.mouseY() <= Display.getHeight()/2-360+yMouseShift+5) {
			hoverMove = true;
		}
		if(moving) {
			yMouseShift = Mideas.mouseY()-baseMouseY+lastMouseY;
			xMouseShift = Mideas.mouseX()-baseMouseX+lastMouseX;
		}
		if(Mouse.getEventButtonState()) {
			if(Mouse.getEventButton() == 0 || Mouse.getEventButton() == 1) {
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
			isHover(xLeft, y+yMouseShift, 0);
			isHover(xLeft, y+yShift+yMouseShift, 1);
			isHover(xLeft, y+2*yShift+yMouseShift, 2);
			isHover(xLeft, y+3*yShift+yMouseShift, 3);
			isHover(xLeft, y+4*yShift+yMouseShift, 4);
			isHover(xLeft, y+5*yShift+yMouseShift, 5);
			isHover(xLeft, y+6*yShift+yMouseShift, 6);
			isHover(xLeft, y+7*yShift+yMouseShift, 7);
			isHover(xRight, y+yMouseShift, 8);
			isHover(xRight, y+1*yShift+yMouseShift, 9);
			isHover(xRight, y+2*yShift+yMouseShift, 10);
			isHover(xRight, y+3*yShift+yMouseShift, 11);
			isHover(xRight, y+4*yShift+yMouseShift, 12);
			isHover(xRight, y+5*yShift+yMouseShift, 13);
			isHover(xRight, y+6*yShift+yMouseShift, 14);
			isHover(xRight, y+7*yShift+yMouseShift, 15);
			isHover(xLeft+126, y+372+yMouseShift, 16);
			isHover(xLeft+180, y+372+yMouseShift, 17);
			isHover(xLeft+233, y+372+yMouseShift, 18);
		}
		return false;
	}
	
	/*public static void characterLeftItemsHover(boolean hoverCharacter, Stuff stuff, int x_stuff_hover, int x_item, int y_item, int move_item_name, int x_bg, int y_bg, int y_stuff_hover, int x_item_name, int y_item_name, String base_name) {
		if(hoverCharacter) {
			if(stuff == null) {
				Draw.drawColorQuad(Display.getWidth()/2+x_bg, Display.getHeight()/2+y_bg, 150, 80, bgColor);
				TTF2.itemName.drawStringShadow(Display.getWidth()/2+x_item_name, Display.getHeight()/2+y_item_name, base_name, Color.white, Color.black, 1, 1, 1);
			}
			else {
				Draw.drawColorQuad(Display.getWidth()+x_item-300, Display.getHeight()+30+y+z, 285, 60+TTF2.statsName.getLineHeight()*getNumberStats((Stuff)Mideas.bag().getBag(i)), bgColor);
				Draw.drawColorQuadBorder(Display.getWidth()+x_item-301, Display.getHeight()+30+y+z, 287, 60+TTF2.statsName.getLineHeight()*getNumberStats((Stuff)Mideas.bag().getBag(i)), borderColor);
				TTF2.itemName.drawStringShadow(Display.getWidth()+x_item-295, Display.getHeight()+y+32+z, Mideas.bag().getBag(i).getStuffName(), getItemNameColor(Mideas.bag().getBag(i)), Color.black, 1, 1, 1);
				//Draw.drawColorQuad(Display.getWidth()+x-300, Display.getHeight()+30+y+z, 285, 80+TTF2.statsName.getLineHeight()*4, bgColor);
				if(((Stuff)Mideas.bag().getBag(i)).getArmor() > 0) {
					TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()+y+shift+z, "Armor : "+((Stuff)Mideas.bag().getBag(i)).getArmor(), Color.white, Color.black, 1, 1, 1);
					shift+= 20;
				}
				if(((Stuff)Mideas.bag().getBag(i)).getStrength() > 0) {
					TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()+y+shift+z, "+ "+((Stuff)Mideas.bag().getBag(i)).getStrength()+" Strengh", Color.white, Color.black, 1, 1, 1);
					shift+= 20;
				}
				if(((Stuff)Mideas.bag().getBag(i)).getMana() > 0) {
					TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()+y+shift+z, "+ "+((Stuff)Mideas.bag().getBag(i)).getMana()+" Mana", Color.white, Color.black, 1, 1, 1);
					shift+= 20;
				}
				if(((Stuff)Mideas.bag().getBag(i)).getStamina() > 0) {
					TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()+y+shift+z, "+ "+((Stuff)Mideas.bag().getBag(i)).getStamina()+" Stamina", Color.white, Color.black, 1, 1, 1);
					shift+= 20;
				}
				if(((Stuff)Mideas.bag().getBag(i)).getCritical() > 0) {
					TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()+y+shift+z, "+ "+((Stuff)Mideas.bag().getBag(i)).getCritical()+" Critical", Color.white, Color.black, 1, 1, 1);
					shift+= 20;
				}
				if(Mideas.getLevel() >= ((Stuff)Mideas.bag().getBag(i)).getLevel()) {
					TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()+y+shift+z, "Level "+((Stuff)Mideas.bag().getBag(i)).getLevel()+" required", Color.white, Color.black, 1, 1, 1);
				}
				else {
					TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()+y+shift+z, "Level "+((Stuff)Mideas.bag().getBag(i)).getLevel()+" required", Color.red, Color.black, 1, 1, 1);
				}	
			}
			Draw.drawQuad(Sprites.stuff_hover, Display.getWidth()/2+x_stuff_hover, Display.getHeight()/2+y_stuff_hover);
		}
	}*/
	
	private static void characterItemHover(int i, float x_item, float y_item, float x_hover, float y_hover) {
		if(hoverCharacterFrame[i]) {
			if(Mideas.joueur1().getStuff(i) != null) {
				int shift = 60;
				Draw.drawColorQuad(Display.getWidth()/2+x_item, Display.getHeight()/2+30+y_item, 285, 60+TTF2.statsName.getLineHeight()*ContainerFrame.getNumberStats(Mideas.joueur1().getStuff(i)), bgColor);
				Draw.drawColorQuadBorder(Display.getWidth()/2+x_item, Display.getHeight()/2+30+y_item, 287, 60+TTF2.statsName.getLineHeight()*ContainerFrame.getNumberStats(Mideas.joueur1().getStuff(i)), borderColor);
				TTF2.itemName.drawStringShadow(Display.getWidth()/2+x_item+5, Display.getHeight()/2+y_item+32, Mideas.joueur1().getStuff(i).getStuffName(), ContainerFrame.getItemNameColor(Mideas.joueur1().getStuff(i)), Color.black, 1, 1, 1);
				//Draw.drawColorQuad(Display.getWidth()+x-300, Display.getHeight()+30+y+z, 285, 80+TTF2.statsName.getLineHeight()*4, bgColor);
				if(Mideas.joueur1().getStuff(i).getArmor() > 0) {
					TTF2.statsName.drawStringShadow(Display.getWidth()/2+x_item+5, Display.getHeight()/2+y_item+shift, "Armor : "+Mideas.joueur1().getStuff(i).getArmor(), Color.white, Color.black, 1, 1, 1);
					shift+= 20;
				}
				if(Mideas.joueur1().getStuff(i).getStrength() > 0) {
					TTF2.statsName.drawStringShadow(Display.getWidth()/2+x_item+5, Display.getHeight()/2+y_item+shift, "+ "+Mideas.joueur1().getStuff(i).getStrength()+" Strengh", Color.white, Color.black, 1, 1, 1);
					shift+= 20;
				}
				if(Mideas.joueur1().getStuff(i).getMana() > 0) {
					TTF2.statsName.drawStringShadow(Display.getWidth()/2+x_item+5, Display.getHeight()/2+y_item+shift, "+ "+Mideas.joueur1().getStuff(i).getMana()+" Mana", Color.white, Color.black, 1, 1, 1);
					shift+= 20;
				}
				if(Mideas.joueur1().getStuff(i).getStamina() > 0) {
					TTF2.statsName.drawStringShadow(Display.getWidth()/2+x_item+5, Display.getHeight()/2+y_item+shift, "+ "+Mideas.joueur1().getStuff(i).getStamina()+" Stamina", Color.white, Color.black, 1, 1, 1);
					shift+= 20;
				}
				if(Mideas.joueur1().getStuff(i).getCritical() > 0) {
					TTF2.statsName.drawStringShadow(Display.getWidth()/2+x_item+5, Display.getHeight()/2+y_item+shift, "+ "+Mideas.joueur1().getStuff(i).getCritical()+" Critical", Color.white, Color.black, 1, 1, 1);
					shift+= 20;
				}
				if(Mideas.getLevel() >= Mideas.joueur1().getStuff(i).getLevel()) {
					TTF2.statsName.drawStringShadow(Display.getWidth()/2+x_item+5, Display.getHeight()/2+y_item+shift, "Level "+Mideas.joueur1().getStuff(i).getLevel()+" required", Color.white, Color.black, 1, 1, 1);
				}
				else {
					TTF2.statsName.drawStringShadow(Display.getWidth()/2+x_item+5, Display.getHeight()/2+y_item+shift, "Level "+Mideas.joueur1().getStuff(i).getLevel()+" required", Color.red, Color.black, 1, 1, 1);
				}	
			}
			Draw.drawQuad(Sprites.stuff_hover, Display.getWidth()/2+x_hover, Display.getHeight()/2+y_hover);
		}
	}
	
	/*private static void characterRightItemsHover(boolean hoverCharacter, Stuff stuff, int x_stuff_hover, int x_item, int y_item, int move_item_name, int x_bg, int y_bg, int y_stuff_hover, int x_item_name, int y_item_name, String base_name) {
		if(hoverCharacter) {
			if(stuff == null) {
				Draw.drawColorQuad(Display.getWidth()/2+160, Display.getHeight()/2+y_bg, 150, 80, bgColor);
				TTF2.itemName.drawStringShadow(Display.getWidth()/2+x_item_name, Display.getHeight()/2+y_item_name, base_name, Color.white, Color.black, 1, 1, 1);
			}
			else {
				Draw.drawColorQuad(Display.getWidth()/2+x_bg+50+TTF2.itemName.getWidth(stuff.getStuffName())/2, Display.getHeight()/2+y_bg, 150+TTF2.itemName.getWidth(stuff.getStuffName())/2, 80+TTF2.statsName.getLineHeight()*4, bgColor);
				TTF2.itemName.drawStringShadow(Display.getWidth()/2+x_item+move_item_name+TTF2.itemName.getWidth(stuff.getStuffName())/2, Display.getHeight()/2+y_item+11, stuff.getStuffName(), Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()/2+x_item, Display.getHeight()/2+y_item+33, "+"+stuff.getArmor()+" Armor", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()/2+x_item, Display.getHeight()/2+y_item+53, "+"+stuff.getStamina()+" Stamina", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()/2+x_item, Display.getHeight()/2+y_item+73, "+"+stuff.getMana()+" Mana", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()/2+x_item, Display.getHeight()/2+y_item+93, "+"+stuff.getStrength()+" Strengh", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()/2+x_item, Display.getHeight()/2+y_item+113, "+"+stuff.getCritical()+" Critical", Color.white, Color.black, 1, 1, 1);
			}
			Draw.drawQuad(Sprites.stuff_hover, Display.getWidth()/2+x_stuff_hover, Display.getHeight()/2+y_stuff_hover);
		}
	}*/
	
	private static void drawCharacterItems(Stuff stuff, float x, float y) {
		if(stuff != null) {
			if(!(stuff == DragManager.getDraggedItem())) {
				Draw.drawQuad(IconsManager.getSprite42((stuff.getSpriteId())), Display.getWidth()/2+x, Display.getHeight()/2+y);
				Draw.drawQuad(Sprites.stuff_border, Display.getWidth()/2+x-5, Display.getHeight()/2+y-5);
			}
			if(DragManager.getDraggedItem() != null && DragManager.getDraggedItem().getItemType() == ItemType.STUFF && ((Stuff)DragManager.getDraggedItem()).getType() == stuff.getType()) {
				if(hover%2 == 0) {
					Draw.drawQuad(Sprites.stuff_hover, Display.getWidth()/2+x-3, Display.getHeight()/2+y-3);
				}
				else {
					Draw.drawQuad(Sprites.stuff_hover2, Display.getWidth()/2+x-3, Display.getHeight()/2+y-3);
				}
			}
		}
		hover++;
	}
	
	private static void isHover(int x, int y, int i) {
		if(Mideas.mouseX() >= Display.getWidth()/2+x && Mideas.mouseX() <= Display.getWidth()/2+x+48 && Mideas.mouseY() >= Display.getHeight()/2+y && Mideas.mouseY() <= Display.getHeight()/2+y+48) {
			hoverCharacterFrame[i] = true;
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
