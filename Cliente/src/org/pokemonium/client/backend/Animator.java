package org.pokemonium.client.backend;

import org.lwjgl.util.Timer;
import org.pokemonium.client.GameClient;
import org.pokemonium.client.backend.entity.Player;
import org.pokemonium.client.backend.entity.Player.Direction;
import org.pokemonium.client.constants.ServerPacket;
import org.pokemonium.client.protocol.ClientMessage;

public class Animator
{
	private static final int ANIMATION_INCREMENT = 4;

	private ClientMapMatrix m_mapMatrix;
	private Timer m_timer;

	// Sets up calls
	public Animator(ClientMapMatrix maps)
	{
		m_mapMatrix = maps;
		m_timer = new Timer();
	}

	// Prepares for animation
	public void animate()
	{
		try
		{
			while(m_timer.getTime() <= 0.025)
				Timer.tick();
			ClientMap map = m_mapMatrix.getCurrentMap();
			if(map != null)
				for(int i = 0; i < m_mapMatrix.getPlayers().size(); i++)
				{
					animatePlayer(m_mapMatrix.getPlayers().get(i));
					animatePokemon(m_mapMatrix.getPlayers().get(i));
					animateMapName();
				}
			m_timer.reset();
		}
		catch(Exception e)
		{
			m_timer.reset();
		}
	}
	
	private void animateMapName() {
		try {
			GameClient.getInstance().getHUD().getTopBar().getLocationLabel().setText(GameClient.getInstance().getMapMatrix().getMapName(GameClient.getInstance().getOurPlayer().m_Mapx, GameClient.getInstance().getOurPlayer().m_Mapy));
		}
		catch(Exception ex)	{}
	}

