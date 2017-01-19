package com.mideas.rpg.v2.hud.social.discussion;

import java.util.ArrayList;

import com.mideas.rpg.v2.Mideas;

public class DiscussionFrame {

	private final ArrayList<ChatChannelCategoryButton> categoryList = new ArrayList<ChatChannelCategoryButton>();
	private ChatChannelButton selectedChannel;
	protected float x;
	private float y;
	protected float yDraw;
	protected float yShift;
	
	public DiscussionFrame(float x, float y) {
		this.x = (int)x;
		this.y = (int)y;
		this.yShift = 25*Mideas.getDisplayYFactor();
	}
	
	public DiscussionFrame() {}
	
	public void draw() {
		int i = 0;
		this.yDraw = this.y;
		//draw discussion frame
		while(i < this.categoryList.size()) {
			this.categoryList.get(i).draw();
			i++;
		}
	}
	
	protected ChatChannelButton getSelectedChannel() {
		return this.selectedChannel;
	}
	
	protected void setSelectedChannel(ChatChannelButton channel) {
		this.selectedChannel = channel;
	}
	
	public void updateSize(float x, float y) {
		this.x = (int)x;
		this.y = (int)y;
	}
}
