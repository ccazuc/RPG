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
import com.mideas.rpg.v2.hud.LoginScreen;
import com.mideas.rpg.v2.hud.SelectScreen;



public class CommandLogin extends Command {

	@Override
	public void read() {
		byte packetId = ConnectionManager.getAuthConnection().readByte();
		if(packetId == LOGIN_ACCEPT) {
			System.out.println('a');
			int id = ConnectionManager.getAuthConnection().readInt();
			//int rank = ConnectionManager.getAuthConnection().readInt();
			double key = ConnectionManager.getAuthConnection().readDouble();
			ConnectionManager.connectWorldServer();
			Interface.setHasLoggedIn(true);
			Mideas.setAccountId(id);
			//Mideas.setRank(rank);
			SelectScreen.mouseEvent();
			LoginScreen.loginSuccess();
		}
		else if(packetId == ACCOUNT_BANNED_TEMP) {
			System.out.println('b');
			LoginScreen.getAlert().setActive();
			LoginScreen.getAlert().setText("Vous avez été bannis de façon temporaire.");
			ConnectionManager.closeAuth();
		}
		else if(packetId == ACCOUNT_BANNED_PERM) {
			System.out.println('c');
			LoginScreen.getAlert().setActive();
			LoginScreen.getAlert().setText("Vous avez été bannis de façon permanente.");
			ConnectionManager.closeAuth();
		}
		else if(packetId == LOGIN_WRONG) {
			System.out.println('d');
			LoginScreen.getAlert().setActive();
			LoginScreen.getAlert().setText("Identifiants incorrectes.");
			ConnectionManager.closeAuth();
		}
		else if(packetId == ALREADY_LOGGED) {
			System.out.println('d');
			LoginScreen.getAlert().setActive();
			LoginScreen.getAlert().setText("Ce compte est déjà connecté.");
			ConnectionManager.closeAuth();
		}
	}
	
	public static void write(String account, String password) {
		if(ConnectionManager.isAuthServerConnected()) {
			ConnectionManager.getAuthConnection().writeByte(LOGIN);
			ConnectionManager.getAuthConnection().writeString(account);
			ConnectionManager.getAuthConnection().writeString(password);
			ConnectionManager.getAuthConnection().send();
		}
	}
}
