package com.mideas.rpg.v2.hud;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.Sprites;
import com.mideas.rpg.v2.FontManager;
import com.mideas.rpg.v2.game.Unit;
import com.mideas.rpg.v2.utils.Color;
import com.mideas.rpg.v2.utils.Draw;

public class PlayerPortraitFrame {
	
	private final static Color backgroundColors = new Color(0, 0, 0, .4f);
	
	public static void draw(Unit joueur, float x, float y) {
		if(joueur.getHasHpChanged()) {
			joueur.setHealthText(joueur.getStamina()+" / "+joueur.getMaxStamina());
			joueur.setHasHpChanged(false);
		}
		if(joueur.getHasManaChanged()) {
			joueur.setManaText(joueur.getMana()+" / "+joueur.getMaxMana());
			joueur.setHasManaChanged(false);
		}
		Draw.drawColorQuad(x+70*Mideas.getDisplayXFactor(), y+15*Mideas.getDisplayYFactor(), 120*Mideas.getDisplayXFactor(), 18*Mideas.getDisplayXFactor(), backgroundColors);
		drawHealthBar(joueur, (int)x, (int)y);
		drawManaBar(joueur, (int)x, (int)y);
		Draw.drawQuad(Sprites.playerUI, (int)x, (int)y);
		Draw.drawQuad(Sprites.level, x, y+45);
		FontManager.get("FRIZQT", 12).drawStringShadow((int)(x+135*Mideas.getDisplayXFactor()-FontManager.get("FRIZQT", 12).getWidth(joueur.getName())/2), (int)(15*Mideas.getDisplayYFactor()+y), joueur.getName(), Color.YELLOW, Color.BLACK, 1, 1, 1);    
		FontManager.get("FRIZQT", 11).drawBegin();
		drawHealthText(joueur, (int)x, (int)y);
		drawManaText(joueur, (int)x, (int)y);
		FontManager.get("FRIZQT", 11).drawStringShadow(x+16-FontManager.get("FRIZQT", 11).getWidth(joueur.getLevelString())/2, y+55, joueur.getLevelString(), Color.YELLOW, Color.BLACK, 1, 0, 0);
		FontManager.get("FRIZQT", 11).drawEnd();
		drawPortait(joueur, (int)x, (int)y);
		if(Mideas.joueur1().getParty() != null && Mideas.joueur1().getParty().isPartyLeader(joueur)) {
			Draw.drawQuad(Sprites.party_leader_crown, x+5, y+8);
		}
	}
	
	private static void drawHealthBar(Unit joueur, int x, int y) {
		Draw.drawColorQuad(x+70*Mideas.getDisplayXFactor(), y+35*Mideas.getDisplayYFactor(), 120f*Mideas.getDisplayXFactor(), 12*Mideas.getDisplayXFactor(), backgroundColors);
		Draw.drawQuad(Sprites.life_bar, x+70*Mideas.getDisplayXFactor(), y+35*Mideas.getDisplayYFactor(), 120f*Mideas.getDisplayXFactor()*joueur.getStamina()/joueur.getMaxStamina(), 12*Mideas.getDisplayXFactor());
	}
	
	private static void drawManaBar(Unit joueur, int x, int y) {
		Draw.drawColorQuad(x+70*Mideas.getDisplayXFactor(), y+45*Mideas.getDisplayYFactor(), 119*Mideas.getDisplayXFactor(), 13,  backgroundColors);
		Draw.drawQuad(Sprites.mana_bar, x+70*Mideas.getDisplayXFactor(), y+45*Mideas.getDisplayYFactor(), 120f*Mideas.getDisplayXFactor()*joueur.getMana()/joueur.getMaxMana(), 12*Mideas.getDisplayXFactor());
	}
	
	private static void drawHealthText(Unit joueur, int x, int y) {
		for(int i=129;i<132;i++) {
			for(int j=32;j<35;j++) {
				FontManager.get("FRIZQT", 11).drawStringPart(x+(int)(i*Mideas.getDisplayXFactor()-FontManager.get("FRIZQT", 11).getWidth(joueur.getHealthText())/2), y+j*Mideas.getDisplayYFactor(), joueur.getHealthText(), Color.BLACK);
			}
		}
		FontManager.get("FRIZQT", 11).drawStringPart(x+(int)(130*Mideas.getDisplayXFactor())-FontManager.get("FRIZQT", 11).getWidth(joueur.getHealthText())/2, y+33*Mideas.getDisplayYFactor(), joueur.getHealthText(), Color.WHITE);
		//TTF2.hpAndMana.drawStringShadow(x+130*Mideas.getDisplayXFactor()-TTF2.hpAndMana.getWidth(joueur.getStamina()+" / "+joueur.getMaxStamina())/2, y+32*Mideas.getDisplayYFactor(), joueur.getStamina()+" / "+joueur.getMaxStamina(), Colors.WHITE, Colors.BLACK, 1, -1, -1);
	}
	
	private static void drawManaText(Unit joueur, int x, int y) {
		for(int i=129;i<132;i++) {
			for(int j=43;j<46;j++) {
				FontManager.get("FRIZQT", 11).drawStringPart(x+(int)(i*Mideas.getDisplayXFactor()-FontManager.get("FRIZQT", 11).getWidth(joueur.getManaText())/2), y+j*Mideas.getDisplayYFactor(), joueur.getManaText(), Color.BLACK);
			}
		}
		FontManager.get("FRIZQT", 11).drawStringPart(x+(int)(130*Mideas.getDisplayXFactor())-FontManager.get("FRIZQT", 11).getWidth(joueur.getManaText())/2, y+44*Mideas.getDisplayYFactor(), joueur.getManaText(), Color.WHITE);
		//TTF2.hpAndMana.drawStringShadow(x+130*Mideas.getDisplayXFactor()-TTF2.hpAndMana.getWidth(joueur.getMana()+" / "+joueur.getMaxMana())/2, y+44*Mideas.getDisplayYFactor(), joueur.getMana()+" / "+joueur.getMaxMana(), Colors.WHITE, Colors.BLACK, 1, -1, -1);
	}
	
	private static void drawPortait(Unit joueur, int x, int y) {
		Draw.drawQuad(joueur.getPortrait(), x+11*Mideas.getDisplayXFactor(), y+8*Mideas.getDisplayYFactor());
	}
}
