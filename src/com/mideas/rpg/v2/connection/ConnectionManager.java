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
import com.mideas.rpg.v2.command.CommandAura;
import com.mideas.rpg.v2.command.CommandCreateCharacter;
import com.mideas.rpg.v2.command.CommandDeleteCharacter;
import com.mideas.rpg.v2.command.CommandFriend;
import com.mideas.rpg.v2.command.CommandGuild;
import com.mideas.rpg.v2.command.CommandIgnore;
import com.mideas.rpg.v2.command.CommandLoadBagItems;
import com.mideas.rpg.v2.command.CommandLoadCharacter;
import com.mideas.rpg.v2.command.CommandLoadEquippedItems;
import com.mideas.rpg.v2.command.CommandLoadStats;
import com.mideas.rpg.v2.command.CommandLogin;
import com.mideas.rpg.v2.command.CommandLoginRealm;
import com.mideas.rpg.v2.command.CommandLogout;
import com.mideas.rpg.v2.command.CommandLogoutCharacter;
import com.mideas.rpg.v2.command.CommandParty;
import com.mideas.rpg.v2.command.CommandPing;
import com.mideas.rpg.v2.command.CommandSelectScreenLoadCharacters;
import com.mideas.rpg.v2.command.CommandSendPlayer;
import com.mideas.rpg.v2.command.CommandSendRealmList;
import com.mideas.rpg.v2.command.CommandSendRedAlert;
import com.mideas.rpg.v2.command.CommandSendSingleBagItem;
import com.mideas.rpg.v2.command.CommandSendTarget;
import com.mideas.rpg.v2.command.CommandTrade;
import com.mideas.rpg.v2.command.CommandUpdateStats;
import com.mideas.rpg.v2.command.CommandWho;
import com.mideas.rpg.v2.command.chat.CommandChannel;
import com.mideas.rpg.v2.command.chat.CommandDefaultMessage;
import com.mideas.rpg.v2.command.chat.CommandGet;
import com.mideas.rpg.v2.command.chat.CommandListPlayer;
import com.mideas.rpg.v2.command.chat.CommandNotAllowed;
import com.mideas.rpg.v2.command.chat.CommandPlayerNotFound;
import com.mideas.rpg.v2.command.chat.CommandSendMessage;
import com.mideas.rpg.v2.command.item.CommandDeleteItem;
import com.mideas.rpg.v2.command.item.CommandGem;
import com.mideas.rpg.v2.command.item.CommandRequestItem;
import com.mideas.rpg.v2.command.item.CommandSendContainer;
import com.mideas.rpg.v2.command.item.CommandSetItem;
import com.mideas.rpg.v2.command.item.CommandStuff;
import com.mideas.rpg.v2.command.item.CommandWeapon;
import com.mideas.rpg.v2.command.spell.CommandCast;
import com.mideas.rpg.v2.command.spell.CommandSendGCD;
import com.mideas.rpg.v2.command.spell.CommandSendSpellCD;
import com.mideas.rpg.v2.command.spell.CommandSpellUnlocked;
import com.mideas.rpg.v2.files.config.ChatConfigManager;
import com.mideas.rpg.v2.game.WorldServer;
import com.mideas.rpg.v2.hud.LoginScreen;
import com.mideas.rpg.v2.hud.RealmListFrame;
import com.mideas.rpg.v2.hud.SelectScreen;

import static com.mideas.rpg.v2.connection.PacketID.*;

public class ConnectionManager {

	private static Connection worldServerConnection;
	private static Connection authServerConnection;
	private static SocketChannel authSocket;
	private static SocketChannel socket;
	private static HashMap<Short, Command> commandList = new HashMap<Short, Command>();
	private static final String IP = "127.0.0.1";
	private static final int AUTH_PORT = 5725;
	private static boolean init;
	private static short worldLastReadedPacket;
	private static short authLastReadedPacket;
	private static boolean isLoggedOnWorldServer;
	private static WorldServer loggedServer;
	private static WorldServer lastLoggedRealm;
	
