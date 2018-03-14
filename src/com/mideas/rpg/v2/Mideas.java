package com.mideas.rpg.v2;

import java.awt.FontFormatException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.sql.SQLException;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Cursor;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import com.mideas.rpg.v2.chat.ChatCommandMgr;
import com.mideas.rpg.v2.chat.ChatFrame;
import com.mideas.rpg.v2.chat.lua.ChatAdvancedCommandMgr;
import com.mideas.rpg.v2.chat.lua.StoreLuaFunctions;
import com.mideas.rpg.v2.command.CommandLogout;
import com.mideas.rpg.v2.connection.AuthServerConnectionRunnable;
import com.mideas.rpg.v2.connection.Connection;
import com.mideas.rpg.v2.connection.ConnectionManager;
import com.mideas.rpg.v2.files.cache.ItemCacheMgr;
import com.mideas.rpg.v2.files.config.ChatConfigManager;
import com.mideas.rpg.v2.files.config.ConfigManager;
import com.mideas.rpg.v2.files.dbc.AuraDBC;
import com.mideas.rpg.v2.files.dbc.SpellDBC;
import com.mideas.rpg.v2.game.CharacterStuff;
import com.mideas.rpg.v2.game.GameState;
import com.mideas.rpg.v2.game.classes.ClassManager;
import com.mideas.rpg.v2.game.item.container.ContainerManager;
import com.mideas.rpg.v2.game.item.potion.PotionManager;
import com.mideas.rpg.v2.game.item.shop.Shop;
import com.mideas.rpg.v2.game.item.shop.ShopManager;
import com.mideas.rpg.v2.game.spell.SpellManager;
import com.mideas.rpg.v2.game.task.TaskManager;
import com.mideas.rpg.v2.game.unit.Joueur;
import com.mideas.rpg.v2.hud.AdminPanelFrame;
import com.mideas.rpg.v2.hud.AuraFrame;
import com.mideas.rpg.v2.hud.ChangeBackGroundFrame;
import com.mideas.rpg.v2.hud.CharacterFrame;
import com.mideas.rpg.v2.hud.ContainerFrame;
import com.mideas.rpg.v2.hud.DragManager;
import com.mideas.rpg.v2.hud.EscapeFrame;
import com.mideas.rpg.v2.hud.LoginScreen;
import com.mideas.rpg.v2.hud.PartyFrame;
import com.mideas.rpg.v2.hud.PerformanceBarFrame;
import com.mideas.rpg.v2.hud.PopupFrame;
import com.mideas.rpg.v2.hud.RealmListFrame;
import com.mideas.rpg.v2.hud.SelectScreen;
import com.mideas.rpg.v2.hud.SocketingFrame;
import com.mideas.rpg.v2.hud.SpellBarFrame;
import com.mideas.rpg.v2.hud.TradeFrame;
import com.mideas.rpg.v2.hud.auction.hold.AuctionHouseFrame;
import com.mideas.rpg.v2.hud.social.SocialFrame;
import com.mideas.rpg.v2.jdo.JDO;
import com.mideas.rpg.v2.jdo.JDOStatement;
import com.mideas.rpg.v2.jdo.wrapper.MariaDB;
import com.mideas.rpg.v2.render.Draw;
import com.mideas.rpg.v2.render.PNGDecoder;
import com.mideas.rpg.v2.render.Sprites;
import com.mideas.rpg.v2.render.Texture;
import com.mideas.rpg.v2.stresstest.StresstestConnectionMgr;
import com.mideas.rpg.v2.stresstest.StresstestMgr;
import com.mideas.rpg.v2.utils.StringUtils;
import com.mideas.rpg.v2.utils.UIElement;

public class Mideas {
	
