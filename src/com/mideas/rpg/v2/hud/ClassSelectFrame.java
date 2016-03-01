package com.mideas.rpg.v2.hud;

import java.io.FileNotFoundException;
import java.sql.SQLException;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;

import com.mideas.rpg.v2.Interface;
import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.Sprites;
import com.mideas.rpg.v2.TTF2;
import com.mideas.rpg.v2.game.CharacterStuff;
import com.mideas.rpg.v2.game.Joueur;
import com.mideas.rpg.v2.game.classes.DeathKnight;
import com.mideas.rpg.v2.game.classes.Guerrier;
import com.mideas.rpg.v2.game.classes.Hunter;
import com.mideas.rpg.v2.game.classes.Mage;
import com.mideas.rpg.v2.game.classes.Monk;
import com.mideas.rpg.v2.game.classes.Paladin;
import com.mideas.rpg.v2.game.classes.Priest;
import com.mideas.rpg.v2.game.classes.Rogue;
import com.mideas.rpg.v2.game.classes.Shaman;
import com.mideas.rpg.v2.game.classes.Warlock;
import com.mideas.rpg.v2.utils.Draw;

public class ClassSelectFrame {
	
	private static boolean warriorHover;
	private static boolean paladinHover;
	private static boolean hunterHover;
	private static boolean rogueHover;
	private static boolean priestHover;
	private static boolean deathknightHover;
	private static boolean shamanHover;
	private static boolean mageHover;
	private static boolean warlockHover;
	private static boolean monkHover;
	private static boolean isBagEquipped;
	private static float expClass;
	