	private static void initPacket() {
		commandList.put(LOGIN, new CommandLogin());
		commandList.put(LOGOUT, new CommandLogout());
		commandList.put(SELECT_SCREEN_LOAD_CHARACTERS, new CommandSelectScreenLoadCharacters());
		commandList.put(CREATE_CHARACTER, new CommandCreateCharacter());
		commandList.put(DELETE_CHARACTER, new CommandDeleteCharacter());
		commandList.put(LOAD_EQUIPPED_ITEMS, new CommandLoadEquippedItems());
		commandList.put(LOAD_BAG_ITEMS, new CommandLoadBagItems());
		commandList.put(LOAD_CHARACTER, new CommandLoadCharacter());
		commandList.put(PING, new CommandPing());
		commandList.put(STUFF, new CommandStuff());
		commandList.put(WEAPON, new CommandWeapon());
		commandList.put(GEM, new CommandGem());
		//commandList.put((int)CONTAINER, new CommandContainer());
		commandList.put(ADD_ITEM, new CommandAddItem());
		//commandList.put((int)POTION, new CommandPotion());
		commandList.put(SEND_SINGLE_BAG_ITEM, new CommandSendSingleBagItem());
		commandList.put(CHAT_LIST_PLAYER, new CommandListPlayer());
		commandList.put(LOAD_STATS, new CommandLoadStats());
		commandList.put(CHAT_GET, new CommandGet());
		commandList.put(CHAT_NOT_ALLOWED, new CommandNotAllowed());
		commandList.put(UPDATE_STATS, new CommandUpdateStats());
		commandList.put(TRADE, new CommandTrade());
		commandList.put(FRIEND, new CommandFriend());
		commandList.put(SEND_REALM_LIST, new CommandSendRealmList());
		commandList.put(LOGIN_REALM, new CommandLoginRealm());
		commandList.put(PLAYER_NOT_FOUND, new CommandPlayerNotFound());
		commandList.put(SEND_MESSAGE, new CommandSendMessage());
		commandList.put(PARTY, new CommandParty());
		commandList.put(GUILD, new CommandGuild());
		commandList.put(IGNORE, new CommandIgnore());
		commandList.put(WHO, new CommandWho());
		commandList.put(REQUEST_ITEM, new CommandRequestItem());
		commandList.put(SET_ITEM, new CommandSetItem());
		commandList.put(SEND_RED_ALERT, new CommandSendRedAlert());
		commandList.put(CHAT_DEFAULT_MESSAGE, new CommandDefaultMessage());
		commandList.put(DELETE_ITEM, new CommandDeleteItem());
		commandList.put(SPELL_CAST, new CommandCast());
		commandList.put(SEND_GCD, new CommandSendGCD());
		commandList.put(SEND_SPELL_CD, new CommandSendSpellCD());
		commandList.put(SEND_TARGET, new CommandSendTarget());
		commandList.put(AURA, new CommandAura());
		commandList.put(SEND_PLAYER, new CommandSendPlayer());
		commandList.put(SEND_EQUIPPED_CONTAINER, new CommandSendContainer());
		commandList.put(SPELL_UNLOCKED, new CommandSpellUnlocked());
		commandList.put(CHANNEL, new CommandChannel());
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
				authSocket.socket().setTcpNoDelay(false);
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
			LoginScreen.setAlertButtonOk();
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
				socket.socket().setTcpNoDelay(false);
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
			LoginScreen.setAlertButtonOk();
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
				disconnect();
				return;
			}
		}
	}
	
	public static void readAuthServer() {
		if(authServerConnection != null && authSocket.isConnected()) {
			try {
				if(authServerConnection.read() == 1) {
					readAuthPacket();
				}
			} 
			catch (IOException e) {
				e.printStackTrace();
				disconnect();
				return;
			}
		}
	}
	
	public static void logoutCharacter() {
		ChatConfigManager.saveConfig();
		CommandLogoutCharacter.write();
		Mideas.setJoueur1Null();
		Interface.closeAllFrame();
		Interface.setCharacterLoaded(false);
	}
	
	public static void disconnect() {
		ChatConfigManager.saveConfig();
		CommandLogout.write();
		close();
		closeAuth();
		Interface.setHasLoggedInToAuth(false);
		SelectScreen.setRealmScreenActive(true);
		SelectScreen.resetScreen();
		isLoggedOnWorldServer = false;
		RealmListFrame.clearRealmList();
		Mideas.setJoueur1Null();
		Mideas.setAccountId(0);
		ChatFrame.clearChat();
		LoginScreen.getAlert().setActive();
		LoginScreen.getAlert().setText("You have been disconnected.");
		LoginScreen.setAlertButtonOk();
		LoginScreen.resetMenuState();
	}
	
	private static void readAuthPacket() {
		while(authServerConnection != null && authServerConnection.hasRemaining() && authServerConnection.rBufferRemaining() > 4) {
			int packetLength = authServerConnection.readInt();
			if(authServerConnection.rBufferRemaining()+4 < packetLength) {
				authServerConnection.rBufferSetPosition(authServerConnection.rBufferPosition()-4);
				return;
			}
			short packetId = authServerConnection.readShort();
			if(commandList.containsKey(packetId)) {
				commandList.get(packetId).read();
				authLastReadedPacket = packetId;
			}
			else {
				System.out.println("Unknown Auth packet: "+packetId+", last readed packet: "+authLastReadedPacket);
			}
		}
	}
	
	private static void readPacket() {
		while(worldServerConnection != null && worldServerConnection.hasRemaining() && worldServerConnection.rBufferRemaining() > 4) {
			int packetLength = worldServerConnection.readInt();
			if(worldServerConnection.rBufferRemaining()+4 < packetLength) {
				worldServerConnection.rBufferSetPosition(worldServerConnection.rBufferPosition()-4);
				return;
			}
			short packetId = worldServerConnection.readShort();
			if(commandList.containsKey(packetId)) {
				commandList.get(packetId).read();
				worldLastReadedPacket = packetId;
			}
			else {
				System.out.println("Unknown World packet: "+packetId+", last readed packet: "+worldLastReadedPacket);
			}
		}
	}
	
	public static boolean isLoggedOnWorldServer() {
		return isLoggedOnWorldServer;
	}
	
	public static void setIsLoggedOnWorldServer(boolean we) {
		isLoggedOnWorldServer = we;
	}
	
	public static void setWorldServer(WorldServer server) {
		loggedServer = server;
		if(server != null) {
			lastLoggedRealm = server;
			LoginScreen.setRealmName(server.getRealmName());
		}
	}
	
	public static WorldServer getLastLoggedWorldServer() {
		return lastLoggedRealm;
	}
	 
	public static WorldServer getWorldServer() {
		return loggedServer;
	}
}
