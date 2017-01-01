package com.mideas.rpg.v2.connection;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SocketChannel;

import com.mideas.rpg.v2.game.ClassType;
import com.mideas.rpg.v2.game.classes.Wear;
import com.mideas.rpg.v2.game.item.Item;
import com.mideas.rpg.v2.game.item.ItemType;
import com.mideas.rpg.v2.game.item.container.Container;
import com.mideas.rpg.v2.game.item.gem.Gem;
import com.mideas.rpg.v2.game.item.gem.GemBonusType;
import com.mideas.rpg.v2.game.item.gem.GemColor;
import com.mideas.rpg.v2.game.item.potion.Potion;
import com.mideas.rpg.v2.game.item.stuff.Stuff;
import com.mideas.rpg.v2.game.item.stuff.StuffType;
import com.mideas.rpg.v2.game.item.weapon.WeaponSlot;
import com.mideas.rpg.v2.game.item.weapon.WeaponType;
import com.mideas.rpg.v2.utils.Color;

public class Buffer {

	private ByteBuffer buffer;	
	private boolean written;
	private SocketChannel socket;
	
	public Buffer(SocketChannel socket) {
		this.buffer = ByteBuffer.allocateDirect(16000);
		this.socket = socket;
	}
	
	public Buffer() {
		this.buffer = ByteBuffer.allocateDirect(16000);
	}
	
	public Buffer(int capacity) {
		this.buffer = ByteBuffer.allocateDirect(capacity);
	}
	
	protected final void send() throws IOException {
		if(this.socket.isOpen()) {
			if(this.written) {
				send(this.buffer);
				this.written = false;
			}
		}
		else {
			throw new ClosedChannelException();
		}
	}

	private final void send(final ByteBuffer buffer) throws IOException {
		buffer.flip();
		while(buffer.hasRemaining()) {
			this.socket.write(buffer);
		}
		buffer.clear();
	}

	protected final byte read() throws IOException {
		return read(this.buffer);
	}
	
	private final byte read(final ByteBuffer buffer) throws IOException {
		if(!buffer.hasRemaining()) {
			buffer.clear();
		}
		if(this.socket.read(buffer) >= 0) {
			buffer.flip();
			return 1;
		}
		return 2;
	}
	
	public final void flip() {
		this.buffer.flip();
	}
	
	public final int position() {
		return this.buffer.position();
	}
	
	public final void position(int position) {
		this.buffer.position(position);
	}
	
	public final int capacity() {
		return this.buffer.capacity();
	}
	
	public final void setOrder(ByteOrder order) {
		this.buffer.order(order);
	}
	
	public final int remaining() {
		return this.buffer.remaining();
	}
	
	public final ByteBuffer getBuffer() {
		return this.buffer;
	}
	
	public final boolean hasRemaining() {
		return this.buffer.hasRemaining();
	}
	
	protected final Item readItem() {
		ItemType type = ItemType.values()[readByte()];
		if(type == ItemType.CONTAINER) {
			return readContainer();
		}
		if(type == ItemType.GEM) {
			return readGem();
		}
		if(type == ItemType.ITEM) {
			//return readItem();
		}
		if(type == ItemType.POTION) {
			return readPotion();
		}
		if(type == ItemType.STUFF) {
			return readStuff();
		}
		if(type == ItemType.WEAPON) {
			return readWeapon();
		}
		System.out.println("rBuffer.readItem: Unknown ItemType value.");
		return null;
	}
	
	public final Gem readGem() {
		return new Gem(readInt(), readString(), readString(), readInt(), GemColor.values()[ConnectionManager.getConnection().readByte()], readInt(), readInt(), readInt(), readInt(), readInt(), readInt());
	}
	
	public final Container readContainer() {
		return new Container(readInt(), readString(), readString(), readInt(), readInt(), readInt());
	}
	
	public final Stuff readStuff() {
		return new Stuff(StuffType.values()[readByte()], readClassType(), readString(), readInt(), readString(), readInt(), GemColor.values()[readByte()], GemColor.values()[readByte()], GemColor.values()[readByte()], GemBonusType.values()[readByte()], readInt(), readInt(), Wear.values()[readByte()], readInt(), readInt(), readInt(), readInt(), readInt(), readInt());
	}
	
	public final Stuff readWeapon() {
		return new Stuff(readInt(), readString(), readString(), readClassType(), WeaponType.values()[readByte()], WeaponSlot.values()[readByte()], readInt(), GemColor.values()[readByte()], GemColor.values()[readByte()], GemColor.values()[readByte()], GemBonusType.values()[readByte()], readInt(), readInt(), readInt(), readInt(), readInt(), readInt(), readInt(), readInt());
	}
	
	public final Potion readPotion() {
		return new Potion(readInt(), readString(), readString(), readInt(), readInt(), readInt(), readInt(), readInt());
	}
	
	public final Color readColor() {
		return new Color(readFloat(), readFloat(), readFloat(), readFloat());
	}
	
