package com.mideas.rpg.v2.hud.auction;

import java.util.ArrayList;

import com.mideas.rpg.v2.FontManager;
import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.game.auction.AuctionHouseFilter;
import com.mideas.rpg.v2.game.auction.AuctionHouseSort;
import com.mideas.rpg.v2.render.Draw;
import com.mideas.rpg.v2.render.Sprites;
import com.mideas.rpg.v2.render.TTF;
import com.mideas.rpg.v2.utils.ButtonMenuSort;
import com.mideas.rpg.v2.utils.Color;
import com.mideas.rpg.v2.utils.Frame;
import com.mideas.rpg.v2.utils.InputBox;
import com.mideas.rpg.v2.utils.ScrollBar;

public class AuctionHouseBrowseFrame extends Frame
{
	
	@SuppressWarnings("hiding")
	private AuctionHouseFrame parentFrame;
	private final ArrayList<AuctionHouseBrowseCategoryFilterButton> categoryList;
	private AuctionHouseFilter categoryFilter = AuctionHouseFilter.NONE;
	private boolean categoryFilterScrollbarEnabled;
	private short filterScrollbarYOffset;
	private short categoryFilterTotalLine;
	AuctionHouseSort selectedSort = AuctionHouseSort.RARITY_ASCENDING;
	private final InputBox searchInputBox = new InputBox(this, "AuctionHouseFrameSearchInputBox", SEARCH_INPUT_BOX_X, SEARCH_INPUT_BOX_Y, SEARCH_INPUT_BOX_WIDTH, (short)63, SEARCH_INPUT_BOX_TEXT_X_OFFSET, SEARCH_INPUT_BOX_TEXT_Y_OFFSET, SEARCH_INPUT_BOX_TEXT_MAX_WIDTH, INPUT_BOX_FONT, false, SEARCH_INPUT_BOX_CURSOR_WIDTH, SEARCH_INPUT_BOX_CURSOR_HEIGHT, "Search", true, INPUT_BOX_COLOR)
	{
	
		@Override
		public boolean keyEvent(char c)
		{
			return (false);
		}
	};	
	private final InputBox minLevelInputBox = new InputBox(this, "AuctionHouseFrameMinLevelInputBox", MIN_LEVEL_INPUT_BOX_X, MIN_LEVEL_INPUT_BOX_Y, MIN_LEVEL_INPUT_BOX_WIDTH, (short)2, MIN_LEVEL_INPUT_BOX_TEXT_X_OFFSET, MIN_LEVEL_INPUT_BOX_TEXT_Y_OFFSET, MIN_LEVEL_INPUT_BOX_TEXT_MAX_WIDTH, INPUT_BOX_FONT, true, MIN_LEVEL_INPUT_BOX_CURSOR_WIDTH, MIN_LEVEL_INPUT_BOX_CURSOR_HEIGHT, "", false, INPUT_BOX_COLOR)
	{
	
		@Override
		public boolean keyEvent(char c)
		{
			return (false);
		}
	};	
	private final InputBox maxLevelInputBox = new InputBox(this, "AuctionHouseFrameMaxLevelInputBox", MAX_LEVEL_INPUT_BOX_X, MAX_LEVEL_INPUT_BOX_Y, MAX_LEVEL_INPUT_BOX_WIDTH, (short)2, MAX_LEVEL_INPUT_BOX_TEXT_X_OFFSET, MAX_LEVEL_INPUT_BOX_TEXT_Y_OFFSET, MAX_LEVEL_INPUT_BOX_TEXT_MAX_WIDTH, INPUT_BOX_FONT, true, MAX_LEVEL_INPUT_BOX_CURSOR_WIDTH, MAX_LEVEL_INPUT_BOX_CURSOR_HEIGHT, "", false, INPUT_BOX_COLOR)
	{
	
		@Override
		public boolean keyEvent(char c)
		{
			return (false);
		}
	};
	private final ScrollBar filterScrollbar = new ScrollBar(this, FILTER_SCROLLBAR_X, FILTER_SCROLLBAR_Y, FILTER_SCROLLBAR_HEIGHT, FILTER_SCROLLBAR_WIDTH, FILTER_SCROLLBAR_HEIGHT, false, CATEGORY_FILTER_BUTTON_Y_SHIFT, false) {
		
		@SuppressWarnings("synthetic-access")
		@Override
		public void onScroll() {
			onCategoryFilterScrollbarScroll();
		}
	};
	private final ButtonMenuSort sortByRarityButton = new ButtonMenuSort(this, SORT_RARITY_BUTTON_X, SORT_BUTTON_Y, SORT_RARITY_BUTTON_WIDTH, SORT_BUTTON_HEIGHT, "Rarity", BUTTON_SORT_FONT) {
		
		@Override
		public void eventButtonClick() {
			if(AuctionHouseBrowseFrame.this.selectedSort == AuctionHouseSort.RARITY_ASCENDING)
				AuctionHouseBrowseFrame.this.selectedSort = AuctionHouseSort.RARITY_DESCENDING;
			else
				AuctionHouseBrowseFrame.this.selectedSort = AuctionHouseSort.RARITY_ASCENDING;
			sendSearchQuery();
		}
	};
	private final ButtonMenuSort sortByLevelButton = new ButtonMenuSort(this, SORT_LEVEL_BUTTON_X, SORT_BUTTON_Y, SORT_LEVEL_BUTTON_WIDTH, SORT_BUTTON_HEIGHT, "Lvl", BUTTON_SORT_FONT) {
		
		@Override
		public void eventButtonClick() {
			if(AuctionHouseBrowseFrame.this.selectedSort == AuctionHouseSort.LEVEL_ASCENDING)
				AuctionHouseBrowseFrame.this.selectedSort = AuctionHouseSort.LEVEL_DESCENDING;
			else
				AuctionHouseBrowseFrame.this.selectedSort = AuctionHouseSort.LEVEL_ASCENDING;
			sendSearchQuery();
		}
	};
	private final ButtonMenuSort sortByTimeLeftButton = new ButtonMenuSort(this, SORT_TIME_LEFT_BUTTON_X, SORT_BUTTON_Y, SORT_TIME_LEFT_BUTTON_WIDTH, SORT_BUTTON_HEIGHT, "Time Left", BUTTON_SORT_FONT) {
		
		@Override
		public void eventButtonClick() {
			if(AuctionHouseBrowseFrame.this.selectedSort == AuctionHouseSort.TIME_LEFT_ASCENDING)
				AuctionHouseBrowseFrame.this.selectedSort = AuctionHouseSort.TIME_LEFT_DESCENDING;
			else
				AuctionHouseBrowseFrame.this.selectedSort = AuctionHouseSort.TIME_LEFT_ASCENDING;
			sendSearchQuery();
		}
	};
	private final ButtonMenuSort sortBySellerButton = new ButtonMenuSort(this, SORT_SELLER_BUTTON_X, SORT_BUTTON_Y, SORT_SELLER_BUTTON_WIDTH, SORT_BUTTON_HEIGHT, "Seller", BUTTON_SORT_FONT) {
		
		@Override
		public void eventButtonClick() {
			if(AuctionHouseBrowseFrame.this.selectedSort == AuctionHouseSort.VENDOR_ASCENDING)
				AuctionHouseBrowseFrame.this.selectedSort = AuctionHouseSort.VENDOR_DESCENDING;
			else
				AuctionHouseBrowseFrame.this.selectedSort = AuctionHouseSort.VENDOR_ASCENDING;
			sendSearchQuery();
		}
	};
	private final ButtonMenuSort sortByPricePerUnitButton = new ButtonMenuSort(this, SORT_PRICE_PER_UNIT_BUTTON_X, SORT_BUTTON_Y, SORT_PRICE_PER_UNIT_BUTTON_WIDTH_NO_SCROLLBAR, SORT_BUTTON_HEIGHT, "Price Per Unit", BUTTON_SORT_FONT) {
		
		@Override
		public void eventButtonClick() {
			if(AuctionHouseBrowseFrame.this.selectedSort == AuctionHouseSort.PRICE_PER_UNIT_ASCENDING)
				AuctionHouseBrowseFrame.this.selectedSort = AuctionHouseSort.PRICE_PER_UNIT_DESCENDING;
			else
				AuctionHouseBrowseFrame.this.selectedSort = AuctionHouseSort.PRICE_PER_UNIT_ASCENDING;
			sendSearchQuery();
		}
	};
	private final static short FILTER_SCROLLBAR_X = 167;
	private final static short FILTER_SCROLLBAR_WIDTH = 184;
	private final static short FILTER_SCROLLBAR_HEIGHT = 290;
	private final static short FILTER_SCROLLBAR_Y = 100;
	
