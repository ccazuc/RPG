package com.mideas.rpg.v2.hud;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.Sprites;
import com.mideas.rpg.v2.FontManager;
import com.mideas.rpg.v2.chat.ChatFrame;
import com.mideas.rpg.v2.command.CommandParty;
import com.mideas.rpg.v2.command.CommandTrade;
import com.mideas.rpg.v2.game.unit.Unit;
import com.mideas.rpg.v2.utils.Color;
import com.mideas.rpg.v2.utils.Draw;
import com.mideas.rpg.v2.utils.TextMenu;
import com.mideas.rpg.v2.utils.TooltipMenu;

public class PartyFrame {

	private static boolean shouldUpdateSize;
	private final static Color backgroundColors = new Color(0, 0, 0, .4f);
	private static int hoveredMember = -1;
	private static float hoveredMemberX;
	private static float hoveredMemberY;
	static int displayMember = -1;
	private static float displayMemberX;
	private static float displayMemberY;
	private static TextMenu whisper = new TextMenu(0, 0, 87*Mideas.getDisplayXFactor(), "Whisper", 12, 1, -8*Mideas.getDisplayXFactor()) {
		@Override
		public void eventButtonClick() {
			ChatFrame.setWhisper(Mideas.joueur1().getParty().getPartyMember(displayMember).getName());
			ChatFrame.setChatActive(true);
			this.reset();
			menuTooltip.setActive(false);
		}
	};
	private static TextMenu setLeader = new TextMenu(0, 0, 90*Mideas.getDisplayXFactor(), "Give leadership", 12, 1, -8*Mideas.getDisplayXFactor()) {
		@Override
		public void eventButtonClick() {
			CommandParty.setLeaderServer(Mideas.joueur1().getParty().getPartyMember(displayMember).getId());
			this.reset();
			menuTooltip.setActive(false);
		}
	};
	private static TextMenu kick = new TextMenu(0, 0, 80*Mideas.getDisplayXFactor(), "Kick player", 12, 1, -8*Mideas.getDisplayXFactor()) {
		@Override
		public void eventButtonClick() {
			CommandParty.kickPlayer(Mideas.joueur1().getParty().getPartyMember(displayMember).getId());
			this.reset();
			menuTooltip.setActive(false);
		}
	};
	private static TextMenu inspect = new TextMenu(0, 0, 80*Mideas.getDisplayXFactor(), "Inspect", 12, 1, -8*Mideas.getDisplayXFactor()) {
		@Override
		public boolean activateCondition() { return false; }
	};
	private static TextMenu trade = new TextMenu(0, 0, 80*Mideas.getDisplayXFactor(), "Trade", 12, 1, -8*Mideas.getDisplayXFactor()) {
		@Override
		public void eventButtonClick() {
			CommandTrade.requestNewTrade(Mideas.joueur1().getParty().getPartyMember(displayMember).getName());
			this.reset();
			menuTooltip.setActive(false);
		}
	};
	private static TextMenu follow = new TextMenu(0, 0, 80*Mideas.getDisplayXFactor(), "Follow", 12, 1, -8*Mideas.getDisplayXFactor()) {
		@Override
		public boolean activateCondition() { return false; }
	};
	private static TextMenu duel = new TextMenu(0, 0, 80*Mideas.getDisplayXFactor(), "Duel", 12, 1, -8*Mideas.getDisplayXFactor()) {
		@Override
		public boolean activateCondition() { return false; }
	};
	private static TextMenu cancel = new TextMenu(0, 0, 80*Mideas.getDisplayXFactor(), "Cancel", 12, 1, -8*Mideas.getDisplayXFactor()) {
		@Override
		public void eventButtonClick() {
			this.reset();
			menuTooltip.setActive(false);
		}
	};
	static TooltipMenu menuTooltip = new TooltipMenu(0, 0, 0) {
		
		@Override
		public void onClose() {
			displayMember = -1;
		}
 	};
	
