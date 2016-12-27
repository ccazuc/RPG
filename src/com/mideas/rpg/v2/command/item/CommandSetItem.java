package com.mideas.rpg.v2.command.item;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.command.Command;
import com.mideas.rpg.v2.connection.ConnectionManager;
import com.mideas.rpg.v2.connection.PacketID;
import com.mideas.rpg.v2.game.item.DragItem;
import com.mideas.rpg.v2.game.item.Item;
import com.mideas.rpg.v2.game.item.RequestItem;
import com.mideas.rpg.v2.hud.DragManager;

public class CommandSetItem extends Command {

	@Override
	public void read() {
		short packetId = ConnectionManager.getConnection().readShort();
		if(packetId == PacketID.SET_ITEM_AMOUNT) {
			DragItem type = DragItem.values()[ConnectionManager.getConnection().readByte()];
			int slot = ConnectionManager.getConnection().readInt();
			int amount = ConnectionManager.getConnection().readInt();
			if(type == DragItem.BAG) {
				Mideas.joueur1().bag().getBag(slot).setAmount(amount);
				Mideas.joueur1().bag().getBag(slot).setIsSelectable(true);
			}
			else if(type == DragItem.BANK) {
				
			}
			else if(type == DragItem.GUILDBANK) {
				
			}
			else if(type == DragItem.INVENTORY) {
				
			}
		}
		else if(packetId == PacketID.SET_ITEM_NULL) {
			DragItem type = DragItem.values()[ConnectionManager.getConnection().readByte()];
			int slot = ConnectionManager.getConnection().readInt();
			if(type == DragItem.BAG) {
				if(DragManager.getDraggedItem() == Mideas.joueur1().bag().getBag(slot)) {
					DragManager.setDraggedItem(null);
				}
				Mideas.joueur1().bag().setBag(slot, null);
			}
			else if(type == DragItem.BANK) {
				
			}
			else if(type == DragItem.GUILDBANK) {
				
			}
			else if(type == DragItem.INVENTORY) {
				if(DragManager.getDraggedItem() == Mideas.joueur1().getStuff(slot)) {
					DragManager.setDraggedItem(null);
				}
				Mideas.joueur1().setStuff(slot, null);
			}
		}
		else if(packetId == PacketID.SET_ITEM_SWAP) {
			DragItem sourceType = DragItem.values()[ConnectionManager.getConnection().readByte()];
			int source = ConnectionManager.getConnection().readInt();
			DragItem destinationType = DragItem.values()[ConnectionManager.getConnection().readByte()];
			int destination = ConnectionManager.getConnection().readInt();
			if(sourceType == DragItem.BAG) {
				if(destinationType  == DragItem.BAG) {
					swapBagToBagItems(source, destination);
				}
				else if(destinationType == DragItem.BANK) {
					
				}
				else if(destinationType == DragItem.GUILDBANK) {
					
				}
				else if(destinationType == DragItem.INVENTORY) {
					swapBagToInventoryItems(source, destination);
				}
			}
			else if(sourceType == DragItem.BANK) {
				
			}
			else if(sourceType == DragItem.GUILDBANK) {
				
			}
			else if(sourceType == DragItem.INVENTORY) {
				
			}
		}
		else if(packetId == PacketID.SET_ITEM_SELECTABLE) {
			DragItem type = DragItem.values()[ConnectionManager.getConnection().readByte()];
			int slot = ConnectionManager.getConnection().readInt();
			if(type == DragItem.BAG) {
				if(Mideas.joueur1().bag().getBag(slot) != null) {
					Mideas.joueur1().bag().getBag(slot).setIsSelectable(true);
				}
			}
			else if(type == DragItem.BANK) {
				
			}
			else if(type == DragItem.GUILDBANK) {
				
			}
			else if(type == DragItem.INVENTORY) {
				if(Mideas.joueur1().getStuff(slot) != null) {
					Mideas.joueur1().getStuff(slot).setIsSelectable(true);
				}
			}
		}
		else if(packetId == PacketID.SET_ITEM_ADD) {
			DragItem type = DragItem.values()[ConnectionManager.getConnection().readByte()];
			int id = ConnectionManager.getConnection().readInt();
			int slot = ConnectionManager.getConnection().readInt();
			int amount = ConnectionManager.getConnection().readInt();
			addItem(type, id, slot, amount);
		}
	}
	
	private static void swapBagToBagItems(int source, int destination) {
		if(Mideas.joueur1().bag().getBag(source) != null) {
			Mideas.joueur1().bag().getBag(source).setIsSelectable(true);
		}
		if(Mideas.joueur1().bag().getBag(destination) != null) {
			Mideas.joueur1().bag().getBag(destination).setIsSelectable(true);
		}
		Item tmp = Mideas.joueur1().bag().getBag(source);
		Mideas.joueur1().bag().setBag(source, Mideas.joueur1().bag().getBag(destination));
		Mideas.joueur1().bag().setBag(destination, tmp);
	}
	
	private static void swapBagToInventoryItems(int source, int destination) {
		if(Mideas.joueur1().bag().getBag(source) != null) {
			Mideas.joueur1().bag().getBag(source).setIsSelectable(true);
		}
		if(Mideas.joueur1().getStuff(destination) != null) {
			Mideas.joueur1().getStuff(destination).setIsSelectable(true);
		}
		Item tmp = Mideas.joueur1().bag().getBag(source);
		Mideas.joueur1().bag().setBag(source, Mideas.joueur1().getStuff(destination));
		Mideas.joueur1().setStuff(destination, tmp);
	}
	
	private static void addItem(DragItem type, int id, int slot, int amount) {
		Item item = Item.getItem(id);
		if(item == null) {
			CommandRequestItem.write(new RequestItem(id, type, amount));
			return;
		}
		if(type == DragItem.BAG) {
			Mideas.joueur1().bag().setBag(slot, item, amount);
		}
		else if(type == DragItem.BANK) {
			
		}
		else if(type == DragItem.GUILDBANK) {
			
		}
		else if(type == DragItem.INVENTORY) {
			
		}
	}
} 
