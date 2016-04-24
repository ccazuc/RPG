package com.mideas.rpg.v2.utils;

import com.mideas.rpg.v2.OpenGL;

public class Color {
	public static final Color WHITE = new Color(1, 1, 1);
	public static final Color LIGHTGREY = new Color(.75f, .75f, .75f);
	public static final Color GREY = new Color(.5f, .5f, .5f);
	public static final Color DARKGREY = new Color(.25f, .25f, .25f);
	public static final Color BLACK = new Color(0, 0, 0);
	public static final Color LIGHTRED = new Color(1, .25f, .25f);
	public static final Color RED = new Color(1, 0, 0);
	public static final Color DARKRED = new Color(.5f, 0, 0);
	public static final Color LIGHTGREEN = new Color(.25f, 1, .25f);
	public static final Color GREEN = new Color(0, 1, 0);
	public static final Color DARKGREEN = new Color(0, .5f, 0);
	public static final Color LIGHTBLUE = new Color(.25f, 1, 1);
	public static final Color BLUE = new Color(0, 0, 1);
	public static final Color DARKBLUE = new Color(0, 0, .5f);
	public static final Color LIGHTYELLOW = new Color(1, 1, .33f);
	public static final Color YELLOW = new Color(1, 1, 0);
	public static final Color DARKYELLOW = new Color(.5f, .5f, 0);
	public static final Color LIGHTORANGE = new Color(1, .75f, .25f);
	public static final Color ORANGE = new Color(1, .5f, 0);
	public static final Color DARKORANGE = new Color(.5f, .25f, 0);
	
	private float red;
	private float green;
	private float blue;
	private float alpha;
	
	public Color(final float rgb, final float alpha) {
		this(rgb, rgb, rgb, alpha);
	}
	
	public Color(final float rgb) {
		this(rgb, rgb, rgb, 1);
	}
	
	public Color(final float red, final float green, final float blue) {
		this(red, green, blue, 1);
	}
	
	public Color(final float red, final float green, final float blue, final float alpha) {
		this.red = red;
		this.green = green;
		this.blue = blue;
		this.alpha = alpha;
	}
	
	public final void bind() {
		OpenGL.glColor4f(this.red, this.green, this.blue, this.alpha);
	}
	
	public final float red() {
		return this.red;
	}
	
	public final void setRed(final float red) {
		this.red = red;
	}
	
	public final float green() {
		return this.green;
	}
	
	public final void setGreen(final float green) {
		this.green = green;
	}
	
	public final float blue() {
		return this.blue;
	}
	
	public final void setBlue(final float blue) {
		this.blue = blue;
	}
	
	public final float alpha() {
		return this.alpha;
	}
	
	public final void setAlpha(final float alpha) {
		this.alpha = alpha;
	}
}
