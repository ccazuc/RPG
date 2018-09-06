package com.mideas.rpg.v2.chat.new_chat;

import java.util.ArrayList;

import com.mideas.rpg.v2.chat.MessageType;

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
		ChatFrame frame = new ChatFrame("CHAT_FRAME1", ChatFrame.DEFAULT_X, ChatFrame.DEFAULT_Y, ChatFrame.DEFAULT_WIDTH, ChatFrame.DEFAULT_HEIGHT, false);
		ChatFrameTab tab = new ChatFrameTab(frame, "WINDOW1TAB1", "General");
		tab.addAcceptedMessageType(MessageType.GUILD);
		tab.addAcceptedMessageType(MessageType.SAY);
		tab.addAcceptedMessageType(MessageType.PARTY);
		tab.addAcceptedMessageType(MessageType.PARTY_LEADER);
		tab.addAcceptedMessageType(MessageType.CHANNEL);
		frame.addChatFrameTab(tab);
		this.chatFrameList.add(frame);
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
