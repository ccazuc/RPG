package com.mideas.rpg.v2.chat;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.ArrayList;

import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;

import com.mideas.rpg.v2.ClassColor;
import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.Sprites;
import com.mideas.rpg.v2.TTF2;
import com.mideas.rpg.v2.command.CommandParty;
import com.mideas.rpg.v2.command.chat.CommandSendMessage;
import com.mideas.rpg.v2.utils.Draw;

public class ChatFrame {
	
	private static ArrayList<String> rawMessages = new ArrayList<String>();
	private static ArrayList<Message> messages = new ArrayList<Message>();
	private static Color selectedColor = new Color(1, 1, 1, .5f); 
	private static int defaultHeight = Display.getHeight()-285;
	private static MessageType selectedType = MessageType.SAY;
	private final static int NUMBER_LAST_MESSAGE_TAKEN = 100;
	private static Color bgColor = new Color(0, 0, 0, .35f);
	private final static int MAXIMUM_MESSAGES = 100;
	private final static int MAXIMUM_LENGTH = 255;
	private static String currentWhisper = "";
	private static int messageShowHeight = 4;
	private static boolean hoverHeightResize;
	private static boolean hoverWidthResize;
	private static String tempMessage = "";
	private static boolean heightResizing;
	private static boolean hoverAllResize;
	private static String previousWhisper;
	private static int selectedQuadLength;
	private static int defaultWidth = 540;
	private static int numberMessageSent;
	private static boolean widthResizing;
	private static int numberUpArrow = 1;
	private static boolean toBottomArrow;
	private static boolean allResizing;
	private static boolean bottomArrow;
	private static int totalNumberLine;
	private static int maxLength = 490; 
	private static int cursorPosition;
	private static boolean chatActive;
	private static int selectedLength;
	private static int selectedStarts;
	private static boolean topArrow;
	private static int cursorShift;
	private static int tempLength;
	private static int yResize;
	private static int xResize;
	private static int xShift;
	private static int yDraw; 
	private static int xDraw;
	private static int i;
	
