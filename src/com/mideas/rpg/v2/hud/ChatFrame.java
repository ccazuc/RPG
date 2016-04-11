package com.mideas.rpg.v2.hud;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;

import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.Sprites;
import com.mideas.rpg.v2.TTF2;
import com.mideas.rpg.v2.game.CharacterStuff;
import com.mideas.rpg.v2.game.ShopManager;
import com.mideas.rpg.v2.game.classes.ClassManager;
import com.mideas.rpg.v2.game.item.potion.PotionManager;
import com.mideas.rpg.v2.game.item.stuff.StuffManager;
import com.mideas.rpg.v2.game.item.stuff.WeaponManager;
import com.mideas.rpg.v2.game.spell.SpellBarManager;
import com.mideas.rpg.v2.game.spell.SpellManager;
import com.mideas.rpg.v2.utils.Draw;

public class ChatFrame {
	
	private static boolean chatActive;
	private static ArrayList<String> messages = new ArrayList<String>();
	private static ArrayList<String> rawMessages = new ArrayList<String>();
	private static int numberMessageSent;
	private static String tempMessage = "";
	private static String[] tempTable = new String[30];
	private static int i;
	private static int numberUpArrow = 1;
	private static int cursorPosition;
	private static boolean topArrow;
	private static boolean bottomArrow;
	private static boolean toBottomArrow;
	private static boolean hoverHeightResize;
	private static boolean heightResizing;
	private static int defaultHeight = Display.getHeight()-285;
	private static int yResize;
	private static boolean hoverWidthResize;
	private static boolean widthResizing;
	private static boolean hoverAllResize;
	private static boolean allResizing;
	private static int defaultWidth = 540;
	private static int xResize;
	private static int messageShowHeight = 4;
	private static int tempLength;
	private static int shift;
	private static int cursorShift;
	private static int maxLength = 490;
	private static Color bgColor = new Color(0, 0, 0,.35f); 

