package com.mideas.rpg.v2.hud.social;

import org.lwjgl.input.Mouse;
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

import static com.mideas.rpg.v2.hud.social.SocialFrame.Y_SOCIAL_FRAME;
import static com.mideas.rpg.v2.hud.social.SocialFrame.X_SOCIAL_FRAME;

public class FriendsFrame {

	private final static Color YELLOW = Color.decode("#FFC700");
	private final static Color GREY = Color.decode("#999999");
	static Friend selectedFriend;
	private static int hoveredFriend = -1;
	private static int leftButtonDownFriend = -1;
	private static boolean friendInit;
	private final static int BUTTON_LENGTH = 145;
	private final static int BUTTON_HEIGHT = 25;
	private static Button deleteFriendButton = new Button(X_SOCIAL_FRAME+17*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+437*Mideas.getDisplayYFactor(), BUTTON_LENGTH*Mideas.getDisplayXFactor(), BUTTON_HEIGHT*Mideas.getDisplayXFactor(), "Delete", 12, 1) {
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
	private static Button addFriendButton = new Button(X_SOCIAL_FRAME+17*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+410*Mideas.getDisplayYFactor(), BUTTON_LENGTH*Mideas.getDisplayXFactor(), BUTTON_HEIGHT*Mideas.getDisplayXFactor(), "Add friend", 12, 1) {
		@Override
		public void eventButtonClick() {
			Interface.setAddFriendStatus(true);
		}
	};
	private static Button sendMessageFriendButton = new Button(X_SOCIAL_FRAME+241*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+410*Mideas.getDisplayYFactor(), BUTTON_LENGTH*Mideas.getDisplayXFactor(), BUTTON_HEIGHT*Mideas.getDisplayXFactor(), "Send message", 12, 1) {
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
	private static Button invInParty = new Button(X_SOCIAL_FRAME+241*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+437*Mideas.getDisplayYFactor(), BUTTON_LENGTH*Mideas.getDisplayXFactor(), BUTTON_HEIGHT*Mideas.getDisplayXFactor(), "Inv. in party", 12 , 1) {
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
		Draw.drawQuad(Sprites.friend_frame, X_SOCIAL_FRAME, Y_SOCIAL_FRAME);
		int i = 0;
		Friend friend;
		float y = Y_SOCIAL_FRAME+80*Mideas.getDisplayYFactor();
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
				Draw.drawQuadBlend(Sprites.friend_border, X_SOCIAL_FRAME+20*Mideas.getDisplayXFactor(), y, 343*Mideas.getDisplayXFactor(), 34*Mideas.getDisplayYFactor());
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
		return false;
	}
	
	private static boolean isHover(float y) {
		return Mideas.mouseX() >= X_SOCIAL_FRAME+20*Mideas.getDisplayXFactor() && Mideas.mouseX() <= X_SOCIAL_FRAME+(20+343)*Mideas.getDisplayXFactor() && Mideas.mouseY() >= y && Mideas.mouseY() <= y+Sprites.friend_border.getImageHeight()*Mideas.getDisplayYFactor();
	}
	
	public static void updateSize() {
		deleteFriendButton.update(X_SOCIAL_FRAME+17*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+437*Mideas.getDisplayYFactor(), BUTTON_LENGTH*Mideas.getDisplayXFactor(), BUTTON_HEIGHT*Mideas.getDisplayXFactor());
		addFriendButton.update(X_SOCIAL_FRAME+17*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+410*Mideas.getDisplayYFactor(), BUTTON_LENGTH*Mideas.getDisplayXFactor(), BUTTON_HEIGHT*Mideas.getDisplayXFactor());
		sendMessageFriendButton.update(X_SOCIAL_FRAME+241*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+410*Mideas.getDisplayYFactor(), BUTTON_LENGTH*Mideas.getDisplayXFactor(), BUTTON_HEIGHT*Mideas.getDisplayXFactor());
		invInParty.update(X_SOCIAL_FRAME+241*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+437*Mideas.getDisplayYFactor(), BUTTON_LENGTH*Mideas.getDisplayXFactor(), BUTTON_HEIGHT*Mideas.getDisplayXFactor());
	}
}
