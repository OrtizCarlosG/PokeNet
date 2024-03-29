/* StatChangeEffect.java
 * Created on December 23, 2006, 4:07 PM
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
import org.pokemonium.server.battle.mechanics.StatMultiplier;
import org.pokemonium.server.battle.mechanics.moves.MoveList;

/**
 * @author Colin
 */
public class StatChangeEffect extends StatusEffect
{

	private String m_desc = null;
	private boolean m_raise = true;
	private int m_stages = 1, m_undo = 1;
	private int m_stat = -1;

	/** Creates a new instance of StatusEffect */
	public StatChangeEffect(int stat, boolean raise)
	{
		m_stat = stat;
		m_raise = raise;
		m_desc = getDefaultDescription();
	}

	public StatChangeEffect(int stat, boolean raise, int stages)
	{
		m_stat = stat;
		m_raise = raise;
		m_stages = m_undo = stages;
		m_desc = getDefaultDescription();
	}

	/**
	 * Applies the initial effects of the status to a pokemon but does not add
	 * the status to the list of statuses the pokemon has.
	 */
	@Override
	public boolean apply(Pokemon p)
	{

		// Abilities do not prevent pokemon from lowering their own stats.
		if(getInducer() != p)
		{
			if(!m_raise && (p.hasAbility("Clear Body") || p.hasAbility("White Smoke")))
			{
				p.getField().showMessage(p.getAbilityName() + " prevents stats being lowered");
				return false;
			}
			if(!m_raise)
			{
				MoveList.MistEffect effect = (MoveList.MistEffect) p.getField().getEffectByType(MoveList.MistEffect.class);
				if(effect != null)
					if(effect.isActive(p.getParty()))
						return false;
			}
			if(!m_raise && m_stat == Pokemon.S_ATTACK && p.hasAbility("Hyper Cutter"))
			{
				p.getField().showMessage("Hyper Cutter prevents the attack stat being lowered");
				return false;
			}
			if(!m_raise && m_stat == Pokemon.S_ACCURACY && p.hasAbility("Keen Eye"))
			{
				p.getField().showMessage("Keen Eye prevents the accuracy being lowered");
				return false;
			}
		}

		StatMultiplier mul = getMultiplier(p);
		if(mul == null)
			return false;

		int total = 0;
		for(int i = 0; i < m_stages; ++i)
		{
			boolean changed = false;
			if(m_raise)
			{
				changed = mul.increaseMultiplier();
				p.getField().showMessage(p.getName() + getDefaultDescription());
			}
			else
			{
				changed = mul.decreaseMultiplier();
				p.getField().showMessage(p.getName() + getDefaultDescription());
			}
			if(changed)
				++total;
		}

		m_undo = total;
		if(total == 0)
		{
			String keyword = m_raise ? "higher" : "lower";
			p.getField().showMessage(p.getName() + "'s " + Pokemon.getStatName(m_stat) + " won't go " + keyword + "!");
			return false;
		}
		return true;
	}

	/**
	 * Get a description of this status effect.
	 */
	@Override
	public String getDescription()
	{
		return m_desc;
	}

	@Override
	public String getName()
	{
		return "Status";
	}

	/**
	 * Returns the stat being modified by this effect.
	 */
	public int getStat()
	{
		return m_stat;
	}

	@Override
	public int getTier()
	{
		return -1;
	}

	@Override
	public boolean immobilises(Pokemon poke)
	{
		return false;
	}

	/**
	 * Returns whether this stat represents an increase in a stat.
	 */
	public boolean isRaise()
	{
		return m_raise;
	}

	/**
	 * There can be more than one version of this effect.
	 */
	@Override
	public boolean isSingleton()
	{
		return false;
	}

	/**
	 * Set a description.
	 */
	public void setDescription(String desc)
	{
		m_desc = desc;
	}

	/**
	 * Called when a pokemon with this status effect switches out.
	 * Returns true if the status effect should be removed.
	 */
	@Override
	public boolean switchOut(Pokemon p)
	{
		return true;
	}

	@Override
	public boolean tick(Pokemon p)
	{
		return false;
	}

	/**
	 * Unapply this status effect.
	 */
	@Override
	public void unapply(Pokemon p)
	{
		StatMultiplier mul = getMultiplier(p);
		for(int i = 0; i < m_undo; ++i)
			if(mul != null)
				if(m_raise)
					mul.decreaseMultiplier();
				else
					mul.increaseMultiplier();
	}

	private String getDefaultDescription()
	{
		String adj = "", adv = "";
		if(m_raise)
		{
			adj = "raised";
			if(m_stages == 2)
				adv = "sharply ";
		}
		else
		{
			adj = "lowered";
			if(m_stages == 2)
				adv = "harshly ";
		}
		return "'s " + Pokemon.getStatName(m_stat) + " was " + adv + adj + ".";
	}

	private StatMultiplier getMultiplier(Pokemon p)
	{
		StatMultiplier mul = null;
		if(m_stat == Pokemon.S_ACCURACY)
			mul = p.getAccuracy();
		else if(m_stat == Pokemon.S_EVASION)
			mul = p.getEvasion();
		else
			mul = p.getMultiplier(m_stat);
		return mul;
	}
}
