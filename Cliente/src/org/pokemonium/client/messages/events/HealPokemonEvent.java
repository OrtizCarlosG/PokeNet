package org.pokemonium.client.messages.events;

import org.pokemonium.client.GameClient;
import org.pokemonium.client.Session;
import org.pokemonium.client.messages.MessageEvent;
import org.pokemonium.client.protocol.ClientMessage;
import org.pokemonium.client.protocol.ServerMessage;

public class HealPokemonEvent implements MessageEvent
{

	@Override
	public void parse(Session Session, ServerMessage Request, ClientMessage Message)
	{
		for(int i = 0; i < GameClient.getInstance().getOurPlayer().getPokemon().length; i++)
			if(GameClient.getInstance().getOurPlayer().getPokemon()[i] != null)
			{
				GameClient.getInstance().getOurPlayer().getPokemon()[i].setCurHP(GameClient.getInstance().getOurPlayer().getPokemon()[i].getMaxHP());
				for(int x = 0; x < 4; x++)
					GameClient.getInstance().getOurPlayer().getPokemon()[i].setMoveCurPP(x, GameClient.getInstance().getOurPlayer().getPokemon()[i].getMoveMaxPP()[x]);
			}
		GameClient.getInstance().getHUD().update(false);
	}
}
