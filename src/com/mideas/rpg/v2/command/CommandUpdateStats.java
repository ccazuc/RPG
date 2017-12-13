package com.mideas.rpg.v2.command;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.connection.ConnectionManager;
import com.mideas.rpg.v2.connection.PacketID;
import com.mideas.rpg.v2.game.unit.Joueur;
import com.mideas.rpg.v2.game.unit.Unit;

public class CommandUpdateStats extends Command {

	@Override
	public void read() {
		short packetID = ConnectionManager.getConnection().readShort();
		int unitID = ConnectionManager.getConnection().readInt();
		int value = ConnectionManager.getConnection().readInt();
		Unit unit = unitID == Mideas.joueur1().getId() ? (Joueur)Mideas.joueur1() : Mideas.joueur1().getTarget();
		if(unit == null) {
			return;
		}
		if(packetID == PacketID.UPDATE_STATS_STAMINA) {
			unit.setStamina(value);
		}
		else if(packetID == PacketID.UPDATE_STATS_MANA) {
			unit.setMana(value);
		}
		else if(packetID == PacketID.CHANGE_GOLD) {
			((Joueur)unit).setGold(value);
		}
		else if(packetID == PacketID.CHANGE_EXPERIENCE) {
			((Joueur)unit).setExp(value);
		}
		else if(packetID == PacketID.UPDATE_STATS_MAX_STAMINA) {
			unit.setMaxStamina(value);
		}
	}
}
