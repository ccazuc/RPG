package com.mideas.rpg.v2.hud;

import org.lwjgl.opengl.Display;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.command.CommandGuild;
import com.mideas.rpg.v2.command.CommandParty;
import com.mideas.rpg.v2.utils.Button;
import com.mideas.rpg.v2.utils.Popup;

public class PopupFrame {
	
	static int id;
	private static Popup popup = new Popup(Display.getWidth()/2-240*Mideas.getDisplayXFactor(), Display.getHeight()/2-365*Mideas.getDisplayYFactor(), 480*Mideas.getDisplayXFactor(), 75*Mideas.getDisplayYFactor(), "Ceci est un test");
	private static Button guildKickMemberButton = new Button(0, 0, 0, 0, "Yes", 12, 1) {
		
		@Override
		public void eventButtonClick() {
			CommandGuild.kickMember(id);
		}
	};
	private static Button guildInvitationButton = new Button(0, 0, 0, 0, "Yes", 12, 1) {
		
		@Override
		public void eventButtonClick() {
			CommandGuild.acceptRequest();
		}
		
		@Override
		public void popupClosed() {
			CommandGuild.declineRequest();
		}
	};
	private static Button partyInvitationButton = new Button(0, 0, 0, 0, "Yes", 12, 1) {
		
		@Override
		public void eventButtonClick() {
			CommandParty.acceptRequest();
		}
		
		@Override
		public void popupClosed() {
			CommandParty.declineRequest();
		}
	};
	
	public static void draw() {
		if(popup.isActive()) {
			popup.draw();
		}
	}
	
	public static boolean mouseEvent() {
		if(popup.isActive()) {
			return popup.event();
		}
		return false;
	}
	
	public static void activateGuildKickMemberPopup(String name, int id) {
		popup.setPopup(guildKickMemberButton, "Do you want to kick "+name+" from the guild ?");
		popup.setTextTypeAccept();
		PopupFrame.id = id;
	}
	
	public static void activateGuildInvitationPopup(String name, String guildName) {
		popup.setPopup(guildInvitationButton, name+" wants to invite you in the guild: "+guildName);
		popup.setTextTypeYes();
	}
	
	public static void activatePartyInvitationPopup(String name) {
		popup.setPopup(partyInvitationButton, name+" wants to invite you in a party.");
		popup.setTextTypeAccept();
	}
	
	public static void updateSize() {
		popup.update(Display.getWidth()/2-240*Mideas.getDisplayXFactor(), Display.getHeight()/2-365*Mideas.getDisplayYFactor(), 480*Mideas.getDisplayXFactor(), 75*Mideas.getDisplayYFactor());
	}
}
