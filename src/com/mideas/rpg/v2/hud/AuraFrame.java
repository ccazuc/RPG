package com.mideas.rpg.v2.hud;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import com.mideas.rpg.v2.FontManager;
import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.TTF;
import com.mideas.rpg.v2.command.CommandAura;
import com.mideas.rpg.v2.utils.Color;
import com.mideas.rpg.v2.utils.Draw;

public class AuraFrame {

	private static int buffHovered = -1;
	private static int rightClickDownBuff = -1;
	private static float X_BUFF = Display.getWidth()-100*Mideas.getDisplayXFactor();
	private static float X_SHIFT_BUFF = -40*Mideas.getDisplayXFactor();
	private static float Y_BUFF = 15*Mideas.getDisplayYFactor();
	private static float X_SIZE = 30*Mideas.getDisplayXFactor();
	private static float Y_SIZE = 28*Mideas.getDisplayYFactor();
	private final static String[] value = new String[61];
	private static boolean init;
	private final static TTF FONT = FontManager.get("FRIZQT", 13);
	private final static float MINIMUM_OPACITY = .3f;
	private final static float OPACITY_TICK = .012f;
	private static boolean shouldUpdate;
	
	public static void draw() {
		if(!init) {
			int j = 0;
			while(j < value.length) {
				value[j] = String.valueOf(j);
				j++;
			}
			init = true;
		}
		if(shouldUpdate) {
			updateSize();
			shouldUpdate = false;
		}
		int i = 0;
		while(i < Mideas.joueur1().getBuffList().size()) {
			if(Mideas.joueur1().getBuffList().get(i).getAura().isVisible()) {
				long remaining = (Mideas.joueur1().getBuffList().get(i).getEndTimer()-Mideas.getLoopTickTimer());
				if(remaining >= 60000) {
					remaining = remaining/60000;
					Draw.drawQuad(Mideas.joueur1().getBuffList().get(i).getAura().getTexture(), X_BUFF+i*X_SHIFT_BUFF, Y_BUFF, X_SIZE, Y_SIZE);
					FONT.drawStringShadow(X_BUFF+i*X_SHIFT_BUFF, Y_BUFF+27*Mideas.getDisplayYFactor(), value[(int)remaining+1]+" m", Color.YELLOW, Color.BLACK, 1, 0, 0);
				}
				else {
					remaining = remaining/1000;
					Draw.drawQuad(Mideas.joueur1().getBuffList().get(i).getAura().getTexture(), X_BUFF+i*X_SHIFT_BUFF, Y_BUFF, X_SIZE, Y_SIZE, Mideas.joueur1().getBuffList().get(i).getOpacity());
					FONT.drawStringShadow(X_BUFF+i*X_SHIFT_BUFF, Y_BUFF+27*Mideas.getDisplayYFactor(), value[(int)remaining+1]+" s", Color.WHITE, Color.BLACK, 1, 0, 0, Mideas.joueur1().getBuffList().get(i).getOpacity());
					if(Mideas.joueur1().getBuffList().get(i).isOpacityAscending()) {
						if(Mideas.joueur1().getBuffList().get(i).getOpacity() >= 1) {
							Mideas.joueur1().getBuffList().get(i).setOpacityAscending(false);
							Mideas.joueur1().getBuffList().get(i).setOpacity(Mideas.joueur1().getBuffList().get(i).getOpacity()-OPACITY_TICK);
						}
						else {
							Mideas.joueur1().getBuffList().get(i).setOpacity(Mideas.joueur1().getBuffList().get(i).getOpacity()+OPACITY_TICK);
						}
					}
					else {
						if(Mideas.joueur1().getBuffList().get(i).getOpacity() <= MINIMUM_OPACITY) {
							Mideas.joueur1().getBuffList().get(i).setOpacityAscending(true);
							Mideas.joueur1().getBuffList().get(i).setOpacity(Mideas.joueur1().getBuffList().get(i).getOpacity()+OPACITY_TICK);
						}
						else {
							Mideas.joueur1().getBuffList().get(i).setOpacity(Mideas.joueur1().getBuffList().get(i).getOpacity()-OPACITY_TICK);
						}
					}
				}
			}
			i++;
		}
	}
	
	public static void mouseEvent() {
		int i = 0;
		buffHovered = -1;
		while(i < Mideas.joueur1().getBuffList().size()) {
			if(Mideas.joueur1().getBuffList().get(i).getAura().isVisible()) {
				if(Mideas.getHover() && Mideas.mouseX() >= X_BUFF+i*X_SHIFT_BUFF && Mideas.mouseX() <= X_BUFF+i*X_SHIFT_BUFF+X_SIZE && Mideas.mouseY() >= Y_BUFF && Mideas.mouseY() <= Y_BUFF+Y_SIZE) {
					buffHovered = i;
				}
			}
			i++;
		}
		if(Mouse.getEventButtonState()) {
			if(Mouse.getEventButton() == 1) {
				rightClickDownBuff = buffHovered;
			}
		}
		else {
			if(Mouse.getEventButton() == 1 && buffHovered == rightClickDownBuff && buffHovered != -1) {
				CommandAura.cancelAura(Mideas.joueur1().getBuffList().get(rightClickDownBuff).getAura().getId());
				System.out.println(rightClickDownBuff+" "+Mideas.joueur1().getBuffList().get(rightClickDownBuff).getAura().getId());
			}
		}
	}
	
	public static void shouldUpdate() {
		shouldUpdate = true;
	}
	
	public static void updateSize() {
		X_BUFF = Display.getWidth()-100*Mideas.getDisplayXFactor();
		X_SHIFT_BUFF = -40*Mideas.getDisplayXFactor();
		Y_BUFF = 15*Mideas.getDisplayYFactor();
		X_SIZE = 30*Mideas.getDisplayXFactor();
		Y_SIZE = 28*Mideas.getDisplayYFactor();
	}
}
