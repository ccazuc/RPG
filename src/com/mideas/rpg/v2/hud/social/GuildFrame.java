package com.mideas.rpg.v2.hud.social;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;

import com.mideas.rpg.v2.Interface;
import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.Sprites;
import com.mideas.rpg.v2.TTF2;
import com.mideas.rpg.v2.game.guild.GuildMember;
import com.mideas.rpg.v2.utils.AlertBackground;
import com.mideas.rpg.v2.utils.Button;
import com.mideas.rpg.v2.utils.CheckBox;
import com.mideas.rpg.v2.utils.CrossButton;
import com.mideas.rpg.v2.utils.Draw;
import com.mideas.rpg.v2.utils.Input;
import com.mideas.rpg.v2.utils.Tooltip;

import static com.mideas.rpg.v2.hud.social.SocialFrame.Y_SOCIAL_FRAME;
import static com.mideas.rpg.v2.hud.social.SocialFrame.X_SOCIAL_FRAME;

public class GuildFrame {

	static boolean showOfflineMembers;
	private static int hoveredMember = -1;
	private static GuildMember memberInformationDisplayed;
	static boolean memberInformationDisplay;
	private static int hoveredMemberX;
	private static int hoveredMemberY;
	private static int selectedMember = -1;
	private final static Color YELLOW = Color.decode("#FFC700");
	private final static Color GREY = Color.decode("#999999");
	private final static Color GREEN = new Color(64/255f, 251/255f, 64f/255f);
	static boolean displayGuildInformation;
	private static boolean inputInit;
	private final static CheckBox showOfflineMembersCheckBox = new CheckBox(X_SOCIAL_FRAME+348*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+43*Mideas.getDisplayYFactor(), 20*Mideas.getDisplayXFactor(), 18*Mideas.getDisplayYFactor()) {
		
		@Override
		public boolean get() {
			return showOfflineMembers;
		}
		
		@Override
		public void set() {
			showOfflineMembers = !showOfflineMembers;
		}
	};
	final static Input informationInput = new Input(TTF2.guildInformationText, 300, true, false) {
		
		@Override
		public boolean keyEvent(char c) {
			if(c == 27) { //escape
				this.setIsActive(false);
				return true;
			}
			return false;
		}
	};
	private final static int GUILD_INFORMATION_ALERT_BACKGROUND_X = 393;
	private final static int GUILD_INFORMATION_ALERT_BACKGROUND_X_SIZE = 340;
	private final static int GUILD_INFORMATION_ALERT_BACKGROUND_Y_SIZE = 315;
	private final static int MEMBER_INFORMATION_ALERT_BACKGROUND_X = 395;
	private final static int MEMBER_INFORMATION_ALERT_BACKGROUND_Y = 25;
	private final static int MEMBER_INFORMATION_ALERT_BACKGROUND_X_SIZE = 240;
	private final static int MEMBER_INFORMATION_ALERT_BACKGROUND_Y_SIZE = 275;
	final static AlertBackground informationBackground = new AlertBackground(X_SOCIAL_FRAME+GUILD_INFORMATION_ALERT_BACKGROUND_X*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME, GUILD_INFORMATION_ALERT_BACKGROUND_X_SIZE*Mideas.getDisplayXFactor(), GUILD_INFORMATION_ALERT_BACKGROUND_Y_SIZE*Mideas.getDisplayYFactor(), .6f);
	private final static Tooltip guildInformationTooltip = new Tooltip(X_SOCIAL_FRAME+403*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+36*Mideas.getDisplayYFactor(), 320*Mideas.getDisplayXFactor(), 242*Mideas.getDisplayYFactor(), .6f);
	private final static Tooltip noteTooltip = new Tooltip(X_SOCIAL_FRAME+415*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+150*Mideas.getDisplayYFactor(), 150*Mideas.getDisplayXFactor(), 50*Mideas.getDisplayYFactor(), .2f);
	private final static Tooltip officerNoteTooltip = new Tooltip(X_SOCIAL_FRAME+415*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+215*Mideas.getDisplayYFactor(), 150*Mideas.getDisplayXFactor(), 50*Mideas.getDisplayYFactor(), .6f);
	private final static Button infoButton = new Button(X_SOCIAL_FRAME+17*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+437*Mideas.getDisplayYFactor(), 134*Mideas.getDisplayXFactor(), 23*Mideas.getDisplayYFactor(), "Guild infos", 12, 1) {
		
		@Override
		public void eventButtonClick() {
			if(displayGuildInformation) {
				informationInput.setIsActive(false);
			}
			else {
				informationBackground.update(X_SOCIAL_FRAME+GUILD_INFORMATION_ALERT_BACKGROUND_X*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME, GUILD_INFORMATION_ALERT_BACKGROUND_X_SIZE*Mideas.getDisplayXFactor(), GUILD_INFORMATION_ALERT_BACKGROUND_Y_SIZE*Mideas.getDisplayYFactor());
				memberInformationDisplay = false;
			}
			displayGuildInformation = !displayGuildInformation;
		}
	};
	private final static Button addMemberButton = new Button(X_SOCIAL_FRAME+158*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+437*Mideas.getDisplayYFactor(), 110*Mideas.getDisplayXFactor(), 23*Mideas.getDisplayYFactor(), "Add member", 12, 1) {
		
		@Override
		public void eventButtonClick() {
			Interface.setAllInputInactive();
			Interface.setAddGuildMemberStatus(true);
			AddGuildMemberInputFrame.getInput().setIsActive(true);
		}
	};
	private final static Button manageGuildButton = new Button(X_SOCIAL_FRAME+274*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+437*Mideas.getDisplayYFactor(), 112*Mideas.getDisplayXFactor(), 23*Mideas.getDisplayYFactor(), "Manage guild", 12, 1) {
		
		@Override
		public void eventButtonClick() {
			
		}
	};
	private final static Button journalButton = new Button(X_SOCIAL_FRAME+405*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+276*Mideas.getDisplayYFactor(), 70*Mideas.getDisplayXFactor(), 24*Mideas.getDisplayYFactor(), "Journal", 10, 1) {
		
		@Override
		public void eventButtonClick() {
			//open journal
		}
	};
	private final static Button closeInformationButton = new Button(X_SOCIAL_FRAME+625*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+274*Mideas.getDisplayYFactor(), 90*Mideas.getDisplayXFactor(), 28*Mideas.getDisplayYFactor(), "Close", 12, 1) {
		
		@Override
		public void eventButtonClick() {
			this.reset();
			informationInput.setIsActive(false);
			displayGuildInformation = false;
			informationInput.setText(Mideas.joueur1().getGuild().getTempInformation());
		}
	};
	private final static Button acceptInformationButton = new Button(X_SOCIAL_FRAME+530*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+274*Mideas.getDisplayYFactor(), 90*Mideas.getDisplayXFactor(), 28*Mideas.getDisplayYFactor(), "Accept", 12, 1) {
		
		@Override
		public void eventButtonClick() {
			this.reset();
			informationInput.setIsActive(false);
			displayGuildInformation = false;
			//CommandGuild etc
		}
	};
	private final static CrossButton closeInformationFrameCrossButton = new CrossButton(X_SOCIAL_FRAME+698*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+10*Mideas.getDisplayYFactor()) {
		
		@Override
		public void eventButtonClick() {
			this.reset();
			informationInput.setIsActive(false);
			displayGuildInformation = false;
		}
	};
	
