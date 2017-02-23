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
import com.mideas.rpg.v2.utils.Arrow;
import com.mideas.rpg.v2.utils.ArrowDirection;
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
	private final short BROWSE_ITEM_SCROLLBAR_X = 794;
	private final short BROWSE_ITEM_SCROLLBAR_WIDTH = 640;
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
	private final short BROWSE_SORT_PRICE_PER_UNIT_BUTTON_WIDTH_SCROLLBAR = 184;
	private final short BROWSE_SORT_PRICE_PER_UNIT_BUTTON_WIDTH_NO_SCROLLBAR = 207;
	private final short BROWSE_SEND_QUERY_BUTTON_X = 659;
	private final short BROWSE_RESET_BUTTON_X = 744;
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
	private final short BROWSE_QUERY_BUTTON_SCROLLBAR_WIDTH = 566;
	private final short BROWSE_ITEM_Y = 108;
	private final short BROWSE_ITEM_Y_SHIFT = 37;
	private final short BROWSE_ITEM_HEIGHT = 40;
	private final short BROWSE_ITEM_X = 194;
	private final short BROWSE_BID_COPPER_EDIT_BOX_X = 437;
	private final short BROWSE_BID_SILVER_EDIT_BOX_X = 389;
	private final short BROWSE_BID_GOLD_EDIT_BOX_X = 309;
	private final short BROWSE_BID_EDIT_BOX_Y = 409;
	private final short BROWSE_BID_COPPER_EDIT_BOX_WIDTH = 43;
	private final short BROWSE_BID_GOLD_EDIT_BOX_WIDTH = 75;
	private final short BROWSE_NEXT_PAGE_ARROW_X = 796;
	private final short BROWSE_PREVIOUS_PAGE_ARROW_X = 700;
	private final short BROWSE_PAGE_ARROW_Y = 54;
	
	
	private AuctionFrameTab selectedTab = AuctionFrameTab.BROWSE;
	private final ArrayList<AuctionCategoryFilterButton> browseCategoryList;
	private final ArrayList<AuctionBrowseQueryButton> queryButtonList;
	AuctionEntry browseSelectedEntry;
	AuctionHouseFilter selectedFilter = AuctionHouseFilter.NONE;
	AuctionHouseFilter selectedCategoryFilter = AuctionHouseFilter.NONE;
	AuctionHouseSort selectedSort = AuctionHouseSort.LEVEL_ASCENDING;
	AuctionHouseQualityFilter qualityFilter = AuctionHouseQualityFilter.ALL;
	protected final TTF browseFilterFont;
	protected final TTF browseSortFont = FontManager.get("FRIZQT", 11);
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
	short browsePage = 1;
	short browseTotalPage;
	private String browseResultString;
	private short browseResultStringWidth;
	boolean querySent;
	final ScrollBar browseFilterScrollbar = new ScrollBar(this.x_frame+this.BROWSE_FILTER_SCROLLBAR_X*Mideas.getDisplayXFactor(), this.y_frame+this.BROWSE_FILTER_SCROLLBAR_Y*Mideas.getDisplayYFactor(), this.BROWSE_FILTER_SCROLLBAR_HEIGHT*Mideas.getDisplayYFactor(), this.BROWSE_FILTER_SCROLLBAR_WIDTH*Mideas.getDisplayXFactor(), this.BROWSE_FILTER_SCROLLBAR_HEIGHT*Mideas.getDisplayYFactor(), false, this.BROWSE_FILTER_HEIGHT*Mideas.getDisplayYFactor()) {
		
		@Override
		public void onScroll() {
			AuctionFrameUI.this.browseFilterScrollbarOffset = (short)(AuctionFrameUI.this.BROWSE_FILTER_HEIGHT*Mideas.getDisplayYFactor()*(AuctionFrameUI.this.browserFilterNumberLine-15)*getScrollPercentage());
		}
	};
	final ScrollBar browseItemScrollbar = new ScrollBar(this.x_frame+this.BROWSE_ITEM_SCROLLBAR_X*Mideas.getDisplayXFactor(), this.y_frame+this.BROWSE_ITEM_SCROLLBAR_Y*Mideas.getDisplayYFactor(), this.BROWSE_ITEM_SCROLLBAR_HEIGHT*Mideas.getDisplayYFactor(), this.BROWSE_ITEM_SCROLLBAR_WIDTH*Mideas.getDisplayXFactor(), (this.BROWSE_ITEM_SCROLLBAR_HEIGHT+15)*Mideas.getDisplayYFactor(), false, this.browseItemYShift*Mideas.getDisplayYFactor()) {
		
		@Override
		public void onScroll() {
			AuctionFrameUI.this.browseItemStartIndex = (short)((Mideas.joueur1().getAuctionHouse().getQueryList().size()+1-AuctionFrameUI.this.BROWSE_MAXIMUM_ITEM_DISPLAYED)*getScrollPercentage());
		}
	};
	private final Button browseBidButton = new Button(this.x_frame+this.BROWSE_BID_BUTTON_X*Mideas.getDisplayXFactor(), this.y_frame+this.BROWSE_CLOSE_BUTTON_Y*Mideas.getDisplayYFactor(), this.BROWSE_BUTTON_WIDTH*Mideas.getDisplayXFactor(), this.BROWSE_BUTTON_HEIGHT*Mideas.getDisplayYFactor(), "Bid", 12, 1) {
	
		@Override
		public void eventButtonClick() {
			CommandAuction.makeABid(AuctionFrameUI.this.browseSelectedEntry, AuctionFrameUI.this.browseGoldBidEditBox.getValue()*10000+AuctionFrameUI.this.browseSilverBidEditBox.getValue()*100+AuctionFrameUI.this.browseCopperBidEditBox.getValue());
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
			return AuctionFrameUI.this.browseSelectedEntry != null && AuctionFrameUI.this.browseSelectedEntry.canBeBuy();
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
			AuctionFrameUI.this.browsePage = 1;
			sendSearchQuery();
		}
		
		@Override
		public boolean activateCondition() {
			return !AuctionFrameUI.this.querySent;
		}
	};
	private final Button browseResetButton = new Button(this.x_frame+this.BROWSE_RESET_BUTTON_X*Mideas.getDisplayXFactor(), this.y_frame+this.BROWSE_SEND_QUERY_BUTTON_Y*Mideas.getDisplayYFactor(), this.BROWSE_SEND_QUERY_BUTTON_WIDTH*Mideas.getDisplayXFactor(), this.BROWSE_SEND_QUERY_BUTTON_HEIGHT*Mideas.getDisplayYFactor(), "Reset", 12, 1) {
	
		@Override
		public void eventButtonClick() {
			resetBrowseOptions();
		}
		
		@Override
		public boolean activateCondition() {
			return AuctionFrameUI.this.selectedCategoryFilter != AuctionHouseFilter.NONE || AuctionFrameUI.this.selectedFilter != AuctionHouseFilter.NONE || AuctionFrameUI.this.qualityFilter != AuctionHouseQualityFilter.ALL || AuctionFrameUI.this.browseSearchEditBox.getText().length() != 0 || AuctionFrameUI.this.browseMinLevelEditBox.getText().length() != 0 || AuctionFrameUI.this.browseMaxLevelEditBox.getText().length() != 0;
		}
	};
	private final Arrow browseNextPageArrow = new Arrow(this.x_frame+this.BROWSE_NEXT_PAGE_ARROW_X*Mideas.getDisplayXFactor(), this.Y_FRAME+this.BROWSE_PAGE_ARROW_Y*Mideas.getDisplayYFactor(), 27, 26, ArrowDirection.RIGHT) {
		
		@Override
		public void eventButtonClick() {
			AuctionFrameUI.this.browsePage++;
			sendSearchQuery();
		}
		
		@Override
		public boolean activateCondition() {
			return !AuctionFrameUI.this.querySent && AuctionFrameUI.this.browsePage < AuctionFrameUI.this.browseTotalPage;
		}
	};
	private final Arrow browsePreviousPageArrow = new Arrow(this.x_frame+this.BROWSE_PREVIOUS_PAGE_ARROW_X*Mideas.getDisplayXFactor(), this.Y_FRAME+this.BROWSE_PAGE_ARROW_Y*Mideas.getDisplayYFactor(), 27, 26, ArrowDirection.LEFT) {
		
		@Override
		public void eventButtonClick() {
			AuctionFrameUI.this.browsePage--;
			sendSearchQuery();
		}
		
		@Override
		public boolean activateCondition() {
			return !AuctionFrameUI.this.querySent && AuctionFrameUI.this.browsePage > 1;
		}
	};
	final EditBox browseSearchEditBox = new EditBox(this.x_frame+this.BROWSE_SEARCH_EDIT_BOX_X*Mideas.getDisplayXFactor(), this.y_frame+this.BROWSE_SEARCH_EDIT_BOX_Y*Mideas.getDisplayYFactor(), this.BROWSE_SEARCH_EDIT_BOX_WIDTH*Mideas.getDisplayXFactor(), 63, 21, this.BROWSE_SEARCH_EDIT_BOX_INPUT_WIDTH*Mideas.getDisplayXFactor(), FontManager.get("FRIZQT", 11), false, 2, 13, "Search", true) {
		
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
			if(c == Input.TAB_CHAR_VALUE) {
				AuctionFrameUI.this.browseMinLevelEditBox.setActive(true);
				return true;
			}
			return false;
		}
	};
	final EditBox browseMinLevelEditBox = new EditBox(this.x_frame+this.BROWSE_MIN_LEVEL_EDIT_BOX_X*Mideas.getDisplayXFactor(), this.y_frame+this.BROWSE_SEARCH_EDIT_BOX_Y*Mideas.getDisplayYFactor(), this.BROWSE_LEVEL_EDIT_BOX_WIDTH, 3, 5, this.BROWSE_LEVEL_EDIT_BOX_INPUT_WIDTH, FontManager.get("FRIZQT", 11), true) {
		
		@Override
		public boolean keyEvent(char c) {
			if(c == Input.ENTER_CHAR_VALUE || c == Input.ESCAPE_CHAR_VALUE) {
				this.input.setIsActive(false);
				return true;
			}
			if(c == Input.TAB_CHAR_VALUE) {
				AuctionFrameUI.this.browseMaxLevelEditBox.setActive(true);
				System.out.println('a');
				return true;
			}
			return false;
		}
		
		@Override
		public boolean checkValue(int value) {
			return value <= 999;
		}
	};
	final EditBox browseMaxLevelEditBox = new EditBox(this.x_frame+this.BROWSE_MAX_LEVEL_EDIT_BOX_X*Mideas.getDisplayXFactor(), this.y_frame+this.BROWSE_SEARCH_EDIT_BOX_Y*Mideas.getDisplayYFactor(), this.BROWSE_LEVEL_EDIT_BOX_WIDTH, 3, 5, this.BROWSE_LEVEL_EDIT_BOX_INPUT_WIDTH, FontManager.get("FRIZQT", 11), true) {
		
		@Override
		public boolean keyEvent(char c) {
			if(c == Input.ENTER_CHAR_VALUE || c == Input.ESCAPE_CHAR_VALUE) {
				this.input.setIsActive(false);
				return true;
			}
			if(c == Input.TAB_CHAR_VALUE) {
				AuctionFrameUI.this.browseGoldBidEditBox.setActive(true);
				return true;
			}
			return false;
		}
		
		@Override
		public boolean checkValue(int value) {
			return value <= 999;
		}
	};
	final EditBox browseGoldBidEditBox = new EditBox(this.x_frame+this.BROWSE_BID_GOLD_EDIT_BOX_X*Mideas.getDisplayXFactor(), this.y_frame+this.BROWSE_BID_EDIT_BOX_Y*Mideas.getDisplayYFactor(), this.BROWSE_BID_GOLD_EDIT_BOX_WIDTH, 6, 5, this.BROWSE_BID_GOLD_EDIT_BOX_WIDTH, FontManager.get("FRIZQT", 11), true) {
		
		@Override
		public boolean keyEvent(char c) {
			if(c == Input.ESCAPE_CHAR_VALUE) {
				this.input.setIsActive(false);
				return true;
			}
			if(c == Input.TAB_CHAR_VALUE || c == Input.ENTER_CHAR_VALUE) {
				AuctionFrameUI.this.browseSilverBidEditBox.setActive(true);
				return true;
			}
			return false;
		}
		
		@Override
		public boolean checkValue(int value) {
			return value <= 999999;
		}
	};
	final EditBox browseSilverBidEditBox = new EditBox(this.x_frame+this.BROWSE_BID_SILVER_EDIT_BOX_X*Mideas.getDisplayXFactor(), this.y_frame+this.BROWSE_BID_EDIT_BOX_Y*Mideas.getDisplayYFactor(), this.BROWSE_BID_COPPER_EDIT_BOX_WIDTH, 2, 5, this.BROWSE_BID_COPPER_EDIT_BOX_WIDTH, FontManager.get("FRIZQT", 11), true) {
		
		@Override
		public boolean keyEvent(char c) {
			if(c == Input.ESCAPE_CHAR_VALUE) {
				this.input.setIsActive(false);
				return true;
			}
			if(c == Input.TAB_CHAR_VALUE || c == Input.ENTER_CHAR_VALUE) {
				AuctionFrameUI.this.browseCopperBidEditBox.setActive(true);
				return true;
			}
			return false;
		}
		
		@Override
		public boolean checkValue(int value) {
			return value <= 99;
		}
	};
	final EditBox browseCopperBidEditBox = new EditBox(this.x_frame+this.BROWSE_BID_COPPER_EDIT_BOX_X*Mideas.getDisplayXFactor(), this.y_frame+this.BROWSE_BID_EDIT_BOX_Y*Mideas.getDisplayYFactor(), this.BROWSE_BID_COPPER_EDIT_BOX_WIDTH, 2, 5, this.BROWSE_BID_COPPER_EDIT_BOX_WIDTH, FontManager.get("FRIZQT", 11), true) {
		
		@Override
		public boolean keyEvent(char c) {
			if(c == Input.ESCAPE_CHAR_VALUE) {
				this.input.setIsActive(false);
				return true;
			}
			if(c == Input.TAB_CHAR_VALUE || c == Input.ENTER_CHAR_VALUE) {
				AuctionFrameUI.this.browseSearchEditBox.setActive(true);
				return true;
			}
			return false;
		}
		
		@Override
		public boolean checkValue(int value) {
			return value <= 99;
		}
	};
	private final ButtonMenuSort browseSortByRarityButton = new ButtonMenuSort(this.x_frame+this.BROWSE_SORT_RARITY_BUTTON_X*Mideas.getDisplayXFactor(), this.y_frame+this.BROWSE_SORT_BUTTON_Y*Mideas.getDisplayYFactor(), this.BROWSE_SORT_RARITY_BUTTON_WIDTH*Mideas.getDisplayXFactor(), this.BROWSE_SORT_BUTTON_HEIGHT*Mideas.getDisplayYFactor(), "Rarity", this.browseSortFont) {
		
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
	private final ButtonMenuSort browseSortByLevelButton = new ButtonMenuSort(this.x_frame+this.BROWSE_SORT_LEVEL_BUTTON_X*Mideas.getDisplayXFactor(), this.y_frame+this.BROWSE_SORT_BUTTON_Y*Mideas.getDisplayYFactor(), this.BROWSE_SORT_LEVEL_BUTTON_WIDTH*Mideas.getDisplayXFactor(), this.BROWSE_SORT_BUTTON_HEIGHT*Mideas.getDisplayYFactor(), "Lvl", this.browseSortFont) {
		
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
	private final ButtonMenuSort browseSortByTimeLeftButton = new ButtonMenuSort(this.x_frame+this.BROWSE_SORT_TIME_LEFT_BUTTON_X*Mideas.getDisplayXFactor(), this.y_frame+this.BROWSE_SORT_BUTTON_Y*Mideas.getDisplayYFactor(), this.BROWSE_SORT_TIME_LEFT_BUTTON_WIDTH*Mideas.getDisplayXFactor(), this.BROWSE_SORT_BUTTON_HEIGHT*Mideas.getDisplayYFactor(), "Time Left", this.browseSortFont) {
		
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
	private final ButtonMenuSort browseSortBySellerButton = new ButtonMenuSort(this.x_frame+this.BROWSE_SORT_SELLER_BUTTON_X*Mideas.getDisplayXFactor(), this.y_frame+this.BROWSE_SORT_BUTTON_Y*Mideas.getDisplayYFactor(), this.BROWSE_SORT_SELLER_BUTTON_WIDTH*Mideas.getDisplayXFactor(), this.BROWSE_SORT_BUTTON_HEIGHT*Mideas.getDisplayYFactor(), "Seller", this.browseSortFont) {
		
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
	private final ButtonMenuSort browseSortByPricePerUnitButton = new ButtonMenuSort(this.x_frame+this.BROWSE_SORT_PRICE_PER_UNIT_BUTTON_X*Mideas.getDisplayXFactor(), this.y_frame+this.BROWSE_SORT_BUTTON_Y*Mideas.getDisplayYFactor(), this.BROWSE_SORT_PRICE_PER_UNIT_BUTTON_WIDTH_NO_SCROLLBAR*Mideas.getDisplayXFactor(), this.BROWSE_SORT_BUTTON_HEIGHT*Mideas.getDisplayYFactor(), "Price Per Unit", this.browseSortFont) {
		
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
		this.browseItemScrollbar.resetScroll();
		CommandAuction.sendQuery(filter, this.selectedSort, this.qualityFilter, this.browseSearchEditBox.getText(), this.browsePage, false, (short)this.browseMinLevelEditBox.getValue(), (short)this.browseMaxLevelEditBox.getValue(), false);
		this.hasSearched = true;
		this.querySent = true;
	}
	
	void resetBrowseOptions() {
		int i = this.browseCategoryList.size();
		while(--i >= 0) {
			this.browseCategoryList.get(i).unexpand();
		}
		this.browseFilterScrollbar.resetScroll();
		checkBrowseFilterScrollbar();
		this.browseSearchEditBox.resetText();
		this.browseMinLevelEditBox.resetText();
		this.browseMaxLevelEditBox.resetText();
		this.browseRarityDropDownmenu.setValue(0);
		this.qualityFilter = AuctionHouseQualityFilter.ALL;
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
		else {
			System.out.println("AuctionFrameUI.draw error, no tab selected");
		}
	}
	
	private void drawBrowseFrame() {
		Draw.drawQuad(Sprites.auction_browse_frame, this.x_frame, this.y_frame);
		this.browseBidButton.draw();
		this.browseBuyoutButton.draw();
		this.browseClose.draw();
		
		Sprites.button_menu_sort.drawBegin();
		this.browseSortByRarityButton.drawTexturePart();
		this.browseSortByLevelButton.drawTexturePart();
		this.browseSortByTimeLeftButton.drawTexturePart();
		this.browseSortBySellerButton.drawTexturePart();
		this.browseSortByPricePerUnitButton.drawTexturePart();
		Sprites.button_menu_sort.drawEnd();

		this.browseSortFont.drawBegin();
		this.browseSortByRarityButton.drawStringPart();
		this.browseSortByLevelButton.drawStringPart();
		this.browseSortByTimeLeftButton.drawStringPart();
		this.browseSortBySellerButton.drawStringPart();
		this.browseSortByPricePerUnitButton.drawStringPart();
		this.browseSortFont.drawEnd();
		
		Sprites.button_menu_hover.drawBlendBegin();
		this.browseSortByRarityButton.drawHoverPart();
		this.browseSortByLevelButton.drawHoverPart();
		this.browseSortByTimeLeftButton.drawHoverPart();
		this.browseSortBySellerButton.drawHoverPart();
		this.browseSortByPricePerUnitButton.drawHoverPart();
		Sprites.button_menu_hover.drawBlendEnd();
		
		/*this.browseSortByRarityButton.draw();
		this.browseSortByLevelButton.draw();
		this.browseSortByTimeLeftButton.draw();
		this.browseSortBySellerButton.draw();
		this.browseSortByPricePerUnitButton.draw();*/
		this.browseSendQueryButton.draw();
		/*this.browseSearchEditBox.draw();
		this.browseMinLevelEditBox.draw();
		this.browseMaxLevelEditBox.draw();
		this.browseGoldBidEditBox.draw();
		this.browseSilverBidEditBox.draw();
		this.browseCopperBidEditBox.draw();*/
		Sprites.edit_box.drawBegin();
		this.browseSearchEditBox.drawTexturePart();
		this.browseMinLevelEditBox.drawTexturePart();
		this.browseMaxLevelEditBox.drawTexturePart();
		this.browseGoldBidEditBox.drawTexturePart();
		this.browseSilverBidEditBox.drawTexturePart();
		this.browseCopperBidEditBox.drawTexturePart();
		Sprites.edit_box.drawEnd();
		this.browseSearchEditBox.drawString();
		this.browseMinLevelEditBox.drawString();
		this.browseMaxLevelEditBox.drawString();
		this.browseGoldBidEditBox.drawString();
		this.browseSilverBidEditBox.drawString();
		this.browseCopperBidEditBox.drawString();
		this.browseResetButton.draw();
		this.browseNextPageArrow.draw();
		this.browsePreviousPageArrow.draw();
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
		Sprites.copper_coin.drawBegin();
		Draw.drawQuadPart(Sprites.copper_coin, this.x_frame+153*Mideas.getDisplayXFactor(), this.y_frame+412*Mideas.getDisplayYFactor(), 13*Mideas.getDisplayXFactor(), 13*Mideas.getDisplayYFactor());
		Draw.drawQuadPart(Sprites.copper_coin, this.x_frame+463*Mideas.getDisplayXFactor(), this.y_frame+413*Mideas.getDisplayYFactor(), 13*Mideas.getDisplayXFactor(), 13*Mideas.getDisplayYFactor());
		Sprites.copper_coin.drawEnd();
		Sprites.silver_coin.drawBegin();
		Draw.drawQuadPart(Sprites.silver_coin, this.x_frame+118*Mideas.getDisplayXFactor(), this.y_frame+412*Mideas.getDisplayYFactor(), 13*Mideas.getDisplayXFactor(), 13*Mideas.getDisplayYFactor());
		Draw.drawQuadPart(Sprites.silver_coin, this.x_frame+415*Mideas.getDisplayXFactor(), this.y_frame+413*Mideas.getDisplayYFactor(), 13*Mideas.getDisplayXFactor(), 13*Mideas.getDisplayYFactor());
		Sprites.silver_coin.drawEnd();
		Sprites.gold_coin.drawBegin();
		Draw.drawQuadPart(Sprites.gold_coin, this.x_frame+83*Mideas.getDisplayXFactor(), this.y_frame+412*Mideas.getDisplayYFactor(), 13*Mideas.getDisplayXFactor(), 13*Mideas.getDisplayYFactor());
		Draw.drawQuadPart(Sprites.gold_coin, this.x_frame+367*Mideas.getDisplayXFactor(), this.y_frame+413*Mideas.getDisplayYFactor(), 13*Mideas.getDisplayXFactor(), 13*Mideas.getDisplayYFactor());
		Sprites.gold_coin.drawEnd();
		drawBrowseFilter();
		drawBrowseItem();
		if(this.browseFilterScrollbarEnabled) {
			this.browseFilterScrollbar.draw();
		}
		if(this.browseItemScrollbarEnabled) {
			this.browseItemScrollbar.draw();
		}
		this.browseRarityDropDownmenu.draw();
	}
	
	public void updateQueryButtonList() {
		ArrayList<AuctionEntry> list = Mideas.joueur1().getAuctionHouse().getQueryList();
		short width;
		if(list.size() > 8) {
			width = this.BROWSE_QUERY_BUTTON_SCROLLBAR_WIDTH;
			enableBrowseItemScrollbar();
		}
		else {
			width = this.BROWSE_QUERY_BUTTON_NO_SCROLLBAR_WIDTH;
			disableBrowseItemScrollbar();
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
	
	public void noItemFound() {
		disableBrowseItemScrollbar();
		this.browsePage = 1;
		this.browseTotalPage = 0;
	}
	
	private void drawBrowseFilter() {
		int i = -1;
		Draw.glScissorBegin(0, 495*Mideas.getDisplayYFactor(), 500, 305*Mideas.getDisplayYFactor());
		this.browseFilterY = (short)(this.browseFilterYSave-this.browseFilterScrollbarOffset);
		Sprites.chat_channel_button.drawBegin();
		while(++i < this.browseCategoryList.size()) {
			this.browseCategoryList.get(i).drawTexturePart();
		}
		Sprites.chat_channel_button.drawEnd();
		i = -1;
		this.browseFilterY = (short)(this.browseFilterYSave-this.browseFilterScrollbarOffset);
		this.browseFilterFont.drawBegin();
		while(++i < this.browseCategoryList.size()) {
			this.browseCategoryList.get(i).drawStringPart();
		}
		this.browseFilterFont.drawEnd();
		i = -1;
		this.browseFilterY = (short)(this.browseFilterYSave-this.browseFilterScrollbarOffset);
		Sprites.button_menu_hover.drawBlendBegin();
		while(++i < this.browseCategoryList.size()) {
			this.browseCategoryList.get(i).drawHoverPart();
		}
		Sprites.button_menu_hover.drawBlendEnd();
		Draw.glScissorEnd();
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
		if(this.querySent) {
			return;
		}
		this.browseItemY = this.browseItemYSave;
		int i = this.browseItemStartIndex;
		int end = Math.min(this.browseItemStartIndex+this.BROWSE_MAXIMUM_ITEM_DISPLAYED, this.queryButtonList.size());
		while(i < end && this.queryButtonList.get(i).getEntry() != null) {
			this.queryButtonList.get(i).draw();
			incrementBrowseItemY();
			i++;
		}
		this.browseItemY = this.browseItemYSave;
		i = this.browseItemStartIndex;
		AuctionBrowseQueryButton.ITEM_NAME_FONT.drawBegin();
		while(i < end && this.queryButtonList.get(i).getEntry() != null) {
			this.queryButtonList.get(i).drawItemName();
			incrementBrowseItemY();
			i++;
		}
		AuctionBrowseQueryButton.ITEM_NAME_FONT.drawEnd();
		this.browseItemY = this.browseItemYSave;
		i = this.browseItemStartIndex;
		AuctionBrowseQueryButton.DURATION_FONT.drawBegin();
		while(i < end && this.queryButtonList.get(i).getEntry() != null) {
			this.queryButtonList.get(i).drawString();
			incrementBrowseItemY();
			i++;
		}
		AuctionBrowseQueryButton.DURATION_FONT.drawEnd();
		this.browseItemY = this.browseItemYSave;
		i = this.browseItemStartIndex;
		Sprites.gold_coin.drawBegin();
		while(i < end && this.queryButtonList.get(i).getEntry() != null) {
			this.queryButtonList.get(i).drawGoldTexture();
			incrementBrowseItemY();
			i++;
		}
		Sprites.gold_coin.drawEnd();
		this.browseItemY = this.browseItemYSave;
		i = this.browseItemStartIndex;
		Sprites.silver_coin.drawBegin();
		while(i < end && this.queryButtonList.get(i).getEntry() != null) {
			this.queryButtonList.get(i).drawSilverTexture();
			incrementBrowseItemY();
			i++;
		}
		Sprites.silver_coin.drawEnd();
		this.browseItemY = this.browseItemYSave;
		i = this.browseItemStartIndex;
		Sprites.copper_coin.drawBegin();
		while(i < end && this.queryButtonList.get(i).getEntry() != null) {
			this.queryButtonList.get(i).drawCopperTexture();
			incrementBrowseItemY();
			i++;
		}
		Sprites.copper_coin.drawEnd();
		this.browseItemY = this.browseItemYSave;
		i = this.browseItemStartIndex;
		AuctionBrowseQueryButton.GOLD_FONT.drawBegin();
		while(i < end && this.queryButtonList.get(i).getEntry() != null) {
			this.queryButtonList.get(i).drawGoldString();
			incrementBrowseItemY();
			i++;
		}
		AuctionBrowseQueryButton.GOLD_FONT.drawEnd();
		if(this.browseItemScrollbar.getScrollPercentage() == 1f) {
			FontManager.get("FRIZQT", 11).drawStringShadow(this.x_frame+(570-this.browseResultStringWidth)*Mideas.getDisplayXFactor(), this.y_frame+375*Mideas.getDisplayYFactor(), this.browseResultString, Color.WHITE, Color.BLACK, 1, 0, 0);
		}
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
		System.out.println("AuctionFrameUI.mouseEvent error, no tab selected");
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
		if(this.browseGoldBidEditBox.mouseEvent()) return true;
		if(this.browseSilverBidEditBox.mouseEvent()) return true;
		if(this.browseCopperBidEditBox.mouseEvent()) return true;
		if(this.browseResetButton.event()) return true;
		if(this.browseNextPageArrow.event()) return true;
		if(this.browsePreviousPageArrow.event()) return true;
		if(this.browseItemScrollbarEnabled) {
			if(this.browseItemScrollbar.event()) return true;
		}
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
		int end = Math.min(this.browseItemStartIndex+this.BROWSE_MAXIMUM_ITEM_DISPLAYED, this.queryButtonList.size());
		while(i < end && this.queryButtonList.get(i).getEntry() != null) {
			if(this.queryButtonList.get(i).mouseEvent()) {
				return true;
			}
			incrementBrowseItemY();
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
		System.out.println("AuctionFrameUI.keyboardEvent error, no tab selected");
		return false;
	}
	
	private boolean browseFrameKeyboardEvent() {
		if(this.browseSearchEditBox.keyboardEvent()) return true;
		if(this.browseMinLevelEditBox.keyboardEvent()) return true;
		if(this.browseMaxLevelEditBox.keyboardEvent()) return true;
		if(this.browseGoldBidEditBox.keyboardEvent()) return true;
		if(this.browseSilverBidEditBox.keyboardEvent()) return true;
		if(this.browseCopperBidEditBox.keyboardEvent()) return true;
		return false;
	}
	
	private boolean bidsFrameKeyboardEvent() {
		return false;
	}
	
	private boolean auctionsFrameKeyboardEvent() {
		return false;
	}
	
	private void enableBrowseItemScrollbar() {
		this.browseItemScrollbarEnabled = true;
		this.browseSortByPricePerUnitButton.update(this.x_frame+this.BROWSE_SORT_PRICE_PER_UNIT_BUTTON_X*Mideas.getDisplayXFactor(), this.y_frame+this.BROWSE_SORT_BUTTON_Y*Mideas.getDisplayYFactor(), this.BROWSE_SORT_PRICE_PER_UNIT_BUTTON_WIDTH_SCROLLBAR*Mideas.getDisplayXFactor(), this.BROWSE_SORT_BUTTON_HEIGHT*Mideas.getDisplayYFactor());
	}
	
	private void disableBrowseItemScrollbar() {
		this.browseItemScrollbarEnabled = false;
		this.browseSortByPricePerUnitButton.update(this.x_frame+this.BROWSE_SORT_PRICE_PER_UNIT_BUTTON_X*Mideas.getDisplayXFactor(), this.y_frame+this.BROWSE_SORT_BUTTON_Y*Mideas.getDisplayYFactor(), this.BROWSE_SORT_PRICE_PER_UNIT_BUTTON_WIDTH_NO_SCROLLBAR*Mideas.getDisplayXFactor(), this.BROWSE_SORT_BUTTON_HEIGHT*Mideas.getDisplayYFactor());
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
		this.browseItemYShift =  (short)(this.BROWSE_ITEM_Y_SHIFT*Mideas.getDisplayYFactor());
		this.browseBidButton.update(this.x_frame+this.BROWSE_BID_BUTTON_X*Mideas.getDisplayXFactor(), this.y_frame+this.BROWSE_CLOSE_BUTTON_Y*Mideas.getDisplayYFactor(), this.BROWSE_BUTTON_WIDTH*Mideas.getDisplayXFactor(), this.BROWSE_BUTTON_HEIGHT*Mideas.getDisplayYFactor());
		this.browseBuyoutButton.update(this.x_frame+this.BROWSE_BUYOUT_BUTTON_X*Mideas.getDisplayXFactor(), this.y_frame+this.BROWSE_CLOSE_BUTTON_Y*Mideas.getDisplayYFactor(), this.BROWSE_BUTTON_WIDTH*Mideas.getDisplayXFactor(), this.BROWSE_BUTTON_HEIGHT*Mideas.getDisplayYFactor());
		this.browseClose.update(this.x_frame+this.BROWSE_CLOSE_BUTTON_X*Mideas.getDisplayXFactor(), this.y_frame+this.BROWSE_CLOSE_BUTTON_Y*Mideas.getDisplayYFactor(), this.BROWSE_BUTTON_WIDTH*Mideas.getDisplayXFactor(), this.BROWSE_BUTTON_HEIGHT*Mideas.getDisplayYFactor());
		this.browseFilterScrollbar.update(this.x_frame+this.BROWSE_FILTER_SCROLLBAR_X*Mideas.getDisplayXFactor(), this.y_frame+this.BROWSE_FILTER_SCROLLBAR_Y*Mideas.getDisplayYFactor(), this.BROWSE_FILTER_SCROLLBAR_HEIGHT*Mideas.getDisplayYFactor(), this.BROWSE_FILTER_SCROLLBAR_WIDTH*Mideas.getDisplayXFactor(), this.BROWSE_FILTER_SCROLLBAR_HEIGHT*Mideas.getDisplayYFactor(), this.BROWSE_FILTER_HEIGHT*Mideas.getDisplayYFactor());
		this.browseSortByRarityButton.update(this.x_frame+this.BROWSE_SORT_RARITY_BUTTON_X*Mideas.getDisplayXFactor(), this.y_frame+this.BROWSE_SORT_BUTTON_Y*Mideas.getDisplayYFactor(), this.BROWSE_SORT_RARITY_BUTTON_WIDTH*Mideas.getDisplayXFactor(), this.BROWSE_SORT_BUTTON_HEIGHT*Mideas.getDisplayYFactor());
		this.browseSortByLevelButton.update(this.x_frame+this.BROWSE_SORT_LEVEL_BUTTON_X*Mideas.getDisplayXFactor(), this.y_frame+this.BROWSE_SORT_BUTTON_Y*Mideas.getDisplayYFactor(), this.BROWSE_SORT_LEVEL_BUTTON_WIDTH*Mideas.getDisplayXFactor(), this.BROWSE_SORT_BUTTON_HEIGHT*Mideas.getDisplayYFactor());
		this.browseSortByTimeLeftButton.update(this.x_frame+this.BROWSE_SORT_TIME_LEFT_BUTTON_X*Mideas.getDisplayXFactor(), this.y_frame+this.BROWSE_SORT_BUTTON_Y*Mideas.getDisplayYFactor(), this.BROWSE_SORT_TIME_LEFT_BUTTON_WIDTH*Mideas.getDisplayXFactor(), this.BROWSE_SORT_BUTTON_HEIGHT*Mideas.getDisplayYFactor());
		this.browseSortBySellerButton.update(this.x_frame+this.BROWSE_SORT_SELLER_BUTTON_X*Mideas.getDisplayXFactor(), this.y_frame+this.BROWSE_SORT_BUTTON_Y*Mideas.getDisplayYFactor(), this.BROWSE_SORT_SELLER_BUTTON_WIDTH*Mideas.getDisplayXFactor(), this.BROWSE_SORT_BUTTON_HEIGHT*Mideas.getDisplayYFactor());
		if(this.browseItemScrollbarEnabled) {
			this.browseSortByPricePerUnitButton.update(this.x_frame+this.BROWSE_SORT_PRICE_PER_UNIT_BUTTON_X*Mideas.getDisplayXFactor(), this.y_frame+this.BROWSE_SORT_BUTTON_Y*Mideas.getDisplayYFactor(), this.BROWSE_SORT_PRICE_PER_UNIT_BUTTON_WIDTH_SCROLLBAR*Mideas.getDisplayXFactor(), this.BROWSE_SORT_BUTTON_HEIGHT*Mideas.getDisplayYFactor());
		}
		else {
			this.browseSortByPricePerUnitButton.update(this.x_frame+this.BROWSE_SORT_PRICE_PER_UNIT_BUTTON_X*Mideas.getDisplayXFactor(), this.y_frame+this.BROWSE_SORT_BUTTON_Y*Mideas.getDisplayYFactor(), this.BROWSE_SORT_PRICE_PER_UNIT_BUTTON_WIDTH_NO_SCROLLBAR*Mideas.getDisplayXFactor(), this.BROWSE_SORT_BUTTON_HEIGHT*Mideas.getDisplayYFactor());
			
		}
		this.browseSendQueryButton.update(this.x_frame+this.BROWSE_SEND_QUERY_BUTTON_X*Mideas.getDisplayXFactor(), this.y_frame+this.BROWSE_SEND_QUERY_BUTTON_Y*Mideas.getDisplayYFactor(), this.BROWSE_SEND_QUERY_BUTTON_WIDTH*Mideas.getDisplayXFactor(), this.BROWSE_SEND_QUERY_BUTTON_HEIGHT*Mideas.getDisplayYFactor());
		this.browseResetButton.update(this.x_frame+this.BROWSE_RESET_BUTTON_X*Mideas.getDisplayXFactor(), this.y_frame+this.BROWSE_SEND_QUERY_BUTTON_Y*Mideas.getDisplayYFactor(), this.BROWSE_SEND_QUERY_BUTTON_WIDTH*Mideas.getDisplayXFactor(), this.BROWSE_SEND_QUERY_BUTTON_HEIGHT*Mideas.getDisplayYFactor());
		this.browseSearchEditBox.update(this.x_frame+this.BROWSE_SEARCH_EDIT_BOX_X*Mideas.getDisplayXFactor(), this.y_frame+this.BROWSE_SEARCH_EDIT_BOX_Y*Mideas.getDisplayYFactor());
		this.browseMinLevelEditBox.update(this.x_frame+this.BROWSE_MIN_LEVEL_EDIT_BOX_X*Mideas.getDisplayXFactor(), this.y_frame+this.BROWSE_SEARCH_EDIT_BOX_Y*Mideas.getDisplayYFactor());
		this.browseMaxLevelEditBox.update(this.x_frame+this.BROWSE_MAX_LEVEL_EDIT_BOX_X*Mideas.getDisplayXFactor(), this.y_frame+this.BROWSE_SEARCH_EDIT_BOX_Y*Mideas.getDisplayYFactor());
		this.browseRarityDropDownmenu.update(this.x_frame+this.BROWSE_QUALITY_FILTER_DROP_DOWN_X*Mideas.getDisplayXFactor(), this.y_frame+this.BROWSE_QUALITY_FILTER_DROP_DOWN_Y*Mideas.getDisplayXFactor(), this.BROWSE_QUALITY_FILTER_DROP_DOWN_BAR_WIDTH*Mideas.getDisplayXFactor(), this.x_frame+this.BROWSE_QUALITY_FILTER_DROP_DOWN_X*Mideas.getDisplayXFactor(), this.y_frame+(this.BROWSE_QUALITY_FILTER_DROP_DOWN_Y+20)*Mideas.getDisplayYFactor(), this.BROWSE_QUALITY_FILTER_DROP_DOWN_MENU_WIDTH*Mideas.getDisplayXFactor());
		this.browseGoldBidEditBox.update(this.x_frame+this.BROWSE_BID_GOLD_EDIT_BOX_X*Mideas.getDisplayXFactor(), this.y_frame+this.BROWSE_BID_EDIT_BOX_Y*Mideas.getDisplayYFactor());
		this.browseSilverBidEditBox.update(this.x_frame+this.BROWSE_BID_SILVER_EDIT_BOX_X*Mideas.getDisplayXFactor(), this.y_frame+this.BROWSE_BID_EDIT_BOX_Y*Mideas.getDisplayYFactor());
		this.browseCopperBidEditBox.update(this.x_frame+this.BROWSE_BID_COPPER_EDIT_BOX_X*Mideas.getDisplayXFactor(), this.y_frame+this.BROWSE_BID_EDIT_BOX_Y*Mideas.getDisplayYFactor());
		this.browseNextPageArrow.update(this.x_frame+this.BROWSE_NEXT_PAGE_ARROW_X*Mideas.getDisplayXFactor(), this.y_frame+this.BROWSE_PAGE_ARROW_Y*Mideas.getDisplayYFactor());
		this.browsePreviousPageArrow.update(this.x_frame+this.BROWSE_PREVIOUS_PAGE_ARROW_X*Mideas.getDisplayXFactor(), this.y_frame+this.BROWSE_PAGE_ARROW_Y*Mideas.getDisplayYFactor());
		this.browseItemScrollbar.update(this.x_frame+this.BROWSE_ITEM_SCROLLBAR_X*Mideas.getDisplayXFactor(), this.y_frame+this.BROWSE_ITEM_SCROLLBAR_Y*Mideas.getDisplayYFactor(), this.BROWSE_ITEM_SCROLLBAR_HEIGHT*Mideas.getDisplayYFactor(), this.BROWSE_ITEM_SCROLLBAR_WIDTH*Mideas.getDisplayXFactor(), (this.BROWSE_ITEM_SCROLLBAR_HEIGHT+15)*Mideas.getDisplayYFactor(), this.browseItemYShift*Mideas.getDisplayYFactor());
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
	
	public void updateUnloadedBrowseItem(int itemID) {
		int i = -1;
		while(++i < this.queryButtonList.size() && this.queryButtonList.get(i).getEntry() != null) {
			this.queryButtonList.get(i).updateUnloadedItem(itemID);
		}
	}
	
	public void setSelectedBrowseEntry(AuctionEntry entry) {
		this.browseSelectedEntry = entry;
		if(entry != null) {
			this.browseCopperBidEditBox.setText(entry.getBidCopperString());
			this.browseSilverBidEditBox.setText(entry.getBidSilverString());
			this.browseGoldBidEditBox.setText(entry.getBidGoldString());
		}
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
		}
		else if(!this.browseFilterScrollbarEnabled) {
			if(count < 15) {
				return;
			}
			showBrowseFilterScrollbar();
		}
	}
	
	private void hideBrowseFilterScrollbar() {
		this.browseFilterWidth = (short)(this.BROWSE_FILTER_WIDTH_NO_SCROLLBAR*Mideas.getDisplayXFactor());
		this.browseFilterScrollbarEnabled = false;
	}
	
	private void showBrowseFilterScrollbar() {
		this.browseFilterWidth = (short)(this.BROWSE_FILTER_WIDTH_SCROLLBAR*Mideas.getDisplayXFactor());
		this.browseFilterScrollbarEnabled = true;
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
	
	protected void buildResultString(byte result, int totalResult, short page, short numberPage) {
		this.browsePage = page;
		this.browseTotalPage = numberPage;
		if(result == 0 ) {
			return;
		}
		this.browseResultString = "Items "+(1+(page-1)*50)+" - "+(page*50)+" ( "+totalResult+" total )";
		this.browseResultStringWidth = (short)FontManager.get("FRIZQT", 11).getWidth(this.browseResultString);
	}
	
	protected void frameClosed() {
		Mideas.joueur1().getAuctionHouse().clearQueryList();
		this.hasSearched = false;
		disableBrowseItemScrollbar();
		this.browsePage = 1;
		this.browseTotalPage = 0;
		this.browseRarityDropDownmenu.setValue(0);
		this.browseGoldBidEditBox.setActive(false);
		this.browseSilverBidEditBox.setActive(false);
		this.browseCopperBidEditBox.setActive(false);
		this.browseSearchEditBox.setActive(false);
		this.browseMinLevelEditBox.setActive(false);
		this.browseMaxLevelEditBox.setActive(false);
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
	
	protected boolean browseItemScrollbarEnabled() {
		return this.browseItemScrollbarEnabled;
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
