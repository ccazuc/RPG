package com.mideas.rpg.v2.hud;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;
import java.util.Arrays;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;

import com.mideas.rpg.v2.Interface;
import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.Sprites;
import com.mideas.rpg.v2.TTF2;
import com.mideas.rpg.v2.game.CharacterStuff;
import com.mideas.rpg.v2.game.IconsManager;
import com.mideas.rpg.v2.game.item.Item;
import com.mideas.rpg.v2.game.item.ItemType;
import com.mideas.rpg.v2.game.item.stuff.Stuff;
import com.mideas.rpg.v2.utils.Draw;

public class ContainerFrame {
	
	private static boolean[] slot_hover = new boolean[33];
	private static Color bgColor = new Color(0, 0, 0,.8f); 
	private static int x;
	private static int xShift;
	private static int y;
	private static int yShift;
	private static int ySecondBag = 251;
	
	public static void draw() throws LWJGLException, IOException {
		Draw.drawQuad(Sprites.back_bag, Display.getWidth()-220, Display.getHeight()/2+150);
		Draw.drawQuad(Sprites.back_bag2, Display.getWidth()-220, Display.getHeight()/2-100);
		x = -202;
		xShift = 41;
		y = -51;
		yShift = 42;
		ySecondBag = 251;
		drawBag(0, x, y);
		drawBag(1, x+xShift, y);
		drawBag(2, x+2*xShift, y);
		drawBag(3, x+3*xShift, y);
		drawBag(4, x, y+yShift);
		drawBag(5, x+xShift, y+yShift);
		drawBag(6, x+2*xShift, y+yShift);
		drawBag(7, x+3*xShift, y+yShift);
		drawBag(8, x, y+2*yShift);
		drawBag(9, x+xShift, y+2*yShift);
		drawBag(10, x+2*xShift, y+2*yShift);
		drawBag(11, x+3*xShift, y+2*yShift);
		drawBag(12, x, y+3*yShift);
		drawBag(13, x+xShift, y+3*yShift);
		drawBag(14, x+2*xShift, y+3*yShift);
		drawBag(15, x+3*xShift, y+3*yShift);
		drawBag(16, x, y+ySecondBag);
		drawBag(17, x+xShift, y+ySecondBag);
		drawBag(18, x+2*xShift, y+ySecondBag);
		drawBag(19, x+3*xShift, y+ySecondBag);
		drawBag(20, x, y+yShift+ySecondBag);
		drawBag(21, x+xShift, y+yShift+ySecondBag);
		drawBag(22, x+2*xShift, y+yShift+ySecondBag);
		drawBag(23, x+3*xShift, y+yShift+ySecondBag);
		drawBag(24, x, y+2*yShift+ySecondBag);
		drawBag(25, x+xShift, y+2*yShift+ySecondBag);
		drawBag(26, x+2*xShift, y+2*yShift+ySecondBag);
		drawBag(27, x+3*xShift, y+2*yShift+ySecondBag);
		drawBag(28, x, y+3*yShift+ySecondBag);
		drawBag(29, x+xShift, y+3*yShift+ySecondBag);
		drawBag(30, x+2*xShift, y+3*yShift+ySecondBag);
		drawBag(31, x+3*xShift, y+3*yShift+ySecondBag);
		x = -205;
		yShift = 42;
		y = -53;
		drawBorder(Mideas.bag().getBag(0), Sprites.bag_border1, x, y);
		drawBorder(Mideas.bag().getBag(1), Sprites.bag_border2, x+xShift, y);
		drawBorder(Mideas.bag().getBag(2), Sprites.bag_border3, x+2*xShift, y);
		drawBorder(Mideas.bag().getBag(3), Sprites.bag_border4, x+3*xShift, y);
		drawBorder(Mideas.bag().getBag(4), Sprites.bag_border5, x, y+yShift);
		drawBorder(Mideas.bag().getBag(5), Sprites.bag_border6, x+xShift, y+yShift);
		drawBorder(Mideas.bag().getBag(6), Sprites.bag_border7, x+2*xShift, y+yShift);
		drawBorder(Mideas.bag().getBag(7), Sprites.bag_border8, x+3*xShift, y+yShift);
		drawBorder(Mideas.bag().getBag(8), Sprites.bag_border9, x, y+2*yShift);
		drawBorder(Mideas.bag().getBag(9), Sprites.bag_border10, x+xShift, y+2*yShift);
		drawBorder(Mideas.bag().getBag(10), Sprites.bag_border11, x+2*xShift, y+2*yShift);
		drawBorder(Mideas.bag().getBag(11), Sprites.bag_border12, x+3*xShift, y+2*yShift);
		drawBorder(Mideas.bag().getBag(12), Sprites.bag_border13, x, y+3*yShift);
		drawBorder(Mideas.bag().getBag(13), Sprites.bag_border14, x+xShift, y+3*yShift);
		drawBorder(Mideas.bag().getBag(14), Sprites.bag_border15, x+2*xShift, y+3*yShift);
		drawBorder(Mideas.bag().getBag(15), Sprites.bag_border16, x+3*xShift, y+3*yShift);
		drawBorder(Mideas.bag().getBag(16), Sprites.bag_border17, x, y+ySecondBag);
		drawBorder(Mideas.bag().getBag(17), Sprites.bag_border18, x+xShift, y+ySecondBag);
		drawBorder(Mideas.bag().getBag(18), Sprites.bag_border19, x+2*xShift, y+ySecondBag);
		drawBorder(Mideas.bag().getBag(19), Sprites.bag_border20, x+3*xShift, y+ySecondBag);
		drawBorder(Mideas.bag().getBag(20), Sprites.bag_border21, x, y+yShift+ySecondBag);
		drawBorder(Mideas.bag().getBag(21), Sprites.bag_border22, x+xShift, y+yShift+ySecondBag);
		drawBorder(Mideas.bag().getBag(22), Sprites.bag_border23, x+2*xShift, y+yShift+ySecondBag);
		drawBorder(Mideas.bag().getBag(23), Sprites.bag_border24, x+3*xShift, y+yShift+ySecondBag);
		drawBorder(Mideas.bag().getBag(24), Sprites.bag_border25, x, y+2*yShift+ySecondBag);
		drawBorder(Mideas.bag().getBag(25), Sprites.bag_border26, x+xShift, y+2*yShift+ySecondBag);
		drawBorder(Mideas.bag().getBag(26), Sprites.bag_border27, x+2*xShift, y+2*yShift+ySecondBag);
		drawBorder(Mideas.bag().getBag(27), Sprites.bag_border28, x+3*xShift, y+2*yShift+ySecondBag);
		drawBorder(Mideas.bag().getBag(28), Sprites.bag_border29, x, y+3*yShift+ySecondBag);
		drawBorder(Mideas.bag().getBag(29), Sprites.bag_border30, x+xShift, y+3*yShift+ySecondBag);
		drawBorder(Mideas.bag().getBag(30), Sprites.bag_border31, x+2*xShift, y+3*yShift+ySecondBag);
		drawBorder(Mideas.bag().getBag(31), Sprites.bag_border32, x+3*xShift, y+3*yShift+ySecondBag);
		drawHoverBag(0, x, y, 1, 2);
		drawHoverBag(1, x, y, 42, 2);
		/*if(slot_hover[0]) {
			if(Mideas.bag().getBag(0) != null) {
				Draw.drawColorQuad(Display.getWidth()+x-300, Display.getHeight()/2-80, 285, 80+TTF2.statsName.getLineHeight()*4, bgColor);
				TTF2.itemName.drawStringShadow(Display.getWidth()+x-180-TTF2.itemName.getWidth(Mideas.bag().getBag(0).getStuffName())/2, Display.getHeight()/2+y-20, Mideas.bag().getBag(0).getStuffName(), Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y, "+"+((Stuff)Mideas.bag().getBag(0)).getArmor()+" Armor", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+20, "+"+Mideas.bag().getBag(0).getStamina()+" Stamina", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+40, "+"+Mideas.bag().getBag(0).getMana()+" Mana", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+60, "+"+Mideas.bag().getBag(0).getStrength()+" Strengh", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+80, "+"+Mideas.bag().getBag(0).getCritical()+" Critical", Color.white, Color.black, 1, 1, 1);
				ContainerFrame.calcCoinContainer(Mideas.bag().getBag(0).getSellPrice(), x-70, y+100);
				if(Interface.getShopFrameStatus()) {
					//Mideas.cursorFrame("sprite/interface/cursor_buy.png");
				}
			}
			Draw.drawQuad(Sprites.bag_hover, Display.getWidth()+x+1, Display.getHeight()/2+y+2);
		}
		else if(slot_hover[1]) {
			if(Mideas.bag().getBag(1) != null) {
				Draw.drawColorQuad(Display.getWidth()+x-300, Display.getHeight()/2-80, 285, 80+TTF2.statsName.getLineHeight()*4, bgColor);
				TTF2.itemName.drawStringShadow(Display.getWidth()+x-180-TTF2.itemName.getWidth(Mideas.bag().getBag(1).getStuffName())/2, Display.getHeight()/2+y-20, Mideas.bag().getBag(1).getStuffName(), Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y, "+"+Mideas.bag().getBag(1).getArmor()+" Armor", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+20, "+"+Mideas.bag().getBag(1).getStamina()+" Stamina", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+40, "+"+Mideas.bag().getBag(1).getMana()+" Mana", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+60, "+"+Mideas.bag().getBag(1).getStrength()+" Strengh", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+80, "+"+Mideas.bag().getBag(1).getCritical()+" Critical", Color.white, Color.black, 1, 1, 1);
				ContainerFrame.calcCoinContainer(Mideas.bag().getBag(1).getSellPrice(), x-70, y+100);
				if(Interface.getShopFrameStatus()) {
					//Mideas.cursorFrame("sprite/interface/cursor_buy.png");
				}
			}
			Draw.drawQuad(Sprites.bag_hover, Display.getWidth()+x+42, Display.getHeight()/2+y+2);
		}
		else if(slot_hover[2]) {
			if(Mideas.bag().getBag(2) != null) {
				Draw.drawColorQuad(Display.getWidth()+x-300, Display.getHeight()/2-80, 285, 80+TTF2.statsName.getLineHeight()*4, bgColor);
				TTF2.itemName.drawStringShadow(Display.getWidth()+x-180-TTF2.itemName.getWidth(Mideas.bag().getBag(2).getStuffName())/2, Display.getHeight()/2+y-20, Mideas.bag().getBag(2).getStuffName(), Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y, "+"+Mideas.bag().getBag(2).getArmor()+" Armor", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+20, "+"+Mideas.bag().getBag(2).getStamina()+" Stamina", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+40, "+"+Mideas.bag().getBag(2).getMana()+" Mana", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+60, "+"+Mideas.bag().getBag(2).getStrength()+" Strengh", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+80, "+"+Mideas.bag().getBag(2).getCritical()+" Critical", Color.white, Color.black, 1, 1, 1);
				ContainerFrame.calcCoinContainer(Mideas.bag().getBag(2).getSellPrice(), x-70, y+100);
				if(Interface.getShopFrameStatus()) {
					//Mideas.cursorFrame("sprite/interface/cursor_buy.png");
				}
			}
			Draw.drawQuad(Sprites.bag_hover, Display.getWidth()+x+84, Display.getHeight()/2+y+2);
		}
		else if(slot_hover[3]) {
			if(Mideas.bag().getBag(3) != null) {
				Draw.drawColorQuad(Display.getWidth()+x-300, Display.getHeight()/2-80, 285, 80+TTF2.statsName.getLineHeight()*4, bgColor);
				TTF2.itemName.drawStringShadow(Display.getWidth()+x-180-TTF2.itemName.getWidth(Mideas.bag().getBag(3).getStuffName())/2, Display.getHeight()/2+y-20, Mideas.bag().getBag(3).getStuffName(), Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y, "+"+Mideas.bag().getBag(3).getArmor()+" Armor", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+20, "+"+Mideas.bag().getBag(3).getStamina()+" Stamina", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+40, "+"+Mideas.bag().getBag(3).getMana()+" Mana", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+60, "+"+Mideas.bag().getBag(3).getStrength()+" Strengh", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+80, "+"+Mideas.bag().getBag(3).getCritical()+" Critical", Color.white, Color.black, 1, 1, 1);
				ContainerFrame.calcCoinContainer(Mideas.bag().getBag(3).getSellPrice(), x-70, y+100);
				if(Interface.getShopFrameStatus()) {
					//Mideas.cursorFrame("sprite/interface/cursor_buy.png");
				}
			}
			Draw.drawQuad(Sprites.bag_hover, Display.getWidth()+x+126, Display.getHeight()/2+y+2);
		}
		else if(slot_hover[4]) {
			if(Mideas.bag().getBag(4) != null) {
				Draw.drawColorQuad(Display.getWidth()+x-300, Display.getHeight()/2-80, 285, 80+TTF2.statsName.getLineHeight()*4, bgColor);
				TTF2.itemName.drawStringShadow(Display.getWidth()+x-180-TTF2.itemName.getWidth(Mideas.bag().getBag(4).getStuffName())/2, Display.getHeight()/2+y-20, Mideas.bag().getBag(4).getStuffName(), Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y, "+"+Mideas.bag().getBag(4).getArmor()+" Armor", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+20, "+"+Mideas.bag().getBag(4).getStamina()+" Stamina", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+40, "+"+Mideas.bag().getBag(4).getMana()+" Mana", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+60, "+"+Mideas.bag().getBag(4).getStrength()+" Strengh", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+80, "+"+Mideas.bag().getBag(4).getCritical()+" Critical", Color.white, Color.black, 1, 1, 1);
				ContainerFrame.calcCoinContainer(Mideas.bag().getBag(4).getSellPrice(), x-70, y+100);
				if(Interface.getShopFrameStatus()) {
					//Mideas.cursorFrame("sprite/interface/cursor_buy.png");
				}
			}
			Draw.drawQuad(Sprites.bag_hover, Display.getWidth()+x, Display.getHeight()/2+y+44);
		}
		else if(slot_hover[5]) {
			if(Mideas.bag().getBag(5) != null) {
				Draw.drawColorQuad(Display.getWidth()+x-300, Display.getHeight()/2-80, 285, 80+TTF2.statsName.getLineHeight()*4, bgColor);
				TTF2.itemName.drawStringShadow(Display.getWidth()+x-180-TTF2.itemName.getWidth(Mideas.bag().getBag(5).getStuffName())/2, Display.getHeight()/2+y-20, Mideas.bag().getBag(5).getStuffName(), Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y, "+"+Mideas.bag().getBag(5).getArmor()+" Armor", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+20, "+"+Mideas.bag().getBag(5).getStamina()+" Stamina", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+40, "+"+Mideas.bag().getBag(5).getMana()+" Mana", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+60, "+"+Mideas.bag().getBag(5).getStrength()+" Strengh", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+80, "+"+Mideas.bag().getBag(5).getCritical()+" Critical", Color.white, Color.black, 1, 1, 1);
				ContainerFrame.calcCoinContainer(Mideas.bag().getBag(5).getSellPrice(), x-70, y+100);
				if(Interface.getShopFrameStatus()) {
					//Mideas.cursorFrame("sprite/interface/cursor_buy.png");
				}
			}
			Draw.drawQuad(Sprites.bag_hover, Display.getWidth()+x+42, Display.getHeight()/2+y+44);
		}
		else if(slot_hover[6]) {
			if(Mideas.bag().getBag(6) != null) {
				Draw.drawColorQuad(Display.getWidth()+x-300, Display.getHeight()/2-80, 285, 80+TTF2.statsName.getLineHeight()*4, bgColor);
				TTF2.itemName.drawStringShadow(Display.getWidth()+x-180-TTF2.itemName.getWidth(Mideas.bag().getBag(6).getStuffName())/2, Display.getHeight()/2+y-20, Mideas.bag().getBag(6).getStuffName(), Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y, "+"+Mideas.bag().getBag(6).getArmor()+" Armor", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+20, "+"+Mideas.bag().getBag(6).getStamina()+" Stamina", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+40, "+"+Mideas.bag().getBag(6).getMana()+" Mana", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+60, "+"+Mideas.bag().getBag(6).getStrength()+" Strengh", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+80, "+"+Mideas.bag().getBag(6).getCritical()+" Critical", Color.white, Color.black, 1, 1, 1);
				ContainerFrame.calcCoinContainer(Mideas.bag().getBag(6).getSellPrice(), x-70, y+100);
				if(Interface.getShopFrameStatus()) {
					//Mideas.cursorFrame("sprite/interface/cursor_buy.png");
				}
			}
			Draw.drawQuad(Sprites.bag_hover, Display.getWidth()+x+84, Display.getHeight()/2+y+44);
		}
		else if(slot_hover[7]) {
			if(Mideas.bag().getBag(7) != null) {
				Draw.drawColorQuad(Display.getWidth()+x-300, Display.getHeight()/2-80, 285, 80+TTF2.statsName.getLineHeight()*4, bgColor);
				TTF2.itemName.drawStringShadow(Display.getWidth()+x-180-TTF2.itemName.getWidth(Mideas.bag().getBag(7).getStuffName())/2, Display.getHeight()/2+y-20, Mideas.bag().getBag(7).getStuffName(), Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y, "+"+Mideas.bag().getBag(7).getArmor()+" Armor", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+20, "+"+Mideas.bag().getBag(7).getStamina()+" Stamina", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+40, "+"+Mideas.bag().getBag(7).getMana()+" Mana", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+60, "+"+Mideas.bag().getBag(7).getStrength()+" Strengh", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+80, "+"+Mideas.bag().getBag(7).getCritical()+" Critical", Color.white, Color.black, 1, 1, 1);
				ContainerFrame.calcCoinContainer(Mideas.bag().getBag(7).getSellPrice(), x-70, y+100);
				if(Interface.getShopFrameStatus()) {
					//Mideas.cursorFrame("sprite/interface/cursor_buy.png");
				}
			}
			Draw.drawQuad(Sprites.bag_hover, Display.getWidth()+x+126, Display.getHeight()/2+y+42);
		}
		else if(slot_hover[8]) {
			if(Mideas.bag().getBag(8) != null) {
				Draw.drawColorQuad(Display.getWidth()+x-300, Display.getHeight()/2-80, 285, 80+TTF2.statsName.getLineHeight()*4, bgColor);
				TTF2.itemName.drawStringShadow(Display.getWidth()+x-180-TTF2.itemName.getWidth(Mideas.bag().getBag(8).getStuffName())/2, Display.getHeight()/2+y-20, Mideas.bag().getBag(8).getStuffName(), Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y, "+"+Mideas.bag().getBag(8).getArmor()+" Armor", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+20, "+"+Mideas.bag().getBag(8).getStamina()+" Stamina", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+40, "+"+Mideas.bag().getBag(8).getMana()+" Mana", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+60, "+"+Mideas.bag().getBag(8).getStrength()+" Strengh", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+80, "+"+Mideas.bag().getBag(8).getCritical()+" Critical", Color.white, Color.black, 1, 1, 1);
				ContainerFrame.calcCoinContainer(Mideas.bag().getBag(8).getSellPrice(), x-70, y+100);
				if(Interface.getShopFrameStatus()) {
					//Mideas.cursorFrame("sprite/interface/cursor_buy.png");
				}
			}
			Draw.drawQuad(Sprites.bag_hover, Display.getWidth()+x+1, Display.getHeight()/2+y+84);
		}
		else if(slot_hover[9]) {
			if(Mideas.bag().getBag(9) != null) {
				Draw.drawColorQuad(Display.getWidth()+x-300, Display.getHeight()/2-80, 285, 80+TTF2.statsName.getLineHeight()*4, bgColor);
				TTF2.itemName.drawStringShadow(Display.getWidth()+x-180-TTF2.itemName.getWidth(Mideas.bag().getBag(9).getStuffName())/2, Display.getHeight()/2+y-20, Mideas.bag().getBag(9).getStuffName(), Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y, "+"+Mideas.bag().getBag(9).getArmor()+" Armor", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+20, "+"+Mideas.bag().getBag(9).getStamina()+" Stamina", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+40, "+"+Mideas.bag().getBag(9).getMana()+" Mana", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+60, "+"+Mideas.bag().getBag(9).getStrength()+" Strengh", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+80, "+"+Mideas.bag().getBag(9).getCritical()+" Critical", Color.white, Color.black, 1, 1, 1);
				ContainerFrame.calcCoinContainer(Mideas.bag().getBag(9).getSellPrice(), x-70, y+100);
				if(Interface.getShopFrameStatus()) {
					//Mideas.cursorFrame("sprite/interface/cursor_buy.png");
				}
			}
			Draw.drawQuad(Sprites.bag_hover, Display.getWidth()+x+42, Display.getHeight()/2+y+84);
		}
		else if(slot_hover[10]) {
			if(Mideas.bag().getBag(10) != null) {
				Draw.drawColorQuad(Display.getWidth()+x-300, Display.getHeight()/2-80, 285, 80+TTF2.statsName.getLineHeight()*4, bgColor);
				TTF2.itemName.drawStringShadow(Display.getWidth()+x-180-TTF2.itemName.getWidth(Mideas.bag().getBag(10).getStuffName())/2, Display.getHeight()/2+y-20, Mideas.bag().getBag(10).getStuffName(), Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y, "+"+Mideas.bag().getBag(10).getArmor()+" Armor", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+20, "+"+Mideas.bag().getBag(10).getStamina()+" Stamina", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+40, "+"+Mideas.bag().getBag(10).getMana()+" Mana", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+60, "+"+Mideas.bag().getBag(10).getStrength()+" Strengh", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+80, "+"+Mideas.bag().getBag(10).getCritical()+" Critical", Color.white, Color.black, 1, 1, 1);
				ContainerFrame.calcCoinContainer(Mideas.bag().getBag(10).getSellPrice(), x-70, y+100);
				if(Interface.getShopFrameStatus()) {
					//Mideas.cursorFrame("sprite/interface/cursor_buy.png");
				}
			}
			Draw.drawQuad(Sprites.bag_hover, Display.getWidth()+x+84, Display.getHeight()/2+y+84);
		}
		else if(slot_hover[11]) {
			if(Mideas.bag().getBag(11) != null) {
				Draw.drawColorQuad(Display.getWidth()+x-300, Display.getHeight()/2-80, 285, 80+TTF2.statsName.getLineHeight()*4, bgColor);
				TTF2.itemName.drawStringShadow(Display.getWidth()+x-180-TTF2.itemName.getWidth(Mideas.bag().getBag(11).getStuffName())/2, Display.getHeight()/2+y-20, Mideas.bag().getBag(11).getStuffName(), Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y, "+"+Mideas.bag().getBag(11).getArmor()+" Armor", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+20, "+"+Mideas.bag().getBag(11).getStamina()+" Stamina", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+40, "+"+Mideas.bag().getBag(11).getMana()+" Mana", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+60, "+"+Mideas.bag().getBag(11).getStrength()+" Strengh", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+80, "+"+Mideas.bag().getBag(11).getCritical()+" Critical", Color.white, Color.black, 1, 1, 1);
				ContainerFrame.calcCoinContainer(Mideas.bag().getBag(11).getSellPrice(), x-70, y+100);
				if(Interface.getShopFrameStatus()) {
					//Mideas.cursorFrame("sprite/interface/cursor_buy.png");
				}
			}
			Draw.drawQuad(Sprites.bag_hover, Display.getWidth()+x+126, Display.getHeight()/2+y+84);
		}
		else if(slot_hover[12]) {
			if(Mideas.bag().getBag(12) != null) {
				Draw.drawColorQuad(Display.getWidth()+x-300, Display.getHeight()/2-80, 285, 80+TTF2.statsName.getLineHeight()*4, bgColor);
				TTF2.itemName.drawStringShadow(Display.getWidth()+x-180-TTF2.itemName.getWidth(Mideas.bag().getBag(12).getStuffName())/2, Display.getHeight()/2+y-20, Mideas.bag().getBag(12).getStuffName(), Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y, "+"+Mideas.bag().getBag(12).getArmor()+" Armor", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+20, "+"+Mideas.bag().getBag(12).getStamina()+" Stamina", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+40, "+"+Mideas.bag().getBag(12).getMana()+" Mana", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+60, "+"+Mideas.bag().getBag(12).getStrength()+" Strengh", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+80, "+"+Mideas.bag().getBag(12).getCritical()+" Critical", Color.white, Color.black, 1, 1, 1);
				ContainerFrame.calcCoinContainer(Mideas.bag().getBag(12).getSellPrice(), x-70, y+100);
				if(Interface.getShopFrameStatus()) {
					//Mideas.cursorFrame("sprite/interface/cursor_buy.png");
				}
			}
			Draw.drawQuad(Sprites.bag_hover, Display.getWidth()+x+1, Display.getHeight()/2+y+126);
		}
		else if(slot_hover[13]) {
			if(Mideas.bag().getBag(13) != null) {
				Draw.drawColorQuad(Display.getWidth()+x-300, Display.getHeight()/2-80, 285, 80+TTF2.statsName.getLineHeight()*4, bgColor);
				TTF2.itemName.drawStringShadow(Display.getWidth()+x-180-TTF2.itemName.getWidth(Mideas.bag().getBag(13).getStuffName())/2, Display.getHeight()/2+y-20, Mideas.bag().getBag(13).getStuffName(), Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y, "+"+Mideas.bag().getBag(13).getArmor()+" Armor", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+20, "+"+Mideas.bag().getBag(13).getStamina()+" Stamina", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+40, "+"+Mideas.bag().getBag(13).getMana()+" Mana", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+60, "+"+Mideas.bag().getBag(13).getStrength()+" Strengh", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+80, "+"+Mideas.bag().getBag(13).getCritical()+" Critical", Color.white, Color.black, 1, 1, 1);
				ContainerFrame.calcCoinContainer(Mideas.bag().getBag(13).getSellPrice(), x-70, y+100);
				if(Interface.getShopFrameStatus()) {
					//Mideas.cursorFrame("sprite/interface/cursor_buy.png");
				}
			}
			Draw.drawQuad(Sprites.bag_hover, Display.getWidth()+x+42, Display.getHeight()/2+y+126);
		}
		else if(slot_hover[14]) {
			if(Mideas.bag().getBag(14) != null) {
				Draw.drawColorQuad(Display.getWidth()+x-300, Display.getHeight()/2-80, 285, 80+TTF2.statsName.getLineHeight()*4, bgColor);
				TTF2.itemName.drawStringShadow(Display.getWidth()+x-180-TTF2.itemName.getWidth(Mideas.bag().getBag(14).getStuffName())/2, Display.getHeight()/2+y-20, Mideas.bag().getBag(14).getStuffName(), Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y, "+"+Mideas.bag().getBag(14).getArmor()+" Armor", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+20, "+"+Mideas.bag().getBag(14).getStamina()+" Stamina", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+40, "+"+Mideas.bag().getBag(14).getMana()+" Mana", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+60, "+"+Mideas.bag().getBag(14).getStrength()+" Strengh", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+80, "+"+Mideas.bag().getBag(14).getCritical()+" Critical", Color.white, Color.black, 1, 1, 1);
				ContainerFrame.calcCoinContainer(Mideas.bag().getBag(14).getSellPrice(), x-70, y+100);
				if(Interface.getShopFrameStatus()) {
					//Mideas.cursorFrame("sprite/interface/cursor_buy.png");
				}
			}
			Draw.drawQuad(Sprites.bag_hover, Display.getWidth()+x+84, Display.getHeight()/2+y+126);
		}
		else if(slot_hover[15]) {
			if(Mideas.bag().getBag(15) != null) {
				Draw.drawColorQuad(Display.getWidth()+x-300, Display.getHeight()/2-80, 285, 80+TTF2.statsName.getLineHeight()*4, bgColor);
				TTF2.itemName.drawStringShadow(Display.getWidth()+x-180-TTF2.itemName.getWidth(Mideas.bag().getBag(15).getStuffName())/2, Display.getHeight()/2+y-20, Mideas.bag().getBag(15).getStuffName(), Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y, "+"+Mideas.bag().getBag(15).getArmor()+" Armor", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+20, "+"+Mideas.bag().getBag(15).getStamina()+" Stamina", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+40, "+"+Mideas.bag().getBag(15).getMana()+" Mana", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+60, "+"+Mideas.bag().getBag(15).getStrength()+" Strengh", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+80, "+"+Mideas.bag().getBag(15).getCritical()+" Critical", Color.white, Color.black, 1, 1, 1);
				ContainerFrame.calcCoinContainer(Mideas.bag().getBag(15).getSellPrice(), x-70, y+100);
				if(Interface.getShopFrameStatus()) {
					//Mideas.cursorFrame("sprite/interface/cursor_buy.png");
				}
			}
			Draw.drawQuad(Sprites.bag_hover, Display.getWidth()+x+126, Display.getHeight()/2+y+126);
		}
		else if(slot_hover[16]) {
			if(Mideas.bag().getBag(16) != null) {
				Draw.drawColorQuad(Display.getWidth()+x-300, Display.getHeight()/2-80+ySecondBag, 285, 80+TTF2.statsName.getLineHeight()*4, bgColor);
				TTF2.itemName.drawStringShadow(Display.getWidth()+x-180-TTF2.itemName.getWidth(Mideas.bag().getBag(16).getStuffName())/2, Display.getHeight()/2+y-20+ySecondBag, Mideas.bag().getBag(16).getStuffName(), Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+ySecondBag, "+"+Mideas.bag().getBag(16).getArmor()+" Armor", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+20+ySecondBag, "+"+Mideas.bag().getBag(16).getStamina()+" Stamina", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+40+ySecondBag, "+"+Mideas.bag().getBag(16).getMana()+" Mana", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+60+ySecondBag, "+"+Mideas.bag().getBag(16).getStrength()+" Strengh", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+80+ySecondBag, "+"+Mideas.bag().getBag(16).getCritical()+" Critical", Color.white, Color.black, 1, 1, 1);
				ContainerFrame.calcCoinContainer(Mideas.bag().getBag(16).getSellPrice(), x-70, y+100+ySecondBag);
				if(Interface.getShopFrameStatus()) {
					//Mideas.cursorFrame("sprite/interface/cursor_buy.png");
				}
			}
			Draw.drawQuad(Sprites.bag_hover, Display.getWidth()+x+1, Display.getHeight()/2+y+2+ySecondBag);
		}
		else if(slot_hover[17]) {
			if(Mideas.bag().getBag(17) != null) {
				Draw.drawColorQuad(Display.getWidth()+x-300, Display.getHeight()/2-80+ySecondBag, 285, 80+TTF2.statsName.getLineHeight()*4, bgColor);
				TTF2.itemName.drawStringShadow(Display.getWidth()+x-180-TTF2.itemName.getWidth(Mideas.bag().getBag(17).getStuffName())/2, Display.getHeight()/2+y-20+ySecondBag, Mideas.bag().getBag(17).getStuffName(), Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+ySecondBag, "+"+Mideas.bag().getBag(17).getArmor()+" Armor", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+20+ySecondBag, "+"+Mideas.bag().getBag(17).getStamina()+" Stamina", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+40+ySecondBag, "+"+Mideas.bag().getBag(17).getMana()+" Mana", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+60+ySecondBag, "+"+Mideas.bag().getBag(17).getStrength()+" Strengh", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+80+ySecondBag, "+"+Mideas.bag().getBag(17).getCritical()+" Critical", Color.white, Color.black, 1, 1, 1);
				ContainerFrame.calcCoinContainer(Mideas.bag().getBag(17).getSellPrice(), x-70, y+100+ySecondBag);
				if(Interface.getShopFrameStatus()) {
					//Mideas.cursorFrame("sprite/interface/cursor_buy.png");
				}
			}
			Draw.drawQuad(Sprites.bag_hover, Display.getWidth()+x+42, Display.getHeight()/2+y+2+ySecondBag);
		}
		else if(slot_hover[18]) {
			if(Mideas.bag().getBag(18) != null) {
				Draw.drawColorQuad(Display.getWidth()+x-300, Display.getHeight()/2-80+ySecondBag, 285, 80+TTF2.statsName.getLineHeight()*4, bgColor);
				TTF2.itemName.drawStringShadow(Display.getWidth()+x-180-TTF2.itemName.getWidth(Mideas.bag().getBag(18).getStuffName())/2, Display.getHeight()/2+y-20+ySecondBag, Mideas.bag().getBag(18).getStuffName(), Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+ySecondBag, "+"+Mideas.bag().getBag(18).getArmor()+" Armor", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+20+ySecondBag, "+"+Mideas.bag().getBag(18).getStamina()+" Stamina", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+40+ySecondBag, "+"+Mideas.bag().getBag(18).getMana()+" Mana", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+60+ySecondBag, "+"+Mideas.bag().getBag(18).getStrength()+" Strengh", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+80+ySecondBag, "+"+Mideas.bag().getBag(18).getCritical()+" Critical", Color.white, Color.black, 1, 1, 1);
				ContainerFrame.calcCoinContainer(Mideas.bag().getBag(18).getSellPrice(), x-70, y+100+ySecondBag);
				if(Interface.getShopFrameStatus()) {
					//Mideas.cursorFrame("sprite/interface/cursor_buy.png");
				}
			}
			Draw.drawQuad(Sprites.bag_hover, Display.getWidth()+x+84, Display.getHeight()/2+y+2+ySecondBag);
		}
		else if(slot_hover[19]) {
			if(Mideas.bag().getBag(19) != null) {
				Draw.drawColorQuad(Display.getWidth()+x-300, Display.getHeight()/2-80+ySecondBag, 285, 80+TTF2.statsName.getLineHeight()*4, bgColor);
				TTF2.itemName.drawStringShadow(Display.getWidth()+x-180-TTF2.itemName.getWidth(Mideas.bag().getBag(19).getStuffName())/2, Display.getHeight()/2+y-20+ySecondBag, Mideas.bag().getBag(19).getStuffName(), Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+ySecondBag, "+"+Mideas.bag().getBag(19).getArmor()+" Armor", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+20+ySecondBag, "+"+Mideas.bag().getBag(19).getStamina()+" Stamina", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+40+ySecondBag, "+"+Mideas.bag().getBag(19).getMana()+" Mana", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+60+ySecondBag, "+"+Mideas.bag().getBag(19).getStrength()+" Strengh", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+80+ySecondBag, "+"+Mideas.bag().getBag(19).getCritical()+" Critical", Color.white, Color.black, 1, 1, 1);
				ContainerFrame.calcCoinContainer(Mideas.bag().getBag(19).getSellPrice(), x-70, y+100+ySecondBag);
				if(Interface.getShopFrameStatus()) {
					//Mideas.cursorFrame("sprite/interface/cursor_buy.png");
				}
			}
			Draw.drawQuad(Sprites.bag_hover, Display.getWidth()+x+126, Display.getHeight()/2+y+2+ySecondBag);
		}
		else if(slot_hover[20]) {
			if(Mideas.bag().getBag(20) != null) {
				Draw.drawColorQuad(Display.getWidth()+x-300, Display.getHeight()/2-80+ySecondBag, 285, 80+TTF2.statsName.getLineHeight()*4, bgColor);
				TTF2.itemName.drawStringShadow(Display.getWidth()+x-180-TTF2.itemName.getWidth(Mideas.bag().getBag(20).getStuffName())/2, Display.getHeight()/2+y-20+ySecondBag, Mideas.bag().getBag(20).getStuffName(), Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+ySecondBag, "+"+Mideas.bag().getBag(20).getArmor()+" Armor", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+20+ySecondBag, "+"+Mideas.bag().getBag(20).getStamina()+" Stamina", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+40+ySecondBag, "+"+Mideas.bag().getBag(20).getMana()+" Mana", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+60+ySecondBag, "+"+Mideas.bag().getBag(20).getStrength()+" Strengh", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+80+ySecondBag, "+"+Mideas.bag().getBag(20).getCritical()+" Critical", Color.white, Color.black, 1, 1, 1);
				ContainerFrame.calcCoinContainer(Mideas.bag().getBag(20).getSellPrice(), x-70, y+100+ySecondBag);
				if(Interface.getShopFrameStatus()) {
					//Mideas.cursorFrame("sprite/interface/cursor_buy.png");
				}
			}
			Draw.drawQuad(Sprites.bag_hover, Display.getWidth()+x+1, Display.getHeight()/2+y+41+ySecondBag);
		}
		else if(slot_hover[21]) {
			if(Mideas.bag().getBag(21) != null) {
				Draw.drawColorQuad(Display.getWidth()+x-300, Display.getHeight()/2-80+ySecondBag, 285, 80+TTF2.statsName.getLineHeight()*4, bgColor);
				TTF2.itemName.drawStringShadow(Display.getWidth()+x-180-TTF2.itemName.getWidth(Mideas.bag().getBag(21).getStuffName())/2, Display.getHeight()/2+y-20+ySecondBag, Mideas.bag().getBag(21).getStuffName(), Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+ySecondBag, "+"+Mideas.bag().getBag(21).getArmor()+" Armor", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+20+ySecondBag, "+"+Mideas.bag().getBag(21).getStamina()+" Stamina", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+40+ySecondBag, "+"+Mideas.bag().getBag(21).getMana()+" Mana", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+60+ySecondBag, "+"+Mideas.bag().getBag(21).getStrength()+" Strengh", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+80+ySecondBag, "+"+Mideas.bag().getBag(21).getCritical()+" Critical", Color.white, Color.black, 1, 1, 1);
				ContainerFrame.calcCoinContainer(Mideas.bag().getBag(21).getSellPrice(), x-70, y+100+ySecondBag);
				if(Interface.getShopFrameStatus()) {
					//Mideas.cursorFrame("sprite/interface/cursor_buy.png");
				}
			}
			Draw.drawQuad(Sprites.bag_hover, Display.getWidth()+x+42, Display.getHeight()/2+y+41+ySecondBag);
		}
		else if(slot_hover[22]) {
			if(Mideas.bag().getBag(22) != null) {
				Draw.drawColorQuad(Display.getWidth()+x-300, Display.getHeight()/2-80+ySecondBag, 285, 80+TTF2.statsName.getLineHeight()*4, bgColor);
				TTF2.itemName.drawStringShadow(Display.getWidth()+x-180-TTF2.itemName.getWidth(Mideas.bag().getBag(22).getStuffName())/2, Display.getHeight()/2+y-20+ySecondBag, Mideas.bag().getBag(22).getStuffName(), Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+ySecondBag, "+"+Mideas.bag().getBag(22).getArmor()+" Armor", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+20+ySecondBag, "+"+Mideas.bag().getBag(22).getStamina()+" Stamina", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+40+ySecondBag, "+"+Mideas.bag().getBag(22).getMana()+" Mana", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+60+ySecondBag, "+"+Mideas.bag().getBag(22).getStrength()+" Strengh", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+80+ySecondBag, "+"+Mideas.bag().getBag(22).getCritical()+" Critical", Color.white, Color.black, 1, 1, 1);
				ContainerFrame.calcCoinContainer(Mideas.bag().getBag(22).getSellPrice(), x-70, y+100+ySecondBag);
				if(Interface.getShopFrameStatus()) {
					//Mideas.cursorFrame("sprite/interface/cursor_buy.png");
				}
			}
			Draw.drawQuad(Sprites.bag_hover, Display.getWidth()+x+84, Display.getHeight()/2+y+41+ySecondBag);
		}
		else if(slot_hover[23]) {
			if(Mideas.bag().getBag(23) != null) {
				Draw.drawColorQuad(Display.getWidth()+x-300, Display.getHeight()/2-80+ySecondBag, 285, 80+TTF2.statsName.getLineHeight()*4, bgColor);
				TTF2.itemName.drawStringShadow(Display.getWidth()+x-180-TTF2.itemName.getWidth(Mideas.bag().getBag(23).getStuffName())/2, Display.getHeight()/2+y-20+ySecondBag, Mideas.bag().getBag(23).getStuffName(), Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+ySecondBag, "+"+Mideas.bag().getBag(23).getArmor()+" Armor", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+20+ySecondBag, "+"+Mideas.bag().getBag(23).getStamina()+" Stamina", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+40+ySecondBag, "+"+Mideas.bag().getBag(23).getMana()+" Mana", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+60+ySecondBag, "+"+Mideas.bag().getBag(23).getStrength()+" Strengh", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+80+ySecondBag, "+"+Mideas.bag().getBag(23).getCritical()+" Critical", Color.white, Color.black, 1, 1, 1);
				ContainerFrame.calcCoinContainer(Mideas.bag().getBag(23).getSellPrice(), x-70, y+100+ySecondBag);
				if(Interface.getShopFrameStatus()) {
					//Mideas.cursorFrame("sprite/interface/cursor_buy.png");
				}
			}
			Draw.drawQuad(Sprites.bag_hover, Display.getWidth()+x+126, Display.getHeight()/2+y+41+ySecondBag);
		}
		else if(slot_hover[24]) {
			if(Mideas.bag().getBag(24) != null) {
				Draw.drawColorQuad(Display.getWidth()+x-300, Display.getHeight()/2-80+ySecondBag, 285, 80+TTF2.statsName.getLineHeight()*4, bgColor);
				TTF2.itemName.drawStringShadow(Display.getWidth()+x-180-TTF2.itemName.getWidth(Mideas.bag().getBag(24).getStuffName())/2, Display.getHeight()/2+y-20+ySecondBag, Mideas.bag().getBag(24).getStuffName(), Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+ySecondBag, "+"+Mideas.bag().getBag(24).getArmor()+" Armor", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+20+ySecondBag, "+"+Mideas.bag().getBag(24).getStamina()+" Stamina", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+40+ySecondBag, "+"+Mideas.bag().getBag(24).getMana()+" Mana", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+60+ySecondBag, "+"+Mideas.bag().getBag(24).getStrength()+" Strengh", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+80+ySecondBag, "+"+Mideas.bag().getBag(24).getCritical()+" Critical", Color.white, Color.black, 1, 1, 1);
				ContainerFrame.calcCoinContainer(Mideas.bag().getBag(24).getSellPrice(), x-70, y+100+ySecondBag);
				if(Interface.getShopFrameStatus()) {
					//Mideas.cursorFrame("sprite/interface/cursor_buy.png");
				}
			}
			Draw.drawQuad(Sprites.bag_hover, Display.getWidth()+x+1, Display.getHeight()/2+y+83+ySecondBag);
		}
		else if(slot_hover[25]) {
			if(Mideas.bag().getBag(25) != null) {
				Draw.drawColorQuad(Display.getWidth()+x-300, Display.getHeight()/2-80+ySecondBag, 285, 80+TTF2.statsName.getLineHeight()*4, bgColor);
				TTF2.itemName.drawStringShadow(Display.getWidth()+x-180-TTF2.itemName.getWidth(Mideas.bag().getBag(25).getStuffName())/2, Display.getHeight()/2+y-20+ySecondBag, Mideas.bag().getBag(25).getStuffName(), Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+ySecondBag, "+"+Mideas.bag().getBag(25).getArmor()+" Armor", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+20+ySecondBag, "+"+Mideas.bag().getBag(25).getStamina()+" Stamina", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+40+ySecondBag, "+"+Mideas.bag().getBag(25).getMana()+" Mana", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+60+ySecondBag, "+"+Mideas.bag().getBag(25).getStrength()+" Strengh", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+80+ySecondBag, "+"+Mideas.bag().getBag(25).getCritical()+" Critical", Color.white, Color.black, 1, 1, 1);
				ContainerFrame.calcCoinContainer(Mideas.bag().getBag(25).getSellPrice(), x-70, y+100+ySecondBag);
				if(Interface.getShopFrameStatus()) {
					//Mideas.cursorFrame("sprite/interface/cursor_buy.png");
				}
			}
			Draw.drawQuad(Sprites.bag_hover, Display.getWidth()+x+42, Display.getHeight()/2+y+83+ySecondBag);
		}
		else if(slot_hover[26]) {
			if(Mideas.bag().getBag(26) != null) {
				Draw.drawColorQuad(Display.getWidth()+x-300, Display.getHeight()/2-80+ySecondBag, 285, 80+TTF2.statsName.getLineHeight()*4, bgColor);
				TTF2.itemName.drawStringShadow(Display.getWidth()+x-180-TTF2.itemName.getWidth(Mideas.bag().getBag(26).getStuffName())/2, Display.getHeight()/2+y-20+ySecondBag, Mideas.bag().getBag(26).getStuffName(), Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+ySecondBag, "+"+Mideas.bag().getBag(26).getArmor()+" Armor", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+20+ySecondBag, "+"+Mideas.bag().getBag(26).getStamina()+" Stamina", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+40+ySecondBag, "+"+Mideas.bag().getBag(26).getMana()+" Mana", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+60+ySecondBag, "+"+Mideas.bag().getBag(26).getStrength()+" Strengh", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+80+ySecondBag, "+"+Mideas.bag().getBag(26).getCritical()+" Critical", Color.white, Color.black, 1, 1, 1);
				ContainerFrame.calcCoinContainer(Mideas.bag().getBag(26).getSellPrice(), x-70, y+100+ySecondBag);
				if(Interface.getShopFrameStatus()) {
					//Mideas.cursorFrame("sprite/interface/cursor_buy.png");
				}
			}
			Draw.drawQuad(Sprites.bag_hover, Display.getWidth()+x+84, Display.getHeight()/2+y+83+ySecondBag);
		}
		else if(slot_hover[27]) {
			if(Mideas.bag().getBag(27) != null) {
				Draw.drawColorQuad(Display.getWidth()+x-300, Display.getHeight()/2-80+ySecondBag, 285, 80+TTF2.statsName.getLineHeight()*4, bgColor);
				TTF2.itemName.drawStringShadow(Display.getWidth()+x-180-TTF2.itemName.getWidth(Mideas.bag().getBag(27).getStuffName())/2, Display.getHeight()/2+y-20+ySecondBag, Mideas.bag().getBag(27).getStuffName(), Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+ySecondBag, "+"+Mideas.bag().getBag(27).getArmor()+" Armor", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+20+ySecondBag, "+"+Mideas.bag().getBag(27).getStamina()+" Stamina", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+40+ySecondBag, "+"+Mideas.bag().getBag(27).getMana()+" Mana", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+60+ySecondBag, "+"+Mideas.bag().getBag(27).getStrength()+" Strengh", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+80+ySecondBag, "+"+Mideas.bag().getBag(27).getCritical()+" Critical", Color.white, Color.black, 1, 1, 1);
				ContainerFrame.calcCoinContainer(Mideas.bag().getBag(27).getSellPrice(), x-70, y+100+ySecondBag);
				if(Interface.getShopFrameStatus()) {
					//Mideas.cursorFrame("sprite/interface/cursor_buy.png");
				}
			}
			Draw.drawQuad(Sprites.bag_hover, Display.getWidth()+x+126, Display.getHeight()/2+y+83+ySecondBag);
		}
		else if(slot_hover[28]) {
			if(Mideas.bag().getBag(28) != null) {
				Draw.drawColorQuad(Display.getWidth()+x-300, Display.getHeight()/2-80+ySecondBag, 285, 80+TTF2.statsName.getLineHeight()*4, bgColor);
				TTF2.itemName.drawStringShadow(Display.getWidth()+x-180-TTF2.itemName.getWidth(Mideas.bag().getBag(28).getStuffName())/2, Display.getHeight()/2+y-20+ySecondBag, Mideas.bag().getBag(28).getStuffName(), Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+ySecondBag, "+"+Mideas.bag().getBag(28).getArmor()+" Armor", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+20+ySecondBag, "+"+Mideas.bag().getBag(28).getStamina()+" Stamina", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+40+ySecondBag, "+"+Mideas.bag().getBag(28).getMana()+" Mana", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+60+ySecondBag, "+"+Mideas.bag().getBag(28).getStrength()+" Strengh", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+80+ySecondBag, "+"+Mideas.bag().getBag(28).getCritical()+" Critical", Color.white, Color.black, 1, 1, 1);
				ContainerFrame.calcCoinContainer(Mideas.bag().getBag(28).getSellPrice(), x-70, y+100+ySecondBag);
				if(Interface.getShopFrameStatus()) {
					//Mideas.cursorFrame("sprite/interface/cursor_buy.png");
				}
			}
			Draw.drawQuad(Sprites.bag_hover, Display.getWidth()+x+1, Display.getHeight()/2+y+126+ySecondBag);
		}
		else if(slot_hover[29]) {
			if(Mideas.bag().getBag(29) != null) {
				Draw.drawColorQuad(Display.getWidth()+x-300, Display.getHeight()/2-80+ySecondBag, 285, 80+TTF2.statsName.getLineHeight()*4, bgColor);
				TTF2.itemName.drawStringShadow(Display.getWidth()+x-180-TTF2.itemName.getWidth(Mideas.bag().getBag(29).getStuffName())/2, Display.getHeight()/2+y-20+ySecondBag, Mideas.bag().getBag(29).getStuffName(), Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+ySecondBag, "+"+Mideas.bag().getBag(29).getArmor()+" Armor", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+20+ySecondBag, "+"+Mideas.bag().getBag(29).getStamina()+" Stamina", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+40+ySecondBag, "+"+Mideas.bag().getBag(29).getMana()+" Mana", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+60+ySecondBag, "+"+Mideas.bag().getBag(29).getStrength()+" Strengh", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+80+ySecondBag, "+"+Mideas.bag().getBag(29).getCritical()+" Critical", Color.white, Color.black, 1, 1, 1);
				ContainerFrame.calcCoinContainer(Mideas.bag().getBag(29).getSellPrice(), x-70, y+100+ySecondBag);
				if(Interface.getShopFrameStatus()) {
					//Mideas.cursorFrame("sprite/interface/cursor_buy.png");
				}
			}
			Draw.drawQuad(Sprites.bag_hover, Display.getWidth()+x+42, Display.getHeight()/2+y+126+ySecondBag);
		}
		else if(slot_hover[30]) {
			if(Mideas.bag().getBag(30) != null) {
				Draw.drawColorQuad(Display.getWidth()+x-300, Display.getHeight()/2-80+ySecondBag, 285, 80+TTF2.statsName.getLineHeight()*4, bgColor);
				TTF2.itemName.drawStringShadow(Display.getWidth()+x-180-TTF2.itemName.getWidth(Mideas.bag().getBag(30).getStuffName())/2, Display.getHeight()/2+y-20+ySecondBag, Mideas.bag().getBag(30).getStuffName(), Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+ySecondBag, "+"+Mideas.bag().getBag(30).getArmor()+" Armor", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+20+ySecondBag, "+"+Mideas.bag().getBag(30).getStamina()+" Stamina", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+40+ySecondBag, "+"+Mideas.bag().getBag(30).getMana()+" Mana", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+60+ySecondBag, "+"+Mideas.bag().getBag(30).getStrength()+" Strengh", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+80+ySecondBag, "+"+Mideas.bag().getBag(30).getCritical()+" Critical", Color.white, Color.black, 1, 1, 1);
				ContainerFrame.calcCoinContainer(Mideas.bag().getBag(30).getSellPrice(), x-70, y+100+ySecondBag);
				if(Interface.getShopFrameStatus()) {
					//Mideas.cursorFrame("sprite/interface/cursor_buy.png");
				}
			}
			Draw.drawQuad(Sprites.bag_hover, Display.getWidth()+x+84, Display.getHeight()/2+y+126+ySecondBag);
		}
		else if(slot_hover[31]) {
			if(Mideas.bag().getBag(31) != null) {
				Draw.drawColorQuad(Display.getWidth()+x-300, Display.getHeight()/2-80+ySecondBag, 285, 80+TTF2.statsName.getLineHeight()*4, bgColor);
				TTF2.itemName.drawStringShadow(Display.getWidth()+x-180-TTF2.itemName.getWidth(Mideas.bag().getBag(31).getStuffName())/2, Display.getHeight()/2+y-20+ySecondBag, Mideas.bag().getBag(31).getStuffName(), Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+ySecondBag, "+"+Mideas.bag().getBag(31).getArmor()+" Armor", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+20+ySecondBag, "+"+Mideas.bag().getBag(31).getStamina()+" Stamina", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+40+ySecondBag, "+"+Mideas.bag().getBag(31).getMana()+" Mana", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+60+ySecondBag, "+"+Mideas.bag().getBag(31).getStrength()+" Strengh", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+80+ySecondBag, "+"+Mideas.bag().getBag(31).getCritical()+" Critical", Color.white, Color.black, 1, 1, 1);
				ContainerFrame.calcCoinContainer(Mideas.bag().getBag(31).getSellPrice(), x-70, y+100+ySecondBag);
				if(Interface.getShopFrameStatus()) {
					//Mideas.cursorFrame("sprite/interface/cursor_buy.png");
				}
			}
			Draw.drawQuad(Sprites.bag_hover, Display.getWidth()+x+126, Display.getHeight()/2+y+126+ySecondBag);
		}*/
	}
	
