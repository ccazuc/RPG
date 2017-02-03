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
import com.mideas.rpg.v2.utils.Button;
import com.mideas.rpg.v2.utils.Draw;
import com.mideas.rpg.v2.utils.ScrollBar;

public class AuctionFrameUI {


	private final short BROWSE_FILTER_SCROLLBAR_X = 153;
	private final short BROWSE_FILTER_SCROLLBAR_WIDTH = 170;
	private final short BROWSE_FILTER_SCROLLBAR_HEIGHT = 290;
	private final short BROWSE_FILTER_SCROLLBAR_Y = 100;
	private final short BROWSE_FILTER_X = 21;
	private final short BROWSE_FILTER_Y = 103;
	private final short BROWSE_FILTER_Y_SHIFT = 20;
	private final short BROWSE_FILTER_WIDTH_NO_SCROLLBAR = 156;
	private final short BROWSE_FILTER_WIDTH_SCROLLBAR = 136;
	private final short BROWSE_FILTER_HEIGHT = 20;
	private final short X_FRAME = 18;
	private final short Y_FRAME = 118;
	
	private final short BROWSE_CLOSE_BUTTON_X = 744;
	private final short BROWSE_CLOSE_BUTTON_Y = 407;
	private final short BROWSE_BUYOUT_BUTTON_X = 663;
	private final short BROWSE_BID_BUTTON_X = 584;
	private final short BROWSE_BUTTON_WIDTH = 75;
	private final short BROWSE_BUTTON_HEIGHT = 25;
	
