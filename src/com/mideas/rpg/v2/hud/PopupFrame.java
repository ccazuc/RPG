package com.mideas.rpg.v2.hud;

import org.lwjgl.opengl.Display;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.command.CommandFriend;
import com.mideas.rpg.v2.command.CommandGuild;
import com.mideas.rpg.v2.command.CommandIgnore;
import com.mideas.rpg.v2.command.CommandParty;
import com.mideas.rpg.v2.game.guild.Guild;
import com.mideas.rpg.v2.utils.Button;
import com.mideas.rpg.v2.utils.Popup;
import com.mideas.rpg.v2.utils.PopupInput;

public class PopupFrame {
	
	private final static int POPUP_INPUT_WIDTH = 370;
	static int id;
	static String name;
	private static Popup popup = new Popup(Display.getWidth()/2-240*Mideas.getDisplayXFactor(), Display.getHeight()/2-365*Mideas.getDisplayYFactor(), 480*Mideas.getDisplayXFactor(), 75*Mideas.getDisplayYFactor(), "Ceci est un test");
	static PopupInput popupInput = new PopupInput(Display.getWidth()/2-POPUP_INPUT_WIDTH/2*Mideas.getDisplayXFactor(), Display.getHeight()/2-365*Mideas.getDisplayYFactor(), POPUP_INPUT_WIDTH, 120*Mideas.getDisplayYFactor(), "Ceci est un test", 150);
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
	private static Button addFriendButton = new Button(0, 0, 0, 0, "Yes", 12, 1) {
		
		@Override
		public void eventButtonClick() {
			CommandFriend.addFriend(popupInput.getInput().getText());;
		}
	};
	private static Button addGuildMemberButton = new Button(0, 0, 0, 0, "Yes", 12, 1) {
		
		@Override
		public void eventButtonClick() {
			CommandGuild.addMember(popupInput.getInput().getText());
		}
	};
	private static Button setGuildMemberNoteButton = new Button(0, 0, 0, 0, "Yes", 12, 1) {
		
		@Override
		public void eventButtonClick() {
			CommandGuild.setMemberNote(id, popupInput.getInput().getText());
		}
	};
	private static Button setGuildMemberOfficerNoteButton = new Button(0, 0, 0, 0, "Yes", 12, 1) {
		
		@Override
		public void eventButtonClick() {
			CommandGuild.setMemberOfficerNote(id, popupInput.getInput().getText());
		}
	};
	private static Button addIgnoreButton = new Button(0, 0, 0, 0, "Yes", 12, 1) {
		
		@Override
		public void eventButtonClick() {
			CommandIgnore.addIgnore(popupInput.getInput().getText());
		}
	};
	
	public static void draw() {
		popup.draw();
		popupInput.draw();
	}
	
	public static boolean mouseEvent() {
		return popup.event() || popupInput.event();
	}
	
	public static boolean keyEvent() {
		return popupInput.keyEvent();
	}
	
	public static void activateAddFriendPopupInput() {
		popupInput.setPopup(addFriendButton, "Add Friend:", 170, 20);
		popupInput.setTextTypeAccept();
	}
	
	public static void activateAddIgnorePopupInput() {
		popupInput.setPopup(addIgnoreButton, "Ignore Player:", 170, 20);
		popupInput.setTextTypeAccept();
	}
	
	public static void activateAddGuildMemberPopupInput() {
		popupInput.setPopup(addGuildMemberButton, "Add Guild Member:", 170, 20);
		popupInput.setTextTypeAccept();
	}
	
	public static void activateSetGuildMemberNotePopupInput(int id, String text) {
		popupInput.setPopup(setGuildMemberNoteButton, "Write Player Note:", 430, Guild.MEMBER_NOTE_MAX_LENGTH);
		popupInput.setInputText(text);
		popupInput.setTextTypeAccept();
		PopupFrame.id = id;
	}
	
	public static void activateSetGuildMemberOfficerNotePopupInput(int id, String text) {
		popupInput.setPopup(setGuildMemberOfficerNoteButton, "Write Player Officer Note:", 430, Guild.MEMBER_OFFICER_NOTE_MAX_LENGTH);
		popupInput.setInputText(text);
		popupInput.setTextTypeAccept();
		PopupFrame.id = id;
	}
	
	public static void activateGuildKickMemberPopup(String name, int id) {
		popup.setPopup(guildKickMemberButton, "Do you want to kick "+name+" from the guild ?");
		popup.setTextTypeAccept();
		PopupFrame.id = id;
	}
	
	public static void activateGuildInvitationPopup(String name, String guildName) {
		popup.setPopup(guildInvitationButton, name+" wants to invite you in the guild: "+guildName);
		popup.setTextTypeAccept();
	}
	
	public static void activatePartyInvitationPopup(String name) {
		popup.setPopup(partyInvitationButton, name+" wants to invite you in a party.");
		popup.setTextTypeAccept();
	}
	
	public static void updateSize() {
		popup.update(Display.getWidth()/2-240*Mideas.getDisplayXFactor(), Display.getHeight()/2-365*Mideas.getDisplayYFactor(), 480*Mideas.getDisplayXFactor(), 75*Mideas.getDisplayYFactor());
		popupInput.update(Display.getHeight()/2-365*Mideas.getDisplayYFactor(), 120*Mideas.getDisplayYFactor());
	}
}
