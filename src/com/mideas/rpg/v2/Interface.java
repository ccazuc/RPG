package com.mideas.rpg.v2;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Arrays;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;

import com.mideas.rpg.v2.chat.ChatFrame;
import com.mideas.rpg.v2.dungeon.BlackTemple;
import com.mideas.rpg.v2.dungeon.Dungeon;
import com.mideas.rpg.v2.game.CharacterStuff;
import com.mideas.rpg.v2.game.item.shop.ShopManager;
import com.mideas.rpg.v2.game.item.stuff.Stuff;
import com.mideas.rpg.v2.game.profession.ProfessionManager;
import com.mideas.rpg.v2.game.spell.SpellBarManager;
import com.mideas.rpg.v2.game.talent.Talent;
import com.mideas.rpg.v2.hud.AdminPanelFrame;
import com.mideas.rpg.v2.hud.CastBar;
import com.mideas.rpg.v2.hud.ChangeBackGroundFrame;
import com.mideas.rpg.v2.hud.CharacterFrame;
import com.mideas.rpg.v2.hud.ContainerFrame;
import com.mideas.rpg.v2.hud.DragBagManager;
import com.mideas.rpg.v2.hud.DragManager;
import com.mideas.rpg.v2.hud.DragSpellManager;
import com.mideas.rpg.v2.hud.DrawContainerHover;
import com.mideas.rpg.v2.hud.EndFightFrame;
import com.mideas.rpg.v2.hud.EscapeFrame;
import com.mideas.rpg.v2.hud.GoldFrame;
import com.mideas.rpg.v2.hud.LogChat;
import com.mideas.rpg.v2.hud.LoginScreen;
import com.mideas.rpg.v2.hud.PerformanceBarFrame;
import com.mideas.rpg.v2.hud.PlayerPortraitFrame;
import com.mideas.rpg.v2.hud.SelectScreen;
import com.mideas.rpg.v2.hud.ShopFrame;
import com.mideas.rpg.v2.hud.ShortcutFrame;
import com.mideas.rpg.v2.hud.SpellBarFrame;
import com.mideas.rpg.v2.hud.SpellBookFrame;
import com.mideas.rpg.v2.hud.SpellLevel;
import com.mideas.rpg.v2.utils.Draw;

public class Interface {
	
	private static boolean characterFrameActive;
	private static boolean containerFrameActive;
	private static boolean shopFrameActive;
	private static boolean isStuffEquipped;
	private static boolean isStatsCalc;
	private static boolean isGoldLoaded;
	private static boolean isExpLoaded;
	private static boolean escapeFrameActive;
	private static boolean talentFrameActive;
	private static boolean spellBookFrameActive;
	private static boolean changeBackgroundFrameActive;
	private static boolean adminPanelFrameActive;
	private static boolean interfaceFrameActive;
	private static boolean dungeonFrameActive;
	private static boolean isTalentLoaded;
	private static boolean isConfigLoaded;
	private static boolean isChangeClassActive;
	private static boolean craftFrameActive;
	private static boolean chatFrameActive = true;
	private static boolean cast;
	private static double containerDrawTime;
	private static double containerMouseEventTime;
	private static double characterMouseEventTime;
	private static double spellBarMouseEventTime;
	private static double dragMouseEventTime;
	private static boolean hasLoggedIn;
	private static boolean isStuffFullyLoaded;