	public static void draw() throws FileNotFoundException, SQLException {
		Color bgColor = new Color(0, 0, 0,.45f); 
		Draw.drawColorQuad(Display.getWidth()/2-280, Display.getHeight()-330, 600, 550, bgColor);
		int x = -140;
		int xShift = 62;
		int yFirstLine = -80;
		int ySecondLine = -13;
		drawFrameCreateClass(Sprites.class_choose, x-22, yFirstLine-39);
		drawFrameCreateClass(Sprites.warrior_create_classe, x, yFirstLine);
		drawFrameCreateClass(Sprites.paladin_create_classe, x+xShift, yFirstLine);
		drawFrameCreateClass(Sprites.hunter_create_classe, x+2*xShift, yFirstLine);
		drawFrameCreateClass(Sprites.rogue_create_classe, x+3*xShift, yFirstLine);
		drawFrameCreateClass(Sprites.priest_create_classe, x+4*xShift, yFirstLine);
		drawFrameCreateClass(Sprites.deathknight_create_classe, x, ySecondLine);
		drawFrameCreateClass(Sprites.shaman_create_classe, x+xShift, ySecondLine);
		drawFrameCreateClass(Sprites.mage_create_classe, x+2*xShift, ySecondLine);
		drawFrameCreateClass(Sprites.warlock_create_classe, x+3*xShift, ySecondLine);
		drawFrameCreateClass(Sprites.monk_create_classe, x+4*xShift, ySecondLine);
		x-= 6;
		yFirstLine-= 5;
		ySecondLine-= 5;
		drawBorder(x, yFirstLine, 0);
		drawBorder(x+xShift, yFirstLine, 1);
		drawBorder(x+2*xShift, yFirstLine, 0);
		drawBorder(x+3*xShift, yFirstLine, 1);
		drawBorder(x+4*xShift, yFirstLine, 0);
		drawBorder(x, ySecondLine, 1);
		drawBorder(x+xShift, ySecondLine, 0);
		drawBorder(x+2*xShift, ySecondLine, 1);
		drawBorder(x+3*xShift, ySecondLine, 0);
		drawBorder(x+4*xShift, ySecondLine, 1);
		TTF2.playerName.drawStringShadow(Display.getWidth()/2-26, Display.getHeight()/2-108, "Class select", Color.yellow, Color.black, 1, 1, 1);
		int xLeft = -210;
		int xRight = 90;
		int y = -295;
		int yShift = 60;
		drawLevel(Mideas.getExpAll(1), xLeft, y);
		drawLevel(Mideas.getExpAll(5), xLeft, y+yShift);
		drawLevel(Mideas.getExpAll(2), xLeft, y+2*yShift);
		drawLevel(Mideas.getExpAll(7), xLeft, y+3*yShift);
		drawLevel(Mideas.getExpAll(6), xLeft, y+4*yShift);
		drawLevel(Mideas.getExpAll(0), xRight, y);
		drawLevel(Mideas.getExpAll(8), xRight, y+yShift);
		drawLevel(Mideas.getExpAll(3), xRight, y+2*yShift);
		drawLevel(Mideas.getExpAll(9), xRight, y+3*yShift);
		drawLevel(Mideas.getExpAll(4), xRight, y+4*yShift);
		y = -30;
		TTF2.spellName.drawStringShadow(Display.getWidth()/2+5, Display.getHeight()+y-295, "Level :", Color.white, Color.black, 1, 1, 1);
		xLeft = -270;
		xRight = 30;
		y = -285;
		drawFrameClass(Sprites.warrior_create_classe_level, xLeft, y);
		drawFrameClass(Sprites.paladin_create_classe_level, xLeft, y+yShift);
		drawFrameClass(Sprites.hunter_create_classe_level, xLeft, y+2*yShift);
		drawFrameClass(Sprites.rogue_create_classe_level, xLeft, y+3*yShift);
		drawFrameClass(Sprites.priest_create_classe_level, xLeft, y+4*yShift);
		drawFrameClass(Sprites.deathknight_create_classe_level, xRight, y);
		drawFrameClass(Sprites.shaman_create_classe_level, xRight, y+yShift);
		drawFrameClass(Sprites.mage_create_classe_level, xRight, y+2*yShift);
		drawFrameClass(Sprites.warlock_create_classe_level, xRight, y+3*yShift);
		drawFrameClass(Sprites.monk_create_classe_level, xRight, y+4*yShift);
		xLeft = -220;
		xRight = 80;
		y = -270;
		drawExpBar(Mideas.getExpAll(1), xLeft, y);
		drawExpBar(Mideas.getExpAll(5), xLeft, y+yShift);
		drawExpBar(Mideas.getExpAll(2), xLeft, y+2*yShift);
		drawExpBar(Mideas.getExpAll(7), xLeft, y+3*yShift);
		drawExpBar(Mideas.getExpAll(6), xLeft, y+4*yShift);
		drawExpBar(Mideas.getExpAll(0), xRight, y);
		drawExpBar(Mideas.getExpAll(8), xRight, y+yShift);
		drawExpBar(Mideas.getExpAll(3), xRight, y+2*yShift);
		drawExpBar(Mideas.getExpAll(9), xRight, y+3*yShift);
		drawExpBar(Mideas.getExpAll(4), xRight, y+4*yShift);
		y = -30;
		isClassHoverExp(warriorHover, Mideas.getExpAll(1), -144, -82, -220, y, -240);
		isClassHoverExp(paladinHover, Mideas.getExpAll(5), -82, -82, -220, y, -180);
		isClassHoverExp(hunterHover, Mideas.getExpAll(2), -20, -82, -220, y, -120);
		isClassHoverExp(rogueHover, Mideas.getExpAll(7), 40, -82, -220, y, -60);
		isClassHoverExp(priestHover, Mideas.getExpAll(6), 104, -82, -220, y, 0);
		isClassHoverExp(deathknightHover, Mideas.getExpAll(0), -144, -15, 80, y, -240);
		isClassHoverExp(shamanHover, Mideas.getExpAll(8), -82, -15, 80, y, -180);
		isClassHoverExp(mageHover, Mideas.getExpAll(3), -20, -15, 80, y, -120);
		isClassHoverExp(warlockHover, Mideas.getExpAll(9), 40, -15, 80, y, -60);
		isClassHoverExp(monkHover, Mideas.getExpAll(4), 104, -15, 80, y, 0);
	}
	
