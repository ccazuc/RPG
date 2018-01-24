package com.mideas.rpg.v2.utils;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.ArrayList;

import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;

import com.mideas.rpg.v2.FontManager;
import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.chat.ChatFrame;
import com.mideas.rpg.v2.utils.render.Draw;
import com.mideas.rpg.v2.utils.render.TTF;

public class Input
{

	protected final String defaultText;
	protected String text = "";
	protected int cursorPosition;
	protected int cursorShift;
	protected int tempLength;
	protected int selectedLength;
	protected int selectedQuadLength;
	protected int selectedStarts;
	protected byte cursorHeight;
	protected byte cursorWidth;
	protected byte cursorHeightSave;
	protected byte cursorWidthSave;
	protected TTF font;
	protected int maxLength;
	protected boolean isActive;
	protected boolean multipleLine;
	protected boolean debugActive;
	protected float xDefault;
	protected float xDraw;
	protected long lastWrite;
	protected long writeTickTimerActivation = 500;
	protected float y;
	protected float maxWidth = 100;
	protected float maxWidthSave = 100;
	protected short maxHeight;
	protected short maxHeightSave;
	protected short xOffset;
	protected short xOffsetSave;
	protected short yOffset;
	protected short yOffsetSave;
	protected short cursorYOffset;
	protected short cursorYOffsetSave;
	protected short lineHeight;
	protected short lineHeightSave;
	protected short drawLineStart;
	protected short totalLine;
	protected short cursorCurrentLine;
	protected short cursorPositionOnLine;
	protected short maximumDisplayedLine;
	protected Color textColor = Color.WHITE;
	private final static ArrayList<Input> inputList = new ArrayList<Input>();
	protected static Input activatedInput;
	private Color color = Color.WHITE;
	
	public final static int ESCAPE_CHAR_VALUE = 27;
	public final static int ENTER_CHAR_VALUE = 13;
	public final static int TAB_CHAR_VALUE = 9;
	
	public Input(TTF font, int maxLength, boolean multipleLine, float x, float y, float maxWidth, boolean isActive, int cursorWidth, int cursorHeight)
	{
		this.multipleLine = multipleLine;
		this.cursorHeight = (byte)(cursorHeight*Mideas.getDisplayYFactor());
		this.cursorWidth = (byte)(cursorWidth*Mideas.getDisplayXFactor());
		this.cursorHeightSave = (byte)cursorHeight;
		this.cursorWidthSave = (byte)cursorWidth;
		this.maxLength = maxLength;
		this.maxWidth = maxWidth;
		setIsActive(isActive);
		inputList.add(this);
		this.xDefault = x;
		this.font = font;
		this.y = y;
		this.defaultText = "";
	}

	public Input(TTF font, int maxLength, boolean multipleLine, float x, float y, float maxWidth, boolean isActive, int cursorWidth, int cursorHeight, Color color)
	{
		this.multipleLine = multipleLine;
		this.cursorHeight = (byte)(cursorHeight*Mideas.getDisplayYFactor());
		this.cursorWidth = (byte)(cursorWidth*Mideas.getDisplayXFactor());
		this.cursorHeightSave = (byte)cursorHeight;
		this.cursorWidthSave = (byte)cursorWidth;
		this.maxLength = maxLength;
		this.maxWidth = maxWidth;
		setIsActive(isActive);
		inputList.add(this);
		this.xDefault = x;
		this.font = font;
		this.y = y;
		this.defaultText = "";
		this.color = color;
	}

	public Input(TTF font, int maxLength, boolean multipleLine, float x, float y, float maxWidth, float maxHeight, float xOffset, float yOffset, boolean isActive, int cursorWidth, int cursorHeight, int cursorYOffset, int lineHeight, Color color)
	{
		this.multipleLine = multipleLine;
		this.cursorHeight = (byte)(cursorHeight*Mideas.getDisplayYFactor());
		this.cursorWidth = (byte)(cursorWidth*Mideas.getDisplayXFactor());
		this.cursorHeightSave = (byte)cursorHeight;
		this.cursorWidthSave = (byte)cursorWidth;
		this.cursorYOffset = (short)(cursorYOffset * Mideas.getDisplayYFactor());
		this.cursorYOffsetSave = (short)cursorYOffset;
		this.maxLength = maxLength;
		this.maxWidth = (short)(maxWidth * Mideas.getDisplayXFactor());
		this.maxWidthSave = maxWidth;
		this.maxHeight = (short)(maxHeight * Mideas.getDisplayYFactor());
		this.maxHeightSave = (short)maxHeight;
		this.xOffset = (short)(xOffset * Mideas.getDisplayXFactor());
		this.xOffsetSave = (short)xOffset;
		this.yOffset = (short)(yOffset * Mideas.getDisplayYFactor());
		this.yOffsetSave = (short)yOffset;
		this.lineHeight = (short)(lineHeight * Mideas.getDisplayYFactor());
		this.lineHeightSave = (short)lineHeight;
		setIsActive(isActive);
		inputList.add(this);
		this.xDefault = x;
		this.font = font;
		this.y = y + this.yOffset;
		this.defaultText = "";
		this.color = color;
	}
	