	/**
	 * Animates players moving
	 * 
	 * @param player
	 */
	private void animatePlayer(Player player)
	{
		/* Check if we need to move the player */
		if(player.requiresMovement())
		{
			// Player position
			Direction d = player.getNextMovement();
			
			switch(d)
			{
				case Down:
				{
					//System.out.println("Baixo");
					if(player.getDirection() == Direction.Down)
					{
						player.setServerY(player.getY() + 32);
						
						// Apply last x, y
						player.setLastPlayerX(player.getX());
						player.setLastPlayerY(player.getY());
					}
					else
					{
						player.setDirection(Direction.Down);
						player.loadSpriteImage();
						player.loadPokemonSpriteImage();
					}
					break;
				}
				case Left:
				{
					//System.out.println("Esquerda");
					if(player.getDirection() == Direction.Left)
					{
						player.setServerX(player.getX() - 32);
						
						// Apply last x, y
						player.setLastPlayerX(player.getX());
						player.setLastPlayerY(player.getY());
					}
					else
					{
						player.setDirection(Direction.Left);
						player.loadSpriteImage();
						player.loadPokemonSpriteImage();
					}
					break;
				}
				case Right:
				{
					//System.out.println("Direita");
					if(player.getDirection() == Direction.Right)
					{
						player.setServerX(player.getX() + 32);
						
						// Apply last x, y
						player.setLastPlayerX(player.getX());
						player.setLastPlayerY(player.getY());
					}
					else
					{
						player.setDirection(Direction.Right);
						player.loadSpriteImage();
						player.loadPokemonSpriteImage();
					}
					break;
				}
				case Up:
				{
					//System.out.println("Cima");
					if(player.getDirection() == Direction.Up)
					{
						player.setServerY(player.getY() - 32);
						
						// Apply last x, y
						player.setLastPlayerX(player.getX());
						player.setLastPlayerY(player.getY());
					}
					else
					{
						player.setDirection(Direction.Up);
						player.loadSpriteImage();
						player.loadPokemonSpriteImage();
					}
					break;
				}
			}
			
			// Update last position
			ClientMessage UpdateLast = new ClientMessage(ServerPacket.PLAYER_UPDATE_LASTPOSITION);
			UpdateLast.addString(player.getLastPlayerX() + "," + player.getLastPlayerY());
			GameClient.getInstance().getSession().send(UpdateLast);
		}
		
		/* Keep the screen following the player, i.e. move the map also */
		if(player.isOurPlayer())
		{
			if(player.getX() > player.getServerX())
				m_mapMatrix.getCurrentMap().setXOffset(m_mapMatrix.getCurrentMap().getXOffset() + ANIMATION_INCREMENT, true);
			else if(player.getX() < player.getServerX())
				m_mapMatrix.getCurrentMap().setXOffset(m_mapMatrix.getCurrentMap().getXOffset() - ANIMATION_INCREMENT, true);
			else if(player.getY() > player.getServerY())
				m_mapMatrix.getCurrentMap().setYOffset(m_mapMatrix.getCurrentMap().getYOffset() + ANIMATION_INCREMENT, true);
			else if(player.getY() < player.getServerY())
				m_mapMatrix.getCurrentMap().setYOffset(m_mapMatrix.getCurrentMap().getYOffset() - ANIMATION_INCREMENT, true);
		}
		
		/* Move the player on screen */
		if(player.getX() > player.getServerX())
		{
			// Choose the sprite according to the player's position
			if(player.getX() % 32 == 0)
			{
				player.setDirection(Direction.Left);
				player.m_leftOrRight = !player.m_leftOrRight;
				player.m_PokemonleftOrRight = !player.m_PokemonleftOrRight;
				player.setCurrentImage(Player.getSpriteFactory().getSprite(player.getDirection(), true, player.m_leftOrRight, player.getSprite()));
				player.setCurrentPokemonImage(Player.getPokemonSpriteFactory().getSprite(player.getPokemonDirection(), player.m_PokemonleftOrRight, player.getPokemonSprite()));
			}

			player.setX(player.getX() - ANIMATION_INCREMENT);
			
			if(player.getX() > player.getServerX() && player.getX() % 32 == 0)
			{
				/* If the player is still behind the server, make sure the stopped animation is shown */
				player.setCurrentImage(Player.getSpriteFactory().getSprite(player.getDirection(), false, player.m_leftOrRight, player.getSprite()));
				player.setCurrentPokemonImage(Player.getPokemonSpriteFactory().getSprite(player.getPokemonDirection(), player.m_PokemonleftOrRight, player.getPokemonSprite()));
			}
		}
		else if(player.getX() < player.getServerX())
		{
			if(player.getX() % 32 == 0)
			{
				player.setDirection(Direction.Right);
				player.m_leftOrRight = !player.m_leftOrRight;
				player.m_PokemonleftOrRight = !player.m_PokemonleftOrRight;
				player.setCurrentImage(Player.getSpriteFactory().getSprite(player.getDirection(), true, player.m_leftOrRight, player.getSprite()));
				player.setCurrentPokemonImage(Player.getPokemonSpriteFactory().getSprite(player.getPokemonDirection(), player.m_PokemonleftOrRight, player.getPokemonSprite()));
			}
			
			player.setX(player.getX() + ANIMATION_INCREMENT);
			
			if(player.getX() < player.getServerX() && player.getX() % 32 == 0)
			{
				/* If the player is still behind the server, make sure the stopped animation is shown */
				player.setCurrentImage(Player.getSpriteFactory().getSprite(player.getDirection(), false, player.m_leftOrRight, player.getSprite()));
				player.setCurrentPokemonImage(Player.getPokemonSpriteFactory().getSprite(player.getPokemonDirection(), player.m_PokemonleftOrRight, player.getPokemonSprite()));
			}
		}
		else if(player.getY() > player.getServerY())
		{
			if((player.getY() + 8) % 32 == 0)
			{
				player.setDirection(Direction.Up);
				player.m_leftOrRight = !player.m_leftOrRight;
				player.m_PokemonleftOrRight = !player.m_PokemonleftOrRight;
				player.setCurrentImage(Player.getSpriteFactory().getSprite(player.getDirection(), true, player.m_leftOrRight, player.getSprite()));
				//player.setCurrentPokemonImage(Player.getPokemonSpriteFactory().getSprite(player.getPokemonDirection(), player.m_PokemonleftOrRight, player.getPokemonSprite()));
			}
			
			player.setY(player.getY() - ANIMATION_INCREMENT);
			
			if(player.getY() > player.getServerY() && (player.getY() + 8) % 32 == 0)
			{
				/* If the player is still behind the server, make sure the stopped animation is shown */
				player.setCurrentImage(Player.getSpriteFactory().getSprite(player.getDirection(), false, player.m_leftOrRight, player.getSprite()));
				player.setCurrentPokemonImage(Player.getPokemonSpriteFactory().getSprite(player.getPokemonDirection(), player.m_PokemonleftOrRight, player.getPokemonSprite()));
			}
		}
		else if(player.getY() < player.getServerY())
		{
			if((player.getY() + 8) % 32 == 0)
			{
				player.setDirection(Direction.Down);
				player.m_leftOrRight = !player.m_leftOrRight;
				player.m_PokemonleftOrRight = !player.m_PokemonleftOrRight;
				player.setCurrentImage(Player.getSpriteFactory().getSprite(player.getDirection(), true, player.m_leftOrRight, player.getSprite()));
				player.setCurrentPokemonImage(Player.getPokemonSpriteFactory().getSprite(player.getPokemonDirection(), player.m_PokemonleftOrRight, player.getPokemonSprite()));
			}
			
			player.setY(player.getY() + ANIMATION_INCREMENT);
			
			if(player.getY() < player.getServerY() && (player.getY() + 8) % 32 == 0)
			{
				/* If the player is still behind the server, make sure the stopped animation is shown */
				player.setCurrentImage(Player.getSpriteFactory().getSprite(player.getDirection(), false, player.m_leftOrRight, player.getSprite()));
				player.setCurrentPokemonImage(Player.getPokemonSpriteFactory().getSprite(player.getPokemonDirection(), player.m_PokemonleftOrRight, player.getPokemonSprite()));
			}
		}
		
		/* The player is now in sync with the server, stop moving/animating them */
		if(player.getX() == player.getServerX() && player.getY() == player.getServerY())
		{
			//player.setPokemonDirection(player.getPokemonDirection());
			player.setDirection(player.getDirection());
			player.setAnimating(false);
			player.loadSpriteImage();
			player.loadPokemonSpriteImage();
		}
	}
	
