package org.pokemonium.server.backend.map;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Random;
import org.pokemonium.server.backend.DataLoader;
import org.pokemonium.server.backend.entity.Character;
import org.pokemonium.server.backend.entity.HMObject;
import org.pokemonium.server.backend.entity.NPC;
import org.pokemonium.server.backend.entity.Player;
import org.pokemonium.server.backend.entity.Player.Language;
import org.pokemonium.server.backend.entity.Positionable.Direction;
import org.pokemonium.server.battle.DataService;
import org.pokemonium.server.battle.Pokemon;
import org.pokemonium.server.battle.impl.NpcBattleLauncher;
import org.pokemonium.server.constants.ClientPacket;
import org.pokemonium.server.feature.TimeService;
import org.pokemonium.server.feature.TimeService.Weather;
import org.pokemonium.server.protocol.ServerMessage;
import tiled.core.Map;
import tiled.core.TileLayer;

/**
 * Represents a map in the game world
 * 
 * @author shadowkanji
 */
public class ServerMap
{
	public enum PvPType
	{
		DISABLED, ENABLED, ENFORCED
	}

	// The following stores collision information
	private ServerTileLayer m_blocked = null;
	private HashMap<String, Integer> m_dayPokemonChances;
	// The following stores information for day, night and water wild pokemon
	private HashMap<String, int[]> m_dayPokemonLevels;
	private HashMap<String, Integer> m_fishPokemonChances;
	private HashMap<String, int[]> m_fishPokemonLevels;
	private Weather m_forcedWeather = null;
	private ServerTileLayer m_grass = null;
	private int m_heigth;
	private ArrayList<MapItem> m_items = new ArrayList<MapItem>();
	private ServerTileLayer m_ledgesDown = null;
	private ServerTileLayer m_ledgesLeft = null;
	private ServerTileLayer m_ledgesRight = null;
	private ServerMapMatrix m_mapMatrix;
	private HashMap<String, Integer> m_nightPokemonChances;
	private HashMap<String, int[]> m_nightPokemonLevels;
	private ArrayList<NPC> m_npcs;
	// Players and NPCs
	private HashMap<String, Player> m_players;
	private PvPType m_pvpType = PvPType.ENABLED;
	// Misc
	private Random m_random = DataService.getBattleMechanics().getRandom();
	private ServerTileLayer m_surf = null;
	private ArrayList<WarpTile> m_warps;
	private HashMap<String, Integer> m_waterPokemonChances;
	private HashMap<String, int[]> m_waterPokemonLevels;
	// Stores the width, heigth, x, y and offsets of this map
	private int m_width;
	private int m_wildProbability;
	private int m_x;
	private int m_xOffsetModifier;
	private int m_y;
	private int m_yOffsetModifier;

