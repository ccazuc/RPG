package com.mideas.rpg.v2.connection;

public class PacketID {
	
	public final static short LOGIN = 1;
	public final static short LOGOUT = 2;
	public final static short ALREADY_LOGGED = 3;
	public final static short LOGIN_ACCEPT = 4;
	public final static short ACCOUNT_BANNED_TEMP = 5;
	public final static short ACCOUNT_BANNED_PERM = 6;
	public final static short LOGIN_WRONG = 7;
	public final static short SELECT_SCREEN_LOAD_CHARACTERS = 8;
	public final static short CREATE_CHARACTER = 9;
	public final static short ERROR_NAME_ALPHABET = 10;
	public final static short ERROR_NAME_ALREADY_TAKEN = 11;
	public final static short ERROR_NAME_LENGTH = 12;
	public final static short CHARACTER_CREATED = 13;
	public final static short DELETE_CHARACTER = 14;
	public final static short LOAD_CHARACTER = 15;
	public final static short LOAD_EQUIPPED_ITEMS = 16;
	public final static short LOAD_BAG_ITEMS = 17;
	public final static short LOAD_SPELLBAR = 18;
	public final static short STUFF = 19;
	public final static short WEAPON = 20;
	public final static short GEM = 21;
	public final static short POTION = 22;
	public final static short SEND_EQUIPPED_ITEMS = 23;
	public final static short SEND_BAG_ITEMS = 24;
	public final static short PING = 25;
	public final static short PING_CONFIRMED = 26;
	public final static short SEND_SINGLE_BAG_ITEM = 27;
	public final static short SEND_SINGLE_EQUIPPED_ITEM = 28;
	public final static short SEND_SINGLE_SPELLBAR_ITEM = 29;
	public final static short LOAD_STATS = 30;
	public final static short ADD_ITEM = 31;
	public final static short ADD_ITEM_CONFIRMED = 32;
	public final static short REQUEST_ITEM = 33;
	public final static short KNOWN_ITEM = 34;
	public final static short UNKNOWN_ITEM = 35;
	public final static short CHAT_LIST_PLAYER = 36;
	public final static short CHAT_SET_STAMINA = 37;
	public final static short CHAT_SET_MANA = 38;
	public final static short CHAT_PLAYER_INFO = 39;
	public final static short CHAT_NOT_ALLOWED = 40;
	public final static short CHAT_SET = 41;
	public final static short CHAT_SET_GOLD = 42;
	public final static short CHAT_SET_EXPERIENCE = 43;
	public final static short CHAT_GET = 44;
	public final static short CHAT_GET_STAMINA = 45;
	public final static short CHAT_GET_MANA = 46;
	public final static short CHAT_GET_GOLD = 47;
	public final static short CHAT_GET_EXPERIENCE = 48;
	public final static short CHAT_GET_ID = 49;
	public final static short CHAT_GET_IP = 50;
	public final static short STRING = 51;
	public final static short INT = 52;
	public final static short SPELL_CAST = 53;
	public final static short UPDATE_STATS = 54;
	public final static short UPDATE_STATS_STAMINA = 55;
	public final static short UPDATE_STATS_MANA = 56;
	public final static short CHARACTER_LOGOUT = 57;
	public final static short UPDATE_STATS_EXPERIENCE = 58;
	public final static short UPDATE_STATS_GOLD = 59;
	public final static short TRADE = 60;
	public final static short TRADE_NEW = 61;
	public final static short TRADE_ADD_ITEM = 62;
	public final static short TRADE_ACCEPT = 63;
	public final static short TRADE_NEW_CONFIRM = 64;
	public final static short TRADE_REQUEST = 65;
	public final static short TRADE_CLOSE = 66;
	public final static short TRADE_UNACCEPT = 67;
	public final static short TRADE_REMOVE_ITEM = 68;
	public final static short TRADE_ADD_ITEM_ERROR = 69;
	public final static short TRADE_SEND_ALL_ITEMS = 70;
	public final static short FRIEND = 71;
	public final static short FRIEND_SEND_INFO = 72;
	public final static short FRIEND_ADD = 73;
	public final static short LOGIN_NEW_KEY = 74;
	public final static short REGISTER_WORLD_SERVER = 75;
	public final static short SEND_REALM_LIST = 76;
	public final static short LOGIN_REALM = 77;
	public final static short LOGIN_REALM_ACCEPTED = 78;
	public final static short LOGIN_REALM_REQUEST = 79;
	public final static short LOGIN_REALM_SUCCESS = 80;
	public final static short FRIEND_OFFLINE = 81;
	public final static short FRIEND_ONLINE = 82;
	public final static short FRIEND_REMOVE = 83;
	public final static short PLAYER_NOT_FOUND = 84;
	public final static short SEND_MESSAGE = 85;
	public final static short PARTY = 86;
	public final static short PARTY_ADD_MEMBER = 87;
	public final static short PARTY_DECLINE_REQUEST = 88;
	public final static short PARTY_ACCEPT_REQUEST = 89;
	public final static short PARTY_MEMBER_JOINED = 90;
	public final static short PARTY_NEW = 91;
	public final static short PARTY_DISBAND = 92;
	public final static short PARTY_LEFT = 93;
	public final static short PARTY_MEMBER_LEFT = 94;
	public final static short PARTY_SET_LEADER = 95;
	public final static short PARTY_KICK_PLAYER = 96;
	public final static short CONTAINER = 97;
	public final static short FRIEND_LOAD_ALL = 98;
	public final static short GUILD = 99;
	public final static short GUILD_UPDATE_PERMISSION = 100;
	public final static short GUILD_INVITE_PLAYER = 101;
	public final static short GUILD_INIT = 102;
	public final static short GUILD_ACCEPT_REQUEST = 103;
	public final static short GUILD_DECLINE_REQUEST = 104;
	public final static short GUILD_NEW_MEMBER = 105;
	public final static short GUILD_KICK_MEMBER = 106;
	public final static short GUILD_ONLINE_PLAYER = 107;
	public final static short GUILD_OFFLINE_PLAYER = 108;
	public final static short GUILD_SET_INFORMATION = 109;
	public final static short GUILD_SET_MOTD = 110;
	public final static short GUILD_PROMOTE_PLAYER = 111;
	public final static short GUILD_DEMOTE_PLAYER = 112;
	public final static short GUILD_LEAVE = 113;
	public final static short GUILD_SET_LEADER = 114;
	public final static short GUILD_MEMBER_LEFT = 115;
	public final static short GUILD_SET_MEMBER_NOTE = 116;
	public final static short GUILD_SET_MEMBER_OFFICER_NOTE = 117;
	public final static short GUILD_SET_JOURNAL = 118;
	public final static short IGNORE = 119;
	public final static short IGNORE_INIT = 120;
	public final static short IGNORE_ADD = 121;
	public final static short IGNORE_REMOVE = 122;
	public final static short PLAYER_LOGGED_WORLD_SERVER = 123;
	public final static short WHO = 124;
	public final static short WHO_CHAT = 125;
	public final static short CHAT_DEFAULT_MESSAGE = 126;
	public final static short LOGIN_REALM_DOESNT_ACCEPT_CONNECTION = 127;
	public final static short DRAG_ITEM = 128;
	public final static short SET_ITEM = 129;
	public final static short SET_ITEM_NULL = 130;
	public final static short SET_ITEM_AMOUNT = 131;
	public final static short SET_ITEM_SWAP = 132;
	public final static short SET_ITEM_SELECTABLE = 133;
	public final static short SET_ITEM_ADD = 134;
	public final static short SET_ITEM_DRAGGED_ITEM = 135;
	public final static short SEND_RED_ALERT = 136;
	public final static short DELETE_ITEM = 137;
	public final static short PLAYER_LOGGED_OUT = 138;
	public final static short SPELL_CAST_SUCCEED = 139;
	public final static short SPELL_CAST_START = 140;
	public final static short SPELL_CAST_REQUEST = 141;
	public final static short SEND_GCD = 142;
	public final static short SEND_SPELL_CD = 143;
	public final static short SEND_TARGET = 144;
}
