package com.mideas.rpg.v2.utils;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.render.Draw;
import com.mideas.rpg.v2.render.Sprites;
import com.mideas.rpg.v2.render.TTF;

import org.lwjgl.input.Mouse;

public class InputBox extends UIElement
{

	private final short textOffset;
	private final short inputMaxWidth;
	protected final Input input;
	private short x;
	private short y;
	private short xSave;
	private short ySave;
	private short width;
	private short height;
	private final short widthSave;
	private short heightSave;
	private boolean buttonDown;
	private boolean buttonHover;
	private final EditBoxCrossButton crossButton;
	private final static byte CROSS_BUTTON_X_OFFSET = 21;
	private final static byte CROSS_BUTTON_Y_OFFSET = 5;
	
	public InputBox(String name, float x, float y, float x_size, int inputMaxLength, float textOffset, float inputMaxWidth, TTF font, boolean isIntegerInput, int cursorWidth, int cursorHeight, String defaultText, float textXOffset, float textYOffset, boolean hasCrossButton)
	{
		super(name, UIElementType.INPUT_BOX);
		this.x = (short)x;
		this.y = (short)y;
		this.widthSave = (short)x_size;
		this.width = (short)(x_size*Mideas.getDisplayXFactor());
		this.height = (short)(Sprites.edit_box_left_border.getImageHeight()*Mideas.getDisplayYFactor());
		this.heightSave = (short)Sprites.edit_box_left_border.getImageHeight();
		this.textOffset = (short)textOffset;
		this.inputMaxWidth = (short)inputMaxWidth;
		if (isIntegerInput)
		{
			this.input = new IntegerInput(font, x, y, inputMaxLength, inputMaxWidth*Mideas.getDisplayXFactor(), cursorWidth, cursorHeight, textXOffset, textYOffset, Color.WHITE)
			{
				
				@Override
				public boolean checkValue(int value)
				{
					return InputBox.this.checkValue(value);
				}
				
				@Override
				public boolean keyEvent(char c)
				{
					return InputBox.this.keyEvent(c);
				}
			};
		}
		else
		{
			this.input = new Input(font, inputMaxLength, false, x, y, inputMaxWidth*Mideas.getDisplayXFactor(), cursorWidth, cursorHeight, defaultText, textXOffset, textYOffset, Color.WHITE)
			{
				
				@Override
				public boolean keyEvent(char c)
				{
					return (InputBox.this.keyEvent(c));
				}
			};
		}
		if (hasCrossButton)
		{
			this.crossButton = new EditBoxCrossButton("", x+x_size-CROSS_BUTTON_X_OFFSET*Mideas.getDisplayXFactor(), y+CROSS_BUTTON_Y_OFFSET*Mideas.getDisplayYFactor())
			{
				
				@Override
				public void eventButtonClick()
				{
					setActive(false);
					resetText();
				}
				
				@Override
				public boolean activateCondition()
				{
					return InputBox.this.input.isActive() || InputBox.this.input.getText().length() > 0;
				}
			};
		}
		else
		{
			this.crossButton = null;
		}
	}
	
	public InputBox(String name, float x, float y, float x_size, int inputMaxLength, float textOffset, float inputMaxWidth, TTF font, boolean isIntegerInput, float textXOffset, float textYOffset)
	{
		this(name, x, y, x_size, inputMaxLength, textOffset, inputMaxWidth, font, isIntegerInput, 2, 13, "", textXOffset, textYOffset, false);
	}
	
	public InputBox(Frame parentFrame, String name, short x, short y, short width, short height, short inputXOffset, short inputYOffset, int inputMaxLength, int inputMaxWidth, int lineHeight, TTF font, int cursorWidth, int cursorHeight, int cursorYOffset, Color color)
	{	
		super(name, UIElementType.INPUT_BOX);
		this.x = x;
		this.y = (short)(y * Mideas.getDisplayYFactor());
		this.xSave = x;
		this.ySave = y;
		this.widthSave = width;
		this.width = (short)(width * Mideas.getDisplayXFactor());
		this.textOffset = 0;
		this.height = (short)(height * Mideas.getDisplayYFactor());
		this.heightSave = height;
		this.inputMaxWidth = (short)inputMaxWidth;
		this.crossButton = null;
		this.parentFrame = parentFrame;
		this.input = new Input(font, inputMaxLength, true, x, y, inputMaxWidth, height, inputXOffset, inputYOffset, false, cursorWidth, cursorHeight, cursorYOffset, lineHeight, color)
		{
		
			@Override
			public boolean keyEvent(char c)
			{
				return (InputBox.this.keyEvent(c));
			}
		};
	}
	
