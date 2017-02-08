package com.mideas.rpg.v2.hud.auction;

import java.util.ArrayList;

import com.mideas.rpg.v2.FontManager;
import com.mideas.rpg.v2.Interface;
import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.Sprites;
import com.mideas.rpg.v2.TTF;
import com.mideas.rpg.v2.command.item.CommandAuction;
import com.mideas.rpg.v2.game.auction.AuctionEntry;
import com.mideas.rpg.v2.game.auction.AuctionHouseFilter;
import com.mideas.rpg.v2.game.auction.AuctionHouseQualityFilter;
import com.mideas.rpg.v2.game.auction.AuctionHouseSort;
import com.mideas.rpg.v2.utils.Button;
import com.mideas.rpg.v2.utils.ButtonMenuSort;
import com.mideas.rpg.v2.utils.Color;
import com.mideas.rpg.v2.utils.Draw;
import com.mideas.rpg.v2.utils.DropDownMenu;
import com.mideas.rpg.v2.utils.EditBox;
import com.mideas.rpg.v2.utils.Input;
import com.mideas.rpg.v2.utils.ScrollBar;
import com.mideas.rpg.v2.utils.TextMenu;

public class AuctionFrameUI {


	private final short X_FRAME = 18;
	private final short Y_FRAME = 118;
	private short x_frame = (short)(this.X_FRAME*Mideas.getDisplayXFactor());
	private short y_frame = (short)(this.Y_FRAME*Mideas.getDisplayYFactor());
	final byte BROWSE_MAXIMUM_ITEM_DISPLAYED = 8;
	
