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
