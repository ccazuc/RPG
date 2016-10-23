package com.mideas.rpg.v2.hud;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.Sprites;
import com.mideas.rpg.v2.TTF2;
import com.mideas.rpg.v2.command.CommandParty;
import com.mideas.rpg.v2.game.Unit;
import com.mideas.rpg.v2.utils.Button;
import com.mideas.rpg.v2.utils.Draw;

public class PartyFrame {

	static boolean requestPending;
	private static String requestName;
	private static long requestReceivedTimer;
	private final static int MAXIMUM_TIMER = 30000;
	private final static Color backgroundColor = new Color(0, 0, 0, .4f);
	private final static Color YELLOW = Color.decode("#F0CE0C");
	private static Button acceptRequest = new Button(Display.getWidth()/2-200*Mideas.getDisplayXFactor(), Display.getHeight()/2-190*Mideas.getDisplayYFactor(), 180*Mideas.getDisplayXFactor(), 25*Mideas.getDisplayXFactor(), "Accept", 15, 2) {
		@Override
		public void eventButtonClick() {
			CommandParty.acceptRequest();
			requestPending = false;
		}
	};
	private static Button declineRequest = new Button(Display.getWidth()/2+20*Mideas.getDisplayXFactor(), Display.getHeight()/2-190*Mideas.getDisplayYFactor(), 180*Mideas.getDisplayXFactor(), 25*Mideas.getDisplayXFactor(), "Decline", 15, 2) {
		@Override
		public void eventButtonClick() {
			CommandParty.declineRequest();
			requestPending = false;
		}
	};
	
	public static void draw() {
		if(requestPending) {
			if(System.currentTimeMillis()-requestReceivedTimer <= MAXIMUM_TIMER) {
				Draw.drawQuad(Sprites.alert, Display.getWidth()/2-250*Mideas.getDisplayXFactor(), Display.getHeight()/2-250*Mideas.getDisplayYFactor(), 500*Mideas.getDisplayXFactor(), 110*Mideas.getDisplayYFactor());
				TTF2.font4.drawStringShadow(Display.getWidth()/2-(TTF2.font4.getWidth(requestName+" wants to invite you in a party.")*Mideas.getDisplayXFactor())/2, Display.getHeight()/2-230*Mideas.getDisplayYFactor(), requestName+" wants to invite you in a party.", Color.white, Color.black, 1, Mideas.getDisplayXFactor(), Mideas.getDisplayXFactor());
				acceptRequest.draw();
				declineRequest.draw();
			}
			else {
				requestPending = false;
				CommandParty.declineRequest();
			}
		}
		else if(Mideas.joueur1().getParty() != null) {
			float y = Display.getHeight()/2-300*Mideas.getDisplayYFactor();
			float yShift = 60*Mideas.getDisplayYFactor();
			int i = 0;
			while(i < Mideas.joueur1().getParty().getMemberList().length) {
				if(Mideas.joueur1().getParty().getPartyMember(i) != null) {
					drawPortraitFrame(Mideas.joueur1().getParty().getPartyMember(i), 50, y+i*yShift);
				}
				i++;
			}
		}
	}
	
	public static boolean mouseEvent() {
		if(requestPending) {
			acceptRequest.event();
			declineRequest.event();
		}
		return false;
	}
	
	private static void drawPortraitFrame(Unit unit, float x, float y) {
		if(unit.getHasHpChanged()) {
			unit.setHealthText(unit.getStamina()+" / "+unit.getMaxStamina());
			unit.setHasHpChanged(false);
		}
		if(unit.getHasManaChanged()) {
			unit.setManaText(unit.getMana()+" / "+unit.getMaxMana());
			unit.setHasManaChanged(false);
		}
		Draw.drawColorQuad(x+45*Mideas.getDisplayXFactor(), y+13*Mideas.getDisplayYFactor(), 96*Mideas.getDisplayXFactor(), 30*Mideas.getDisplayYFactor(), backgroundColor);
		Draw.drawQuad(Sprites.life_bar, x+54*Mideas.getDisplayXFactor(), y+28*Mideas.getDisplayYFactor(), 88*Mideas.getDisplayXFactor()/*unit.getStamina()/unit.getMaxStamina()*/, 8*Mideas.getDisplayYFactor());
		Draw.drawQuad(Sprites.mana_bar, x+54*Mideas.getDisplayXFactor(), y+35*Mideas.getDisplayYFactor(), 88*unit.getMana()/unit.getMaxMana(), 8*Mideas.getDisplayYFactor());
		Draw.drawQuad(unit.getPortrait(), x+6*Mideas.getDisplayXFactor(), y+5*Mideas.getDisplayYFactor(), 45*Mideas.getDisplayXFactor(), 45*Mideas.getDisplayYFactor());
		//if(System.currentTimeMillis()%3 == 0)
		Draw.drawQuad(Sprites.player_portrait_party, x, y);
		TTF2.portraitPartyName.drawStringShadow(x+100*Mideas.getDisplayXFactor()-TTF2.portraitPartyName.getWidth(unit.getName())/2, y+11*Mideas.getDisplayYFactor(), unit.getName(), YELLOW, Color.black, 1, 0, 0);
		TTF2.portraitPartyStats.drawStringShadow(x+100*Mideas.getDisplayXFactor()-TTF2.portraitPartyStats.getWidth(unit.getHealthText())/2, y+26*Mideas.getDisplayYFactor(), unit.getHealthText(), Color.white, Color.black, 1, 0, 0);
		TTF2.portraitPartyStats.drawStringShadow(x+100*Mideas.getDisplayXFactor()-TTF2.portraitPartyStats.getWidth(unit.getManaText())/2, y+33*Mideas.getDisplayYFactor(), unit.getManaText(), Color.white, Color.black, 1, 0, 0);
		if(Mideas.joueur1().getParty().isPartyLeader(unit)) {
			Draw.drawQuad(Sprites.party_leader_crown, x+5, y+8);
		}
	}
	
	public static void setRequestPending(boolean we) {
		requestPending = we;
		requestReceivedTimer = System.currentTimeMillis();
	}
	
	public static void setRequestName(String name) {
		requestName = name;
	}
}
