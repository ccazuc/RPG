package com.mideas.rpg.v2.utils;

import org.lwjgl.input.Keyboard;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.render.TTF;

public class IntegerInput extends Input {
	
	private int textValue;
	
	public IntegerInput(TTF font, int maxLength) {
		super(font, maxLength, false, false);
	}
	
	public IntegerInput(TTF font, float x, float y, int maxLength, float maxWidth, float cursorWidth, float cursorHeight, float textXOffset, float textYOffset, Color textColor) {
		super(font, maxLength, false, x, y, maxWidth, cursorWidth, cursorHeight, "", textXOffset, textYOffset, textColor);
	}
	
	@Override
	public boolean event() {
		if(!this.isActive) {
			return false;
		}
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
		if(Keyboard.getEventKey() != Keyboard.KEY_RETURN && Keyboard.getEventKey() != 156 && Keyboard.getEventKey() != Keyboard.KEY_LSHIFT && Keyboard.getEventKey() != Keyboard.KEY_LCONTROL && Keyboard.getEventKey() != Keyboard.KEY_RCONTROL && Keyboard.getEventKey() != Keyboard.KEY_RSHIFT) {
			if(keyEvent(Keyboard.getEventCharacter())) {
				return true;
			}
			write(Keyboard.getEventCharacter());
			return true;
		}
		return false;
	}
	
	@SuppressWarnings("unused")
	public boolean checkValue(int value) {return true;}
	
	public void write(char c) {
		if(!StringUtils.isInteger(c)) {
			return;
		}
		this.lastWrite = Mideas.getLoopTickTimer();
		if(this.text.length() >= this.maxLength) {
			return;
		}
		String tmp;
		if(this.cursorPosition != this.text.length() && this.text.length() > 0) {
			tmp = this.text.substring(0, this.cursorPosition)+c+this.text.substring(this.cursorPosition);
		}
		else {
			tmp = this.text+c;
		}
		int tmpValue = Integer.parseInt(tmp);
		if(checkValue(tmpValue)) {
			this.text = tmp;
			this.textValue = tmpValue;
			this.cursorPosition++;
			this.cursorShift = this.font.getWidth(tmp);
			checkLength();
		}
	}
	
	public void updateTextValue()
	{
		if (this.text.length() > 0)
		{
			int tmpValue = Integer.parseInt(this.text);
			if(checkValue(tmpValue)) {
				this.textValue = tmpValue;
			}
		}
	}
	
	public void setText(char c) {
		if(!StringUtils.isInteger(c)) {
			return;
		}
		this.text = String.valueOf(c);
		this.textValue = c-'0';
		checkLength();
		this.cursorPosition = this.text.length();
	}
	
	@Override
	public void setText(String s) {
		if(!StringUtils.isInteger(s)) {
			System.out.println("IntegerInput.setText error, s in not an integer.");
			return;
		}
		int tmpValue = Integer.parseInt(s);
		if(checkValue(tmpValue)) {
			this.text = s;
			this.textValue = tmpValue;
			checkLength();
		}
		this.cursorPosition = this.text.length();
	}
	
	public void setText(int i) {
		this.text = Integer.toString(i);
		this.textValue = i;
		checkLength();
		this.cursorPosition = this.text.length();
	}
	
	private void checkLength() {
		if(this.text.length() > this.maxLength) {
			this.text = this.text.substring(0, this.maxLength);
		}
	}
	
	@Override
	public int getTextValue() {
		return this.textValue;
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
			updateTextValue();
		}
	}
	
	private void suppr() {
		if(this.text.length() > 0) {
			if(this.cursorPosition < this.text.length()) {
				this.text = this.text.substring(0, this.cursorPosition)+this.text.substring(this.cursorPosition+1, this.text.length());
				updateTextValue();
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
