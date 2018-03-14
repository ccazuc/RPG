package com.mideas.rpg.v2.utils;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.render.TTF;

public class LargeTextDisplay extends UIElement {

	private final short xSave;
	private final short ySave;
	private final short widthSave;
	private final short heightSave;
	private final short scrollbarXSave;
	private final short scrollbarYSave;
	private final short scrollbarWidthSave;
	private final short scrollbarHeightSave;
	private final short textXOffsetSave;
	private final short textYOffsetSave;
	private final short lineHeightSave;
	private final Color color;
	private final TTF font;
	private short width;
	private short height;
	private short lineHeight;
	private short textNumberLine;
	private short currentLine;
	private String text;
	private String textSave;
	private Color shadowcolor = Color.decode("#3B2A16");
	
	public LargeTextDisplay(Frame parentFrame, String name, short x, short y, short width, short height, short textXOffset, short textYOffset, short lineHeight, short scrollbarX, short scrollbarY, short scrollbarWidt, short scrollbarHeight, TTF font, Color color)
	{
		super(name, UIElementType.LARGE_TEXT_DISPLAY);
		this.parentFrame = parentFrame;
		this.xSave = x;
		this.ySave = y;
		this.widthSave = width;
		this.heightSave = height;
		this.lineHeightSave = lineHeight;
		this.scrollbarXSave = scrollbarX;
		this.scrollbarYSave = scrollbarY;
		this.scrollbarWidthSave = scrollbarWidt;
		this.scrollbarHeightSave = scrollbarHeight;
		this.textXOffsetSave = textXOffset;
		this.textYOffsetSave = textYOffset;
		this.font = font;
		this.color = color;
	}
	
	public void initParentFrame(Frame parentFrame)
	{
		this.parentFrame = parentFrame;
		updateSize();
	}
	
	@Override
	public void draw()
	{
		int i = -1;
		this.font.drawBegin();
		short x = this.x;
		short y = (short)(this.y - this.currentLine * this.lineHeight);
		char c;
		while (++i < this.text.length())
		{
			c = this.text.charAt(i);
			if (c == '\n' || c == Input.ENTER_CHAR_VALUE)
			{
				y += this.lineHeight;
				x = this.x;
				if (y - this.y + this.lineHeight >= this.height)
					break;
				continue;
			}
			this.font.drawCharPart(x + 1, y + 1, c, Color.BLACK, .2f);
			this.font.drawCharPart(x, y, c, this.shadowcolor);
			x += (short)this.font.getWidth(c);
		}
		this.font.drawEnd();
	}
	
	private void formatText()
	{
		if (this.textSave == null)
			return;
		//System.out.println("---------------------------------------------------------------------------------------");
		//System.out.println(this.textSave);
		StringBuilder builder = new StringBuilder();
		int i = -1;
		char c;
		float x = (short)(this.textXOffsetSave * Mideas.getDisplayXFactor());
		short numberLine = 0;
		int previousSpace = -1;
		while (++i < this.textSave.length())
		{
			c = this.textSave.charAt(i);
			if (c == '\n' || c == Input.ENTER_CHAR_VALUE || (i < this.textSave.length() - 1 && x + this.font.getWidth(this.textSave.charAt(i + 1)) >= this.width))
			{
				previousSpace = -1;
				if (c != '\n' && c != Input.ENTER_CHAR_VALUE)
				{
					previousSpace = findPreviousSpace(builder, i - 1);
					//System.out.println("PreviousSpace: " + previousSpace + ", builderLength: " + builder.length());
					if (previousSpace != -1 && previousSpace < builder.length() - 1)
					{
						//System.out.println("PreviousSpaceChar: " + builder.substring(previousSpace, previousSpace + 1));
						builder.replace(previousSpace, previousSpace + 1, "\n");
						//System.out.println("BuilderToString: " + builder.toString());
						//System.out.println("CharAt(i)" + this.textSave.charAt(i));
						//System.out.println("PreviousSpace != -1");
						builder.append(c);
					}
					else
					{
						if (c != ' ')
							builder.append(c);
						builder.insert(builder.length() - 1, '\n');
						//System.out.println("PreviousSpace == -1");
					}
					//System.out.println("char: '" + c + "', +1: '" + this.textSave.charAt(i + 1) + "', -1: '" + this.textSave.charAt(i - 1) + "'");
				}
				else
					builder.append('\n');
				++numberLine;
				//System.out.println("Line jump!");
				if (previousSpace != -1 && previousSpace < builder.length() - 1)
				{
					x = (short)(this.textXOffsetSave * Mideas.getDisplayXFactor() + this.font.getWidth(builder.substring(previousSpace, builder.length())));
					//System.out.println("Substing: " + builder.substring(previousSpace , builder.length()));
				}
				else
					x = (short)(this.textXOffsetSave * Mideas.getDisplayXFactor());
				continue;
			}
			//System.out.println("x: " + x + ", width: " + this.width + ", char: " + c + ", i: " + i);
			builder.append(c);
			x += this.font.getWidth(c);
		}
		//System.out.println(builder.toString());
		//System.out.println("---------------------------------------------------------------------------------------");
		this.textNumberLine = numberLine;
		this.text = builder.toString();
	}
	
	private static int findPreviousSpace(StringBuilder builder, int index)
	{
		while (index >= 0)
		{
			if (builder.charAt(index) == ' ')
				return (index);
			if (builder.charAt(index) == '\n')
				return (-1);
			--index;
		}
		return (-1);
	}
	
	public void setText(String text)
	{
		this.textSave = text;
		formatText();
	}
	
	@Override
	public boolean mouseEvent()
	{
		return (false);
	}
	
	@Override
	public boolean keyboardEvent()
	{
		return (false);
	}
	
	@Override
	public Frame getParentFrame()
	{
		return (this.parentFrame);
	}
	
	public void updateSize()
	{
		this.x = (short)(this.parentFrame.getX() + this.xSave * Mideas.getDisplayXFactor() + this.textXOffsetSave * Mideas.getDisplayXFactor());
		this.y = (short)(this.parentFrame.getY() + this.ySave * Mideas.getDisplayYFactor() + this.textYOffsetSave * Mideas.getDisplayYFactor());
		this.width = (short)(this.widthSave * Mideas.getDisplayXFactor());
		this.height = (short)(this.heightSave * Mideas.getDisplayYFactor());
		this.lineHeight = (short)(this.lineHeightSave * Mideas.getDisplayYFactor());
		formatText();
	}
}
