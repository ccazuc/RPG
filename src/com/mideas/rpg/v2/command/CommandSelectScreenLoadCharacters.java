package com.mideas.rpg.v2.command;

import static com.mideas.rpg.v2.connection.PacketID.SELECT_SCREEN_LOAD_CHARACTERS;

import com.mideas.rpg.v2.connection.ConnectionManager;
import com.mideas.rpg.v2.game.classes.SelectScreenPlayer;
import com.mideas.rpg.v2.hud.SelectScreen;

public class CommandSelectScreenLoadCharacters extends Command {
	
	@Override
	public void read() {
		if(ConnectionManager.getConnection().hasRemaining()) {
			int i = 0;
			while(ConnectionManager.getConnection().hasRemaining()) {
				int id = ConnectionManager.getConnection().readInt();
				String name = ConnectionManager.getConnection().readString();
				int level = ConnectionManager.getConnection().readInt();
				String classe = ConnectionManager.getConnection().readString();
				String race = ConnectionManager.getConnection().readString();
				SelectScreen.setCharacterList(new SelectScreenPlayer(id, name, level, classe, race), i);
				i++;
			}
			SelectScreen.setTotalCharacter(i);
		}
		SelectScreen.getAlert().setInactive();
		System.out.println("Select screen character loaded");
	}
	
	public static void write() {
		if(ConnectionManager.isConnected()) {
			ConnectionManager.getConnection().startPacket();
			ConnectionManager.getConnection().writeShort(SELECT_SCREEN_LOAD_CHARACTERS);
			ConnectionManager.getConnection().endPacket();
			ConnectionManager.getConnection().send();
		}
	}
}