	/**
	 * Default constructor
	 * 
	 * @param map
	 * @param x
	 * @param y
	 */
	public ServerMap(Map map, int x, int y)
	{
		m_x = x;
		m_y = y;
		m_heigth = map.getHeight();
		m_width = map.getWidth();
		/* Store all the map layers */
		for(int i = 0; i < map.getTotalLayers(); i++)
			if(map.getLayer(i).getName().equalsIgnoreCase("Grass"))
				m_grass = new ServerTileLayer((TileLayer) map.getLayer(i));
			else if(map.getLayer(i).getName().equalsIgnoreCase("Collisions"))
				m_blocked = new ServerTileLayer((TileLayer) map.getLayer(i));
			else if(map.getLayer(i).getName().equalsIgnoreCase("LedgesLeft"))
				m_ledgesLeft = new ServerTileLayer((TileLayer) map.getLayer(i));
			else if(map.getLayer(i).getName().equalsIgnoreCase("LedgesRight"))
				m_ledgesRight = new ServerTileLayer((TileLayer) map.getLayer(i));
			else if(map.getLayer(i).getName().equalsIgnoreCase("LedgesDown"))
				m_ledgesDown = new ServerTileLayer((TileLayer) map.getLayer(i));
			else if(map.getLayer(i).getName().equalsIgnoreCase("Water"))
				m_surf = new ServerTileLayer((TileLayer) map.getLayer(i));

		m_players = new HashMap<String, Player>();
		m_npcs = new ArrayList<NPC>();

		/* Load pvp settings */
		try
		{
			String type = map.getProperties().getProperty("pvp");
			if(type.equalsIgnoreCase("disabled"))
				m_pvpType = PvPType.DISABLED;
			else if(type.equalsIgnoreCase("ENFORCED"))
				m_pvpType = PvPType.ENFORCED;
			else
				m_pvpType = PvPType.ENABLED;
		}
		catch(Exception e)
		{
			m_pvpType = PvPType.ENABLED;
		}

		/* Add enforced weather if any */
		try
		{
			if(x < -30)
			{
				if(x != -49 || y != -3)
					m_forcedWeather = Weather.NORMAL;
				else if(x != -36 || y != -49)
					m_forcedWeather = Weather.NORMAL;
			}
			else if(map.getProperties().getProperty("forcedWeather") != null && !map.getProperties().getProperty("forcedWeather").equalsIgnoreCase(""))
				m_forcedWeather = Weather.valueOf(map.getProperties().getProperty("forcedWeather"));
		}
		catch(Exception e)
		{
			m_forcedWeather = null;
		}

		/* Load offsets */
		try
		{
			m_xOffsetModifier = Integer.parseInt(map.getProperties().getProperty("xOffsetModifier"));
		}
		catch(Exception e)
		{
			m_xOffsetModifier = 0;
		}
		try
		{
			m_yOffsetModifier = Integer.parseInt(map.getProperties().getProperty("yOffsetModifier"));
		}
		catch(Exception e)
		{
			m_yOffsetModifier = 0;
		}

		/* Load wild pokemon */
		try
		{
			if(!map.getProperties().getProperty("wildProbabilty").equalsIgnoreCase(""))
				m_wildProbability = Integer.parseInt(map.getProperties().getProperty("wildProbabilty"));
			else
				m_wildProbability = 28;
		}
		catch(Exception e)
		{
			m_wildProbability = 28;
		}

		String[] species;
		String[] levels;
		// Daytime Pokemon
		try
		{
			if(!map.getProperties().getProperty("dayPokemonChances").equalsIgnoreCase(""))
			{
				species = map.getProperties().getProperty("dayPokemonChances").split(";");
				levels = map.getProperties().getProperty("dayPokemonLevels").split(";");
				if(!species[0].equals("") && !levels[0].equals("") && species.length == levels.length)
				{
					m_dayPokemonChances = new HashMap<String, Integer>();
					m_dayPokemonLevels = new HashMap<String, int[]>();
					for(int i = 0; i < species.length; i++)
					{
						String[] speciesInfo = species[i].split(",");
						m_dayPokemonChances.put(speciesInfo[0], Integer.parseInt(speciesInfo[1]));
						String[] levelInfo = levels[i].split("-");
						m_dayPokemonLevels.put(speciesInfo[0], new int[] { Integer.parseInt(levelInfo[0]), Integer.parseInt(levelInfo[1]) });
					}
				}
			}
		}
		catch(Exception e)
		{
			m_dayPokemonChances = null;
			m_dayPokemonLevels = null;
			species = new String[] { "" };
			levels = new String[] { "" };
		}
		// Nocturnal Pokemon
		try
		{
			if(!map.getProperties().getProperty("nightPokemonChances").equalsIgnoreCase(""))
			{
				species = map.getProperties().getProperty("nightPokemonChances").split(";");
				levels = map.getProperties().getProperty("nightPokemonLevels").split(";");
				if(!species[0].equals("") && !levels[0].equals("") && species.length == levels.length)
				{
					m_nightPokemonChances = new HashMap<String, Integer>();
					m_nightPokemonLevels = new HashMap<String, int[]>();
					for(int i = 0; i < species.length; i++)
					{
						String[] speciesInfo = species[i].split(",");
						m_nightPokemonChances.put(speciesInfo[0], Integer.parseInt(speciesInfo[1]));
						String[] levelInfo = levels[i].split("-");
						m_nightPokemonLevels.put(speciesInfo[0], new int[] { Integer.parseInt(levelInfo[0]), Integer.parseInt(levelInfo[1]) });
					}
				}
			}
		}
		catch(Exception e)
		{
			m_nightPokemonChances = null;
			m_nightPokemonLevels = null;
			species = new String[] { "" };
			levels = new String[] { "" };
		}
		// Surf Pokemon
		try
		{
			if(!map.getProperties().getProperty("waterPokemonChances").equalsIgnoreCase(""))
			{
				species = map.getProperties().getProperty("waterPokemonChances").split(";");
				levels = map.getProperties().getProperty("waterPokemonLevels").split(";");
				if(!species[0].equals("") && !levels[0].equals("") && species.length == levels.length)
				{
					m_waterPokemonChances = new HashMap<String, Integer>();
					m_waterPokemonLevels = new HashMap<String, int[]>();
					for(int i = 0; i < species.length; i++)
					{
						String[] speciesInfo = species[i].split(",");
						m_waterPokemonChances.put(speciesInfo[0], Integer.parseInt(speciesInfo[1]));
						String[] levelInfo = levels[i].split("-");
						m_waterPokemonLevels.put(speciesInfo[0], new int[] { Integer.parseInt(levelInfo[0]), Integer.parseInt(levelInfo[1]) });
					}
				}
			}
		}
		catch(Exception e)
		{
			m_waterPokemonChances = null;
			m_waterPokemonLevels = null;
			species = new String[] { "" };
			levels = new String[] { "" };
		}
		// Fish Pokemon
		try
		{
			if(!map.getProperties().getProperty("fishPokemonChances").equalsIgnoreCase(""))
			{
				species = map.getProperties().getProperty("fishPokemonChances").split(";");
				levels = map.getProperties().getProperty("fishPokemonLevels").split(";");
				if(!species[0].equals("") && !levels[0].equals("") && species.length == levels.length)
				{
					m_fishPokemonChances = new HashMap<String, Integer>();
					m_fishPokemonLevels = new HashMap<String, int[]>();
					for(int i = 0; i < species.length; i++)
					{
						String[] speciesInfo = species[i].split(",");
						m_fishPokemonChances.put(speciesInfo[0], Integer.parseInt(speciesInfo[1]));
						String[] levelInfo = levels[i].split("-");
						m_fishPokemonLevels.put(speciesInfo[0], new int[] { Integer.parseInt(levelInfo[0]), Integer.parseInt(levelInfo[1]) });
					}
				}
			}
		}
		catch(Exception e)
		{
			m_fishPokemonChances = null;
			m_fishPokemonLevels = null;
			species = new String[] { "" };
			levels = new String[] { "" };
		}
	}

