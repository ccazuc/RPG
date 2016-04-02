package com.mideas.rpg.v2.game;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;

import com.mideas.rpg.v2.Interface;
import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.Sprites;
import com.mideas.rpg.v2.TTF2;
import com.mideas.rpg.v2.game.item.Item;
import com.mideas.rpg.v2.game.item.ItemType;
import com.mideas.rpg.v2.game.item.potion.Potion;
import com.mideas.rpg.v2.game.item.potion.PotionManager;
import com.mideas.rpg.v2.game.item.stuff.Bag;
import com.mideas.rpg.v2.game.item.stuff.BagManager;
import com.mideas.rpg.v2.game.item.stuff.Stuff;
import com.mideas.rpg.v2.game.item.stuff.StuffManager;
import com.mideas.rpg.v2.hud.ContainerFrame;
import com.mideas.rpg.v2.hud.DragManager;
import com.mideas.rpg.v2.hud.LogChat;
import com.mideas.rpg.v2.jdo.JDOStatement;
import com.mideas.rpg.v2.utils.Draw;

public class ShopManager {
	
	private static boolean[] slot_hover = new boolean[12];
	private static boolean left_arrow;
	private static boolean right_arrow;
	private static boolean hover_button;
	private static int page;
	private static Color bgColor = new Color(0, 0, 0, .6f); 
	private static Color borderColor = Color.decode("#494D4B");

	private static ArrayList<Shop> shopList = new ArrayList<Shop>();
	
	public static void loadStuffs() throws SQLException, CloneNotSupportedException {
		JDOStatement statement = Mideas.getJDO().prepare("SELECT id, class, price FROM shop");
		statement.execute();
		while(statement.fetch()) {
			int id = statement.getInt();
			short classeTemp = statement.getShort();
			ClassType[] classeType = StuffManager.getClasses(classeTemp);
			int sellPrice = statement.getInt();
			shopList.add(new Shop(id, classeType, sellPrice));
		}
	}
	
	private static Item getItem(int id) {
		int i = 0;
		while(i < StuffManager.getStuffList().size()) {
			if(StuffManager.getStuffList().get(i).getId() == id) {
				return StuffManager.getStuffList().get(i);
			}
			i++;
		}
		i = 0;
		while(i < PotionManager.getPotionList().size()) {
			if(PotionManager.getPotionList().get(i).getId() == id) {
				return PotionManager.getPotionList().get(i);
			}
			i++;
		}
		i = 0;
		while(i < BagManager.getContainerList().size()) {
			if(BagManager.getContainerList().get(i).getId() == id) {
				return BagManager.getContainerList().get(i);
			}
			i++;
		}
		return null;
	}
	
