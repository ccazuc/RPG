package com.mideas.rpg.v2.connection;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.util.HashMap;

import com.mideas.rpg.v2.Interface;
import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.chat.ChatFrame;
import com.mideas.rpg.v2.command.Command;
import com.mideas.rpg.v2.command.CommandAddItem;
import com.mideas.rpg.v2.command.CommandCreateCharacter;
import com.mideas.rpg.v2.command.CommandDeleteCharacter;
import com.mideas.rpg.v2.command.CommandFriend;
import com.mideas.rpg.v2.command.CommandLoadBagItems;
import com.mideas.rpg.v2.command.CommandLoadCharacter;
import com.mideas.rpg.v2.command.CommandLoadEquippedItems;
import com.mideas.rpg.v2.command.CommandLoadStats;
import com.mideas.rpg.v2.command.CommandLogin;
import com.mideas.rpg.v2.command.CommandLoginRealm;
import com.mideas.rpg.v2.command.CommandParty;
import com.mideas.rpg.v2.command.CommandPing;
import com.mideas.rpg.v2.command.CommandSelectScreenLoadCharacters;
import com.mideas.rpg.v2.command.CommandSendRealmList;
import com.mideas.rpg.v2.command.CommandSendSingleBagItem;
import com.mideas.rpg.v2.command.CommandTrade;
import com.mideas.rpg.v2.command.CommandUpdateStats;
import com.mideas.rpg.v2.command.chat.CommandGet;
import com.mideas.rpg.v2.command.chat.CommandListPlayer;
import com.mideas.rpg.v2.command.chat.CommandNotAllowed;
import com.mideas.rpg.v2.command.chat.CommandPlayerNotFound;
import com.mideas.rpg.v2.command.chat.CommandSendMessage;
import com.mideas.rpg.v2.command.item.CommandGem;
import com.mideas.rpg.v2.command.item.CommandPotion;
import com.mideas.rpg.v2.command.item.CommandStuff;
import com.mideas.rpg.v2.command.item.CommandWeapon;
import com.mideas.rpg.v2.game.item.Item;
import com.mideas.rpg.v2.hud.LoginScreen;
import static com.mideas.rpg.v2.connection.PacketID.*;

public class ConnectionManager {

	private static Connection worldServerConnection;
	private static Connection authServerConnection;
	private static SocketChannel authSocket;
	private static SocketChannel socket;
	private static HashMap<Integer, Command> commandList = new HashMap<Integer, Command>();
	private static HashMap<Integer, Item> itemRequested = new HashMap<Integer, Item>();
	private static final String IP = "127.0.0.1";
	private static final int AUTH_PORT = 5725;
	private static boolean init;
	
	private static void initPacket() {
		commandList.put((int)LOGIN, new CommandLogin());
		commandList.put((int)SELECT_SCREEN_LOAD_CHARACTERS, new CommandSelectScreenLoadCharacters());
		commandList.put((int)CREATE_CHARACTER, new CommandCreateCharacter());
		commandList.put((int)DELETE_CHARACTER, new CommandDeleteCharacter());
		commandList.put((int)LOAD_EQUIPPED_ITEMS, new CommandLoadEquippedItems());
		commandList.put((int)LOAD_BAG_ITEMS, new CommandLoadBagItems());
		commandList.put((int)LOAD_CHARACTER, new CommandLoadCharacter());
		commandList.put((int)PING, new CommandPing());
		commandList.put((int)STUFF, new CommandStuff());
		commandList.put((int)WEAPON, new CommandWeapon());
		commandList.put((int)GEM, new CommandGem());
		commandList.put((int)ADD_ITEM, new CommandAddItem());
		commandList.put((int)POTION, new CommandPotion());
		commandList.put((int)SEND_SINGLE_BAG_ITEM, new CommandSendSingleBagItem());
		commandList.put((int)CHAT_LIST_PLAYER, new CommandListPlayer());
		commandList.put((int)LOAD_STATS, new CommandLoadStats());
		commandList.put((int)CHAT_GET, new CommandGet());
		commandList.put((int)CHAT_NOT_ALLOWED, new CommandNotAllowed());
		commandList.put((int)UPDATE_STATS, new CommandUpdateStats());
		commandList.put((int)TRADE, new CommandTrade());
		commandList.put((int)FRIEND, new CommandFriend());
		commandList.put((int)SEND_REALM_LIST, new CommandSendRealmList());
		commandList.put((int)LOGIN_REALM, new CommandLoginRealm());
		commandList.put((int)PLAYER_NOT_FOUND, new CommandPlayerNotFound());
		commandList.put((int)SEND_MESSAGE, new CommandSendMessage());
		commandList.put((int)PARTY, new CommandParty());
	}

