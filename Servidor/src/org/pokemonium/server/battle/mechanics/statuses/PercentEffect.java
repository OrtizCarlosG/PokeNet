/* PercentMove.java
 * Created on January 6, 2007, 4:47 PM
 * This file is a part of Shoddy Battle.
 * Copyright (C) 2006 Colin Fitzpatrick
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, visit the Free Software Foundation, Inc.
 * online at http://gnu.org. */

package org.pokemonium.server.battle.mechanics.statuses;

import org.pokemonium.server.battle.Pokemon;
import org.pokemonium.server.battle.mechanics.moves.MoveList.HealBlockEffect;

/**
 * This class represents an effect that heals or does damage
 * based on a percentage. It can also represent statuses that
 * do damage each turn.
 * 
 * @author Ben
 */
public class PercentEffect extends StatusEffect
{

	private String m_description; // extra description applied when an effect or tick is applied
	private double m_percent;
	private boolean m_staysOnSwitch; // true if the effects remains upon switching out
	private int m_tier; // a tier other than -1 indicates that the move is a tick effect

	/** Creates a new instance of PercentEffect */
	public PercentEffect(double percent, boolean staysOnSwitch, int tier, String description)
	{
		m_percent = percent;
		m_staysOnSwitch = staysOnSwitch;
		m_tier = tier;
		m_description = description;
	}

	@Override
	public boolean apply(Pokemon p)
	{
		if(m_tier != -1)
			return true;
		if(p.hasEffect(HealBlockEffect.class))
			return false;
		if(m_description != null)
			p.getField().showMessage(m_description);
		p.changeHealth(calculateChange(p, m_percent), true);
		return false;
	}

	@Override
	public String getDescription()
	{
		return null;
	}

	@Override
	public String getName()
	{
		return "Percent effect";
	}

	public double getPercent()
	{
		return m_percent;
	}

	@Override
	public int getTier()
	{
		return m_tier;
	}

	@Override
	public boolean immobilises(Pokemon poke)
	{
		return false;
	}

	@Override
	public boolean isSingleton()
	{
		return false;
	}

	/**
	 * Change the percent that this move heals/hurts.
	 * 
	 * @author Colin
	 */
	public void setPercent(double percent)
	{
		m_percent = percent;
	}

	@Override
	public boolean switchOut(Pokemon p)
	{
		return !m_staysOnSwitch;
	}

	@Override
	public boolean tick(Pokemon p)
	{
		if(p.hasEffect(HealBlockEffect.class))
			return false;
		p.getField().showMessage(p.getName() + m_description);
		p.changeHealth(calculateChange(p, m_percent), true);
		return false;
	}

	@Override
	public void unapply(Pokemon p)
	{
	}

	protected int calculateChange(Pokemon poke, double percent)
	{
		double maximum = poke.getStat(Pokemon.S_HP);
		double change = maximum * m_percent;
		int floor = (int) change;
		return floor;
	}
}
