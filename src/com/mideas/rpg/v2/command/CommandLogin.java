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
import com.mideas.rpg.v2.files.logs.LogsMgr;
import com.mideas.rpg.v2.hud.LoginScreen;
import com.mideas.rpg.v2.hud.RealmListFrame;
import com.mideas.rpg.v2.hud.SelectScreen;

public class CommandLogin extends Command
{

	@Override
	public void read()
	{
		short packetId = ConnectionManager.getAuthConnection().readShort();
		if (packetId == LOGIN_ACCEPT)
		{
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
			LogsMgr.writeConnectionLog("Auth connection accepted.");
		}
		else if(packetId == ACCOUNT_BANNED_TEMP)
		{
			LoginScreen.getAlert().setActive();
			LoginScreen.getAlert().setText("Vous avez été bannis de façon temporaire.");
			LoginScreen.setAlertButtonOk();
			ConnectionManager.closeAuth();
			LogsMgr.writeConnectionLog("Auth connection refused, account banned temp.");
		}
		else if(packetId == ACCOUNT_BANNED_PERM)
		{
			LoginScreen.getAlert().setActive();
			LoginScreen.getAlert().setText("Vous avez été bannis de façon permanente.");
			LoginScreen.setAlertButtonOk();
			ConnectionManager.closeAuth();
			LogsMgr.writeConnectionLog("Auth connection refused, account banned perm.");
		}
		else if(packetId == LOGIN_WRONG) 
		{
			LoginScreen.getAlert().setActive();
			LoginScreen.getAlert().setText("Identifiants incorrectes.");
			LoginScreen.setAlertButtonOk();
			ConnectionManager.closeAuth();
			LogsMgr.writeConnectionLog("Auth connection refused, invalid login.");
		}
		else if(packetId == ALREADY_LOGGED)
		{
			LoginScreen.getAlert().setActive();
			LoginScreen.getAlert().setText("Ce compte est déjà connecté.");
			LoginScreen.setAlertButtonOk();
			ConnectionManager.closeAuth();
			LogsMgr.writeConnectionLog("Auth connection refused, account already logged.");
		}
		else if(packetId == PacketID.LOGIN_REALM_ACCEPTED)
		{
			double key = ConnectionManager.getAuthConnection().readDouble();
			int port = ConnectionManager.getAuthConnection().readInt();
			LogsMgr.writeConnectionLog("Received world server information from auth.");
			ConnectionManager.connectWorldServer(port);
			LogsMgr.writeConnectionLog("Trying to connect to world server.");
			if (ConnectionManager.isConnected())
			{
				LogsMgr.writeConnectionLog("Connected to world server.");
				ConnectionManager.getWorldServerConnection().startPacket();
				ConnectionManager.getWorldServerConnection().writeShort(PacketID.LOGIN_REALM);
				ConnectionManager.getWorldServerConnection().writeShort(PacketID.LOGIN_REALM_REQUEST);
				ConnectionManager.getWorldServerConnection().writeDouble(key);
				ConnectionManager.getWorldServerConnection().writeInt(Mideas.getAccountId());
				ConnectionManager.getWorldServerConnection().endPacket();
				ConnectionManager.getWorldServerConnection().send();
				LogsMgr.writeConnectionLog("Sending login proof to world server.");
				System.out.println("LOGINr:LOGIN_REALM_ACCEPTED");
			}
			else
			{
				SelectScreen.getAlert().setInactive();
				SelectScreen.setRealmScreenActive(true);
				System.out.println("CommandLogin: cannot connect to worldServer");
				LogsMgr.writeConnectionLog("Connection to world server failed.");
			}
		}
	}
	
	public static void write(String account, String password)
	{
		if (ConnectionManager.isAuthServerConnected())
		{
			ConnectionManager.getAuthConnection().startPacket();
			ConnectionManager.getAuthConnection().writeShort(LOGIN);
			ConnectionManager.getAuthConnection().writeString(account);
			ConnectionManager.getAuthConnection().writeString(password);
			ConnectionManager.getAuthConnection().endPacket();
			ConnectionManager.getAuthConnection().send();
			LogsMgr.writeConnectionLog("Sent account and password to auth server.");
		}
	}
	
	public static void loginRealm(int id)
	{
		ConnectionManager.getAuthConnection().startPacket();
		ConnectionManager.getAuthConnection().writeShort(PacketID.LOGIN_REALM);
		ConnectionManager.getAuthConnection().writeInt(id);
		ConnectionManager.getAuthConnection().endPacket();
		ConnectionManager.getAuthConnection().send();
		System.out.println("LOGINw:LOGIN_REALM");
		LogsMgr.writeConnectionLog("Requested world server information to auth server.");
	}
}