	private final static short CATEGORY_FILTER_MAX_LINE_DISPLAYED = 15;
	private final static short CATEGORY_FILTER_BUTTON_X = 21;
	private final static short CATEGORY_FILTER_BUTTON_Y = 103;
	public  final static short CATEGORY_FILTER_BUTTON_Y_SHIFT = 20;
	private final static short CATEGORY_FILTER_BUTTON_WIDTH_NO_SCROLLBAR = 167;
	private final static short CATEGORY_FILTER_BUTTON_WIDTH_SCROLLBAR = 147;
	public  final static short CATEGORY_FILTER_MAX_Y = 408;
	public  final static short CATEGORY_FILTER_MIN_Y = 100;
	public  final static short CATEGORY_FILTER_SCISSOR_Y = 380;
	public  final static short CATEGORY_FILTER_SCISSOR_HEIGHT = 300;
	public  final static short CATEGORY_FILTER_MAX_HEIGHT = 308;
	
	private final static short MIN_LEVEL_INPUT_BOX_X = 240;
	private final static short MIN_LEVEL_INPUT_BOX_Y = 49;
	private final static short MIN_LEVEL_INPUT_BOX_WIDTH = 32;
	private final static short MIN_LEVEL_INPUT_BOX_TEXT_X_OFFSET = 4;
	private final static short MIN_LEVEL_INPUT_BOX_TEXT_Y_OFFSET = 0;
	private final static short MIN_LEVEL_INPUT_BOX_TEXT_MAX_WIDTH = 20;
	private final static short MIN_LEVEL_INPUT_BOX_CURSOR_WIDTH = 4;
	private final static short MIN_LEVEL_INPUT_BOX_CURSOR_HEIGHT = 14;
	
