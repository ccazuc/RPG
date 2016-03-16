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
import com.mideas.rpg.v2.game.item.stuff.Stuff;
import com.mideas.rpg.v2.utils.Draw;

public class CharacterFrame {
	
	private static boolean[] hoverCharacterFrame = new boolean[20];
	private static boolean hoverCloseButton;
	private static Color bgColor = new Color(0, 0, 0,.8f); 
	
	public static void draw() {
		
		bgColor = new Color(0, 0, 0,.8f); 
		Draw.drawQuad(Sprites.character_frame, Display.getWidth()/2-300, Display.getHeight()/2-380);
		Draw.drawQuad(Sprites.character_frame_stats, Display.getWidth()/2-224, Display.getHeight()/2-60);
		TTF2.characterFrameStats.drawStringShadow(Display.getWidth()/2-215, Display.getHeight()/2-27, "Strength:", Color.yellow, Color.black, 1, 1, 1);
		TTF2.characterFrameStats.drawStringShadow(Display.getWidth()/2-88-TTF2.characterFrameStats.getWidth(String.valueOf(Mideas.joueur1().getStrength())), Display.getHeight()/2-27, String.valueOf(Mideas.joueur1().getStrength()), Color.white, Color.black, 1, 1, 1);
		TTF2.characterFrameStats.drawStringShadow(Display.getWidth()/2-215, Display.getHeight()/2-15, "Stamina:", Color.yellow, Color.black, 1, 1, 1);
		TTF2.characterFrameStats.drawStringShadow(Display.getWidth()/2-88-TTF2.characterFrameStats.getWidth(String.valueOf(Mideas.joueur1().getMaxStamina()/10)), Display.getHeight()/2-15, String.valueOf(Mideas.joueur1().getMaxStamina()/10), Color.white, Color.black, 1, 1, 1);
		TTF2.characterFrameStats.drawStringShadow(Display.getWidth()/2-215, Display.getHeight()/2-3, "Armor:", Color.yellow, Color.black, 1, 1, 1);
		TTF2.characterFrameStats.drawStringShadow(Display.getWidth()/2-88-TTF2.characterFrameStats.getWidth(String.valueOf((int)Mideas.joueur1().getArmor())), Display.getHeight()/2-3, String.valueOf((int)Mideas.joueur1().getArmor()), Color.white, Color.black, 1, 1, 1);
		TTF2.characterFrameStats.drawStringShadow(Display.getWidth()/2-215, Display.getHeight()/2+9, "Critical:", Color.yellow, Color.black, 1, 1, 1);
		TTF2.characterFrameStats.drawStringShadow(Display.getWidth()/2-88-TTF2.characterFrameStats.getWidth(String.valueOf(Mideas.joueur1().getCritical())+"%"), Display.getHeight()/2+9, String.valueOf(Mideas.joueur1().getCritical())+"%", Color.white, Color.black, 1, 1, 1);
		TTF2.characterFrameStats.drawStringShadow(Display.getWidth()/2-215, Display.getHeight()/2+21, "Mana:", Color.yellow, Color.black, 1, 1, 1);
		TTF2.characterFrameStats.drawStringShadow(Display.getWidth()/2-88-TTF2.characterFrameStats.getWidth(String.valueOf(Mideas.joueur1().getMana())), Display.getHeight()/2+21, String.valueOf(Mideas.joueur1().getMana()), Color.white, Color.black, 1, 1, 1);
		drawCharacterItems(Mideas.joueur1().getStuff(0), -281, -288);
		drawCharacterItems(Mideas.joueur1().getStuff(1), -281, -240);
		drawCharacterItems(Mideas.joueur1().getStuff(2), -281, -192);
		drawCharacterItems(Mideas.joueur1().getStuff(3), -281, -144);
		drawCharacterItems(Mideas.joueur1().getStuff(4), -281, -93);
		//drawCharacterItems(Mideas.joueur1().getNecklace(), Sprites.stuff_border6, -281, -194);
		//drawCharacterItems(Mideas.joueur1().getChest(), Sprites.stuff_border7, -281, -194);
		drawCharacterItems(Mideas.joueur1().getStuff(7), -281, 54);
		drawCharacterItems(Mideas.joueur1().getStuff(8), 79, -288);
		drawCharacterItems(Mideas.joueur1().getStuff(9), 79, -240);
		drawCharacterItems(Mideas.joueur1().getStuff(10), 79, -192);
		drawCharacterItems(Mideas.joueur1().getStuff(11), 79, -144);
		drawCharacterItems(Mideas.joueur1().getStuff(12), 79, -93);
		drawCharacterItems(Mideas.joueur1().getStuff(13), 79, -42);
		drawCharacterItems(Mideas.joueur1().getStuff(14), 79, 6);
		drawCharacterItems(Mideas.joueur1().getStuff(15), 79, 56);
		drawCharacterItems(Mideas.joueur1().getStuff(16), -154, 85);
		drawCharacterItems(Mideas.joueur1().getStuff(17), -101, 85);
		drawCharacterItems(Mideas.joueur1().getStuff(18), -48, 85);
		if(hoverCloseButton) {
			Draw.drawQuad(Sprites.close_hover, Display.getWidth()/2+109, Display.getHeight()/2-362);
		}
		int x_hoverLeft = -284;
		int x_hoverRight = 76;
		//int x = -568;
		//int y = -277;
		characterLeftItemsHover(hoverCharacterFrame[0], Mideas.joueur1().getStuff(0), x_hoverLeft, -568, -257, 110, -460, -280, -290, -410, -272, "Head");
		characterLeftItemsHover(hoverCharacterFrame[1], Mideas.joueur1().getStuff(1), x_hoverLeft, -540, -227, 85, -460, -250, -242, -425, -240, "Necklace");
		characterLeftItemsHover(hoverCharacterFrame[2], Mideas.joueur1().getStuff(2), x_hoverLeft, -550, -177, 85, -460, -200, -194, -428, -190, "Shoulders");
		characterLeftItemsHover(hoverCharacterFrame[3], Mideas.joueur1().getStuff(3), x_hoverLeft, -540, -127, 85, -460, -150, -146, -410, -140, "Back");
		characterLeftItemsHover(hoverCharacterFrame[4], Mideas.joueur1().getStuff(4), x_hoverLeft, -540, -78, 85, -460, -100, -95, -392, -90, "Chest");
		//characterLeftItemsHover(hoverCharacterFrame[5], Mideas.joueur1().getChest(), x_hoverLeft, -540, -78, 85, -460, -100, -98, -392, -90);
		//characterLeftItemsHover(hoverCharacterFrame[6}, Mideas.joueur1().getChest(), x_hoverLeft, -540, -78, 85, -460, -100, -98, -392, -90);
		characterLeftItemsHover(hoverCharacterFrame[7], Mideas.joueur1().getStuff(7), x_hoverLeft, -540, -22, 85, -460, -43, 51, -417, 40, "Wrists");
		characterRightItemsHover(hoverCharacterFrame[8], Mideas.joueur1().getStuff(8), x_hoverRight, 172, -300, -115, 0, -300, -290, 203, -290, "Gloves");
		characterRightItemsHover(hoverCharacterFrame[9], Mideas.joueur1().getStuff(9), x_hoverRight, 170, -250, -115, 0, -250, -242, 212, -240, "Belt");
		characterRightItemsHover(hoverCharacterFrame[10], Mideas.joueur1().getStuff(10), x_hoverRight, 168, -200, -115, 0, -200, -194, 193, -190, "Leggings");
		characterRightItemsHover(hoverCharacterFrame[11], Mideas.joueur1().getStuff(11), x_hoverRight, 170, -158, -115, 0, -150, -146, 203, -140, "Boots");
		characterRightItemsHover(hoverCharacterFrame[12], Mideas.joueur1().getStuff(12), x_hoverRight, 168, -110, -115, 0, -100, -95, 203, -90, "Ring");
		characterRightItemsHover(hoverCharacterFrame[13], Mideas.joueur1().getStuff(13), x_hoverRight, 168, -60, -115, 0, -50, -45, 203, -40, "Ring2");
		characterRightItemsHover(hoverCharacterFrame[14], Mideas.joueur1().getStuff(14), x_hoverRight, 170, -10, -115, 0, 0, 3, 202, 10, "Trinket");
		characterRightItemsHover(hoverCharacterFrame[15], Mideas.joueur1().getStuff(15), x_hoverRight, 168, 40, -115, 0, 50, 52, 202, 60, "Trinket2");
		
		/*if(hoverCharacterFrame) {
			if(Mideas.joueur1.getHead() == null) {
				Draw.drawColorQuad(Display.getWidth()/2-460, Display.getHeight()/2-280, 150, 80, bgColor);
				TTF.itemName.drawStringShadow(Display.getWidth()/2-410, Display.getHeight()/2-270, "Head");
			}
			else {
				int x = -568;
				int y = -277;
				Draw.drawColorQuad(Display.getWidth()/2-460-TTF.itemName.getWidth(Mideas.joueur1.getHead().getStuffName())/2, Display.getHeight()/2-280, 150+TTF.itemName.getWidth(Mideas.joueur1.getHead().getStuffName())/2, 80+TTF.statsName.getHeight()*4, bgColor);
				TTF.itemName.drawStringShadow(Display.getWidth()/2+x+110-TTF.itemName.getWidth(Mideas.joueur1.getHead().getStuffName())/2, Display.getHeight()/2+y, Mideas.joueur1.getHead().getStuffName());
				TTF.statsName.drawStringShadow(Display.getWidth()/2+x, Display.getHeight()/2+y+20, "+"+Mideas.joueur1.getHead().getArmor()+" Armor");
				TTF.statsName.drawStringShadow(Display.getWidth()/2+x, Display.getHeight()/2+y+40, "+"+Mideas.joueur1.getHead().getStamina()+" Stamina");
				TTF.statsName.drawStringShadow(Display.getWidth()/2+x, Display.getHeight()/2+y+60, "+"+Mideas.joueur1.getHead().getMana()+" Mana");
				TTF.statsName.drawStringShadow(Display.getWidth()/2+x, Display.getHeight()/2+y+80, "+"+Mideas.joueur1.getHead().getStrengh()+" Strengh");
				TTF.statsName.drawStringShadow(Display.getWidth()/2+x, Display.getHeight()/2+y+100, "+"+Mideas.joueur1.getHead().getCritical()+" Critical");
				
			}
			Draw.drawQuad(Sprites.stuff_hover, Display.getWidth()/2+x_hoverLeft, Display.getHeight()/2-290);
		}
		if(hoverCharacterFrame2) {
			if(Mideas.joueur1.getNecklace() == null) {
				Draw.drawColorQuad(Display.getWidth()/2-460, Display.getHeight()/2-250, 150, 80, bgColor);
				TTF.itemName.drawStringShadow(Display.getWidth()/2-425, Display.getHeight()/2-240, "Necklace");
			}
			else { 
				int x = -540;
				int y = -227;
				Draw.drawColorQuad(Display.getWidth()/2+x+80-TTF.itemName.getWidth(Mideas.joueur1.getNecklace().getStuffName())/2, Display.getHeight()/2-250, 150+TTF.itemName.getWidth(Mideas.joueur1.getNecklace().getStuffName())/2, 80+TTF.statsName.getHeight()*4, bgColor);
				TTF.itemName.drawStringShadow(Display.getWidth()/2+x+85-TTF.itemName.getWidth(Mideas.joueur1.getNecklace().getStuffName())/2, Display.getHeight()/2+y-20, Mideas.joueur1.getNecklace().getStuffName());
				TTF.statsName.drawStringShadow(Display.getWidth()/2+x, Display.getHeight()/2+y, "+"+Mideas.joueur1.getNecklace().getArmor()+" Armor");
				TTF.statsName.drawStringShadow(Display.getWidth()/2+x, Display.getHeight()/2+y+20, "+"+Mideas.joueur1.getNecklace().getStamina()+" Stamina");
				TTF.statsName.drawStringShadow(Display.getWidth()/2+x, Display.getHeight()/2+y+40, "+"+Mideas.joueur1.getNecklace().getMana()+" Mana");
				TTF.statsName.drawStringShadow(Display.getWidth()/2+x, Display.getHeight()/2+y+60, "+"+Mideas.joueur1.getNecklace().getStrengh()+" Strengh");
				TTF.statsName.drawStringShadow(Display.getWidth()/2+x, Display.getHeight()/2+y+80, "+"+Mideas.joueur1.getNecklace().getCritical()+" Critical");
			}
			Draw.drawQuad(Sprites.stuff_hover, Display.getWidth()/2+x_hoverLeft, Display.getHeight()/2-242);
		}
		if(hoverCharacterFrame3) {
			if(Mideas.joueur1.getShoulders() == null) {
				Draw.drawColorQuad(Display.getWidth()/2-460, Display.getHeight()/2-200, 150, 80, bgColor);
				TTF.itemName.drawStringShadow(Display.getWidth()/2-428, Display.getHeight()/2-190, "Shoulders");
			}
			else {
				int x = -550;
				int y = -177;
				Draw.drawColorQuad(Display.getWidth()/2+x+80-TTF.itemName.getWidth(Mideas.joueur1.getShoulders().getStuffName())/2, Display.getHeight()/2-200, 150+TTF.itemName.getWidth(Mideas.joueur1.getShoulders().getStuffName())/2, 80+TTF.statsName.getHeight()*4, bgColor);
				TTF.itemName.drawStringShadow(Display.getWidth()/2+x+85-TTF.itemName.getWidth(Mideas.joueur1.getShoulders().getStuffName())/2, Display.getHeight()/2+y-20, Mideas.joueur1.getShoulders().getStuffName());
				TTF.statsName.drawStringShadow(Display.getWidth()/2+x-42, Display.getHeight()/2+y, "+"+Mideas.joueur1.getShoulders().getArmor()+" Armor");
				TTF.statsName.drawStringShadow(Display.getWidth()/2+x-42, Display.getHeight()/2+y+20, "+"+Mideas.joueur1.getShoulders().getStamina()+" Stamina");
				TTF.statsName.drawStringShadow(Display.getWidth()/2+x-42, Display.getHeight()/2+y+40, "+"+Mideas.joueur1.getShoulders().getMana()+" Mana");
				TTF.statsName.drawStringShadow(Display.getWidth()/2+x-42, Display.getHeight()/2+y+60, "+"+Mideas.joueur1.getShoulders().getStrengh()+" Strengh");
				TTF.statsName.drawStringShadow(Display.getWidth()/2+x-42, Display.getHeight()/2+y+80, "+"+Mideas.joueur1.getShoulders().getCritical()+" Critical");
			}
			Draw.drawQuad(Sprites.stuff_hover, Display.getWidth()/2+x_hoverLeft, Display.getHeight()/2-194);
		}
		if(hoverCharacterFrame4) {
			if(Mideas.joueur1.getBack() == null) {
				Draw.drawColorQuad(Display.getWidth()/2-460, Display.getHeight()/2-150, 150, 80, bgColor);
				TTF.itemName.drawStringShadow(Display.getWidth()/2-410, Display.getHeight()/2-140, "Back");
			}
			else {
				int x = -540;
				int y = -127;
				Draw.drawColorQuad(Display.getWidth()/2+x+80-TTF.itemName.getWidth(Mideas.joueur1.getBack().getStuffName())/2, Display.getHeight()/2-150, 150+TTF.itemName.getWidth(Mideas.joueur1.getBack().getStuffName())/2, 80+TTF.statsName.getHeight()*4, bgColor);
				TTF.itemName.drawStringShadow(Display.getWidth()/2+x+85-TTF.itemName.getWidth(Mideas.joueur1.getBack().getStuffName())/2, Display.getHeight()/2+y-20, Mideas.joueur1.getBack().getStuffName());
				TTF.statsName.drawStringShadow(Display.getWidth()/2+x+10, Display.getHeight()/2+y, "+"+Mideas.joueur1.getBack().getArmor()+" Armor");
				TTF.statsName.drawStringShadow(Display.getWidth()/2+x+10, Display.getHeight()/2+y+20, "+"+Mideas.joueur1.getBack().getStamina()+" Stamina");
				TTF.statsName.drawStringShadow(Display.getWidth()/2+x+10, Display.getHeight()/2+y+40, "+"+Mideas.joueur1.getBack().getMana()+" Mana");
				TTF.statsName.drawStringShadow(Display.getWidth()/2+x+10, Display.getHeight()/2+y+60, "+"+Mideas.joueur1.getBack().getStrengh()+" Strengh");
				TTF.statsName.drawStringShadow(Display.getWidth()/2+x+10, Display.getHeight()/2+y+80, "+"+Mideas.joueur1.getBack().getCritical()+" Critical");
			}
			Draw.drawQuad(Sprites.stuff_hover, Display.getWidth()/2+x_hoverLeft, Display.getHeight()/2-144);
		}
		else if(hoverCharacterFrame5) {
			if(Mideas.joueur1.getChest() == null) {
				Draw.drawColorQuad(Display.getWidth()/2-460, Display.getHeight()/2-100, 150, 80, bgColor);
				TTF.itemName.drawStringShadow(Display.getWidth()/2-415, Display.getHeight()/2-90, "Chest");
			}
			else {
				int x = -540;
				int y = -78;
				Draw.drawColorQuad(Display.getWidth()/2+x+80-TTF.itemName.getWidth(Mideas.joueur1.getChest().getStuffName())/2, Display.getHeight()/2-100, 150+TTF.itemName.getWidth(Mideas.joueur1.getChest().getStuffName())/2, 80+TTF.statsName.getHeight()*4, bgColor);
				TTF.itemName.drawStringShadow(Display.getWidth()/2+x+85-TTF.itemName.getWidth(Mideas.joueur1.getChest().getStuffName())/2, Display.getHeight()/2+y-20, Mideas.joueur1.getChest().getStuffName());
				TTF.statsName.drawStringShadow(Display.getWidth()/2+x-25, Display.getHeight()/2+y, "+"+Mideas.joueur1.getChest().getArmor()+" Armor");
				TTF.statsName.drawStringShadow(Display.getWidth()/2+x-25, Display.getHeight()/2+y+20, "+"+Mideas.joueur1.getChest().getStamina()+" Stamina");
				TTF.statsName.drawStringShadow(Display.getWidth()/2+x-25, Display.getHeight()/2+y+40, "+"+Mideas.joueur1.getChest().getMana()+" Mana");
				TTF.statsName.drawStringShadow(Display.getWidth()/2+x-25, Display.getHeight()/2+y+60, "+"+Mideas.joueur1.getChest().getStrengh()+" Strengh");
				TTF.statsName.drawStringShadow(Display.getWidth()/2+x-25, Display.getHeight()/2+y+80, "+"+Mideas.joueur1.getChest().getCritical()+" Critical");
			}
			Draw.drawQuad(Sprites.stuff_hover, Display.getWidth()/2+x_hoverLeft, Display.getHeight()/2-93);
		}*/
		if(hoverCharacterFrame[5]) {
			Draw.drawQuad(Sprites.stuff_hover, Display.getWidth()/2+x_hoverLeft, Display.getHeight()/2-45);
		}
		else if(hoverCharacterFrame[6]) {
			Draw.drawQuad(Sprites.stuff_hover, Display.getWidth()/2+x_hoverLeft, Display.getHeight()/2+5);
		}
		/*if(hoverCharacterFrame8) {
			if(Mideas.joueur1.getWrists() == null) {
				Draw.drawColorQuad(Display.getWidth()/2-460, Display.getHeight()/2+30, 150, 80, bgColor);
				TTF.itemName.drawStringShadow(Display.getWidth()/2-417, Display.getHeight()/2+40, "Wrists");
			}
			else {
				int x = -540;
				int y = -50;
				Draw.drawColorQuad(Display.getWidth()/2+x+80-TTF.itemName.getWidth(Mideas.joueur1.getWrists().getStuffName())/2, Display.getHeight()/2+y+80-TTF.statsName.getHeight()*4, 150+TTF.itemName.getWidth(Mideas.joueur1.getWrists().getStuffName())/2, 80+TTF.statsName.getHeight()*4, bgColor);
				TTF.itemName.drawStringShadow(Display.getWidth()/2+x+85-TTF.itemName.getWidth(Mideas.joueur1.getWrists().getStuffName())/2, Display.getHeight()/2+y+8, Mideas.joueur1.getWrists().getStuffName());
				TTF.statsName.drawStringShadow(Display.getWidth()/2+x-22, Display.getHeight()/2+y+33, "+"+Mideas.joueur1.getWrists().getArmor()+" Armor");
				TTF.statsName.drawStringShadow(Display.getWidth()/2+x-22, Display.getHeight()/2+y+53, "+"+Mideas.joueur1.getWrists().getStamina()+" Stamina");
				TTF.statsName.drawStringShadow(Display.getWidth()/2+x-22, Display.getHeight()/2+y+73, "+"+Mideas.joueur1.getWrists().getMana()+" Mana");
				TTF.statsName.drawStringShadow(Display.getWidth()/2+x-22, Display.getHeight()/2+y+93, "+"+Mideas.joueur1.getWrists().getStrengh()+" Strengh");
				TTF.statsName.drawStringShadow(Display.getWidth()/2+x-22, Display.getHeight()/2+y+113, "+"+Mideas.joueur1.getWrists().getCritical()+" Critical");
			}
			Draw.drawQuad(Sprites.stuff_hover, Display.getWidth()/2+x_hoverLeft, Display.getHeight()/2+56);
		}
		if(hoverCharacterFrame9) {
			if(Mideas.joueur1.getGloves() == null) {
				Draw.drawColorQuad(Display.getWidth()/2+160, Display.getHeight()/2-300, 150, 80, bgColor);
				TTF.itemName.drawStringShadow(Display.getWidth()/2+203, Display.getHeight()/2-290, "Gloves");
			}
			else {
				int x = 172;
				int y = -300;
				Draw.drawColorQuad(Display.getWidth()/2+50+TTF.itemName.getWidth(Mideas.joueur1.getGloves().getStuffName())/2, Display.getHeight()/2-290, 150+TTF.itemName.getWidth(Mideas.joueur1.getGloves().getStuffName())/2, 80+TTF.statsName.getHeight()*4, bgColor);
				TTF.itemName.drawStringShadow(Display.getWidth()/2+x-115+TTF.itemName.getWidth(Mideas.joueur1.getGloves().getStuffName())/2, Display.getHeight()/2+y+11, Mideas.joueur1.getGloves().getStuffName());
				TTF.statsName.drawStringShadow(Display.getWidth()/2+x, Display.getHeight()/2+y+33, "+"+Mideas.joueur1.getGloves().getArmor()+" Armor");
				TTF.statsName.drawStringShadow(Display.getWidth()/2+x, Display.getHeight()/2+y+53, "+"+Mideas.joueur1.getGloves().getStamina()+" Stamina");
				TTF.statsName.drawStringShadow(Display.getWidth()/2+x, Display.getHeight()/2+y+73, "+"+Mideas.joueur1.getGloves().getMana()+" Mana");
				TTF.statsName.drawStringShadow(Display.getWidth()/2+x, Display.getHeight()/2+y+93, "+"+Mideas.joueur1.getGloves().getStrengh()+" Strengh");
				TTF.statsName.drawStringShadow(Display.getWidth()/2+x, Display.getHeight()/2+y+113, "+"+Mideas.joueur1.getGloves().getCritical()+" Critical");
			}
			Draw.drawQuad(Sprites.stuff_hover, Display.getWidth()/2+x_hoverRight, Display.getHeight()/2-291);
		}
		if(hoverCharacterFrame10) {
			if(Mideas.joueur1.getBelt() == null) {
				Draw.drawColorQuad(Display.getWidth()/2+160, Display.getHeight()/2-250, 150, 80, bgColor);
				TTF.itemName.drawStringShadow(Display.getWidth()/2+212, Display.getHeight()/2-240, "Belt");
			}
			else {
				int x = 170;
				int y = -250;
				Draw.drawColorQuad(Display.getWidth()/2+50+TTF.itemName.getWidth(Mideas.joueur1.getBelt().getStuffName())/2, Display.getHeight()/2-240, 150+TTF.itemName.getWidth(Mideas.joueur1.getBelt().getStuffName())/2, 80+TTF.statsName.getHeight()*4, bgColor);
				TTF.itemName.drawStringShadow(Display.getWidth()/2+x-115+TTF.itemName.getWidth(Mideas.joueur1.getBelt().getStuffName())/2, Display.getHeight()/2+y+11, Mideas.joueur1.getBelt().getStuffName());
				TTF.statsName.drawStringShadow(Display.getWidth()/2+x, Display.getHeight()/2+y+33, "+"+Mideas.joueur1.getBelt().getArmor()+" Armor");
				TTF.statsName.drawStringShadow(Display.getWidth()/2+x, Display.getHeight()/2+y+53, "+"+Mideas.joueur1.getBelt().getStamina()+" Stamina");
				TTF.statsName.drawStringShadow(Display.getWidth()/2+x, Display.getHeight()/2+y+73, "+"+Mideas.joueur1.getBelt().getMana()+" Mana");
				TTF.statsName.drawStringShadow(Display.getWidth()/2+x, Display.getHeight()/2+y+93, "+"+Mideas.joueur1.getBelt().getStrengh()+" Strengh");
				TTF.statsName.drawStringShadow(Display.getWidth()/2+x, Display.getHeight()/2+y+113, "+"+Mideas.joueur1.getBelt().getCritical()+" Critical");
			}
			Draw.drawQuad(Sprites.stuff_hover, Display.getWidth()/2+x_hoverRight, Display.getHeight()/2-243);
		}
		if(hoverCharacterFrame11) {
			if(Mideas.joueur1.getLeggings() == null) {
				Draw.drawColorQuad(Display.getWidth()/2+160, Display.getHeight()/2-200, 150, 80, bgColor);
				TTF.itemName.drawStringShadow(Display.getWidth()/2+193, Display.getHeight()/2-190, "Leggings");
			}
			else {
				int x = 163;
				int y = -200;
				Draw.drawColorQuad(Display.getWidth()/2+50+TTF.itemName.getWidth(Mideas.joueur1.getLeggings().getStuffName())/2, Display.getHeight()/2-190, 150+TTF.itemName.getWidth(Mideas.joueur1.getLeggings().getStuffName())/2, 80+TTF.statsName.getHeight()*4, bgColor);
				TTF.itemName.drawStringShadow(Display.getWidth()/2+x-110+TTF.itemName.getWidth(Mideas.joueur1.getLeggings().getStuffName())/2, Display.getHeight()/2+y+11, Mideas.joueur1.getLeggings().getStuffName());
				TTF.statsName.drawStringShadow(Display.getWidth()/2+x, Display.getHeight()/2+y+33, "+"+Mideas.joueur1.getLeggings().getArmor()+" Armor");
				TTF.statsName.drawStringShadow(Display.getWidth()/2+x, Display.getHeight()/2+y+53, "+"+Mideas.joueur1.getLeggings().getStamina()+" Stamina");
				TTF.statsName.drawStringShadow(Display.getWidth()/2+x, Display.getHeight()/2+y+73, "+"+Mideas.joueur1.getLeggings().getMana()+" Mana");
				TTF.statsName.drawStringShadow(Display.getWidth()/2+x, Display.getHeight()/2+y+93, "+"+Mideas.joueur1.getLeggings().getStrengh()+" Strengh");
				TTF.statsName.drawStringShadow(Display.getWidth()/2+x, Display.getHeight()/2+y+113, "+"+Mideas.joueur1.getLeggings().getCritical()+" Critical");
			}
			Draw.drawQuad(Sprites.stuff_hover, Display.getWidth()/2+x_hoverRight, Display.getHeight()/2-195);
		}
		if(hoverCharacterFrame12) {
			if(Mideas.joueur1.getBoots() == null) {
				Draw.drawColorQuad(Display.getWidth()/2+160, Display.getHeight()/2-150, 150, 80, bgColor);
				TTF.itemName.drawStringShadow(Display.getWidth()/2+203, Display.getHeight()/2-140, "Boots");
			}
			else {
				int x = 170;
				int y = -158;
				Draw.drawColorQuad(Display.getWidth()/2+50+TTF.itemName.getWidth(Mideas.joueur1.getBoots().getStuffName())/2, Display.getHeight()/2-150, 150+TTF.itemName.getWidth(Mideas.joueur1.getBoots().getStuffName())/2, 80+TTF.statsName.getHeight()*4, bgColor);
				TTF.itemName.drawStringShadow(Display.getWidth()/2+x-117+TTF.itemName.getWidth(Mideas.joueur1.getBoots().getStuffName())/2, Display.getHeight()/2+y+11, Mideas.joueur1.getBoots().getStuffName());
				TTF.statsName.drawStringShadow(Display.getWidth()/2+x, Display.getHeight()/2+y+33, "+"+Mideas.joueur1.getBoots().getArmor()+" Armor");
				TTF.statsName.drawStringShadow(Display.getWidth()/2+x, Display.getHeight()/2+y+53, "+"+Mideas.joueur1.getBoots().getStamina()+" Stamina");
				TTF.statsName.drawStringShadow(Display.getWidth()/2+x, Display.getHeight()/2+y+73, "+"+Mideas.joueur1.getBoots().getMana()+" Mana");
				TTF.statsName.drawStringShadow(Display.getWidth()/2+x, Display.getHeight()/2+y+93, "+"+Mideas.joueur1.getBoots().getStrengh()+" Strengh");
				TTF.statsName.drawStringShadow(Display.getWidth()/2+x, Display.getHeight()/2+y+113, "+"+Mideas.joueur1.getBoots().getCritical()+" Critical");
			}
			Draw.drawQuad(Sprites.stuff_hover, Display.getWidth()/2+x_hoverRight, Display.getHeight()/2-145);
		}
		if(hoverCharacterFrame13) {
			if(Mideas.joueur1.getRing() == null) {
				Draw.drawColorQuad(Display.getWidth()/2+160, Display.getHeight()/2-100, 150, 80, bgColor);
				TTF.itemName.drawStringShadow(Display.getWidth()/2+203, Display.getHeight()/2-90, "Ring");
			}
			else {
				int x = 168;
				int y = -110;
				Draw.drawColorQuad(Display.getWidth()/2+50+TTF.itemName.getWidth(Mideas.joueur1.getRing().getStuffName())/2, Display.getHeight()/2-100, 150+TTF.itemName.getWidth(Mideas.joueur1.getRing().getStuffName())/2, 80+TTF.statsName.getHeight()*4, bgColor);
				TTF.itemName.drawStringShadow(Display.getWidth()/2+x-115+TTF.itemName.getWidth(Mideas.joueur1.getRing().getStuffName())/2, Display.getHeight()/2+y+11, Mideas.joueur1.getRing().getStuffName());
				TTF.statsName.drawStringShadow(Display.getWidth()/2+x, Display.getHeight()/2+y+33, "+"+Mideas.joueur1.getRing().getArmor()+" Armor");
				TTF.statsName.drawStringShadow(Display.getWidth()/2+x, Display.getHeight()/2+y+53, "+"+Mideas.joueur1.getRing().getStamina()+" Stamina");
				TTF.statsName.drawStringShadow(Display.getWidth()/2+x, Display.getHeight()/2+y+73, "+"+Mideas.joueur1.getRing().getMana()+" Mana");
				TTF.statsName.drawStringShadow(Display.getWidth()/2+x, Display.getHeight()/2+y+93, "+"+Mideas.joueur1.getRing().getStrengh()+" Strengh");
				TTF.statsName.drawStringShadow(Display.getWidth()/2+x, Display.getHeight()/2+y+113, "+"+Mideas.joueur1.getRing().getCritical()+" Critical");
			}
			Draw.drawQuad(Sprites.stuff_hover, Display.getWidth()/2+x_hoverRight, Display.getHeight()/2-94);
		}
		if(hoverCharacterFrame14) {
			if(Mideas.joueur1.getRing2() == null) {
				Draw.drawColorQuad(Display.getWidth()/2+160, Display.getHeight()/2-50, 150, 80, bgColor);
				TTF.itemName.drawStringShadow(Display.getWidth()/2+203, Display.getHeight()/2-40, "Ring2");
			}
			else {
				int x = 168;
				int y = -60;
				Draw.drawColorQuad(Display.getWidth()/2+50+TTF.itemName.getWidth(Mideas.joueur1.getRing2().getStuffName())/2, Display.getHeight()/2-50, 150+TTF.itemName.getWidth(Mideas.joueur1.getRing2().getStuffName())/2, 80+TTF.statsName.getHeight()*4, bgColor);
				TTF.itemName.drawStringShadow(Display.getWidth()/2+x-115+TTF.itemName.getWidth(Mideas.joueur1.getRing2().getStuffName())/2, Display.getHeight()/2+y+11, Mideas.joueur1.getRing2().getStuffName());
				TTF.statsName.drawStringShadow(Display.getWidth()/2+x, Display.getHeight()/2+y+33, "+"+Mideas.joueur1.getRing2().getArmor()+" Armor");
				TTF.statsName.drawStringShadow(Display.getWidth()/2+x, Display.getHeight()/2+y+53, "+"+Mideas.joueur1.getRing2().getStamina()+" Stamina");
				TTF.statsName.drawStringShadow(Display.getWidth()/2+x, Display.getHeight()/2+y+73, "+"+Mideas.joueur1.getRing2().getMana()+" Mana");
				TTF.statsName.drawStringShadow(Display.getWidth()/2+x, Display.getHeight()/2+y+93, "+"+Mideas.joueur1.getRing2().getStrengh()+" Strengh");
				TTF.statsName.drawStringShadow(Display.getWidth()/2+x, Display.getHeight()/2+y+113, "+"+Mideas.joueur1.getRing2().getCritical()+" Critical");
			}
			Draw.drawQuad(Sprites.stuff_hover, Display.getWidth()/2+x_hoverRight, Display.getHeight()/2-46);
		}
		if(hoverCharacterFrame15) {
			if(Mideas.joueur1.getTrinket() == null) {
				Draw.drawColorQuad(Display.getWidth()/2+160, Display.getHeight()/2, 150, 80, bgColor);
				TTF.itemName.drawStringShadow(Display.getWidth()/2+202, Display.getHeight()/2+10, "Trinket");
			}
			else {
				int x = 168;
				int y = -10;
				Draw.drawColorQuad(Display.getWidth()/2+50+TTF.itemName.getWidth(Mideas.joueur1.getTrinket().getStuffName())/2, Display.getHeight()/2, 150+TTF.itemName.getWidth(Mideas.joueur1.getTrinket().getStuffName())/2, 80+TTF.statsName.getHeight()*4, bgColor);
				TTF.itemName.drawStringShadow(Display.getWidth()/2+x-115+TTF.itemName.getWidth(Mideas.joueur1.getTrinket().getStuffName())/2, Display.getHeight()/2+y+11, Mideas.joueur1.getTrinket().getStuffName());
				TTF.statsName.drawStringShadow(Display.getWidth()/2+x, Display.getHeight()/2+y+33, "+"+Mideas.joueur1.getTrinket().getArmor()+" Armor");
				TTF.statsName.drawStringShadow(Display.getWidth()/2+x, Display.getHeight()/2+y+53, "+"+Mideas.joueur1.getTrinket().getStamina()+" Stamina");
				TTF.statsName.drawStringShadow(Display.getWidth()/2+x, Display.getHeight()/2+y+73, "+"+Mideas.joueur1.getTrinket().getMana()+" Mana");
				TTF.statsName.drawStringShadow(Display.getWidth()/2+x, Display.getHeight()/2+y+93, "+"+Mideas.joueur1.getTrinket().getStrengh()+" Strengh");
				TTF.statsName.drawStringShadow(Display.getWidth()/2+x, Display.getHeight()/2+y+113, "+"+Mideas.joueur1.getTrinket().getCritical()+" Critical");
			}
			Draw.drawQuad(Sprites.stuff_hover, Display.getWidth()/2+x_hoverRight, Display.getHeight()/2+4);
		}
		if(hoverCharacterFrame16) {
			if(Mideas.joueur1.getTrinket2() == null) {
				Draw.drawColorQuad(Display.getWidth()/2+160, Display.getHeight()/2+50, 150, 80, bgColor);
				TTF.itemName.drawStringShadow(Display.getWidth()/2+200, Display.getHeight()/2+60, "Trinket2");
			}
			else {
				int x = 168;
				int y = 40;
				Draw.drawColorQuad(Display.getWidth()/2+50+TTF.itemName.getWidth(Mideas.joueur1.getTrinket2().getStuffName())/2, Display.getHeight()/2+50, 150+TTF.itemName.getWidth(Mideas.joueur1.getTrinket2().getStuffName())/2, 80+TTF.statsName.getHeight()*4, bgColor);
				TTF.itemName.drawStringShadow(Display.getWidth()/2+x-115+TTF.itemName.getWidth(Mideas.joueur1.getTrinket2().getStuffName())/2, Display.getHeight()/2+y+11, Mideas.joueur1.getTrinket2().getStuffName());
				TTF.statsName.drawStringShadow(Display.getWidth()/2+x, Display.getHeight()/2+y+33, "+"+Mideas.joueur1.getTrinket2().getArmor()+" Armor");
				TTF.statsName.drawStringShadow(Display.getWidth()/2+x, Display.getHeight()/2+y+53, "+"+Mideas.joueur1.getTrinket2().getStamina()+" Stamina");
				TTF.statsName.drawStringShadow(Display.getWidth()/2+x, Display.getHeight()/2+y+73, "+"+Mideas.joueur1.getTrinket2().getMana()+" Mana");
				TTF.statsName.drawStringShadow(Display.getWidth()/2+x, Display.getHeight()/2+y+93, "+"+Mideas.joueur1.getTrinket2().getStrengh()+" Strengh");
				TTF.statsName.drawStringShadow(Display.getWidth()/2+x, Display.getHeight()/2+y+113, "+"+Mideas.joueur1.getTrinket2().getCritical()+" Critical");
			}
			Draw.drawQuad(Sprites.stuff_hover, Display.getWidth()/2+x_hoverRight, Display.getHeight()/2+55);
		}*/
		if(hoverCharacterFrame[16]) {
			if(Mideas.joueur1().getStuff(16) == null) {
				Draw.drawColorQuad(Display.getWidth()/2-100, Display.getHeight()/2+150, 150, 80, bgColor);
				TTF2.itemName.drawStringShadow(Display.getWidth()/2-73, Display.getHeight()/2+160, "MainHand", Color.white, Color.black, 1, 1, 1);
			}
			else {
				int x = -95;
				int y = 142;
				Draw.drawColorQuad(Display.getWidth()/2-215+TTF2.itemName.getWidth(Mideas.joueur1().getStuff(16).getStuffName())/2, Display.getHeight()/2+150, 150+TTF2.itemName.getWidth(Mideas.joueur1().getStuff(16).getStuffName())/2, 80+TTF2.statsName.getLineHeight()*4, bgColor);
				TTF2.itemName.drawStringShadow(Display.getWidth()/2+x-115+TTF2.itemName.getWidth(Mideas.joueur1().getStuff(16).getStuffName())/2, Display.getHeight()/2+y+11, Mideas.joueur1().getStuff(16).getStuffName(), Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()/2+x, Display.getHeight()/2+y+33, "+"+Mideas.joueur1().getStuff(16).getArmor()+" Armor", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()/2+x, Display.getHeight()/2+y+53, "+"+Mideas.joueur1().getStuff(16).getStamina()+" Stamina", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()/2+x, Display.getHeight()/2+y+73, "+"+Mideas.joueur1().getStuff(16).getMana()+" Mana", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()/2+x, Display.getHeight()/2+y+93, "+"+Mideas.joueur1().getStuff(16).getStrength()+" Strengh", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()/2+x, Display.getHeight()/2+y+113, "+"+Mideas.joueur1().getStuff(16).getCritical()+" Critical", Color.white, Color.black, 1, 1, 1);
			}
			Draw.drawQuad(Sprites.stuff_hover, Display.getWidth()/2-157, Display.getHeight()/2+82);
		}
		if(hoverCharacterFrame[17]) {
			if(Mideas.joueur1().getStuff(17) == null) {
				Draw.drawColorQuad(Display.getWidth()/2-70, Display.getHeight()/2+150, 150, 80, bgColor);
				TTF2.itemName.drawStringShadow(Display.getWidth()/2-33, Display.getHeight()/2+160, "OffHand", Color.white, Color.black, 1, 1, 1);
			}
			else {
				int x = -65;
				int y = 142;
				Draw.drawColorQuad(Display.getWidth()/2-185+TTF2.itemName.getWidth(Mideas.joueur1().getStuff(17).getStuffName())/2, Display.getHeight()/2+150, 150+TTF2.itemName.getWidth(Mideas.joueur1().getStuff(17).getStuffName())/2, 80+TTF2.statsName.getLineHeight()*4, bgColor);
				TTF2.itemName.drawStringShadow(Display.getWidth()/2+x-115+TTF2.itemName.getWidth(Mideas.joueur1().getStuff(17).getStuffName())/2, Display.getHeight()/2+y+11, Mideas.joueur1().getStuff(17).getStuffName(), Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()/2+x, Display.getHeight()/2+y+33, "+"+Mideas.joueur1().getStuff(17).getArmor()+" Armor", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()/2+x, Display.getHeight()/2+y+53, "+"+Mideas.joueur1().getStuff(17).getStamina()+" Stamina", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()/2+x, Display.getHeight()/2+y+73, "+"+Mideas.joueur1().getStuff(17).getMana()+" Mana", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()/2+x, Display.getHeight()/2+y+93, "+"+Mideas.joueur1().getStuff(17).getStrength()+" Strengh", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()/2+x, Display.getHeight()/2+y+113, "+"+Mideas.joueur1().getStuff(17).getCritical()+" Critical", Color.white, Color.black, 1, 1, 1);
			}
			Draw.drawQuad(Sprites.stuff_hover, Display.getWidth()/2-105, Display.getHeight()/2+82);
		}
		if(hoverCharacterFrame[18]) {
			if(Mideas.joueur1().getStuff(18) == null) {
				Draw.drawColorQuad(Display.getWidth()/2-40, Display.getHeight()/2+150, 150, 80, bgColor);
				TTF2.itemName.drawStringShadow(Display.getWidth()/2+03, Display.getHeight()/2+160, "Ranged", Color.white, Color.black, 1, 1, 1);
			}
			else {
				int x = -35;
				int y = 142;
				Draw.drawColorQuad(Display.getWidth()/2-185+TTF2.itemName.getWidth(Mideas.joueur1().getStuff(18).getStuffName())/2, Display.getHeight()/2+150, 150+TTF2.itemName.getWidth(Mideas.joueur1().getStuff(18).getStuffName())/2, 80+TTF2.statsName.getLineHeight()*4, bgColor);
				TTF2.itemName.drawStringShadow(Display.getWidth()/2+x-115+TTF2.itemName.getWidth(Mideas.joueur1().getStuff(18).getStuffName())/2, Display.getHeight()/2+y+11, Mideas.joueur1().getStuff(18).getStuffName(), Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()/2+x, Display.getHeight()/2+y+33, "+"+Mideas.joueur1().getStuff(18).getArmor()+" Armor", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()/2+x, Display.getHeight()/2+y+53, "+"+Mideas.joueur1().getStuff(18).getStamina()+" Stamina", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()/2+x, Display.getHeight()/2+y+73, "+"+Mideas.joueur1().getStuff(18).getMana()+" Mana", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()/2+x, Display.getHeight()/2+y+93, "+"+Mideas.joueur1().getStuff(18).getStrength()+" Strengh", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()/2+x, Display.getHeight()/2+y+113, "+"+Mideas.joueur1().getStuff(18).getCritical()+" Critical", Color.white, Color.black, 1, 1, 1);
			}
			Draw.drawQuad(Sprites.stuff_hover, Display.getWidth()/2-52, Display.getHeight()/2+82);
		}
	}