	public static void draw() {
		Draw.drawQuad(Sprites.guild_frame, X_SOCIAL_FRAME, Y_SOCIAL_FRAME);
		TTF2.guildTitle.drawStringShadow(X_SOCIAL_FRAME+220*Mideas.getDisplayXFactor()-TTF2.guildTitle.getWidth(Mideas.joueur1().getGuildTitle())/2, Y_SOCIAL_FRAME+14*Mideas.getDisplayYFactor(), Mideas.joueur1().getGuildTitle(), YELLOW, Color.black, 1, 0, 0);
		TTF2.guildMotd.drawStringShadow(X_SOCIAL_FRAME+23*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+368*Mideas.getDisplayYFactor(), "Message of the day :", YELLOW, Color.black, 1, 0, 0);
		drawMembers();
		drawMotd();
		drawNumberMember();
		drawInformationFrame();
		drawDisplayedMember();
		infoButton.draw();
		addMemberButton.draw();
		manageGuildButton.draw();
		showOfflineMembersCheckBox.draw();
	}
	
	public static boolean mouseEvent() {
		if(displayGuildInformation) {
			if(guildInformationTooltip.isHover()) {
				if(!Mouse.getEventButtonState()) {
					if(Mouse.getEventButton() == 0 || Mouse.getEventButton() == 1) {
						Interface.setAllInputInactive();
						informationInput.setIsActive(true);
					}
				}
			}
			if(journalButton.event()) return true;
			if(closeInformationButton.event()) return true;
			if(acceptInformationButton.event()) return true;
			if(closeInformationFrameCrossButton.event()) return true;
		}
		if(mouseEventMembers()) return true;
		if(showOfflineMembersCheckBox.event()) return true;
		if(infoButton.event()) return true;
		if(addMemberButton.event()) return true;
		if(manageGuildButton.event()) return true;
		return false;
	}
	