	private static AuthServerConnectionRunnable authServerConnectionRunnable;
	private static GameState gameState = GameState.LOGGED_OUT;
	private static Thread authServerConnectionThread;
	private static float displayXFactor = 1700/1920f;
	private static float displayYFactor = 930/1018f;
	private final static String IP = "127.0.0.1";
	private static BufferedImage cursor_image;
	private static double interfaceDrawTime;
	private static Shop shop = new Shop();
	private static boolean closeRequested;
	private static boolean currentPlayer;
	private static double mouseEventTime;
	private static Connection connection;
	private final static int PORT = 5720;
	private static SocketChannel socket;
	private static String accountName;
	private static Joueur joueur1;
	private static int expNeeded;
	private static long gold_calc;
	private static double ping;
	private static JDO jdo;
	//private static String cursor;
	private static UIElement hoveredElement;
	private static long last;
	private static int count;
	private static int fps;
	private static String fpsString;
	private static int i;
	private static int k;
	static long usedRAM;
	private static int accountId;
	private static int rank;
	public final static int FPS = 60;
	private static boolean hover;
	private static long LOOP_TICK_TIMER;
	private static short mouseScrolledTick;

	public final static short SCROLL_TICK_VALUE = 120;
	public final static int TIMEOUT_TIMER = 15000;
	public final static int PING_FREQUENCE = 10000;
	public final static int GC_FREQUENCE = 30000;
	public final static int RAM_UPDATE_FREQUENCE = 5000;
	private static long LAST_MOUSE_EVENT_TIMER;
	private static long LAST_INTERFACE_DRAW_TIMER;
	private final static int INTERFACE_DRAW_UPDATE_FREQUENCE = 1000;
	private final static int MOUSE_EVENT_UPDATE_FREQUENCE = 1000;
	
	public static void context2D() {
		GL11.glEnable(GL11.GL_TEXTURE_2D);            
		GL11.glClearColor(0f, 0f, 0f, 0f);                
		GL11.glClearDepth(1f);     
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glViewport(0, 0, Display.getWidth(), Display.getHeight());
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, Display.getWidth(), Display.getHeight(), 0, 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
	}
	
