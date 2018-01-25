package com.mideas.rpg.v2.utils;

public interface UIElement {

	abstract int getX();
	abstract int getY();
	abstract void draw();
	abstract boolean mouseEvent();
	abstract boolean keyboardEvent();
	abstract String getName();
	abstract Frame getParentFrame();
}
