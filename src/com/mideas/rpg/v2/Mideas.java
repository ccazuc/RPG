package com.mideas.rpg.v2;

import java.awt.FontFormatException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Cursor;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import com.mideas.rpg.v2.command.CommandLogout;
import com.mideas.rpg.v2.connection.Connection;
import com.mideas.rpg.v2.connection.ConnectionManager;
import com.mideas.rpg.v2.game.CharacterStuff;
import com.mideas.rpg.v2.game.Joueur;
import com.mideas.rpg.v2.game.bag.Bag;
import com.mideas.rpg.v2.game.bag.BagManager;
import com.mideas.rpg.v2.game.classes.ClassManager;
import com.mideas.rpg.v2.game.item.gem.GemManager;
import com.mideas.rpg.v2.game.item.potion.PotionManager;
import com.mideas.rpg.v2.game.item.shop.Shop;
import com.mideas.rpg.v2.game.item.shop.ShopManager;
import com.mideas.rpg.v2.game.item.stuff.StuffManager;
import com.mideas.rpg.v2.game.item.weapon.WeaponManager;
import com.mideas.rpg.v2.game.shortcut.SpellShortcut;
import com.mideas.rpg.v2.game.spell.Spell;
import com.mideas.rpg.v2.game.spell.SpellManager;
import com.mideas.rpg.v2.hud.AdminPanelFrame;
import com.mideas.rpg.v2.hud.ChangeBackGroundFrame;
import com.mideas.rpg.v2.hud.CharacterFrame;
import com.mideas.rpg.v2.hud.DragManager;
import com.mideas.rpg.v2.hud.LoginScreen;
import com.mideas.rpg.v2.hud.SelectScreen;
import com.mideas.rpg.v2.jdo.JDO;
import com.mideas.rpg.v2.jdo.JDOStatement;
import com.mideas.rpg.v2.jdo.wrapper.MariaDB;
import com.mideas.rpg.v2.utils.Draw;

public class Mideas {
	
	private static boolean currentPlayer;
	private static Joueur joueur1;
	private static Joueur joueur2;
	private static JDO jdo;
	private static Bag bag = new Bag();
	private static Shop shop = new Shop();
	//private static String cursor;
	private static long last;
	private static int count;
	private static String fps;
	private static BufferedImage cursor_image;
	private static int exp;
	private static int level;
	private static int expNeeded;
	private static int gold;
	private static int i;
	private static int k;
	private static int[] expAll = new int[11];
	private static int currentGold;
	private static long usedRAM;
	private static double interfaceDrawTime;
	private static double mouseEventTime;
	private static int accountId;
	private static float displayXFactor = 1700/1920f;
	private static float displayYFactor = 930/1018f;
	private static int rank;
	private static int characterId;
	private static boolean isHover;
	private static Connection connection;
	private static SocketChannel socket;
	private final static int PORT = 5720;
	private final static String ADRESS = "127.0.0.1";
	
