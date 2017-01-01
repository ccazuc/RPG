package com.mideas.rpg.v2.utils;


import org.lwjgl.opengl.GL11;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.OpenGL;
import com.mideas.rpg.v2.utils.Color;

import static org.lwjgl.opengl.GL11.glScissor;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glLineWidth;
import static org.lwjgl.opengl.GL11.glPointSize;
import static org.lwjgl.opengl.GL11.glVertex2f;
import static org.lwjgl.opengl.GL11.glVertex2d;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL14.glBlendFuncSeparate;
import static org.lwjgl.opengl.GL20.glBlendEquationSeparate;
import static org.lwjgl.opengl.GL11.GL_TRIANGLE_FAN;
import static org.lwjgl.opengl.GL11.GL_SCISSOR_TEST;
import static org.lwjgl.opengl.GL14.GL_FUNC_ADD;
import static org.lwjgl.opengl.GL11.GL_DST_COLOR;
import static org.lwjgl.opengl.GL11.GL_LINE_SMOOTH;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_LINE_STRIP;
import static org.lwjgl.opengl.GL11.GL_LINE_LOOP;
import static org.lwjgl.opengl.GL11.GL_POINTS;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_COLOR;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;

public final class Draw {
	
	public final static void drawBegin() {
		glBegin(GL_QUADS);
	}
	
	public final static void drawEnd() {
		glEnd();
	}
	
	public final static void glScissorBegin(final float x, final float y, final float width, final float height) {
		glEnable(GL_SCISSOR_TEST);
		glScissor((int)x, (int)y, (int)width, (int)height);
	}
	
	public final static void glScissorEnd() {
		glDisable(GL_SCISSOR_TEST);
	}
	
	public final static void drawColorQuad(final float x, final float y, final float width, final float height, final Color Colors) {
		drawColorQuad(x, y, width, height, Colors, Colors, Colors, Colors);
	}
	
	public final static void drawColorQuad(final float x, final float y, final float width, final float height, final Color topLeft, final Color topRight, final Color bottomRight, final Color bottomLeft) {
		glDisable(GL_TEXTURE_2D);
		glBegin(GL_QUADS);
		glColor4f(topLeft.red(), topLeft.green(), topLeft.blue(), topLeft.alpha());
		glVertex2f(x, y);
		glColor4f(topRight.red(), topRight.green(), topRight.blue(), topRight.alpha());
		glVertex2f(x+width, y);
		glColor4f(bottomRight.red(), bottomRight.green(), bottomRight.blue(), bottomRight.alpha());
		glVertex2f(x+width, y+height);
		glColor4f(bottomLeft.red(), bottomLeft.green(), bottomLeft.blue(), bottomLeft.alpha());
		glVertex2f(x, y+height);
		glEnd();
		glEnable(GL_TEXTURE_2D);
	}
	
	public final static void drawColorQuadPart(final float x, final float y, final float width, final float height, final Color topLeft, final Color topRight, final Color bottomRight, final Color bottomLeft) {
		glDisable(GL_TEXTURE_2D);
		glColor4f(topLeft.getRed(), topLeft.getGreen(), topLeft.getBlue(), topLeft.getAlpha());
		glVertex2f(x, y);
		glColor4f(topRight.getRed(), topRight.getGreen(), topRight.getBlue(), topRight.getAlpha());
		glVertex2f(x+width, y);
		glColor4f(bottomRight.getRed(), bottomRight.getGreen(), bottomRight.getBlue(), bottomRight.getAlpha());
		glVertex2f(x+width, y+height);
		glColor4f(bottomLeft.getRed(), bottomLeft.getGreen(), bottomLeft.getBlue(), bottomLeft.getAlpha());
		glVertex2f(x, y+height);
		glEnable(GL_TEXTURE_2D);
	}
	
	public final static void drawColorQuadPart(final float x, final float y, final float width, final float height, final Color Colors) {
		drawColorQuadPart(x, y, width, height, Colors, Colors, Colors, Colors);
	}
	
