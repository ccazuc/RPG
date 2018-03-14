package com.mideas.rpg.v2.utils;

import org.lwjgl.input.Mouse;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.render.Draw;
import com.mideas.rpg.v2.render.Sprites;

public class CrossButton2 extends UIElement {

	private final short xSave;
	private final short ySave;
	private final short widthSave;
	private final short heightSave;
	private short width;
	private short height;
	private boolean mouseHover;
	private boolean leftClickDown;
	private boolean rightClickDown;
	
	public CrossButton2(Frame parentFrame, String name, short x, short y, short width, short height)
	{
		super(name, UIElementType.CROSS_BUTTON);
		this.parentFrame = parentFrame;
		this.xSave = x;
		this.ySave = y;
		this.widthSave = width;
		this.heightSave = height;
		this.width = (short)(this.widthSave * Mideas.getDisplayXFactor());
		this.height = (short)(this.heightSave * Mideas.getDisplayYFactor());
	}
	
	@Override
	public void draw()
	{
		if (this.leftClickDown || this.rightClickDown)
			Draw.drawQuad(Sprites.new_cross_button_down, this.x, this.y, this.width, this.height);
		else
			Draw.drawQuad(Sprites.new_cross_button, this.x, this.y, this.width, this.height);
		if (this.mouseHover)
			Draw.drawQuadBlend(Sprites.new_cross_button_hover, this.x, this.y, this.width, this.height);
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
	
	public void initParentFrame(Frame parentFrame)
	{
		this.parentFrame = parentFrame;
		updateSize();
	}
	
	public void updateSize()
	{
		this.width = (short)(this.widthSave * Mideas.getDisplayXFactor());
		this.height = (short)(this.heightSave * Mideas.getDisplayYFactor());
		this.x = (short)(this.parentFrame.getX() + this.xSave * Mideas.getDisplayXFactor());
		this.y = (short)(this.parentFrame.getY() + this.ySave * Mideas.getDisplayYFactor());
	}
	
	public void reset()
	{
		this.mouseHover = false;
		this.leftClickDown = false;
		this.rightClickDown = false;
	}
	
	public void onLeftClickUp() {}
	public void onLeftClickDown() {}
	public void onRightClickUp() {}
	public void onRightClickDown() {}
}
