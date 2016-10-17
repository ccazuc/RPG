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
		byte packetId = ConnectionManager.getConnection().readByte();
		if(packetId == LOGIN_ACCEPT) {
			ConnectionManager.connectWorldServer();
			int id = ConnectionManager.getConnection().readInt();
			int rank = ConnectionManager.getConnection().readInt();
			Interface.setHasLoggedIn(true);
			Mideas.setAccountId(id);
			Mideas.setRank(rank);
			SelectScreen.mouseEvent();
			LoginScreen.loginSuccess();
		}
		else if(packetId == ACCOUNT_BANNED_TEMP) {
			LoginScreen.getAlert().setActive();
			LoginScreen.getAlert().setText("Vous avez été bannis de façon temporaire.");
			ConnectionManager.close();
		}
		else if(packetId == ACCOUNT_BANNED_PERM) {
			LoginScreen.getAlert().setActive();
			LoginScreen.getAlert().setText("Vous avez été bannis de façon permanente.");
			ConnectionManager.close();
		}
		else if(packetId == LOGIN_WRONG) {
			LoginScreen.getAlert().setActive();
			LoginScreen.getAlert().setText("Identifiants incorrectes.");
			ConnectionManager.close();
		}
		else if(packetId == ALREADY_LOGGED) {
			LoginScreen.getAlert().setActive();
			LoginScreen.getAlert().setText("Ce compte est déjà connecté.");
			ConnectionManager.close();
		}
	}
	
	public static void write(String account, String password) {
		if(ConnectionManager.isConnected()) {
			ConnectionManager.getConnection().writeByte(LOGIN);
			ConnectionManager.getConnection().writeString(account);
			ConnectionManager.getConnection().writeString(password);
			ConnectionManager.getConnection().send();
		}
	}
}