	private static void loop() throws FontFormatException, IOException, LWJGLException, IllegalAccessException, ClassNotFoundException {
		//System.setProperty("org.lwjgl.opengl.Window.undecorated", "false");
		//Display.setDisplayMode(new DisplayMode(1200, 800));
		//Display.setDisplayMode(new DisplayMode(1200, 930));
		//setDisplayMode(1920, 1080, false);
		//Display.setFullscreen(true);
		Display.setTitle("World Of Warcraft");
		final String[] ICON_PATHS = {"sprite/interface/icon_32.png", "sprite/interface/icon_128.png"};
		final ByteBuffer[] icon_array = new ByteBuffer[ICON_PATHS.length];
		int i = ICON_PATHS.length;
		while(--i >= 0) {
			icon_array[i] = PNGDecoder.decode(new File(ICON_PATHS[i]));
		}
		Display.setIcon(icon_array);
		Display.create();
		Display.setResizable(true);
		Display.setDisplayMode(new DisplayMode(1700, 930));
		Display.setVSyncEnabled(true);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_BLEND);
		FontManager.init();
		long time = System.currentTimeMillis();
		loadingScreen(true);
		System.out.println("Sprites loaded in "+(System.currentTimeMillis()-time)/1000.0+"s.");
		cursor_image = ImageIO.read(new File("sprite/interface/cursor.png"));
		final int cursor_width = cursor_image.getWidth();
		final int cursor_height = cursor_image.getHeight();
		final int[] cursor_pixels = new int[cursor_width*cursor_height];
		final ByteBuffer cursor_buffer = BufferUtils.createByteBuffer(cursor_width*cursor_height*4);
		cursor_image.getRGB(0, 0, cursor_width, cursor_height, cursor_pixels, 0, cursor_width);
		int cursor_y = 32;
		while(--cursor_y >= 0) {
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
		time = System.currentTimeMillis();
		initSQL();
		CharacterStuff.initSQLRequest();
		//GemManager.loadGems();
		//WeaponManager.loadWeapons();
		ItemCacheMgr.readItemCache();
		SpellDBC.readFile();
		AuraDBC.readFile();
		//PotionManager.loadPotions();
		ChatCommandMgr.initCommandMap();
		ContainerManager.loadBags();
		ContainerManager.loadBagsSprites();
		//StuffManager.loadStuffs();
		ShopManager.loadStuffs();
		//SpellManager.loadSpells();
		ClassManager.loadClasses();
		StringUtils.initValues();
		//GemManager.loadGemSprites();
		ConfigManager.initConfigMap();
		ConfigManager.loadConfig();
		ChatConfigManager.initConfigMap();
		StresstestMgr.initCommandMap();
		StresstestConnectionMgr.initPacket();
		System.out.println(PotionManager.getNumberPotionLoaded()+" potions loaded, "+SpellManager.getNumberSpellLoaded()+" spells loaded in "+(System.currentTimeMillis()-time)/1000.0+"s.");
		authServerConnectionRunnable = new AuthServerConnectionRunnable();
		authServerConnectionThread = new Thread(authServerConnectionRunnable);
		authServerConnectionThread.start();
		System.gc();
		usedRAM = Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
		Keyboard.enableRepeatEvents(true);
		context2D();
		try {
			while(!closeRequested) {
				LOOP_TICK_TIMER = System.currentTimeMillis();
				if(Display.isCloseRequested()) {
					closeRequested = true;
				}
				fpsUpdate();
				GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
				if(ConnectionManager.isConnected()) {
					ConnectionManager.read();
				}
				if(ConnectionManager.isAuthServerConnected()) {
					ConnectionManager.readAuthServer();
				}
				time = System.nanoTime();
				while(Mouse.next()) {
					mouseScrolledTick = (short)Mouse.getDWheel();
					if(Interface.mouseEvent()) {
						continue;
					}
				}
				if(LOOP_TICK_TIMER-LAST_MOUSE_EVENT_TIMER >= MOUSE_EVENT_UPDATE_FREQUENCE) {
					mouseEventTime = System.nanoTime()-time;
					LAST_MOUSE_EVENT_TIMER = LOOP_TICK_TIMER;
				}
				while(Keyboard.next()) {
					if(Interface.keyboardEvent()) {
						continue;
					}
				}
				if(Display.wasResized()) {
					context2D();
					updateDisplayFactor();
				}
				StresstestMgr.readClient();
				TaskManager.executeTask();
				time = System.nanoTime();
				Interface.draw();
				if (Texture.generatingTextureIdAsync)
					Texture.generateNextTextureIdAsync();
				if(LOOP_TICK_TIMER-LAST_INTERFACE_DRAW_TIMER >= INTERFACE_DRAW_UPDATE_FREQUENCE) {
					interfaceDrawTime = System.nanoTime()-time;
					LAST_INTERFACE_DRAW_TIMER = LOOP_TICK_TIMER;
				}
				Display.update();
				//Display.sync(240);
			}
		}
		catch(IllegalStateException e) {
			e.printStackTrace();
		}
		ChatConfigManager.saveConfig();
		CommandLogout.write();
		ConnectionManager.close();
		ConfigManager.saveConfig();
		ItemCacheMgr.writeItemCache();
		authServerConnectionRunnable.setRun(false);
		
	}
	
	public static void main(String[] args) throws FontFormatException, IOException, LWJGLException, IllegalAccessException, ClassNotFoundException {
		StoreLuaFunctions.initFunctionMap();
		//ChatAdvancedCommandMgr.handleCommand("GetFunction(\"a\\\"rg(1\", \"5555\",    \"BLAB)LA\",     \"1515151515120029990.\"   ,    GetMouseFocus(GetMouseFocus(GetMouseFocus(\"abc\"), \"def\")), \"args2\", 540   , 93154   )", false);
		//ChatAdvancedCommandMgr.handleCommand("GetMouseFocus(Random(1, 100))", true);
		//ChatAdvancedCommandMgr.handleCommand("TestFrame    :     GetWidth()", true);
		loop();
		saveAllStats();
	}
	
	public static void closeGame() {
		closeRequested = true;
	}
	