	public static void draw() {
		messageShowHeight = 4+yResize/TTF2.font4.getLineHeight();
		maxLength = 490+xResize;
		Draw.drawColorQuad(30, Display.getHeight()-280-yResize, 510+xResize, 130+yResize, bgColor);
		Draw.drawQuad(Sprites.chat_button, 3, Display.getHeight()-268);
		if(chatActive) {
			if(TTF2.font4.getWidth(tempMessage.substring(tempLength)) >= maxLength) {
				tempLength = tempMessage.length()-10;
				cursorPosition = tempMessage.substring(tempLength).length();
				cursorShift = TTF2.font4.getWidth(tempMessage.substring(tempLength));
			}
			if(tempLength != 0 && tempMessage.substring(tempLength).equals("")) {
				tempLength-= getLength(tempMessage, 0, maxLength-130);
				cursorPosition = tempMessage.substring(tempLength).length();
				cursorShift = TTF2.font4.getWidth(tempMessage.substring(tempLength));
			}
			if(System.currentTimeMillis()%1000 < 500) {
				TTF2.font4.drawString(37+cursorShift, Display.getHeight()-175, "|", Color.white);
				//TTF2.font4.drawString(37+cursorShift+TTF2.font4.getWidth(tempMessage.substring(tempLength)), Display.getHeight()-175, "|", Color.white);
			}
		}
		TTF2.font4.drawString(40, Display.getHeight()-175, tempMessage.substring(tempLength), Color.white);
		int j = 0;
		/*if(messages.size() <= messageShowHeight) {
			int k = messages.size()-1;
			while(k >= 0) {
				TTF2.font4.drawString(40, Display.getHeight()-175-TTF2.font4.getLineHeight()*(j+1), messages.get(k+shift), Color.white);
				k--;
				j++;
			}
		}
		else if(messages.size() >= messageShowHeight+1) {*/
			int k = messages.size()-1;
			String draw = "";
			int numberLineSent = 0;
			int beg = 0;
			while(k > messages.size()-messageShowHeight-2 && numberLineSent < messageShowHeight+1 && k+shift < messages.size() && k+shift >= 0) {
				beg = 0;
				if(TTF2.font4.getWidth(messages.get(k+shift)) >= maxLength) {
					String temp = messages.get(k+shift);
					//System.out.println(temp);
					draw = temp;
					/*String temp = messages.get(k+shift);
					draw = temp;
					l = 0;
					while(l <= getNumberLine(temp)) {
						if(TTF2.font4.getWidth(draw) > maxLength-10){
							draw = temp.substring(0, getLength(temp, maxLength-10));
						}
						else {
							//temp = temp.substring(getLength(temp, maxLength-10));
							draw = temp.substring(getLength(temp, maxLength-10));
						}
						TTF2.font4.drawString(40, Display.getHeight()-175-TTF2.font4.getLineHeight()*(j+z+1), draw, Color.white);
						z++;
						l++;
						System.out.println("z : "+z+" draw : "+draw+" temp : "+temp+" numberline : "+getNumberLine(temp));
						//System.out.println(l+" line : "+getNumberLine(temp));
					}*/
					//length = getLength(draw, 0, maxLength-30);
					//draw = temp.substring(0, length);
					//System.out.println(length+" draw : ");
					//System.out.println(draw+" length : "+length+" total length : "+draw.length());
					/*if(!draw.equals("") && numberLineSent < messageShowHeight+1) {
						//System.out.println(draw+" length : "+getLength(draw, maxLength-10)+" total length : "+draw.length());
						TTF2.font4.drawString(40, Display.getHeight()-175-TTF2.font4.getLineHeight()*(j+1), draw, Color.white);
						j++;
						numberLineSent++;
					}
					draw = temp;
					beg = getLength(draw, beg, maxLength-30);*/
					int x = 0;
					while(x < getNumberLine(temp) && numberLineSent < messageShowHeight+1/*TTF2.font4.getWidth(draw) >= maxLength && numberLineSent < messageShowHeight+1*/) {
						//length = getLength(draw, beg, maxLength-30);
						draw = temp.substring(beg, getLength(temp, beg, maxLength-30));
						beg = getLength(draw, beg, maxLength-30);
						//System.out.println(draw+" length : "+getLength(draw, maxLength-10)+" total length : "+draw.length()+" font : "+TTF2.font4.getWidth(temp)+" maxL : "+maxLength);
						//TTF2.font4.drawString(40, Display.getHeight()-175-TTF2.font4.getLineHeight()*(j+1), draw, Color.white);
						//j++;
						//k--;
						x++;
						//numberLineSent++;
						tempTable[x] = draw;
						//System.out.println(draw);
						//System.out.println(draw+" x : "+x);
					}
					int i = 0;
					while(i < x && i < messageShowHeight+1) {
						if(numberLineSent < messageShowHeight+1) {
							TTF2.font4.drawString(40, Display.getHeight()-175-TTF2.font4.getLineHeight()*(j+1), tempTable[x-i], Color.white);
							j++;
							numberLineSent++;
						}
						i++;
					}
				}
				else {
					TTF2.font4.drawString(40, Display.getHeight()-175-TTF2.font4.getLineHeight()*(j+1), messages.get(k+shift), Color.white);
					j++;
					numberLineSent++;
				}
				k--;
			}
		//}
		if(topArrow) {
			Draw.drawQuad(Sprites.up_chat_button, 3, Display.getHeight()-236);
		}
	}
	
