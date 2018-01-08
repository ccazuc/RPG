package com.mideas.rpg.v2.connection;

import java.io.IOException;
import java.nio.channels.SocketChannel;

import com.mideas.rpg.v2.game.item.Item;
import com.mideas.rpg.v2.game.item.container.Container;
import com.mideas.rpg.v2.game.item.gem.Gem;
import com.mideas.rpg.v2.game.item.potion.Potion;
import com.mideas.rpg.v2.game.item.stuff.Stuff;
import com.mideas.rpg.v2.utils.Color;

public class Connection {

	private Buffer wBuffer;
	private Buffer rBuffer;
	private SocketChannel socket;
	private int startPacketPosition;
	
	public Connection(SocketChannel socket) {
		this.socket = socket;
		this.wBuffer = new Buffer(socket);
		this.rBuffer = new Buffer(socket);
	}

	public final void close() {
		try {
			if(this.socket != null) {
				this.socket.close();
			}
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setSocket(SocketChannel socket) {
		this.socket = socket;
	}
	
	public SocketChannel getSocket()
	{
		return (this.socket);
	}

	public final void clearRBuffer() {
		this.rBuffer.clear();
	}
	
	public final void clearEmptyRBuffer() {
		this.rBuffer.emptyClear();
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
	
	public final void startPacket() {
		if(this.wBuffer.position() >= 3*this.wBuffer.capacity()/4) {
			send();
		}
		this.startPacketPosition = this.wBuffer.position();
		writeInt(0);
	}
	
	public final void endPacket() {
		int position = this.wBuffer.position();
		this.wBuffer.position(this.startPacketPosition);
		this.wBuffer.writeInt(position-this.startPacketPosition);
		this.wBuffer.position(position);
	}
	
	public final int rBufferRemaining() {
		return this.rBuffer.remaining();
	}
	
	public final void rBufferSetPosition(int position) {
		this.rBuffer.position(position);
	}
	
	public final void flipRBuffer() {
		this.rBuffer.flip();
	}
	
	public final int rBufferPosition() {
		return this.rBuffer.position();
	}
	
	public final boolean hasRemaining() {
		return this.rBuffer.hasRemaining();
	}
	
	public final Item readItem() {
		return this.rBuffer.readItem();
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
	
	public final Color readColor() {
		return this.rBuffer.readColor();
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