	public final static void drawColorQuadBorder(final float x, final float y, final float width, final float height, final Color Colors) {
		drawColorQuadBorder(x, y, width, height, Colors, Colors, Colors, Colors, 1);
	}
	
	public final static void drawColorQuadBorder(final float x, final float y, final float width, final float height, final Color topLeftColor, final Color topRightColor, final Color bottomRightColor, final Color bottomLeftColor) {
		drawColorQuadBorder(x, y, width, height, topLeftColor, topRightColor, bottomRightColor, bottomLeftColor, 1);
	}

	public final static void drawColorQuadBorder(final float x, final float y, final float width, final float height, final Color Colors, final int lineWeight) {
		drawColorQuadBorder(x, y, width, height, Colors, Colors, Colors, Colors, lineWeight);
	}
	
	public final static void drawColorQuadBorder(final float x, final float y, final float width, final float height, final Color topLeftColor, final Color topRightColor, final Color bottomRightColor, final Color bottomLeftColor, final int lineWeight) {
		glLineWidth(lineWeight);
		glDisable(GL_TEXTURE_2D);
		glBegin(GL_LINE_LOOP);
		glColor4f(topLeftColor.getRed(), topLeftColor.getGreen(), topLeftColor.getBlue(), topLeftColor.getAlpha());
		glVertex2f(x+1, y);
		glColor4f(topRightColor.getRed(), topRightColor.getGreen(), topRightColor.getBlue(), topRightColor.getAlpha());
		glVertex2f(x+width, y);
		glColor4f(bottomRightColor.getRed(), bottomRightColor.getGreen(), bottomRightColor.getBlue(), bottomRightColor.getAlpha());
		glVertex2f(x+width, y+height);
		glColor4f(bottomLeftColor.getRed(), bottomLeftColor.getGreen(), bottomLeftColor.getBlue(), bottomLeftColor.getAlpha());
		glVertex2f(x, y+height);
		glEnd();
		glEnable(GL_TEXTURE_2D);
	}
	
	public static float npot(float size) {
		return (float)Math.pow(2, Math.ceil(Math.log(size)/Math.log(2)));
	}
	
	public final static void drawQuadPart(final Texture texture, final float x, final float y) {
		final float xFrom = 0;
		final float xTo = 1;
		final float yFrom = 0;
		final float yTo = 1;
		int width = texture.getImageWidth();
		int height = texture.getImageHeight();
		glTexCoord2f(xFrom, yFrom);
		glVertex2f(x, y);
		glTexCoord2f(xFrom, yTo);
		glVertex2f(x, y+height);
		glTexCoord2f(xTo, yTo);
		glVertex2f(x+width, y+height);
		glTexCoord2f(xTo, yFrom);
		glVertex2f(x+width, y);
	}
	
	public final static void drawQuadPart(final Texture texture, final float x, final float y, final float width, final float height, final float texXOrg, final float texYOrg, final float texCoWidth, final float texCoHeight) {
		final float xFrom = texXOrg/texture.getWidth();
		final float xTo = (texXOrg+texCoWidth)/texture.getWidth();
		final float yFrom = texYOrg/texture.getHeight();
		final float yTo = (texYOrg+texCoHeight)/texture.getHeight();
		glTexCoord2f(xFrom, yFrom);
		glVertex2f(x, y);
		glTexCoord2f(xFrom, yTo);
		glVertex2f(x, y+height);
		glTexCoord2f(xTo, yTo);
		glVertex2f(x+width, y+height);
		glTexCoord2f(xTo, yFrom);
		glVertex2f(x+width, y);
	}
	
	public final static void drawQuadPart(final Texture texture, final float x, final float y, final float width, final float height) {
		final float xFrom = 0;
		final float xTo = 1;
		final float yFrom = 0;
		final float yTo = 1;
		glTexCoord2f(xFrom, yFrom);
		glVertex2f(x, y);
		glTexCoord2f(xFrom, yTo);
		glVertex2f(x, y+height);
		glTexCoord2f(xTo, yTo);
		glVertex2f(x+width, y+height);
		glTexCoord2f(xTo, yFrom);
		glVertex2f(x+width, y);
	}
	