	public static boolean mouseEvent() throws FileNotFoundException, SQLException {
		setHoverFalse();
		warriorHover = isClassHover(-140, -80);
		paladinHover = isClassHover(-78, -80);
		hunterHover = isClassHover(-16, -80);
		rogueHover = isClassHover(46, -80);
		priestHover = isClassHover(108, -80);
		deathknightHover = isClassHover(-140, -13);
		shamanHover = isClassHover(-78, -13);
		mageHover= isClassHover(-16, -13);
		warlockHover = isClassHover(46, -13);
		monkHover = isClassHover(108, -13);
		if(Mouse.getEventButtonState()) {
			setNewClass("Guerrier", new Guerrier(), warriorHover);
			setNewClass("Paladin", new Paladin(), paladinHover);
			setNewClass("Hunter", new Hunter(), hunterHover);
			setNewClass("Rogue", new Rogue(), rogueHover);
			setNewClass("Priest", new Priest(), priestHover);
			setNewClass("DeathKnight", new DeathKnight(), deathknightHover);
			setNewClass("Shaman", new Shaman(), shamanHover);
			setNewClass("Mage", new Mage(), mageHover);
			setNewClass("Warlock", new Warlock(), warlockHover);
			setNewClass("Monk", new Monk(), monkHover);
		}
		return false;
	}

	/*public static String classTxt() {
		if(Mideas.joueur1() != null && !isStuffEquipped) {
			if(Mideas.joueur1().getClasse().equals("Guerrier")) {
				return "equippedItemsGuerrier.txt";
			}
			else if(Mideas.joueur1().getClasse().equals("Paladin")) {
				return "equippedItemsPaladin.txt";
			}
			else if(Mideas.joueur1().getClasse().equals("Hunter")) {
				return "equippedItemsHunter.txt";
			}
			else if(Mideas.joueur1().getClasse().equals("Rogue")) {
				return "equippedItemsRogue.txt";
			}
			else if(Mideas.joueur1().getClasse().equals("Priest")) {
				return "equippedItemsPriest.txt";
			}
			else if(Mideas.joueur1().getClasse().equals("DeathKnight")) {
				return "equippedItemsDeathKnight.txt";
			}
			else if(Mideas.joueur1().getClasse().equals("Shaman")) {
				return "equippedItemsShaman.txt";
			}
			else if(Mideas.joueur1().getClasse().equals("Mage")) {
				return "equippedItemsMage.txt";
			}
			else if(Mideas.joueur1().getClasse().equals("Warlock")) {
				return "equippedItemsWarlock.txt";
			}
		return "equippedItemsMonk.txt";
		}
		isStuffEquipped = true;
		return null;
	
	}*/
	
	public static String goldTxt() {
		if(Mideas.joueur1() != null) {
			if(Mideas.joueur1().getClasse().equals("Guerrier")) {
				return "goldGuerrier.txt";
			}
			else if(Mideas.joueur1().getClasse().equals("Paladin")) {
				return "goldPaladin.txt";
			}
			else if(Mideas.joueur1().getClasse().equals("Hunter")) {
				return "goldHunter.txt";
			}
			else if(Mideas.joueur1().getClasse().equals("Rogue")) {
				return "goldRogue.txt";
			}
			else if(Mideas.joueur1().getClasse().equals("Priest")) {
				return "goldPriest.txt";
			}
			else if(Mideas.joueur1().getClasse().equals("DeathKnight")) {
				return "goldDeathKnight.txt";
			}
			else if(Mideas.joueur1().getClasse().equals("Shaman")) {
				return "goldShaman.txt";
			}
			else if(Mideas.joueur1().getClasse().equals("Mage")) {
				return "goldMage.txt";
			}
			else if(Mideas.joueur1().getClasse().equals("Warlock")) {
				return "goldWarlock.txt";
			}
		return "equippedItemsMonk.txt";
		}
		return null;
	
	}
	
	/*public static String bagTxt() {
		if(Mideas.joueur1() != null && !isBagEquipped) {
			if(Mideas.joueur1().getClasse().equals("Guerrier")) {
				return "bagGuerrier.txt";
			}
			else if(Mideas.joueur1().getClasse().equals("Paladin")) {
				return "bagPaladin.txt";
			}
			else if(Mideas.joueur1().getClasse().equals("Hunter")) {
				return "bagHunter.txt";
			}
			else if(Mideas.joueur1().getClasse().equals("Rogue")) {
				return "bagRogue.txt";
			}
			else if(Mideas.joueur1().getClasse().equals("Priest")) {
				return "bagPriest.txt";
			}
			else if(Mideas.joueur1().getClasse().equals("DeathKnight")) {
				return "bagDeathKnight.txt";
			}
			else if(Mideas.joueur1().getClasse().equals("Shaman")) {
				return "bagShaman.txt";
			}
			else if(Mideas.joueur1().getClasse().equals("Mage")) {
				return "bagMage.txt";
			}
			else if(Mideas.joueur1().getClasse().equals("Warlock")) {
				return "bagWarlock.txt";
			}
			else {
				return "bagMonk.txt";
			}
		}
		isBagEquipped = true;
		return null;
	}*/
	