	public static void draw() throws IOException, SQLException {
		Draw.drawQuadBG(Sprites.current_bg);
		if(!isConfigLoaded) {
			Mideas.getConfig();
			isConfigLoaded = true;
			Mideas.setDisplayXFactor(Display.getWidth()/1920f);
			Mideas.setDisplayYFactor(Display.getHeight()/1018f);
		}
		if(!changeBackgroundFrameActive && !dungeonFrameActive) {
			if(!hasLoggedIn) {
				LoginScreen.draw();
				return;
			}
			else if(Mideas.joueur1() == null) {
				SelectScreen.draw();
			}
			else if(Mideas.joueur1() != null) {
				if(!isGoldLoaded) {
					SpellBarManager.loadSpellBar();
					Mideas.loadGold();
					isGoldLoaded = true;
				}
				if(!isExpLoaded) {
					Mideas.loadExp();
					isExpLoaded = true;
				}
				if(!isStuffEquipped) {
					CharacterStuff.getEquippedBags();
					CharacterStuff.getBagItems();
					ProfessionManager.LoadAllCraft();
					Mideas.joueur1().setFirstProfession(ProfessionManager.getProfession(100001));
					isStuffEquipped = true;
				}
				if(!isStatsCalc) {
					CharacterStuff.calcStuffStats();
					isStatsCalc = true;
				}	
				if(!isTalentLoaded) {
					Talent.getTalent();
					isTalentLoaded = true;
				}
				if(!isStuffFullyLoaded) {
					Mideas.joueur1().loadStuff();
				}
				if(Mideas.joueur2() != null) {
					PlayerPortraitFrame.draw(Mideas.joueur2(), Window.getWidth()-243, 50);
					PlayerPortraitFrame.draw(Mideas.joueur1(), 50, 50);
					if((Mideas.joueur1().getStamina() <= 0 || Mideas.joueur2().getStamina() <= 0) && !Dungeon.dungeonActive()) {
						EndFightFrame.draw();
					}
				}
				SpellBarFrame.draw();
				//cast = CastBar.draw();
				CastBar.event();
				SpellLevel.addSpell();
				double time = System.nanoTime();
				if(ContainerFrame.getBagOpen(0) || ContainerFrame.getBagOpen(1) || ContainerFrame.getBagOpen(2) || ContainerFrame.getBagOpen(3) || ContainerFrame.getBagOpen(4)) {
					containerFrameActive = true;
					ContainerFrame.draw();
					GoldFrame.draw();
				}
				if(System.currentTimeMillis()%2000 < 10) {
					containerDrawTime = System.nanoTime()-time;
				}
				if(shopFrameActive) {
					ShopManager.draw();		
				}
				if(escapeFrameActive) {
					EscapeFrame.draw();
				}
				if(talentFrameActive) {
					Talent.draw();
				}
				if(adminPanelFrameActive) {
					AdminPanelFrame.draw();
				}
				if(spellBookFrameActive) {
					SpellBookFrame.draw();
				}
				if(craftFrameActive) {
					Mideas.joueur1().getFirstProfession().draw(Display.getWidth()/2-200, Display.getHeight()/2-300);
				}
				if(BlackTemple.getBlackTempleStatus()) {
					Dungeon.event();
				}
				if(chatFrameActive) {
					ChatFrame.draw();
				}
				Draw.drawQuad(Sprites.level, 50, 95);
				TTF2.hpAndMana.drawStringShadow(66-TTF2.hpAndMana.getWidth(String.valueOf(Mideas.getLevel()))/2, 105, String.valueOf(Mideas.getLevel()), Color.decode("#F0CE0C"), Color.black, 1, 1, 1);
				ShortcutFrame.draw();
				if(characterFrameActive) {
					CharacterFrame.draw();
				}
				if(ContainerFrame.getBagOpen(0) || ContainerFrame.getBagOpen(1) || ContainerFrame.getBagOpen(2) || ContainerFrame.getBagOpen(3) || ContainerFrame.getBagOpen(4)) {
					DrawContainerHover.draw();
				}
				PerformanceBarFrame.draw();
				LogChat.draw();
				DragManager.draw();
				DragBagManager.draw();
				DragSpellManager.draw();
				Mideas.bag().event();
			}
		}
		if(changeBackgroundFrameActive) {
			ChangeBackGroundFrame.draw();
		}
		if(dungeonFrameActive) {
			Dungeon.draw();
		}
	}
	