	public static void draw() throws LWJGLException, IOException, SQLException {
		int xLeft = -279;
		int xRight = -114;
		int y = -275;
		int yShift = 52;
		Draw.drawQuad(Sprites.shop_frame, Display.getWidth()/2-300, Display.getHeight()/2-350);
		int i = 0;
		int j = 0;
		int x = xLeft;
		int z = -300;
		while(i < 10) {
			drawShopItem(i, x, y+j*yShift);
			shopHover(i, x+z, y+j*yShift, x, y+j*yShift);
			drawGoldCoin(i, x, y+j*yShift);
			i++;
			j++;
			if(i%5 == 0) {
				j = 0;
			}
			if(i == 5) {
				x = xRight;
				z = 165;
			}
		}
		/*drawShopItem(0, xLeft, y);
		drawShopItem(1, xLeft, y+yShift);
		drawShopItem(2, xLeft, y+2*yShift);
		drawShopItem(3, xLeft, y+3*yShift);
		drawShopItem(4, xLeft, y+4*yShift);
		drawShopItem(5, xRight, y);
		drawShopItem(6, xRight, y+yShift);
		drawShopItem(7, xRight, y+2*yShift);
		drawShopItem(8, xRight, y+3*yShift);
		drawShopItem(9, xRight, y+4*yShift);
		shopHover(0, xLeft, y);
		shopHover(1, xLeft, y+yShift);
		shopHover(2, xLeft, y+2*yShift);
		shopHover(3, xLeft, y+3*yShift);
		shopHover(4, xLeft, y+4*yShift);
		shopHover(5, xRight, y);
		shopHover(6, xRight, y+yShift);
		shopHover(7, xRight, y+2*yShift);
		shopHover(8, xRight, y+3*yShift);
		shopHover(9, xRight, y+4*yShift);
		drawGoldCoin(0, xLeft, y);
		drawGoldCoin(1, xLeft, y+yShift);
		drawGoldCoin(2, xLeft, y+2*yShift);
		drawGoldCoin(3, xLeft, y+3*yShift);
		drawGoldCoin(4, xLeft, y+4*yShift);
		drawGoldCoin(5, xRight, y);
		drawGoldCoin(6, xRight, y+yShift);
		drawGoldCoin(7, xRight, y+2*yShift);
		drawGoldCoin(8, xRight, y+3*yShift);
		drawGoldCoin(9, xRight, y+4*yShift);*/
		calcCoin(Mideas.joueur1().getGold(), xRight, y+250);
		if(hover_button) {
			Draw.drawQuad(Sprites.close_shop_hover, Display.getWidth()/2+27, Display.getHeight()/2-337);
		}
		if(page != 0) {
			Draw.drawQuad(Sprites.left_colored_arrow, Display.getWidth()/2+xLeft+3, Display.getHeight()/2+y+268);
		}
		if(page != 2) {
			Draw.drawQuad(Sprites.right_colored_arrow, Display.getWidth()/2+xRight+125, Display.getHeight()/2+y+268);
		}
		if(right_arrow && page != 2) {
			Draw.drawQuad(Sprites.right_arrow_hover, Display.getWidth()/2+xRight+125, Display.getHeight()/2+y+268);
		}
		if(left_arrow && page != 0) {
			Draw.drawQuad(Sprites.left_arrow_hover, Display.getWidth()/2+xLeft+3, Display.getHeight()/2+y+268);
		}
		if(page == 2) {
			Draw.drawQuad(Sprites.right_uncolored_arrow, Display.getWidth()/2+xRight+125, Display.getHeight()/2+y+268);
		}
	}
	
	public static boolean mouseEvent() throws FileNotFoundException, SQLException {
		Arrays.fill(slot_hover, false);
		right_arrow = false;
		left_arrow = false;
		hover_button = false;
		int xLeft = -279;
		int xRight = -114;
		int y = -275;
		if(Mideas.mouseX() >= Display.getWidth()/2+xRight+126 && Mideas.mouseX() <= Display.getWidth()/2+xRight+151 && Mideas.mouseY() >= Display.getHeight()/2+y+266 && Mideas.mouseY() <= Display.getHeight()/2+y+292) {
			right_arrow = true;
		}
		else if(Mideas.mouseX() >= Display.getWidth()/2+xRight-161 && Mideas.mouseX() <= Display.getWidth()/2+xRight-136 && Mideas.mouseY() >= Display.getHeight()/2+y+266 && Mideas.mouseY() <= Display.getHeight()/2+y+292) {
			left_arrow = true;
		}
		if(Mideas.mouseX() >= Display.getWidth()/2+27 && Mideas.mouseX() <= Display.getWidth()/2+46 && Mideas.mouseY() >= Display.getHeight()/2-337 && Mideas.mouseY() <= Display.getHeight()/2-319) {
			hover_button = true;
		}
		if(Mouse.getEventButtonState()) {
			if(Mideas.mouseX() >= Display.getWidth()/2+27 && Mideas.mouseX() <= Display.getWidth()/2+46 && Mideas.mouseY() >= Display.getHeight()/2-337 && Mideas.mouseY() <= Display.getHeight()/2-319) {
				Interface.closeShopFrame();
				return true;
			}
			else if(page == 0 && right_arrow) {
				page++;
			}
			else if(page == 1 && right_arrow) {
				page++;
			}
			else if(page == 1 && left_arrow) {
				page--;
			}
			else if(page == 2 && left_arrow) {
				page--;
			}	
		}
		isSlotHover(xLeft, y, 0, 41, 0);
		isSlotHover(xLeft, y, 52, 93, 1);
		isSlotHover(xLeft, y, 104, 145, 2);
		isSlotHover(xLeft, y, 156, 197, 3);
		isSlotHover(xLeft, y, 208, 249, 4);
		isSlotHover(xRight, y, 0, 41, 5);
		isSlotHover(xRight, y, 52, 93, 6);
		isSlotHover(xRight, y, 104, 145, 7);
		isSlotHover(xRight, y, 156, 197, 8);
		isSlotHover(xRight, y, 208, 249, 9);
		int i = 0;
		while(i < 10 && i+10*page < shopList.size()) {
			buyItems(slot_hover[i], shopList.get(i+10*page));
			i++;
		}
		return false;
	}
	
