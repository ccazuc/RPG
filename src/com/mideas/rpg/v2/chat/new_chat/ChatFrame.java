package com.mideas.rpg.v2.chat.new_chat;

import java.util.ArrayList;

import com.mideas.rpg.v2.chat.Message;

public class ChatFrame
{
	private final ArrayList<ChatFrameTab> tabList;
	private ChatFrameTab activeFrameTab;
	
	public ChatFrame()
	{
		this.tabList = new ArrayList<ChatFrameTab>();
	}

	public void addMessage(Message message)
	{
		for (int i = 0; i < this.tabList.size(); ++i)
			if (this.tabList.get(i).acceptMessageType(message.getType()))
					this.tabList.get(i).addMessage(message);
	}
	
	public void draw()
	{
		
	}
	
	public ChatFrameTab getActiveFrameTab()
	{
		return (this.activeFrameTab);
	}
}
