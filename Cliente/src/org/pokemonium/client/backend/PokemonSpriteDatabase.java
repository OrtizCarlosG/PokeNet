package org.pokemonium.client.backend;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import de.matthiasmann.twl.renderer.Image;
import de.matthiasmann.twl.renderer.Texture;

public class PokemonSpriteDatabase
{
	public static int MALE = 0;
	public static int FEMALE = 1;
	private static int spriteamount = 494;
	private static Image[][] front = new Image[2][spriteamount];
	private static Image[][] front_shiny = new Image[2][spriteamount];
	private static Image[][] back = new Image[2][spriteamount];
	private static Image[][] back_shiny = new Image[2][spriteamount];
	private static Image[] icons = new Image[spriteamount];
	private static String respath;
	private static ArrayList<HashMap<String, Image[]>> overworldnormal;
	private static ArrayList<HashMap<String, Image[]>> overworldshiny;
	
	private static Image[][] front_Animated = new Image[spriteamount][spriteamount];
	private static int[] front_Animated_Index = new int[spriteamount];
	
	private static Image[][] back_Animated = new Image[spriteamount][spriteamount];
	private static int[] back_Animated_Index = new int[spriteamount];
	
	public static void Init()
	{
		respath = System.getProperty("res.path");
		if(respath == null)
			respath = "";

		overworldnormal = new ArrayList<HashMap<String, Image[]>>();
		overworldshiny = new ArrayList<HashMap<String, Image[]>>();
	}

	/**
	 * Returns an array with images, based on a direction.
	 * 
	 * @param key UP, DOWN, LEFT, RIGHT
	 * @param pokenum number of the pokemon
	 * @return
	 */
	public static Image[] getOverworldNormal(String key, int pokenum)
	{
		return overworldnormal.get(pokenum).get(key);
	}
	
	/**
	 * Returns images based on a direction.
	 * 
	 * @param key UP, DOWN, LEFT, RIGHT
	 * @param pokenum number of the pokemon
	 * @return
	 */
	public static Image getOverworldNormalOne(String key, int pokenum)
	{
		int rRandom = randInt(0, overworldnormal.get(pokenum).get(key).length);
		return overworldnormal.get(pokenum).get(key)[ rRandom ];
	}
	
	/**
	 * Random between.
	 * 
	 * @param key UP, DOWN, LEFT, RIGHT
	 * @param pokenum number of the pokemon
	 * @return
	 */
	public static int randInt(int min, int max) {

	    Random rand = new Random();
	    int randomNum = rand.nextInt((max - min) + 1) + min;

	    return randomNum;
	}
	
	/**
	 * Returns an array with images, based on a direction.
	 * 
	 * @param key UP, DOWN, LEFT, RIGHT
	 * @param pokenum number of the pokemon
	 * @return
	 */
	public static Image[] getOverworldShiny(String key, int pokenum)
	{
		return overworldshiny.get(pokenum).get(key);
	}

	public static Image getIcon(int pokenum)
	{
		if(icons[pokenum] == null)
		{
			loadSpriteWithNumber(pokenum);
		}
		return icons[pokenum];
	}

	public static Image getNormalBack(int gender, int pokenum)
	{
		if(back[gender][pokenum] == null)
		{
			loadSpriteWithNumber(pokenum);
		}
		return back[gender][pokenum];
	}

	public static Image getShinyBack(int gender, int pokenum)
	{
		if(back_shiny[gender][pokenum] == null)
		{
			loadSpriteWithNumber(pokenum);
		}
		return back_shiny[gender][pokenum];
	}

	public static Image getNormalFront(int gender, int pokenum)
	{
		if(front[gender][pokenum] == null)
		{
			loadSpriteWithNumber(pokenum);
		}
		return front[gender][pokenum];
	}

	public static Image getShinyFront(int gender, int pokenum)
	{
		if(front_shiny[gender][pokenum] == null)
		{
			loadSpriteWithNumber(pokenum);
		}
		return front_shiny[gender][pokenum];
	}
	
