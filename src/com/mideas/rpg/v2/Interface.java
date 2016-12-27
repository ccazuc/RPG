package com.mideas.rpg.v2;

import java.io.IOException;
import java.util.Arrays;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import com.mideas.rpg.v2.chat.ChatFrame;
import com.mideas.rpg.v2.dungeon.BlackTemple;
import com.mideas.rpg.v2.dungeon.Dungeon;
import com.mideas.rpg.v2.game.CharacterStuff;
import com.mideas.rpg.v2.game.ClassType;
import com.mideas.rpg.v2.game.Unit;
import com.mideas.rpg.v2.game.item.shop.ShopManager;
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
import com.mideas.rpg.v2.hud.PartyFrame;
import com.mideas.rpg.v2.hud.PerformanceBarFrame;
import com.mideas.rpg.v2.hud.PlayerPortraitFrame;
import com.mideas.rpg.v2.hud.PopupFrame;
import com.mideas.rpg.v2.hud.RedAlertFrame;
import com.mideas.rpg.v2.hud.SelectScreen;
import com.mideas.rpg.v2.hud.ShortcutFrame;
import com.mideas.rpg.v2.hud.SocketingFrame;
import com.mideas.rpg.v2.hud.SpellBarFrame;
import com.mideas.rpg.v2.hud.SpellBookFrame;
import com.mideas.rpg.v2.hud.SpellLevel;
import com.mideas.rpg.v2.hud.TradeFrame;
import com.mideas.rpg.v2.hud.social.OsefFrame;
import com.mideas.rpg.v2.hud.social.SocialFrame;
import com.mideas.rpg.v2.hud.social.guild.GuildFrame;
import com.mideas.rpg.v2.utils.Draw;
import com.mideas.rpg.v2.utils.Input;

public class Interface {
	
	private static boolean characterFrameActive;
	private static boolean containerFrameActive;
	private static boolean shopFrameActive;
	private static boolean isCharacterLoaded;
	private static boolean escapeFrameActive;
	private static boolean talentFrameActive;
	private static boolean spellBookFrameActive;
	private static boolean changeBackgroundFrameActive;
	private static boolean adminPanelFrameActive;
	private static boolean interfaceFrameActive;
	private static boolean dungeonFrameActive;
	private static boolean tradeFrameActive;
	private static boolean isConfigLoaded;
	private static boolean craftFrameActive;
	private static boolean chatFrameActive = true;
	private static boolean cast;
	private static long containerDrawTime;
	private static long containerMouseEventTime;
	private static long characterMouseEventTime;
	private static long spellBarMouseEventTime;
	private static long dragMouseEventTime;
	private static boolean hasLoggedInToAuth;
	private static boolean isSpellbarFullyLoaded = true;
	private static boolean socketingFrameActive;
	private static boolean socialFrameActive;
	private static long time;
	private static long socialDrawTime;
	
	private static long LAST_CONTAINER_TIMER;
	private final static int CONTAINER_TIMER_FREQUENCE = 1000;
	private static long LAST_SPELLBAR_TIMER;
	private final static int SPELLBAR_TIMER_FREQUENCE = 1000;
	private static long LAST_SOCIAL_TIMER;
	private final static int SOCIAL_TIMER_FREQUENCE = 1000;
	private static long LAST_CHARACTER_MOUSE_EVENT_TIMER;
	private final static int CHARACTER_MOUSE_EVENT_TIMER_FREQUENCE = 1000;
	private static long LAST_CONTAINER_MOUSE_EVENT_TIMER;
	private final static int CONTAINER_MOUSE_EVENT_TIMER_FREQUENCE = 1000;
	private static long LAST_DRAGMANAGER_MOUSE_EVENT_TIMER;
	private final static int DRAGMANAGER_MOUSE_EVENT_TIMER_FREQUENCE = 1000;

