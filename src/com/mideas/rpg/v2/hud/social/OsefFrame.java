package com.mideas.rpg.v2.hud.social;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import com.mideas.rpg.v2.FontManager;
import com.mideas.rpg.v2.Sprites;
import com.mideas.rpg.v2.utils.Color;
import com.mideas.rpg.v2.utils.Draw;

public class OsefFrame {

	private static boolean running;
	private static long started;
	private final static int SIZE = 1;
	private final static String text = "MLG";
	private final static double DURATION = 3000000000.;
	
	public static void draw() {
		if (running) {
			long timer = System.nanoTime();
			Color color = new Color((float)(Math.random()/1), (float)(Math.random()/1), (float)(Math.random()/1));
			double angle = (timer-started)/DURATION;
			angle *= angle;
			angle *= angle;
			angle *= 360;
			double opacity = 13-(timer-started)/DURATION;
			int width = FontManager.get("FRIZQT", 20).getWidth(text);
			int height = FontManager.get("FRIZQT", 20).getLineHeight();
			GL11.glPushMatrix();
			GL11.glTranslated(Display.getWidth()/2, Display.getHeight()/2, 0);
			GL11.glRotated(angle, 0, 0, 1);
			float scale = (float)((timer-started)/DURATION*SIZE);
			double moveFactor = scale-(timer-started)/5000000000.*scale*2;
			String textRender = genText(text);
			FontManager.get("FRIZQT", 20).drawString((float)(-width/2*moveFactor), (float)(-height/2*moveFactor), textRender, color, scale, scale, opacity);
			//int randomSize = (int)(Math.random()*30);
			int randomSize = (int)(Math.random()*70);
			Draw.drawQuad(Sprites.mlg_frog, (float)(-width/2*moveFactor)-randomSize-10, (float)(-height/2*moveFactor)-randomSize/2, randomSize, randomSize, color);
			//randomSize = (int)(Math.random()*30);
			Draw.drawQuad(Sprites.mlg_frog, (float)(-width*scale/2*moveFactor)+randomSize+FontManager.get("FRIZQT", 20).getWidth(textRender)*scale+10, (float)(-height/2*moveFactor)-randomSize/2, randomSize, randomSize, color);
			GL11.glPopMatrix();
			if (timer-started > 50000000000l) {
				running = false;
			}
		}
	}
	
	private static String genText(String text) {
		return randomInt(3)+text+randomInt(3);
	}
	
	private static String randomInt(int length) {
		int i = 0;
		StringBuilder builder = new StringBuilder();
		while(i < length) {
			builder.append((int)(Math.random()*9));
			i++;
		}
		return builder.toString();
	}
	
	public static void run() {
		running = true;
		started = System.nanoTime();
	}
} 
