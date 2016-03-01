package com.mideas.rpg.v2.hud;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.Arrays;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;

import com.mideas.rpg.v2.Interface;
import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.Sprites;
import com.mideas.rpg.v2.TTF2;
import com.mideas.rpg.v2.utils.Draw;

public class SpellBookFrame {
	
	private static boolean[] hover_book = new boolean[7];
	private static boolean hover_book_button;

	public static void draw() throws FileNotFoundException, SQLException {
		int x = 110;
		int y = -290;
		int yShift = 85;
		Draw.drawQuad(Sprites.spellbook_page1, Display.getWidth()/2, Display.getHeight()/2-350);
		if(Mideas.joueur1().getClasse().equals("Guerrier")) {
			Draw.drawQuad(Sprites.spellbook_spell_bg, Display.getWidth()/2+x, Display.getHeight()/2+y);
			Draw.drawQuad(Sprites.spell_book_heroic_strike, Display.getWidth()/2+x+20, Display.getHeight()/2+y+yShift+15);
			Draw.drawQuad(Sprites.spellbook_spell_bg, Display.getWidth()/2+x, Display.getHeight()/2+y+yShift);
			TTF2.characterFrameStats.drawStringShadow(Display.getWidth()/2+x+75, Display.getHeight()/2+y+yShift+15, "Heroic Strike", Color.decode("#D9D623"), Color.black, 1, 1, 1);
			drawSpell(Sprites.spell_book_grey_charge, Sprites.spell_book_charge, 3, x, y+2*yShift, "Charge");
			drawSpell(Sprites.spell_book_grey_thunder_clap, Sprites.spell_book_thunder_clap, 7, x, y+3*yShift, "Thunder Clap");
			drawSpell(Sprites.spell_book_grey_rend, Sprites.spell_book_rend, 10, x, y+4*yShift, "Rend");
			drawSpell(Sprites.spell_book_grey_mortal_strike, Sprites.spell_book_mortal_strike, 15, x, y+5*yShift, "Mortal Strike");
			if(hover_book_button) {
				Draw.drawQuad(Sprites.close_spell_book_hover, Display.getWidth()/2+593, Display.getHeight()/2-332);
			}
		}
	}
	
	public static boolean mouseEvent() {
		setHoverFalse();
		Arrays.fill(hover_book, false);
		int x = 110;
		int y = -290;
		int yShift = 85;
		if(Mideas.mouseX() >= Display.getWidth()/2+x+480 && Mideas.mouseX() <= Display.getWidth()/2+x+502 && Mideas.mouseY() >= Display.getHeight()/2+y-45 && Mideas.mouseY() <= Display.getHeight()/2+y-25) {
			hover_book_button = true;
			if(Mouse.getEventButtonState()) {
				Interface.closeSpellBookFrame();
				return true;
			}
		}
		int i = 0;
		while(i <= 5) {
			isHoverBook(x, y, i*yShift, i);
			i++;
		}
		return false;
	}
	
	private static boolean isHoverBook(int x, int y, int yShift, int i) {
		if(Mideas.mouseX() >= Display.getWidth()/2+x+20 && Mideas.mouseX() <= Display.getWidth()/2+x+20+44 && Mideas.mouseY() >= Display.getHeight()/2+y+yShift+15 && Mideas.mouseY() <= Display.getHeight()/2+y+yShift+15+44) {
			hover_book[i] = true;
		}
		return false;
	}
	
	private static void drawSpell(Texture grey_texture, Texture texture, int level, int x, int y, String spellName) throws FileNotFoundException, SQLException {
		if(Mideas.getLevel() < level) {
			Draw.drawQuad(grey_texture, Display.getWidth()/2+x+20, Display.getHeight()/2+y+15);
			Draw.drawQuad(Sprites.spellbook_spell_bg_grey, Display.getWidth()/2+x, Display.getHeight()/2+y);
			TTF2.characterFrameStats.drawStringShadow(Display.getWidth()/2+x+75, Display.getHeight()/2+y+15, spellName, Color.decode("#633E21"), Color.black, 1, 1, 1);
			TTF2.characterFrameStats.drawStringShadow(Display.getWidth()/2+x+75, Display.getHeight()/2+y+30, "Available at level "+level, Color.decode("#633E21"), Color.black, 1, 1, 1);
		}
		else {
			Draw.drawQuad(texture, Display.getWidth()/2+x+20, Display.getHeight()/2+y+15);
			Draw.drawQuad(Sprites.spellbook_spell_bg, Display.getWidth()/2+x, Display.getHeight()/2+y);
			TTF2.characterFrameStats.drawStringShadow(Display.getWidth()/2+x+75, Display.getHeight()/2+y+15, spellName, Color.decode("#D9D623"), Color.black, 1, 1, 1);
		}
	}
	
	public static boolean getHoverBook(int i) {
		return hover_book[i];
	}
	
	public static boolean[] getHoverBook() {
		return hover_book;
	}
	
	private static void setHoverFalse() {
		hover_book_button = false;
	}
}