	/**
	 * Animates pokemon moving
	 * 
	 * @param player
	 */
	private void animatePokemon(Player player)
	{
		// Check if pokemon exist
		if ( player.getPokemonSprite() == 0 )
			return;
		
		// Check if can move pokemon on map
		if( player.getLastPlayerX() != player.getPokemonX() )
		{
			if ( player.getLastPlayerX() > player.getPokemonX() )
			{
				// Check if need change the direction
				if ( player.getPokemonDirection() != Direction.Right )
				{
					player.setCurrentPokemonImage(Player.getPokemonSpriteFactory().getSprite(Direction.Right, player.m_PokemonleftOrRight, player.getPokemonSprite()));
				}
				
				player.setPokemonDirection(  Direction.Right );
				player.setPokemonX( player.getPokemonX() + 4 );
				
			}
			else
			{
				// Check if need change the direction
				if ( player.getPokemonDirection() != Direction.Left )
				{
					player.setCurrentPokemonImage(Player.getPokemonSpriteFactory().getSprite(Direction.Left, player.m_PokemonleftOrRight, player.getPokemonSprite()));
				}
				
				player.setPokemonDirection(  Direction.Left  );
				player.setPokemonX( player.getPokemonX() - 4 );
			}
		}
		else if( player.getLastPlayerY() != player.getPokemonY() )
		{
			if ( player.getLastPlayerY() > player.getPokemonY() )
			{
				// Check if need change the direction
				if ( player.getPokemonDirection() != Direction.Down )
				{
					player.setCurrentPokemonImage(Player.getPokemonSpriteFactory().getSprite(Direction.Down, player.m_PokemonleftOrRight, player.getPokemonSprite()));
				}
				
				player.setPokemonDirection(  Direction.Down  );
				player.setPokemonY( player.getPokemonY() + 4 );
			}
			else
			{
				// Check if need change the direction
				if ( player.getPokemonDirection() != Direction.Up )
				{
					player.setCurrentPokemonImage(Player.getPokemonSpriteFactory().getSprite(Direction.Up, player.m_PokemonleftOrRight, player.getPokemonSprite()));
				}
				
				player.setPokemonDirection(  Direction.Up    );
				player.setPokemonY( player.getPokemonY() - 4 );
			}
		}
	}
}
