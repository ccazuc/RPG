package com.mideas.rpg.v2.hud;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.Arrays;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.Sprites;
import com.mideas.rpg.v2.utils.Draw;

public class ChangeBackGroundFrame {
	
	private static boolean[] hover = new boolean[6];
	private static int xLeft = 100;
	private static int xShift = 400;
	private static int y = 50;
	private static int yShift = 230;

	public static void draw() {
		Draw.drawQuad(Sprites.resized_base_bg, xLeft, y);
		Draw.drawQuad(Sprites.resized_bc_bg, xLeft, y+yShift);
		Draw.drawQuad(Sprites.resized_doge_bg, xLeft, y+2*yShift);
		Draw.drawQuad(Sprites.resized_illidan_bg, xLeft, y+3*yShift);
		Draw.drawQuad(Sprites.resized_sunwell1_bg, xLeft+xShift, y);
		Draw.drawQuad(Sprites.resized_sunwell2_bg, xLeft+xShift, y+yShift);
		drawBorder(xLeft, y, 0);
		drawBorder(xLeft, y+yShift, 1);
		drawBorder(xLeft, y+2*yShift, 2);
		drawBorder(xLeft, y+3*yShift, 3);
		drawBorder(xLeft+xShift, y, 4);
		drawBorder(xLeft+xShift, y+yShift, 5);
	}
	
	public static boolean mouseEvent() throws FileNotFoundException, SQLException {
		Arrays.fill(hover, false);
		isHover(xLeft, y, 0);
		isHover(xLeft, y+yShift, 1);
		isHover(xLeft, y+2*yShift, 2);
		isHover(xLeft, y+3*yShift, 3);
		isHover(xLeft+xShift, y, 4);
		isHover(xLeft+xShift, y+yShift, 5);
		if(Mouse.getEventButtonState()) {
			changeBackground(0, Sprites.bg);
			changeBackground(1, Sprites.bc_bg);
			changeBackground(2, Sprites.doge_bg);
			changeBackground(3, Sprites.illidan_bg);
			changeBackground(4, Sprites.sunwell1_bg);
			changeBackground(5, Sprites.sunwell2_bg);
		}
		return false;
	}
	
	private static void isHover(int x, int y, int i) {
		if(Mideas.mouseX() >= x && Mideas.mouseX() <= x+350 && Mideas.mouseY() >= y && Mideas.mouseY() <= y+219) {
			hover[i] = true;
		}
	}
	
	private static void changeBackground(int i, Texture texture) throws FileNotFoundException, SQLException {
		if(hover[i]) {
			Sprites.current_bg = texture;
			Mideas.setConfig();
		}
	}
	
	private static void drawBorder(int x, int y, int i) {
		if(hover[i]) {
			Draw.drawColorQuadBorder(x-3, y-3, 356, 225, Color.black, 3);
		}
	}
	
	public static String getCurrentBackground() throws SQLException {
		if(Sprites.current_bg.equals(Sprites.bg)) {
			return "bg";
		}
		else if(Sprites.current_bg.equals(Sprites.bc_bg)) {
			return "bc_bg";
		}
		else if(Sprites.current_bg.equals(Sprites.doge_bg)) {
			return "doge_bg";
		}
		else if(Sprites.current_bg.equals(Sprites.illidan_bg)) {
			return "illidan_bg";
		}
		else if(Sprites.current_bg.equals(Sprites.sunwell1_bg)) {
			return "sunwell1_bg";
		}
		else if(Sprites.current_bg.equals(Sprites.sunwell2_bg)) {
			return "sunwell2_bg";
		}
		else {
			return null;
		}
	}
	
	public static void loadBG(String string) {
		if(string.equals("bg")) {
			Sprites.current_bg = Sprites.bg;
		}
		else if(string.equals("bc_bg")) {
			Sprites.current_bg = Sprites.bc_bg;
		}
		else if(string.equals("doge_bg")) {
			Sprites.current_bg = Sprites.doge_bg;
		}
		else if(string.equals("illidan_bg")) {
			Sprites.current_bg = Sprites.illidan_bg;
		}
		else if(string.equals("sunwell1_bg")) {
			Sprites.current_bg = Sprites.sunwell1_bg;
		}
		else if(string.equals("sunwell_2bg")) {
			Sprites.current_bg = Sprites.sunwell2_bg;
		}
	}
}
