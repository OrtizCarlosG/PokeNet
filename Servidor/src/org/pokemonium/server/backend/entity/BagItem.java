package org.pokemonium.server.backend.entity;

/**
 * Represents an item in a player's bag
 * 
 * @author shadowkanji
 */
public class BagItem
{
	private final int m_item;
	private int m_quantity;

	/**
	 * Default contructor
	 * 
	 * @param itemNumber
	 * @param quantity
	 */
	public BagItem(int itemNumber, int quantity)
	{
		m_item = itemNumber;
		m_quantity = quantity;
	}

	/**
	 * Sets the quantity
	 * 
	 * @param q
	 */
	public void decreaseQuantity(int q)
	{
		m_quantity -= q;
	}

	/**
	 * Returns the item number of the item this object represents
	 * 
	 * @return
	 */
	public int getItemNumber()
	{
		return m_item;
	}

	/**
	 * Returns the quantity
	 * 
	 * @return
	 */
	public int getQuantity()
	{
		return m_quantity;
	}

	public void increaseQuantity(int q)
	{
		m_quantity += q;
	}
}