	public static boolean mouseEvent() {
		int xLeft = -283;
		int xRight = 78;
		int y = -290;
		int yShift = 49;
		Arrays.fill(hoverCharacterFrame, false);
		if(Mideas.mouseX() >= Display.getWidth()/2+112 && Mideas.mouseX() <= Display.getWidth()/2+132 && Mideas.mouseY() >= Display.getHeight()/2-360 && Mideas.mouseY() <= Display.getHeight()/2-342) {
			hoverCloseButton = true;
			if(Mouse.getEventButtonState()) {
				Interface.closeCharacterFrame();
				return true;
			}
		}
		if(Mideas.mouseX() >= Display.getWidth()/2+112 && Mideas.mouseX() <= Display.getWidth()/2+132 && Mideas.mouseY() >= Display.getHeight()/2-360 && Mideas.mouseY() <= Display.getHeight()/2-342) {
			hoverCloseButton = true;
		}
		isHover(xLeft, y, 0);
		isHover(xLeft, y+yShift, 1);
		isHover(xLeft, y+2*yShift, 2);
		isHover(xLeft, y+3*yShift, 3);
		isHover(xLeft, y+4*yShift, 4);
		isHover(xLeft, y+5*yShift, 5);
		isHover(xLeft, y+6*yShift, 6);
		isHover(xLeft, y+7*yShift, 7);
		isHover(xRight, y, 8);
		isHover(xRight, y+1*yShift, 9);
		isHover(xRight, y+2*yShift, 10);
		isHover(xRight, y+3*yShift, 11);
		isHover(xRight, y+4*yShift, 12);
		isHover(xRight, y+5*yShift, 13);
		isHover(xRight, y+6*yShift, 14);
		isHover(xRight, y+7*yShift, 15);
		isHover(xLeft+126, y+372, 16);
		isHover(xLeft+180, y+372, 17);
		isHover(xLeft+233, y+372, 18);
		return false;
	}
	
