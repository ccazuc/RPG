package com.mideas.rpg.v2.hud;

import java.util.ArrayList;

import org.lwjgl.opengl.Display;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.game.redalert.DefaultRedAlert;
import com.mideas.rpg.v2.game.redalert.RedAlert;
import com.mideas.rpg.v2.FontManager;
import com.mideas.rpg.v2.utils.Color;
import com.mideas.rpg.v2.utils.render.TTF;

public class RedAlertFrame {

	private final static int OPACITY_DECREASE_TIMER = 4000;
	private final static int MAXIMUM_ALERT = 3;
	private static float Y_DRAW = Display.getHeight()/2-300*Mideas.getDisplayYFactor();
	private static float Y_SHIFT = 25*Mideas.getDisplayXFactor();
	private static ArrayList<RedAlert> alertList = new ArrayList<RedAlert>();
	public final static Color RED = Color.decode("#FA1518");
	public final static Color YELLOW = Color.decode("#DBD709");
	public final static TTF FONT = FontManager.get("FRIZQT", 21);
	
	public static void draw() {
		int i = alertList.size();
		FONT.drawBegin();
		while(--i >= 0 && i < alertList.size()) {
			if(Mideas.getLoopTickTimer()-alertList.get(i).getTimer() >= OPACITY_DECREASE_TIMER) {
				alertList.get(i).decreaseOpacity(-1/(Mideas.FPS*OPACITY_DECREASE_TIMER/1000f));
				if(alertList.get(i).getOpacity() <= 0) {
					alertList.remove(i);
					i++;
					continue;
				}
			}
			FONT.drawStringShadowPart(Display.getWidth()/2-alertList.get(i).getMessageWidth()/2, Y_DRAW+(alertList.size()-1-i)*Y_SHIFT, alertList.get(i).getMessage(), alertList.get(i).getColor(), Color.BLACK, 1, 1, 1, alertList.get(i).getOpacity());
		}
		FONT.drawEnd();
	}
	
	public static void addNewAlert(String message) {
		alertList.add(new RedAlert(message));
		if(alertList.size() > MAXIMUM_ALERT) {
			alertList.remove(0);
		}
	}
	
	public static void addNewAlert(DefaultRedAlert alert) {
		alertList.add(new RedAlert(alert.getMessage()));
		if(alertList.size() > MAXIMUM_ALERT) {
			alertList.remove(0);
		}
	}
	
	public static void addNewAlert(DefaultRedAlert alert, Color color) {
		alertList.add(new RedAlert(alert.getMessage(), color));
		if(alertList.size() > MAXIMUM_ALERT) {
			alertList.remove(0);
		}
	}
}
