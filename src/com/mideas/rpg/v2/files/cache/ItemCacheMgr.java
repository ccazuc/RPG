package com.mideas.rpg.v2.files.cache;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import com.mideas.rpg.v2.connection.Buffer;
import com.mideas.rpg.v2.game.item.Item;
import com.mideas.rpg.v2.game.item.ItemType;
import com.mideas.rpg.v2.game.item.container.Container;
import com.mideas.rpg.v2.game.item.container.ContainerManager;
import com.mideas.rpg.v2.game.item.gem.Gem;
import com.mideas.rpg.v2.game.item.gem.GemManager;
import com.mideas.rpg.v2.game.item.potion.Potion;
import com.mideas.rpg.v2.game.item.potion.PotionManager;
import com.mideas.rpg.v2.game.item.stuff.Stuff;
import com.mideas.rpg.v2.game.item.stuff.StuffManager;
import com.mideas.rpg.v2.game.item.weapon.WeaponManager;

public class ItemCacheMgr {

	private final static Buffer readBufferHeader = new Buffer(8);
	private final static Buffer writeBuffer = new Buffer(160000);
	private static Buffer readBuffer;
	private final static String FOLDER_PATH = "Cache/WDB/frFR";
	private final static String FILE_PATH = "Cache/WDB/frFR/itemcache.wdb";
	private final static byte[] HEADER_SIGNATURE = new byte[] {66, 68, 73, 87};

	public static void writeItemCache() {
		FileChannel out;
		writeBuffer.clear();
		writeHeader();
		int position = writeBuffer.position();
		writeBuffer.writeInt(0);
		for(Stuff stuff : StuffManager.getStuffMap().values()) {
			writeBuffer.writeByte(ItemType.STUFF.getValue());
			writeBuffer.writeStuff(stuff);
		}
		for(Stuff weapon : WeaponManager.getWeaponMap().values()) {
			writeBuffer.writeByte(ItemType.WEAPON.getValue());
			writeBuffer.writeWeapon(weapon);
		}
		for(Potion potion : PotionManager.getPotionMap().values()) {
			writeBuffer.writeByte(ItemType.POTION.getValue());
			writeBuffer.writePotion(potion);
		}
		for(Container container : ContainerManager.getContainerMap().values()) {
			writeBuffer.writeByte(ItemType.CONTAINER.getValue());
			writeBuffer.writeContainer(container);
		}
		for(Gem gem : GemManager.getGemMap().values()) {
			writeBuffer.writeByte(ItemType.GEM.getValue());
			writeBuffer.writeGem(gem);
		}
		int endPosition = writeBuffer.position();
		writeBuffer.position(position);
		writeBuffer.writeInt(endPosition);
		writeBuffer.position(endPosition);
		writeBuffer.flip();
		writeBuffer.setOrder(ByteOrder.LITTLE_ENDIAN);
		try {
			checkFileStatus();
			FileOutputStream outputStream = new FileOutputStream(FILE_PATH);
			out = outputStream.getChannel();
			out.write(writeBuffer.getBuffer());
			out.close();
			outputStream.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void readItemCache() {
		FileChannel fc = null;
		try {
			checkFileStatus();
			fc = (FileChannel) Files.newByteChannel(Paths.get(FILE_PATH), StandardOpenOption.READ);
			readBufferHeader.setOrder(ByteOrder.BIG_ENDIAN);
			fc.read(readBufferHeader.getBuffer());
			readBufferHeader.flip();
			int i = 0;
			while(i < HEADER_SIGNATURE.length) {
				if(readBufferHeader.readByte() != HEADER_SIGNATURE[i]) {
					fc.close();
					clearFile();
					return;
				}
				i++;
			}
			int fileSize = readBufferHeader.readInt();
			readBuffer = new Buffer(fileSize);
			readBuffer.setOrder(ByteOrder.BIG_ENDIAN);
			fc.read(readBuffer.getBuffer());
			readBuffer.flip();
			while(readBuffer.hasRemaining()) {
				ItemType type = ItemType.values()[readBuffer.readByte()];
				if(type == ItemType.STUFF) {
					Item.storeItem(readBuffer.readStuff());
				}
				else if(type == ItemType.CONTAINER) {
					Item.storeItem(readBuffer.readContainer());
				}
				else if(type == ItemType.GEM) {
					Item.storeItem(readBuffer.readGem());
				}
				else if(type == ItemType.POTION) {
					Item.storeItem(readBuffer.readPotion());
				}
				else if(type == ItemType.WEAPON) {
					Item.storeItem(readBuffer.readWeapon());
				}
				else {
					System.out.println("Error type not found on read "+FILE_PATH);
					fc.close();
					clearFile();
					return;
				}
 			}
			fc.close();
		}
		catch(RuntimeException e) {
			System.out.println("READ ERROR IN "+FILE_PATH+", clearing file.");
			e.printStackTrace();
			clearFile();
		}
		catch(IOException e) {
			System.out.println("READ ERROR IN "+FILE_PATH+", clearing file.");
			e.printStackTrace();
			clearFile();
		}
		finally {
			if(fc != null) {
				try {
					fc.close();
				} 
				catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private static void checkFileStatus() {
		File folder = new File(FOLDER_PATH);
		if(!folder.exists()) {
			folder.mkdirs();
		}
		File file = new File(FILE_PATH);
		if(!file.exists()) {
			try {
				file.createNewFile();
			}
			catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private static void writeHeader() {
		int i = 0;
		while(i < HEADER_SIGNATURE.length) {
			writeBuffer.writeByte(HEADER_SIGNATURE[i]);
			i++;
		}
	}
	
	private static void clearFile() {
		FileChannel out;
		writeBuffer.clear();
		try {
			writeHeader();
			writeBuffer.writeInt(writeBuffer.position());
			writeBuffer.flip();
			writeBuffer.setOrder(ByteOrder.LITTLE_ENDIAN);
			FileOutputStream outputStream = new FileOutputStream(FILE_PATH);
			out = outputStream.getChannel();
			out.write(writeBuffer.getBuffer());
			out.close();
			outputStream.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
}