	/**
	 * Adds a player to this map and notifies all other clients on the map.
	 * 
	 * @param player
	 */
	public void addChar(Character c)
	{
		if(c instanceof Player)
			m_players.put(c.getName(), (Player) c);
		else if(c instanceof NPC || c instanceof HMObject)
		{
			// Set the id of the npc
			c.setId(-1 - m_npcs.size());
			m_npcs.add((NPC) c);
		}
		synchronized(m_players)
		{
			for(Player p : m_players.values())
			{
				if(c.getId() != p.getId())
				{
					String name = c.getName();
					if(c instanceof NPC)
						name = "!NPC!";
					ServerMessage message = new ServerMessage(ClientPacket.ADD_PLAYER_MAP);
					message.addString(name + "," + c.getId() + "," + c.getSprite() + "," + c.getX() + "," + c.getY() + ","
							+ (c.getFacing() == Direction.Down ? "D" : c.getFacing() == Direction.Up ? "U" : c.getFacing() == Direction.Left ? "L" : "R") + ","
							+ m_players.get(c.getName()).getAdminLevel() + "," + c.getFollowPokemonID() + "," + c.getPokemonLastX() + "," + c.getPokemonLastY() + ",");
					p.getSession().Send(message);
				}
			}
		}
	}
	
	/**
	 * Adds a char and sets their x y based on a 32 by 32 pixel grid. Allows easier adding of NPCs as the x,y can easily be counted via Tiled
	 * 
	 * @param c
	 * @param tileX
	 * @param tileY
	 */
	public void addChar(Character c, int tileX, int tileY)
	{
		this.addChar(c);
		c.setX(tileX * 32);
		c.setY(tileY * 32 - 8);
	}

