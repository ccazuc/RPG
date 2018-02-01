package com.mideas.rpg.v2.utils;

import org.lwjgl.input.Mouse;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.utils.render.Draw;
import com.mideas.rpg.v2.utils.render.Sprites;
import com.mideas.rpg.v2.utils.render.TTF;

public class FrameTab2 extends UIElement {

	private final short ySave;
	private final short defaultWidth;
	private final short middleAnchorSave;
	private short widthSave;
	private short heightSave;
	private final String text;
	private final short textWidth;
	private final TTF font;
	private short textX;
	private short textY;
	private short xMiddlePart;
	private short xRightPart;
	private short width;
	private short height;
	private short middleWidth;
	private short borderWidth;
	private boolean isSelected;
	private boolean mouseHover;
	private boolean leftClickDown;
	private boolean rightClickDown;
	
	public FrameTab2(Frame parentFrame, String name, short x, short y, short width, String text, TTF font, boolean isSelected)
	{
		super(name, UIElementType.FRAME_TAB);
		this.parentFrame = parentFrame;
		this.middleAnchorSave = x;
		this.defaultWidth = width;
		if (isSelected)
		{
			this.heightSave = (short)Sprites.frame_tab_active2.getImageHeight();
			this.borderWidth = (short)24;
			this.widthSave = width;
		}
		else
		{
			this.heightSave = (short)(Sprites.frame_tab_not_active2.getImageHeight() + 3);
			this.borderWidth = (short)6;
			this.widthSave = (short)(width - 10);
		}
		this.ySave = y;
		this.isSelected = isSelected;
		this.text = text;
		this.font = font;
		this.textWidth = (short)this.font.getWidth(this.text);
		this.middleWidth = (short)(this.width - 2 * this.borderWidth);
		this.xMiddlePart = (short)(this.x + this.borderWidth);
		this.xRightPart = (short)(this.x + this.borderWidth + this.middleWidth);
		this.width = (short)(this.widthSave * Mideas.getDisplayXFactor());
		this.height = (short)(this.heightSave * Mideas.getDisplayYFactor());
		this.textX = (short)(this.x + this.width / 2 - this.textWidth / 2);
		this.textY = (short)(this.y + this.height / 2 - this.font.getLineHeight() / 2 - 2 * Mideas.getDisplayYFactor());
	}
	
	@Override
	public void draw()
	{
		if (this.isSelected)
		{
			Sprites.frame_tab_active2.drawBegin();
			Draw.drawQuadPart(Sprites.frame_tab_active2, this.x, this.y, this.borderWidth, this.height, 0, 0, 24, 38);
			Draw.drawQuadPart(Sprites.frame_tab_active2, this.xMiddlePart, this.y, this.middleWidth, this.height, 24, 0, 24, 38);
			Draw.drawQuadPart(Sprites.frame_tab_active2, this.xRightPart, this.y, this.borderWidth, this.height, 48, 0, 24, 38);
			Sprites.frame_tab_active2.drawEnd();
			this.font.drawStringShadow(this.textX, this.textY, this.text, Color.WHITE, Color.BLACK, 1, 1, 1);
		}
		else
		{
			Sprites.frame_tab_not_active2.drawBegin();
			Draw.drawQuadPart(Sprites.frame_tab_not_active2, this.x, this.y, this.borderWidth, this.height, 0, 0, 6, 25);
			Draw.drawQuadPart(Sprites.frame_tab_not_active2, this.xMiddlePart, this.y, this.middleWidth, this.height, 6, 0, 6, 25);
			Draw.drawQuadPart(Sprites.frame_tab_not_active2, this.xRightPart, this.y, this.borderWidth, this.height, 12, 0, 6, 25);
			Sprites.frame_tab_not_active2.drawEnd();
			if (this.mouseHover)
			{
				Draw.drawQuadBlend(Sprites.frame_tab_not_hover2, this.x, this.y - 5 * Mideas.getDisplayYFactor(), this.width, this.height);
				if (this.leftClickDown || this.rightClickDown)
					this.font.drawStringShadow(this.textX + 2 * Mideas.getDisplayXFactor(), this.textY + 2 * Mideas.getDisplayYFactor(), this.text, Color.WHITE, Color.BLACK, 1, 1, 1);
				else
					this.font.drawStringShadow(this.textX, this.textY, this.text, Color.WHITE, Color.BLACK, 1, 1, 1);
			}
			else if (this.leftClickDown || this.rightClickDown)
				this.font.drawStringShadow(this.textX + 2 * Mideas.getDisplayXFactor(), this.textY + 2 * Mideas.getDisplayYFactor(), this.text, Color.YELLOW, Color.BLACK, 1, 1, 1);
			else
				this.font.drawStringShadow(this.textX, this.textY, this.text, Color.YELLOW, Color.BLACK, 1, 1, 1);
		}
	}
	
