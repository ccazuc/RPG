package com.mideas.rpg.v2.stresstest;

import java.util.ArrayList;
import java.util.HashMap;

import com.mideas.rpg.v2.chat.MessageType;
import com.mideas.rpg.v2.command.CommandLogout;
import com.mideas.rpg.v2.connection.PacketID;
import com.mideas.rpg.v2.utils.Hash;

public class StresstestMgr {

	private final static ArrayList<Stresstest> clientList = new ArrayList<Stresstest>();
	private final static HashMap<Short, Command> commandMap = new HashMap<Short, Command>();
	private static int currentClientId = 1;
	
	public static void initCommandMap()
	{
		commandMap.put(PacketID.LOGIN, new CommandLogin());
		commandMap.put(PacketID.LOGIN_REALM, new CommandLoginRealm());
		commandMap.put(PacketID.CHARACTER_LOGIN, new CommandLoadCharacter());
	}
	
	public static void clearClient()
	{
		int i = -1;
		while (++i < clientList.size())
			CommandLogout.write(clientList.get(i));
		clientList.clear();
		currentClientId = 1;
	}
	
	public static void addClient(int number)
	{
		int i = -1;
		Stresstest client;
		while (++i < number)
		{
			client = new Stresstest(generateClientId());
			client.getConnectionMgr().connectAuthServer();
			CommandLogin.write(client, "test" + currentClientId, Hash.hash("test"));
			clientList.add(client);
		}
	}
	
	public static void removeClient(int number)
	{
		System.out.println("Client list size before: " + clientList.size());
		int i = clientList.size();
		while (--i >= 0 && --number >= 0 && -- currentClientId >= -1)
		{
			CommandLogout.write(clientList.get(i));
			clientList.remove(i);
		}
		System.out.println("Client list size after: " + clientList.size());
	}
	
	public static void readClient()
	{
		int i = -1;
		while (++i < clientList.size())
		{
			clientList.get(i).getConnectionMgr().readAuthServer();
			clientList.get(i).getConnectionMgr().read();
		}
	}
	
	public static void sendMessage(String msg)
	{
		int i = -1;
		while (++i < clientList.size())
			CommandSendMessage.write(clientList.get(i), msg, MessageType.SAY);
	}
	
	public static int generateClientId()
	{
		return (++currentClientId);
	}
}