	public Input(TTF font, int maxLength, boolean multipleLine, float x, float y, float maxWidth, float cursorWidth, float cursorHeight, String defaultText, float textXOffset, float textYOffset, Color textColor)
	{
		this.cursorHeight = (byte)(cursorHeight*Mideas.getDisplayYFactor());
		this.cursorWidth = (byte)(cursorWidth*Mideas.getDisplayXFactor());
		this.cursorHeightSave = (byte)cursorHeight;
		this.cursorWidthSave = (byte)cursorWidth;
		this.multipleLine = multipleLine;
		this.maxLength = maxLength;
		this.maxWidth = maxWidth * Mideas.getDisplayXFactor();
		this.maxWidthSave = maxWidth;
		this.xOffset = (short)(textXOffset * Mideas.getDisplayXFactor());
		this.xOffsetSave = (short)textXOffset;
		this.yOffset = (short)(textYOffset * Mideas.getDisplayYFactor());
		this.yOffsetSave = (short)textYOffset;
		this.textColor = textColor;
		inputList.add(this);
		this.xDefault = x;
		this.font = font;
		this.defaultText = defaultText;
		this.y = y + this.yOffset;
	}
	
	public Input(TTF font, int maxLength, boolean debugActive, boolean multipleLine, float x, float y, float maxWidth, int cursorHeight)
	{
		this.multipleLine = multipleLine;
		this.cursorHeight = (byte)cursorHeight;
		this.debugActive = debugActive;
		this.maxLength = maxLength;
		this.maxWidth = maxWidth;
		inputList.add(this);
		this.xDefault = x;
		this.font = font;
		this.defaultText = "";
		this.y = y;
	}
	
	public Input(TTF font, int maxLength, boolean multipleLine, boolean debugActive, boolean isActive)
	{
		this.multipleLine = multipleLine;
		this.debugActive = debugActive;
		this.maxLength = maxLength;
		setIsActive(isActive);
		inputList.add(this);
		this.font = font;
		this.defaultText = "";
	}
	
	public Input(TTF font, int maxLength, boolean multipleLine, boolean debugActive)
	{
		this(font, maxLength, multipleLine, debugActive, false);
	}
	
	public Input(int font_size, int maxLength, boolean multipleLine, boolean debugActive)
	{
		this(FontManager.get("FRIZQT", font_size), maxLength, multipleLine, debugActive, false);
	}
	
	public void draw()
	{
		int i = -1;
		if(this.multipleLine) {
			float x = this.xDraw + this.xDefault;
			if (this.text.length() == 0)
			{
				this.font.drawStringShadow(x, this.y, this.defaultText, Color.DARKGREY, Color.BLACK, 1, 0, 0);
				if(this.isActive && (Mideas.getLoopTickTimer()%1000 < 500 || Mideas.getLoopTickTimer()-this.lastWrite <= this.writeTickTimerActivation)) 
					Draw.drawColorQuad(this.xDefault, this.y + this.cursorYOffset, this.cursorWidth, this.cursorHeight, this.color);
			}
			else
			{
				short yDraw = (short)(this.y - this.drawLineStart * this.lineHeight);
				short totalLineDisplayed = (short)(this.maxHeight / this.lineHeight);
				short cursorX = 0;
				short cursorY = 0;
				short currentLine = 0;
				this.cursorPositionOnLine = 0;
				this.font.drawBegin();
				while (++i < this.text.length())
				{
					if (x + this.font.getWidth(this.text.charAt(i)) >= this.xDefault + this.maxWidth)
					{
						yDraw += this.lineHeight;	
						if (yDraw - this.y >= this.maxHeight)
							break;
						++currentLine;
						x = this.xDraw + this.xDefault;
						if (this.cursorPosition - 1 == i)
						{
							cursorX = (short)(x + this.font.getWidth(this.text.charAt(i)) + 1);
							cursorY = yDraw;
							this.cursorCurrentLine = currentLine;
							if (currentLine < this.drawLineStart)
								--this.drawLineStart;
							else if (currentLine > this.drawLineStart && currentLine > this.drawLineStart + totalLineDisplayed)
								++this.drawLineStart;
						}
					
					}
					if (this.text.charAt(i) == '\n' || this.text.charAt(i) == ENTER_CHAR_VALUE)
					{
						yDraw += this.lineHeight;
						if (yDraw - this.y >= this.maxHeight)
							break;
						x = this.xDraw + this.xDefault;
						++currentLine;
						if (this.cursorPosition - 1 == i)
						{
							cursorX = (short)(x + this.font.getWidth(this.text.charAt(i)) + 1);
							cursorY = yDraw;
							this.cursorCurrentLine = currentLine;
							if (currentLine < this.drawLineStart)
								--this.drawLineStart;
							else if (currentLine > this.drawLineStart && currentLine > this.drawLineStart + totalLineDisplayed)
								++this.drawLineStart;
						}
					}
					else if (currentLine >= this.drawLineStart && currentLine < this.drawLineStart + totalLineDisplayed)
					{
						this.font.drawCharPart(x+1, yDraw, this.text.charAt(i), Color.BLACK);
						this.font.drawCharPart(x, yDraw, this.text.charAt(i), this.color);
						if (this.cursorPosition - 1 == i && cursorX == 0 && cursorY == 0)
						{
							cursorX = (short)(x + this.font.getWidth(this.text.charAt(i)) + 1);
							cursorY = yDraw;
							this.cursorCurrentLine = currentLine;
						}
					}
					else if (i == this.cursorPosition - 1)
					{
						if (currentLine < this.drawLineStart)
							--this.drawLineStart;
						else
							++this.drawLineStart;
					}
					if (currentLine == this.cursorCurrentLine && i < this.cursorPosition && this.text.charAt(i) != ENTER_CHAR_VALUE && this.text.charAt(i) != '\n')
						++this.cursorPositionOnLine; 
					x+= this.font.getWidth(this.text.charAt(i));
				}
				//System.out.println("PositionOnLine: " + this.cursorPositionOnLine);
				this.font.drawEnd();
				if(this.isActive && ((cursorX != 0 || cursorY != 0) && (Mideas.getLoopTickTimer()%1000 < 500 || Mideas.getLoopTickTimer()-this.lastWrite <= this.writeTickTimerActivation))) 
					Draw.drawColorQuad(cursorX, cursorY + this.cursorYOffset, this.cursorWidth, this.cursorHeight, this.color);
				if(this.isActive && this.cursorPosition == 0 && (Mideas.getLoopTickTimer()%1000 < 500 || Mideas.getLoopTickTimer()-this.lastWrite <= this.writeTickTimerActivation)) 
					Draw.drawColorQuad(this.xDraw + this.xDefault, this.y, this.cursorWidth, this.cursorHeight, this.color);
			}
		}
		else
		{
			float x = this.xDraw + this.xDefault;
			this.font.drawBegin();
			if(this.text.length() == 0)
				this.font.drawStringShadowPart(x, this.y, this.defaultText, Color.DARKGREY, Color.BLACK, 1, 0, 0);
			else
			{
				while(++i < this.text.length())
				{
					if(x >= this.xDefault)
					{
						this.font.drawCharPart(x+1, this.y, this.text.charAt(i), Color.BLACK);
						this.font.drawCharPart(x, this.y, this.text.charAt(i), this.textColor);
					}
					x+= this.font.getWidth(this.text.charAt(i));
					if(x >= this.xDefault+this.maxWidth || (i < this.text.length()-1 && x+this.font.getWidth(this.text.charAt(i)) >= this.xDefault+this.maxWidth))
						break;
				}
			}
			this.font.drawEnd();
			if(!this.isActive)
				return;
			if(Mideas.getLoopTickTimer()%1000 < 500 || Mideas.getLoopTickTimer()-this.lastWrite <= this.writeTickTimerActivation) 
				Draw.drawColorQuad(this.xDraw+this.cursorShift+this.xDefault, this.y + 3 * Mideas.getDisplayYFactor(), this.cursorWidth, this.cursorHeight, this.color);
		}
	}
	
