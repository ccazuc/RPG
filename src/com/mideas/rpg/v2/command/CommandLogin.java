package com.mideas.rpg.v2.command;

import static com.mideas.rpg.v2.connection.PacketID.ACCOUNT_BANNED_TEMP;
import static com.mideas.rpg.v2.connection.PacketID.ACCOUNT_BANNED_PERM;
import static com.mideas.rpg.v2.connection.PacketID.ALREADY_LOGGED;
import static com.mideas.rpg.v2.connection.PacketID.LOGIN;
import static com.mideas.rpg.v2.connection.PacketID.LOGIN_ACCEPT;
import static com.mideas.rpg.v2.connection.PacketID.LOGIN_WRONG;

import com.mideas.rpg.v2.Interface;
import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.connection.ConnectionManager;
import com.mideas.rpg.v2.connection.PacketID;
import com.mideas.rpg.v2.hud.LoginScreen;
import com.mideas.rpg.v2.hud.RealmListFrame;
import com.mideas.rpg.v2.hud.SelectScreen;
import com.mideas.rpg.v2.stresstest.Stresstest;
import com.mideas.rpg.v2.stresstest.StresstestConnectionMgr;

public class CommandLogin extends Command {

	@Override
	public void read() {
		short packetId = ConnectionManager.getAuthConnection().readShort();
		if(packetId == LOGIN_ACCEPT) {
			int id = ConnectionManager.getAuthConnection().readInt();
			String accountName = ConnectionManager.getAuthConnection().readString();
			//int rank = ConnectionManager.getAuthConnection().readInt();
			Interface.setHasLoggedInToAuth(true);
			Mideas.setAccountName(accountName);
			Mideas.setAccountId(id);
			//Mideas.setRank(rank);
			//SelectScreen.mouseEvent();
			RealmListFrame.mouseEvent();
			LoginScreen.loginSuccess();
			LoginScreen.closeInput();
		}
		else if(packetId == ACCOUNT_BANNED_TEMP) {
			LoginScreen.getAlert().setActive();
			LoginScreen.getAlert().setText("Vous avez été bannis de façon temporaire.");
			LoginScreen.setAlertButtonOk();
			ConnectionManager.closeAuth();
		}
		else if(packetId == ACCOUNT_BANNED_PERM) {
			LoginScreen.getAlert().setActive();
			LoginScreen.getAlert().setText("Vous avez été bannis de façon permanente.");
			LoginScreen.setAlertButtonOk();
			ConnectionManager.closeAuth();
		}
		else if(packetId == LOGIN_WRONG) {
			LoginScreen.getAlert().setActive();
			LoginScreen.getAlert().setText("Identifiants incorrectes.");
			LoginScreen.setAlertButtonOk();
			ConnectionManager.closeAuth();
		}
		else if(packetId == ALREADY_LOGGED) {
			LoginScreen.getAlert().setActive();
			LoginScreen.getAlert().setText("Ce compte est déjà connecté.");
			LoginScreen.setAlertButtonOk();
			ConnectionManager.closeAuth();
		}
		else if(packetId == PacketID.LOGIN_REALM_ACCEPTED) {
			double key = ConnectionManager.getAuthConnection().readDouble();
			int port = ConnectionManager.getAuthConnection().readInt();
			ConnectionManager.connectWorldServer(port);
			if(ConnectionManager.isConnected()) {
				ConnectionManager.getWorldServerConnection().startPacket();
				ConnectionManager.getWorldServerConnection().writeShort(PacketID.LOGIN_REALM);
				ConnectionManager.getWorldServerConnection().writeShort(PacketID.LOGIN_REALM_REQUEST);
				ConnectionManager.getWorldServerConnection().writeDouble(key);
				ConnectionManager.getWorldServerConnection().writeInt(Mideas.getAccountId());
				ConnectionManager.getWorldServerConnection().endPacket();
				ConnectionManager.getWorldServerConnection().send();
				System.out.println("LOGINr:LOGIN_REALM_ACCEPTED");
			}
			else {
				SelectScreen.getAlert().setInactive();
				SelectScreen.setRealmScreenActive(true);
				System.out.println("CommandLogin: cannot connect to worldServer");
			}
		}
	}
	
	public static void write(String account, String password) {
		if(ConnectionManager.isAuthServerConnected()) {
			ConnectionManager.getAuthConnection().startPacket();
			ConnectionManager.getAuthConnection().writeShort(LOGIN);
			ConnectionManager.getAuthConnection().writeString(account);
			ConnectionManager.getAuthConnection().writeString(password);
			ConnectionManager.getAuthConnection().endPacket();
			ConnectionManager.getAuthConnection().send();
		}
	}
	
	public static void loginRealm(int id) {
		ConnectionManager.getAuthConnection().startPacket();
		ConnectionManager.getAuthConnection().writeShort(PacketID.LOGIN_REALM);
		ConnectionManager.getAuthConnection().writeInt(id);
		ConnectionManager.getAuthConnection().endPacket();
		ConnectionManager.getAuthConnection().send();
		System.out.println("LOGINw:LOGIN_REALM");
	}
}
