package com.mideas.rpg.v2.utils;

import org.lwjgl.input.Keyboard;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.TTF;

public class IntegerInput {

	private String text = "";
	private int cursorPosition;
	private int cursorShift;
	private TTF font;
	private int maxLength;
	
	public IntegerInput(TTF font, int maxLength) {
		this.font = font;
		this.maxLength = maxLength;
	}
	
	public boolean event() {
		if(Keyboard.getEventKey() == 0) {
			return false;
		}
		if(Keyboard.getEventKey() == 14) {
			delete();
			return true;
		}
		if(Keyboard.getEventKey() == 203) {
			leftArrow();
			return true;
		}
		if(Keyboard.getEventKey() == 205) {
			rightArrow();
			return true;
		}
		if(Keyboard.getEventKey() == 211) {
			suppr();
			return true;
		}
		if(Keyboard.getEventKey() != Keyboard.KEY_RETURN && Keyboard.getEventKey() != 156 && Keyboard.getEventKey() != Keyboard.KEY_LSHIFT && Keyboard.getEventKey() != Keyboard.KEY_LCONTROL && Keyboard.getEventKey() != Keyboard.KEY_RCONTROL && Keyboard.getEventKey() != Keyboard.KEY_RSHIFT && Keyboard.getEventKey() != Keyboard.KEY_TAB) { //write
			if(this.text.length() < this.maxLength) {
				write(Keyboard.getEventCharacter());
			}
			return true;
		}
		return false;
	}
	
	public int maximumValue() {
		return 99;
	}
	
	public void write(char c) {
		if(!Mideas.isInteger(c)) {
			return;
		}
		if(Integer.parseInt(this.text+c) <= maximumValue()) {
			if(this.cursorPosition != this.text.length()) {
				this.text = this.text.substring(0, this.cursorPosition)+c+this.text.substring(this.cursorPosition);
			}
			else {
				this.text+= c;
			}
			this.cursorPosition++;
			this.cursorShift+= this.font.getWidth(c);
		}
	}
	
	public String getText() {
		return this.text;
	}
	
	public void setText(char c) {
		this.text = String.valueOf(c);
		checkLength();
		this.cursorPosition = this.text.length();
	}
	
	public void setText(String s) {
		this.text = s;
		checkLength();
		this.cursorPosition = this.text.length();
	}
	
	public void setText(int i) {
		this.text = Integer.toString(i);
		checkLength();
		this.cursorPosition = this.text.length();
	}
	
	public int getCursorShift() {
		return this.cursorShift;
	}
	
	private void checkLength() {
		if(this.text.length() > this.maxLength) {
			this.text = this.text.substring(0, this.maxLength);
		}
	}
	
	private void delete() {
		if(this.text.length() > 0) {
			if(this.cursorPosition > 0) {
				this.cursorShift-= this.font.getWidth(this.text.substring(this.cursorPosition-1).charAt(0));
			}
			if(this.cursorPosition == this.text.length()) {
				this.text = this.text.substring(0, this.text.length()-1);
			}
			else {
				this.text = this.text.substring(0, this.cursorPosition-1)+this.text.substring(this.cursorPosition, this.text.length());
			}
			this.cursorPosition--;
		}
	}
	
	private void suppr() {
		if(this.text.length() > 0) {
			if(this.cursorPosition < this.text.length()) {
				this.text = this.text.substring(0, this.cursorPosition)+this.text.substring(this.cursorPosition+1, this.text.length());
			}
		}
	}
	
	private void leftArrow() {
		if(this.cursorPosition > 0) {
			this.cursorPosition--;
			this.cursorShift-= this.font.getWidth(this.text.charAt(this.cursorPosition));
		}
	}
	
	private void rightArrow() {
		if(this.cursorPosition < this.text.length()) {
			this.cursorPosition++;
			this.cursorShift+= this.font.getWidth(this.text.charAt(this.cursorPosition-1));
		}
	}
}