	private final static short MAX_LEVEL_INPUT_BOX_X = 279;
	private final static short MAX_LEVEL_INPUT_BOX_Y = 49;
	private final static short MAX_LEVEL_INPUT_BOX_WIDTH = 32;
	private final static short MAX_LEVEL_INPUT_BOX_TEXT_X_OFFSET = 4;
	private final static short MAX_LEVEL_INPUT_BOX_TEXT_Y_OFFSET = 0;
	private final static short MAX_LEVEL_INPUT_BOX_TEXT_MAX_WIDTH = 20;
	private final static short MAX_LEVEL_INPUT_BOX_CURSOR_WIDTH = 4;
	private final static short MAX_LEVEL_INPUT_BOX_CURSOR_HEIGHT = 14;
	
	private final static short SEARCH_INPUT_BOX_X = 81;
	private final static short SEARCH_INPUT_BOX_Y = 49;
	private final static short SEARCH_INPUT_BOX_WIDTH = 154;
	private final static short SEARCH_INPUT_BOX_TEXT_X_OFFSET = 23;
	private final static short SEARCH_INPUT_BOX_TEXT_Y_OFFSET = -1;
	private final static short SEARCH_INPUT_BOX_TEXT_MAX_WIDTH = 105;
	private final static short SEARCH_INPUT_BOX_CURSOR_WIDTH = 4;
	private final static short SEARCH_INPUT_BOX_CURSOR_HEIGHT = 14;
	private final static Color INPUT_BOX_COLOR = Color.WHITE;
	private final static TTF INPUT_BOX_FONT = FontManager.get("FRIZQT", 13);

