package com.mideas.rpg.v2.hud.social;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;

import com.mideas.rpg.v2.Interface;
import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.Sprites;
import com.mideas.rpg.v2.TTF2;
import com.mideas.rpg.v2.chat.ChatFrame;
import com.mideas.rpg.v2.command.CommandFriend;
import com.mideas.rpg.v2.command.CommandParty;
import com.mideas.rpg.v2.game.Friend;
import com.mideas.rpg.v2.utils.Button;
import com.mideas.rpg.v2.utils.Draw;

public class FriendsFrame {

	private final static Color YELLOW = Color.decode("#FFC700");
	private final static Color GREY = Color.decode("#999999");
	static Friend selectedFriend;
	private static int hoveredFriend = -1;
	private static int leftButtonDownFriend = -1;
	private static boolean friendInit;
	private final static int BUTTON_LENGTH = 145;
	private final static int BUTTON_HEIGHT = 25;
	private static Button deleteFriendButton = new Button(27*Mideas.getDisplayXFactor(), Display.getHeight()/2+79*Mideas.getDisplayYFactor(), BUTTON_LENGTH*Mideas.getDisplayXFactor(), BUTTON_HEIGHT*Mideas.getDisplayXFactor(), "Delete", 12, 1) {
		@Override
		public void eventButtonClick() {
			CommandFriend.removeFriend(selectedFriend.getCharacterId());
			Mideas.joueur1().getFriendList().remove(selectedFriend);
			if(Mideas.joueur1().getFriendList().size() > 0) {
				selectedFriend = Mideas.joueur1().getFriendList().get(0);
			}
			else {
				selectedFriend = null;
			}
		}
		
		@Override
		public boolean activateCondition() {
			return selectedFriend != null;
		}
	};
	private static Button addFriendButton = new Button(27*Mideas.getDisplayXFactor(), Display.getHeight()/2+52*Mideas.getDisplayYFactor(), BUTTON_LENGTH*Mideas.getDisplayXFactor(), BUTTON_HEIGHT*Mideas.getDisplayXFactor(), "Add friend", 12, 1) {
		@Override
		public void eventButtonClick() {
			Interface.setAddFriendStatus(true);
		}
	};
	private static Button sendMessageFriendButton = new Button(251*Mideas.getDisplayXFactor(), Display.getHeight()/2+52*Mideas.getDisplayYFactor(), BUTTON_LENGTH*Mideas.getDisplayXFactor(), BUTTON_HEIGHT*Mideas.getDisplayXFactor(), "Send message", 12, 1) {
		@Override
		public void eventButtonClick() {
			ChatFrame.setWhisper(selectedFriend.getName());
			ChatFrame.setChatActive(true);
		}
		
		@Override
		public boolean activateCondition() {
			return selectedFriend != null && selectedFriend.isOnline();
		}
	};
	private static Button invInParty = new Button(251*Mideas.getDisplayXFactor(), Display.getHeight()/2+79*Mideas.getDisplayYFactor(), BUTTON_LENGTH*Mideas.getDisplayXFactor(), BUTTON_HEIGHT*Mideas.getDisplayXFactor(), "Inv. in party", 12 , 1) {
		@Override
		public void eventButtonClick() {
			CommandParty.invitePlayer(selectedFriend.getName());
		}
		
		@Override
		public boolean activateCondition() {
			return selectedFriend != null && selectedFriend.isOnline();
		}
	};

	
	public static void draw() {
		if(!friendInit && Mideas.joueur1().getFriendList().size() > 0) {
			selectedFriend = Mideas.joueur1().getFriendList().get(0);
			friendInit = true;
		}
		Draw.drawQuad(Sprites.friend_frame, 10*Mideas.getDisplayXFactor(), 150*Mideas.getDisplayYFactor());
		int i = 0;
		Friend friend;
		float y = 230*Mideas.getDisplayYFactor();
		float yShift = 32*Mideas.getDisplayYFactor();
		while(i < Mideas.joueur1().getFriendList().size()) {
			friend = Mideas.joueur1().getFriendList().get(i);
			if(selectedFriend == friend || hoveredFriend == i) {
				Draw.drawQuad(Sprites.friend_border, 30*Mideas.getDisplayXFactor(), y, 343*Mideas.getDisplayXFactor(), 34*Mideas.getDisplayYFactor());
			}
			if(friend.isOnline()) {
				Draw.drawQuad(Sprites.friend_note_online, 40*Mideas.getDisplayXFactor(), y+5);
				TTF2.friendName.drawStringShadow(50*Mideas.getDisplayXFactor(), y, friend.getName(), YELLOW, Color.black, 1, 0, 0);
				TTF2.friendName.drawStringShadow(50*Mideas.getDisplayXFactor()+TTF2.friendName.getWidth(friend.getName()), y, friend.getAreaText(), Color.white, Color.black, 1, 0, 0);
				TTF2.friendInfos.drawStringShadow(50*Mideas.getDisplayXFactor(), y+18*Mideas.getDisplayYFactor(), friend.getInfosText(), Color.white, Color.black, 1, 0, 0);
			}
			else {
				Draw.drawQuad(Sprites.friend_note_offline, 40*Mideas.getDisplayXFactor(), y+5);
				TTF2.friendName.drawStringShadow(50*Mideas.getDisplayXFactor(), y, friend.getName(), GREY, Color.black, 1, 0, 0);
				TTF2.friendName.drawStringShadow(50*Mideas.getDisplayXFactor()+TTF2.friendName.getWidth(friend.getName()), y, " - offline", GREY, Color.black, 1, 0, 0);
				TTF2.friendInfos.drawStringShadow(50*Mideas.getDisplayXFactor(), y+18*Mideas.getDisplayYFactor(), "Unknwon", Color.white, Color.black, 1, 0, 0);
			}
			i++;
			y+= yShift;
		}
		deleteFriendButton.draw();
		addFriendButton.draw();
		sendMessageFriendButton.draw();
		invInParty.draw();
	}
	