	public static void draw() {
		if(Mideas.joueur1().getParty() == null) {
			return;
		}
		updateSize();
		float y = Display.getHeight()/2-300*Mideas.getDisplayYFactor();
		float yShift = 60*Mideas.getDisplayYFactor();
		int i = 0;
		while(i < Mideas.joueur1().getParty().getMemberList().length) {
			if(Mideas.joueur1().getParty().getPartyMember(i) != null) {
				drawPortraitFrame(Mideas.joueur1().getParty().getPartyMember(i), 50*Mideas.getDisplayXFactor(), y+i*yShift);
			}
			i++;
		}
		if(displayMember != -1) {
			menuTooltip.draw();
		}
	}
	
	public static boolean mouseEvent() {
		if(Mideas.joueur1().getParty() == null) {
			return false;
		}
		hoveredMember = -1;
		if(isHoverPartyFrame()) {
			int i = 0;
			float x = 50;
			float y = -300;
			float yShift = 60;
			while(i < Mideas.joueur1().getParty().getMemberList().length) {
				if(Mideas.joueur1().getParty().getPartyMember(i) != null && isHoverMember(x*Mideas.getDisplayXFactor(), Display.getHeight()/2+y*Mideas.getDisplayYFactor()+i*yShift*Mideas.getDisplayYFactor())) {
					hoveredMember = i;
					hoveredMemberX = x;
					hoveredMemberY = y+i*yShift;
					break;
				}
				i++;
			}
		}
		if(displayMember != -1) {
			menuTooltip.event();
		}
		if(!Mouse.getEventButtonState()) {
			if(Mouse.getEventButton() == 1) {
				if(hoveredMember != -1) {
					displayMember = hoveredMember;
					displayMemberX = hoveredMemberX;
					displayMemberY = hoveredMemberY;
					if(Mideas.joueur1().getParty().getPartyMember(displayMember) != null && Mideas.joueur1().getParty().isPartyLeader(Mideas.joueur1())) {
						setTooltipLeader(Mideas.joueur1().getParty().getPartyMember(displayMember).getName(), displayMemberX*Mideas.getDisplayXFactor()+50*Mideas.getDisplayXFactor(), Display.getHeight()/2+displayMemberY*Mideas.getDisplayYFactor()+50*Mideas.getDisplayYFactor());
					}
					else if(Mideas.joueur1().getParty().getPartyMember(displayMember) != null) {
						setTooltipNonLeader(Mideas.joueur1().getParty().getPartyMember(displayMember).getName(), displayMemberX*Mideas.getDisplayXFactor()+50*Mideas.getDisplayXFactor(), Display.getHeight()/2+displayMemberY*Mideas.getDisplayYFactor()+50*Mideas.getDisplayYFactor());
					}
					menuTooltip.setName(Mideas.joueur1().getParty().getPartyMember(displayMember).getName());
				}
				else if(!isHoverTooltip()) {
					menuTooltip.setActive(false);
				}
			}
			else if(Mouse.getEventButton() == 0) {
				if(!isHoverTooltip()) {
					menuTooltip.setActive(false);
				}
			}
		}
		return false;
	}
	
