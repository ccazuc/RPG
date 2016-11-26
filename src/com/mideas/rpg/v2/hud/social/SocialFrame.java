package com.mideas.rpg.v2.hud.social;

import com.mideas.rpg.v2.Interface;
import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.game.SocialFrameMenu;
import com.mideas.rpg.v2.hud.social.friends.FriendsFrame;
import com.mideas.rpg.v2.hud.social.guild.GuildFrame;
import com.mideas.rpg.v2.utils.ButtonMenu;
import com.mideas.rpg.v2.utils.CrossButton;

public class SocialFrame {

	static SocialFrameMenu selectedMenu = SocialFrameMenu.FRIEND_FRAME;
	private static int Y_SOCIAL_FRAME_DEFAULT = 115;
	public static float Y_SOCIAL_FRAME = Y_SOCIAL_FRAME_DEFAULT*Mideas.getDisplayYFactor();
	private static int X_SOCIAL_FRAME_DEFAULT = 5;
	public static float X_SOCIAL_FRAME = X_SOCIAL_FRAME_DEFAULT*Mideas.getDisplayXFactor();
	private final static float BUTTON_MENU_Y = 461;
	private final static float BUTTON_MENU_Y_SIZE = 33;
	private static ButtonMenu friendButtonMenu = new ButtonMenu(X_SOCIAL_FRAME+20*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+BUTTON_MENU_Y*Mideas.getDisplayYFactor(), 54*Mideas.getDisplayXFactor(), BUTTON_MENU_Y_SIZE*Mideas.getDisplayYFactor(), "Friends", 10, 1, true) {
		
		@Override
		public void eventButtonClick() {
			unselectAllButton();
			this.setIsSelected(true);
			selectedMenu = SocialFrameMenu.FRIEND_FRAME;
		}
	};
	private static ButtonMenu whoButtonMenu = new ButtonMenu(X_SOCIAL_FRAME+80*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+BUTTON_MENU_Y*Mideas.getDisplayYFactor(), 44*Mideas.getDisplayXFactor(), BUTTON_MENU_Y_SIZE*Mideas.getDisplayYFactor(), "Who", 10, 1, false) {
		
		@Override
		public void eventButtonClick() {
			unselectAllButton();
			this.setIsSelected(true);
			//selectedMenu = SocialFrameMenu.WHO_FRAME;
		}
	};
	private static ButtonMenu guildButtonMenu = new ButtonMenu(X_SOCIAL_FRAME+130*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+BUTTON_MENU_Y*Mideas.getDisplayYFactor(), 60*Mideas.getDisplayXFactor(), BUTTON_MENU_Y_SIZE*Mideas.getDisplayYFactor(), "Guild", 10, 1, false) {
		
		@Override
		public void eventButtonClick() {
			unselectAllButton();
			this.setIsSelected(true);
			selectedMenu = SocialFrameMenu.GUILD_FRAME;
		}
		
		@Override
		public boolean activateCondition() {
			return Mideas.joueur1().getGuild() != null;
		}
	};
	private static ButtonMenu discussionButtonMenu = new ButtonMenu(X_SOCIAL_FRAME+194*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+BUTTON_MENU_Y*Mideas.getDisplayYFactor(), 85*Mideas.getDisplayXFactor(), BUTTON_MENU_Y_SIZE*Mideas.getDisplayYFactor(), "Discussion", 10, 1, false) {
		
		@Override
		public void eventButtonClick() {
			unselectAllButton();
			this.setIsSelected(true);
			//selectedMenu = SocialFrameMenu.DISCUSSION_FRAME;
		}
	};
	private static ButtonMenu raidButtonMenu = new ButtonMenu(X_SOCIAL_FRAME+285*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+BUTTON_MENU_Y*Mideas.getDisplayYFactor(), 49*Mideas.getDisplayXFactor(), BUTTON_MENU_Y_SIZE*Mideas.getDisplayYFactor(), "Raid", 10, 1, false) {
		
		@Override
		public void eventButtonClick() {
			unselectAllButton();
			this.setIsSelected(true);
			//selectedMenu = SocialFrameMenu.RAID_FRAME;
		}
	};
	private static CrossButton closeFrame = new CrossButton(X_SOCIAL_FRAME+370*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+14*Mideas.getDisplayYFactor()) {
		
		@Override
		public void eventButtonClick() {
			Interface.setSocialFrameStatus(false);
			this.reset();
		}
	};
	private static boolean draw = true;
	
