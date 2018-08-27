package com.mideas.rpg.v2.utils;

public abstract class Frame extends UIElement
{

	public Frame(String name)
	{
		super(name, UIElementType.FRAME);
	}

	@Override
	public abstract void draw();
	@Override
	public abstract boolean mouseEvent();
	@Override
	public abstract boolean keyboardEvent();
	public abstract void open();
	public abstract void close();
	public abstract void reset();
	public abstract boolean isOpen();
}
