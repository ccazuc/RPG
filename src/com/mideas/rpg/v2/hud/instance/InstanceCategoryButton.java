package com.mideas.rpg.v2.hud.instance;

import org.lwjgl.input.Mouse;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.utils.UIElement;
import com.mideas.rpg.v2.utils.UIElementType;

public class InstanceCategoryButton extends UIElement
{

	private final InstanceMenuFrame parentFrame;
	private InstanceCategoryFrame frame;
	private boolean isLeftClickDown;
	private boolean isRightClickDown;
	private boolean isMouseOver;
	
	public final static short BUTTON_X_OFFSET = 20;
	public final static short BUTTON_Y_OFFSET = 60;
	public final static short BUTTON_Y_DEFAULT_OFFSET = 100;
	public final static short BUTTON_WIDTH = 150;
	public final static short BUTTON_HEIGHT = 50;
	
	public InstanceCategoryButton(InstanceMenuFrame parentFrame, String name, short y)
	{
		super(name + "Button", UIElementType.PREMADE_GROUP_CATEGORY_BUTTON);
		this.xSave = BUTTON_X_OFFSET;
		this.ySave = y;
		this.parentFrame = parentFrame;
		this.width = (short)(BUTTON_WIDTH * Mideas.getDisplayXFactor());
		this.height = (short)(BUTTON_HEIGHT * Mideas.getDisplayYFactor());
		this.x = (short)(this.parentFrame.getX() + this.xSave * Mideas.getDisplayXFactor());
		this.y = (short)(this.parentFrame.getY() + this.ySave * Mideas.getDisplayYFactor());
	}
	
	@Override
	public void draw()
	{
		updateSize();
		if (this.isMouseOver)
			;
		if (this.parentFrame.getSelectedMenu() == this)
			this.frame.draw();
	}
	
	@Override
	public boolean mouseEvent()
	{
		if (Mideas.getHover() && Mideas.mouseX() >= this.x && Mideas.mouseX() <= this.x + this.width && Mideas.mouseY() >= this.y && Mideas.mouseY() <= this.y + this.height)
		{
			this.isMouseOver = true;
			Mideas.setHover(false);
		}
		else
			this.isMouseOver = false;
		if (this.isMouseOver)
		{
			if (!Mouse.getEventButtonState())
			{
				if (this.isLeftClickDown && Mouse.getEventButton() == 0 && this.parentFrame.getSelectedMenu() != this)
				{
					this.isLeftClickDown = false;
					this.parentFrame.setSelectedMenu(this);
				}
				else if (this.isRightClickDown && Mouse.getEventButton() == 1)
				{
					this.isRightClickDown = false;
				}
			}
			else if (Mouse.getEventButtonState())
			{
				if (Mouse.getEventButton() == 0)
				{
					this.isLeftClickDown = true;
				}
				else if (Mouse.getEventButton() == 1)
				{
					this.isRightClickDown = true;
				}
			}
		}
		if (this.parentFrame.getSelectedMenu() == this && this.frame.mouseEvent())
			return (true);
		return (false);
	}
	
	@Override
	public boolean keyboardEvent()
	{
		if (this.parentFrame.getSelectedMenu() == this && this.frame.keyboardEvent())
			return (true);
		return (false);
	}
	
	@Override
	public void resetState()
	{
		this.isMouseOver = false;
		this.isLeftClickDown = false;
		this.isRightClickDown = false;
		this.frame.resetState();
	}
	
	private void updateSize()
	{
		if (!this.shouldUpdateSize)
			return;
		this.x = (short)(this.parentFrame.getX() + this.xSave * Mideas.getDisplayXFactor());
		this.y = (short)(this.parentFrame.getY() + this.ySave * Mideas.getDisplayYFactor());
		this.shouldUpdateSize = false;
	}
	
	public boolean isSelected()
	{
		return (this.parentFrame.getSelectedMenu() == this);
	}
	
	public void setFrame(InstanceCategoryFrame frame)
	{
		this.frame = frame;
	}
	
	@Override
	public void setX(float x)
	{
		this.xSave = (short)x;
		this.x = (short)(this.parentFrame.getX() + this.xSave * Mideas.getDisplayXFactor());
	}
	
	@Override
	public void setY(float y)
	{
		this.xSave = (short)y;
		this.y = (short)(this.parentFrame.getY() + this.ySave * Mideas.getDisplayYFactor());
	}

	@Override
	public void shouldUpdateSize()
	{
		this.shouldUpdateSize = true;
		this.frame.shouldUpdateSize();
	}
}