	private short x_frame = (short)(this.X_FRAME*Mideas.getDisplayXFactor());
	private short y_frame = (short)(this.Y_FRAME*Mideas.getDisplayYFactor());
	private AuctionFrameTab selectedTab = AuctionFrameTab.BROWSE;
	private final ArrayList<AuctionCategoryFilterButton> browseCategoryList;
	private final ArrayList<AuctionBrowseQueryButton> queryButtonList;
	AuctionEntry browseSelectedEntry;
	private AuctionHouseFilter selectedFilter;
	private AuctionHouseFilter selectedCategoryFilter;
	protected final TTF browseFilterFont;
	private short browseFilterX;
	private short browseFilterY;
	private short browseFilterYSave;
	private short browseFilterYShift;
	private short browseFilterWidth;
	private short browseFilterHeight;
	short browserFilterScrollbarOffset;
	private short browseItemY;
	private short browseItemX;
	private short browseItemWidth;
	private short browseItemHeight;
	private short browseItemYSave;
	private short browseItemYShift;
	byte browserFilterNumberLine;
	private boolean shouldUpdate;
	private boolean browseFilterScrollbarEnabled;
	private boolean hasSearched;
	final ScrollBar browseFilterScrollbar = new ScrollBar(this.x_frame+this.BROWSE_FILTER_SCROLLBAR_X*Mideas.getDisplayXFactor(), this.y_frame+this.BROWSE_FILTER_SCROLLBAR_Y*Mideas.getDisplayYFactor(), this.BROWSE_FILTER_SCROLLBAR_HEIGHT*Mideas.getDisplayYFactor(), this.BROWSE_FILTER_SCROLLBAR_WIDTH*Mideas.getDisplayXFactor(), this.BROWSE_FILTER_SCROLLBAR_HEIGHT*Mideas.getDisplayYFactor(), false, this.BROWSE_FILTER_HEIGHT*Mideas.getDisplayYFactor()) {
		
		@Override
		public void onScroll() {
			System.out.println("Before : "+AuctionFrameUI.this.browserFilterScrollbarOffset+" "+AuctionFrameUI.this.browseFilterScrollbar.getScrollPercentage()+" "+AuctionFrameUI.this.browserFilterNumberLine);
			AuctionFrameUI.this.browserFilterScrollbarOffset = (short)(AuctionFrameUI.this.BROWSE_FILTER_HEIGHT*Mideas.getDisplayYFactor()*(AuctionFrameUI.this.browserFilterNumberLine-15)*AuctionFrameUI.this.browseFilterScrollbar.getScrollPercentage());
			System.out.println("After : "+AuctionFrameUI.this.browserFilterScrollbarOffset+" "+AuctionFrameUI.this.browseFilterScrollbar.getScrollPercentage()+" "+AuctionFrameUI.this.browserFilterNumberLine);
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
	
	public AuctionFrameUI() {
		this.queryButtonList = new ArrayList<AuctionBrowseQueryButton>();
		this.browseCategoryList = new ArrayList<AuctionCategoryFilterButton>();
		this.shouldUpdate = true;
		updateSize();
		this.browseFilterFont = FontManager.get("FRIZQT", 11);
		buildBrowseFilterMenu();
	}
	
	public void sendSearchQuery() {
		//CommandAuction.sendSearchQuery()
		this.hasSearched = true;
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
		this.browseFilterY = (short)(this.browseFilterYSave-this.browserFilterScrollbarOffset);
		this.browseBidButton.draw();
		this.browseBuyoutButton.draw();
		this.browseClose.draw();
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
	}
	
	public void updateQueryButtonList() {
		ArrayList<AuctionEntry> list = Mideas.joueur1().getAuctionHouse().getQueryList();
		int i = -1;
		while(++i < 50) {
			if(i < list.size()) {
				this.queryButtonList.get(i).setEntry(list.get(i));
			}
			else {
				this.queryButtonList.get(i).setEntry(null);
			}
		}
	}
	
	private void drawBrowseItem() {
		ArrayList<AuctionEntry> list = Mideas.joueur1().getAuctionHouse().getQueryList();
		if(list.size() == 0) {
			if(this.hasSearched) {
				
			}
			else {
				
			}
			return;
		}
		this.browseItemY = this.browseItemYSave;
		int i = -1;
		AuctionEntry entry;
		while(++i < this.queryButtonList.size() && (entry = this.queryButtonList.get(i).getEntry()) != null) {
			this.queryButtonList.get(i).draw();
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
		return false;
	}
	
	private boolean browseFrameMouseEvent() {
		if(this.browseBidButton.event()) return true;
		if(this.browseBuyoutButton.event()) return true;
		if(this.browseClose.event()) return true;
		if(this.browseFilterScrollbarEnabled) {
			if(this.browseFilterScrollbar.event()) return true;
		}
		int i = -1;
		this.browseFilterY = (short)(this.browseFilterYSave-this.browserFilterScrollbarOffset);
		while(++i < this.browseCategoryList.size()) {
			if(this.browseCategoryList.get(i).event()) {
				return true;
			}
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
		this.browseBidButton.update(this.x_frame+this.BROWSE_BID_BUTTON_X*Mideas.getDisplayXFactor(), this.y_frame+this.BROWSE_CLOSE_BUTTON_Y*Mideas.getDisplayYFactor(), this.BROWSE_BUTTON_WIDTH*Mideas.getDisplayXFactor(), this.BROWSE_BUTTON_HEIGHT*Mideas.getDisplayYFactor());
		this.browseBuyoutButton.update(this.x_frame+this.BROWSE_BUYOUT_BUTTON_X*Mideas.getDisplayXFactor(), this.y_frame+this.BROWSE_CLOSE_BUTTON_Y*Mideas.getDisplayYFactor(), this.BROWSE_BUTTON_WIDTH*Mideas.getDisplayXFactor(), this.BROWSE_BUTTON_HEIGHT*Mideas.getDisplayYFactor());
		this.browseClose.update(this.x_frame+this.BROWSE_CLOSE_BUTTON_X*Mideas.getDisplayXFactor(), this.y_frame+this.BROWSE_CLOSE_BUTTON_Y*Mideas.getDisplayYFactor(), this.BROWSE_BUTTON_WIDTH*Mideas.getDisplayXFactor(), this.BROWSE_BUTTON_HEIGHT*Mideas.getDisplayYFactor());
		this.browseFilterScrollbar.update(this.x_frame+this.BROWSE_FILTER_SCROLLBAR_X*Mideas.getDisplayXFactor(), this.y_frame+this.BROWSE_FILTER_SCROLLBAR_Y*Mideas.getDisplayYFactor(), this.BROWSE_FILTER_SCROLLBAR_HEIGHT*Mideas.getDisplayYFactor(), this.BROWSE_FILTER_SCROLLBAR_WIDTH*Mideas.getDisplayXFactor(), this.BROWSE_FILTER_SCROLLBAR_HEIGHT*Mideas.getDisplayYFactor(), this.BROWSE_FILTER_HEIGHT*Mideas.getDisplayYFactor());
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
