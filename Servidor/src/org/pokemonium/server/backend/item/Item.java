package org.pokemonium.server.backend.item;

import java.util.ArrayList;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;

/**
 * Represents an item
 * 
 * @author shadowkanji
 * @author Nushio
 */
public class Item
{
	/* Handles item attributes, lets us know what we need to do with them */
	public enum ItemAttribute
	{
		BATTLE, CRAFT, FIELD, HOLD, MOVESLOT, OTHER, POKEMON
	}

	@ElementList
	private ArrayList<ItemAttribute> m_attributes;
	@Element
	private String m_category;
	@Element
	private String m_description;
	@Element
	private int m_id;
	@Element
	private String m_name;
	@Element
	private int m_price;

	@Element
	private int m_shop;

	/**
	 * Adds an attribute
	 * 
	 * @param i
	 */
	public void addAttribute(ItemAttribute i)
	{
		if(m_attributes == null)
			m_attributes = new ArrayList<ItemAttribute>();
		m_attributes.add(i);
	}

	/**
	 * Returns the arraylist of attributes
	 * 
	 * @return
	 */
	public ArrayList<ItemAttribute> getAttributes()
	{
		return m_attributes;
	}

	/**
	 * Returns the category of the item
	 * 
	 * @return
	 */
	public String getCategory()
	{
		return m_category;
	}

	/**
	 * Returns the description of the item
	 * 
	 * @return
	 */
	public String getDescription()
	{
		return m_description;
	}

	/**
	 * Returns the id of the item
	 * 
	 * @return
	 */
	public int getId()
	{
		return m_id;
	}

	/**
	 * Returns the name of the item
	 * 
	 * @return
	 */
	public String getName()
	{
		return m_name;
	}

	/**
	 * Returns the price of the item
	 * 
	 * @return
	 */
	public int getPrice()
	{
		return m_price;
	}

	/**
	 * Returns the shop of the item
	 * 
	 * @return
	 */
	public int getShop()
	{
		return m_shop;
	}

	/**
	 * Sets the category of the item
	 * 
	 * @param s
	 */
	public void setCategory(String s)
	{
		m_category = s;
	}

	/**
	 * Sets the description of the item
	 * 
	 * @param s
	 */
	public void setDescription(String s)
	{
		m_description = s;
	}

	/**
	 * Sets the id of the item
	 * 
	 * @param i
	 */
	public void setId(int i)
	{
		m_id = i;
	}

	/**
	 * Sets the name of the item
	 * 
	 * @param s
	 */
	public void setName(String s)
	{
		m_name = s;
	}

	/**
	 * Sets the price of the item
	 * 
	 * @param s
	 */
	public void setPrice(int s)
	{
		m_price = s;
	}

	/**
	 * Sets the shop of the item
	 * 
	 * @param s
	 */
	public void setShop(int s)
	{
		m_shop = s;
	}
}
