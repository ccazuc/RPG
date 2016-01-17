package com.mideas.rpg.v2;

import java.awt.FontFormatException;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.ByteBuffer;
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

import com.mideas.rpg.v2.game.CharacterStuff;
import com.mideas.rpg.v2.game.DeathKnight;
import com.mideas.rpg.v2.game.Guerrier;
import com.mideas.rpg.v2.game.Hunter;
import com.mideas.rpg.v2.game.Joueur;
import com.mideas.rpg.v2.game.Mage;
import com.mideas.rpg.v2.game.Monk;
import com.mideas.rpg.v2.game.Paladin;
import com.mideas.rpg.v2.game.Priest;
import com.mideas.rpg.v2.game.Rogue;
import com.mideas.rpg.v2.game.Shaman;
import com.mideas.rpg.v2.game.Warlock;
import com.mideas.rpg.v2.game.spell.Spell;
import com.mideas.rpg.v2.game.stuff.Bag;
import com.mideas.rpg.v2.game.stuff.Shop;
import com.mideas.rpg.v2.game.stuff.Stuff;
import com.mideas.rpg.v2.hud.ChangeBackGroundFrame;

public class Mideas {
	
	private static boolean currentPlayer;
	private static Joueur joueur1;
	private static Joueur joueur2;
	private static Bag bag = new Bag();
	private static Shop shop = new Shop();
	private static String cursor;
	//private static JDO jdo;
	private static long last;
	private static int count;
	private static String fps;
	private static boolean isSpritesLoaded;
	private static BufferedImage cursor_image;
	private static int exp;
	private static int expDeathKnight;
	private static int expGuerrier;
	private static int expHunter;
	private static int expMage;
	private static int expMonk;
	private static int expPaladin;
	private static int expPriest;
	private static int expRogue;
	private static int expShaman;
	private static int expWarlock;
	private static int level;
	private static int expNeeded;
	private static int gold;
	private static int i;
	private static int k;
	
