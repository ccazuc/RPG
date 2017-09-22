package com.mideas.rpg.v2.callback;

import com.mideas.rpg.v2.Mideas;

public class PlayerHealthChangedCallback implements Callback {

	@Override
	public void handleCallback(Object ...obj) {
		if(Mideas.joueur1() != null && Mideas.joueur1().getHasHpChanged()) {
			Mideas.joueur1().setHealthText(Mideas.joueur1().getStamina()+" / "+Mideas.joueur1().getMaxStamina());
			Mideas.joueur1().setHasHpChanged(false);
		}
	}
}
