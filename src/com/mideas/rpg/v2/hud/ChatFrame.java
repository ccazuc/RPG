package com.mideas.rpg.v2.hud;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

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
import com.mideas.rpg.v2.game.item.gem.GemManager;
import com.mideas.rpg.v2.game.item.potion.PotionManager;
import com.mideas.rpg.v2.game.item.stuff.StuffManager;
import com.mideas.rpg.v2.game.item.stuff.WeaponManager;
import com.mideas.rpg.v2.game.spell.SpellBarManager;
import com.mideas.rpg.v2.game.spell.SpellManager;
import com.mideas.rpg.v2.utils.Draw;

public class ChatFrame {
	
	private static boolean chatActive;
	private static ArrayList<Message> messages = new ArrayList<Message>();
	private static ArrayList<String> rawMessages = new ArrayList<String>();
	private static int numberMessageSent;
	private static String tempMessage = "";
	private static String[] tempTable = new String[30];
	private static int i;
	private static int numberUpArrow = 1;
	private static int cursorPosition;
	private static int totalNumberLine;
	private static boolean topArrow;
	private static boolean bottomArrow;
	private static boolean toBottomArrow;
	private static boolean hoverHeightResize;
	private static boolean heightResizing;
	private static int selectedLength;
	private static int selectedQuadLength;
	private static int selectedStarts;
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
	private static int cursorShift;
	private static int maxLength = 490;
	private static Color bgColor = new Color(0, 0, 0, .35f); 
	private static Color selectedColor = new Color(1, 1, 1, .5f); 
	private static int xDraw;
	private static int xShift;
	private static boolean showMessageTime = true;