	public static boolean event() {
		return informationInput.event();
	}
	
	private static void drawMembers() {
		int i = 0;
		float x = X_SOCIAL_FRAME+35*Mideas.getDisplayXFactor();
		float y = Y_SOCIAL_FRAME+105*Mideas.getDisplayYFactor();
		int yShift = 0;
		float yShiftHeight = 18*Mideas.getDisplayYFactor();
		while(i < Mideas.joueur1().getGuild().getMemberList().size()) {
			if(Mideas.joueur1().getGuild().getMemberList().get(i).isOnline()) {
				TTF2.guildMember.drawStringShadow(x, y+yShift, Mideas.joueur1().getGuild().getMemberList().get(i).getName(), YELLOW, Color.black, 1, 0, 0);
				TTF2.guildMember.drawStringShadow(x+90*Mideas.getDisplayXFactor(), y+yShift, "Area", Color.white, Color.black, 1, 0, 0);
				TTF2.guildMember.drawStringShadow(x+223*Mideas.getDisplayXFactor(), y+yShift, Mideas.joueur1().getGuild().getMemberList().get(i).getLevelString(), Color.white, Color.black, 1, 0, 0);
				TTF2.guildMember.drawStringShadow(x+255*Mideas.getDisplayXFactor(), y+yShift, Mideas.joueur1().getGuild().getMemberList().get(i).getClassTypeString(), Mideas.joueur1().getGuild().getMemberList().get(i).getColor(), Color.black, 1, 0, 0);
				yShift+= yShiftHeight;
			}
			else if(showOfflineMembers) {
				TTF2.guildMember.drawStringShadow(x, y+yShift, Mideas.joueur1().getGuild().getMemberList().get(i).getName(), GREY, Color.black, 1, 0, 0);
				TTF2.guildMember.drawStringShadow(x+90*Mideas.getDisplayXFactor(), y+yShift, "Area", GREY, Color.black, 1, 0, 0);
				TTF2.guildMember.drawStringShadow(x+223*Mideas.getDisplayXFactor(), y+yShift, Mideas.joueur1().getGuild().getMemberList().get(i).getLevelString(), GREY, Color.black, 1, 0, 0);
				TTF2.guildMember.drawStringShadow(x+255*Mideas.getDisplayXFactor(), y+yShift, Mideas.joueur1().getGuild().getMemberList().get(i).getClassTypeString(), GREY, Color.black, 1, 0, 0);
				yShift+= yShiftHeight;
			}
			if(hoveredMember == i || selectedMember == i) {
				Draw.drawQuadBlend(Sprites.friend_border, x-5*Mideas.getDisplayXFactor(), y+yShift-yShiftHeight, 343*Mideas.getDisplayXFactor(), 18*Mideas.getDisplayYFactor());
			}
			if(y+yShift+yShiftHeight >= Y_SOCIAL_FRAME+350*Mideas.getDisplayYFactor()) {
				break;
			}
			i++;
		}
	}
	
