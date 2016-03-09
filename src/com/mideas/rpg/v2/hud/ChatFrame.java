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
	private static String tempMessage = "";
	private static int i;
	private static int numberUpArrow = 1;
	private static boolean isAdmin;
	private static boolean hover_button1;
	private static boolean hover_button2;
	private static boolean hover_button3;
	private static int shift;

	public static boolean draw() {
		if(chatActive) {
			if(tempMessage.length() > 49) {
				TTF2.font4.drawString(40, Display.getHeight()-175, tempMessage.substring(checkTempMessageLength()*49), Color.white);
			}
			else {
				TTF2.font4.drawString(40, Display.getHeight()-175, tempMessage, Color.white);
			}
			if(System.currentTimeMillis()%1000 < 500) {
				TTF2.font4.drawString(40+TTF2.font4.getWidth(tempMessage.substring(checkTempMessageLength()*49)), Display.getHeight()-175, "|", Color.white);
			}
		}
		Draw.drawQuad(Sprites.chat_button, 3, Display.getHeight()-268);
		int j = 0;
		if(messages.size() <= 4) {
			int k = messages.size()-1;
			while(k >= 0) {
				TTF2.font4.drawString(40, Display.getHeight()-175-TTF2.font4.getLineHeight()*(j+1), messages.get(k+shift), Color.white);
				k--;
				j++;
			}
		}
		else if(messages.size() >= 5) {
			int k = messages.size()-1;
			while(k > messages.size()-6) {
				TTF2.font4.drawString(40, Display.getHeight()-175-TTF2.font4.getLineHeight()*(j+1), messages.get(k+shift), Color.white);
				k--;
				j++;
			}
		}
		if(hover_button1) {
			Draw.drawQuad(Sprites.up_chat_button, 3, Display.getHeight()-236);
		}
		return false;
	}
	
	public static void event() throws FileNotFoundException, SQLException, CloneNotSupportedException {
		if(chatActive) {
			if(Keyboard.getEventKey() == 1) { //escape
				chatActive = false;
			}
			else if(Keyboard.getEventKey() == 14) { //delete
				if(tempMessage.length() > 0) {
					tempMessage = tempMessage.substring(0, tempMessage.length()-1);
				}
			}
			else if(Keyboard.getEventKey() == 200) {  //up arrow
				if(i >= numberUpArrow) {
					tempMessage = messages.get(i-numberUpArrow).substring(13);
					//String[] temp = tempMessage.split(" : ");
					//String split = temp[1];
					//tempMessage = split;
					numberUpArrow++;
				}
			}
			else if(Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) || Keyboard.isKeyDown(Keyboard.KEY_RCONTROL)) {
	            if(Keyboard.getEventKey() == Keyboard.KEY_V) {
	                tempMessage = Sys.getClipboard().replace("\n", "").replace("\t", "").replace("\r", "");
	            }
	        }
			else if(Keyboard.getEventKey() != Keyboard.KEY_RETURN) {
				char tempChar = Keyboard.getEventCharacter();
				tempMessage = tempMessage+tempChar;
			}
		}
        if(Keyboard.getEventKey() == Keyboard.KEY_RETURN) {
			if(chatActive) {
				if(tempMessage != "") {
					checkTempMessage();
					messages.add(convTime(System.currentTimeMillis())+tempMessage);
				}
				tempMessage =  "";
				numberUpArrow = 1;
				i++;
			}
			chatActive = !chatActive;
		}
	}
	
	public static boolean mouseEvent() {
		hover_button1 = false;
		hover_button2 = false;
		hover_button3 = false;
		if(Mideas.mouseX() >= 3 && Mideas.mouseX() <= 27 && Mideas.mouseY() >= Display.getHeight()-236 && Mideas.mouseY() <= Display.getHeight()-213) {
			hover_button1 = true;
		}
		else if(Mideas.mouseX() >= 3 && Mideas.mouseX() <= 27 && Mideas.mouseY() >= Display.getHeight()-202 && Mideas.mouseY() <= Display.getHeight()-179) {
			hover_button2 = true;
		}
		else if(Mideas.mouseX() >= 3 && Mideas.mouseX() <= 27 && Mideas.mouseY() >= Display.getHeight()-168 && Mideas.mouseY() <= Display.getHeight()-145) {
			hover_button3 = true;
		}
		if(Mouse.getEventButtonState()) {
			if(Mouse.getEventButton() == 0) {
				if(hover_button1) {
					if(messages.size()-5+shift > 0) {
						shift--;
					}
				}
				else if(hover_button2) {
					if(shift < 0) {
						shift++;
					}
				}
				else if(hover_button3) {
					shift = 0;
				}
			}
		}
		return false;
	}
	
	public static int checkTempMessageLength() {
		int i = 0;
		int length = tempMessage.length();
		while(length-49 > 0) {
			length-= 49;
			i++;
		}
		return i;
	}
	
	private static void checkTempMessage() throws FileNotFoundException, SQLException, CloneNotSupportedException {
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
		else if(tempMessage.equals(".set admin pw:mideas")) {
			isAdmin = true;
		}
		else if(tempMessage.equals(".update")) {
			/*if(!isAdmin) {
				Sys.alert("Admin", "Vous n'êtes pas admin.");
			}*/
			Mideas.getGold();
			Mideas.getExp();
			ShopManager.getShopList().clear();
			ShopManager.loadStuffs();
			CharacterStuff.getBagItems();
			CharacterStuff.getEquippedItems();
		}
		else if(tempMessage.equals(".quit")) {
			System.exit(1);
		}
		else if(tempMessage.contains(".modify gold ")) {
			String[] temp = tempMessage.split("gold ");
			int value = Integer.parseInt(temp[1]);	
			Mideas.setGold(value);
		}
		else if(tempMessage.contains(".modify exp ")) {
			String[] temp = tempMessage.split("exp ");
			int value = Integer.parseInt(temp[1]);	
			System.out.println(value);
			Mideas.joueur1().setExp(0, value);
		}
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
