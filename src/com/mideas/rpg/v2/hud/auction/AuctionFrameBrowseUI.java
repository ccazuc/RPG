package com.mideas.rpg.v2.hud.auction;

import java.util.ArrayList;

import com.mideas.rpg.v2.FontManager;
import com.mideas.rpg.v2.Interface;
import com.mideas.rpg.v2.Mideas;
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
import com.mideas.rpg.v2.utils.DropDownMenu;
import com.mideas.rpg.v2.utils.InputBox;
import com.mideas.rpg.v2.utils.Input;
import com.mideas.rpg.v2.utils.ScrollBar;
import com.mideas.rpg.v2.utils.TextMenu;
import com.mideas.rpg.v2.utils.render.Draw;
import com.mideas.rpg.v2.utils.render.Sprites;
import com.mideas.rpg.v2.utils.render.TTF;

public class AuctionFrameBrowseUI {
	
	final byte MAXIMUM_ITEM_DISPLAYED = 8;
	
	private final short FILTER_SCROLLBAR_X = 153;
	private final short FILTER_SCROLLBAR_WIDTH = 170;
	private final short FILTER_SCROLLBAR_HEIGHT = 290;
	private final short FILTER_SCROLLBAR_Y = 100;
	private final short ITEM_SCROLLBAR_X = 794;
	private final short ITEM_SCROLLBAR_WIDTH = 640;
	private final short ITEM_SCROLLBAR_HEIGHT = 290;
	private final short ITEM_SCROLLBAR_Y = 100;
	private final short FILTER_X = 21;
	private final short FILTER_Y = 103;
	private final short FILTER_Y_SHIFT = 20;
	private final short FILTER_WIDTH_NO_SCROLLBAR = 156;
	private final short FILTER_WIDTH_SCROLLBAR = 136;
	private final short FILTER_HEIGHT = 20;
	private final short CLOSE_BUTTON_X = 744;
	private final short CLOSE_BUTTON_Y = 409;
	private final short BUYOUT_BUTTON_X = 663;
	private final short BID_BUTTON_X = 584;
	private final short BUTTON_WIDTH = 75;
	private final short BUTTON_HEIGHT = 22;
	private final short SORT_BUTTON_Y = 80;
	private final short SORT_BUTTON_HEIGHT = 21;
	private final short SORT_RARITY_BUTTON_WIDTH = 214;
	private final short SORT_RARITY_BUTTON_X = 184;
	private final short SORT_LEVEL_BUTTON_X = 396;
	private final short SORT_LEVEL_BUTTON_WIDTH = 57;
	private final short SORT_TIME_LEFT_BUTTON_X = 451;
	private final short SORT_TIME_LEFT_BUTTON_WIDTH = 91;
	private final short SORT_SELLER_BUTTON_X = 540;
	private final short SORT_SELLER_BUTTON_WIDTH = 76;
	private final short SORT_PRICE_PER_UNIT_BUTTON_X = 614;
	private final short SORT_PRICE_PER_UNIT_BUTTON_WIDTH_SCROLLBAR = 184;
	private final short SORT_PRICE_PER_UNIT_BUTTON_WIDTH_NO_SCROLLBAR = 207;
	private final short SEND_QUERY_BUTTON_X = 659;
	private final short RESET_BUTTON_X = 744;
	private final short SEND_QUERY_BUTTON_WIDTH = 78;
	private final short SEND_QUERY_BUTTON_Y = 33;
	private final short SEND_QUERY_BUTTON_HEIGHT = 22;
	private final short SEARCH_EDIT_BOX_X = 76;
	private final short SEARCH_EDIT_BOX_WIDTH = 145;
	private final short SEARCH_EDIT_BOX_INPUT_WIDTH = 105;
	private final short SEARCH_EDIT_BOX_Y = 49;
	private final short MIN_LEVEL_EDIT_BOX_X = 226;
	private final short MAX_LEVEL_EDIT_BOX_X = 263;
	private final short LEVEL_EDIT_BOX_WIDTH = 30;
	private final short LEVEL_EDIT_BOX_INPUT_WIDTH = 30;
	private final short QUALITY_FILTER_DROP_DOWN_X = 305;
	private final short QUALITY_FILTER_DROP_DOWN_Y = 49;
	private final short QUALITY_FILTER_DROP_DOWN_BAR_WIDTH = 131;
	private final short QUALITY_FILTER_DROP_DOWN_MENU_WIDTH = 155;
	private final short QUERY_BUTTON_NO_SCROLLBAR_WIDTH = 590;
	private final short QUERY_BUTTON_SCROLLBAR_WIDTH = 566;
	private final short ITEM_Y = 108;
	private final short ITEM_Y_SHIFT = 37;
	private final short ITEM_HEIGHT = 40;
	private final short ITEM_X = 194;
	private final short BID_COPPER_EDIT_BOX_X = 437;
	private final short BID_SILVER_EDIT_BOX_X = 389;
	private final short BID_GOLD_EDIT_BOX_X = 309;
	private final short BID_EDIT_BOX_Y = 409;
	private final short BID_COPPER_EDIT_BOX_WIDTH = 43;
	private final short BID_GOLD_EDIT_BOX_WIDTH = 75;
	private final short NEXT_PAGE_ARROW_X = 796;
	private final short PREVIOUS_PAGE_ARROW_X = 700;
	private final short PAGE_ARROW_Y = 54;
	
	
	private final AuctionFrameUI frame;
	private final ArrayList<AuctionCategoryFilterButton> categoryList;
	private final ArrayList<AuctionBrowseQueryButton> queryButtonList;
	AuctionEntry selectedEntry;
	AuctionHouseFilter selectedFilter = AuctionHouseFilter.NONE;
	AuctionHouseFilter selectedCategoryFilter = AuctionHouseFilter.NONE;
	AuctionHouseSort selectedSort = AuctionHouseSort.LEVEL_ASCENDING;
	AuctionHouseQualityFilter qualityFilter = AuctionHouseQualityFilter.ALL;
	protected final TTF filterFont;
	protected final TTF sortFont = FontManager.get("FRIZQT", 11);
	private short filterX;
	private short filterY;
	private short filterYSave;
	private short filterYShift;
	private short filterWidth;
	private short filterHeight;
	short filterScrollbarOffset;
	private short itemX;
	private short itemY;
	private short itemWidth;
	private short itemHeight;
	private short itemYSave;
	private short itemYShift;
	short itemStartIndex;
	byte filterNumberLine;
	private boolean shouldUpdate;
	private boolean filterScrollbarEnabled;
	private boolean hasSearched;
	private boolean itemScrollbarEnabled;
	short page = 1;
	short totalPage;
	private String resultString;
	private short resultStringWidth;
	boolean querySent;
	//No this.xframe+this.button_x to avoid NullPointerException caused by frame = null
	final ScrollBar filterScrollbar = new ScrollBar(this.FILTER_SCROLLBAR_X*Mideas.getDisplayXFactor(), this.FILTER_SCROLLBAR_Y*Mideas.getDisplayYFactor(), this.FILTER_SCROLLBAR_HEIGHT*Mideas.getDisplayYFactor(), this.FILTER_SCROLLBAR_WIDTH*Mideas.getDisplayXFactor(), this.FILTER_SCROLLBAR_HEIGHT*Mideas.getDisplayYFactor(), false, this.FILTER_HEIGHT*Mideas.getDisplayYFactor()) {
		
		@Override
		public void onScroll() {
			AuctionFrameBrowseUI.this.filterScrollbarOffset = (short)(AuctionFrameBrowseUI.this.FILTER_HEIGHT*Mideas.getDisplayYFactor()*(AuctionFrameBrowseUI.this.filterNumberLine-15)*getScrollPercentage());
		}
	};
	final ScrollBar itemScrollbar = new ScrollBar(this.ITEM_SCROLLBAR_X*Mideas.getDisplayXFactor(), this.ITEM_SCROLLBAR_Y*Mideas.getDisplayYFactor(), this.ITEM_SCROLLBAR_HEIGHT*Mideas.getDisplayYFactor(), this.ITEM_SCROLLBAR_WIDTH*Mideas.getDisplayXFactor(), (this.ITEM_SCROLLBAR_HEIGHT+15)*Mideas.getDisplayYFactor(), false, this.itemYShift*Mideas.getDisplayYFactor()) {
		
		@Override
		public void onScroll() {
			AuctionFrameBrowseUI.this.itemStartIndex = (short)((Mideas.joueur1().getAuctionHouse().getQueryList().size()+1-AuctionFrameBrowseUI.this.MAXIMUM_ITEM_DISPLAYED)*getScrollPercentage());
		}
	};
	private final Button bidButton = new Button(this.BID_BUTTON_X*Mideas.getDisplayXFactor(), this.CLOSE_BUTTON_Y*Mideas.getDisplayYFactor(), this.BUTTON_WIDTH, this.BUTTON_HEIGHT, "Bid", 12, 1) {
	
		@Override
		public void onLeftClickUp() {
			CommandAuction.makeABid(AuctionFrameBrowseUI.this.selectedEntry, AuctionFrameBrowseUI.this.goldBidEditBox.getValue()*10000+AuctionFrameBrowseUI.this.silverBidEditBox.getValue()*100+AuctionFrameBrowseUI.this.copperBidEditBox.getValue());
		}
		
		@Override
		public boolean activateCondition() {
			return AuctionFrameBrowseUI.this.selectedEntry != null && AuctionFrameBrowseUI.this.selectedEntry.canBeBuy();
		}
	};
	private final Button buyoutButton = new Button(this.BUYOUT_BUTTON_X*Mideas.getDisplayXFactor(), this.CLOSE_BUTTON_Y*Mideas.getDisplayYFactor(), this.BUTTON_WIDTH, this.BUTTON_HEIGHT, "Buyout", 12, 1) {
	
		@Override
		public void onLeftClickUp() {
			CommandAuction.buyout(AuctionFrameBrowseUI.this.selectedEntry);
		}
		
		@Override
		public boolean activateCondition() {
			return AuctionFrameBrowseUI.this.selectedEntry != null && AuctionFrameBrowseUI.this.selectedEntry.canBeBuy();
		}
	};
	private final Button closeButton = new Button(this.CLOSE_BUTTON_X*Mideas.getDisplayXFactor(), this.CLOSE_BUTTON_Y*Mideas.getDisplayYFactor(), this.BUTTON_WIDTH, this.BUTTON_HEIGHT, "Close", 12, 1) {
	
		@Override
		public void onLeftClickUp() {
			Interface.closeAuctionFrame();
			this.reset();
		}
	};
	private final Button sendQueryButton = new Button(this.SEND_QUERY_BUTTON_X*Mideas.getDisplayXFactor(), this.SEND_QUERY_BUTTON_Y*Mideas.getDisplayYFactor(), this.SEND_QUERY_BUTTON_WIDTH, this.SEND_QUERY_BUTTON_HEIGHT, "Search", 12, 1) {
	
		@Override
		public void onLeftClickUp() {
			AuctionFrameBrowseUI.this.page = 1;
			sendSearchQuery();
		}
		
		@Override
		public boolean activateCondition() {
			return !AuctionFrameBrowseUI.this.querySent;
		}
	};
	private final Button resetButton = new Button(this.RESET_BUTTON_X*Mideas.getDisplayXFactor(), this.SEND_QUERY_BUTTON_Y*Mideas.getDisplayYFactor(), this.SEND_QUERY_BUTTON_WIDTH, this.SEND_QUERY_BUTTON_HEIGHT, "Reset", 12, 1) {
	
		@Override
		public void onLeftClickUp() {
			resetBrowseOptions();
		}
		
		@Override
		public boolean activateCondition() {
			return AuctionFrameBrowseUI.this.selectedCategoryFilter != AuctionHouseFilter.NONE || AuctionFrameBrowseUI.this.selectedFilter != AuctionHouseFilter.NONE || AuctionFrameBrowseUI.this.qualityFilter != AuctionHouseQualityFilter.ALL || AuctionFrameBrowseUI.this.searchEditBox.getText().length() != 0 || AuctionFrameBrowseUI.this.minLevelEditBox.getText().length() != 0 || AuctionFrameBrowseUI.this.maxLevelEditBox.getText().length() != 0;
		}
	};
	private final Arrow nextPageArrow = new Arrow(this.NEXT_PAGE_ARROW_X*Mideas.getDisplayXFactor(), this.PAGE_ARROW_Y*Mideas.getDisplayYFactor(), 27, 26, ArrowDirection.RIGHT) {
		
		@Override
		public void onLeftClickUp() {
			AuctionFrameBrowseUI.this.page++;
			sendSearchQuery();
		}
		
		@Override
		public boolean activateCondition() {
			return !AuctionFrameBrowseUI.this.querySent && AuctionFrameBrowseUI.this.page < AuctionFrameBrowseUI.this.totalPage;
		}
	};
	private final Arrow previousPageArrow = new Arrow(this.PREVIOUS_PAGE_ARROW_X*Mideas.getDisplayXFactor(), this.PAGE_ARROW_Y*Mideas.getDisplayYFactor(), 27, 26, ArrowDirection.LEFT) {
		
		@Override
		public void onLeftClickUp() {
			AuctionFrameBrowseUI.this.page--;
			sendSearchQuery();
		}
		
		@Override
		public boolean activateCondition() {
			return !AuctionFrameBrowseUI.this.querySent && AuctionFrameBrowseUI.this.page > 1;
		}
	};
	final InputBox searchEditBox = new InputBox(this.SEARCH_EDIT_BOX_X*Mideas.getDisplayXFactor(), this.SEARCH_EDIT_BOX_Y*Mideas.getDisplayYFactor(), this.SEARCH_EDIT_BOX_WIDTH, 63, 21, this.SEARCH_EDIT_BOX_INPUT_WIDTH, FontManager.get("FRIZQT", 11), false, 2, 13, "Search", true) {
		
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
				AuctionFrameBrowseUI.this.minLevelEditBox.setActive(true);
				return true;
			}
			return false;
		}
	};
	final InputBox minLevelEditBox = new InputBox(this.MIN_LEVEL_EDIT_BOX_X*Mideas.getDisplayXFactor(), this.SEARCH_EDIT_BOX_Y*Mideas.getDisplayYFactor(), this.LEVEL_EDIT_BOX_WIDTH, 3, 5, this.LEVEL_EDIT_BOX_INPUT_WIDTH, FontManager.get("FRIZQT", 11), true) {
		
		@Override
		public boolean keyEvent(char c) {
			if(c == Input.ENTER_CHAR_VALUE || c == Input.ESCAPE_CHAR_VALUE) {
				this.input.setIsActive(false);
				return true;
			}
			if(c == Input.TAB_CHAR_VALUE) {
				AuctionFrameBrowseUI.this.maxLevelEditBox.setActive(true);
				return true;
			}
			return false;
		}
		
		@Override
		public boolean checkValue(int value) {
			return value <= 999;
		}
	};
	final InputBox maxLevelEditBox = new InputBox(this.MAX_LEVEL_EDIT_BOX_X*Mideas.getDisplayXFactor(), this.SEARCH_EDIT_BOX_Y*Mideas.getDisplayYFactor(), this.LEVEL_EDIT_BOX_WIDTH, 3, 5, this.LEVEL_EDIT_BOX_INPUT_WIDTH, FontManager.get("FRIZQT", 11), true) {
		
		@Override
		public boolean keyEvent(char c) {
			if(c == Input.ENTER_CHAR_VALUE || c == Input.ESCAPE_CHAR_VALUE) {
				this.input.setIsActive(false);
				return true;
			}
			if(c == Input.TAB_CHAR_VALUE) {
				AuctionFrameBrowseUI.this.goldBidEditBox.setActive(true);
				return true;
			}
			return false;
		}
		
		@Override
		public boolean checkValue(int value) {
			return value <= 999;
		}
	};
	final InputBox goldBidEditBox = new InputBox(this.BID_GOLD_EDIT_BOX_X*Mideas.getDisplayXFactor(), this.BID_EDIT_BOX_Y*Mideas.getDisplayYFactor(), this.BID_GOLD_EDIT_BOX_WIDTH, 6, 5, this.BID_GOLD_EDIT_BOX_WIDTH, FontManager.get("FRIZQT", 11), true) {
		
		@Override
		public boolean keyEvent(char c) {
			if(c == Input.ESCAPE_CHAR_VALUE) {
				this.input.setIsActive(false);
				return true;
			}
			if(c == Input.TAB_CHAR_VALUE || c == Input.ENTER_CHAR_VALUE) {
				AuctionFrameBrowseUI.this.silverBidEditBox.setActive(true);
				return true;
			}
			return false;
		}
		
		@Override
		public boolean checkValue(int value) {
			return value <= 999999;
		}
	};
	final InputBox silverBidEditBox = new InputBox(this.BID_SILVER_EDIT_BOX_X*Mideas.getDisplayXFactor(), this.BID_EDIT_BOX_Y*Mideas.getDisplayYFactor(), this.BID_COPPER_EDIT_BOX_WIDTH, 2, 5, this.BID_COPPER_EDIT_BOX_WIDTH, FontManager.get("FRIZQT", 11), true) {
		
		@Override
		public boolean keyEvent(char c) {
			if(c == Input.ESCAPE_CHAR_VALUE) {
				this.input.setIsActive(false);
				return true;
			}
			if(c == Input.TAB_CHAR_VALUE || c == Input.ENTER_CHAR_VALUE) {
				AuctionFrameBrowseUI.this.copperBidEditBox.setActive(true);
				return true;
			}
			return false;
		}
		
		@Override
		public boolean checkValue(int value) {
			return value <= 99;
		}
	};
	final InputBox copperBidEditBox = new InputBox(this.BID_COPPER_EDIT_BOX_X*Mideas.getDisplayXFactor(), this.BID_EDIT_BOX_Y*Mideas.getDisplayYFactor(), this.BID_COPPER_EDIT_BOX_WIDTH, 2, 5, this.BID_COPPER_EDIT_BOX_WIDTH, FontManager.get("FRIZQT", 11), true) {
		
		@Override
		public boolean keyEvent(char c) {
			if(c == Input.ESCAPE_CHAR_VALUE) {
				this.input.setIsActive(false);
				return true;
			}
			if(c == Input.TAB_CHAR_VALUE || c == Input.ENTER_CHAR_VALUE) {
				AuctionFrameBrowseUI.this.searchEditBox.setActive(true);
				return true;
			}
			return false;
		}
		
		@Override
		public boolean checkValue(int value) {
			return value <= 99;
		}
	};
	private final ButtonMenuSort sortByRarityButton = new ButtonMenuSort(this.SORT_RARITY_BUTTON_X*Mideas.getDisplayXFactor(), this.SORT_BUTTON_Y*Mideas.getDisplayYFactor(), this.SORT_RARITY_BUTTON_WIDTH*Mideas.getDisplayXFactor(), this.SORT_BUTTON_HEIGHT*Mideas.getDisplayYFactor(), "Rarity", this.sortFont) {
		
		@Override
		public void eventButtonClick() {
			if(AuctionFrameBrowseUI.this.selectedSort == AuctionHouseSort.RARITY_ASCENDING) {
				AuctionFrameBrowseUI.this.selectedSort = AuctionHouseSort.RARITY_DESCENDING;
			}
			else {
				AuctionFrameBrowseUI.this.selectedSort = AuctionHouseSort.RARITY_ASCENDING;
			}
			sendSearchQuery();
		}
	};
	private final ButtonMenuSort sortByLevelButton = new ButtonMenuSort(this.SORT_LEVEL_BUTTON_X*Mideas.getDisplayXFactor(), this.SORT_BUTTON_Y*Mideas.getDisplayYFactor(), this.SORT_LEVEL_BUTTON_WIDTH*Mideas.getDisplayXFactor(), this.SORT_BUTTON_HEIGHT*Mideas.getDisplayYFactor(), "Lvl", this.sortFont) {
		
		@Override
		public void eventButtonClick() {
			if(AuctionFrameBrowseUI.this.selectedSort == AuctionHouseSort.LEVEL_ASCENDING) {
				AuctionFrameBrowseUI.this.selectedSort = AuctionHouseSort.LEVEL_DESCENDING;
			}
			else {
				AuctionFrameBrowseUI.this.selectedSort = AuctionHouseSort.LEVEL_ASCENDING;
			}
			sendSearchQuery();
		}
	};
	private final ButtonMenuSort sortByTimeLeftButton = new ButtonMenuSort(this.SORT_TIME_LEFT_BUTTON_X*Mideas.getDisplayXFactor(), this.SORT_BUTTON_Y*Mideas.getDisplayYFactor(), this.SORT_TIME_LEFT_BUTTON_WIDTH*Mideas.getDisplayXFactor(), this.SORT_BUTTON_HEIGHT*Mideas.getDisplayYFactor(), "Time Left", this.sortFont) {
		
		@Override
		public void eventButtonClick() {
			if(AuctionFrameBrowseUI.this.selectedSort == AuctionHouseSort.TIME_LEFT_ASCENDING) {
				AuctionFrameBrowseUI.this.selectedSort = AuctionHouseSort.TIME_LEFT_DESCENDING;
			}
			else {
				AuctionFrameBrowseUI.this.selectedSort = AuctionHouseSort.TIME_LEFT_ASCENDING;
			}
			sendSearchQuery();
		}
	};
	private final ButtonMenuSort sortBySellerButton = new ButtonMenuSort(this.SORT_SELLER_BUTTON_X*Mideas.getDisplayXFactor(), this.SORT_BUTTON_Y*Mideas.getDisplayYFactor(), this.SORT_SELLER_BUTTON_WIDTH*Mideas.getDisplayXFactor(), this.SORT_BUTTON_HEIGHT*Mideas.getDisplayYFactor(), "Seller", this.sortFont) {
		
		@Override
		public void eventButtonClick() {
			if(AuctionFrameBrowseUI.this.selectedSort == AuctionHouseSort.VENDOR_ASCENDING) {
				AuctionFrameBrowseUI.this.selectedSort = AuctionHouseSort.VENDOR_DESCENDING;
			}
			else {
				AuctionFrameBrowseUI.this.selectedSort = AuctionHouseSort.VENDOR_ASCENDING;
			}
			sendSearchQuery();
		}
	};
	private final ButtonMenuSort sortByPricePerUnitButton = new ButtonMenuSort(this.SORT_PRICE_PER_UNIT_BUTTON_X*Mideas.getDisplayXFactor(), this.SORT_BUTTON_Y*Mideas.getDisplayYFactor(), this.SORT_PRICE_PER_UNIT_BUTTON_WIDTH_NO_SCROLLBAR*Mideas.getDisplayXFactor(), this.SORT_BUTTON_HEIGHT*Mideas.getDisplayYFactor(), "Price Per Unit", this.sortFont) {
		
		@Override
		public void eventButtonClick() {
			if(AuctionFrameBrowseUI.this.selectedSort == AuctionHouseSort.PRICE_PER_UNIT_ASCENDING) {
				AuctionFrameBrowseUI.this.selectedSort = AuctionHouseSort.PRICE_PER_UNIT_DESCENDING;
			}
			else {
				AuctionFrameBrowseUI.this.selectedSort = AuctionHouseSort.PRICE_PER_UNIT_ASCENDING;
			}
			sendSearchQuery();
		}
	};
	private final DropDownMenu rarityDropDownmenu = new DropDownMenu(this.QUALITY_FILTER_DROP_DOWN_X*Mideas.getDisplayXFactor(), this.QUALITY_FILTER_DROP_DOWN_Y*Mideas.getDisplayXFactor(), this.QUALITY_FILTER_DROP_DOWN_BAR_WIDTH*Mideas.getDisplayXFactor(), this.QUALITY_FILTER_DROP_DOWN_X*Mideas.getDisplayXFactor(), (this.QUALITY_FILTER_DROP_DOWN_Y+20)*Mideas.getDisplayYFactor(), this.QUALITY_FILTER_DROP_DOWN_MENU_WIDTH*Mideas.getDisplayXFactor(), 12, .6f) {
		
		@Override
		public void menuEventButtonClick() {
			AuctionFrameBrowseUI.this.qualityFilter = AuctionHouseQualityFilter.values()[this.selectedMenuValue+1];
		}
	};
	private final TextMenu rarityAllTextMenu = new TextMenu(0, 0, 0, "All", 11, 1);
	private final TextMenu rarityPoorTextMenu = new TextMenu(0, 0, 0, "Poor", 11, 1);
	private final TextMenu rarityCommonTextMenu = new TextMenu(0, 0, 0, "Common", 11, 1);
	private final TextMenu rarityUncommonTextMenu = new TextMenu(0, 0, 0, "Uncommon", 11, 1);
	private final TextMenu rarityRareTextMenu = new TextMenu(0, 0, 0, "Rare", 11, 1);
	private final TextMenu rarityEpicTextMenu = new TextMenu(0, 0, 0, "Epic", 11, 1);
	private final TextMenu rarityLegendaryTextMenu = new TextMenu(0, 0, 0, "Legendary", 11, 1);

	public AuctionFrameBrowseUI(AuctionFrameUI frame) {
		this.frame = frame;
		this.queryButtonList = new ArrayList<AuctionBrowseQueryButton>();
		this.categoryList = new ArrayList<AuctionCategoryFilterButton>();
		this.shouldUpdate = true;
		updateSize();
		int i = 0;
		while(i < 50) {
			this.queryButtonList.add(new AuctionBrowseQueryButton(this));
			i++;
		}
		this.filterFont = FontManager.get("FRIZQT", 11);
		buildBrowseFilterQualityMenu();
		buildBrowseFilterMenu();
	}
	
	void sendSearchQuery() {
		AuctionHouseFilter filter = this.selectedFilter;
		if(this.selectedFilter == AuctionHouseFilter.NONE) {
			filter = this.selectedCategoryFilter;
		}
		this.itemScrollbar.resetScroll();
		CommandAuction.sendQuery(filter, this.selectedSort, this.qualityFilter, this.searchEditBox.getText(), this.page, false, (short)this.minLevelEditBox.getValue(), (short)this.maxLevelEditBox.getValue(), false);
		this.hasSearched = true;
		this.querySent = true;
	}
	
	void resetBrowseOptions() {
		int i = this.categoryList.size();
		while(--i >= 0) {
			this.categoryList.get(i).unexpand();
		}
		this.filterScrollbar.resetScroll();
		checkBrowseFilterScrollbar();
		this.searchEditBox.resetText();
		this.minLevelEditBox.resetText();
		this.maxLevelEditBox.resetText();
		this.rarityDropDownmenu.setValue(0);
		this.qualityFilter = AuctionHouseQualityFilter.ALL;
		this.selectedCategoryFilter = AuctionHouseFilter.NONE;
		this.selectedFilter = AuctionHouseFilter.NONE;
	}
	
	protected void draw() {
		updateSize();
		Draw.drawQuad(Sprites.auction_browse_frame, this.frame.getXFrame(), this.frame.getYFrame());
		this.bidButton.draw();
		this.buyoutButton.draw();
		this.closeButton.draw();
		
		Sprites.button_menu_sort.drawBegin();
		this.sortByRarityButton.drawTexturePart();
		this.sortByLevelButton.drawTexturePart();
		this.sortByTimeLeftButton.drawTexturePart();
		this.sortBySellerButton.drawTexturePart();
		this.sortByPricePerUnitButton.drawTexturePart();
		Sprites.button_menu_sort.drawEnd();

		this.sortFont.drawBegin();
		this.sortByRarityButton.drawStringPart();
		this.sortByLevelButton.drawStringPart();
		this.sortByTimeLeftButton.drawStringPart();
		this.sortBySellerButton.drawStringPart();
		this.sortByPricePerUnitButton.drawStringPart();
		this.sortFont.drawEnd();
		
		Sprites.button_menu_hover.drawBlendBegin();
		this.sortByRarityButton.drawHoverPart();
		this.sortByLevelButton.drawHoverPart();
		this.sortByTimeLeftButton.drawHoverPart();
		this.sortBySellerButton.drawHoverPart();
		this.sortByPricePerUnitButton.drawHoverPart();
		Sprites.button_menu_hover.drawBlendEnd();
		
		/*this.browseSortByRarityButton.draw();
		this.browseSortByLevelButton.draw();
		this.browseSortByTimeLeftButton.draw();
		this.browseSortBySellerButton.draw();
		this.browseSortByPricePerUnitButton.draw();*/
		this.sendQueryButton.draw();
		/*this.browseSearchEditBox.draw();
		this.browseMinLevelEditBox.draw();
		this.browseMaxLevelEditBox.draw();
		this.browseGoldBidEditBox.draw();
		this.browseSilverBidEditBox.draw();
		this.browseCopperBidEditBox.draw();*/
		Sprites.edit_box.drawBegin();
		this.searchEditBox.drawTexturePart();
		this.minLevelEditBox.drawTexturePart();
		this.maxLevelEditBox.drawTexturePart();
		this.goldBidEditBox.drawTexturePart();
		this.silverBidEditBox.drawTexturePart();
		this.copperBidEditBox.drawTexturePart();
		Sprites.edit_box.drawEnd();
		this.searchEditBox.drawString();
		this.minLevelEditBox.drawString();
		this.maxLevelEditBox.drawString();
		this.goldBidEditBox.drawString();
		this.silverBidEditBox.drawString();
		this.copperBidEditBox.drawString();
		this.resetButton.draw();
		this.nextPageArrow.draw();
		this.previousPageArrow.draw();
		TTF font = FontManager.get("FRIZQT", 11);
		font.drawBegin();
		font.drawStringShadowPart(this.frame.getXFrame()+80*Mideas.getDisplayXFactor(), this.frame.getYFrame()+34*Mideas.getDisplayYFactor(), "Name", Color.WHITE, Color.BLACK, 1, 0, 0);
		font.drawStringShadowPart(this.frame.getXFrame()+229*Mideas.getDisplayXFactor(), this.frame.getYFrame()+34*Mideas.getDisplayYFactor(), "Level Range", Color.WHITE, Color.BLACK, 1, 0, 0);
		font.drawStringShadowPart(this.frame.getXFrame()+257*Mideas.getDisplayXFactor(), this.frame.getYFrame()+50*Mideas.getDisplayYFactor(), "-", Color.WHITE, Color.BLACK, 1, 0, 0);
		font.drawStringShadowPart(this.frame.getXFrame()+308*Mideas.getDisplayXFactor(), this.frame.getYFrame()+34*Mideas.getDisplayYFactor(), "Rarity", Color.WHITE, Color.BLACK, 1, 0, 0);
		font.drawStringShadowPart(this.frame.getXFrame()+288*Mideas.getDisplayXFactor(), this.frame.getYFrame()+410*Mideas.getDisplayYFactor(), "Bid", Color.WHITE, Color.BLACK, 1, 0, 0);
		font.drawEnd();
		font = FontManager.get("ARIALN", 14);
		font.drawBegin();
		font.drawStringShadowPartReversed(this.frame.getXFrame()+150*Mideas.getDisplayXFactor(), this.frame.getYFrame()+409*Mideas.getDisplayYFactor(), Mideas.joueur1().getCoppierPieceString(), Color.WHITE, Color.BLACK, 1, 0, 0);
		font.drawStringShadowPartReversed(this.frame.getXFrame()+115*Mideas.getDisplayXFactor(), this.frame.getYFrame()+409*Mideas.getDisplayYFactor(), Mideas.joueur1().getSilverPieceString(), Color.WHITE, Color.BLACK, 1, 0, 0);
		font.drawStringShadowPartReversed(this.frame.getXFrame()+77*Mideas.getDisplayXFactor(), this.frame.getYFrame()+409*Mideas.getDisplayYFactor(), Mideas.joueur1().getGoldPieceString(), Color.WHITE, Color.BLACK, 1, 0, 0);
		font.drawEnd();
		Sprites.copper_coin.drawBegin();
		Draw.drawQuadPart(Sprites.copper_coin, this.frame.getXFrame()+153*Mideas.getDisplayXFactor(), this.frame.getYFrame()+412*Mideas.getDisplayYFactor(), 13*Mideas.getDisplayXFactor(), 13*Mideas.getDisplayYFactor());
		Draw.drawQuadPart(Sprites.copper_coin, this.frame.getXFrame()+463*Mideas.getDisplayXFactor(), this.frame.getYFrame()+413*Mideas.getDisplayYFactor(), 13*Mideas.getDisplayXFactor(), 13*Mideas.getDisplayYFactor());
		Sprites.copper_coin.drawEnd();
		Sprites.silver_coin.drawBegin();
		Draw.drawQuadPart(Sprites.silver_coin, this.frame.getXFrame()+118*Mideas.getDisplayXFactor(), this.frame.getYFrame()+412*Mideas.getDisplayYFactor(), 13*Mideas.getDisplayXFactor(), 13*Mideas.getDisplayYFactor());
		Draw.drawQuadPart(Sprites.silver_coin, this.frame.getXFrame()+415*Mideas.getDisplayXFactor(), this.frame.getYFrame()+413*Mideas.getDisplayYFactor(), 13*Mideas.getDisplayXFactor(), 13*Mideas.getDisplayYFactor());
		Sprites.silver_coin.drawEnd();
		Sprites.gold_coin.drawBegin();
		Draw.drawQuadPart(Sprites.gold_coin, this.frame.getXFrame()+83*Mideas.getDisplayXFactor(), this.frame.getYFrame()+412*Mideas.getDisplayYFactor(), 13*Mideas.getDisplayXFactor(), 13*Mideas.getDisplayYFactor());
		Draw.drawQuadPart(Sprites.gold_coin, this.frame.getXFrame()+367*Mideas.getDisplayXFactor(), this.frame.getYFrame()+413*Mideas.getDisplayYFactor(), 13*Mideas.getDisplayXFactor(), 13*Mideas.getDisplayYFactor());
		Sprites.gold_coin.drawEnd();
		drawBrowseFilter();
		drawBrowseItem();
		if(this.filterScrollbarEnabled) {
			this.filterScrollbar.draw();
		}
		if(this.itemScrollbarEnabled) {
			this.itemScrollbar.draw();
		}
		this.rarityDropDownmenu.draw();
	}
	
	public void updateQueryButtonList() {
		ArrayList<AuctionEntry> list = Mideas.joueur1().getAuctionHouse().getQueryList();
		short width;
		if(list.size() > 8) {
			width = this.QUERY_BUTTON_SCROLLBAR_WIDTH;
			enableBrowseItemScrollbar();
		}
		else {
			width = this.QUERY_BUTTON_NO_SCROLLBAR_WIDTH;
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
		this.page = 1;
		this.totalPage = 0;
	}
	
	private void drawBrowseFilter() {
		int i = -1;
		Draw.glScissorBegin(0, 495*Mideas.getDisplayYFactor(), 500, 305*Mideas.getDisplayYFactor());
		this.filterY = (short)(this.filterYSave-this.filterScrollbarOffset);
		Sprites.chat_channel_button.drawBegin();
		while(++i < this.categoryList.size()) {
			this.categoryList.get(i).drawTexturePart();
		}
		Sprites.chat_channel_button.drawEnd();
		i = -1;
		this.filterY = (short)(this.filterYSave-this.filterScrollbarOffset);
		this.filterFont.drawBegin();
		while(++i < this.categoryList.size()) {
			this.categoryList.get(i).drawStringPart();
		}
		this.filterFont.drawEnd();
		i = -1;
		this.filterY = (short)(this.filterYSave-this.filterScrollbarOffset);
		Sprites.button_menu_hover.drawBlendBegin();
		while(++i < this.categoryList.size()) {
			this.categoryList.get(i).drawHoverPart();
		}
		Sprites.button_menu_hover.drawBlendEnd();
		Draw.glScissorEnd();
	}
	
	private void drawBrowseItem() {
		ArrayList<AuctionEntry> list = Mideas.joueur1().getAuctionHouse().getQueryList();
		if(list.size() == 0 && !this.querySent) {
			if(this.hasSearched) {
				FontManager.get("FRIZQT", 13).drawStringShadow(this.frame.getXFrame()+445*Mideas.getDisplayXFactor(), this.frame.getYFrame()+115*Mideas.getDisplayYFactor(), "No items found", Color.YELLOW, Color.BLACK, 1, 0, 0);
			}
			else if(!this.querySent) {
				FontManager.get("FRIZQT", 13).drawStringShadow(this.frame.getXFrame()+363*Mideas.getDisplayXFactor(), this.frame.getYFrame()+115*Mideas.getDisplayYFactor(), "Choose search criteria and press \"Search\"", Color.YELLOW, Color.BLACK, 1, 0, 0);
			}
			else {
				FontManager.get("FRIZQT", 13).drawStringShadow(this.frame.getXFrame()+448*Mideas.getDisplayXFactor(), this.frame.getYFrame()+115*Mideas.getDisplayYFactor(), "Searching...", Color.YELLOW, Color.BLACK, 1, 0, 0);
			}
			return;
		}
		if(this.querySent) {
			return;
		}
		this.itemY = this.itemYSave;
		int i = this.itemStartIndex;
		int end = Math.min(this.itemStartIndex+this.MAXIMUM_ITEM_DISPLAYED, this.queryButtonList.size());
		while(i < end && this.queryButtonList.get(i).getEntry() != null) {
			this.queryButtonList.get(i).draw();
			incrementBrowseItemY();
			i++;
		}
		this.itemY = this.itemYSave;
		i = this.itemStartIndex;
		AuctionBrowseQueryButton.ITEM_NAME_FONT.drawBegin();
		while(i < end && this.queryButtonList.get(i).getEntry() != null) {
			this.queryButtonList.get(i).drawItemName();
			incrementBrowseItemY();
			i++;
		}
		AuctionBrowseQueryButton.ITEM_NAME_FONT.drawEnd();
		this.itemY = this.itemYSave;
		i = this.itemStartIndex;
		AuctionBrowseQueryButton.DURATION_FONT.drawBegin();
		while(i < end && this.queryButtonList.get(i).getEntry() != null) {
			this.queryButtonList.get(i).drawString();
			incrementBrowseItemY();
			i++;
		}
		AuctionBrowseQueryButton.DURATION_FONT.drawEnd();
		this.itemY = this.itemYSave;
		i = this.itemStartIndex;
		Sprites.gold_coin.drawBegin();
		while(i < end && this.queryButtonList.get(i).getEntry() != null) {
			this.queryButtonList.get(i).drawGoldTexture();
			incrementBrowseItemY();
			i++;
		}
		Sprites.gold_coin.drawEnd();
		this.itemY = this.itemYSave;
		i = this.itemStartIndex;
		Sprites.silver_coin.drawBegin();
		while(i < end && this.queryButtonList.get(i).getEntry() != null) {
			this.queryButtonList.get(i).drawSilverTexture();
			incrementBrowseItemY();
			i++;
		}
		Sprites.silver_coin.drawEnd();
		this.itemY = this.itemYSave;
		i = this.itemStartIndex;
		Sprites.copper_coin.drawBegin();
		while(i < end && this.queryButtonList.get(i).getEntry() != null) {
			this.queryButtonList.get(i).drawCopperTexture();
			incrementBrowseItemY();
			i++;
		}
		Sprites.copper_coin.drawEnd();
		this.itemY = this.itemYSave;
		i = this.itemStartIndex;
		AuctionBrowseQueryButton.GOLD_FONT.drawBegin();
		while(i < end && this.queryButtonList.get(i).getEntry() != null) {
			this.queryButtonList.get(i).drawGoldString();
			incrementBrowseItemY();
			i++;
		}
		AuctionBrowseQueryButton.GOLD_FONT.drawEnd();
		if(this.itemScrollbar.getScrollPercentage() == 1f) {
			FontManager.get("FRIZQT", 11).drawStringShadow(this.frame.getXFrame()+(570-this.resultStringWidth)*Mideas.getDisplayXFactor(), this.frame.getYFrame()+375*Mideas.getDisplayYFactor(), this.resultString, Color.WHITE, Color.BLACK, 1, 0, 0);
		}
	}
	
	protected boolean mouseEvent() {
		if(this.rarityDropDownmenu.event()) return true;
		if(this.bidButton.event()) return true;
		if(this.buyoutButton.event()) return true;
		if(this.closeButton.event()) return true;
		if(this.sortByRarityButton.event()) return true;
		if(this.sortByLevelButton.event()) return true;
		if(this.sortByTimeLeftButton.event()) return true;
		if(this.sortBySellerButton.event()) return true;
		if(this.sortByPricePerUnitButton.event()) return true;
		if(this.sendQueryButton.event()) return true;
		if(this.searchEditBox.mouseEvent()) return true;
		if(this.minLevelEditBox.mouseEvent()) return true;
		if(this.maxLevelEditBox.mouseEvent()) return true;
		if(this.goldBidEditBox.mouseEvent()) return true;
		if(this.silverBidEditBox.mouseEvent()) return true;
		if(this.copperBidEditBox.mouseEvent()) return true;
		if(this.resetButton.event()) return true;
		if(this.nextPageArrow.event()) return true;
		if(this.previousPageArrow.event()) return true;
		if(this.itemScrollbarEnabled) {
			if(this.itemScrollbar.event()) return true;
		}
		if(this.filterScrollbarEnabled) {
			if(this.filterScrollbar.event()) return true;
		}
		int i = -1;
		this.filterY = (short)(this.filterYSave-this.filterScrollbarOffset);
		while(++i < this.categoryList.size()) {
			if(this.categoryList.get(i).event()) {
				return true;
			}
		}
		this.itemY = this.itemYSave;
		i = this.itemStartIndex;
		int end = Math.min(this.itemStartIndex+this.MAXIMUM_ITEM_DISPLAYED, this.queryButtonList.size());
		while(i < end && this.queryButtonList.get(i).getEntry() != null) {
			if(this.queryButtonList.get(i).mouseEvent()) {
				return true;
			}
			incrementBrowseItemY();
			i++;
		}
		return false;
	}
	
	protected boolean keyboardEvent() {
		if(this.searchEditBox.keyboardEvent()) return true;
		if(this.minLevelEditBox.keyboardEvent()) return true;
		if(this.maxLevelEditBox.keyboardEvent()) return true;
		if(this.goldBidEditBox.keyboardEvent()) return true;
		if(this.silverBidEditBox.keyboardEvent()) return true;
		if(this.copperBidEditBox.keyboardEvent()) return true;
		return false;
	}
	
	private void enableBrowseItemScrollbar() {
		this.itemScrollbarEnabled = true;
		this.sortByPricePerUnitButton.update(this.frame.getXFrame()+this.SORT_PRICE_PER_UNIT_BUTTON_X*Mideas.getDisplayXFactor(), this.frame.getYFrame()+this.SORT_BUTTON_Y*Mideas.getDisplayYFactor(), this.SORT_PRICE_PER_UNIT_BUTTON_WIDTH_SCROLLBAR*Mideas.getDisplayXFactor(), this.SORT_BUTTON_HEIGHT*Mideas.getDisplayYFactor());
	}
	
	private void disableBrowseItemScrollbar() {
		this.itemScrollbarEnabled = false;
		this.sortByPricePerUnitButton.update(this.frame.getXFrame()+this.SORT_PRICE_PER_UNIT_BUTTON_X*Mideas.getDisplayXFactor(), this.frame.getYFrame()+this.SORT_BUTTON_Y*Mideas.getDisplayYFactor(), this.SORT_PRICE_PER_UNIT_BUTTON_WIDTH_NO_SCROLLBAR*Mideas.getDisplayXFactor(), this.SORT_BUTTON_HEIGHT*Mideas.getDisplayYFactor());
	}
	
	private void updateSize() {
		if(!this.shouldUpdate) {
			return;
		}
		if(this.filterScrollbarEnabled) {
			this.filterWidth = (short)(this.FILTER_WIDTH_SCROLLBAR*Mideas.getDisplayXFactor());
		}
		else {
			this.filterWidth = (short)(this.FILTER_WIDTH_NO_SCROLLBAR*Mideas.getDisplayXFactor());
		}
		this.filterHeight = (short)(this.FILTER_HEIGHT*Mideas.getDisplayYFactor());
		this.filterX = (short)(this.frame.getXFrame()+this.FILTER_X*Mideas.getDisplayXFactor());
		this.filterYSave = (short)(this.frame.getYFrame()+this.FILTER_Y*Mideas.getDisplayYFactor());
		this.filterYShift = (short)(this.FILTER_Y_SHIFT*Mideas.getDisplayYFactor());
		this.itemYSave = (short)(this.frame.getYFrame()+this.ITEM_Y*Mideas.getDisplayYFactor());
		this.itemX = (short)(this.frame.getXFrame()+this.ITEM_X*Mideas.getDisplayXFactor());
		this.itemHeight = (short)(this.ITEM_HEIGHT*Mideas.getDisplayYFactor());
		this.itemYShift =  (short)(this.ITEM_Y_SHIFT*Mideas.getDisplayYFactor());
		this.bidButton.update(this.frame.getXFrame()+this.BID_BUTTON_X*Mideas.getDisplayXFactor(), this.frame.getYFrame()+this.CLOSE_BUTTON_Y*Mideas.getDisplayYFactor());
		this.buyoutButton.update(this.frame.getXFrame()+this.BUYOUT_BUTTON_X*Mideas.getDisplayXFactor(), this.frame.getYFrame()+this.CLOSE_BUTTON_Y*Mideas.getDisplayYFactor());
		this.closeButton.update(this.frame.getXFrame()+this.CLOSE_BUTTON_X*Mideas.getDisplayXFactor(), this.frame.getYFrame()+this.CLOSE_BUTTON_Y*Mideas.getDisplayYFactor());
		this.filterScrollbar.update(this.frame.getXFrame()+this.FILTER_SCROLLBAR_X*Mideas.getDisplayXFactor(), this.frame.getYFrame()+this.FILTER_SCROLLBAR_Y*Mideas.getDisplayYFactor(), this.FILTER_SCROLLBAR_HEIGHT*Mideas.getDisplayYFactor(), this.FILTER_SCROLLBAR_WIDTH*Mideas.getDisplayXFactor(), this.FILTER_SCROLLBAR_HEIGHT*Mideas.getDisplayYFactor(), this.FILTER_HEIGHT*Mideas.getDisplayYFactor());
		this.sortByRarityButton.update(this.frame.getXFrame()+this.SORT_RARITY_BUTTON_X*Mideas.getDisplayXFactor(), this.frame.getYFrame()+this.SORT_BUTTON_Y*Mideas.getDisplayYFactor(), this.SORT_RARITY_BUTTON_WIDTH*Mideas.getDisplayXFactor(), this.SORT_BUTTON_HEIGHT*Mideas.getDisplayYFactor());
		this.sortByLevelButton.update(this.frame.getXFrame()+this.SORT_LEVEL_BUTTON_X*Mideas.getDisplayXFactor(), this.frame.getYFrame()+this.SORT_BUTTON_Y*Mideas.getDisplayYFactor(), this.SORT_LEVEL_BUTTON_WIDTH*Mideas.getDisplayXFactor(), this.SORT_BUTTON_HEIGHT*Mideas.getDisplayYFactor());
		this.sortByTimeLeftButton.update(this.frame.getXFrame()+this.SORT_TIME_LEFT_BUTTON_X*Mideas.getDisplayXFactor(), this.frame.getYFrame()+this.SORT_BUTTON_Y*Mideas.getDisplayYFactor(), this.SORT_TIME_LEFT_BUTTON_WIDTH*Mideas.getDisplayXFactor(), this.SORT_BUTTON_HEIGHT*Mideas.getDisplayYFactor());
		this.sortBySellerButton.update(this.frame.getXFrame()+this.SORT_SELLER_BUTTON_X*Mideas.getDisplayXFactor(), this.frame.getYFrame()+this.SORT_BUTTON_Y*Mideas.getDisplayYFactor(), this.SORT_SELLER_BUTTON_WIDTH*Mideas.getDisplayXFactor(), this.SORT_BUTTON_HEIGHT*Mideas.getDisplayYFactor());
		if(this.itemScrollbarEnabled) {
			this.sortByPricePerUnitButton.update(this.frame.getXFrame()+this.SORT_PRICE_PER_UNIT_BUTTON_X*Mideas.getDisplayXFactor(), this.frame.getYFrame()+this.SORT_BUTTON_Y*Mideas.getDisplayYFactor(), this.SORT_PRICE_PER_UNIT_BUTTON_WIDTH_SCROLLBAR*Mideas.getDisplayXFactor(), this.SORT_BUTTON_HEIGHT*Mideas.getDisplayYFactor());
		}
		else {
			this.sortByPricePerUnitButton.update(this.frame.getXFrame()+this.SORT_PRICE_PER_UNIT_BUTTON_X*Mideas.getDisplayXFactor(), this.frame.getYFrame()+this.SORT_BUTTON_Y*Mideas.getDisplayYFactor(), this.SORT_PRICE_PER_UNIT_BUTTON_WIDTH_NO_SCROLLBAR*Mideas.getDisplayXFactor(), this.SORT_BUTTON_HEIGHT*Mideas.getDisplayYFactor());
			
		}
		this.sendQueryButton.update(this.frame.getXFrame()+this.SEND_QUERY_BUTTON_X*Mideas.getDisplayXFactor(), this.frame.getYFrame()+this.SEND_QUERY_BUTTON_Y*Mideas.getDisplayYFactor());
		this.resetButton.update(this.frame.getXFrame()+this.RESET_BUTTON_X*Mideas.getDisplayXFactor(), this.frame.getYFrame()+this.SEND_QUERY_BUTTON_Y*Mideas.getDisplayYFactor());
		this.searchEditBox.update(this.frame.getXFrame()+this.SEARCH_EDIT_BOX_X*Mideas.getDisplayXFactor(), this.frame.getYFrame()+this.SEARCH_EDIT_BOX_Y*Mideas.getDisplayYFactor());
		this.minLevelEditBox.update(this.frame.getXFrame()+this.MIN_LEVEL_EDIT_BOX_X*Mideas.getDisplayXFactor(), this.frame.getYFrame()+this.SEARCH_EDIT_BOX_Y*Mideas.getDisplayYFactor());
		this.maxLevelEditBox.update(this.frame.getXFrame()+this.MAX_LEVEL_EDIT_BOX_X*Mideas.getDisplayXFactor(), this.frame.getYFrame()+this.SEARCH_EDIT_BOX_Y*Mideas.getDisplayYFactor());
		this.rarityDropDownmenu.update(this.frame.getXFrame()+this.QUALITY_FILTER_DROP_DOWN_X*Mideas.getDisplayXFactor(), this.frame.getYFrame()+this.QUALITY_FILTER_DROP_DOWN_Y*Mideas.getDisplayXFactor(), this.QUALITY_FILTER_DROP_DOWN_BAR_WIDTH*Mideas.getDisplayXFactor(), this.frame.getXFrame()+this.QUALITY_FILTER_DROP_DOWN_X*Mideas.getDisplayXFactor(), this.frame.getYFrame()+(this.QUALITY_FILTER_DROP_DOWN_Y+20)*Mideas.getDisplayYFactor(), this.QUALITY_FILTER_DROP_DOWN_MENU_WIDTH*Mideas.getDisplayXFactor());
		this.goldBidEditBox.update(this.frame.getXFrame()+this.BID_GOLD_EDIT_BOX_X*Mideas.getDisplayXFactor(), this.frame.getYFrame()+this.BID_EDIT_BOX_Y*Mideas.getDisplayYFactor());
		this.silverBidEditBox.update(this.frame.getXFrame()+this.BID_SILVER_EDIT_BOX_X*Mideas.getDisplayXFactor(), this.frame.getYFrame()+this.BID_EDIT_BOX_Y*Mideas.getDisplayYFactor());
		this.copperBidEditBox.update(this.frame.getXFrame()+this.BID_COPPER_EDIT_BOX_X*Mideas.getDisplayXFactor(), this.frame.getYFrame()+this.BID_EDIT_BOX_Y*Mideas.getDisplayYFactor());
		this.nextPageArrow.update(this.frame.getXFrame()+this.NEXT_PAGE_ARROW_X*Mideas.getDisplayXFactor(), this.frame.getYFrame()+this.PAGE_ARROW_Y*Mideas.getDisplayYFactor());
		this.previousPageArrow.update(this.frame.getXFrame()+this.PREVIOUS_PAGE_ARROW_X*Mideas.getDisplayXFactor(), this.frame.getYFrame()+this.PAGE_ARROW_Y*Mideas.getDisplayYFactor());
		this.itemScrollbar.update(this.frame.getXFrame()+this.ITEM_SCROLLBAR_X*Mideas.getDisplayXFactor(), this.frame.getYFrame()+this.ITEM_SCROLLBAR_Y*Mideas.getDisplayYFactor(), this.ITEM_SCROLLBAR_HEIGHT*Mideas.getDisplayYFactor(), this.ITEM_SCROLLBAR_WIDTH*Mideas.getDisplayXFactor(), (this.ITEM_SCROLLBAR_HEIGHT+15)*Mideas.getDisplayYFactor(), this.itemYShift*Mideas.getDisplayYFactor());
		int i = -1;
		System.out.println("Search size : "+this.searchEditBox.getWidth());
		while(++i < this.queryButtonList.size()) {
			this.queryButtonList.get(i).updateSize();
		}
		this.shouldUpdate = false;
	}
	
	protected void unexpandAllCategoryFilter() {
		int i = -1;
		while(++i < this.categoryList.size()) {
			this.categoryList.get(i).unexpand();
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
		this.selectedEntry = entry;
		if(entry != null) {
			this.copperBidEditBox.setText(entry.getBidCopperString());
			this.silverBidEditBox.setText(entry.getBidSilverString());
			this.goldBidEditBox.setText(entry.getBidGoldString());
		}
	}
	
	public AuctionEntry getSelectedBrowseEntry() {
		return this.selectedEntry;
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
		while(++i < this.categoryList.size()) {
			count+= this.categoryList.get(i).getNumberLine();
		}
		this.filterNumberLine = (byte)count;
		this.filterScrollbar.onScroll();
		if(this.filterScrollbarEnabled) {
			if(count > 15) {
				return;
			}
			hideBrowseFilterScrollbar();
		}
		else if(!this.filterScrollbarEnabled) {
			if(count < 15) {
				return;
			}
			showBrowseFilterScrollbar();
		}
	}
	
	private void hideBrowseFilterScrollbar() {
		this.filterWidth = (short)(this.FILTER_WIDTH_NO_SCROLLBAR*Mideas.getDisplayXFactor());
		this.filterScrollbarEnabled = false;
	}
	
	private void showBrowseFilterScrollbar() {
		this.filterWidth = (short)(this.FILTER_WIDTH_SCROLLBAR*Mideas.getDisplayXFactor());
		this.filterScrollbarEnabled = true;
	}
	
	private void buildBrowseFilterQualityMenu() {
		this.rarityDropDownmenu.resetMenuList();
		this.rarityDropDownmenu.addMenu(this.rarityAllTextMenu);
		this.rarityDropDownmenu.addMenu(this.rarityPoorTextMenu);
		this.rarityDropDownmenu.addMenu(this.rarityCommonTextMenu);
		this.rarityDropDownmenu.addMenu(this.rarityUncommonTextMenu);
		this.rarityDropDownmenu.addMenu(this.rarityRareTextMenu);
		this.rarityDropDownmenu.addMenu(this.rarityEpicTextMenu);
		this.rarityDropDownmenu.addMenu(this.rarityLegendaryTextMenu);
	}
	
	private void buildBrowseFilterMenu() {
		this.categoryList.add(new AuctionCategoryFilterButton(this, AuctionHouseFilter.WEAPON));
		this.categoryList.get(0).addFilter(AuctionHouseFilter.WEAPON_ONE_HANDED_AXE);
		this.categoryList.get(0).addFilter(AuctionHouseFilter.WEAPON_TWO_HANDED_AXE);
		this.categoryList.get(0).addFilter(AuctionHouseFilter.WEAPON_BOW);
		this.categoryList.get(0).addFilter(AuctionHouseFilter.WEAPON_GUN);
		this.categoryList.get(0).addFilter(AuctionHouseFilter.WEAPON_ONE_HANDED_MACE);
		this.categoryList.get(0).addFilter(AuctionHouseFilter.WEAPON_TWO_HANDED_MACE);
		this.categoryList.get(0).addFilter(AuctionHouseFilter.WEAPON_POLEARM);
		this.categoryList.get(0).addFilter(AuctionHouseFilter.WEAPON_ONE_HANDED_SWORD);
		this.categoryList.get(0).addFilter(AuctionHouseFilter.WEAPON_TWO_HANDED_SWORD);
		this.categoryList.get(0).addFilter(AuctionHouseFilter.WEAPON_STAFF);
		this.categoryList.get(0).addFilter(AuctionHouseFilter.WEAPON_FIST_WEAPON);
		this.categoryList.get(0).addFilter(AuctionHouseFilter.WEAPON_MISCELLANEOUS);
		this.categoryList.get(0).addFilter(AuctionHouseFilter.WEAPON_DAGGERS);
		this.categoryList.get(0).addFilter(AuctionHouseFilter.WEAPON_THROW_WEAPON);
		this.categoryList.get(0).addFilter(AuctionHouseFilter.WEAPON_CROSSBOW);
		this.categoryList.get(0).addFilter(AuctionHouseFilter.WEAPON_WAND);
		this.categoryList.get(0).addFilter(AuctionHouseFilter.WEAPON_FISHING_POLES);

		this.categoryList.add(new AuctionCategoryFilterButton(this, AuctionHouseFilter.ARMOR));
		this.categoryList.get(1).addFilter(AuctionHouseFilter.ARMOR_MISCELLANEOUS);
		this.categoryList.get(1).addFilter(AuctionHouseFilter.ARMOR_CLOTH);
		this.categoryList.get(1).addFilter(AuctionHouseFilter.ARMOR_LEATHER);
		this.categoryList.get(1).addFilter(AuctionHouseFilter.ARMOR_MAIL);
		this.categoryList.get(1).addFilter(AuctionHouseFilter.ARMOR_PLATE);
		this.categoryList.get(1).addFilter(AuctionHouseFilter.ARMOR_SHIELDS);
		this.categoryList.get(1).addFilter(AuctionHouseFilter.ARMOR_LIBRAMS);
		this.categoryList.get(1).addFilter(AuctionHouseFilter.ARMOR_IDOLS);
		this.categoryList.get(1).addFilter(AuctionHouseFilter.ARMOR_TOTEMS);

		this.categoryList.add(new AuctionCategoryFilterButton(this, AuctionHouseFilter.CONTAINER));
		this.categoryList.get(2).addFilter(AuctionHouseFilter.CONTAINER_BAG);
		this.categoryList.get(2).addFilter(AuctionHouseFilter.CONTAINER_SOUL_BAG);
		this.categoryList.get(2).addFilter(AuctionHouseFilter.CONTAINER_HERB_BAG);
		this.categoryList.get(2).addFilter(AuctionHouseFilter.CONTAINER_ENCHANTING_BAG);
		this.categoryList.get(2).addFilter(AuctionHouseFilter.CONTAINER_ENGINEERING_BAG);
		this.categoryList.get(2).addFilter(AuctionHouseFilter.CONTAINER_GEM_BAG);
		this.categoryList.get(2).addFilter(AuctionHouseFilter.CONTAINER_MINING_BAG);
		this.categoryList.get(2).addFilter(AuctionHouseFilter.CONTAINER_LEATHERWORKING_BAG);

		this.categoryList.add(new AuctionCategoryFilterButton(this, AuctionHouseFilter.CONSUMABLES));
		this.categoryList.get(3).addFilter(AuctionHouseFilter.CONSUMABLES_FOOD_AND_DRINK);
		this.categoryList.get(3).addFilter(AuctionHouseFilter.CONSUMABLES_POTION);
		this.categoryList.get(3).addFilter(AuctionHouseFilter.CONSUMABLES_ELIXIR);
		this.categoryList.get(3).addFilter(AuctionHouseFilter.CONSUMABLES_FLASK);
		this.categoryList.get(3).addFilter(AuctionHouseFilter.CONSUMABLES_BANDAGE);
		this.categoryList.get(3).addFilter(AuctionHouseFilter.CONSUMABLES_ITEMS_ENCHANCEMENTS);
		this.categoryList.get(3).addFilter(AuctionHouseFilter.CONSUMABLES_PARCHEMIN);
		this.categoryList.get(3).addFilter(AuctionHouseFilter.CONSUMABLES_MISCELLANEOUS);

		this.categoryList.add(new AuctionCategoryFilterButton(this, AuctionHouseFilter.TRADE_GOOD));
		this.categoryList.get(4).addFilter(AuctionHouseFilter.TRADE_GOOD_ELEMENTAL);
		this.categoryList.get(4).addFilter(AuctionHouseFilter.TRADE_GOOD_CLOTH);
		this.categoryList.get(4).addFilter(AuctionHouseFilter.TRADE_GOOD_LEATHER);
		this.categoryList.get(4).addFilter(AuctionHouseFilter.TRADE_GOOD_METAL_AND_STONE);
		this.categoryList.get(4).addFilter(AuctionHouseFilter.TRADE_GOOD_COOKING);
		this.categoryList.get(4).addFilter(AuctionHouseFilter.TRADE_GOOD_HERB);
		this.categoryList.get(4).addFilter(AuctionHouseFilter.TRADE_GOOD_ENCHANTING);
		this.categoryList.get(4).addFilter(AuctionHouseFilter.TRADE_GOOD_JEWELCRAFTING);
		this.categoryList.get(4).addFilter(AuctionHouseFilter.TRADE_GOOD_PARTS);
		this.categoryList.get(4).addFilter(AuctionHouseFilter.TRADE_GOOD_MATERIALS);
		this.categoryList.get(4).addFilter(AuctionHouseFilter.TRADE_GOOD_OTHER);

		this.categoryList.add(new AuctionCategoryFilterButton(this, AuctionHouseFilter.PROJECTILE));
		this.categoryList.get(5).addFilter(AuctionHouseFilter.PROJECTILE_ARROW);
		this.categoryList.get(5).addFilter(AuctionHouseFilter.PROJECTILE_BULLET);
		
		this.categoryList.add(new AuctionCategoryFilterButton(this, AuctionHouseFilter.QUIVER));
		this.categoryList.get(6).addFilter(AuctionHouseFilter.QUIVER_QUIVER);
		this.categoryList.get(6).addFilter(AuctionHouseFilter.QUIVER_GIBERNE);

		this.categoryList.add(new AuctionCategoryFilterButton(this, AuctionHouseFilter.RECIPES));
		this.categoryList.get(7).addFilter(AuctionHouseFilter.RECIPES_BOOK);
		this.categoryList.get(7).addFilter(AuctionHouseFilter.RECIPES_LEATHERWORKING);
		this.categoryList.get(7).addFilter(AuctionHouseFilter.RECIPES_TAILORING);
		this.categoryList.get(7).addFilter(AuctionHouseFilter.RECIPES_ENGINEERING);
		this.categoryList.get(7).addFilter(AuctionHouseFilter.RECIPES_BLACKSMITHING);
		this.categoryList.get(7).addFilter(AuctionHouseFilter.RECIPES_COOKING);
		this.categoryList.get(7).addFilter(AuctionHouseFilter.RECIPES_ALCHEMY);
		this.categoryList.get(7).addFilter(AuctionHouseFilter.RECIPES_FIRST_AID);
		this.categoryList.get(7).addFilter(AuctionHouseFilter.RECIPES_ENCHANTING);
		this.categoryList.get(7).addFilter(AuctionHouseFilter.RECIPES_FISHING);
		this.categoryList.get(7).addFilter(AuctionHouseFilter.RECIPES_JEWELCRAFTING);

		this.categoryList.add(new AuctionCategoryFilterButton(this, AuctionHouseFilter.GEM));
		this.categoryList.get(8).addFilter(AuctionHouseFilter.GEM_RED);
		this.categoryList.get(8).addFilter(AuctionHouseFilter.GEM_BLUE);
		this.categoryList.get(8).addFilter(AuctionHouseFilter.GEM_YELLOW);
		this.categoryList.get(8).addFilter(AuctionHouseFilter.GEM_PURPLE);
		this.categoryList.get(8).addFilter(AuctionHouseFilter.GEM_GREEN);
		this.categoryList.get(8).addFilter(AuctionHouseFilter.GEM_ORANGE);
		this.categoryList.get(8).addFilter(AuctionHouseFilter.GEM_META);
		this.categoryList.get(8).addFilter(AuctionHouseFilter.GEM_SIMPLE);
		this.categoryList.get(8).addFilter(AuctionHouseFilter.GEM_PRISMATIC);

		this.categoryList.add(new AuctionCategoryFilterButton(this, AuctionHouseFilter.MISCELLANEOUS));
		this.categoryList.get(9).addFilter(AuctionHouseFilter.MISCELLANEOUS_JUNK);
		this.categoryList.get(9).addFilter(AuctionHouseFilter.MISCELLANEOUS_REAGENT);
		this.categoryList.get(9).addFilter(AuctionHouseFilter.MISCELLANEOUS_COMPANION_PETS);
		this.categoryList.get(9).addFilter(AuctionHouseFilter.MISCELLANEOUS_HOLIDAY);
		this.categoryList.get(9).addFilter(AuctionHouseFilter.MISCELLANEOUS_OTHER);
		this.categoryList.get(9).addFilter(AuctionHouseFilter.MISCELLANEOUS_MOUNT);

		this.categoryList.add(new AuctionCategoryFilterButton(this, AuctionHouseFilter.QUESTS));
	}
	
	protected void buildResultString(byte result, int totalResult, short page, short numberPage) {
		this.page = page;
		this.totalPage = numberPage;
		if(result == 0 ) {
			return;
		}
		this.resultString = "Items "+(1+(page-1)*50)+" - "+(page*50)+" ( "+totalResult+" total )";
		this.resultStringWidth = (short)FontManager.get("FRIZQT", 11).getWidth(this.resultString);
	}
	
	protected void frameClosed() {
		if(Mideas.joueur1() != null) {
			Mideas.joueur1().getAuctionHouse().clearQueryList();
		}
		this.hasSearched = false;
		disableBrowseItemScrollbar();
		this.page = 1;
		this.totalPage = 0;
		this.rarityDropDownmenu.setValue(0);
		this.goldBidEditBox.setActive(false);
		this.silverBidEditBox.setActive(false);
		this.copperBidEditBox.setActive(false);
		this.searchEditBox.setActive(false);
		this.minLevelEditBox.setActive(false);
		this.maxLevelEditBox.setActive(false);
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
		this.filterY+= this.filterYShift;
	}
	
	protected boolean browseItemScrollbarEnabled() {
		return this.itemScrollbarEnabled;
	}
	
	protected short getBrowseItemX() {
		return this.itemX;
	}
	
	protected short getBrowseItemWidth() {
		return this.itemWidth;
	}
	
	protected short getBrowseItemY() {
		return this.itemY;
	}
	
	protected short getBrowseItemHeight() {
		return this.itemHeight;
	}
	
	protected void incrementBrowseItemY() {
		this.itemY+= this.itemYShift;
	}
	
	protected short getBrowseFilterY() {
		return this.filterY;
	}
	
	protected short getBrowseFilterX() {
		return this.filterX;
	}
	
	protected short getBrowseFilterYShift() {
		return this.filterYShift;
	}
	
	protected short getBrowseFilterWidth() {
		return this.filterWidth;
	}
	
	protected short getBrowseFilterHeight() {
		return this.filterHeight;
	}
}