	public static void buyItems(boolean slot_hover, Shop stuff) throws FileNotFoundException, SQLException {
		if(Mouse.getEventButton() == 1 && slot_hover && stuff != null) {
			if(Mouse.getEventButtonState()) {
			}
			else {
				if(Mideas.getCurrentGold() >= stuff.getSellPrice()) {
					/*if(stuff.get) {
						EndFightFrame.dropItem(stuff, 1);
						LogChat.setStatusText3("Vous avez bien achet� "+stuff.getStuffName());
						Mideas.setGold(-stuff.getSellPrice());
					}*/
					if(checkInventory(stuff)) {
						Mideas.setGold(-stuff.getSellPrice());
						LogChat.setStatusText3("Vous avez bien achet� "+StuffManager.getStuff(stuff.getId()).getStuffName());
					}
				}
				else {
					LogChat.setStatusText3("Vous n'avez pas assez d'argent");
				}
			}
		}
	}

	private static boolean checkInventory(Shop item) throws FileNotFoundException, SQLException {
		int i = 0;
		while(i < Mideas.bag().getBag().length) {
			if(Mideas.bag().getBag(i) == null) {
				Mideas.bag().setBag(i, StuffManager.getClone(item.getId()));
				CharacterStuff.setBagItems();
				return true;
			}
			i++;
		}
		LogChat.setStatusText3("Votre inventaire est pleins");
		return false;
	}

	private static void drawShopItem(int i, int x, int y) {
		if(i+10*page < shopList.size()) {
			Draw.drawQuad(IconsManager.getSprite35(getItem(shopList.get(i+10*page).getId()).getSpriteId()), Display.getWidth()/2+x+3, Display.getHeight()/2+y+3);
			Draw.drawQuad(Sprites.shop_border, Display.getWidth()/2+x-1, Display.getHeight()/2+y);
		}
	}
	
