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
import com.mideas.rpg.v2.utils.Input;
import com.mideas.rpg.v2.utils.InputBar;

public class ChatFrame {

	private static int INPUT_BAR_Y = Display.getHeight()-168;
	private final static InputBar inputBar = new InputBar(30, INPUT_BAR_Y, 510);
	private static ArrayList<String> rawMessages = new ArrayList<String>();
	private static ArrayList<Message> messages = new ArrayList<Message>();
	private static Color selectedColor = new Color(1, 1, 1, .5f); 
	private static int defaultHeight = Display.getHeight()-285;
	private static MessageType selectedType = MessageType.SAY;
	private final static int NUMBER_LAST_MESSAGE_TAKEN = 100;
	private static Color bgColor = new Color(0, 0, 0, .35f);
	private final static int OPACITY_START_DECREASE_TIMER = 30000;
	private final static int OPACITY_DECREASE_TIMER = 7000;
	private final static int MAXIMUM_MESSAGES = 128; //same as wow BC
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
		Draw.drawColorQuad(30, Display.getHeight()-280-yResize, 510+xResize, 115+yResize, bgColor);
		Draw.drawQuad(Sprites.chat_button, 3, Display.getHeight()-268);
		if(tempMessage.length() >= 1 || chatActive) {
			inputBar.draw();
			TTF2.chat.drawString(44, INPUT_BAR_Y+7*Mideas.getDisplayYFactor(), selectedType.getDefaultText()+currentWhisper, selectedType.getColor());
			Draw.drawColorQuad(selectedStarts+TTF2.chat.getWidth(selectedType.getDefaultText()+currentWhisper), INPUT_BAR_Y+6*Mideas.getDisplayYFactor(), selectedQuadLength, 20, selectedColor);
			TTF2.chat.drawString(44+TTF2.chat.getWidth(selectedType.getDefaultText()+currentWhisper), INPUT_BAR_Y+7*Mideas.getDisplayYFactor(), tempMessage.substring(tempLength), selectedType.getColor());
		}
		if(chatActive) {
			if(TTF2.chat.getWidth(selectedType.getDefaultText()+currentWhisper+tempMessage.substring(tempLength)) >= maxLength) {
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
				Draw.drawColorQuad(45+cursorShift+TTF2.chat.getWidth(selectedType.getDefaultText()+currentWhisper), INPUT_BAR_Y+9*Mideas.getDisplayYFactor(), 4*Mideas.getDisplayXFactor(), 15*Mideas.getDisplayYFactor(), selectedType.getColor());
			}
		}
		if(messages.size() > MAXIMUM_MESSAGES) {
			while(messages.size() >= MAXIMUM_MESSAGES) {
				messages.remove(0);
			}
			totalNumberLine = getNumberLineLast(NUMBER_LAST_MESSAGE_TAKEN);
		}
		int k = 0;
		yDraw = -totalNumberLine*TTF2.chat.getLineHeight()+Display.getHeight()-175+xShift;
		//yDraw = -numberLineLastMessages*TTF2.chat.getLineHeight()+Display.getHeight()-175+xShift;
		//System.out.println(numberLineLastMessages);
		xDraw = 40;
		while(k < messages.size()) {
			xDraw = 40;
				if(yDraw <= Display.getHeight()-185) {
					if(messages.get(k).getOpacity() > 0 && System.currentTimeMillis()-messages.get(k).lastSeenTimer() >= OPACITY_START_DECREASE_TIMER) {
						messages.get(k).decreaseOpacity(-1/(Mideas.FPS*OPACITY_DECREASE_TIMER/1000f));
					}
					int j = 0;
					if(messages.get(k).getAuthor() != null) {
						if(yDraw >= Display.getHeight()-280-yResize && messages.get(k).getOpacity() > 0) {
							TTF2.chat.drawString(xDraw+1, yDraw, messages.get(k).getAuthorText(), Color.black, messages.get(k).getOpacity());
							TTF2.chat.drawString(xDraw, yDraw, messages.get(k).getAuthorText(), messages.get(k).getColor(), messages.get(k).getOpacity());
						}
						xDraw+= TTF2.chat.getWidth(messages.get(k).getAuthorText());
					}
					while(j < messages.get(k).getMessage().length()) {
						if(yDraw >= Display.getHeight()-280-yResize && messages.get(k).getOpacity() > 0) {
							TTF2.chat.drawChar(xDraw+1, yDraw, messages.get(k).getMessage().charAt(j), Color.black, messages.get(k).getOpacity());
							TTF2.chat.drawChar(xDraw, yDraw, messages.get(k).getMessage().charAt(j), messages.get(k).getColor(), messages.get(k).getOpacity());
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
	
	public static boolean event() throws NumberFormatException {
		if(chatActive) {
			Keyboard.enableRepeatEvents(true);
			if(Keyboard.getEventKey() == 1) { //escape
				setChatActive(false);
				resetTempMessage();
			}
			if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) { //left shift
				if(Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) {
					if(Keyboard.getEventKey() == 203) { // shift CTRL left arrow
						selectCTRLLeftArrow();
						return true;
					}
					else if(Keyboard.getEventKey() == 205) { // shift CTRL right arrow
						selectCTRLRightArrow();
						return true;
					}
				}
				if(Keyboard.getEventKey() == 203) { //shift left arrow
					selectLeftArrow();
					return true;
				}
				else if(Keyboard.getEventKey() == 205) { // shift right arrow
					selectRightArrow();
					return true;
				}
			}
			if(Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) { //ctrl down
				if(Keyboard.getEventKey() == 14) { //delete
					CTRLDelete();
					tempLength = 0;
					resetSelectedPosition();
					return true;
				}
				else if(Keyboard.getEventKey() == Keyboard.KEY_V) { // c/c
	                write(Sys.getClipboard().replace("\n", "").replace("\t", "").replace("\r", ""));
	                cursorPosition+= Sys.getClipboard().replace("\n", "").replace("\t", "").replace("\r", "").length();
					return true;
	            }
				else if(Keyboard.getEventKey() == 203) { //left arrow
					CTRLleftArrow();
					resetSelectedPosition();
					return true;
				}
				else if(Keyboard.getEventKey() == 205) { //right arrow
					CTRLrightArrow();
					resetSelectedPosition();
					return true;
				}
				else if(Keyboard.getEventKey() == Keyboard.KEY_C) {
					copySelected();
					return true;
				}
			}
			if(Keyboard.getEventKey() == 14) { //delete
				delete();
				resetSelectedPosition();
				return true;
			}
			if(Keyboard.getEventKey() == 200) {  //up arrow
				upArrow();
				resetSelectedPosition();
				return true;
			}
			if(Keyboard.getEventKey() == 208) { //down arrow
				downArrow();
				resetSelectedPosition();
				return true;
			}
			if(Keyboard.getEventKey() == 203) { //left arrow
				leftArrow();
				resetSelectedPosition();
				return true;
			}
			if(Keyboard.getEventKey() == 205) { //right arrow
				rightArrow();
				resetSelectedPosition();
				return true;
			}
			if(Keyboard.getEventKey() == 211) { //suppr
				suppr();
				resetSelectedPosition();
				return true;
			}
			if(Keyboard.getEventKey() != Keyboard.KEY_RETURN && Keyboard.getEventKey() != 156 && Keyboard.getEventKey() != Keyboard.KEY_LSHIFT && Keyboard.getEventKey() != Keyboard.KEY_LCONTROL && Keyboard.getEventKey() != Keyboard.KEY_RCONTROL && Keyboard.getEventKey() != Keyboard.KEY_RSHIFT) { //write
				if(tempMessage.length() < MAXIMUM_LENGTH) {
					char tempChar = Keyboard.getEventCharacter();
					if(tempChar == ' ' && checkNewMessageType(tempChar)) {
						cursorPosition = 0;
						cursorShift = 0;
						tempMessage =  "";
						tempLength = 0;
						return true;
					}
					else if(isValidCharacter(tempChar)) {
						write(tempChar);
						resetSelectedPosition();
						return true;
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
							getNumberLineChar(tempMessage);
							getNumberLineDivide(tempMessage);
						}
					}
					numberUpArrow = 1;
					numberMessageSent++;
					cursorPosition = 0;
					cursorShift = 0;
				}
				tempMessage =  "";
				tempLength = 0;
				resetSelectedPosition();
			}
			chatActive = !chatActive;
		}
        else if(Keyboard.getEventKey() == 201 && !chatActive) {  //scroll up
        	scrollUp();
			return true;
        }
        else if(Keyboard.getEventKey() == 209 && !chatActive) {  //scroll down
        	scrollDown();
			return true;
        }
        return false;
	}
	
	private static boolean isValidCharacter(char c) {
		return c >= ' ' && c <= 'ÿ';
	}
	
	public static boolean mouseEvent() {
		topArrow = false;
		bottomArrow = false;
		toBottomArrow = false;
		hoverHeightResize = false;
		hoverWidthResize = false;
		hoverAllResize = false;            //TODO: Replace else if by ButtonArrow
		if(Mideas.getHover() && Mideas.mouseX() >= 3 && Mideas.mouseX() <= 27 && Mideas.mouseY() >= Display.getHeight()-236 && Mideas.mouseY() <= Display.getHeight()-213) {
			topArrow = true;
			Mideas.setHover(false);
		}
		else if(Mideas.getHover() && Mideas.mouseX() >= 3 && Mideas.mouseX() <= 27 && Mideas.mouseY() >= Display.getHeight()-202 && Mideas.mouseY() <= Display.getHeight()-179) {
			bottomArrow = true;
			Mideas.setHover(false);
		}
		else if(Mideas.getHover() && Mideas.mouseX() >= 3 && Mideas.mouseX() <= 27 && Mideas.mouseY() >= Display.getHeight()-168 && Mideas.mouseY() <= Display.getHeight()-145) {
			toBottomArrow = true;
			Mideas.setHover(false);
		}
		else if(Mideas.getHover() && Mideas.mouseX() >= 535+xResize && Mideas.mouseX() <= 545+xResize && Mideas.mouseY() >= Display.getHeight()-285-yResize && Mideas.mouseY() <= Display.getHeight()-275-yResize) {
			hoverAllResize = true;
			Mideas.setHover(false);
		}
		else if(Mideas.getHover() && Mideas.mouseX() >= 3 && Mideas.mouseX() <= 513+xResize && Mideas.mouseY() >= Display.getHeight()-285-yResize && Mideas.mouseY() <= Display.getHeight()-275-yResize) {
			hoverHeightResize = true;
			Mideas.setHover(false);
		}
		else if(Mideas.getHover() && Mideas.mouseX() >= 535+xResize && Mideas.mouseX() <= 545+xResize && Mideas.mouseY() >= Display.getHeight()-280-yResize && Mideas.mouseY() <= Display.getHeight()-150) {
			hoverWidthResize = true;
			Mideas.setHover(false);
		}
		else if(chatActive && Mideas.getHover() && inputBar.isHover()) {
			Mideas.setHover(false);
			if(Mouse.getEventButtonState()) {
				if(Mouse.getEventButton() == 0 || Mouse.getEventButton() == 1) {
					setChatActive(true);
					return true;
				}
			}
		}
		if(Mideas.mouseX() >= 40 && Mideas.mouseX() <= 535+xResize && Mideas.mouseY() >= Display.getHeight()-285-yResize && Mideas.mouseY() <= Display.getHeight()-173) {
			if(!Mouse.getEventButtonState()) {
				int i = 0;
				int j = 0;
				int k = 0;
				int x = 40;
				int xAuthor = 0;
				boolean hasAuthor = false;
				int y = -totalNumberLine*TTF2.chat.getLineHeight()+Display.getHeight()-175+xShift;
				while(i < messages.size()) {
					x = 40;
					j = 0;
					k = 0;
	 				if(messages.get(i).getAuthor() != null && !messages.get(i).getAuthor().equals("")) {
						xAuthor = x;
						while(k < messages.get(i).getAuthorText().length()-1) {
							if(messages.get(i).getAuthorText().charAt(k) == '[' && messages.get(i).getAuthorText().charAt(k+1) == messages.get(i).getAuthor().charAt(0)) {
								hasAuthor = true;
								break;
							}
							xAuthor+= TTF2.chat.getWidth(messages.get(i).getAuthorText().charAt(k));
							k++;
						}
						if(hasAuthor && Mideas.getHover() && Mideas.mouseX() >= xAuthor && Mideas.mouseX() <= xAuthor+TTF2.chat.getWidth(messages.get(i).getAuthor()+"[]") && Mideas.mouseY() >= y && Mideas.mouseY() < y+TTF2.chat.getLineHeight()) {
							Mideas.setHover(false);
							if(Mouse.getEventButton() == 0) {
								if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) {
									if(chatActive) {
										write(messages.get(i).getAuthor());
									}
									else {
										
									}
								}
								else {
									setChatActive(true);
									setWhisper(messages.get(i).getAuthor());
								}
							}
							else if(Mouse.getEventButton() == 1) {
								
							}
						}
						x+= TTF2.chat.getWidth(messages.get(i).getAuthorText());
					}
					while(j < messages.get(i).getMessage().length()) {
						x+= TTF2.chat.getWidth(messages.get(i).getMessage().charAt(j));
						if(x-40 > maxLength-10 && j < messages.get(i).getMessage().length()) {
							y+= TTF2.chat.getLineHeight();
							x = 40;
						}
						j++;
					}
					if(y > Display.getHeight()-185 || !(i < messages.size())) {
						break;
					}
					//System.out.println("MOUSE: "+Mideas.mouseY()+" Y: "+y);
					y+= TTF2.chat.getLineHeight();
					i++;
				}
			}
		}
		int maxResize = 1200;
		if(allResizing) {
			if(Mideas.mouseY() >= defaultHeight-maxResize && Mideas.mouseY() <= defaultHeight) {
				yResize = -Mideas.mouseY()+defaultHeight;
			}
			if(Mideas.mouseX() >= defaultWidth && Mideas.mouseX() <= defaultWidth+maxResize) {
				xResize = Mideas.mouseX()-defaultWidth;
				inputBar.setXSize(510+xResize);
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
				inputBar.setXSize(510+xResize);
				totalNumberLine = getTotalNumberLine();
			}
		}
		if(Mouse.getEventButton() == 0) {
			if(Mouse.getEventButtonState()) {
				if(topArrow) {
					scrollUp();
					return true;
				}
				else if(bottomArrow) {
					scrollDown();
					return true;
				}
				else if(toBottomArrow) {
					xShift = 0;
					return true;
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
	
	private static void getNumberLineChar(String msg) {
		int i = 0;
		int shift = 0;
		int line = 0;
		while(i < msg.length()) {
			shift+= TTF2.chat.getWidth(msg.charAt(i));
			if(shift >= maxLength+30) {
				line++;                                                        //Both works well
				shift = 0;
			}
			i++;
		}
		System.out.println("Char method: "+line);
	}
	
	private static int getNumberLineDivide(String msg) {
		int line = Math.max(TTF2.chat.getWidth(msg)/(maxLength+30), 1);
		//System.out.println("Divide method: "+line);
		return line;
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
	
	private static void resetTempMessage() {
		tempMessage = "";
		resetSelectedPosition();
		cursorPosition = 0;
		cursorShift = 0;
	}
	
	private static boolean checkNewMessageType(char c) {
		if(tempMessage.length() == 2 && tempMessage.startsWith("/") && c == ' ') {
			if(tempMessage.startsWith("/s")) {
				selectedType = MessageType.SAY;
				currentWhisper = "";
				return true;
			}
			if(tempMessage.startsWith("/p")) {
				selectedType = MessageType.PARTY;
				currentWhisper = "";
				return true;
			}
			if(tempMessage.startsWith("/g")) {
				selectedType = MessageType.GUILD;
				currentWhisper = "";
				return true;
			}
			else if(tempMessage.startsWith("/o")) {
				selectedType = MessageType.OFFICER;
				currentWhisper = "";
				return true;
			}
			if(tempMessage.startsWith("/y")) {
				selectedType = MessageType.YELL;
				currentWhisper = "";
				return true;
			}
			if(tempMessage.startsWith("/r") && previousWhisper != null) {
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
	
	public static void updateSize() {
		INPUT_BAR_Y = Display.getHeight()-168;
		inputBar.setY(INPUT_BAR_Y);
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
			if(tempMessage.charAt(cursorPosition-1) == ' ' || tempMessage.charAt(cursorPosition-1) == ',') {
				cursorShift-= TTF2.chat.getWidth(tempMessage.charAt(cursorPosition-1));
				cursorPosition--;
				i--;
			}
		}
		while(i > 0) {
			cursorPosition--;
			cursorShift-= TTF2.chat.getWidth(tempMessage.charAt(i-1));
			i--;
			if(i <= 0) {
				return true;
			}
			if(tempMessage.charAt(i-1) == ' ' || tempMessage.charAt(i-1) == ',') {
				return true;
			}
		}
		return false;
	}
	
	private static boolean CTRLrightArrow() {
		int i = cursorPosition;
		if(i < tempMessage.length()) {
			if(tempMessage.length() != 0) {
				if(tempMessage.charAt(cursorPosition+1) == ' ' || tempMessage.charAt(cursorPosition+1) == ',') {
					cursorShift+= TTF2.chat.getWidth(tempMessage.charAt(cursorPosition+1));
					cursorPosition++;
					i++;
				}
			}
			while(i < tempMessage.length() && i >= 0) {
				cursorPosition++;
				cursorShift+= TTF2.chat.getWidth(tempMessage.charAt(i));
				i++;
				if(i >= tempMessage.length()) {
					return true;
				}
				if(tempMessage.charAt(i) == ' ' || tempMessage.charAt(i) == ',') {
					cursorPosition++;
					cursorShift+= TTF2.chat.getWidth(tempMessage.charAt(i));
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
			if(tempMessage.charAt(cursorPosition-1) == ' ' || tempMessage.charAt(cursorPosition-1) == ',') {
				cursorShift-= TTF2.chat.getWidth(tempMessage.charAt(cursorPosition-1));
				selectedQuadLength-= TTF2.chat.getWidth(tempMessage.charAt(cursorPosition-1));
				selectedLength--;
				cursorPosition--;
				i--;
			}
		}
		while(i > 0) {
			cursorPosition--;
			cursorShift-= TTF2.chat.getWidth(tempMessage.charAt(i-1));
			selectedQuadLength-= TTF2.chat.getWidth(tempMessage.charAt(i-1));
			selectedLength--;
			i--;
			if(i <= 0) {
				return true;
			}
			if(tempMessage.charAt(i-1) == ' ' || tempMessage.charAt(i-1) == ',') {
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
				if(tempMessage.charAt(cursorPosition+1) == ' ' || tempMessage.charAt(cursorPosition+1) == ',') {
					cursorShift+= TTF2.chat.getWidth(tempMessage.charAt(cursorPosition+1));
					selectedQuadLength+= TTF2.chat.getWidth(tempMessage.charAt(cursorPosition+1));
					selectedLength++;
					cursorPosition++;
					i++;
				}
			}
			while(i < tempMessage.length() && i >= 0) {
				cursorPosition++;
				cursorShift+= TTF2.chat.getWidth(tempMessage.charAt(i));
				selectedQuadLength+= TTF2.chat.getWidth(tempMessage.charAt(i));
				selectedLength++;
				i++;
				if(i >= tempMessage.length()) {
					return true;
				}
				if(tempMessage.charAt(i) == ' ' || tempMessage.charAt(i) == ',') {
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
		if(numberMessageSent >= numberUpArrow) {
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
    	resetMessagesOpacity();
	}
	
	private static void scrollDown() {
    	if(xShift/TTF2.chat.getLineHeight() > 0) {
    		xShift-= TTF2.chat.getLineHeight();
    	}
    	resetMessagesOpacity();
	}
	
	private static void resetMessagesOpacity() {
		int i = 0;
		long time = System.currentTimeMillis();
		while(i < messages.size()) {
			messages.get(i).setOpacity(1);
			messages.get(i).setLastSeenTimer(time);
			i++;
		}
	}
	
	private static boolean CTRLDelete() {
		int i = cursorPosition;
		String temp = tempMessage.substring(cursorPosition);
		if(tempMessage.length() != 0) {
			if(tempMessage.charAt(tempMessage.length()-1) == ' ' || tempMessage.charAt(tempMessage.length()-1) == ',') {
				tempMessage = tempMessage.substring(0, tempMessage.length()-1);
				i--;
			}
		}
		while(i > 0) {
			if(tempMessage.charAt(i-1) == ' ' || tempMessage.charAt(i-1) == ',') {
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
				cursorShift-= TTF2.chat.getWidth(tempMessage.charAt(cursorPosition-1));
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
		cursorPosition+= add.length();
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
		cursorPosition++;
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
		if(we) {
			Input.setInactiveAllInput();
		}
		chatActive = we;
	}
	
	public static void setPreviousWhisper(String name) {
		previousWhisper = name;
	}
}
