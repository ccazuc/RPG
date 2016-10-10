package com.mideas.rpg.v2.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import javax.imageio.ImageIO;

import com.mideas.rpg.v2.OpenGL;

public final class Texture {

	private int textureID;
	private int height;
	private int width;

	public Texture(final File file) {
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

	public Texture(String string) {
		this(new File(string));
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
