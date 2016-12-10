package com.mideas.rpg.v2.utils;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.ArrayList;

import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;

import com.mideas.rpg.v2.TTF;
import com.mideas.rpg.v2.FontManager;
import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.chat.ChatFrame;

public class Input {

	private String text = "";
	private int cursorPosition;
	private int cursorShift;
	private int tempLength;
	private int selectedLength;
	private int selectedQuadLength;
	private int selectedStarts;
	private TTF font;
	private int maxLength;
	private boolean isActive;
	private boolean multipleLine;
	private boolean debugActive;
	private float xDefault;
	private float xDraw;
	private float y;
	private float maxWidth;
	private final static ArrayList<Input> inputList = new ArrayList<Input>();
	private static Input activatedInput;
	
	public final static int ESCAPE_CHAR_VALUE = 27;
	public final static int ENTER_CHAR_VALUE = 13;
	
	public Input(TTF font, int maxLength, boolean multipleLine, float x, float y, float maxWidth, boolean isActive) {
		this.multipleLine = multipleLine;
		this.maxLength = maxLength;
		this.maxWidth = maxWidth;
		setIsActive(isActive);
		this.xDefault = x;
		this.font = font;
		this.y = y;
	}
	
	public Input(TTF font, int maxLength, boolean multipleLine, float x, float y, float maxWidth) {
		this.multipleLine = multipleLine;
		this.maxLength = maxLength;
		this.maxWidth = maxWidth;
		this.xDefault = x;
		this.font = font;
		this.y = y;
	}
	
	public Input(TTF font, int maxLength, boolean debugActive, boolean multipleLine, float x, float y, float maxWidth) {
		this.multipleLine = multipleLine;
		this.debugActive = debugActive;
		this.maxLength = maxLength;
		this.maxWidth = maxWidth;
		this.xDefault = x;
		this.font = font;
		this.y = y;
	}
	
	public Input(TTF font, int maxLength, boolean multipleLine, boolean debugActive, boolean isActive) {
		this.multipleLine = multipleLine;
		this.debugActive = debugActive;
		this.maxLength = maxLength;
		setIsActive(isActive);
		inputList.add(this);
		this.font = font;
	}
	
	public Input(TTF font, int maxLength, boolean multipleLine, boolean debugActive) {
		this(font, maxLength, multipleLine, debugActive, false);
	}
	
	public Input(int font_size, int maxLength, boolean multipleLine, boolean debugActive) {
		this(FontManager.get("FRIZQT", font_size), maxLength, multipleLine, debugActive, false);
	}
	
	public void draw() {
		int i = 0;
		if(this.multipleLine) {
			
		}
		else {
			float x = this.xDraw+this.xDefault;
			this.font.drawBegin();
			while(i < this.text.length()) {
				if(x >= this.xDefault) {
					this.font.drawCharPart(x+1, this.y, this.text.charAt(i), Color.BLACK);
					this.font.drawCharPart(x, this.y, this.text.charAt(i), Color.WHITE);
				}
				x+= this.font.getWidth(this.text.charAt(i));
				if(x >= this.xDefault+this.maxWidth || (i < this.text.length()-1 && x+this.font.getWidth(this.text.charAt(i)) >= this.xDefault+this.maxWidth)) {
					break;
				}
				i++;
			}
			this.font.drawEnd();
			if(!this.isActive) {
				return;
			}
			if(System.currentTimeMillis()%1000 < 500) {
				Draw.drawColorQuad(this.xDraw+this.cursorShift+this.xDefault, this.y, 6*Mideas.getDisplayXFactor(), 24*Mideas.getDisplayYFactor(), Color.WHITE);
			}
		}
	}
	
