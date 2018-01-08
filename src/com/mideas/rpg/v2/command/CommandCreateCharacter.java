package com.mideas.rpg.v2.command;

import static com.mideas.rpg.v2.connection.PacketID.CREATE_CHARACTER;
import static com.mideas.rpg.v2.connection.PacketID.ERROR_NAME_ALPHABET;
import static com.mideas.rpg.v2.connection.PacketID.ERROR_NAME_ALREADY_TAKEN;
import static com.mideas.rpg.v2.connection.PacketID.ERROR_NAME_LENGTH;

import static com.mideas.rpg.v2.connection.PacketID.CHARACTER_CREATED;

import com.mideas.rpg.v2.connection.ConnectionManager;
import com.mideas.rpg.v2.hud.SelectScreen;

public class CommandCreateCharacter extends Command {
	
	@Override
	public void read() {
		short packetId = ConnectionManager.getWorldServerConnection().readShort();
		if(packetId == CHARACTER_CREATED) {
			CommandSelectScreenLoadCharacters.write();
			SelectScreen.setCreatingCharacter(false);
			SelectScreen.setSelectedCharacter(SelectScreen.getSelectedCharacterIndex(), false);
			SelectScreen.setSelectedCharacter(SelectScreen.getTotalCharacter(), true);
			SelectScreen.setSelectedCharacterIndex(SelectScreen.getTotalCharacter());
			SelectScreen.getCharacterInput().resetText();
		}
		else if(packetId == ERROR_NAME_ALPHABET) {
			SelectScreen.getAlert().setActive();
			SelectScreen.getAlert().setText("Le nom doit contenir uniquement des caractères alphabétiques.");
		}
		else if(packetId == ERROR_NAME_ALREADY_TAKEN) {
			SelectScreen.getAlert().setActive();
			SelectScreen.getAlert().setText("This name is already taken");
		}
		else if(packetId == ERROR_NAME_LENGTH) {
			SelectScreen.getAlert().setActive();
			SelectScreen.getAlert().setText("Your name should contains between 2 and 10 characters.");
		}
	}
	
	public static void write(String name) {
		if(name.length() >= 2 && name.length() <= 10) {
			if(ConnectionManager.isConnected()) {
				ConnectionManager.getWorldServerConnection().startPacket();
				ConnectionManager.getWorldServerConnection().writeShort(CREATE_CHARACTER);
				ConnectionManager.getWorldServerConnection().writeString(name);
				ConnectionManager.getWorldServerConnection().writeString(SelectScreen.getSelectedClasse());
				ConnectionManager.getWorldServerConnection().writeString(SelectScreen.getSelectedRace());
				ConnectionManager.getWorldServerConnection().endPacket();
				ConnectionManager.getWorldServerConnection().send();
			}
		}
		else {
			SelectScreen.getAlert().setActive();
			SelectScreen.getAlert().setText("Your name should contains between 2 and 10 characters.");
		}
	}
}
