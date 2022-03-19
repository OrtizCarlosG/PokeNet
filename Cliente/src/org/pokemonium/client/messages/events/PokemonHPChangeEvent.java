package org.pokemonium.client.messages.events;

import org.pokemonium.client.GameClient;
import org.pokemonium.client.Session;
import org.pokemonium.client.messages.MessageEvent;
import org.pokemonium.client.protocol.ClientMessage;
import org.pokemonium.client.protocol.ServerMessage;

public class PokemonHPChangeEvent implements MessageEvent
{

	@Override
	public void parse(Session Session, ServerMessage Request, ClientMessage Message)
	{
		GameClient.getInstance().getOurPlayer().getPokemon()[Request.readInt()].setCurHP(Request.readInt());
		GameClient.getInstance().getHUD().update(false);
	}
}
