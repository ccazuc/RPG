package com.mideas.rpg.v2.chat.new_chat;

import org.lwjgl.input.Mouse;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.render.Draw;
import com.mideas.rpg.v2.utils.Color;
import com.mideas.rpg.v2.utils.UIElement;
import com.mideas.rpg.v2.utils.UIElementType;

public class ChatFrameTabButton extends UIElement
{

	private boolean isMouseHover;
	private boolean isLeftClickDown;
	private boolean isRightClickDown;
	private final ChatFrameTab parentFrame;
	
	public final static short BUTTON_WIDTH = 80;
	public final static short BUTTON_HEIGHT = 50;

	public ChatFrameTabButton(ChatFrameTab parentFrame, String name, int x, int y)
	{
		super(name, UIElementType.CHAT_FRAME_TAB);
		this.parentFrame = parentFrame;
		this.xSave = (short)x;
		this.x = (short)(x * Mideas.getDisplayXFactor());
		this.widthSave = BUTTON_WIDTH;
		this.width = (short)(BUTTON_WIDTH * Mideas.getDisplayXFactor());
		this.ySave = (short)y;
		this.y = (short)(y * Mideas.getDisplayYFactor());
		this.heightSave = BUTTON_HEIGHT;
		this.height = (short)(BUTTON_HEIGHT * Mideas.getDisplayYFactor());
	}
	
	@Override
	public void draw()
	{
		if (this.parentFrame.isActiveTab())
			Draw.drawColorQuad(this.x, this.y, this.width, this.height, Color.GREEN);
		else
			Draw.drawColorQuad(this.x, this.y, this.width, this.height, Color.RED);
	}
	
	@Override
	public boolean mouseEvent()
	{
		if (Mideas.getHover() && Mideas.mouseX() >= this.x && Mideas.mouseX() <= this.x + this.width && Mideas.mouseY() >= this.y && Mideas.mouseY() <= this.y + this.height)
		{
			Mideas.setHover(this, true);
			this.isMouseHover = true;
		}
		else
		{
			this.isMouseHover = false;
		}
		if (this.isMouseHover)
		{
			if (Mouse.getEventButtonState())
			{
				if (Mouse.getEventButton() == 0)
				{
					this.isLeftClickDown = true;
					onLeftClickDown();
				}
				else if (Mouse.getEventButton() == 1)
				{
					this.isRightClickDown = true;
					onRightClickDown();
				}
			}
			else
			{
				if (Mouse.getEventButton() == 0)
				{
					if (this.isLeftClickDown)
						onLeftClickUp();
					this.isLeftClickDown = false;
				}
				else if (Mouse.getEventButton() == 1)
				{
					if (this.isRightClickDown)
						onRightClickUp();
				}
			}
		}
		else if (!Mouse.getEventButtonState())
		{
			if (Mouse.getEventButton() == 0)
			{
				this.isLeftClickDown = false;
			}
			else if (Mouse.getEventButton() == 1)
			{
				this.isRightClickDown = false;
			}
		}	
		return (false);
	}
	
	protected void onLeftClickDown()
	{
		
	}
	
	protected void onLeftClickUp()
	{
		if (this.parentFrame.isActiveTab())
			return;
		this.parentFrame.setActiveTab();
	}
	
	protected void onRightClickDown()
	{
		
	}
	
	protected void onRightClickUp()
	{
		
	}
	
	@Override
	public void setX(float x)
	{
		this.xSave = (short)x;
		this.x = (short)(this.parentFrame.getFrame().getX() + x * Mideas.getDisplayXFactor());
	}
	
	@Override
	public void setY(float y)
	{
		this.ySave = (short)y;
		this.y = (short)(this.parentFrame.getFrame().getY() + y * Mideas.getDisplayYFactor());
	}
	
	@Override
	public void updateSize()
	{
		this.x = (short)(this.parentFrame.getFrame().getX() + this.xSave * Mideas.getDisplayXFactor());
		this.y = (short)(this.parentFrame.getFrame().getY() + this.ySave * Mideas.getDisplayYFactor());
		this.width = (short)(this.widthSave * Mideas.getDisplayXFactor());
		this.height = (short)(this.height * Mideas.getDisplayYFactor());
	}
}
