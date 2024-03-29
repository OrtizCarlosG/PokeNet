package org.pokemonium.server.backend.map;

import org.pokemonium.server.GameServer;
import org.pokemonium.server.backend.entity.Character;
import org.pokemonium.server.backend.entity.Player;

/**
 * Represents a warp tile
 * 
 * @author shadowkanji
 */
public class WarpTile
{
	private int m_reqBadges = 0;
	private int m_x, m_y, m_warpMapX, m_warpMapY, m_warpX, m_warpY;

	/**
	 * Returns the x co-ordinate of this tile
	 * 
	 * @return
	 */
	public int getX()
	{
		return m_x;
	}

	/**
	 * Returns the y co-ordinate of this tile
	 * 
	 * @return
	 */
	public int getY()
	{
		return m_y;
	}

	/**
	 * Sets the required amount of badges to use this warp tile
	 * 
	 * @param amount
	 */
	public void setBadgeRequirement(int amount)
	{
		m_reqBadges = amount;
	}

	/**
	 * Sets the x co-ordianate of the map this warps to
	 * 
	 * @param x
	 */
	public void setWarpMapX(int x)
	{
		m_warpMapX = x;
	}

	/**
	 * Sets the y co-ordinate of the map this warps to
	 * 
	 * @param y
	 */
	public void setWarpMapY(int y)
	{
		m_warpMapY = y;
	}

	/**
	 * Sets the x co-ordinate of where this tile warps to
	 * 
	 * @param x
	 */
	public void setWarpX(int x)
	{
		m_warpX = x;
	}

	/**
	 * Sets the y co-ordinate of where this tile warps to
	 * 
	 * @param y
	 */
	public void setWarpY(int y)
	{
		m_warpY = y;
	}

	/**
	 * Sets the x co-ordinate of this tile
	 * 
	 * @param x
	 */
	public void setX(int x)
	{
		m_x = x;
	}

	/**
	 * Sets the y co-ordinate of this tile
	 * 
	 * @param y
	 */
	public void setY(int y)
	{
		m_y = y;
	}

	/**
	 * Warps a character
	 * 
	 * @param c
	 */
	public void warp(Character c)
	{
		if(c instanceof Player)
		{
			Player p = (Player) c;
			if(p.getBadgeCount() >= m_reqBadges)
			{
				p.setX(m_warpX);
				p.setY(m_warpY);
				p.setMap(GameServer.getServiceManager().getMovementService().getMapMatrix().getMapByGamePosition(m_warpMapX, m_warpMapY), null);
			}
		}
	}
}
