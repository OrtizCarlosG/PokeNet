/* RainEffect.java
 * Created on May 6, 7:49 PM
 * This file is a part of Shoddy Battle.
 * Copyright (C) 2006 Colin Fitzpatrick
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details. */

package org.pokemonium.server.battle.mechanics.statuses.field;

import org.pokemonium.server.battle.BattleField;
import org.pokemonium.server.battle.Pokemon;
import org.pokemonium.server.battle.mechanics.BattleMechanics;
import org.pokemonium.server.battle.mechanics.PokemonType;
import org.pokemonium.server.battle.mechanics.moves.MoveList;
import org.pokemonium.server.battle.mechanics.moves.MoveListEntry;
import org.pokemonium.server.battle.mechanics.moves.PokemonMove;
import org.pokemonium.server.battle.mechanics.moves.StatusMove;
import org.pokemonium.server.battle.mechanics.statuses.ChargeEffect;
import org.pokemonium.server.battle.mechanics.statuses.ParalysisEffect;
import org.pokemonium.server.battle.mechanics.statuses.PercentEffect;
import org.pokemonium.server.battle.mechanics.statuses.StatusEffect;

/**
 * 1. Raises the power of Water moves by 1.5x
 * 2. Cuts the power of Fire moves by 1/2.
 * 3. Cuts the power of Solarbeam to 60.
 * 4. Makes Thunder have perfect accuracy. (Perfect, not 100%)
 * 5. Doubles the current effective Speed of pokemon with the Swift Swim
 * ability.
 * 6. Restores 1/16 HP to pokemon with the Rain Dish ability.
 * 7. Makes pokemon with the Forecast ability Water-type.
 * 8. Makes Weather Ball a power 100 Water-type move.
 * 9. Makes Moonlight and Morning Sun restore 1/4 of the user's max HP.
 * 10. Heals Pokemon with Dry Skin 1/8 health each turn
 * 
 * @author Ben
 */
public class RainEffect extends WeatherEffect
{

	public RainEffect()
	{
		super(5);
	}

	/** Creates a new instance of SunEffect */
	public RainEffect(int turns)
	{
		super(turns);
	}

	@Override
	public boolean apply(Pokemon p)
	{
		if(m_applied[p.getParty()] || !(m_applied[p.getParty()] = hasEffects(p.getField())))
			return true;
		// Double the current effective Speed of pokemon with the
		// Swift Swim ability.
		modifySpeed(p, 2.0);
		setTypes(p, new PokemonType[] { PokemonType.T_WATER }, true);
		return true;
	}

	/**
	 * Apply this effect to a field.
	 */
	@Override
	public boolean applyToField(BattleField field)
	{
		field.showMessage("Rain began to fall!");
		return true;
	}

	@Override
	public String getDescription()
	{
		return null;
	}

	@Override
	public String getName()
	{
		return "Rain";
	}

	/**
	 * 1. Raises the power of Water moves by 1.5x
	 * 2. Cuts the power of Fire moves by 1/2.
	 * 3. Cuts the power of Solarbeam to 60.
	 * 4. Makes Thunder have perfect accuracy. (Perfect, not 100%)
	 * 5. Doubles the current effective Speed of pokemon with the Swift Swim
	 * ability.
	 * 6. Restores 1/16 HP to pokemon with the Rain Dish ability.
	 * 7. Makes pokemon with the Forecast ability Water-type.
	 * 8. Makes Weather Ball a power 100 Water-type move.
	 * 9. Makes Moonlight and Morning Sun restore 1/4 of the user's max HP.
	 */
	@Override
	public MoveListEntry getTransformedMove(Pokemon poke, MoveListEntry entry)
	{
		if(!hasEffects(poke.getField()))
			return entry;

		PokemonMove move = entry.getMove();
		PokemonType type = move.getType();
		String name = entry.getName();
		if(type.equals(PokemonType.T_WATER))
			move.setPower((int) (move.getPower() * 1.5));
		else if(type.equals(PokemonType.T_FIRE))
			move.setPower((int) (move.getPower() / 2.0));
		else if(name.equals("Weather Ball"))
		{
			move.setPower(100);
			move.setType(PokemonType.T_WATER);
		}
		else if(name.equals("Moonlight") || name.equals("Morning Sun") || name.equals("Synthesis"))
		{
			StatusMove statusMove = (StatusMove) move;
			// Assume that the first effect is the PercentEffect!
			PercentEffect perc = (PercentEffect) statusMove.getEffects()[0];
			perc.setPercent(1.0 / 3.0);
		}
		else if(name.equals("Solar Beam"))
		{
			if(move instanceof StatusMove)
			{
				StatusMove statusMove = (StatusMove) move;
				ChargeEffect charge = (ChargeEffect) statusMove.getEffects()[0];
				charge.setTurns(2);
			}
		}
		else if(name.equals("Thunder"))
			return new MoveListEntry("Thunder", new StatusMove(PokemonType.T_ELECTRIC, 120, 0.7, 10, new StatusEffect[] { new ParalysisEffect() }, new boolean[] { false }, new double[] { 0.3 })
			{
				@Override
				public boolean attemptHit(BattleMechanics mech, Pokemon user, Pokemon target)
				{
					return MoveList.PerfectAccuracyMove.isHit(mech, user, target);
				}
			});

		return entry;
	}

	@Override
	public boolean immobilises(Pokemon p)
	{
		return false;
	}

	@Override
	public boolean tickPokemon(Pokemon p)
	{
		if(!hasEffects(p.getField()))
			return false;
		if(p.hasAbility("Dry Skin"))
		{
			p.getField().showMessage(p.getName() + " soaked up rain!");
			p.changeHealth(p.getStat(Pokemon.S_HP) / 8);
		}
		if(p.hasAbility("Hydration"))
			if(p.hasEffect(StatusEffect.SPECIAL_EFFECT_LOCK))
			{
				p.getField().showMessage(p.getName() + "'s status was cured!");
				p.removeStatus(StatusEffect.SPECIAL_EFFECT_LOCK);
			}
		return false;
	}

	@Override
	public void unapply(Pokemon p)
	{
		if(!m_applied[p.getParty()])
			return;
		m_applied[p.getParty()] = false;
		modifySpeed(p, 0.5);
		setTypes(p, null, false);
	}

	/**
	 * Remove this effect from a field.
	 */
	@Override
	public void unapplyToField(BattleField field)
	{
		field.showMessage("The rain stopped.");
	}

	/**
	 * Tick this effect for the whole field.
	 */
	@Override
	protected void tickWeather(BattleField field)
	{
		field.showMessage("The rain continues to fall.");
	}

	private void modifySpeed(Pokemon p, double factor)
	{
		if(!p.hasAbility("Swift Swim"))
			return;
		p.getMultiplier(Pokemon.S_SPEED).multiplyBy(factor);
	}

}
