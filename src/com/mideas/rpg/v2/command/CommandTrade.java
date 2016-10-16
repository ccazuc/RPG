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
		if(packetId == PacketID.TRADE_NEW_CONFIRM) {
			Interface.setTradeFrameStatus(true);
			//System.out.println("trade confirmed");
		}
		else if(packetId == PacketID.TRADE_ADD_ITEM) {
			byte itemState = ConnectionManager.getConnection().readByte();
			//System.out.println("state: "+itemState);
			if(itemState == PacketID.KNOWN_ITEM) {
				int id = ConnectionManager.getConnection().readInt();
				int slot = ConnectionManager.getConnection().readInt();
				int amount = ConnectionManager.getConnection().readInt();
				TradeFrame.addItem(id, slot+7);
				TradeFrame.getItem(slot+7).setAmount(amount);
				if(ConnectionManager.getConnection().readBoolean()) {
					((Stuff)TradeFrame.getItem(slot+7)).setEquippedGem1(GemManager.getClone(ConnectionManager.getConnection().readInt()));
					((Stuff)TradeFrame.getItem(slot+7)).setEquippedGem2(GemManager.getClone(ConnectionManager.getConnection().readInt()));
					((Stuff)TradeFrame.getItem(slot+7)).setEquippedGem3(GemManager.getClone(ConnectionManager.getConnection().readInt()));
				}
				//System.out.println("added item");
			}
			else if(itemState == PacketID.UNKNOWN_ITEM) {
				Item item = ConnectionManager.getConnection().readItem();
				int slot = ConnectionManager.getConnection().readInt();
				int amount = ConnectionManager.getConnection().readInt();
				TradeFrame.addItem(item, slot+7);
				TradeFrame.getItem(slot+7).setAmount(amount);
				if(ConnectionManager.getConnection().readBoolean()) {
					((Stuff)TradeFrame.getItem(slot+7)).setEquippedGem1(GemManager.getClone(ConnectionManager.getConnection().readInt()));
					((Stuff)TradeFrame.getItem(slot+7)).setEquippedGem2(GemManager.getClone(ConnectionManager.getConnection().readInt()));
					((Stuff)TradeFrame.getItem(slot+7)).setEquippedGem3(GemManager.getClone(ConnectionManager.getConnection().readInt()));
				}
				//TradeFrame.addItem(id, slot);
			}
		}
		else if(packetId == PacketID.TRADE_REQUEST) {
			//check if windows are open etc
			String name = ConnectionManager.getConnection().readString();
			TradeFrame.setName(name);
			TradeFrame.requestPending(true);
			/*ConnectionManager.getConnection().writeByte(PacketID.TRADE);
			ConnectionManager.getConnection().writeByte(PacketID.TRADE_NEW_CONFIRM);
			ConnectionManager.getConnection().send();
			Interface.setTradeFrameStatus(true);*/
		}
		else if(packetId == PacketID.TRADE_ACCEPT) {
			TradeFrame.setTraceAcceptedOther(true);
		}
		else if(packetId == PacketID.TRADE_UNACCEPT) {
			TradeFrame.setTraceAcceptedOther(false);
		}
		else if(packetId == PacketID.TRADE_REMOVE_ITEM) {
			int slot = ConnectionManager.getConnection().readInt();
			TradeFrame.addItem(null, slot+7);
		}
		else if(packetId == PacketID.TRADE_CLOSE) {
			TradeFrame.reset();
			Interface.setTradeFrameStatus(false);
		}
		else if(packetId == PacketID.TRADE_ADD_ITEM_ERROR) {
			int slot = ConnectionManager.getConnection().readInt();
			TradeFrame.addItem(null, slot);
		}
		else if(packetId == PacketID.TRADE_SEND_ALL_ITEMS) {
			int i = 0;
			byte packetID;
			int id;
			int amount;
			Item item;
			while(i < 6) {
				packetID = ConnectionManager.getConnection().readByte();
				if(packetID == PacketID.KNOWN_ITEM) {
					id = ConnectionManager.getConnection().readInt();
					amount = ConnectionManager.getConnection().readInt();
					if(Item.exists(id)) {
						Mideas.joueur1().addItem(Item.getItem(id), amount);
					}
				}
				else if(packetID == PacketID.UNKNOWN_ITEM) {
					item = ConnectionManager.getConnection().readItem();
					amount = ConnectionManager.getConnection().readInt();
					Mideas.joueur1().addItem(item, amount);
				}
			}
		}
	}
	
	public static void writeAddItem(Item item, int slot, int amount) {
		ConnectionManager.getConnection().writeByte(PacketID.TRADE);
		ConnectionManager.getConnection().writeByte(PacketID.TRADE_ADD_ITEM);
		ConnectionManager.getConnection().writeInt(item.getId());
		ConnectionManager.getConnection().writeInt(slot);
		ConnectionManager.getConnection().writeInt(amount);
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
		else {
			ConnectionManager.getConnection().writeBoolean(false);
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
	
	public static void writeRemovedItem(int i) {
		ConnectionManager.getConnection().writeByte(PacketID.TRADE);
		ConnectionManager.getConnection().writeByte(PacketID.TRADE_REMOVE_ITEM);
		ConnectionManager.getConnection().writeInt(i);
		ConnectionManager.getConnection().send();
	}
	
	public static void writeCloseTrade() {
		ConnectionManager.getConnection().writeByte(PacketID.TRADE);
		ConnectionManager.getConnection().writeByte(PacketID.TRADE_CLOSE);
		ConnectionManager.getConnection().send();
	}
	
	public static void writeConfirm() {
		ConnectionManager.getConnection().writeByte(PacketID.TRADE);
		ConnectionManager.getConnection().writeByte(PacketID.TRADE_NEW_CONFIRM);
		ConnectionManager.getConnection().send();
	}
}
