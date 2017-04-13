package com.mideas.rpg.v2.hud.social.guild;

import org.lwjgl.input.Mouse;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.Sprites;
import com.mideas.rpg.v2.TTF;
import com.mideas.rpg.v2.FontManager;
import com.mideas.rpg.v2.chat.ChatFrame;
import com.mideas.rpg.v2.command.CommandGuild;
import com.mideas.rpg.v2.command.CommandIgnore;
import com.mideas.rpg.v2.command.CommandParty;
import com.mideas.rpg.v2.game.guild.GuildMember;
import com.mideas.rpg.v2.game.guild.GuildRank;
import com.mideas.rpg.v2.game.guild.MemberSort;
import com.mideas.rpg.v2.hud.PopupFrame;
import com.mideas.rpg.v2.utils.AlertBackground;
import com.mideas.rpg.v2.utils.Button;
import com.mideas.rpg.v2.utils.ButtonMenuSort;
import com.mideas.rpg.v2.utils.CheckBox;
import com.mideas.rpg.v2.utils.Color;
import com.mideas.rpg.v2.utils.CrossButton;
import com.mideas.rpg.v2.utils.Draw;
import com.mideas.rpg.v2.utils.DropDownMenu;
import com.mideas.rpg.v2.utils.EditBox;
import com.mideas.rpg.v2.utils.HorizontalBar;
import com.mideas.rpg.v2.utils.Input;
import com.mideas.rpg.v2.utils.ScrollBar;
import com.mideas.rpg.v2.utils.TextMenu;
import com.mideas.rpg.v2.utils.Tooltip;
import com.mideas.rpg.v2.utils.TooltipMenu;

import static com.mideas.rpg.v2.hud.social.SocialFrame.Y_SOCIAL_FRAME;
import static com.mideas.rpg.v2.hud.social.SocialFrame.X_SOCIAL_FRAME;

public class GuildFrame {

	private final static int MANAGE_FRAME_ALERT_BACKGROUND_X = 394;
	private final static int MANAGE_FRAME_ALERT_BACKGROUND_X_SIZE = 330;
	private final static int MANAGE_FRAME_ALERT_BACKGROUND_Y = 8;
	private final static int MANAGE_FRAME_ALERT_BACKGROUND_Y_SIZE = 460;
	private final static int GUILD_INFORMATION_ALERT_BACKGROUND_X = 393;
	private final static int GUILD_INFORMATION_ALERT_BACKGROUND_X_SIZE = 340;
	private final static int GUILD_INFORMATION_ALERT_BACKGROUND_Y_SIZE = 315;
	private final static int MEMBER_INFORMATION_ALERT_BACKGROUND_X = 395;
	private final static int MEMBER_INFORMATION_ALERT_BACKGROUND_Y = 25;
	private final static int MEMBER_INFORMATION_ALERT_BACKGROUND_X_SIZE = 240;
	private final static int MEMBER_INFORMATION_ALERT_BACKGROUND_OFFICER_Y_SIZE = 275;
	private final static int MEMBER_INFORMATION_ALERT_BACKGROUND_NO_OFFICER_Y_SIZE = 213;
	private final static int MEMBER_DISPLAY_KICK_BUTTON_X = 405;
	private final static int MEMBER_DISPLAY_BUTTON_X_SIZE = 108;
	private final static int MEMBER_DISPLAY_BUTTON_Y_SIZE = 24;
	private final static int MEMBER_DISPLAY_INVITE_BUTTON_X = 515;
	private final static int MEMBER_DISPLAY_BUTTON_NO_OFFICER_Y = 203;
	private final static int MEMBER_DISPLAY_BUTTON_OFFICER_Y = 266;
	private final static int BUTTON_MENU_SORT_Y = 74;
	
