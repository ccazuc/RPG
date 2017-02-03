package com.mideas.rpg.v2.hud.auction;

import java.util.ArrayList;

import com.mideas.rpg.v2.FontManager;
import com.mideas.rpg.v2.Interface;
import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.TTF;
import com.mideas.rpg.v2.command.item.CommandAuction;
import com.mideas.rpg.v2.game.auction.AuctionEntry;
import com.mideas.rpg.v2.game.auction.AuctionHouseFilter;
import com.mideas.rpg.v2.utils.Button;

public class AuctionFrameUI {

	private int x_frame;
	private int y_frame;
	private AuctionFrameTab selectedTab = AuctionFrameTab.BROWSE;
	private final ArrayList<AuctionCategoryFilterButton> browseCategoryList;
	int browseSelectedItem = -1;
	private int browseHoveredItem = -1;
	private AuctionHouseFilter selectedFilter;
	private AuctionHouseFilter selectedCategoryFilter;
	protected final TTF browseCategoryFont;
	private int browseCategoryX;
	private int browseCategoryY;
	private int browseCategoryYShift;
	private int browseCategoryWidth;
	private int browseCategoryHeight;
	private boolean shouldUpdate;
	private boolean browseFilterScrollbarEnabled;
	
	private final int BROWSER_FILTER_WIDTH_NO_SCROLLBAR = 110;
	private final int BROWSER_FILTER_WIDTH_SCROLLBAR = 90;
	
	private final Button browseBidButton = new Button(this.x_frame, this.y_frame, 80*Mideas.getDisplayXFactor(), 22*Mideas.getDisplayYFactor(), "Bid", 12, 1) {
	
		@Override
		public void eventButtonClick() {
			CommandAuction.makeABid(Mideas.joueur1().getAuctionHouse().getQueryEntry(AuctionFrameUI.this.browseSelectedItem), 1); //TODO: use input values
		}
		
		@Override
		public boolean activateCondition() {
			AuctionEntry entry = Mideas.joueur1().getAuctionHouse().getQueryEntry(AuctionFrameUI.this.browseSelectedItem);
			return entry != null && entry.canBeBuy();
		}
	};
	private final Button browseBuyoutButton = new Button(this.x_frame, this.y_frame, 80*Mideas.getDisplayXFactor(), 22*Mideas.getDisplayYFactor(), "Buyout", 12, 1) {
	
		@Override
		public void eventButtonClick() {
			CommandAuction.buyout(Mideas.joueur1().getAuctionHouse().getQueryEntry(AuctionFrameUI.this.browseSelectedItem));
		}
		
		@Override
		public boolean activateCondition() {
			return AuctionFrameUI.this.browseSelectedItem != -1;
		}
	};
	private final Button browseClose = new Button(this.x_frame, this.y_frame, 80*Mideas.getDisplayXFactor(), 22*Mideas.getDisplayYFactor(), "Close", 12, 1) {
	
		@Override
		public void eventButtonClick() {
			Interface.closeAuctionFrame();
		}
	};
	
	public AuctionFrameUI() {
		this.browseCategoryList = new ArrayList<AuctionCategoryFilterButton>();
		this.browseCategoryFont = FontManager.get("FRIZQT", 12);
		buildBrowseFilterMenu();
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
		this.browseBidButton.draw();
		this.browseBuyoutButton.draw();
		this.browseClose.draw();
		int i = -1;
		while(++i < this.browseCategoryList.size()) {
			this.browseCategoryList.get(i).draw();
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
			this.browseCategoryWidth = (int)(this.BROWSER_FILTER_WIDTH_SCROLLBAR*Mideas.getDisplayXFactor());
		}
		else {
			this.browseCategoryWidth = (int)(this.BROWSER_FILTER_WIDTH_NO_SCROLLBAR*Mideas.getDisplayXFactor());
		}
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
		this.browseCategoryWidth = (int)(this.BROWSER_FILTER_WIDTH_NO_SCROLLBAR*Mideas.getDisplayXFactor());
	}
	
	private void showBrowseFilterScrollbar() {
		this.browseCategoryWidth = (int)(this.BROWSER_FILTER_WIDTH_SCROLLBAR*Mideas.getDisplayXFactor());
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
	
	protected void incrementBrowseCateogryY() {
		this.browseCategoryY+= this.browseCategoryYShift;
	}
	
	protected int getBrowseCategoryY() {
		return this.browseCategoryY;
	}
	
	protected int getBrowseCategoryX() {
		return this.browseCategoryX;
	}
	
	protected int getBrowseCategoryYShift() {
		return this.browseCategoryYShift;
	}
	
	protected int getBrowseCategoryWidth() {
		return this.browseCategoryWidth;
	}
	
	protected int getBrowseCategoryHeight() {
		return this.browseCategoryHeight;
	}
}