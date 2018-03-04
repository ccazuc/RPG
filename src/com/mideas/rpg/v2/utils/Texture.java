package com.mideas.rpg.v2.utils;

import static org.lwjgl.opengl.GL11.GL_DST_COLOR;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_COLOR;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL14.GL_FUNC_ADD;
import static org.lwjgl.opengl.GL14.glBlendFuncSeparate;
import static org.lwjgl.opengl.GL20.glBlendEquationSeparate;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.HashSet;

import javax.imageio.ImageIO;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.utils.render.Draw;
import com.mideas.rpg.v2.utils.render.OpenGL;
import com.mideas.rpg.v2.utils.render.Sprites;

public final class Texture {

	private final static HashSet<Texture> textureSet = new HashSet<Texture>();
	private static boolean textureLoaded = false;
	private int textureID;
	private int height;
	private int width;
	private File file;

	public Texture(String string, boolean directLoad)
	{
		this(new File(string), directLoad);
	}

	public Texture(String string)
	{
		this(new File(string));
	}

	public Texture(final File file)
	{
		this.file = file;
		textureSet.add(this);
		/*try {
			BufferedImage image = ImageIO.read(file);
			final int width = image.getWidth();
			final int height = image.getHeight();
			final int[] pixels = new int[width*height];
			final ByteBuffer buffer = ByteBuffer.allocateDirect(width*height*4).order(ByteOrder.nativeOrder());
			image.getRGB(0, 0, width, height, pixels, 0, width);
			int i = -1;
			while(++i < pixels.length) {
				buffer.put((byte)(pixels[i]>>16));
				buffer.put((byte)(pixels[i]>>8));
				buffer.put((byte) pixels[i]);
				buffer.put((byte)(pixels[i]>>24));
			}
			buffer.position(0);
			this.textureID = OpenGL.glGenTextures();
			OpenGL.glBindTexture(OpenGL.GL_TEXTURE_2D, this.textureID);
			OpenGL.glTexParameteri(OpenGL.GL_TEXTURE_2D, OpenGL.GL_TEXTURE_MIN_FILTER, OpenGL.GL_LINEAR);
			OpenGL.glTexParameteri(OpenGL.GL_TEXTURE_2D, OpenGL.GL_TEXTURE_MAG_FILTER, OpenGL.GL_LINEAR);
			//OpenGL.glTexParameteri(OpenGL.GL_TEXTURE_2D, OpenGL.GL_TEXTURE_MIN_FILTER, OpenGL.GL_NEAREST);
			//OpenGL.glTexParameteri(OpenGL.GL_TEXTURE_2D, OpenGL.GL_TEXTURE_MAG_FILTER, OpenGL.GL_NEAREST);
			OpenGL.glTexImage2D(OpenGL.GL_TEXTURE_2D, 0, OpenGL.GL_RGBA8, width, height, 0, OpenGL.GL_RGBA, OpenGL.GL_UNSIGNED_BYTE, buffer);
			this.height = height;
			this.width = width;
		}
		catch (IOException e) {
			e.printStackTrace();
			this.height = 0;
			this.width = 0;
			this.textureID = 0;
		}*/
	}

