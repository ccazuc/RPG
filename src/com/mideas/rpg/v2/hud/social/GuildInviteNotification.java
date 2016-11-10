package com.mideas.rpg.v2.hud.social;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.TTF2;
import com.mideas.rpg.v2.command.CommandGuild;
import com.mideas.rpg.v2.utils.AlertBackground;
import com.mideas.rpg.v2.utils.Button;

public class GuildInviteNotification {
	
	static boolean requestPending;
	private static String playerName;
	private static String guildName;
	private static AlertBackground memberTooltip = new AlertBackground(Display.getWidth()/2-250*Mideas.getDisplayXFactor(), Display.getHeight()/2-250*Mideas.getDisplayYFactor(), 500*Mideas.getDisplayXFactor(), 110*Mideas.getDisplayYFactor(), 0.6f);
	private static Button acceptRequest = new Button(Display.getWidth()/2-200*Mideas.getDisplayXFactor(), Display.getHeight()/2-190*Mideas.getDisplayYFactor(), 180*Mideas.getDisplayXFactor(), 25*Mideas.getDisplayXFactor(), "Accept", 15, 2) {
		@Override
		public void eventButtonClick() {
			CommandGuild.acceptRequest();
			requestPending = false;
			this.reset();
		}
	};
	private static Button declineRequest = new Button(Display.getWidth()/2+20*Mideas.getDisplayXFactor(), Display.getHeight()/2-190*Mideas.getDisplayYFactor(), 180*Mideas.getDisplayXFactor(), 25*Mideas.getDisplayXFactor(), "Decline", 15, 2) {
		@Override
		public void eventButtonClick() {
			CommandGuild.declineRequest();
			requestPending = false;
			this.reset();
		}
	};
	
	public static void draw() {
		if(requestPending) {
			memberTooltip.draw();
			TTF2.font4.drawStringShadow(Display.getWidth()/2-(TTF2.font4.getWidth(playerName+" wants to invite you in the guild : "+guildName)*Mideas.getDisplayXFactor())/2, Display.getHeight()/2-230*Mideas.getDisplayYFactor(), playerName+" wants to invite you in the guild : "+guildName, Color.white, Color.black, 1, Mideas.getDisplayXFactor(), Mideas.getDisplayXFactor());
			acceptRequest.draw();
			declineRequest.draw();
		}
	}
	
	public static boolean mouseEvent() {
		if(requestPending) {
			if(acceptRequest.event()) return true;
			if(declineRequest.event()) return true;
		}
		return false;
	}
	
	public static void setRequest(String player_name, String guild_name) {
		requestPending = true;
		playerName = player_name;
		guildName = guild_name;
	}
}
