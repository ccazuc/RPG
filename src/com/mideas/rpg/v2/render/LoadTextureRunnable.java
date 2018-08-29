package com.mideas.rpg.v2.render;

import com.mideas.rpg.v2.files.logs.LogsMgr;

public class LoadTextureRunnable implements Runnable
{

	@Override
	public void run()
	{
		Texture.asyncTextureLoadFinished = false;
		int i = -1;
		System.out.println("Texture data load started");
		LogsMgr.writeMiscLog("Started loading texture async.");
		while (++i < Texture.getAsynTextureList().size())
			Texture.getAsynTextureList().get(i).loadTextureDatasAsync();
		Texture.asyncTextureLoadFinished = true;
		System.out.println("Texture data load ended");
		LogsMgr.writeMiscLog("Finished loading texture async.");
	}
}
