package org.pokemonium.client.backend.entity;

import java.util.LinkedList;
import java.util.Queue;

import org.newdawn.slick.Image;
import org.pokemonium.client.backend.PokemonSpriteFactory;
import org.pokemonium.client.backend.SpriteFactory;

/**
 * Represents a player
 * 
 * @author shadowkanji
 */
public class Player
{
	public enum Direction
	{
		Down, Left, Right, Up
	}

	private static SpriteFactory m_spriteFactory;
	private static PokemonSpriteFactory m_spritePokemonFactory;

	// Handles animating footsteps
	public boolean m_PokemonleftOrRight = false;
	public boolean m_leftOrRight = false;
	public boolean m_canMove = true;

	protected Image m_currentImage;
	protected Image m_PokemonImage;

	protected Direction m_direction = Direction.Down;

	protected int m_id;
	protected boolean m_isAnimating = false;
	/* Handles movement queue */
	protected Queue<Direction> m_movementQueue = new LinkedList<Direction>();
	protected boolean m_ours = false;
	protected int m_sprite;
	protected int m_svrX;
	protected int m_svrY;
	protected String m_username;
	protected boolean m_wasOnGrass = false;
	
	protected int m_x;
	protected int m_y;
	
	public int m_Mapx;
	public int m_Mapy;
	
	protected int m_adminLevel;
	
	// New variables
	protected int m_LastPlayerX;
	protected int m_LastPlayerY;
	
	// Pokemon variables
	private int m_pokemonSprite, m_PokemonX, m_PokemonY;
	protected Direction m_pokemonDirection = Direction.Down;
	
	/**
	 * Returns the sprite factory
	 * 
	 * @return
	 */
	public static SpriteFactory getSpriteFactory()
	{
		return m_spriteFactory;
	}

	public static void loadSpriteFactory()
	{
		m_spriteFactory = new SpriteFactory();
	}

	public static void setSpriteFactory(SpriteFactory spriteFactory)
	{
		m_spriteFactory = spriteFactory;
	}
	
	/**
	 * Returns the pokemon sprite factory
	 * 
	 * @return
	 */
	public static PokemonSpriteFactory getPokemonSpriteFactory()
	{
		return m_spritePokemonFactory;
	}

	public static void loadPokemonSpriteFactory()
	{
		m_spritePokemonFactory = new PokemonSpriteFactory();
	}

	public static void setPokemonSpriteFactory(PokemonSpriteFactory spriteFactory)
	{
		m_spritePokemonFactory = spriteFactory;
	}
	
	/**
	 * Returns true if our player can move
	 * 
	 * @return
	 */
	public boolean canMove()
	{
		if(getX() == getServerX() && getY() == getServerY() && m_canMove == true)
			return true;
		return false;
	}

	public void clearMovementQueue()
	{
		m_movementQueue.clear();
	}

	/**
	 * Returns the current image of this player's animation
	 * 
	 * @return
	 */
	public Image getCurrentImage()
	{
		return m_currentImage;
	}
	
	/**
	 * Returns the current pokemon image of this player's animation
	 * 
	 * @return
	 */
	public Image getCurrentPokemonImage()
	{
		return m_PokemonImage;
	}
	
	/**
	 * Returns this player's direction
	 * 
	 * @return
	 */
	public Direction getDirection()
	{
		return m_direction;
	}
	
	/**
	 * Returns this player's direction
	 * 
	 * @return
	 */
	public Direction getPokemonDirection()
	{
		return m_pokemonDirection;
	}
	
	/**
	 * Returns this player's id
	 * 
	 * @return
	 */
	public int getId()
	{
		return m_id;
	}

	/**
	 * Returns the next movement for the player
	 * 
	 * @return
	 */
	public Direction getNextMovement()
	{
		return m_movementQueue.poll();
	}
	
	/**
	 * Returns this player's x co-ordinate serverside
	 * 
	 * @return
	 */
	public int getLastPlayerX()
	{
		return m_LastPlayerX;
	}

	/**
	 * Returns this player's y co-ordinate serverside
	 * 
	 * @return
	 */
	public int getLastPlayerY()
	{
		return m_LastPlayerY;
	}
	
	/**
	 * Returns this player's x co-ordinate serverside
	 * 
	 * @return
	 */
	public int getServerX()
	{
		return m_svrX;
	}

	/**
	 * Returns this player's y co-ordinate serverside
	 * 
	 * @return
	 */
	public int getServerY()
	{
		return m_svrY;
	}
	
	/**
	 * Returns this player's sprite
	 * 
	 * @return
	 */
	public int getSprite()
	{
		return m_sprite;
	}
	
	/**
	 * Returns this player's pokemon sprite
	 * 
	 * @return
	 */
	public int getPokemonSprite()
	{
		return m_pokemonSprite;
	}
	
	/**
	 * Returns the type of player
	 * 
	 * @return 0 for NPC, 1 for Player, 2 for HMObject
	 */
	public int getType()
	{
		return 0;
	}

	/**
	 * Returns this player's username
	 * 
	 * @return
	 */
	public String getUsername()
	{
		return m_username;
	}

	/**
	 * Returns this player's x co-ordinate
	 * 
	 * @return
	 */
	public int getX()
	{
		return m_x;
	}

	/**
	 * Returns this player's y co-ordinate
	 * 
	 * @return
	 */
	public int getY()
	{
		return m_y;
	}
	
	/**
	 * Returns the y co-ordinate of this char
	 */
	public int getPokemonY()
	{
		return m_PokemonY;
	}
	