	private static void drawPortraitFrame(Unit unit, float x, float y) {
		if(unit.getHasHpChanged()) {
			unit.setHealthText(unit.getStamina()+" / "+unit.getMaxStamina());
			unit.setHasHpChanged(false);
		}
		if(unit.getHasManaChanged()) {
			unit.setManaText(unit.getMana()+" / "+unit.getMaxMana());
			unit.setHasManaChanged(false);
		}
		Draw.drawColorQuad(x+45*Mideas.getDisplayXFactor(), y+13*Mideas.getDisplayYFactor(), 96*Mideas.getDisplayXFactor(), 30*Mideas.getDisplayYFactor(), backgroundColors);
		Draw.drawQuad(Sprites.life_bar, x+54*Mideas.getDisplayXFactor(), y+28*Mideas.getDisplayYFactor(), 88*Mideas.getDisplayXFactor()*unit.getStamina()/unit.getMaxStamina(), 8*Mideas.getDisplayYFactor());
		Draw.drawQuad(Sprites.mana_bar, x+54*Mideas.getDisplayXFactor(), y+35*Mideas.getDisplayYFactor(), 88*Mideas.getDisplayXFactor()*unit.getMana()/unit.getMaxMana(), 8*Mideas.getDisplayYFactor());
		Draw.drawQuad(unit.getPortrait(), x+6*Mideas.getDisplayXFactor(), y+5*Mideas.getDisplayYFactor(), 45*Mideas.getDisplayXFactor(), 45*Mideas.getDisplayYFactor());
		Draw.drawQuad(Sprites.player_portrait_party, x, y);
		FontManager.get("FRIZQT", 13).drawStringShadow(x+100*Mideas.getDisplayXFactor()-FontManager.get("FRIZQT", 13).getWidth(unit.getName())/2, y+11*Mideas.getDisplayYFactor(), unit.getName(), Color.YELLOW, Color.BLACK, 1, 0, 0);
		FontManager.get("FRIZQT", 9).drawBegin();
		FontManager.get("FRIZQT", 9).drawStringShadowPart(x+100*Mideas.getDisplayXFactor()-FontManager.get("FRIZQT", 9).getWidth(unit.getHealthText())/2, y+26*Mideas.getDisplayYFactor(), unit.getHealthText(), Color.WHITE, Color.BLACK, 1, 0, 0);
		FontManager.get("FRIZQT", 9).drawStringShadowPart(x+100*Mideas.getDisplayXFactor()-FontManager.get("FRIZQT", 9).getWidth(unit.getManaText())/2, y+33*Mideas.getDisplayYFactor(), unit.getManaText(), Color.WHITE, Color.BLACK, 1, 0, 0);
		FontManager.get("FRIZQT", 9).drawEnd();
		if(Mideas.joueur1().getParty().isPartyLeader(unit)) {
			Draw.drawQuad(Sprites.party_leader_crown, x+5, y+8);
		}
	}
	
	private static boolean isHoverTooltip() {
		return displayMember != -1 && menuTooltip.isHover();
	}
	
	private static boolean isHoverMember(float x, float y) {
		return Mideas.mouseX() >= x && Mideas.mouseX() <= x+150*Mideas.getDisplayXFactor() && Mideas.mouseY() >= y && Mideas.mouseY() <= y+60*Mideas.getDisplayYFactor();
	}
	
	private static boolean isHoverPartyFrame() {
		return Mideas.mouseX() >= 50*Mideas.getDisplayXFactor() && Mideas.mouseX() <= 150*Mideas.getDisplayXFactor() && Mideas.mouseY() >= Display.getHeight()/2-300*Mideas.getDisplayYFactor() && Mideas.mouseY() <= Display.getHeight()/2-300+Mideas.joueur1().getParty().getMemberList().length*60*Mideas.getDisplayYFactor();
	}
	
	public static void updateSize() {
		if(!shouldUpdateSize) {
			return;
		}
		if(displayMember != -1) {
			menuTooltip.updateSize();
			shouldUpdateSize = false;
		}
	}
	
	public static void shouldUpdate() {
		shouldUpdateSize = true;
	}
	
	public static void setDisplayMember(int value) {
		displayMember = value;
	}
	
	public static void setTooltipLeader(String name, float x, float y) {
		menuTooltip.clearMenu();
		menuTooltip.setName(name);
		menuTooltip.addMenu(whisper);
		menuTooltip.addMenu(setLeader);
		menuTooltip.addMenu(kick);
		menuTooltip.addMenu(inspect);
		menuTooltip.addMenu(trade);
		menuTooltip.addMenu(follow);
		menuTooltip.addMenu(duel);
		menuTooltip.addMenu(cancel);
		menuTooltip.setActive(true);
		menuTooltip.updateSize(x, y);
	}
	
	public static void setTooltipNonLeader(String name, float x, float y) {
		menuTooltip.clearMenu();
		menuTooltip.setName(name);
		menuTooltip.addMenu(whisper);
		menuTooltip.addMenu(inspect);
		menuTooltip.addMenu(trade);
		menuTooltip.addMenu(follow);
		menuTooltip.addMenu(duel);
		menuTooltip.addMenu(cancel);
		menuTooltip.setActive(true);
		menuTooltip.updateSize(x, y);
		
	}
}
