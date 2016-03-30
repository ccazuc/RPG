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
import com.mideas.rpg.v2.game.item.stuff.StuffManager;
import com.mideas.rpg.v2.game.spell.SpellManager;
import com.mideas.rpg.v2.utils.Draw;

public class ChatFrame {
	
	private static boolean chatActive;
	private static ArrayList<String> messages = new ArrayList<String>();
	private static ArrayList<String> rawMessages = new ArrayList<String>();
	private static int numberMessageSent;
	private static String tempMessage = "";
	private static int i;
	private static int numberUpArrow = 1;
	private static boolean isAdmin;
	private static int cursorPosition;
	private static boolean topArrow;
	private static boolean bottomArrow;
	private static boolean toBottomArrow;
	private static boolean hoverHeightResize;
	private static boolean heightResizing;
	private static int defaultHeight = Display.getHeight()-285;
	private static int yResize;
	private static int messageShowHeight = 4;
	private static int tempLength;
	private static int shift;
	private static int cursorShift;
	private static Color bgColor = new Color(0, 0, 0,.35f); 

	public static void draw() {
		//System.out.println(cursorPosition);
		messageShowHeight = 4+yResize/TTF2.font4.getLineHeight();
		Draw.drawColorQuad(30, Display.getHeight()-280-yResize, 510, 130+yResize, bgColor);
		Draw.drawQuad(Sprites.chat_button, 3, Display.getHeight()-268);
		if(chatActive) {
			if(TTF2.font4.getWidth(tempMessage.substring(tempLength)) >= 490) {
				tempLength = tempMessage.length()-10;
			}
			TTF2.font4.drawString(40, Display.getHeight()-175, tempMessage.substring(tempLength), Color.white);
			if(System.currentTimeMillis()%1000 < 500) {
				//shift 
				TTF2.font4.drawString(37+cursorShift+TTF2.font4.getWidth(tempMessage.substring(tempLength)), Display.getHeight()-175, "|", Color.white);
			}
		}
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
			while(k > messages.size()-messageShowHeight-2 && k+shift < messages.size() && k+shift >= 0) {
				TTF2.font4.drawString(40, Display.getHeight()-175-TTF2.font4.getLineHeight()*(j+1), messages.get(k+shift), Color.white);
				k--;
				j++;
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
			else if(Keyboard.isKeyDown(29) && Keyboard.getEventKey() == 14) { //ctrl+delete
				delMessage();
				tempLength = 0;
			}
			else if(Keyboard.getEventKey() == 14) { //delete
				if(tempMessage.length() > 0) {
					tempMessage = tempMessage.substring(0, tempMessage.length()-1);
				}
			}
			else if(Keyboard.getEventKey() == 200) {  //up arrow
				if(i >= numberUpArrow) {
					tempMessage = rawMessages.get(numberMessageSent-numberUpArrow);
					numberUpArrow++;
					cursorPosition = tempMessage.length();
					System.out.println(tempMessage.length());
					cursorShift = TTF2.font4.getWidth(tempMessage.substring(0, tempMessage.length()));
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
						cursorShift-= TTF2.font4.getWidth(tempMessage.substring(cursorPosition-1, cursorPosition));
					}
				}
			}
			else if(Keyboard.getEventKey() == 205) { //right arrow
				if(cursorPosition < tempMessage.length()) {
					cursorPosition++;
					cursorShift+= TTF2.font4.getWidth(tempMessage.substring(cursorPosition-1, cursorPosition));
				}
			}
			else if(Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) || Keyboard.isKeyDown(Keyboard.KEY_RCONTROL)) {
	            if(Keyboard.getEventKey() == Keyboard.KEY_V) {
	                tempMessage = Sys.getClipboard().replace("\n", "").replace("\t", "").replace("\r", "");
	            }
	        }
			else if(Keyboard.getEventKey() != Keyboard.KEY_RETURN) { //write
				char tempChar = Keyboard.getEventCharacter();
				write(tempChar);
				cursorPosition++;
			}
		}
        if(Keyboard.getEventKey() == Keyboard.KEY_RETURN) { //enter
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
		if(Mideas.mouseX() >= 3 && Mideas.mouseX() <= 27 && Mideas.mouseY() >= Display.getHeight()-236 && Mideas.mouseY() <= Display.getHeight()-213) {
			topArrow = true;
		}
		else if(Mideas.mouseX() >= 3 && Mideas.mouseX() <= 27 && Mideas.mouseY() >= Display.getHeight()-202 && Mideas.mouseY() <= Display.getHeight()-179) {
			bottomArrow = true;
		}
		else if(Mideas.mouseX() >= 3 && Mideas.mouseX() <= 27 && Mideas.mouseY() >= Display.getHeight()-168 && Mideas.mouseY() <= Display.getHeight()-145) {
			toBottomArrow = true;
		}
		else if(Mideas.mouseX() >= 3 && Mideas.mouseX() <= 513 && Mideas.mouseY() >= Display.getHeight()-285-yResize && Mideas.mouseY() <= Display.getHeight()-275-yResize) {
			hoverHeightResize = true;
		}
		if(heightResizing) {
			if(Mideas.mouseY() >= defaultHeight-250 && Mideas.mouseY() <= defaultHeight) {
				yResize = -(Mideas.mouseY()-defaultHeight);
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
				else if(hoverHeightResize && !heightResizing) {
					heightResizing = true;
					return true;
				}
			}
			else {
				if(heightResizing) {
					heightResizing = false;
				}
			}
		}
		return false;
	}
	
	private static void addMessage() {
		String temp = tempMessage;
		if(TTF2.font4.getWidth(temp) <= 383) {
			messages.add(convTime(System.currentTimeMillis())+temp);
		}
		else {
			messages.add(convTime(System.currentTimeMillis())+temp.substring(0, getLength(temp, 383)));
			temp = temp.substring(getLength(temp, 383));
			while(TTF2.font4.getWidth(temp) > 490) {
				messages.add(temp.substring(0, getLength(temp, 480)));
				temp = temp.substring(getLength(temp, 480));
			}
			if(temp != "") {
				messages.add(temp);
			}
		}
	}
	
	private static int getLength(String msg, int length) {
		int i = 0;
		String temp = "";
		while(TTF2.font4.getWidth(temp) <= length && i < msg.length()) {
			temp = msg.substring(0, i);
			i++;
		}
		return i;
	}
	
	private static void delMessage() {
		int i = tempMessage.length()-1;
		while(i > 0) {
			System.out.println("i : "+i+" length : "+tempMessage.length()+" char : "+tempMessage.substring(i-1, i).charAt(0));
			if(tempMessage.substring(i-1, i).charAt(0) == ' ') {
				tempMessage = tempMessage.substring(0, i);
				return;
			}
			i--;
		}
		tempMessage = "";
	}
	
	private static void write(String add) {
		if(cursorPosition == 0 || cursorPosition == tempMessage.length()) {
			tempMessage+= add;
		}
		else {
			String beg = tempMessage.substring(0, cursorPosition);
			String end = tempMessage.substring(cursorPosition+1, tempMessage.length());
			tempMessage = beg+add+end;
		}
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
				messages.add(StuffManager.getStuff(value).getStuffName());
			}
			else if(tempMessage.contains(".lookup spell ")) {
				String[] temp = tempMessage.split("spell ");
				int value = Integer.parseInt(temp[1]);	
				messages.add(SpellManager.getBookSpell(value).getName());
			}
			else if(tempMessage.contains(".set joueur2 ")) {
				String[] temp = tempMessage.split("joueur2 ");
				String joueur = temp[1];
				if(Mideas.getJoueur(joueur.substring(1)) != null) {
					Mideas.setJoueur2(Mideas.getJoueur(joueur.substring(1)));
				}
			}
			else if(tempMessage.contains(".damage joueur1 ")) {
				String[] temp = tempMessage.split("joueur1 ");
				int value = Integer.parseInt(temp[1]);
				Mideas.joueur1().setStamina(Mideas.joueur1().getStamina()-value);
			}
			else if(tempMessage.contains(".damage joueur2 ")) {
				String[] temp = tempMessage.split("joueur2 ");
				int value = Integer.parseInt(temp[1]);
				Mideas.joueur2().setStamina(Mideas.joueur2().getStamina()-value);
			}
			else if(tempMessage.contains(".add stuff ")) {
				String[] temp = tempMessage.split("stuff ");
				int value = Integer.parseInt(temp[1]);
				DragManager.checkFreeSlotBag(StuffManager.getClone(value));
				CharacterStuff.setBagItems();
			}
			else if(tempMessage.contains(".delete bag item ")) {
				String[] temp = tempMessage.split("item ");
				int value = Integer.parseInt(temp[1]);
				DragManager.deleteItem(value);
				CharacterStuff.setBagItems();
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
			else if(tempMessage.equals(".set admin pw:mideas")) {
				isAdmin = true;
			}
			else if(tempMessage.equals(".update")) {
				Mideas.getGold();
				Mideas.getExp();
				ShopManager.getShopList().clear();
				ShopManager.loadStuffs();
				CharacterStuff.getEquippedBags();
				CharacterStuff.getBagItems();
				CharacterStuff.getEquippedItems();
			}
			else if(tempMessage.equals(".quit")) {
				System.exit(1);
				CharacterStuff.setBagItems();
				CharacterStuff.setEquippedBags();
				CharacterStuff.setEquippedItems();
				Mideas.setConfig();
				Mideas.setExp();
				Mideas.setGold(0);
			}
			else if(tempMessage.contains(".modify gold ")) {
				String[] temp = tempMessage.split("gold ");
				int value = Integer.parseInt(temp[1]);	
				Mideas.setGold(value);
			}
			else if(tempMessage.contains(".modify exp ")) {
				String[] temp = tempMessage.split("exp ");
				int value = Integer.parseInt(temp[1]);	
				Mideas.joueur1().setExp(0, value);
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
		int hour = 0;
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
