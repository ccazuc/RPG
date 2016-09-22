package com.mideas.rpg.v2.connection;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.util.HashMap;

import com.mideas.rpg.v2.command.Command;
import com.mideas.rpg.v2.command.CommandCreateCharacter;
import com.mideas.rpg.v2.command.CommandDeleteCharacter;
import com.mideas.rpg.v2.command.CommandLoadEquippedItems;
import com.mideas.rpg.v2.command.CommandLogin;
import com.mideas.rpg.v2.command.CommandSelectScreenLoadCharacters;
import com.mideas.rpg.v2.command.item.CommandGem;
import com.mideas.rpg.v2.command.item.CommandStuff;
import com.mideas.rpg.v2.command.item.CommandWeapon;
import com.mideas.rpg.v2.hud.LoginScreen;
import static com.mideas.rpg.v2.connection.PacketID.*;

public class ConnectionManager {

	private static Connection connection;
	private static SocketChannel socket;
	private static HashMap<Integer, Command> commandList = new HashMap<Integer, Command>();
	private static final String IP = "127.0.0.1";
	private static final int PORT = 5720;
	private static boolean init;
	
	private static void initPacket() {
		commandList.put((int)LOGIN, new CommandLogin());
		commandList.put((int)SELECT_SCREEN_LOAD_CHARACTERS, new CommandSelectScreenLoadCharacters());
		commandList.put((int)CREATE_CHARACTER, new CommandCreateCharacter());
		commandList.put((int)DELETE_CHARACTER, new CommandDeleteCharacter());
		commandList.put((int)LOAD_EQUIPPED_ITEMS, new CommandLoadEquippedItems());
		commandList.put((int)STUFF, new CommandStuff());
		commandList.put((int)WEAPON, new CommandWeapon());
		commandList.put((int)GEM, new CommandGem());
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
			byte packetId = -1;
			try {
				if(connection.read() == 1) {
					packetId = connection.readByte();
				}
			} 
			catch (IOException e) {
				e.printStackTrace();
				connection.close();
				return;
			}
			if(connection != null) {
				if(packetId != -1 && commandList.containsKey((int)packetId)) {
					commandList.get((int)packetId).read();
				}
			}
		}
	}
	
	public static HashMap<Integer, Command> getCommandList() {
		return commandList;
	}
}
