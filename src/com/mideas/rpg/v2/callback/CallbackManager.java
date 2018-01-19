package com.mideas.rpg.v2.callback;

import com.mideas.rpg.v2.Interface;

public class CallbackManager {

	public static void onMailReceived()
	{
		Interface.getMailFrame().onMailReceived();
	}
}