	private final short BROWSE_FILTER_SCROLLBAR_X = 153;
	private final short BROWSE_FILTER_SCROLLBAR_WIDTH = 170;
	private final short BROWSE_FILTER_SCROLLBAR_HEIGHT = 290;
	private final short BROWSE_FILTER_SCROLLBAR_Y = 100;
	private final short BROWSE_ITEM_SCROLLBAR_X = 153;
	private final short BROWSE_ITEM_SCROLLBAR_WIDTH = 170;
	private final short BROWSE_ITEM_SCROLLBAR_HEIGHT = 290;
	private final short BROWSE_ITEM_SCROLLBAR_Y = 100;
	private final short BROWSE_FILTER_X = 21;
	private final short BROWSE_FILTER_Y = 103;
	private final short BROWSE_FILTER_Y_SHIFT = 20;
	private final short BROWSE_FILTER_WIDTH_NO_SCROLLBAR = 156;
	private final short BROWSE_FILTER_WIDTH_SCROLLBAR = 136;
	private final short BROWSE_FILTER_HEIGHT = 20;
	private final short BROWSE_CLOSE_BUTTON_X = 744;
	private final short BROWSE_CLOSE_BUTTON_Y = 409;
	private final short BROWSE_BUYOUT_BUTTON_X = 663;
	private final short BROWSE_BID_BUTTON_X = 584;
	private final short BROWSE_BUTTON_WIDTH = 75;
	private final short BROWSE_BUTTON_HEIGHT = 22;
	private final short BROWSE_SORT_BUTTON_Y = 80;
	private final short BROWSE_SORT_BUTTON_HEIGHT = 21;
	private final short BROWSE_SORT_RARITY_BUTTON_WIDTH = 214;
	private final short BROWSE_SORT_RARITY_BUTTON_X = 184;
	private final short BROWSE_SORT_LEVEL_BUTTON_X = 396;
	private final short BROWSE_SORT_LEVEL_BUTTON_WIDTH = 57;
	private final short BROWSE_SORT_TIME_LEFT_BUTTON_X = 451;
	private final short BROWSE_SORT_TIME_LEFT_BUTTON_WIDTH = 91;
	private final short BROWSE_SORT_SELLER_BUTTON_X = 540;
	private final short BROWSE_SORT_SELLER_BUTTON_WIDTH = 76;
	private final short BROWSE_SORT_PRICE_PER_UNIT_BUTTON_X = 614;
	private final short BROWSE_SORT_PRICE_PER_UNIT_BUTTON_WIDTH = 184;
	private final short BROWSE_SEND_QUERY_BUTTON_X = 659;
	private final short BROWSE_SEND_QUERY_BUTTON_WIDTH = 78;
	private final short BROWSE_SEND_QUERY_BUTTON_Y = 33;
	private final short BROWSE_SEND_QUERY_BUTTON_HEIGHT = 22;
	private final short BROWSE_SEARCH_EDIT_BOX_X = 76;
	private final short BROWSE_SEARCH_EDIT_BOX_WIDTH = 164;
	private final short BROWSE_SEARCH_EDIT_BOX_INPUT_WIDTH = 105;
	private final short BROWSE_SEARCH_EDIT_BOX_Y = 49;
	private final short BROWSE_MIN_LEVEL_EDIT_BOX_X = 226;
	private final short BROWSE_MAX_LEVEL_EDIT_BOX_X = 263;
	private final short BROWSE_LEVEL_EDIT_BOX_WIDTH = 30;
	private final short BROWSE_LEVEL_EDIT_BOX_INPUT_WIDTH = 30;
	private final short BROWSE_QUALITY_FILTER_DROP_DOWN_X = 305;
	private final short BROWSE_QUALITY_FILTER_DROP_DOWN_Y = 49;
	private final short BROWSE_QUALITY_FILTER_DROP_DOWN_BAR_WIDTH = 131;
	private final short BROWSE_QUALITY_FILTER_DROP_DOWN_MENU_WIDTH = 155;
	private final short BROWSE_QUERY_BUTTON_NO_SCROLLBAR_WIDTH = 590;
	private final short BROWSE_QUERY_BUTTON_SCROLLBAR_WIDTH = 400;
	private final short BROWSE_ITEM_Y = 108;
	private final short BROWSE_ITEM_HEIGHT = 40;
	private final short BROWSE_ITEM_X = 194;
	
	
	private AuctionFrameTab selectedTab = AuctionFrameTab.BROWSE;
	private final ArrayList<AuctionCategoryFilterButton> browseCategoryList;
	private final ArrayList<AuctionBrowseQueryButton> queryButtonList;
	AuctionEntry browseSelectedEntry;
	AuctionHouseFilter selectedFilter = AuctionHouseFilter.NONE;
	AuctionHouseFilter selectedCategoryFilter = AuctionHouseFilter.NONE;
	AuctionHouseSort selectedSort;
	AuctionHouseQualityFilter qualityFilter;
	protected final TTF browseFilterFont;
	private short browseFilterX;
	private short browseFilterY;
	private short browseFilterYSave;
	private short browseFilterYShift;
	private short browseFilterWidth;
	private short browseFilterHeight;
	short browseFilterScrollbarOffset;
	private short browseItemX;
	private short browseItemY;
	private short browseItemWidth;
	private short browseItemHeight;
	private short browseItemYSave;
	private short browseItemYShift;
	short browseItemStartIndex;
	byte browserFilterNumberLine;
	private boolean shouldUpdate;
	private boolean browseFilterScrollbarEnabled;
	private boolean hasSearched;
	private boolean browseItemScrollbarEnabled;
	private short browsePage;
	private int browseNumberTotalResult;
	private String browseResultString;
	boolean querySent;
	final ScrollBar browseFilterScrollbar = new ScrollBar(this.x_frame+this.BROWSE_FILTER_SCROLLBAR_X*Mideas.getDisplayXFactor(), this.y_frame+this.BROWSE_FILTER_SCROLLBAR_Y*Mideas.getDisplayYFactor(), this.BROWSE_FILTER_SCROLLBAR_HEIGHT*Mideas.getDisplayYFactor(), this.BROWSE_FILTER_SCROLLBAR_WIDTH*Mideas.getDisplayXFactor(), this.BROWSE_FILTER_SCROLLBAR_HEIGHT*Mideas.getDisplayYFactor(), false, this.BROWSE_FILTER_HEIGHT*Mideas.getDisplayYFactor()) {
		
		@Override
		public void onScroll() {
			AuctionFrameUI.this.browseFilterScrollbarOffset = (short)(AuctionFrameUI.this.BROWSE_FILTER_HEIGHT*Mideas.getDisplayYFactor()*(AuctionFrameUI.this.browserFilterNumberLine-15)*getScrollPercentage());
		}
	};
	final ScrollBar browseItemScrollbar = new ScrollBar(this.x_frame+this.BROWSE_ITEM_SCROLLBAR_X*Mideas.getDisplayXFactor(), this.y_frame+this.BROWSE_ITEM_SCROLLBAR_Y*Mideas.getDisplayYFactor(), this.BROWSE_ITEM_SCROLLBAR_HEIGHT*Mideas.getDisplayYFactor(), this.BROWSE_ITEM_SCROLLBAR_WIDTH*Mideas.getDisplayXFactor(), this.BROWSE_ITEM_SCROLLBAR_HEIGHT*Mideas.getDisplayYFactor(), false, this.browseItemYShift*Mideas.getDisplayYFactor()) {
		
		@Override
		public void onScroll() {
			AuctionFrameUI.this.browseItemStartIndex = (short)((Mideas.joueur1().getAuctionHouse().getQueryList().size()-AuctionFrameUI.this.BROWSE_MAXIMUM_ITEM_DISPLAYED)*getScrollPercentage());
		}
	};
	private final Button browseBidButton = new Button(this.x_frame+this.BROWSE_BID_BUTTON_X*Mideas.getDisplayXFactor(), this.y_frame+this.BROWSE_CLOSE_BUTTON_Y*Mideas.getDisplayYFactor(), this.BROWSE_BUTTON_WIDTH*Mideas.getDisplayXFactor(), this.BROWSE_BUTTON_HEIGHT*Mideas.getDisplayYFactor(), "Bid", 12, 1) {
	
		@Override
		public void eventButtonClick() {
			CommandAuction.makeABid(AuctionFrameUI.this.browseSelectedEntry, 1); //TODO: use input values
		}
		
		@Override
		public boolean activateCondition() {
			return AuctionFrameUI.this.browseSelectedEntry != null && AuctionFrameUI.this.browseSelectedEntry.canBeBuy();
		}
	};
	private final Button browseBuyoutButton = new Button(this.x_frame+this.BROWSE_BUYOUT_BUTTON_X*Mideas.getDisplayXFactor(), this.y_frame+this.BROWSE_CLOSE_BUTTON_Y*Mideas.getDisplayYFactor(), this.BROWSE_BUTTON_WIDTH*Mideas.getDisplayXFactor(), this.BROWSE_BUTTON_HEIGHT*Mideas.getDisplayYFactor(), "Buyout", 12, 1) {
	
		@Override
		public void eventButtonClick() {
			CommandAuction.buyout(AuctionFrameUI.this.browseSelectedEntry);
		}
		
		@Override
		public boolean activateCondition() {
			return AuctionFrameUI.this.browseSelectedEntry != null;
		}
	};
	private final Button browseClose = new Button(this.x_frame+this.BROWSE_CLOSE_BUTTON_X*Mideas.getDisplayXFactor(), this.y_frame+this.BROWSE_CLOSE_BUTTON_Y*Mideas.getDisplayYFactor(), this.BROWSE_BUTTON_WIDTH*Mideas.getDisplayXFactor(), this.BROWSE_BUTTON_HEIGHT*Mideas.getDisplayYFactor(), "Close", 12, 1) {
	
		@Override
		public void eventButtonClick() {
			Interface.closeAuctionFrame();
			this.reset();
		}
	};
	private final Button browseSendQueryButton = new Button(this.x_frame+this.BROWSE_SEND_QUERY_BUTTON_X*Mideas.getDisplayXFactor(), this.y_frame+this.BROWSE_SEND_QUERY_BUTTON_Y*Mideas.getDisplayYFactor(), this.BROWSE_SEND_QUERY_BUTTON_WIDTH*Mideas.getDisplayXFactor(), this.BROWSE_SEND_QUERY_BUTTON_HEIGHT*Mideas.getDisplayYFactor(), "Search", 12, 1) {
	
		@Override
		public void eventButtonClick() {
			sendSearchQuery();
		}
		
		@Override
		public boolean activateCondition() {
			return !AuctionFrameUI.this.querySent;
		}
	};
	private final Button browseResetButton = new Button(this.x_frame+this.BROWSE_SEND_QUERY_BUTTON_X*Mideas.getDisplayXFactor(), this.y_frame+this.BROWSE_SEND_QUERY_BUTTON_Y*Mideas.getDisplayYFactor(), this.BROWSE_SEND_QUERY_BUTTON_WIDTH*Mideas.getDisplayXFactor(), this.BROWSE_SEND_QUERY_BUTTON_HEIGHT*Mideas.getDisplayYFactor(), "Search", 12, 1) {
	
		@Override
		public void eventButtonClick() {
			//resetBrowseOptions(); TODO: create function
		}
		
		@Override
		public boolean activateCondition() {
			return AuctionFrameUI.this.selectedCategoryFilter != AuctionHouseFilter.NONE || AuctionFrameUI.this.selectedFilter != AuctionHouseFilter.NONE;
		}
	};
	private final EditBox browseSearchEditBox = new EditBox(this.x_frame+this.BROWSE_SEARCH_EDIT_BOX_X*Mideas.getDisplayXFactor(), this.y_frame+this.BROWSE_SEARCH_EDIT_BOX_Y*Mideas.getDisplayYFactor(), this.BROWSE_SEARCH_EDIT_BOX_WIDTH*Mideas.getDisplayXFactor(), 63, 21, this.BROWSE_SEARCH_EDIT_BOX_INPUT_WIDTH*Mideas.getDisplayXFactor(), FontManager.get("FRIZQT", 11), false, 2, 13, "Search") {
		
		@Override
		public boolean keyEvent(char c) {
			if(c == Input.ENTER_CHAR_VALUE) {
				sendSearchQuery();
				this.input.setIsActive(false);
				return true;
			}
			if(c == Input.ESCAPE_CHAR_VALUE) {
				this.input.setIsActive(false);
				return true;
			}
			return false;
		}
	};
	private final EditBox browseMinLevelEditBox = new EditBox(this.x_frame+this.BROWSE_MIN_LEVEL_EDIT_BOX_X*Mideas.getDisplayXFactor(), this.y_frame+this.BROWSE_SEARCH_EDIT_BOX_Y*Mideas.getDisplayYFactor(), this.BROWSE_LEVEL_EDIT_BOX_WIDTH, 3, 5, this.BROWSE_LEVEL_EDIT_BOX_INPUT_WIDTH, FontManager.get("FRIZQT", 11), true) {
		
		@Override
		public boolean keyEvent(char c) {
			if(c == Input.ENTER_CHAR_VALUE || c == Input.ESCAPE_CHAR_VALUE) {
				this.input.setIsActive(false);
				return true;
			}
			return false;
		}
		
		@Override
		public boolean checkValue(int value) {
			return value <= 999;
		}
	};
	private final EditBox browseMaxLevelEditBox = new EditBox(this.x_frame+this.BROWSE_MAX_LEVEL_EDIT_BOX_X*Mideas.getDisplayXFactor(), this.y_frame+this.BROWSE_SEARCH_EDIT_BOX_Y*Mideas.getDisplayYFactor(), this.BROWSE_LEVEL_EDIT_BOX_WIDTH, 3, 5, this.BROWSE_LEVEL_EDIT_BOX_INPUT_WIDTH, FontManager.get("FRIZQT", 12), true) {
		
		@Override
		public boolean keyEvent(char c) {
			if(c == Input.ENTER_CHAR_VALUE || c == Input.ESCAPE_CHAR_VALUE) {
				this.input.setIsActive(false);
				return true;
			}
			return false;
		}
		
		@Override
		public boolean checkValue(int value) {
			return value <= 999;
		}
	};
	private final ButtonMenuSort browseSortByRarityButton = new ButtonMenuSort(this.x_frame+this.BROWSE_SORT_RARITY_BUTTON_X*Mideas.getDisplayXFactor(), this.y_frame+this.BROWSE_SORT_BUTTON_Y*Mideas.getDisplayYFactor(), this.BROWSE_SORT_RARITY_BUTTON_WIDTH*Mideas.getDisplayXFactor(), this.BROWSE_SORT_BUTTON_HEIGHT*Mideas.getDisplayYFactor(), "Rarity", 11) {
		
		@Override
		public void eventButtonClick() {
			if(AuctionFrameUI.this.selectedSort == AuctionHouseSort.RARITY_ASCENDING) {
				AuctionFrameUI.this.selectedSort = AuctionHouseSort.RARITY_DESCENDING;
			}
			else {
				AuctionFrameUI.this.selectedSort = AuctionHouseSort.RARITY_ASCENDING;
			}
			sendSearchQuery();
		}
	};
	private final ButtonMenuSort browseSortByLevelButton = new ButtonMenuSort(this.x_frame+this.BROWSE_SORT_LEVEL_BUTTON_X*Mideas.getDisplayXFactor(), this.y_frame+this.BROWSE_SORT_BUTTON_Y*Mideas.getDisplayYFactor(), this.BROWSE_SORT_LEVEL_BUTTON_WIDTH*Mideas.getDisplayXFactor(), this.BROWSE_SORT_BUTTON_HEIGHT*Mideas.getDisplayYFactor(), "Lvl", 11) {
		
		@Override
		public void eventButtonClick() {
			if(AuctionFrameUI.this.selectedSort == AuctionHouseSort.LEVEL_ASCENDING) {
				AuctionFrameUI.this.selectedSort = AuctionHouseSort.LEVEL_DESCENDING;
			}
			else {
				AuctionFrameUI.this.selectedSort = AuctionHouseSort.LEVEL_ASCENDING;
			}
			sendSearchQuery();
		}
	};
	private final ButtonMenuSort browseSortByTimeLeftButton = new ButtonMenuSort(this.x_frame+this.BROWSE_SORT_TIME_LEFT_BUTTON_X*Mideas.getDisplayXFactor(), this.y_frame+this.BROWSE_SORT_BUTTON_Y*Mideas.getDisplayYFactor(), this.BROWSE_SORT_TIME_LEFT_BUTTON_WIDTH*Mideas.getDisplayXFactor(), this.BROWSE_SORT_BUTTON_HEIGHT*Mideas.getDisplayYFactor(), "Time Left", 11) {
		
		@Override
		public void eventButtonClick() {
			if(AuctionFrameUI.this.selectedSort == AuctionHouseSort.TIME_LEFT_ASCENDING) {
				AuctionFrameUI.this.selectedSort = AuctionHouseSort.TIME_LEFT_DESCENDING;
			}
			else {
				AuctionFrameUI.this.selectedSort = AuctionHouseSort.TIME_LEFT_ASCENDING;
			}
			sendSearchQuery();
		}
	};
	private final ButtonMenuSort browseSortBySellerButton = new ButtonMenuSort(this.x_frame+this.BROWSE_SORT_SELLER_BUTTON_X*Mideas.getDisplayXFactor(), this.y_frame+this.BROWSE_SORT_BUTTON_Y*Mideas.getDisplayYFactor(), this.BROWSE_SORT_SELLER_BUTTON_WIDTH*Mideas.getDisplayXFactor(), this.BROWSE_SORT_BUTTON_HEIGHT*Mideas.getDisplayYFactor(), "Seller", 11) {
		
		@Override
		public void eventButtonClick() {
			if(AuctionFrameUI.this.selectedSort == AuctionHouseSort.VENDOR_ASCENDING) {
				AuctionFrameUI.this.selectedSort = AuctionHouseSort.VENDOR_DESCENDING;
			}
			else {
				AuctionFrameUI.this.selectedSort = AuctionHouseSort.VENDOR_ASCENDING;
			}
			sendSearchQuery();
		}
	};
	private final ButtonMenuSort browseSortByPricePerUnitButton = new ButtonMenuSort(this.x_frame+this.BROWSE_SORT_PRICE_PER_UNIT_BUTTON_X*Mideas.getDisplayXFactor(), this.y_frame+this.BROWSE_SORT_BUTTON_Y*Mideas.getDisplayYFactor(), this.BROWSE_SORT_PRICE_PER_UNIT_BUTTON_WIDTH*Mideas.getDisplayXFactor(), this.BROWSE_SORT_BUTTON_HEIGHT*Mideas.getDisplayYFactor(), "Price Per Unit", 11) {
		
		@Override
		public void eventButtonClick() {
			if(AuctionFrameUI.this.selectedSort == AuctionHouseSort.PRICE_PER_UNIT_ASCENDING) {
				AuctionFrameUI.this.selectedSort = AuctionHouseSort.PRICE_PER_UNIT_DESCENDING;
			}
			else {
				AuctionFrameUI.this.selectedSort = AuctionHouseSort.PRICE_PER_UNIT_ASCENDING;
			}
			sendSearchQuery();
		}
	};
	private final DropDownMenu browseRarityDropDownmenu = new DropDownMenu(this.x_frame+this.BROWSE_QUALITY_FILTER_DROP_DOWN_X*Mideas.getDisplayXFactor(), this.y_frame+this.BROWSE_QUALITY_FILTER_DROP_DOWN_Y*Mideas.getDisplayXFactor(), this.BROWSE_QUALITY_FILTER_DROP_DOWN_BAR_WIDTH*Mideas.getDisplayXFactor(), this.x_frame+this.BROWSE_QUALITY_FILTER_DROP_DOWN_X*Mideas.getDisplayXFactor(), this.y_frame+(this.BROWSE_QUALITY_FILTER_DROP_DOWN_Y+20)*Mideas.getDisplayYFactor(), this.BROWSE_QUALITY_FILTER_DROP_DOWN_MENU_WIDTH*Mideas.getDisplayXFactor(), 12, .6f) {
		
		@Override
		public void menuEventButtonClick() {
			AuctionFrameUI.this.qualityFilter = AuctionHouseQualityFilter.values()[this.selectedMenuValue+1];
		}
	};
	private final TextMenu browseRarityAllTextMenu = new TextMenu(0, 0, 0, "All", 11, 1);
	private final TextMenu browseRarityPoorTextMenu = new TextMenu(0, 0, 0, "Poor", 11, 1);
	private final TextMenu browseRarityCommonTextMenu = new TextMenu(0, 0, 0, "Common", 11, 1);
	private final TextMenu browseRarityUncommonTextMenu = new TextMenu(0, 0, 0, "Uncommon", 11, 1);
	private final TextMenu browseRarityRareTextMenu = new TextMenu(0, 0, 0, "Rare", 11, 1);
	private final TextMenu browseRarityEpicTextMenu = new TextMenu(0, 0, 0, "Epic", 11, 1);
	private final TextMenu browseRarityLegendaryTextMenu = new TextMenu(0, 0, 0, "Legendary", 11, 1);