	private static void shopHover(int i, int x_item, int y_item, int x_hover, int y_hover) throws FileNotFoundException, SQLException {
		if(slot_hover[i]) {
			if(i+10*page < shopList.size()) {
				int shift = 40;
				Item item = getItem(shopList.get(i+10*page).getId());
				if(item.getItemType() == ItemType.STUFF) {
					Color temp = null;
					Draw.drawColorQuad(Display.getWidth()/2+x_item, Display.getHeight()/2+y_item, 285, 100+TTF2.statsName.getLineHeight()*ContainerFrame.getNumberStats((Stuff)item), bgColor);
					Draw.drawColorQuadBorder(Display.getWidth()/2+x_item, Display.getHeight()/2+y_item, 287, 100+TTF2.statsName.getLineHeight()*ContainerFrame.getNumberStats((Stuff)item), borderColor);
					TTF2.itemName.drawStringShadow(Display.getWidth()/2+x_item+10, Display.getHeight()/2+y_item, Mideas.bag().getBag(i).getStuffName(), ContainerFrame.getItemNameColor(item), Color.black, 1, 1, 1);
					TTF2.statsName.drawStringShadow(Display.getWidth()/2+x_item+10, Display.getHeight()/2+y_item+25, ((Stuff)item).convStuffTypeToString(), Color.white, Color.black, 1, 1, 1);
					if(DragManager.canWear((Stuff)item)) {
						temp = Color.white;
					}
					else {
						temp = Color.red;
					}
					TTF2.statsName.drawStringShadow(Display.getWidth()/2+275+x_item-TTF2.font4.getWidth(((Stuff)item).convWearToString()), Display.getHeight()/2+y_item+20, ((Stuff)item).convWearToString(), temp, Color.black, 1, 1, 1);
					if(((Stuff)item).getArmor() > 0) {
						TTF2.statsName.drawStringShadow(Display.getWidth()/2+10+x_item, Display.getHeight()/2+y_item+shift, "Armor : "+((Stuff)item).getArmor(), Color.white, Color.black, 1, 1, 1);
						shift+= 20;
					}
					if(((Stuff)item).getStrength() > 0) {
						TTF2.statsName.drawStringShadow(Display.getWidth()/2+10+x_item, Display.getHeight()/2+y_item+shift, "+ "+((Stuff)item).getStrength()+" Strengh", Color.white, Color.black, 1, 1, 1);
						shift+= 20;
					}
					if(((Stuff)item).getMana() > 0) {
						TTF2.statsName.drawStringShadow(Display.getWidth()/2+10+x_item, Display.getHeight()/2+y_item+shift, "+ "+((Stuff)item).getMana()+" Mana", Color.white, Color.black, 1, 1, 1);
						shift+= 20;
					}
					if(((Stuff)item).getStamina() > 0) {
						TTF2.statsName.drawStringShadow(Display.getWidth()/2+10+x_item, Display.getHeight()/2+y_item+shift, "+ "+((Stuff)item).getStamina()+" Stamina", Color.white, Color.black, 1, 1, 1);
						shift+= 20;
					}
					if(((Stuff)item).getCritical() > 0) {
						TTF2.statsName.drawStringShadow(Display.getWidth()/2+10+x_item, Display.getHeight()/2+y_item+shift, "+ "+((Stuff)item).getCritical()+" Critical", Color.white, Color.black, 1, 1, 1);
						shift+= 20;
					}
					if(((Stuff)item).canEquipTo(DragManager.convClassType())) {
						temp = Color.white;
					}
					else {
						temp = Color.red;
					}
					if(((Stuff)item).getClassType().length < 10) {
						TTF2.statsName.drawStringShadow(Display.getWidth()/2+10+x_item, Display.getHeight()/2+y_item+shift, "Classes: ", temp, Color.black, 1, 1, 1);
						int k = 0;
						int xShift = 0;
						while(k < ((Stuff)item).getClassType().length) {
							if(k == ((Stuff)item).getClassType().length-1) {
								TTF2.statsName.drawStringShadow(Display.getWidth()/2+10+x_item+xShift+TTF2.font4.getWidth("Classes : "), Display.getHeight()/2+y_item+shift, ((Stuff)item).convClassTypeToString(k), temp, Color.black, 1, 1, 1);
							}
							else {
								TTF2.statsName.drawStringShadow(Display.getWidth()/2+10+x_item+xShift+TTF2.font4.getWidth("Classes : "), Display.getHeight()/2+y_item+shift, ((Stuff)item).convClassTypeToString(k)+", ", temp, Color.black, 1, 1, 1);
							}
							xShift+= TTF2.font4.getWidth(((Stuff)item).convClassTypeToString(k)+", ");
							k++;
						}
						shift+= 20;
					}
					if(Mideas.getLevel() >= ((Stuff)item).getLevel()) {
						TTF2.statsName.drawStringShadow(Display.getWidth()/2+10+x_item, Display.getHeight()/2+y_item+shift, "Level "+((Stuff)item).getLevel()+" required", Color.white, Color.black, 1, 1, 1);
					}
					else {
						TTF2.statsName.drawStringShadow(Display.getWidth()/2+10+x_item, Display.getHeight()/2+y_item+shift, "Level "+((Stuff)item).getLevel()+" required", Color.red, Color.black, 1, 1, 1);
					}
				}
				else if(item.getItemType() == ItemType.POTION) {
					Draw.drawColorQuad(Display.getWidth()/2+x_item, Display.getHeight()/2+y_item, 285, 40+TTF2.statsName.getLineHeight()*3, bgColor);
					Draw.drawColorQuadBorder(Display.getWidth()/2+x_item, Display.getHeight()/2+y_item, 287, 40+TTF2.statsName.getLineHeight()*3, borderColor);
					TTF2.itemName.drawStringShadow(Display.getWidth()/2+x_item, Display.getHeight()/2+y_item, item.getStuffName(), ContainerFrame.getItemNameColor(item), Color.black, 1, 1, 1);
					if(((Potion)item).getPotionHeal() > 0) {
						TTF2.statsName.drawStringShadow(Display.getWidth()/2+10+x_item, Display.getHeight()/2+y_item+shift, "Restores "+((Potion)item).getPotionHeal()+" Hp", Color.green, Color.black, 1, 1, 1);
						shift+= 20;
					}
					if(((Potion)item).getPotionMana() > 0) {
						TTF2.statsName.drawStringShadow(Display.getWidth()/2+10+x_item, Display.getHeight()/2+y_item+shift, "Restores "+((Potion)item).getPotionMana()+" Mana", Color.green, Color.black, 1, 1, 1);
					}
				}
				else if(item.getItemType() == ItemType.BAG) {
					Draw.drawColorQuad(Display.getWidth()/2+x_item, Display.getHeight()/2+y_item, 285, 40+TTF2.statsName.getLineHeight(), bgColor);
					Draw.drawColorQuadBorder(Display.getWidth()/2+x_item, Display.getHeight()/2+y_item, 287, 40+TTF2.statsName.getLineHeight(), borderColor);
					TTF2.itemName.drawStringShadow(Display.getWidth()/2+x_item, Display.getHeight()/2+y_item, item.getStuffName(), ContainerFrame.getItemNameColor(item), Color.black, 1, 1, 1);
					TTF2.itemName.drawStringShadow(Display.getWidth()/2+x_item, Display.getHeight()/2+y_item+20, "Container "+((Bag)item).getSize()+" slots", Color.white, Color.black, 1, 1, 1);
				}
			}
			Draw.drawQuad(Sprites.shop_hover, Display.getWidth()/2+x_hover, Display.getHeight()/2+y_hover);
		}
	}
	
