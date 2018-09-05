package com.mideas.rpg.v2.chat.new_chat;

import org.lwjgl.input.Mouse;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.utils.UIElement;
import com.mideas.rpg.v2.utils.UIElementType;

public class ChatFrameTabButton extends UIElement
{

	private boolean isMouseHover;
	private boolean isLeftClickDown;
	private boolean isRightClickDown;
	private short width;
	private short height;
	private short xSave;
	private short ySave;
	private final ChatFrameTab parentFrame;

	public ChatFrameTabButton(ChatFrameTab parentFrame, String name, int x, int y)
	{
		super(name, UIElementType.CHAT_FRAME_TAB);
		this.parentFrame = parentFrame;
		this.xSave = (short)x;
		this.ySave = (short)y;
	}
	
	@Override
	public void draw()
	{
		
	}
	
	@Override
	public boolean mouseEvent()
	{
		if (Mideas.getHover() && Mideas.mouseX() >= this.x && Mideas.mouseX() <= this.x + this.width && Mideas.mouseY() >= this.y && Mideas.mouseY() <= this.y + this.height)
		{
			Mideas.setHover(this, true);
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
		if (this.parentFrame.isActiveTab())
			return;
	}
	
	protected void onLeftClickUp()
	{
		
	}
	
	protected void onRightClickDown()
	{
		
	}
	
	protected void onRightClickUp()
	{
		
	}
}