	public AuctionFrameUI() {
		this.queryButtonList = new ArrayList<AuctionBrowseQueryButton>();
		this.browseCategoryList = new ArrayList<AuctionCategoryFilterButton>();
		this.shouldUpdate = true;
		updateSize();
		int i = 0;
		while(i < 50) {
			this.queryButtonList.add(new AuctionBrowseQueryButton(this));
			i++;
		}
		this.browseFilterFont = FontManager.get("FRIZQT", 11);
		buildBrowseFilterQualityMenu();
		buildBrowseFilterMenu();
	}
	
	void sendSearchQuery() {
		AuctionHouseFilter filter = this.selectedFilter;
		if(this.selectedFilter == AuctionHouseFilter.NONE) {
			filter = this.selectedCategoryFilter;
		}
		CommandAuction.sendQuery(filter, AuctionHouseSort.LEVEL_ASCENDING, AuctionHouseQualityFilter.COMMON, this.browseSearchEditBox.getText(), (short)1, false, (short)this.browseMinLevelEditBox.getValue(), (short)this.browseMaxLevelEditBox.getValue(), false);
		this.hasSearched = true;
		this.querySent = true;
	}
	
	void resetBrowseOption() {
		int i = this.browseCategoryList.size();
		while(--i >= 0) {
			this.browseCategoryList.get(i).unexpand();
		}
		this.selectedCategoryFilter = AuctionHouseFilter.NONE;
		this.selectedFilter = AuctionHouseFilter.NONE;
	}
	