	private static boolean mouseEventMembers() {
		hoveredMember = -1;
		int i = 0;
		float x = X_SOCIAL_FRAME+30*Mideas.getDisplayXFactor();
		float y = Y_SOCIAL_FRAME+105*Mideas.getDisplayYFactor();
		int yShift = 0;
		float yShiftHeight = 18*Mideas.getDisplayYFactor();
		while(i < Mideas.joueur1().getGuild().getMemberList().size()) {
			if(Mideas.joueur1().getGuild().getMemberList().get(i).isOnline() || (!Mideas.joueur1().getGuild().getMemberList().get(i).isOnline() && showOfflineMembers)) {
				if(isHoverMember(x, y+yShift)) {
					hoveredMember = i;
					hoveredMemberX = (int)x;
					hoveredMemberY = (int)y+yShift;
					break;
				}
				yShift+= yShiftHeight;
			}
			if(y+yShift+yShiftHeight >= Y_SOCIAL_FRAME+350*Mideas.getDisplayYFactor()) {
				break;
			}
			i++;
		}
		if(hoveredMember != -1) {
			if(!Mouse.getEventButtonState()) {
				if(Mouse.getEventButton() == 0) {
					memberInformationDisplayed = Mideas.joueur1().getGuild().getMemberList().get(hoveredMember);
					informationBackground.update(X_SOCIAL_FRAME+MEMBER_INFORMATION_ALERT_BACKGROUND_X*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+MEMBER_INFORMATION_ALERT_BACKGROUND_Y, MEMBER_INFORMATION_ALERT_BACKGROUND_X_SIZE*Mideas.getDisplayXFactor(), MEMBER_INFORMATION_ALERT_BACKGROUND_Y_SIZE*Mideas.getDisplayYFactor());
					memberInformationDisplay = true;
					displayGuildInformation = false;
				}
				else if(Mouse.getEventButton() == 1) {
					//open tooltip frame
				}
			}
		}
		return false;
	}
	
	private static boolean isHoverMember(float x, float y) {
		return Mideas.mouseX() >= x && Mideas.mouseX() <= x+343*Mideas.getDisplayXFactor() && Mideas.mouseY() >= y && Mideas.mouseY() <= y+18*Mideas.getDisplayYFactor();
	}
	
	private static void drawDisplayedMember() {
		if(memberInformationDisplay) {
			informationBackground.draw();
			noteTooltip.draw();
			float x = X_SOCIAL_FRAME+415*Mideas.getDisplayXFactor();
			float y = Y_SOCIAL_FRAME+45*Mideas.getDisplayYFactor();
			TTF2.guildMemberInformationName.drawStringShadow(x, y, memberInformationDisplayed.getName(), YELLOW, Color.black, 1, 0, 0);
			y+= (TTF2.guildMemberInformationLevel.getLineHeight())*Mideas.getDisplayYFactor();
			TTF2.guildMemberInformationLevel.drawStringShadow(x, y, memberInformationDisplayed.getInformationString(), Color.white, Color.black, 1, 0, 0);
			y+= (TTF2.guildMemberInformationLevel.getLineHeight()+4)*Mideas.getDisplayYFactor();
			TTF2.guildMemberInformationLevel.drawStringShadow(x, y, "Area : ", YELLOW, Color.black, 1, 0, 0);
			TTF2.guildMemberInformationLevel.drawStringShadow(x+TTF2.guildMemberInformationLevel.getWidth("Area : "), y, "Area", Color.white, Color.black, 1, 0, 0);
			y+= (TTF2.guildMemberInformationLevel.getLineHeight()+2)*Mideas.getDisplayYFactor();
			TTF2.guildMemberInformationLevel.drawStringShadow(x, y, "Rank : ", YELLOW, Color.black, 1, 0, 0);
			TTF2.guildMemberInformationLevel.drawStringShadow(x+TTF2.guildMemberInformationLevel.getWidth("Rank : "), y, memberInformationDisplayed.getRank().getName(), Color.white, Color.black, 1, 0, 0);
			y+= (TTF2.guildMemberInformationLevel.getLineHeight()+1)*Mideas.getDisplayYFactor();
			TTF2.guildMemberInformationLevel.drawStringShadow(x, y, "Last connection : ", YELLOW, Color.black, 1, 0, 0);
			TTF2.guildMemberInformationLevel.drawStringShadow(x+TTF2.guildMemberInformationLevel.getWidth("Last connection : "), y, "Unknown", Color.white, Color.black, 1, 0, 0);
			y+= (TTF2.guildMemberInformationLevel.getLineHeight()+1)*Mideas.getDisplayYFactor();
			TTF2.guildMemberInformationLevel.drawStringShadow(x, y, "Note : ", YELLOW, Color.black, 1, 0, 0);
		}
	}
	