	/**
	 * Adds an item to the map
	 * 
	 * @param x
	 * @param y
	 * @param id
	 */
	public void addItem(int x, int y, int id)
	{
		m_items.add(new MapItem(x, y, id));
	}

	/**
	 * Adds a warp tile to the map
	 * 
	 * @param w
	 */
	public void addWarp(WarpTile w)
	{
		if(m_warps == null)
			m_warps = new ArrayList<WarpTile>();
		m_warps.add(w);
	}
	
	/**
	 * Update player pokemon follow.
	 * 
	 * @param player
	 */
	public void updatePokemonFollowChar(Character c)
	{
		synchronized(m_players)
		{
			for(Player p : m_players.values())
			{
				if(c.getId() != p.getId())
				{
					ServerMessage message = new ServerMessage(ClientPacket.PLAYER_UPDATE_POKEMONFOLLOW);
					message.addString(c.getId() + "," + c.getFollowPokemonID());
					p.getSession().Send(message);
				}
			}
		}
	}

	/**
	 * Returns true if a fishing attempt was deemed successful(Will the player pull up any pogey or find nothing?)
	 * 
	 * @param player
	 * @param direction
	 * @param rod
	 */
	public boolean caughtFish(Player player, Direction direction, int rod)
	{
		int failureRate = 75;
		// Subtract the rod's power from the failure rate.
		failureRate -= rod;
		// If that tile is a water tile, determine if you pulled anything, if not, autofail(You can't fish on dry land)
		if(facingWater(player, direction))
		{
			player.setFishing(true);
			if((int) (Math.random() * 101) > failureRate)
				return true;
			else
				return false;
		}
		else
		{
			/* Tell the player he can't fish on land. */
			ServerMessage message = new ServerMessage(ClientPacket.CANT_FISH_LAND);
			player.getSession().Send(message);
		}
		return false;
	}

	/**
	 * Returns true if the player is facing water
	 * 
	 * @param c
	 * @param newX
	 * @param newY
	 * @return
	 */
	public boolean facingWater(Player c, Direction d)
	{
		int playerX = c.getX();
		int playerY = c.getY();
		int newX = 0;
		int newY = 0;
		// Determine what tile the player is facing
		switch(d)
		{
			case Up:
				newX = playerX / 32;
				newY = (playerY + 8 - 32) / 32;
				break;
			case Down:
				newX = playerX / 32;
				newY = (playerY + 8 + 32) / 32;
				break;
			case Left:
				newX = (playerX - 32) / 32;
				newY = (playerY + 8) / 32;
				break;
			case Right:
				newX = (playerX + 32) / 32;
				newY = (playerY + 8) / 32;
				break;
		}
		if(m_surf != null && m_surf.getTileAt(newX, newY) == '1')
			return true;
		return false;
	}

	/**
	 * Returns the height of this map
	 * 
	 * @return
	 */
	public int getHeight()
	{
		return m_heigth;
	}

	/**
	 * Returns the arraylist of npcs
	 * 
	 * @return
	 */
	public ArrayList<NPC> getNpcs()
	{
		return m_npcs;
	}

	/**
	 * Returns the arraylist of players
	 * 
	 * @return
	 */
	public HashMap<String, Player> getPlayers()
	{
		return m_players;
	}

	/**
	 * Returns the pvp type of the map
	 * 
	 * @return
	 */
	public PvPType getPvPType()
	{
		return m_pvpType;
	}

	/**
	 * Returns the enforced weather on this map
	 * 
	 * @return
	 */
	public Weather getWeather()
	{
		return m_forcedWeather;
	}

	/**
	 * Returns the weather id for the enforced weather on this map
	 * 
	 * @return
	 */
	public int getWeatherId()
	{
		if(m_forcedWeather != null)
			switch(m_forcedWeather)
			{
				case NORMAL:
					return 0;
				case RAIN:
					return 1;
				case HAIL:
					return 2;
				case SANDSTORM:
					return 3;
				case FOG:
					return 4;
				default:
					return 0;
			}
		else
			return 0;
	}