	private final static short SORT_BUTTON_Y = 80;
	private final static short SORT_BUTTON_HEIGHT = 21;
	private final static short SORT_RARITY_BUTTON_WIDTH = 227;
	private final static short SORT_RARITY_BUTTON_X = 195;
	private final static short SORT_LEVEL_BUTTON_X = 420;
	private final static short SORT_LEVEL_BUTTON_WIDTH = 61;
	private final static short SORT_TIME_LEFT_BUTTON_X = 479;
	private final static short SORT_TIME_LEFT_BUTTON_WIDTH = 96;
	private final static short SORT_SELLER_BUTTON_X = 573;
	private final static short SORT_SELLER_BUTTON_WIDTH = 81;
	private final static short SORT_PRICE_PER_UNIT_BUTTON_X = 652;
	private final static short SORT_PRICE_PER_UNIT_BUTTON_WIDTH_SCROLLBAR = 195;
	private final static short SORT_PRICE_PER_UNIT_BUTTON_WIDTH_NO_SCROLLBAR = 219;
	private final static TTF BUTTON_SORT_FONT = FontManager.get("FRIZQT", 11);
	
	private short categoryFilterScissorY;
	private short categoryFilterScissorHeight;	

	public AuctionHouseBrowseFrame(AuctionHouseFrame parentFrame)
	{
		super("AuctionHouseBrowseFrame");
		this.parentFrame = parentFrame;
		this.x = this.parentFrame.getX();
		this.y = this.parentFrame.getY();
		this.searchInputBox.initParentFrame(this);
		this.minLevelInputBox.initParentFrame(this);
		this.maxLevelInputBox.initParentFrame(this);
		this.sortByLevelButton.initParentFrame(this);
		this.sortByPricePerUnitButton.initParentFrame(this);
		this.sortByRarityButton.initParentFrame(this);
		this.sortBySellerButton.initParentFrame(this);
		this.sortByTimeLeftButton.initParentFrame(this);
		this.categoryList = new ArrayList<AuctionHouseBrowseCategoryFilterButton>();
		this.categoryFilterScissorY = (short)(this.y + CATEGORY_FILTER_SCISSOR_Y * Mideas.getDisplayYFactor());
		this.categoryFilterScissorHeight = (short)(CATEGORY_FILTER_SCISSOR_HEIGHT * Mideas.getDisplayYFactor());
		this.filterScrollbar.initParentFrame(this);
		buildBrowseFilterMenu();
		int i = -1;
		while (++i < this.categoryList.size())
			this.categoryList.get(i).initParentFrame(this);
		updateCategoryButton();
	}
	
	@Override
	public void draw()
	{
		updateSize();
		Draw.drawQuad(Sprites.auction_browse_frame, this.parentFrame.getX(), this.parentFrame.getY(), this.parentFrame.getWidth(), this.parentFrame.getHeight());
		this.searchInputBox.draw();
		this.minLevelInputBox.draw();
		this.maxLevelInputBox.draw();
		drawBrowseFilter();
		drawButtonSort();
		this.filterScrollbar.draw();
	}
	
	private void drawButtonSort()
	{
		Sprites.button_menu_sort.drawBegin();
		this.sortByLevelButton.drawTexturePart();
		this.sortByPricePerUnitButton.drawTexturePart();
		this.sortByRarityButton.drawTexturePart();
		this.sortBySellerButton.drawTexturePart();
		this.sortByTimeLeftButton.drawTexturePart();
		Sprites.button_menu_sort.drawEnd();
		
		BUTTON_SORT_FONT.drawBegin();
		this.sortByRarityButton.drawStringPart();
		this.sortByLevelButton.drawStringPart();
		this.sortByTimeLeftButton.drawStringPart();
		this.sortBySellerButton.drawStringPart();
		this.sortByPricePerUnitButton.drawStringPart();
		BUTTON_SORT_FONT.drawEnd();
		
		Sprites.button_menu_hover.drawBlendBegin();
		this.sortByRarityButton.drawHoverPart();
		this.sortByLevelButton.drawHoverPart();
		this.sortByTimeLeftButton.drawHoverPart();
		this.sortBySellerButton.drawHoverPart();
		this.sortByPricePerUnitButton.drawHoverPart();
		Sprites.button_menu_hover.drawBlendEnd();
	}
	