	public static void initSQL() throws IllegalAccessException, ClassNotFoundException {
		try {
			jdo = new MariaDB("127.0.0.1", 3306, "rpg", "root", "mideas");
			//jdo = new MariaDB("88.163.90.215", 3306, "rpg", "root", "mideas");
			//jdo = new MariaDB("82.236.60.133", 3306, "rpg", "root", "mideas");
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void updateDisplayFactor() {
		displayXFactor = Display.getWidth()/1920f;
		displayYFactor = Display.getHeight()/1018f;
		LoginScreen.shouldUpdate();
		SelectScreen.shouldUpdate();
		DragManager.shouldUpdate();
		AdminPanelFrame.shouldUpdate();
		CharacterFrame.shouldUpdate();
		SocketingFrame.shouldUpdate();
		EscapeFrame.shouldUpdate();
		TradeFrame.shouldUpdate();
		RealmListFrame.shouldUpdate();
		SocialFrame.shouldUpdate();
		PartyFrame.shouldUpdate();
		ChatFrame.shouldUpdate();
		PerformanceBarFrame.shouldUpdate();
		PopupFrame.shouldUpdate();
		SpellBarFrame.shouldUpdate();
		AuraFrame.shouldUpdate();
		AuctionHouseFrame.shouldUpdate();
		Interface.getMailFrame().shouldUpdateSize();
		//Interface.getAuctionHouseFrame().shouldUpdateSize();
		if(joueur1 != null) {
			if(joueur1.getFirstProfession() != null) {
				joueur1.getFirstProfession().updateSize(Display.getWidth()/2-200, Display.getHeight()/2-300);
			}
			if(joueur1.getGuild() != null) {
				joueur1.getGuild().updateMemberNote();
			}
			ContainerFrame.updateBagFrameSize();
		}
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
	
	public static long getLoopTickTimer() {
		return LOOP_TICK_TIMER;
	}
	
	public static void fpsUpdate() {
		count++;
		if(System.currentTimeMillis()-last >= 1000) {
			last = System.currentTimeMillis();
			if(count != fps) {
				fps = count;
				fpsString = String.valueOf(fps);
			}
			count = 0;
		}
	}
	
	public static String getFPSString() {
		return fpsString;
	}
	
	public static void setUsedRam(long ram) {
		usedRAM = ram;
	}
	
	public static void setPing(double pings) {
		ping = pings;
	}
	
	public static short getMouseScrolledTick() {
		return mouseScrolledTick;
	}
	
	public static double getPing() {
		return ping;
	}
	
	public static boolean getHover() {
		return hover;
	}
	
	public static UIElement getHoveredElement()
	{
		return (hoveredElement);
	}
	
	public static void setHover(UIElement element, boolean we)
	{
		hover = we;
		hoveredElement = element;
	}
	
	public static void setHover(boolean we)
	{
		hover = we;
	}
	
	public static void resetHover()
	{
		hover = true;
		hoveredElement = null;
	}
	
	public static SocketChannel getSocket() {
		return socket;
	}
	
	public static void connectToServer() throws IOException {
		socket = SocketChannel.open();
		socket.socket().connect(new InetSocketAddress(IP, PORT), 5000);
	}
	
	public static Connection getConnection() {
		return connection;
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
	
	public static long getUsedRAM() {
		return usedRAM;
	}
	
	public static Joueur getRandomClass(int id) {
		double rand = Math.random();
		rand = 1/9f;
		if(rand <= 1/9f) {
			return ClassManager.getClone("Guerrier");
		}
		else if(rand <= 2/9f) {
			System.out.println("Le joueur "+id+" est un Priest !");
			return ClassManager.getClone("Priest");
		}
		else if(rand <= 3/9f){
			System.out.println("Le joueur "+id+" est un Mage !");
			return ClassManager.getClone("Mage");
		}
		else if(rand <= 4/9f) {
			System.out.println("Le joueur "+id+" est un Hunter !");
			return ClassManager.getClone("Hunter");
		}
		else if(rand <= 5/9f) {
			System.out.println("Le joueur "+id+" est un Paladin !");
			return ClassManager.getClone("Paladin");
		}
		else if(rand <= 6/9f) {
			System.out.println("Le joueur "+id+" est un Rogue !");
			return ClassManager.getClone("Rogue");
		}
		else if(rand <= 7/9f) {
			System.out.println("Le joueur "+id+" est un Shaman !");
			return ClassManager.getClone("Shaman");
		}
		else if(rand <= 8/9f) {
			System.out.println("Le joueur "+id+" est un Druide !");
			return ClassManager.getClone("Druid");
		}
		System.out.println("Le joueur "+id+" est un Warlock !");
		return ClassManager.getClone("Warlock");
	}
	
	public static void mTime(long time, String text) {
		System.out.println(System.currentTimeMillis()-time+"ms "+text);
	}
	
	public static void nTime(long time, String text) {
		long timer = System.nanoTime();
		System.out.println((timer-time)+"ns "+((timer-time)/1000)+"µs "+((timer-time)/1000000)+"ms "+text);
	}
	
	public static void setConfig() {
		try {
			JDOStatement statement = Mideas.getJDO().prepare("UPDATE config SET value = ? WHERE `key` = ?");
			statement.putString("bg");
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
		catch(SQLException e) {
			e.printStackTrace();
		}
	}

	public static void getConfig() throws NumberFormatException {
		try {
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
			CharacterFrame.setMouseX(Integer.parseInt(temp));
	
			statement = Mideas.getJDO().prepare("SELECT value FROM config WHERE `key` = ?");
			statement.putString("y_inventory_frame");
			statement.execute();
			if(statement.fetch()) {
				temp = statement.getString();
			}
			CharacterFrame.setMouseY(Integer.parseInt(temp));
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static int calcGoldCoin() {
		gold_calc = Mideas.joueur1.getGold();
		i = 0;
		while(gold_calc-10000 >= 0) {
			gold_calc-= 10000;
			i++;
		}
		return i;
	}
	
	public static int calcSilverCoin() {
		gold_calc = Mideas.joueur1().getGold()-i*10000;
		k = 0;
		while(gold_calc-100 >= 0) {
			gold_calc-= 100;
			k++;
		}
		return k;
	}
	
	public static int calcGoldCoinCost(long cost) {
		i = 0;
		while(cost-10000 >= 0) {
			cost-= 10000;
			i++;
		}
		return i;
	}
	
	public static int calcSilverCoinCost(long cost) {
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
	
	public static String getAccountName() {
		return accountName;
	}
	
	public static void setAccountName(String accountName) {
		Mideas.accountName = accountName;
	}

	public static void setJoueur1(Joueur joueur) {
		joueur1 = new Joueur(joueur);
	}
	
	/*private static void lessCd() {
		int i = 0;
		int j = 0;
		first:
		while(i < Mideas.joueur1.getSpells().length) {
			j = 0;
			while(j < i) { //check if the spells i appears more than once between 0 and i because j < i
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
	}*/
	
	public static void loadingScreen(boolean fastReload) {
		/*Sprites.initBG();
		context2D();
		int barWidth = (int)(850*Mideas.getDisplayXFactor());
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		Draw.drawQuadBG(Sprites.loading_screen);
		Draw.drawQuad(Sprites.loading_screen_bar, Display.getWidth()/2-barWidth/2, Display.getHeight()/2+350*Mideas.getDisplayYFactor(), barWidth, 40*Mideas.getDisplayYFactor());
		Display.update();
		Display.sync(60);
		if(Display.wasResized()) {
			context2D();
			updateDisplayFactor();
			barWidth = (int)(850*Mideas.getDisplayXFactor());
		}
		
		Sprites.sprite();
		context2D();
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		Draw.drawQuadBG(Sprites.loading_screen);
		Draw.drawQuad(Sprites.loading_screen_bar_progress, Display.getWidth()/2-barWidth/2+45, Display.getHeight()/2+350*Mideas.getDisplayYFactor()+7, 400, 20);
		Draw.drawQuad(Sprites.loading_screen_bar, Display.getWidth()/2-barWidth/2, Display.getHeight()/2+350*Mideas.getDisplayYFactor(), barWidth, 40*Mideas.getDisplayYFactor());
		Display.update();
		Display.sync(60);
		if(Display.wasResized()) {
			context2D();
			updateDisplayFactor();
			barWidth = (int)(850*Mideas.getDisplayXFactor());
		}
		Sprites.sprite2();
		context2D();
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		Draw.drawQuadBG(Sprites.loading_screen);
		Draw.drawQuad(Sprites.loading_screen_bar_progress, Display.getWidth()/2-barWidth/2+45, Display.getHeight()/2+350*Mideas.getDisplayYFactor()+7, 500, 20);
		Draw.drawQuad(Sprites.loading_screen_bar, Display.getWidth()/2-barWidth/2, Display.getHeight()/2+350*Mideas.getDisplayYFactor(), barWidth, 40*Mideas.getDisplayYFactor());
		Display.update();
		Display.sync(60);
		if(Display.wasResized()) {
			context2D();
			updateDisplayFactor();
			barWidth = (int)(850*Mideas.getDisplayXFactor());
		}
		
		Sprites.sprite8();
		context2D();
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		Draw.drawQuadBG(Sprites.loading_screen);
		Draw.drawQuad(Sprites.loading_screen_bar_progress, Display.getWidth()/2-barWidth/2+45, Display.getHeight()/2+350*Mideas.getDisplayYFactor()+7, 600, 20);
		Draw.drawQuad(Sprites.loading_screen_bar, Display.getWidth()/2-barWidth/2, Display.getHeight()/2+350*Mideas.getDisplayYFactor(), barWidth, 40*Mideas.getDisplayYFactor());
		Display.update();
		Display.sync(60);
		if(Display.wasResized()) {
			context2D();
			updateDisplayFactor();
			barWidth = (int)(850*Mideas.getDisplayXFactor());
		}
		
		Sprites.sprite9();
		Sprites.sprite10();
		context2D();
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		Draw.drawQuadBG(Sprites.loading_screen);
		Draw.drawQuad(Sprites.loading_screen_bar_progress, Display.getWidth()/2-barWidth/2+45, Display.getHeight()/2+350*Mideas.getDisplayYFactor()+7, barWidth-90, 20);
		Draw.drawQuad(Sprites.loading_screen_bar, Display.getWidth()/2-barWidth/2, Display.getHeight()/2+350*Mideas.getDisplayYFactor(), barWidth, 40*Mideas.getDisplayYFactor());
		Display.update();
		Display.sync(60);
		if(Display.wasResized()) {
			context2D();
			updateDisplayFactor();
			barWidth = (int)(850*Mideas.getDisplayXFactor());
		}*/
		if (fastReload)
		{
			Sprites.initBG();
			Sprites.sprite();
			Sprites.sprite2();
			Sprites.sprite8();
			Sprites.sprite9();
			Sprites.sprite10();
		}
		Texture.loadAllTextureDatasAsync();
		Texture.loadAllTexture(fastReload);
	}
	
	public static Joueur joueur1() {
		return joueur1;
	}
	
	public static void setJoueur1Null() {
		joueur1 = null;
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
	
	public static int getFps() {
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
	
	public static void saveAllStats() {
		if(Mideas.joueur1() != null) {
			CharacterStuff.setBagItems();
			CharacterStuff.setEquippedBags();
			CharacterStuff.setEquippedItems();
			Mideas.joueur1().setGold(Mideas.joueur1().getGold());
			Mideas.joueur1().setExp(Mideas.joueur1().getExp());
		}
		Mideas.setConfig();
	}
	
	public static GameState getGameState() {
		return gameState;
	}
	
	public static void setGameState(GameState state) {
		gameState = state;
	}
	
	public static int getLevel(long exp) {
		if(exp <= 400) {
			return 1;
		}
		else if(exp <= 900) {
			return 2;
		}
		else if(exp <= 1400) {
			return 3;
		}
		else if(exp <= 2100) {
			return 4;
		}
		else if(exp <= 2800) {
			return 5;
		}
		else if(exp <= 3600) {
			return 6;
		}
		else if(exp <= 4500) {
			return 7;
		}
		else if(exp <= 5400) {
			return 8;
		}
		else if(exp <= 6500) {
			return 9;
		}
		else if(exp <= 7600) {
			return 10;
		}
		else if(exp <= 8700) {
			return 11;
		}
		else if(exp <= 9800) {
			return 12;
		}
		else if(exp <= 11000) {
			return 13;
		}
		else if(exp <= 12300) {
			return 14;
		}
		else if(exp <= 13600) {
			return 15;
		}
		else if(exp <= 15000) {
			return 16;
		}
		else if(exp <= 16400) {
			return 17;
		}
		else if(exp <= 17800) {
			return 18;
		}
		else if(exp <= 19300) {
			return 19;
		}
		else if(exp <= 20800) {
			return 20;
		}
		else if(exp <= 22400) {
			return 21;
		}
		else if(exp <= 24000) {
			return 22;
		}
		else if(exp <= 25500) {
			return 23;
		}
		else if(exp <= 27200) {
			return 24;
		}
		else if(exp <= 28900) {
			return 25;
		}
		else if(exp <= 30500) {
			return 26;
		}
		else if(exp <= 32200) {
			return 27;
		}
		else if(exp <= 33900) {
			return 28;
		}
		else if(exp <= 36300) {
			return 29;
		}
		else if(exp <= 38800) {
			return 30;
		}
		else if(exp <= 41600) {
			return 31;
		}
		else if(exp <= 44600) {
			return 32;
		}
		else if(exp <= 48000) {
			return 33;
		}
		else if(exp <= 51400) {
			return 34;
		}
		else if(exp <= 55000) {
			return 35;
		}
		else if(exp <= 58700) {
			return 36;
		}
		else if(exp <= 62400) {
			return 37;
		}
		else if(exp <= 66200) {
			return 38;
		}
		else if(exp <= 70200) {
			return 39;
		}
		else if(exp <= 74300) {
			return 40;
		}
		else if(exp <= 78500) {
			return 41;
		}
		else if(exp <= 82800) {
			return 42;
		}
		else if(exp <= 87100) {
			return 43;
		}
		else if(exp <= 91600) {
			return 44;
		}
		else if(exp <= 96300) {
			return 45;
		}
		else if(exp <= 101000) {
			return 46;
		}
		else if(exp <= 105800) {
			return 47;
		}
		else if(exp <= 110700) {
			return 48;
		}
		else if(exp <= 115700) {
			return 49;
		}
		else if(exp <= 120900) {
			return 50;
		}
		else if(exp <= 126100) {
			return 51;
		}
		else if(exp <= 131500) {
			return 52;
		}
		else if(exp <= 137000) {
			return 53;
		}
		else if(exp <= 142500) {
			return 54;
		}
		else if(exp <= 148200) {
			return 55;
		}
		else if(exp <= 154000) {
			return 56;
		}
		else if(exp <= 159900) {
			return 57;
		}
		else if(exp <= 165800) {
			return 58;
		}
		else if(exp <= 172000) {
			return 59;
		}
		else if(exp <= 290000) {
			return 60;
		}
		else if(exp <= 317000) {
			return 61;
		}
		else if(exp <= 349000) {
			return 62;
		}
		else if(exp <= 386000) {
			return 63;
		}
		else if(exp <= 428000) {
			return 64;
		}
		else if(exp <= 475000) {
			return 65;
		}
		else if(exp <= 527000) {
			return 66;
		}
		else if(exp <= 585000) {
			return 67;
		}
		else if(exp <= 648000) {
			return 68;
		}
		else if(exp <= 717000) {
			return 69;
		}
		return 70;
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
}