	/**
	 * Returns the x last co-ordinate of this char
	 */
	public int getPokemonX()
	{
		return m_PokemonX;
	}
	
	/**
	 * Returns true if this player is/should be animating
	 * 
	 * @return
	 */
	public boolean isAnimating()
	{
		return m_isAnimating;
	}

	/**
	 * Returns true if this is our player
	 * 
	 * @return
	 */
	public boolean isOurPlayer()
	{
		return m_ours;
	}

	/**
	 * Loads the player's sprite image
	 */
	public void loadSpriteImage()
	{
		// Apply current player sprite
		m_currentImage = Player.getSpriteFactory().getSprite(m_direction, false, m_leftOrRight, m_sprite);
	}
	
	/**
	 * Loads the player's sprite image
	 */
	public void loadPokemonSpriteImage()
	{
		// Apply current player pokemon sprite
		m_PokemonImage = Player.getPokemonSpriteFactory().getSprite(m_pokemonDirection, m_leftOrRight, m_pokemonSprite);
	}
	
	/**
	 * Moves this player down
	 */
	public void moveDown()
	{
		m_svrY += 32;
		m_isAnimating = true;
	}

	/**
	 * Moves this player left
	 */
	public void moveLeft()
	{
		m_svrX -= 32;
		m_isAnimating = true;
	}

	/**
	 * Moves this player right
	 */
	public void moveRight()
	{
		m_svrX += 32;
		m_isAnimating = true;
	}

	/**
	 * Moves this player up
	 */
	public void moveUp()
	{
		m_svrY -= 32;
		m_isAnimating = true;
	}

	/**
	 * Queues a movement
	 * 
	 * @param d
	 */
	public void queueMovement(Direction d)
	{
		/* if(m_direction != d || !GameClient.getInstance().getMapMatrix().getCurrentMap().isColliding(this, d) && !GameClient.getInstance().getHUD().getBattleDialog().isVisible())
		 * ;
		 * ; // TODO: Owh yeah... Lets make an awesome big IF statement and DO ABSOLUTELY NOTHING WITH IT. */
		m_movementQueue.offer(d);
	}
	
	/**
	 * Returns true if player has a movement queued and can be moved
	 * 
	 * @return
	 */
	public boolean requiresMovement()
	{
		return canMove() && m_movementQueue.size() > 0;
	}
	
	/**
	 * Sets if this player is animating
	 * 
	 * @param a
	 */
	public void setAnimating(boolean a)
	{
		m_isAnimating = a;
	}

	/**
	 * Sets this player's current image
	 * 
	 * @param i
	 */
	public void setCurrentImage(Image i)
	{
		m_currentImage = i;
	}
	
	/**
	 * Sets this player's current pokemon image
	 * 
	 * @param i
	 */
	public void setCurrentPokemonImage(Image i)
	{
		m_PokemonImage = i;
	}
	
	/**
	 * Sets this player's direction
	 * 
	 * @param d
	 */
	public void setDirection(Direction d)
	{
		m_direction = d;
	}
	
	/**
	 * Sets this player's pokemon direction
	 * 
	 * @param d
	 */
	public void setPokemonDirection(Direction d)
	{
		m_pokemonDirection = d;
	}
	
	/**
	 * Sets this player's id
	 * 
	 * @param id
	 */
	public void setId(int id)
	{
		m_id = id;
	}

	/**
	 * Sets if this is our player
	 * 
	 * @param b
	 */
	public void setOurPlayer(boolean b)
	{
		m_ours = b;
	}
	
	/**
	 * Sets this player's x co-ordinate on the server
	 * 
	 * @param x
	 */
	public void setServerX(int x)
	{
		m_svrX = x;
	}

	/**
	 * Sets this player's y co-ordinate on the server
	 * 
	 * @param y
	 */
	public void setServerY(int y)
	{
		m_svrY = y;
	}
	
	/**
	 * Sets this player's x pokemon co-ordinate on the server
	 * 
	 * @param x
	 */
	public void setPokemonX(int x)
	{
		m_PokemonX = x;
	}
	
	/**
	 * Sets this player's y co-ordinate on the server
	 * 
	 * @param y
	 */
	public void setLastPlayerY(int y)
	{
		m_LastPlayerY = y;
	}
	
	/**
	 * Sets this player's y pokemon co-ordinate on the server
	 * 
	 * @param y
	 */
	public void setPokemonY(int y)
	{
		m_PokemonY = y;
	}
	
	/**
	 * Sets this player's y co-ordinate on the server
	 * 
	 * @param y
	 */
	public void setLastPlayerX(int x)
	{
		m_LastPlayerX = x;
	}
	
	/**
	 * Sets this player's sprite
	 * 
	 * @param sprite
	 */
	public void setSprite(int sprite)
	{
		m_sprite = sprite;
	}
	
	/**
	 * Sets this player's pokemon sprite
	 * 
	 * @param sprite
	 */
	public void setPokemonsSprite(int sprite)
	{
		m_pokemonSprite = sprite;
	}
	
	/**
	 * Sets this player's username
	 * 
	 * @param username
	 */
	public void setUsername(String username)
	{
		m_username = username;
	}

	/**
	 * Sets this player's x co-ordinate
	 * 
	 * @param x
	 */
	public void setX(int x)
	{
		m_x = x;
	}
	
	/**
	 * Sets this player's y co-ordinate
	 * 
	 * @param y
	 */
	public void setY(int y)
	{
		m_y = y;
	}

	public void setAdminLevel(int admin)
	{
		m_adminLevel = admin;
	}

	public int getAdminLevel()
	{
		return m_adminLevel;
	}
}