	private static boolean shouldUpdateSize;
	private final static int GUILD_MEMBER_MAXIMUM_DISPLAY = 13;
	static MemberSort memberSort;
	static boolean manageFrameChangeMade;
	static boolean manageFrameOpen;
	static boolean showOfflineMembers;
	private static int hoveredMember = -1;
	static GuildMember memberInformationDisplayed;
	static GuildRank selectedRank;
	private static boolean hasInitRank;
	static boolean memberInformationDisplay;
	private static int hoveredMemberX;
	private static int hoveredMemberY;
	private static int downMember = -1;
	static GuildMember memberMenu;
	private static int selectedMember = -1;
	private final static Color YELLOW = Color.YELLOW;
	private final static Color GREY = Color.GREY;
	private final static Color GREEN = new Color(64/255f, 251/255f, 64f/255f);
	static boolean displayGuildInformation;
	private static boolean inputInit;
	static boolean dropDownMenuInit;
	private final static HorizontalBar manageFrameHorizontalBar = new HorizontalBar(X_SOCIAL_FRAME+402*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+65*Mideas.getDisplayYFactor(), 300*Mideas.getDisplayXFactor());
	private final static ScrollBar memberScrollBar = new ScrollBar(X_SOCIAL_FRAME+358*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+100*Mideas.getDisplayYFactor(), 240*Mideas.getDisplayYFactor(), 400*Mideas.getDisplayXFactor(), 270*Mideas.getDisplayYFactor(), false, 18*Mideas.getDisplayYFactor());
	private final static CheckBox showOfflineMembersCheckBox = new CheckBox(X_SOCIAL_FRAME+353*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+44*Mideas.getDisplayYFactor(), 17*Mideas.getDisplayXFactor(), 15*Mideas.getDisplayYFactor()) {
		
		@Override
		public boolean get() {
			return showOfflineMembers;
		}
		
		@Override
		public void set() {
			showOfflineMembers = !showOfflineMembers;
		}
	};
	final static Input informationInput = new Input(FontManager.get("FRIZQT", 13), 300, true, false) {
		
		@Override
		public boolean keyEvent(char c) {
			if(c == 27) { //escape
				this.setIsActive(false);
				return true;
			}
			return false;
		}
	};
	/*final static Input rankNameInput = new Input(FontManager.get("FRIZQT", 13), 300, true, false) {
		
		@Override
		public boolean keyEvent(char c) {
			if(c == Input.ENTER_CHAR_VALUE || c == Input.ESCAPE_CHAR_VALUE) {
				this.setIsActive(false);
				return true;
			}
			return false;
		}
	};*/
	final static EditBox rankNameEditBox = new EditBox(X_SOCIAL_FRAME+530*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+85*Mideas.getDisplayYFactor(), 155*Mideas.getDisplayXFactor(), 15, 3, 140*Mideas.getDisplayXFactor(), FontManager.get("FRIZQT", 12), false) {
		
		@Override
		public boolean keyEvent(char c) {
			if(c == Input.ENTER_CHAR_VALUE || c == Input.ESCAPE_CHAR_VALUE) {
				this.input.setIsActive(false);
				return true;
			}
			return false;
		}
	};
	private final static int DROP_DOWN_MENU_BAR_X = 458;
	private final static int DROP_DOWN_MENU_BAR_X_SIZE = 205;
	private final static int DROP_DOWN_MENU_BAR_Y = 38;
	private final static int DROP_DOWN_MENU_ALERT_X = 450;
	private final static int DROP_DOWN_MENU_ALERT_Y = 63;
	private final static int DROP_DOWN_MENU_ALERT_X_SIZE = 130;
	final static DropDownMenu manageRankDropDownMenu = new DropDownMenu(X_SOCIAL_FRAME+DROP_DOWN_MENU_BAR_X*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+DROP_DOWN_MENU_BAR_Y*Mideas.getDisplayYFactor(), DROP_DOWN_MENU_BAR_X_SIZE*Mideas.getDisplayXFactor(), X_SOCIAL_FRAME+DROP_DOWN_MENU_ALERT_X*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+DROP_DOWN_MENU_ALERT_Y*Mideas.getDisplayXFactor(), DROP_DOWN_MENU_ALERT_X_SIZE*Mideas.getDisplayXFactor(), 13, .6f) {
		
		@Override
		public void menuEventButtonClick() {
			selectedRank.resetTempRank();
			selectedRank = Mideas.joueur1().getGuild().getRankList().get(this.selectedMenuValue);
			rankNameEditBox.setText(selectedRank.getName());
			manageFrameChangeMade = false;
		}
	};	
	private final static Button manageRankAcceptButton = new Button(X_SOCIAL_FRAME+537*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+435*Mideas.getDisplayYFactor(), 88*Mideas.getDisplayXFactor(), 24*Mideas.getDisplayYFactor(), "Accept", 12, 1) {
		
		@Override
		public void eventButtonClick() {
			selectedRank.buildTempPermission();
			CommandGuild.updatePermission(selectedRank.getOrder(), selectedRank.getTempPermission(), rankNameEditBox.getText());
			rankNameEditBox.setText(selectedRank.getName());
			closeManageFrame();
		}
		
		@Override
		public boolean activateCondition() {
			return manageFrameChangeMade;
		}
	};	
	private final static Button manageRankCloseFrameButton = new Button(X_SOCIAL_FRAME+630*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+435*Mideas.getDisplayYFactor(), 84*Mideas.getDisplayXFactor(), 24*Mideas.getDisplayYFactor(), "Cancel", 12, 1) {
		
		@Override
		public void eventButtonClick() {
			closeManageFrame();
		}
	};
	final static AlertBackground informationBackground = new AlertBackground(X_SOCIAL_FRAME+GUILD_INFORMATION_ALERT_BACKGROUND_X*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME, GUILD_INFORMATION_ALERT_BACKGROUND_X_SIZE*Mideas.getDisplayXFactor(), GUILD_INFORMATION_ALERT_BACKGROUND_Y_SIZE*Mideas.getDisplayYFactor(), .6f);
	private final static Tooltip guildInformationTooltip = new Tooltip(X_SOCIAL_FRAME+403*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+36*Mideas.getDisplayYFactor(), 320*Mideas.getDisplayXFactor(), 242*Mideas.getDisplayYFactor(), .6f);
	private final static Tooltip noteTooltip = new Tooltip(X_SOCIAL_FRAME+412*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+153*Mideas.getDisplayYFactor(), 205*Mideas.getDisplayXFactor(), 45*Mideas.getDisplayYFactor(), .3f);
	private final static Tooltip officerNoteTooltip = new Tooltip(X_SOCIAL_FRAME+412*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+213*Mideas.getDisplayYFactor(), 205*Mideas.getDisplayXFactor(), 45*Mideas.getDisplayYFactor(), .3f);
	private final static Button infoButton = new Button(X_SOCIAL_FRAME+17*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+437*Mideas.getDisplayYFactor(), 134*Mideas.getDisplayXFactor(), 23*Mideas.getDisplayYFactor(), "Guild Information", 12, 1) {
		
		@Override
		public void eventButtonClick() {
			if(displayGuildInformation) {
				informationInput.setIsActive(false);
			}
			else {
				informationBackground.update(X_SOCIAL_FRAME+GUILD_INFORMATION_ALERT_BACKGROUND_X*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME, GUILD_INFORMATION_ALERT_BACKGROUND_X_SIZE*Mideas.getDisplayXFactor(), GUILD_INFORMATION_ALERT_BACKGROUND_Y_SIZE*Mideas.getDisplayYFactor());
				closeMemberInformationFrame();
			}
			closeManageFrame();
			displayGuildInformation = !displayGuildInformation;
		}
	};
	private final static Button addMemberButton = new Button(X_SOCIAL_FRAME+158*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+437*Mideas.getDisplayYFactor(), 110*Mideas.getDisplayXFactor(), 23*Mideas.getDisplayYFactor(), "Add Member", 12, 1) {
		
		@Override
		public void eventButtonClick() {
			PopupFrame.activateAddGuildMemberPopupInput();
		}
		
		@Override
		public boolean activateCondition() {
			return Mideas.joueur1().getGuildRank().canInvitePlayer();
		}
	};
	private final static Button manageGuildButton = new Button(X_SOCIAL_FRAME+274*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+437*Mideas.getDisplayYFactor(), 112*Mideas.getDisplayXFactor(), 23*Mideas.getDisplayYFactor(), "Guild Control", 12, 1) {
		
		@Override
		public void eventButtonClick() {
			closeGuildInformationFrame();
			closeMemberInformationFrame();
			manageFrameOpen = !manageFrameOpen;
			if(!dropDownMenuInit) {
				fillDropDownMenuWithRank();
				manageRankDropDownMenu.update(X_SOCIAL_FRAME+DROP_DOWN_MENU_BAR_X*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+DROP_DOWN_MENU_BAR_Y*Mideas.getDisplayYFactor(), DROP_DOWN_MENU_BAR_X_SIZE*Mideas.getDisplayXFactor(), X_SOCIAL_FRAME+DROP_DOWN_MENU_ALERT_X*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+DROP_DOWN_MENU_ALERT_Y*Mideas.getDisplayXFactor(), DROP_DOWN_MENU_ALERT_X_SIZE*Mideas.getDisplayXFactor());
				rankNameEditBox.setText(selectedRank.getName());
				dropDownMenuInit = true;
				manageRankDropDownMenu.setActive(true);
			}
			if(manageFrameOpen) {
				openManageFrame();
			}
			else {
				closeManageFrame();
			}
			informationBackground.update(X_SOCIAL_FRAME+MANAGE_FRAME_ALERT_BACKGROUND_X*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+MANAGE_FRAME_ALERT_BACKGROUND_Y*Mideas.getDisplayYFactor(), MANAGE_FRAME_ALERT_BACKGROUND_X_SIZE*Mideas.getDisplayXFactor(), MANAGE_FRAME_ALERT_BACKGROUND_Y_SIZE*Mideas.getDisplayYFactor());
		}
		
		@Override
		public boolean activateCondition() {
			return Mideas.joueur1().getGuild().isLeader(Mideas.joueur1().getId());
		}
		
		@Override
		public boolean hoverSpriteActivateCondition() {
			return manageFrameOpen;
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
			closeGuildInformationFrame();
		}
	};
	private final static Button acceptInformationButton = new Button(X_SOCIAL_FRAME+530*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+274*Mideas.getDisplayYFactor(), 90*Mideas.getDisplayXFactor(), 28*Mideas.getDisplayYFactor(), "Accept", 12, 1) {
		
		@Override
		public void eventButtonClick() {
			this.reset();
			CommandGuild.setInformation(informationInput.getText());
			closeGuildInformationFrame();
		}
		
		@Override
		public boolean activateCondition() {
			return Mideas.joueur1().getGuildRank().canModifyGuildInformation();
		}
	};
	private final static Button memberDisplayKickButton = new Button(X_SOCIAL_FRAME+MEMBER_DISPLAY_KICK_BUTTON_X*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+MEMBER_DISPLAY_BUTTON_OFFICER_Y*Mideas.getDisplayYFactor(), MEMBER_DISPLAY_BUTTON_X_SIZE*Mideas.getDisplayXFactor(), MEMBER_DISPLAY_BUTTON_Y_SIZE*Mideas.getDisplayYFactor(), "Delete", 12, 1) {
		
		@Override
		public void eventButtonClick() {
			PopupFrame.activateGuildKickMemberPopup(memberInformationDisplayed.getName(), memberInformationDisplayed.getId());
		}
		
		@Override
		public boolean activateCondition() {
			return memberInformationDisplayed != null && memberInformationDisplayed.getId() != Mideas.joueur1().getId() && Mideas.joueur1().getGuildRank().canKickMember() && memberInformationDisplayed.getRank().getOrder() > Mideas.joueur1().getGuildRank().getOrder();
		}
	};
	private final static Button memberDisplayInviteButton = new Button(X_SOCIAL_FRAME+MEMBER_DISPLAY_INVITE_BUTTON_X*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+MEMBER_DISPLAY_BUTTON_OFFICER_Y*Mideas.getDisplayYFactor(), MEMBER_DISPLAY_BUTTON_X_SIZE*Mideas.getDisplayXFactor(), MEMBER_DISPLAY_BUTTON_Y_SIZE*Mideas.getDisplayYFactor(), "Inv. party", 12, 1) {
		
		@Override
		public void eventButtonClick() {
			informationInput.setIsActive(false);
			displayGuildInformation = false;
			CommandParty.invitePlayer(memberInformationDisplayed.getName());
		}
		
		@Override
		public boolean activateCondition() {
			return memberInformationDisplayed != null && memberInformationDisplayed.isOnline() && Mideas.joueur1().canInvitePlayerInParty(memberInformationDisplayed.getId());
		}
	};
	private final static CrossButton closeInformationFrameCrossButton = new CrossButton(X_SOCIAL_FRAME+701*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+10*Mideas.getDisplayYFactor(), 19*Mideas.getDisplayXFactor(), 18*Mideas.getDisplayYFactor()) {
		
		@Override
		public void eventButtonClick() {
			closeGuildInformationFrame();
			this.reset();
		}
	};
	private final static CrossButton closeDisplayMemberFrameCrossButton = new CrossButton(X_SOCIAL_FRAME+605*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+35*Mideas.getDisplayYFactor(), 19*Mideas.getDisplayXFactor(), 18*Mideas.getDisplayYFactor()) {
		
		@Override
		public void eventButtonClick() {
			closeMemberInformationFrame();
			this.reset();
		}
	};
	private final static ButtonMenuSort sortByNameButton = new ButtonMenuSort(X_SOCIAL_FRAME+20*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+BUTTON_MENU_SORT_Y*Mideas.getDisplayYFactor(), 94*Mideas.getDisplayXFactor(), "Name", 12) {
		
		@Override
		public void eventButtonClick() {
			if(memberSort == MemberSort.NAME_ASCENDING) {
				Mideas.joueur1().getGuild().sortMemberByNameDescending();
				memberSort = MemberSort.NAME_DESCENDING;
			}
			else {
				Mideas.joueur1().getGuild().sortMemberByNameAscending();
				memberSort = MemberSort.NAME_ASCENDING;
			}
		}
	};
	private final static ButtonMenuSort sortByRankButton = new ButtonMenuSort(X_SOCIAL_FRAME+113*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+BUTTON_MENU_SORT_Y*Mideas.getDisplayYFactor(), 86*Mideas.getDisplayXFactor(), "Rank", 12) {
		
		@Override
		public void eventButtonClick() {
			if(memberSort == MemberSort.RANK_ASCENDING) {
				Mideas.joueur1().getGuild().sortMemberByRankDescending();
				memberSort = MemberSort.RANK_DESCENDING;
			}
			else {
				Mideas.joueur1().getGuild().sortMemberByRankAscending();
				memberSort = MemberSort.RANK_ASCENDING;
			}
		}
	};
	private final static ButtonMenuSort sortByNoteButton = new ButtonMenuSort(X_SOCIAL_FRAME+198*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+BUTTON_MENU_SORT_Y*Mideas.getDisplayYFactor(), 100*Mideas.getDisplayXFactor(), "Note", 12) {
		
		@Override
		public void eventButtonClick() {
			if(memberSort == MemberSort.NOTE_ASCENDING) {
				Mideas.joueur1().getGuild().sortMemberByNoteDescending();
				memberSort = MemberSort.NOTE_DESCENDING;
			}
			else {
				Mideas.joueur1().getGuild().sortMemberByNoteAscending();
				memberSort = MemberSort.NOTE_ASCENDING;
			}
		}
	};
	private final static ButtonMenuSort sortByLastOnlineButton = new ButtonMenuSort(X_SOCIAL_FRAME+296*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+BUTTON_MENU_SORT_Y*Mideas.getDisplayYFactor(), 89*Mideas.getDisplayXFactor(), "Last Online", 12) {
		
		@Override
		public void eventButtonClick() {
			if(memberSort == MemberSort.ONLINE_ASCENDING) {
				Mideas.joueur1().getGuild().sortMemberByOnlineDescending();
				memberSort = MemberSort.ONLINE_DESCENDING;
			}
			else {
				Mideas.joueur1().getGuild().sortMemberByOnlineAscending();
				memberSort = MemberSort.ONLINE_ASCENDING;
			}
		}
	};
	final static TooltipMenu displayedMemberMenu = new TooltipMenu(0, 0, 0) {
		
		@Override
		public void onClose() {
			memberMenu = null;
		}
	};
	private static TextMenu whisperTextMenu = new TextMenu(0, 0, 87*Mideas.getDisplayXFactor(), "Whisper", 12, 1, -8*Mideas.getDisplayXFactor()) {
		@Override
		public void eventButtonClick() {
			ChatFrame.setWhisper(memberMenu.getName());
			ChatFrame.setChatActive(true);
			this.reset();
			displayedMemberMenu.setActive(false);
		}
	};
	private static TextMenu targetTextMenu = new TextMenu(0, 0, 80*Mideas.getDisplayXFactor(), "Target", 12, 1, -8*Mideas.getDisplayXFactor()) {
		@Override
		public void eventButtonClick() {
			this.reset();
			displayedMemberMenu.setActive(false);
		}
	};
	private static TextMenu ignoreTextMenu = new TextMenu(0, 0, 80*Mideas.getDisplayXFactor(), "Ignore", 12, 1, -8*Mideas.getDisplayXFactor()) {
		@Override
		public void eventButtonClick() {
			this.reset();
			CommandIgnore.addIgnore(memberMenu.getName());
			displayedMemberMenu.setActive(false);
		}
	};
	private static TextMenu leaveGuildTextMenu = new TextMenu(0, 0, 80*Mideas.getDisplayXFactor(), "Leave guild", 12, 1, -8*Mideas.getDisplayXFactor()) {
		@Override
		public void eventButtonClick() {
			this.reset();
			CommandGuild.leaveGuild();
			displayedMemberMenu.setActive(false);
		}
	};
	private static TextMenu setLeaderTextMenu = new TextMenu(0, 0, 80*Mideas.getDisplayXFactor(), "Give leadership", 12, 1, -8*Mideas.getDisplayXFactor()) {
		@Override
		public void eventButtonClick() {
			this.reset();
			CommandGuild.setLeader(memberMenu.getId());
			displayedMemberMenu.setActive(false);
		}
	};
	private static TextMenu cancelTextMenu = new TextMenu(0, 0, 80*Mideas.getDisplayXFactor(), "Cancel", 12, 1, -8*Mideas.getDisplayXFactor()) {
		@Override
		public void eventButtonClick() {
			this.reset();
			displayedMemberMenu.setActive(false);
		}
	};
	
	
	
	
	private final static int MANAGE_FRAME_CHECKBOX_LEFT_SIDE = 419;
	private final static int MANAGE_FRAME_CHECKBOX_RIGHT_SIDE = 574;
	private final static CheckBox canListenGuildChannelCheckBox = new CheckBox(X_SOCIAL_FRAME+MANAGE_FRAME_CHECKBOX_LEFT_SIDE*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+137*Mideas.getDisplayYFactor(), 15*Mideas.getDisplayXFactor(), 13*Mideas.getDisplayYFactor(), "Guildchat Listen", YELLOW, 12) {
		
		@Override
		public boolean get() {
			return selectedRank != null && selectedRank.canTempListenGuildChannel();
		}
		
		@Override
		public void set() {
			if(selectedRank != null) {
				selectedRank.setTempPermission(GuildRank.CAN_LISTEN_GUILD_CHANNEL, !get());
				manageFrameChangeMade = true;
			}
		}
		
		@Override
		public boolean activateCondition() {
			return selectedRank.getOrder() != 1;
		}
	};
	private final static CheckBox canListenOfficerChannelCheckBox = new CheckBox(X_SOCIAL_FRAME+MANAGE_FRAME_CHECKBOX_LEFT_SIDE*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+158*Mideas.getDisplayYFactor(), 15*Mideas.getDisplayXFactor(), 13*Mideas.getDisplayYFactor(), "Officerchat Listen", YELLOW, 12) {
		
		@Override
		public boolean get() {
			return selectedRank != null && selectedRank.canTempListenOfficerChannel();
		}
		
		@Override
		public void set() {
			if(selectedRank != null) {
				selectedRank.setTempPermission(GuildRank.CAN_LISTEN_OFFICER_CHANNEL, !get());
				manageFrameChangeMade = true;
			}
		}
		
		@Override
		public boolean activateCondition() {
			return selectedRank.getOrder() != 1;
		}
	};
	private final static CheckBox canPromoteCheckBox = new CheckBox(X_SOCIAL_FRAME+MANAGE_FRAME_CHECKBOX_LEFT_SIDE*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+179*Mideas.getDisplayYFactor(), 15*Mideas.getDisplayXFactor(), 13*Mideas.getDisplayYFactor(), "Promote", YELLOW, 12) {
		
		@Override
		public boolean get() {
			return selectedRank != null && selectedRank.canTempPromote();
		}
		
		@Override
		public void set() {
			if(selectedRank != null) {
				selectedRank.setTempPermission(GuildRank.CAN_PROMOTE, !get());
				manageFrameChangeMade = true;
			}
		}
		
		@Override
		public boolean activateCondition() {
			return selectedRank.getOrder() != 1;
		}
	};
	private final static CheckBox canInviteMemberCheckBox = new CheckBox(X_SOCIAL_FRAME+MANAGE_FRAME_CHECKBOX_LEFT_SIDE*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+200*Mideas.getDisplayYFactor(), 15*Mideas.getDisplayXFactor(), 13*Mideas.getDisplayYFactor(), "Invite Member", YELLOW, 12) {
		
		@Override
		public boolean get() {
			return selectedRank != null && selectedRank.canTempInvitePlayer();
		}
		
		@Override
		public void set() {
			if(selectedRank != null) {
				selectedRank.setTempPermission(GuildRank.CAN_INVITE_MEMBER, !get());
				manageFrameChangeMade = true;
			}
		}
		
		@Override
		public boolean activateCondition() {
			return selectedRank.getOrder() != 1;
		}
	};
	private final static CheckBox canSetMotdCheckBox = new CheckBox(X_SOCIAL_FRAME+MANAGE_FRAME_CHECKBOX_LEFT_SIDE*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+221*Mideas.getDisplayYFactor(), 15*Mideas.getDisplayXFactor(), 13*Mideas.getDisplayYFactor(), "Set MOTD", YELLOW, 12) {
		
		@Override
		public boolean get() {
			return selectedRank != null && selectedRank.canTempModifyMotd();
		}
		
		@Override
		public void set() {
			if(selectedRank != null) {
				selectedRank.setTempPermission(GuildRank.CAN_SET_MOTD, !get());
				manageFrameChangeMade = true;
			}
		}
		
		@Override
		public boolean activateCondition() {
			return selectedRank.getOrder() != 1;
		}
	};
	private final static CheckBox canSeeOfficerNoteCheckBox = new CheckBox(X_SOCIAL_FRAME+MANAGE_FRAME_CHECKBOX_LEFT_SIDE*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+242*Mideas.getDisplayYFactor(), 15*Mideas.getDisplayXFactor(), 13*Mideas.getDisplayYFactor(), "View Officer Note", YELLOW, 12) {
		
		@Override
		public boolean get() {
			return selectedRank != null && selectedRank.canTempSeeOfficerNote();
		}
		
		@Override
		public void set() {
			if(selectedRank != null) {
				selectedRank.setTempPermission(GuildRank.CAN_SEE_OFFICER_NOTE, !get());
				manageFrameChangeMade = true;
			}
		}
		
		@Override
		public boolean activateCondition() {
			return selectedRank.getOrder() != 1;
		}
	};
	private final static CheckBox canModifyGuildInformationCheckBox = new CheckBox(X_SOCIAL_FRAME+MANAGE_FRAME_CHECKBOX_LEFT_SIDE*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+263*Mideas.getDisplayYFactor(), 15*Mideas.getDisplayXFactor(), 13*Mideas.getDisplayYFactor(), "Modify Guild Infos", YELLOW, 12) {
		
		@Override
		public boolean get() {
			return selectedRank != null && selectedRank.canTempModifyGuildInformation();
		}
		
		@Override
		public void set() {
			if(selectedRank != null) {
				selectedRank.setTempPermission(GuildRank.CAN_MODIFY_GUILD_INFORMATION, !get());
				manageFrameChangeMade = true;
			}
		}
		
		@Override
		public boolean activateCondition() {
			return selectedRank.getOrder() != 1;
		}
	};
	private final static CheckBox canTalkInGuildChannelCheckBox = new CheckBox(X_SOCIAL_FRAME+MANAGE_FRAME_CHECKBOX_RIGHT_SIDE*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+136*Mideas.getDisplayYFactor(), 15*Mideas.getDisplayXFactor(), 13*Mideas.getDisplayYFactor(), "Guildchat Speak", YELLOW, 12) {
		
		@Override
		public boolean get() {
			return selectedRank != null && selectedRank.canTempTalkInGuildChannel();
		}
		
		@Override
		public void set() {
			if(selectedRank != null) {
				selectedRank.setTempPermission(GuildRank.CAN_TALK_GUILD_CHANNEL, !get());
				manageFrameChangeMade = true;
			}
		}
		
		@Override
		public boolean activateCondition() {
			return selectedRank.getOrder() != 1;
		}
	};
	private final static CheckBox canTalkInOfficerChannelCheckBox = new CheckBox(X_SOCIAL_FRAME+MANAGE_FRAME_CHECKBOX_RIGHT_SIDE*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+157*Mideas.getDisplayYFactor(), 15*Mideas.getDisplayXFactor(), 13*Mideas.getDisplayYFactor(), "Officerchat Speak", YELLOW, 12) {
		
		@Override
		public boolean get() {
			return selectedRank != null && selectedRank.canTempTalkInOfficerChannel();
		}
		
		@Override
		public void set() {
			if(selectedRank != null) {
				selectedRank.setTempPermission(GuildRank.CAN_TALK_OFFICER_CHANNEL, !get());
				manageFrameChangeMade = true;
			}
		}
		
		@Override
		public boolean activateCondition() {
			return selectedRank.getOrder() != 1;
		}
	};
	private final static CheckBox canDemoteCheckBox = new CheckBox(X_SOCIAL_FRAME+MANAGE_FRAME_CHECKBOX_RIGHT_SIDE*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+178*Mideas.getDisplayYFactor(), 15*Mideas.getDisplayXFactor(), 13*Mideas.getDisplayYFactor(), "Demote", YELLOW, 12) {
		
		@Override
		public boolean get() {
			return selectedRank != null && selectedRank.canTempDemote();
		}
		
		@Override
		public void set() {
			if(selectedRank != null) {
				selectedRank.setTempPermission(GuildRank.CAN_DEMOTE, !get());
				manageFrameChangeMade = true;
			}
		}
		
		@Override
		public boolean activateCondition() {
			return selectedRank.getOrder() != 1;
		}
	};
	private final static CheckBox canRemoveMemberCheckBox = new CheckBox(X_SOCIAL_FRAME+MANAGE_FRAME_CHECKBOX_RIGHT_SIDE*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+199*Mideas.getDisplayYFactor(), 15*Mideas.getDisplayXFactor(), 13*Mideas.getDisplayYFactor(), "Remove Member", YELLOW, 12) {
		
		@Override
		public boolean get() {
			return selectedRank != null && selectedRank.canTempKickMember();
		}
		
		@Override
		public void set() {
			if(selectedRank != null) {
				selectedRank.setTempPermission(GuildRank.CAN_KICK_MEMBER, !get());
				manageFrameChangeMade = true;
			}
		}
		
		@Override
		public boolean activateCondition() {
			return selectedRank.getOrder() != 1;
		}
	};
	private final static CheckBox canEditPublicNoteCheckBox = new CheckBox(X_SOCIAL_FRAME+MANAGE_FRAME_CHECKBOX_RIGHT_SIDE*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+220*Mideas.getDisplayYFactor(), 15*Mideas.getDisplayXFactor(), 13*Mideas.getDisplayYFactor(), "Edit Public Note", YELLOW, 12) {
		
		@Override
		public boolean get() {
			return selectedRank != null && selectedRank.canTempEditPublicNote();
		}
		
		@Override
		public void set() {
			if(selectedRank != null) {
				selectedRank.setTempPermission(GuildRank.CAN_EDIT_PUBLIC_NOTE, !get());
				manageFrameChangeMade = true;
			}
		}
		
		@Override
		public boolean activateCondition() {
			return selectedRank.getOrder() != 1;
		}
	};
	private final static CheckBox canEditOfficerNoteCheckBox = new CheckBox(X_SOCIAL_FRAME+MANAGE_FRAME_CHECKBOX_RIGHT_SIDE*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+241*Mideas.getDisplayYFactor(), 15*Mideas.getDisplayXFactor(), 13*Mideas.getDisplayYFactor(), "Edit Officer Note", YELLOW, 12) {
		
		@Override
		public boolean get() {
			return selectedRank != null && selectedRank.canTempEditOfficerNote();
		}
		
		@Override
		public void set() {
			if(selectedRank != null) {
				selectedRank.setTempPermission(GuildRank.CAN_EDIT_OFFICER_NOTE, !get());
				manageFrameChangeMade = true;
			}
		}
		
		@Override
		public boolean activateCondition() {
			return selectedRank.getOrder() != 1;
		}
	};
	