	public static Image getAnimatedFront(int pokenum, int index)
	{
		if(front_Animated[pokenum][index] == null)
		{
			loadGif(pokenum);
		}
		return front_Animated[pokenum][index];
	}

	public static Image getAnimatedBack(int pokenum, int index)
	{
		if(back_Animated[pokenum][index] == null)
		{
			loadGif(pokenum);
		}
		return back_Animated[pokenum][index];
	}
	
	public static int getAnimatedFrontSize(int pokenum)
	{
		return front_Animated_Index[pokenum];
	}

	public static int getAnimatedBackSize(int pokenum)
	{
		return back_Animated_Index[pokenum];
	}
	
	public static void loadAllPokemonSprites()
	{
		System.out.println("Filling pokemon sprites database.");
		for(int pokenum = 1; pokenum < spriteamount; pokenum++)
		{
			if(pokenum != 29 && pokenum != 30 && pokenum != 31)
			{
				loadMaleSprites(pokenum);
			}
			if(pokenum != 32 && pokenum != 33 && pokenum != 34)
			{
				loadFemaleSprites(pokenum);
			}
			loadGif(pokenum);
			loadIcon(pokenum);
			loadSpritesheet(pokenum);
		}
		System.out.println("Done loading pokemon sprites.");
	}

	private static void loadSpriteWithNumber(int pokenum)
	{
		if(pokenum != 29 && pokenum != 30 && pokenum != 31)
		{
			loadMaleSprites(pokenum);
		}
		if(pokenum != 32 && pokenum != 33 && pokenum != 34)
		{
			loadFemaleSprites(pokenum);
		}
		loadGif(pokenum);
		loadIcon(pokenum);
		loadSpritesheet(pokenum);
	}

	private static void loadSpritesheet(int pokenum)
	{
		Texture normalsheet = FileLoader.loadImageAsTexture(respath + "res/pokemon/overworld/normal/" + pokenum + ".png");
		Texture shinysheet = FileLoader.loadImageAsTexture(respath + "res/pokemon/overworld/shiny/" + pokenum + ".png");
		int spriteWidth = 32;
		int spriteHeight = 32;
		int x = 0;
		int y = 0;

		HashMap<String, Image[]> normalmap = new HashMap<String, Image[]>();
		HashMap<String, Image[]> shinymap = new HashMap<String, Image[]>();
		Image[] normal = new Image[2];
		Image[] shiny = new Image[2];
		// UP
		for(int i = 0; i < 2; i++)
		{
			y = (i % 2) * 32;
			normal[i] = normalsheet.getImage(x, y, spriteWidth, spriteHeight, null, false, Texture.Rotation.NONE);
			shiny[i] = shinysheet.getImage(x, y, spriteWidth, spriteHeight, null, false, Texture.Rotation.NONE);
		}
		normalmap.put("UP", normal);
		shinymap.put("UP", shiny);
		normal = new Image[2];
		shiny = new Image[2];

		x = 0;
		// DOWN
		for(int i = 0; i < 2; i++)
		{
			y = 64 + (i % 2) * 32;
			normalsheet.getImage(x, y, spriteWidth, spriteHeight, null, false, Texture.Rotation.NONE);
		}
		normalmap.put("DOWN", normal);
		shinymap.put("DOWN", shiny);
		normal = new Image[2];
		shiny = new Image[2];

		x = 32;
		// LEFT
		for(int i = 0; i < 2; i++)
		{
			y = (i % 2) * 32;
			normalsheet.getImage(x, y, spriteWidth, spriteHeight, null, false, Texture.Rotation.NONE);
		}
		normalmap.put("LEFT", normal);
		shinymap.put("LEFT", shiny);
		normal = new Image[2];
		shiny = new Image[2];

		// RIGHT
		for(int i = 0; i < 2; i++)
		{
			y = 64 + (i % 2) * 32;
			normalsheet.getImage(x, y, spriteWidth, spriteHeight, null, false, Texture.Rotation.NONE);
		}
		normalmap.put("RIGHT", normal);
		shinymap.put("RIGHT", shiny);
		overworldnormal.add(normalmap);
		overworldshiny.add(shinymap);
	}

