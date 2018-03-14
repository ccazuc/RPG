package com.mideas.rpg.v2.hud.auction;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.render.Sprites;
import com.mideas.rpg.v2.utils.Frame;

public class AuctionHouseFrame extends Frame {

	private final Frame browseFrame;
	private Frame activeFrame;
	private short width;
	private short height;
	private boolean shouldUpdateSize;
	private final static short X_FRAME = 19;
	private final static short Y_FRAME = 118;
	private final static short FRAME_WIDTH = (short)879;
	private final static short FRAME_HEIGHT = (short)434;
	
	public AuctionHouseFrame()
	{
		super("AuctionHouseFrame");
		this.x = (short)(X_FRAME * Mideas.getDisplayXFactor());
		this.y = (short)(Y_FRAME * Mideas.getDisplayYFactor());
		this.width = (short)(FRAME_WIDTH * Mideas.getDisplayXFactor());
		this.height = (short)(FRAME_HEIGHT * Mideas.getDisplayYFactor());
		this.browseFrame = new AuctionHouseBrowseFrame(this);
		this.activeFrame = this.browseFrame;
	}
	
	@Override
	public void draw()
	{
		updateSize();
		this.activeFrame.draw();
	}
	
	@Override
	public boolean mouseEvent()
	{
		if (this.activeFrame.mouseEvent())
			return (true);
		return (false);
	}
	
	@Override
	public boolean keyboardEvent()
	{
		if (this.activeFrame.keyboardEvent())
			return (true);
		return (false);
	}
	
	public void openBrowseFrame()
	{
		if (this.activeFrame == this.browseFrame)
			return;
		this.activeFrame.close();
		this.activeFrame = this.browseFrame;
		this.activeFrame.open();
	}
	
	@Override
	public void open()
	{
		
	}
	
	@Override
	public void close()
	{
		
	}
	
	@Override
	public boolean isOpen()
	{
		return (false);
	}
	
	@Override
	public void reset()
	{
		this.browseFrame.reset();
	}
	
	public void updateSize()
	{
		if (!this.shouldUpdateSize)
			return;
		this.x = (short)(X_FRAME * Mideas.getDisplayXFactor());
		this.y = (short)(Y_FRAME * Mideas.getDisplayYFactor());
		this.width = (short)(FRAME_WIDTH * Mideas.getDisplayXFactor());
		this.height = (short)(FRAME_HEIGHT * Mideas.getDisplayYFactor());
		this.browseFrame.shouldUpdateSize();
		this.shouldUpdateSize = false;
	}
	
	public short getWidth()
	{
		return (this.width);
	}
	
	public short getHeight()
	{
		return (this.height);
	}
	
	@Override
	public void shouldUpdateSize()
	{
		this.shouldUpdateSize = true;
	}
}
