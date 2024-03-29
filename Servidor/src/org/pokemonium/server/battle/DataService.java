package org.pokemonium.server.battle;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;
import java.util.StringTokenizer;
import org.ini4j.Ini;
import org.ini4j.Ini.Section;
import org.pokemonium.server.backend.item.DropData;
import org.pokemonium.server.battle.Pokemon.ExpTypes;
import org.pokemonium.server.battle.PokemonEvolution.EvolutionTypes;
import org.pokemonium.server.battle.mechanics.JewelMechanics;
import org.pokemonium.server.battle.mechanics.PokemonType;
import org.pokemonium.server.battle.mechanics.moves.MoveList;
import org.pokemonium.server.battle.mechanics.moves.MoveSetData;
import org.pokemonium.server.feature.FishDatabase;
import org.simpleframework.xml.core.Persister;

/**
 * Provides a data service for accessing various databases used by the server
 * 
 * @author shadowkanji
 */
public class DataService
{
	private static FishDatabase m_fishingData;
	private static JewelMechanics m_mechanics;
	private static MoveList m_moveList;
	private static MoveSetData m_moveSetData;
	private static final String[] m_nonTrades = new String[] { "Bulbasaur", "Ivysaur", "Venusaur", "Wartortle", "Blastoise", "Charmander", "Charmeleon", "Charizard", "Chikorita", "Bayleef",
			"Meganium", "Cyndaquil", "Quilava", "Typhlosion", "Totodile", "Croconaw", "Feraligatr", "Treecko", "Grovyle", "Sceptile", "Torchic", "Combusken", "Blaziken", "Mudkip", "Marshtomp",
			"Swampert", "Turtwig", "Grotle", "Torterra", "Chimchar", "Monferno", "Infernape", "Piplup", "Prinplup", "Empoleon" };
	private PokemonSpeciesData m_speciesData;

