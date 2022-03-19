package org.pokemonium.client.messages.events;

import org.pokemonium.client.GameClient;
import org.pokemonium.client.Session;
import org.pokemonium.client.backend.entity.Player;
import org.pokemonium.client.backend.entity.Player.Direction;
import org.pokemonium.client.messages.MessageEvent;
import org.pokemonium.client.protocol.ClientMessage;
import org.pokemonium.client.protocol.ServerMessage;

public class PlayerNewPokemonOutPokeball implements MessageEvent
{

	@Override
	public void parse(Session Session, ServerMessage Request, ClientMessage Message)
	{
		// read id and pokemon follow
		String[] details = Request.readString().split(",");
		
		// player id
		int iNetworkPlayerID = Integer.parseInt(details[0]);
		
		// player pokemon follow
		int iPlayerPokemonFollowID = Integer.parseInt(details[1]);
		
		// Aplly information
		for(Player p : GameClient.getInstance().getMapMatrix().getPlayers())
		{
			// Check if id is the same
			if ( p.getId() == iNetworkPlayerID )
			{
				// Load image
				p.loadPokemonSpriteImage();
				
				// Get direction
				Direction pDirection = p.getDirection();
				
				// Apply Direction
				p.setPokemonDirection(pDirection);
				
				// Apply sprite
				p.setPokemonsSprite(iPlayerPokemonFollowID);
				
				// Aplicar posicao
				p.setPokemonX(p.getLastPlayerX());
				p.setPokemonY(p.getLastPlayerY());
				
				// Apply "correct" position
				/*
				if ( pDirection == Direction.Down )
				{
					p.setLastPlayerY(p.getLastPlayerY() - 32);
					p.setPokemonY(p.getPokemonY() - 32);
				}
				else if ( pDirection == Direction.Left )
				{
					p.setLastPlayerX(p.getLastPlayerX()  + 32);
					p.setPokemonX(p.getPokemonX()  + 32);
				}
				else if ( pDirection == Direction.Right )
				{
					p.setLastPlayerX(p.getLastPlayerX()  - 32);
					p.setPokemonX(p.getPokemonX()  - 32);
				}
				else if ( pDirection == Direction.Up )
				{
					p.setLastPlayerY(p.getLastPlayerY() + 32);
					p.setPokemonY(p.getPokemonY() + 32);
				}
				*/
			}
		}
	}
}
