package com.mideas.rpg.v2.connection;

import java.io.IOException;
import java.nio.channels.SocketChannel;

import com.mideas.rpg.v2.game.item.bag.Container;
import com.mideas.rpg.v2.game.item.gem.Gem;
import com.mideas.rpg.v2.game.item.potion.Potion;
import com.mideas.rpg.v2.game.item.stuff.Stuff;

public class Connection {

	private Buffer wBuffer;
	private Buffer rBuffer;
	private SocketChannel socket;
	
	public Connection(SocketChannel socket) {
		this.socket = socket;
		this.wBuffer = new Buffer(socket);
		this.rBuffer = new Buffer(socket);
	}

	public final void close() {
		try {
			this.socket.close();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setSocket(SocketChannel socket) {
		this.socket = socket;
	}
	
	public final void clearRBuffer() {
		this.rBuffer.clear();
	}
	
	public final byte read() throws IOException {
		return this.rBuffer.read();
	}
	
	public final void send() {
		try {
			this.wBuffer.send();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public final boolean hasRemaining() {
		return this.rBuffer.hasRemaining();
	}
	
	public final Container readContainer() {
		return this.rBuffer.readContainer();
	}
	
	public final Stuff readStuff() {
		return this.rBuffer.readStuff();
	}
	
	public final Stuff readWeapon() {
		return this.rBuffer.readWeapon();
	}
	
	public final Gem readGem() {
		return this.rBuffer.readGem();
	}
	
	public final Potion readPotion() {
		return this.rBuffer.readPotion();
	}
	
	public final void writeBoolean(final boolean b) {
		this.wBuffer.writeBoolean(b);
	}
	
	public final boolean readBoolean() {
		return this.rBuffer.readBoolean();
	}
	
	public final void writeByte(final byte b) {
		this.wBuffer.writeByte(b);
	}
	
	public final byte readByte() {
		return this.rBuffer.readByte();
	}
	
	public final void writeShort(final short s) {
		this.wBuffer.writeShort(s);
	}
	
	public final short readShort() {
		return this.rBuffer.readShort();
	}
	
	public final void writeInt(final int i) {
		this.wBuffer.writeInt(i);
	}
	
	public final int readInt() {
		return this.rBuffer.readInt();
	}
	
	public final void writeLong(final long l) {
		this.wBuffer.writeLong(l);
	}
	
	public final long readLong() {
		return this.rBuffer.readLong();
	}
	
	public final void writeFloat(final float f) {
		this.wBuffer.writeFloat(f);
	}
	
	public final float readFloat() {
		return this.rBuffer.readFloat();
	}
	
	public final void writeDouble(final double d) {
		this.wBuffer.writeDouble(d);
	}
	
	public final double readDouble() {
		return this.rBuffer.readDouble();
	}
	
	public final void writeChar(final char c) {
		this.wBuffer.writeChar(c);
	}
	
	public final char readChar() {
		return this.rBuffer.readChar();
	}
	
	public final void writeString(final String s) {
		this.wBuffer.writeString(s);
	}
	
	public final String readString() {
		return this.rBuffer.readString();
}
	
}
