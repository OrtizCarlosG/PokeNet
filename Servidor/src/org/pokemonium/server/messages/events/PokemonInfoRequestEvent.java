package org.pokemonium.server.messages.events;

import org.pokemonium.server.backend.entity.Player;
import org.pokemonium.server.battle.PokemonSpecies;
import org.pokemonium.server.client.Session;
import org.pokemonium.server.constants.ClientPacket;
import org.pokemonium.server.messages.MessageEvent;
import org.pokemonium.server.protocol.ClientMessage;
import org.pokemonium.server.protocol.ServerMessage;

public class PokemonInfoRequestEvent implements MessageEvent
{

	public void Parse(Session session, ClientMessage request, ServerMessage message)
	{
		Player p = session.getPlayer();
		String r = request.readString();
		switch(r)
		{
			case "summaryInBox":
				// TODO: IMPLEMENT
				break;
			case "MoveRelearner":
				int idx = request.readInt();
				String moves = "";
				boolean b = false;
				for(int i = 0; i < p.getParty()[idx].getLevel(); i++)
				{
					String tmp = PokemonSpecies.getDefaultData().getPokemonByName(p.getParty()[idx].getName()).getLevelMoves().get(i) + ", ";
					for(int j = 0; j < p.getParty()[idx].getMoves().length; j++)
					{
						if(tmp.equalsIgnoreCase(p.getParty()[idx].getMove(j).getName()))
						{
							b = true;
						}
					}
					if(!tmp.equals("null, ") && !b)
					{
						moves += tmp;
					}
				}
				for(String s : PokemonSpecies.getDefaultData().getPokemonByName(p.getParty()[idx].getName()).getEggMoves())
				{
					moves += s + ", ";
				}
				moves += "/END";
				ServerMessage moveLearn = new ServerMessage(ClientPacket.SENDINFO);
				moveLearn.addString("MoveRelearner");
				moveLearn.addString(moves);
				p.getSession().Send(moveLearn);
				break;
			case "moveTutor":
				// TODO: implement
				break;
			default:
				break;
		}
	}
}
