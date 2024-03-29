/* PoisonEffect.java
 * Created on December 23, 2006, 12:17 PM
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
import org.pokemonium.server.battle.mechanics.PokemonType;

/**
 * @author Colin
 */
public class PoisonEffect extends StatusEffect
{

	/** Creates a new instance of PoisonEffect */
	public PoisonEffect()
	{
		m_lock = SPECIAL_EFFECT_LOCK;
	}

	@Override
	public boolean apply(Pokemon p)
	{
		if(p.hasAbility("Immunity"))
			return false;
		if(p.isType(PokemonType.T_POISON) || p.isType(PokemonType.T_STEEL))
			return false;
		return true;
	}

	@Override
	public String getDescription()
	{
		return " was poisoned!";
	}

	@Override
	public String getName()
	{
		return "Poison";
	}

	/**
	 * Poison is in the fourth tier.
	 */
	@Override
	public int getTier()
	{
		return 3;
	}

	/**
	 * Poison does not immobilise.
	 */
	@Override
	public boolean immobilises(Pokemon poke)
	{
		return false;
	}

	/**
	 * Poison stays through switching out.
	 */
	@Override
	public boolean switchOut(Pokemon p)
	{
		return false;
	}

	/**
	 * Poison removes 1/8 max health each round.
	 */
	@Override
	public boolean tick(Pokemon p)
	{
		int damage;
		if(p.hasAbility("Poison Heal"))
		{
			damage = p.getStat(Pokemon.S_HP) / 8;
			p.getField().showMessage(p.getName() + "'s Poison Heal restored health!");
			p.changeHealth(damage, true);
		}
		else
		{
			damage = p.getStat(Pokemon.S_HP) / 8;
			if(damage <= 0)
				damage = 1;
			p.getField().showMessage(p.getName() + " is hurt by poison!");
			p.changeHealth(-damage, true);
		}
		return false;
	}

	@Override
	public void unapply(Pokemon p)
	{
	}

}