	public static void context2D() throws LWJGLException, IOException {
		GL11.glEnable(GL11.GL_TEXTURE_2D);            
        GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);                
        GL11.glClearDepth(1);     
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glViewport(0,0,Display.getWidth(),Display.getHeight());
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0, Display.getWidth(), Display.getHeight(), 0, 1, -1);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glLoadIdentity();
        /*cursor_image = ImageIO.read(new File("sprite/interface/cursor.png"));
		final int cursor_width = cursor_image.getWidth();
		final int cursor_height = cursor_image.getHeight();
		final int[] cursor_pixels = new int[cursor_width*cursor_height];
		final ByteBuffer cursor_buffer = BufferUtils.createByteBuffer(cursor_width*cursor_height*4);
		cursor_image.getRGB(0, 0, cursor_width, cursor_height, cursor_pixels, 0, cursor_width);
		int cursor_y = 32;
		while(--cursor_y > -1) {
			int cursor_x = -1;
			while(++cursor_x < 32) {
				int i = cursor_y*32+cursor_x;
				cursor_buffer.put((byte)(cursor_pixels[i]>>16));
				cursor_buffer.put((byte)(cursor_pixels[i]>>8));
				cursor_buffer.put((byte) cursor_pixels[i]);
				cursor_buffer.put((byte)(cursor_pixels[i]>>24));
			}
		}
		cursor_buffer.position(0);
		Mouse.setNativeCursor(new Cursor(32, 32, 0, 31, 1, cursor_buffer.asIntBuffer(), null));*/
	}
	
	private static void loop() throws FontFormatException, IOException, LWJGLException, SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException, InterruptedException {
		//System.setProperty("org.lwjgl.opengl.Window.undecorated", "false");
		//Display.setDisplayMode(new DisplayMode(1200, 800));
		Display.setDisplayMode(new DisplayMode(1200, 930));
		//setDisplayMode(1920, 1080, true);
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
		Display.setDisplayMode(new DisplayMode(1200, 930));
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
		if(!isSpritesLoaded) {
			Sprites.sprite();
			TTF2.init();
			Stuff.loadSlotStuff();
			isSpritesLoaded = true;
		}
		
		/*cursor_image = ImageIO.read(new File("sprite/interface/cursor.png"));
		final int cursor_width = cursor_image.getWidth();
		final int cursor_height = cursor_image.getHeight();
		final int[] cursor_pixels = new int[cursor_width*cursor_height];
		final ByteBuffer cursor_buffer = BufferUtils.createByteBuffer(cursor_width*cursor_height*4);
		cursor_image.getRGB(0, 0, cursor_width, cursor_height, cursor_pixels, 0, cursor_width);
		int cursor_y = 32;
		while(--cursor_y > -1) {
			int cursor_x = -1;
			while(++cursor_x < 32) {
				int i = cursor_y*32+cursor_x;
				cursor_buffer.put((byte)(cursor_pixels[i]>>16));
				cursor_buffer.put((byte)(cursor_pixels[i]>>8));
				cursor_buffer.put((byte) cursor_pixels[i]);
				cursor_buffer.put((byte)(cursor_pixels[i]>>24));
			}
		}
		cursor_buffer.position(0);
		Mouse.setNativeCursor(new Cursor(32, 32, 0, 31, 1, cursor_buffer.asIntBuffer(), null));*/
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		/*openSQLite();
		generateStuff();
		joueur1 = getRandomClass(1);
		joueur2 = getRandomClass(2);*/
		/*joueur1.setLeggings((Leggings)stuffManager.getStuff(1));
		joueur1.setChest((Chest)stuffManager.getStuff(3));
		joueur1.setHead((Head)stuffManager.getStuff(5));
		joueur1.setBoots((Boots)stuffManager.getStuff(7));
		joueur1.setWrists((Wrists)stuffManager.getStuff(9));
		joueur2.setLeggings((Leggings)StuffManager.getStuff(2));
		joueur2.setChest((Chest)StuffManager.getStuff(4));
		joueur2.setHead((Head)StuffManager.getStuff(6));
		joueur2.setBoots((Boots)StuffManager.getStuff(8));
		joueur2.setWrists((Wrists)StuffManager.getStuff(10));*/
		/*Hud.lifeBars();
		Hud.manaBars();*/
		joueur2 = getRandomClass(2);
		while(!Display.isCloseRequested()) {
			fpsUpdate();
			context2D();
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
			if(joueur1 != null && !currentPlayer && Mideas.joueur2.getStamina() > 0) {
				joueur2.attackUI(Spell.getRandomSpell());
				currentPlayer = true;
				lessCd();
			}
			while(Mouse.next()) {
				if(Interface.mouseEvent()) {
					continue;
				}
			}
			while(Keyboard.next()) {
				if(Interface.keyboardEvent()) {
					continue;
				}
			}
			Interface.draw();
			Display.update();
			Display.sync(60);
		}
		/*
		boolean closeRequested = false;
		while(!(closeRequested = Display.isCloseRequested()) && !c) {
			fpsUpdate();
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
			GL11.glLoadIdentity();
			context2D();
			while(Mouse.next()) {
				if(Mouse.getEventButtonState()) { 
					joueur1 = setClasse(1);
					System.out.println("gg");
				}
			}
			Hud.render();  
			x = Mouse.getX();
			y = Window.getHeight()-Mouse.getY();
			Draw.drawQuad(Sprites.bg, Display.getWidth()/2-Sprites.bg.getImageWidth()/2, Display.getHeight()/2-Sprites.bg.getImageHeight()/2);
			Draw.drawQuad(Sprites.class_choose, Window.getWidth()/2-162, Window.getHeight()/2-119);
			Draw.drawQuad(Sprites.warrior_create_classe, Window.getWidth()/2-140, Window.getHeight()/2-80);
			Draw.drawQuad(Sprites.paladin_create_classe,Window.getWidth()/2-78, Window.getHeight()/2-80);
			Draw.drawQuad(Sprites.hunter_create_classe,Window.getWidth()/2-16, Window.getHeight()/2-80);
			Draw.drawQuad(Sprites.rogue_create_classe,Window.getWidth()/2+46, Window.getHeight()/2-80);
			Draw.drawQuad(Sprites.priest_create_classe, Window.getWidth()/2+108, Window.getHeight()/2-80);
			Draw.drawQuad(Sprites.deathknight_create_classe,Window.getWidth()/2-140, Window.getHeight()/2-13);
			Draw.drawQuad(Sprites.shaman_create_classe,Window.getWidth()/2-78, Window.getHeight()/2-13);
			Draw.drawQuad(Sprites.mage_create_classe,Window.getWidth()/2-16, Window.getHeight()/2-13);
			Draw.drawQuad(Sprites.warlock_create_classe,Window.getWidth()/2+46, Window.getHeight()/2-13);
			Draw.drawQuad(Sprites.monk_create_classe,Window.getWidth()/2+108, Window.getHeight()/2-13);
			
			Draw.drawQuad(Sprites.border, Window.getWidth()/2-146, Window.getHeight()/2-85);
			Draw.drawQuad(Sprites.border2, Window.getWidth()/2-84, Window.getHeight()/2-85);
			Draw.drawQuad(Sprites.border3, Window.getWidth()/2-22, Window.getHeight()/2-85);
			Draw.drawQuad(Sprites.border4, Window.getWidth()/2+40, Window.getHeight()/2-85);
			Draw.drawQuad(Sprites.border5, Window.getWidth()/2+102, Window.getHeight()/2-85);
			Draw.drawQuad(Sprites.border6, Window.getWidth()/2-146, Window.getHeight()/2-18);
			Draw.drawQuad(Sprites.border7, Window.getWidth()/2-84, Window.getHeight()/2-18);
			Draw.drawQuad(Sprites.border8, Window.getWidth()/2-22, Window.getHeight()/2-18);
			Draw.drawQuad(Sprites.border9, Window.getWidth()/2+40, Window.getHeight()/2-18);
			Draw.drawQuad(Sprites.border10, Window.getWidth()/2+102, Window.getHeight()/2-18);
			
			TTF.playerName.drawString(Window.getWidth()/2-26, Window.getHeight()/2-108, "Class select", Color.yellow);
			
			if(x >= Window.getWidth()/2-140 && x <= Window.getWidth()/2-84 && y >= Window.getHeight()/2-80 && y <= Window.getHeight()/2-24) {
				Draw.drawQuad(Sprites.hover, Window.getWidth()/2-144, Window.getHeight()/2-82);
			}
			else if(x >= Window.getWidth()/2-78 && x <= Window.getWidth()/2-22 && y >= Window.getHeight()/2-80 && y <= Window.getHeight()/2-24) {
				Draw.drawQuad(Sprites.hover, Window.getWidth()/2-82, Window.getHeight()/2-82);
			}
			else if(x >= Window.getWidth()/2-16 && x <= Window.getWidth()/2+40 && y >= Window.getHeight()/2-80 && y <= Window.getHeight()/2-24) {
				Draw.drawQuad(Sprites.hover, Window.getWidth()/2-20, Window.getHeight()/2-82);
			}
			else if(x >= Window.getWidth()/2+46 && x <= Window.getWidth()/2+102 && y >= Window.getHeight()/2-80 && y <= Window.getHeight()/2-24) {
				Draw.drawQuad(Sprites.hover, Window.getWidth()/2+40, Window.getHeight()/2-82);
			}
			else if(x >= Window.getWidth()/2+108 && x <= Window.getWidth()/2+164 && y >= Window.getHeight()/2-80 && y <= Window.getHeight()/2-24) {
				Draw.drawQuad(Sprites.hover, Window.getWidth()/2+104, Window.getHeight()/2-82);
			}
			else if(x >= Window.getWidth()/2-140 && x <= Window.getWidth()/2-84 && y >= Window.getHeight()/2-13 && y <= Window.getHeight()/2+43) {
				Draw.drawQuad(Sprites.hover, Window.getWidth()/2-144, Window.getHeight()/2-15);
			}
			else if(x >= Window.getWidth()/2-78 && x <= Window.getWidth()/2-22 && y >= Window.getHeight()/2-13 && y <= Window.getHeight()/2+43) {
				Draw.drawQuad(Sprites.hover, Window.getWidth()/2-82, Window.getHeight()/2-15);
			}
			else if(x >= Window.getWidth()/2-16 && x <= Window.getWidth()/2+40 && y >= Window.getHeight()/2-13 && y <= Window.getHeight()/2+43) {
				Draw.drawQuad(Sprites.hover, Window.getWidth()/2-20, Window.getHeight()/2-15);
			}
			else if(x >= Window.getWidth()/2+46 && x <= Window.getWidth()/2+102 && y >= Window.getHeight()/2-13 && y <= Window.getHeight()/2+43) {
				Draw.drawQuad(Sprites.hover, Window.getWidth()/2+40, Window.getHeight()/2-15);
			}
			else if(x >= Window.getWidth()/2+108 && x <= Window.getWidth()/2+164 && y >= Window.getHeight()/2-13 && y <= Window.getHeight()/2+43) {
				Draw.drawQuad(Sprites.hover, Window.getWidth()/2+104, Window.getHeight()/2-15);
			}
			Display.update();
			Display.sync(60);
		}*/
		/*while(!(closeRequested = Display.isCloseRequested()) && joueur1.getStamina() > 0 && joueur2.getStamina() > 0) {
			boolean hasNext = Keyboard.next();
			if(hasNext) {
				do {
					if(Keyboard.getEventKeyState()) {
						if(Keyboard.getEventKey() == Keyboard.KEY_RETURN) {
							if(currentPlayer) {
        						joueur1.tick();
        					}
        					else {
        						joueur2.attackUI(Spell.getRandomSpell());
        					}
    						Hud.lifeBars();
    						Hud.manaBars();
    						Hud.redBars();
    						if(Hud.redBars()) {
    							break;
    						}
							currentPlayer = !currentPlayer;
						}
						if(Keyboard.getEventKey() == Keyboard.KEY_1) {
							if(currentPlayer) {
								joueur1.tick();
								Hud.lifeBars();
								Hud.manaBars();
        						Hud.redBars();
        						if(Hud.redBars()) {
        							break;
        						}
							}
        					else {
        						joueur2.attackUI(Spell.getRandomSpell());
        						Hud.lifeBars();
        						Hud.manaBars();
        						Hud.redBars();
        						if(Hud.redBars()) {
        							break;
        						}
        					}
							currentPlayer = !currentPlayer;
						}
						else if(Keyboard.getEventKey() == Keyboard.KEY_2) {
							if(currentPlayer) {
								joueur1.cast(Mideas.joueur1().getSpells()[2], joueur2);
								Hud.lifeBars();
								Hud.manaBars();
        						Hud.redBars();
        						if(Hud.redBars()) {
        							break;
        						}
							}
        					else {
        						joueur2.attackUI(Spell.getRandomSpell());
        						Hud.lifeBars();
        						Hud.manaBars();
        						Hud.redBars();
        						if(Hud.redBars()) {
        							break;
        						}
        					}
							currentPlayer = !currentPlayer;
						}
						else if(Keyboard.getEventKey() == Keyboard.KEY_3) {
							if(currentPlayer) {
								joueur1.cast(Mideas.joueur1().getSpells()[1], joueur2);
								Hud.lifeBars();
								Hud.manaBars();
        						Hud.redBars();
        						if(Hud.redBars()) {
        							break;
        						}
							}
        					else {
        						joueur2.attackUI(Spell.getRandomSpell());
        						Hud.lifeBars();
        						Hud.manaBars();
        						Hud.redBars();
        						if(Hud.redBars()) {
        							break;
        						}
        					}
							currentPlayer = !currentPlayer;
						}
						else if(Keyboard.getEventKey() == Keyboard.KEY_4) {
							if(currentPlayer) {
								joueur1.cast(Mideas.joueur1().getSpells()[0], joueur2);
								Hud.lifeBars();
								Hud.manaBars();
        						Hud.redBars();
        						if(Hud.redBars()) {
        							break;
        						}
							}
        					else {
        						joueur2.attackUI(Spell.getRandomSpell());
        						Hud.lifeBars();
        						Hud.manaBars();
        						Hud.redBars();
        						if(Hud.redBars()) {
        							break;
        						}
        					}
							currentPlayer = !currentPlayer;
						}
						else if(Keyboard.getEventKey() == Keyboard.KEY_C) {
							if(currentPlayer) {
								while(!key) {
									while(Keyboard.next()) {						
									}
									fpsUpdate();
									GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
									GL11.glLoadIdentity();
									context2D();
									Hud.render();
									Draw.drawQuad(Sprites.cursor, -100, -100);
									InventoryFrame.frame();
									Display.update();
									Display.sync(60);
									if(Keyboard.getEventKey() == Keyboard.KEY_ESCAPE) {
										key = true;
									}
								}
							}
							key = false;
						}
						else if(Keyboard.getEventKey() == Keyboard.KEY_B) {
							if(currentPlayer) {
								while(!key) {
									while(Keyboard.next()) {						
									}
									fpsUpdate();
									GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
									GL11.glLoadIdentity();
									context2D();
									Hud.render();
									Draw.drawQuad(Sprites.cursor, -100, -100);
									InventoryFrame.bags();
									Display.update();
									Display.sync(60);
									if(Keyboard.getEventKey() == Keyboard.KEY_ESCAPE) {
										key = true;
									}
								}
							}
						}
						key = false;
					}				
				} while(Keyboard.next());
			}
			while(Mouse.next()) {
				x = Mouse.getX();
				y = Display.getHeight()-Mouse.getY();
				if(Mouse.getEventButtonState()) {
					if(Hud.hoveredSpell != null) {
						if(currentPlayer) {
							joueur1.cast(Hud.hoveredSpell, joueur2);
							System.out.println(Hud.hoveredSpell);
    						Hud.redBars();
    						if(Hud.redBars()) {
    							break;
    						}
						}
						else {
							joueur2.cast(Spell.getRandomSpell(), joueur1);
							System.out.println(Hud.hoveredSpell);
    						Hud.redBars();
    						if(Hud.redBars()) {
    							break;
    						}
						}
						currentPlayer = !currentPlayer;
					}			
					else {
						x = Mouse.getX();
						y = Display.getHeight()-Mouse.getY();
						if(Mideas.x >= Window.getWidth()-739 && Mideas.y >= Window.getHeight()-61 && Mideas.x <= Window.getWidth()-683 && Mideas.y <= Window.getHeight()-5) {
							if(currentPlayer) {
								joueur1.tick();
			        			Hud.lifeBars();
        						Hud.manaBars();
        						Hud.redBars();
        						if(Hud.redBars()) {
        							break;
        						}
			        		}
			        		else {
			        			joueur2.attackUI(Spell.getRandomSpell());
			        			Hud.lifeBars();
        						Hud.manaBars();
        						Hud.redBars();
        						if(Hud.redBars()) {
        							break;
        						}
			        		}
			        		currentPlayer = !currentPlayer;
						}
					}
				}
			}
			if(!currentPlayer) {
				joueur2.attackUI(Spell.getRandomSpell());
				currentPlayer = !currentPlayer;
			}
			fpsUpdate();
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
			GL11.glLoadIdentity();
			context2D();
			Hud.render();
			Display.update();
			Display.sync(60);
		}
		if(!closeRequested) {
			while(!Display.isCloseRequested()) {
				fpsUpdate();
				GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
				GL11.glLoadIdentity();
				context2D();
				Display.sync(60);
				Hud.setStatusText("");
				Hud.setStatusText2("");
				Hud.redBars();
				Hud.render();
				x = Mouse.getX();
				y = Display.getHeight()-Mouse.getY();
				while(Keyboard.next()) {
					
				}
				Draw.drawQuad(Sprites.alert, Window.getWidth()/2-Sprites.button_hover.getImageWidth()/2-105, Window.getHeight()/2-80);
				if(x >= Window.getWidth()/2-130 && x <= Window.getWidth()/2-3 && y <= Window.getHeight()/2-18 && y >= Window.getHeight()/2-37) {
					Draw.drawQuad(Sprites.button_hover, Window.getWidth()/2-Sprites.button_hover.getImageWidth()/2-70, Window.getHeight()/2-43);
				}
				else {
					Draw.drawQuad(Sprites.button, Window.getWidth()/2-Sprites.button_hover.getImageWidth()/2-70, Window.getHeight()/2-43);
				}
				TTF.buttonFont.drawString(Window.getWidth()/2-TTF.buttonFont.getWidth("Retry")/2-70, Window.getHeight()/2-40, "Retry", Color.black);
				TTF.buttonFont.drawString(Window.getWidth()/2-TTF.buttonFont.getWidth("Retry")/2-69, Window.getHeight()/2-41, "Retry", Color.white);
				if(x >= Window.getWidth()/2+7 && x <= Window.getWidth()/2+134 && y <= Window.getHeight()/2-15 && y >= Window.getHeight()/2-38) {
					Draw.drawQuad(Sprites.button_hover2, Window.getWidth()/2-Sprites.button_hover.getImageWidth()/2+70, Window.getHeight()/2-43);
					if(Mouse.getEventButtonState()) {
						System.exit(1);
					}
				}
				else {
					Draw.drawQuad(Sprites.button2, Window.getWidth()/2-Sprites.button_hover.getImageWidth()/2+70, Window.getHeight()/2-43);
				}
				TTF.buttonFont.drawString(Window.getWidth()/2-TTF.buttonFont.getWidth("Quit")/2+70, Window.getHeight()/2-40, "Quit", Color.black);
				TTF.buttonFont.drawString(Window.getWidth()/2-TTF.buttonFont.getWidth("Quit")/2+69, Window.getHeight()/2-41, "Quit", Color.white);
				if(Mideas.joueur1.getStamina() <= 0) {
					TTF.font4.drawString(Window.getWidth()/2-49, Window.getHeight()/2-65, "Player 2 WON", Color.black);
					TTF.font4.drawString(Window.getWidth()/2-50, Window.getHeight()/2-66, "Player 2 WON", Color.white);
					//TTF.font2.drawString(250-104-TTF.font.getWidth("Dead")/2, 56, "Dead", Color.black); 
				}
				else if(Mideas.joueur2.getStamina() <= 0) {
					TTF.font4.drawString(Window.getWidth()/2-49, Window.getHeight()/2-65, "Player 1 WON", Color.black);
					TTF.font4.drawString(Window.getWidth()/2-50, Window.getHeight()/2-66, "Player 1 WON", Color.white);
					//TTF.font2.drawString(Window.getWidth()-104-TTF.font.getWidth("Dead")/2, 56, "Dead", Color.black);
					if(isExp == 0) {
						setExp();
						getLevel();
						isExp++;
					}
				}
				//Display.update();
				while(Mouse.next()) {
					if(Mouse.getEventButtonState()) {
						if(x >= Window.getWidth()/2-130 && x <= Window.getWidth()/2-3 && y <= Window.getHeight()/2-18 && y >= Window.getHeight()/2-37) {
							joueur2 = getRandomClass(2);
							joueur1.setStamina(joueur1.getMaxStamina());
							joueur1.setMana(joueur1.getMaxMana());
							/*joueur1.setLeggings((Leggings)stuffManager.getStuff(1));
							joueur1.setChest((Chest)stuffManager.getStuff(3));
							joueur1.setHead((Head)stuffManager.getStuff(5));
							joueur1.setBoots((Boots)stuffManager.getStuff(7));
							joueur1.setWrists((Wrists)stuffManager.getStuff(9));
							joueur2.setLeggings((Leggings)stuffManager.getStuff(2));
							joueur2.setChest((Chest)stuffManager.getStuff(4));
							joueur2.setHead((Head)stuffManager.getStuff(6));
							joueur2.setBoots((Boots)stuffManager.getStuff(8));
							joueur2.setWrists((Wrists)stuffManager.getStuff(10));
							System.out.println("Joueur 1 hp : "+Mideas.joueur1.getStamina());
							System.out.println("Joueur 2 hp : "+Mideas.joueur2.getStamina());
							Hud.lifeBars();
							Hud.manaBars();
							closeRequested = false;
							while(!(closeRequested = Display.isCloseRequested()) && joueur1.getStamina() > 0 && joueur2.getStamina() > 0) {
								boolean hasNext = Keyboard.next();
								isExp = 0;
								if(hasNext) {
									do {
										if(Keyboard.getEventKeyState()) {
											if(Keyboard.getEventKey() == Keyboard.KEY_RETURN) {
												if(currentPlayer) {
					        						joueur1.tick();
					        						Hud.lifeBars();
					        						Hud.manaBars();
					        						Hud.redBars();
					        						if(Hud.redBars()) {
					        							break;
					        						}
					        					}
					        					else {
					        						joueur2.attackUI(Spell.getRandomSpell());
					        						Hud.lifeBars();
					        						Hud.manaBars();
					        						Hud.redBars();
					        						if(Hud.redBars()) {
					        							break;
					        						}
					        					}
												currentPlayer = !currentPlayer;
											}
											if(Keyboard.getEventKey() == Keyboard.KEY_1) {
												if(currentPlayer) {
													joueur1.tick();
													Hud.lifeBars();
													Hud.manaBars();
					        						Hud.redBars();
					        						if(Hud.redBars()) {
					        							break;
					        						}
												}
					        					else {
					        						joueur2.attackUI(Spell.getRandomSpell());
					        						Hud.lifeBars();
					        						Hud.manaBars();
					        						Hud.redBars();
					        						if(Hud.redBars()) {
					        							break;
					        						}
					        					}
												currentPlayer = !currentPlayer;
											}
											else if(Keyboard.getEventKey() == Keyboard.KEY_2) {
												if(currentPlayer) {
													joueur1.cast(Mideas.joueur1().getSpells()[2], joueur2);
													Hud.lifeBars();
													Hud.manaBars();
					        						Hud.redBars();
					        						if(Hud.redBars()) {
					        							break;
					        						}
												}
					        					else {
					        						joueur2.attackUI(Spell.getRandomSpell());
					        						Hud.lifeBars();
					        						Hud.manaBars();
					        						Hud.redBars();
					        						if(Hud.redBars()) {
					        							break;
					        						}
					        					}
												currentPlayer = !currentPlayer;
											}
											else if(Keyboard.getEventKey() == Keyboard.KEY_3) {
												if(currentPlayer) {
													joueur1.cast(Mideas.joueur1().getSpells()[1], joueur2);
													Hud.lifeBars();
													Hud.manaBars();
					        						Hud.redBars();
					        						if(Hud.redBars()) {
					        							break;
					        						}
												}
					        					else {
					        						joueur2.attackUI(Spell.getRandomSpell());
					        						Hud.lifeBars();
					        						Hud.manaBars();
					        						Hud.redBars();
					        						if(Hud.redBars()) {
					        							break;
					        						}
					        					}
												currentPlayer = !currentPlayer;
											}
											else if(Keyboard.getEventKey() == Keyboard.KEY_4) {
												if(currentPlayer) {
													joueur1.cast(Mideas.joueur1().getSpells()[0], joueur2);
													Hud.lifeBars();
													Hud.manaBars();
					        						Hud.redBars();
					        						if(Hud.redBars()) {
					        							break;
					        						}
												}
					        					else {
					        						joueur2.attackUI(Spell.getRandomSpell());
					        						Hud.lifeBars();
					        						Hud.manaBars();
					        					}
												currentPlayer = !currentPlayer;
											}
											else if(Keyboard.getEventKey() == Keyboard.KEY_C) {
												if(currentPlayer) {
													while(!key) {
														while(Keyboard.next()) {			
														}
														fpsUpdate();
														Draw.drawQuad(Sprites.character_frame2, Window.getWidth()/2-300, Window.getHeight()/2-380);
														InventoryFrame.frame();
														Display.update();
														Display.sync(60);
														if(Keyboard.getEventKey() == Keyboard.KEY_ESCAPE) {
															key = true;
														}
													}
												}
											}
											key = false;
										}
									} while(Keyboard.next());
								}
								while(Mouse.next()) {
									x = Mouse.getX();
									y = Display.getHeight()-Mouse.getY();
									if(Mouse.getEventButtonState()) {
										if(Hud.hoveredSpell != null) {
											if(currentPlayer) {
												joueur1.cast(Hud.hoveredSpell, joueur2);
												System.out.println(Hud.hoveredSpell);
				        						Hud.redBars();
				        						if(Hud.redBars()) {
				        							break;
				        						}
											}
											currentPlayer = !currentPlayer;
										}			
										else {
											x = Mouse.getX();
											y = Display.getHeight()-Mouse.getY();
											if(Mideas.x >= Window.getWidth()-739 && Mideas.y >= Window.getHeight()-61 && Mideas.x <= Window.getWidth()-683 && Mideas.y <= Window.getHeight()-5) {
												if(currentPlayer) {
													joueur1.tick();
								        			Hud.lifeBars();
					        						Hud.manaBars();
					        						Hud.redBars();
					        						if(Hud.redBars()) {
					        							break;
					        						}
								        		}
								        		currentPlayer = !currentPlayer;
											}
										}
									}
								}
								if(!currentPlayer) {
									joueur2.attackUI(Spell.getRandomSpell());
									currentPlayer = !currentPlayer;
								}
								fpsUpdate();
								GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
								GL11.glLoadIdentity();
								context2D();
								Hud.render();
								Display.update();
								Display.sync(60);
							}
						}
					}
				}
				Display.update();
			}
		}*/
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
	
	public static void main(String[] args) throws FontFormatException, IOException, LWJGLException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, InterruptedException {
		loop();
	}
	
	public static Joueur getRandomClass(int id) {
		double rand = Math.random();
		//rand = 2/10f;
		if(rand <= 1/10f) {
			System.out.println("Le joueur "+id+" est un Guerrier !");
			return new Guerrier();
		}
		else if(rand <= 2/10f) {
			System.out.println("Le joueur "+id+" est un Priest !");
			return new Priest();
		}
		else if(rand <= 3/10f){
			System.out.println("Le joueur "+id+" est un Mage !");
			return new Mage();
		}
		else if(rand <= 4/10f) {
			System.out.println("Le joueur "+id+" est un DeathKnight !");
			return new DeathKnight();
		}
		else if(rand <= 5/10f) {
			System.out.println("Le joueur "+id+" est un Hunter !");
			return new Hunter();
		}
		else if(rand <= 6/10f) {
			System.out.println("Le joueur "+id+" est un Monk !");
			return new Monk();
		}
		else if(rand <= 7/10f) {
			System.out.println("Le joueur "+id+" est un Paladin !");
			return new Paladin();
		}
		else if(rand <= 8/10f) {
			System.out.println("Le joueur "+id+" est un Rogue !");
			return new Rogue();
		}
		else if(rand <= 9/10f) {
			System.out.println("Le joueur "+id+" est un Shaman !");
			return new Shaman();
		}
		else {
			System.out.println("Le joueur "+id+" est un Warlock !");
			return new Warlock();
		}
	}
	
	/*private static void generateStuff() throws SQLException {
		JDOStatement statement = jdo.prepare("SELECT * FROM items");
		statement.execute();
		while(statement.fetch()) {
			int id = statement.getInt();
			int type = statement.getInt();
			String name = statement.getString();
			int critical = statement.getInt();
			int strengh = statement.getInt();
			int stamina = statement.getInt();
			int armor = statement.getInt();
			int mana = statement.getInt();
			Stuff stuff = null;
			switch(type) {
				case 1:
					stuff = new Leggings(id, name, critical, strengh, stamina, armor, mana);
					break;
				case 2:
					stuff = new Chest(id, name, critical, strengh, stamina, armor, mana);
					break;
				case 3:
					stuff = new Head(id, name, critical, strengh, stamina, armor, mana);
					break;
				case 4:
					stuff = new Boots(id, name, critical, strengh, stamina, armor, mana);
					break;
				case 5:
					stuff = new Wrists(id, name, critical, strengh, stamina, armor, mana);
					break;
			}
			StuffManager.addStuff(stuff);
		}
	}*/
	
	public static int getExp() throws FileNotFoundException {
		BufferedReader br = null;
		try {
			int i = 0;
			String sCurrentLine;
			String tempExp[] = {"1","2","3"};
			br = new BufferedReader(new FileReader("exp.txt"));
			while ((sCurrentLine = br.readLine()) != null) {
				if(Mideas.getClassLine() == i) {
					tempExp = sCurrentLine.split("=");	
				}
				if(Mideas.getClassLine() == i) {
					tempExp = sCurrentLine.split("=");
				}
				if(Mideas.getClassLine() == i) {
					tempExp = sCurrentLine.split("=");
				}
				if(Mideas.getClassLine() == i) {
					tempExp = sCurrentLine.split("=");
				}
				if(Mideas.getClassLine() == i) {
					tempExp = sCurrentLine.split("=");
				}
				if(Mideas.getClassLine() == i) {
					tempExp = sCurrentLine.split("=");
				}
				if(Mideas.getClassLine() == i) {
					tempExp = sCurrentLine.split("=");
				}
				if(Mideas.getClassLine() == i) {
					tempExp = sCurrentLine.split("=");
				}
				if(Mideas.getClassLine() == i) {
					tempExp = sCurrentLine.split("=");
				}
				if(Mideas.getClassLine() == i) {
					tempExp = sCurrentLine.split("=");
				}
				i++;
			}
			exp = Integer.parseInt(tempExp[1]);
			
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			try {
				if (br != null) {
					br.close();
				}
			}
			catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return exp;
	}
	
	/*public static int getExp() throws FileNotFoundException {
		BufferedReader br = null;
		try {
			String sCurrentLine;
			br = new BufferedReader(new FileReader(ClassSelectFrame.expTxt()));
			while ((sCurrentLine = br.readLine()) != null) {
				exp = Integer.parseInt(sCurrentLine);
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			try {
				if (br != null)
					br.close();
			}
			catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return exp;
	}*/
	
	public static int getExpDeathKnight() throws FileNotFoundException {
		BufferedReader br = null;
		try {
			int i = 0;
			String sCurrentLine;
			String tempExp[] = {"1","2","3"};
			br = new BufferedReader(new FileReader("exp.txt"));
			while ((sCurrentLine = br.readLine()) != null) {
				if(i == 0) {
					tempExp = sCurrentLine.split("=");
				}
				i++;
			}
			expDeathKnight = Integer.parseInt(tempExp[1]);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			try {
				if (br != null)
					br.close();
			}
			catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return expDeathKnight;
	}
	
	public static int getExpGuerrier() throws FileNotFoundException {
		BufferedReader br = null;
		try {
			int i = 0;
			String sCurrentLine;
			String tempExp[] = {"1","2","3"};
			br = new BufferedReader(new FileReader("exp.txt"));
			while ((sCurrentLine = br.readLine()) != null) {
				if(i == 1) {
					tempExp = sCurrentLine.split("=");
				}
				i++;
			}
			expGuerrier = Integer.parseInt(tempExp[1]);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			try {
				if (br != null)
					br.close();
			}
			catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return expGuerrier;
	}
	
	public static int getExpHunter() throws FileNotFoundException {
		BufferedReader br = null;
		try {
			int i = 0;
			String sCurrentLine;
			String tempExp[] = {"1","2","3"};
			br = new BufferedReader(new FileReader("exp.txt"));
			while ((sCurrentLine = br.readLine()) != null) {
				if(i == 2) {
					tempExp = sCurrentLine.split("=");
				}
				i++;
			}
			expHunter = Integer.parseInt(tempExp[1]);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			try {
				if (br != null)
					br.close();
			}
			catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return expHunter;
	}
	
	public static int getExpMage() throws FileNotFoundException {
		BufferedReader br = null;
		try {
			int i = 0;
			String sCurrentLine;
			String tempExp[] = {"1","2","3"};
			br = new BufferedReader(new FileReader("exp.txt"));
			while ((sCurrentLine = br.readLine()) != null) {
				if(i == 3) {
					tempExp = sCurrentLine.split("=");
				}
				i++;
			}
			expMage = Integer.parseInt(tempExp[1]);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			try {
				if (br != null)
					br.close();
			}
			catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return expMage;
	}
	
	public static int getExpMonk() throws FileNotFoundException {
		BufferedReader br = null;
		try {
			int i = 0;
			String sCurrentLine;
			String tempExp[] = {"1","2","3"};
			br = new BufferedReader(new FileReader("exp.txt"));
			while ((sCurrentLine = br.readLine()) != null) {
				if(i == 4) {
					tempExp = sCurrentLine.split("=");
				}
				i++;
			}
			expMonk = Integer.parseInt(tempExp[1]);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			try {
				if (br != null)
					br.close();
			}
			catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return expMonk;
	}
	
	public static int getExpPaladin() throws FileNotFoundException {
		BufferedReader br = null;
		try {
			int i = 0;
			String sCurrentLine;
			String tempExp[] = {"1","2","3"};
			br = new BufferedReader(new FileReader("exp.txt"));
			while ((sCurrentLine = br.readLine()) != null) {
				if(i == 5) {
					tempExp = sCurrentLine.split("=");
				}
				i++;
			}
			expPaladin = Integer.parseInt(tempExp[1]);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			try {
				if (br != null)
					br.close();
			}
			catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return expPaladin;
	}
	
	public static int getExpPriest() throws FileNotFoundException {
		BufferedReader br = null;
		try {
			int i = 0;
			String sCurrentLine;
			String tempExp[] = {"1","2","3"};
			br = new BufferedReader(new FileReader("exp.txt"));
			while ((sCurrentLine = br.readLine()) != null) {
				if(i == 6) {
					tempExp = sCurrentLine.split("=");
				}
				i++;
			}
			expPriest = Integer.parseInt(tempExp[1]);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			try {
				if (br != null)
					br.close();
			}
			catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return expPriest;
	}
	
	public static int getExpRogue() throws FileNotFoundException {
		BufferedReader br = null;
		try {
			int i = 0;
			String sCurrentLine;
			String tempExp[] = {"1","2","3"};
			br = new BufferedReader(new FileReader("exp.txt"));
			while ((sCurrentLine = br.readLine()) != null) {
				if(i == 7) {
					tempExp = sCurrentLine.split("=");
				}
				i++;
			}
			expRogue = Integer.parseInt(tempExp[1]);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			try {
				if (br != null)
					br.close();
			}
			catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return expRogue;
	}
	
	public static int getExpShaman() throws FileNotFoundException {
		BufferedReader br = null;
		try {
			int i = 0;
			String sCurrentLine;
			String tempExp[] = {"1","2","3"};
			br = new BufferedReader(new FileReader("exp.txt"));
			while ((sCurrentLine = br.readLine()) != null) {
				if(i == 8) {
					tempExp = sCurrentLine.split("=");
				}
				i++;
			}
			expShaman = Integer.parseInt(tempExp[1]);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			try {
				if (br != null)
					br.close();
			}
			catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return expShaman;
	}
	
	public static int getExpWarlock() throws FileNotFoundException {
		BufferedReader br = null;
		try {
			int i = 0;
			String sCurrentLine;
			String tempExp[] = {"1","2","3"};
			br = new BufferedReader(new FileReader("exp.txt"));
			while ((sCurrentLine = br.readLine()) != null) {
				if(i == 9) {
					tempExp = sCurrentLine.split("=");
				}
				i++;
			}
			expWarlock = Integer.parseInt(tempExp[1]);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			try {
				if (br != null)
					br.close();
			}
			catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return expWarlock;
	}
	
	public static void setExp() throws FileNotFoundException {
		exp = getExp()+Mideas.joueur2.getExpGained();
		BufferedReader br = null;
		try {
			String content = "";
			String sCurrentLine;
			int i = 0;
			File file = new File("exp.txt");
				if (!file.exists()) {
					file.createNewFile();
				}
			br = new BufferedReader(new FileReader("exp.txt"));
			while(i <10) {
				sCurrentLine = br.readLine();
				if(joueur1.getClasse() == "DeathKnight" && i == 0) {
					content+= joueur1.getClasse()+"="+exp+"\r\n";
				}
				else if(i == 0) {
					content+= sCurrentLine+"\r\n";
				}
				if(joueur1.getClasse() == "Guerrier" && i == 1) {
					content+= joueur1.getClasse()+"="+exp+"\r\n";
					
				}
				else if(i == 1){
					content+= sCurrentLine+"\r\n";
				}
				if(joueur1.getClasse() == "Hunter"&& i == 2) {
					content+= joueur1.getClasse()+"="+exp+"\r\n";
				}
				else if(i == 2) {
					content+= sCurrentLine+"\r\n";
				}
				if(joueur1.getClasse() == "Mage" && i == 3) {
					content+= joueur1.getClasse()+"="+exp+"\r\n";
				}
				else if(i == 3) {
					content+= sCurrentLine+"\r\n";
				}
				if(joueur1.getClasse() == "Monk" && i == 4) {
					content+= joueur1.getClasse()+"="+exp+"\r\n";
				}
				else if(i == 4) {
					content+= sCurrentLine+"\r\n";
				}
				if(joueur1.getClasse() == "Paladin" && i == 5) {
					content+= joueur1.getClasse()+"="+exp+"\r\n";
				}
				else if(i == 5) {
					content+= sCurrentLine+"\r\n";
				}
				if(joueur1.getClasse() == "Priest"&& i == 6) {
					content+= joueur1.getClasse()+"="+exp+"\r\n";
				}
				else if(i == 6) {
					content+= sCurrentLine+"\r\n";
				}
				if(joueur1.getClasse() == "Rogue" && i == 7) {
					content+= joueur1.getClasse()+"="+exp+"\r\n";
				}
				else if(i == 7) {
					content+= sCurrentLine+"\r\n";
				}
				if(joueur1.getClasse() == "Shaman" && i == 8) {
					content+= joueur1.getClasse()+"="+exp+"\r\n";
				}
				else if(i == 8) {
					content+= sCurrentLine+"\r\n";
				}
				if(joueur1.getClasse() == "Warlock" && i == 9) {
					content+= joueur1.getClasse()+"="+exp+"\r\n";
				}
				else if(i == 9) {
					content+= sCurrentLine+"\r\n";
				}
				i++;
			}
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(content);
			bw.close();
			br.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/*public static void setExp() throws FileNotFoundException {
		exp = getExp()+Mideas.joueur2.getExpGained();
		try {
			String content = String.valueOf(exp);
			File file = new File(ClassSelectFrame.expTxt());
			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(content);
			bw.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}*/

	/*public static int getGold() throws FileNotFoundException {
		BufferedReader br = null;
		try {
			String sCurrentLine;
			br = new BufferedReader(new FileReader(ClassSelectFrame.goldTxt()));
			while ((sCurrentLine = br.readLine()) != null) {
				gold = Integer.parseInt(sCurrentLine);
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			try {
				if (br != null)
					br.close();
			}
			catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return gold;
	}*/
	
	public static int getGold() throws FileNotFoundException {
		BufferedReader br = null;
		try {
			int i = 0;
			String sCurrentLine;
			String tempGold[] = {"1","2","3"};
			br = new BufferedReader(new FileReader("gold.txt"));
			while ((sCurrentLine = br.readLine()) != null) {
				if(Mideas.getClassLine() == i) {
					tempGold = sCurrentLine.split("=");	
				}
				if(Mideas.getClassLine() == i) {
					tempGold = sCurrentLine.split("=");
				}
				if(Mideas.getClassLine() == i) {
					tempGold = sCurrentLine.split("=");
				}
				if(Mideas.getClassLine() == i) {
					tempGold = sCurrentLine.split("=");
				}
				if(Mideas.getClassLine() == i) {
					tempGold = sCurrentLine.split("=");
				}
				if(Mideas.getClassLine() == i) {
					tempGold = sCurrentLine.split("=");
				}
				if(Mideas.getClassLine() == i) {
					tempGold = sCurrentLine.split("=");
				}
				if(Mideas.getClassLine() == i) {
					tempGold = sCurrentLine.split("=");
				}
				if(Mideas.getClassLine() == i) {
					tempGold = sCurrentLine.split("=");
				}
				if(Mideas.getClassLine() == i) {
					tempGold = sCurrentLine.split("=");
				}
				i++;
			}
			gold = Integer.parseInt(tempGold[1]);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			try {
				if (br != null) {
					br.close();
				}
			}
			catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return gold;
	}
	
	/*public static void setGold(int gold) throws FileNotFoundException {
		gold+= joueur1.getGold();
		try {
			String content = "";
			int i = 0;
			File file = new File("gold.txt");
				if (!file.exists()) {
					file.createNewFile();
				}
				FileWriter fw = new FileWriter(file.getAbsoluteFile());
				BufferedWriter bw = new BufferedWriter(fw);
				if(joueur1.getClasse() == "DeathKnight") {
					content+= joueur1.getClasse()+"="+gold+"\r\n";
				}
				else {
					content+= "\r\n";
				}
				if(joueur1.getClasse() == "Guerrier") {
					content+= joueur1.getClasse()+"="+gold+"\r\n";
				}
				else {
					content+= "\r\n";
				}
				if(Mideas.getClassLine() == i) {
					content+= joueur1().getClasse()+"="+gold+"\r\n";
				}
				else {
					content+= "\r\n";
				}
				if(Mideas.getClassLine() == i) {
					content+= joueur1().getClasse()+"="+gold+"\r\n";
				}
				else {
					content+= "\r\n";
				}
				if(Mideas.getClassLine() == i) {
					content+= joueur1().getClasse()+"="+gold+"\r\n";
				}
				else {
					content+= "\r\n";
				}
				if(Mideas.getClassLine() == i) {
					content+= joueur1().getClasse()+"="+gold+"\r\n";
				}
				else {
					content+= "\r\n";
				}
				if(Mideas.getClassLine() == i) {
					content+= joueur1().getClasse()+"="+gold+"\r\n";
				}
				else {
					content+= "\r\n";
				}
				if(Mideas.getClassLine() == i) {
					content+= joueur1().getClasse()+"="+gold+"\r\n";
				}
				else {
					content+= "\r\n";
				}
				if(Mideas.getClassLine() == i) {
					content+= joueur1().getClasse()+"="+gold+"\r\n";
				}
				else {
					content+= "\r\n";
				}
				if(Mideas.getClassLine() == i) {
					content+= joueur1().getClasse()+"="+gold+"\r\n";
				}
				else {
					content+= "\r\n";
				}
				System.out.println(content);
				i++;
				bw.write(content);
				bw.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}*/
	
	public static void setConfig() throws FileNotFoundException {
		BufferedReader br = null;
		try {
			String content = "";
			//String sCurrentLine;
			int i = 0;
			File file = new File("config.txt");
				if (!file.exists()) {
					file.createNewFile();
				}
			br = new BufferedReader(new FileReader("config.txt"));
			while(i <10) {
				//sCurrentLine = br.readLine();
				if(i == 0) {
					content+= "bg="+ChangeBackGroundFrame.getCurrentBackground()+System.lineSeparator();
				}/*
				else if(i == 1){
					content+= sCurrentLine+System.lineSeparator();
				}
				else if(i == 2) {
					content+= sCurrentLine+System.lineSeparator();
				}
				else if(i == 3) {
					content+= sCurrentLine+System.lineSeparator();
				}
				else if(i == 4) {
					content+= sCurrentLine+System.lineSeparator();
				}
				else if(i == 5) {
					content+= sCurrentLine+System.lineSeparator();
				}
				else if(i == 6) {
					content+= sCurrentLine+System.lineSeparator();
				}
				else if(i == 7) {
					content+= sCurrentLine+System.lineSeparator();
				}
				else if(i == 8) {
					content+= sCurrentLine+System.lineSeparator();
				}*/
				i++;
			}
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(content);
			bw.close();
			br.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void getConfig() throws FileNotFoundException {
		BufferedReader br = null;
		try {
			int i = 0;
			String sCurrentLine;
			String tempConfig[] = {"1","2","3"};
			br = new BufferedReader(new FileReader("config.txt"));
			while ((sCurrentLine = br.readLine()) != null) {
				if(i == 0) {
					tempConfig = sCurrentLine.split("=");
					ChangeBackGroundFrame.loadBG(tempConfig[1]);
				}
				/*if(Mideas.getClassLine() == i) {
					tempConfig = sCurrentLine.split("=");
				}
				if(Mideas.getClassLine() == i) {
					tempConfig = sCurrentLine.split("=");
				}
				if(Mideas.getClassLine() == i) {
					tempConfig = sCurrentLine.split("=");
				}
				if(Mideas.getClassLine() == i) {
					tempConfig = sCurrentLine.split("=");
				}
				if(Mideas.getClassLine() == i) {
					tempConfig = sCurrentLine.split("=");
				}
				if(Mideas.getClassLine() == i) {
					tempConfig = sCurrentLine.split("=");
				}
				if(Mideas.getClassLine() == i) {
					tempConfig = sCurrentLine.split("=");
				}
				if(Mideas.getClassLine() == i) {
					tempConfig = sCurrentLine.split("=");
				}
				if(Mideas.getClassLine() == i) {
					tempConfig = sCurrentLine.split("=");
				}*/
				i++;
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			try {
				if (br != null) {
					br.close();
				}
			}
			catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	public static void setGold(int gold) throws FileNotFoundException {
		gold+= getGold();
		BufferedReader br = null;
		try {
			String content = "";
			String sCurrentLine;
			int i = 0;
			File file = new File("gold.txt");
				if (!file.exists()) {
					file.createNewFile();
				}
			br = new BufferedReader(new FileReader("gold.txt"));
			while(i <10) {
				sCurrentLine = br.readLine();
				if(joueur1.getClasse() == "DeathKnight" && i == 0) {
					content+= joueur1.getClasse()+"="+gold+System.lineSeparator();
				}
				else if(i == 0) {
					content+= sCurrentLine+System.lineSeparator();
				}
				if(joueur1.getClasse() == "Guerrier" && i == 1) {
					content+= joueur1.getClasse()+"="+gold+System.lineSeparator();
				}
				else if(i == 1){
					content+= sCurrentLine+System.lineSeparator();
				}
				if(joueur1.getClasse() == "Hunter"&& i == 2) {
					content+= joueur1().getClasse()+"="+gold+System.lineSeparator();
				}
				else if(i == 2) {
					content+= sCurrentLine+System.lineSeparator();
				}
				if(joueur1.getClasse() == "Mage" && i == 3) {
					content+= joueur1().getClasse()+"="+gold+System.lineSeparator();
				}
				else if(i == 3) {
					content+= sCurrentLine+System.lineSeparator();
				}
				if(joueur1.getClasse() == "Monk" && i == 4) {
					content+= joueur1().getClasse()+"="+gold+System.lineSeparator();
				}
				else if(i == 4) {
					content+= sCurrentLine+System.lineSeparator();
				}
				if(joueur1.getClasse() == "Paladin" && i == 5) {
					content+= joueur1().getClasse()+"="+gold+System.lineSeparator();
				}
				else if(i == 5) {
					content+= sCurrentLine+System.lineSeparator();
				}
				if(joueur1.getClasse() == "Priest" && i == 6) {
					content+= joueur1().getClasse()+"="+gold+System.lineSeparator();
				}
				else if(i == 6) {
					content+= sCurrentLine+System.lineSeparator();
				}
				if(joueur1.getClasse() == "Rogue" && i == 7) {
					content+= joueur1().getClasse()+"="+gold+System.lineSeparator();
				}
				else if(i == 7) {
					content+= sCurrentLine+System.lineSeparator();
				}
				if(joueur1.getClasse() == "Shaman" && i == 8) {
					content+= joueur1().getClasse()+"="+gold+System.lineSeparator();
				}
				else if(i == 8) {
					content+= sCurrentLine+System.lineSeparator();
				}
				if(joueur1.getClasse() == "Warlock" && i == 9) {
					content+= joueur1().getClasse()+"="+gold+System.lineSeparator();
				}
				else if(i == 9) {
					content+= sCurrentLine+System.lineSeparator();
				}
				i++;
			}
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(content);
			bw.close();
			br.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/*public static void setGold(int gold) throws FileNotFoundException {
		gold+= getGold();
		try {
			String content = String.valueOf(gold);
			File file = new File(ClassSelectFrame.goldTxt());
			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(content);
			bw.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}*/
	
	public static int getCurrentGold() {
		return gold;
	}
	
	public static int calcGoldCoin() throws FileNotFoundException {
		gold = Mideas.getGold();
		i = 0;
		while(gold-10000 >= 0) {
			gold-= 10000;
			i++;
		}
		return i;
	}
	
	public static int calcSilverCoin() throws FileNotFoundException {
		gold = Mideas.getGold()-i*10000;
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
	
	/*public static void cursorFrame(String cursor) throws LWJGLException, IOException {
		cursor_image = ImageIO.read(new File(cursor));
		final int cursor_width = cursor_image.getWidth();
		final int cursor_height = cursor_image.getHeight();
		final int[] cursor_pixels = new int[cursor_width*cursor_height];
		final ByteBuffer cursor_buffer = BufferUtils.createByteBuffer(cursor_width*cursor_height*4);
		cursor_image.getRGB(0, 0, cursor_width, cursor_height, cursor_pixels, 0, cursor_width);
		int cursor_y = 32;
		while(--cursor_y > -1) {
			int cursor_x = -1;
			while(++cursor_x < 32) {
				int i = cursor_y*32+cursor_x;
				cursor_buffer.put((byte)(cursor_pixels[i]>>16));
				cursor_buffer.put((byte)(cursor_pixels[i]>>8));
				cursor_buffer.put((byte) cursor_pixels[i]);
				cursor_buffer.put((byte)(cursor_pixels[i]>>24));
			}
		}
		cursor_buffer.position(0);
		Mouse.setNativeCursor(new Cursor(32, 32, 0, 31, 1, cursor_buffer.asIntBuffer(), null));
		setCursor("cursor");
	}*/
	
	public static String getCursor() {
		return cursor;
	}
	
	public static void setCursor(String string) {
		cursor = string;
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
	
	public static int getLevel() throws FileNotFoundException {
		if(getExp() <= 400) {
			level = 1;
			return level;
		}
		else if(getExp() <= 900) {
			level = 2;
			return level;
		}
		else if(getExp() <= 1400) {
			level = 3;
			return level;
		}
		else if(getExp() <= 2100) {
			level = 4;
			return level;
		}
		else if(getExp() <= 2800) {
			level = 5;
			return level;
		}
		else if(getExp() <= 3600) {
			level = 6;
			return level;
		}
		else if(getExp() <= 4500) {
			level = 7;
			return level;
		}
		else if(getExp() <= 5400) {
			level = 8;
			return level;
		}
		else if(getExp() <= 6500) {
			level = 9;
			return level;
		}
		else if(getExp() <= 7600) {
			level = 10;
			return level;
		}
		else if(getExp() <= 8700) {
			level = 11;
			return level;
		}
		else if(getExp() <= 9800) {
			level = 12;
			return level;
		}
		else if(getExp() <= 11000) {
			level = 13;
			return level;
		}
		else if(getExp() <= 12300) {
			level = 14;
			return level;
		}
		else if(getExp() <= 13600) {
			level = 15;
			return level;
		}
		else if(getExp() <= 15000) {
			level = 16;
			return level;
		}
		else if(getExp() <= 16400) {
			level = 17;
			return level;
		}
		else if(getExp() <= 17800) {
			level = 18;
			return level;
		}
		else if(getExp() <= 19300) {
			level = 19;
			return level;
		}
		else if(getExp() <= 20800) {
			level = 20;
			return level;
		}
		else if(getExp() <= 22400) {
			level = 21;
			return level;
		}
		else if(getExp() <= 24000) {
			level = 22;
			return level;
		}
		else if(getExp() <= 25500) {
			level = 23;
			return level;
		}
		else if(getExp() <= 27200) {
			level = 24;
			return level;
		}
		else if(getExp() <= 28900) {
			level = 25;
			return level;
		}
		else if(getExp() <= 30500) {
			level = 26;
			return level;
		}
		else if(getExp() <= 32200) {
			level = 27;
			return level;
		}
		else if(getExp() <= 33900) {
			level = 28;
			return level;
		}
		else if(getExp() <= 36300) {
			level = 29;
			return level;
		}
		else if(getExp() <= 38800) {
			level = 30;
			return level;
		}
		else if(getExp() <= 41600) {
			level = 31;
			return level;
		}
		else if(getExp() <= 44600) {
			level = 32;
			return level;
		}
		else if(getExp() <= 48000) {
			level = 33;
			return level;
		}
		else if(getExp() <= 51400) {
			level = 34;
			return level;
		}
		else if(getExp() <= 55000) {
			level = 35;
			return level;
		}
		else if(getExp() <= 58700) {
			level = 36;
			return level;
		}
		else if(getExp() <= 62400) {
			level = 37;
			return level;
		}
		else if(getExp() <= 66200) {
			level = 38;
			return level;
		}
		else if(getExp() <= 70200) {
			level = 39;
			return level;
		}
		else if(getExp() <= 74300) {
			level = 40;
			return level;
		}
		else if(getExp() <= 78500) {
			level = 41;
			return level;
		}
		else if(getExp() <= 82800) {
			level = 42;
			return level;
		}
		else if(getExp() <= 87100) {
			level = 43;
			return level;
		}
		else if(getExp() <= 91600) {
			level = 44;
			return level;
		}
		else if(getExp() <= 96300) {
			level = 45;
			return level;
		}
		else if(getExp() <= 101000) {
			level = 46;
			return level;
		}
		else if(getExp() <= 105800) {
			level = 47;
			return level;
		}
		else if(getExp() <= 110700) {
			level = 48;
			return level;
		}
		else if(getExp() <= 115700) {
			level = 49;
			return level;
		}
		else if(getExp() <= 120900) {
			level = 50;
			return level;
		}
		else if(getExp() <= 126100) {
			level = 51;
			return level;
		}
		else if(getExp() <= 131500) {
			level = 52;
			return level;
		}
		else if(getExp() <= 137000) {
			level = 53;
			return level;
		}
		else if(getExp() <= 142500) {
			level = 54;
			return level;
		}
		else if(getExp() <= 148200) {
			level = 55;
			return level;
		}
		else if(getExp() <= 154000) {
			level = 56;
			return level;
		}
		else if(getExp() <= 159900) {
			level = 57;
			return level;
		}
		else if(getExp() <= 165800) {
			level = 58;
			return level;
		}
		else if(getExp() <= 172000) {
			level = 59;
			return level;
		}
		else if(getExp() <= 290000) {
			level = 60;
			return level;
		}
		else if(getExp() <= 317000) {
			level = 61;
			return level;
		}
		else if(getExp() <= 349000) {
			level = 62;
			return level;
		}
		else if(getExp() <= 386000) {
			level = 63;
			return level;
		}
		else if(getExp() <= 428000) {
			level = 64;
			return level;
		}
		else if(getExp() <= 475000) {
			level = 65;
			return level;
		}
		else if(getExp() <= 527000) {
			level = 66;
			return level;
		}
		else if(getExp() <= 585000) {
			level = 67;
			return level;
		}
		else if(getExp() <= 648000) {
			level = 68;
			return level;
		}
		else if(getExp() <= 717000) {
			level = 69;
			return level;
		}
		else if(getExp() <= 1523800) {
			level = 70;
			return level;
		}
		else {
			level = 70;
			return level;
		}
	}
	
	public static int getExpNeeded(int level) throws FileNotFoundException {
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

	public static void setJoueur1(Joueur joueur) throws FileNotFoundException {
		joueur1 = joueur;
		if(joueur1.getClasse() == "Guerrier") {
			CharacterStuff.getEquippedItems();
		}
		else if(joueur1.getClasse() == "Paladin") {
			CharacterStuff.getEquippedItems();
		}
		else if(joueur1.getClasse() == "Hunter") {
			CharacterStuff.getEquippedItems();
		}
		else if(joueur1.getClasse() == "Rogue") {
			CharacterStuff.getEquippedItems();
		}
		else if(joueur1.getClasse() == "Priest") {
			CharacterStuff.getEquippedItems();
		}
		else if(joueur1.getClasse() == "DeathKnight") {
			CharacterStuff.getEquippedItems();
		}
		else if(joueur1.getClasse() == "Shaman") {
			CharacterStuff.getEquippedItems();
		}
		else if(joueur1.getClasse() == "Mage") {
			CharacterStuff.getEquippedItems();
		}
		else if(joueur1.getClasse() == "Warlock") {
			CharacterStuff.getEquippedItems();
		}
		else if(joueur1.getClasse() == "Monk") {
			CharacterStuff.getEquippedItems();
		}
	}
	
	public static int getClassLine() {
		if(joueur1 != null) {
			if(joueur1.getClasse() == "DeathKnight") {
				return 0;
			}
			if(joueur1.getClasse() == "Guerrier") {
				return 1;
			}
			if(joueur1.getClasse() == "Hunter") {
				return 2;
			}
			if(joueur1.getClasse() == "Mage") {
				return 3;
			}
			if(joueur1.getClasse() == "Monk") {
				return 4;
			}
			if(joueur1.getClasse() == "Paladin") {
				return 5;
			}
			if(joueur1.getClasse() == "Priest") {
				return 6;
			}
			if(joueur1.getClasse() == "Rogue") {
				return 7;
			}
			if(joueur1.getClasse() == "Shaman") {
				return 8;
			}
			if(joueur1.getClasse() == "Warlock") {
				return 9;
			}
			else {
				return 10;
			}
		}
		return 10;
	}
	
	/*private static void lessCd() {
		int i = 0;
		while(i < Mideas.joueur1.getSpells().length && Mideas.joueur1.getSpells(i) != null) {
			Mideas.joueur1.getSpells()[i].setSpellCd(Mideas.joueur1.getSpells()[i], Spell.getSpellCd(Mideas.joueur1.getSpells()[i])-1);
			i++;
		}
	}*/
	
	private static void lessCd() {
		int i = 0;
		int j = 0;
		first:
		while(i < Mideas.joueur1.getSpells().length) {
			j = 0;
			while(j < i && Mideas.joueur1.getSpells(i) != null && Mideas.joueur1.getSpells(j) != null && Mideas.joueur1.getSpells(i) == Mideas.joueur1.getSpells(j)) {
				j++;
				continue first;
			}
			
		}
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
	
	public static void setDisplayMode(int width, int height, boolean fullscreen) {
		 
	    // return if requested DisplayMode is already set
	    if ((Display.getDisplayMode().getWidth() == width) && (Display.getDisplayMode().getHeight() == height) && (Display.isFullscreen() == fullscreen)) {
	        return;
	    }
	    try {
	        DisplayMode targetDisplayMode = null;
		    if (fullscreen) {
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
	        if (targetDisplayMode == null) {
	            System.out.println("Failed to find value mode: "+width+"x"+height+" fs="+fullscreen);
	            return;
	        }
	        Display.setDisplayMode(targetDisplayMode);
	        Display.setFullscreen(fullscreen);        
	    } 
	    catch (LWJGLException e) {
	        System.out.println("Unable to setup mode "+width+"x"+height+" fullscreen="+fullscreen + e);
	    }
	}
}
