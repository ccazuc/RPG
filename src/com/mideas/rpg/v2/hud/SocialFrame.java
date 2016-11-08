package com.mideas.rpg.v2.hud;

import com.mideas.rpg.v2.Interface;
import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.game.Friend;
import com.mideas.rpg.v2.game.SocialFrameMenu;
import com.mideas.rpg.v2.hud.social.FriendsFrame;
import com.mideas.rpg.v2.utils.ButtonMenu;
import com.mideas.rpg.v2.utils.CrossButton;

public class SocialFrame {

	static SocialFrameMenu selectedMenu = SocialFrameMenu.FRIEND_FRAME;
	static Friend selectedFriend;
	private final static float BUTTON_MENU_Y = 611;
	private final static float BUTTON_MENU_Y_SIZE = 33;
	private static ButtonMenu friendButtonMenu = new ButtonMenu(30*Mideas.getDisplayXFactor(), BUTTON_MENU_Y*Mideas.getDisplayYFactor(), 54*Mideas.getDisplayXFactor(), BUTTON_MENU_Y_SIZE*Mideas.getDisplayYFactor(), "Friends", 10, 1, true) {
		
		@Override
		public void eventButtonClick() {
			unselectAllButton();
			this.setIsSelected(true);
			selectedMenu = SocialFrameMenu.FRIEND_FRAME;
		}
	};
	private static ButtonMenu whoButtonMenu = new ButtonMenu(90*Mideas.getDisplayXFactor(), BUTTON_MENU_Y*Mideas.getDisplayYFactor(), 44*Mideas.getDisplayXFactor(), BUTTON_MENU_Y_SIZE*Mideas.getDisplayYFactor(), "Who", 10, 1, false) {
		
		@Override
		public void eventButtonClick() {
			unselectAllButton();
			this.setIsSelected(true);
			//selectedMenu = SocialFrameMenu.WHO_FRAME;
		}
	};
	private static ButtonMenu guildButtonMenu = new ButtonMenu(140*Mideas.getDisplayXFactor(), BUTTON_MENU_Y*Mideas.getDisplayYFactor(), 60*Mideas.getDisplayXFactor(), BUTTON_MENU_Y_SIZE*Mideas.getDisplayYFactor(), "Guild", 10, 1, false) {
		
		@Override
		public void eventButtonClick() {
			unselectAllButton();
			this.setIsSelected(true);
			//selectedMenu = SocialFrameMenu.GUILD_FRAME;
		}
		
		@Override
		public boolean activateCondition() {
			return Mideas.joueur1().getGuild() != null;
		}
	};
	private static ButtonMenu discussionButtonMenu = new ButtonMenu(204*Mideas.getDisplayXFactor(), BUTTON_MENU_Y*Mideas.getDisplayYFactor(), 85*Mideas.getDisplayXFactor(), BUTTON_MENU_Y_SIZE*Mideas.getDisplayYFactor(), "Discussion", 10, 1, false) {
		
		@Override
		public void eventButtonClick() {
			unselectAllButton();
			this.setIsSelected(true);
			//selectedMenu = SocialFrameMenu.DISCUSSION_FRAME;
		}
	};
	private static ButtonMenu raidButtonMenu = new ButtonMenu(295*Mideas.getDisplayXFactor(), BUTTON_MENU_Y*Mideas.getDisplayYFactor(), 49*Mideas.getDisplayXFactor(), BUTTON_MENU_Y_SIZE*Mideas.getDisplayYFactor(), "Raid", 10, 1, false) {
		
		@Override
		public void eventButtonClick() {
			unselectAllButton();
			this.setIsSelected(true);
			//selectedMenu = SocialFrameMenu.RAID_FRAME;
		}
	};
	private static CrossButton closeFrame = new CrossButton(380*Mideas.getDisplayXFactor(), 164*Mideas.getDisplayYFactor()) {
		
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
	
	private static void drawWhoFrame() {
		
	}
	
	private static void drawGuildFrame() {
		
	}
	
	private static void drawDiscussionFrame() {
		
	}
	
	private static void drawRaidFrame() {
		
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
	
	static void unselectAllButton() {
		friendButtonMenu.setIsSelected(false);
		whoButtonMenu.setIsSelected(false);
		guildButtonMenu.setIsSelected(false);
		discussionButtonMenu.setIsSelected(false);
		raidButtonMenu.setIsSelected(false);
	}
	
	public static void updateSize() {
		friendButtonMenu.update(30*Mideas.getDisplayXFactor(), BUTTON_MENU_Y*Mideas.getDisplayYFactor(), 54*Mideas.getDisplayXFactor(), BUTTON_MENU_Y_SIZE*Mideas.getDisplayYFactor());
		whoButtonMenu.update(90*Mideas.getDisplayXFactor(), BUTTON_MENU_Y*Mideas.getDisplayYFactor(), 44*Mideas.getDisplayXFactor(), BUTTON_MENU_Y_SIZE*Mideas.getDisplayYFactor());
		guildButtonMenu.update(140*Mideas.getDisplayXFactor(), BUTTON_MENU_Y*Mideas.getDisplayYFactor(), 60*Mideas.getDisplayXFactor(), BUTTON_MENU_Y_SIZE*Mideas.getDisplayYFactor());
		discussionButtonMenu.update(204*Mideas.getDisplayXFactor(), BUTTON_MENU_Y*Mideas.getDisplayYFactor(), 85*Mideas.getDisplayXFactor(), BUTTON_MENU_Y_SIZE*Mideas.getDisplayYFactor());
		raidButtonMenu.update(295*Mideas.getDisplayXFactor(), BUTTON_MENU_Y*Mideas.getDisplayYFactor(), 49*Mideas.getDisplayXFactor(), BUTTON_MENU_Y_SIZE*Mideas.getDisplayYFactor());
		closeFrame.update(380*Mideas.getDisplayXFactor(), 164*Mideas.getDisplayYFactor());
	}
	
	public static void test() {
		friendButtonMenu.setIsSelected(!friendButtonMenu.isSelected());
		whoButtonMenu.setIsSelected(!whoButtonMenu.isSelected());
		guildButtonMenu.setIsSelected(!guildButtonMenu.isSelected());
		draw = !draw;
	}
}
