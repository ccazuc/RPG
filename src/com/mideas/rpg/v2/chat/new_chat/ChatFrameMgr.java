package com.mideas.rpg.v2.chat.new_chat;

import java.util.ArrayList;

public class ChatFrameMgr
{
	
	private final ArrayList<ChatFrame> chatFrameList;
	
	public ChatFrameMgr()
	{
		this.chatFrameList = new ArrayList<ChatFrame>();
	}
	
	public void drawChatFrame()
	{
		for (int i = 0; i < this.chatFrameList.size(); ++i)
			this.chatFrameList.get(i).draw();
	}
	
	public boolean mouseEventChatFrame()
	{
		for (int i = 0; i < this.chatFrameList.size(); ++i)
			if (this.chatFrameList.get(i).mouseEvent())
				return (true);
		return (false);
	}
	
	public boolean keyboardEventChatFrame()
	{
		for (int i = 0; i < this.chatFrameList.size(); ++i)
			if (this.chatFrameList.get(i).keyboardEvent())
				return (true);
		return (false);
	}
	
	public void createDefaultChatFrame()
	{
		
	}
	
	public ArrayList<ChatFrame> getChatFrameList()
	{
		return (this.chatFrameList);
	}
	
	public void addChatFrame(ChatFrame frame)
	{
		this.chatFrameList.add(frame);
	}
	
	public void removeChatFrame(ChatFrame frame)
	{
		for (int i = 0; i < this.chatFrameList.size(); ++i)
		{
			if (this.chatFrameList.get(i) == frame)
			{
				this.chatFrameList.remove(i);
				return;
			}
		}
	}
}
