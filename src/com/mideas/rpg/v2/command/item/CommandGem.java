package com.mideas.rpg.v2.command.item;

import com.mideas.rpg.v2.command.Command;
import com.mideas.rpg.v2.connection.ConnectionManager;
import com.mideas.rpg.v2.connection.PacketID;
import com.mideas.rpg.v2.game.item.gem.Gem;
import com.mideas.rpg.v2.game.item.gem.GemColor;
import com.mideas.rpg.v2.game.item.gem.GemManager;

public class CommandGem extends Command {
	
	@Override
	public void read() {
		int id = ConnectionManager.getConnection().readInt();
		String spriteId = ConnectionManager.getConnection().readString();
		String name = ConnectionManager.getConnection().readString();
		int quality = ConnectionManager.getConnection().readInt();
		GemColor type = GemColor.values()[ConnectionManager.getConnection().readChar()];
		int strength = ConnectionManager.getConnection().readInt();
		int stamina = ConnectionManager.getConnection().readInt();
		int armor = ConnectionManager.getConnection().readInt();
		int mana = ConnectionManager.getConnection().readInt();
		int critical = ConnectionManager.getConnection().readInt();
		int sellPrice = ConnectionManager.getConnection().readInt();
		GemManager.storeNewPiece(new Gem(id, spriteId, name, quality, type, strength, stamina, armor, mana, critical, sellPrice));
	}

	public static void write(int id) {
		ConnectionManager.getConnection().writeByte(PacketID.GEM);
		ConnectionManager.getConnection().writeInt(id);
		ConnectionManager.getConnection().send();
	}
}