	public static String talentTxt() {
		if(Mideas.joueur1() != null && !isBagEquipped) {
			if(Mideas.joueur1().getClasse().equals("Guerrier")) {
				return "talentGuerrier.txt";
			}
			else if(Mideas.joueur1().getClasse().equals("Paladin")) {
				return "talentPaladin.txt";
			}
			else if(Mideas.joueur1().getClasse().equals("Hunter")) {
				return "talentHunter.txt";
			}
			else if(Mideas.joueur1().getClasse().equals("Rogue")) {
				return "talentRogue.txt";
			}
			else if(Mideas.joueur1().getClasse().equals("Priest")) {
				return "talentPriest.txt";
			}
			else if(Mideas.joueur1().getClasse().equals("DeathKnight")) {
				return "talentDeathKnight.txt";
			}
			else if(Mideas.joueur1().getClasse().equals("Shaman")) {
				return "talentShaman.txt";
			}
			else if(Mideas.joueur1().getClasse().equals("Mage")) {
				return "talentMage.txt";
			}
			else if(Mideas.joueur1().getClasse().equals("Warlock")) {
				return "talentWarlock.txt";
			}
			else {
				return "talentMonk.txt";
			}
		}
		return null;
	}

	/*public static String expTxt() {
		if(!isExpSet) {
			if(Mideas.joueur1().getClasse().equals("Guerrier")) {
				return "expGuerrier.txt";
			}
			else if(Mideas.joueur1().getClasse().equals("Paladin")) {
				return "expPaladin.txt";
			}
			else if(Mideas.joueur1().getClasse().equals("Hunter")) {
				return "expHunter.txt";
			}
			else if(Mideas.joueur1().getClasse().equals("Rogue")) {
				return "expRogue.txt";
			}
			else if(Mideas.joueur1().getClasse().equals("Priest")) {
				return "expPriest.txt";
			}
			else if(Mideas.joueur1().getClasse().equals("DeathKnight")) {
				return "expDeathKnight.txt";
			}
			else if(Mideas.joueur1().getClasse().equals("Shaman")) {
				return "expShaman.txt";
			}
			else if(Mideas.joueur1().getClasse().equals("Mage")) {
				return "expMage.txt";
			}
			else if(Mideas.joueur1().getClasse().equals("Warlock")) {
				return "expWarlock.txt";
			}
			else {
				return "expMonk.txt";
			}
		}
		isExpSet = true;
		return null;	
	}
	
	public static String shopPage1Txt() {
		if(Mideas.joueur1() != null) {
			if(Mideas.joueur1().getClasse().equals("Guerrier")) {
				return "shopGuerrierPage1.txt";
			}
			else if(Mideas.joueur1().getClasse().equals("Paladin")) {
				return "shopPaladinPage1.txt";
			}
			else if(Mideas.joueur1().getClasse().equals("Hunter")) {
				return "shopHunterPage1.txt";
			}
			else if(Mideas.joueur1().getClasse().equals("Rogue")) {
				return "shopRoguePage1.txt";
			}
			else if(Mideas.joueur1().getClasse().equals("Priest")) {
				return "shopPriestPage1.txt";
			}
			else if(Mideas.joueur1().getClasse().equals("DeathKnight")) {
				return "shopDeathKnightPage1.txt";
			}
			else if(Mideas.joueur1().getClasse().equals("Shaman")) {
				return "shopShamanPage1.txt";
			}
			else if(Mideas.joueur1().getClasse().equals("Mage")) {
				return "shopMagePage1.txt";
			}
			else if(Mideas.joueur1().getClasse().equals("Warlock")) {
				return "shopWarlockPage1.txt";
			}
			else {
				return "shopMonkPage1.txt";
			}
		}
		return null;
	}
	
	public static String shopPage2Txt() {
		if(Mideas.joueur1() != null) {
			if(Mideas.joueur1().getClasse().equals("Guerrier")) {
				return "shopGuerrierPage2.txt";
			}
			else if(Mideas.joueur1().getClasse().equals("Paladin")) {
				return "shopPaladinPage2.txt";
			}
			else if(Mideas.joueur1().getClasse().equals("Hunter")) {
				return "shopHunterPage2.txt";
			}
			else if(Mideas.joueur1().getClasse().equals("Rogue")) {
				return "shopRoguePage2.txt";
			}
			else if(Mideas.joueur1().getClasse().equals("Priest")) {
				return "shopPriestPage2.txt";
			}
			else if(Mideas.joueur1().getClasse().equals("DeathKnight")) {
				return "shopDeathKnightPage2.txt";
			}
			else if(Mideas.joueur1().getClasse().equals("Shaman")) {
				return "shopShamanPage2.txt";
			}
			else if(Mideas.joueur1().getClasse().equals("Mage")) {
				return "shopMagePage2.txt";
			}
			else if(Mideas.joueur1().getClasse().equals("Warlock")) {
				return "shopWarlockPage2.txt";
			}
			else {
				return "shopMonkPage2.txt";
			}
		}
		return null;
	}

	public static String shopPage3Txt() {
		if(Mideas.joueur1() != null) {
			if(Mideas.joueur1().getClasse().equals("Guerrier")) {
				return "shopGuerrierPage3.txt";
			}
			else if(Mideas.joueur1().getClasse().equals("Paladin")) {
				return "shopPaladinPage3.txt";
			}
			else if(Mideas.joueur1().getClasse().equals("Hunter")) {
				return "shopHunterPage3.txt";
			}
			else if(Mideas.joueur1().getClasse().equals("Rogue")) {
				return "shopRoguePage3.txt";
			}
			else if(Mideas.joueur1().getClasse().equals("Priest")) {
				return "shopPriestPage3.txt";
			}
			else if(Mideas.joueur1().getClasse().equals("DeathKnight")) {
				return "shopDeathKnightPage3.txt";
			}
			else if(Mideas.joueur1().getClasse().equals("Shaman")) {
				return "shopShamanPage3.txt";
			}
			else if(Mideas.joueur1().getClasse().equals("Mage")) {
				return "shopMagePage3.txt";
			}
			else if(Mideas.joueur1().getClasse().equals("Warlock")) {
				return "shopWarlockPage3.txt";
			}
			else {
				return "shopMonkPage3.txt";
			}
		}
		return null;
	}*/
	
