package com.mideas.rpg.v2.command;

import com.mideas.rpg.v2.Interface;
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
		System.out.println(packetId);
		if(packetId == PacketID.TRADE_NEW_CONFIRM) {
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
		else if(packetId == PacketID.TRADE_REQUEST) {
			//check if windows are open etc
			String name = ConnectionManager.getConnection().readString();
			TradeFrame.setName(name);
			ConnectionManager.getConnection().writeByte(PacketID.TRADE);
			ConnectionManager.getConnection().writeByte(PacketID.TRADE_NEW_CONFIRM);
			ConnectionManager.getConnection().send();
			Interface.setTradeFrameStatus(true);
			System.out.println("Trade request");
		}
	}
	
	public static void writeAddItem(int id, int slot) {
		ConnectionManager.getConnection().writeByte(PacketID.TRADE);
		ConnectionManager.getConnection().writeByte(PacketID.TRADE_ADD_ITEM);
		ConnectionManager.getConnection().writeInt(id);
		ConnectionManager.getConnection().writeInt(slot);
		ConnectionManager.getConnection().send();
	}
	
	public static void writeNewTrade(int character_id) {
		ConnectionManager.getConnection().writeByte(PacketID.TRADE);
		ConnectionManager.getConnection().writeByte(PacketID.TRADE_NEW);
		ConnectionManager.getConnection().writeInt(character_id);
		ConnectionManager.getConnection().send();
		System.out.println(character_id);
	}
	
	public static void writeAccept() {
		ConnectionManager.getConnection().writeByte(PacketID.TRADE);
		ConnectionManager.getConnection().writeByte(PacketID.TRADE_ACCEPT);
		ConnectionManager.getConnection().send();
	}
}
