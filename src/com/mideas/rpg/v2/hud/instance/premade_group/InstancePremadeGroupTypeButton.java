package com.mideas.rpg.v2.hud.instance.premade_group;

import org.lwjgl.input.Mouse;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.game.preamade_group.PremadeGroupType;
import com.mideas.rpg.v2.utils.UIElement;
import com.mideas.rpg.v2.utils.UIElementType;

public class InstancePremadeGroupTypeButton extends UIElement
{
	
	private final PremadeGroupType type;
	private final String text;
	private final InstancePremadeGroupBrowseCategoryFrame parentFrame;
	private short xSave;
	private short ySave;
	private short x;
	private short y;
	private short width;
	private short height;
	private boolean isMouseOver;
	private boolean isLeftClickDown;
	private boolean isRightClickDown;

	public final static short X_OFFSET = 30;
	public final static short Y_DEFAULT_OFFSET = 30;
	public final static short Y_OFFSET = 30;
	public final static short WIDTH = 200;
	public final static short HEIGHT = 200;

	InstancePremadeGroupTypeButton(InstancePremadeGroupBrowseCategoryFrame parentFrame, int x, int y, String name, PremadeGroupType type)
	{
		super(name, UIElementType.BUTTON);
		this.type = type;
		this.text = type.getText();
		this.parentFrame = parentFrame;
		this.xSave = (short)x;
		this.ySave = (short)y;
		this.x = (short)(x * Mideas.getDisplayXFactor() + parentFrame.getX());
		this.y = (short)(y * Mideas.getDisplayYFactor() + parentFrame.getY());
	}
	
	@Override
	public void draw()
	{
		updateSize();
		if (this.isLeftClickDown || this.isRightClickDown)
			;
		if (this.isMouseOver)
			;
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
			if (Mouse.getEventButtonState())
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
			else if (!Mouse.getEventButtonState())
			{
				if (Mouse.getEventButton() == 0)
				{
					if (this.isLeftClickDown)
						this.parentFrame.setSelectedButton(this);
					this.isLeftClickDown = false;
				}
				else if (Mouse.getEventButton() == 1)
				{
					this.isRightClickDown = false;
				}
			}
		}
		if (!Mouse.getEventButtonState())
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
	
	private void updateSize()
	{
		if (!this.shouldUpdateSize)
			return;
		this.x = (short)(this.x * Mideas.getDisplayXFactor() + this.parentFrame.getX());
		this.y = (short)(this.y * Mideas.getDisplayYFactor() + this.parentFrame.getY());
		this.width = (short)(WIDTH * Mideas.getDisplayXFactor());
		this.height = (short)(HEIGHT * Mideas.getDisplayYFactor());
	}
	
	public PremadeGroupType getType()
	{
		return (this.type);
	}
	
	@Override
	public void resetState()
	{
		this.isMouseOver = false;
		this.isLeftClickDown = false;
		this.isRightClickDown = false;
	}
}
