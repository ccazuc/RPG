package com.mideas.rpg.v2.utils;

import com.mideas.rpg.v2.OpenGL;

public class Colors {
	
	public static final Colors WHITE = new Colors(1, 1, 1);
	public static final Colors LIGHTGREY = new Colors(.75f, .75f, .75f);
	public static final Colors GREY = new Colors(.5f, .5f, .5f);
	public static final Colors DARKGREY = new Colors(.25f, .25f, .25f);
	public static final Colors BLACK = new Colors(0, 0, 0);
	public static final Colors LIGHTRED = new Colors(1, .25f, .25f);
	public static final Colors RED = new Colors(1, 0, 0);
	public static final Colors DARKRED = new Colors(.5f, 0, 0);
	public static final Colors LIGHTGREEN = new Colors(.25f, 1, .25f);
	public static final Colors GREEN = new Colors(0, 1, 0);
	public static final Colors DARKGREEN = new Colors(0, .5f, 0);
	public static final Colors LIGHTBLUE = new Colors(.25f, 1, 1);
	public static final Colors BLUE = new Colors(0, 0, 1);
	public static final Colors DARKBLUE = new Colors(0, 0, .5f);
	public static final Colors LIGHTYELLOW = new Colors(1, 1, .33f);
	public static final Colors YELLOW = new Colors(1, 1, 0);
	public static final Colors DARKYELLOW = new Colors(.5f, .5f, 0);
	public static final Colors LIGHTORANGE = new Colors(1, .75f, .25f);
	public static final Colors ORANGE = new Colors(1, .5f, 0);
	public static final Colors DARKORANGE = new Colors(.5f, .25f, 0);
	
	private float red;
	private float green;
	private float blue;
	private float alpha;
	
	public Colors(final float rgb, final float alpha) {
		this(rgb, rgb, rgb, alpha);
	}
	
	public Colors(final float rgb) {
		this(rgb, rgb, rgb, 1);
	}
	
	public Colors(final float red, final float green, final float blue) {
		this(red, green, blue, 1);
	}
	
	public Colors(final float red, final float green, final float blue, final float alpha) {
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