	private static boolean isClassHover(int x, int y) {
		if(Mideas.mouseX() >= Display.getWidth()/2+x && Mideas.mouseX() <= Display.getWidth()/2+x+56 && Mideas.mouseY() >= Display.getHeight()/2+y && Mideas.mouseY() <= Display.getHeight()/2+y+56){
			return true;
		}
		return false;
	}
	
	private static void isClassHoverExp(boolean classHover, float expClass, int x_sprite, int y_sprite, int x_exp_bar, int y_exp_bar, int y_exp_bar_border) throws FileNotFoundException {
		if(classHover) {
			if(Mideas.getLevelAll((int)expClass) > 1) {
				expClass = ((float)expClass-(float)Mideas.getExpNeeded(Mideas.getLevelAll((int)expClass)-1))/((float)Mideas.getExpNeeded(Mideas.getLevelAll((int)expClass))-Mideas.getExpNeeded(Mideas.getLevelAll((int)expClass)-1)); 
			}
			else if(Mideas.getLevelAll((int)expClass) == 70) {
				expClass = 1;
			}
			else {
				expClass = ((float)expClass/Mideas.getExpNeeded(Mideas.getLevelAll((int)expClass))); 
			}
			if(expClass == 0.0) {
				Draw.drawColorQuad(Display.getWidth()/2+x_exp_bar, Display.getHeight()+y_exp_bar+y_exp_bar_border, 220, 11, Color.decode("#D418DE"));
			}
			else {
				Draw.drawColorQuad(Display.getWidth()/2+x_exp_bar, Display.getHeight()+y_exp_bar+y_exp_bar_border, 220*expClass, 11, Color.decode("#D418DE"));
			}
			Draw.drawQuad(Sprites.hover, Display.getWidth()/2+x_sprite, Display.getHeight()/2+y_sprite);
			Draw.drawColorQuadBorder(Display.getWidth()/2+x_exp_bar, Display.getHeight()+y_exp_bar+y_exp_bar_border, 220, 12, Color.black);
		}
	}
	
