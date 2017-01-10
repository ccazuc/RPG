package com.mideas.rpg.v2.command;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.connection.ConnectionManager;
import com.mideas.rpg.v2.connection.PacketID;
import com.mideas.rpg.v2.game.unit.ClassType;
import com.mideas.rpg.v2.game.unit.Unit;

public class CommandSendTarget extends Command {

	@Override
	public void read() {
		int id = ConnectionManager.getConnection().readInt();
		int stamina = ConnectionManager.getConnection().readInt();
		int maxStamina= ConnectionManager.getConnection().readInt();
		int mana = ConnectionManager.getConnection().readInt();
		int maxMana = ConnectionManager.getConnection().readInt();
		String name = ConnectionManager.getConnection().readString();
		int level = ConnectionManager.getConnection().readInt();
		Mideas.joueur1().setTarget(new Unit(id, stamina, maxStamina, mana, maxMana, level, name, ClassType.GUERRIER));
	}
	
	public static void requestNewTarget() {
		ConnectionManager.getConnection().startPacket();
		ConnectionManager.getConnection().writeShort(PacketID.SEND_TARGET);
		ConnectionManager.getConnection().endPacket();
		ConnectionManager.getConnection().send();
	}
}