	public void drawTextPart()
	{
		int i = 0;
		if(this.multipleLine) {
			
		}
		else
		{
			float x = this.xDraw+this.xDefault;
			if(this.text.length() == 0)
				this.font.drawStringShadowPart(x, this.y, this.defaultText, Color.DARKGREY, Color.BLACK, 1, 0, 0);
			else
			{
				while(i < this.text.length())
				{
					if(x >= this.xDefault)
					{
						this.font.drawCharPart(x+1, this.y, this.text.charAt(i), Color.BLACK);
						this.font.drawCharPart(x, this.y, this.text.charAt(i), Color.WHITE);
					}
					x+= this.font.getWidth(this.text.charAt(i));
					if(x >= this.xDefault+this.maxWidth || (i < this.text.length()-1 && x+this.font.getWidth(this.text.charAt(i)) >= this.xDefault+this.maxWidth))
						break;
					i++;
				}
			}
			if(!this.isActive)
				return;
		}
	}
	
	public void drawCursorPart()
	{
		if(!this.isActive)
			return;
		if(Mideas.getLoopTickTimer()%1000 < 500 || Mideas.getLoopTickTimer()-this.lastWrite <= this.writeTickTimerActivation) 
			Draw.drawColorQuadPart(this.xDraw+this.cursorShift+this.xDefault, this.y+1, this.cursorWidth, this.cursorHeight, Color.WHITE);
	}
	
