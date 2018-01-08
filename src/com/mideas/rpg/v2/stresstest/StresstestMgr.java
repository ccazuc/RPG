package com.mideas.rpg.v2.stresstest;

import java.util.ArrayList;

import com.mideas.rpg.v2.chat.MessageType;
import com.mideas.rpg.v2.command.chat.CommandSendMessage;

public class StresstestMgr {

	private final static ArrayList<Stresstest> clientList = new ArrayList<Stresstest>();
	
	public static void clearClient()
	{
		clientList.clear();
	}
	
	public static void addClient(int number)
	{
		int i = -1;
		while (++i < number)
			clientList.add(new Stresstest());
	}
	
	public static void sendMessage(String msg)
	{
		int i = -1;
		while (++i < clientList.size())
			CommandSendMessage.write(msg, MessageType.SAY, clientList.get(i).getConnection());
	}
}