	public static final boolean connectAuthServer() {
		if(!init) {
			initPacket();
			init = true;
		}
		try {
			authSocket = SocketChannel.open();
			authSocket.socket().connect(new InetSocketAddress(IP, AUTH_PORT), 5000);
			if(authSocket.isConnected()) {
				authSocket.socket().setTcpNoDelay(true);
				authSocket.configureBlocking(false);
				if(authServerConnection == null) {
					authServerConnection = new Connection(authSocket);
				}
				else {
					authServerConnection.setSocket(socket);
				}
				return true;
			}
		}
		catch(IOException e) {
			e.printStackTrace();
			LoginScreen.getAlert().setActive();
			LoginScreen.getAlert().setText("Impossible de se connecter.");
			Interface.setCharacterLoaded(false);
			Interface.closeAllFrame();
			close();
		}
		return false;
	}
	
	public static final boolean connectWorldServer(int port) {
		try {
			socket = SocketChannel.open();
			socket.socket().connect(new InetSocketAddress(IP, port), 5000);
			if(socket.isConnected()) {
				socket.socket().setTcpNoDelay(true);
				socket.configureBlocking(false);
				if(worldServerConnection == null) {
					worldServerConnection = new Connection(socket);
				}
				else {
					worldServerConnection.setSocket(socket);
				}
				return true;
			}
		}
		catch(IOException e) {
			e.printStackTrace();
			LoginScreen.getAlert().setActive();
			LoginScreen.getAlert().setText("Impossible de se connecter.");
			Interface.setCharacterLoaded(false);
			Interface.closeAllFrame();
			close();
		}
		return false;
	}
	
	public static Connection getConnection() {
		return worldServerConnection;
	}
	
	public static Connection getAuthConnection() {
		return authServerConnection;
	}
	
	public static boolean isConnected() {
		if(socket != null) {
			return socket.isConnected();
		}
		return false;
	}
	
	public static boolean isAuthServerConnected() {
		if(authSocket != null) {
			return authSocket.isConnected();
		}
		return false;
	}
	
 	public static void close() {
 		if(worldServerConnection != null) {
 			worldServerConnection.close();
 		}
		socket = null;
		worldServerConnection = null;
	}
	
 	public static void closeAuth() {
 		if(authServerConnection != null) {
 			authServerConnection.close();
 		}
		authSocket = null;
		authServerConnection = null;
	}
	
	public static void read() {
		if(socket.isConnected()) {
			try {
				if(worldServerConnection.read() == 1) {
					readPacket();
				}
			} 
			catch (IOException e) {
				e.printStackTrace();
				close();
				closeAuth();
				Interface.setHasLoggedInToAuth(false);
				Mideas.setJoueur1Null();
				Mideas.setAccountId(0);
				ChatFrame.clearChat();
				LoginScreen.getAlert().setActive();
				LoginScreen.getAlert().setText("Vous avez été déconnecté.");
				return;
			}
		}
	}
	
	public static void readAuthServer() {
		if(authSocket.isConnected()) {
			try {
				if(authServerConnection.read() == 1) {
					readAuthPacket();
				}
			} 
			catch (IOException e) {
				e.printStackTrace();
				closeAuth();
				Interface.setHasLoggedInToAuth(false);
				Mideas.setJoueur1Null();
				Mideas.setAccountId(0);
				ChatFrame.clearChat();
				LoginScreen.getAlert().setActive();
				LoginScreen.getAlert().setText("Vous avez été déconnecté.");
				return;
			}
		}
	}
	
	private static void readAuthPacket() {
		while(authServerConnection != null && authServerConnection.hasRemaining()) {
			byte packetId = authServerConnection.readByte();
			if(commandList.containsKey((int)packetId)) {
				commandList.get((int)packetId).read();
			}
			else {
				System.out.println("Unknown Auth packet: "+(int)packetId);
			}
		}
	}
	
	private static void readPacket() {
		while(worldServerConnection != null && worldServerConnection.hasRemaining()) {
			byte packetId = worldServerConnection.readByte();
			if(commandList.containsKey((int)packetId)) {
				commandList.get((int)packetId).read();
			}
			else {
				System.out.println("Unknown World packet: "+(int)packetId);
			}
		}
	}
	
	public static HashMap<Integer, Command> getCommandList() {
		return commandList;
	}
	
	public static HashMap<Integer, Item> getItemRequested() {
		return itemRequested;
	}
}
