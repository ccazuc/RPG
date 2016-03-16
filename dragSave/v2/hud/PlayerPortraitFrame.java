package com.mideas.rpg.v2.hud;

import java.io.FileNotFoundException;

import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;

import com.mideas.rpg.v2.Sprites;
import com.mideas.rpg.v2.TTF2;
import com.mideas.rpg.v2.game.Joueur;
import com.mideas.rpg.v2.utils.Draw;

public class PlayerPortraitFrame {
	
	public static void draw(Joueur joueur, int x, int y) throws FileNotFoundException {
		Color backgroundColor = new Color(0, 0, 0,.4f);
		Draw.drawColorQuad(x+70, y+15, 119, 18, backgroundColor);
		drawHealthBar(joueur, x, y);
		drawManaBar(joueur, x, y);
		Draw.drawQuad(Sprites.playerUI, x, y);
		TTF2.playerName.drawStringShadow(x+135-TTF2.playerName.getWidth(joueur.getClasse())/2, 15+y, joueur.getClasse(), Color.orange, Color.black, 1, 1, 1);    
		drawHealthText(joueur, x, y);
		drawManaText(joueur, x, y);
		drawPortait(joueur, x, y);
	}
	
	private static void drawHealthBar(Joueur joueur, int x, int y) {
		Draw.drawColorQuad(x+70, y+35, 120, 13, new Color(0, 0, 0,.4f));
		Draw.drawColorQuad(x+70, y+35, 120f*joueur.getStamina()/joueur.getMaxStamina(), 12, Color.decode("#07D705"));
	}
	
	private static void drawManaBar(Joueur joueur, int x, int y) {
		Draw.drawColorQuad(x+70, y+45, 119, 13,  new Color(0, 0, 0,.4f));
		Draw.drawColorQuad(x+70, y+45, 119f*joueur.getMana()/joueur.getMaxMana(), 12, Color.decode("#0101D5"));
	}
	
	private static void drawHealthText(Joueur joueur, int x, int y) {
		String stamina = joueur.getStamina()+" / "+joueur.getMaxStamina();
		for(int i=129;i<132;i++) {
			for(int j=32;j<35;j++) {
				TTF2.hpAndMana.drawString(x+i-TTF2.hpAndMana.getWidth(stamina)/2, y+j, stamina, Color.black);
			}
		}
		TTF2.hpAndMana.drawString(x+130-TTF2.hpAndMana.getWidth(stamina)/2, y+33, stamina, Color.white);  
	}
	
	private static void drawManaText(Joueur joueur, int x, int y) {
		String mana = joueur.getMana()+" / "+joueur.getMaxMana();
		for(int i=129;i<132;i++) {
			for(int j=43;j<46;j++) {
				TTF2.hpAndMana.drawString(x+i-TTF2.hpAndMana.getWidth(mana)/2, y+j, mana, Color.black);
			}
		}
		TTF2.hpAndMana.drawString(x+130-TTF2.hpAndMana.getWidth(mana)/2, y+44, mana, Color.white);
	}
	
	private static void drawPortait(Joueur joueur, int x, int y) {
		Texture portrait = null;
		if(joueur.getClasse() == "Priest") {
			portrait = Sprites.priest;
		}
		else if(joueur.getClasse() == "Mage") {
			portrait = Sprites.mage;
		}
		else if(joueur.getClasse() == "DeathKnight") {
			portrait = Sprites.deathknight;
		}
		else if(joueur.getClasse() == "Guerrier") {
			portrait = Sprites.war;
		}
		else if(joueur.getClasse() == "Hunter") {
			portrait = Sprites.hunter;
		}
		else if(joueur.getClasse() == "Monk") {
			portrait = Sprites.monk;
		}	
		else if(joueur.getClasse() == "Paladin") {
			portrait = Sprites.paladin;
		}
		else if(joueur.getClasse() == "Rogue") {
			portrait = Sprites.rogue;
		}
		else if(joueur.getClasse() == "Shaman") {
			portrait = Sprites.shaman;
		}
		else if(joueur.getClasse() == "Warlock") {
			portrait = Sprites.warlock;
		}
		else if(joueur.getClasse() == "Illidan") {
			portrait = Sprites.illidan;
		}
		Draw.drawQuad(portrait, x+11, y+8);
	}
}