	private void drawBrowseFilter()
	{
		int i = -1;
		Draw.glScissorBegin(0, this.categoryFilterScissorY, 500, this.categoryFilterScissorHeight);
		Sprites.chat_channel_button.drawBegin();
		while(++i < this.categoryList.size())
			this.categoryList.get(i).drawTexturePart();
		Sprites.chat_channel_button.drawEnd();
		i = -1;
		AuctionHouseBrowseCategoryFilterButton.font.drawBegin();
		while(++i < this.categoryList.size())
			this.categoryList.get(i).drawStringPart();
		AuctionHouseBrowseCategoryFilterButton.font.drawEnd();
		i = -1;
		Sprites.button_menu_hover.drawBlendBegin();
		while(++i < this.categoryList.size())
			this.categoryList.get(i).drawHoverPart();
		Sprites.button_menu_hover.drawBlendEnd();
		Draw.glScissorEnd();
	}
	
	@Override
	public boolean mouseEvent()
	{
		if (this.searchInputBox.mouseEvent())
			return (true);
		if (this.minLevelInputBox.mouseEvent())
			return (true);
		if (this.maxLevelInputBox.mouseEvent())
			return (true);
		if (this.filterScrollbar.event())
			return (true);
		if (this.sortByLevelButton.mouseEvent())
			return (true);
		if (this.sortByPricePerUnitButton.mouseEvent())
			return (true);
		if (this.sortByRarityButton.mouseEvent())
			return (true);
		if (this.sortBySellerButton.mouseEvent())
			return (true);
		if (this.sortByTimeLeftButton.mouseEvent())
			return (true);
		int i = -1;
		while (++i < this.categoryList.size())
			if (this.categoryList.get(i).mouseEvent())
				return (true);
		return (false);
	}
	
	@Override
	public boolean keyboardEvent()
	{
		if (this.searchInputBox.keyboardEvent())
			return (true);
		if (this.minLevelInputBox.keyboardEvent())
			return (true);
		if (this.maxLevelInputBox.keyboardEvent())
			return (true);
		return (false);
	}
	
	void sendSearchQuery()
	{
		
	}
	
	@Override
	public void open()
	{
		
	}
	
	@Override
	public void close()
	{
		this.searchInputBox.setActive(false);
		this.minLevelInputBox.setActive(false);
		this.maxLevelInputBox.setActive(false);
	}
	
	@Override
	public boolean isOpen()
	{
		return (false);
	}
	
	@Override
	public void reset()
	{
		this.searchInputBox.resetText();
		this.minLevelInputBox.resetText();
		this.maxLevelInputBox.resetText();
	}
	
	public void updateSize()
	{
		if (!this.shouldUpdateSize)
			return;
		this.x = this.parentFrame.getX();
		this.y = this.parentFrame.getY();
		this.categoryFilterScissorY = (short)(this.y + CATEGORY_FILTER_SCISSOR_Y * Mideas.getDisplayYFactor());
		this.categoryFilterScissorHeight = (short)(CATEGORY_FILTER_SCISSOR_HEIGHT * Mideas.getDisplayYFactor());
		this.searchInputBox.updateSize();
		this.minLevelInputBox.updateSize();
		this.maxLevelInputBox.updateSize();
		this.filterScrollbar.updateSize();
		this.sortByLevelButton.updateSize();
		this.sortByPricePerUnitButton.updateSize();
		this.sortByRarityButton.updateSize();
		this.sortBySellerButton.updateSize();
		this.sortByTimeLeftButton.updateSize();
		int i = -1;
		while (++i < this.categoryList.size())
			this.categoryList.get(i).updateSize();
		this.shouldUpdateSize = false;
	}
	
