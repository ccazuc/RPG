package com.mideas.rpg.v2.hud;

import org.newdawn.slick.Color;
import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.Sprites;
import com.mideas.rpg.v2.TTF2;
import com.mideas.rpg.v2.game.Unit;
import com.mideas.rpg.v2.utils.Draw;

public class PlayerPortraitFrame {
	
	private final static Color backgroundColor = new Color(0, 0, 0, .4f);
	private final static Color YELLOW = Color.decode("#F0CE0C");
	
	public static void draw(Unit joueur, int x, int y) {
		if(joueur.getHasHpChanged()) {
			joueur.setHealthText(joueur.getStamina()+" / "+joueur.getMaxStamina());
			joueur.setHasHpChanged(false);
		}
		if(joueur.getHasManaChanged()) {
			joueur.setManaText(joueur.getMana()+" / "+joueur.getMaxMana());
			joueur.setHasManaChanged(false);
		}
		Draw.drawColorQuad(x+70*Mideas.getDisplayXFactor(), y+15*Mideas.getDisplayYFactor(), 120*Mideas.getDisplayXFactor(), 18*Mideas.getDisplayXFactor(), backgroundColor);
		drawHealthBar(joueur, x, y);
		drawManaBar(joueur, x, y);
		Draw.drawQuad(Sprites.playerUI, x, y);
		TTF2.playerName.drawStringShadow((int)(x+135*Mideas.getDisplayXFactor()-TTF2.playerName.getWidth(joueur.getName())/2), (int)(15*Mideas.getDisplayYFactor()+y), joueur.getName(), Color.orange, Color.black, 1, 1, 1);    
		drawHealthText(joueur, x, y);
		drawManaText(joueur, x, y);
		drawPortait(joueur, x, y);
		Draw.drawQuad(Sprites.level, x, y+45);
		TTF2.hpAndMana.drawStringShadow(x+16-TTF2.hpAndMana.getWidth(String.valueOf(joueur.getLevel()))/2, y+55, String.valueOf(joueur.getLevel()), YELLOW, Color.black, 1, 0, 0);
		if(Mideas.joueur1().getParty() != null && Mideas.joueur1().getParty().isPartyLeader(joueur)) {
			Draw.drawQuad(Sprites.party_leader_crown, x+5, y+8);
		}
	}
	
	private static void drawHealthBar(Unit joueur, int x, int y) {
		Draw.drawColorQuad(x+70*Mideas.getDisplayXFactor(), y+35*Mideas.getDisplayYFactor(), 120f*Mideas.getDisplayXFactor(), 12*Mideas.getDisplayXFactor(), backgroundColor);
		Draw.drawQuad(Sprites.life_bar, x+70*Mideas.getDisplayXFactor(), y+35*Mideas.getDisplayYFactor(), 120f*Mideas.getDisplayXFactor()*joueur.getStamina()/joueur.getMaxStamina(), 12*Mideas.getDisplayXFactor());
	}
	
	private static void drawManaBar(Unit joueur, int x, int y) {
		Draw.drawColorQuad(x+70*Mideas.getDisplayXFactor(), y+45*Mideas.getDisplayYFactor(), 119*Mideas.getDisplayXFactor(), 13,  backgroundColor);
		Draw.drawQuad(Sprites.mana_bar, x+70*Mideas.getDisplayXFactor(), y+45*Mideas.getDisplayYFactor(), 120f*Mideas.getDisplayXFactor()*joueur.getMana()/joueur.getMaxMana(), 12*Mideas.getDisplayXFactor());
	}
	
	private static void drawHealthText(Unit joueur, int x, int y) {
		for(int i=129;i<132;i++) {
			for(int j=32;j<35;j++) {
				TTF2.hpAndMana.drawString(x+(int)(i*Mideas.getDisplayXFactor()-TTF2.hpAndMana.getWidth(joueur.getHealthText())/2), y+j*Mideas.getDisplayYFactor(), joueur.getHealthText(), Color.black);
			}
		}
		TTF2.hpAndMana.drawString(x+(int)(130*Mideas.getDisplayXFactor())-TTF2.hpAndMana.getWidth(joueur.getHealthText())/2, y+33*Mideas.getDisplayYFactor(), joueur.getHealthText(), Color.white);
		//TTF2.hpAndMana.drawStringShadow(x+130*Mideas.getDisplayXFactor()-TTF2.hpAndMana.getWidth(joueur.getStamina()+" / "+joueur.getMaxStamina())/2, y+32*Mideas.getDisplayYFactor(), joueur.getStamina()+" / "+joueur.getMaxStamina(), Color.white, Color.black, 1, -1, -1);
	}
	
	private static void drawManaText(Unit joueur, int x, int y) {
		for(int i=129;i<132;i++) {
			for(int j=43;j<46;j++) {
				TTF2.hpAndMana.drawString(x+(int)(i*Mideas.getDisplayXFactor()-TTF2.hpAndMana.getWidth(joueur.getManaText())/2), y+j*Mideas.getDisplayYFactor(), joueur.getManaText(), Color.black);
			}
		}
		TTF2.hpAndMana.drawString(x+(int)(130*Mideas.getDisplayXFactor())-TTF2.hpAndMana.getWidth(joueur.getManaText())/2, y+44*Mideas.getDisplayYFactor(), joueur.getManaText(), Color.white);
		//TTF2.hpAndMana.drawStringShadow(x+130*Mideas.getDisplayXFactor()-TTF2.hpAndMana.getWidth(joueur.getMana()+" / "+joueur.getMaxMana())/2, y+44*Mideas.getDisplayYFactor(), joueur.getMana()+" / "+joueur.getMaxMana(), Color.white, Color.black, 1, -1, -1);
	}
	
	private static void drawPortait(Unit joueur, int x, int y) {
		Draw.drawQuad(joueur.getPortrait(), x+11*Mideas.getDisplayXFactor(), y+8*Mideas.getDisplayYFactor());
	}
}
