package org.pokemonium.server.messages.events;


import org.pokemonium.server.backend.entity.Player;
import org.pokemonium.server.client.Session;
import org.pokemonium.server.messages.MessageEvent;
import org.pokemonium.server.protocol.ClientMessage;
import org.pokemonium.server.protocol.ServerMessage;

public class CreatePokemonOutPokeball implements MessageEvent
{

	public void Parse(Session session, ClientMessage request, ServerMessage message)
	{
		// Get player
		Player player = session.getPlayer();
		
		// Get pokemonId
		int pokemonId = request.readInt();
		
		// Set pokemon id
		player.reciveFollowPokemonID(pokemonId);
		
		// Update all player pokemons follow
		player.setUpdatePokemonFollowOverMap();
	}
}