	private void checkCategoryFilterScrollbar()
	{
		
		updateCategoryButton();
		if (this.categoryFilterTotalLine > CATEGORY_FILTER_MAX_LINE_DISPLAYED)
			enableCategoryFilterScrollbar();
		else
			disabledCategoryFilterScrollbar();
	}
	
	private void enableCategoryFilterScrollbar()
	{
		if (this.categoryFilterScrollbarEnabled)
			return;
		this.filterScrollbar.enable();
		this.categoryFilterScrollbarEnabled = true;
		int i = -1;
		while (++i < this.categoryList.size())
			this.categoryList.get(i).setWidth(CATEGORY_FILTER_BUTTON_WIDTH_SCROLLBAR);
	}
	
	private void disabledCategoryFilterScrollbar()
	{
		if (!this.categoryFilterScrollbarEnabled)
			return;
		this.filterScrollbar.disable();
		this.categoryFilterScrollbarEnabled = false;
		this.filterScrollbarYOffset = 0;
		this.filterScrollbar.resetScroll();
		int i = -1;
		while (++i < this.categoryList.size())
			this.categoryList.get(i).setWidth(CATEGORY_FILTER_BUTTON_WIDTH_NO_SCROLLBAR);
	}
	
	private void onCategoryFilterScrollbarScroll()
	{
		this.filterScrollbarYOffset = (short)((this.categoryFilterTotalLine - CATEGORY_FILTER_MAX_LINE_DISPLAYED) * this.filterScrollbar.getScrollPercentage());
		updateCategoryButton();
	}
	
	private void updateCategoryButton()
	{
		int i = -1;
		short currentLine = (short)-this.filterScrollbarYOffset;
		short totalLine = 0;
		while (++i < this.categoryList.size())
		{
			this.categoryList.get(i).setY(CATEGORY_FILTER_BUTTON_Y + currentLine * (CATEGORY_FILTER_BUTTON_Y_SHIFT));
			currentLine += this.categoryList.get(i).getNumberLine();
			totalLine += this.categoryList.get(i).getNumberLine();
		}
		this.categoryFilterTotalLine = totalLine;
	}
	
	public void setSelectedFilter(AuctionHouseFilter filter)
	{
		if (this.categoryFilter == filter)
			return;
		this.categoryFilter = filter;
		checkCategoryFilterScrollbar();
		onCategoryFilterScrollbarScroll();
	}
	
	public void unexpandAllCategoryFilter()
	{
		int i = -1;
		while (++i < this.categoryList.size())
			this.categoryList.get(i).unexpand();
	}
	
	public AuctionHouseFilter getSelectedFilter()
	{
		return (this.categoryFilter);
	}
	
	@Override
	public void shouldUpdateSize()
	{
		this.shouldUpdateSize = true;
	}
	