	public static boolean mouseEvent() throws FileNotFoundException, SQLException {
		Arrays.fill(slot_hover, false);
		if(Mouse.getEventButtonState()) {
			if(Mideas.mouseX() >= Display.getWidth()/2+545 && Mideas.mouseX() <= Display.getWidth()/2+563 && Mideas.mouseY() >= Display.getHeight()/2-95 && Mideas.mouseY() <= Display.getHeight()/2-70 || (Mideas.mouseX() >= Display.getWidth()/2+545 && Mideas.mouseX() <= Display.getWidth()/2+563 && Mideas.mouseY() >= Display.getHeight()/2+155 && Mideas.mouseY() <= Display.getHeight()/2+175 )) {
				Interface.closeContainerFrame();
				return true;
			}
		}
		int x = -203;
		int xShift = 41;
		int yShift = 42;
		int ySecondBag = 251;
		int y = -50;
		slotHover(x, y, 0);
		slotHover(x+xShift, y, 1);
		slotHover(x+2*xShift, y, 2);
		slotHover(x+3*xShift, y, 3);
		slotHover(x, y+yShift, 4);
		slotHover(x+xShift, y+yShift, 5);
		slotHover(x+2*xShift, y+yShift, 6);
		slotHover(x+3*xShift, y+yShift, 7);
		slotHover(x, y+2*yShift, 8);
		slotHover(x+xShift, y+2*yShift, 9);
		slotHover(x+2*xShift, y+2*yShift, 10);
		slotHover(x+3*xShift, y+2*yShift, 11);
		slotHover(x, y+3*yShift, 12);
		slotHover(x+xShift, y+3*yShift, 13);
		slotHover(x+2*xShift, y+3*yShift, 14);
		slotHover(x+3*xShift, y+3*yShift, 15);
		slotHover(x, y+ySecondBag, 16);
		slotHover(x+xShift, y+ySecondBag, 17);
		slotHover(x+2*xShift, y+ySecondBag, 18);
		slotHover(x+3*xShift, y+ySecondBag, 19);
		slotHover(x, y+ySecondBag+yShift, 20);
		slotHover(x+xShift, y+ySecondBag+yShift, 21);
		slotHover(x+2*xShift, y+ySecondBag+yShift, 22);
		slotHover(x+3*xShift, y+ySecondBag+yShift, 23);
		slotHover(x, y+ySecondBag+2*yShift, 24);
		slotHover(x+xShift, y+ySecondBag+2*yShift, 25);
		slotHover(x+2*xShift, y+ySecondBag+2*yShift, 26);
		slotHover(x+3*xShift, y+ySecondBag+2*yShift, 27);
		slotHover(x, y+ySecondBag+3*yShift, 28);
		slotHover(x+xShift, y+ySecondBag+3*yShift, 29);
		slotHover(x+2*xShift, y+ySecondBag+3*yShift, 30);
		slotHover(x+3*xShift, y+ySecondBag+3*yShift, 31);
			if(Mouse.getEventButton() == 1) {
				if(Mouse.getEventButtonState()) {
					int i = 0;
					while(i < Mideas.bag().getBag().length) {
						clickSellItem(i);
						i++;
					}
				CharacterStuff.setBagItems();
				}
			}
		return false;
	}
	