	public boolean event()
	{
		if(!this.isActive)
			return false;
		if(Keyboard.getEventKey() == 0)
			return false;
		if(this.debugActive)
			System.out.println("Key pressed.");
		if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT))
		{ //left shift
			if(Keyboard.isKeyDown(Keyboard.KEY_LCONTROL))
			{
				if(Keyboard.getEventKey() == 203)
				{ // shift CTRL left arrow
					selectCTRLLeftArrow();
					return true;
				}
				if(Keyboard.getEventKey() == 205)
				{ // shift CTRL right arrow
					selectCTRLRightArrow();
					return true;
				}
			}
			if(Keyboard.getEventKey() == 203)
			{ //shift left arrow
				selectLeftArrow();
				return true;
			}
			if(Keyboard.getEventKey() == 205)
			{ // shift right arrow
				selectRightArrow();
				return true;
			}
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_LCONTROL))
		{ //ctrl down
			if(Keyboard.getEventKey() == 14)
			{ //delete
				CTRLDelete();
				this.tempLength = 0;
				resetSelectedPosition();
				return true;
			}
			if(Keyboard.getEventKey() == Keyboard.KEY_V)
			{ // c/c
				String temp = Sys.getClipboard().replace("\n", "").replace("\t", "").replace("\r", "");
				write(temp);
				return true;
			}
			if(Keyboard.getEventKey() == 203)
			{ //left arrow
				CTRLleftArrow();
				resetSelectedPosition();
				return true;
			}
			if(Keyboard.getEventKey() == 205)
			{ //right arrow
				CTRLrightArrow();
				resetSelectedPosition();
				return true;
			}
			if(Keyboard.getEventKey() == Keyboard.KEY_C)
			{
				copySelected();
				return true;
			}
		}
		if(Keyboard.getEventKey() == 14)
		{ //delete
			delete();
			resetSelectedPosition();
			return true;
		}
		if(Keyboard.getEventKey() == 203)
		{ //left arrow
			leftArrow();
			resetSelectedPosition();
			return true;
		}
		if(Keyboard.getEventKey() == 205)
		{ //right arrow
			rightArrow();
			resetSelectedPosition();
			return true;
		}
		if(Keyboard.getEventKey() == 211)
		{ //suppr
			suppr();
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
		if(!(Keyboard.getEventKey() != Keyboard.KEY_LCONTROL && Keyboard.getEventKey() != Keyboard.KEY_RCONTROL && Keyboard.getEventKey() != Keyboard.KEY_RSHIFT))
			return false;
		if(Keyboard.getEventKey() == Keyboard.KEY_RETURN || Keyboard.getEventKey() == 156 || Keyboard.getEventKey() == 28)
		{
			char c = Keyboard.getEventCharacter();
			if(!this.multipleLine && keyEvent(c))
			{
				if(this.debugActive) {
					System.out.println("First call Event triggered");
				}
				return true;
			}
			if(this.debugActive)
				System.out.println("No event triggered");
		}
		char c = Keyboard.getEventCharacter();
		if(keyEvent(c)) {
			if(this.debugActive)
				System.out.println("Second call Event triggered");
			return true;
		}
		if(this.text.length() < this.maxLength)
		{
			if(this.debugActive)
				System.out.println("char: "+c+", value: "+(int)c);
			if(isValidCharacter(c) || (this.multipleLine && c == ENTER_CHAR_VALUE))
			{
				write(c);
				resetSelectedPosition();
				return true;
			}
		}
		return false;
	}
	
	public void update(float x, float y, float maxWidth)
	{
		this.maxWidth = maxWidth;
		this.maxHeight = (short)(this.maxHeightSave * Mideas.getDisplayYFactor());
		this.xOffset = (short)(this.xOffsetSave * Mideas.getDisplayXFactor());
		this.yOffset = (short)(this.yOffsetSave * Mideas.getDisplayYFactor());
		this.cursorYOffset = (short)(this.cursorYOffsetSave * Mideas.getDisplayYFactor());
		this.lineHeight = (short)(this.lineHeightSave * Mideas.getDisplayYFactor());
		this.cursorWidth = (byte)(this.cursorWidthSave * Mideas.getDisplayXFactor());
		this.cursorHeight = (byte)(this.cursorHeightSave * Mideas.getDisplayYFactor());
		this.xDefault = x + this.xOffset;
		this.y = y + this.yOffset;
	}
	
	public void update(float x, float y)
	{
		this.maxWidth = (short)(this.maxWidthSave * Mideas.getDisplayXFactor());
		this.maxHeight = (short)(this.maxHeightSave * Mideas.getDisplayYFactor());
		this.xOffset = (short)(this.xOffsetSave * Mideas.getDisplayXFactor());
		this.yOffset = (short)(this.yOffsetSave * Mideas.getDisplayYFactor());
		this.cursorYOffset = (short)(this.cursorYOffsetSave * Mideas.getDisplayYFactor());
		this.lineHeight = (short)(this.lineHeightSave * Mideas.getDisplayYFactor());
		this.cursorWidth = (byte)(this.cursorWidthSave * Mideas.getDisplayXFactor());
		this.cursorHeight = (byte)(this.cursorHeightSave * Mideas.getDisplayYFactor());
		this.xDefault = x + this.xOffset;
		this.y = y + this.yOffset;
	}
	
	public void setMaxLength(int maxLength)
	{
		this.maxLength = maxLength;
	}
	
	public boolean keyEvent(@SuppressWarnings("unused") char c) {return false;}
	
	private static boolean isValidCharacter(char c)
	{
		return c >= ' ' && c <= 255;
	}
	
	public void setIsActive(boolean we)
	{
		if(we)
		{
			setInactiveAllInput();
			activatedInput = this;
			this.lastWrite = Mideas.getLoopTickTimer();
		}
		this.isActive = we;
	}
	
	public static Input getSelectedInput()
	{
		return activatedInput;
	}
	
	public boolean hasMultipleLine()
	{
		return (this.multipleLine);
	}
	
	public static boolean hasInputActive() 
	{
		int i = -1;
		while(++i < inputList.size())
			if(inputList.get(i).isActive)
				return true;
		return ChatFrame.getChatActive();
	}
	
	public static void setInactiveAllInput()
	{
		/*int i = 0;
		while(i < inputList.size()) {
			inputList.get(i).setIsActive(false);
			i++;
		}*/
		if(activatedInput != null)
			activatedInput.setIsActive(false);
		ChatFrame.setChatActive(false);
	}
	
	public boolean isActive()
	{
		return this.isActive;
	}
	
	public void write(String add)
	{
		if(this.cursorPosition == this.text.length())
			this.text+= add;
		else
		{
			String beg = this.text.substring(0, this.cursorPosition+this.text.substring(0, this.tempLength).length());
			String end = this.text.substring(this.cursorPosition+this.text.substring(0, this.tempLength).length(), this.text.length());
			this.text = beg+add+end;
		}
		this.cursorShift+= this.font.getWidth(add);
		this.cursorPosition+= add.length();
		this.lastWrite = Mideas.getLoopTickTimer();
		shiftTextLeft();
	}
	
	public int getTextValue()
	{
		return 0;
	}
	
	public void resetText()
	{
		this.text = "";
		this.cursorPosition = 0;
		this.cursorShift = 0;
		this.tempLength = 0;
		this.selectedLength = 0;
		this.selectedQuadLength = 0;
		this.selectedStarts = 0;
		this.xDraw = 0;
	}
	
	public void setText(String text)
	{
		resetText();
		write(text);
		this.xDraw = 0;
	}
	
	private void write(char add)
	{
		deleteSelected();
		if(this.cursorPosition == this.text.length())
			this.text+= add;
		else
		{
			String beg = this.text.substring(0, this.cursorPosition+this.text.substring(0, this.tempLength).length());
			String end = this.text.substring(this.cursorPosition+this.text.substring(0, this.tempLength).length(), this.text.length());
			this.text = beg+add+end;
		}
		this.cursorShift+= this.font.getWidth(add);
		this.cursorPosition++;
		this.lastWrite = Mideas.getLoopTickTimer();
		shiftTextLeft();
	}
	
	private void shiftTextLeft()
	{
		if (this.multipleLine)
			return;
		if(this.cursorShift+this.xDraw > this.maxWidth && this.cursorPosition <= this.text.length())
		{
			float shiftWidth = this.maxWidth/2;
			int i = this.cursorPosition;
			int shift = 0;
			while(--i >= 0 && shift < shiftWidth) {
				this.xDraw-= this.font.getWidth(this.text.charAt(i));
				shift+= this.font.getWidth(this.text.charAt(i));
			}
			shiftTextLeft();
		}
		else if(this.text.length() == 0)
			this.xDraw = 0;
		this.lastWrite = Mideas.getLoopTickTimer();
	}
	
	private void shiftTextRight()
	{
		if (this.multipleLine)
			return;
		//System.out.println(this.cursorShift+" "+this.xDraw+" "+this.xDefault+" "+this.cursorPosition+" RIGHT "+(this.cursorShift+this.xDraw < 0));
		if(this.cursorShift+this.xDraw <= 0 && this.cursorPosition > 0 && this.text.length() != 0)
		{
			int i = this.cursorPosition;
			float shiftWidth = this.maxWidth/2;
			int shift = 0;
			while(--i >= 0 && shift < shiftWidth) {
				this.xDraw+= this.font.getWidth(this.text.charAt(i));
				shift+= this.font.getWidth(this.text.charAt(i));
			}
			shiftTextRight();
		}
		else if(this.text.length() == 0 || this.cursorPosition == 0)
			this.xDraw = 0;
		this.lastWrite = Mideas.getLoopTickTimer();
	}
	
	private int findNextLineEnd()
	{
		int i = -1;
		float x = 0;
		byte currentLine = 0;
		while (++i < this.text.length())
		{
			if (this.text.charAt(i) == '\n' || this.text.charAt(i) == ENTER_CHAR_VALUE || x + this.font.getWidth(this.text.charAt(i)) >= this.maxWidth)
			{
				++currentLine;
				if (currentLine == this.cursorCurrentLine + 2)
				{
					this.cursorCurrentLine = (short)(currentLine - 1);
					if (this.text.charAt(i) == '\n' || this.text.charAt(i) == ENTER_CHAR_VALUE)
						return (i - 1);
					return (i);
				}
				x = 0;
			}
			x += this.font.getWidth(this.text.charAt(i));
		}
		if (i == this.text.length())
		{
			this.cursorCurrentLine = currentLine;
			if (i > 0 && (this.text.charAt(i - 1) == '\n' || this.text.charAt(i - 1) == ENTER_CHAR_VALUE))
				return (i - 1);
			return (i);
		}
		return (-1);
	}
	
	private int findLineLen(int line)
	{
		int i = -1;
		float x = 0;
		byte currentLine = 0;
		int lineLength = 0;
		while (++i < this.text.length())
		{
			if (this.text.charAt(i) == '\n' || this.text.charAt(i) == ENTER_CHAR_VALUE || x + this.font.getWidth(this.text.charAt(i)) >= this.maxWidth)
			{
				++currentLine;
				if (currentLine == line + 1)
					return (lineLength);
				x = this.font.getWidth(this.text.charAt(i));
				continue;
			}
			if (currentLine == line)
				++lineLength;
			x += this.font.getWidth(this.text.charAt(i));
		}
		if (i == this.text.length())
			return (lineLength);
		return (-1);
	}
	
	private int findPreviousLineEnd()
	{
		int i = -1;
		float x = 0;
		byte currentLine = 0;
		while (++i < this.text.length())
		{
			if (this.text.charAt(i) == '\n' || this.text.charAt(i) == ENTER_CHAR_VALUE || x + this.font.getWidth(this.text.charAt(i)) >= this.maxWidth)
			{
				++currentLine;
				if (currentLine == this.cursorCurrentLine)
				{
					this.cursorCurrentLine = (short)(currentLine - 1);
					if (this.text.charAt(i) == '\n' || this.text.charAt(i) == ENTER_CHAR_VALUE)
						return (i - 1);
					return (i);
				}
				x = 0;
			}
			x += this.font.getWidth(this.text.charAt(i));
		}
		return (-1);
	}
	
	private int findIndexFromLine(int index, int line)
	{
		int i = -1;
		float x = 0;
		byte currentLine = 0;
		int indexOnLine = -1;
		boolean lastLineJumpCharIsEnter = false;
		if (line == 0)
			return (index);
		while (++i < this.text.length())
		{
			//System.out.println("i: " + i + ", char: " + this.text.charAt(i));
			if (this.text.charAt(i) == '\n' || this.text.charAt(i) == ENTER_CHAR_VALUE || x  + this.font.getWidth(this.text.charAt(i)) >= this.maxWidth)
			{
				++currentLine;
				x = 0;
				lastLineJumpCharIsEnter = this.text.charAt(i) == '\n' || this.text.charAt(i) == ENTER_CHAR_VALUE ? true : false;
			}
			if (currentLine == line)
			{
				//System.out.println("CurrentLine: " + currentLine + ", line: " + line);
				++indexOnLine;
				//System.out.println("CurrentIndex: " + indexOnLine + ", index: " + index);
				if (indexOnLine == index)
				{
					//System.out.println("FINAL INDEX: " + i);
					if (lastLineJumpCharIsEnter)
						return (i + 1);
					return (i);
				}
			}
			x += this.font.getWidth(this.text.charAt(i));
		}
		return (-1);
	}
	
	private void upArrow()
	{
		if (!this.multipleLine)
			return;
		int previousLineEnd = findPreviousLineEnd();
		System.out.println("PreviousLineEnd: " + previousLineEnd);
		if (previousLineEnd == -1)
			return;
		int previousLineLen = findLineLen(this.cursorCurrentLine);
		System.out.println("PreviousLineLen: " + previousLineLen + ", IndexFromLine: " + findIndexFromLine(this.cursorPositionOnLine, this.cursorCurrentLine));
		if (previousLineLen == 0 && this.cursorPosition > 0)
			--this.cursorPosition;
		else if (this.cursorPositionOnLine <= previousLineLen)
			this.cursorPosition = findIndexFromLine(this.cursorPositionOnLine, this.cursorCurrentLine);
		else
			this.cursorPosition = previousLineEnd;
		System.out.println("CursorPosition: " + this.cursorPosition);
		this.lastWrite = Mideas.getLoopTickTimer();
		System.out.println("CurrentLine: " + this.cursorCurrentLine);
	}
	
	private void downArrow()
	{
		if (!this.multipleLine)
			return;
		int nextLineEnd = findNextLineEnd();
		if (nextLineEnd == -1)
			return;
		System.out.println("-----------------------------------------------------------");
		System.out.println("FindNextLineEnd: " + nextLineEnd);
		int nextLineLen = findLineLen(this.cursorCurrentLine);
		if (nextLineLen == 0 && this.cursorPosition < this.text.length() - 1 && (this.text.charAt(this.cursorPosition) == '\n' || this.text.charAt(this.cursorPosition) == ENTER_CHAR_VALUE))
			++this.cursorPosition;
		else if (this.cursorPositionOnLine <= nextLineLen)
			this.cursorPosition = findIndexFromLine(this.cursorPositionOnLine, this.cursorCurrentLine);
		else
			this.cursorPosition = nextLineEnd;
		System.out.println("NextLineLen: " + nextLineLen + ", IndexFromLine: " + findIndexFromLine(this.cursorPositionOnLine, this.cursorCurrentLine));
		System.out.println("CursorPosition: " + this.cursorPosition + ", CursorPositionOnLine: " + this.cursorPositionOnLine);
		this.lastWrite = Mideas.getLoopTickTimer();
		System.out.println("CurrentLine: " + this.cursorCurrentLine);
		System.out.println("-----------------------------------------------------------");
	}

	private void delete()
	{
		if(this.text.length() > 0)
		{
			if(this.selectedLength != 0)
				deleteSelected();
			else if(this.cursorPosition > 0)
			{
				this.cursorShift-= this.font.getWidth(this.text.charAt(this.cursorPosition-1));
				String beg = this.text.substring(0, this.cursorPosition-1+this.text.substring(0, this.tempLength).length());
				String end = this.text.substring(this.cursorPosition+this.text.substring(0, this.tempLength).length(), this.text.length());
				this.text = beg+end;
				this.cursorPosition--;
			}
			shiftTextRight();
		}
		this.lastWrite = Mideas.getLoopTickTimer();
	}
	
	private void suppr()
	{
		if(this.cursorPosition+this.text.substring(0, this.tempLength).length() < this.text.length())
		{
			String beg = this.text.substring(0, this.cursorPosition+this.text.substring(0, this.tempLength).length());
			String end = this.text.substring(this.cursorPosition+1+this.text.substring(0, this.tempLength).length(), this.text.length());
			this.text = beg+end;
			shiftTextLeft();
		}
		this.lastWrite = Mideas.getLoopTickTimer();
	}

	private void copySelected()
	{
		String selected;
		if(this.selectedLength < 0)
			selected = this.text.substring(this.cursorPosition, this.cursorPosition-this.selectedLength);
		else
			selected = this.text.substring(this.cursorPosition-this.selectedLength, this.cursorPosition);
		StringSelection selection = new StringSelection(selected);
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(selection, selection);
	}
	
	private void deleteSelected()
	{
		if(this.selectedLength != 0)
		{
			String beg = "";
			String end = "";
			if(this.selectedLength < 0)
			{
				if(this.cursorPosition-this.selectedLength >= 0 && this.cursorPosition-this.selectedLength <= this.text.length())
				{
					beg = this.text.substring(0, this.cursorPosition);
					end = this.text.substring(this.cursorPosition-this.selectedLength, this.text.length());
				}
			}
			else
			{
				beg = this.text.substring(0, this.cursorPosition-this.selectedLength);
				end = this.text.substring(this.cursorPosition, this.text.length());
				this.cursorShift = this.font.getWidth(this.text.substring(0, this.cursorPosition-this.selectedLength));
				this.cursorPosition = this.cursorPosition-this.selectedLength;
			}
			this.text = beg+end;
		}
	}
	
	private boolean CTRLDelete()
	{
		int i = this.cursorPosition;
		String temp = this.text.substring(this.cursorPosition);
		if(this.text.length() != 0)
			if(this.text.charAt(this.text.length()-1) == ' ' || this.text.charAt(this.text.length()-1) == ',' || this.text.charAt(this.text.length()-1) == ENTER_CHAR_VALUE)
			{
				this.text = this.text.substring(0, this.text.length()-1);
				i--;
			}
		while(i > 0)
		{
			if(this.text.charAt(i-1) == ' ' || this.text.charAt(i-1) == ',' || this.text.charAt(i-1) == ENTER_CHAR_VALUE)
			{
				this.text = this.text.substring(0, i);
				this.cursorPosition = this.text.length();
				this.cursorShift = this.font.getWidth(this.text);
				this.lastWrite = Mideas.getLoopTickTimer();
				return true;
			}
			i--;
		}
		this.text = temp;
		this.cursorPosition = 0;
		this.cursorShift = 0;
		shiftTextRight();
		this.lastWrite = Mideas.getLoopTickTimer();
		return false;
	}
	private void CTRLleftArrow()
	{
		int i = this.cursorPosition;
		if(this.text.length() != 0 && i > 0)
		{
			if(this.text.charAt(this.cursorPosition-1) == ' ' || this.text.charAt(this.cursorPosition-1) == ',' || this.text.charAt(this.cursorPosition-1) == ENTER_CHAR_VALUE)
			{
				this.cursorShift-= this.font.getWidth(this.text.charAt(this.cursorPosition-1));
				this.cursorPosition--;
				i--;
			}
		}
		while(i > 0)
		{
			this.cursorPosition--;
			this.cursorShift-= this.font.getWidth(this.text.charAt(i-1));
			i--;
			if(i <= 0)
				break;
			if(this.text.charAt(i-1) == ' ' || this.text.charAt(i-1) == ',' || this.text.charAt(i-1) == ENTER_CHAR_VALUE)
				break;
		}
		shiftTextRight();
		this.lastWrite = Mideas.getLoopTickTimer();
	}
	
	public String getText()
	{
		return this.text;
	}
	
	public int getCursorShift()
	{
		return this.cursorShift;
	}
	
	public int getCursorPosition()
	{
		return this.cursorPosition;
	}
	
	private void CTRLrightArrow()
	{
		int i = this.cursorPosition;
		if(i < this.text.length())
		{
			if(this.text.length() != 0)
				if(this.text.charAt(this.cursorPosition+1) == ' ' || this.text.charAt(this.cursorPosition+1) == ',' || this.text.charAt(this.cursorPosition+1) == ENTER_CHAR_VALUE)
				{
					this.cursorShift+= this.font.getWidth(this.text.charAt(this.cursorPosition+1));
					this.cursorPosition++;
					i++;
				}
			while(i < this.text.length() && i >= 0)
			{
				this.cursorPosition++;
				this.cursorShift+= this.font.getWidth(this.text.charAt(i));
				i++;
				if(i >= this.text.length())
					break;
				if(this.text.charAt(i) == ' ' || this.text.charAt(i) == ',' || this.text.charAt(i) == ENTER_CHAR_VALUE)
				{
					this.cursorPosition++;
					this.cursorShift+= this.font.getWidth(this.text.charAt(i));
					break;
				}
			}
			shiftTextLeft();
		}
		this.lastWrite = Mideas.getLoopTickTimer();
	}
	
	private void leftArrow()
	{
		if(this.cursorPosition > 0)
		{
			this.cursorPosition--;
			this.cursorShift-= this.font.getWidth(this.text.substring(this.cursorPosition+this.text.substring(0, this.tempLength).length(), this.cursorPosition+1+this.text.substring(0, this.tempLength).length()));
		}
		shiftTextRight();
		this.lastWrite = Mideas.getLoopTickTimer();
	}
	
	private void rightArrow()
	{
		if(this.cursorPosition+this.text.substring(0, this.tempLength).length() < this.text.length())
		{
			this.cursorPosition++;
			if(this.cursorPosition == this.text.length())
				this.cursorShift+= this.font.getWidth(this.text.substring(this.cursorPosition-1+this.text.substring(0, this.tempLength).length()));
			else
				this.cursorShift+= this.font.getWidth(this.text.substring(this.cursorPosition-1+this.text.substring(0, this.tempLength).length(), this.cursorPosition+this.text.substring(0, this.tempLength).length()));
		}
		shiftTextLeft();
		this.lastWrite = Mideas.getLoopTickTimer();
	}
	
	private void selectLeftArrow()
	{
		if(this.cursorPosition > 0)
		{
			if(this.selectedLength == 0)
				this.selectedStarts = 40+this.cursorShift;
			leftArrow();
			this.selectedQuadLength-= this.font.getWidth(this.text.substring(this.cursorPosition+this.text.substring(0, this.tempLength).length(), this.cursorPosition+1+this.text.substring(0, this.tempLength).length()));
			this.selectedLength--;
		}
		this.lastWrite = Mideas.getLoopTickTimer();
 	}
	
	private void selectCTRLLeftArrow()
	{
		int i = this.cursorPosition;
		if(this.selectedLength == 0)
			this.selectedStarts = 40+this.cursorShift;
		if(this.text.length() != 0 && i > 0)
		{
			if(this.text.charAt(this.cursorPosition-1) == ' ' || this.text.charAt(this.cursorPosition-1) == ',' || this.text.charAt(this.cursorPosition-1) == ENTER_CHAR_VALUE)
			{
				this.cursorShift-= this.font.getWidth(this.text.charAt(this.cursorPosition-1));
				this.selectedQuadLength-= this.font.getWidth(this.text.charAt(this.cursorPosition-1));
				this.selectedLength--;
				this.cursorPosition--;
				i--;
			}
		}
		while(i > 0)
		{
			this.cursorPosition--;
			this.cursorShift-= this.font.getWidth(this.text.substring(i-1, i).charAt(0));
			this.selectedQuadLength-= this.font.getWidth(this.text.charAt(i-1));
			this.selectedLength--;
			i--;
			if(i <= 0)
				break;
			if(this.text.charAt(i-1) == ' ' || this.text.charAt(i-1) == ',' || this.text.charAt(i-1) == ENTER_CHAR_VALUE)
				break;
		}
		shiftTextRight();
		this.lastWrite = Mideas.getLoopTickTimer();
	}
	
	private void selectCTRLRightArrow()
	{
		int i = this.cursorPosition;
		if(this.selectedLength == 0)
			this.selectedStarts = 40+this.cursorShift;
		if(i < this.text.length())
		{
			if(this.text.length() != 0 && this.cursorPosition+1 <= this.text.length() && this.cursorPosition+1 >= 0)
				if(this.text.charAt(this.cursorPosition+1) == ' ' || this.text.charAt(this.cursorPosition+1) == ',' || this.text.charAt(this.cursorPosition+1) == ENTER_CHAR_VALUE)
				{
					this.cursorShift+= this.font.getWidth(this.text.charAt(this.cursorPosition+1));
					this.selectedQuadLength+= this.font.getWidth(this.text.charAt(this.cursorPosition+1));
					this.selectedLength++;
					this.cursorPosition++;
					i++;
				}
			while(i < this.text.length() && i >= 0)
			{
				this.cursorPosition++;
				this.cursorShift+= this.font.getWidth(this.text.charAt(i));
				this.selectedQuadLength+= this.font.getWidth(this.text.charAt(i));
				this.selectedLength++;
				i++;
				if(i >= this.text.length())
					break;
				if(this.text.charAt(i) == ' ' || this.text.charAt(i) == ',' || this.text.charAt(i) == ENTER_CHAR_VALUE)
					break;
			}
			shiftTextLeft();
		}
		this.lastWrite = Mideas.getLoopTickTimer();
	}

	private void selectRightArrow() 
	{
		if(this.cursorPosition < this.text.length())
		{
			if(this.selectedLength == 0)
				this.selectedStarts = 40+this.cursorShift;
			rightArrow();
			if(this.cursorPosition == this.text.length())
				this.selectedQuadLength+= this.font.getWidth(this.text.substring(this.cursorPosition-1+this.text.substring(0, this.tempLength).length()));
			else
				this.selectedQuadLength+= this.font.getWidth(this.text.substring(this.cursorPosition-1+this.text.substring(0, this.tempLength).length(), this.cursorPosition+this.text.substring(0, this.tempLength).length()));
			this.selectedLength++;
		}
		this.lastWrite = Mideas.getLoopTickTimer();
 	}

	private void resetSelectedPosition()
	{
		this.selectedQuadLength = 0;
		this.selectedLength = 0;
		this.selectedStarts = 0;
	}
	
	public int getSelectedStarts()
	{
		return this.selectedStarts;
	}
	
	public int getSelectedQuadLength()
	{
		return this.selectedQuadLength;
	}
}