	private static String warrior = "Warrior";
	private static String paladin = "Paladin";
	private static String hunter = "Hunter";
	private static String priest = "Priest";
	private static String mage = "Mage";
	private static String warlock = "Warlock";
	private static String rogue = "Rogue";
	private static String shaman = "Shaman";
	private static String druid = "Druid";

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
				TTF2.chat.drawString(39+cursorShift+TTF2.chat.getWidth(selectedType.getDefaultText()+currentWhisper), Display.getHeight()-175, "|", selectedType.getColor());
			}
		}
		if(messages.size() > MAXIMUM_MESSAGES) {
			while(messages.size() >= MAXIMUM_MESSAGES) {
				messages.remove(0);
			}
			totalNumberLine = getNumberLineLast(NUMBER_LAST_MESSAGE_TAKEN);
		}
		Draw.drawColorQuad(selectedStarts+TTF2.chat.getWidth(selectedType.getDefaultText()+currentWhisper), Display.getHeight()-175, selectedQuadLength, 20, selectedColor);
		TTF2.chat.drawString(40, Display.getHeight()-175, selectedType.getDefaultText()+currentWhisper, selectedType.getColor());
		TTF2.chat.drawString(40+TTF2.chat.getWidth(selectedType.getDefaultText()+currentWhisper), Display.getHeight()-175, tempMessage.substring(tempLength), selectedType.getColor());
		int k = 0;
		yDraw = -totalNumberLine*TTF2.chat.getLineHeight()+Display.getHeight()-175+xShift;
		//yDraw = -numberLineLastMessages*TTF2.chat.getLineHeight()+Display.getHeight()-175+xShift;
		//System.out.println(numberLineLastMessages);
		xDraw = 40;
		while(k < messages.size()) {
			xDraw = 40;
				if(yDraw <= Display.getHeight()-185) {
					int j = 0;
					if(messages.get(k).getAuthor() != null) {
							if(yDraw >= Display.getHeight()-280-yResize) {
								TTF2.chat.drawString(xDraw+1, yDraw, messages.get(k).getAuthorText(), Color.black);
								TTF2.chat.drawString(xDraw, yDraw, messages.get(k).getAuthorText(), messages.get(k).getColor());
							}
						xDraw+= TTF2.chat.getWidth(messages.get(k).getAuthorText());
					}
					while(j < messages.get(k).getMessage().length()) {
						if(yDraw >= Display.getHeight()-280-yResize) {
							TTF2.chat.drawChar(xDraw+1, yDraw, messages.get(k).getMessage().charAt(j), Color.black);
							TTF2.chat.drawChar(xDraw, yDraw, messages.get(k).getMessage().charAt(j), messages.get(k).getColor());
						}
						xDraw+= TTF2.chat.getWidth(messages.get(k).getMessage().charAt(j));
						j++;
						if(xDraw-40 > maxLength-10 && j < messages.get(k).getMessage().length()) {
							yDraw+= TTF2.chat.getLineHeight();
							xDraw = 40;
						}
					}
				}
			k++;
			if(yDraw > Display.getHeight()-185 || !(k < messages.size())) {
				break;
			}
			yDraw+= TTF2.chat.getLineHeight();
		}
		if(topArrow) {
			Draw.drawQuad(Sprites.up_chat_button, 3, Display.getHeight()-236);
		}
	}
	
	private static int getNumberLine(Message msg) {
		int i = 0;
		int x = 0;
		int number = 1;
		x+= TTF2.chat.getWidth(msg.getAuthorText());
		while(i < msg.getMessage().length()) {
			x+= TTF2.chat.getWidth(msg.getMessage().charAt(i));
			if(x > maxLength-5) {
				x = 0;
				number++;
			}
			i++;
		}
		return number;
	}
	
	public static void event() throws NumberFormatException {
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
						return;
					}
					else if(Keyboard.getEventKey() == 205) { // shift CTRL right arrow
						selectCTRLRightArrow();
						return;
					}
				}
				if(Keyboard.getEventKey() == 203) { //shift left arrow
					selectLeftArrow();
					return;
				}
				else if(Keyboard.getEventKey() == 205) { // shift right arrow
					selectRightArrow();
					return;
				}
			}
			if(Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) { //ctrl down
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
				if(tempMessage.length() < MAXIMUM_LENGTH) {
					char tempChar = Keyboard.getEventCharacter();
					if(tempChar == ' ' && checkNewMessageType(tempChar)) {
						cursorPosition = 0;
						cursorShift = 0;
						tempMessage =  "";
						tempLength = 0;
					}
					else {
						write(tempChar);
						cursorPosition++;
						resetSelectedPosition();
					}
				}
			}
		}
        if(Keyboard.getEventKey() == Keyboard.KEY_RETURN || Keyboard.getEventKey() == 156) { //enter
			if(chatActive) {
				if(!tempMessage.equals("")) {
					rawMessages.add(tempMessage);
					if(!ChatCommandManager.chatCommandManager(tempMessage) && !checkNewMessageType(tempMessage)) {
						if(selectedType == MessageType.WHISPER) {
							CommandSendMessage.writeWhisper(tempMessage, currentWhisper.substring(0, currentWhisper.length()-3));
						}
						else {
							CommandSendMessage.write(tempMessage, selectedType);
						}
					}
					numberUpArrow = 1;
					numberMessageSent++;
					cursorPosition = 0;
					cursorShift = 0;
					i++;
				}
				tempMessage =  "";
				tempLength = 0;
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
	
	private static boolean checkNewMessageType(String msg) {
		if(msg.length() >= 3 && msg.startsWith("/")) {
			if(msg.startsWith("/i") && msg.length() >= 4) {
				msg = msg.trim();
				CommandParty.invitePlayer(msg.substring(getDataWithoutSpace(3, msg)));
				return true;
			}
		}
		return false;
	}
	
	private static int getDataWithoutSpace(int start, String msg) {
		while(start < msg.length()) {
			if((msg.charAt(start) != ' ' && start+1 < msg.length() && msg.charAt(start+1) != ' ') || (msg.charAt(start) != ' ' && start+1 == msg.length())) {
				return start;
			}
			start++;
		}
		return -1;
	}
	
	private static boolean checkNewMessageType(char c) {
		if(tempMessage.length() == 2 && tempMessage.startsWith("/") && c == ' ') {
			if(tempMessage.startsWith("/s")) {
				selectedType = MessageType.SAY;
				currentWhisper = "";
				return true;
			}
			else if(tempMessage.startsWith("/p")) {
				selectedType = MessageType.PARTY;
				currentWhisper = "";
				return true;
			}
			else if(tempMessage.startsWith("/g")) {
				selectedType = MessageType.GUILD;
				currentWhisper = "";
				return true;
			}
			else if(tempMessage.startsWith("/y")) {
				selectedType = MessageType.YELL;
				currentWhisper = "";
				return true;
			}
			else if(tempMessage.startsWith("/r") && previousWhisper != null) {
				currentWhisper = previousWhisper+" : ";
				selectedType = MessageType.WHISPER;
				return true;
			}
		}
		else if(tempMessage.length() >= 2 && tempMessage.startsWith("/")) {
			if(tempMessage.startsWith("/w ") && tempMessage.length() >= 4 && tempMessage.charAt(3) != ' ' && c == ' ') {
				currentWhisper = tempMessage.substring(3)+" : ";
				selectedType = MessageType.WHISPER;
				return true;
			}
		}
		return false;
	}
	
	public static void setWhisper(String name) {
		currentWhisper = name+" : ";
		selectedType = MessageType.WHISPER;
		tempMessage = "";
		cursorPosition = 0;
		cursorShift = 0;
		selectedLength = 0;
		selectedQuadLength = 0;
		selectedStarts = 0;
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
			number+= getNumberLine(messages.get(i));
			i++;
		}
		return number;
	}
	
	/*private static void addMessage() {
		String temp = tempMessage;
		if(temp != null && !temp.equals("")) {
			messages.add(new Message(temp, true, Color.white));
			totalNumberLine = getTotalNumberLine();
			numberLineLastMessages = getNumberLineLast(NUMBER_LAST_MESSAGE_TAKEN);
		}
	}*/
	
	public static void addMessage(Message message) {
		messages.add(message);
		totalNumberLine = getTotalNumberLine();
	}
	
	private static int getNumberLineLast(int number) {
		int i = messages.size()-1;
		int height = 0;
		while(i > messages.size()-number && i >= 0) {
			height+= getNumberLine(messages.get(i));
			i--;
		}
		return height;
	}
	
	public static void clearChat() {
		messages.clear();
		rawMessages.clear();
		numberMessageSent = 0;
		cursorPosition = 0;
		totalNumberLine = 0;
		selectedLength = 0;
		selectedQuadLength = 0;
		selectedStarts = 0;
		tempLength = 0;
		cursorShift = 0;
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
	
	public static ArrayList<Message> getMessageList() {
		return messages;
	}
	
	public static ArrayList<String> getRawMessageList() {
		return rawMessages;
	}
	
	public static Color convClassColor(String classe) {
		if(classe.equals(druid)) {
			return ClassColor.DRUID_COLOR;
		}
		if(classe.equals(hunter)) {
			return ClassColor.HUNTER_COLOR;
		}
		if(classe.equals(mage)) {
			return ClassColor.MAGE_COLOR;
 		}
		if(classe.equals(paladin)) {
			return ClassColor.PALADIN_COLOR;
		}
		if(classe.equals(priest)) {
			return ClassColor.PRIEST_COLOR;
		}
		if(classe.equals(rogue)) {
			return ClassColor.ROGUE_COLOR;
		}
		if(classe.equals(shaman)) {
			return ClassColor.SHAMAN_COLOR;
		}
		if(classe.equals(warlock)) {
			return ClassColor.WARLOCK_COLOR;
		}
		if(classe.equals(warrior)) {
			return ClassColor.WARRIOR_COLOR;
		}
		return Color.white;
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
	
	public static void setPreviousWhisper(String name) {
		previousWhisper = name;
	}
}