	public InputBox(Frame parentFrame, String name, short x, short y, short width, short inputMaxLength, short textXOffset, short textYOffset, short inputMaxWidth, TTF font, boolean isIntegerInput, int cursorWidth, int cursorHeight, String defaultText, boolean hasCrossButton, Color textColor)
	{
		super(name, UIElementType.INPUT_BOX);
		this.x = x;
		this.y = y;
		this.parentFrame = parentFrame;
		this.xSave = x;
		this.ySave = y;
		this.widthSave = width;
		this.width = (short)(width * Mideas.getDisplayXFactor());
		this.height = (short)(Sprites.edit_box_left_border.getImageHeight() * Mideas.getDisplayYFactor());
		this.heightSave = (short)Sprites.edit_box_left_border.getImageHeight();
		this.inputMaxWidth = inputMaxWidth;
		this.textOffset = 0;
		if (isIntegerInput)
		{
			this.input = new IntegerInput(font, x, y, inputMaxLength, inputMaxWidth*Mideas.getDisplayXFactor(), cursorWidth, cursorHeight, textXOffset, textYOffset, textColor)
			{
				
				@Override
				public boolean checkValue(int value)
				{
					return (InputBox.this.checkValue(value));
				}
				
				@Override
				public boolean keyEvent(char c)
				{
					return (InputBox.this.keyEvent(c));
				}
			};
		}
		else
		{
			this.input = new Input(font, inputMaxLength, false, x, y, inputMaxWidth*Mideas.getDisplayXFactor(), cursorWidth, cursorHeight, defaultText, textXOffset, textYOffset, textColor)
			{
				
				@Override
				public boolean keyEvent(char c)
				{
					return (InputBox.this.keyEvent(c));
				}
			};
		}
		if (hasCrossButton)
		{
			this.crossButton = new EditBoxCrossButton(parentFrame, x + width - CROSS_BUTTON_X_OFFSET * Mideas.getDisplayXFactor(), y + CROSS_BUTTON_Y_OFFSET * Mideas.getDisplayYFactor(), "")
			{
				
				@Override
				public void eventButtonClick()
				{
					setActive(false);
					resetText();
				}
				
				@Override
				public boolean activateCondition()
				{
					return (InputBox.this.input.isActive() || InputBox.this.input.getText().length() > 0);
				}
			};
		}
		else
		{
			this.crossButton = null;
		}
	}
	
	public void drawTexturePart()
	{
		int imageWidth = Sprites.edit_box_left_border.getImageWidth();
		int imageHeight = (int)(Sprites.edit_box_left_border.getImageHeight()*Mideas.getDisplayYFactor());
		Draw.drawQuadPart(Sprites.edit_box, this.x, this.y, imageWidth, imageHeight, 0, 0, 6, 20);				//left_border
		Draw.drawQuadPart(Sprites.edit_box, this.x+imageWidth, this.y, this.width-2*imageWidth, imageHeight, 8, 0, 1, 20);	//middle_border
		Draw.drawQuadPart(Sprites.edit_box, this.x+this.width-imageWidth, this.y, imageWidth, imageHeight, 11, 0, 6, 20);	//right_border
	}
	
	public void drawStringPart()
	{
		this.input.drawTextPart();
	}
	
	public void drawCursorPart()
	{
		this.input.drawCursorPart();
	}
	
	public void drawString()
	{
		this.input.draw();
		if (this.crossButton != null)
			this.crossButton.draw();
	}
	
	public void drawCrossButton()
	{
		if (this.crossButton != null)
			this.crossButton.draw();
	}
	
	@Override
	public void draw() {
		if (!this.input.hasMultipleLine())
		{
			int imageWidth = Sprites.edit_box_left_border.getImageWidth();
			int imageHeight = (int)(Sprites.edit_box_left_border.getImageHeight()*Mideas.getDisplayYFactor());
			
			Sprites.edit_box.drawBegin();
			Draw.drawQuadPart(Sprites.edit_box, this.x, this.y, imageWidth, imageHeight, 0, 0, 6, 20);				//left_border
			Draw.drawQuadPart(Sprites.edit_box, this.x+imageWidth, this.y, this.width-2*imageWidth, imageHeight, 8, 0, 1, 20);	//middle_border
			Draw.drawQuadPart(Sprites.edit_box, this.x+this.width-imageWidth, this.y, imageWidth, imageHeight, 11, 0, 6, 20);	//right_border
			Sprites.edit_box.drawEnd();
		}
		this.input.draw();
		if (this.crossButton != null)
			this.crossButton.draw();
	}
	