	public boolean event() {
		if(!this.isActive) {
			return false;
		}
		if(Keyboard.getEventKey() == 0) {
			return false;
		}
		if(this.debugActive) {
			System.out.println("Key pressed.");
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) { //left shift
			if(Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) {
				if(Keyboard.getEventKey() == 203) { // shift CTRL left arrow
					selectCTRLLeftArrow();
					return true;
				}
				if(Keyboard.getEventKey() == 205) { // shift CTRL right arrow
					selectCTRLRightArrow();
					return true;
				}
			}
			if(Keyboard.getEventKey() == 203) { //shift left arrow
				selectLeftArrow();
				return true;
			}
			if(Keyboard.getEventKey() == 205) { // shift right arrow
				selectRightArrow();
				return true;
			}
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) { //ctrl down
			if(Keyboard.getEventKey() == 14) { //delete
				CTRLDelete();
				this.tempLength = 0;
				resetSelectedPosition();
				return true;
			}
			if(Keyboard.getEventKey() == Keyboard.KEY_V) { // c/c
				String temp = Sys.getClipboard().replace("\n", "").replace("\t", "").replace("\r", "");
				write(temp);
				this.cursorPosition+= temp.length();
				return true;
			}
			if(Keyboard.getEventKey() == 203) { //left arrow
				CTRLleftArrow();
				resetSelectedPosition();
				return true;
			}
			if(Keyboard.getEventKey() == 205) { //right arrow
				CTRLrightArrow();
				resetSelectedPosition();
				return true;
			}
			if(Keyboard.getEventKey() == Keyboard.KEY_C) {
				copySelected();
				return true;
			}
		}
		if(Keyboard.getEventKey() == 14) { //delete
			delete();
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
		if(!(Keyboard.getEventKey() != Keyboard.KEY_LCONTROL && Keyboard.getEventKey() != Keyboard.KEY_RCONTROL && Keyboard.getEventKey() != Keyboard.KEY_RSHIFT && Keyboard.getEventKey() != Keyboard.KEY_TAB)) { //write
			return false;
		}
		if(Keyboard.getEventKey() == Keyboard.KEY_RETURN || Keyboard.getEventKey() == 156 || Keyboard.getEventKey() == 28) {
			char c = Keyboard.getEventCharacter();
			if(!this.multipleLine && keyEvent(c)) {
				if(this.debugActive) {
					System.out.println("First call Event triggered");
				}
				return true;
			}
			if(this.debugActive) {
				System.out.println("No event triggered");
			}
		}
		if(this.text.length() < this.maxLength) {
			char c = Keyboard.getEventCharacter();
			if(this.debugActive) {
				System.out.println("char: "+c+", value: "+(int)c);
			}
			if(keyEvent(c)) {
				if(this.debugActive) {
					System.out.println("Second call Event triggered");
				}
				return true;
			}
			if(isValidCharacter(c) || (this.multipleLine && c == ENTER_CHAR_VALUE)) {
				write(c);
				resetSelectedPosition();
				return true;
			}
		}
		return false;
	}
	
	public void update(float x, float y, float maxWidth) {
		this.xDefault = x;
		this.y = y;
		this.maxWidth = maxWidth;
	}
	
	public void setMaxLength(int maxLength) {
		this.maxLength = maxLength;
	}
	
	public boolean keyEvent(@SuppressWarnings("unused") char c) {return false;}
	
	private static boolean isValidCharacter(char c) {
		return c >= ' ' && c <= 255;
	}
	
	public void setIsActive(boolean we) {
		if(we) {
			setInactiveAllInput();
			activatedInput = this;
		}
		this.isActive = we;
	}
	
	public static Input getSelectedInput() {
		return activatedInput;
	}
	
	public static boolean hasInputActive() {
		int i = 0;
		while(i < inputList.size()) {
			if(inputList.get(i).isActive) {
				return true;
			}
			i++;
		}
		return ChatFrame.getChatActive();
	}
	
	public static void setInactiveAllInput() {
		/*int i = 0;
		while(i < inputList.size()) {
			inputList.get(i).setIsActive(false);
			i++;
		}*/
		if(activatedInput != null) {
			activatedInput.setIsActive(false);
		}
		ChatFrame.setChatActive(false);
	}
	
	public boolean isActive() {
		return this.isActive;
	}
	
	public void write(String add) {
		if(this.cursorPosition == this.text.length()) {
			this.text+= add;
		}
		else {
			String beg = this.text.substring(0, this.cursorPosition+this.text.substring(0, this.tempLength).length());
			String end = this.text.substring(this.cursorPosition+this.text.substring(0, this.tempLength).length(), this.text.length());
			this.text = beg+add+end;
		}
		this.cursorShift+= this.font.getWidth(add);
		this.cursorPosition+= add.length();
		shiftTextLeft();
	}
	
	public void resetText() {
		this.text = "";
		this.cursorPosition = 0;
		this.cursorShift = 0;
		this.tempLength = 0;
		this.selectedLength = 0;
		this.selectedQuadLength = 0;
		this.selectedStarts = 0;
		this.xDraw = 0;
	}
	
	public void setText(String text) {
		resetText();
		write(text);
	}
	
	private void write(char add) {
		deleteSelected();
		if(this.cursorPosition == this.text.length()) {
			this.text+= add;
		}
		else {
			String beg = this.text.substring(0, this.cursorPosition+this.text.substring(0, this.tempLength).length());
			String end = this.text.substring(this.cursorPosition+this.text.substring(0, this.tempLength).length(), this.text.length());
			this.text = beg+add+end;
		}
		this.cursorShift+= this.font.getWidth(add);
		this.cursorPosition++;
		shiftTextLeft();
	}
	
	private void shiftTextLeft() {
		//System.out.println(this.cursorShift+" "+this.xDraw+" "+this.maxWidth+" LEFT "+(this.cursorShift+this.xDraw > this.maxWidth)+" "+(this.cursorPosition < this.text.length()));
		if(this.cursorShift+this.xDraw > this.maxWidth && this.cursorPosition <= this.text.length()) {
			float shiftWidth = this.maxWidth/2;
			int i = this.cursorPosition-1;
			int shift = 0;
			while(i >= 0 && shift < shiftWidth) {
				this.xDraw-= this.font.getWidth(this.text.charAt(i));
				shift+= this.font.getWidth(this.text.charAt(i));
				i--;
			}
			shiftTextLeft();
		}
	}
	
	private void shiftTextRight() {
		//System.out.println(this.cursorShift+" "+this.xDraw+" "+this.xDefault+" "+this.cursorPosition+" RIGHT "+(this.cursorShift+this.xDraw < 0));
		if(this.cursorShift+this.xDraw < 0 && this.cursorPosition >= 0) {
			int i = this.cursorPosition;
			float shiftWidth = this.maxWidth/2;
			int shift = 0;
			while(i >= 0 && shift < shiftWidth) {
				this.xDraw+= this.font.getWidth(this.text.charAt(i));
				shift+= this.font.getWidth(this.text.charAt(i));
				i--;
			}
			shiftTextRight();
		}
	}

	private void delete() {
		if(this.text.length() > 0) {
			if(this.selectedLength != 0) {
				deleteSelected();
			}
			else if(this.cursorPosition > 0) {
				this.cursorShift-= this.font.getWidth(this.text.charAt(this.cursorPosition-1));
				String beg = this.text.substring(0, this.cursorPosition-1+this.text.substring(0, this.tempLength).length());
				String end = this.text.substring(this.cursorPosition+this.text.substring(0, this.tempLength).length(), this.text.length());
				this.text = beg+end;
				this.cursorPosition--;
			}
			shiftTextRight();
		}
	}
	
	private void suppr() {
		if(this.cursorPosition+this.text.substring(0, this.tempLength).length() < this.text.length()) {
			String beg = this.text.substring(0, this.cursorPosition+this.text.substring(0, this.tempLength).length());
			String end = this.text.substring(this.cursorPosition+1+this.text.substring(0, this.tempLength).length(), this.text.length());
			this.text = beg+end;
			shiftTextLeft();
		}
	}

	private void copySelected() {
		String selected;
		if(this.selectedLength < 0) {
			selected = this.text.substring(this.cursorPosition, this.cursorPosition-this.selectedLength);
		}
		else {
			selected = this.text.substring(this.cursorPosition-this.selectedLength, this.cursorPosition);
		}
		StringSelection selection = new StringSelection(selected);
	    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
	    clipboard.setContents(selection, selection);
	}
	
	private void deleteSelected() {
		if(this.selectedLength != 0) {
			String beg = "";
			String end = "";
			if(this.selectedLength < 0) {
				if(this.cursorPosition-this.selectedLength >= 0 && this.cursorPosition-this.selectedLength <= this.text.length() ) {
					beg = this.text.substring(0, this.cursorPosition);
					end = this.text.substring(this.cursorPosition-this.selectedLength, this.text.length());
				}
			}
			else {
				beg = this.text.substring(0, this.cursorPosition-this.selectedLength);
				end = this.text.substring(this.cursorPosition, this.text.length());
				this.cursorShift = this.font.getWidth(this.text.substring(0, this.cursorPosition-this.selectedLength));
				this.cursorPosition = this.cursorPosition-this.selectedLength;
			}
			this.text = beg+end;
		}
	}
	
	private boolean CTRLDelete() {
		int i = this.cursorPosition;
		String temp = this.text.substring(this.cursorPosition);
		if(this.text.length() != 0) {
			if(this.text.charAt(this.text.length()-1) == ' ' || this.text.charAt(this.text.length()-1) == ',' || this.text.charAt(this.text.length()-1) == ENTER_CHAR_VALUE) {
				this.text = this.text.substring(0, this.text.length()-1);
				i--;
			}
		}
		while(i > 0) {
			if(this.text.charAt(i-1) == ' ' || this.text.charAt(i-1) == ',' || this.text.charAt(i-1) == ENTER_CHAR_VALUE) {
				this.text = this.text.substring(0, i);
				this.cursorPosition = this.text.length();
				this.cursorShift = this.font.getWidth(this.text);
				return true;
			}
			i--;
		}
		this.text = temp;
		this.cursorPosition = 0;
		this.cursorShift = 0;
		return false;
	}
	private void CTRLleftArrow() {
		int i = this.cursorPosition;
		if(this.text.length() != 0 && i > 0) {
			if(this.text.charAt(this.cursorPosition-1) == ' ' || this.text.charAt(this.cursorPosition-1) == ',' || this.text.charAt(this.cursorPosition-1) == ENTER_CHAR_VALUE) {
				this.cursorShift-= this.font.getWidth(this.text.charAt(this.cursorPosition-1));
				this.cursorPosition--;
				i--;
			}
		}
		while(i > 0) {
			this.cursorPosition--;
			this.cursorShift-= this.font.getWidth(this.text.charAt(i-1));
			i--;
			if(i <= 0) {
				break;
			}
			if(this.text.charAt(i-1) == ' ' || this.text.charAt(i-1) == ',' || this.text.charAt(i-1) == ENTER_CHAR_VALUE) {
				break;
			}
		}
		shiftTextRight();
	}
	
	public String getText() {
		return this.text;
	}
	
	public int getCursorShift() {
		return this.cursorShift;
	}
	
	public int getCursorPosition() {
		return this.cursorPosition;
	}
	
	private void CTRLrightArrow() {
		int i = this.cursorPosition;
		if(i < this.text.length()) {
			if(this.text.length() != 0) {
				if(this.text.charAt(this.cursorPosition+1) == ' ' || this.text.charAt(this.cursorPosition+1) == ',' || this.text.charAt(this.cursorPosition+1) == ENTER_CHAR_VALUE) {
					this.cursorShift+= this.font.getWidth(this.text.charAt(this.cursorPosition+1));
					this.cursorPosition++;
					i++;
				}
			}
			while(i < this.text.length() && i >= 0) {
				this.cursorPosition++;
				this.cursorShift+= this.font.getWidth(this.text.charAt(i));
				i++;
				if(i >= this.text.length()) {
					break;
				}
				if(this.text.charAt(i) == ' ' || this.text.charAt(i) == ',' || this.text.charAt(i) == ENTER_CHAR_VALUE) {
					this.cursorPosition++;
					this.cursorShift+= this.font.getWidth(this.text.charAt(i));
					break;
				}
			}
			shiftTextLeft();
		}
	}
	
	private void leftArrow() {
		if(this.cursorPosition > 0) {
			this.cursorPosition--;
			this.cursorShift-= this.font.getWidth(this.text.substring(this.cursorPosition+this.text.substring(0, this.tempLength).length(), this.cursorPosition+1+this.text.substring(0, this.tempLength).length()));
		}
		shiftTextRight();
	}
	
	private void rightArrow() {
		if(this.cursorPosition+this.text.substring(0, this.tempLength).length() < this.text.length()) {
			this.cursorPosition++;
			if(this.cursorPosition == this.text.length()) {
				this.cursorShift+= this.font.getWidth(this.text.substring(this.cursorPosition-1+this.text.substring(0, this.tempLength).length()));
			}
			else {
				this.cursorShift+= this.font.getWidth(this.text.substring(this.cursorPosition-1+this.text.substring(0, this.tempLength).length(), this.cursorPosition+this.text.substring(0, this.tempLength).length()));
			}
		}
		shiftTextLeft();
	}
	
	private void selectLeftArrow() {
		if(this.cursorPosition > 0) {
			if(this.selectedLength == 0) {
				this.selectedStarts = 40+this.cursorShift;
			}
			leftArrow();
			this.selectedQuadLength-= this.font.getWidth(this.text.substring(this.cursorPosition+this.text.substring(0, this.tempLength).length(), this.cursorPosition+1+this.text.substring(0, this.tempLength).length()));
			this.selectedLength--;
		}
 	}
	
	private void selectCTRLLeftArrow() {
		int i = this.cursorPosition;
		if(this.selectedLength == 0) {
			this.selectedStarts = 40+this.cursorShift;
		}
		if(this.text.length() != 0 && i > 0) {
			if(this.text.charAt(this.cursorPosition-1) == ' ' || this.text.charAt(this.cursorPosition-1) == ',' || this.text.charAt(this.cursorPosition-1) == ENTER_CHAR_VALUE) {
				this.cursorShift-= this.font.getWidth(this.text.charAt(this.cursorPosition-1));
				this.selectedQuadLength-= this.font.getWidth(this.text.charAt(this.cursorPosition-1));
				this.selectedLength--;
				this.cursorPosition--;
				i--;
			}
		}
		while(i > 0) {
			this.cursorPosition--;
			this.cursorShift-= this.font.getWidth(this.text.substring(i-1, i).charAt(0));
			this.selectedQuadLength-= this.font.getWidth(this.text.charAt(i-1));
			this.selectedLength--;
			i--;
			if(i <= 0) {
				break;
			}
			if(this.text.charAt(i-1) == ' ' || this.text.charAt(i-1) == ',' || this.text.charAt(i-1) == ENTER_CHAR_VALUE) {
				break;
			}
		}
		shiftTextRight();
		
	}
	
	private void selectCTRLRightArrow() {
		int i = this.cursorPosition;
		if(this.selectedLength == 0) {
			this.selectedStarts = 40+this.cursorShift;
		}
		if(i < this.text.length()) {
			if(this.text.length() != 0 && this.cursorPosition+1 <= this.text.length() && this.cursorPosition+1 >= 0) {
				if(this.text.charAt(this.cursorPosition+1) == ' ' || this.text.charAt(this.cursorPosition+1) == ',' || this.text.charAt(this.cursorPosition+1) == ENTER_CHAR_VALUE) {
					this.cursorShift+= this.font.getWidth(this.text.charAt(this.cursorPosition+1));
					this.selectedQuadLength+= this.font.getWidth(this.text.charAt(this.cursorPosition+1));
					this.selectedLength++;
					this.cursorPosition++;
					i++;
				}
			}
			while(i < this.text.length() && i >= 0) {
				this.cursorPosition++;
				this.cursorShift+= this.font.getWidth(this.text.charAt(i));
				this.selectedQuadLength+= this.font.getWidth(this.text.charAt(i));
				this.selectedLength++;
				i++;
				if(i >= this.text.length()) {
					break;
				}
				if(this.text.charAt(i) == ' ' || this.text.charAt(i) == ',' || this.text.charAt(i) == ENTER_CHAR_VALUE) {
					break;
				}
			}
			shiftTextLeft();
		}
	}

	private void selectRightArrow() {
		if(this.cursorPosition < this.text.length()) {
			if(this.selectedLength == 0) {
				this.selectedStarts = 40+this.cursorShift;
			}
			rightArrow();
			if(this.cursorPosition == this.text.length()) {
				this.selectedQuadLength+= this.font.getWidth(this.text.substring(this.cursorPosition-1+this.text.substring(0, this.tempLength).length()));
			}
			else {
				this.selectedQuadLength+= this.font.getWidth(this.text.substring(this.cursorPosition-1+this.text.substring(0, this.tempLength).length(), this.cursorPosition+this.text.substring(0, this.tempLength).length()));
			}
			this.selectedLength++;
		}
 	}

	private void resetSelectedPosition() {
		this.selectedQuadLength = 0;
		this.selectedLength = 0;
		this.selectedStarts = 0;
	}
	
	public int getSelectedStarts() {
		return this.selectedStarts;
	}
	
	public int getSelectedQuadLength() {
		return this.selectedQuadLength;
	}
}