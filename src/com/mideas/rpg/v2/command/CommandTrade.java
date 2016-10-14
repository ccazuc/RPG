package com.mideas.rpg.v2.command;

import com.mideas.rpg.v2.Interface;
import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.connection.ConnectionManager;
import com.mideas.rpg.v2.connection.PacketID;
import com.mideas.rpg.v2.game.item.Item;
import com.mideas.rpg.v2.game.item.gem.GemManager;
import com.mideas.rpg.v2.hud.TradeFrame;
import com.mideas.rpg.v2.game.item.stuff.Stuff;

public class CommandTrade extends Command {

	@Override
	public void read() {
		byte packetId = ConnectionManager.getConnection().readByte();
		if(packetId == PacketID.TRADE_NEW) {
			int character_id = ConnectionManager.getConnection().readInt();
			String name = ConnectionManager.getConnection().readString();
			TradeFrame.setCharacterId(character_id);
			TradeFrame.setName(name);
			Interface.setTradeFrameStatus(true);
		}
		else if(packetId == PacketID.TRADE_ADD_ITEM) {
			byte itemState = ConnectionManager.getConnection().readByte();
			int slot = ConnectionManager.getConnection().readInt();
			if(itemState == PacketID.KNOWN_ITEM) {
				int id = ConnectionManager.getConnection().readInt();
				if(Item.exists(id)) {
					TradeFrame.addItem(id, slot);
				}
				if(ConnectionManager.getConnection().readBoolean()) {
					((Stuff)TradeFrame.getItem(slot)).setEquippedGem1(GemManager.getClone(ConnectionManager.getConnection().readInt()));
					((Stuff)TradeFrame.getItem(slot)).setEquippedGem2(GemManager.getClone(ConnectionManager.getConnection().readInt()));
					((Stuff)TradeFrame.getItem(slot)).setEquippedGem3(GemManager.getClone(ConnectionManager.getConnection().readInt()));
				}
			}
			else if(itemState == PacketID.UNKNOWN_ITEM) {
				TradeFrame.addItem(ConnectionManager.getConnection().readItem(), slot);
			}
		}
	}
	
	public static void writeNewTrade(int character_id) {
		ConnectionManager.getConnection().writeByte(PacketID.TRADE);
		ConnectionManager.getConnection().writeInt(Mideas.joueur1().getId());
		ConnectionManager.getConnection().writeInt(character_id);
		ConnectionManager.getConnection().send();
	}
	
	public static void writeAccept() {
		ConnectionManager.getConnection().writeByte(PacketID.TRADE);
		ConnectionManager.getConnection().writeByte(PacketID.TRADE_ACCEPT);
		ConnectionManager.getConnection().send();
	}
}
