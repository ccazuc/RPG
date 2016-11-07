package com.mideas.rpg.v2.hud;

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
import com.mideas.rpg.v2.game.SocialFrameMenu;
import com.mideas.rpg.v2.utils.Button;
import com.mideas.rpg.v2.utils.ButtonMenu;
import com.mideas.rpg.v2.utils.Draw;

public class SocialFrame {

	static SocialFrameMenu selectedMenu = SocialFrameMenu.FRIEND_FRAME;
	private final static Color YELLOW = Color.decode("#FFC700");
	private final static Color GREY = Color.decode("#999999");
	static Friend selectedFriend;
	private static int hoveredFriend = -1;
	private static int leftButtonDownFriend = -1;
	private static boolean friendInit;
	final static int BUTTON_LENGTH = 145;
	private static Button deleteFriendButton = new Button(27*Mideas.getDisplayXFactor(), Display.getHeight()/2+79*Mideas.getDisplayYFactor(), BUTTON_LENGTH*Mideas.getDisplayXFactor(), 25*Mideas.getDisplayXFactor(), "Delete", 12, 1) {
		@Override
		public void eventButtonClick() {
			Mideas.joueur1().getFriendList().remove(selectedFriend);
			if(Mideas.joueur1().getFriendList().size() > 0) {
				selectedFriend = Mideas.joueur1().getFriendList().get(0);
			}
			else {
				selectedFriend = null;
			}
			CommandFriend.removeFriend(selectedFriend.getCharacterId());
		}
		
		@Override
		public boolean activateCondition() {
			return selectedFriend != null;
		}
	};
	private static Button addFriendButton = new Button(27*Mideas.getDisplayXFactor(), Display.getHeight()/2+52*Mideas.getDisplayYFactor(), BUTTON_LENGTH*Mideas.getDisplayXFactor(), 25*Mideas.getDisplayXFactor(), "Add friend", 12, 1) {
		@Override
		public void eventButtonClick() {
			Interface.setAddFriendStatus(true);
		}
	};
	private static Button sendMessageFriendButton = new Button(251*Mideas.getDisplayXFactor(), Display.getHeight()/2+52*Mideas.getDisplayYFactor(), BUTTON_LENGTH*Mideas.getDisplayXFactor(), 25*Mideas.getDisplayXFactor(), "Send message", 12, 1) {
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
	private static Button invInParty = new Button(251*Mideas.getDisplayXFactor(), Display.getHeight()/2+79*Mideas.getDisplayYFactor(), BUTTON_LENGTH*Mideas.getDisplayXFactor(), 25*Mideas.getDisplayXFactor(), "Inv. in party", 12, 1) {
		@Override
		public void eventButtonClick() {
			CommandParty.invitePlayer(selectedFriend.getName());
		}
		
		@Override
		public boolean activateCondition() {
			return selectedFriend != null && selectedFriend.isOnline();
		}
	};
	private static ButtonMenu friendButtonMenu = new ButtonMenu(30*Mideas.getDisplayXFactor(), 611*Mideas.getDisplayYFactor(), 54*Mideas.getDisplayXFactor(), 33*Mideas.getDisplayYFactor(), "Friends", 10, 1, true, -4) {
		
		@Override
		public void eventButtonClick() {
			unselectAllButton();
			this.setIsSelected(true);
			selectedMenu = SocialFrameMenu.FRIEND_FRAME;
		}
	};
	private static ButtonMenu whoButtonMenu = new ButtonMenu(90*Mideas.getDisplayXFactor(), 611*Mideas.getDisplayYFactor(), 44*Mideas.getDisplayXFactor(), 33*Mideas.getDisplayYFactor(), "Who", 10, 1, false, -4) {
		
		@Override
		public void eventButtonClick() {
			unselectAllButton();
			this.setIsSelected(true);
			//selectedMenu = SocialFrameMenu.WHO_FRAME;
		}
	};
	private static ButtonMenu guildButtonMenu = new ButtonMenu(140*Mideas.getDisplayXFactor(), 611*Mideas.getDisplayYFactor(), 60*Mideas.getDisplayXFactor(), 33*Mideas.getDisplayYFactor(), "Guild", 10, 1, false, -4) {
		
		@Override
		public void eventButtonClick() {
			unselectAllButton();
			this.setIsSelected(true);
			//selectedMenu = SocialFrameMenu.GUILD_FRAME;
		}
		
		@Override
		public boolean activateCondition() {
			//Mideas.joueur1().getGuild() != null
			return false;
		}
	};
	private static ButtonMenu discussionButtonMenu = new ButtonMenu(204*Mideas.getDisplayXFactor(), 611*Mideas.getDisplayYFactor(), 85*Mideas.getDisplayXFactor(), 33*Mideas.getDisplayYFactor(), "Discussion", 10, 1, false, -4) {
		
		@Override
		public void eventButtonClick() {
			unselectAllButton();
			this.setIsSelected(true);
			//selectedMenu = SocialFrameMenu.DISCUSSION_FRAME;
		}
	};
	private static ButtonMenu raidButtonMenu = new ButtonMenu(295*Mideas.getDisplayXFactor(), 611*Mideas.getDisplayYFactor(), 49*Mideas.getDisplayXFactor(), 33*Mideas.getDisplayYFactor(), "Raid", 10, 1, false, -4) {
		
		@Override
		public void eventButtonClick() {
			unselectAllButton();
			this.setIsSelected(true);
			//selectedMenu = SocialFrameMenu.RAID_FRAME;
		}
	};
	private static boolean draw = true;
	
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
		if(draw) {
			friendButtonMenu.draw();
			whoButtonMenu.draw();
			guildButtonMenu.draw();
			discussionButtonMenu.draw();
			raidButtonMenu.draw();
		}
	}
	