	public static void draw() {
		if(!hasInitRank) {
			selectedRank = Mideas.joueur1().getGuild().getRank(1);
			hasInitRank = true;
		}
		updateSize();
		Draw.drawQuad(Sprites.guild_frame, X_SOCIAL_FRAME, Y_SOCIAL_FRAME);
		FontManager.get("FRIZQT", 15).drawStringShadow(X_SOCIAL_FRAME+220*Mideas.getDisplayXFactor()-FontManager.get("FRIZQT", 15).getWidth(Mideas.joueur1().getGuildTitle())/2, Y_SOCIAL_FRAME+14*Mideas.getDisplayYFactor(), Mideas.joueur1().getGuildTitle(), YELLOW, Color.BLACK, 1, 0, 0);
		FontManager.get("FRIZQT", 12).drawBegin();
		FontManager.get("FRIZQT", 12).drawStringShadowPart(X_SOCIAL_FRAME+23*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+368*Mideas.getDisplayYFactor(), "Guild Message Of The Day:", YELLOW, Color.BLACK, 1, 0, 0);
		FontManager.get("FRIZQT", 12).drawStringShadowPartReversed(X_SOCIAL_FRAME+338*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+43*Mideas.getDisplayYFactor(), "Show Offline Members", Color.WHITE, Color.BLACK, 1, 0, 0);
		FontManager.get("FRIZQT", 12).drawEnd();
		drawNumberMember();
		drawMotd();
		drawMembers();
		drawInformationFrame();
		drawDisplayedMember();
		drawManageFrame();
		drawSortButton();
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
						informationInput.setIsActive(true);
					}
				}
			}
			if(journalButton.event()) return true;
			if(closeInformationButton.event()) return true;
			if(acceptInformationButton.event()) return true;
			if(closeInformationFrameCrossButton.event()) return true;
		}
		if(mouseEventDisplayedMember()) return true;
		if(mouseEventMembers()) return true;
		if(mouseEventManageFrame()) return true;
		if(mouseEventSortButton()) return true;
		if(showOfflineMembersCheckBox.event()) return true;
		if(infoButton.event()) return true;
		if(addMemberButton.event()) return true;
		if(manageGuildButton.event()) return true;
		return false;
	}
	
	public static boolean event() {
		if(displayGuildInformation) {
			if(informationInput.event()) return true;
		}
		else if(manageFrameOpen) {
			if(rankNameEditBox.keyboardEvent()) {
				manageFrameChangeMade = true;
				return true;
			}
		}
		return false;
	}
	
	private static void drawMembers() {
		int iOffset = 0;
		if(showOfflineMembers && Mideas.joueur1().getGuild().getMemberList().size() > GUILD_MEMBER_MAXIMUM_DISPLAY) {
			iOffset = (int)((Mideas.joueur1().getGuild().getMemberList().size()-GUILD_MEMBER_MAXIMUM_DISPLAY)*memberScrollBar.getScrollPercentage());
			memberScrollBar.draw();
		}
		else if(!showOfflineMembers && Mideas.joueur1().getGuild().getNumberOnlineMember() > GUILD_MEMBER_MAXIMUM_DISPLAY) {
			iOffset = (int)((Mideas.joueur1().getGuild().getNumberOnlineMember()-GUILD_MEMBER_MAXIMUM_DISPLAY)*memberScrollBar.getScrollPercentage());
			memberScrollBar.draw();
		}
		int i = iOffset;
		float x = X_SOCIAL_FRAME+35*Mideas.getDisplayXFactor();
		float y = Y_SOCIAL_FRAME+105*Mideas.getDisplayYFactor();
		int yShift = 0;
		float yShiftHeight = 18*Mideas.getDisplayYFactor();
		TTF font = FontManager.get("FRIZQT", 13);
		font.drawBegin();
		while(i < Mideas.joueur1().getGuild().getMemberList().size()) {
			if(Mideas.joueur1().getGuild().getMemberList().get(i).isOnline()) {
				font.drawStringShadowPart(x, y+yShift, Mideas.joueur1().getGuild().getMemberList().get(i).getName(), Color.YELLOW, Color.BLACK, 1, 0, 0);
				font.drawStringShadowPart(x+90*Mideas.getDisplayXFactor(), y+yShift, "Area", Color.WHITE, Color.BLACK, 1, 0, 0);
				font.drawStringShadowPart(x+223*Mideas.getDisplayXFactor(), y+yShift, Mideas.joueur1().getGuild().getMemberList().get(i).getLevelString(), Color.WHITE, Color.BLACK, 1, 0, 0);
				font.drawStringShadowPart(x+255*Mideas.getDisplayXFactor(), y+yShift, Mideas.joueur1().getGuild().getMemberList().get(i).getClassTypeString(), Mideas.joueur1().getGuild().getMemberList().get(i).getColor(), Color.BLACK, 1, 0, 0);
				yShift+= yShiftHeight;
			}
			else if(showOfflineMembers) {
				font.drawStringShadowPart(x, y+yShift, Mideas.joueur1().getGuild().getMemberList().get(i).getName(), GREY, Color.BLACK, 1, 0, 0);
				font.drawStringShadowPart(x+90*Mideas.getDisplayXFactor(), y+yShift, "Area", GREY, Color.BLACK, 1, 0, 0);
				font.drawStringShadowPart(x+223*Mideas.getDisplayXFactor(), y+yShift, Mideas.joueur1().getGuild().getMemberList().get(i).getLevelString(), GREY, Color.BLACK, 1, 0, 0);
				font.drawStringShadowPart(x+255*Mideas.getDisplayXFactor(), y+yShift, Mideas.joueur1().getGuild().getMemberList().get(i).getClassTypeString(), GREY, Color.BLACK, 1, 0, 0);
				yShift+= yShiftHeight;
			}
			if(y+yShift+yShiftHeight >= Y_SOCIAL_FRAME+350*Mideas.getDisplayYFactor()) {
				break;
			}
			i++;
		}
		font.drawEnd();
		i = iOffset;
		yShift = 0;
		while(i < Mideas.joueur1().getGuild().getMemberList().size()) {
			if((Mideas.joueur1().getGuild().getMemberList().get(i).isOnline() || showOfflineMembers)) {
				if(hoveredMember == i || selectedMember == i) {
					Draw.drawQuadBlend(Sprites.friend_border, x-15*Mideas.getDisplayXFactor(), y+yShift, 343*Mideas.getDisplayXFactor(), 18*Mideas.getDisplayYFactor());
				}
				yShift+= yShiftHeight;
			}
			if(y+yShift+yShiftHeight >= Y_SOCIAL_FRAME+350*Mideas.getDisplayYFactor()) {
				break;
			}
			i++;
		}
		displayedMemberMenu.draw();
	}
	
	private static boolean mouseEventMembers() {
		int i = 0;
		if(displayedMemberMenu.event()) return true;
		if((showOfflineMembers && Mideas.joueur1().getGuild().getMemberList().size() > 13)) {
			memberScrollBar.event();
			i = (int)((Mideas.joueur1().getGuild().getMemberList().size()-13)*memberScrollBar.getScrollPercentage());
		}
		else if(!showOfflineMembers && Mideas.joueur1().getGuild().getNumberOnlineMember() > 13) {
			memberScrollBar.event();
			i = (int)((Mideas.joueur1().getGuild().getNumberOnlineMember()-13)*memberScrollBar.getScrollPercentage());
		}
		hoveredMember = -1;
		float x = X_SOCIAL_FRAME+19*Mideas.getDisplayXFactor();
		float y = Y_SOCIAL_FRAME+105*Mideas.getDisplayYFactor();
		int yShift = 0;
		float yShiftHeight = 18*Mideas.getDisplayYFactor();
		while(i < Mideas.joueur1().getGuild().getMemberList().size()) {
			if(Mideas.joueur1().getGuild().getMemberList().get(i).isOnline() || (!Mideas.joueur1().getGuild().getMemberList().get(i).isOnline() && showOfflineMembers)) {
				if(Mideas.getHover() && isHoverMember(x, y+yShift)) {
					hoveredMember = i;
					hoveredMemberX = (int)(Mideas.mouseX()+2*Mideas.getDisplayXFactor());
					hoveredMemberY = (int)(Mideas.mouseY()+2*Mideas.getDisplayYFactor());
					Mideas.setHover(false);
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
			if(Mouse.getEventButtonState()) {
				if(Mouse.getEventButton() == 0 || Mouse.getEventButton() == 1) {
					downMember = hoveredMember;
				}
			}
			if(!Mouse.getEventButtonState() && downMember == hoveredMember) {
				if(Mouse.getEventButton() == 0) {
					if(selectedMember == hoveredMember) {
						selectedMember = -1;
						memberInformationDisplay = false;
						memberInformationDisplayed = null;
						downMember = -1;
						return true;
					}
					memberInformationDisplayed = Mideas.joueur1().getGuild().getMemberList().get(hoveredMember);
					selectedMember = hoveredMember;
					if(Mideas.joueur1().getGuildRank().canSeeOfficerNote()) {
						informationBackground.update(X_SOCIAL_FRAME+MEMBER_INFORMATION_ALERT_BACKGROUND_X*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+MEMBER_INFORMATION_ALERT_BACKGROUND_Y*Mideas.getDisplayYFactor(), MEMBER_INFORMATION_ALERT_BACKGROUND_X_SIZE*Mideas.getDisplayXFactor(), MEMBER_INFORMATION_ALERT_BACKGROUND_OFFICER_Y_SIZE*Mideas.getDisplayYFactor());
						memberDisplayKickButton.update(X_SOCIAL_FRAME+MEMBER_DISPLAY_KICK_BUTTON_X*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+MEMBER_DISPLAY_BUTTON_OFFICER_Y*Mideas.getDisplayYFactor(), MEMBER_DISPLAY_BUTTON_X_SIZE*Mideas.getDisplayXFactor(), MEMBER_DISPLAY_BUTTON_Y_SIZE*Mideas.getDisplayYFactor());
						memberDisplayInviteButton.update(X_SOCIAL_FRAME+MEMBER_DISPLAY_INVITE_BUTTON_X*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+MEMBER_DISPLAY_BUTTON_OFFICER_Y*Mideas.getDisplayYFactor(), MEMBER_DISPLAY_BUTTON_X_SIZE*Mideas.getDisplayXFactor(), MEMBER_DISPLAY_BUTTON_Y_SIZE*Mideas.getDisplayYFactor());
						}
					else {
						informationBackground.update(X_SOCIAL_FRAME+MEMBER_INFORMATION_ALERT_BACKGROUND_X*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+MEMBER_INFORMATION_ALERT_BACKGROUND_Y*Mideas.getDisplayYFactor(), MEMBER_INFORMATION_ALERT_BACKGROUND_X_SIZE*Mideas.getDisplayXFactor(), MEMBER_INFORMATION_ALERT_BACKGROUND_NO_OFFICER_Y_SIZE*Mideas.getDisplayYFactor());
						memberDisplayKickButton.update(X_SOCIAL_FRAME+MEMBER_DISPLAY_KICK_BUTTON_X*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+MEMBER_DISPLAY_BUTTON_NO_OFFICER_Y*Mideas.getDisplayYFactor(), MEMBER_DISPLAY_BUTTON_X_SIZE*Mideas.getDisplayXFactor(), MEMBER_DISPLAY_BUTTON_Y_SIZE*Mideas.getDisplayYFactor());
						memberDisplayInviteButton.update(X_SOCIAL_FRAME+MEMBER_DISPLAY_INVITE_BUTTON_X*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+MEMBER_DISPLAY_BUTTON_NO_OFFICER_Y*Mideas.getDisplayYFactor(), MEMBER_DISPLAY_BUTTON_X_SIZE*Mideas.getDisplayXFactor(), MEMBER_DISPLAY_BUTTON_Y_SIZE*Mideas.getDisplayYFactor());
					}
					memberInformationDisplay = true;
					closeGuildInformationFrame();
					closeManageFrame();
					downMember = -1;
					return true;
				}
				else if(Mouse.getEventButton() == 1) {
					if(displayedMemberMenu.isActive()) {
						displayedMemberMenu.setActive(false);
					}
					memberMenu = Mideas.joueur1().getGuild().getMemberList().get(hoveredMember);
					if(memberMenu.isOnline()) {
						buildMenuList();
						displayedMemberMenu.setName(memberMenu.getName());
						displayedMemberMenu.setActive(true);
					}
					downMember = -1;
					return true;
				}
			}
		}
		return false;
	}
	
	private static void buildMenuList() {
		if(Mideas.joueur1().getGuild().isLeader(Mideas.joueur1().getId()) && memberMenu.getId() != Mideas.joueur1().getId()) {
			buildMenuListLeader(hoveredMemberX, hoveredMemberY);
		}
		else {
			if(memberMenu.getId() == Mideas.joueur1().getId()) {
				buildMenuListSelf(hoveredMemberX, hoveredMemberY);
			}
			else {
				buildMenuListNonLeader(hoveredMemberX, hoveredMemberY);
			}
		}
	}
	
	private static void buildMenuListSelf(float x, float y) {
		displayedMemberMenu.clearMenu();
		displayedMemberMenu.addMenu(whisperTextMenu);
		displayedMemberMenu.addMenu(targetTextMenu);
		displayedMemberMenu.addMenu(ignoreTextMenu);
		displayedMemberMenu.addMenu(leaveGuildTextMenu);
		displayedMemberMenu.addMenu(cancelTextMenu);
		displayedMemberMenu.updateSize(x, y, true);
	}
	
	private static void buildMenuListLeader(float x, float y) {
		displayedMemberMenu.clearMenu();
		displayedMemberMenu.addMenu(whisperTextMenu);
		displayedMemberMenu.addMenu(targetTextMenu);
		displayedMemberMenu.addMenu(ignoreTextMenu);
		displayedMemberMenu.addMenu(setLeaderTextMenu);
		displayedMemberMenu.addMenu(cancelTextMenu);
		displayedMemberMenu.updateSize(x, y, true);
	}
	
	private static void buildMenuListNonLeader(float x, float y) {
		displayedMemberMenu.clearMenu();
		displayedMemberMenu.addMenu(whisperTextMenu);
		displayedMemberMenu.addMenu(targetTextMenu);
		displayedMemberMenu.addMenu(ignoreTextMenu);
		displayedMemberMenu.addMenu(cancelTextMenu);
		displayedMemberMenu.updateSize(x, y, true);
	}
	
	private static boolean isHoverMember(float x, float y) {
		return Mideas.mouseX() >= x && Mideas.mouseX() <= x+343*Mideas.getDisplayXFactor() && Mideas.mouseY() >= y && Mideas.mouseY() <= y+18*Mideas.getDisplayYFactor();
	}
	
	private static void drawManageFrame() {
		if(manageFrameOpen) {
			informationBackground.draw();
			Draw.drawQuad(Sprites.guild_manage_frame_bot_border, X_SOCIAL_FRAME+405*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+433*Mideas.getDisplayYFactor());
			//Draw.drawQuad(Sprites.guild_manage_rank_horizontal_bar, X_SOCIAL_FRAME+399*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+65*Mideas.getDisplayYFactor());
			manageFrameHorizontalBar.draw();
			FontManager.get("FRIZQT", 13).drawBegin();
			FontManager.get("FRIZQT", 13).drawStringShadowPart(X_SOCIAL_FRAME+555*Mideas.getDisplayXFactor()-FontManager.get("FRIZQT", 13).getWidth("Select guild rank to modify:")/2, Y_SOCIAL_FRAME+20*Mideas.getDisplayYFactor(), "Select guild rank to modify:", YELLOW, Color.BLACK, 1, 0, 0);
			FontManager.get("FRIZQT", 13).drawStringShadowPart(X_SOCIAL_FRAME+485*Mideas.getDisplayXFactor()-FontManager.get("FRIZQT", 13).getWidth("Rank Label:")/2, Y_SOCIAL_FRAME+85*Mideas.getDisplayYFactor(), "Rank Label:", YELLOW, Color.BLACK, 1, 0, 0);
			FontManager.get("FRIZQT", 13).drawStringShadowPart(X_SOCIAL_FRAME+555*Mideas.getDisplayXFactor()-FontManager.get("FRIZQT", 13).getWidth("Allow this rank to:")/2, Y_SOCIAL_FRAME+110*Mideas.getDisplayYFactor(), "Allow this rank to:", Color.WHITE, Color.BLACK, 1, 0, 0);
			FontManager.get("FRIZQT", 13).drawEnd();
			rankNameEditBox.draw();
			canListenGuildChannelCheckBox.draw();
			canListenOfficerChannelCheckBox.draw();
			canPromoteCheckBox.draw();
			canInviteMemberCheckBox.draw();
			canSetMotdCheckBox.draw();
			canSeeOfficerNoteCheckBox.draw();
			canModifyGuildInformationCheckBox.draw();
			canTalkInGuildChannelCheckBox.draw();
			canTalkInOfficerChannelCheckBox.draw();
			canDemoteCheckBox.draw();
			canRemoveMemberCheckBox.draw();
			canEditPublicNoteCheckBox.draw();
			canEditOfficerNoteCheckBox.draw();
			manageRankDropDownMenu.draw();
			manageRankAcceptButton.draw();
			manageRankCloseFrameButton.draw();
		}
	}
	
	private static boolean mouseEventManageFrame() {
		if(!manageFrameOpen) {
			return false;
		}
		if(manageRankDropDownMenu.event()) return true;
		if(rankNameEditBox.mouseEvent()) return true;
		if(manageRankAcceptButton.event()) return true;
		if(manageRankCloseFrameButton.event()) return true;
		if(canListenGuildChannelCheckBox.event()) return true;
		if(canListenOfficerChannelCheckBox.event()) return true;
		if(canPromoteCheckBox.event()) return true;
		if(canInviteMemberCheckBox.event()) return true;
		if(canSetMotdCheckBox.event()) return true;
		if(canSeeOfficerNoteCheckBox.event()) return true;
		if(canModifyGuildInformationCheckBox.event()) return true;
		if(canTalkInGuildChannelCheckBox.event()) return true;
		if(canTalkInOfficerChannelCheckBox.event()) return true;
		if(canDemoteCheckBox.event()) return true;
		if(canRemoveMemberCheckBox.event()) return true;
		if(canEditPublicNoteCheckBox.event()) return true;
		if(canEditOfficerNoteCheckBox.event()) return true;
		return false;
	}
	
	public static void fillDropDownMenuWithRank() {
		int i = 0;
		manageRankDropDownMenu.resetMenuList();
		while(i < Mideas.joueur1().getGuild().getRankList().size()) {
			manageRankDropDownMenu.addMenu(new TextMenu(X_SOCIAL_FRAME+DROP_DOWN_MENU_ALERT_X+20*Mideas.getDisplayXFactor(), 0, (DROP_DOWN_MENU_ALERT_X_SIZE-20)*Mideas.getDisplayXFactor(), Mideas.joueur1().getGuild().getRankList().get(i).getName(), 13f, 1));
			i++;
		}
		rankNameEditBox.setText(selectedRank.getName());
	}
	
	private static void drawSortButton() {
		sortByNameButton.draw();
		sortByRankButton.draw();
		sortByNoteButton.draw();
		sortByLastOnlineButton.draw();
	}
	
	private static boolean mouseEventSortButton() {
		if(sortByNameButton.event()) return true;
		if(sortByRankButton.event()) return true;
		if(sortByNoteButton.event()) return true;
		if(sortByLastOnlineButton.event()) return true;
		return false;
	}
	
	private static void drawDisplayedMember() {
		if(!memberInformationDisplay) {
			return;
		}
		long timer = System.nanoTime();
		float x = X_SOCIAL_FRAME+415*Mideas.getDisplayXFactor();
		float y = Y_SOCIAL_FRAME+45*Mideas.getDisplayYFactor();
		informationBackground.draw();
		noteTooltip.draw();
		Draw.drawQuad(Sprites.guild_close_information_button_border, X_SOCIAL_FRAME+595*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+32*Mideas.getDisplayYFactor());
		closeDisplayMemberFrameCrossButton.draw();
		long time = System.nanoTime();
		FontManager.get("FRIZQT", 13).drawBegin();
		FontManager.get("FRIZQT", 13).drawStringShadowPart(x, y, memberInformationDisplayed.getName(), YELLOW, Color.BLACK, 1, 0, 0);
		y+= (FontManager.get("FRIZQT", 13).getLineHeight())*Mideas.getDisplayYFactor();
		FontManager.get("FRIZQT", 13).drawStringShadowPart(x, y, memberInformationDisplayed.getInformationString(), Color.WHITE, Color.BLACK, 1, 0, 0);
		y+= (FontManager.get("FRIZQT", 13).getLineHeight()+4)*Mideas.getDisplayYFactor();
		FontManager.get("FRIZQT", 13).drawStringShadowPart(x, y, "Area : ", YELLOW, Color.BLACK, 1, 0, 0);
		FontManager.get("FRIZQT", 13).drawStringShadowPart(x+FontManager.get("FRIZQT", 13).getWidth("Area : "), y, "Area", Color.WHITE, Color.BLACK, 1, 0, 0);
		y+= (FontManager.get("FRIZQT", 13).getLineHeight()+2)*Mideas.getDisplayYFactor();
		FontManager.get("FRIZQT", 13).drawStringShadowPart(x, y, "Rank : ", YELLOW, Color.BLACK, 1, 0, 0);
		FontManager.get("FRIZQT", 13).drawStringShadowPart(x+FontManager.get("FRIZQT", 13).getWidth("Rank : "), y, memberInformationDisplayed.getRank().getName(), Color.WHITE, Color.BLACK, 1, 0, 0);
		y+= (FontManager.get("FRIZQT", 13).getLineHeight()+1)*Mideas.getDisplayYFactor();
		FontManager.get("FRIZQT", 13).drawStringShadowPart(x, y, "Last connection : ", YELLOW, Color.BLACK, 1, 0, 0);
		FontManager.get("FRIZQT", 13).drawStringShadowPart(x+FontManager.get("FRIZQT", 13).getWidth("Last connection : "), y, memberInformationDisplayed.getLastLoginTimerString(), Color.WHITE, Color.BLACK, 1, 0, 0);
		y+= (FontManager.get("FRIZQT", 13).getLineHeight()+1)*Mideas.getDisplayYFactor();
		FontManager.get("FRIZQT", 13).drawStringShadowPart(x, y, "Note : ", YELLOW, Color.BLACK, 1, 0, 0);
		long noteTimer = System.nanoTime();
		FontManager.get("FRIZQT", 13).drawStringShadowPart(x+5*Mideas.getDisplayXFactor(), y+19*Mideas.getDisplayYFactor(), memberInformationDisplayed.getNoteDisplayed(), Color.WHITE, Color.BLACK, 1, 0, 0);
		Mideas.nTime(noteTimer, "Note draw time");
		FontManager.get("FRIZQT", 13).drawEnd();
		Mideas.nTime(time, "Member information text draw time");
		if(Mideas.joueur1().getGuildRank().canSeeOfficerNote()) {
			y+= 61*Mideas.getDisplayYFactor();
			FontManager.get("FRIZQT", 13).drawStringShadow(x, y, "Officer note :", YELLOW, Color.BLACK, 1, 0, 0);
			time = System.nanoTime();
			officerNoteTooltip.draw();
			Mideas.nTime(time, "Officer note tooltip draw time");
			FontManager.get("FRIZQT", 13).drawStringShadow(x+5*Mideas.getDisplayXFactor(), y+19*Mideas.getDisplayYFactor(), memberInformationDisplayed.getOfficerNoteDisplayed(), Color.WHITE, Color.BLACK, 1, 0, 0);
			Draw.drawQuad(Sprites.guild_member_display_button_border, X_SOCIAL_FRAME+405*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+263*Mideas.getDisplayYFactor());
		}
		else {
			Draw.drawQuad(Sprites.guild_member_display_button_border, X_SOCIAL_FRAME+405*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+201*Mideas.getDisplayYFactor());
		}
		memberDisplayInviteButton.draw();
		memberDisplayKickButton.draw();
		Mideas.nTime(timer, "Member information draw time");
	}
	
	private static boolean mouseEventDisplayedMember() {
		if(!memberInformationDisplay || memberInformationDisplayed == null) {
			return false;
		}
		if(memberDisplayInviteButton.event()) return true;
		if(memberDisplayKickButton.event()) return true;
		if(closeDisplayMemberFrameCrossButton.event()) return true;
		if(Mouse.getEventButtonState()) {
			if(Mouse.getEventButton() == 0 || Mouse.getEventButton() == 1) {
				if(noteTooltip.isHover()) {
					PopupFrame.activateSetGuildMemberNotePopupInput(memberInformationDisplayed.getId(), memberInformationDisplayed.getNoteSave());
				}
				else if(Mideas.joueur1().getGuildRank().canSeeOfficerNote() && officerNoteTooltip.isHover()) {
					PopupFrame.activateSetGuildMemberOfficerNotePopupInput(memberInformationDisplayed.getId(), memberInformationDisplayed.getOfficerNoteSave());
				}
			}
		}
		return false;
	}
	
	private static void drawInformationFrame() {
		if(displayGuildInformation) {
			if(!inputInit) {
				informationInput.setText(Mideas.joueur1().getGuild().getInformation());
				inputInit = true;
			}
			informationBackground.draw();
			guildInformationTooltip.draw();
			FontManager.get("FRIZQT", 14).drawStringShadow(X_SOCIAL_FRAME+411*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+15*Mideas.getDisplayYFactor(), "General options", YELLOW, Color.BLACK, 1, 0, 0);
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
		float x = X_SOCIAL_FRAME+412*Mideas.getDisplayXFactor();
		int xShift = 0;
		float y = Y_SOCIAL_FRAME+45*Mideas.getDisplayYFactor();
		int yShift = 0;
		float maxLength = 285*Mideas.getDisplayXFactor();
		Color color;
		if(Mideas.joueur1().getGuildRank().canModifyGuildInformation()) {
			color = Color.WHITE;
		}
		else {
			color = GREY;
		}
		while(i < informationInput.getText().length()) {
			if(informationInput.getText().charAt(i) == 13) {
				yShift+= FontManager.get("FRIZQT", 14).getLineHeight();
				xShift = 0;
			}
			else {
				FontManager.get("FRIZQT", 13).drawChar(x+xShift+1, y+yShift, informationInput.getText().charAt(i), Color.BLACK);
				FontManager.get("FRIZQT", 13).drawChar(x+xShift, y+yShift, informationInput.getText().charAt(i), color);
				xShift+= FontManager.get("FRIZQT", 13).getWidth(informationInput.getText().charAt(i));
			}
			if(xShift >= maxLength) {
				xShift = 0;
				yShift+= FontManager.get("FRIZQT", 13).getLineHeight();
			}
			if(informationInput.isActive() && informationInput.getCursorPosition() == i+1 && System.currentTimeMillis()%1000 < 500) {
				Draw.drawColorQuad(x+xShift, y+yShift, 5*Mideas.getDisplayXFactor(), 16*Mideas.getDisplayYFactor(), color);
			}
			i++;
		}
	}
	
	private static void drawNumberMember() {
		float x = X_SOCIAL_FRAME+20*Mideas.getDisplayXFactor();
		float y = Y_SOCIAL_FRAME+342*Mideas.getDisplayYFactor();
		int xShift = 0;
		FontManager.get("FRIZQT", 12).drawBegin();
		FontManager.get("FRIZQT", 12).drawStringShadowPart(x, y, Mideas.joueur1().getGuild().getNumberMember(), Color.WHITE, Color.BLACK, 1, 0, 0);
		xShift+= FontManager.get("FRIZQT", 12).getWidth(Mideas.joueur1().getGuild().getNumberMember());
		FontManager.get("FRIZQT", 12).drawStringShadowPart(x+xShift, y, " Guild Member (", YELLOW, Color.BLACK, 1, 0, 0);
		xShift+= FontManager.get("FRIZQT", 12).getWidth(" Guild Member (");
		FontManager.get("FRIZQT", 12).drawStringShadowPart(x+xShift, y, Mideas.joueur1().getGuild().getNumberOnlineMemberString(), Color.WHITE, Color.BLACK, 1, 0, 0);
		xShift+= FontManager.get("FRIZQT", 12).getWidth(Mideas.joueur1().getGuild().getNumberOnlineMemberString());
		FontManager.get("FRIZQT", 12).drawStringShadowPart(x+xShift, y, " Online ", GREEN, Color.BLACK, 1, 0, 0);
		xShift+= FontManager.get("FRIZQT", 12).getWidth("Online ");
		FontManager.get("FRIZQT", 12).drawStringShadowPart(x+xShift, y, ")", YELLOW, Color.BLACK, 1, 0, 0);
		FontManager.get("FRIZQT", 12).drawEnd();
	}
	
	private static void drawMotd() {
		int i = 0;
		float x = X_SOCIAL_FRAME+23*Mideas.getDisplayXFactor();
		float y = Y_SOCIAL_FRAME+383*Mideas.getDisplayYFactor();
		int xShift = 0;
		float yShift = 0;
		float maxWidth = 325*Mideas.getDisplayXFactor();
		int length = Mideas.joueur1().getGuild().getTempMotd().length();
		Color color;
		if(Mideas.joueur1().getGuildRank().canModifyMotd()) {
			color = Color.WHITE;
		}
		else {
			color = GREY;
		}
		FontManager.get("FRIZQT", 12).drawBegin();
		while(i < length) {
			FontManager.get("FRIZQT", 12).drawCharPart(x+xShift+1, y+yShift, Mideas.joueur1().getGuild().getTempMotd().charAt(i), Color.BLACK);
			FontManager.get("FRIZQT", 12).drawCharPart(x+xShift, y+yShift, Mideas.joueur1().getGuild().getTempMotd().charAt(i), color);
			xShift+= FontManager.get("FRIZQT", 12).getWidth(Mideas.joueur1().getGuild().getTempMotd().charAt(i));
			if(xShift >= maxWidth) {
				yShift+= FontManager.get("FRIZQT", 12).getLineHeight();
				xShift = 0;
			}
			i++;
		}
		FontManager.get("FRIZQT", 12).drawEnd();
	}
	
	public static boolean isInformationActive() {
		return displayGuildInformation;
	}
	
	public static void updateSize() {
		if(!shouldUpdateSize) {
			return;
		}
		infoButton.update(X_SOCIAL_FRAME+17*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+437*Mideas.getDisplayYFactor(), 134*Mideas.getDisplayXFactor(), 23*Mideas.getDisplayYFactor());
		addMemberButton.update(X_SOCIAL_FRAME+158*Mideas.getDisplayXFactor(),Y_SOCIAL_FRAME+437*Mideas.getDisplayYFactor(), 110*Mideas.getDisplayXFactor(), 23*Mideas.getDisplayYFactor());
		manageGuildButton.update(X_SOCIAL_FRAME+274*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+437*Mideas.getDisplayYFactor(), 112*Mideas.getDisplayXFactor(), 23*Mideas.getDisplayYFactor());
		journalButton.update(X_SOCIAL_FRAME+405*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+278*Mideas.getDisplayYFactor(), 65*Mideas.getDisplayXFactor(), 22*Mideas.getDisplayYFactor());
		closeInformationButton.update(X_SOCIAL_FRAME+625*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+274*Mideas.getDisplayYFactor(), 90*Mideas.getDisplayXFactor(), 28*Mideas.getDisplayYFactor());
		acceptInformationButton.update(X_SOCIAL_FRAME+530*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+274*Mideas.getDisplayYFactor(), 90*Mideas.getDisplayXFactor(), 28*Mideas.getDisplayYFactor());
		closeInformationFrameCrossButton.update(X_SOCIAL_FRAME+701*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+10*Mideas.getDisplayYFactor(), 19*Mideas.getDisplayXFactor(), 18*Mideas.getDisplayYFactor());
		showOfflineMembersCheckBox.update(X_SOCIAL_FRAME+353*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+44*Mideas.getDisplayYFactor(), 17*Mideas.getDisplayXFactor(), 15*Mideas.getDisplayYFactor());
		guildInformationTooltip.update(X_SOCIAL_FRAME+403*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+36*Mideas.getDisplayYFactor(), 320*Mideas.getDisplayXFactor(), 242*Mideas.getDisplayYFactor());
		if(displayGuildInformation) {
			informationBackground.update(X_SOCIAL_FRAME+GUILD_INFORMATION_ALERT_BACKGROUND_X*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME, GUILD_INFORMATION_ALERT_BACKGROUND_X_SIZE*Mideas.getDisplayXFactor(), GUILD_INFORMATION_ALERT_BACKGROUND_Y_SIZE*Mideas.getDisplayYFactor());
		}
		else if(memberInformationDisplay) {
			if(Mideas.joueur1().getGuildRank().canSeeOfficerNote()) {
				informationBackground.update(X_SOCIAL_FRAME+MEMBER_INFORMATION_ALERT_BACKGROUND_X*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+MEMBER_INFORMATION_ALERT_BACKGROUND_Y*Mideas.getDisplayYFactor(), MEMBER_INFORMATION_ALERT_BACKGROUND_X_SIZE*Mideas.getDisplayXFactor(), MEMBER_INFORMATION_ALERT_BACKGROUND_OFFICER_Y_SIZE*Mideas.getDisplayYFactor());
				memberDisplayInviteButton.update(X_SOCIAL_FRAME+MEMBER_DISPLAY_INVITE_BUTTON_X*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+MEMBER_DISPLAY_BUTTON_OFFICER_Y*Mideas.getDisplayYFactor(), MEMBER_DISPLAY_BUTTON_X_SIZE*Mideas.getDisplayXFactor(), MEMBER_DISPLAY_BUTTON_Y_SIZE*Mideas.getDisplayYFactor());
				memberDisplayKickButton.update(X_SOCIAL_FRAME+MEMBER_DISPLAY_KICK_BUTTON_X*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+MEMBER_DISPLAY_BUTTON_OFFICER_Y*Mideas.getDisplayYFactor(), MEMBER_DISPLAY_BUTTON_X_SIZE*Mideas.getDisplayXFactor(), MEMBER_DISPLAY_BUTTON_Y_SIZE*Mideas.getDisplayYFactor());
			}
			else {
				informationBackground.update(X_SOCIAL_FRAME+MEMBER_INFORMATION_ALERT_BACKGROUND_X*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+MEMBER_INFORMATION_ALERT_BACKGROUND_Y*Mideas.getDisplayYFactor(), MEMBER_INFORMATION_ALERT_BACKGROUND_X_SIZE*Mideas.getDisplayXFactor(), MEMBER_INFORMATION_ALERT_BACKGROUND_NO_OFFICER_Y_SIZE*Mideas.getDisplayYFactor());
				memberDisplayInviteButton.update(X_SOCIAL_FRAME+MEMBER_DISPLAY_INVITE_BUTTON_X*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+MEMBER_DISPLAY_BUTTON_NO_OFFICER_Y*Mideas.getDisplayYFactor(), MEMBER_DISPLAY_BUTTON_X_SIZE*Mideas.getDisplayXFactor(), MEMBER_DISPLAY_BUTTON_Y_SIZE*Mideas.getDisplayYFactor());
				memberDisplayKickButton.update(X_SOCIAL_FRAME+MEMBER_DISPLAY_KICK_BUTTON_X*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+MEMBER_DISPLAY_BUTTON_NO_OFFICER_Y*Mideas.getDisplayYFactor(), MEMBER_DISPLAY_BUTTON_X_SIZE*Mideas.getDisplayXFactor(), MEMBER_DISPLAY_BUTTON_Y_SIZE*Mideas.getDisplayYFactor());
			}
		}
		else if(manageFrameOpen) {
			informationBackground.update(X_SOCIAL_FRAME+MANAGE_FRAME_ALERT_BACKGROUND_X*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+MANAGE_FRAME_ALERT_BACKGROUND_Y*Mideas.getDisplayYFactor(), MANAGE_FRAME_ALERT_BACKGROUND_X_SIZE*Mideas.getDisplayXFactor(), MANAGE_FRAME_ALERT_BACKGROUND_Y_SIZE*Mideas.getDisplayYFactor());
		}
		noteTooltip.update(X_SOCIAL_FRAME+412*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+153*Mideas.getDisplayYFactor(), 205*Mideas.getDisplayXFactor(), 45*Mideas.getDisplayYFactor());
		officerNoteTooltip.update(X_SOCIAL_FRAME+412*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+213*Mideas.getDisplayYFactor(), 205*Mideas.getDisplayXFactor(), 45*Mideas.getDisplayYFactor());
		closeDisplayMemberFrameCrossButton.update(X_SOCIAL_FRAME+605*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+35*Mideas.getDisplayYFactor(), 19*Mideas.getDisplayXFactor(), 18*Mideas.getDisplayYFactor());
		canListenGuildChannelCheckBox.update(X_SOCIAL_FRAME+MANAGE_FRAME_CHECKBOX_LEFT_SIDE*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+137*Mideas.getDisplayYFactor(), 15*Mideas.getDisplayXFactor(), 13*Mideas.getDisplayYFactor());
		canListenOfficerChannelCheckBox.update(X_SOCIAL_FRAME+MANAGE_FRAME_CHECKBOX_LEFT_SIDE*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+158*Mideas.getDisplayYFactor(), 15*Mideas.getDisplayXFactor(), 13*Mideas.getDisplayYFactor());
		canPromoteCheckBox.update(X_SOCIAL_FRAME+MANAGE_FRAME_CHECKBOX_LEFT_SIDE*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+179*Mideas.getDisplayYFactor(), 15*Mideas.getDisplayXFactor(), 13*Mideas.getDisplayYFactor());
		canInviteMemberCheckBox.update(X_SOCIAL_FRAME+MANAGE_FRAME_CHECKBOX_LEFT_SIDE*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+200*Mideas.getDisplayYFactor(), 15*Mideas.getDisplayXFactor(), 13*Mideas.getDisplayYFactor());
		canSetMotdCheckBox.update(X_SOCIAL_FRAME+MANAGE_FRAME_CHECKBOX_LEFT_SIDE*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+221*Mideas.getDisplayYFactor(), 15*Mideas.getDisplayXFactor(), 13*Mideas.getDisplayYFactor());
		canSeeOfficerNoteCheckBox.update(X_SOCIAL_FRAME+MANAGE_FRAME_CHECKBOX_LEFT_SIDE*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+242*Mideas.getDisplayYFactor(), 15*Mideas.getDisplayXFactor(), 13*Mideas.getDisplayYFactor());
		canModifyGuildInformationCheckBox.update(X_SOCIAL_FRAME+MANAGE_FRAME_CHECKBOX_LEFT_SIDE*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+263*Mideas.getDisplayYFactor(), 15*Mideas.getDisplayXFactor(), 13*Mideas.getDisplayYFactor());
		canTalkInGuildChannelCheckBox.update(X_SOCIAL_FRAME+MANAGE_FRAME_CHECKBOX_RIGHT_SIDE*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+136*Mideas.getDisplayYFactor(), 15*Mideas.getDisplayXFactor(), 13*Mideas.getDisplayYFactor());
		canTalkInOfficerChannelCheckBox.update(X_SOCIAL_FRAME+MANAGE_FRAME_CHECKBOX_RIGHT_SIDE*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+157*Mideas.getDisplayYFactor(), 15*Mideas.getDisplayXFactor(), 13*Mideas.getDisplayYFactor());
		canDemoteCheckBox.update(X_SOCIAL_FRAME+MANAGE_FRAME_CHECKBOX_RIGHT_SIDE*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+178*Mideas.getDisplayYFactor(), 15*Mideas.getDisplayXFactor(), 13*Mideas.getDisplayYFactor());
		canRemoveMemberCheckBox.update(X_SOCIAL_FRAME+MANAGE_FRAME_CHECKBOX_RIGHT_SIDE*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+199*Mideas.getDisplayYFactor(), 15*Mideas.getDisplayXFactor(), 13*Mideas.getDisplayYFactor());
		canEditPublicNoteCheckBox.update(X_SOCIAL_FRAME+MANAGE_FRAME_CHECKBOX_RIGHT_SIDE*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+220*Mideas.getDisplayYFactor(), 15*Mideas.getDisplayXFactor(), 13*Mideas.getDisplayYFactor());
		canEditOfficerNoteCheckBox.update(X_SOCIAL_FRAME+MANAGE_FRAME_CHECKBOX_RIGHT_SIDE*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+241*Mideas.getDisplayYFactor(), 15*Mideas.getDisplayXFactor(), 13*Mideas.getDisplayYFactor());
		memberScrollBar.update(X_SOCIAL_FRAME+358*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+100*Mideas.getDisplayYFactor(), 240*Mideas.getDisplayYFactor(), 365*Mideas.getDisplayXFactor(), 260*Mideas.getDisplayYFactor(), 18*Mideas.getDisplayYFactor());
		manageRankDropDownMenu.update(X_SOCIAL_FRAME+DROP_DOWN_MENU_BAR_X*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+DROP_DOWN_MENU_BAR_Y*Mideas.getDisplayYFactor(), DROP_DOWN_MENU_BAR_X_SIZE*Mideas.getDisplayXFactor(), X_SOCIAL_FRAME+DROP_DOWN_MENU_ALERT_X*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+DROP_DOWN_MENU_ALERT_Y*Mideas.getDisplayXFactor(), DROP_DOWN_MENU_ALERT_X_SIZE*Mideas.getDisplayXFactor());
		rankNameEditBox.update(X_SOCIAL_FRAME+530*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+85*Mideas.getDisplayYFactor());
		manageRankAcceptButton.update(X_SOCIAL_FRAME+537*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+435*Mideas.getDisplayYFactor(), 88*Mideas.getDisplayXFactor(), 24*Mideas.getDisplayYFactor());
		manageRankCloseFrameButton.update(X_SOCIAL_FRAME+630*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+435*Mideas.getDisplayYFactor(), 84*Mideas.getDisplayXFactor(), 24*Mideas.getDisplayYFactor());
		sortByNameButton.update(X_SOCIAL_FRAME+20*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+BUTTON_MENU_SORT_Y*Mideas.getDisplayYFactor(), 94*Mideas.getDisplayXFactor());
		sortByRankButton.update(X_SOCIAL_FRAME+113*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+BUTTON_MENU_SORT_Y*Mideas.getDisplayYFactor(), 86*Mideas.getDisplayXFactor());
		sortByNoteButton.update(X_SOCIAL_FRAME+198*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+BUTTON_MENU_SORT_Y*Mideas.getDisplayYFactor(), 100*Mideas.getDisplayXFactor());
		sortByLastOnlineButton.update(X_SOCIAL_FRAME+296*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+BUTTON_MENU_SORT_Y*Mideas.getDisplayYFactor(), 89*Mideas.getDisplayXFactor());
		if(displayedMemberMenu.isActive()) {
			displayedMemberMenu.updateSize();
		}
		shouldUpdateSize = false;
	}
	
	public static void shouldUpdate() {
		shouldUpdateSize = true;
	}
	
	public static Input getInformationInput() {
		return informationInput;
	}
	
	static void closeGuildInformationFrame() {
		displayGuildInformation = false;
		informationInput.setIsActive(false);
		informationInput.setText(Mideas.joueur1().getGuild().getInformation());
	}
	
	static void closeMemberInformationFrame() {
		memberInformationDisplay = false;
		memberInformationDisplayed = null;
	}
	
	static void closeManageFrame() {
		manageFrameOpen = false;
		if(selectedRank != null) {
			selectedRank.resetTempRank();
			rankNameEditBox.setText(selectedRank.getName());
			//rankNameInput.setText(selectedRank.getName());
		}
		//rankNameInput.setIsActive(false);
		manageFrameChangeMade = false;
		manageRankCloseFrameButton.disable();
		manageRankAcceptButton.disable();
		rankNameEditBox.setActive(false);
		manageRankDropDownMenu.setActive(false);
	}
	
	static void openManageFrame() {
		manageFrameOpen = true;
		//rankNameInput.setText(selectedRank.getName());
		manageRankCloseFrameButton.enable();
		manageRankAcceptButton.enable();
		manageRankDropDownMenu.setActive(true);
		if(selectedRank != null) {
			manageRankDropDownMenu.setMenuText(selectedRank.getName());
			manageRankDropDownMenu.setValue(selectedRank.getOrder());
		}
	}
	
	public static void resetFrame() {
		closeManageFrame();
		closeMemberInformationFrame();
		closeGuildInformationFrame();
	}
}