	@Override
	public boolean mouseEvent()
	{
		if (Mideas.getHover() && Mideas.mouseX() >= this.x && Mideas.mouseX() <= this.x + this.width && Mideas.mouseY() >= this.y && Mideas.mouseY() <= this.y + this.height)
		{
			Mideas.setHover(this, false);
			this.mouseHover = true;
		}
		else
			this.mouseHover = false;
		if (this.mouseHover)
		{
			if (Mouse.getEventButtonState())
			{
				if (Mouse.getEventButton() == 0)
				{
					this.leftClickDown = true;
					onLeftClickDown();
					return (true);
				}
				else if (Mouse.getEventButton() == 1)
				{
					this.rightClickDown = true;
					onRightClickDown();
					return (true);
				}
			}
			else
			{
				if (Mouse.getEventButton() == 0)
				{
					this.leftClickDown = false;
					onLeftClickUp();
					return (true);
				}
				else if (Mouse.getEventButton() == 1)
				{
					this.rightClickDown = false;
					onRightClickUp();
					return (true);
				}
			}
		}
		else if (!Mouse.getEventButtonState())
		{
			if (Mouse.getEventButton() == 0)
				this.leftClickDown = false;
			else if (Mouse.getEventButton() == 1)
				this.rightClickDown = false;
		}
		return (false);
	}
	
	@Override
	public boolean keyboardEvent()
	{
		return (false);
	}

	public void setIsSelected(boolean we)
	{
		if (we)
		{
			this.heightSave = (short)Sprites.frame_tab_active2.getImageHeight();
			this.borderWidth = (short)24;
			this.widthSave = this.defaultWidth;
			updateSize();
		}
		else
		{
			this.heightSave = (short)(Sprites.frame_tab_not_active2.getImageHeight() + 3);
			this.borderWidth = (short)6;
			this.widthSave = (short)(this.defaultWidth - 10);
			updateSize();
		}
		this.isSelected = we;
	}
	
	public void initParentFrame(Frame parentFrame)
	{
		this.parentFrame = parentFrame;
		updateSize();
	}
	
	public void onLeftClickUp() {}
	public void onLeftClickDown() {}
	public void onRightClickUp() {}
	public void onRightClickDown() {}
	
	public void updateSize()
	{
		this.width = (short)(this.widthSave * Mideas.getDisplayXFactor());
		this.height = (short)(this.heightSave * Mideas.getDisplayYFactor());
		this.y = (short)(this.parentFrame.getY() + this.ySave * Mideas.getDisplayYFactor());
		this.x = (short)(this.parentFrame.getX() + this.middleAnchorSave * Mideas.getDisplayXFactor() - this.width / 2);
		this.textX = (short)(this.x + this.width / 2 - this.textWidth / 2);
		this.textY = (short)(this.y + this.height / 2 - this.font.getLineHeight() / 2 - 2 * Mideas.getDisplayYFactor());
		this.middleWidth = (short)(this.width - 2 * this.borderWidth);
		this.xMiddlePart = (short)(this.x + this.borderWidth);
		this.xRightPart = (short)(this.x + this.borderWidth + this.middleWidth);
	}
	
	public void reset()
	{
		this.mouseHover = false;
		this.leftClickDown = false;
		this.rightClickDown = false;
	}
	
	public boolean isSelected()
	{
		return (true);
	}
}
