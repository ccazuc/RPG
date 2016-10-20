package com.mideas.rpg.v2.hud;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.Sprites;
import com.mideas.rpg.v2.TTF2;
import com.mideas.rpg.v2.command.CommandFriend;
import com.mideas.rpg.v2.game.Friend;
import com.mideas.rpg.v2.game.SocialFrameMenu;
import com.mideas.rpg.v2.utils.Button;
import com.mideas.rpg.v2.utils.Draw;

public class SocialFrame {

	private static SocialFrameMenu selectedMenu = SocialFrameMenu.FRIEND_FRAME;
	private final static Color YELLOW = Color.decode("#FFC700");
	private final static Color GREY = Color.decode("#999999");
	static Friend selectedFriend;
	private static Friend hoveredFriend;
	private static boolean friendInit;
	static boolean addingFriend;
	final static int BUTTON_LENGTH = 145;
	private static Button deleteFriendButton = new Button(27*Mideas.getDisplayXFactor(), Display.getHeight()/2+79*Mideas.getDisplayYFactor(), BUTTON_LENGTH*Mideas.getDisplayXFactor(), 25*Mideas.getDisplayXFactor(), "Delete", 13, 1) {
		@Override
		public void eventButtonClick() {
			Mideas.joueur1().getFriendList().remove(selectedFriend);
			CommandFriend.removeFriend(selectedFriend.getCharacterId());
		}
		
		@Override
		public boolean activateCondition() {
			return selectedFriend != null;
		}
	};
	private static Button addFriendButton = new Button(27*Mideas.getDisplayXFactor(), Display.getHeight()/2+52*Mideas.getDisplayYFactor(), BUTTON_LENGTH*Mideas.getDisplayXFactor(), 25*Mideas.getDisplayXFactor(), "Add friend", 13, 1) {
		@Override
		public void eventButtonClick() {
			addingFriend = true;
		}
	};
	private static Button sendMessageFriendButton = new Button(251*Mideas.getDisplayXFactor(), Display.getHeight()/2+52*Mideas.getDisplayYFactor(), BUTTON_LENGTH*Mideas.getDisplayXFactor(), 25*Mideas.getDisplayXFactor(), "Send message", 13, 1) {
		@Override
		public void eventButtonClick() {
			//trigger whisper
		}
		
		@Override
		public boolean activateCondition() {
			return selectedFriend != null && selectedFriend.isOnline();
		}
	};
	private static Button invInParty = new Button(251*Mideas.getDisplayXFactor(), Display.getHeight()/2+79*Mideas.getDisplayYFactor(), BUTTON_LENGTH*Mideas.getDisplayXFactor(), 25*Mideas.getDisplayXFactor(), "Inv. in party", 13, 1) {
		@Override
		public void eventButtonClick() {
			//trigger whisper
		}
		
		@Override
		public boolean activateCondition() {
			return selectedFriend != null && selectedFriend.isOnline();
		}
	};
	
	public static void draw() {
		if(selectedMenu == SocialFrameMenu.FRIEND_FRAME) {
			drawFriendFrame();
		}
		else if(selectedMenu == SocialFrameMenu.WHO_FRAME) {
			drawWhoFrame();
		}
		else if(selectedMenu == SocialFrameMenu.GUILD_FRAME) {
			drawGuildFrame();
		}
		else if(selectedMenu == SocialFrameMenu.DISCUSSION_FRAME) {
			drawDiscussionFrame();
		}
		else if(selectedMenu == SocialFrameMenu.RAID_FRAME) {
			drawRaidFrame();
		}
	}
	
	public static boolean mouseEvent() {
		if(selectedMenu == SocialFrameMenu.FRIEND_FRAME) {
			return mouseEventFriendFrame();
		}
		else if(selectedMenu == SocialFrameMenu.WHO_FRAME) {
			return mouseEventWhoFrame();
		}
		else if(selectedMenu == SocialFrameMenu.GUILD_FRAME) {
			return mouseEventGuildFrame();
		}
		else if(selectedMenu == SocialFrameMenu.DISCUSSION_FRAME) {
			return mouseEventDiscussionFrame();
		}
		else if(selectedMenu == SocialFrameMenu.RAID_FRAME) {
			return mouseEventRaidFrame();
		}
		return false;
	}
	
	private static void drawFriendFrame() {
		if(!friendInit && Mideas.joueur1().getFriendList().size() > 0) {
			selectedFriend = Mideas.joueur1().getFriendList().get(1);
			//hoveredFriend = Mideas.joueur1().getFriendList().get(1);
			friendInit = true;
		}
		Draw.drawQuad(Sprites.friend_frame, 10*Mideas.getDisplayXFactor(), 150*Mideas.getDisplayYFactor());
		int i = 0;
		Friend friend;
		float y = 230*Mideas.getDisplayYFactor();
		float yShift = 32*Mideas.getDisplayYFactor();
		while(i < Mideas.joueur1().getFriendList().size()) {
			friend = Mideas.joueur1().getFriendList().get(i);
			if(selectedFriend == friend || hoveredFriend == friend) {
				Draw.drawQuad(Sprites.friend_border, 30*Mideas.getDisplayXFactor(), y);
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
	
	private static void drawWhoFrame() {
		
	}
	
	private static void drawGuildFrame() {
		
	}
	
	private static void drawDiscussionFrame() {
		
	}
	
	private static void drawRaidFrame() {
		
	}
	
	private static boolean mouseEventFriendFrame() {
		deleteFriendButton.event();
		addFriendButton.event();
		sendMessageFriendButton.event();
		invInParty.event();
		return false;
	}
	
	private static boolean mouseEventWhoFrame() {
		
		return false;
	}
	
	private static boolean mouseEventGuildFrame() {
		
		return false;
	}
	
	private static boolean mouseEventDiscussionFrame() {
		
		return false;
	}
	
	private static boolean mouseEventRaidFrame() {
		
		return false;
	}
	
	public static void updateSize() {
		
	}
}
