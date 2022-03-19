package org.pokemonium.client.backend;

import de.matthiasmann.twl.renderer.Image;

public class BattleImageDatabase {

	private static Image[] _battleButtons = new Image[2];
	
	private static String respath;
	
	
	public static void Init()
	{
		respath = System.getProperty("res.path");
		if(respath == null)
			respath = "";
	}
	
	public static void loadBattleHUD()
	{
		_battleButtons[0] = FileLoader.loadImage(respath + "res/themes/default/moveButtonBack.png");
		_battleButtons[1] = FileLoader.loadImage(respath + "res/themes/default/moveButtonBackSel.png");
	}
	
	public static Image[] getBattleButtons()
	{
		return _battleButtons;
	}
}