	public static void draw() throws IOException, NumberFormatException {
		Draw.drawQuadBG(Sprites.current_bg);
		if(!isConfigLoaded) {
			Mideas.getConfig();
			isConfigLoaded = true;
			Mideas.setDisplayXFactor(Display.getWidth()/1920f);
			Mideas.setDisplayYFactor(Display.getHeight()/1018f);
		}
		if(!changeBackgroundFrameActive && !dungeonFrameActive) {
			if(!hasLoggedInToAuth) {
				LoginScreen.draw();
				return;
			}
			else if(Mideas.joueur1() == null) {
				SelectScreen.draw();
			}
			else if(Mideas.joueur1() != null) {
				if(!isCharacterLoaded) {
					SpellBarManager.loadSpellBar();
					CharacterStuff.getEquippedBags();
					//CharacterStuff.getBagItems();
					ProfessionManager.LoadAllCraft();
					Mideas.joueur1().setFirstProfession(ProfessionManager.getProfession(100001));
					Talent.getTalent();
					Mideas.joueur1().setTarget(new Unit(100, 10000, 10000, 3000, 3000, 1, "", ClassType.NPC));
					isCharacterLoaded = true;
				}
				if(!isSpellbarFullyLoaded) {
					//Mideas.joueur1().loadSpellbar();
				}
				if(Mideas.joueur1().getTarget() != null) {
					PlayerPortraitFrame.draw(Mideas.joueur1().getTarget(), Window.getWidth()-243*Mideas.getDisplayXFactor(), 30*Mideas.getDisplayYFactor());
					PlayerPortraitFrame.draw(Mideas.joueur1(), 50*Mideas.getDisplayXFactor(), 30*Mideas.getDisplayYFactor());
					if((Mideas.joueur1().getStamina() <= 0 || Mideas.joueur1().getTarget().getStamina() <= 0) && !Dungeon.dungeonActive()) {
						EndFightFrame.draw();
					}
				}
				OsefFrame.draw();
				PartyFrame.draw();
				TradeFrame.event();
				RedAlertFrame.draw();
				SpellBarFrame.draw();
				CastBar.event();
				SpellLevel.addSpell();
				long time = System.nanoTime();
				if(ContainerFrame.getBagOpen(0) || ContainerFrame.getBagOpen(1) || ContainerFrame.getBagOpen(2) || ContainerFrame.getBagOpen(3) || ContainerFrame.getBagOpen(4)) {
					containerFrameActive = true;
					ContainerFrame.draw();
					GoldFrame.draw();
				}
				if(System.currentTimeMillis()-LAST_CONTAINER_TIMER >= CONTAINER_TIMER_FREQUENCE) {
					containerDrawTime = System.nanoTime()-time;
					LAST_CONTAINER_TIMER = System.currentTimeMillis();
				}
				time = System.nanoTime();
				if(socialFrameActive) {
					SocialFrame.draw();
				}
				if(System.currentTimeMillis()-LAST_SOCIAL_TIMER >= SOCIAL_TIMER_FREQUENCE) {
					socialDrawTime = System.nanoTime()-time;
					LAST_SOCIAL_TIMER = System.currentTimeMillis();
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
				if(socketingFrameActive) {
					SocketingFrame.draw();
				}
				if(tradeFrameActive) {
					TradeFrame.draw();
				}
				ShortcutFrame.draw();
				if(characterFrameActive) {
					CharacterFrame.draw();
				}
				if(ContainerFrame.getBagOpen(0) || ContainerFrame.getBagOpen(1) || ContainerFrame.getBagOpen(2) || ContainerFrame.getBagOpen(3) || ContainerFrame.getBagOpen(4)) {
					DrawContainerHover.draw();
				}
				PerformanceBarFrame.draw();
				LogChat.draw();
				PopupFrame.draw();
				DragManager.draw();
				DragBagManager.draw();
				DragSpellManager.draw();
				Mideas.joueur1().bag().event();
				//Draw.drawQuad(Sprites.interface_option_frame, Display.getWidth()/2-200, Display.getHeight()/2-200);
			}
		}
		if(changeBackgroundFrameActive) {
			ChangeBackGroundFrame.draw();
		}
		if(dungeonFrameActive) {
			Dungeon.draw();
		}
	}
	
	public static boolean mouseEvent() {
		Mideas.setHover(true);
		if(changeBackgroundFrameActive) {
			if(ChangeBackGroundFrame.mouseEvent()) {
				return true;
			}
		}
		else if(!hasLoggedInToAuth) {
			if(LoginScreen.mouseEvent()) {
				return true;
			}
		}
		else if(Mideas.joueur1() == null) {
			if(SelectScreen.mouseEvent()) {
				return true;
			}
		}
		else if(Mideas.joueur1() != null) {
			if(characterFrameActive) {
				time = System.nanoTime();
				if(CharacterFrame.mouseEvent()) {
					if(System.currentTimeMillis()-LAST_CHARACTER_MOUSE_EVENT_TIMER >= CHARACTER_MOUSE_EVENT_TIMER_FREQUENCE) {
						characterMouseEventTime = System.nanoTime()-time;
						LAST_CHARACTER_MOUSE_EVENT_TIMER = System.currentTimeMillis();
					}
					return true;
				}
				if(System.currentTimeMillis()-LAST_CHARACTER_MOUSE_EVENT_TIMER >= CHARACTER_MOUSE_EVENT_TIMER_FREQUENCE) {
					characterMouseEventTime = System.nanoTime()-time;
					LAST_CHARACTER_MOUSE_EVENT_TIMER = System.currentTimeMillis();
				}
			}
			if(DragSpellManager.mouseEvent()) {
				return true;
			}
			time = System.nanoTime();
			if(SpellBarFrame.mouseEvent()) {
				if(System.currentTimeMillis()-LAST_SPELLBAR_TIMER >= SPELLBAR_TIMER_FREQUENCE) {
					spellBarMouseEventTime = System.nanoTime()-time;
					LAST_SPELLBAR_TIMER = System.currentTimeMillis();
				}
				return true;
			}
			if(System.currentTimeMillis()-LAST_SPELLBAR_TIMER >= SPELLBAR_TIMER_FREQUENCE) {
				spellBarMouseEventTime = System.nanoTime()-time;
				LAST_SPELLBAR_TIMER = System.currentTimeMillis();
			}
			if(ShortcutFrame.mouseEvent()) {
				return true;
			}
			if(PopupFrame.mouseEvent()) {
				return true;
			}
			if(ContainerFrame.getBagOpen(0) || ContainerFrame.getBagOpen(1) || ContainerFrame.getBagOpen(2) || ContainerFrame.getBagOpen(3) || ContainerFrame.getBagOpen(4)) {
				time = System.nanoTime();
				if(ContainerFrame.mouseEvent()) {
					if(System.currentTimeMillis()-LAST_CONTAINER_MOUSE_EVENT_TIMER >= CONTAINER_MOUSE_EVENT_TIMER_FREQUENCE) {
						containerMouseEventTime = System.nanoTime()-time;
						LAST_CONTAINER_MOUSE_EVENT_TIMER = System.currentTimeMillis();
					}
					return true;
				}
				if(System.currentTimeMillis()-LAST_CONTAINER_MOUSE_EVENT_TIMER >= CONTAINER_MOUSE_EVENT_TIMER_FREQUENCE) {
					containerMouseEventTime = System.nanoTime()-time;
					LAST_CONTAINER_MOUSE_EVENT_TIMER = System.currentTimeMillis();
				}
			}
			if(socialFrameActive) {
				if(SocialFrame.mouseEvent()) {
					return true;
				}
			}
			DragBagManager.openBag();
			if(DragBagManager.mouseEvent()) {
				return true;
			}
			if(PartyFrame.mouseEvent()) {
				return true;
			}
			if(tradeFrameActive) {
				if(TradeFrame.mouseEvent()) {
					return true;
				}
			}
			if(socketingFrameActive) {
				if(SocketingFrame.mouseEvent()) {
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
	    			if(System.currentTimeMillis()-LAST_DRAGMANAGER_MOUSE_EVENT_TIMER >= DRAGMANAGER_MOUSE_EVENT_TIMER_FREQUENCE) {
	    				dragMouseEventTime = System.nanoTime()-time;
	    				LAST_DRAGMANAGER_MOUSE_EVENT_TIMER = System.currentTimeMillis();
	    			}
	    			return true;
			}
			if(System.currentTimeMillis()-LAST_DRAGMANAGER_MOUSE_EVENT_TIMER >= DRAGMANAGER_MOUSE_EVENT_TIMER_FREQUENCE) {
				dragMouseEventTime = System.nanoTime()-time;
				LAST_DRAGMANAGER_MOUSE_EVENT_TIMER = System.currentTimeMillis();
			}
			if(PerformanceBarFrame.mouseEvent()) {
				return true;
			}
			if(Mideas.joueur1().getStamina() <= 0 || (Mideas.joueur1().getTarget() != null && Mideas.joueur1().getTarget().getStamina() <= 0) && !Dungeon.dungeonActive()) {
				EndFightFrame.mouseEvent();
				return true;
			}
		}
		return false;
	}
	
	public static boolean keyboardEvent() {
		if(Keyboard.getEventKey() != 0) {
			if(Keyboard.getEventKeyState()) {
				//System.out.println(Keyboard.getEventKey());
				if(!Input.hasInputActive() && hasLoggedInToAuth && Mideas.joueur1() != null) {
					if(Keyboard.getEventKey() == Keyboard.KEY_X) {
						OsefFrame.run();
						return true;
					}
					if(Keyboard.getEventKey() == Keyboard.KEY_C && !escapeFrameActive) {
						closeShopFrame();
						closeSpellBookFrame();
						closeAdminPanelFrame();
						Arrays.fill(SpellBookFrame.getHoverBook(), false);
						CharacterFrame.setHoverFalse();
						characterFrameActive = !characterFrameActive;
						return true;
					}
					else if(Keyboard.getEventKey() == Keyboard.KEY_W) {
						PerformanceBarFrame.setPerformanceBarActive(!PerformanceBarFrame.getPerformanceBarActive());
					}
					else if(Keyboard.getEventKey() == Keyboard.KEY_O) {
						socialFrameActive = !socialFrameActive;
						SocialFrame.mouseEvent();
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
					else if(Keyboard.getEventKey() == Keyboard.KEY_T && !escapeFrameActive) {
						closeCharacterFrame();
						closeSpellBookFrame();
						closeAdminPanelFrame();
						CharacterFrame.setHoverFalse();
						Arrays.fill(SpellBookFrame.getHoverBook(), false);
						shopFrameActive = !shopFrameActive;
						return true;
					}
					else if(Keyboard.getEventKey() == Keyboard.KEY_N) {
						closeCharacterFrame();
						closeShopFrame();
						closeSpellBookFrame();
						closeAdminPanelFrame();
						CharacterFrame.setHoverFalse();
						Arrays.fill(SpellBookFrame.getHoverBook(), false);
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
						CharacterFrame.setHoverFalse();
						Arrays.fill(SpellBookFrame.getHoverBook(), false);
						escapeFrameActive = !escapeFrameActive;
						return true;
					}
					else if(Keyboard.getEventKey() == Keyboard.KEY_P) {
						closeTalentFrame();
						closeShopFrame();
						closeCharacterFrame();
						closeAdminPanelFrame();
						CharacterFrame.setHoverFalse();
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
						CharacterFrame.setHoverFalse();
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
						CharacterFrame.setHoverFalse();
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
				if(hasLoggedInToAuth && Mideas.joueur1() != null) {
					if(socialFrameActive && SocialFrame.isGuildFrameActive()) {
						if(GuildFrame.event()) {
							return true;
						}
					}
					if(PopupFrame.keyEvent()) {
						return true;
					}
					if(ChatFrame.event()) {
						return true;
					}
				}
				else if(!hasLoggedInToAuth) {
					LoginScreen.event();
				}
				else if(Mideas.joueur1() == null) {
					SelectScreen.event();
				}
			}
			if(Mideas.joueur1() != null && Mideas.joueur1().getStamina() > 0 && Mideas.joueur1().getTarget() != null && Mideas.joueur1().getTarget().getStamina() > 0) {
				if(SpellBarFrame.keyboardEvent()) {
					return true;
				}
			}
		}
		return false;
	}
	
	public static boolean getTradeStatus() {
		return tradeFrameActive;
	}
	
	public static boolean isSocialFrameActive() {
		return socialFrameActive;
	}
	
	public static void setSocialFrameStatus(boolean we) {
		socialFrameActive = we;
	}
	
	public static void setTradeFrameStatus(boolean we) {
		tradeFrameActive = we;
	}
	
	public static boolean isTradeFrameActive() {
		return tradeFrameActive;
	}
	
	public static void setCharacterLoaded(boolean we) {
		isCharacterLoaded = we;
	}
	
	public static boolean isSocketingFrameActive() {
		return socketingFrameActive;
	}
	
	public static void setSocketingFrameStatus(boolean we) {
		socketingFrameActive = we;
	}
	
	public static void setSpellbarFullyLoaded(boolean we) {
		isSpellbarFullyLoaded = we;
	}
	
	public static boolean getSpellbarFullyLoaded() {
		return isSpellbarFullyLoaded;
	}
	
	public static long getSocialDrawTime() {
		return socialDrawTime;
	}
	
	public static long getContainerDrawTime() {
		return containerDrawTime;
	}
	
	public static long getContainerMouseEventTime() {
		return containerMouseEventTime;
	}
	
	public static long getCharacterMouseEventTime() {
		return characterMouseEventTime;
	}
	
	public static long getSpellBarMouseEventTime() {
		return spellBarMouseEventTime;
	}
	
	public static long getDragMouseEventTime() {
		return dragMouseEventTime;
	}
	
	private static void closeBagEvent() {
		ContainerFrame.setHoverFalse();
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
	
	public static void setHasLoggedInToAuth(boolean we) {
		hasLoggedInToAuth = we;
	}
	
	public static boolean getHasLoggedIn() {
		return hasLoggedInToAuth;
	}
	
	public static void closeAllFrame() {
		characterFrameActive = false;
		containerFrameActive = false;
		shopFrameActive = false;
		escapeFrameActive = false;
		talentFrameActive = false;
		spellBookFrameActive = false;
		changeBackgroundFrameActive = false;
		adminPanelFrameActive = false;
		interfaceFrameActive = false;
		dungeonFrameActive = false;
		craftFrameActive = false;
		ContainerFrame.setBagOpen(0, false);
		ContainerFrame.setBagOpen(1, false);
		ContainerFrame.setBagOpen(2, false);
		ContainerFrame.setBagOpen(3, false);
		ContainerFrame.setBagOpen(4, false);
		closeBagEvent();
		ChatFrame.clearChat();
		if(Mideas.joueur1() != null) {
			if(Mideas.joueur1().getGuild() != null) {
				GuildFrame.resetFrame();
			}
		}
	}
}