	public static void keyboardEvent() {
		
	}
	
	private static void drawBorder(Item item, Texture border, int x, int y) {
		if(item != null && item != DragManager.getDraggedItem()) {
			if(item.getItemType() == ItemType.ITEM || item.getItemType() == ItemType.POTION) {
				TTF2.itemNumber.drawStringShadow(Display.getWidth()+x+35-TTF2.itemNumber.getWidth(String.valueOf(Mideas.joueur1().getNumberItem(item, getSlotItem(item)))), Display.getHeight()/2+y+20, String.valueOf(Mideas.joueur1().getNumberItem(item, getSlotItem(item))), Color.white, Color.black, 1, 1, 1);
			}
		Draw.drawQuad(border, Display.getWidth()+x, Display.getHeight()/2+y);
		}
	}
	
	private static boolean sellItem(Item item, boolean slot_hover) throws FileNotFoundException, SQLTimeoutException, SQLException {
		if(item != null && slot_hover && Interface.getShopFrameStatus()) {
			if(item.getItemType() == ItemType.ITEM || item.getItemType() == ItemType.POTION) {
				LogChat.setStatusText3("Vous avez vendu "+Mideas.joueur1().getNumberItem(item, getSlotItem(item))+" "+item.getStuffName()+" pour "+item.getSellPrice()*Mideas.joueur1().getNumberItem(item, getSlotItem(item)));
				Mideas.setGold(item.getSellPrice()*Mideas.joueur1().getNumberItem(item, getSlotItem(item)));
				Mideas.joueur1().setNumberItem(item, 0);
			}
			else {
				Mideas.setGold(item.getSellPrice());
				LogChat.setStatusText3("Vous avez vendu "+item.getStuffName()+" pour "+item.getSellPrice());
			}
			return true;
		}
		return false;
	}
	public static boolean calcCoinContainer(int cost, int x, int y) throws FileNotFoundException {
		if(Mideas.calcGoldCoinCost(cost) > 0 && Mideas.calcSilverCoinCost(cost) > 0 && cost-Mideas.calcGoldCoinCost(cost)*10000-Mideas.calcSilverCoinCost(cost)*100 > 0) {
			TTF2.coinContainer.drawStringShadow(Display.getWidth()+x-30-TTF2.coinContainer.getWidth(String.valueOf(Mideas.calcGoldCoinCost(cost))+String.valueOf(Mideas.calcSilverCoinCost(cost))+String.valueOf(cost-Mideas.calcGoldCoinCost(cost)*10000-Mideas.calcSilverCoinCost(cost)*100)), Display.getHeight()/2+y, String.valueOf(Mideas.calcGoldCoinCost(cost)), Color.white, Color.black, 1, 1, 1);
			Draw.drawQuad(Sprites.gold_coin_container, Display.getWidth()+x-30-TTF2.coinContainer.getWidth(String.valueOf(Mideas.calcSilverCoinCost(cost))+String.valueOf(cost-Mideas.calcGoldCoinCost(cost)*10000-Mideas.calcSilverCoinCost(cost)*100)), Display.getHeight()/2+y);
			TTF2.coinContainer.drawStringShadow(Display.getWidth()+x-5-TTF2.coinContainer.getWidth(String.valueOf(Mideas.calcSilverCoinCost(cost))+String.valueOf(cost-Mideas.calcGoldCoinCost(cost)*10000-Mideas.calcSilverCoinCost(cost)*100)), Display.getHeight()/2+y, String.valueOf(Mideas.calcSilverCoinCost(cost)), Color.white, Color.black, 1, 1, 1);
			Draw.drawQuad(Sprites.silver_coin_container, Display.getWidth()+x-5-TTF2.coinContainer.getWidth(String.valueOf(cost-Mideas.calcGoldCoinCost(cost)*10000-Mideas.calcSilverCoinCost(cost)*100)), Display.getHeight()/2+y);
			TTF2.coinContainer.drawStringShadow(Display.getWidth()+x+20-TTF2.coinContainer.getWidth(String.valueOf(cost-Mideas.calcGoldCoinCost(cost)*10000-Mideas.calcSilverCoinCost(cost)*100)), Display.getHeight()/2+y, String.valueOf(cost-Mideas.calcGoldCoinCost(cost)*10000-Mideas.calcSilverCoinCost(cost)*100), Color.white, Color.black, 1, 1, 1);
			Draw.drawQuad(Sprites.copper_coin_container, Display.getWidth()+x+20, Display.getHeight()/2+y);
			return true;
		}
		if(Mideas.calcGoldCoinCost(cost) > 0 && Mideas.calcSilverCoinCost(cost) > 0 && cost-Mideas.calcGoldCoinCost(cost)*10000-Mideas.calcSilverCoinCost(cost)*100 <= 0) {
			TTF2.coinContainer.drawStringShadow(Display.getWidth()+x-5-TTF2.coinContainer.getWidth(String.valueOf(Mideas.calcGoldCoinCost(cost))+String.valueOf(Mideas.calcSilverCoinCost(cost))), Display.getHeight()/2+y, String.valueOf(Mideas.calcGoldCoinCost(cost)), Color.white, Color.black, 1, 1, 1);
			Draw.drawQuad(Sprites.gold_coin_container, Display.getWidth()+x-5-TTF2.coinContainer.getWidth(String.valueOf(Mideas.calcSilverCoinCost(cost))), Display.getHeight()/2+y);
			TTF2.coinContainer.drawStringShadow(Display.getWidth()+x+20-TTF2.coinContainer.getWidth(String.valueOf(Mideas.calcSilverCoinCost(cost))), Display.getHeight()/2+y, String.valueOf(Mideas.calcSilverCoinCost(cost)), Color.white, Color.black, 1, 1, 1);
			Draw.drawQuad(Sprites.silver_coin_container, Display.getWidth()+x+20, Display.getHeight()/2+y);
			return true;
		}
		if(Mideas.calcGoldCoinCost(cost) > 0 && Mideas.calcSilverCoinCost(cost) <= 0 && cost-Mideas.calcGoldCoinCost(cost)*10000-Mideas.calcSilverCoinCost(cost)*100 > 0) {
			TTF2.coinContainer.drawStringShadow(Display.getWidth()+x-5-TTF2.coinContainer.getWidth(String.valueOf(Mideas.calcGoldCoinCost(cost))+String.valueOf(cost-Mideas.calcGoldCoinCost(cost)*10000-Mideas.calcSilverCoinCost(cost)*100)), Display.getHeight()/2+y, String.valueOf(Mideas.calcGoldCoinCost(cost)), Color.white, Color.black, 1, 1, 1);
			Draw.drawQuad(Sprites.gold_coin_container, Display.getWidth()+x-5-TTF2.coinContainer.getWidth(String.valueOf(cost-Mideas.calcGoldCoinCost(cost)*10000-Mideas.calcSilverCoinCost(cost)*100)), Display.getHeight()/2+y);
			TTF2.coinContainer.drawStringShadow(Display.getWidth()+x+20-TTF2.coinContainer.getWidth(String.valueOf(cost-Mideas.calcGoldCoinCost(cost)*10000-Mideas.calcSilverCoinCost(cost)*100)), Display.getHeight()/2+y, String.valueOf(cost-Mideas.calcGoldCoinCost(cost)*10000-Mideas.calcSilverCoinCost(cost)*100), Color.white, Color.black, 1, 1, 1);
			Draw.drawQuad(Sprites.copper_coin_container, Display.getWidth()+x+20, Display.getHeight()/2+y);
			return true;
		}
		if(Mideas.calcGoldCoinCost(cost) <= 0 && Mideas.calcSilverCoinCost(cost) > 0 && cost-Mideas.calcGoldCoinCost(cost)*10000-Mideas.calcSilverCoinCost(cost)*100 > 0) {
			TTF2.coinContainer.drawStringShadow(Display.getWidth()+x-5-TTF2.coinContainer.getWidth(String.valueOf(Mideas.calcSilverCoinCost(cost))+String.valueOf(cost-Mideas.calcGoldCoinCost(cost)*10000-Mideas.calcSilverCoinCost(cost)*100)), Display.getHeight()/2+y, String.valueOf(Mideas.calcSilverCoinCost(cost)), Color.white, Color.black, 1, 1, 1);
			Draw.drawQuad(Sprites.silver_coin_container, Display.getWidth()+x-5-TTF2.coinContainer.getWidth(String.valueOf(cost-Mideas.calcGoldCoinCost(cost)*10000-Mideas.calcSilverCoinCost(cost)*100)), Display.getHeight()/2+y);
			TTF2.coinContainer.drawStringShadow(Display.getWidth()+x+20-TTF2.coinContainer.getWidth(String.valueOf(cost-Mideas.calcGoldCoinCost(cost)*10000-Mideas.calcSilverCoinCost(cost)*100)), Display.getHeight()/2+y, String.valueOf(cost-Mideas.calcGoldCoinCost(cost)*10000-Mideas.calcSilverCoinCost(cost)*100), Color.white, Color.black, 1, 1, 1);
			Draw.drawQuad(Sprites.copper_coin_container, Display.getWidth()+x+20, Display.getHeight()/2+y);
			return true;
		}
		if(Mideas.calcGoldCoinCost(cost) > 0 && Mideas.calcSilverCoinCost(cost) <= 0 && cost-Mideas.calcGoldCoinCost(cost)*10000-Mideas.calcSilverCoinCost(cost)*100 <= 0) {
			TTF2.coinContainer.drawStringShadow(Display.getWidth()+x+20-TTF2.coinContainer.getWidth(String.valueOf(Mideas.calcGoldCoinCost(cost))), Display.getHeight()/2+y, String.valueOf(Mideas.calcGoldCoinCost(cost)), Color.white, Color.black, 1, 1, 1);
			Draw.drawQuad(Sprites.gold_coin_container, Display.getWidth()+x+20, Display.getHeight()/2+y);
			return true;
		}
		if(Mideas.calcGoldCoinCost(cost) <= 0 && Mideas.calcSilverCoinCost(cost) > 0 && cost-Mideas.calcGoldCoinCost(cost)*10000-Mideas.calcSilverCoinCost(cost)*100 <= 0) {
			TTF2.coinContainer.drawStringShadow(Display.getWidth()+x+20-TTF2.coinContainer.getWidth(String.valueOf(Mideas.calcSilverCoinCost(cost))), Display.getHeight()/2+y, String.valueOf(Mideas.calcSilverCoinCost(cost)), Color.white, Color.black, 1, 1, 1);
			Draw.drawQuad(Sprites.silver_coin_container, Display.getWidth()+x+20, Display.getHeight()/2+y);
			return true;
		}
		if(Mideas.calcGoldCoinCost(cost) <= 0 && Mideas.calcSilverCoinCost(cost) <= 0 && cost-Mideas.calcGoldCoinCost(cost)*10000-Mideas.calcSilverCoinCost(cost)*100 > 0) {
			TTF2.coinContainer.drawStringShadow(Display.getWidth()+x+20-TTF2.coinContainer.getWidth(String.valueOf(cost-Mideas.calcGoldCoinCost(cost)*10000-Mideas.calcSilverCoinCost(cost)*100)), Display.getHeight()/2+y, String.valueOf(cost-Mideas.calcGoldCoinCost(cost)*10000-Mideas.calcSilverCoinCost(cost)*100), Color.white, Color.black, 1, 1, 1);
			Draw.drawQuad(Sprites.copper_coin_container, Display.getWidth()+x+20, Display.getHeight()/2+y);
			return true;
		}
		return true;
	}
	