	public final static void drawQuad(final Texture texture, final float x, final float y, final float alpha) {
		if(texture != null) {
			glColor4f(1, 1, 1, alpha);
			texture.bind();
			glBegin(GL_QUADS);
			drawQuadPart(texture, x, y);
			glEnd();
			GL11.glBindTexture(GL_TEXTURE_2D, 0);
		}
	}
	
	public final static void drawQuad(final Texture texture, final float x, final float y) {
		if(texture != null) {
			drawQuad(texture, x, y, texture.getWidth()*Mideas.getDisplayXFactor(), texture.getHeight()*Mideas.getDisplayYFactor(), 0, 0, texture.getWidth(), texture.getHeight(), 1);
		}
	}
	
	public final static void drawQuadBlend(final Texture texture, final float x, final float y) {
		/*if(texture != null) {
			texture.bind();
			glBlendFuncSeparate(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_COLOR, GL_SRC_ALPHA, GL_DST_COLOR);
			glBlendEquationSeparate(GL_FUNC_ADD, GL_FUNC_ADD);
			OpenGL.glBegin(OpenGL.GL_QUADS);
			OpenGL.glColor4f(1, 1, 1, 1);
			drawQuadPart(texture, x, y, texture.getWidth()*Mideas.getDisplayXFactor(), texture.getHeight()*Mideas.getDisplayYFactor(), 0, 0, texture.getWidth(), texture.getHeight());
			OpenGL.glEnd();
			glBlendFuncSeparate(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA, GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		}*/
		drawQuadBlend(texture, x,  y, texture.getWidth()*Mideas.getDisplayXFactor(), texture.getHeight()*Mideas.getDisplayYFactor());
	}
	