	public static void event() throws FileNotFoundException, SQLException, CloneNotSupportedException {
		if(chatActive) {
			if(Keyboard.getEventKey() == 1) { //escape
				chatActive = false;
			}
			else if(Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) { //ctrl down
				if(Keyboard.getEventKey() == 14) { //delete
					delMessage();
					tempLength = 0;
				}
				else if(Keyboard.getEventKey() == Keyboard.KEY_V) { // c/c
	                write(Sys.getClipboard().replace("\n", "").replace("\t", "").replace("\r", ""));
	                cursorPosition+= Sys.getClipboard().replace("\n", "").replace("\t", "").replace("\r", "").length();
	            }
				else if(Keyboard.getEventKey() == 203) { //left arrow
					leftArrow();
				}
				else if(Keyboard.getEventKey() == 205) { //right arrow
					rightArrow();
				}
			}
			else if(Keyboard.getEventKey() == 14) { //delete
				if(tempMessage.length() > 0) {
					cursorShift-= TTF2.font4.getWidth(tempMessage.substring(cursorPosition-1).charAt(0));
					String beg = tempMessage.substring(0, cursorPosition-1);
					String end = tempMessage.substring(cursorPosition);
					tempMessage = beg+end;
					cursorPosition--;
				}
			}
			else if(Keyboard.getEventKey() == 200) {  //up arrow
				if(i >= numberUpArrow) {
					tempMessage = rawMessages.get(numberMessageSent-numberUpArrow);
					numberUpArrow++;
					cursorPosition = tempMessage.length();
					cursorShift = TTF2.font4.getWidth(tempMessage);
				}
			}
			else if(Keyboard.getEventKey() == 208) { //down arrow
				if(numberUpArrow > 1) {
					numberUpArrow--;
					tempMessage = rawMessages.get(numberMessageSent-numberUpArrow);
				}
			}
			else if(Keyboard.getEventKey() == 203) { //left arrow
				if(cursorPosition > 0) {
					cursorPosition--;
					if(cursorPosition == 0) {
						cursorShift-= TTF2.font4.getWidth(tempMessage.substring(0, 1));
					}
					else {
						cursorShift-= TTF2.font4.getWidth(tempMessage.substring(cursorPosition, cursorPosition+1));
					}
				}
			}
			else if(Keyboard.getEventKey() == 205) { //right arrow
				if(cursorPosition < tempMessage.length()) {
					cursorPosition++;
					if(cursorPosition == tempMessage.length()) {
						cursorShift+= TTF2.font4.getWidth(tempMessage.substring(cursorPosition-1));
					}
					else {
						cursorShift+= TTF2.font4.getWidth(tempMessage.substring(cursorPosition-1, cursorPosition));
					}
				}
			}
			else if(Keyboard.getEventKey() == 211) { //suppr
				if(cursorPosition < tempMessage.length()) {
					String beg = tempMessage.substring(0, cursorPosition);
					String end = tempMessage.substring(cursorPosition+1);
					tempMessage = beg+end;
				}
			}
			else if(Keyboard.getEventKey() != Keyboard.KEY_RETURN && Keyboard.getEventKey() != 156) { //write
				char tempChar = Keyboard.getEventCharacter();
				write(tempChar);
				cursorPosition++;
			}
		}
        if(Keyboard.getEventKey() == Keyboard.KEY_RETURN || Keyboard.getEventKey() == 156) { //enter
			if(chatActive) {
				if(tempMessage != "") {
					rawMessages.add(tempMessage);
					if(!checkTempMessage()) {
						addMessage();
					}
					numberMessageSent++;
					cursorPosition = 0;
					cursorShift = 0;
				}
				tempMessage =  "";
				tempLength = 0;
				numberUpArrow = 1;
				i++;
			}
			chatActive = !chatActive;
		}
        else if(Keyboard.getEventKey() == 201 && !chatActive) {  //scroll up
			if(messages.size()-messageShowHeight+shift > 1) {
				shift--;
			}
        }
        else if(Keyboard.getEventKey() == 209 && !chatActive) {  //scroll down
			if(shift < 0) {
				shift++;
			}
        }
	}
	
