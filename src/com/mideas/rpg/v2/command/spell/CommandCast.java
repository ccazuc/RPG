package com.mideas.rpg.v2.command.spell;

import com.mideas.rpg.v2.command.Command;
import com.mideas.rpg.v2.connection.ConnectionManager;
import com.mideas.rpg.v2.connection.PacketID;
import com.mideas.rpg.v2.game.spell.Spell;
import com.mideas.rpg.v2.game.spell.SpellManager;
import com.mideas.rpg.v2.game.unit.TargetType;
import com.mideas.rpg.v2.hud.CastBar;
import com.mideas.rpg.v2.utils.DebugUtils;

public class CommandCast extends Command {

	@Override
	public void read() {
		short packetId = ConnectionManager.getWorldServerConnection().readShort();
		if(packetId == PacketID.SPELL_CAST_SUCCEED) {
			
		}
		else if(packetId == PacketID.SPELL_CAST_START) {
			int spellID = ConnectionManager.getWorldServerConnection().readInt();
			long startCastTimer = ConnectionManager.getWorldServerConnection().readLong();
			int castLength = ConnectionManager.getWorldServerConnection().readInt();
			Spell spell = SpellManager.getSpell(spellID);
			if(spell == null) {
				System.out.println("Error in CommandCast:CAST_START_SUCCEED, spell not found, id : "+spellID);
				return;
			}
			CastBar.castSpell(spell, startCastTimer, castLength);
		}
		
	}
	
	public static void cast(int spellId) {
		ConnectionManager.getWorldServerConnection().startPacket();
		ConnectionManager.getWorldServerConnection().writeShort(PacketID.SPELL_CAST);
		ConnectionManager.getWorldServerConnection().writeShort(PacketID.SPELL_CAST_REQUEST);
		ConnectionManager.getWorldServerConnection().writeInt(spellId);
		ConnectionManager.getWorldServerConnection().writeByte(TargetType.TARGET.getValue());
		ConnectionManager.getWorldServerConnection().writeByte((byte)1);
		ConnectionManager.getWorldServerConnection().endPacket();
		ConnectionManager.getWorldServerConnection().send();
	}
}
