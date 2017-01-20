package com.mideas.rpg.v2.hud.social.discussion;

import static com.mideas.rpg.v2.hud.social.SocialFrame.Y_SOCIAL_FRAME;
import static com.mideas.rpg.v2.hud.social.SocialFrame.X_SOCIAL_FRAME;

import com.mideas.rpg.v2.chat.channel.ChatChannel;

public class DiscussionFrame {

	private final static DiscussionFrameUI frame = new DiscussionFrameUI(X_SOCIAL_FRAME, Y_SOCIAL_FRAME);
	private static boolean shouldUpdate;
	
	public static void draw() {
		updateSize();
		frame.draw();
	}
	
	public static boolean mouseEvent() {
		return frame.mouseEvent();
	}
	
	public static void addChannel(ChatChannel channel) {
		frame.addChannel(channel);
	}
	
	public static void removeChannel(ChatChannel channel) {
		frame.removeChannel(channel);
	}
	
	private static void updateSize() {
		if(!shouldUpdate) {
			return;
		}
		frame.updateSize(X_SOCIAL_FRAME, Y_SOCIAL_FRAME);
		shouldUpdate = false;
	}
	 
	public static void shouldUpdate() {
		shouldUpdate = true;
	}
}
