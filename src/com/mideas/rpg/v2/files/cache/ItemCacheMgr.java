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
import com.mideas.rpg.v2.game.item.stuff.Stuff;
import com.mideas.rpg.v2.game.item.stuff.StuffManager;
import com.mideas.rpg.v2.game.item.weapon.WeaponManager;

public class ItemCacheMgr {

	private final static Buffer writeBuffer = new Buffer(160000);
	private static Buffer readBuffer;
	private final static String FOLDER_PATH = "Cache/WDB/frFR";
	private final static String FILE_PATH = "Cache/WDB/frFR/itemcache.wdb";
	private final static byte[] HEADER_SIGNATURE = new byte[] {66, 68, 73, 87};
	
	public static void writeItemCache() {
		FileChannel out;
		writeBuffer.clear();
		int i = 0;
		while(i < HEADER_SIGNATURE.length) {
			writeBuffer.writeByte(HEADER_SIGNATURE[i]);
			i++;
		}
		for(Stuff stuff : StuffManager.getStuffMap().values()) {
			writeBuffer.writeByte(ItemType.STUFF.getValue());
			writeBuffer.writeStuff(stuff);
		}
		/*for(Stuff weapon : WeaponManager.getWeaponMap().values()) {
			writeBuffer.writeByte(ItemType.WEAPON.getValue());
			writeBuffer.writeWeapon(weapon);
		}*/
		writeBuffer.flip();
		writeBuffer.setOrder(ByteOrder.LITTLE_ENDIAN);
		try {
			File folder = new File(FOLDER_PATH);
			if(!folder.exists()) {
				folder.mkdirs();
			}
			File file = new File(FILE_PATH);
			if(!file.exists()) {
				file.createNewFile();
			}
			FileOutputStream outputStream = new FileOutputStream(file);
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
		try {
			File folder = new File(FOLDER_PATH);
			if(!folder.exists()) {
				folder.mkdirs();
			}
			File file = new File(FILE_PATH);
			if(!file.exists()) {
				file.createNewFile();
			}
			FileChannel fc = (FileChannel) Files.newByteChannel(Paths.get(FILE_PATH), StandardOpenOption.READ);
			readBuffer = new Buffer((int)fc.size());
			readBuffer.setOrder(ByteOrder.BIG_ENDIAN);
			fc.read(readBuffer.getBuffer());
			readBuffer.flip();
			byte c;
			boolean readedHeader = false;
			while(readBuffer.hasRemaining()) {
				if(!readedHeader) {
					int i = 0;
					while(i < HEADER_SIGNATURE.length) {
						if((c = readBuffer.readByte()) != HEADER_SIGNATURE[i]) {
							System.out.println("Invalid signature for itemcache.wdb");
						}
						i++;
					}
					readedHeader = true;
				}
				ItemType type = ItemType.values()[readBuffer.readByte()];
				if(type == ItemType.STUFF) {
					Stuff stuff = readBuffer.readStuff();
					Item.storeItem(stuff);
				}
			}
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
}