	public final static void drawQuadBlend(final Texture texture, final float x, final float y, final float width, final float height) {
		if(texture != null) {
			texture.bind();
			glBlendFuncSeparate(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_COLOR, GL_SRC_ALPHA, GL_DST_COLOR);
			glBlendEquationSeparate(GL_FUNC_ADD, GL_FUNC_ADD);
			OpenGL.glBegin(OpenGL.GL_QUADS);
			OpenGL.glColor4f(1, 1, 1, 1);
			drawQuadPart(texture, x, y, width, height, 0, 0, texture.getWidth(), texture.getHeight());
			OpenGL.glEnd();
			glBlendFuncSeparate(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA, GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		}
	}
	
	public final static void drawQuadBlend(final Texture texture, final float x, final float y, final float width, final float height, Color Colors) {
		if(texture != null) {
			texture.bind();
			glBlendFuncSeparate(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_COLOR, GL_SRC_ALPHA, GL_DST_COLOR);
			glBlendEquationSeparate(GL_FUNC_ADD, GL_FUNC_ADD);
			OpenGL.glBegin(OpenGL.GL_QUADS);
			glColor4f(Colors.getRed(), Colors.getGreen(), Colors.getBlue(), Colors.getAlpha());
			drawQuadPart(texture, x, y, width, height, 0, 0, texture.getWidth(), texture.getHeight());
			OpenGL.glEnd();
			glBlendFuncSeparate(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA, GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		}
	}
	
	public final static void drawQuadBlend(final Texture texture, final float x, final float y, Color Colors) {
		if(texture != null) {
			texture.bind();
			glBlendFuncSeparate(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_COLOR, GL_SRC_ALPHA, GL_DST_COLOR);
			glBlendEquationSeparate(GL_FUNC_ADD, GL_FUNC_ADD);
			OpenGL.glBegin(OpenGL.GL_QUADS);
			glColor4f(Colors.getRed(), Colors.getGreen(), Colors.getBlue(), Colors.getAlpha());
			drawQuadPart(texture, x, y, texture.getWidth()*Mideas.getDisplayXFactor(), texture.getHeight()*Mideas.getDisplayYFactor(), 0, 0, texture.getWidth(), texture.getHeight());
			OpenGL.glEnd();
			glBlendFuncSeparate(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA, GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		}
	}
	
	public final static void drawQuadColor(final float x, final float y, final Texture texture, final Color Colors) {
		if(texture != null) {
			glBegin(GL_QUADS);
			glColor4f(Colors.getRed(), Colors.getGreen(), Colors.getBlue(), Colors.getAlpha());
			glTexCoord2f(0, 0);
			glVertex2f(x, y);
			glTexCoord2f(0, 1);
			glVertex2f(x, y+texture.getHeight());
			glTexCoord2f(1, 1);
			glVertex2f(x+texture.getWidth(), y+texture.getHeight());
			glTexCoord2f(1, 0);
			glVertex2f(x+texture.getWidth(), y);
			glEnd();
		}
	}
	
	public final static void drawCirclePart(final float x, final float y, final int rayon, final int nbSeg, final float angle, final float startAngle) {
		int i = nbSeg+1;
		while(--i > -1) {
			glVertex2d(rayon*Math.cos(angle*i/nbSeg+startAngle)+x, rayon*Math.sin(angle*i/nbSeg+startAngle)+y);
		}
	}
	
	public final static void drawCirclePart(final float x, final float y, final int rayon, final int nbSeg) {
		drawCirclePart(x, y, rayon, nbSeg, 2*(float)Math.PI, 0);
	}
	
	public final static void drawCirclePart(final float x, final float y, final int rayon) {
		drawCirclePart(x, y, rayon, (int)(Math.PI*rayon), 2*(float)Math.PI, 0);
	}
	
	public final static void drawCircle(final float x, final float y, final int rayon, final Color color, final int nbSeg, final float lineWeight, final float angle, final float startAngle) {
		glLineWidth(lineWeight);
		glDisable(GL_TEXTURE_2D);
		glEnable(GL_TRIANGLE_FAN);
		glColor4f(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
		glBegin(GL_LINE_STRIP);
		drawCirclePart(x, y, rayon, nbSeg, angle, startAngle);
		glEnd();
		glDisable(GL_TRIANGLE_FAN);
		glEnable(GL_TEXTURE_2D);
	}
	
	public final static void drawCircle(final float x, final float y, final int rayon, final Color Colors, final int nbSeg, final float lineWeight) {
		Draw.drawCircle(x, y, rayon, Colors, nbSeg, lineWeight, 2*(float)Math.PI, 0);
	}
	
	public final static void drawCircle(final float x, final float y, final int rayon, final Color Colors, final int nbSeg) {
		Draw.drawCircle(x, y, rayon, Colors, nbSeg, 1, 2*(float)Math.PI, 0);
	}
	
	public final static void drawCircle(final float x, final float y, final int rayon, final Color Colors) {
		Draw.drawCircle(x, y, rayon, Colors, (int)(Math.PI*rayon), 1, 2*(float)Math.PI, 0);
	}
	
	public final static void drawLine(final float x1, final float y1, final float x2, final float y2, final Color color1, final Color color2, final float lineWeight) {
		glLineWidth(lineWeight);
		glDisable(GL_TEXTURE_2D);
		if(x1 != x2 && y1 != y2) {
			glEnable(GL_LINE_SMOOTH);
		}
		glBegin(GL_LINES);
		glColor4f(color1.getRed(), color1.getGreen(), color1.getBlue(), color1.getAlpha());
		glVertex2f(x1, y1);
		glColor4f(color2.getRed(), color2.getGreen(), color2.getBlue(), color2.getAlpha());
		glVertex2f(x2, y2);
		glEnd();
		glDisable(GL_LINE_SMOOTH);
		glEnable(GL_TEXTURE_2D);
	}
	
	public final static void drawLine(final float x1, final float y1, final float x2, final float y2, final Color color1, final Color color2) {
		drawLine(x1, y1, x2, y2, color1, color2, 1);
	}
	
	public final static void drawLine(final float x1, final float y1, final float x2, final float y2, final Color Colors, final float lineWeight) {
		drawLine(x1, y1, x2, y2, Colors, Colors, lineWeight);
	}
	
	public final static void drawLine(final float x1, final float y1, final float x2, final float y2, final Color Colors) {
		drawLine(x1, y1, x2, y2, Colors, Colors, 1);
	}
	
	public final static void drawPoint(final float x, final float y, final Color Colors, final float size) {
		glPointSize(size);
		glDisable(GL_TEXTURE_2D);
		glColor4f(Colors.getRed(), Colors.getGreen(), Colors.getBlue(), Colors.getAlpha());
		glBegin(GL_POINTS);
		glVertex2f(x-1, y+1);
		glEnd();
		glEnable(GL_TEXTURE_2D);
	}
	
	public final static void drawPoint(final float x, final float y, final Color Colors) {
		drawPoint(x, y, Colors, 1);
	}
	public final static void drawQuad(final Texture texture, final float x, final float y, final float width, final float height, final float texXOrg, final float texYOrg, final float texCoWidth, final float texCoHeight, final Color Colors) {
		if(texture != null) {
			texture.bind();
			OpenGL.glBegin(OpenGL.GL_QUADS);
			OpenGL.glColor4f(Colors.getRed(), Colors.getGreen(), Colors.getBlue(), Colors.getAlpha());
			drawQuadPart(texture, x, y, width, height, texXOrg, texYOrg, texCoWidth, texCoHeight);
			OpenGL.glEnd();
		}
	}
	
	public final static void drawQuad(final Texture texture, final float x, final float y, final float width, final float height, final float texXOrg, final float texYOrg, final float texCoWidth, final float texCoHeight, final float alpha) {
		if(texture != null) {
			texture.bind();
			OpenGL.glBegin(OpenGL.GL_QUADS);
			OpenGL.glColor4f(1, 1, 1, alpha);
			drawQuadPart(texture, x, y, width, height, texXOrg, texYOrg, texCoWidth, texCoHeight);
			OpenGL.glEnd();
		}
	}
	
	public final static void drawQuad(final Texture texture, final float x, final float y, final float width, final float height, final float texXOrg, final float texYOrg, final float texCoWidth, final float texCoHeight) {
		drawQuad(texture, x, y, width, height, texXOrg, texYOrg, texCoWidth, texCoHeight, 1);
	}
	
	public final static void drawQuad(final Texture texture, final float x, final float y, final float width, final float height, final Color Colors) {
		drawQuad(texture, x, y, width, height, 0, 0, texture.getWidth(), texture.getHeight(), Colors);
	}
	
	public final static void drawQuad(final Texture texture, final float x, final float y, final float width, final float height, final float alpha) {
		drawQuad(texture, x, y, width, height, 0, 0, texture.getWidth(), texture.getHeight(), alpha);
	}
	
	public final static void drawQuad(final Texture texture, final float x, final float y, final float width, final float height) {
		drawQuad(texture, x, y, width, height, 0, 0, texture.getWidth(), texture.getHeight(), 1);
	}
	
	public final static void drawQuadCentered(final Texture texture, final float x, final float y, final float width, final float height) {
		drawQuad(texture, x, y, width*Mideas.getDisplayXFactor(), height*Mideas.getDisplayYFactor(), 0, 0, texture.getWidth(), texture.getHeight(), 1);
	}
	
	public final static void drawQuadCentered(final Texture texture, final float x, final float y) {
		drawQuad(texture, x, y, texture.getWidth()*Mideas.getDisplayXFactor(), texture.getHeight()*Mideas.getDisplayYFactor(), 0, 0, texture.getWidth(), texture.getHeight(), 1);
	}
	
	public final static void drawQuadBG(final Texture texture) {
		drawQuad(texture, 0, 0, texture.getWidth()*Mideas.getDisplayXFactor(), texture.getHeight()*Mideas.getDisplayYFactor(), 0, 0, texture.getWidth(), texture.getHeight(), 1);
	}
	
	public final static void drawQuad(final Texture texture, final float x, final float y, final Color Colors) {
		drawQuad(texture, x, y, texture.getWidth(), texture.getHeight(), 0, 0, texture.getWidth(), texture.getHeight(), Colors);
	}
}