	public static boolean mouseEvent() {
		if(friendButtonMenu.event()) return true;
		if(whoButtonMenu.event()) return true;
		if(guildButtonMenu.event()) return true;
		if(discussionButtonMenu.event()) return true;
		if(raidButtonMenu.event()) return true;
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
	
	private static void drawWhoFrame() {
		
	}
	
	private static void drawGuildFrame() {
		
	}
	
	private static void drawDiscussionFrame() {
		
	}
	
	private static void drawRaidFrame() {
		
	}
	
	private static boolean mouseEventFriendFrame() {
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
	
	private static boolean isHover(float y) {
		return Mideas.mouseX() >= 30*Mideas.getDisplayXFactor() && Mideas.mouseX() <= (30+Sprites.friend_border.getImageWidth())*Mideas.getDisplayXFactor() && Mideas.mouseY() >= y && Mideas.mouseY() <= y+32*Mideas.getDisplayYFactor();
	}
	
	static void unselectAllButton() {
		friendButtonMenu.setIsSelected(false);
		whoButtonMenu.setIsSelected(false);
		guildButtonMenu.setIsSelected(false);
		discussionButtonMenu.setIsSelected(false);
		raidButtonMenu.setIsSelected(false);
	}
	
	public static void updateSize() {
		deleteFriendButton.update(27*Mideas.getDisplayXFactor(), Display.getHeight()/2+79*Mideas.getDisplayYFactor(), BUTTON_LENGTH*Mideas.getDisplayXFactor(), 25*Mideas.getDisplayXFactor());
		addFriendButton.update(27*Mideas.getDisplayXFactor(), Display.getHeight()/2+52*Mideas.getDisplayYFactor(), BUTTON_LENGTH*Mideas.getDisplayXFactor(), 25*Mideas.getDisplayXFactor());
		sendMessageFriendButton.update(251*Mideas.getDisplayXFactor(), Display.getHeight()/2+52*Mideas.getDisplayYFactor(), BUTTON_LENGTH*Mideas.getDisplayXFactor(), 25*Mideas.getDisplayXFactor());
		invInParty.update(251*Mideas.getDisplayXFactor(), Display.getHeight()/2+79*Mideas.getDisplayYFactor(), BUTTON_LENGTH*Mideas.getDisplayXFactor(), 25*Mideas.getDisplayXFactor());
	}
	
	public static void test() {
		friendButtonMenu.setIsSelected(!friendButtonMenu.isSelected());
		whoButtonMenu.setIsSelected(!whoButtonMenu.isSelected());
		guildButtonMenu.setIsSelected(!guildButtonMenu.isSelected());
		draw = !draw;
	}
}