	public void draw() {
		updateSize();
		if(this.selectedTab == AuctionFrameTab.BROWSE) {
			drawBrowseFrame();
		}
		else if(this.selectedTab == AuctionFrameTab.BIDS) {
			drawBidsFrame();
		}
		else if(this.selectedTab == AuctionFrameTab.AUCTIONS) {
			drawAuctionsFrame();
		}
	}
	
	private void drawBrowseFrame() {
		Draw.drawQuad(Sprites.auction_browse_frame, this.x_frame, this.y_frame);
		this.browseFilterY = (short)(this.browseFilterYSave-this.browseFilterScrollbarOffset);
		this.browseBidButton.draw();
		this.browseBuyoutButton.draw();
		this.browseClose.draw();
		this.browseSortByRarityButton.draw();
		this.browseSortByLevelButton.draw();
		this.browseSortByTimeLeftButton.draw();
		this.browseSortBySellerButton.draw();
		this.browseSortByPricePerUnitButton.draw();
		this.browseSendQueryButton.draw();
		this.browseSearchEditBox.draw();
		this.browseMinLevelEditBox.draw();
		this.browseMaxLevelEditBox.draw();
		TTF font = FontManager.get("FRIZQT", 11);
		font.drawBegin();
		font.drawStringShadowPart(this.x_frame+80*Mideas.getDisplayXFactor(), this.y_frame+34*Mideas.getDisplayYFactor(), "Name", Color.WHITE, Color.BLACK, 1, 0, 0);
		font.drawStringShadowPart(this.x_frame+229*Mideas.getDisplayXFactor(), this.y_frame+34*Mideas.getDisplayYFactor(), "Level Range", Color.WHITE, Color.BLACK, 1, 0, 0);
		font.drawStringShadowPart(this.x_frame+257*Mideas.getDisplayXFactor(), this.y_frame+50*Mideas.getDisplayYFactor(), "-", Color.WHITE, Color.BLACK, 1, 0, 0);
		font.drawStringShadowPart(this.x_frame+308*Mideas.getDisplayXFactor(), this.y_frame+34*Mideas.getDisplayYFactor(), "Rarity", Color.WHITE, Color.BLACK, 1, 0, 0);
		font.drawStringShadowPart(this.x_frame+288*Mideas.getDisplayXFactor(), this.y_frame+410*Mideas.getDisplayYFactor(), "Bid", Color.WHITE, Color.BLACK, 1, 0, 0);
		font.drawEnd();
		font = FontManager.get("ARIALN", 14);
		font.drawBegin();
		font.drawStringShadowPart(this.x_frame+138*Mideas.getDisplayXFactor(), this.y_frame+409*Mideas.getDisplayYFactor(), Mideas.joueur1().getCoppierPieceString(), Color.WHITE, Color.BLACK, 1, 0, 0);
		font.drawStringShadowPart(this.x_frame+102*Mideas.getDisplayXFactor(), this.y_frame+409*Mideas.getDisplayYFactor(), Mideas.joueur1().getSilverPieceString(), Color.WHITE, Color.BLACK, 1, 0, 0);
		font.drawStringShadowPart(this.x_frame+78*Mideas.getDisplayXFactor()-font.getWidth(Mideas.joueur1().getGoldPieceString()), this.y_frame+409*Mideas.getDisplayYFactor(), Mideas.joueur1().getGoldPieceString(), Color.WHITE, Color.BLACK, 1, 0, 0);
		font.drawEnd();
		Draw.drawQuad(Sprites.copper_coin, this.x_frame+153*Mideas.getDisplayXFactor(), this.y_frame+412*Mideas.getDisplayYFactor(), 13*Mideas.getDisplayXFactor(), 13*Mideas.getDisplayYFactor());
		Draw.drawQuad(Sprites.copper_coin, this.x_frame+463*Mideas.getDisplayXFactor(), this.y_frame+413*Mideas.getDisplayYFactor(), 13*Mideas.getDisplayXFactor(), 13*Mideas.getDisplayYFactor());
		Draw.drawQuad(Sprites.silver_coin, this.x_frame+118*Mideas.getDisplayXFactor(), this.y_frame+412*Mideas.getDisplayYFactor(), 13*Mideas.getDisplayXFactor(), 13*Mideas.getDisplayYFactor());
		Draw.drawQuad(Sprites.silver_coin, this.x_frame+415*Mideas.getDisplayXFactor(), this.y_frame+413*Mideas.getDisplayYFactor(), 13*Mideas.getDisplayXFactor(), 13*Mideas.getDisplayYFactor());
		Draw.drawQuad(Sprites.gold_coin, this.x_frame+83*Mideas.getDisplayXFactor(), this.y_frame+412*Mideas.getDisplayYFactor(), 13*Mideas.getDisplayXFactor(), 13*Mideas.getDisplayYFactor());
		Draw.drawQuad(Sprites.gold_coin, this.x_frame+367*Mideas.getDisplayXFactor(), this.y_frame+413*Mideas.getDisplayYFactor(), 13*Mideas.getDisplayXFactor(), 13*Mideas.getDisplayYFactor());
		Draw.glScissorBegin(0, 495*Mideas.getDisplayYFactor(), 500, 305*Mideas.getDisplayYFactor());
		int i = -1;
		while(++i < this.browseCategoryList.size()) {
			this.browseCategoryList.get(i).draw();
		}
		Draw.glScissorEnd();
		drawBrowseItem();
		if(this.browseFilterScrollbarEnabled) {
			this.browseFilterScrollbar.draw();
		}
		this.browseRarityDropDownmenu.draw();
	}
	
