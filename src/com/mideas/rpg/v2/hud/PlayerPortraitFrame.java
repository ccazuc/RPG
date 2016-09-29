package com.mideas.rpg.v2.hud;

import org.newdawn.slick.Color;
import com.mideas.rpg.v2.utils.Texture;
import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.Sprites;
import com.mideas.rpg.v2.TTF2;
import com.mideas.rpg.v2.game.Joueur;
import com.mideas.rpg.v2.utils.Draw;

public class PlayerPortraitFrame {
	
	private static Color backgroundColor = new Color(0, 0, 0, .4f);
	private static String hp = "";
	private static String mana = "";

	private final static String deathKnight = "DeathKnight";
	private final static String warrior = "Guerrier";
	private final static String hunter = "Hunter";
	private final static String mage = "Mage";
	private final static String monk = "Monk";
	private final static String paladin = "Paladin";
	private final static String priest = "Priest";
	private final static String rogue = "Rogue";
	private final static String shaman = "Shaman";
	private final static String warlock = "Warlock";
	private final static String illidan = "Illidan";
	
	public static void draw(Joueur joueur, int x, int y) {
		if(joueur.getHasHpChanged()) {
			hp = joueur.getStamina()+" / "+joueur.getMaxStamina();
			joueur.setHasHpChanged(false);
		}
		if(joueur.getHasManaChanged()) {
			mana = joueur.getMana()+" / "+joueur.getMaxMana();
			joueur.setHasManaChanged(false);
		}
		Draw.drawColorQuad(x+70*Mideas.getDisplayXFactor(), y+15*Mideas.getDisplayYFactor(), 120*Mideas.getDisplayXFactor(), 18*Mideas.getDisplayXFactor(), backgroundColor);
		drawHealthBar(joueur, x, y);
		drawManaBar(joueur, x, y);
		Draw.drawQuad(Sprites.playerUI, x, y);
		TTF2.playerName.drawStringShadow((int)(x+135*Mideas.getDisplayXFactor()-TTF2.playerName.getWidth(joueur.getClasse())/2), (int)(15*Mideas.getDisplayYFactor()+y), joueur.getClasse(), Color.orange, Color.black, 1, 1, 1);    
		drawHealthText(joueur, x, y);
		drawManaText(joueur, x, y);
		drawPortait(joueur, x, y);
	}
	
	private static void drawHealthBar(Joueur joueur, int x, int y) {
		Draw.drawColorQuad(x+70*Mideas.getDisplayXFactor(), y+35*Mideas.getDisplayYFactor(), 120f*Mideas.getDisplayXFactor(), 12*Mideas.getDisplayXFactor(), backgroundColor);
		Draw.drawQuad(Sprites.life_bar, x+70*Mideas.getDisplayXFactor(), y+35*Mideas.getDisplayYFactor(), 120f*Mideas.getDisplayXFactor()*joueur.getStamina()/joueur.getMaxStamina(), 12*Mideas.getDisplayXFactor());
	}
	
	private static void drawManaBar(Joueur joueur, int x, int y) {
		Draw.drawColorQuad(x+70*Mideas.getDisplayXFactor(), y+45*Mideas.getDisplayYFactor(), 119*Mideas.getDisplayXFactor(), 13,  backgroundColor);
		Draw.drawQuad(Sprites.mana_bar, x+70*Mideas.getDisplayXFactor(), y+45*Mideas.getDisplayYFactor(), 120f*Mideas.getDisplayXFactor()*joueur.getMana()/joueur.getMaxMana(), 12*Mideas.getDisplayXFactor());
	}
	
	private static void drawHealthText(Joueur joueur, int x, int y) {
		for(int i=129;i<132;i++) {
			for(int j=32;j<35;j++) {
				TTF2.hpAndMana.drawString(x+(int)(i*Mideas.getDisplayXFactor()-TTF2.hpAndMana.getWidth(hp)/2), y+j*Mideas.getDisplayYFactor(), hp, Color.black);
			}
		}
		TTF2.hpAndMana.drawString(x+(int)(130*Mideas.getDisplayXFactor())-TTF2.hpAndMana.getWidth(hp)/2, y+33*Mideas.getDisplayYFactor(), hp, Color.white);
		//TTF2.hpAndMana.drawStringShadow(x+130*Mideas.getDisplayXFactor()-TTF2.hpAndMana.getWidth(joueur.getStamina()+" / "+joueur.getMaxStamina())/2, y+32*Mideas.getDisplayYFactor(), joueur.getStamina()+" / "+joueur.getMaxStamina(), Color.white, Color.black, 1, -1, -1);
	}
	
	private static void drawManaText(Joueur joueur, int x, int y) {
		for(int i=129;i<132;i++) {
			for(int j=43;j<46;j++) {
				TTF2.hpAndMana.drawString(x+(int)(i*Mideas.getDisplayXFactor()-TTF2.hpAndMana.getWidth(mana)/2), y+j*Mideas.getDisplayYFactor(), mana, Color.black);
			}
		}
		TTF2.hpAndMana.drawString(x+(int)(130*Mideas.getDisplayXFactor())-TTF2.hpAndMana.getWidth(mana)/2, y+44*Mideas.getDisplayYFactor(), mana, Color.white);
		//TTF2.hpAndMana.drawStringShadow(x+130*Mideas.getDisplayXFactor()-TTF2.hpAndMana.getWidth(joueur.getMana()+" / "+joueur.getMaxMana())/2, y+44*Mideas.getDisplayYFactor(), joueur.getMana()+" / "+joueur.getMaxMana(), Color.white, Color.black, 1, -1, -1);
	}
	
	private static void drawPortait(Joueur joueur, int x, int y) {
		Texture portrait = null;
		if(joueur.getClasse().equals(priest)) {
			portrait = Sprites.priest;
		}
		else if(joueur.getClasse().equals(mage)) {
			portrait = Sprites.mage;
		}
		else if(joueur.getClasse().equals(deathKnight)) {
			portrait = Sprites.deathknight;
		}
		else if(joueur.getClasse().equals(warrior)) {
			portrait = Sprites.war;
		}
		else if(joueur.getClasse().equals(hunter)) {
			portrait = Sprites.hunter;
		}
		else if(joueur.getClasse().equals(monk)) {
			portrait = Sprites.monk;
		}	
		else if(joueur.getClasse().equals(paladin)) {
			portrait = Sprites.paladin;
		}
		else if(joueur.getClasse().equals(rogue)) {
			portrait = Sprites.rogue;
		}
		else if(joueur.getClasse().equals(shaman)) {
			portrait = Sprites.shaman;
		}
		else if(joueur.getClasse().equals(warlock)) {
			portrait = Sprites.warlock;
		}
		else if(joueur.getClasse().equals(illidan)) {
			portrait = Sprites.illidan;
		}
		Draw.drawQuad(portrait, x+11, y+8);
	}
}
