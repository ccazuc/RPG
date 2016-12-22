package com.mideas.rpg.v2.hud;

import org.lwjgl.opengl.Display;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.command.CommandFriend;
import com.mideas.rpg.v2.command.CommandGuild;
import com.mideas.rpg.v2.command.CommandIgnore;
import com.mideas.rpg.v2.command.CommandParty;
import com.mideas.rpg.v2.command.CommandTrade;
import com.mideas.rpg.v2.game.PopupType;
import com.mideas.rpg.v2.game.guild.Guild;
import com.mideas.rpg.v2.utils.Button;
import com.mideas.rpg.v2.utils.Popup;
import com.mideas.rpg.v2.utils.PopupInput;

public class PopupFrame {
	
	static PopupType currentPopup;
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
		
		@Override
		public void popupClosed() {
			currentPopup = PopupType.NONE;
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
			currentPopup = PopupType.NONE;
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
			currentPopup = PopupType.NONE;
		}
	};
	private static Button addFriendButton = new Button(0, 0, 0, 0, "Yes", 12, 1) {
		
		@Override
		public void eventButtonClick() {
			CommandFriend.addFriend(popupInput.getInput().getText());
		}
		
		@Override
		public void popupClosed() {
			CommandParty.declineRequest();
			currentPopup = PopupType.NONE;
		}
	};
	private static Button addGuildMemberButton = new Button(0, 0, 0, 0, "Yes", 12, 1) {
		
		@Override
		public void eventButtonClick() {
			CommandGuild.addMember(popupInput.getInput().getText());
		}
		
		@Override
		public void popupClosed() {
			CommandParty.declineRequest();
			currentPopup = PopupType.NONE;
		}
	};
	private static Button setGuildMemberNoteButton = new Button(0, 0, 0, 0, "Yes", 12, 1) {
		
		@Override
		public void eventButtonClick() {
			CommandGuild.setMemberNote(id, popupInput.getInput().getText());
		}
		
		@Override
		public void popupClosed() {
			CommandParty.declineRequest();
			currentPopup = PopupType.NONE;
		}
	};
	private static Button setGuildMemberOfficerNoteButton = new Button(0, 0, 0, 0, "Yes", 12, 1) {
		
		@Override
		public void eventButtonClick() {
			CommandGuild.setMemberOfficerNote(id, popupInput.getInput().getText());
		}
		
		@Override
		public void popupClosed() {
			CommandParty.declineRequest();
			currentPopup = PopupType.NONE;
		}
	};
	private static Button addIgnoreButton = new Button(0, 0, 0, 0, "Yes", 12, 1) {
		
		@Override
		public void eventButtonClick() {
			CommandIgnore.addIgnore(popupInput.getInput().getText());
		}
		
		@Override
		public void popupClosed() {
			CommandParty.declineRequest();
			currentPopup = PopupType.NONE;
		}
	};
	private static Button acceptTradeButton = new Button(0, 0, 0, 0, "Yes", 12, 1) {
		
		@Override
		public void eventButtonClick() {
			CommandTrade.writeAccept();
		}
		
		@Override
		public void popupClosed() {
			CommandTrade.writeCloseTrade();
			currentPopup = PopupType.NONE;
		}
	};
	private static Button deleteItemWithoutConfirmButton = new Button(0, 0, 0, 0, "Yes", 12, 1) {
		
		@Override
		public void eventButtonClick() {
			DragManager.deleteItem(DragManager.getDraggedItem());
		}
		
		@Override
		public void popupClosed() {
			DragManager.setDraggedItem(null);
			currentPopup = PopupType.NONE;
		}
	};
	private static Button deleteItemWithConfirmButton = new Button(0, 0, 0, 0, "Yes", 12, 1) {
		
		@Override
		public void eventButtonClick() {
			DragManager.deleteItem(DragManager.getDraggedItem());
		}
		
		@Override
		public void popupClosed() {
			DragManager.setDraggedItem(null);
			currentPopup = PopupType.NONE;
		}
		
		@Override
		public boolean activateCondition() {
			return popupInput.getInput().getText().equals("DELETE");
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
		currentPopup = PopupType.ADD_FRIEND;
		popupInput.setPopup(addFriendButton, "Add Friend:", 170, 20);
		popupInput.setTextTypeAccept();
	}
	
	public static void activateAddIgnorePopupInput() {
		currentPopup = PopupType.ADD_IGNORE;
		popupInput.setPopup(addIgnoreButton, "Ignore Player:", 170, 20);
		popupInput.setTextTypeAccept();
	}
	
	public static void activateAddGuildMemberPopupInput() {
		currentPopup = PopupType.ADD_GUILD;
		popupInput.setPopup(addGuildMemberButton, "Add Guild Member:", 170, 20);
		popupInput.setTextTypeAccept();
	}
	
	public static void activateSetGuildMemberNotePopupInput(int id, String text) {
		currentPopup = PopupType.GUILD_SET_MEMBER_PUBLIC_NOTE;
		popupInput.setPopup(setGuildMemberNoteButton, "Write Player Note:", 430, Guild.MEMBER_NOTE_MAX_LENGTH);
		popupInput.setInputText(text);
		popupInput.setTextTypeAccept();
		PopupFrame.id = id;
	}
	
	public static void activateSetGuildMemberOfficerNotePopupInput(int id, String text) {
		currentPopup = PopupType.GUILD_SET_MEMBER_OFFICER_NOTE;
		popupInput.setPopup(setGuildMemberOfficerNoteButton, "Write Player Officer Note:", 430, Guild.MEMBER_OFFICER_NOTE_MAX_LENGTH);
		popupInput.setInputText(text);
		popupInput.setTextTypeAccept();
		PopupFrame.id = id;
	}
	
	public static void activateGuildKickMemberPopup(String name, int id) {
		currentPopup = PopupType.GUILD_KICK_MEMBER;
		popup.setPopup(guildKickMemberButton, "Do you want to kick "+name+" from the guild ?");
		popup.setTextTypeAccept();
		PopupFrame.id = id;
	}
	
	public static void activateGuildInvitationPopup(String name, String guildName) {
		currentPopup = PopupType.GUILD_INVITATION;
		popup.setPopup(guildInvitationButton, name+" wants to invite you in the guild: "+guildName);
		popup.setTextTypeAccept();
	}
	
	public static void activatePartyInvitationPopup(String name) {
		currentPopup = PopupType.PARTY_INVITATION;
		popup.setPopup(partyInvitationButton, name+" wants to invite you in a party.");
		popup.setTextTypeAccept();
	}
	
	public static void activateTradePopup() {
		currentPopup = PopupType.TRADE_REQUEST;
		popup.setPopup(acceptTradeButton, name+" wants to trade with you.");
		popup.setTextTypeAccept();
	}
	
	public static void activateDeleteItemWithoutConfirm(String itemName) {
		currentPopup = PopupType.DELETE_ITEM;
		popup.setPopup(deleteItemWithoutConfirmButton, "Do you want to delete "+itemName+" ?");
		popup.setTextTypeYes();
	}
	
	public static void activateDeleteItemWithConfirm(String itemName) {
		currentPopup = PopupType.DELETE_ITEM;
		popup.setPopup(deleteItemWithConfirmButton, "Do you want to delete "+itemName+" ?");
		popup.setTextTypeYes();
	}
	
	
	public static void closePopup() {
		currentPopup = PopupType.NONE;
		popup.setActive(false);
	}
	
	public static PopupType getCurrentPopup() {
		return currentPopup;
	}
	
	public static void updateSize() {
		popup.update(Display.getWidth()/2-240*Mideas.getDisplayXFactor(), Display.getHeight()/2-365*Mideas.getDisplayYFactor(), 480*Mideas.getDisplayXFactor(), 75*Mideas.getDisplayYFactor());
		popupInput.update(Display.getHeight()/2-365*Mideas.getDisplayYFactor(), 120*Mideas.getDisplayYFactor());
	}
}
