package com.mideas.rpg.v2.hud.social.friends;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;

import com.mideas.rpg.v2.Interface;
import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.Sprites;
import com.mideas.rpg.v2.TTF2;
import com.mideas.rpg.v2.chat.ChatFrame;
import com.mideas.rpg.v2.command.CommandFriend;
import com.mideas.rpg.v2.command.CommandIgnore;
import com.mideas.rpg.v2.command.CommandParty;
import com.mideas.rpg.v2.game.Friend;
import com.mideas.rpg.v2.game.Ignore;
import com.mideas.rpg.v2.hud.PopupFrame;
import com.mideas.rpg.v2.hud.social.guild.AddGuildMemberInputFrame;
import com.mideas.rpg.v2.utils.Button;
import com.mideas.rpg.v2.utils.Draw;
import com.mideas.rpg.v2.utils.FrameTab;
import com.mideas.rpg.v2.utils.ScrollBar;

import static com.mideas.rpg.v2.hud.social.SocialFrame.Y_SOCIAL_FRAME;

import static com.mideas.rpg.v2.hud.social.SocialFrame.X_SOCIAL_FRAME;

public class FriendsFrame {

	private final static Color YELLOW = Color.decode("#FFC700");
	private final static Color GREY = Color.decode("#999999");
	static Friend selectedFriend;
	static Ignore selectedIgnore;
	private static int hoveredFriend = -1;
	private static int leftButtonDownFriend = -1;
	private static int hoveredIgnore = -1;
	private static int leftButtonDownIgnore = -1;
	private static boolean friendInit;
	private final static int FRIEND_BUTTON_LENGTH = 145;
	private final static int FRIEND_BUTTON_HEIGHT = 25;
	private final static int IGNORE_BUTTON_LENGTH = 145;
	private final static int IGNORE_BUTTON_HEIGHT = 25;
	private final static int FL_MAXIMUM_DISPLAY = 10;
	private final static int IL_MAXIMUM_DISPLAY = 20;
	static boolean friend_tab_active = true;
	private final static Button deleteFriendButton = new Button(X_SOCIAL_FRAME+17*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+437*Mideas.getDisplayYFactor(), FRIEND_BUTTON_LENGTH*Mideas.getDisplayXFactor(), FRIEND_BUTTON_HEIGHT*Mideas.getDisplayXFactor(), "Delete", 12, 1) {
		@Override
		public void eventButtonClick() {
			CommandFriend.removeFriend(selectedFriend.getCharacterId());
		}
		
		@Override
		public boolean activateCondition() {
			return selectedFriend != null;
		}
	};
	private final static Button addFriendButton = new Button(X_SOCIAL_FRAME+17*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+410*Mideas.getDisplayYFactor(), FRIEND_BUTTON_LENGTH*Mideas.getDisplayXFactor(), FRIEND_BUTTON_HEIGHT*Mideas.getDisplayXFactor(), "Add friend", 12, 1) {
		@Override
		public void eventButtonClick() {
			PopupFrame.activateAddFriendPopupInput();
		}
	};
	private final static Button sendMessageFriendButton = new Button(X_SOCIAL_FRAME+241*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+410*Mideas.getDisplayYFactor(), FRIEND_BUTTON_LENGTH*Mideas.getDisplayXFactor(), FRIEND_BUTTON_HEIGHT*Mideas.getDisplayXFactor(), "Send message", 12, 1) {
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
	private final static Button invInParty = new Button(X_SOCIAL_FRAME+241*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+437*Mideas.getDisplayYFactor(), FRIEND_BUTTON_LENGTH*Mideas.getDisplayXFactor(), FRIEND_BUTTON_HEIGHT*Mideas.getDisplayXFactor(), "Inv. in party", 12 , 1) {
		@Override
		public void eventButtonClick() {
			CommandParty.invitePlayer(selectedFriend.getName());
		}
		
		@Override
		public boolean activateCondition() {
			return selectedFriend != null && selectedFriend.isOnline();
		}
	};
	private final static Button ignorePlayer = new Button(X_SOCIAL_FRAME+17*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+439*Mideas.getDisplayYFactor(), IGNORE_BUTTON_LENGTH*Mideas.getDisplayXFactor(), IGNORE_BUTTON_HEIGHT*Mideas.getDisplayXFactor(), "Ignore Player", 12 , 1) {
		@Override
		public void eventButtonClick() {
			//enable ignore popup
		}
	};
	private final static Button removeIgnorePlayer = new Button(X_SOCIAL_FRAME+241*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+439*Mideas.getDisplayYFactor(), IGNORE_BUTTON_LENGTH*Mideas.getDisplayXFactor(), IGNORE_BUTTON_HEIGHT*Mideas.getDisplayXFactor(), "Remove Player", 12 , 1) {
		@Override
		public void eventButtonClick() {
			CommandIgnore.removeIgnore(selectedIgnore.getId());
		}
		
		@Override
		public boolean activateCondition() {
			return selectedIgnore != null;
		}
	};
	final static FrameTab friendFrameTab = new FrameTab(X_SOCIAL_FRAME+73*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+45*Mideas.getDisplayYFactor(), 57*Mideas.getDisplayXFactor(), 32*Mideas.getDisplayYFactor(), "Friend", 12, 1, true) {
		
		@Override
		public void eventButtonClick() {
			friend_tab_active = true;
			this.setIsSelected(true);
			ignoreFrameTab.setIsSelected(false);
		}
	};
	final static FrameTab ignoreFrameTab = new FrameTab(X_SOCIAL_FRAME+135*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+45*Mideas.getDisplayYFactor(), 77*Mideas.getDisplayXFactor(), 32*Mideas.getDisplayYFactor(), "Ignore", 12, 1, false) {
		
		@Override
		public void eventButtonClick() {
			friend_tab_active = false;
			this.setIsSelected(true);
			friendFrameTab.setIsSelected(false);
		}
	};
	private final static ScrollBar friendScrollbar = new ScrollBar(X_SOCIAL_FRAME+358*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+77*Mideas.getDisplayYFactor(), 311*Mideas.getDisplayYFactor(), 400*Mideas.getDisplayXFactor(), 307*Mideas.getDisplayYFactor(), false, 32*Mideas.getDisplayYFactor());
	private final static ScrollBar ignoreScrollbar = new ScrollBar(X_SOCIAL_FRAME+358*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+77*Mideas.getDisplayYFactor(), 341*Mideas.getDisplayYFactor(), 400*Mideas.getDisplayXFactor(), 350*Mideas.getDisplayYFactor(), false, 17.6f*Mideas.getDisplayYFactor());

	public static void draw() {
		if(friend_tab_active) {
			if(!friendInit && Mideas.joueur1().getFriendList().size() > 0) {
				selectedFriend = Mideas.joueur1().getFriendList().get(0);
				friendInit = true;
			}
			final int FL_SIZE = Mideas.joueur1().getFriendList().size();
			Draw.drawQuad(Sprites.friend_frame, X_SOCIAL_FRAME, Y_SOCIAL_FRAME);
			int i = 0;
			if(FL_SIZE > 10) {
				friendScrollbar.draw();
				i = (int)((FL_SIZE-FL_MAXIMUM_DISPLAY)*friendScrollbar.getScrollPercentage());
			}
			Friend friend;
			float y = Y_SOCIAL_FRAME+82*Mideas.getDisplayYFactor();
			float yShift = 32*Mideas.getDisplayYFactor();
			while(i < Mideas.joueur1().getFriendList().size()) {
				friend = Mideas.joueur1().getFriendList().get(i);
				if(friend.isOnline()) {
					Draw.drawQuad(Sprites.friend_note_online, X_SOCIAL_FRAME+30*Mideas.getDisplayXFactor(), y+5);
					TTF2.friendName.drawStringShadow(X_SOCIAL_FRAME+40*Mideas.getDisplayXFactor(), y, friend.getName(), YELLOW, Color.black, 1, 0, 0);
					TTF2.friendName.drawStringShadow(X_SOCIAL_FRAME+40*Mideas.getDisplayXFactor()+TTF2.friendName.getWidth(friend.getName()), y, friend.getAreaText(), Color.white, Color.black, 1, 0, 0);
					TTF2.friendInfos.drawStringShadow(X_SOCIAL_FRAME+40*Mideas.getDisplayXFactor(), y+18*Mideas.getDisplayYFactor(), friend.getInfosText(), Color.white, Color.black, 1, 0, 0);
				}
				else {
					Draw.drawQuad(Sprites.friend_note_offline, X_SOCIAL_FRAME+30*Mideas.getDisplayXFactor(), y+5);
					TTF2.friendName.drawStringShadow(X_SOCIAL_FRAME+40*Mideas.getDisplayXFactor(), y, friend.getName(), GREY, Color.black, 1, 0, 0);
					TTF2.friendName.drawStringShadow(X_SOCIAL_FRAME+40*Mideas.getDisplayXFactor()+TTF2.friendName.getWidth(friend.getName()), y, " - offline", GREY, Color.black, 1, 0, 0);
					TTF2.friendInfos.drawStringShadow(X_SOCIAL_FRAME+40*Mideas.getDisplayXFactor(), y+18*Mideas.getDisplayYFactor(), "Unknwon", Color.white, Color.black, 1, 0, 0);
				}
				if(selectedFriend == friend || hoveredFriend == i) {
					if(FL_SIZE > FL_MAXIMUM_DISPLAY) {
						Draw.drawQuadBlend(Sprites.friend_border, X_SOCIAL_FRAME+20*Mideas.getDisplayXFactor(), y, 335*Mideas.getDisplayXFactor(), 34*Mideas.getDisplayYFactor());
					}
					else {
						Draw.drawQuadBlend(Sprites.friend_border, X_SOCIAL_FRAME+20*Mideas.getDisplayXFactor(), y, 355*Mideas.getDisplayXFactor(), 34*Mideas.getDisplayYFactor());
					}
				}
				i++;
				y+= yShift;
				if(y >= Y_SOCIAL_FRAME+400*Mideas.getDisplayYFactor()) {
					break;
				}
			}
			deleteFriendButton.draw();
			addFriendButton.draw();
			sendMessageFriendButton.draw();
			invInParty.draw();
		}
		else {
			Draw.drawQuad(Sprites.ignore_frame, X_SOCIAL_FRAME, Y_SOCIAL_FRAME+1);
			ignorePlayer.draw();
			removeIgnorePlayer.draw();
			int i = 0;
			float x = X_SOCIAL_FRAME+32*Mideas.getDisplayXFactor();
			float y = Y_SOCIAL_FRAME+80*Mideas.getDisplayYFactor();
			float yShift = 17.6f*Mideas.getDisplayYFactor();
			int IGNORE_LIST_SIZE = Mideas.joueur1().getIgnoreList().size();
			if(IGNORE_LIST_SIZE > IL_MAXIMUM_DISPLAY) {
				ignoreScrollbar.draw();
				i = (int)((IGNORE_LIST_SIZE-IL_MAXIMUM_DISPLAY)*ignoreScrollbar.getScrollPercentage());
			}
			while(i < IGNORE_LIST_SIZE) {
				Ignore ignore = Mideas.joueur1().getIgnoreList().get(i);
				TTF2.ignoreName.drawStringShadow(x, y, ignore.getName(), YELLOW, Color.black, 1, 0, 0);
				if(hoveredIgnore == i || ignore == selectedIgnore) {
					if(IGNORE_LIST_SIZE > IL_MAXIMUM_DISPLAY) {
						Draw.drawQuadBlend(Sprites.friend_border, x-7*Mideas.getDisplayXFactor(), y, 328*Mideas.getDisplayXFactor(), 18*Mideas.getDisplayYFactor());
					}
					else {
						Draw.drawQuadBlend(Sprites.friend_border, x-7*Mideas.getDisplayXFactor(), y, 343*Mideas.getDisplayXFactor(), 18*Mideas.getDisplayYFactor());
					}
				}
				i++;
				y+= yShift;
				if(y >= Y_SOCIAL_FRAME+430*Mideas.getDisplayYFactor()) {
					break;
				}
			}
		}
		friendFrameTab.draw();
		ignoreFrameTab.draw();
	}
	
	public static boolean mouseEvent() {
		if(friendFrameTab.event()) return true;
		if(ignoreFrameTab.event()) return true;
		if(friend_tab_active) {
			hoveredFriend = -1;
			int i = 0;
			float y = Y_SOCIAL_FRAME+80*Mideas.getDisplayYFactor();
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
			if(Mideas.joueur1().getFriendList().size() > FL_MAXIMUM_DISPLAY) {
				friendScrollbar.event();
			}
		}
		else {
			int i = 0;
			float x = X_SOCIAL_FRAME+25*Mideas.getDisplayXFactor();
			float y = Y_SOCIAL_FRAME+80*Mideas.getDisplayYFactor();
			float yShift = 17.6f*Mideas.getDisplayYFactor();
			int IGNORE_LIST_SIZE = Mideas.joueur1().getIgnoreList().size();
			if(IGNORE_LIST_SIZE > IL_MAXIMUM_DISPLAY) {
				ignoreScrollbar.event();
				i = (int)((IGNORE_LIST_SIZE-IL_MAXIMUM_DISPLAY)*ignoreScrollbar.getScrollPercentage());
			}
			hoveredIgnore = -1;
			while(i < IGNORE_LIST_SIZE) {
				if(Mideas.mouseX() >= x && Mideas.mouseX() <= x+350*Mideas.getDisplayXFactor() && Mideas.mouseY() >= y && Mideas.mouseY() <= y+yShift) {
					hoveredIgnore = i;
				}
				i++;
				y+= yShift;
				if(y >= Y_SOCIAL_FRAME+430*Mideas.getDisplayYFactor()) {
					break;
				}
			}
			if(hoveredIgnore != -1) {
				if(Mouse.getEventButtonState()) {
					if(Mouse.getEventButton() == 0) {
						leftButtonDownIgnore = hoveredIgnore;
					}
				}
			}
			if(!Mouse.getEventButtonState()) {
				if(Mouse.getEventButton() == 0) {
					if(hoveredIgnore != -1 && leftButtonDownIgnore == hoveredIgnore) {
						selectedIgnore = Mideas.joueur1().getIgnoreList().get(hoveredIgnore);
					}
					else {
						leftButtonDownIgnore = -1;
					}
				}
			}
			if(removeIgnorePlayer.event()) return true;
			if(ignorePlayer.event()) return true;
		}
		return false;
	}
	
	private static boolean isHover(float y) {
		return Mideas.mouseX() >= X_SOCIAL_FRAME+20*Mideas.getDisplayXFactor() && Mideas.mouseX() <= X_SOCIAL_FRAME+(20+343)*Mideas.getDisplayXFactor() && Mideas.mouseY() >= y && Mideas.mouseY() <= y+Sprites.friend_border.getImageHeight()*Mideas.getDisplayYFactor();
	}
	
	public static void updateSize() {
		deleteFriendButton.update(X_SOCIAL_FRAME+17*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+437*Mideas.getDisplayYFactor(), FRIEND_BUTTON_LENGTH*Mideas.getDisplayXFactor(), FRIEND_BUTTON_HEIGHT*Mideas.getDisplayXFactor());
		addFriendButton.update(X_SOCIAL_FRAME+17*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+410*Mideas.getDisplayYFactor(), FRIEND_BUTTON_LENGTH*Mideas.getDisplayXFactor(), FRIEND_BUTTON_HEIGHT*Mideas.getDisplayXFactor());
		sendMessageFriendButton.update(X_SOCIAL_FRAME+241*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+410*Mideas.getDisplayYFactor(), FRIEND_BUTTON_LENGTH*Mideas.getDisplayXFactor(), FRIEND_BUTTON_HEIGHT*Mideas.getDisplayXFactor());
		invInParty.update(X_SOCIAL_FRAME+241*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+437*Mideas.getDisplayYFactor(), FRIEND_BUTTON_LENGTH*Mideas.getDisplayXFactor(), FRIEND_BUTTON_HEIGHT*Mideas.getDisplayXFactor());
		friendFrameTab.update(X_SOCIAL_FRAME+73*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+45*Mideas.getDisplayYFactor(), 57*Mideas.getDisplayXFactor(), 32*Mideas.getDisplayYFactor());
		ignoreFrameTab.update(X_SOCIAL_FRAME+135*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+45*Mideas.getDisplayYFactor(), 77*Mideas.getDisplayXFactor(), 32*Mideas.getDisplayYFactor());
		ignorePlayer.update(X_SOCIAL_FRAME+18*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+439*Mideas.getDisplayYFactor(), IGNORE_BUTTON_LENGTH*Mideas.getDisplayXFactor(), IGNORE_BUTTON_HEIGHT*Mideas.getDisplayXFactor());
		removeIgnorePlayer.update(X_SOCIAL_FRAME+241*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+439*Mideas.getDisplayYFactor(), IGNORE_BUTTON_LENGTH*Mideas.getDisplayXFactor(), IGNORE_BUTTON_HEIGHT*Mideas.getDisplayXFactor());
		friendScrollbar.update(X_SOCIAL_FRAME+358*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+77*Mideas.getDisplayYFactor(), 311*Mideas.getDisplayYFactor(), 400*Mideas.getDisplayXFactor(), 307*Mideas.getDisplayYFactor(), 32*Mideas.getDisplayYFactor());
		ignoreScrollbar.update(X_SOCIAL_FRAME+358*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+77*Mideas.getDisplayYFactor(), 341*Mideas.getDisplayYFactor(), 400*Mideas.getDisplayXFactor(), 350*Mideas.getDisplayYFactor(), 17.6f*Mideas.getDisplayYFactor());
	}
}