	private static void slotHover(int x, int y, int i) {
		if(Mideas.mouseX() >= Display.getWidth()+x-3 && Mideas.mouseX() <= Display.getWidth()+x+38 && Mideas.mouseY() >= Display.getHeight()/2+y-3 && Mideas.mouseY() <= Display.getHeight()/2+y+38) {
			slot_hover[i] = true;
		}
	}
	
	public static boolean getContainerFrameSlotHover(int i) {
		return slot_hover[i];
	}
	
	public static boolean[] getContainerFrameSlotHover() {
		return slot_hover;
	}
	
	private static void clickSellItem(int i) throws FileNotFoundException, SQLTimeoutException, SQLException {
		if(sellItem(Mideas.bag().getBag(i), slot_hover[i])) {
			Mideas.bag().setBag(i, null);
		}
	}
	
	private static void drawBag(int i, int x, int y) {
		if(Mideas.bag().getBag(i) != null && !(Mideas.bag().getBag(i) == DragManager.getDraggedItem())) {
			Draw.drawQuad(IconsManager.getSprite35((Mideas.bag().getBag(i).getSpriteId())), Display.getWidth()+x, Display.getHeight()/2+y);
			Draw.drawQuad(Sprites.cursor, -200, -200);
			if(Mideas.bag().getBag(i).getItemType() == ItemType.ITEM && Mideas.joueur1().getNumberItem(Mideas.bag().getBag(i), getSlotItem(Mideas.bag().getBag(i))) <= 0) {
				Mideas.bag().setBag(i, null);
			}
		}
	}
	