	public final void writeStuff(final Stuff stuff) {
		int i = 0;
		writeByte(stuff.getType().getValue());
		writeInt(stuff.getClassType().length);
		while(i < stuff.getClassType().length) {
			writeByte(stuff.getClassType(i).getValue());
			i++;
		}
		writeString(stuff.getSpriteId());
		writeInt(stuff.getId());
		writeString(stuff.getStuffName());
		writeInt(stuff.getQuality());
		writeByte(stuff.getGemColor(0).getValue());
		writeByte(stuff.getGemColor(1).getValue());
		writeByte(stuff.getGemColor(2).getValue());
		writeByte(stuff.getGemBonusType().getValue());
		writeInt(stuff.getGemBonusValue());
		writeInt(stuff.getLevel());
		writeByte(stuff.getWear().getValue());
		writeInt(stuff.getCritical());
		writeInt(stuff.getStrength());
		writeInt(stuff.getStamina());
		writeInt(stuff.getArmor());
		writeInt(stuff.getMana());
		writeInt(stuff.getSellPrice());
		this.written = true;
	}
	
	public final void writeGem(final Gem gem) {
		writeInt(gem.getId());
		writeString(gem.getSpriteId());
		writeString(gem.getStuffName());
		writeInt(gem.getQuality());
		writeByte(gem.getColor().getValue());
		writeInt(gem.getStrength());
		writeInt(gem.getStamina());
		writeInt(gem.getArmor());
		writeInt(gem.getMana());
		writeInt(gem.getCritical());
		writeInt(gem.getSellPrice());
		this.written = true;
	}
	
	public final void writePotion(final Potion potion) {
		writeInt(potion.getId());
		writeString(potion.getSpriteId());
		writeString(potion.getStuffName());
		writeInt(potion.getLevel());
		writeInt(potion.getPotionHeal());
		writeInt(potion.getPotionMana());
		writeInt(potion.getSellPrice());
		writeInt(potion.getAmount());
		this.written = true;
	}
	
	public final void writeWeapon(final Stuff weapon) {
		int i = 0;
		writeInt(weapon.getId());
		writeString(weapon.getStuffName());
		writeString(weapon.getSpriteId());
		writeInt(weapon.getClassType().length);
		while(i < weapon.getClassType().length) {
			writeByte(weapon.getClassType(i).getValue());
			i++;
		}
		writeByte(weapon.getWeaponType().getValue());
		writeByte(weapon.getWeaponSlot().getValue());
		writeInt(weapon.getQuality());
		writeByte(weapon.getGemColor(0).getValue());
		writeByte(weapon.getGemColor(1).getValue());
		writeByte(weapon.getGemColor(2).getValue());
		writeByte(weapon.getGemBonusType().getValue());
		writeInt(weapon.getGemBonusValue());
		writeInt(weapon.getLevel());
		writeInt(weapon.getArmor());
		writeInt(weapon.getStamina());
		writeInt(weapon.getMana());
		writeInt(weapon.getCritical());
		writeInt(weapon.getStrength());
		writeInt(weapon.getSellPrice());
		this.written = true;
	}
	
	public final void writeContainer(final Container bag) {
		writeInt(bag.getId());
		writeString(bag.getStuffName());
		writeString(bag.getSpriteId());
		writeInt(bag.getQuality());
		writeInt(bag.getSize());
		writeInt(bag.getSellPrice());
		this.written = true;
	}
	
	protected final ClassType[] readClassType() {
		int i = 0;
		int number = readInt();
		ClassType[] classType = new ClassType[number];
		while(i < classType.length) {
			classType[i] = ClassType.values()[readByte()];
			i++;
		}
		return classType;
	}

	public final void writeString(final String s) {
		writeShort((short)s.length());
		int i = -1;
		while(++i < s.length()) {
			writeChar(s.charAt(i));
		}
		this.written = true;
	}
	
	public final String readString() {
		final short length = readShort();
		if(length == 0) {
			return "";
		}
		final char[] chars = new char[length];
		int i = -1;
		while(++i < length) {
			chars[i] = readChar();
		}
		return new String(chars);
	}
	
	public final void clear() {
		this.buffer.clear();
	}
	
	public final void writeBoolean(final boolean b) {
		this.buffer.put((byte)(b?1:0));
		this.written = true;
	}
	
	public final boolean readBoolean() {
		return this.buffer.get() == 1;
	}
	
	public final void writeByte(final byte b) {
		this.buffer.put(b);
		this.written = true;
	}
	
	public final byte readByte() {
		return this.buffer.get();
	}
	
	public final void writeShort(final short s) {
		this.buffer.putShort(s);
		this.written = true;
	}
	
	public final short readShort() {
		return this.buffer.getShort();
	}
	
	public final void writeInt(final int i) {
		this.buffer.putInt(i);
		this.written = true;
	}
	
	public final int readInt() {
		return this.buffer.getInt();
	}
	
	public final void writeLong(final long l) {
		this.buffer.putLong(l);
		this.written = true;
	}
	
	public final long readLong() {
		return this.buffer.getLong();
	}
	
	public final void writeFloat(final float f) {
		this.buffer.putFloat(f);
		this.written = true;
	}
	
	public final float readFloat() {
		return this.buffer.getFloat();
	}
	
	public final void writeDouble(final double d) {
		this.buffer.putDouble(d);
		this.written = true;
	}
	
	public final double readDouble() {
		return this.buffer.getDouble();
	}
	
	public final void writeChar(final char c) {
		this.buffer.putChar((char)(Character.MAX_VALUE-c));
		this.written = true;
	}
	
	public final char readChar() {
		return (char)(Character.MAX_VALUE-this.buffer.getChar());
	}
}
