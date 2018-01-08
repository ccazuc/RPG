package com.mideas.rpg.v2.command;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.connection.ConnectionManager;
import com.mideas.rpg.v2.connection.PacketID;
import com.mideas.rpg.v2.game.unit.ClassType;
import com.mideas.rpg.v2.game.unit.Unit;

public class CommandSendTarget extends Command {

	@Override
	public void read() {
		int id = ConnectionManager.getWorldServerConnection().readInt();
		int stamina = ConnectionManager.getWorldServerConnection().readInt();
		int maxStamina= ConnectionManager.getWorldServerConnection().readInt();
		int mana = ConnectionManager.getWorldServerConnection().readInt();
		int maxMana = ConnectionManager.getWorldServerConnection().readInt();
		String name = ConnectionManager.getWorldServerConnection().readString();
		int level = ConnectionManager.getWorldServerConnection().readInt();
		Mideas.joueur1().setTarget(new Unit(id, stamina, maxStamina, mana, maxMana, level, name, ClassType.GUERRIER));
	}
	
	public static void requestNewTarget() {
		ConnectionManager.getWorldServerConnection().startPacket();
		ConnectionManager.getWorldServerConnection().writeShort(PacketID.SEND_TARGET);
		ConnectionManager.getWorldServerConnection().endPacket();
		ConnectionManager.getWorldServerConnection().send();
	}
}