	public static boolean mouseEvent() {
		topArrow = false;
		bottomArrow = false;
		toBottomArrow = false;
		hoverHeightResize = false;
		hoverWidthResize = false;
		hoverAllResize = false;
		if(Mideas.mouseX() >= 3 && Mideas.mouseX() <= 27 && Mideas.mouseY() >= Display.getHeight()-236 && Mideas.mouseY() <= Display.getHeight()-213) {
			topArrow = true;
		}
		else if(Mideas.mouseX() >= 3 && Mideas.mouseX() <= 27 && Mideas.mouseY() >= Display.getHeight()-202 && Mideas.mouseY() <= Display.getHeight()-179) {
			bottomArrow = true;
		}
		else if(Mideas.mouseX() >= 3 && Mideas.mouseX() <= 27 && Mideas.mouseY() >= Display.getHeight()-168 && Mideas.mouseY() <= Display.getHeight()-145) {
			toBottomArrow = true;
		}
		else if(Mideas.mouseX() >= 535+xResize && Mideas.mouseX() <= 545+xResize && Mideas.mouseY() >= Display.getHeight()-285-yResize && Mideas.mouseY() <= Display.getHeight()-275-yResize) {
			hoverAllResize = true;
		}
		else if(Mideas.mouseX() >= 3 && Mideas.mouseX() <= 513+xResize && Mideas.mouseY() >= Display.getHeight()-285-yResize && Mideas.mouseY() <= Display.getHeight()-275-yResize) {
			hoverHeightResize = true;
		}
		else if(Mideas.mouseX() >= 535+xResize && Mideas.mouseX() <= 545+xResize && Mideas.mouseY() >= Display.getHeight()-280-yResize && Mideas.mouseY() <= Display.getHeight()-150) {
			hoverWidthResize = true;
		}
		if(allResizing) {
			if(Mideas.mouseY() >= defaultHeight-550 && Mideas.mouseY() <= defaultHeight) {
				yResize = -Mideas.mouseY()+defaultHeight;
			}
			if(Mideas.mouseX() >= defaultWidth && Mideas.mouseX() <= defaultWidth+550) {
				xResize = Mideas.mouseX()-defaultWidth;
			}
		}
		else if(heightResizing) {
			if(Mideas.mouseY() >= defaultHeight-550 && Mideas.mouseY() <= defaultHeight) {
				yResize = -Mideas.mouseY()+defaultHeight;
			}
		}
		else if(widthResizing) {
			if(Mideas.mouseX() >= defaultWidth && Mideas.mouseX() <= defaultWidth+550) {
				xResize = Mideas.mouseX()-defaultWidth;
			}
		}
		if(Mouse.getEventButton() == 0) {
			if(Mouse.getEventButtonState()) {
				if(topArrow) {
					if(messages.size()-messageShowHeight+shift > 1) {
						shift--;
					}
				}
				else if(bottomArrow) {
					if(shift < 0) {
						shift++;
					}
				}
				else if(toBottomArrow) {
					shift = 0;
				}
				else if(hoverAllResize && !allResizing) {
					allResizing = true;
					return true;
				}
				else if(hoverHeightResize && !heightResizing) {
					heightResizing = true;
					return true;
				}
				else if(hoverWidthResize && !widthResizing) {
					widthResizing = true;
					return true;
				}
			}
			else {
				if(allResizing) {
					allResizing = false;
				}
				else if(heightResizing) {
					heightResizing = false;
				}
				else if(widthResizing) {
					widthResizing = false;
				}
			}
		}
		return false;
	}
	
	private static void addMessage() {
		String temp = tempMessage;
		messages.add(convTime(System.currentTimeMillis())+temp);
		/*if(TTF2.font4.getWidth(temp) <= maxLength-107) {
			messages.add(convTime(System.currentTimeMillis())+temp);
		}
		else {
			messages.add(convTime(System.currentTimeMillis())+temp.substring(0, getLength(temp, maxLength-107)));
			temp = temp.substring(getLength(temp, maxLength-107));
			while(TTF2.font4.getWidth(temp) > maxLength) {
				messages.add(temp.substring(0, getLength(temp, maxLength-10)));
				temp = temp.substring(getLength(temp, maxLength-10));
			}
			if(temp != "") {
				messages.add(temp);
			}
		}*/
	}
	