	public Texture(final File file, boolean directLoad) {
		if (directLoad)
		{
			try {
				BufferedImage image = ImageIO.read(file);
				final int width = image.getWidth();
				final int height = image.getHeight();
				final int[] pixels = new int[width*height];
				final ByteBuffer buffer = ByteBuffer.allocateDirect(width*height*4).order(ByteOrder.nativeOrder());
				image.getRGB(0, 0, width, height, pixels, 0, width);
				int i = -1;
				while(++i < pixels.length) {
					buffer.put((byte)(pixels[i]>>16));
					buffer.put((byte)(pixels[i]>>8));
					buffer.put((byte) pixels[i]);
					buffer.put((byte)(pixels[i]>>24));
				}
				buffer.position(0);
				this.textureID = OpenGL.glGenTextures();
				OpenGL.glBindTexture(OpenGL.GL_TEXTURE_2D, this.textureID);
				OpenGL.glTexParameteri(OpenGL.GL_TEXTURE_2D, OpenGL.GL_TEXTURE_MIN_FILTER, OpenGL.GL_LINEAR);
				OpenGL.glTexParameteri(OpenGL.GL_TEXTURE_2D, OpenGL.GL_TEXTURE_MAG_FILTER, OpenGL.GL_LINEAR);
				//OpenGL.glTexParameteri(OpenGL.GL_TEXTURE_2D, OpenGL.GL_TEXTURE_MIN_FILTER, OpenGL.GL_NEAREST);
				//OpenGL.glTexParameteri(OpenGL.GL_TEXTURE_2D, OpenGL.GL_TEXTURE_MAG_FILTER, OpenGL.GL_NEAREST);
				OpenGL.glTexImage2D(OpenGL.GL_TEXTURE_2D, 0, OpenGL.GL_RGBA8, width, height, 0, OpenGL.GL_RGBA, OpenGL.GL_UNSIGNED_BYTE, buffer);
				this.height = height;
				this.width = width;
			}
			catch (IOException e) {
				e.printStackTrace();
				this.height = 0;
				this.width = 0;
				this.textureID = 0;
			}
		}
		else
		{
			this.file = file;
			textureSet.add(this);
		}
	}
	
	private void loadTexture()
	{
		try
		{
			BufferedImage image = ImageIO.read(this.file);
			final int width = image.getWidth();
			final int height = image.getHeight();
			final int[] pixels = new int[width*height];
			final ByteBuffer buffer = ByteBuffer.allocateDirect(width*height*4).order(ByteOrder.nativeOrder());
			image.getRGB(0, 0, width, height, pixels, 0, width);
			int i = -1;
			while(++i < pixels.length) {
				buffer.put((byte)(pixels[i]>>16));
				buffer.put((byte)(pixels[i]>>8));
				buffer.put((byte) pixels[i]);
				buffer.put((byte)(pixels[i]>>24));
			}
			buffer.position(0);
			this.textureID = OpenGL.glGenTextures();
			OpenGL.glBindTexture(OpenGL.GL_TEXTURE_2D, this.textureID);
			OpenGL.glTexParameteri(OpenGL.GL_TEXTURE_2D, OpenGL.GL_TEXTURE_MIN_FILTER, OpenGL.GL_LINEAR);
			OpenGL.glTexParameteri(OpenGL.GL_TEXTURE_2D, OpenGL.GL_TEXTURE_MAG_FILTER, OpenGL.GL_LINEAR);
			//OpenGL.glTexParameteri(OpenGL.GL_TEXTURE_2D, OpenGL.GL_TEXTURE_MIN_FILTER, OpenGL.GL_NEAREST);
			//OpenGL.glTexParameteri(OpenGL.GL_TEXTURE_2D, OpenGL.GL_TEXTURE_MAG_FILTER, OpenGL.GL_NEAREST);
			OpenGL.glTexImage2D(OpenGL.GL_TEXTURE_2D, 0, OpenGL.GL_RGBA8, width, height, 0, OpenGL.GL_RGBA, OpenGL.GL_UNSIGNED_BYTE, buffer);
			this.height = height;
			this.width = width;
		}
		catch (IOException e)
		{
			e.printStackTrace();
			this.height = 0;
			this.width = 0;
			this.textureID = 0;
		}
	}
	
	public static void loadAllTexture()
	{
		if (!textureLoaded)
		{
			Sprites.initBG();
			Sprites.sprite();
			Sprites.sprite2();
			Sprites.sprite8();
			Sprites.sprite9();
			Sprites.sprite10();
			textureLoaded = true;
		}
		Mideas.context2D();
		Display.setVSyncEnabled(false);
		Display.sync(1000);
		int index = 0;
		Mideas.updateDisplayFactor();
		float barWidth = 700 * Mideas.getDisplayXFactor();
		for (Texture texture : textureSet)
		{
			if(Display.wasResized())
			{
				Mideas.context2D();
				Mideas.updateDisplayFactor();
				barWidth = 700 * Mideas.getDisplayXFactor();
			}
			texture.loadTexture();
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
			Draw.drawQuadBG(Sprites.loading_screen);
			Draw.drawQuad(Sprites.loading_screen_bar_progress, Display.getWidth() / 2 - barWidth / 2 + 25 * Mideas.getDisplayXFactor(), Display.getHeight() / 2 + 350 * Mideas.getDisplayYFactor() + 7, (int)(barWidth * index / textureSet.size()) - 55 * Mideas.getDisplayXFactor(), 20);
			Draw.drawQuad(Sprites.loading_screen_bar, Display.getWidth() / 2 - barWidth / 2, Display.getHeight() / 2 + 350 * Mideas.getDisplayYFactor(), barWidth, 40 * Mideas.getDisplayYFactor());
			Display.update();
			Display.sync(1000);
			++index;
		}
		Display.setVSyncEnabled(true);
	}