	private static boolean setNewClass(String string, Joueur joueur, boolean hover) throws FileNotFoundException, SQLException {
		if(hover) {
			if(Mideas.joueur1()!= null && Mideas.joueur1().getClasse().equals(string)){
				Interface.setIsChangeClassActive(true);
				CharacterStuff.getBagItems();
				return true;
			}
			if(Mideas.joueur2() != null) {
				Mideas.joueur2().setStamina(Mideas.joueur2().getMaxStamina());
			}
			Mideas.setJoueur1(joueur);
			CharacterStuff.getBagItems();
			SpellLevel.setSpellLevelFalse();
			Interface.setIsStuffEquipped(false);
			Interface.setIsShopLoaded(false);
			Interface.setIsStatsCalc(false);
			Interface.setIsChangeClassActive(true);
			return true;
		}
		return false;
	}
	
	private static void drawExpBar(int exp, int x, int y) throws FileNotFoundException {
		Color bgExpBarColor = new Color(0, 0, 0,.65f); 
		if(Mideas.getLevelAll(exp) == 70) {
			Draw.drawColorQuad(Display.getWidth()/2+x, Display.getHeight()+y, 220, 11, bgExpBarColor);
			Draw.drawColorQuad(Display.getWidth()/2+x, Display.getHeight()+y, 220, 11, Color.decode("#680764"));
		}
		else if(Mideas.getLevelAll(exp) > 1) {
			expClass = ((float)exp-(float)Mideas.getExpNeeded(Mideas.getLevelAll(exp)-1))/((float)Mideas.getExpNeeded(Mideas.getLevelAll(exp))-Mideas.getExpNeeded(Mideas.getLevelAll(exp)-1)); 
			Draw.drawColorQuad(Display.getWidth()/2+x, Display.getHeight()+y, 220, 11, bgExpBarColor);
			Draw.drawColorQuad(Display.getWidth()/2+x, Display.getHeight()+y, 220*expClass, 11, Color.decode("#680764"));
		}
		else {
			expClass = ((float)exp/Mideas.getExpNeeded(Mideas.getLevelAll(exp))); 
			Draw.drawColorQuad(Display.getWidth()/2+x, Display.getHeight()+y, 220, 11, bgExpBarColor);
			Draw.drawColorQuad(Display.getWidth()/2+x, Display.getHeight()+y, 220*expClass, 11, Color.decode("#680764"));
		}
	}
	
	private static void drawLevel(int exp, int x, int y) {
		TTF2.font5.drawStringShadow(Display.getWidth()/2+x, Display.getHeight()+y, String.valueOf(Mideas.getLevelAll(exp)), Color.white, Color.black, 1, 1, 1);
	}
	
	private static void drawBorder(int x, int y, int i) {
		if(i == 0) { 
			Draw.drawQuad(Sprites.border, Display.getWidth()/2+x, Display.getHeight()/2+y);	
		}
		else {
			Draw.drawQuad(Sprites.border2, Display.getWidth()/2+x, Display.getHeight()/2+y);	
		}
	}
	
	private static void drawFrameClass(Texture texture, int x, int y) {
		Draw.drawQuad(texture, Display.getWidth()/2+x, Display.getHeight()+y);
	}
	
	private static void drawFrameCreateClass(Texture texture, int x, int y) {
		Draw.drawQuad(texture, Display.getWidth()/2+x, Display.getHeight()/2+y);
	}
	
	public static void setHoverFalse() {
		warriorHover = false;
		paladinHover = false;
		hunterHover = false;
		rogueHover = false;
		priestHover = false;
		deathknightHover = false;
		shamanHover = false;
		mageHover = false;
		warlockHover = false;
		monkHover = false;
	}
}
	
