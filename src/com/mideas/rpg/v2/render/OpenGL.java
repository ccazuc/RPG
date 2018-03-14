package com.mideas.rpg.v2.render;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.lwjgl.opengl.GL11;

public final class OpenGL {
	
	public static final int GL_LINEAR_MIPMAP_NEAREST = GL11.GL_LINEAR_MIPMAP_NEAREST;
	public static final int GL_LINEAR_MIPMAP_LINEAR = GL11.GL_LINEAR_MIPMAP_LINEAR;
	public static final int GL_ONE_MINUS_SRC_ALPHA = GL11.GL_ONE_MINUS_SRC_ALPHA;
	public static final int GL_TEXTURE_MAG_FILTER = GL11.GL_TEXTURE_MAG_FILTER;
	public static final int GL_TEXTURE_MIN_FILTER = GL11.GL_TEXTURE_MIN_FILTER;
	public static final int GL_COLOR_BUFFER_BIT = GL11.GL_COLOR_BUFFER_BIT;
	public static final int GL_DEPTH_BUFFER_BIT = GL11.GL_DEPTH_BUFFER_BIT;
	public static final int GL_UNSIGNED_BYTE = GL11.GL_UNSIGNED_BYTE;
	public static final int GL_UNSIGNED_INT = GL11.GL_UNSIGNED_INT;
	public static final int GL_LINE_SMOOTH = GL11.GL_LINE_SMOOTH;
	public static final int GL_LINE_STRIP = GL11.GL_LINE_STRIP;
	public static final int GL_TEXTURE_2D = GL11.GL_TEXTURE_2D;
	public static final int GL_PROJECTION = GL11.GL_PROJECTION;
	public static final int GL_LINE_LOOP = GL11.GL_LINE_LOOP;
	public static final int GL_MODELVIEW = GL11.GL_MODELVIEW;
	public static final int GL_SRC_ALPHA = GL11.GL_SRC_ALPHA;
	public static final int GL_NEAREST = GL11.GL_NEAREST;
	public static final int GL_LINEAR = GL11.GL_LINEAR;
	public static final int GL_POINTS = GL11.GL_POINTS;
	public static final int GL_QUADS = GL11.GL_QUADS;
	public static final int GL_LINES = GL11.GL_LINES;
	public static final int GL_FRONT = GL11.GL_FRONT;
	public static final int GL_RGBA8 = GL11.GL_RGBA8;
	public static final int GL_BLEND = GL11.GL_BLEND;
	public static final int GL_RGBA = GL11.GL_RGBA;
	
	public static final void glEnable(final int cap) {
		GL11.glEnable(cap);
	}
	
	public static final void glDisable(final int cap) {
		GL11.glDisable(cap);
	}
	
	public static final void glBegin(final int mode) {
		GL11.glBegin(mode);
	}
	
	public static final void glEnd() {
		GL11.glEnd();
	}
	
	public static final void glTexCoord1f(final float x) {
		GL11.glTexCoord1f(x);
	}
	
	public static final void glTexCoord1d(final double x) {
		GL11.glTexCoord1d(x);
	}
	
	public static final void glTexCoord2f(final float x, final float y) {
		GL11.glTexCoord2f(x, y);
	}
	
	public static final void glTexCoord2d(final double x, final double y) {
		GL11.glTexCoord2d(x, y);
	}
	
	public static final void glTexCoord3f(final float x, final float y, final float z) {
		GL11.glTexCoord3f(x, y, z);
	}
	
	public static final void glTexCoord3d(final double x, final double y, final double z) {
		GL11.glTexCoord3d(x, y, z);
	}
	
	public static final void glVertex2i(final int x, final int y) {
		GL11.glVertex2i(x, y);
	}
	
	public static final void glVertex2f(final float x, final float y) {
		GL11.glVertex2f(x, y);
	}
	
	public static final void glVertex2d(final double x, final double y) {
		GL11.glVertex2d(x, y);
	}
	
	public static final void glColor3f(final float red, final float green, final float blue) {
		GL11.glColor3f(red, green, blue);
	}
	
	public static final void glColor3d(final double red, final double green, final double blue) {
		GL11.glColor3d(red, green, blue);
	}
	
	public static final void glColor4f(final float red, final float green, final float blue, final float alpha) {
		GL11.glColor4f(red, green, blue, alpha);
	}
	
	public static final void glColor4d(final double red, final double green, final double blue, final double alpha) {
		GL11.glColor4d(red, green, blue, alpha);
	}
	
	public static final int glGenTextures() {
		return GL11.glGenTextures();
	}
	
	public static final void glBindTexture(final int target, final int texture) {
		GL11.glBindTexture(target, texture);
	}
	
	public static final void glTexParameteri(final int target, final int pname, final int param) {
		GL11.glTexParameteri(target, pname, param);
	}
	
	public static final void glLineWidth(final float width) {
		GL11.glLineWidth(width);
	}
	
	public static final void glPointSize(final float size) {
		GL11.glPointSize(size);
	}
	
	public static final void glViewport(final int x, final int y, final int width, final int height) {
		GL11.glViewport(x, y, width, height);
	}
	
	public static final void glMatrixMode(final int mode) {
		GL11.glMatrixMode(mode);
	}
	
	public static final void glLoadIdentity() {
		GL11.glLoadIdentity();
	}
	
	public static final void glOrtho(final double left, final double right, final double bottom, final double top, final double zNear, final double zFar) {
		GL11.glOrtho(left, right, bottom, top, zNear, zFar);
	}
	
	public static final void glBlendFunc(final int sfactor, final int dfactor) {
		GL11.glBlendFunc(sfactor, dfactor);
	}
	
	public static final void glClear(final int mask) {
		GL11.glClear(mask);
	}
	
	public static final void glReadBuffer(final int mode) {
		GL11.glReadBuffer(mode);
	}
	
	public static final void glReadPixels(final int x, final int y, final int width, final int height, final int format, final int type, final ByteBuffer pixels) {
		GL11.glReadPixels(x, y, width, height, format, type, pixels);
	}
	
	public static final void glTexImage2D(final int target, final int level, final int internalformat, final int width, final int height, final int border, final int format, final int type, final ByteBuffer pixels) {
		GL11.glTexImage2D(target, level, internalformat, width, height, border, format, type, pixels);
	}
	
	public static final void glTexImage2D(final int target, final int level, final int internalformat, final int width, final int height, final int border, final int format, final int type, final IntBuffer pixels) {
		GL11.glTexImage2D(target, level, internalformat, width, height, border, format, type, pixels);
	}
	
}
