package com.mideas.rpg.v2.utils;

import java.util.HashMap;

import com.mideas.rpg.v2.Mideas;

public abstract class  UIElement {

	protected String name;
	private final UIElementType type;
	protected Frame parentFrame;
	protected short xSave;
	protected short x;
	protected short ySave;
	protected short y;
	protected short widthSave;
	protected short width;
	protected short heightSave;
	protected short height;
	protected boolean shouldUpdateSize;
	private final static HashMap<String, UIElement> elementMap = new HashMap<String, UIElement>();

	public UIElement(String name, UIElementType type)
	{
		this.name = name;
		this.type = type;
		if (elementMap.containsKey(this.name))
		{
			System.out.println("ERROR: Duplicate name for an UIElmeent: " + this.name);
			DebugUtils.printStackTrace("Element creation trace:");
		}
		else
		{
			elementMap.put(this.name, this);
		}
	}
	
	public String getName()
	{
		return (this.name);
	}
	
	public void setName(String name)
	{
		if (elementMap.containsKey(name))
		{
			System.out.println("ERROR: Duplicate name for an UIElmeent: " + this.name);
			return;
		}
		elementMap.remove(this.name);
		this.name = name;
		elementMap.put(this.name, this);
	}
	
	public Frame getParentFrame()
	{
		return (this.parentFrame);
	}
	
	public UIElementType getElementType()
	{
		return (this.type);
	}
	
	public boolean keyboardEvent()
	{
		return (false);
	}
	
	public void shouldUpdateSize()
	{
		this.shouldUpdateSize = true;
	}
	
	public static UIElement getElementByName(String name)
	{
		return (elementMap.get(name));
	}
	
	public void resetState() {}

	public void setX(float x)
	{
		this.xSave = (short)x;
		if (this.parentFrame != null) //TODO: fix every Frame and remove this check
			this.x = (short)(this.parentFrame.getX() + this.xSave * Mideas.getDisplayXFactor());
	}

	public void setY(float y)
	{
		this.ySave = (short)y;
		if (this.parentFrame != null) //TODO: fix every Frame and remove this check
			this.y = (short)(this.parentFrame.getY() + this.ySave * Mideas.getDisplayYFactor());
	}

	public short getX()
	{
		return (this.x);
	}
	
	public short getY()
	{
		return (this.y);
	}

	public void setWidth(float width)
	{
		this.widthSave = (short)width;
		this.width = (short)(this.widthSave * Mideas.getDisplayXFactor());
	}
	
	public void setHeight(float height)
	{
		this.heightSave = (short)height;
		this.height = (short)(this.heightSave * Mideas.getDisplayXFactor());
	}
	
	public short getWidth()
	{
		return (this.width);
	}
	
	public short getHeight()
	{
		return (this.height);
	}
	
	public abstract void draw();
	
	public abstract boolean mouseEvent();
}
