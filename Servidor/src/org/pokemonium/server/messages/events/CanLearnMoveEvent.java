package org.pokemonium.server.messages.events;

import org.pokemonium.server.backend.entity.Player;
import org.pokemonium.server.client.Session;
import org.pokemonium.server.messages.MessageEvent;
import org.pokemonium.server.protocol.ClientMessage;
import org.pokemonium.server.protocol.ServerMessage;

public class CanLearnMoveEvent implements MessageEvent
{

	public void Parse(Session session, ClientMessage request, ServerMessage message)
	{
		// Player is allowing move to be learned
		Player p = session.getPlayer();
		int pokemonIndex = request.readInt();
		int moveIndex = request.readInt();
		String move = request.readString();

		if(move != null && !move.equalsIgnoreCase("") && p.getParty()[pokemonIndex] != null)
		{
			boolean hasMove = false;
			for(int i = 0; i < 4; i++)
			{
				if(p.getParty()[pokemonIndex].getMoveName(i) == null)
					break;
				if(p.getParty()[pokemonIndex].getMoveName(i).equalsIgnoreCase(move))
				{
					hasMove = true;
					break;
				}
			}
			if(p.getParty()[pokemonIndex].getMovesLearning().contains(move) && !hasMove)
			{
				p.getParty()[pokemonIndex].learnMove(moveIndex, move);
				p.updateClientPP(pokemonIndex, moveIndex);
			}
		}
	}

}
