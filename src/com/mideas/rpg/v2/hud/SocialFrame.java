package com.mideas.rpg.v2.hud;

import org.newdawn.slick.Color;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.Sprites;
import com.mideas.rpg.v2.TTF2;
import com.mideas.rpg.v2.game.Friend;
import com.mideas.rpg.v2.game.SocialFrameMenu;
import com.mideas.rpg.v2.utils.Draw;

public class SocialFrame {

	private static SocialFrameMenu selectedMenu = SocialFrameMenu.FRIEND_FRAME;
	private final static Color YELLOW = Color.decode("#FFC700");
	private final static Color GREY = Color.decode("#999999");
	private static Friend selectedFriend;
	private static Friend hoveredFriend;
	private static boolean friendInit;
	
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
		
		return false;
	}
	
	private static void drawFriendFrame() {
		if(!friendInit && Mideas.joueur1().getFriendList().size() > 0) {
			selectedFriend = Mideas.joueur1().getFriendList().get(0);
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
	}
	
	private static void drawWhoFrame() {
		
	}
	
	private static void drawGuildFrame() {
		
	}
	
	private static void drawDiscussionFrame() {
		
	}
	
	private static void drawRaidFrame() {
		
	}
}