	public void updateQueryButtonList() {
		ArrayList<AuctionEntry> list = Mideas.joueur1().getAuctionHouse().getQueryList();
		short width;
		if(list.size() > 8) {
			width = this.BROWSE_QUERY_BUTTON_SCROLLBAR_WIDTH;
		}
		else {
			width = this.BROWSE_QUERY_BUTTON_NO_SCROLLBAR_WIDTH;
		}
		int i = -1;
		while(++i < 50) {
			if(i < list.size()) {
				this.queryButtonList.get(i).setEntry(list.get(i));
			}
			else {
				this.queryButtonList.get(i).setEntry(null);
			}
			this.queryButtonList.get(i).setWidth(width);
		}
	}
	
	private void drawBrowseItem() {
		ArrayList<AuctionEntry> list = Mideas.joueur1().getAuctionHouse().getQueryList();
		if(list.size() == 0 && !this.querySent) {
			if(this.hasSearched) {
				FontManager.get("FRIZQT", 13).drawStringShadow(this.x_frame+445*Mideas.getDisplayXFactor(), this.y_frame+115*Mideas.getDisplayYFactor(), "No items found", Color.YELLOW, Color.BLACK, 1, 0, 0);
			}
			else if(!this.querySent) {
				FontManager.get("FRIZQT", 13).drawStringShadow(this.x_frame+363*Mideas.getDisplayXFactor(), this.y_frame+115*Mideas.getDisplayYFactor(), "Choose search criteria and press \"Search\"", Color.YELLOW, Color.BLACK, 1, 0, 0);
			}
			else {
				FontManager.get("FRIZQT", 13).drawStringShadow(this.x_frame+448*Mideas.getDisplayXFactor(), this.y_frame+115*Mideas.getDisplayYFactor(), "Searching...", Color.YELLOW, Color.BLACK, 1, 0, 0);
			}
			return;
		}
		this.browseItemY = this.browseItemYSave;
		int i = this.browseItemStartIndex;
		int end = this.browseItemStartIndex+this.BROWSE_MAXIMUM_ITEM_DISPLAYED;
		while(i < end && this.queryButtonList.get(i).getEntry() != null) {
			this.queryButtonList.get(i).draw();
			i++;
		}
		i = this.browseItemStartIndex;
		AuctionBrowseQueryButton.ITEM_NAME_FONT.drawBegin();
		while(i < end && this.queryButtonList.get(i).getEntry() != null) {
			this.queryButtonList.get(i).drawItemName();
			i++;
		}
		AuctionBrowseQueryButton.ITEM_NAME_FONT.drawEnd();
		i = this.browseItemStartIndex;
		AuctionBrowseQueryButton.DURATION_FONT.drawBegin();
		while(i < end && this.queryButtonList.get(i).getEntry() != null) {
			this.queryButtonList.get(i).drawString();
			i++;
		}
		AuctionBrowseQueryButton.DURATION_FONT.drawEnd();
	}
	
	private void drawBidsFrame() {
		
	}
	
	private void drawAuctionsFrame() {
		
	}
	
	public boolean mouseEvent() {
		if(this.selectedTab == AuctionFrameTab.BROWSE) {
			return browseFrameMouseEvent();
		}
		else if(this.selectedTab == AuctionFrameTab.BIDS) {
			return bidsFrameMouseEvent();
		}
		else if(this.selectedTab == AuctionFrameTab.AUCTIONS) {
			return auctionsFrameMouseEvent();
		}
		return false;
	}
	
	private boolean browseFrameMouseEvent() {
		if(this.browseRarityDropDownmenu.event()) return true;
		if(this.browseBidButton.event()) return true;
		if(this.browseBuyoutButton.event()) return true;
		if(this.browseClose.event()) return true;
		if(this.browseSortByRarityButton.event()) return true;
		if(this.browseSortByLevelButton.event()) return true;
		if(this.browseSortByTimeLeftButton.event()) return true;
		if(this.browseSortBySellerButton.event()) return true;
		if(this.browseSortByPricePerUnitButton.event()) return true;
		if(this.browseSendQueryButton.event()) return true;
		if(this.browseSearchEditBox.mouseEvent()) return true;
		if(this.browseMinLevelEditBox.mouseEvent()) return true;
		if(this.browseMaxLevelEditBox.mouseEvent()) return true;
		if(this.browseFilterScrollbarEnabled) {
			if(this.browseFilterScrollbar.event()) return true;
		}
		int i = -1;
		this.browseFilterY = (short)(this.browseFilterYSave-this.browseFilterScrollbarOffset);
		while(++i < this.browseCategoryList.size()) {
			if(this.browseCategoryList.get(i).event()) {
				return true;
			}
		}
		this.browseItemY = this.browseItemYSave;
		i = this.browseItemStartIndex;
		int end = this.browseItemStartIndex+this.BROWSE_MAXIMUM_ITEM_DISPLAYED;
		while(i < end && this.queryButtonList.get(i).getEntry() != null) {
			if(this.queryButtonList.get(i).mouseEvent()) {
				return true;
			}
			i++;
		}
		return false;
	}
	
	private boolean bidsFrameMouseEvent() {
		return false;
	}
	
	private boolean auctionsFrameMouseEvent() {
		return false;
	}
	
	public boolean keyboardEvent() {
		if(this.selectedTab == AuctionFrameTab.BROWSE) {
			return browseFrameKeyboardEvent();
		}
		else if(this.selectedTab == AuctionFrameTab.BIDS) {
			return bidsFrameKeyboardEvent();
		}
		else if(this.selectedTab == AuctionFrameTab.AUCTIONS) {
			return auctionsFrameKeyboardEvent();
		}
		return false;
	}
	
	private boolean browseFrameKeyboardEvent() {
		if(this.browseSearchEditBox.keyboardEvent()) return true;
		if(this.browseMinLevelEditBox.keyboardEvent()) return true;
		if(this.browseMaxLevelEditBox.keyboardEvent()) return true;
		return false;
	}
	
	private boolean bidsFrameKeyboardEvent() {
		return false;
	}
	
	private boolean auctionsFrameKeyboardEvent() {
		return false;
	}
	
