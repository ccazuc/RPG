package com.mideas.rpg.v2.game.item;

import com.mideas.rpg.v2.connection.ConnectionManager;
import com.mideas.rpg.v2.connection.PacketID;
import com.mideas.rpg.v2.game.item.container.Container;
import com.mideas.rpg.v2.game.item.container.ContainerManager;
import com.mideas.rpg.v2.game.item.gem.Gem;
import com.mideas.rpg.v2.game.item.gem.GemManager;
import com.mideas.rpg.v2.game.item.potion.Potion;
import com.mideas.rpg.v2.game.item.potion.PotionManager;
import com.mideas.rpg.v2.game.item.stuff.Stuff;
import com.mideas.rpg.v2.game.item.stuff.StuffManager;
import com.mideas.rpg.v2.game.item.weapon.WeaponManager;
import com.mideas.rpg.v2.game.shortcut.PotionShortcut;
import com.mideas.rpg.v2.game.shortcut.Shortcut;
import com.mideas.rpg.v2.game.shortcut.StuffShortcut;
import com.mideas.rpg.v2.utils.Color;

public class Item implements Cloneable {

	protected ItemType itemType;
	protected String sprite_id;
	protected int sellPrice;
	protected int maxStack;
	protected String name;
	protected byte quality;
	protected int id;
	protected byte level;
	protected boolean isLoaded;
	protected String deleteConfirm;
	protected boolean isSelectable = true;
	protected int amount;
	protected String amountString = "";
	protected int draggedAmount = -1;
	protected Color nameColor;
	
	private final static String delete = "Voulez vous supprimer ";
	private final static Color BLUE = Color.decode("#0268CC");
	private final static Color PURPLE = Color.decode("#822CB7");
	private final static Color LEGENDARY = Color.decode("#FF800D");
	
	public Item(int id, String sprite_id, ItemType itemType, String name, byte level, byte quality, int sellPrice, int maxStack, int amount) {
		this.sellPrice = sellPrice;
		this.sprite_id = sprite_id;
		this.maxStack = maxStack;
		this.itemType = itemType;
		this.quality = quality;
		this.name = name;
		this.id = id;
		this.amount = amount;
		this.level = level;
		this.nameColor = getItemNameColor(this);
		buildAllString();
	}
	
	public Item(int id) {
		this.id = id;
		this.isLoaded = false;
	}
	
	public Item() {}

	private void buildAllString() {
		buildDeleteConfirm();
	}
	
	public Color getNameColor() {
		return this.nameColor;
	}
	
	public int getDraggedAmount() {
		return this.draggedAmount;
	}
	
	public void setDraggedAmount(int amount) {
		this.draggedAmount = amount;
	}
	
	public void setAmount(int amount) {
		this.amount = amount;
		this.amountString = Integer.toString(amount);
	}
	
	public int getMaxStack() {
		return this.maxStack;
	}
	
	public int getAmount() {
		return this.amount;
	}
	
	public String getAmountString() {
		return this.amountString;
	}
	
	public boolean isSelectable() {
		return this.isSelectable;
	}
	
	public void setIsSelectable(boolean we) {
		this.isSelectable = we;
	}
	
	private void buildDeleteConfirm() {
		this.deleteConfirm = delete+this.name;
	}
	
	public String getDeleteConfirm() {
		return this.deleteConfirm;
	}
	
	public int getId() {
		return this.id;
	}
	
	public void setIsLoaded(boolean we) {
		this.isLoaded = we;
	}
	
	public boolean getIsLoaded() {
		return this.isLoaded;
	}
	
	public String getSpriteId() {
		return this.sprite_id;
	}
	
	public boolean equals(Item item) {
		return item != null && item.getId() == this.id;
	}
	
	public String getStuffName() {
		return this.name;
	}
	
	public byte getLevel() {
		return this.level;
	}
	
	public static void requestItem(int id) {
		ConnectionManager.getWorldServerConnection().writeShort(PacketID.REQUEST_ITEM);
		ConnectionManager.getWorldServerConnection().writeInt(id);
	}
	
	public boolean isStackable() {
		return this.itemType == ItemType.ITEM || this.itemType == ItemType.POTION;
	}
	
	public byte getQuality() {
		return this.quality;
	}
	
