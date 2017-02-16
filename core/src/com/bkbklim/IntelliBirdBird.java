package com.bkbklim;

import com.badlogic.gdx.Game;
import com.bkbklim.Helpers.AssetLoader;
import com.bkbklim.Helpers.ThirdPartyController;
import com.bkbklim.Screens.SplashScreen;

public class IntelliBirdBird extends Game {

	private ThirdPartyController controller;

	public IntelliBirdBird(ThirdPartyController controller) {
		this.controller = controller;
	}

	@Override
	public void create() {
		AssetLoader.load();
		setScreen(new SplashScreen(this, controller));


	}

	@Override
	public void dispose() {
		super.dispose();
		AssetLoader.dispose();
	}
}