	private static void drawInformationFrame() {
		if(displayGuildInformation) {
			if(!inputInit) {
				informationInput.setText(Mideas.joueur1().getGuild().getTempInformation());
				inputInit = true;
			}
			informationBackground.draw();
			guildInformationTooltip.draw();
			TTF2.guildInformationTitle.drawStringShadow(X_SOCIAL_FRAME+411*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+15*Mideas.getDisplayYFactor(), "General options", YELLOW, Color.black, 1, 0, 0);
			drawInformationText();
			Draw.drawQuad(Sprites.guild_close_information_button_border, X_SOCIAL_FRAME+691*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+7*Mideas.getDisplayYFactor());
			journalButton.draw();
			closeInformationButton.draw();
			acceptInformationButton.draw();
			closeInformationFrameCrossButton.draw();
		}
	}
	
	private static void drawInformationText() {
		int i = 0;
		float x = X_SOCIAL_FRAME+410*Mideas.getDisplayXFactor();
		int xShift = 0;
		float y = Y_SOCIAL_FRAME+45*Mideas.getDisplayYFactor();
		int yShift = 0;
		float maxLength = 285*Mideas.getDisplayXFactor();
		while(i < informationInput.getText().length()) {
			if(informationInput.getText().charAt(i) == 13) {
				yShift+= TTF2.guildInformationTitle.getLineHeight();
				xShift = 0;
			}
			else {
				TTF2.guildInformationText.drawChar(x+xShift+1, y+yShift, informationInput.getText().charAt(i), Color.black);
				TTF2.guildInformationText.drawChar(x+xShift, y+yShift, informationInput.getText().charAt(i), Color.white);
				xShift+= TTF2.guildInformationText.getWidth(informationInput.getText().charAt(i));
			}
			if(xShift >= maxLength) {
				xShift = 0;
				yShift+= TTF2.guildInformationText.getLineHeight();
			}
			if(informationInput.isActive() && informationInput.getCursorPosition() == i+1 && System.currentTimeMillis()%1000 < 500) {
				Draw.drawColorQuad(x+xShift, y+yShift, 5*Mideas.getDisplayXFactor(), 16*Mideas.getDisplayYFactor(), Color.white);
			}
			i++;
		}
	}
	
	private static void drawNumberMember() {
		float x = X_SOCIAL_FRAME+20*Mideas.getDisplayXFactor();
		float y = Y_SOCIAL_FRAME+342*Mideas.getDisplayYFactor();
		int xShift = 0;
		TTF2.guildMotd.drawStringShadow(x, y, Mideas.joueur1().getGuild().getNumberMember(), Color.white, Color.black, 1, 0, 0);
		xShift+= TTF2.guildMotd.getWidth(Mideas.joueur1().getGuild().getNumberMember());
		TTF2.guildMotd.drawStringShadow(x+xShift, y, " Members of the guild (", YELLOW, Color.black, 1, 0, 0);
		xShift+= TTF2.guildMotd.getWidth(" Members of the guild (");
		TTF2.guildMotd.drawStringShadow(x+xShift, y, Mideas.joueur1().getGuild().getNumberOnlineMember(), Color.white, Color.black, 1, 0, 0);
		xShift+= TTF2.guildMotd.getWidth(Mideas.joueur1().getGuild().getNumberOnlineMember());
		TTF2.guildMotd.drawStringShadow(x+xShift, y, " Online ", GREEN, Color.black, 1, 0, 0);
		xShift+= TTF2.guildMotd.getWidth("Online ");
		TTF2.guildMotd.drawStringShadow(x+xShift, y, ")", YELLOW, Color.black, 1, 0, 0);
	}
	
