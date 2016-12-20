package com.mideas.rpg.v2;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import org.newdawn.slick.util.ResourceLoader;

public class FontManager {

	//public static TTF buttonFont;
	//public static TTF spellName;
	//public static TTF hpAndMana;
	//public static TTF playerName;
	//public static TTF characterFrameStats;
	public static TTF font;
	public static TTF font2;
	public static TTF font3;
	//public static TTF font4;
	public static TTF font5;
	//public static TTF coin;
	//public static TTF coinContainer;
	//public static TTF statsName;
	//public static TTF itemName;
	//public static TTF itemNumber;
	//public static TTF talent;
	public static TTF chat;
	//public static TTF loginScreenAccount;
	//public static TTF loginScreenPassword;
	public static TTF loginScreenTick;
	//public static TTF selectScreenName;
	//public static TTF selectScreenLevel;
	//public static TTF raceName;
	//public static TTF selectScreenDeleteCharacterConfirm;
	//public static TTF alertFont;
	//public static TTF craft;
	//public static TTF checkBox;
	//public static TTF realmList;
	//public static TTF friendName;
	//public static TTF friendInfos;
	//public static TTF portraitPartyName;
	//public static TTF portraitPartyStats;
	//public static TTF partyFrameTooltip;
	//public static TTF addingFriendInput;
	//public static TTF addingFriendTitle;
	//public static TTF guildTitle;
	//public static TTF guildMember;
	//public static TTF guildMotd;
	//public static TTF guildInformationTitle;
	/*public static TTF guildInformationText;
	public static TTF guildMemberInformationName;
	public static TTF guildMemberInformationRank;
	public static TTF guildMemberInformationLevel;
	public static TTF guildMemberInformationNote;
	public static TTF popup;
	public static TTF ignoreName;*/
	private static HashMap<String, HashMap<Float, TTF>> fontMap = new HashMap<String, HashMap<Float, TTF>>();
	