	private static void drawHoverBag(int i, int x, int z, int a, int b) throws FileNotFoundException {
		if(slot_hover[i]) {
			if(Mideas.bag().getBag(i) != null && Mideas.bag().getBag(i).getItemType() == ItemType.STUFF) {
				Draw.drawColorQuad(Display.getWidth()+x-300, Display.getHeight()/2-80, 285, 80+TTF2.statsName.getLineHeight()*4, bgColor);
				TTF2.itemName.drawStringShadow(Display.getWidth()+x-180-TTF2.itemName.getWidth(Mideas.bag().getBag(i).getStuffName())/2, Display.getHeight()/2+y-20, Mideas.bag().getBag(i).getStuffName(), Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+z, "+"+((Stuff)Mideas.bag().getBag(i)).getArmor()+" Armor", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+20+z, "+"+((Stuff)Mideas.bag().getBag(i)).getStamina()+" Stamina", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+40+z, "+"+((Stuff)Mideas.bag().getBag(i)).getMana()+" Mana", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+60+z, "+"+((Stuff)Mideas.bag().getBag(i)).getStrength()+" Strengh", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+80+z, "+"+((Stuff)Mideas.bag().getBag(i)).getCritical()+" Critical", Color.white, Color.black, 1, 1, 1);
				ContainerFrame.calcCoinContainer(Mideas.bag().getBag(i).getSellPrice(), x-70, y+100);
				if(Interface.getShopFrameStatus()) {
					//Mideas.cursorFrame("sprite/interface/cursor_buy.png");
				}
			}
			Draw.drawQuad(Sprites.bag_hover, Display.getWidth()+x+a, Display.getHeight()/2+y+b);
		}
	}
	
	public static int getSlotItem(Item item) {
		int i = 0;
		while(i < Mideas.bag().getBag().length) {
			if(item != null && Mideas.bag().getBag(i) != null && item.getId() == Mideas.bag().getBag(i).getId()) {
				return i;
			}
			i++;
		}
		return -1;
	}
}