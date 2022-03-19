package org.pokemonium.server.messages.events;

import org.pokemonium.server.client.Session;
import org.pokemonium.server.messages.MessageEvent;
import org.pokemonium.server.protocol.ClientMessage;
import org.pokemonium.server.protocol.ServerMessage;

public class PlayerLastPosition implements MessageEvent
{

	public void Parse(Session session, ClientMessage request, ServerMessage message)
	{
		String[] details = request.readString().split(",");
		session.getPlayer().setLastX(Integer.parseInt(details[0]));
		session.getPlayer().setLastY(Integer.parseInt(details[1]));
	}

}