	/*public Texture(final File file) throws IOException {
		final GZIPInputStream stream = new GZIPInputStream(new FileInputStream(file));
		final byte[] dimensions = new byte[8];
		int i = 0;
		while((i+= stream.read(dimensions, i, 8-i)) < 8) {
			//Wait for the dimensions to load
		}
		final int width  = ((Byte.MAX_VALUE-dimensions[0])<<24)+((Byte.MAX_VALUE-dimensions[1])<<16)+((Byte.MAX_VALUE-dimensions[2])<<8)+(Byte.MAX_VALUE-dimensions[3]);
		final int height = ((Byte.MAX_VALUE-dimensions[4])<<24)+((Byte.MAX_VALUE-dimensions[5])<<16)+((Byte.MAX_VALUE-dimensions[6])<<8)+(Byte.MAX_VALUE-dimensions[7]);
		final byte[] pixels = new byte[width*height*4];
		int total = 0;
		i = 0;
		final ByteBuffer buffer = ByteBuffer.allocateDirect(width*height*4).order(ByteOrder.nativeOrder());
		while((total+= stream.read(pixels, total, pixels.length-total)) < pixels.length || i < total) {
			while(i < total) {
				buffer.put((pixels[i++]));
			}
		}
		stream.close();
		buffer.position(0);
		this.textureID = OpenGL.glGenTextures();
		OpenGL.glBindTexture(OpenGL.GL_TEXTURE_2D, this.textureID);
		OpenGL.glTexParameteri(OpenGL.GL_TEXTURE_2D, OpenGL.GL_TEXTURE_MIN_FILTER, OpenGL.GL_LINEAR);
		OpenGL.glTexParameteri(OpenGL.GL_TEXTURE_2D, OpenGL.GL_TEXTURE_MAG_FILTER, OpenGL.GL_LINEAR);
		OpenGL.glTexImage2D(OpenGL.GL_TEXTURE_2D, 0, OpenGL.GL_RGBA8, width, height, 0, OpenGL.GL_RGBA, OpenGL.GL_UNSIGNED_BYTE, buffer);
		this.height = height;
		this.width = width;
	}*/

	public final void drawBegin() {
		bind();
		OpenGL.glColor4f(1, 1, 1, 1);
		OpenGL.glBegin(OpenGL.GL_QUADS);
	}
	
	@SuppressWarnings("static-method")
	public final void drawEnd() {
		OpenGL.glEnd();
	}
	
	public final void drawBlendBegin() {
		glBlendFuncSeparate(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_COLOR, GL_SRC_ALPHA, GL_DST_COLOR);
		glBlendEquationSeparate(GL_FUNC_ADD, GL_FUNC_ADD);
		drawBegin();
	}
	
	public final void drawBlendEnd() {
		drawEnd();
		glBlendFuncSeparate(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA, GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
	}
	
	public final static void drawColorQuadBegin() {
		glDisable(GL_TEXTURE_2D);
		glBegin(GL_QUADS);
	}
	
	public final static void drawColorQuadEnd() {
		glEnd();
		glEnable(GL_TEXTURE_2D);
	}

	public final void bind() {
		OpenGL.glBindTexture(OpenGL.GL_TEXTURE_2D, this.textureID);
	}

	public final int getImageWidth() {
		return this.width;
	}

	public final int getImageHeight() {
		return this.height;
	}

	public final int getWidth() {
		return this.width;
	}

	public final int getHeight() {
		return this.height;
	}

	public final int getTextureID() {
		return this.textureID;
	}

}
