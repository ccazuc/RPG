package com.mideas.rpg.v2;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.DataBufferInt;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import org.newdawn.slick.Color;

public final class TTF {
	
	private FontMetrics fontMetrics;
	private Char[] charArray;
	private Font font;
	private boolean antiAlias;
	private int fontTextureID;
	private int textureHeight;
	private int textureWidth;
	private int lineHeight;
	private int correctL;
	
	private final class Char {
		
		public int textureX;
		public int textureY;
		public int width;
		
		public Char() {
			
		}
		
	}
	
	public TTF(final Font font, final boolean antiAlias) {
		this.charArray = new Char[500];
		this.antiAlias = antiAlias;
		this.correctL = 2;
		this.font = font;
		loadFontMetrics();
		loadTextureSize();
		createSet();
	}
	
	public TTF(final Font font) {
		this(font, true);
	}
	
	private final void loadTextureSize() {
		this.textureWidth = 1920;
		this.textureHeight = 512;
		this.lineHeight = this.fontMetrics.getMaxAscent()+this.fontMetrics.getMaxDescent()+this.fontMetrics.getLeading();
		int i = -1;
		int positionX = 0;
		int positionY = 0;
		while(++i < this.charArray.length) {
			if(this.font.canDisplay(i)) {
				this.charArray[i] = new Char();
				this.charArray[i].width = this.fontMetrics.charWidth(i);
				if(positionX+this.charArray[i].width+this.correctL >= this.textureWidth) {
					positionX = 0;
					positionY+= this.lineHeight;
				}
				positionX+= this.charArray[i].width+this.correctL;
			}
		}
		this.textureHeight = positionY+this.lineHeight;
	}
	