	private static void loadMaleSprites(int pokenum)
	{
		front_shiny[MALE][pokenum] = FileLoader.loadImage(respath + "res/pokemon/front/shiny/" + getSpriteIndex(pokenum) + "-3.png");
		front[MALE][pokenum] = FileLoader.loadImage(respath + "res/pokemon/front/normal/" + getSpriteIndex(pokenum) + "-3.png");
		back_shiny[MALE][pokenum] = FileLoader.loadImage(respath + "res/pokemon/back/shiny/" + getSpriteIndex(pokenum) + "-1.png");
		back[MALE][pokenum] = FileLoader.loadImage(respath + "res/pokemon/back/normal/" + getSpriteIndex(pokenum) + "-1.png");
	}

	private static void loadFemaleSprites(int pokenum)
	{
		front_shiny[FEMALE][pokenum] = FileLoader.loadImage(respath + "res/pokemon/front/shiny/" + getSpriteIndex(pokenum) + "-2.png");
		front[FEMALE][pokenum] = FileLoader.loadImage(respath + "res/pokemon/front/normal/" + getSpriteIndex(pokenum) + "-2.png");
		back_shiny[FEMALE][pokenum] = FileLoader.loadImage(respath + "res/pokemon/back/shiny/" + getSpriteIndex(pokenum) + "-0.png");
		back[FEMALE][pokenum] = FileLoader.loadImage(respath + "res/pokemon/back/normal/" + getSpriteIndex(pokenum) + "-0.png");
	}

	private static void loadIcon(int pokenum)
	{
		icons[pokenum] = FileLoader.loadImage(respath + "res/pokemon/icons/" + getSpriteIndex(pokenum) + ".png");
	}
	
	private static void loadGif(int pokenum)
	{
		try
		{
			System.out.println("Read Pokemon animated Images");
			
			// Index inicial
			int iBackIndex = 0;
			
			// Boolean to check if exist
			boolean bBackFileExist = true;
			
			do
			{
				// pokemon sprite string
				String szPokemonFile = "res/pokemon/animated/normal/back/" + getSpriteIndex(pokenum) + "/" + String.valueOf(iBackIndex) + ".png";
				
				// Check if file exist
				File FilePokemonFile = new File(szPokemonFile);
				if ( FilePokemonFile.exists() )
				{
					// Read
					back_Animated[pokenum][iBackIndex] = FileLoader.loadImage(respath + szPokemonFile);
					
					// Add to count
					iBackIndex++;
				}
				else
				{
					// Read size
					back_Animated_Index[pokenum] = iBackIndex;
					
					// Not exist
					bBackFileExist = false;
				}
			}
			while( bBackFileExist );
			
			// Index inicial
			int iFrontIndex = 0;
			
			// Boolean to check if exist
			boolean bFrontFileExist = true;
			
			do
			{
				// pokemon sprite string
				String szPokemonFile = "res/pokemon/animated/normal/front/" + getSpriteIndex(pokenum) + "/" + String.valueOf(iFrontIndex) + ".png";
				
				// Check if file exist
				File FilePokemonFile = new File(szPokemonFile);
				if ( FilePokemonFile.exists() )
				{
					// Read
					front_Animated[pokenum][iFrontIndex] = FileLoader.loadImage(respath + szPokemonFile);
					
					// Add to count
					iFrontIndex++;
				}
				else
				{
					// Read size
					front_Animated_Index[pokenum] = iFrontIndex;
					
					// Not exist
					bFrontFileExist = false;
				}
			}
			while( bFrontFileExist );
		}
		catch ( Exception ex )	{}
	}
	
	private static String getSpriteIndex(int pokenum)
	{
		String index = "";
		if(pokenum < 10)
		{
			index = "00" + String.valueOf(pokenum);
		}
		else if(pokenum < 100)
		{
			index = "0" + String.valueOf(pokenum);
		}
		else
		{
			index = String.valueOf(pokenum);
		}
		return index;
	}
}
