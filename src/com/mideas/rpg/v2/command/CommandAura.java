package com.mideas.rpg.v2.command;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.connection.ConnectionManager;
import com.mideas.rpg.v2.connection.PacketID;
import com.mideas.rpg.v2.game.unit.Unit;
import com.mideas.rpg.v2.game.aura.Aura;
import com.mideas.rpg.v2.game.aura.AuraMgr;
import com.mideas.rpg.v2.game.aura.AppliedAura;

public class CommandAura extends Command {

	@Override
	public void read() {
		short packetId = ConnectionManager.getConnection().readShort();
		if(packetId == PacketID.AURA_SEND) {
			int unitID = ConnectionManager.getConnection().readInt();
			int auraID = ConnectionManager.getConnection().readInt();
			long endTimer = ConnectionManager.getConnection().readLong();
			byte numberStack = ConnectionManager.getConnection().readByte();
			Aura aura = AuraMgr.getAura(auraID);
			if(aura == null) {
				return;
			}
			Unit unit = unitID == Mideas.joueur1().getId() ? Mideas.joueur1() : Mideas.joueur1().getTarget();
			if(unit == null) {
				return;
			}
			unit.applyAura(new AppliedAura(aura, endTimer, numberStack));
		}
		else if(packetId == PacketID.AURA_UPDATE) {
			int unitID = ConnectionManager.getConnection().readInt();
			int auraID = ConnectionManager.getConnection().readInt();
			long endTimer = ConnectionManager.getConnection().readLong();
			byte numberStack = ConnectionManager.getConnection().readByte();
			Aura aura = AuraMgr.getAura(auraID);
			if(aura == null) {
				return;
			}
			Unit unit = unitID == Mideas.joueur1().getId() ? Mideas.joueur1() : Mideas.joueur1().getTarget();
			if(unit == null) {
				return;
			}
			AppliedAura applied = null;
			if(aura.isBuff()) {
				applied = unit.getBuff(auraID);
			}
			else {
				applied = unit.getDebuff(auraID);
			}
			if(applied == null) {
				return;
			}
			applied.update(endTimer, numberStack);
		}
		else if(packetId == PacketID.AURA_CANCEL) {
			int unitID = ConnectionManager.getConnection().readInt();
			int auraID = ConnectionManager.getConnection().readInt();
			Aura aura = AuraMgr.getAura(auraID);
			if(aura == null) {
				return;
			}
			Unit unit = unitID == Mideas.joueur1().getId() ? Mideas.joueur1() : Mideas.joueur1().getTarget();
			if(unit == null) {
				return;
			}
			if(aura.isBuff()) {
				unit.removeBuff(auraID);
			}
			else {
				unit.removeDebuff(auraID);
			}
		}
	}
	
	public static void cancelAura(int auraID) {
		ConnectionManager.getConnection().startPacket();
		ConnectionManager.getConnection().writeShort(PacketID.AURA);
		ConnectionManager.getConnection().writeShort(PacketID.AURA_CANCEL);
		ConnectionManager.getConnection().writeInt(auraID);
		ConnectionManager.getConnection().endPacket();
		ConnectionManager.getConnection().send();
	}
}