	private static boolean leftArrow() {
		int i = cursorPosition;
		if(tempMessage.length() != 0 && i > 0) {
			if(tempMessage.substring(cursorPosition-1).charAt(0) == ' ' || tempMessage.substring(cursorPosition-1).charAt(0) == ',') {
				cursorShift-= TTF2.font4.getWidth(tempMessage.substring(cursorPosition-1).charAt(0));
				cursorPosition--;
				i--;
			}
		}
		while(i > 0) {
			cursorPosition--;
			cursorShift-= TTF2.font4.getWidth(tempMessage.substring(i-1, i).charAt(0));
			i--;
			if(i <= 0) {
				return true;
			}
			if(tempMessage.substring(i-1, i).charAt(0) == ' ' || tempMessage.substring(i-1, i).charAt(0) == ',') {
				return true;
			}
		}
		return false;
	}
	
	private static boolean rightArrow() {
		int i = cursorPosition;
		if(i < tempMessage.length()) {
			if(tempMessage.length() != 0) {
				if(tempMessage.substring(cursorPosition+1).charAt(0) == ' ' || tempMessage.substring(cursorPosition+1).charAt(0) == ',') {
					cursorShift+= TTF2.font4.getWidth(tempMessage.substring(cursorPosition+1).charAt(0));
					cursorPosition++;
					i++;
				}
			}
			while(i < tempMessage.length() && i >= 0) {
				cursorPosition++;
				cursorShift+= TTF2.font4.getWidth(tempMessage.substring(i, i+1).charAt(0));
				i++;
				if(i >= tempMessage.length()) {
					return true;
				}
				if(tempMessage.substring(i, i+1).charAt(0) == ' ' || tempMessage.substring(i, i+1).charAt(0) == ',') {
					cursorPosition++;
					cursorShift+= TTF2.font4.getWidth(tempMessage.substring(i, i+1).charAt(0));
					return true;
				}
			}
		}
		return false;
	}
	
	private static int getNumberLine(String msg) {
		return TTF2.font4.getWidth(msg)/maxLength+1;
	}
	
	private static int getLength(String msg, int beg, int length) {
		int i = beg;
		String temp = "";
		while(TTF2.font4.getWidth(temp) <= length && i < msg.length()) {
			temp = msg.substring(beg, i);
			i++;
		}
		return i;
	}
	
	private static boolean delMessage() {
		int i = cursorPosition;
		String temp = tempMessage.substring(cursorPosition);
		if(tempMessage.length() != 0) {
			if(tempMessage.substring(tempMessage.length()-1).charAt(0) == ' ' || tempMessage.substring(tempMessage.length()-1).charAt(0) == ',') {
				tempMessage = tempMessage.substring(0, tempMessage.length()-1);
				i--;
			}
		}
		while(i > 0) {
			if(tempMessage.substring(i-1, i).charAt(0) == ' ' || tempMessage.substring(i-1, i).charAt(0) == ',') {
				tempMessage = tempMessage.substring(0, i);
				cursorPosition = tempMessage.length();
				cursorShift = TTF2.font4.getWidth(tempMessage);
				return true;
			}
			i--;
		}
		tempMessage = temp;
		cursorPosition = 0;
		cursorShift = 0;
		return false;
	}
	
	private static void write(String add) {
		if(cursorPosition == tempMessage.length()) {
			tempMessage+= add;
		}
		else {
			String beg = tempMessage.substring(0, cursorPosition);
			String end = tempMessage.substring(cursorPosition, tempMessage.length());
			tempMessage = beg+add+end;
		}
		cursorShift+= TTF2.font4.getWidth(add);
	}
	
	private static void write(char add) {
		if(cursorPosition == tempMessage.length()) {
			tempMessage+= add;
		}
		else {
			String beg = tempMessage.substring(0, cursorPosition);
			String end = tempMessage.substring(cursorPosition, tempMessage.length());
			tempMessage = beg+add+end;
		}
		cursorShift+= TTF2.font4.getWidth(add);
	}
	
