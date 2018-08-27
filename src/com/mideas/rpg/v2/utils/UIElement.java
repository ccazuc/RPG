package com.mideas.rpg.v2.utils;

public abstract class  UIElement {

	private final String name;
	private final UIElementType type;
	protected Frame parentFrame;
	protected short x;
	protected short y;
	protected boolean shouldUpdateSize;

	public UIElement(String name, UIElementType type)
	{
		this.name = name;
		this.type = type;
	}
	
	public String getName()
	{
		return (this.name);
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
