package org.pokemonium.client.messages.events;

import org.pokemonium.client.GameClient;
import org.pokemonium.client.Session;
import org.pokemonium.client.messages.MessageEvent;
import org.pokemonium.client.protocol.ClientMessage;
import org.pokemonium.client.protocol.ServerMessage;

public class ClanEvent implements MessageEvent {
	
	public void parse(Session session, ServerMessage request, ClientMessage message)
	{
		System.out.println("Clan dialog recived");
		GameClient.getInstance().getGUIPane().getHUD().showClanDialog();
	}

}
