package com.mideas.rpg.v2;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;

import com.mideas.rpg.v2.dungeon.BlackTemple;
import com.mideas.rpg.v2.dungeon.Dungeon;
import com.mideas.rpg.v2.game.CharacterStuff;
import com.mideas.rpg.v2.game.ShopManager;
import com.mideas.rpg.v2.game.talent.Talent;
import com.mideas.rpg.v2.hud.AdminPanelFrame;
import com.mideas.rpg.v2.hud.ChangeBackGroundFrame;
import com.mideas.rpg.v2.hud.CharacterFrame;
import com.mideas.rpg.v2.hud.ChatFrame;
import com.mideas.rpg.v2.hud.ClassSelectFrame;
import com.mideas.rpg.v2.hud.ContainerFrame;
import com.mideas.rpg.v2.hud.CraftManager;
import com.mideas.rpg.v2.hud.DragBagManager;
import com.mideas.rpg.v2.hud.DragManager;
import com.mideas.rpg.v2.hud.DragSpellManager;
import com.mideas.rpg.v2.hud.EndFightFrame;
import com.mideas.rpg.v2.hud.EscapeFrame;
import com.mideas.rpg.v2.hud.GoldFrame;
import com.mideas.rpg.v2.hud.LogChat;
import com.mideas.rpg.v2.hud.PlayerPortraitFrame;
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

	public static void draw() throws LWJGLException, IOException, SQLException, CloneNotSupportedException {
		Draw.drawQuad(Sprites.current_bg, Display.getWidth()/2-Sprites.current_bg.getImageWidth()/2, Display.getHeight()/2-Sprites.current_bg.getImageHeight()/2);
		if(!isConfigLoaded) {
			Mideas.getConfig();
			isConfigLoaded = true;
		}
		if(!changeBackgroundFrameActive && !dungeonFrameActive) {
			if(!isChangeClassActive) {
				ClassSelectFrame.draw();
			}
			else {
				if(Mideas.joueur1() != null && !isGoldLoaded) {
					Mideas.getGold();
					isGoldLoaded = true;
				}
				if(Mideas.joueur1() != null && !isExpLoaded) {
					Mideas.getExp();
					isExpLoaded = true;
				}
				if(Mideas.joueur1() != null && !isStuffEquipped) {
					CharacterStuff.getEquippedBags();
					CharacterStuff.getBagItems();
					isStuffEquipped = true;
				}
				if(Mideas.joueur1() != null && !isStatsCalc) {
					CharacterStuff.calcStuffStats();
					isStatsCalc = true;
				}	
				if(Mideas.joueur1() != null && !isTalentLoaded) {
					Talent.getTalent();
					isTalentLoaded = true;
				}
				if(Mideas.joueur1() != null) {
					if(Mideas.joueur2() != null) {
						PlayerPortraitFrame.draw(Mideas.joueur2(), Window.getWidth()-243, 50);
						if(Mideas.joueur1().getStamina() <= 0 || Mideas.joueur2().getStamina() <= 0 && !Dungeon.dungeonActive()) {
							EndFightFrame.draw();
						}
					}
					SpellBarFrame.draw();
					SpellLevel.addSpell();
					if(characterFrameActive) {
						CharacterFrame.draw();
					}
					if(ContainerFrame.getBagOpen(0) || ContainerFrame.getBagOpen(1) || ContainerFrame.getBagOpen(2) || ContainerFrame.getBagOpen(3) || ContainerFrame.getBagOpen(4)) {
						containerFrameActive = true;
						ContainerFrame.draw();
						GoldFrame.draw();
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
						CraftManager.draw();
					}
					if(BlackTemple.getBlackTempleStatus()) {
						Dungeon.event();
					}
					LogChat.draw();
					if(chatFrameActive) {
						ChatFrame.draw();
					}
					DragManager.draw();
					DragBagManager.draw();
					PlayerPortraitFrame.draw(Mideas.joueur1(), 50, 50);
					ShortcutFrame.draw();
					Draw.drawQuad(Sprites.level, 50, 95);
					TTF2.hpAndMana.drawStringShadow(66-TTF2.hpAndMana.getWidth(String.valueOf(Mideas.getLevel()))/2, 105, String.valueOf(Mideas.getLevel()), Color.decode("#F0CE0C"), Color.black, 1, 1, 1);
					DragSpellManager.draw();
				}
			}
		}
		if(changeBackgroundFrameActive) {
			ChangeBackGroundFrame.draw();
		}
		if(dungeonFrameActive) {
			Dungeon.draw();
		}
	}
	
	public static boolean mouseEvent() throws FileNotFoundException, SQLException, CloneNotSupportedException {
		if(changeBackgroundFrameActive) {
			if(ChangeBackGroundFrame.mouseEvent()) {
				return true;
			}
		}
		if(!isChangeClassActive) {
			if(ClassSelectFrame.mouseEvent()) {
				return true;
			}
		}
		else {
			if(Mideas.joueur1().getStamina() > 0 && Mideas.joueur2().getStamina() > 0) {
				if(SpellBarFrame.mouseEvent()) {
					return true;
				}
				if(DragSpellManager.mouseEvent()) {
					return true;
				}
				if(ShortcutFrame.mouseEvent()) {
					return true;
				}
				DragBagManager.openBag();
				if(DragBagManager.mouseEvent()) {
					return true;
				}
 			}
			if(Mideas.joueur1().getStamina() <= 0 || Mideas.joueur2().getStamina() <= 0 && !Dungeon.dungeonActive()) {
				EndFightFrame.mouseEvent();
				return true;
			}
			if(characterFrameActive) {
				if(CharacterFrame.mouseEvent()) {
					return true;
				}
			}
			if(ContainerFrame.getBagOpen(0) || ContainerFrame.getBagOpen(1) || ContainerFrame.getBagOpen(2) || ContainerFrame.getBagOpen(3) || ContainerFrame.getBagOpen(4)) {
				ContainerFrame.mouseEvent();
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
				if(CraftManager.mouseEvent()) {
					return true;
				}
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
            if(DragManager.mouseEvent()) {
                return true;
            }
		}
		return false;
	}
	
	public static boolean keyboardEvent() throws LWJGLException, IOException, SQLException, CloneNotSupportedException {
		if(Keyboard.getEventKey() != 0) {
			if(Keyboard.getEventKeyState()) {
				//System.out.println(Keyboard.getEventKey());
				if(!ChatFrame.getChatActive()) {
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
					else if(Keyboard.isKeyDown(42) && Keyboard.getEventKey() == Keyboard.KEY_B && !escapeFrameActive) {
						if(containerFrameActive) {
							ContainerFrame.setBagOpen(0, false);
							ContainerFrame.setBagOpen(1, false);
							ContainerFrame.setBagOpen(2, false);
							ContainerFrame.setBagOpen(3, false);
							ContainerFrame.setBagOpen(4, false);
						}
						else {
							ContainerFrame.setBagOpen(0, true);
							ContainerFrame.setBagOpen(1, true);
							ContainerFrame.setBagOpen(2, true);
							ContainerFrame.setBagOpen(3, true);
							ContainerFrame.setBagOpen(4, true);
						}
						containerFrameActive = !containerFrameActive;
						Arrays.fill(ContainerFrame.getContainerFrameSlotHover(), false);
						return true;
					}
					else if(Keyboard.getEventKey() == Keyboard.KEY_B && !escapeFrameActive) {
						if(containerFrameActive) {
							ContainerFrame.setBagOpen(0, false);
							ContainerFrame.setBagOpen(1, false);
							ContainerFrame.setBagOpen(2, false);
							ContainerFrame.setBagOpen(3, false);
							ContainerFrame.setBagOpen(4, false);
						}
						else {
							ContainerFrame.setBagOpen(0, true);
						}
						containerFrameActive = !containerFrameActive;
						Arrays.fill(ContainerFrame.getContainerFrameSlotHover(), false);
						return true;
					}
					else if(Keyboard.getEventKey() == Keyboard.KEY_W) {
						ContainerFrame.setBagOpen(3, false);
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
						Arrays.fill(ContainerFrame.getContainerFrameSlotHover(), false);
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
						Arrays.fill(ContainerFrame.getContainerFrameSlotHover(), false);
						ClassSelectFrame.setHoverFalse();
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
						ClassSelectFrame.setHoverFalse();
						craftFrameActive = !craftFrameActive;
						return true;
					}
					else if(Keyboard.getEventKey() == Keyboard.KEY_M) {
						dungeonFrameActive = !dungeonFrameActive;
					}	
				}
				if(chatFrameActive) {
					ChatFrame.event();
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
}
