package com.mideas.rpg.v2.hud;

import org.lwjgl.opengl.Display;

import com.mideas.rpg.v2.FontManager;
import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.TTF;
import com.mideas.rpg.v2.utils.Color;
import com.mideas.rpg.v2.utils.Draw;

public class AuraFrame {

	private static float X_BUFF = Display.getWidth()-100*Mideas.getDisplayXFactor();
	private static float Y_BUFF = 15*Mideas.getDisplayYFactor();
	private static float X_SIZE;
	private static float Y_SIZE;
	private final static String[] value = new String[61];
	private static boolean init;
	private final static TTF FONT = FontManager.get("FRIZQT", 13);
	
	public static void draw() {
		if(!init) {
			int j = 0;
			while(j < value.length) {
				value[j] = String.valueOf(j);
				j++;
			}
			init = true;
		}
		int i = 0;
		while(i < Mideas.joueur1().getBuffList().size()) {
			if(Mideas.joueur1().getBuffList().get(i).getAura().isVisible()) {
				long remaining = (Mideas.joueur1().getBuffList().get(i).getEndTimer()-Mideas.getLoopTickTimer());
				if(remaining >= 60000) {
					remaining = remaining/60000;
				}
				else {
					remaining = remaining/1000;
					Draw.drawQuad(Mideas.joueur1().getBuffList().get(i).getAura().getTexture(), Display.getWidth()/2, Display.getHeight()/2, 30*Mideas.getDisplayXFactor(), 28*Mideas.getDisplayYFactor());
					FONT.drawStringShadow(Display.getWidth()/2, Display.getHeight()/2+50, value[(int)remaining+1]+" s", Color.WHITE, Color.BLACK, 1, 0, 0);
				}
			}
			i++;
		}
	}
}
