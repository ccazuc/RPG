package com.mideas.rpg.v2.hud;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.Sprites;
import com.mideas.rpg.v2.TTF2;
import com.mideas.rpg.v2.chat.ChatFrame;
import com.mideas.rpg.v2.command.CommandParty;
import com.mideas.rpg.v2.command.CommandTrade;
import com.mideas.rpg.v2.game.Unit;
import com.mideas.rpg.v2.utils.Button;
import com.mideas.rpg.v2.utils.Draw;
import com.mideas.rpg.v2.utils.TextMenu;
import com.mideas.rpg.v2.utils.Tooltip;

public class PartyFrame {

	static boolean requestPending;
	private static String requestName;
	private static long requestReceivedTimer;
	private final static int MAXIMUM_TIMER = 30000;
	private final static Color backgroundColor = new Color(0, 0, 0, .4f);
	private final static Color YELLOW = Color.decode("#F0CE0C");
	private static int hoveredMember = -1;
	private static float hoveredMemberX;
	private static float hoveredMemberY;
	static int displayMember = -1;
	private static float displayMemberX;
	private static float displayMemberY;
	private static TextMenu whisper = new TextMenu(0, 0, 80*Mideas.getDisplayXFactor(), 20*Mideas.getDisplayYFactor(), "Whisper", 14, 1) {
		@Override
		public void eventButtonClick() {
			ChatFrame.setWhisper(Mideas.joueur1().getParty().getPartyMember(displayMember).getName());
			ChatFrame.setChatActive(true);
			this.reset();
			displayMember = -1;
		}
	};
	private static TextMenu setLeader = new TextMenu(0, 0, 80*Mideas.getDisplayXFactor(), 20*Mideas.getDisplayYFactor(), "Give leadership", 14, 1) {
		@Override
		public void eventButtonClick() {
			CommandParty.setLeaderServer(Mideas.joueur1().getParty().getPartyMember(displayMember).getId());
			this.reset();
			displayMember = -1;
		}
	};
	private static TextMenu kick = new TextMenu(0, 0, 80*Mideas.getDisplayXFactor(), 20*Mideas.getDisplayYFactor(), "Kick player", 14, 1) {
		@Override
		public void eventButtonClick() {
			CommandParty.kickPlayer(Mideas.joueur1().getParty().getPartyMember(displayMember).getId());
			this.reset();
			displayMember = -1;
		}
	};
	private static TextMenu inspect = new TextMenu(0, 0, 80*Mideas.getDisplayXFactor(), 20*Mideas.getDisplayYFactor(), "Inspect", 14, 1) {
		@Override
		public boolean activateCondition() { return false; }
	};
	private static TextMenu trade = new TextMenu(0, 0, 80*Mideas.getDisplayXFactor(), 20*Mideas.getDisplayYFactor(), "Trade", 14, 1) {
		@Override
		public void eventButtonClick() {
			CommandTrade.requestNewTrade(Mideas.joueur1().getParty().getPartyMember(displayMember).getName());
			this.reset();
			displayMember = -1;
		}
	};
	private static TextMenu follow = new TextMenu(0, 0, 80*Mideas.getDisplayXFactor(), 20*Mideas.getDisplayYFactor(), "Follow", 14, 1) {
		@Override
		public boolean activateCondition() { return false; }
	};
	private static TextMenu duel = new TextMenu(0, 0, 80*Mideas.getDisplayXFactor(), 20*Mideas.getDisplayYFactor(), "Duel", 14, 1) {
		@Override
		public boolean activateCondition() { return false; }
	};
	private static TextMenu cancel = new TextMenu(0, 0, 80*Mideas.getDisplayXFactor(), 20*Mideas.getDisplayYFactor(), "Cancel", 14, 1) {
		@Override
		public void eventButtonClick() {
			this.reset();
			displayMember = -1;
		}
	};
	private static TextMenu[] menuTable = new TextMenu[] {whisper, setLeader, kick, inspect, trade, follow, duel, cancel};
	private static Tooltip memberTooltip = new Tooltip(0, 0, 171*Mideas.getDisplayXFactor(), 199*Mideas.getDisplayYFactor(), 0.8f);
	private static Button acceptRequest = new Button(Display.getWidth()/2-200*Mideas.getDisplayXFactor(), Display.getHeight()/2-190*Mideas.getDisplayYFactor(), 180*Mideas.getDisplayXFactor(), 25*Mideas.getDisplayXFactor(), "Accept", 15, 2) {
		@Override
		public void eventButtonClick() {
			CommandParty.acceptRequest();
			requestPending = false;
			this.reset();
		}
	};
	private static Button declineRequest = new Button(Display.getWidth()/2+20*Mideas.getDisplayXFactor(), Display.getHeight()/2-190*Mideas.getDisplayYFactor(), 180*Mideas.getDisplayXFactor(), 25*Mideas.getDisplayXFactor(), "Decline", 15, 2) {
		@Override
		public void eventButtonClick() {
			CommandParty.declineRequest();
			requestPending = false;
			this.reset();
		}
	};
	
