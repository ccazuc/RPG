package com.mideas.rpg.v2.utils;

import java.util.HashMap;

public abstract class  UIElement {

	private String name;
	private final UIElementType type;
	protected Frame parentFrame;
	protected short x;
	protected short y;
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

	public short getX()
	{
		return (this.x);
	}
	
	public short getY()
	{
		return (this.y);
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

	@SuppressWarnings("unused")
	public void setX(int x) {}
	
	@SuppressWarnings("unused")
	public void setY(int y) {}
	
	@SuppressWarnings("unused")
	public void setWidth(int width) {}
	
	@SuppressWarnings("unused")
	public void setHeight(int height) {}
	
	public abstract void draw();
	
	public abstract boolean mouseEvent();
}
