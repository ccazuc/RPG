package com.mideas.rpg.v2.command;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.connection.ConnectionManager;
import com.mideas.rpg.v2.connection.PacketID;
import com.mideas.rpg.v2.game.Unit;

public class CommandUpdateStats extends Command {

	@Override
	public void read() {
		byte packetID = ConnectionManager.getConnection().readByte();
		int id = ConnectionManager.getConnection().readInt();
		int value = ConnectionManager.getConnection().readInt();
		System.out.println(id+" value: "+value);
		Unit unit = id == Mideas.joueur1().getId() ? Mideas.joueur1() : Mideas.joueur1().getTarget();
		if(packetID == PacketID.UPDATE_STATS_STAMINA) {
			unit.setStamina(value);
		}
		if(packetID == PacketID.UPDATE_STATS_MANA) {
			unit.setMana(value);
		}
	}
}