	private static void drawMotd() {
		int i = 0;
		float x = X_SOCIAL_FRAME+23*Mideas.getDisplayXFactor();
		float y = Y_SOCIAL_FRAME+383*Mideas.getDisplayYFactor();
		int xShift = 0;
		float yShift = 0;
		float maxWidth = 325*Mideas.getDisplayXFactor();
		int length = Mideas.joueur1().getGuild().getTempMotd().length();
		while(i < length) {
			TTF2.guildMotd.drawChar(x+xShift+1, y+yShift, Mideas.joueur1().getGuild().getTempMotd().charAt(i), Color.black);
			TTF2.guildMotd.drawChar(x+xShift, y+yShift, Mideas.joueur1().getGuild().getTempMotd().charAt(i), Color.white);
			xShift+= TTF2.guildMotd.getWidth(Mideas.joueur1().getGuild().getTempMotd().charAt(i));
			if(xShift >= maxWidth) {
				yShift+= TTF2.guildMotd.getLineHeight();
				xShift = 0;
			}
			i++;
		}
	}
	
	public static boolean isInformationActive() {
		return displayGuildInformation;
	}
	
	public static void updateSize() {
		infoButton.update(X_SOCIAL_FRAME+17*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+437*Mideas.getDisplayYFactor(), 134*Mideas.getDisplayXFactor(), 23*Mideas.getDisplayYFactor());
		addMemberButton.update(X_SOCIAL_FRAME+158*Mideas.getDisplayXFactor(),Y_SOCIAL_FRAME+437*Mideas.getDisplayYFactor(), 110*Mideas.getDisplayXFactor(), 23*Mideas.getDisplayYFactor());
		manageGuildButton.update(X_SOCIAL_FRAME+274*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+437*Mideas.getDisplayYFactor(), 112*Mideas.getDisplayXFactor(), 23*Mideas.getDisplayYFactor());
		journalButton.update(X_SOCIAL_FRAME+405*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+278*Mideas.getDisplayYFactor(), 65*Mideas.getDisplayXFactor(), 22*Mideas.getDisplayYFactor());
		closeInformationButton.update(X_SOCIAL_FRAME+625*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+274*Mideas.getDisplayYFactor(), 90*Mideas.getDisplayXFactor(), 28*Mideas.getDisplayYFactor());
		acceptInformationButton.update(X_SOCIAL_FRAME+530*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+274*Mideas.getDisplayYFactor(), 90*Mideas.getDisplayXFactor(), 28*Mideas.getDisplayYFactor());
		closeInformationFrameCrossButton.update(X_SOCIAL_FRAME+698*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+10*Mideas.getDisplayYFactor());
		showOfflineMembersCheckBox.update(X_SOCIAL_FRAME+348*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+43*Mideas.getDisplayYFactor(), 20*Mideas.getDisplayXFactor(), 18*Mideas.getDisplayYFactor());
		guildInformationTooltip.update(X_SOCIAL_FRAME+403*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+36*Mideas.getDisplayYFactor(), 320*Mideas.getDisplayXFactor(), 242*Mideas.getDisplayYFactor());
		if(displayGuildInformation) {
			informationBackground.update(X_SOCIAL_FRAME+GUILD_INFORMATION_ALERT_BACKGROUND_X*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME, GUILD_INFORMATION_ALERT_BACKGROUND_X_SIZE*Mideas.getDisplayXFactor(), GUILD_INFORMATION_ALERT_BACKGROUND_Y_SIZE*Mideas.getDisplayYFactor());
		}
		else if(memberInformationDisplay) {
			informationBackground.update(X_SOCIAL_FRAME+MEMBER_INFORMATION_ALERT_BACKGROUND_X*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+MEMBER_INFORMATION_ALERT_BACKGROUND_Y, MEMBER_INFORMATION_ALERT_BACKGROUND_X_SIZE*Mideas.getDisplayXFactor(), MEMBER_INFORMATION_ALERT_BACKGROUND_Y_SIZE*Mideas.getDisplayYFactor());
		}
		noteTooltip.update(X_SOCIAL_FRAME+412*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+153*Mideas.getDisplayYFactor(), 205*Mideas.getDisplayXFactor(), 45*Mideas.getDisplayYFactor());
	}
	
	public static Input getInformationInput() {
		return informationInput;
	}
}
