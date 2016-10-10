package com.mideas.rpg.v2.hud;

import java.util.ArrayList;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.TTF2;
import com.mideas.rpg.v2.utils.RedAlert;

public class RedAlertFrame {

	private final static int OPACITY_DECREASE_TIMER = 4000;
	private final static int MAXIMUM_ALERT = 3;
	private static float Y_DRAW = Display.getHeight()/2-300*Mideas.getDisplayYFactor();
	private static float Y_SHIFT = 25*Mideas.getDisplayXFactor();
	private static ArrayList<RedAlert> alertList = new ArrayList<RedAlert>();
	private final static Color RED = Color.decode("#FA1518");
	
	public static void draw() {
		int i = alertList.size()-1;
		while(i >= 0 && i < alertList.size()) {
			if(System.currentTimeMillis()-alertList.get(i).getTimer() >= OPACITY_DECREASE_TIMER) {
				alertList.get(i).decreaseOpacity(-1/(60*OPACITY_DECREASE_TIMER/1000f));
				if(alertList.get(i).getOpacity() <= 0) {
					alertList.remove(i);
					i++;
					continue;
				}
			}
			TTF2.selectScreenDeleteCharacterConfirm.drawString(Display.getWidth()/2-TTF2.selectScreenDeleteCharacterConfirm.getWidth(alertList.get(i).getMessage()), Y_DRAW+(alertList.size()-1-i)*Y_SHIFT, alertList.get(i).getMessage(), RED, alertList.get(i).getOpacity());
			i--;
		}
	}
	
	public static void addNewAlert(String message) {
		alertList.add(new RedAlert(message));
		if(alertList.size() > MAXIMUM_ALERT) {
			alertList.remove(0);
		}
	}
}
