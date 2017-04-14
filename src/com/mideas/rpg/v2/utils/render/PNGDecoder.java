package com.mideas.rpg.v2.utils.render;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import javax.imageio.ImageIO;
public class PNGDecoder {
 
	public static ByteBuffer decode(final File file) throws IOException {
		final BufferedImage image = ImageIO.read(file);
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
		return buffer;
	}
 
}