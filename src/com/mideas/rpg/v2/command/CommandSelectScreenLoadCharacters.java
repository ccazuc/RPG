package com.mideas.rpg.v2.command;

import static com.mideas.rpg.v2.connection.PacketID.SELECT_SCREEN_LOAD_CHARACTERS;

import com.mideas.rpg.v2.connection.ConnectionManager;
import com.mideas.rpg.v2.game.classes.SelectScreenPlayer;
import com.mideas.rpg.v2.hud.SelectScreen;

public class CommandSelectScreenLoadCharacters extends Command {
	
	@Override
	public void read() {
		int i = 0;
		while(true) {
			int id = ConnectionManager.getWorldServerConnection().readInt();
			if(id == -1) {
				break;
			}
			String name = ConnectionManager.getWorldServerConnection().readString();
			int level = ConnectionManager.getWorldServerConnection().readInt();
			String classe = ConnectionManager.getWorldServerConnection().readString();
			String race = ConnectionManager.getWorldServerConnection().readString();
			SelectScreen.setCharacterList(new SelectScreenPlayer(id, name, level, classe, race), i);
			i++;
		}
			SelectScreen.setTotalCharacter(i);
		SelectScreen.getAlert().setInactive();
	}
	
	public static void write() {
		if(ConnectionManager.isConnected()) {
			System.out.println("Load character requested");
			ConnectionManager.getWorldServerConnection().startPacket();
			ConnectionManager.getWorldServerConnection().writeShort(SELECT_SCREEN_LOAD_CHARACTERS);
			ConnectionManager.getWorldServerConnection().endPacket();
			ConnectionManager.getWorldServerConnection().send();
		}
	}
}