	public static boolean mouseEvent() throws FileNotFoundException, SQLException, NoSuchAlgorithmException {
		if(changeBackgroundFrameActive) {
			if(ChangeBackGroundFrame.mouseEvent()) {
				return true;
			}
		}
		else if(!hasLoggedIn) {
			if(LoginScreen.mouseEvent()) {
				return true;
			}
		}
		else if(Mideas.joueur1() == null) {
			/*if(ClassSelectFrame.mouseEvent()) {
				return true;
			}*/
			if(SelectScreen.mouseEvent()) {
				return true;
			}
		}
		else {
			if(DragSpellManager.mouseEvent()) {
				return true;
			}
			double time = System.nanoTime();
			if(SpellBarFrame.mouseEvent()) {
				return true;
			}
			if(System.currentTimeMillis()%500 < 10) {
				spellBarMouseEventTime = System.nanoTime()-time;
			}
			if(ShortcutFrame.mouseEvent()) {
				return true;
			}
			time = System.nanoTime();
			if(characterFrameActive) {
				if(CharacterFrame.mouseEvent()) {
					if(System.currentTimeMillis()%500 < 10) {
						characterMouseEventTime = System.nanoTime()-time;
					}
					return true;
				}
			}
			if(System.currentTimeMillis()%500 < 10) {
				characterMouseEventTime = System.nanoTime()-time;
			}
			time = System.nanoTime();
			if(ContainerFrame.getBagOpen(0) || ContainerFrame.getBagOpen(1) || ContainerFrame.getBagOpen(2) || ContainerFrame.getBagOpen(3) || ContainerFrame.getBagOpen(4)) {
				if(ContainerFrame.mouseEvent()) {
					if(System.currentTimeMillis()%500 < 10) {
						containerMouseEventTime = System.nanoTime()-time;
					}
					return true;
				}
			}
			if(System.currentTimeMillis()%500 < 10) {
				containerMouseEventTime = System.nanoTime()-time;
			}
			DragBagManager.openBag();
			if(DragBagManager.mouseEvent()) {
				return true;
			}
			if(characterFrameActive) {
				if(CharacterFrame.mouseEvent()) {
					return true;
				}
			}
			if(shopFrameActive) {
				if(ShopManager.mouseEvent()) {
					return true;
				}
			}
			if(chatFrameActive) {
				if(ChatFrame.mouseEvent()) {
					return true;
				}
			}
			if(craftFrameActive) {
				Mideas.joueur1().getFirstProfession().event(Display.getWidth()/2-200, Display.getHeight()/2-300);
					//return true;
				//}
			}
			if(talentFrameActive) {
				if(Talent.mouseEvent()) {
					return true;
				}
			}
			if(escapeFrameActive)
				if(EscapeFrame.mouseEvent()) {
					return true;
			}
			if(spellBookFrameActive) {
				if(SpellBookFrame.mouseEvent()) {
					return true;
				}
			}
			if(adminPanelFrameActive) {
				if(AdminPanelFrame.mouseEvent()) {
					return true;
				}
			}
			if(dungeonFrameActive) {
				if(Dungeon.mouseEvent()) {
					return true;
				}
			}
			time = System.nanoTime();
            if(DragManager.mouseEvent()) {
    			if(System.currentTimeMillis()%500 < 10) {
    				dragMouseEventTime = System.nanoTime()-time;
    			}
                return true;
            }
			if(System.currentTimeMillis()%500 < 10) {
				dragMouseEventTime = System.nanoTime()-time;
			}
            if(PerformanceBarFrame.mouseEvent()) {
            	return true;
            }
			if(Mideas.joueur1() != null && (Mideas.joueur1().getStamina() <= 0 || Mideas.joueur2().getStamina() <= 0 && !Dungeon.dungeonActive())) {
				EndFightFrame.mouseEvent();
				return true;
			}
		}
		return false;
	}
	
