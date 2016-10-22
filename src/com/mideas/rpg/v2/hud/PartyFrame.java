package com.mideas.rpg.v2.hud;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.Sprites;
import com.mideas.rpg.v2.TTF2;
import com.mideas.rpg.v2.command.CommandParty;
import com.mideas.rpg.v2.utils.Button;
import com.mideas.rpg.v2.utils.Draw;

public class PartyFrame {

	static boolean requestPending;
	private static String requestName;
	private static long requestReceivedTimer;
	private final static int MAXIMUM_TIMER = 30000;
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
				System.out.println("timed out");
			}
		}
		else if(Mideas.joueur1().getParty() != null) {
			int i = 0;
			while(i < Mideas.joueur1().getParty().getMemberList().length) {
				if(Mideas.joueur1().getParty().getPartyMember(i) != null) {
					PlayerPortraitFrame.draw(Mideas.joueur1().getParty().getPartyMember(i), 50, Display.getHeight()/2);
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
	
	public static void setRequestPending(boolean we) {
		requestPending = we;
		requestReceivedTimer = System.currentTimeMillis();
	}
	
	public static void setRequestName(String name) {
		requestName = name;
	}
}