	public static void draw() {
		messageShowHeight = 4+yResize/TTF2.chat.getLineHeight();
		maxLength = 490+xResize;
		Draw.drawColorQuad(30, Display.getHeight()-280-yResize, 510+xResize, 130+yResize, bgColor);
		Draw.drawQuad(Sprites.chat_button, 3, Display.getHeight()-268);
		if(chatActive) {
			if(TTF2.chat.getWidth(tempMessage.substring(tempLength)) >= maxLength) {
				tempLength = tempMessage.length()-10;
				cursorPosition = tempMessage.substring(tempLength).length();
				cursorShift = TTF2.chat.getWidth(tempMessage.substring(tempLength));
			}
			if(tempLength != 0 && tempMessage.substring(tempLength).equals("")) {
				tempLength-= getLength(tempMessage, 0, maxLength-130);
				cursorPosition = tempMessage.substring(tempLength).length();
				cursorShift = TTF2.chat.getWidth(tempMessage.substring(tempLength));
			}
			if(System.currentTimeMillis()%1000 < 500) {
				TTF2.chat.drawString(37+cursorShift, Display.getHeight()-175, "|", Color.white);
			}
		}
		Draw.drawColorQuad(selectedStarts, Display.getHeight()-175, selectedQuadLength, 20, selectedColor);
		TTF2.chat.drawString(40, Display.getHeight()-175, tempMessage.substring(tempLength), Color.white);
		//int j = 0;
		int k = 0;
		String draw = "";
		int beg = 0;
		/*while(k > messages.size()-messageShowHeight-2 && numberLineSent < messageShowHeight+1 && k+shift < messages.size() && k+shift >= 0) {
			beg = 0;
			if(TTF2.chat.getWidth(messages.get(k+shift)) >= maxLength) {
				String temp = messages.get(k+shift);
				draw = temp;
				int x = 0;
				while(x < getNumberLine(temp) && numberLineSent < messageShowHeight+1) {
					draw = temp.substring(beg, getLength(temp, beg, maxLength-30));
					beg = getLength(temp, beg, maxLength-30);
					x++;
					tempTable[x] = draw;
				}
				int i = 0;
				while(i < x && i < messageShowHeight+1) {
					if(numberLineSent < messageShowHeight+1) {
						TTF2.chat.drawString(40, Display.getHeight()-175-TTF2.chat.getLineHeight()*(j+1), tempTable[x-i], Color.white);
						j++;
						numberLineSent++;
					}
					i++;
				}
			}
			else {
				TTF2.chat.drawString(40, Display.getHeight()-175-TTF2.chat.getLineHeight()*(j+1), messages.get(k+shift), Color.white);
				j++;
				numberLineSent++;
			}
			k--;
		}*/
		xDraw = -totalNumberLine*TTF2.chat.getLineHeight()+Display.getHeight()-175+xShift;
		while(k < messages.size()) {
			beg = 0;
			if(TTF2.chat.getWidth(messages.get(k).getMessage()) >= maxLength) {
				String temp = messages.get(k).getMessage();
				draw = temp;
				int x = 0;
				while(x < getNumberLine(temp)) {
					if(x == 0 && showMessageTime && messages.get(k).getDisplayHour()) {
						draw = temp.substring(beg, getLength(temp, beg, maxLength-30-TTF2.chat.getWidth("["+convMessageFormat(messages.get(k).getHour())+":"+convMessageFormat(messages.get(k).getMinute())+":"+convMessageFormat(messages.get(k).getSecond())+"] ")));
					}
					else {
						draw = temp.substring(beg, getLength(temp, beg, maxLength-30));
					}
					beg = getLength(temp, beg, maxLength-30);
					x++;
					tempTable[x] = draw;
				}
				int i = 1;
				while(i <= x) {
					if(xDraw >= Display.getHeight()-280-yResize && xDraw <= Display.getHeight()-185 && tempTable[i] != null) {
						if(messages.get(k).getDisplayHour() && i == 1 && showMessageTime) {
							TTF2.chat.drawString(40, xDraw, "["+convMessageFormat(messages.get(k).getHour())+":"+convMessageFormat(messages.get(k).getMinute())+":"+convMessageFormat(messages.get(k).getSecond())+"] "+tempTable[i], Color.white);
						}
						else {
							TTF2.chat.drawString(40, xDraw, tempTable[i], Color.white);
						}
					}
					xDraw+= TTF2.chat.getLineHeight();
					i++;
				}
			}
			else {
				//System.out.println("k : "+k+" draw : "+xDraw+" total : "+totalNumberLine+" display min : "+(Display.getHeight()-280-yResize)+" display max : "+(Display.getHeight()-150));
				if(xDraw >= Display.getHeight()-280-yResize && xDraw <= Display.getHeight()-185) {
					if(messages.get(k).getDisplayHour() && showMessageTime) {
						TTF2.chat.drawString(40, xDraw, "["+convMessageFormat(messages.get(k).getHour())+":"+convMessageFormat(messages.get(k).getMinute())+":"+convMessageFormat(messages.get(k).getSecond())+"] "+messages.get(k).getMessage(), messages.get(k).getColor());
					}
					else {
						TTF2.chat.drawString(40, xDraw, messages.get(k).getMessage(), messages.get(k).getColor());
					}
				}
				xDraw+= TTF2.chat.getLineHeight();
			}
			k++;
			if(xDraw > Display.getHeight()-185) {
				break;
			}
		}
		if(topArrow) {
			Draw.drawQuad(Sprites.up_chat_button, 3, Display.getHeight()-236);
		}
	}
	
