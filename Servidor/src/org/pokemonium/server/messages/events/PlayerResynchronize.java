package org.pokemonium.server.messages.events;

import org.pokemonium.server.backend.entity.Player;
import org.pokemonium.server.client.Session;
import org.pokemonium.server.messages.MessageEvent;
import org.pokemonium.server.protocol.ClientMessage;
import org.pokemonium.server.protocol.ServerMessage;

public class PlayerResynchronize implements MessageEvent {

	public void Parse(Session session, ClientMessage request, ServerMessage message)
	{
		// Get Player
		Player p = session.getPlayer();
		
		// Resync
		p.sendToBeResynchronized();
	}
}