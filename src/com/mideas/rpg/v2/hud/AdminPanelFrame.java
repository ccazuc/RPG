package com.mideas.rpg.v2.hud;

import java.util.Arrays;

import org.lwjgl.opengl.Display;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.FontManager;
import com.mideas.rpg.v2.game.CharacterStuff;
import com.mideas.rpg.v2.render.Draw;
import com.mideas.rpg.v2.render.Sprites;
import com.mideas.rpg.v2.utils.Button;
import com.mideas.rpg.v2.utils.Color;

public class AdminPanelFrame {

	private static boolean shouldUpdateSize;
	private static int LEFT_ANCHOR = Display.getWidth()/2-325;
	private static int y = -250;
	private static String joueur1 = "joueur1";
	private static Button sexMaxStaminaPlayer1 = new Button(LEFT_ANCHOR, Display.getHeight()/2+y, (short)130, (short)30, "Fully heals you", 12, 1) {
		@Override
		public void onLeftClickUp() {
			Mideas.joueur1().setStamina(Mideas.joueur1().getMaxStamina());
		}
	};
	
	private static Button sexMaxManaPlayer1 = new Button(LEFT_ANCHOR, Display.getHeight()/2+y+40, (short)130, (short)30, "Restore your mana", 12, 1) {
		@Override
		public void onLeftClickUp() {
			Mideas.joueur1().setMana(Mideas.joueur1().getMaxMana());
		}
	};
	
	private static Button killPlayer1 = new Button(LEFT_ANCHOR, Display.getHeight()/2+y+80, (short)130, (short)30, "Kill you", 12, 1) {
		@Override
		public void onLeftClickUp() {
			Mideas.joueur1().setStamina(0);
		}
	};
	
	private static Button clearBag = new Button(LEFT_ANCHOR, Display.getHeight()/2+y+120, (short)130, (short)30, "Clear your bag", 12, 1) {
		@Override
		public void onLeftClickUp() {
			Arrays.fill(Mideas.joueur1().bag().getBag(), null);
			CharacterStuff.setBagItems();
		}
	};
	
	public static void draw() {
		updateSize();
		Draw.drawQuad(Sprites.admin_panel, LEFT_ANCHOR-75, Display.getHeight()/2+y-100);
		FontManager.get("FRIZQT", 18).drawStringShadow(LEFT_ANCHOR+25, Display.getHeight()/2+y-40, joueur1, Color.WHITE, Color.BLACK, 1, 1, 1);
		sexMaxStaminaPlayer1.draw();
		sexMaxManaPlayer1.draw();
		killPlayer1.draw();
		clearBag.draw();
	}
	
	public static boolean mouseEvent() {
		sexMaxStaminaPlayer1.event();
		sexMaxManaPlayer1.event();
		killPlayer1.event();
		clearBag.event();
		return false;
	}
	
	public static void updateSize() {
		if(!shouldUpdateSize) {
			return;
		}
		LEFT_ANCHOR = Display.getWidth()/2-325;
		sexMaxStaminaPlayer1.update(LEFT_ANCHOR, Display.getHeight()/2+y);
		sexMaxManaPlayer1.update(LEFT_ANCHOR, Display.getHeight()/2+y+40);
		killPlayer1.update(LEFT_ANCHOR, Display.getHeight()/2+y+80);
		clearBag.update(LEFT_ANCHOR, Display.getHeight()/2+y+120);
		shouldUpdateSize = false;
	}
	
	public static void shouldUpdate() {
		shouldUpdateSize = true;
	}
}
