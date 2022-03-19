package org.pokemonium.client.backend;


import java.util.HashMap;

import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;
import org.pokemonium.client.backend.entity.Player.Direction;

/**
 * Handles overworld sprites
 * 
 * @author shinobi
 */
public class PokemonSpriteFactory
{
	private HashMap<Integer, SpriteSheet> spriteSheets;

	/**
	 * Returns the requested sprite
	 * 
	 * @param dir
	 * @param isMoving
	 * @param isLeftFoot
	 * @param sprite
	 * @return
	 */
	public Image getSprite(Direction dir, boolean isLeftFoot, int sprite)
	{
		// Check if the index is valid
		if ( sprite == 0 )
			return null;
		
		SpriteSheet sheet = spriteSheets.get(sprite);
		try
		{
			if(isLeftFoot)
			{
				switch(dir)
				{
					case Up:
						return sheet.getSprite(0, 0);
					case Down:
						return sheet.getSprite(0, 2);
					case Left:
						return sheet.getSprite(1, 0);
					case Right:
						return sheet.getSprite(1, 2);
				}
			}
			else
			{
				switch(dir)
				{
					case Up:
						return sheet.getSprite(0, 1);
					case Down:
						return sheet.getSprite(0, 3);
					case Left:
						return sheet.getSprite(1, 1);
					case Right:
						return sheet.getSprite(1, 3);
				}
			}
		}
		catch (Exception ex)
		{
			System.out.println(sprite + " x: " + ex);
		}
		
		return null;
	}

	/**
	 * Initialises the database of sprites
	 */
	public PokemonSpriteFactory()
	{

		spriteSheets = new HashMap<Integer, SpriteSheet>();
		try
		{
			String location;
			String respath = System.getProperty("res.path");
			if(respath == null)
				respath = "";
			Image temp;
			Image[] imgArray = new Image[500];
			SpriteSheet ss = null;
			/* WARNING: Change 224 to the amount of sprites we have in client
			 * the load bar only works when we don't make a new SpriteSheet ie.
			 * ss = new SpriteSheet(temp, 41, 51); needs to be commented out in
			 * order for the load bar to work. */
			for(int i = 1; i < 494; i++)
			{
				try
				{
					location = respath + "res/pokemon/overworld/normal/" + String.valueOf(i) + ".png";
					temp = new Image(location);
					imgArray[i] = temp;
					ss = new SpriteSheet(temp, 32, 32);

					spriteSheets.put(i, ss);
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public PokemonSpriteFactory(Image[] imgArray)
	{
		spriteSheets = new HashMap<Integer, SpriteSheet>();

		for(int i = 1; i < 494; i++)
		{
			spriteSheets.put(i, new SpriteSheet(imgArray[i], 32, 32));
		}
	}
}