	private void buildBrowseFilterMenu() {
		this.categoryList.add(new AuctionHouseBrowseCategoryFilterButton(this, AuctionHouseFilter.WEAPON, 1, CATEGORY_FILTER_BUTTON_X, CATEGORY_FILTER_BUTTON_Y, CATEGORY_FILTER_BUTTON_WIDTH_NO_SCROLLBAR));
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

		this.categoryList.add(new AuctionHouseBrowseCategoryFilterButton(this, AuctionHouseFilter.ARMOR, 2, CATEGORY_FILTER_BUTTON_X, CATEGORY_FILTER_BUTTON_Y + CATEGORY_FILTER_BUTTON_Y_SHIFT, CATEGORY_FILTER_BUTTON_WIDTH_NO_SCROLLBAR));
		this.categoryList.get(1).addFilter(AuctionHouseFilter.ARMOR_MISCELLANEOUS);
		this.categoryList.get(1).addFilter(AuctionHouseFilter.ARMOR_CLOTH);
		this.categoryList.get(1).addFilter(AuctionHouseFilter.ARMOR_LEATHER);
		this.categoryList.get(1).addFilter(AuctionHouseFilter.ARMOR_MAIL);
		this.categoryList.get(1).addFilter(AuctionHouseFilter.ARMOR_PLATE);
		this.categoryList.get(1).addFilter(AuctionHouseFilter.ARMOR_SHIELDS);
		this.categoryList.get(1).addFilter(AuctionHouseFilter.ARMOR_LIBRAMS);
		this.categoryList.get(1).addFilter(AuctionHouseFilter.ARMOR_IDOLS);
		this.categoryList.get(1).addFilter(AuctionHouseFilter.ARMOR_TOTEMS);

		this.categoryList.add(new AuctionHouseBrowseCategoryFilterButton(this, AuctionHouseFilter.CONTAINER, 3, CATEGORY_FILTER_BUTTON_X, CATEGORY_FILTER_BUTTON_Y + 2 * CATEGORY_FILTER_BUTTON_Y_SHIFT, CATEGORY_FILTER_BUTTON_WIDTH_NO_SCROLLBAR));
		this.categoryList.get(2).addFilter(AuctionHouseFilter.CONTAINER_BAG);
		this.categoryList.get(2).addFilter(AuctionHouseFilter.CONTAINER_SOUL_BAG);
		this.categoryList.get(2).addFilter(AuctionHouseFilter.CONTAINER_HERB_BAG);
		this.categoryList.get(2).addFilter(AuctionHouseFilter.CONTAINER_ENCHANTING_BAG);
		this.categoryList.get(2).addFilter(AuctionHouseFilter.CONTAINER_ENGINEERING_BAG);
		this.categoryList.get(2).addFilter(AuctionHouseFilter.CONTAINER_GEM_BAG);
		this.categoryList.get(2).addFilter(AuctionHouseFilter.CONTAINER_MINING_BAG);
		this.categoryList.get(2).addFilter(AuctionHouseFilter.CONTAINER_LEATHERWORKING_BAG);

		this.categoryList.add(new AuctionHouseBrowseCategoryFilterButton(this, AuctionHouseFilter.CONSUMABLES, 4, CATEGORY_FILTER_BUTTON_X, CATEGORY_FILTER_BUTTON_Y + 3 * CATEGORY_FILTER_BUTTON_Y_SHIFT, CATEGORY_FILTER_BUTTON_WIDTH_NO_SCROLLBAR));
		this.categoryList.get(3).addFilter(AuctionHouseFilter.CONSUMABLES_FOOD_AND_DRINK);
		this.categoryList.get(3).addFilter(AuctionHouseFilter.CONSUMABLES_POTION);
		this.categoryList.get(3).addFilter(AuctionHouseFilter.CONSUMABLES_ELIXIR);
		this.categoryList.get(3).addFilter(AuctionHouseFilter.CONSUMABLES_FLASK);
		this.categoryList.get(3).addFilter(AuctionHouseFilter.CONSUMABLES_BANDAGE);
		this.categoryList.get(3).addFilter(AuctionHouseFilter.CONSUMABLES_ITEMS_ENCHANCEMENTS);
		this.categoryList.get(3).addFilter(AuctionHouseFilter.CONSUMABLES_PARCHEMIN);
		this.categoryList.get(3).addFilter(AuctionHouseFilter.CONSUMABLES_MISCELLANEOUS);

		this.categoryList.add(new AuctionHouseBrowseCategoryFilterButton(this, AuctionHouseFilter.TRADE_GOOD, 5, CATEGORY_FILTER_BUTTON_X, CATEGORY_FILTER_BUTTON_Y + 4 * CATEGORY_FILTER_BUTTON_Y_SHIFT, CATEGORY_FILTER_BUTTON_WIDTH_NO_SCROLLBAR));
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

		this.categoryList.add(new AuctionHouseBrowseCategoryFilterButton(this, AuctionHouseFilter.PROJECTILE, 6, CATEGORY_FILTER_BUTTON_X, CATEGORY_FILTER_BUTTON_Y + 5 * CATEGORY_FILTER_BUTTON_Y_SHIFT, CATEGORY_FILTER_BUTTON_WIDTH_NO_SCROLLBAR));
		this.categoryList.get(5).addFilter(AuctionHouseFilter.PROJECTILE_ARROW);
		this.categoryList.get(5).addFilter(AuctionHouseFilter.PROJECTILE_BULLET);
		
		this.categoryList.add(new AuctionHouseBrowseCategoryFilterButton(this, AuctionHouseFilter.QUIVER, 7, CATEGORY_FILTER_BUTTON_X, CATEGORY_FILTER_BUTTON_Y + 6 * CATEGORY_FILTER_BUTTON_Y_SHIFT, CATEGORY_FILTER_BUTTON_WIDTH_NO_SCROLLBAR));
		this.categoryList.get(6).addFilter(AuctionHouseFilter.QUIVER_QUIVER);
		this.categoryList.get(6).addFilter(AuctionHouseFilter.QUIVER_GIBERNE);

		this.categoryList.add(new AuctionHouseBrowseCategoryFilterButton(this, AuctionHouseFilter.RECIPES, 8, CATEGORY_FILTER_BUTTON_X, CATEGORY_FILTER_BUTTON_Y + 7 * CATEGORY_FILTER_BUTTON_Y_SHIFT, CATEGORY_FILTER_BUTTON_WIDTH_NO_SCROLLBAR));
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

		this.categoryList.add(new AuctionHouseBrowseCategoryFilterButton(this, AuctionHouseFilter.GEM, 9, CATEGORY_FILTER_BUTTON_X, CATEGORY_FILTER_BUTTON_Y + 8 * CATEGORY_FILTER_BUTTON_Y_SHIFT, CATEGORY_FILTER_BUTTON_WIDTH_NO_SCROLLBAR));
		this.categoryList.get(8).addFilter(AuctionHouseFilter.GEM_RED);
		this.categoryList.get(8).addFilter(AuctionHouseFilter.GEM_BLUE);
		this.categoryList.get(8).addFilter(AuctionHouseFilter.GEM_YELLOW);
		this.categoryList.get(8).addFilter(AuctionHouseFilter.GEM_PURPLE);
		this.categoryList.get(8).addFilter(AuctionHouseFilter.GEM_GREEN);
		this.categoryList.get(8).addFilter(AuctionHouseFilter.GEM_ORANGE);
		this.categoryList.get(8).addFilter(AuctionHouseFilter.GEM_META);
		this.categoryList.get(8).addFilter(AuctionHouseFilter.GEM_SIMPLE);
		this.categoryList.get(8).addFilter(AuctionHouseFilter.GEM_PRISMATIC);

		this.categoryList.add(new AuctionHouseBrowseCategoryFilterButton(this, AuctionHouseFilter.MISCELLANEOUS, 10, CATEGORY_FILTER_BUTTON_X, CATEGORY_FILTER_BUTTON_Y + 9 * CATEGORY_FILTER_BUTTON_Y_SHIFT, CATEGORY_FILTER_BUTTON_WIDTH_NO_SCROLLBAR));
		this.categoryList.get(9).addFilter(AuctionHouseFilter.MISCELLANEOUS_JUNK);
		this.categoryList.get(9).addFilter(AuctionHouseFilter.MISCELLANEOUS_REAGENT);
		this.categoryList.get(9).addFilter(AuctionHouseFilter.MISCELLANEOUS_COMPANION_PETS);
		this.categoryList.get(9).addFilter(AuctionHouseFilter.MISCELLANEOUS_HOLIDAY);
		this.categoryList.get(9).addFilter(AuctionHouseFilter.MISCELLANEOUS_OTHER);
		this.categoryList.get(9).addFilter(AuctionHouseFilter.MISCELLANEOUS_MOUNT);

		this.categoryList.add(new AuctionHouseBrowseCategoryFilterButton(this, AuctionHouseFilter.QUESTS, 11, CATEGORY_FILTER_BUTTON_X, CATEGORY_FILTER_BUTTON_Y + 10 * CATEGORY_FILTER_BUTTON_Y_SHIFT, CATEGORY_FILTER_BUTTON_WIDTH_NO_SCROLLBAR));
	}
}