	public static boolean mouseEvent() {
		hoveredFriend = -1;
		int i = 0;
		float y = 230*Mideas.getDisplayYFactor();
		float yShift = 32*Mideas.getDisplayYFactor();
		while(i < Mideas.joueur1().getFriendList().size()) {
			if(isHover(y+i*yShift)) {
				hoveredFriend = i;
				break;
			}
			i++;
		}
		if(hoveredFriend != -1) {
			if(Mouse.getEventButtonState()) {
				if(Mouse.getEventButton() == 0) {
					leftButtonDownFriend = hoveredFriend;
				}
			}
		}
		if(!Mouse.getEventButtonState()) {
			if(Mouse.getEventButton() == 0) {
				if(leftButtonDownFriend != -1 && leftButtonDownFriend == hoveredFriend) {
					selectedFriend = Mideas.joueur1().getFriendList().get(leftButtonDownFriend);
				}
				else {
					leftButtonDownFriend = -1;
				}
			}
		}
		deleteFriendButton.event();
		addFriendButton.event();
		sendMessageFriendButton.event();
		invInParty.event();
		return false;
	}
	
	private static boolean isHover(float y) {
		return Mideas.mouseX() >= 30*Mideas.getDisplayXFactor() && Mideas.mouseX() <= (30+Sprites.friend_border.getImageWidth())*Mideas.getDisplayXFactor() && Mideas.mouseY() >= y && Mideas.mouseY() <= y+32*Mideas.getDisplayYFactor();
	}
	
	public static void updateSize() {
		deleteFriendButton.update(27*Mideas.getDisplayXFactor(), Display.getHeight()/2+79*Mideas.getDisplayYFactor(), BUTTON_LENGTH*Mideas.getDisplayXFactor(), BUTTON_HEIGHT*Mideas.getDisplayXFactor());
		addFriendButton.update(27*Mideas.getDisplayXFactor(), Display.getHeight()/2+52*Mideas.getDisplayYFactor(), BUTTON_LENGTH*Mideas.getDisplayXFactor(), BUTTON_HEIGHT*Mideas.getDisplayXFactor());
		sendMessageFriendButton.update(251*Mideas.getDisplayXFactor(), Display.getHeight()/2+52*Mideas.getDisplayYFactor(), BUTTON_LENGTH*Mideas.getDisplayXFactor(), BUTTON_HEIGHT*Mideas.getDisplayXFactor());
		invInParty.update(251*Mideas.getDisplayXFactor(), Display.getHeight()/2+79*Mideas.getDisplayYFactor(), BUTTON_LENGTH*Mideas.getDisplayXFactor(), BUTTON_HEIGHT*Mideas.getDisplayXFactor());
	}
}
