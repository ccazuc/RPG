package com.mideas.rpg.v2.connection;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.util.HashMap;

import com.mideas.rpg.v2.Interface;
import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.command.Command;
import com.mideas.rpg.v2.command.CommandCreateCharacter;
import com.mideas.rpg.v2.command.CommandDeleteCharacter;
import com.mideas.rpg.v2.command.CommandLoadBagItems;
import com.mideas.rpg.v2.command.CommandLoadCharacter;
import com.mideas.rpg.v2.command.CommandLoadEquippedItems;
import com.mideas.rpg.v2.command.CommandLogin;
import com.mideas.rpg.v2.command.CommandSelectScreenLoadCharacters;
import com.mideas.rpg.v2.command.item.CommandGem;
import com.mideas.rpg.v2.command.item.CommandPotion;
import com.mideas.rpg.v2.command.item.CommandStuff;
import com.mideas.rpg.v2.command.item.CommandWeapon;
import com.mideas.rpg.v2.game.item.Item;
import com.mideas.rpg.v2.hud.LoginScreen;
import static com.mideas.rpg.v2.connection.PacketID.*;

public class ConnectionManager {

	private static Connection connection;
	private static SocketChannel socket;
	private static HashMap<Integer, Command> commandList = new HashMap<Integer, Command>();
	private static HashMap<Integer, Item> itemRequested = new HashMap<Integer, Item>();
	private static final String IP = "127.0.0.1";
	private static final int PORT = 5720;
	private static boolean init;
	
	private static void initPacket() {
		commandList.put((int)LOGIN, new CommandLogin());
		commandList.put((int)SELECT_SCREEN_LOAD_CHARACTERS, new CommandSelectScreenLoadCharacters());
		commandList.put((int)CREATE_CHARACTER, new CommandCreateCharacter());
		commandList.put((int)DELETE_CHARACTER, new CommandDeleteCharacter());
		commandList.put((int)LOAD_EQUIPPED_ITEMS, new CommandLoadEquippedItems());
		commandList.put((int)LOAD_BAG_ITEMS, new CommandLoadBagItems());
		commandList.put((int)LOAD_CHARACTER, new CommandLoadCharacter());
		commandList.put((int)STUFF, new CommandStuff());
		commandList.put((int)WEAPON, new CommandWeapon());
		commandList.put((int)GEM, new CommandGem());
		commandList.put((int)POTION, new CommandPotion());
	}
	
	public static final boolean connect() {
		if(!init) {
			initPacket();
			init = true;
		}
		try {
			socket = SocketChannel.open();
			socket.socket().connect(new InetSocketAddress(IP, PORT), 5000);
			if(socket.isConnected()) {
				socket.socket().setTcpNoDelay(true);
				socket.configureBlocking(false);
				if(connection == null) {
					connection = new Connection(socket);
				}
				else {
					connection.setSocket(socket);
				}
				return true;
			}
		}
		catch(IOException e) {
			e.printStackTrace();
			LoginScreen.getAlert().setActive();
			LoginScreen.getAlert().setText("Impossible de se connecter.");
		}
		return false;
	}
	
	public static Connection getConnection() {
		return connection;
	}
	
	public static boolean isConnected() {
		if(socket != null) {
			return socket.isConnected();
		}
		return false;
	}
	
 	public static void close() {
 		if(connection != null) {
 			connection.close();
 		}
		socket = null;
		connection = null;
	}
	
	public static void read() {
		if(socket.isConnected()) {
			try {
				if(connection.read() == 1) {
					readPacket();
				}
			} 
			catch (IOException e) {
				e.printStackTrace();
				close();
				Interface.setHasLoggedIn(false);
				Mideas.setJoueur1Null();
				Mideas.setAccountId(0);
				LoginScreen.getAlert().setActive();
				LoginScreen.getAlert().setText("Vous avez été déconnecté.");
				return;
			}
		}
	}
	
	private static void readPacket() {
		while(connection != null && connection.hasRemaining()) {
			byte packetId = connection.readByte();
			if(commandList.containsKey((int)packetId)) {
				commandList.get((int)packetId).read();
			}
			else {
				System.out.println("Unknown packet: "+(int)packetId);
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