	public static void isSlotHover(int x, int y, int i, int j, int k) {
		if(Mideas.mouseX() >= Display.getWidth()/2+x && Mideas.mouseX() <= Display.getWidth()/2+x+42 && Mideas.mouseY() >= Display.getHeight()/2+y+i && Mideas.mouseY() <= Display.getHeight()/2+y+j) {
			slot_hover[k] = true;
		}
	}

	private static void drawGoldCoin(int i, int x, int y) throws FileNotFoundException {
		if(i+10*page < shopList.size()) {
			calcCoin(shopList.get(i+10*page).getSellPrice(), x+49, y+35);
			Draw.drawQuad(Sprites.cursor, -100, -100);
		}
	}
	
	public static boolean calcCoin(int cost, int x, int y) throws FileNotFoundException {
		if(Mideas.calcGoldCoinCost(cost) > 0 && Mideas.calcSilverCoinCost(cost) > 0 && cost-Mideas.calcGoldCoinCost(cost)*10000-Mideas.calcSilverCoinCost(cost)*100 > 0) {
			TTF2.coin.drawStringShadow(Display.getWidth()/2+x, Display.getHeight()/2+y, String.valueOf(Mideas.calcGoldCoinCost(cost)), Color.white, Color.black, 1, 1, 1);
			Draw.drawQuad(Sprites.gold_coin, Display.getWidth()/2+x+1+TTF2.coin.getWidth(String.valueOf(Mideas.calcGoldCoinCost(cost))), Display.getHeight()/2+y);
			TTF2.coin.drawStringShadow(Display.getWidth()/2+x+20+TTF2.coin.getWidth(String.valueOf(Mideas.calcGoldCoinCost(cost))), Display.getHeight()/2+y, String.valueOf(Mideas.calcSilverCoinCost(cost)), Color.white, Color.black, 1, 1, 1);
			Draw.drawQuad(Sprites.silver_coin, Display.getWidth()/2+x+21+TTF2.coin.getWidth(String.valueOf(Mideas.calcGoldCoinCost(cost))+String.valueOf(Mideas.calcSilverCoinCost(cost))), Display.getHeight()/2+y);
			TTF2.coin.drawStringShadow(Display.getWidth()/2+x+48+TTF2.coin.getWidth(String.valueOf(Mideas.calcGoldCoinCost(cost)+Mideas.calcSilverCoinCost(cost))), Display.getHeight()/2+y, String.valueOf(cost-Mideas.calcGoldCoinCost(cost)*10000-Mideas.calcSilverCoinCost(cost)*100), Color.white, Color.black, 1, 1, 1);
			Draw.drawQuad(Sprites.copper_coin, Display.getWidth()/2+x+38+TTF2.coin.getWidth(String.valueOf(Mideas.calcGoldCoinCost(cost))+String.valueOf(Mideas.calcSilverCoinCost(cost))+String.valueOf(cost-Mideas.calcGoldCoinCost(cost)*10000-Mideas.calcSilverCoinCost(cost)*100)), Display.getHeight()/2+y);
			return true;
		}
		if(Mideas.calcGoldCoinCost(cost) > 0 && Mideas.calcSilverCoinCost(cost) > 0 && cost-Mideas.calcGoldCoinCost(cost)*10000-Mideas.calcSilverCoinCost(cost)*100 <= 0) {
			TTF2.coin.drawStringShadow(Display.getWidth()/2+x, Display.getHeight()/2+y, String.valueOf(Mideas.calcGoldCoinCost(cost)), Color.white, Color.black, 1, 1, 1);
			Draw.drawQuad(Sprites.gold_coin, Display.getWidth()/2+x+1+TTF2.coin.getWidth(String.valueOf(Mideas.calcGoldCoinCost(cost))), Display.getHeight()/2+y);
			TTF2.coin.drawStringShadow(Display.getWidth()/2+x+20+TTF2.coin.getWidth(String.valueOf(Mideas.calcGoldCoinCost(cost))), Display.getHeight()/2+y, String.valueOf(Mideas.calcSilverCoinCost(cost)), Color.white, Color.black, 1, 1, 1);
			Draw.drawQuad(Sprites.silver_coin, Display.getWidth()/2+x+21+TTF2.coin.getWidth(String.valueOf(Mideas.calcGoldCoinCost(cost))+String.valueOf(Mideas.calcSilverCoinCost(cost))), Display.getHeight()/2+y);
			return true;
		}
		if(Mideas.calcGoldCoinCost(cost) > 0 && Mideas.calcSilverCoinCost(cost) <= 0 && cost-Mideas.calcGoldCoinCost(cost)*10000-Mideas.calcSilverCoinCost(cost)*100 > 0) {
			TTF2.coin.drawStringShadow(Display.getWidth()/2+x, Display.getHeight()/2+y, String.valueOf(Mideas.calcGoldCoinCost(cost)), Color.white, Color.black, 1, 1, 1);
			Draw.drawQuad(Sprites.gold_coin, Display.getWidth()/2+x+1+TTF2.coin.getWidth(String.valueOf(Mideas.calcGoldCoinCost(cost))), Display.getHeight()/2+y);
			TTF2.coin.drawStringShadow(Display.getWidth()/2+x+20+TTF2.coin.getWidth(String.valueOf(Mideas.calcGoldCoinCost(cost))), Display.getHeight()/2+y, String.valueOf(cost-Mideas.calcGoldCoinCost(cost)*10000-Mideas.calcSilverCoinCost(cost)*100), Color.white, Color.black, 1, 1, 1);
			Draw.drawQuad(Sprites.copper_coin, Display.getWidth()/2+x+21+TTF2.coin.getWidth(String.valueOf(Mideas.calcGoldCoinCost(cost))+String.valueOf(cost-Mideas.calcGoldCoinCost(cost)*10000-Mideas.calcSilverCoinCost(cost)*100)), Display.getHeight()/2+y);
			return true;
		}
		if(Mideas.calcGoldCoinCost(cost) <= 0 && Mideas.calcSilverCoinCost(cost) > 0 && cost-Mideas.calcGoldCoinCost(cost)*10000-Mideas.calcSilverCoinCost(cost)*100 > 0) {
			TTF2.coin.drawStringShadow(Display.getWidth()/2+x, Display.getHeight()/2+y, String.valueOf(Mideas.calcSilverCoinCost(cost)), Color.white, Color.black, 1, 1, 1);
			Draw.drawQuad(Sprites.silver_coin, Display.getWidth()/2+x+1+TTF2.coin.getWidth(String.valueOf(Mideas.calcSilverCoinCost(cost))), Display.getHeight()/2+y);
			TTF2.coin.drawStringShadow(Display.getWidth()/2+x+20+TTF2.coin.getWidth(String.valueOf(Mideas.calcSilverCoinCost(cost))), Display.getHeight()/2+y, String.valueOf(cost-Mideas.calcGoldCoinCost(cost)*10000-Mideas.calcSilverCoinCost(cost)*100), Color.white, Color.black, 1, 1, 1);
			Draw.drawQuad(Sprites.copper_coin, Display.getWidth()/2+x+21+TTF2.coin.getWidth(String.valueOf(Mideas.calcSilverCoinCost(cost))+String.valueOf(cost-Mideas.calcGoldCoinCost(cost)*10000-Mideas.calcSilverCoinCost(cost)*100)), Display.getHeight()/2+y);
			return true;
		}
		if(Mideas.calcGoldCoinCost(cost) > 0 && Mideas.calcSilverCoinCost(cost) <= 0 && cost-Mideas.calcGoldCoinCost(cost)*10000-Mideas.calcSilverCoinCost(cost)*100 <= 0) {
			TTF2.coin.drawStringShadow(Display.getWidth()/2+x, Display.getHeight()/2+y, String.valueOf(Mideas.calcGoldCoinCost(cost)), Color.white, Color.black, 1, 1, 1);
			Draw.drawQuad(Sprites.gold_coin, Display.getWidth()/2+x+1+TTF2.coin.getWidth(String.valueOf(Mideas.calcGoldCoinCost(cost))), Display.getHeight()/2+y);
			return true;
		}
		if(Mideas.calcGoldCoinCost(cost) <= 0 && Mideas.calcSilverCoinCost(cost) > 0 && cost-Mideas.calcGoldCoinCost(cost)*10000-Mideas.calcSilverCoinCost(cost)*100 <= 0) {
			TTF2.coin.drawStringShadow(Display.getWidth()/2+x, Display.getHeight()/2+y, String.valueOf(Mideas.calcSilverCoinCost(cost)), Color.white, Color.black, 1, 1, 1);
			Draw.drawQuad(Sprites.silver_coin, Display.getWidth()/2+x+1+TTF2.coin.getWidth(String.valueOf(Mideas.calcSilverCoinCost(cost))), Display.getHeight()/2+y);
			return true;
		}
		if(Mideas.calcGoldCoinCost(cost) <= 0 && Mideas.calcSilverCoinCost(cost) <= 0 && cost-Mideas.calcGoldCoinCost(cost)*10000-Mideas.calcSilverCoinCost(cost)*100 > 0) {
			TTF2.coin.drawStringShadow(Display.getWidth()/2+x, Display.getHeight()/2+y, String.valueOf(cost-Mideas.calcGoldCoinCost(cost)*10000-Mideas.calcSilverCoinCost(cost)*100), Color.white, Color.black, 1, 1, 1);
			Draw.drawQuad(Sprites.copper_coin, Display.getWidth()/2+x+1+TTF2.coin.getWidth(String.valueOf(cost-Mideas.calcGoldCoinCost(cost)*10000-Mideas.calcSilverCoinCost(cost)*100)), Display.getHeight()/2+y);
			return true;
		}
		return true;
	}
	
	public static ArrayList<Shop> getShopList() {
		return shopList;
	}
}