	/**
	 * Default constructor. Loads data immediately.
	 */
	public DataService()
	{
		try
		{
			Persister stream = new Persister();
			/* Load all of shoddy's databases */
			m_moveList = DataService.getMovesList();
			m_moveSetData = DataService.getMoveSetData();
			m_speciesData = new PokemonSpeciesData();
			m_mechanics = DataService.getBattleMechanics();
			m_fishingData = DataService.getFishDatabase();
			m_fishingData.reinitialise();
			JewelMechanics.loadMoveTypes("res/movetypes.txt");
			File f = new File(".");
			m_moveSetData = stream.read(MoveSetData.class, new File(f.getCanonicalPath() + "/res/movesets.xml"));
			initialiseSpecies();
			PokemonSpecies.setDefaultData(m_speciesData);
			System.out.println("INFO: Pokemon Databases loaded.");
			System.out.println("INFO: Trade Block List established.");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Returns true if the pokemon is tradeable
	 * 
	 * @param pokemon
	 * @return
	 */
	public static boolean canTrade(String pokemon)
	{
		for(int i = 0; i < m_nonTrades.length; i++)
			if(m_nonTrades[i].equalsIgnoreCase(pokemon))
				return false;
		return true;
	}

	/**
	 * Returns shoddybattle battle mechanics
	 * 
	 * @return
	 */
	public static JewelMechanics getBattleMechanics()
	{
		if(m_mechanics == null)
			m_mechanics = new JewelMechanics(5);
		return m_mechanics;
	}

	/**
	 * Returns the fish database
	 * 
	 * @return
	 */
	public static FishDatabase getFishDatabase()
	{
		if(m_fishingData == null)
			m_fishingData = new FishDatabase();
		return m_fishingData;
	}

	/**
	 * Returns move set data
	 * 
	 * @return
	 */
	public static MoveSetData getMoveSetData()
	{
		if(m_moveSetData == null)
			m_moveSetData = new MoveSetData();
		return m_moveSetData;
	}

	/**
	 * Returns the move list
	 * 
	 * @return
	 */
	public static MoveList getMovesList()
	{
		if(m_moveList == null)
			m_moveList = new MoveList(true);
		return m_moveList;
	}

	/**
	 * Initializes the species database
	 */
	public void initialiseSpecies()
	{
		/* Load shoddy database */
		try
		{
			m_speciesData.loadSpeciesDatabase(new File("./res/dpspecies.db"));
		}
		catch(Exception e1)
		{
			e1.printStackTrace();
			return;
		}
		Ini ini = null;
		/* Load updated POLR db */
		try
		{
			ini = new Ini(new FileInputStream("./res/pokemon.ini"));
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return;
		}
		for(int i = 0; i < 493; i++)
		{
			Ini.Section s = ini.get(String.valueOf(i + 1));
			PokemonSpecies species = null;
			String name = s.get("InternalName");
			if(name.equalsIgnoreCase("NIDORANfE"))
			{
				name = "Nidoran-f";
				species = m_speciesData.getPokemonByName(name);
				initialisePokemon(species, s, i);
			}
			else if(name.equalsIgnoreCase("NIDORANmA"))
			{
				name = "Nidoran-m";
				species = m_speciesData.getPokemonByName(name);
				initialisePokemon(species, s, i);
			}
			else if(name.equalsIgnoreCase("DEOXYS"))
			{
				species = m_speciesData.getPokemonByName(name);
				initialisePokemon(species, s, i);
				species = m_speciesData.getPokemonByName("Deoxys-f");
				initialisePokemon(species, s, i);
				species = m_speciesData.getPokemonByName("Deoxys-l");
				initialisePokemon(species, s, i);
				species = m_speciesData.getPokemonByName("Deoxys-e");
				initialisePokemon(species, s, i);
			}
			else if(name.equalsIgnoreCase("WORMADAM"))
			{
				species = m_speciesData.getPokemonByName(name);
				initialisePokemon(species, s, i);
				species = m_speciesData.getPokemonByName("Wormadam-g");
				initialisePokemon(species, s, i);
				species = m_speciesData.getPokemonByName("Wormadam-s");
				initialisePokemon(species, s, i);
			}
			else if(name.equalsIgnoreCase("Porygonz"))
			{
				name = "Porygonz";
				species = m_speciesData.getPokemonByName(name);
				initialisePokemon(species, s, i);
			}
			else
			{
				name = s.get("Name");
				species = m_speciesData.getPokemonByName(name);
				initialisePokemon(species, s, i);
			}
		}
		/* Load TM info */
		try
		{
			ini = new Ini(new FileInputStream("./res/tms.ini"));
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return;
		}
		Iterator<String> iterator = ini.keySet().iterator();
		while(iterator.hasNext())
		{
			String tm = iterator.next();
			Ini.Section s = ini.get(tm);
			String[] pokemons = s.get("POKEMON").split(",");
			for(int i = 0; i < pokemons.length; i++)
			{
				PokemonSpecies species = m_speciesData.getPokemonByName(pokemons[i]);
				if(species != null)
					for(int j = 0; j < species.getTMMoves().length; j++)
						if(species.getTMMoves()[j] == null)
						{
							species.getTMMoves()[j] = tm;
							break;
						}
			}
		}
		/* We originally gave 92 possible TMs for every Pokemon, lets trim that down */
		for(int i = 0; i < m_speciesData.getSpeciesCount(); i++)
		{
			int a = 0;
			PokemonSpecies s = m_speciesData.getSpecies()[i];
			for(int j = 0; j < s.getTMMoves().length; j++)
				if(s.getTMMoves()[j] != null)
					a++;
				else
					break;
			String[] newTMList = new String[a];
			for(int j = 0; j < newTMList.length; j++)
				if(s.getTMMoves()[j] != null)
					newTMList[j] = s.getTMMoves()[j];
			s.setTMMoves(newTMList);
		}
		/* Load Drop Data */
		try
		{
			Scanner s = new Scanner(new File("./res/itemdrops.txt"));
			String pokemon = "";
			while(s.hasNextLine())
			{
				pokemon = s.nextLine();
				DropData[] drops = new DropData[10];
				/* Parse the data in the form ITEM, PROBABILITY */
				StringTokenizer st = new StringTokenizer(pokemon);
				String pokeName = st.nextToken().toUpperCase();
				if(pokeName.equalsIgnoreCase("ARCEUS"))
					drops = new DropData[17];
				int dp = 0;
				while(st.hasMoreTokens())
				{
					int item = Integer.parseInt(st.nextToken());
					int p = Integer.parseInt(st.nextToken());
					DropData d = new DropData(item, p);
					if(dp < drops.length)
					{
						drops[dp] = d;
						dp++;
					}
				}
				if(pokeName.equalsIgnoreCase("Mr.Mime"))
					pokeName = "Mr. Mime";
				else if(pokeName.equalsIgnoreCase("MIMEJR."))
					pokeName = "Mime Jr.";
				m_speciesData.getPokemonByName(pokeName).setDropData(drops);
			}
			s.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return;
		}
	}

	/**
	 * Initialises a pokemon from an ini section
	 * 
	 * @param species
	 * @param s
	 */
	private void initialisePokemon(PokemonSpecies species, Section s, int pokemonNumber)
	{
		if(species != null)
		{
			species.setPokemonNumber(pokemonNumber);
			species.setInternalName(s.get("InternalName"));
			species.setKind(s.get("Kind"));
			species.setPokedexInfo(s.get("Pokedex"));
			species.setType1(s.get("Type1"));
			species.setType2(s.get("Type2"));
			if(species.getType2() == null)
				species.setType2("");
			PokemonType[] types;
			if(species.getType2() != null && !species.getType2().equalsIgnoreCase(""))
			{
				types = new PokemonType[2];
				types[0] = PokemonType.getType(species.getType1().toUpperCase());
				types[1] = PokemonType.getType(species.getType2().toUpperCase());
			}
			else
			{
				types = new PokemonType[1];
				types[0] = PokemonType.getType(species.getType1().toUpperCase());
			}
			species.setType(types);

			String[] stringBaseStats = s.get("BaseStats").split(",");
			for(int j = 0; j < stringBaseStats.length; j++)
				species.getBaseStats()[j] = Integer.parseInt(stringBaseStats[j]);
			species.setRareness(Integer.parseInt(s.get("Rareness")));
			species.setBaseEXP(Integer.parseInt(s.get("BaseEXP")));
			species.setHappiness(Integer.parseInt(s.get("Happiness")));
			species.setGrowthRate(ExpTypes.valueOf(s.get("GrowthRate").toUpperCase()));
			species.setStepsToHatch(Integer.parseInt(s.get("StepsToHatch")));
			species.setColor(s.get("Color"));
			species.setHabitat(s.get("Habitat"));
			if(species.getHabitat() == null)
				species.setHabitat("");
			String[] stringEffortPoints = s.get("EffortPoints").split(",");
			for(int j = 0; j < stringEffortPoints.length; j++)
				species.getEffortPoints()[j] = Integer.parseInt(stringEffortPoints[j]);
			String[] abilities = new String[2];
			if(s.get("Abilities").contains(","))
			{
				String[] temp = s.get("Abilities").split(",");
				abilities[0] = temp[0].trim();
				abilities[1] = temp[1].trim();
			}
			else
			{
				abilities = new String[1];
				abilities[0] = s.get("Abilities").trim();
			}
			species.setAbilities(abilities);
			String[] stringCompatibility = s.get("Compatibility").split(",");
			for(int j = 0; j < stringCompatibility.length; j++)
				species.getCompatibility()[j] = Integer.parseInt(stringCompatibility[j]);
			species.setHeight(Float.parseFloat(s.get("Height")));
			species.setWeight(Float.parseFloat(s.get("Weight")));
			String gender = s.get("GenderRate");
			if(gender.equalsIgnoreCase("Female50Percent"))
			{
				species.setFemalePercentage(50);
				species.setGenders(PokemonSpecies.GENDER_BOTH);
			}
			else if(gender.equalsIgnoreCase("Female75Percent"))
			{
				species.setFemalePercentage(75);
				species.setGenders(PokemonSpecies.GENDER_BOTH);
			}
			else if(gender.equalsIgnoreCase("Genderless"))
			{
				species.setFemalePercentage(-1);
				species.setGenders(PokemonSpecies.GENDER_NONE);
			}
			else if(gender.equalsIgnoreCase("AlwaysMale"))
			{
				species.setFemalePercentage(0);
				species.setGenders(PokemonSpecies.GENDER_MALE);
			}
			else if(gender.equalsIgnoreCase("AlwaysFemale"))
			{
				species.setFemalePercentage(100);
				species.setGenders(PokemonSpecies.GENDER_FEMALE);
			}
			else if(gender.equalsIgnoreCase("Female25Percent"))
			{
				species.setFemalePercentage(25);
				species.setGenders(PokemonSpecies.GENDER_BOTH);
			}
			else
			{
				/* Female one eighth */
				species.setFemalePercentage(12);
				species.setGenders(PokemonSpecies.GENDER_BOTH);
			}
			String[] stringMoves = s.get("Moves").split(",");
			species.setLevelMoves(new HashMap<Integer, String>());
			String[] startMoves = new String[4];
			int sp = 0;
			for(int j = 0; j < stringMoves.length; j++)
				if(j % 2 == 0)
				{
					int level = Integer.parseInt(stringMoves[j]);
					String move = stringMoves[j + 1].charAt(0) + stringMoves[j + 1].substring(1).toLowerCase();
					if(move.contains(" "))
					{
						// Capitalise words correctly
						String tmp = "";
						for(int i = 1; i <= move.length(); i++)
							if(i < move.length() && move.substring(i - 1, i).compareTo(" ") == 0)
							{
								tmp = tmp + " " + move.substring(i, i + 1).toUpperCase();
								i++;
							}
							else
								tmp = tmp + move.charAt(i - 1);
						move = tmp;
					}
					if(level < 2)
					{
						if(sp <= 3)
						{
							startMoves[sp] = move;
							sp++;
						}
					}
					else
						species.getLevelMoves().put(level, move);
				}
			species.setStarterMoves(startMoves);
			species.setEggMoves(s.get("EggMoves").split(","));
			String[] stringEvolutions = s.get("Evolutions").split(",");

			PokemonEvolution[] evos = new PokemonEvolution[(int) Math.ceil(stringEvolutions.length / 3.0)];
			int ep = 0;
			for(int j = 0; j < stringEvolutions.length; j = j + 3)
			{
				PokemonEvolution evo = new PokemonEvolution();
				if(stringEvolutions[j] != null && !stringEvolutions[j].equalsIgnoreCase(""))
				{
					evo.setEvolveTo(stringEvolutions[j]);
					evo.setType(EvolutionTypes.valueOf(stringEvolutions[j + 1]));
					if(evo.getType() == EvolutionTypes.Level)
						evo.setLevel(Integer.parseInt(stringEvolutions[j + 2]));
					else if(evo.getType() != EvolutionTypes.Happiness && evo.getType() != EvolutionTypes.HappinessDay && evo.getType() != EvolutionTypes.HappinessNight
							&& evo.getType() != EvolutionTypes.Trade)
						evo.setAttribute(stringEvolutions[j + 2]);
					if(ep < evos.length)
					{
						evos[ep] = evo;
						ep++;
					}
				}
			}
			species.setEvolutions(evos);
			species.setTMMoves(new String[92]);
			if(s.get("Tier") == null)
				species.setTier(0);
			else
				species.setTier(Integer.parseInt(s.get("Tier")));
		}
	}
}