	private void updateSize() {
		if(!this.shouldUpdate) {
			return;
		}
		if(this.browseFilterScrollbarEnabled) {
			this.browseFilterWidth = (short)(this.BROWSE_FILTER_WIDTH_SCROLLBAR*Mideas.getDisplayXFactor());
		}
		else {
			this.browseFilterWidth = (short)(this.BROWSE_FILTER_WIDTH_NO_SCROLLBAR*Mideas.getDisplayXFactor());
		}
		this.x_frame = (short)(this.X_FRAME*Mideas.getDisplayXFactor());
		this.y_frame = (short)(this.Y_FRAME*Mideas.getDisplayYFactor());
		this.browseFilterHeight = (short)(this.BROWSE_FILTER_HEIGHT*Mideas.getDisplayYFactor());
		this.browseFilterX = (short)(this.x_frame+this.BROWSE_FILTER_X*Mideas.getDisplayXFactor());
		this.browseFilterYSave = (short)(this.y_frame+this.BROWSE_FILTER_Y*Mideas.getDisplayYFactor());
		this.browseFilterYShift = (short)(this.BROWSE_FILTER_Y_SHIFT*Mideas.getDisplayYFactor());
		this.browseItemYSave = (short)(this.y_frame+this.BROWSE_ITEM_Y*Mideas.getDisplayYFactor());
		this.browseItemX = (short)(this.x_frame+this.BROWSE_ITEM_X*Mideas.getDisplayXFactor());
		this.browseItemHeight = (short)(this.BROWSE_ITEM_HEIGHT*Mideas.getDisplayYFactor());
		this.browseBidButton.update(this.x_frame+this.BROWSE_BID_BUTTON_X*Mideas.getDisplayXFactor(), this.y_frame+this.BROWSE_CLOSE_BUTTON_Y*Mideas.getDisplayYFactor(), this.BROWSE_BUTTON_WIDTH*Mideas.getDisplayXFactor(), this.BROWSE_BUTTON_HEIGHT*Mideas.getDisplayYFactor());
		this.browseBuyoutButton.update(this.x_frame+this.BROWSE_BUYOUT_BUTTON_X*Mideas.getDisplayXFactor(), this.y_frame+this.BROWSE_CLOSE_BUTTON_Y*Mideas.getDisplayYFactor(), this.BROWSE_BUTTON_WIDTH*Mideas.getDisplayXFactor(), this.BROWSE_BUTTON_HEIGHT*Mideas.getDisplayYFactor());
		this.browseClose.update(this.x_frame+this.BROWSE_CLOSE_BUTTON_X*Mideas.getDisplayXFactor(), this.y_frame+this.BROWSE_CLOSE_BUTTON_Y*Mideas.getDisplayYFactor(), this.BROWSE_BUTTON_WIDTH*Mideas.getDisplayXFactor(), this.BROWSE_BUTTON_HEIGHT*Mideas.getDisplayYFactor());
		this.browseFilterScrollbar.update(this.x_frame+this.BROWSE_FILTER_SCROLLBAR_X*Mideas.getDisplayXFactor(), this.y_frame+this.BROWSE_FILTER_SCROLLBAR_Y*Mideas.getDisplayYFactor(), this.BROWSE_FILTER_SCROLLBAR_HEIGHT*Mideas.getDisplayYFactor(), this.BROWSE_FILTER_SCROLLBAR_WIDTH*Mideas.getDisplayXFactor(), this.BROWSE_FILTER_SCROLLBAR_HEIGHT*Mideas.getDisplayYFactor(), this.BROWSE_FILTER_HEIGHT*Mideas.getDisplayYFactor());
		this.browseSortByRarityButton.update(this.x_frame+this.BROWSE_SORT_RARITY_BUTTON_X*Mideas.getDisplayXFactor(), this.y_frame+this.BROWSE_SORT_BUTTON_Y*Mideas.getDisplayYFactor(), this.BROWSE_SORT_RARITY_BUTTON_WIDTH*Mideas.getDisplayXFactor(), this.BROWSE_SORT_BUTTON_HEIGHT*Mideas.getDisplayYFactor());
		this.browseSortByLevelButton.update(this.x_frame+this.BROWSE_SORT_LEVEL_BUTTON_X*Mideas.getDisplayXFactor(), this.y_frame+this.BROWSE_SORT_BUTTON_Y*Mideas.getDisplayYFactor(), this.BROWSE_SORT_LEVEL_BUTTON_WIDTH*Mideas.getDisplayXFactor(), this.BROWSE_SORT_BUTTON_HEIGHT*Mideas.getDisplayYFactor());
		this.browseSortByTimeLeftButton.update(this.x_frame+this.BROWSE_SORT_TIME_LEFT_BUTTON_X*Mideas.getDisplayXFactor(), this.y_frame+this.BROWSE_SORT_BUTTON_Y*Mideas.getDisplayYFactor(), this.BROWSE_SORT_TIME_LEFT_BUTTON_WIDTH*Mideas.getDisplayXFactor(), this.BROWSE_SORT_BUTTON_HEIGHT*Mideas.getDisplayYFactor());
		this.browseSortBySellerButton.update(this.x_frame+this.BROWSE_SORT_SELLER_BUTTON_X*Mideas.getDisplayXFactor(), this.y_frame+this.BROWSE_SORT_BUTTON_Y*Mideas.getDisplayYFactor(), this.BROWSE_SORT_SELLER_BUTTON_WIDTH*Mideas.getDisplayXFactor(), this.BROWSE_SORT_BUTTON_HEIGHT*Mideas.getDisplayYFactor());
		this.browseSortByPricePerUnitButton.update(this.x_frame+this.BROWSE_SORT_PRICE_PER_UNIT_BUTTON_X*Mideas.getDisplayXFactor(), this.y_frame+this.BROWSE_SORT_BUTTON_Y*Mideas.getDisplayYFactor(), this.BROWSE_SORT_PRICE_PER_UNIT_BUTTON_WIDTH*Mideas.getDisplayXFactor(), this.BROWSE_SORT_BUTTON_HEIGHT*Mideas.getDisplayYFactor());
		this.browseSendQueryButton.update(this.x_frame+this.BROWSE_SEND_QUERY_BUTTON_X*Mideas.getDisplayXFactor(), this.y_frame+this.BROWSE_SEND_QUERY_BUTTON_Y*Mideas.getDisplayYFactor(), this.BROWSE_SEND_QUERY_BUTTON_WIDTH*Mideas.getDisplayXFactor(), this.BROWSE_SEND_QUERY_BUTTON_HEIGHT*Mideas.getDisplayYFactor());
		this.browseSearchEditBox.update(this.x_frame+this.BROWSE_SEARCH_EDIT_BOX_X*Mideas.getDisplayXFactor(), this.y_frame+this.BROWSE_SEARCH_EDIT_BOX_Y*Mideas.getDisplayYFactor());
		this.browseMinLevelEditBox.update(this.x_frame+this.BROWSE_MIN_LEVEL_EDIT_BOX_X*Mideas.getDisplayXFactor(), this.y_frame+this.BROWSE_SEARCH_EDIT_BOX_Y*Mideas.getDisplayYFactor());
		this.browseMaxLevelEditBox.update(this.x_frame+this.BROWSE_MAX_LEVEL_EDIT_BOX_X*Mideas.getDisplayXFactor(), this.y_frame+this.BROWSE_SEARCH_EDIT_BOX_Y*Mideas.getDisplayYFactor());
		this.browseRarityDropDownmenu.update(this.x_frame+this.BROWSE_QUALITY_FILTER_DROP_DOWN_X*Mideas.getDisplayXFactor(), this.y_frame+this.BROWSE_QUALITY_FILTER_DROP_DOWN_Y*Mideas.getDisplayXFactor(), this.BROWSE_QUALITY_FILTER_DROP_DOWN_BAR_WIDTH*Mideas.getDisplayXFactor(), this.x_frame+this.BROWSE_QUALITY_FILTER_DROP_DOWN_X*Mideas.getDisplayXFactor(), this.y_frame+(this.BROWSE_QUALITY_FILTER_DROP_DOWN_Y+20)*Mideas.getDisplayYFactor(), this.BROWSE_QUALITY_FILTER_DROP_DOWN_MENU_WIDTH*Mideas.getDisplayXFactor());
		int i = -1;
		while(++i < this.queryButtonList.size()) {
			this.queryButtonList.get(i).updateSize();
		}
		this.shouldUpdate = false;
	}
	
	protected void unexpandAllCategoryFilter() {
		int i = -1;
		while(++i < this.browseCategoryList.size()) {
			this.browseCategoryList.get(i).unexpand();
		}
	}
	
	public boolean hasSearched() {
		return this.hasSearched;
	}
	
	public void setHasSearched(boolean we) {
		this.hasSearched = we;
	}
	
	public void setSelectedBrowseEntry(AuctionEntry entry) {
		this.browseSelectedEntry = entry;
	}
	
	public AuctionEntry getSelectedBrowseEntry() {
		return this.browseSelectedEntry;
	}
	