	private final void loadFontMetrics() {
		final Graphics2D graphics = (Graphics2D)new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB).getGraphics();
		//graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		graphics.setFont(this.font);
		this.fontMetrics = graphics.getFontMetrics();
	}
	
	private final void createSet() {  
		try {
			final BufferedImage imgTemp = new BufferedImage(this.textureWidth, this.textureHeight, BufferedImage.TYPE_INT_ARGB);
			final Graphics2D graphics = (Graphics2D)imgTemp.getGraphics();
		   	//graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		    graphics.setFont(this.font);
		    graphics.setColor(java.awt.Color.WHITE);
			int positionX = 0;
			int positionY = 0;
			int i = -1;
			while(++i < this.charArray.length) {
				if(this.font.canDisplay((char)i)) {
					final Char charObject = this.charArray[i];
					if(positionX+charObject.width+this.correctL >= this.textureWidth) {
						positionX = 0;
						positionY+= this.lineHeight;
					}
					charObject.textureX = positionX;
					charObject.textureY = positionY;
					graphics.drawString(String.valueOf((char)i), positionX, positionY+this.fontMetrics.getAscent());
					positionX+= charObject.width+this.correctL;
				}
			}
			this.fontTextureID = loadImage(imgTemp);
		}
		catch(final Exception e) {
			e.printStackTrace();
		}
	}
	
	private final void drawQuad(final float x, final float y, final float width, final float height, final float texX, final float texY, final float texWidth, final float texHeight) {
		final float textureSrcX = texX/this.textureWidth;
		final float textureSrcY = texY/this.textureHeight;
		final float renderWidth = texWidth/this.textureWidth;
		final float renderHeight = texHeight/this.textureHeight;
		OpenGL.glTexCoord2f(textureSrcX, textureSrcY);
		OpenGL.glVertex2f(x, y);
		OpenGL.glTexCoord2f(textureSrcX, textureSrcY+renderHeight);
		OpenGL.glVertex2f(x, y+height);
		OpenGL.glTexCoord2f(textureSrcX+renderWidth, textureSrcY+renderHeight);
		OpenGL.glVertex2f(x+width, y+height);
		OpenGL.glTexCoord2f(textureSrcX+renderWidth, textureSrcY);
		OpenGL.glVertex2f(x+width, y);
	}
	
	public final int getWidth(final String text) {
		char currentChar;
		int currentWidth = 0;
		int i = text.length();
		int maxWidth = 0;
		while(--i > -1) {
			if((currentChar = text.charAt(i)) != '\n') {
				currentWidth+= getWidth(currentChar);
			}
			else {
				if(currentWidth > maxWidth) {
					maxWidth = currentWidth;
					currentWidth = 0;
				}
			}
		}
		if(currentWidth > maxWidth) {
			maxWidth = currentWidth;
			currentWidth = 0;
		}
		return maxWidth;
	}
	
	public final int getWidth(final char character) {
		return character > 0 && this.charArray[character] != null ? this.charArray[character].width : 0;
	}
	
	public final int getLineHeight() {
		return this.lineHeight;
	}
	
	public final int getHeight(final String text) {
		return (1+text.length()-text.replace("\n", "").length())*this.lineHeight;
	}
	
	public final void drawChar(final float x, final float y, final char character, final Color color) {
		drawChar(x, y, character, color, 1, 1, 1);
	}
	
	public final void drawChar(final float x, final float y, final char character, final Color color, final float scaleX, final float scaleY) {
		drawChar(x, y, character, color, scaleX, scaleY, 1);
	}
	
	public final void drawChar(final float x, final float y, final char character, final Color color, final float scaleX, final float scaleY, final float opacity) {
		bind();
		OpenGL.glBegin(OpenGL.GL_QUADS);
		OpenGL.glColor4f(color.getRed()/255f, color.getGreen()/255f, color.getBlue()/255f, color.getAlpha()*opacity/255f);
		drawCharPart(x, y, character, scaleX, scaleY);
		OpenGL.glEnd();
	}
	
	public final void drawCharPart(final float x, final float y, final char character) {
		drawCharPart(x, y, character, 1, 1);
	}
	
	public final void drawCharPart(final float x, final float y, final char character, final float scaleX, final float scaleY) {
		Char charObject;
		if(character > 0 && (charObject = this.charArray[character]) != null && character != '\n') {
			drawQuad(x, y, charObject.width*scaleX, this.lineHeight*scaleY, charObject.textureX, charObject.textureY, charObject.width, this.lineHeight);
		}
	}
	
	public final void drawStringPart(final float x, final float y, final String text, final Color color) {
		drawStringPart(x, y, text, color, 1, 1, 1);
	}
	
	public final void drawStringPart(final float x, final float y, final String text, final Color color, final float opacity) {
		drawStringPart(x, y, text, color, 1, 1, opacity);
	}
	
	public final void drawStringPart(final float x, final float y, final String text, final Color color, final float scaleX, final float scaleY) {
		drawStringPart(x, y, text, color, scaleX, scaleY, 1);
	}
	
	public final void drawStringPart(final float x, final float y, final String text, final Color color, final float scaleX, final float scaleY, final float opacity) {
		Char charObject;
		float totalHeight = 0;
		float totalWidth = 0;
		char currentChar;
		int i = -1;
		OpenGL.glColor4f(color.getRed()/255f, color.getGreen()/255f, color.getBlue()/255f, color.getAlpha()*opacity/255f);
		while(++i < text.length()) {
			currentChar = text.charAt(i);
			if(currentChar == '\n') {
				totalHeight+= this.lineHeight*scaleY;
				totalWidth = 0;
			}
			else if(currentChar > 0 && (charObject = this.charArray[currentChar]) != null) {
				drawCharPart(totalWidth+x, totalHeight+y, currentChar, scaleX, scaleY);
				totalWidth+= (charObject.width)*scaleX;
			}
		}
	}
	
	public final void drawString(final float x, final float y, final String text, final Color color) {
		drawString(x, y, text, color, 1, 1, 1);
	}
	
	public final void drawString(final float x, final float y, final String text, final Color color, final float opacity) {
		drawString(x, y, text, color, 1, 1, opacity);
	}
	
	public final void drawString(final float x, final float y, final String text, final Color color, final float scaleX, final float scaleY) {
		drawString(x, y, text, color, scaleX, scaleY, 1);
	}
	
	public final void drawString(final float x, final float y, final String text, final Color color, final float scaleX, final float scaleY, final float opacity) {
		bind();
		OpenGL.glBegin(OpenGL.GL_QUADS);
		drawStringPart(x, y, text, color, scaleX, scaleY, opacity);
		OpenGL.glEnd();
	}
	
	public final void drawStringShadow(final float x, final int y, final String text, final Color color, final Color shadowColor, final int shadowSize) {
		drawStringShadow(x, y, text, color, shadowColor, shadowSize, 0, 0, 1, 1, 1);
	}
	
	public final void drawStringShadow(final float x, final float y, final String text, final Color color, final Color shadowColor, final int shadowSize, final float scaleX, final float scaleY) {
		drawStringShadow(x, y, text, color, shadowColor, shadowSize, 0, 0, 1, scaleX, scaleY);
	}
	
	public final void drawStringShadow(final float x, final float y, final String text, final Color color, final Color shadowColor, final int shadowSize, final float opacity) {
		drawStringShadow(x, y, text, color, shadowColor, shadowSize, 0, 0, opacity, 1, 1);
	}
	
	public final void drawStringShadow(final float x, final float y, final String text, final Color color, final Color shadowColor, final int shadowSize, final float opacity, final float scaleX, final float scaleY) {
		drawStringShadow(x, y, text, color, shadowColor, shadowSize, 0, 0, opacity, scaleX, scaleY);
	}
	
	public final void drawStringShadow(final float x, final float y, final String text, final Color color, final Color shadowColor, final int shadowSize, final int shadowX, final int shadowY) {
		drawStringShadow(x, y, text, color, shadowColor, shadowSize, shadowX, shadowY, 1, 1, 1);
	}
	
	public final void drawStringShadow(final float x, final float y, final String text, final Color color, final Color shadowColor, final int shadowSize, final int shadowX, final int shadowY, final float scaleX, final float scaleY) {
		drawStringShadow(x, y, text, color, shadowColor, shadowSize, shadowX, shadowY, 1, scaleX, scaleY);
	}
	
	public final void drawStringShadow(final float x, final float y, final String text, final Color color, final Color shadowColor, final int shadowSize, final int shadowX, final int shadowY, final float opacity, final float scaleX, final float scaleY) {
		bind();
		OpenGL.glBegin(OpenGL.GL_QUADS);
		drawStringShadowPart(x, y, text, color, shadowColor, shadowSize, shadowX, shadowY, opacity, scaleX, scaleY);
		OpenGL.glEnd();
	}
	
	public final void drawStringShadowPart(final float x, final float y, final String text, final Color color, final Color shadowColor, final int shadowSize) {
		drawStringShadowPart(x, y, text, color, shadowColor, shadowSize, 0, 0, 1, 1, 1);
	}
	
	public final void drawStringShadowPart(final float x, final float y, final String text, final Color color, final Color shadowColor, final int shadowSize, final float scaleX, final float scaleY) {
		drawStringShadowPart(x, y, text, color, shadowColor, shadowSize, 0, 0, 1, scaleX, scaleY);
	}
	
	public final void drawStringShadowPart(final float x, final float y, final String text, final Color color, final Color shadowColor, final int shadowSize, final float opacity) {
		drawStringShadowPart(x, y, text, color, shadowColor, shadowSize, 0, 0, opacity, 1, 1);
	}
	
	public final void drawStringShadowPart(final float x, final float y, final String text, final Color color, final Color shadowColor, final int shadowSize, final float opacity, final float scaleX, final float scaleY) {
		drawStringShadowPart(x, y, text, color, shadowColor, shadowSize, 0, 0, opacity, scaleX, scaleY);
	}
	
	public final void drawStringShadowPart(final float x, final float y, final String text, final Color color, final Color shadowColor, final int shadowSize, final int shadowX, final int shadowY) {
		drawStringShadowPart(x, y, text, color, shadowColor, shadowSize, shadowX, shadowY, 1, 1, 1);
	}
	
	public final void drawStringShadowPart(final float x, final float y, final String text, final Color color, final Color shadowColor, final int shadowSize, final int shadowX, final int shadowY, final float scaleX, final float scaleY) {
		drawStringShadowPart(x, y, text, color, shadowColor, shadowSize, shadowX, shadowY, 1, scaleX, scaleY);
	}
	
	public final void drawStringShadowPart(final float x, final float y, final String text, final Color color, final Color shadowColor, final int shadowSize, final int shadowX, final int shadowY, final float opacity, final float scaleX, final float scaleY) {
		float i = x-shadowSize+shadowX-1;
		while(++i <= x+shadowSize+shadowX) {
			float ii = y-shadowSize+shadowY-1;
			while(++ii <= y+shadowSize+shadowY) {
				if(Math.abs(i-x-shadowX) != Math.abs(ii-y-shadowY)) {
					drawStringPart(i, ii, text, shadowColor, scaleX, scaleY, opacity);
				}
			}
		}
		drawStringPart(x, y, text, color, scaleX, scaleY, opacity);
	}
	
	private static final int loadImage(final BufferedImage bufferedImage) {
		try {
			final int width = bufferedImage.getWidth();
			final int height = bufferedImage.getHeight();
			final int bpp = bufferedImage.getColorModel().getPixelSize();
			ByteBuffer byteBuffer;
			DataBuffer db = bufferedImage.getData().getDataBuffer();
			if(db instanceof DataBufferInt) {
				final int pixels[] = ((DataBufferInt)(bufferedImage.getData().getDataBuffer())).getData();
				byteBuffer = ByteBuffer.allocateDirect(width*height*(bpp/8)).order(ByteOrder.nativeOrder());
				int i = -1;
				while(++i < pixels.length) {
					final int pixel = pixels[i];
					byteBuffer.put((byte)(pixel>>16));
					byteBuffer.put((byte)(pixel>>8));
					byteBuffer.put((byte) pixel);
					if(pixel>>24 == 1) {
						byteBuffer.put((byte)0);
					}
					else {
						byteBuffer.put((byte)(pixel>>24));
					}
				}
			}
			else {
				byteBuffer = ByteBuffer.allocateDirect(width*height*(bpp/8)).order(ByteOrder.nativeOrder()).put(((DataBufferByte)db).getData());
			}
			byteBuffer.flip();
			final int textureId = OpenGL.glGenTextures();
			OpenGL.glBindTexture(OpenGL.GL_TEXTURE_2D, textureId);
			OpenGL.glTexParameteri(OpenGL.GL_TEXTURE_2D, OpenGL.GL_TEXTURE_MAG_FILTER, OpenGL.GL_LINEAR);
			OpenGL.glTexParameteri(OpenGL.GL_TEXTURE_2D, OpenGL.GL_TEXTURE_MIN_FILTER, OpenGL.GL_LINEAR);
			OpenGL.glTexImage2D(OpenGL.GL_TEXTURE_2D, 0, OpenGL.GL_RGBA8, width, height, 0, OpenGL.GL_RGBA, OpenGL.GL_UNSIGNED_BYTE, byteBuffer);
			return textureId;
		}
		catch(final Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public final void bind() {
		OpenGL.glBindTexture(OpenGL.GL_TEXTURE_2D, this.fontTextureID);
	}
	
	public float getSize() {
		return this.font.getSize();
	}
	
}