package com.mideas.rpg.v2.stresstest;

import static com.mideas.rpg.v2.connection.PacketID.ACCOUNT_BANNED_TEMP;
import static com.mideas.rpg.v2.connection.PacketID.ACCOUNT_BANNED_PERM;
import static com.mideas.rpg.v2.connection.PacketID.ALREADY_LOGGED;
import static com.mideas.rpg.v2.connection.PacketID.LOGIN;
import static com.mideas.rpg.v2.connection.PacketID.LOGIN_ACCEPT;
import static com.mideas.rpg.v2.connection.PacketID.LOGIN_WRONG;

import com.mideas.rpg.v2.connection.ConnectionManager;
import com.mideas.rpg.v2.connection.PacketID;

public class CommandLogin implements Command {

	@Override
	public void read(Stresstest client) {
		short packetId = client.getAuthConnection().readShort();
		System.out.println("packetId : " + packetId);
		if(packetId == LOGIN_ACCEPT) {
			int id = client.getAuthConnection().readInt();
			String accountName = client.getAuthConnection().readString();
			client.setAccountId(id);
			System.out.println("Logged accepted");
			loginRealm(client, ConnectionManager.getWorldServer().getRealmId());
		}
		else if(packetId == ACCOUNT_BANNED_TEMP) {
			client.getConnectionMgr().closeAuth();
			System.out.println("Stresstest error, account is banned temp.");
		}
		else if(packetId == ACCOUNT_BANNED_PERM) {
			client.getConnectionMgr().closeAuth();
			System.out.println("Stresstest error, account is banned perm.");
		}
		else if(packetId == LOGIN_WRONG) {
			client.getConnectionMgr().closeAuth();
			System.out.println("Stresstest error, wrong login.");
		}
		else if(packetId == ALREADY_LOGGED) {
			client.getConnectionMgr().closeAuth();
			System.out.println("Stresstest error, account is already logged.");
		}
		else if(packetId == PacketID.LOGIN_REALM_ACCEPTED) {
			double key = client.getAuthConnection().readDouble();
			int port = client.getAuthConnection().readInt();
			client.getConnectionMgr().connectWorldServer(port);
			System.out.println("Key received : " + key);
			if(client.getConnectionMgr().isConnected()) {
				client.getWorldServerConnection().startPacket();
				client.getWorldServerConnection().writeShort(PacketID.LOGIN_REALM);
				client.getWorldServerConnection().writeShort(PacketID.LOGIN_REALM_REQUEST);
				client.getWorldServerConnection().writeDouble(key);
				client.getWorldServerConnection().writeInt(client.getAccountId());
				client.getWorldServerConnection().endPacket();
				client.getWorldServerConnection().send();
			}
			else {
				System.out.println("Stresstest error, cannot connect to worldServer");
			}
		}
	}
	
	public static void write(Stresstest client, String account, String password) {
		if(client.getConnectionMgr().isAuthServerConnected()) {
			client.getAuthConnection().startPacket();
			client.getAuthConnection().writeShort(LOGIN);
			client.getAuthConnection().writeString(account);
			client.getAuthConnection().writeString(password);
			client.getAuthConnection().endPacket();
			client.getAuthConnection().send();
		}
	}
	
	public static void loginRealm(Stresstest client, int id) {
		client.getAuthConnection().startPacket();
		client.getAuthConnection().writeShort(PacketID.LOGIN_REALM);
		client.getAuthConnection().writeInt(id);
		client.getAuthConnection().endPacket();
		client.getAuthConnection().send();
		System.out.println("Login realm request sent");
	}
}