	public void shouldUpdate() {
		this.shouldUpdate = true;
	}
	
	public void queryReceived() {
		this.querySent = false;
	}
	
	protected void checkBrowseFilterScrollbar() {
		int count = 0;
		int i = -1;
		while(++i < this.browseCategoryList.size()) {
			count+= this.browseCategoryList.get(i).getNumberLine();
		}
		this.browserFilterNumberLine = (byte)count;
		this.browseFilterScrollbar.onScroll();
		if(this.browseFilterScrollbarEnabled) {
			if(count > 15) {
				return;
			}
			hideBrowseFilterScrollbar();
			this.browseFilterScrollbarEnabled = false;
		}
		else if(!this.browseFilterScrollbarEnabled) {
			if(count < 15) {
				return;
			}
			showBrowseFilterScrollbar();
			this.browseFilterScrollbarEnabled = true;
		}
	}
	
	private void hideBrowseFilterScrollbar() {
		this.browseFilterWidth = (short)(this.BROWSE_FILTER_WIDTH_NO_SCROLLBAR*Mideas.getDisplayXFactor());
	}
	
	private void showBrowseFilterScrollbar() {
		this.browseFilterWidth = (short)(this.BROWSE_FILTER_WIDTH_SCROLLBAR*Mideas.getDisplayXFactor());
	}
	
	private void buildBrowseFilterQualityMenu() {
		this.browseRarityDropDownmenu.resetMenuList();
		this.browseRarityDropDownmenu.addMenu(this.browseRarityAllTextMenu);
		this.browseRarityDropDownmenu.addMenu(this.browseRarityPoorTextMenu);
		this.browseRarityDropDownmenu.addMenu(this.browseRarityCommonTextMenu);
		this.browseRarityDropDownmenu.addMenu(this.browseRarityUncommonTextMenu);
		this.browseRarityDropDownmenu.addMenu(this.browseRarityRareTextMenu);
		this.browseRarityDropDownmenu.addMenu(this.browseRarityEpicTextMenu);
		this.browseRarityDropDownmenu.addMenu(this.browseRarityLegendaryTextMenu);
	}
	
