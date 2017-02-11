package com.mideas.rpg.v2.game.item.container;

import com.mideas.rpg.v2.FontManager;
import com.mideas.rpg.v2.game.item.Item;
import com.mideas.rpg.v2.game.item.ItemType;

public class Container extends Item {

	private byte size;
	private int tooltip_maximum_size;
	private String container_slot_string = "";
	
	public Container(int id, byte size) {
		super(id, "", ItemType.CONTAINER, "", (byte)1, (byte)0, 0, 1, 1);
		this.size = size;
	}
	
	public Container(Container bag) {
		super(bag.id, bag.sprite_id, ItemType.CONTAINER, bag.name, bag.level, bag.quality, bag.sellPrice, bag.maxStack, 1);
		this.size = bag.size;
		buildString();
	}
	
	public Container(int id,  String name, String sprite_id, byte quality, byte size, int sellPrice) {
		super(id, sprite_id, ItemType.CONTAINER, name, (byte)1, quality, sellPrice, 1, 1);
		this.size = size;
		buildString();
	}
	
	public void updateContainer(Container container) {
		this.id = container.id;
		this.sprite_id = container.sprite_id;
		this.name = container.name;
		this.level = container.level;
		this.quality = container.quality;
		this.sellPrice = container.sellPrice;
		this.maxStack = container.maxStack;
		this.size = container.size;
		this.nameColor = getItemNameColor(this);
		buildString();
	}
	
	private void buildString() {
		this.container_slot_string = "Container "+Integer.toString(this.size)+" slots";
		this.tooltip_maximum_size = Math.max(FontManager.get("FRIZQT", 14).getWidth(this.name), FontManager.get("FRIZQT", 14).getWidth(this.container_slot_string));
	}
	
	@Override
	public String getSpriteId()  {
		return this.sprite_id;
	}
	
	public byte getSize() {
		return this.size;
	}
	
	public String getSlotTooltipString() {
		return this.container_slot_string;
	}
	
	public int getMaximumTooltipSize() {
		return this.tooltip_maximum_size;
	}
	@Override
	public int getId() {
		return this.id;
	}
}