	@Override
	public boolean mouseEvent()
	{
		if (this.crossButton != null && this.crossButton.mouseEvent())
			return (true);
		if (Mideas.getHover() && Mideas.mouseX() >= this.x && Mideas.mouseX() <= this.x+this.width && Mideas.mouseY() >= this.y && Mideas.mouseY() <= this.y + this.height)
		{
			this.buttonHover = true;
			Mideas.setHover(this, false);
		}
		else
		{
			this.buttonHover = false;
		}
		if (this.buttonHover)
		{
			if (Mouse.getEventButtonState())
			{
				if(Mouse.getEventButton() == 0 || Mouse.getEventButton() == 1)
					this.buttonDown = true;
			}
			else if (this.buttonDown)
			{
				if (Mouse.getEventButton() == 0 || Mouse.getEventButton() == 1) {
					this.buttonDown = false;
					this.input.setIsActive(true);
					return true;
				}
			}
		}
		else if (!Mouse.getEventButtonState())
			if (Mouse.getEventButton() == 0 || Mouse.getEventButton() == 1)
				this.buttonDown = false;
		return (false);
	}
	
	@Override
	public boolean keyboardEvent()
	{
		return (this.input.event());
	}
	
	public void setText(String text)
	{
		this.input.setText(text);
	}
	
	public void resetText()
	{
		this.input.resetText();
	}
	
	public String getText()
	{
		return (this.input.getText());
	}
	
	public void setActive(boolean we)
	{
		this.input.setIsActive(we);
	}
	
	public int getValue()
	{
		return (this.input.getTextValue());
	}
	
	public void setCursorEndOfText()
	{
		this.input.setText(this.input.getText());
	}
	
	@Override
	public void setX(int x)
	{
		this.xSave = (short)x;
		updateSize();
	}
	
	@SuppressWarnings("unused")
	public boolean checkValue(int value) {return true;}
	
	public void update(float x, float y)
	{
		this.x = (short)x;
		this.y = (short)y;
		this.width = (short)(this.widthSave*Mideas.getDisplayXFactor());
		this.input.update(this.x+this.textOffset*Mideas.getDisplayXFactor(), this.y + 3 * Mideas.getDisplayYFactor(), this.inputMaxWidth*Mideas.getDisplayXFactor());
		if (this.crossButton != null)
			this.crossButton.update(this.x+this.width-CROSS_BUTTON_X_OFFSET*Mideas.getDisplayXFactor(), y+CROSS_BUTTON_Y_OFFSET*Mideas.getDisplayYFactor());
	}
	
	public void updateSize()
	{
		if (!this.input.hasMultipleLine())
			this.heightSave = (short)Sprites.edit_box_left_border.getImageHeight();
		this.x = (short)(this.parentFrame.getX() + this.xSave * Mideas.getDisplayXFactor());
		this.y = (short)(this.parentFrame.getY() + this.ySave * Mideas.getDisplayYFactor());
		this.width = (short)(this.widthSave * Mideas.getDisplayXFactor());
		this.height = (short)(this.heightSave * Mideas.getDisplayYFactor());
		this.input.update(this.x + this.textOffset * Mideas.getDisplayXFactor(), this.y + 3 * Mideas.getDisplayYFactor(), this.inputMaxWidth * Mideas.getDisplayXFactor());
		if (this.crossButton != null)
			this.crossButton.updateSize();
	}
	
	public void initParentFrame(Frame frame)
	{
		this.parentFrame = frame;
		this.x = (short)(this.parentFrame.getX() + this.xSave * Mideas.getDisplayXFactor());
		this.y = (short)(this.parentFrame.getY() + this.ySave * Mideas.getDisplayYFactor());
		this.input.update(this.x + this.textOffset * Mideas.getDisplayXFactor(), this.y + 3 * Mideas.getDisplayYFactor(), this.inputMaxWidth * Mideas.getDisplayXFactor());
		if (this.crossButton != null)
			this.crossButton.initParentFrame(frame);
	}
	
	public short getWidth()
	{
		return (this.width);
	}
	
	protected boolean keyEvent(@SuppressWarnings("unused") char c) {return false;}
}