	public static void context2D() {
		GL11.glEnable(GL11.GL_TEXTURE_2D);            
        GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);                
        GL11.glClearDepth(1);     
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glViewport(0, 0, Display.getWidth(), Display.getHeight());
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0, Display.getWidth(), Display.getHeight(), 0, 1, -1);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glLoadIdentity();
	}
	
	private static void loop() throws FontFormatException, IOException, LWJGLException, SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException, NoSuchAlgorithmException {
		//System.setProperty("org.lwjgl.opengl.Window.undecorated", "false");
		//Display.setDisplayMode(new DisplayMode(1200, 800));
		//Display.setDisplayMode(new DisplayMode(1200, 930));
		//setDisplayMode(1920, 1080, false);
		//Display.setFullscreen(true);
		Display.setTitle("World Of Warcraft");
		final String[] ICON_PATHS = {"sprite/interface/icon_32.png", "sprite/interface/icon_128.png"};
		final ByteBuffer[] icon_array = new ByteBuffer[ICON_PATHS.length];
		int i = ICON_PATHS.length;
		while(--i > -1) {
			icon_array[i] = PNGDecoder.decode(new File(ICON_PATHS[i]));
		}
		Display.setIcon(icon_array);
		Display.create();
		Display.setResizable(true);
		Display.setDisplayMode(new DisplayMode(1700, 930));
		setDisplayMode(1920, 1080, false);
        cursor_image = ImageIO.read(new File("sprite/interface/cursor.png"));
		final int cursor_width = cursor_image.getWidth();
		final int cursor_height = cursor_image.getHeight();
		final int[] cursor_pixels = new int[cursor_width*cursor_height];
		final ByteBuffer cursor_buffer = BufferUtils.createByteBuffer(cursor_width*cursor_height*4);
		cursor_image.getRGB(0, 0, cursor_width, cursor_height, cursor_pixels, 0, cursor_width);
		int cursor_y = 32;
		while(--cursor_y > -1) {
			int cursor_x = -1;
			while(++cursor_x < 32) {
				int j = cursor_y*32+cursor_x;
				cursor_buffer.put((byte)(cursor_pixels[j]>>16));
				cursor_buffer.put((byte)(cursor_pixels[j]>>8));
				cursor_buffer.put((byte) cursor_pixels[j]);
				cursor_buffer.put((byte)(cursor_pixels[j]>>24));
			}
		}
		cursor_buffer.position(0);
		Mouse.setNativeCursor(new Cursor(32, 32, 0, 31, 1, cursor_buffer.asIntBuffer(), null));
		//Display.setFullscreen(true);
		TTF2.init();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		double time = System.currentTimeMillis();
		loadingScreen();
		System.out.println("Sprites loaded in "+(System.currentTimeMillis()-time)/1000.0+"s.");
		time = System.currentTimeMillis();
		initSQL();
		CharacterStuff.initSQLRequest();
		GemManager.loadGems();
		WeaponManager.loadWeapons();
		PotionManager.loadPotions();
		BagManager.loadBags();
		BagManager.loadBagsSprites();
		StuffManager.loadStuffs();
		ShopManager.loadStuffs();
		SpellManager.loadSpells();
		ClassManager.loadClasses();
		GemManager.loadGemSprites();
		System.out.println(StuffManager.getNumberStuffLoaded()+" pieces of stuff loaded, "+PotionManager.getNumberPotionLoaded()+" potions loaded, "+SpellManager.getNumberSpellLoaded()+" spells loaded in "+(System.currentTimeMillis()-time)/1000.0+"s.");
		getExpAll();
		joueur2 = getRandomClass(2);
		System.gc();
		usedRAM = Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
		try {
			while(!Display.isCloseRequested()) {
				isHover = true;
				fpsUpdate();
				context2D();
				GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
				if(ConnectionManager.isConnected()) {
					ConnectionManager.read();
				}
				if(joueur1 != null && !currentPlayer && joueur2.getStamina() > 0) {
					joueur2.attackUI(Spell.getRandomSpell());
					currentPlayer = true;
					lessCd();
				}
				time = System.nanoTime();
				while(Mouse.next()) {
					if(Interface.mouseEvent()) {
						continue;
					}
				}
				if(System.currentTimeMillis()%500 < 10) {
					mouseEventTime = (float)(System.nanoTime()-time);
				}
				while(Keyboard.next()) {
					if(Interface.keyboardEvent()) {
						continue;
					}
				}
				if(Display.wasResized()) {
					updateDisplayFactor();
				}
				time = System.nanoTime();
				try {
					Interface.draw();
				}
				catch(RuntimeException e) {
				}
				if(System.currentTimeMillis()%1000 < 10) {
					usedRAM = Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
					interfaceDrawTime = System.nanoTime()-time;
				}
				timeEvent();
				Display.update();
				Display.sync(240);
			}
		}
		catch(IllegalStateException e) {
		}
		CommandLogout.write();
		ConnectionManager.close();
		
	}
	
	public static void main(String[] args) throws FontFormatException, IOException, LWJGLException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, NoSuchAlgorithmException {
		loop();
		saveAllStats();
	}
	
	public static void initSQL() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		jdo = new MariaDB("127.0.0.1", 3306, "rpg", "root", "mideas");
		//jdo = new MariaDB("88.163.90.215", 3306, "rpg", "root", "mideas");
		//jdo = new MariaDB("82.236.60.133", 3306, "rpg", "root", "mideas");
	}
	
	public static JDO getJDO() {
		return jdo;
	}
	
	public static int mouseX() {
		return Mouse.getX();
	}
	
	public static int mouseY() {
		return Display.getHeight()-Mouse.getY();
	}
	
	public static void fpsUpdate() {
		count++;
		if(System.currentTimeMillis()-last >= 1000) {
			last = System.currentTimeMillis();
			fps = String.valueOf(count);
			count = 0;
		}
	}
	
	public static boolean getHover() {
		return isHover;
	}
	
	public static void setHover(boolean we) {
		isHover = we;
	}
	
	public static SocketChannel getSocket() {
		return socket;
	}
	
	public static void connectToServer() throws IOException {
		socket = SocketChannel.open();
		socket.socket().connect(new InetSocketAddress(ADRESS, PORT), 5000);
	}
	
	public static Connection getConnection() {
		return connection;
	}
	
	private static void updateDisplayFactor() {
		displayXFactor = Display.getWidth()/1920f;
		displayYFactor = Display.getHeight()/1018f;
		LoginScreen.updateSize();
		SelectScreen.updateSize();
		DragManager.updateSize();
		AdminPanelFrame.updateSize();
		if(joueur1 != null && joueur1.getFirstProfession() != null) {
			joueur1.getFirstProfession().updateSize(Display.getWidth()/2-200, Display.getHeight()/2-300);
		}
	}
	
	public static void setConnection(Connection connections) {
		connection = connections;
	}
	
	public static double getInterfaceDrawTime() {
		return interfaceDrawTime;
	}
	
	public static double getMouseEventTime() {
		return mouseEventTime;
	}
	
	private static void timeEvent() {
		if(System.currentTimeMillis()%10000 < 10) {
			System.gc();
		}
	}
	
	public static long getUsedRAM() {
		return usedRAM;
	}
	
	public static Joueur getRandomClass(int id) {
		double rand = Math.random();
		rand = 1/10f;
		if(rand <= 1/10f) {
			return ClassManager.getClone("Guerrier");
		}
		if(rand <= 2/10f) {
			System.out.println("Le joueur "+id+" est un Priest !");
			return ClassManager.getClone("Priest");
		}
		if(rand <= 3/10f){
			System.out.println("Le joueur "+id+" est un Mage !");
			return ClassManager.getClone("Mage");
		}
		if(rand <= 4/10f) {
			System.out.println("Le joueur "+id+" est un DeathKnight !");
			return ClassManager.getClone("DeathKnight");
		}
		if(rand <= 5/10f) {
			System.out.println("Le joueur "+id+" est un Hunter !");
			return ClassManager.getClone("Hunter");
		}
		if(rand <= 6/10f) {
			System.out.println("Le joueur "+id+" est un Monk !");
			return ClassManager.getClone("Monk");
		}
		if(rand <= 7/10f) {
			System.out.println("Le joueur "+id+" est un Paladin !");
			return ClassManager.getClone("Paladin");
		}
		if(rand <= 8/10f) {
			System.out.println("Le joueur "+id+" est un Rogue !");
			return ClassManager.getClone("Rogue");
		}
		if(rand <= 9/10f) {
			System.out.println("Le joueur "+id+" est un Shaman !");
			return ClassManager.getClone("Shaman");
		}
		System.out.println("Le joueur "+id+" est un Warlock !");
		return ClassManager.getClone("Warlock");
	}

	public static void getExpAll() throws SQLException {
		int id;
		JDOStatement statement = Mideas.getJDO().prepare("SELECT exp FROM stats");
		statement.execute();
		while(statement.fetch()) {
			id = statement.getInt();
			expAll[i] = id;
			i++;
		}
	}
	
	public static int getExpAll(int i) {
		return expAll[i];
	}
	
	public static int getCurrentExp() {
		return exp;
	}
	
	public static int loadExp() throws SQLException {
		JDOStatement statement = Mideas.getJDO().prepare("SELECT exp FROM stats WHERE character_id = ?");
		statement.putInt(Mideas.getCharacterId());
		statement.execute();
		if(statement.fetch()) {
			exp = statement.getInt();
		}
		return exp;
	}
	
	public static int getExp() {
		return exp;
	}
	
	public static void setExp(int exps) throws SQLException {
		exp = exps;
		JDOStatement statement = Mideas.getJDO().prepare("UPDATE stats SET exp = ? WHERE character_id = ?");
		statement.putInt(exps);
		statement.putInt(Mideas.getCharacterId());
		statement.execute();
	}
	
	public static void setGold(int golds) throws SQLTimeoutException, SQLException {
		gold = golds;
		currentGold = golds;
		JDOStatement statement = Mideas.getJDO().prepare("UPDATE stats SET gold = ? WHERE character_id = ?");
		statement.putInt(gold);
		statement.putInt(Mideas.getCharacterId());
		statement.execute();	
	}
	
	public static int loadGold() throws SQLException {
		JDOStatement statement = Mideas.getJDO().prepare("SELECT gold FROM stats WHERE character_id = ?");
		statement.putInt(Mideas.getCharacterId());
		statement.execute();
		if(statement.fetch()) {
			gold = statement.getInt();
			currentGold = gold;
		}
		return gold;
	}
	
	public static int getGold() {
		return gold;
	}
	
	public static void setConfig() throws SQLException {
		JDOStatement statement = Mideas.getJDO().prepare("UPDATE config SET value = ? WHERE `key` = ?");
		statement.putString(ChangeBackGroundFrame.getCurrentBackground());
		statement.putString("background");
		statement.execute();

		statement = Mideas.getJDO().prepare("UPDATE config SET value = ? WHERE `key` = ?");
		statement.putString(Integer.toString(CharacterFrame.getMouseX()));
		statement.putString("x_inventory_frame");
		statement.execute();

		statement = Mideas.getJDO().prepare("UPDATE config SET value = ? WHERE `key` = ?");
		statement.putString(Integer.toString(CharacterFrame.getMouseY()));
		statement.putString("y_inventory_frame");
		statement.execute();
	}

	public static void getConfig() throws SQLException {
		String temp = "";
		JDOStatement statement = Mideas.getJDO().prepare("SELECT value FROM config WHERE `key` = ?");
		statement.putString("background");
		statement.execute();
		if(statement.fetch()) {
			temp = statement.getString();
		}
		ChangeBackGroundFrame.loadBG(temp);
		
		statement = Mideas.getJDO().prepare("SELECT value FROM config WHERE `key` = ?");
		statement.putString("x_inventory_frame");
		statement.execute();
		if(statement.fetch()) {
			temp = statement.getString();
		}
		CharacterFrame.setMouseX(Integer.valueOf(temp));

		statement = Mideas.getJDO().prepare("SELECT value FROM config WHERE `key` = ?");
		statement.putString("y_inventory_frame");
		statement.execute();
		if(statement.fetch()) {
			temp = statement.getString();
		}
		CharacterFrame.setMouseY(Integer.valueOf(temp));
	}
	
	public static int getCurrentGold() {
		return currentGold;
	}
	
	public static int calcGoldCoin() {
		gold = Mideas.getCurrentGold();
		i = 0;
		while(gold-10000 >= 0) {
			gold-= 10000;
			i++;
		}
		return i;
	}
	
	public static int calcSilverCoin() {
		gold = Mideas.getCurrentGold()-i*10000;
		k = 0;
		while(gold-100 >= 0) {
			gold-= 100;
			k++;
		}
		return k;
	}
	
	public static int calcGoldCoinCost(int cost) {
		i = 0;
		while(cost-10000 >= 0) {
			cost-= 10000;
			i++;
		}
		return i;
	}
	
	public static int calcSilverCoinCost(int cost) {
		k = 0;
		cost-= calcGoldCoinCost(cost)*10000;
		while(cost-100 >= 0) {
			cost-= 100;
			k++;
		}
		return k;
	}

	public static void setAccountId(int id) {
		accountId = id;
	}
	
	public static int getAccountId() {
		return accountId;
	}

	public static void setJoueur1(Joueur joueur) {
		joueur1 = new Joueur(joueur);
	}
	
	public static int getClassLine() {
		if(joueur1 != null) {
			if(joueur1.getClasse().equals("DeathKnight")) {
				return 0;
			}
			if(joueur1.getClasse().equals("Guerrier")) {
				return 1;
			}
			if(joueur1.getClasse().equals("Hunter")) {
				return 2;
			}
			if(joueur1.getClasse().equals("Mage")) {
				return 3;
			}
			if(joueur1.getClasse().equals("Monk")) {
				return 4;
			}
			if(joueur1.getClasse().equals("Paladin")) {
				return 5;
			}
			if(joueur1.getClasse().equals("Priest")) {
				return 6;
			}
			if(joueur1.getClasse().equals("Rogue")) {
				return 7;
			}
			if(joueur1.getClasse().equals("Shaman")) {
				return 8;
			}
			if(joueur1.getClasse().equals("Warlock")) {
				return 9;
			}
			else {
				return 10;
			}
		}
		return 10;
	}
	
	private static void lessCd() {
		int i = 0;
		int j = 0;
		first:
		while(i < Mideas.joueur1.getSpells().length) {
			j = 0;
			while(j < i) { //check if the spells i appears more than once between 0 and i
				if(Mideas.joueur1.getSpells(i) != null && Mideas.joueur1.getSpells(j) != null && Mideas.joueur1.getSpells(i) instanceof SpellShortcut && Mideas.joueur1.getSpells(j) instanceof SpellShortcut && ((SpellShortcut)Mideas.joueur1.getSpells(i)).getSpell().equal(((SpellShortcut)Mideas.joueur1.getSpells(j)).getSpell())) {
					i++;
					continue first;
				}
				j++;
			}
			if(Mideas.joueur1.getSpells(i) != null && Mideas.joueur1.getSpells(i) instanceof SpellShortcut) {	
				Mideas.joueur1.getSpells(i).setCd(((SpellShortcut)Mideas.joueur1.getSpells(i)).getSpell().getSpellId(), SpellManager.getCd(((SpellShortcut)Mideas.joueur1.getSpells(i)).getSpell().getSpellId())-1);
			}
			i++;
		}
	}
	
	private static void loadingScreen() throws IOException {
		Sprites.initBG();
		context2D();
		
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		Draw.drawQuadBG(Sprites.loading_screen);
		Draw.drawQuadCentered(Sprites.loading_screen_bar1, Display.getWidth()/2-Sprites.loading_screen_bar1.getImageWidth()*Mideas.getDisplayXFactor()/2, Display.getHeight()-100);
		Display.update();
		Display.sync(60);
		
		Sprites.sprite();
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		Draw.drawQuadBG(Sprites.loading_screen);
		Draw.drawQuadCentered(Sprites.loading_screen_bar2, Display.getWidth()/2-Sprites.loading_screen_bar2.getImageWidth()*Mideas.getDisplayXFactor()/2, Display.getHeight()-100);
		Display.update();
		Display.sync(60);
		
		Sprites.sprite2();
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		Draw.drawQuadBG(Sprites.loading_screen);
		Draw.drawQuadCentered(Sprites.loading_screen_bar3, Display.getWidth()/2-Sprites.loading_screen_bar3.getImageWidth()*Mideas.getDisplayXFactor()/2, Display.getHeight()-100);
		Display.update();
		Display.sync(60);
		
		Sprites.sprite8();
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		Draw.drawQuadBG(Sprites.loading_screen);
		Draw.drawQuadCentered(Sprites.loading_screen_bar4, Display.getWidth()/2-Sprites.loading_screen_bar4.getImageWidth()*Mideas.getDisplayXFactor()/2, Display.getHeight()-100);
		Display.update();
		Display.sync(60);
		
		Sprites.sprite9();
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		Draw.drawQuadBG(Sprites.loading_screen);
		Draw.drawQuadCentered(Sprites.loading_screen_bar5, Display.getWidth()/2-Sprites.loading_screen_bar5.getImageWidth()*Mideas.getDisplayXFactor()/2, Display.getHeight()-100);
		Display.update();
		Display.sync(60);
		
		Sprites.sprite10();
	}
	
	public static Joueur joueur1() {
		return joueur1;
	}
	
	public static Joueur joueur2() {
		return joueur2;
	}
	
	public static void setJoueur2(Joueur joueur) {
		joueur2 = joueur;
	}
	
	public static void setJoueur1Null() {
		joueur1 = null;
	}
	
	public static Bag bag() {
		return bag;
	}
	
	public static Shop shop() {
		return shop;
	}
	
	public static boolean getCurrentPlayer() {
		return currentPlayer;
	}
	
	public static void setCurrentPlayer(boolean we) {
		currentPlayer = we;
	}
	
	public static String getFps() {
		return fps;
	}
	
	public static int getRank() {
		return rank;
	}
	
	public static void setRank(int ranks) {
		rank = ranks;
	}
	
	public static void setDisplayMode(int width, int height, boolean fullscreen) {	 
	    // return if requested DisplayMode is already set
	    if((Display.getDisplayMode().getWidth() == width) && (Display.getDisplayMode().getHeight() == height) && (Display.isFullscreen() == fullscreen)) {
	        return;
	    }
	    try {
	        DisplayMode targetDisplayMode = null;
		    if(fullscreen) {
		        DisplayMode[] modes = Display.getAvailableDisplayModes();
		        int freq = 0;             
		        for(int i=0;i<modes.length;i++) {
		            DisplayMode current = modes[i];       
			        if((current.getWidth() == width) && (current.getHeight() == height)) {
			            if((targetDisplayMode == null) || (current.getFrequency() >= freq)) {
			                if((targetDisplayMode == null) || (current.getBitsPerPixel() > targetDisplayMode.getBitsPerPixel())) {
			                	targetDisplayMode = current;
			                	freq = targetDisplayMode.getFrequency();
			                }
			            }
			            if((current.getBitsPerPixel() == Display.getDesktopDisplayMode().getBitsPerPixel()) && (current.getFrequency() == Display.getDesktopDisplayMode().getFrequency())) {
		                    targetDisplayMode = current;
		                    break;
			            }
			        }
		        }
		    } 
		    else {
		    	targetDisplayMode = new DisplayMode(width,height);
	        }
	        if(targetDisplayMode == null) {
	            System.out.println("Failed to find value mode: "+width+"x"+height+" fs="+fullscreen);
	            return;
	        }
	        Display.setDisplayMode(targetDisplayMode);
	        Display.setFullscreen(fullscreen);        
	    } 
	    catch(LWJGLException e) {
	        System.out.println("Unable to setup mode "+width+"x"+height+" fullscreen="+fullscreen + e);
	    }
	}
	
	public static void saveAllStats() throws SQLException {
		if(Mideas.joueur1() != null) {
			CharacterStuff.setBagItems();
			CharacterStuff.setEquippedBags();
			CharacterStuff.setEquippedItems();
			Mideas.setGold(Mideas.getGold());
			Mideas.setExp(Mideas.getExp());
		}
		Mideas.setConfig();
	}
	
	public static int getLevelAll(int exp) {
		if(exp <= 400) {
			level = 1;
			return level;
		}
		else if(exp <= 900) {
			level = 2;
			return level;
		}
		else if(exp <= 1400) {
			level = 3;
			return level;
		}
		else if(exp <= 2100) {
			level = 4;
			return level;
		}
		else if(exp <= 2800) {
			level = 5;
			return level;
		}
		else if(exp <= 3600) {
			level = 6;
			return level;
		}
		else if(exp <= 4500) {
			level = 7;
			return level;
		}
		else if(exp <= 5400) {
			level = 8;
			return level;
		}
		else if(exp <= 6500) {
			level = 9;
			return level;
		}
		else if(exp <= 7600) {
			level = 10;
			return level;
		}
		else if(exp <= 8700) {
			level = 11;
			return level;
		}
		else if(exp <= 9800) {
			level = 12;
			return level;
		}
		else if(exp <= 11000) {
			level = 13;
			return level;
		}
		else if(exp <= 12300) {
			level = 14;
			return level;
		}
		else if(exp <= 13600) {
			level = 15;
			return level;
		}
		else if(exp <= 15000) {
			level = 16;
			return level;
		}
		else if(exp <= 16400) {
			level = 17;
			return level;
		}
		else if(exp <= 17800) {
			level = 18;
			return level;
		}
		else if(exp <= 19300) {
			level = 19;
			return level;
		}
		else if(exp <= 20800) {
			level = 20;
			return level;
		}
		else if(exp <= 22400) {
			level = 21;
			return level;
		}
		else if(exp <= 24000) {
			level = 22;
			return level;
		}
		else if(exp <= 25500) {
			level = 23;
			return level;
		}
		else if(exp <= 27200) {
			level = 24;
			return level;
		}
		else if(exp <= 28900) {
			level = 25;
			return level;
		}
		else if(exp <= 30500) {
			level = 26;
			return level;
		}
		else if(exp <= 32200) {
			level = 27;
			return level;
		}
		else if(exp <= 33900) {
			level = 28;
			return level;
		}
		else if(exp <= 36300) {
			level = 29;
			return level;
		}
		else if(exp <= 38800) {
			level = 30;
			return level;
		}
		else if(exp <= 41600) {
			level = 31;
			return level;
		}
		else if(exp <= 44600) {
			level = 32;
			return level;
		}
		else if(exp <= 48000) {
			level = 33;
			return level;
		}
		else if(exp <= 51400) {
			level = 34;
			return level;
		}
		else if(exp <= 55000) {
			level = 35;
			return level;
		}
		else if(exp <= 58700) {
			level = 36;
			return level;
		}
		else if(exp <= 62400) {
			level = 37;
			return level;
		}
		else if(exp <= 66200) {
			level = 38;
			return level;
		}
		else if(exp <= 70200) {
			level = 39;
			return level;
		}
		else if(exp <= 74300) {
			level = 40;
			return level;
		}
		else if(exp <= 78500) {
			level = 41;
			return level;
		}
		else if(exp <= 82800) {
			level = 42;
			return level;
		}
		else if(exp <= 87100) {
			level = 43;
			return level;
		}
		else if(exp <= 91600) {
			level = 44;
			return level;
		}
		else if(exp <= 96300) {
			level = 45;
			return level;
		}
		else if(exp <= 101000) {
			level = 46;
			return level;
		}
		else if(exp <= 105800) {
			level = 47;
			return level;
		}
		else if(exp <= 110700) {
			level = 48;
			return level;
		}
		else if(exp <= 115700) {
			level = 49;
			return level;
		}
		else if(exp <= 120900) {
			level = 50;
			return level;
		}
		else if(exp <= 126100) {
			level = 51;
			return level;
		}
		else if(exp <= 131500) {
			level = 52;
			return level;
		}
		else if(exp <= 137000) {
			level = 53;
			return level;
		}
		else if(exp <= 142500) {
			level = 54;
			return level;
		}
		else if(exp <= 148200) {
			level = 55;
			return level;
		}
		else if(exp <= 154000) {
			level = 56;
			return level;
		}
		else if(exp <= 159900) {
			level = 57;
			return level;
		}
		else if(exp <= 165800) {
			level = 58;
			return level;
		}
		else if(exp <= 172000) {
			level = 59;
			return level;
		}
		else if(exp <= 290000) {
			level = 60;
			return level;
		}
		else if(exp <= 317000) {
			level = 61;
			return level;
		}
		else if(exp <= 349000) {
			level = 62;
			return level;
		}
		else if(exp <= 386000) {
			level = 63;
			return level;
		}
		else if(exp <= 428000) {
			level = 64;
			return level;
		}
		else if(exp <= 475000) {
			level = 65;
			return level;
		}
		else if(exp <= 527000) {
			level = 66;
			return level;
		}
		else if(exp <= 585000) {
			level = 67;
			return level;
		}
		else if(exp <= 648000) {
			level = 68;
			return level;
		}
		else if(exp <= 717000) {
			level = 69;
			return level;
		}
		else if(exp <= 1523800) {
			level = 70;
			return level;
		}
		else {
			level = 70;
			return level;
		}
	}
	
	public static int getLevel() {
		return getLevelAll(getCurrentExp());
	}
	
	public static int getExpNeeded(int level) {
		if(level == 1) {
			expNeeded = 400;
			return expNeeded;
		}
		else if(level == 2) {
			expNeeded = 900;
			return expNeeded;
		}
		else if(level == 3) {
			expNeeded = 1400;
			return expNeeded;
		}
		else if(level == 4) {
			expNeeded = 2100;
			return expNeeded;
		}
		else if(level == 5) {
			expNeeded = 2800;
			return expNeeded;
		}
		else if(level == 6) {
			expNeeded = 3600;
			return expNeeded;
		}
		else if(level == 7) {
			expNeeded = 4500;
			return expNeeded;
		}
		else if(level == 8) {
			expNeeded = 5400;
			return expNeeded;
		}
		else if(level == 9) {
			expNeeded = 6500;
			return expNeeded;
		}
		else if(level == 10) {
			expNeeded = 7600;
			return expNeeded;
		}
		else if(level == 11) {
			expNeeded = 8700;
			return expNeeded;
		}
		else if(level == 12) {
			expNeeded = 9800;
			return expNeeded;
		}
		else if(level == 13) {
			expNeeded = 11000;
			return expNeeded;
		}
		else if(level == 14) {
			expNeeded = 12300;
			return expNeeded;
		}
		else if(level == 15) {
			expNeeded = 13600;
			return expNeeded;
		}
		else if(level == 16) {
			expNeeded = 15000;
			return expNeeded;
		}
		else if(level == 17) {
			expNeeded = 16400;
			return expNeeded;
		}
		else if(level == 18) {
			expNeeded = 17800;
			return expNeeded;
		}
		else if(level == 19) {
			expNeeded = 19300;
			return expNeeded;
		}
		else if(level == 20) {
			expNeeded = 20800;
			return expNeeded;
		}
		else if(level == 21) {
			expNeeded = 22400;
			return expNeeded;
		}
		else if(level == 22) {
			expNeeded = 24000;
			return expNeeded;
		}
		else if(level == 23) {
			expNeeded = 25500;
			return expNeeded;
		}
		else if(level == 24) {
			expNeeded = 27200;
			return expNeeded;
		}
		else if(level == 25) {
			expNeeded = 28900;
			return expNeeded;
		}
		else if(level == 26) {
			expNeeded = 30500;
			return expNeeded;
		}
		else if(level == 27) {
			expNeeded = 32200;
			return expNeeded;
		}
		else if(level == 28) {
			expNeeded = 33900;
			return expNeeded;
		}
		else if(level == 29) {
			expNeeded = 36300;
			return expNeeded;
		}
		else if(level == 30) {
			expNeeded = 38800;
			return expNeeded;
		}
		else if(level == 31) {
			expNeeded = 41600;
			return expNeeded;
		}
		else if(level == 32) {
			expNeeded = 44600;
			return expNeeded;
		}
		else if(level == 33) {
			expNeeded = 48000;
			return expNeeded;
		}
		else if(level == 34) {
			expNeeded = 51400;
			return expNeeded;
		}
		else if(level == 35) {
			expNeeded = 55000;
			return expNeeded;
		}
		else if(level == 36) {
			expNeeded = 58700;
			return expNeeded;
		}
		else if(level == 37) {
			expNeeded = 62400;
			return expNeeded;
		}
		else if(level == 38) {
			expNeeded = 66200;
			return expNeeded;
		}
		else if(level == 39) {
			expNeeded = 70200;
			return expNeeded;
		}
		else if(level == 40) {
			expNeeded = 74300;
			return expNeeded;
		}
		else if(level == 41) {
			expNeeded = 78500;
			return expNeeded;
		}
		else if(level == 42) {
			expNeeded = 82800;
			return expNeeded;
		}
		else if(level == 43) {
			expNeeded = 87100;
			return expNeeded;
		}
		else if(level == 44) {
			expNeeded = 91600;
			return expNeeded;
		}
		else if(level == 45) {
			expNeeded = 96300;
			return expNeeded;
		}
		else if(level == 46) {
			expNeeded = 101000;
			return expNeeded;
		}
		else if(level == 47) {
			expNeeded = 105800;
			return expNeeded;
		}
		else if(level == 48) {
			expNeeded = 110700;
			return expNeeded;
		}
		else if(level == 49) {
			expNeeded = 115700;
			return expNeeded;
		}
		else if(level == 50) {
			expNeeded = 120900;
			return expNeeded;
		}
		else if(level == 51) {
			expNeeded = 126100;
			return expNeeded;
		}
		else if(level == 52) {
			expNeeded = 131500;
			return expNeeded;
		}
		else if(level == 53) {
			expNeeded = 137000;
			return expNeeded;
		}
		else if(level == 54) {
			expNeeded = 142500;
			return expNeeded;
		}
		else if(level == 55) {
			expNeeded = 148200;
			return expNeeded;
		}
		else if(level == 56) {
			expNeeded = 154000;
			return expNeeded;
		}
		else if(level == 57) {
			expNeeded = 159900;
			return expNeeded;
		}
		else if(level == 58) {
			expNeeded = 165800;
			return expNeeded;
		}
		else if(level == 59) {
			expNeeded = 172000;
			return expNeeded;
		}
		else if(level == 60) {
			expNeeded = 290000;
			return expNeeded;
		}
		else if(level == 61) {
			expNeeded = 317000;
			return expNeeded;
		}
		else if(level == 62) {
			expNeeded = 349000;
			return expNeeded;
		}
		else if(level == 63) {
			expNeeded = 386000;
			return expNeeded;
		}
		else if(level == 64) {
			expNeeded = 428000;
			return expNeeded;
		}
		else if(level == 65) {
			expNeeded = 475000;
			return expNeeded;
		}
		else if(level == 66) {
			expNeeded = 527000;
			return expNeeded;
		}
		else if(level == 67) {
			expNeeded = 585000;
			return expNeeded;
		}
		else if(level == 68) {
			expNeeded = 648000;
			return expNeeded;
		}
		else if(level == 69) {
			expNeeded = 717000;
			return expNeeded;
		}
		else {
			expNeeded = 1;
			return expNeeded;
		}
	}

	public static float getDisplayXFactor() {
		return displayXFactor;
	}

	public static void setDisplayXFactor(float displayXFactor) {
		Mideas.displayXFactor = displayXFactor;
	}

	public static float getDisplayYFactor() {
		return displayYFactor;
	}

	public static void setDisplayYFactor(float displayYFactor) {
		Mideas.displayYFactor = displayYFactor;
	}

	public static int getCharacterId() {
		return characterId;
	}

	public static void setCharacterId(int characterId) {
		Mideas.characterId = characterId;
	}
}
