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
		}
		else if(packetId == PacketID.TRADE_ACCEPT) {
			TradeFrame.setTraceAcceptedOther(true);
		}
		else if(packetId == PacketID.TRADE_UNACCEPT) {
			TradeFrame.setTraceAcceptedOther(false);
		}
	}
	
	public static void writeAddItem(Item item, int slot) {
		ConnectionManager.getConnection().writeByte(PacketID.TRADE);
		ConnectionManager.getConnection().writeByte(PacketID.TRADE_ADD_ITEM);
		ConnectionManager.getConnection().writeInt(item.getId());
		ConnectionManager.getConnection().writeInt(slot);
		if(item.isStuff() || item.isWeapon()) {
			Stuff stuff = (Stuff)item;
			if(stuff.getEquippedGem1() != null || stuff.getEquippedGem2() != null || stuff.getEquippedGem3() != null) {
				ConnectionManager.getConnection().writeBoolean(true);
				if(stuff.getEquippedGem1() != null) {
					ConnectionManager.getConnection().writeInt(stuff.getEquippedGem1().getId());
				}
				else {
					ConnectionManager.getConnection().writeInt(0);
				}
				if(stuff.getEquippedGem2() != null) {
					ConnectionManager.getConnection().writeInt(stuff.getEquippedGem2().getId());
				}
				else {
					ConnectionManager.getConnection().writeInt(0);
				}
				if(stuff.getEquippedGem3() != null) {
					ConnectionManager.getConnection().writeInt(stuff.getEquippedGem3().getId());
				}
				else {
					ConnectionManager.getConnection().writeInt(0);
				}
			}
			else {
				ConnectionManager.getConnection().writeBoolean(false);
			}
		}
		ConnectionManager.getConnection().send();
	}
	
	public static void writeNewTrade(int character_id) {
		ConnectionManager.getConnection().writeByte(PacketID.TRADE);
		ConnectionManager.getConnection().writeByte(PacketID.TRADE_NEW);
		ConnectionManager.getConnection().writeInt(character_id);
		ConnectionManager.getConnection().send();
	}
	
	public static void writeAccept() {
		ConnectionManager.getConnection().writeByte(PacketID.TRADE);
		ConnectionManager.getConnection().writeByte(PacketID.TRADE_ACCEPT);
		ConnectionManager.getConnection().send();
	}
	
	public static void writeCloseTrade() {
		ConnectionManager.getConnection().writeByte(PacketID.TRADE);
		ConnectionManager.getConnection().writeByte(PacketID.TRADE_CLOSE);
		ConnectionManager.getConnection().send();
	}
}