	public static void draw() {
		if(requestPending) {
			if(System.currentTimeMillis()-requestReceivedTimer <= MAXIMUM_TIMER) {
				Draw.drawQuad(Sprites.alert, Display.getWidth()/2-250*Mideas.getDisplayXFactor(), Display.getHeight()/2-250*Mideas.getDisplayYFactor(), 500*Mideas.getDisplayXFactor(), 110*Mideas.getDisplayYFactor());
				TTF2.font4.drawStringShadow(Display.getWidth()/2-(TTF2.font4.getWidth(requestName+" wants to invite you in a party.")*Mideas.getDisplayXFactor())/2, Display.getHeight()/2-230*Mideas.getDisplayYFactor(), requestName+" wants to invite you in a party.", Color.white, Color.black, 1, Mideas.getDisplayXFactor(), Mideas.getDisplayXFactor());
				acceptRequest.draw();
				declineRequest.draw();
			}
			else {
				requestPending = false;
				CommandParty.declineRequest();
			}
		}
		else if(Mideas.joueur1().getParty() != null) {
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
				i = 0;
				y = Display.getHeight()/2+displayMemberY*Mideas.getDisplayYFactor()+65*Mideas.getDisplayYFactor();
				yShift = 18*Mideas.getDisplayYFactor();
				float x = (displayMemberX+65)*Mideas.getDisplayXFactor();
				memberTooltip.draw();
				TTF2.partyFrameTooltip.drawStringShadow(x, y, Mideas.joueur1().getParty().getPartyMember(displayMember).getName(), YELLOW, Color.black, 1, 0, 0);
				y+= yShift;
				while(i < menuTable.length) {
					if((i == 1 || i == 2) && !Mideas.joueur1().getParty().isPartyLeader(Mideas.joueur1())) {
						i++;
						continue;
					}
					menuTable[i].drawXY(x, y);
					y+= yShift;
					i++;
				}
			}
		}
	}
	
	public static boolean mouseEvent() {
		if(requestPending) {
			acceptRequest.event();
			declineRequest.event();
		}
		else if(Mideas.joueur1().getParty() != null) {
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
				int i = 0;
				float y = Display.getHeight()/2+displayMemberY*Mideas.getDisplayYFactor()+65*Mideas.getDisplayYFactor();
				float yShift = 18*Mideas.getDisplayYFactor();
				float x = (displayMemberX+65)*Mideas.getDisplayXFactor();
				y+= yShift;
				while(i < menuTable.length) {
					if((i == 1 || i == 2) && !Mideas.joueur1().getParty().isPartyLeader(Mideas.joueur1())) {
						i++;
						continue;
					}
					menuTable[i].eventXY(x, y);
					y+= yShift;
					i++;
				}
			}
			if(!Mouse.getEventButtonState()) {
				if(Mouse.getEventButton() == 1) {
					if(hoveredMember != -1) {
						displayMember = hoveredMember;
						displayMemberX = hoveredMemberX;
						displayMemberY = hoveredMemberY;
						memberTooltip.setX(displayMemberX*Mideas.getDisplayXFactor()+50*Mideas.getDisplayXFactor());
						memberTooltip.setY(Display.getHeight()/2+displayMemberY*Mideas.getDisplayYFactor()+50*Mideas.getDisplayYFactor());
						if(Mideas.joueur1().getParty().getPartyMember(displayMember) != null && Mideas.joueur1().getParty().isPartyLeader(Mideas.joueur1())) {
							memberTooltip.setWidth(140*Mideas.getDisplayXFactor());
							memberTooltip.setHeight(185*Mideas.getDisplayYFactor());
						}
						else if(Mideas.joueur1().getParty().getPartyMember(displayMember) != null) {
							memberTooltip.setWidth(95*Mideas.getDisplayXFactor());
							memberTooltip.setHeight(150*Mideas.getDisplayYFactor());
						}
					}
					else if(!isHoverTooltip()) {
						int i = 0;
						while(i < menuTable.length) {
							menuTable[i].reset();
							i++;
						}
						displayMember = -1;
					}
				}
				else if(Mouse.getEventButton() == 0) {
					if(!isHoverTooltip()) {
						int i = 0;
						while(i < menuTable.length) {
							menuTable[i].reset();
							i++;
						}
						displayMember = -1;
					}
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
		Draw.drawColorQuad(x+45*Mideas.getDisplayXFactor(), y+13*Mideas.getDisplayYFactor(), 96*Mideas.getDisplayXFactor(), 30*Mideas.getDisplayYFactor(), backgroundColor);
		Draw.drawQuad(Sprites.life_bar, x+54*Mideas.getDisplayXFactor(), y+28*Mideas.getDisplayYFactor(), 88*Mideas.getDisplayXFactor()*unit.getStamina()/unit.getMaxStamina(), 8*Mideas.getDisplayYFactor());
		Draw.drawQuad(Sprites.mana_bar, x+54*Mideas.getDisplayXFactor(), y+35*Mideas.getDisplayYFactor(), 88*Mideas.getDisplayXFactor()*unit.getMana()/unit.getMaxMana(), 8*Mideas.getDisplayYFactor());
		Draw.drawQuad(unit.getPortrait(), x+6*Mideas.getDisplayXFactor(), y+5*Mideas.getDisplayYFactor(), 45*Mideas.getDisplayXFactor(), 45*Mideas.getDisplayYFactor());
		Draw.drawQuad(Sprites.player_portrait_party, x, y);
		TTF2.portraitPartyName.drawStringShadow(x+100*Mideas.getDisplayXFactor()-TTF2.portraitPartyName.getWidth(unit.getName())/2, y+11*Mideas.getDisplayYFactor(), unit.getName(), YELLOW, Color.black, 1, 0, 0);
		TTF2.portraitPartyStats.drawStringShadow(x+100*Mideas.getDisplayXFactor()-TTF2.portraitPartyStats.getWidth(unit.getHealthText())/2, y+26*Mideas.getDisplayYFactor(), unit.getHealthText(), Color.white, Color.black, 1, 0, 0);
		TTF2.portraitPartyStats.drawStringShadow(x+100*Mideas.getDisplayXFactor()-TTF2.portraitPartyStats.getWidth(unit.getManaText())/2, y+33*Mideas.getDisplayYFactor(), unit.getManaText(), Color.white, Color.black, 1, 0, 0);
		if(Mideas.joueur1().getParty().isPartyLeader(unit)) {
			Draw.drawQuad(Sprites.party_leader_crown, x+5, y+8);
		}
	}
	
	private static boolean isHoverTooltip() {
		return displayMember != -1 && memberTooltip.isHover();
	}
	
	private static boolean isHoverMember(float x, float y) {
		return Mideas.mouseX() >= x && Mideas.mouseX() <= x+150*Mideas.getDisplayXFactor() && Mideas.mouseY() >= y && Mideas.mouseY() <= y+60*Mideas.getDisplayYFactor();
	}
	
	public static void setRequestPending(boolean we) {
		requestPending = we;
		requestReceivedTimer = System.currentTimeMillis();
	}
	
	public static void setRequestName(String name) {
		requestName = name;
	}
	
	private static boolean isHoverPartyFrame() {
		return Mideas.mouseX() >= 50*Mideas.getDisplayXFactor() && Mideas.mouseX() <= 150*Mideas.getDisplayXFactor() && Mideas.mouseY() >= Display.getHeight()/2-300*Mideas.getDisplayYFactor() && Mideas.mouseY() <= Display.getHeight()/2-300+Mideas.joueur1().getParty().getMemberList().length*60*Mideas.getDisplayYFactor();
	}
	
	public static void updateSize() {
		memberTooltip.updatePosition(displayMemberX*Mideas.getDisplayXFactor()+50*Mideas.getDisplayXFactor(), Display.getHeight()/2+displayMemberY*Mideas.getDisplayYFactor()+50*Mideas.getDisplayYFactor());
		if(Mideas.joueur1() != null && Mideas.joueur1().getParty() != null && Mideas.joueur1().getParty().isPartyLeader(Mideas.joueur1())) {
			memberTooltip.setWidth(140*Mideas.getDisplayXFactor());
			memberTooltip.setHeight(185*Mideas.getDisplayYFactor());
		}
		else if(Mideas.joueur1() != null && Mideas.joueur1().getParty() != null) {
			memberTooltip.setWidth(95*Mideas.getDisplayXFactor());
			memberTooltip.setHeight(150*Mideas.getDisplayYFactor());
		}
	}
	
	public static void setDisplayMember(int value) {
		displayMember = value;
	}
}