	/**
	 * Returns the width of this map
	 * 
	 * @return
	 */
	public int getWidth()
	{
		return m_width;
	}

	/**
	 * Returns a wild pokemon. Different players have different chances of encountering rarer Pokemon.
	 * 
	 * @return
	 */
	public Pokemon getWildPokemon(Player player)
	{
		int[] range;
		String species;
		if(player.isSurfing())
		{
			// Generate a Pokemon from the water
			species = getWildSpeciesWater();
			range = m_waterPokemonLevels.get(species);
			return Pokemon.getRandomPokemon(species, m_random.nextInt(range[1] - range[0] + 1) + range[0]);
		}
		else if(player.isFishing())
		{
			// Generate a pokemon caught by fishing
			species = getWildSpeciesFish();
			range = m_fishPokemonLevels.get(species);
			return Pokemon.getRandomPokemon(species, m_random.nextInt(range[1] - range[0] + 1) + range[0]);
		}
		else if(TimeService.isNight())
		{
			// Generate a nocturnal Pokemon
			species = getWildSpeciesNight();
			range = m_nightPokemonLevels.get(species);
			return Pokemon.getRandomPokemon(species, m_random.nextInt(range[1] - range[0] + 1) + range[0]);
		}
		else
		{
			// Generate a day Pokemon
			species = getWildSpeciesDay();
			range = m_dayPokemonLevels.get(species);
			return Pokemon.getRandomPokemon(species, m_random.nextInt(range[1] - range[0] + 1) + range[0]);
		}
	}

	/**
	 * Returns the x co-ordinate of this servermap in the map matrix
	 * 
	 * @return
	 */
	public int getX()
	{
		return m_x;
	}

	/**
	 * Returns the x offset of this map
	 * 
	 * @return
	 */
	public int getXOffsetModifier()
	{
		return m_xOffsetModifier;
	}

	/**
	 * Returns the y co-ordinate of this servermap in the map matrix
	 * 
	 * @return
	 */
	public int getY()
	{
		return m_y;
	}

	/**
	 * Returns the y offset of this map
	 * 
	 * @return
	 */
	public int getYOffsetModifier()
	{
		return m_yOffsetModifier;
	}