	public static boolean keyboardEvent() throws IOException, SQLException, NoSuchAlgorithmException {
		if(Keyboard.getEventKey() != 0) {
			if(Keyboard.getEventKeyState()) {
				if(!ChatFrame.getChatActive() && hasLoggedIn && Mideas.joueur1() != null) {
					if(Keyboard.getEventKey() == Keyboard.KEY_C && !escapeFrameActive) {
						closeShopFrame();
						closeSpellBookFrame();
						closeAdminPanelFrame();
						Arrays.fill(ShopFrame.getShopHover(), false);
						Arrays.fill(SpellBookFrame.getHoverBook(), false);
						Arrays.fill(CharacterFrame.getHoverCharacterFrame(), false);
						characterFrameActive = !characterFrameActive;
						return true;
					}
					else if(Keyboard.getEventKey() == Keyboard.KEY_W) {
						PerformanceBarFrame.setPerformanceBarActive(!PerformanceBarFrame.getPerformanceBarActive());
					}
					else if(Keyboard.isKeyDown(42) && Keyboard.getEventKey() == Keyboard.KEY_B && !escapeFrameActive) {
						if(containerFrameActive) {
							ContainerFrame.setBagOpen(0, false);
							ContainerFrame.setBagOpen(1, false);
							ContainerFrame.setBagOpen(2, false);
							ContainerFrame.setBagOpen(3, false);
							ContainerFrame.setBagOpen(4, false);
							closeBagEvent();
						}
						else {
							ContainerFrame.setBagOpen(0, true);
							ContainerFrame.setBagOpen(1, true);
							ContainerFrame.setBagOpen(2, true);
							ContainerFrame.setBagOpen(3, true);
							ContainerFrame.setBagOpen(4, true);
						}
						containerFrameActive = !containerFrameActive;
						return true;
					}
					else if(Keyboard.getEventKey() == Keyboard.KEY_B && !escapeFrameActive) {
						if(containerFrameActive) {
							ContainerFrame.setBagOpen(0, false);
							ContainerFrame.setBagOpen(1, false);
							ContainerFrame.setBagOpen(2, false);
							ContainerFrame.setBagOpen(3, false);
							ContainerFrame.setBagOpen(4, false);
							closeBagEvent();
						}
						else {
							ContainerFrame.setBagOpen(0, true);
						}
						containerFrameActive = !containerFrameActive;
						return true;
					}
					else if(Keyboard.getEventKey() == Keyboard.KEY_W) {
						CastBar.event(5, "Random");
					}
					else if(Keyboard.getEventKey() == Keyboard.KEY_X) {
						CastBar.event(2, "Random");
					}
					else if(Keyboard.getEventKey() == Keyboard.KEY_T && !escapeFrameActive) {
						closeCharacterFrame();
						closeSpellBookFrame();
						closeAdminPanelFrame();
						Arrays.fill(CharacterFrame.getHoverCharacterFrame(), false);
						Arrays.fill(SpellBookFrame.getHoverBook(), false);
						Arrays.fill(ShopFrame.getShopHover(), false);
						ShopFrame.setHoverShopFalse();
						shopFrameActive = !shopFrameActive;
						return true;
					}
					else if(Keyboard.getEventKey() == Keyboard.KEY_N) {
						closeCharacterFrame();
						closeShopFrame();
						closeSpellBookFrame();
						closeAdminPanelFrame();
						Arrays.fill(CharacterFrame.getHoverCharacterFrame(), false);
						Arrays.fill(ShopFrame.getShopHover(), false);
						Arrays.fill(SpellBookFrame.getHoverBook(), false);
						Arrays.fill(ShopFrame.getShopHover(), false);
						ShopFrame.setHoverShopFalse();
						talentFrameActive = !talentFrameActive;
						return true;
					}
					else if(Keyboard.getEventKey() == Keyboard.KEY_ESCAPE) {
						closeCharacterFrame();
						closeContainerFrame();
						closeShopFrame();
						closeTalentFrame();
						closeSpellBookFrame();
						closeAdminPanelFrame();
						closeBagEvent();
						Arrays.fill(CharacterFrame.getHoverCharacterFrame(), false);
						Arrays.fill(ShopFrame.getShopHover(), false);
						Arrays.fill(SpellBookFrame.getHoverBook(), false);
						ShopFrame.setHoverShopFalse();
						escapeFrameActive = !escapeFrameActive;
						return true;
					}
					else if(Keyboard.getEventKey() == Keyboard.KEY_P) {
						closeTalentFrame();
						closeShopFrame();
						closeCharacterFrame();
						closeAdminPanelFrame();
						Arrays.fill(CharacterFrame.getHoverCharacterFrame(), false);
						Arrays.fill(ShopFrame.getShopHover(), false);
						Arrays.fill(SpellBookFrame.getHoverBook(), false);
						spellBookFrameActive = !spellBookFrameActive;
						return true;
					}
					else if(Keyboard.getEventKey() == Keyboard.KEY_A) {
						closeTalentFrame();
						closeShopFrame();
						closeCharacterFrame();
						closeSpellBookFrame();
						closeContainerFrame();
						closeEscapeFrame();
						closeAdminPanelFrame();
						Arrays.fill(CharacterFrame.getHoverCharacterFrame(), false);
						Arrays.fill(ShopFrame.getShopHover(), false);
						Arrays.fill(SpellBookFrame.getHoverBook(), false);
						closeBagEvent();
						//ClassSelectFrame.setHoverFalse();
						changeBackgroundFrameActive = !changeBackgroundFrameActive;
						return true;
					}
					else if(Keyboard.getEventKey() == Keyboard.KEY_K) {
						closeTalentFrame();
						closeShopFrame();
						closeCharacterFrame();
						closeSpellBookFrame();
						Arrays.fill(CharacterFrame.getHoverCharacterFrame(), false);
						Arrays.fill(ShopFrame.getShopHover(), false);
						Arrays.fill(SpellBookFrame.getHoverBook(), false);
						//ClassSelectFrame.setHoverFalse();
						Mideas.joueur1().getFirstProfession().event(Display.getWidth()/2, Display.getHeight()/2);
						craftFrameActive = !craftFrameActive;
						return true;
					}
					else if(Keyboard.getEventKey() == Keyboard.KEY_M) {
						dungeonFrameActive = !dungeonFrameActive;
						return true;
					}
					else if(Keyboard.getEventKey() == Keyboard.KEY_RETURN || Keyboard.getEventKey() == 156) {
						ChatFrame.setChatActive(!ChatFrame.getChatActive());
						return true;
					}
				}
				if(hasLoggedIn && Mideas.joueur1() != null) {
					ChatFrame.event();
				}
				else if(!hasLoggedIn) {
					LoginScreen.event();
				}
				else if(Mideas.joueur1() == null) {
					SelectScreen.event();
				}
				if(Mideas.joueur1() != null && Mideas.joueur1().getStamina() > 0 && Mideas.joueur2().getStamina() > 0) {
					if(SpellBarFrame.keyboardEvent()) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public static void setStuffFullyLoaded(boolean we) {
		isStuffFullyLoaded = we;
	}
	
	public static double getContainerDrawTime() {
		return containerDrawTime;
	}
	
	public static double getContainerMouseEventTime() {
		return containerMouseEventTime;
	}
	
	public static double getCharacterMouseEventTime() {
		return characterMouseEventTime;
	}
	
	public static double getSpellBarMouseEventTime() {
		return spellBarMouseEventTime;
	}
	
	public static double getDragMouseEventTime() {
		return dragMouseEventTime;
	}
	
	private static void closeBagEvent() {
		Arrays.fill(ContainerFrame.getContainerFrameSlotHover(), false);
		ContainerFrame.setIsOneButtonDown(false);
	}
	public static void closeCharacterFrame() {
		characterFrameActive = false;
	}
	
	public static boolean getCharacterFrameStatus() {
		return characterFrameActive;
	}
	
	public static void setCharacterFrameStatus(boolean we) {
		characterFrameActive = we;
	}
	
	public static void closeContainerFrame() {
		containerFrameActive = false;
	}
	
	public static void closeTalentFrame() {
		talentFrameActive = false;
	}
	
	public static void closeShopFrame() {
		shopFrameActive = false;
	}
	
	public static void closeEscapeFrame() {
		escapeFrameActive = false;
	}
	
	public static void setEscapeFrameStatus(boolean  we) {
		escapeFrameActive = we;
	}
	public static void closeSpellBookFrame() {
		spellBookFrameActive = false;
	}
	
	public static boolean isSpellBookFrameActive() {
		return spellBookFrameActive;
	}
	
	public static void setSpellBookFrameStatus(boolean we) {
		spellBookFrameActive = we;
	}
	
	public static void closeAdminPanelFrame() {
		adminPanelFrameActive = false;
	}
	
	public static void openAdminPanelFrame() {
		adminPanelFrameActive = true;
	}

	public static void openInterfaceFrame() {
		interfaceFrameActive = true;
	}
	
	public static void closeChangeBackgroundFrame() {
		changeBackgroundFrameActive = false;
	}
	
	public static void closeInterfaceFrame() {
		interfaceFrameActive = false;
	}
	
	public static void closeDungeonActiveFrame() {
		dungeonFrameActive = false;
	}
	
	public static boolean getShopFrameStatus() {
		return shopFrameActive;
	}
	
 	public static boolean getContainerFrameStatus() {
 		return containerFrameActive;
 	}
 	
 	public static void setContainerFrameStatsu(boolean we) {
 		containerFrameActive = we;
 	}
 	
 	public static boolean getEscapeFrameStatus() {
 		return escapeFrameActive;
 	}
 	
 	public static boolean getAdminPanelFrameStatus() {
 		return adminPanelFrameActive;
 	}
 	
 	public static boolean getInterfaceFrameStatus() {
 		return interfaceFrameActive;
 	}

 	public static void setIsStatsCalc(boolean we) {
 		isStatsCalc = we;
 	}
	
	public static void setIsStuffEquipped(boolean we) {
		isStuffEquipped = we;
	}
	
	public static boolean getIsChangeClassActive() {
		return isChangeClassActive;
	}
	
	public static void setIsChangeClassActive(boolean we) {
		isChangeClassActive = we;
	}
	
	public static boolean getCraftFrameStatus() {
		return craftFrameActive;
	}
	
	public static void setCraftFrameActive(boolean we) {
		craftFrameActive = we;
	}
	
	public static boolean isTalentFrameActive() {
		return talentFrameActive;
	}
	
	public static void setTalentFrameStatus(boolean we) {
		talentFrameActive = we;
	}
	
	public static boolean getCast() {
		return cast;
	}
	
	public static void setHasLoggedIn(boolean we) {
		hasLoggedIn = we;
	}
	
	public static boolean getHasLoggedIn() {
		return hasLoggedIn;
	}
	
	public static void closeAllFrame() {
		characterFrameActive = false;
		containerFrameActive = false;
		shopFrameActive = false;
		isStuffEquipped = false;
		isStatsCalc = false;
		isGoldLoaded = false;
		isExpLoaded = false;
		escapeFrameActive = false;
		talentFrameActive = false;
		spellBookFrameActive = false;
		changeBackgroundFrameActive = false;
		adminPanelFrameActive = false;
		interfaceFrameActive = false;
		dungeonFrameActive = false;
		isTalentLoaded = false;
		isConfigLoaded = false;
		isChangeClassActive = false;
		craftFrameActive = false;
		ContainerFrame.setBagOpen(0, false);
		ContainerFrame.setBagOpen(1, false);
		ContainerFrame.setBagOpen(2, false);
		ContainerFrame.setBagOpen(3, false);
		ContainerFrame.setBagOpen(4, false);
		closeBagEvent();
	}
}