	private void buildBrowseFilterMenu() {
		this.browseCategoryList.add(new AuctionCategoryFilterButton(this, AuctionHouseFilter.WEAPON));
		this.browseCategoryList.get(0).addFilter(AuctionHouseFilter.WEAPON_ONE_HANDED_AXE);
		this.browseCategoryList.get(0).addFilter(AuctionHouseFilter.WEAPON_TWO_HANDED_AXE);
		this.browseCategoryList.get(0).addFilter(AuctionHouseFilter.WEAPON_BOW);
		this.browseCategoryList.get(0).addFilter(AuctionHouseFilter.WEAPON_GUN);
		this.browseCategoryList.get(0).addFilter(AuctionHouseFilter.WEAPON_ONE_HANDED_MACE);
		this.browseCategoryList.get(0).addFilter(AuctionHouseFilter.WEAPON_TWO_HANDED_MACE);
		this.browseCategoryList.get(0).addFilter(AuctionHouseFilter.WEAPON_POLEARM);
		this.browseCategoryList.get(0).addFilter(AuctionHouseFilter.WEAPON_ONE_HANDED_SWORD);
		this.browseCategoryList.get(0).addFilter(AuctionHouseFilter.WEAPON_TWO_HANDED_SWORD);
		this.browseCategoryList.get(0).addFilter(AuctionHouseFilter.WEAPON_STAFF);
		this.browseCategoryList.get(0).addFilter(AuctionHouseFilter.WEAPON_FIST_WEAPON);
		this.browseCategoryList.get(0).addFilter(AuctionHouseFilter.WEAPON_MISCELLANEOUS);
		this.browseCategoryList.get(0).addFilter(AuctionHouseFilter.WEAPON_DAGGERS);
		this.browseCategoryList.get(0).addFilter(AuctionHouseFilter.WEAPON_THROW_WEAPON);
		this.browseCategoryList.get(0).addFilter(AuctionHouseFilter.WEAPON_CROSSBOW);
		this.browseCategoryList.get(0).addFilter(AuctionHouseFilter.WEAPON_WAND);
		this.browseCategoryList.get(0).addFilter(AuctionHouseFilter.WEAPON_FISHING_POLES);

		this.browseCategoryList.add(new AuctionCategoryFilterButton(this, AuctionHouseFilter.ARMOR));
		this.browseCategoryList.get(1).addFilter(AuctionHouseFilter.ARMOR_MISCELLANEOUS);
		this.browseCategoryList.get(1).addFilter(AuctionHouseFilter.ARMOR_CLOTH);
		this.browseCategoryList.get(1).addFilter(AuctionHouseFilter.ARMOR_LEATHER);
		this.browseCategoryList.get(1).addFilter(AuctionHouseFilter.ARMOR_MAIL);
		this.browseCategoryList.get(1).addFilter(AuctionHouseFilter.ARMOR_PLATE);
		this.browseCategoryList.get(1).addFilter(AuctionHouseFilter.ARMOR_SHIELDS);
		this.browseCategoryList.get(1).addFilter(AuctionHouseFilter.ARMOR_LIBRAMS);
		this.browseCategoryList.get(1).addFilter(AuctionHouseFilter.ARMOR_IDOLS);
		this.browseCategoryList.get(1).addFilter(AuctionHouseFilter.ARMOR_TOTEMS);

		this.browseCategoryList.add(new AuctionCategoryFilterButton(this, AuctionHouseFilter.CONTAINER));
		this.browseCategoryList.get(2).addFilter(AuctionHouseFilter.CONTAINER_BAG);
		this.browseCategoryList.get(2).addFilter(AuctionHouseFilter.CONTAINER_SOUL_BAG);
		this.browseCategoryList.get(2).addFilter(AuctionHouseFilter.CONTAINER_HERB_BAG);
		this.browseCategoryList.get(2).addFilter(AuctionHouseFilter.CONTAINER_ENCHANTING_BAG);
		this.browseCategoryList.get(2).addFilter(AuctionHouseFilter.CONTAINER_ENGINEERING_BAG);
		this.browseCategoryList.get(2).addFilter(AuctionHouseFilter.CONTAINER_GEM_BAG);
		this.browseCategoryList.get(2).addFilter(AuctionHouseFilter.CONTAINER_MINING_BAG);
		this.browseCategoryList.get(2).addFilter(AuctionHouseFilter.CONTAINER_LEATHERWORKING_BAG);

		this.browseCategoryList.add(new AuctionCategoryFilterButton(this, AuctionHouseFilter.CONSUMABLES));
		this.browseCategoryList.get(3).addFilter(AuctionHouseFilter.CONSUMABLES_FOOD_AND_DRINK);
		this.browseCategoryList.get(3).addFilter(AuctionHouseFilter.CONSUMABLES_POTION);
		this.browseCategoryList.get(3).addFilter(AuctionHouseFilter.CONSUMABLES_ELIXIR);
		this.browseCategoryList.get(3).addFilter(AuctionHouseFilter.CONSUMABLES_FLASK);
		this.browseCategoryList.get(3).addFilter(AuctionHouseFilter.CONSUMABLES_BANDAGE);
		this.browseCategoryList.get(3).addFilter(AuctionHouseFilter.CONSUMABLES_ITEMS_ENCHANCEMENTS);
		this.browseCategoryList.get(3).addFilter(AuctionHouseFilter.CONSUMABLES_PARCHEMIN);
		this.browseCategoryList.get(3).addFilter(AuctionHouseFilter.CONSUMABLES_MISCELLANEOUS);

		this.browseCategoryList.add(new AuctionCategoryFilterButton(this, AuctionHouseFilter.TRADE_GOOD));
		this.browseCategoryList.get(4).addFilter(AuctionHouseFilter.TRADE_GOOD_ELEMENTAL);
		this.browseCategoryList.get(4).addFilter(AuctionHouseFilter.TRADE_GOOD_CLOTH);
		this.browseCategoryList.get(4).addFilter(AuctionHouseFilter.TRADE_GOOD_LEATHER);
		this.browseCategoryList.get(4).addFilter(AuctionHouseFilter.TRADE_GOOD_METAL_AND_STONE);
		this.browseCategoryList.get(4).addFilter(AuctionHouseFilter.TRADE_GOOD_COOKING);
		this.browseCategoryList.get(4).addFilter(AuctionHouseFilter.TRADE_GOOD_HERB);
		this.browseCategoryList.get(4).addFilter(AuctionHouseFilter.TRADE_GOOD_ENCHANTING);
		this.browseCategoryList.get(4).addFilter(AuctionHouseFilter.TRADE_GOOD_JEWELCRAFTING);
		this.browseCategoryList.get(4).addFilter(AuctionHouseFilter.TRADE_GOOD_PARTS);
		this.browseCategoryList.get(4).addFilter(AuctionHouseFilter.TRADE_GOOD_MATERIALS);
		this.browseCategoryList.get(4).addFilter(AuctionHouseFilter.TRADE_GOOD_OTHER);

		this.browseCategoryList.add(new AuctionCategoryFilterButton(this, AuctionHouseFilter.PROJECTILE));
		this.browseCategoryList.get(5).addFilter(AuctionHouseFilter.PROJECTILE_ARROW);
		this.browseCategoryList.get(5).addFilter(AuctionHouseFilter.PROJECTILE_BULLET);
		
		this.browseCategoryList.add(new AuctionCategoryFilterButton(this, AuctionHouseFilter.QUIVER));
		this.browseCategoryList.get(6).addFilter(AuctionHouseFilter.QUIVER_QUIVER);
		this.browseCategoryList.get(6).addFilter(AuctionHouseFilter.QUIVER_GIBERNE);

		this.browseCategoryList.add(new AuctionCategoryFilterButton(this, AuctionHouseFilter.RECIPES));
		this.browseCategoryList.get(7).addFilter(AuctionHouseFilter.RECIPES_BOOK);
		this.browseCategoryList.get(7).addFilter(AuctionHouseFilter.RECIPES_LEATHERWORKING);
		this.browseCategoryList.get(7).addFilter(AuctionHouseFilter.RECIPES_TAILORING);
		this.browseCategoryList.get(7).addFilter(AuctionHouseFilter.RECIPES_ENGINEERING);
		this.browseCategoryList.get(7).addFilter(AuctionHouseFilter.RECIPES_BLACKSMITHING);
		this.browseCategoryList.get(7).addFilter(AuctionHouseFilter.RECIPES_COOKING);
		this.browseCategoryList.get(7).addFilter(AuctionHouseFilter.RECIPES_ALCHEMY);
		this.browseCategoryList.get(7).addFilter(AuctionHouseFilter.RECIPES_FIRST_AID);
		this.browseCategoryList.get(7).addFilter(AuctionHouseFilter.RECIPES_ENCHANTING);
		this.browseCategoryList.get(7).addFilter(AuctionHouseFilter.RECIPES_FISHING);
		this.browseCategoryList.get(7).addFilter(AuctionHouseFilter.RECIPES_JEWELCRAFTING);

		this.browseCategoryList.add(new AuctionCategoryFilterButton(this, AuctionHouseFilter.GEM));
		this.browseCategoryList.get(8).addFilter(AuctionHouseFilter.GEM_RED);
		this.browseCategoryList.get(8).addFilter(AuctionHouseFilter.GEM_BLUE);
		this.browseCategoryList.get(8).addFilter(AuctionHouseFilter.GEM_YELLOW);
		this.browseCategoryList.get(8).addFilter(AuctionHouseFilter.GEM_PURPLE);
		this.browseCategoryList.get(8).addFilter(AuctionHouseFilter.GEM_GREEN);
		this.browseCategoryList.get(8).addFilter(AuctionHouseFilter.GEM_ORANGE);
		this.browseCategoryList.get(8).addFilter(AuctionHouseFilter.GEM_META);
		this.browseCategoryList.get(8).addFilter(AuctionHouseFilter.GEM_SIMPLE);
		this.browseCategoryList.get(8).addFilter(AuctionHouseFilter.GEM_PRISMATIC);

		this.browseCategoryList.add(new AuctionCategoryFilterButton(this, AuctionHouseFilter.MISCELLANEOUS));
		this.browseCategoryList.get(9).addFilter(AuctionHouseFilter.MISCELLANEOUS_JUNK);
		this.browseCategoryList.get(9).addFilter(AuctionHouseFilter.MISCELLANEOUS_REAGENT);
		this.browseCategoryList.get(9).addFilter(AuctionHouseFilter.MISCELLANEOUS_COMPANION_PETS);
		this.browseCategoryList.get(9).addFilter(AuctionHouseFilter.MISCELLANEOUS_HOLIDAY);
		this.browseCategoryList.get(9).addFilter(AuctionHouseFilter.MISCELLANEOUS_OTHER);
		this.browseCategoryList.get(9).addFilter(AuctionHouseFilter.MISCELLANEOUS_MOUNT);

		this.browseCategoryList.add(new AuctionCategoryFilterButton(this, AuctionHouseFilter.QUESTS));
	}
	
	protected void buildResultString(byte result, int totalResult, short page) {
		this.browseNumberTotalResult = totalResult;
		this.browsePage = page;
		if(result == 0 ) {
			return;
		}
		this.browseResultString = "Items "+(1+page*50)+" - "+(page*50)+" ( "+totalResult+" total )";
	}
	
	protected void frameClosed() {
		Mideas.joueur1().getAuctionHouse().clearQueryList();
		this.hasSearched = false;
	}
	
	protected AuctionHouseFilter getSelectedFilter() {
		return this.selectedFilter;
	}
	
	protected void setSelectedFilter(AuctionHouseFilter filter) {
		this.selectedFilter = filter;
	}
	
	protected AuctionHouseFilter getSelectedCategoryFilter() {
		return this.selectedCategoryFilter;
	}
	
	protected void setSelectedCategoryFilter(AuctionHouseFilter filter) {
		this.selectedCategoryFilter = filter;
	}
	
	protected void incrementBrowseFilterY() {
		this.browseFilterY+= this.browseFilterYShift;
	}
	
	protected short getBrowseItemX() {
		return this.browseItemX;
	}
	
	protected short getBrowseItemWidth() {
		return this.browseItemWidth;
	}
	
	protected short getBrowseItemY() {
		return this.browseItemY;
	}
	
	protected short getBrowseItemHeight() {
		return this.browseItemHeight;
	}
	
	protected void incrementBrowseItemY() {
		this.browseItemY+= this.browseItemYShift;
	}
	
	protected short getBrowseFilterY() {
		return this.browseFilterY;
	}
	
	protected short getBrowseFilterX() {
		return this.browseFilterX;
	}
	
	protected short getBrowseFilterYShift() {
		return this.browseFilterYShift;
	}
	
	protected short getBrowseFilterWidth() {
		return this.browseFilterWidth;
	}
	
	protected short getBrowseFilterHeight() {
		return this.browseFilterHeight;
	}
}