	public static void draw() {
		if(selectedMenu == SocialFrameMenu.FRIEND_FRAME) {
			FriendsFrame.draw();
		}
		else if(selectedMenu == SocialFrameMenu.WHO_FRAME) {
			//WhoFrame.draw();
		}
		else if(selectedMenu == SocialFrameMenu.GUILD_FRAME) {
			GuildFrame.draw();
		}
		else if(selectedMenu == SocialFrameMenu.DISCUSSION_FRAME) {
			//DiscussionFrame.draw();
		}
		else if(selectedMenu == SocialFrameMenu.RAID_FRAME) {
			//RaidFrame.draw();
		}
		if(draw) {
			friendButtonMenu.draw();
			whoButtonMenu.draw();
			guildButtonMenu.draw();
			discussionButtonMenu.draw();
			raidButtonMenu.draw();
			closeFrame.draw();
		}
	}
	
	public static boolean mouseEvent() {
		if(friendButtonMenu.event()) return true;
		if(whoButtonMenu.event()) return true;
		if(guildButtonMenu.event()) return true;
		if(discussionButtonMenu.event()) return true;
		if(raidButtonMenu.event()) return true;
		if(closeFrame.event()) return true;
		if(selectedMenu == SocialFrameMenu.FRIEND_FRAME) {
			return FriendsFrame.mouseEvent();
		}
		else if(selectedMenu == SocialFrameMenu.WHO_FRAME) {
			//return WhoFrame.mouseEvent();
		}
		else if(selectedMenu == SocialFrameMenu.GUILD_FRAME) {
			return GuildFrame.mouseEvent();
		}
		else if(selectedMenu == SocialFrameMenu.DISCUSSION_FRAME) {
			//return DiscussionFrame.mouseEvent();
		}
		else if(selectedMenu == SocialFrameMenu.RAID_FRAME) {
			//return RaidFrame.mouseEvent();
		}
		return false;
	}
	
	static void unselectAllButton() {
		friendButtonMenu.setIsSelected(false);
		whoButtonMenu.setIsSelected(false);
		guildButtonMenu.setIsSelected(false);
		discussionButtonMenu.setIsSelected(false);
		raidButtonMenu.setIsSelected(false);
	}
	
	public static void updateSize() {
		Y_SOCIAL_FRAME = Y_SOCIAL_FRAME_DEFAULT*Mideas.getDisplayYFactor();
		X_SOCIAL_FRAME = X_SOCIAL_FRAME_DEFAULT*Mideas.getDisplayXFactor();
		friendButtonMenu.update(X_SOCIAL_FRAME+20*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+BUTTON_MENU_Y*Mideas.getDisplayYFactor(), 54*Mideas.getDisplayXFactor(), BUTTON_MENU_Y_SIZE*Mideas.getDisplayYFactor());
		whoButtonMenu.update(X_SOCIAL_FRAME+80*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+BUTTON_MENU_Y*Mideas.getDisplayYFactor(), 44*Mideas.getDisplayXFactor(), BUTTON_MENU_Y_SIZE*Mideas.getDisplayYFactor());
		guildButtonMenu.update(X_SOCIAL_FRAME+130*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+BUTTON_MENU_Y*Mideas.getDisplayYFactor(), 60*Mideas.getDisplayXFactor(), BUTTON_MENU_Y_SIZE*Mideas.getDisplayYFactor());
		discussionButtonMenu.update(X_SOCIAL_FRAME+194*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+BUTTON_MENU_Y*Mideas.getDisplayYFactor(), 85*Mideas.getDisplayXFactor(), BUTTON_MENU_Y_SIZE*Mideas.getDisplayYFactor());
		raidButtonMenu.update(X_SOCIAL_FRAME+285*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+BUTTON_MENU_Y*Mideas.getDisplayYFactor(), 49*Mideas.getDisplayXFactor(), BUTTON_MENU_Y_SIZE*Mideas.getDisplayYFactor());
		closeFrame.update(X_SOCIAL_FRAME+370*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+14*Mideas.getDisplayYFactor());
	}
	
	public static void test() {
		friendButtonMenu.setIsSelected(!friendButtonMenu.isSelected());
		whoButtonMenu.setIsSelected(!whoButtonMenu.isSelected());
		guildButtonMenu.setIsSelected(!guildButtonMenu.isSelected());
		draw = !draw;
	}
	
	public static void setSelectedMenu(SocialFrameMenu menu) {
		selectedMenu = menu;
		unselectAllButton();
		friendButtonMenu.setIsSelected(true);
	}
	
	public static boolean isGuildFrameActive() {
		return selectedMenu == SocialFrameMenu.GUILD_FRAME;
	}
}