	public int getSellPrice() {
		return this.sellPrice;
	}
	
	public ItemType getItemType() {
		return this.itemType;
	}
	
	public boolean isStuff() {
		return this.itemType == ItemType.STUFF;
	}
	
	public boolean isWeapon() {
		return this.itemType == ItemType.WEAPON;
	}
	
	public boolean isItem() {
		return this.itemType == ItemType.ITEM;
	}
	
	public boolean isPotion() {
		return this.itemType == ItemType.POTION;
	}
	
	public boolean isGem() {
		return this.itemType == ItemType.GEM;
	}
	
	public boolean isContainer() {
		return this.itemType == ItemType.CONTAINER;
	}
	
	public static Item getItem(int id) {
		if(ContainerManager.exists(id)) {
			return ContainerManager.getClone(id);
		}
		if(StuffManager.exists(id)) {
			return StuffManager.getClone(id);
		}
		if(WeaponManager.exists(id)) {
			return WeaponManager.getClone(id);
		}
		if(GemManager.exists(id)) {
			return GemManager.getClone(id);
		}
		if(PotionManager.exists(id)) {
			return PotionManager.getClone(id);
		}
		return null;
	}
	
	public static Color getItemNameColor(Item item) {
		if(item.getQuality() == 0) {
			return Color.GREY;
		}
		if(item.getQuality() == 1) {
			return Color.WHITE;
		}
		if(item.getQuality() == 2) {
			return Color.GREEN;
		}
		if(item.getQuality() == 3) {
			return BLUE;
		}
		if(item.getQuality() == 4) {
			return PURPLE;
		}
		if(item.getQuality() == 5) {
			return LEGENDARY;
		}
		return Color.WHITE;
	}
	
	public static Item getStoredItem(int id) {
		if(ContainerManager.exists(id)) {
			return ContainerManager.getContainer(id);
		}
		if(StuffManager.exists(id)) {
			return StuffManager.getStuff(id);
		}
		if(WeaponManager.exists(id)) {
			return WeaponManager.getWeapon(id);
		}
		if(GemManager.exists(id)) {
			return GemManager.getGem(id);
		}
		if(PotionManager.exists(id)) {
			return PotionManager.getPotion(id);
		}
		return null;
	}
	
	public static void storeItem(Item item) {
		if(item.isStuff()) {
			StuffManager.storeNewPiece(new Stuff((Stuff)item));
		}
		else if(item.isWeapon()) {
			WeaponManager.storeNewPiece(new Stuff((Stuff)item, 0));
		}
		else if(item.isGem()) {
			GemManager.storeNewPiece(new Gem((Gem)item));
		}
		else if(item.isPotion()) {
			PotionManager.storeNewPiece(new Potion((Potion)item));
		}
		else if(item.isItem()) {
			
		}
		else if(item.isContainer()) {
			ContainerManager.storeNewPiece(new Container((Container)item));
		}
	}
	
	public static void storeTempItem(ItemType type, int itemID) {
		if(type == ItemType.STUFF) {
			StuffManager.storeNewPiece(new Stuff(itemID));
		}
		else if(type == ItemType.GEM) {
			GemManager.storeNewPiece(new Gem(itemID));
		}
		else if(type == ItemType.ITEM) {
			
		}
		else if(type == ItemType.POTION) {
			PotionManager.storeNewPiece(new Potion(itemID));
		}
		else if(type == ItemType.WEAPON) {
			WeaponManager.storeNewPiece(new Stuff(itemID, 0));
		}
		else if(type == ItemType.CONTAINER) {
			ContainerManager.storeNewPiece(new Container(itemID, (byte)0));
		}
	}
	
	public static boolean exists(int id) {
		if(ContainerManager.exists(id)) {
			return true;
		}
		if(StuffManager.exists(id)) {
			return true;
		}
		if(WeaponManager.exists(id)) {
			return true;
		}
		if(GemManager.exists(id)) {
			return true;
		}
		if(PotionManager.exists(id)) {
			return true;
		}
		return false;
	}
	
	public static Shortcut createShortcut(Item item) {
		if(item.isStuff() || item.isWeapon()) {
			return new StuffShortcut((Stuff)item);
		}
		if(item.isPotion()) {
			return new PotionShortcut((Potion)item);
		}
		return null;
	}
}