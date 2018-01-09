package com.mideas.rpg.v2.stresstest;

import static com.mideas.rpg.v2.connection.PacketID.CHARACTER_LOGIN;
import static com.mideas.rpg.v2.connection.PacketID.LOGIN;
import static com.mideas.rpg.v2.connection.PacketID.LOGIN_REALM;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.util.HashMap;

import com.mideas.rpg.v2.command.CommandLogout;
import com.mideas.rpg.v2.connection.Connection;
import com.mideas.rpg.v2.connection.ConnectionManager;

public class StresstestConnectionMgr {

	private Connection worldServerConnection;
	private Connection authServerConnection;
	private SocketChannel authSocket;
	private SocketChannel socket;
	private final Stresstest client;
	private static HashMap<Short, Command> commandList = new HashMap<Short, Command>();
	
	public static void initPacket() {
		commandList.put(LOGIN, new CommandLogin());
		commandList.put(CHARACTER_LOGIN, new CommandLoadCharacter());
		commandList.put(LOGIN_REALM, new CommandLoginRealm());
		/*commandList.put(LOGOUT, new CommandLogout());
		commandList.put(SELECT_SCREEN_LOAD_CHARACTERS, new CommandSelectScreenLoadCharacters());
		commandList.put(CREATE_CHARACTER, new CommandCreateCharacter());
		commandList.put(DELETE_CHARACTER, new CommandDeleteCharacter());
		commandList.put(LOAD_EQUIPPED_ITEMS, new CommandLoadEquippedItems());
		commandList.put(LOAD_BAG_ITEMS, new CommandLoadBagItems());
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
		commandList.put(AUCTION, new CommandAuction());*/
	}
	
	public StresstestConnectionMgr(Stresstest client)
	{
		this.client = client;
	}

	public final boolean connectAuthServer() {
		try {
			this.authSocket = SocketChannel.open();
			this.authSocket.socket().connect(new InetSocketAddress(ConnectionManager.IP, ConnectionManager.AUTH_PORT), 5000);
			if(this.authSocket.isConnected()) {
				this.authSocket.socket().setTcpNoDelay(true);
				this.authSocket.configureBlocking(false);
				if(this.authServerConnection == null) {
					this.authServerConnection = new Connection(this.authSocket);
				}
				else {
					this.authServerConnection.setSocket(this.socket);
				}
				return true;
			}
		}
		catch(IOException e) {
			
		}
		return false;
	}
	
	public final boolean connectWorldServer(int port) {
		try {
			this.socket = SocketChannel.open();
			this.socket.socket().connect(new InetSocketAddress(ConnectionManager.IP, port), 5000);
			if(this.socket.isConnected()) {
				this.socket.socket().setTcpNoDelay(true);
				this.socket.configureBlocking(false);
				if(this.worldServerConnection == null) {
					this.worldServerConnection = new Connection(this.socket);
				}
				else {
					this.worldServerConnection.setSocket(this.socket);
				}
				return true;
			}
		}
		catch(IOException e) {
			System.out.println("One stresstest client failed to connect to world server");
		}
		return false;
	}
	
	public Connection getWorldServerConnection() {
		return this.worldServerConnection;
	}
	
	public Connection getAuthConnection() {
		return this.authServerConnection;
	}
	
	public boolean isConnected() {
		if(this.socket != null) {
			return this.socket.isConnected();
		}
		return false;
	}
	
	public boolean isAuthServerConnected() {
		if(this.authSocket != null) {
			return this.authSocket.isConnected();
		}
		return false;
	}
	
 	public void close() {
 		if(this.worldServerConnection != null) {
 			this.worldServerConnection.close();
 		}
		this.socket = null;
		this.worldServerConnection = null;
	}
	
 	public void closeAuth() {
 		if(this.authServerConnection != null) {
 			//CommandLogout.write(this);
 			this.authServerConnection.close();
 		}
		this.authSocket = null;
		this.authServerConnection = null;
	}
 	
 	public void authCloseRequested() {
 		if(this.authServerConnection != null) {
 			this.authServerConnection.close();
 		}
		this.authSocket = null;
		this.authServerConnection = null;
 	}
	
	public void read() {
		if(this.worldServerConnection != null && this.socket.isConnected()) {
			try {
				if(this.worldServerConnection.read() == 1) {
					readPacket();
				}
			} 
			catch (IOException e) {
				//e.printStackTrace();
				return;
			}
		}
	}
	
	public void readAuthServer() {
		if(this.authServerConnection != null && this.authSocket.isConnected()) {
			try {
				if(this.authServerConnection.read() == 1)
					readAuthPacket();
			} 
			catch (IOException e) {
				//e.printStackTrace();
				return;
			}
		}
	}
	
	public void disconnect() {
		CommandLogout.write();
		close();
		closeAuth();
	}
	
	private void readAuthPacket() {
		while(this.authServerConnection != null && this.authServerConnection.hasRemaining() && this.authServerConnection.rBufferRemaining() > 4) {
			int packetLength = this.authServerConnection.readInt();
			if(this.authServerConnection.rBufferRemaining()+4 < packetLength) {
				this.authServerConnection.rBufferSetPosition(this.authServerConnection.rBufferPosition()-4);
				return;
			}
			short packetId = this.authServerConnection.readShort();
			System.out.println("AUTH Stresstest " + this.client.getId() + " packetId read: " + packetId + " buffer position : " + this.authServerConnection.rBufferPosition() + " packetLength " + packetLength + " contains : " + commandList.containsKey(packetId));
			System.out.println("Remaining : " + this.authServerConnection.rBufferRemaining());
			if(commandList.containsKey(packetId))
				commandList.get(packetId).read(this.client);
			else
				this.authServerConnection.rBufferSetPosition(this.authServerConnection.rBufferPosition() + packetLength - 6);
		}
	}
	
	private void readPacket() {
		while(this.worldServerConnection != null && this.worldServerConnection.hasRemaining() && this.worldServerConnection.rBufferRemaining() > 4) {
			int packetLength = this.worldServerConnection.readInt();
			if(this.worldServerConnection.rBufferRemaining()+4 < packetLength) {
				this.worldServerConnection.rBufferSetPosition(this.worldServerConnection.rBufferPosition()-4);
				return;
			}
			short packetId = this.worldServerConnection.readShort();
			//System.out.println("WORLD Stresstest " + this.client.getId() + " packetId read: " + packetId + " buffer position : " + this.worldServerConnection.rBufferPosition() + " packetLength " + packetLength + " contains : " + commandList.containsKey(packetId));
			//System.out.println("Remaining : " + this.worldServerConnection.rBufferRemaining());
			if(commandList.containsKey(packetId))
				commandList.get(packetId).read(this.client);
			else
				this.worldServerConnection.rBufferSetPosition(this.worldServerConnection.rBufferPosition() + packetLength - 6);
		}
		if(this.worldServerConnection != null)
			this.worldServerConnection.clearEmptyRBuffer();
	}
}
