package com.mideas.rpg.v2.command.item;

import com.mideas.rpg.v2.command.Command;
import com.mideas.rpg.v2.connection.ConnectionManager;
import com.mideas.rpg.v2.connection.PacketID;
import com.mideas.rpg.v2.game.item.potion.Potion;
import com.mideas.rpg.v2.game.item.potion.PotionManager;

public class CommandPotion extends Command {
	
	@Override
	public void read() {
		int id = ConnectionManager.getConnection().readInt();
		String spriteId = ConnectionManager.getConnection().readString();
		String name = ConnectionManager.getConnection().readString();
		int level = ConnectionManager.getConnection().readInt();
		int potionHeal = ConnectionManager.getConnection().readInt();
		int potionMana = ConnectionManager.getConnection().readInt();
		int sellPrice = ConnectionManager.getConnection().readInt();
		PotionManager.storeNewPiece(new Potion(id, spriteId, name, level, potionHeal, potionMana, sellPrice));
	}

	public static void write(int id) {
		ConnectionManager.getConnection().writeByte(PacketID.POTION);
		ConnectionManager.getConnection().writeInt(id);
		ConnectionManager.getConnection().send();
	}
}
