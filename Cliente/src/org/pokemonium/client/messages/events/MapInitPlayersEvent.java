package org.pokemonium.client.messages.events;

import org.pokemonium.client.GameClient;
import org.pokemonium.client.Session;
import org.pokemonium.client.backend.entity.HMObject;
import org.pokemonium.client.backend.entity.OurPlayer;
import org.pokemonium.client.backend.entity.Player;
import org.pokemonium.client.backend.entity.Player.Direction;
import org.pokemonium.client.messages.MessageEvent;
import org.pokemonium.client.protocol.ClientMessage;
import org.pokemonium.client.protocol.ServerMessage;

public class MapInitPlayersEvent implements MessageEvent
{
	@Override
	public void parse(Session Session, ServerMessage Request, ClientMessage Message)
	{
		GameClient.getInstance().getMapMatrix().getPlayers().clear();
		GameClient.getInstance().getMapMatrix().getPlayers().trimToSize();
		String[] details = Request.readString().split(",");
		/* Parse all the information. This packet contains details for all players on this map */
		for(int i = 0; i < details.length - 1; i++)
		{
			Player p = new Player();
			try
			{
				HMObject hm = new HMObject(HMObject.parseHMObject(details[i]));
				i++;
				hm.setId(Integer.parseInt(details[i]));
				i++;
				hm.setSprite(Integer.parseInt(details[i]));
				i++;
				hm.setX(Integer.parseInt(details[i]));
				hm.setServerX(Integer.parseInt(details[i]));
				hm.setPokemonX(Integer.parseInt(details[i]));
				hm.setLastPlayerX(Integer.parseInt(details[i]));
				i++;
				hm.setY(Integer.parseInt(details[i]));
				hm.setServerY(Integer.parseInt(details[i]));
				hm.setPokemonY(Integer.parseInt(details[i]));
				hm.setLastPlayerY(Integer.parseInt(details[i]));
				i++;
				hm.setDirection(Direction.Down);
				i++;
				hm.setAdminLevel(Integer.parseInt(details[i]));
				i++;
				hm.setPokemonsSprite(Integer.parseInt(details[i]));
				i++;
				//hm.setPokemonX(Integer.parseInt(details[i]));
				//hm.setLastPlayerX(Integer.parseInt(details[i]));
				i++;
				//hm.setPokemonY(Integer.parseInt(details[i]));
				//hm.setLastPlayerY(Integer.parseInt(details[i]));
				hm.loadSpriteImage();
				hm.loadPokemonSpriteImage();
				p = hm;
			}
			catch(Exception e)
			{
				p.setUsername(details[i]);
				i++;
				p.setId(Integer.parseInt(details[i]));
				i++;
				p.setSprite(Integer.parseInt(details[i]));
				i++;
				p.setX(Integer.parseInt(details[i]));
				p.setServerX(Integer.parseInt(details[i]));
				p.setPokemonX(Integer.parseInt(details[i]));
				p.setLastPlayerX(Integer.parseInt(details[i]));
				i++;
				p.setY(Integer.parseInt(details[i]));
				p.setServerY(Integer.parseInt(details[i]));
				p.setPokemonY(Integer.parseInt(details[i]));
				p.setLastPlayerY(Integer.parseInt(details[i]));
				i++;
				switch(details[i].charAt(0))
				{
					case 'D':
					{
						p.setLastPlayerY(p.getLastPlayerY() - 32);
						p.setPokemonY(p.getPokemonY() - 32);
						p.setDirection(Direction.Down);
						break;
					}
					case 'L':
					{
						p.setLastPlayerX(p.getLastPlayerX()  + 32);
						p.setPokemonX(p.getPokemonX()  + 32);
						p.setDirection(Direction.Left);
						break;
					}
					case 'R':
					{
						p.setLastPlayerX(p.getLastPlayerX()  - 32);
						p.setPokemonX(p.getPokemonX()  - 32);
						p.setDirection(Direction.Right);
						break;
					}
					case 'U':
					{
						p.setLastPlayerY(p.getLastPlayerY() + 32);
						p.setPokemonY(p.getPokemonY() + 32);
						p.setDirection(Direction.Up);
						break;
					}
					default:
					{
						p.setDirection(Direction.Down);
						break;
					}
				}
				i++;
				p.setAdminLevel(Integer.parseInt(details[i]));
				i++;
				p.setPokemonsSprite(Integer.parseInt(details[i]));
				i++;
				//p.setPokemonX(Integer.parseInt(details[i]));
				//p.setLastPlayerX(Integer.parseInt(details[i]));
				i++;
				//p.setPokemonY(Integer.parseInt(details[i]));
				//p.setLastPlayerY(Integer.parseInt(details[i]));
				p.loadSpriteImage();
				p.loadPokemonSpriteImage();
				p.setPokemonDirection(p.getDirection());
			}
			
			if(p.getId() == GameClient.getInstance().getPlayerId())
			{
				/* This dude is our player! Store this information */
				p.setOurPlayer(true);
				
				OurPlayer pl;
				if(GameClient.getInstance().getOurPlayer() == null)
					pl = new OurPlayer();
				else
					pl = new OurPlayer(GameClient.getInstance().getOurPlayer());
				
				pl.set(p);
				
				GameClient.getInstance().setOurPlayer(pl);
				GameClient.getInstance().getMapMatrix().addPlayer(pl);
				GameClient.getInstance().setOurPlayer(pl);
				GameClient.getInstance().getOurPlayer().setAnimating(true);
				
				GameClient.getInstance().getOurPlayer().setPokemonDirection(p.getDirection());
				
				GameClient.getInstance().getOurPlayer().setLastPlayerX(p.getX());
				GameClient.getInstance().getOurPlayer().setLastPlayerY(p.getY());
				GameClient.getInstance().getOurPlayer().setPokemonX(p.getX());
				GameClient.getInstance().getOurPlayer().setPokemonY(p.getY());
				
				GameClient.getInstance().getOurPlayer().setPokemonsSprite(p.getPokemonSprite());
			}
			else
				GameClient.getInstance().getMapMatrix().addPlayer(p);
		}
	}
}