	/**
	 * Starts an npc battle with the player if the player was challenged
	 * 
	 * @param player
	 * @return
	 */
	public boolean isNpcBattle(Player player)
	{
		NPC npc = null;
		for(int i = 0; i < m_npcs.size(); i++)
		{
			npc = m_npcs.get(i);
			if(npc != null && npc.isTrainer() && !npc.isGymLeader())
			{
				/* For the npc to be able to challenge the player, the must be on the same axis as the player, the x axis or the y axis */
				if(npc.getX() == player.getX())
				{
					/* Same column */
					if(npc.getY() > player.getY())
					{
						/* NPC is above the player */
						if(npc.getFacing() == Direction.Up && npc.canSee(player))
						{
							NpcBattleLauncher l = new NpcBattleLauncher(npc, player);
							l.start();
							return true;
						}
					}
					else if(npc.getFacing() == Direction.Down && npc.canSee(player))
					{
						NpcBattleLauncher l = new NpcBattleLauncher(npc, player);
						l.start();
						return true;
					}
				}
				else if(npc.getY() == player.getY())
				{
					/* Same row. Check if the NPC is to the right of the player, or the left. */
					if(npc.getX() > player.getX())
					{
						if(npc.getFacing() == Direction.Left && npc.canSee(player))
						{
							NpcBattleLauncher l = new NpcBattleLauncher(npc, player);
							l.start();
							return true;
						}
					}
					else if(npc.getFacing() == Direction.Right && npc.canSee(player))
					{
						NpcBattleLauncher l = new NpcBattleLauncher(npc, player);
						l.start();
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * Returns true if this map has a forced weather
	 * 
	 * @return
	 */
	public boolean isWeatherForced()
	{
		return m_forcedWeather != null;
	}

	/**
	 * Returns true if a wild pokemon was encountered.
	 * 
	 * @return
	 */
	public boolean isWildBattle(int x, int y, Player p)
	{
		if(m_random.nextInt(2874) < m_wildProbability * 16)
			if(p.isSurfing())
			{
				if(m_waterPokemonChances != null && m_waterPokemonLevels != null)
					return true;
			}
			else if(m_grass != null && m_grass.getTileAt(x / 32, (y + 8) / 32) == '1')
				if(m_dayPokemonChances != null && m_dayPokemonLevels != null || m_nightPokemonChances != null && m_nightPokemonLevels != null)
					return true;
		return false;
	}

	/**
	 * Loads all npc and warp tile data
	 */
	public void loadData()
	{
		/* Load all npcs and warptiles */
		File f = new File("res/npc/" + m_x + "." + m_y + ".txt");
		if(f.exists())
			try
			{
				@SuppressWarnings("unused")
				DataLoader d = new DataLoader(f, this);
			}
			catch(Exception e)
			{

			}
	}

	/**
	 * Returns true if the char is able to move
	 * 
	 * @param c
	 * @param d
	 */
	public boolean moveChar(Character c, Direction d)
	{
		int playerX = c.getX();
		int playerY = c.getY();
		int newX;
		int newY;

		switch(d)
		{
			case Up:
				newX = playerX / 32;
				newY = (playerY + 8 - 32) / 32;
				if(playerY >= 1)
				{
					if(!isBlocked(newX, newY, Direction.Up))
						if(m_surf != null && m_surf.getTileAt(newX, newY) == '1')
						{
							if(c.isSurfing())
								return true;
							else if(c instanceof Player)
							{
								Player p = (Player) c;
								if(p.canSurf())
								{
									p.setSurfing(true);
									return true;
								}
								else
									return false;
							}
						}
						else
						{
							if(c.isSurfing())
								c.setSurfing(false);
							if(!isWarped(newX, newY, c))
								return true;
						}
				}
				else
				{
					ServerMap newMap = m_mapMatrix.getMapByGamePosition(m_x, m_y - 1);
					if(newMap != null)
						m_mapMatrix.moveBetweenMaps(c, this, newMap);
				}
				break;
			case Down:
				newX = playerX / 32;
				newY = (playerY + 8 + 32) / 32;
				if(playerY + 40 < m_heigth * 32)
				{
					if(!isBlocked(newX, newY, Direction.Down))
						if(m_surf != null && m_surf.getTileAt(newX, newY) == '1')
						{
							if(c.isSurfing())
								return true;
							else if(c instanceof Player)
							{
								Player p = (Player) c;
								if(p.canSurf())
								{
									p.setSurfing(true);
									return true;
								}
								else
									return false;
							}
						}
						else
						{
							if(c.isSurfing())
								c.setSurfing(false);
							if(!isWarped(newX, newY, c))
								return true;
						}
				}
				else
				{
					ServerMap newMap = m_mapMatrix.getMapByGamePosition(m_x, m_y + 1);
					if(newMap != null)
						m_mapMatrix.moveBetweenMaps(c, this, newMap);
				}
				break;
			case Left:
				newX = (playerX - 32) / 32;
				newY = (playerY + 8) / 32;
				if(playerX >= 32)
				{
					if(!isBlocked(newX, newY, Direction.Left))
						if(m_surf != null && m_surf.getTileAt(newX, newY) == '1')
						{
							if(c.isSurfing())
								return true;
							else if(c instanceof Player)
							{
								Player p = (Player) c;
								if(p.canSurf())
								{
									p.setSurfing(true);
									return true;
								}
								else
									return false;
							}
						}
						else
						{
							if(c.isSurfing())
								c.setSurfing(false);
							if(!isWarped(newX, newY, c))
								return true;
						}
				}
				else
				{
					ServerMap newMap = m_mapMatrix.getMapByGamePosition(m_x - 1, m_y);
					if(newMap != null)
						m_mapMatrix.moveBetweenMaps(c, this, newMap);
				}
				break;
			case Right:
				newX = (playerX + 32) / 32;
				newY = (playerY + 8) / 32;
				if(playerX + 32 < m_width * 32)
				{
					if(!isBlocked(newX, newY, Direction.Right))
						if(m_surf != null && m_surf.getTileAt(newX, newY) == '1')
						{
							if(c.isSurfing())
								return true;
							else if(c instanceof Player)
							{
								Player p = (Player) c;
								if(p.canSurf())
								{
									p.setSurfing(true);
									return true;
								}
								else
									return false;
							}
						}
						else
						{
							if(c.isSurfing())
								c.setSurfing(false);
							if(!isWarped(newX, newY, c))
								return true;
						}
				}
				else
				{
					ServerMap newMap = m_mapMatrix.getMapByGamePosition(m_x + 1, m_y);
					if(newMap != null)
						m_mapMatrix.moveBetweenMaps(c, this, newMap);
				}
				break;
		}
		return false;
	}

	/**
	 * Allows a player to pick up an item
	 * 
	 * @param p
	 */
	public void pickupItem(Player p)
	{

	}

	/**
	 * Removes a char from this map
	 * 
	 * @param c
	 */
	public void removeChar(Character c)
	{
		if(c instanceof Player)
			synchronized(m_players)
			{
				m_players.remove(c.getName());
			}
		else if(c instanceof NPC)
		{
			m_npcs.remove(c);
			m_npcs.trimToSize();
		}
		synchronized(m_players)
		{
			for(Player p : m_players.values())
			{
				ServerMessage message = new ServerMessage(ClientPacket.REMOVE_PLAYER_MAP);
				message.addInt(c.getId());
				p.getSession().Send(message);
			}
		}
	}

	/**
	 * Removes forced weather
	 */
	public void removeWeather()
	{
		m_forcedWeather = null;
	}

	/**
	 * Sends a chat message to everyone of the same language
	 * 
	 * @param message
	 * @param l
	 */
	public void sendChatMessage(String message, Language l)
	{
		synchronized(m_players)
		{
			Collection<Player> list = m_players.values();
			for(Player p : list)
				if(p.getLanguage() == l)
				{
					ServerMessage chatMessage = new ServerMessage(p.getSession());
					chatMessage.init(ClientPacket.CHAT_PACKET.getValue());
					chatMessage.addInt(0);
					chatMessage.addString(message);
					chatMessage.sendResponse();
				}
		}
	}

	/**
	 * Sends a movement packet to everyone
	 * 
	 * @param moveMessage
	 * @param char1
	 */
	public void sendMovementToAll(Direction d, Character c)
	{
		if(c instanceof Player)
		{
			/* If a player, send movement to everyone but themselves Movement for themself is sent over TCP */
			Player p = (Player) c;
			synchronized(m_players)
			{
				Collection<Player> list = m_players.values();
				for(Player pl : list)
					if(p != pl)
						pl.queueOtherPlayerMovement(d, c.getId());
			}
		}
		else
			/* Else, send the movement to everyone */
			synchronized(m_players)
			{
				Collection<Player> list = m_players.values();
				for(Player pl : list)
					pl.queueOtherPlayerMovement(d, c.getId());
			}
	}

	/**
	 * Sends a packet to all players on the map
	 * 
	 * @param message
	 */
	public void sendToAll(ServerMessage m)
	{
		synchronized(m_players)
		{
			Collection<Player> list = m_players.values();
			for(Player p : list)
				// TcpProtocolHandler.writeMessage(p.getTcpSession(), m);
				p.getSession().Send(m);
		}
	}

	/**
	 * Sets the map matrix
	 * 
	 * @param matrix
	 */
	public void setMapMatrix(ServerMapMatrix matrix)
	{
		m_mapMatrix = matrix;
	}

	/**
	 * Sets forced weather
	 * 
	 * @param w
	 */
	public void setWeather(Weather w)
	{
		m_forcedWeather = w;
	}

	/**
	 * Allows a player to talk to the npc in front of them, if any
	 * 
	 * @param p
	 */
	public void talkToNpc(Player p)
	{
		int x = 0, y = 0;
		switch(p.getFacing())
		{
			case Up:
				x = p.getX();
				y = p.getY() - 32;
				break;
			case Down:
				x = p.getX();
				y = p.getY() + 32;
				break;
			case Left:
				x = p.getX() - 32;
				y = p.getY();
				break;
			case Right:
				x = p.getX() + 32;
				y = p.getY();
				break;
			default:
				break;
		}
		for(int i = 0; i < m_npcs.size(); i++)
			if(m_npcs.get(i).getX() == x && m_npcs.get(i).getY() == y)
			{
				if(!(m_npcs.get(i) instanceof HMObject))
					p.setTalking(true);
				m_npcs.get(i).talkToPlayer(p);
				break;
			}
	}

	/**
	 * Returns a wild species for day
	 * 
	 * @return
	 */
	private String getWildSpeciesDay()
	{
		ArrayList<String> potentialSpecies = new ArrayList<String>();
		do
			for(String species : m_dayPokemonChances.keySet())
				if(m_random.nextInt(101) < m_dayPokemonChances.get(species))
					potentialSpecies.add(species);
		while(potentialSpecies.size() <= 0);
		return potentialSpecies.get(m_random.nextInt(potentialSpecies.size()));
	}

	/**
	 * Returns a wild species for fishing
	 * 
	 * @return
	 */
	private String getWildSpeciesFish()
	{
		ArrayList<String> potentialSpecies = new ArrayList<String>();
		do
			for(String species : m_fishPokemonChances.keySet())
				if(m_random.nextInt(101) < m_fishPokemonChances.get(species))
					potentialSpecies.add(species);
		while(potentialSpecies.size() <= 0);
		return potentialSpecies.get(m_random.nextInt(potentialSpecies.size()));
	}

	/**
	 * Returns a wild species for night
	 * 
	 * @return
	 */
	private String getWildSpeciesNight()
	{
		ArrayList<String> potentialSpecies = new ArrayList<String>();
		do
			for(String species : m_nightPokemonChances.keySet())
				if(m_random.nextInt(101) < m_nightPokemonChances.get(species))
					potentialSpecies.add(species);
		while(potentialSpecies.size() <= 0);
		return potentialSpecies.get(m_random.nextInt(potentialSpecies.size()));
	}

	/**
	 * Returns a wild species for water
	 * 
	 * @return
	 */
	private String getWildSpeciesWater()
	{
		ArrayList<String> potentialSpecies = new ArrayList<String>();
		do
			for(String species : m_waterPokemonChances.keySet())
				if(m_random.nextInt(101) < m_waterPokemonChances.get(species))
					potentialSpecies.add(species);
		while(potentialSpecies.size() <= 0);
		return potentialSpecies.get(m_random.nextInt(potentialSpecies.size()));
	}

	/**
	 * Returns true if there is an obstacle
	 * 
	 * @param x
	 * @param y
	 * @param d
	 * @return
	 */
	private boolean isBlocked(int x, int y, Direction d)
	{
		if(m_blocked.getTileAt(x, y) == '1')
			return true;
		if(m_npcs.size() < 4)
		{
			for(int i = 0; i < m_npcs.size(); i++)
				if(m_npcs.get(i).getX() == x * 32 && m_npcs.get(i).getY() == y * 32 - 8)
					return true;
		}
		else
			for(int i = 0; i <= m_npcs.size() / 2; i++)
				if(m_npcs.get(i).getX() == x * 32 && m_npcs.get(i).getY() == y * 32 - 8)
					return true;
				else if(m_npcs.get(m_npcs.size() - 1 - i).getX() == x * 32 && m_npcs.get(m_npcs.size() - 1 - i).getY() == y * 32 - 8)
					return true;
		if(m_ledgesRight != null && m_ledgesRight.getTileAt(x, y) == '1')
			if(d != Direction.Right)
				return true;
		if(m_ledgesLeft != null && m_ledgesLeft.getTileAt(x, y) == '1')
			if(d != Direction.Left)
				return true;
		if(m_ledgesDown != null && m_ledgesDown.getTileAt(x, y) == '1')
			if(d != Direction.Down)
				return true;
		return false;
	}

	/**
	 * Returns true if the char was warped
	 * 
	 * @param x
	 * @param y
	 * @param c
	 * @return
	 */
	private boolean isWarped(int x, int y, Character c)
	{
		if(m_warps != null)
			for(int i = 0; i < m_warps.size(); i++)
				if(m_warps.get(i).getX() == x && m_warps.get(i).getY() == y)
				{
					m_warps.get(i).warp(c);
					return true;
				}
		return false;
	}
}
