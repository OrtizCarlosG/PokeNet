package org.pokemonium.client.messages.events;

import org.pokemonium.client.GameClient;
import org.pokemonium.client.Session;
import org.pokemonium.client.backend.entity.HMObject;
import org.pokemonium.client.backend.entity.Player;
import org.pokemonium.client.backend.entity.Player.Direction;
import org.pokemonium.client.messages.MessageEvent;
import org.pokemonium.client.protocol.ClientMessage;
import org.pokemonium.client.protocol.ServerMessage;

public class MapAddPlayerEvent implements MessageEvent
{
	@Override
	public void parse(Session Session, ServerMessage Request, ClientMessage Message)
	{
		// Add player
		String[] details = Request.readString().split(",");
		Player p = new Player();
		try
		{
			HMObject hm = new HMObject(HMObject.parseHMObject(details[0]));
			hm.setId(Integer.parseInt(details[1]));
			hm.setSprite(Integer.parseInt(details[2]));
			hm.setX(Integer.parseInt(details[3]));
			hm.setServerX(Integer.parseInt(details[3]));
			hm.setPokemonX(Integer.parseInt(details[3]));
			hm.setLastPlayerX(Integer.parseInt(details[3]));
			hm.setY(Integer.parseInt(details[4]));
			hm.setServerY(Integer.parseInt(details[4]));
			hm.setPokemonY(Integer.parseInt(details[4]) + 32);
			hm.setLastPlayerY(Integer.parseInt(details[4]) + 32);
			hm.setDirection(Direction.Down);
			hm.setAdminLevel(Integer.parseInt(details[6]));
			hm.setPokemonsSprite(Integer.parseInt(details[7]));
			hm.loadSpriteImage();
			hm.loadPokemonSpriteImage();
			p = hm;
			p.setId(hm.getId());
		}
		catch(Exception e)
		{
			p.setUsername(details[0]);
			p.setId(Integer.parseInt(details[1]));
			p.setSprite(Integer.parseInt(details[2]));
			p.setPokemonX(Integer.parseInt(details[3]));
			p.setLastPlayerX(Integer.parseInt(details[3]));
			p.setPokemonY(Integer.parseInt(details[4]));
			p.setLastPlayerY(Integer.parseInt(details[4]));
			p.setX(Integer.parseInt(details[3]));
			p.setY(Integer.parseInt(details[4]));
			p.setServerX(Integer.parseInt(details[3]));
			p.setServerY(Integer.parseInt(details[4]));
			switch(details[5].charAt(0))
			{
				case 'D':
				{
					p.setLastPlayerY(Integer.parseInt(details[4]) - 32);
					p.setPokemonY(Integer.parseInt(details[4]) - 32);
					p.setDirection(Direction.Down);
					break;
				}
				case 'L':
				{
					p.setLastPlayerX(Integer.parseInt(details[3]) + 32);
					p.setPokemonX(Integer.parseInt(details[3]) + 32);
					p.setDirection(Direction.Left);
					break;
				}
				case 'R':
				{
					p.setLastPlayerX(Integer.parseInt(details[3]) - 32);
					p.setPokemonX(Integer.parseInt(details[3]) - 32);
					p.setDirection(Direction.Right);
					break;
				}
				case 'U':
				{
					p.setLastPlayerY(Integer.parseInt(details[4]) + 32);
					p.setPokemonY(Integer.parseInt(details[4]) + 32);
					p.setDirection(Direction.Up);
					break;
				}
				default:
				{
					p.setDirection(Direction.Down);
					break;
				}
			}
			p.setAdminLevel(Integer.parseInt(details[6]));
			p.setPokemonsSprite(Integer.parseInt(details[7]));
		}

		GameClient.getInstance().getMapMatrix().addPlayer(p);
	}
}