	private static boolean checkTempMessage() throws FileNotFoundException, SQLException, CloneNotSupportedException {
		if(tempMessage.substring(0, 1).equals(".") && tempMessage.length() > 1 && !tempMessage.substring(1, 2).equals(".")) {
			if(tempMessage.equals(".kill joueur2")) {
				Mideas.joueur2().setStamina(0);
			}
			else if(tempMessage.equals(".kill joueur1")) {
				Mideas.joueur1().setStamina(0);
			}
			else if(tempMessage.contains(".modify hp joueur1 ")) {
				String[] temp = tempMessage.split("joueur1 ");
				int value = Integer.parseInt(temp[1]);	
				Mideas.joueur1().setStamina(value);
				Mideas.joueur1().setMaxStamina(value);
			}
			else if(tempMessage.contains(".modify hp joueur2 ")) {
				String[] temp = tempMessage.split("joueur2 ");
				int value = Integer.parseInt(temp[1]);	
				Mideas.joueur2().setStamina(value);
				Mideas.joueur2().setMaxStamina(value);
			}
			else if(tempMessage.contains(".modify mana joueur1 ")) {
				String[] temp = tempMessage.split("joueur1 ");
				int value = Integer.parseInt(temp[1]);	
				Mideas.joueur1().setMana(value);
				Mideas.joueur1().setMaxMana(value);
			}
			else if(tempMessage.contains(".modify mana joueur2 ")) {
				String[] temp = tempMessage.split("joueur2 ");
				int value = Integer.parseInt(temp[1]);	
				Mideas.joueur2().setMana(value);
				Mideas.joueur2().setMaxMana(value);
			}
			else if(tempMessage.contains(".lookup item ")) {
				String[] temp = tempMessage.split("item ");
				int value = Integer.parseInt(temp[1]);
				if(ShopManager.getItem(value) != null) {
					messages.add(ShopManager.getItem(value).getStuffName());
				}
				else {
					messages.add("Item not found");
				}
			}
			else if(tempMessage.contains(".lookup spell ")) {
				String[] temp = tempMessage.split("spell ");
				int value = Integer.parseInt(temp[1]);	
				messages.add(SpellManager.getBookSpell(value).getName());
			}
			else if(tempMessage.contains(".set joueur2 ")) {
				String[] temp = tempMessage.split("joueur2 ");
				String joueur = temp[1];
				if(ClassManager.exists(joueur.substring(1))) {
					Mideas.setJoueur2(ClassManager.getClone((joueur.substring(1))));
				}
			}
			else if(tempMessage.contains(".damage joueur1 ")) {
				String[] temp = tempMessage.split("joueur1 ");
				int value = Integer.parseInt(temp[1]);
				if(value < Math.pow(2, 32)) {
					Mideas.joueur1().setStamina(Mideas.joueur1().getStamina()-value);
				}
				else {
					messages.add("Incorrect value");
				}
			}
			else if(tempMessage.contains(".damage joueur2 ")) {
				String[] temp = tempMessage.split("joueur2 ");
				int value = Integer.parseInt(temp[1]);
				if(value < Math.pow(2, 32)) {
					Mideas.joueur2().setStamina(Mideas.joueur2().getStamina()-value);
				}
				else {
					messages.add("Incorrect value");
				}
			}
			else if(tempMessage.contains(".add stuff ")) {
				String[] temp = tempMessage.split("stuff ");
				int value = 0;
				if(temp[1].length() < 11) {
					value = Integer.parseInt(temp[1]);
				}
				if(StuffManager.exists(value)) {
					DragManager.checkFreeSlotBag(StuffManager.getClone(value));
					CharacterStuff.setBagItems();
				}
				else if(WeaponManager.exists(value)) {
					DragManager.checkFreeSlotBag(WeaponManager.getClone(value));
					CharacterStuff.setBagItems();
				}
				else if(PotionManager.exists(value)) {
					DragManager.checkFreeSlotBag(PotionManager.getClone(value));
					CharacterStuff.setBagItems();
				}
				else {
					messages.add("Item not found");
				}
			}
			else if(tempMessage.contains(".delete bag item ")) {
				String[] temp = tempMessage.split("item ");
				int value = Integer.parseInt(temp[1]);
				if(StuffManager.exists(value)) {
					DragManager.deleteItem(value);
					CharacterStuff.setBagItems();
				}
				else if(WeaponManager.exists(value)) {
					DragManager.deleteItem(value);
					CharacterStuff.setBagItems();
				}
				else {
					messages.add("Item not found");
				}
			}
			else if(tempMessage.equals(".clear chat")) {
				messages.clear();
			}
			else if(tempMessage.equals(".set fullscreen true")) {
				Mideas.setDisplayMode(1920, 1080, true);
			}
			else if(tempMessage.equals(".set fullscreen false")) {
				Mideas.setDisplayMode(1700, 930, false);
			}
			else if(tempMessage.equals(".update")) {
				Mideas.getGold();
				Mideas.getExp();
				ShopManager.getShopList().clear();
				ShopManager.loadStuffs();
				CharacterStuff.getEquippedBags();
				CharacterStuff.getBagItems();
				CharacterStuff.getEquippedItems();
				ContainerFrame.setBagchange(true);
				SpellBarManager.loadSpellBar();
			}
			else if(tempMessage.equals(".quit")) {
				System.exit(1);
				CharacterStuff.setBagItems();
				CharacterStuff.setEquippedBags();
				CharacterStuff.setEquippedItems();
				Mideas.setConfig();
				Mideas.setExp(0);
				Mideas.setGold(0);
				SpellBarManager.setSpellBar();
			}
			else if(tempMessage.contains(".modify gold ")) {
				String[] temp = tempMessage.split("gold ");
				int value = 0; 
				if(temp[1].length() < 11) {
					value = Integer.parseInt(temp[1]);
				}
				else {
					messages.add("Incorrect value");
					return true;
				}
				if(value < Math.pow(2, 32) && value >= 0) {
					Mideas.setGold(value);
				}
				else {
					messages.add("Incorrect value");
				}
			}
			else if(tempMessage.contains(".modify exp ")) {
				String[] temp = tempMessage.split("exp ");
				int value = 0; 
				if(temp[1].length() < 11) {
					value = Integer.parseInt(temp[1]);
				}
				else {
					messages.add("Incorrect value");
					return true;
				}
				if(value < Math.pow(2, 32) && value >= 0) {
					Mideas.joueur1().setExp(0, value);
				}
				else {
					messages.add("Incorrect value");
				}	
			}
			else if(tempMessage.equals(".help")) {
				messages.add(".kill [joueur]");
				messages.add(".modify hp [joueur] [value]");
				messages.add(".modify mana [joueur] [value]");
				messages.add(".lookup item [id]");
				messages.add(".lookup spell [id]");
				messages.add(".set joueur2 [joueur]");
				messages.add(".damage [joueur] [value]");
				messages.add(".add stuff [id]");
				messages.add(".delete bag item [id]");
				messages.add(".clear chat");
				messages.add(".set fullscreen [boolean]");
			}
			else if(tempMessage.equals(".clear chat")) {
				messages.clear();
				rawMessages.clear();
			}
			else {
				messages.add("Unknown command");
			}
			return true;
		}
		return false;
	}
	
	private static String convTime(long time) {
		time = time/1000;
		while(time-(60*60*24) >= 0) {
			time-= (60*60*24);
		}
		int hour = 1;
		while(time-3600 >= 0) {
			time-= 3600;
			hour++;
		}
		int min = 0;
		while(time-60 >= 0) {
			time-= 60;
			min++;
		}
		int seconds = 0;
		while(time-1 >= 0) {
			time-= 1;
			seconds++;
		}
		String result = "";
		if(hour < 10) {
			result = "[0"+(hour+1)+":";
		}
		else {
			result = "["+(hour+1)+":";
		}
		if(min < 10) {
			result+= "0"+min+":";
		}
		else {
			result+= min+":";
		}
		if(seconds < 10) {
			result+= "0"+seconds+"] : ";
		}
		else {
			result+= seconds+"] : ";
		}
		return result;
	}
	
	public static boolean getChatActive() {
		return chatActive;
	}
	
	public static void setChatActive(boolean we) {
		chatActive = we;
	}
}