	public static void event() throws SQLException {
		if(chatActive) {
			Keyboard.enableRepeatEvents(true);
			if(Keyboard.getEventKey() == 1) { //escape
				chatActive = false;
				resetSelectedPosition();
			}
			else if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) { //left shift
				if(Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) {
					if(Keyboard.getEventKey() == 203) { // shift CTRL left arrow
						selectCTRLLeftArrow();
					}
					else if(Keyboard.getEventKey() == 205) { // shift CTRL right arrow
						selectCTRLRightArrow();
					}
				}
				if(Keyboard.getEventKey() == 203) { //shift left arrow
					selectLeftArrow();
				}
				else if(Keyboard.getEventKey() == 205) { // shift right arrow
					selectRightArrow();
				}
			}
			else if(Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) { //ctrl down
				if(Keyboard.getEventKey() == 14) { //delete
					CTRLDelete();
					tempLength = 0;
					resetSelectedPosition();
				}
				else if(Keyboard.getEventKey() == Keyboard.KEY_V) { // c/c
	                write(Sys.getClipboard().replace("\n", "").replace("\t", "").replace("\r", ""));
	                cursorPosition+= Sys.getClipboard().replace("\n", "").replace("\t", "").replace("\r", "").length();
	            }
				else if(Keyboard.getEventKey() == 203) { //left arrow
					CTRLleftArrow();
					resetSelectedPosition();
				}
				else if(Keyboard.getEventKey() == 205) { //right arrow
					CTRLrightArrow();
					resetSelectedPosition();
				}
				else if(Keyboard.getEventKey() == Keyboard.KEY_C) {
					copySelected();
				}
			}
			else if(Keyboard.getEventKey() == 14) { //delete
				delete();
				resetSelectedPosition();
			}
			else if(Keyboard.getEventKey() == 200) {  //up arrow
				upArrow();
				resetSelectedPosition();
			}
			else if(Keyboard.getEventKey() == 208) { //down arrow
				downArrow();
				resetSelectedPosition();
			}
			else if(Keyboard.getEventKey() == 203) { //left arrow
				leftArrow();
				resetSelectedPosition();
			}
			else if(Keyboard.getEventKey() == 205) { //right arrow
				rightArrow();
				resetSelectedPosition();
			}
			else if(Keyboard.getEventKey() == 211) { //suppr
				suppr();
				resetSelectedPosition();
			}
			else if(Keyboard.getEventKey() != Keyboard.KEY_RETURN && Keyboard.getEventKey() != 156 && Keyboard.getEventKey() != Keyboard.KEY_LSHIFT && Keyboard.getEventKey() != Keyboard.KEY_LCONTROL && Keyboard.getEventKey() != Keyboard.KEY_RCONTROL && Keyboard.getEventKey() != Keyboard.KEY_RSHIFT) { //write
				char tempChar = Keyboard.getEventCharacter();
				write(tempChar);
				cursorPosition++;
				resetSelectedPosition();
			}
		}
        if(Keyboard.getEventKey() == Keyboard.KEY_RETURN || Keyboard.getEventKey() == 156) { //enter
			if(chatActive) {
				if(!tempMessage.equals("")) {
					rawMessages.add(tempMessage);
					if(!checkTempMessage()) {
						addMessage();
					}
					totalNumberLine = getTotalNumberLine();
					numberMessageSent++;
					cursorPosition = 0;
					cursorShift = 0;
				}
				tempMessage =  "";
				tempLength = 0;
				numberUpArrow = 1;
				i++;
				resetSelectedPosition();
			}
			chatActive = !chatActive;
		}
        else if(Keyboard.getEventKey() == 201 && !chatActive) {  //scroll up
        	scrollUp();
        }
        else if(Keyboard.getEventKey() == 209 && !chatActive) {  //scroll down
        	scrollDown();
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
		int maxResize = 1200;
		if(allResizing) {
			if(Mideas.mouseY() >= defaultHeight-maxResize && Mideas.mouseY() <= defaultHeight) {
				yResize = -Mideas.mouseY()+defaultHeight;
			}
			if(Mideas.mouseX() >= defaultWidth && Mideas.mouseX() <= defaultWidth+maxResize) {
				xResize = Mideas.mouseX()-defaultWidth;
				totalNumberLine = getTotalNumberLine();
			}
		}
		else if(heightResizing) {
			if(Mideas.mouseY() >= defaultHeight-maxResize && Mideas.mouseY() <= defaultHeight) {
				yResize = -Mideas.mouseY()+defaultHeight;
			}
		}
		else if(widthResizing) {
			if(Mideas.mouseX() >= defaultWidth && Mideas.mouseX() <= defaultWidth+maxResize) {
				xResize = Mideas.mouseX()-defaultWidth;
				totalNumberLine = getTotalNumberLine();
			}
		}
		if(Mouse.getEventButton() == 0) {
			if(Mouse.getEventButtonState()) {
				if(topArrow) {
					scrollUp();
				}
				else if(bottomArrow) {
					scrollDown();
				}
				else if(toBottomArrow) {
					xShift = 0;
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
		if(!temp.equals("") && temp != null) {
			long time = System.currentTimeMillis();
			messages.add(new Message(temp, true, getMessageHour(time), getMessageMinute(time), getMessageSecond(time), Color.white));
		}
	}
	
	private static boolean CTRLleftArrow() {
		int i = cursorPosition;
		if(tempMessage.length() != 0 && i > 0) {
			if(tempMessage.substring(cursorPosition-1).charAt(0) == ' ' || tempMessage.substring(cursorPosition-1).charAt(0) == ',') {
				cursorShift-= TTF2.chat.getWidth(tempMessage.substring(cursorPosition-1).charAt(0));
				cursorPosition--;
				i--;
			}
		}
		while(i > 0) {
			cursorPosition--;
			cursorShift-= TTF2.chat.getWidth(tempMessage.substring(i-1, i).charAt(0));
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
	
	private static boolean CTRLrightArrow() {
		int i = cursorPosition;
		if(i < tempMessage.length()) {
			if(tempMessage.length() != 0) {
				if(tempMessage.substring(cursorPosition+1).charAt(0) == ' ' || tempMessage.substring(cursorPosition+1).charAt(0) == ',') {
					cursorShift+= TTF2.chat.getWidth(tempMessage.substring(cursorPosition+1).charAt(0));
					cursorPosition++;
					i++;
				}
			}
			while(i < tempMessage.length() && i >= 0) {
				cursorPosition++;
				cursorShift+= TTF2.chat.getWidth(tempMessage.substring(i, i+1).charAt(0));
				i++;
				if(i >= tempMessage.length()) {
					return true;
				}
				if(tempMessage.substring(i, i+1).charAt(0) == ' ' || tempMessage.substring(i, i+1).charAt(0) == ',') {
					cursorPosition++;
					cursorShift+= TTF2.chat.getWidth(tempMessage.substring(i, i+1).charAt(0));
					return true;
				}
			}
		}
		return false;
	}
	
	private static void leftArrow() {
		if(cursorPosition > 0) {
			cursorPosition--;
			cursorShift-= TTF2.chat.getWidth(tempMessage.substring(cursorPosition+tempMessage.substring(0, tempLength).length(), cursorPosition+1+tempMessage.substring(0, tempLength).length()));
		}
	}
	
	private static void rightArrow() {
		if(cursorPosition+tempMessage.substring(0, tempLength).length() < tempMessage.length()) {
			cursorPosition++;
			if(cursorPosition == tempMessage.length()) {
				cursorShift+= TTF2.chat.getWidth(tempMessage.substring(cursorPosition-1+tempMessage.substring(0, tempLength).length()));
			}
			else {
				cursorShift+= TTF2.chat.getWidth(tempMessage.substring(cursorPosition-1+tempMessage.substring(0, tempLength).length(), cursorPosition+tempMessage.substring(0, tempLength).length()));
			}
		}
	}
	
	private static void selectLeftArrow() {
		if(cursorPosition > 0) {
			if(selectedLength == 0) {
				selectedStarts = 40+cursorShift;
			}
			leftArrow();
			selectedQuadLength-= TTF2.chat.getWidth(tempMessage.substring(cursorPosition+tempMessage.substring(0, tempLength).length(), cursorPosition+1+tempMessage.substring(0, tempLength).length()));
			selectedLength--;
		}
 	}
	
	private static boolean selectCTRLLeftArrow() {
		int i = cursorPosition;
		if(selectedLength == 0) {
			selectedStarts = 40+cursorShift;
		}
		if(tempMessage.length() != 0 && i > 0) {
			if(tempMessage.substring(cursorPosition-1).charAt(0) == ' ' || tempMessage.substring(cursorPosition-1).charAt(0) == ',') {
				cursorShift-= TTF2.chat.getWidth(tempMessage.substring(cursorPosition-1).charAt(0));
				selectedQuadLength-= TTF2.chat.getWidth(tempMessage.substring(cursorPosition-1).charAt(0));
				selectedLength--;
				cursorPosition--;
				i--;
			}
		}
		while(i > 0) {
			cursorPosition--;
			cursorShift-= TTF2.chat.getWidth(tempMessage.substring(i-1, i).charAt(0));
			selectedQuadLength-= TTF2.chat.getWidth(tempMessage.substring(i-1, i).charAt(0));
			selectedLength--;
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
	
	private static boolean selectCTRLRightArrow() {
		int i = cursorPosition;
		if(selectedLength == 0) {
			selectedStarts = 40+cursorShift;
		}
		if(i < tempMessage.length()) {
			if(tempMessage.length() != 0 && cursorPosition+1 <= tempMessage.length() && cursorPosition+1 >= 0) {
				if(tempMessage.substring(cursorPosition+1).charAt(0) == ' ' || tempMessage.substring(cursorPosition+1).charAt(0) == ',') {
					cursorShift+= TTF2.chat.getWidth(tempMessage.substring(cursorPosition+1).charAt(0));
					selectedQuadLength+= TTF2.chat.getWidth(tempMessage.substring(cursorPosition+1).charAt(0));
					selectedLength++;
					cursorPosition++;
					i++;
				}
			}
			while(i < tempMessage.length() && i >= 0) {
				cursorPosition++;
				cursorShift+= TTF2.chat.getWidth(tempMessage.substring(i, i+1).charAt(0));
				selectedQuadLength+= TTF2.chat.getWidth(tempMessage.substring(i, i+1).charAt(0));
				selectedLength++;
				i++;
				if(i >= tempMessage.length()) {
					return true;
				}
				if(tempMessage.substring(i, i+1).charAt(0) == ' ' || tempMessage.substring(i, i+1).charAt(0) == ',') {
					return true;
				}
			}
		}
		return false;
	}

	private static void selectRightArrow() {
		if(cursorPosition < tempMessage.length()) {
			if(selectedLength == 0) {
				selectedStarts = 40+cursorShift;
			}
			rightArrow();
			if(cursorPosition == tempMessage.length()) {
				selectedQuadLength+= TTF2.chat.getWidth(tempMessage.substring(cursorPosition-1+tempMessage.substring(0, tempLength).length()));
			}
			else {
				selectedQuadLength+= TTF2.chat.getWidth(tempMessage.substring(cursorPosition-1+tempMessage.substring(0, tempLength).length(), cursorPosition+tempMessage.substring(0, tempLength).length()));
			}
			selectedLength++;
		}
 	}
	
	private static void upArrow() {
		if(i >= numberUpArrow) {
			tempMessage = rawMessages.get(numberMessageSent-numberUpArrow);
			numberUpArrow++;
			cursorPosition = tempMessage.length();
			cursorShift = TTF2.chat.getWidth(tempMessage);
		}
	}

	private static void downArrow() {
		if(numberUpArrow > 1) {
			numberUpArrow--;
			tempMessage = rawMessages.get(numberMessageSent-numberUpArrow);
		}
	}
	
	private static void suppr() {
		if(cursorPosition+tempMessage.substring(0, tempLength).length() < tempMessage.length()) {
			String beg = tempMessage.substring(0, cursorPosition+tempMessage.substring(0, tempLength).length());
			String end = tempMessage.substring(cursorPosition+1+tempMessage.substring(0, tempLength).length(), tempMessage.length());
			tempMessage = beg+end;
		}
	}
	
	private static void scrollUp() {
    	if(xShift/TTF2.chat.getLineHeight() <= totalNumberLine-messageShowHeight-2) {
    		xShift+= TTF2.chat.getLineHeight();
    	}
	}
	
	private static void scrollDown() {
    	if(xShift/TTF2.chat.getLineHeight() > 0) {
    		xShift-= TTF2.chat.getLineHeight();
    	}
	}
	
	private static int getNumberLine(String msg) {
		return TTF2.chat.getWidth(msg)/maxLength+1;
	}
	
	private static int getLength(String msg, int beg, int length) {
		int i = beg;
		String temp = "";
		while(TTF2.chat.getWidth(temp) <= length && i < msg.length()) {
			temp = msg.substring(beg, i);
			i++;
		}
		return i;
	}
	
	private static int getTotalNumberLine() {
		int i = 0;
		int number = 0;
		while(i < messages.size()) {
			number+= getNumberLine(messages.get(i).getMessage());
			i++;
		}
		return number;
	}
	
	private static boolean CTRLDelete() {
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
				cursorShift = TTF2.chat.getWidth(tempMessage);
				return true;
			}
			i--;
		}
		tempMessage = temp;
		cursorPosition = 0;
		cursorShift = 0;
		return false;
	}
	
	private static void deleteSelected() {
		if(selectedLength != 0) {
			String beg = "";
			String end = "";
			if(selectedLength < 0) {
				if(cursorPosition-selectedLength >= 0 && cursorPosition-selectedLength <= tempMessage.length() ) {
					beg = tempMessage.substring(0, cursorPosition);
					end = tempMessage.substring(cursorPosition-selectedLength, tempMessage.length());
				}
			}
			else {
				beg = tempMessage.substring(0, cursorPosition-selectedLength);
				end = tempMessage.substring(cursorPosition, tempMessage.length());
				cursorShift = TTF2.chat.getWidth(tempMessage.substring(0, cursorPosition-selectedLength));
				cursorPosition = cursorPosition-selectedLength;
			}
			tempMessage = beg+end;
		}
	}
	
	private static void copySelected() {
		String selected;
		if(selectedLength < 0) {
			selected = tempMessage.substring(cursorPosition, cursorPosition-selectedLength);
		}
		else {
			selected = tempMessage.substring(cursorPosition-selectedLength, cursorPosition);
		}
		StringSelection selection = new StringSelection(selected);
	    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
	    clipboard.setContents(selection, selection);
	}
	
	private static void delete() {
		if(tempMessage.length() > 0) {
			if(selectedLength != 0) {
				deleteSelected();
			}
			else if(cursorPosition > 0) {
				cursorShift-= TTF2.chat.getWidth(tempMessage.substring(cursorPosition-1).charAt(0));
				String beg = tempMessage.substring(0, cursorPosition-1+tempMessage.substring(0, tempLength).length());
				String end = tempMessage.substring(cursorPosition+tempMessage.substring(0, tempLength).length(), tempMessage.length());
				tempMessage = beg+end;
				cursorPosition--;
			}
		}
	}
	
	private static void write(String add) {
		if(cursorPosition == tempMessage.length()) {
			tempMessage+= add;
		}
		else {
			String beg = tempMessage.substring(0, cursorPosition+tempMessage.substring(0, tempLength).length());
			String end = tempMessage.substring(cursorPosition+tempMessage.substring(0, tempLength).length(), tempMessage.length());
			tempMessage = beg+add+end;
		}
		cursorShift+= TTF2.chat.getWidth(add);
	}
	
	private static void write(char add) {
		deleteSelected();
		if(cursorPosition == tempMessage.length()) {
			tempMessage+= add;
		}
		else {
			String beg = tempMessage.substring(0, cursorPosition+tempMessage.substring(0, tempLength).length());
			String end = tempMessage.substring(cursorPosition+tempMessage.substring(0, tempLength).length(), tempMessage.length());
			tempMessage = beg+add+end;
		}
		cursorShift+= TTF2.chat.getWidth(add);
	}
	
	private static boolean checkTempMessage() throws SQLException {
		String message = tempMessage.trim().toLowerCase();
		if(message.length() > 1 && message.substring(0, 1).equals(".") && !message.substring(1, 2).equals(".")) {
			if(message.equals(".kill joueur2")) {
				Mideas.joueur2().setStamina(0);
			}
			else if(message.equals(".kill joueur1")) {
				Mideas.joueur1().setStamina(0);
			}
			else if(message.startsWith(".modify hp joueur1 ")) {
				String[] temp = message.split("joueur1 ");
				int value = Integer.parseInt(temp[1]);	
				Mideas.joueur1().setStamina(value);
				Mideas.joueur1().setMaxStamina(value);
			}
			else if(message.startsWith(".modify hp joueur2 ")) {
				String[] temp = message.split("joueur2 ");
				int value = Integer.parseInt(temp[1]);	
				Mideas.joueur2().setStamina(value);
				Mideas.joueur2().setMaxStamina(value);
			}
			else if(message.startsWith(".modify mana joueur1 ")) {
				String[] temp = message.split("joueur1 ");
				int value = Integer.parseInt(temp[1]);	
				Mideas.joueur1().setMana(value);
				Mideas.joueur1().setMaxMana(value);
			}
			else if(message.startsWith(".modify mana joueur2 ")) {
				String[] temp = message.split("joueur2 ");
				int value = Integer.parseInt(temp[1]);	
				Mideas.joueur2().setMana(value);
				Mideas.joueur2().setMaxMana(value);
			}
			else if(message.startsWith(".lookup item ")) {
				String[] temp = message.split("item ");
				int value = Integer.parseInt(temp[1]);
				if(ShopManager.getItem(value) != null) {
					messages.add(new Message(ShopManager.getItem(value).getStuffName(), false, 0, 0, 0, Color.yellow));
				}
				else {
					messages.add(new Message("Item not found", false, 0, 0, 0, Color.yellow));
				}
			}
			else if(message.startsWith(".lookup spell ")) {
				String[] temp = message.split("spell ");
				int value = Integer.parseInt(temp[1]);
				messages.add(new Message(SpellManager.getBookSpell(value).getName(), false, 0, 0, 0, Color.yellow));
			}
			else if(message.startsWith(".set x ")) {
				String[] temp = message.split("x ");
				int value = Integer.parseInt(temp[1]);	
				CharacterFrame.setMouseX(value);
			}
			else if(message.startsWith(".set y ")) {
				String[] temp = message.split("y ");
				int value = Integer.parseInt(temp[1]);	
				CharacterFrame.setMouseY(value);
			}
			else if(message.startsWith(".set joueur2 ")) {
				String[] temp = message.split("joueur2 ");
				String joueur = temp[1];
				if(ClassManager.exists(joueur.substring(1))) {
					Mideas.setJoueur2(ClassManager.getClone((joueur.substring(1))));
				}
			}
			else if(message.startsWith(".damage joueur1 ")) {
				String[] temp = message.split("joueur1 ");
				int value = Integer.parseInt(temp[1]);
				if(value < Math.pow(2, 32)) {
					Mideas.joueur1().setStamina(Mideas.joueur1().getStamina()-value);
				}
				else {
					messages.add(new Message("Incorrect value", false, 0, 0, 0, Color.yellow));
				}
			}
			else if(message.startsWith(".damage joueur2 ")) {
				String[] temp = message.split("joueur2 ");
				int value = Integer.parseInt(temp[1]);
				if(value < Math.pow(2, 32)) {
					Mideas.joueur2().setStamina(Mideas.joueur2().getStamina()-value);
				}
				else {
					messages.add(new Message("Incorrect value", false, 0, 0, 0, Color.yellow));
				}
			}
			else if(message.startsWith(".add stuff ")) {
				String[] temp = message.split("stuff ");
				int value = 0;
				if(temp[1].length() < 11) {
					value = Integer.parseInt(temp[1]);
				}
				if(StuffManager.exists(value)) {
					Mideas.joueur1().addItem(StuffManager.getClone(value), 1);
				}
				else if(WeaponManager.exists(value)) {
					Mideas.joueur1().addItem(WeaponManager.getClone(value), 1);
				}
				else if(PotionManager.exists(value)) {
					Mideas.joueur1().addItem(PotionManager.getClone(value), 1);
				}
				else if(GemManager.exists(value)) {
					Mideas.joueur1().addItem(GemManager.getClone(value), 1);
				}
				else {
					messages.add(new Message("Item not found", false, 0, 0, 0, Color.yellow));
				}
			}
			else if(message.startsWith(".delete bag item ")) {
				String[] temp = message.split("item ");
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
					messages.add(new Message("Item not found", false, 0, 0, 0, Color.yellow));
				}
			}
			else if(message.equals(".clear chat")) {
				messages.clear();
			}
			else if(message.equals(".set fullscreen true")) {
				Mideas.setDisplayMode(1920, 1080, true);
			}
			else if(message.equals(".set fullscreen false")) {
				Mideas.setDisplayMode(1700, 930, false);
			}
			else if(message.equals(".update")) {
				Mideas.getGold();
				Mideas.getExp();
				Mideas.getConfig();
				ShopManager.getShopList().clear();
				ShopManager.loadStuffs();
				CharacterStuff.getEquippedBags();
				CharacterStuff.getBagItems();
				CharacterStuff.getEquippedItems();
				ContainerFrame.setBagchange(true);
				SpellBarManager.loadSpellBar();
				Mideas.bag().setBagChange(true);
			}
			else if(message.equals(".quit")) {
				System.exit(1);
				CharacterStuff.setBagItems();
				CharacterStuff.setEquippedBags();
				CharacterStuff.setEquippedItems();
				Mideas.setConfig();
				Mideas.setExp(0);
				Mideas.setGold(0);
				SpellBarManager.setSpellBar();
			}
			else if(message.startsWith(".modify gold ")) {
				String[] temp = message.split("gold ");
				int value = 0; 
				if(temp[1].length() < 11) {
					value = Integer.parseInt(temp[1]);
				}
				else {
					messages.add(new Message("Incorrect value", false, 0, 0, 0, Color.yellow));
					return true;
				}
				if(value < Math.pow(2, 32) && value >= 0) {
					Mideas.setGold(value);
				}
				else {
					messages.add(new Message("Incorrect value", false, 0, 0, 0, Color.yellow));
				}
			}
			else if(message.startsWith(".modify exp ")) {
				String[] temp = message.split("exp ");
				int value = 0; 
				if(temp[1].length() < 11) {
					value = Integer.parseInt(temp[1]);
				}
				else {
					messages.add(new Message("Incorrect value", false, 0, 0, 0, Color.yellow));
					return true;
				}
				if(value < Math.pow(2, 32) && value >= 0) {
					Mideas.joueur1().setExp(0, value);
				}
				else {
					messages.add(new Message("Incorrect value", false, 0, 0, 0, Color.yellow));
				}	
			}
			else if(message.equals(".help")) {
				messages.add(new Message(".kill [joueur]", false, 0, 0, 0, Color.yellow));
				messages.add(new Message(".modify hp [joueur] [value]", false, 0, 0, 0, Color.yellow));
				messages.add(new Message(".modify mana [joueur] [value]", false, 0, 0, 0, Color.yellow));
				messages.add(new Message(".lookup item [id]", false, 0, 0, 0, Color.yellow));
				messages.add(new Message(".lookup spell [id]", false, 0, 0, 0, Color.yellow));
				messages.add(new Message(".set joueur2 [joueur]", false, 0, 0, 0, Color.yellow));
				messages.add(new Message(".damage [joueur] [value]", false, 0, 0, 0, Color.yellow));
				messages.add(new Message(".add stuff [id]", false, 0, 0, 0, Color.yellow));
				messages.add(new Message(".delete bag item [id]", false, 0, 0, 0, Color.yellow));
				messages.add(new Message(".clear chat", false, 0, 0, 0, Color.yellow));
				messages.add(new Message(".set fullscreen [boolean]", false, 0, 0, 0, Color.yellow));
				messages.add(new Message(".show time", false, 0, 0, 0, Color.yellow));
			}
			else if(message.equals(".clear chat")) {
				messages.clear();
				rawMessages.clear();
			}
			else if(message.equals(".show time")) {
				showMessageTime = !showMessageTime;
			}
			else {
				messages.add(new Message("Unknown command", false, 0, 0, 0, Color.yellow));
			}
			return true;
		}
		return false;
	}
	
	private static int getMessageHour(long time) {
		int hour;
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(time);
		hour = (byte)calendar.get(Calendar.HOUR_OF_DAY);
		return hour;
	}
	
	private static int getMessageMinute(long time) {
		int minute;
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(time);
		minute = (byte)calendar.get(Calendar.MINUTE);
		return minute;
	}
	
	private static int getMessageSecond(long time) {
		int second;
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(time);
		second = (byte)calendar.get(Calendar.SECOND);
		return second;
	}
	
	private static String convMessageFormat(long time) {
		if(time < 10) {
			return "0"+Long.toString(time);
		}
		return Long.toString(time);
	}
	
	private static void resetSelectedPosition() {
		selectedQuadLength = 0;
		selectedLength = 0;
		selectedStarts = 0;
	}
	
	public static boolean getChatActive() {
		return chatActive;
	}
	
	public static void setChatActive(boolean we) {
		chatActive = we;
	}
}