	public static void characterLeftItemsHover(boolean hoverCharacter, Stuff stuff, int x_stuff_hover, int x_item, int y_item, int move_item_name, int x_bg, int y_bg, int y_stuff_hover, int x_item_name, int y_item_name, String base_name) {
		if(hoverCharacter) {
			if(stuff == null) {
				Draw.drawColorQuad(Display.getWidth()/2+x_bg, Display.getHeight()/2+y_bg, 150, 80, bgColor);
				TTF2.itemName.drawStringShadow(Display.getWidth()/2+x_item_name, Display.getHeight()/2+y_item_name, base_name, Color.white, Color.black, 1, 1, 1);
			}
			else {
				Draw.drawColorQuad(Display.getWidth()/2+x_bg-TTF2.itemName.getWidth(stuff.getStuffName())/2, Display.getHeight()/2+y_bg, 150+TTF2.itemName.getWidth(stuff.getStuffName())/2, 80+TTF2.statsName.getLineHeight()*4, bgColor);
				TTF2.itemName.drawStringShadow(Display.getWidth()/2+x_item+move_item_name-TTF2.itemName.getWidth(stuff.getStuffName())/2, Display.getHeight()/2+y_item-20, stuff.getStuffName(), Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()/2+x_item, Display.getHeight()/2+y_item, "+"+stuff.getArmor()+" Armor", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()/2+x_item, Display.getHeight()/2+y_item+20, "+"+stuff.getStamina()+" Stamina", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()/2+x_item, Display.getHeight()/2+y_item+40, "+"+stuff.getMana()+" Mana", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()/2+x_item, Display.getHeight()/2+y_item+60, "+"+stuff.getStrength()+" Strengh", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()/2+x_item, Display.getHeight()/2+y_item+80, "+"+stuff.getCritical()+" Critical", Color.white, Color.black, 1, 1, 1);		
			}
			Draw.drawQuad(Sprites.stuff_hover, Display.getWidth()/2+x_stuff_hover, Display.getHeight()/2+y_stuff_hover);
		}
	}
	public static void characterRightItemsHover(boolean hoverCharacter, Stuff stuff, int x_stuff_hover, int x_item, int y_item, int move_item_name, int x_bg, int y_bg, int y_stuff_hover, int x_item_name, int y_item_name, String base_name) {
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
	}
	
	public static void drawCharacterItems(Stuff stuff, int x, int y) {
		if(stuff != null && !(stuff == DragManager.getDraggedItem())) {
			Draw.drawQuad(IconsManager.getSprite42((stuff.getSpriteId())), Display.getWidth()/2+x, Display.getHeight()/2+y);
			Draw.drawQuad(Sprites.stuff_border, Display.getWidth()/2+x-5, Display.getHeight()/2+y-5);
		}
	}
	
	private static void isHover(int x, int y, int i) {
		if(Mideas.mouseX() >= Display.getWidth()/2+x && Mideas.mouseX() <= Display.getWidth()/2+x+48 && Mideas.mouseY() >= Display.getHeight()/2+y && Mideas.mouseY() <= Display.getHeight()/2+y+48) {
			hoverCharacterFrame[i] = true;
		}
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