	public static void init() throws FontFormatException, IOException {
		fontMap.put("FRIZQT", new HashMap<Float, TTF>());
		fontMap.put("ARIALN", new HashMap<Float, TTF>());
		InputStream inputStream = ResourceLoader.getResourceAsStream("sprite/police/MORPHEUS.TTF");
		Font awtFont = Font.createFont(Font.TRUETYPE_FONT, inputStream);
		awtFont = awtFont.deriveFont(18f);
		font = new TTF(awtFont, true);
	    
		awtFont = new Font("MORPHEUS", Font.BOLD, 11);
		font2 = new TTF(awtFont, true);
    
		awtFont = new Font("MORPHEUS", Font.BOLD, 20);
		font3 = new TTF(awtFont, true);

		inputStream = ResourceLoader.getResourceAsStream("sprite/police/FRIZQT__.TTF");
		awtFont = Font.createFont(Font.TRUETYPE_FONT, inputStream);
		int i = 9;
		while(i < 40) {
			fontMap.get("FRIZQT").put((float)i, new TTF(awtFont.deriveFont((float)i), true));
			i++;
		}
		inputStream = ResourceLoader.getResourceAsStream("sprite/police/ARIALN.TTF");
		awtFont = Font.createFont(Font.TRUETYPE_FONT, inputStream);
		i = 9;
		while(i < 30) {
			fontMap.get("ARIALN").put((float)i, new TTF(awtFont.deriveFont((float)i), true));
			i++;
		}
	    
		/*inputStream = ResourceLoader.getResourceAsStream("sprite/police/FRIZQT__.TTF");
		awtFont = Font.createFont(Font.TRUETYPE_FONT, inputStream);
		awtFont = awtFont.deriveFont(16f);
		font4 = new TTF(awtFont, true);*/
	    
		inputStream = ResourceLoader.getResourceAsStream("sprite/police/ARIALN.TTF");
		awtFont = Font.createFont(Font.TRUETYPE_FONT, inputStream).deriveFont(15f);
		chat = new TTF(awtFont, true);
	    
		/*inputStream = ResourceLoader.getResourceAsStream("sprite/police/FRIZQT__.TTF");
		awtFont = Font.createFont(Font.TRUETYPE_FONT, inputStream).deriveFont(13f);
		playerName = new TTF(awtFont, true);*/
	    
		/*inputStream = ResourceLoader.getResourceAsStream("sprite/police/FRIZQT__.TTF");
		awtFont = Font.createFont(Font.TRUETYPE_FONT, inputStream).deriveFont(11f);
		hpAndMana = new TTF(awtFont, true);*/
	    
		awtFont = new Font("MORPHEUS", Font.BOLD, 20);
		font5 = new TTF(awtFont, true);

		/*inputStream = ResourceLoader.getResourceAsStream("sprite/police/FRIZQT__.TTF");
		awtFont = Font.createFont(Font.TRUETYPE_FONT, inputStream).deriveFont(13f);
		buttonFont = new TTF(awtFont, true);*/

		/*inputStream = ResourceLoader.getResourceAsStream("sprite/police/FRIZQT__.TTF");
		awtFont = Font.createFont(Font.TRUETYPE_FONT, inputStream).deriveFont(21f);
		spellName = new TTF(awtFont, true);*/

		/*inputStream = ResourceLoader.getResourceAsStream("sprite/police/FRIZQT__.TTF");
		awtFont = Font.createFont(Font.TRUETYPE_FONT, inputStream).deriveFont(15f);
		statsName= new TTF(awtFont, true);*/
		
		/*inputStream = ResourceLoader.getResourceAsStream("sprite/police/FRIZQT__.TTF");
		awtFont = Font.createFont(Font.TRUETYPE_FONT, inputStream).deriveFont(20f);
		itemName = new TTF(awtFont, true);*/

		/*inputStream = ResourceLoader.getResourceAsStream("sprite/police/FRIZQT__.TTF");
		awtFont = Font.createFont(Font.TRUETYPE_FONT, inputStream).deriveFont(11f);
		coin = new TTF(awtFont, true);*/
	    
		/*inputStream = ResourceLoader.getResourceAsStream("sprite/police/FRIZQT__.TTF");
		awtFont = Font.createFont(Font.TRUETYPE_FONT, inputStream).deriveFont(18f).deriveFont(Font.BOLD);
		coinContainer = new TTF(awtFont, true);*/
	    
		/*inputStream = ResourceLoader.getResourceAsStream("sprite/police/FRIZQT__.TTF");
		awtFont = Font.createFont(Font.TRUETYPE_FONT, inputStream).deriveFont(13f).deriveFont(Font.BOLD);
		itemNumber = new TTF(awtFont, true);*/
	    
		/*inputStream = ResourceLoader.getResourceAsStream("sprite/police/FRIZQT__.TTF");
		awtFont = Font.createFont(Font.TRUETYPE_FONT, inputStream).deriveFont(14f);
		talent = new TTF(awtFont, true);*/

		/*inputStream = ResourceLoader.getResourceAsStream("sprite/police/FRIZQT__.TTF");
		awtFont = Font.createFont(Font.TRUETYPE_FONT, inputStream).deriveFont(12f).deriveFont(Font.BOLD);
		characterFrameStats = new TTF(awtFont, true);*/
		
		/*inputStream = ResourceLoader.getResourceAsStream("sprite/police/FRIZQT__.TTF");
		awtFont = Font.createFont(Font.TRUETYPE_FONT, inputStream).deriveFont(21f);
		loginScreenAccount = new TTF(awtFont, true);*/

		/*inputStream = ResourceLoader.getResourceAsStream("sprite/police/FRIZQT__.TTF");
		awtFont = Font.createFont(Font.TRUETYPE_FONT, inputStream).deriveFont(16f);
		loginScreenPassword = new TTF(awtFont, true);*/
		
		inputStream = ResourceLoader.getResourceAsStream("sprite/police/FRIZQT__.TTF");
		awtFont = Font.createFont(Font.TRUETYPE_FONT, inputStream).deriveFont(30f).deriveFont(Font.BOLD);
		loginScreenTick = new TTF(awtFont, true);
		
		/*inputStream = ResourceLoader.getResourceAsStream("sprite/police/FRIZQT__.TTF");
		awtFont = Font.createFont(Font.TRUETYPE_FONT, inputStream).deriveFont(22f);
		selectScreenName = new TTF(awtFont, true);*/
		
		/*inputStream = ResourceLoader.getResourceAsStream("sprite/police/FRIZQT__.TTF");
		awtFont = Font.createFont(Font.TRUETYPE_FONT, inputStream).deriveFont(16f);
		selectScreenLevel = new TTF(awtFont, true);*/
		
		/*inputStream = ResourceLoader.getResourceAsStream("sprite/police/FRIZQT__.TTF");
		awtFont = Font.createFont(Font.TRUETYPE_FONT, inputStream).deriveFont(15f);
		raceName = new TTF(awtFont, true);*/

		/*inputStream = ResourceLoader.getResourceAsStream("sprite/police/FRIZQT__.TTF");
		awtFont = Font.createFont(Font.TRUETYPE_FONT, inputStream).deriveFont(21f);
		selectScreenDeleteCharacterConfirm = new TTF(awtFont, true);*/
		
		/*inputStream = ResourceLoader.getResourceAsStream("sprite/police/FRIZQT__.TTF");
		awtFont = Font.createFont(Font.TRUETYPE_FONT, inputStream).deriveFont(22f);
		alertFont = new TTF(awtFont, true);*/
		
		/*inputStream = ResourceLoader.getResourceAsStream("sprite/police/FRIZQT__.TTF");
		awtFont = Font.createFont(Font.TRUETYPE_FONT, inputStream).deriveFont(13f);
		craft = new TTF(awtFont, true);*/

		/*inputStream = ResourceLoader.getResourceAsStream("sprite/police/FRIZQT__.TTF");
		awtFont = Font.createFont(Font.TRUETYPE_FONT, inputStream).deriveFont(13f);
		checkBox = new TTF(awtFont, true);*/

		/*inputStream = ResourceLoader.getResourceAsStream("sprite/police/FRIZQT__.TTF");
		awtFont = Font.createFont(Font.TRUETYPE_FONT, inputStream).deriveFont(18f);
		realmList = new TTF(awtFont, true);*/

		/*inputStream = ResourceLoader.getResourceAsStream("sprite/police/FRIZQT__.TTF");
		awtFont = Font.createFont(Font.TRUETYPE_FONT, inputStream).deriveFont(15f);
		friendName = new TTF(awtFont, true);*/
		
		/*inputStream = ResourceLoader.getResourceAsStream("sprite/police/FRIZQT__.TTF");
		awtFont = Font.createFont(Font.TRUETYPE_FONT, inputStream).deriveFont(12f);
		friendInfos = new TTF(awtFont, true);*/
		
		/*inputStream = ResourceLoader.getResourceAsStream("sprite/police/FRIZQT__.TTF");
		awtFont = Font.createFont(Font.TRUETYPE_FONT, inputStream).deriveFont(13f);
		portraitPartyName = new TTF(awtFont, true);*/
		
		/*inputStream = ResourceLoader.getResourceAsStream("sprite/police/FRIZQT__.TTF");
		awtFont = Font.createFont(Font.TRUETYPE_FONT, inputStream).deriveFont(9f);
		portraitPartyStats = new TTF(awtFont, true);*/
		
		/*inputStream = ResourceLoader.getResourceAsStream("sprite/police/ARIALN.TTF");
		awtFont = Font.createFont(Font.TRUETYPE_FONT, inputStream).deriveFont(14f);
		partyFrameTooltip = new TTF(awtFont, true);*/
		
		/*inputStream = ResourceLoader.getResourceAsStream("sprite/police/ARIALN.TTF");
		awtFont = Font.createFont(Font.TRUETYPE_FONT, inputStream).deriveFont(15f);
		addingFriendInput = new TTF(awtFont, true);*/
		
		/*inputStream = ResourceLoader.getResourceAsStream("sprite/police/FRIZQT__.TTF");
		awtFont = Font.createFont(Font.TRUETYPE_FONT, inputStream).deriveFont(14f);
		addingFriendTitle = new TTF(awtFont, true);*/
		
		/*inputStream = ResourceLoader.getResourceAsStream("sprite/police/FRIZQT__.TTF");
		awtFont = Font.createFont(Font.TRUETYPE_FONT, inputStream).deriveFont(15f);
		guildTitle = new TTF(awtFont, true);*/
		
		/*inputStream = ResourceLoader.getResourceAsStream("sprite/police/FRIZQT__.TTF");
		awtFont = Font.createFont(Font.TRUETYPE_FONT, inputStream).deriveFont(13f);
		guildMember = new TTF(awtFont, true);*/
		
		/*inputStream = ResourceLoader.getResourceAsStream("sprite/police/FRIZQT__.TTF");
		awtFont = Font.createFont(Font.TRUETYPE_FONT, inputStream).deriveFont(12f);
		guildMotd = new TTF(awtFont, true);*/
		
		/*inputStream = ResourceLoader.getResourceAsStream("sprite/police/FRIZQT__.TTF");
		awtFont = Font.createFont(Font.TRUETYPE_FONT, inputStream).deriveFont(14f);
		guildInformationTitle = new TTF(awtFont, true);*/
		
		/*inputStream = ResourceLoader.getResourceAsStream("sprite/police/FRIZQT__.TTF");
		awtFont = Font.createFont(Font.TRUETYPE_FONT, inputStream).deriveFont(13f);
		guildInformationText = new TTF(awtFont, true);
		
		inputStream = ResourceLoader.getResourceAsStream("sprite/police/FRIZQT__.TTF");
		awtFont = Font.createFont(Font.TRUETYPE_FONT, inputStream).deriveFont(13f);
		guildMemberInformationName = new TTF(awtFont, true);
		
		inputStream = ResourceLoader.getResourceAsStream("sprite/police/FRIZQT__.TTF");
		awtFont = Font.createFont(Font.TRUETYPE_FONT, inputStream).deriveFont(13f);
		guildMemberInformationRank = new TTF(awtFont, true);
		
		inputStream = ResourceLoader.getResourceAsStream("sprite/police/FRIZQT__.TTF");
		awtFont = Font.createFont(Font.TRUETYPE_FONT, inputStream).deriveFont(13f);
		guildMemberInformationLevel = new TTF(awtFont, true);
		
		inputStream = ResourceLoader.getResourceAsStream("sprite/police/FRIZQT__.TTF");
		awtFont = Font.createFont(Font.TRUETYPE_FONT, inputStream).deriveFont(13f);
		guildMemberInformationNote = new TTF(awtFont, true);
		
		inputStream = ResourceLoader.getResourceAsStream("sprite/police/FRIZQT__.TTF");
		awtFont = Font.createFont(Font.TRUETYPE_FONT, inputStream).deriveFont(13f);
		popup = new TTF(awtFont, true);
		
		inputStream = ResourceLoader.getResourceAsStream("sprite/police/FRIZQT__.TTF");
		awtFont = Font.createFont(Font.TRUETYPE_FONT, inputStream).deriveFont(13f);
		ignoreName = new TTF(awtFont, true);*/
	}
	
	public static TTF get(String font, float size) {
		return fontMap.get(font).get(size);
	}
}
