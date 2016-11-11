package com.mideas.rpg.v2.game.guild;

import java.util.Arrays;

public class GuildRank {

	private int order;
	private int permission;
	private boolean[] permissionList;
	private boolean[] tempPermissionList;
	private String name;
	
	//Permission index in the table
	public final static int CAN_LISTEN_GUILD_CHANNEL = 0;
	public final static int CAN_LISTEN_OFFICER_CHANNEL = 1;
	public final static int CAN_PROMOTE = 2;
	public final static int CAN_INVITE_MEMBER = 3;
	public final static int CAN_SET_MOTD = 4;
	public final static int CAN_SEE_OFFICER_NOTE = 5;
	public final static int CAN_MODIFY_GUILD_INFORMATION = 6;
	public final static int CAN_TALK_GUILD_CHANNEL = 7;
	public final static int CAN_TALK_OFFICER_CHANNEL = 8;
	public final static int CAN_DEMOTE = 9;
	public final static int CAN_KICK_MEMBER = 10;
	public final static int CAN_EDIT_PUBLIC_NOTE = 11;
	public final static int CAN_EDIT_OFFICER_NOTE = 12;
	public final static int CAN_WITHDRAW_GOLD = 13;
	public final static int CAN_USE_GOLD_REPARATION = 14;
	
	public GuildRank(int order, int permission, String name) {
		this.order = order;
		this.permission = permission;
		this.name = name;
		this.permissionList = new boolean[15];
		this.tempPermissionList = new boolean[15];
		parsePermission();
		Arrays.fill(this.permissionList, true);
	}
	
	private void parsePermission() {
		int i = 0;
		while(i < this.permissionList.length) {
			if((this.permission & (1 << i)) != 0) {
				this.permissionList[i] = true;
				this.tempPermissionList[i] = true;
			}
			i++;
		}
	}
	
	public boolean canListenGuildChannel() {
		return this.permissionList[CAN_LISTEN_GUILD_CHANNEL];
	}
	
	public boolean canListenOfficerChannel() {
		return this.permissionList[CAN_LISTEN_OFFICER_CHANNEL];
	}
	
	public boolean canPromote() {
		return this.permissionList[CAN_PROMOTE];
	}
	
	public boolean canInvitePlayer() {
		return this.permissionList[CAN_INVITE_MEMBER];
	}
	
	public boolean canModifyMotd() {
		return this.permissionList[CAN_SET_MOTD];
	}
	
	public boolean canSeeOfficerNote() {
		return this.permissionList[CAN_SEE_OFFICER_NOTE];
	}
	
	public boolean canModifyGuildInformation() {
		return this.permissionList[CAN_MODIFY_GUILD_INFORMATION];
	}
	
	public boolean canTalkInGuildChannel() {
		return this.permissionList[CAN_TALK_GUILD_CHANNEL];
	}
	
	public boolean canTalkInOfficerChannel() {
		return this.permissionList[CAN_TALK_OFFICER_CHANNEL];
	}
	
	public boolean canDemote() {
		return this.permissionList[CAN_DEMOTE];
	}
	
	public boolean canKickMember() {
		return this.permissionList[CAN_KICK_MEMBER];
	}
	
	public boolean canEditPublicNote() {
		return this.permissionList[CAN_EDIT_PUBLIC_NOTE];
	}
	
	public boolean canEditOfficerNote() {
		return this.permissionList[CAN_EDIT_OFFICER_NOTE];
	}
	
	public boolean canWithdrawGold() {
		return this.permissionList[CAN_WITHDRAW_GOLD];
	}
	
	public boolean canUseGoldForReparation() {
		return this.permissionList[CAN_USE_GOLD_REPARATION];
	}

	
	public boolean canTempListenGuildChannel() {
		return this.tempPermissionList[CAN_LISTEN_GUILD_CHANNEL];
	}
	
	public boolean canTempListenOfficerChannel() {
		return this.tempPermissionList[CAN_LISTEN_OFFICER_CHANNEL];
	}
	
	public boolean canTempPromote() {
		return this.tempPermissionList[CAN_PROMOTE];
	}
	
	public boolean canTempInvitePlayer() {
		return this.tempPermissionList[CAN_INVITE_MEMBER];
	}
	
	public boolean canTempModifyMotd() {
		return this.tempPermissionList[CAN_SET_MOTD];
	}
	
	public boolean canTempSeeOfficerNote() {
		return this.tempPermissionList[CAN_SEE_OFFICER_NOTE];
	}
	
	public boolean canTempModifyGuildInformation() {
		return this.tempPermissionList[CAN_MODIFY_GUILD_INFORMATION];
	}
	
	public boolean canTempTalkInGuildChannel() {
		return this.tempPermissionList[CAN_TALK_GUILD_CHANNEL];
	}
	
	public boolean canTempTalkInOfficerChannel() {
		return this.tempPermissionList[CAN_TALK_OFFICER_CHANNEL];
	}
	
	public boolean canTempDemote() {
		return this.tempPermissionList[CAN_DEMOTE];
	}
	
	public boolean canTempKickMember() {
		return this.tempPermissionList[CAN_KICK_MEMBER];
	}
	
	public boolean canTempEditPublicNote() {
		return this.tempPermissionList[CAN_EDIT_PUBLIC_NOTE];
	}
	
	public boolean canTempEditOfficerNote() {
		return this.tempPermissionList[CAN_EDIT_OFFICER_NOTE];
	}
	
	public boolean canTempWithdrawGold() {
		return this.tempPermissionList[CAN_WITHDRAW_GOLD];
	}
	
	public boolean canTempUseGoldForReparation() {
		return this.tempPermissionList[CAN_USE_GOLD_REPARATION];
	}
	
	public void setTempPermission(int index, boolean we) {
		this.tempPermissionList[index] = we;
	}
	public void cancelTempModification() {
		this.tempPermissionList = this.permissionList;
	}
	
	public int getOrder() {
		return this.order;
	}
	
	public void setOrder(int order) {
		this.order = order;
	}
	
	public int getPermission() {
		return this.permission;
	}
	
	public void setPermission(int permission) {
		if(this.permission != permission) {
			this.permission = permission;
			parsePermission();
		}
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}
