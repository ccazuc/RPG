package com.mideas.rpg.v2.hud;

import java.io.FileNotFoundException;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;

import com.mideas.rpg.v2.Interface;
import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.Sprites;
import com.mideas.rpg.v2.TTF2;
import com.mideas.rpg.v2.game.CharacterStuff;
import com.mideas.rpg.v2.utils.Draw;


public class EscapeFrame {
	
	private static boolean hoverQuit;
	private static boolean hoverHelp;
	private static boolean hoverChangeClass;
	private static boolean hoverReturn;
	private static boolean hoverAdminPanel;
	private static boolean hoverInterface;
	private static int x;
	private static int y;
	private static int yShift;
	
	public static void draw() throws FileNotFoundException {
		x = -10;
		y = -245;
		yShift = 20;
		Draw.drawQuad(Sprites.escape_frame, Display.getWidth()/2-120, Display.getHeight()/2-300);
		drawButton(hoverHelp, x, y);
		drawButton(hoverAdminPanel, x, y+yShift);
		drawButton(hoverInterface, x, y+2*yShift);
		drawButton(hoverChangeClass, x, y+7*yShift+5);
		drawButton(hoverQuit, x, y+8*yShift+5);
		drawButton(hoverReturn, x, y+10*yShift);
		TTF2.buttonFont.drawStringShadow(Display.getWidth()/2-TTF2.buttonFont.getWidth("Help")/2-10, Display.getHeight()/2-243, "Help", Color.white, Color.black, 1, 1, 1);
		TTF2.buttonFont.drawStringShadow(Display.getWidth()/2-TTF2.buttonFont.getWidth("Admin Panel")/2-10, Display.getHeight()/2-223, "Admin Panel", Color.white, Color.black, 1, 1, 1);
		TTF2.buttonFont.drawStringShadow(Display.getWidth()/2-TTF2.buttonFont.getWidth("Interface")/2-10, Display.getHeight()/2-203, "Interface", Color.white, Color.black, 1, 1, 1);
		TTF2.buttonFont.drawStringShadow(Display.getWidth()/2-TTF2.buttonFont.getWidth("Quit")/2-10, Display.getHeight()/2-78, "Quit", Color.white, Color.black, 1, 1, 1);
		TTF2.buttonFont.drawStringShadow(Display.getWidth()/2-TTF2.buttonFont.getWidth("Change Class")/2-10, Display.getHeight()/2-98, "Change Class", Color.white, Color.black, 1, 1, 1);
		TTF2.buttonFont.drawStringShadow(Display.getWidth()/2-TTF2.buttonFont.getWidth("Return to Game")/2-10, Display.getHeight()/2-43, "Return to Game", Color.white, Color.black, 1, 1, 1);
 	}
	
	public static boolean mouseEvent() throws FileNotFoundException {
		setHoverFalse();
		x = -70;
		y = -225;
		if(isHover(x, y)) {
			hoverHelp = true;
		}
		else if(isHover(x, y+yShift)) {
			hoverAdminPanel = true;
		}
		else if(isHover(x, y+2*yShift)) {
			hoverInterface = true;
		}
		else if(isHover(x, y+7*yShift+5)) {
			hoverChangeClass = true;
		}
		else if(isHover(x, y+8*yShift+5)) {
			hoverQuit = true;
		}
		else if(isHover(x, y+10*yShift+5)) {
			hoverReturn = true;
		}
		if(Mouse.getEventButtonState()) {
			if(hoverQuit) {
				CharacterStuff.setBagItems();
				System.exit(1);
			}
			else if(hoverChangeClass) {
				Interface.closeEscapeFrame();
				Interface.setIsChangeClassActive(false);
			}
			else if(hoverReturn) {
				Interface.closeEscapeFrame();
			}
			else if(hoverAdminPanel) {
				Interface.closeEscapeFrame();
				Interface.openAdminPanelFrame();
			}
			else if(hoverInterface) {
				
			}
		}
		return false;
	}
	
	public static void keyboardEvent() {
		
	}
	
	private static boolean isHover(int x, int y) {
		if(Mideas.mouseX() >= Display.getWidth()/2+x && Mideas.mouseX() <= Display.getWidth()/2+x+122 && Mideas.mouseY() <= Display.getHeight()/2+y && Mideas.mouseY() >= Display.getHeight()/2+y-15) {
			return true;
		}
		return false;
	}
	
	private static void drawButton(boolean hover, int x, int y) {
		if(hover) {
			Draw.drawQuad(Sprites.button_hover, Display.getWidth()/2-Sprites.button_hover.getImageWidth()/2+x, Display.getHeight()/2+y);
		}
		else {
			Draw.drawQuad(Sprites.button, Display.getWidth()/2-Sprites.button_hover.getImageWidth()/2+x, Display.getHeight()/2+y);
		}
		Draw.drawQuad(Sprites.cursor, -200, -200);
	}
	
	private static void setHoverFalse() {
		hoverQuit = false;
		hoverHelp = false;
		hoverChangeClass = false;
		hoverReturn = false;
		hoverAdminPanel = false;
		hoverInterface = false;
	}
}
