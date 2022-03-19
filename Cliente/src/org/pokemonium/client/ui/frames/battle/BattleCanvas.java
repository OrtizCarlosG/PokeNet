package org.pokemonium.client.ui.frames.battle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import org.lwjgl.util.Timer;
import org.newdawn.slick.Color;
import org.pokemonium.client.GameClient;
import org.pokemonium.client.backend.BattleManager;
import org.pokemonium.client.backend.PokemonSpriteDatabase;
import org.pokemonium.client.ui.components.Image;
import org.pokemonium.client.ui.components.ProgressBar;

import de.matthiasmann.twl.Label;
import de.matthiasmann.twl.Widget;

/**
 * Canvas for drawing the battle and it's animations. This is the topside of the battleFrame
 * 
 * @author Myth1c
 */
public class BattleCanvas extends Widget
{
	// Images
	public Image enemyDataBG;
	private Image enemyHPBar;
	private Image playerDataBG;
	private Image playerHPBar;

	private Image genderFemale;
	private Image genderMale;
	
	public Image attackEffectPlayer;
	public Image attackEffectEnemy;
	
	public Image enemyGender;
	private Image playerGender;

	private Image enemyStatus;
	private Image playerStatus;

	private Image enemyPokeSprite;
	private Image playerPokeSprite;
	
	private boolean DoPlayerAnimation;
	private double fPlayerTimerEffect;
	private boolean DoPlayerRight;
	private double fPlayerTimer;
	
	private boolean DoEnemysAnimation;
	private double fEnemyTimerEffect;
	private boolean DoEnemyRight;
	private double fEnemyTimer;
	
	private ProgressBar enemyHP;

	public Label enemyLv;
	public Label enemyNameLabel;

	private ArrayList<Image> m_enemyPokeballs = new ArrayList<Image>();
	// Image Loading stuff
	private String m_path = "res/battle/";
	private HashMap<String, de.matthiasmann.twl.renderer.Image> m_pokeballIcons = new HashMap<String, de.matthiasmann.twl.renderer.Image>();
	private HashMap<String, de.matthiasmann.twl.renderer.Image> m_statusIcons = new HashMap<String, de.matthiasmann.twl.renderer.Image>();
	private Timer mTimer = new Timer();

	private ProgressBar playerHP;

	private Label playerLv;
	private Label playerNameLabel;
	private ProgressBar playerXP, playerXPEND;

	private de.matthiasmann.twl.renderer.Image hp_high = GameClient.getInstance().getTheme().getImage("hpbar_high");
	private de.matthiasmann.twl.renderer.Image hp_middle = GameClient.getInstance().getTheme().getImage("hpbar_middle");
	private de.matthiasmann.twl.renderer.Image hp_low = GameClient.getInstance().getTheme().getImage("hpbar_low");
	
	private int iBackIndex = 0;
	private int iFrontIndex = 0;
	private boolean bCanAnimate = false;
	
	/**
	 * Default constructor
	 */
	public BattleCanvas()
	{
		String respath = System.getProperty("res.path");
		if(respath == null)
			respath = "";
		m_path = respath + m_path;
		setVisible(true);
		initComponents();
	}

	/**
	 * Initialises the components for the battlecanvas
	 */
	private void initComponents()
	{
		loadImages();
		initPlayerComponents();
		initEnemyComponents();
		setClip(true);
	}

	/**
	 * Initialises the components for the enemy
	 */
	private void initEnemyComponents()
	{
		enemyGender = new Image();
		enemyStatus = new Image();
		enemyPokeSprite = new Image();
		enemyLv = new Label();
		enemyLv.setTheme("label_enemy_level");
		enemyNameLabel = new Label();
		enemyNameLabel.setTheme("label_enemyname");
		enemyHP = new ProgressBar(0, 0);
		add(enemyDataBG);
		add(enemyStatus);
		add(enemyLv);
		add(enemyNameLabel);
		add(enemyHP);
		add(enemyHPBar);
		add(enemyPokeSprite);
		add(attackEffectEnemy);
		initEnemyPokeballs();
	}

	/**
	 * Initialises the components for the player
	 */
	private void initPlayerComponents()
	{
		playerGender = new Image();
		playerStatus = new Image();
		playerPokeSprite = new Image();
		playerLv = new Label();
		playerLv.setTheme("label_playerlevel");
		playerNameLabel = new Label();
		playerNameLabel.setTheme("label_playername");
		playerHP = new ProgressBar(0, 0);
		playerXP = new ProgressBar(0, 0, true);
		playerXP.setTheme("expbar");
		playerXPEND = new ProgressBar(0, 0, true);
		playerXPEND.setTheme("expbar");
		add(playerDataBG);
		add(playerGender);
		add(playerStatus);
		add(playerLv);
		add(playerNameLabel);
		add(playerHP);
		add(playerXP);
		add(playerHPBar);
		add(playerXPEND);
		add(playerPokeSprite);
		add(attackEffectPlayer);
	}
	
	/**
	 * Initialises the components for the pokemon animation
	 */
	public void updatePokemonImage()
	{
		if ( BattleManager.getInstance().getCurPoke() != null && bCanAnimate == true )
		{
			// Apply next image
			try
			{
				// Mostrar que aplicou
				int iPokemonBackIndex = BattleManager.getInstance().getCurPoke().getSpriteNumber();
					
				// Buffered image
				playerPokeSprite.setImage(PokemonSpriteDatabase.getAnimatedBack(iPokemonBackIndex, iBackIndex));
					
				// check if can add to count
				iBackIndex = ( (iBackIndex + 1) >= PokemonSpriteDatabase.getAnimatedBackSize(iPokemonBackIndex) ) ? 0 : (iBackIndex + 1);
				
				// Mostrar que aplicou
				int iPokemonFrontIndex = BattleManager.getInstance().getCurEnemyPoke().getSpriteNumber();
					
				// Buffered image
				enemyPokeSprite.setImage(PokemonSpriteDatabase.getAnimatedFront(iPokemonFrontIndex, iFrontIndex));
					
				// check if can add to count
				iFrontIndex = ( (iFrontIndex + 1) >= PokemonSpriteDatabase.getAnimatedFrontSize(iPokemonFrontIndex) ) ? 0 : (iFrontIndex + 1);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println(e.toString());
			}
		}
	}
	
	/**
	 * Initializes the pokeballs for trainer battles with all empty pokeballs.
	 */
	private void initEnemyPokeballs()
	{
		m_enemyPokeballs = new ArrayList<>();
		for(int i = 0; i < 6; i++)
		{
			Image img = new Image(m_pokeballIcons.get("empty"));
			add(img);
			m_enemyPokeballs.add(img);
		}
	}

	/**
	 * Loads images from the theme.
	 */
	private void loadImages()
	{
		enemyDataBG = new Image(GameClient.getInstance().getTheme().getImage("battle_playerdata_background"));
		playerDataBG = new Image(GameClient.getInstance().getTheme().getImage("battle_enemydata_background"));

		enemyHPBar = new Image(GameClient.getInstance().getTheme().getImage("enemyhp"));
		playerHPBar = new Image(GameClient.getInstance().getTheme().getImage("playerhp"));
		// Gender Image Load
		genderFemale = new Image(GameClient.getInstance().getTheme().getImage("gender_female"));
		genderMale = new Image(GameClient.getInstance().getTheme().getImage("gender_male"));
		
		// Attack effect
		attackEffectPlayer = new Image(GameClient.getInstance().getTheme().getImage("attack_basic_effect"));
		attackEffectEnemy = new Image(GameClient.getInstance().getTheme().getImage("attack_basic_effect"));
		
		// Invisible
		attackEffectPlayer.setVisible(false);
		attackEffectEnemy.setVisible(false);
		
		m_pokeballIcons.put("empty", GameClient.getInstance().getTheme().getImage("pokeball_empty"));
		m_pokeballIcons.put("normal", GameClient.getInstance().getTheme().getImage("pokeball_normal"));
		m_pokeballIcons.put("status", GameClient.getInstance().getTheme().getImage("pokeball_status"));
		m_pokeballIcons.put("fainted", GameClient.getInstance().getTheme().getImage("pokeball_fainted"));
	}

	/**
	 * Draw our enemy poke's information
	 */
	public void setEnemyInfo()
	{
		// display enemy's data
		enemyNameLabel.setText(BattleManager.getInstance().getCurEnemyPoke().getName());

		if(BattleManager.getInstance().getCurEnemyPoke().getGender() == 0)
		{
			enemyGender = genderFemale;
		}
		else if(BattleManager.getInstance().getCurEnemyPoke().getGender() == 1)
		{
			enemyGender = genderMale;
		}

		enemyLv.setText("Lv: " + BattleManager.getInstance().getCurEnemyPoke().getLevel());
		setEnemyPokeSprite();
	}

	/**
	 * Sets the enemy's Pokemon sprite
	 */
	public void setEnemyPokeSprite()
	{
		GameClient.getInstance().getGUI().invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				if(BattleManager.getInstance().getCurEnemyPoke().isShiny())
				{
					// We dont have shiny gifs to use, so use normal
					//enemyPokeSprite.setImage(PokemonSpriteDatabase.getShinyFront(BattleManager.getInstance().getCurEnemyPoke().getGender(), BattleManager.getInstance().getCurEnemyPoke().getSpriteNumber()));
					enemyPokeSprite.setImage(PokemonSpriteDatabase.getAnimatedFront(BattleManager.getInstance().getCurEnemyPoke().getSpriteNumber(), iFrontIndex));
				}
				else
				{
					//enemyPokeSprite.setImage(PokemonSpriteDatabase.getNormalFront(BattleManager.getInstance().getCurEnemyPoke().getGender(), BattleManager.getInstance().getCurEnemyPoke().getSpriteNumber()));
					enemyPokeSprite.setImage(PokemonSpriteDatabase.getAnimatedFront(BattleManager.getInstance().getCurEnemyPoke().getSpriteNumber(), iFrontIndex));
				}
			}
		});
	}

	/**
	 * Draw our poke's information
	 */
	public void setPlayerInfo()
	{
		// display player's data
		playerNameLabel.setText(BattleManager.getInstance().getCurPoke().getName());

		if(BattleManager.getInstance().getCurPoke().getGender() == 0)
		{
			playerGender = genderFemale;
		}
		else if(BattleManager.getInstance().getCurPoke().getGender() == 1)
		{
			playerGender = genderMale;
		}

		playerLv.setText("Lv:" + BattleManager.getInstance().getCurPoke().getLevel());
		setPlayerPokemonSprite();
	}

	/**
	 * Draws our Pokemon
	 */
	public void setPlayerPokemonSprite()
	{
		GameClient.getInstance().getGUI().invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				// Init fight
				bCanAnimate = true;
				
				// Animated
				updatePokemonImage();
				
				// Check shiny
				if(BattleManager.getInstance().getCurPoke().isShiny())
				{
					// We dont have shiny gifs to use, so use normal
					//playerPokeSprite.setImage(PokemonSpriteDatabase.getShinyBack(BattleManager.getInstance().getCurPoke().getGender(), BattleManager.getInstance().getCurPoke().getSpriteNumber()));
					playerPokeSprite.setImage(PokemonSpriteDatabase.getAnimatedBack(BattleManager.getInstance().getCurPoke().getSpriteNumber(), 0));
				}
				else
				{
					//playerPokeSprite.setImage(PokemonSpriteDatabase.getNormalBack(BattleManager.getInstance().getCurPoke().getGender(), BattleManager.getInstance().getCurPoke().getSpriteNumber()));// FileLoader.toTWLImage(BattleManager.getInstance().getCurPoke().getBackSprite()));
					playerPokeSprite.setImage(PokemonSpriteDatabase.getAnimatedBack(BattleManager.getInstance().getCurPoke().getSpriteNumber(), 0));
				}
			}
		});
	}

	/**
	 * Starts the enemy HP Bar
	 */
	public void initEnemyHPBar()
	{
		// show enemy hp bar
		enemyHP.setMinimum(0);
		enemyHP.setMaximum(BattleManager.getInstance().getCurEnemyPoke().getMaxHP());
		enemyHP.setValue(BattleManager.getInstance().getCurEnemyPoke().getCurHP());
		updateEnemyHPBarColor();
	}

	/**
	 * Starts the player's HP bar
	 */
	public void initPlayerHPBar()
	{
		// show hp bar
		playerHP.setMinimum(0);
		playerHP.setMaximum(BattleManager.getInstance().getCurPoke().getMaxHP());
		playerHP.setValue(BattleManager.getInstance().getCurPoke().getCurHP());
		updatePlayerHPBarColor();
	}

	/**
	 * Starts the player's XP bar
	 */
	public void initPlayerXPBar()
	{
		float max = BattleManager.getInstance().getCurPoke().getExpLvlUp();
		float min = BattleManager.getInstance().getCurPoke().getExpLvl();
		// float val = BattleManager.getInstance().getCurPoke().getExp();
		float testVal = (max - min) / 101;
		int xpMax = (int) (max - testVal * 1.0);
		int xpENDMin = (int) (max - testVal * 8.0);

		playerXP.setMinimum(BattleManager.getInstance().getCurPoke().getExpLvl());
		playerXP.setMaximum(xpMax);
		playerXPEND.setMinimum(xpENDMin);
		playerXPEND.setMaximum((int) max);
		updatePlayerXPValue(BattleManager.getInstance().getCurPoke().getExp());
	}

	/**
	 * Loads the status icons
	 */
	public void loadStatusIcons()
	{
		m_statusIcons.put("poison", GameClient.getInstance().getTheme().getImage("status_psn"));
		m_statusIcons.put("sleep", GameClient.getInstance().getTheme().getImage("status_slp"));
		m_statusIcons.put("freeze", GameClient.getInstance().getTheme().getImage("status_frz"));
		m_statusIcons.put("burn", GameClient.getInstance().getTheme().getImage("status_brn"));
		m_statusIcons.put("paralysis", GameClient.getInstance().getTheme().getImage("status_par"));
	}

	/**
	 * Sets the image for the pokeballs
	 * 
	 * @param i
	 * @param key
	 */
	public void setEnemyPokeballImage(int i, String key)
	{
		m_enemyPokeballs.get(i).setImage(m_pokeballIcons.get(key));
	}

	/**
	 * Shows pokeballs
	 */
	public void showPokeballs()
	{
		for(Image img : m_enemyPokeballs)
		{
			img.setVisible(true);
		}
	}

	public void hidePokeballs()
	{
		for(Image img : m_enemyPokeballs)
		{
			img.setVisible(false);
		}
	}

	/**
	 * Sets the status image
	 * 
	 * @param trainer
	 * @param status
	 */
	public void setStatus(int trainer, String status)
	{
		if(trainer == 0)
		{
			// The player's pokemon
			if(status.equalsIgnoreCase("poison") || status.equalsIgnoreCase("freeze") || status.equalsIgnoreCase("burn") || status.equalsIgnoreCase("paralysis") || status.equalsIgnoreCase("sleep"))
			{
				BattleManager.getInstance().getOurStatuses().put(BattleManager.getInstance().getCurPokeIndex(), status);
				playerStatus.setImage(m_statusIcons.get(status.toLowerCase()));
			}
			else if(status.equalsIgnoreCase("normal"))
			{
				BattleManager.getInstance().getOurStatuses().remove(BattleManager.getInstance().getCurPokeIndex());
				playerStatus.setImage(null);
			}
		}
		else if(status.equalsIgnoreCase("poison") || status.equalsIgnoreCase("freeze") || status.equalsIgnoreCase("burn") || status.equalsIgnoreCase("paralysis") || status.equalsIgnoreCase("sleep"))
		{
			enemyStatus.setImage(m_statusIcons.get(status.toLowerCase()));
		}
		else if(status.equalsIgnoreCase("normal"))
		{
			enemyStatus.setImage(null);
		}
	}

	/**
	 * Starts a battle
	 */
	public void startBattle()
	{
		setPlayerInfo();
		initPlayerHPBar();
		initPlayerXPBar();
	}

	/**
	 * Updates the HP bar for the opponent's poke
	 * 
	 * @param newValue
	 */
	public void updateEnemyHP(int newValue)
	{
		boolean healing = false;
		int currentHP = (int) enemyHP.getValue();
		if(newValue > currentHP)
		{
			healing = true;
		}
		while(currentHP != newValue)
		{
			while(mTimer.getTime() <= 0.05)
			{
				Timer.tick();
			}
			if(healing)
			{
				currentHP += 1.00f;
			}
			else
			{
				currentHP -= 1.00f;
			}
			enemyHP.setValue(currentHP);
			updateEnemyHPBarColor();
			mTimer.reset();
		}
	}

	/**
	 * Updates the HP bar for the player's poke
	 * 
	 * @param newValue
	 */
	public void updatePlayerHP(int newValue)
	{
		boolean healing = false;
		int currentHP = (int) playerHP.getValue();

		if(newValue > currentHP)
		{
			healing = true;
		}

		while(currentHP != newValue)
		{
			while(mTimer.getTime() <= 0.05)
			{
				Timer.tick();
			}
			if(healing)
			{
				currentHP += 1.00f;
			}
			else
			{
				currentHP -= 1.00f;
			}
			playerHP.setValue(currentHP);
			updatePlayerHPBarColor();

			mTimer.reset();
		}
	}

	/**
	 * Updates the XP bar for the player's poke
	 * 
	 * @param newValue
	 */
	public void updatePlayerXPValue(int newValue)
	{
		playerXP.setValue(newValue);
		playerXPEND.setValue(newValue);
	}

	public void setPlayerHP(float hp)
	{
		playerHP.setValue(hp);
	}

	public void setPlayerMaxHP(int maxHP)
	{
		playerHP.setMaximum(maxHP);
	}

	public void updatePlayerHPBarColor()
	{
		int currentHp = BattleManager.getInstance().getCurPoke().getCurHP();
		int maxHp = BattleManager.getInstance().getCurPoke().getMaxHP();
		if(currentHp > maxHp / 2)
		{
			setPlayerHPColor(Color.green);
		}
		else if(currentHp < maxHp / 2 && currentHp > maxHp / 3)
		{
			setPlayerHPColor(Color.orange);
		}
		else if(currentHp < maxHp / 3)
		{
			setPlayerHPColor(Color.red);
		}
	}

	public void updateEnemyHPBarColor()
	{
		int enemyCurHp = BattleManager.getInstance().getCurEnemyPoke().getCurHP();
		int enemyMaxHp = BattleManager.getInstance().getCurEnemyPoke().getMaxHP();
		if(enemyCurHp > enemyMaxHp / 2)
		{
			setEnemyHPColor(Color.green);
		}
		else if(enemyCurHp < enemyMaxHp / 2 && enemyCurHp > enemyMaxHp / 3)
		{
			setEnemyHPColor(Color.orange);
		}
		else if(enemyCurHp < enemyMaxHp / 3)
		{
			setEnemyHPColor(Color.red);
		}
	}

	public void setPlayerHPColor(Color c)
	{
		if(c == Color.green)
		{
			playerHP.setProgressImage(hp_high);
		}
		else if(c == Color.orange)
		{
			playerHP.setProgressImage(hp_middle);
		}
		else if(c == Color.red)
		{
			playerHP.setProgressImage(hp_low);
		}
	}

	public void setEnemyHP(float hp)
	{
		enemyHP.setValue(hp);
	}

	public void setEnemyMaxHP(int maxHP)
	{
		enemyHP.setMaximum(maxHP);
	}

	public void setEnemyHPColor(Color c)
	{
		if(c == Color.green)
		{
			enemyHP.setProgressImage(hp_high);
		}
		else if(c == Color.orange)
		{
			enemyHP.setProgressImage(hp_middle);
		}
		else if(c == Color.red)
		{
			enemyHP.setProgressImage(hp_low);
		}
	}
	
	@Override
	public void layout()
	{
		// Apply adition
		int iXAdition = 400;
		int iYAdition = 175;
		
		enemyDataBG		.setPosition(getInnerX() + 10 + iXAdition, 																					getInnerY() + 15);
		playerDataBG	.setPosition(getInnerX() + 90 - 075, 																						getInnerY() - 98 + iYAdition + 50);
		
		playerXP		.setPosition(getInnerX() + 145 - 75, 																						getInnerY() + 132 + iYAdition - 145);
		playerXPEND		.setPosition(getInnerX() + 143 - 75, 																						getInnerY() + 125 + iYAdition - 145);
		playerHPBar		.setPosition(getInnerX() + 135 - 75, 																						getInnerY() + 120 + iYAdition - 145);
		playerHP		.setPosition(getInnerX() + 170 - 75, 																						getInnerY() + 125 + iYAdition - 145);
		playerLv		.setPosition(getInnerX() + 210 - 90, 																						playerDataBG.getY() + 7 + iYAdition - 175);
		
		enemyHP			.setPosition(enemyHPBar.getX() + 23 + 0, 																					enemyHPBar.getY() + 3);
		enemyNameLabel	.setPosition(enemyDataBG.getX() + 15 + 0, 																					enemyDataBG.getY() + 7);
		enemyHPBar		.setPosition(enemyDataBG.getX() + 15 + 0, 																					enemyDataBG.getY() + 25);
		enemyLv			.setPosition(enemyDataBG.getX() + 105 + 0, 																				enemyDataBG.getY() + 7);
		
		playerNameLabel	.setPosition(playerDataBG.getX() + 30, 																						playerDataBG.getY() + 7 + iYAdition - 175);
		
		enemyStatus		.setPosition(105 + getInnerX() + 0, 																						40 + getInnerY());
		playerStatus	.setPosition(playerNameLabel.getX() + getInnerX() - 75,																		125 + getInnerY() + iYAdition - 200);
		
		enemyGender		.setPosition(enemyNameLabel.getX() + GameClient.getInstance().getFontSmall().getWidth(enemyNameLabel.getText()) + 0, 		enemyNameLabel.getY());
		playerGender	.setPosition(playerNameLabel.getX() + GameClient.getInstance().getFontSmall().getWidth(playerNameLabel.getText()) + 3 - 75, playerNameLabel.getY() + iYAdition - 200);
		
		playerXPEND		.setSize(8, 9);
		playerHP		.setSize(72, 5);
		playerXP		.setSize(99, 4);
		enemyHP			.setSize(72, 5);
		enemyNameLabel	.setSize(enemyNameLabel.computeTextWidth(), enemyNameLabel.computeTextHeight());
		enemyLv			.setSize(GameClient.getInstance().getFontSmall().getWidth(enemyLv.getText()), GameClient.getInstance().getFontSmall().getHeight(enemyLv.getText()));
		playerLv		.setSize(GameClient.getInstance().getFontSmall().getWidth(playerLv.getText()), GameClient.getInstance().getFontSmall().getHeight(playerLv.getText()));
		playerNameLabel	.setSize(GameClient.getInstance().getFontSmall().getWidth(playerNameLabel.getText()), GameClient.getInstance().getFontSmall().getHeight(playerNameLabel.getText()));
		
		for(int i = 0; i < 6; i++)
		{
			m_enemyPokeballs.get(i).setPosition((475 + 14 * i + i * 5) + getInnerX(), 3 + getInnerY());
		}
		
		if ( DoPlayerAnimation == true )
		{
			if ( DoPlayerRight )
			{
				DoPlayerRight = false;
				playerPokeSprite.setPosition(getInnerX() + 15 + 90 + randInt(-5, 5), getInnerY() + 40 + iYAdition + 30 + randInt(-5, 5));
			}
			else
			{
				DoPlayerRight = true;
				playerPokeSprite.setPosition(getInnerX() + 15 + 90 + randInt(-5, 5) , getInnerY() + 40 + iYAdition + 30 + randInt(-5, 5));
			}
			
			if ( (fPlayerTimerEffect - System.currentTimeMillis()) <= 0 )
			{
				DoPlayerAnimation = false;
			}
			
			if ( (fPlayerTimer - System.currentTimeMillis()) <= 0 )
			{
				attackEffectPlayer.setVisible(false);
			}
		}
		else
		{
			playerPokeSprite.setPosition(getInnerX() + 15 + 90, getInnerY() + 40 + iYAdition + 30);
		}
		
		
		if ( DoEnemysAnimation == true )
		{
			if ( DoEnemyRight )
			{
				DoEnemyRight = false;
				enemyPokeSprite.setPosition(getInnerX() + iXAdition + 65 + randInt(-5, 5), getInnerY() + iYAdition - 85 + randInt(-5, 5));
			}
			else
			{
				DoEnemyRight = true;
				enemyPokeSprite.setPosition(getInnerX() + iXAdition + 65 + randInt(-5, 5), getInnerY() + iYAdition - 85 + randInt(-5, 5));
			}
			
			if ( (fEnemyTimerEffect - System.currentTimeMillis()) <= 0 )
			{
				DoEnemysAnimation = false;
			}
			
			if ( (fEnemyTimer - System.currentTimeMillis()) <= 0 )
			{
				attackEffectEnemy.setVisible(false);
			}
		}
		else
		{
			enemyPokeSprite.setPosition(getInnerX() + iXAdition + 65, getInnerY() + iYAdition - 85);
		}
		
		attackEffectPlayer.setPosition(getInnerX() + 15 + 90, getInnerY() + 40 + iYAdition + 30);
		attackEffectEnemy.setPosition(getInnerX() + iXAdition + 65, getInnerY() + iYAdition - 85);
	}
	
	public boolean isDoPlayerAnimation() {
		return DoPlayerAnimation;
	}
	
	public void setDoPlayerAnimation(boolean doPlayerAnimation) {
		attackEffectPlayer.setVisible(true);
		DoPlayerAnimation = doPlayerAnimation;
		fPlayerTimerEffect = System.currentTimeMillis() + 1500;
		fPlayerTimer = System.currentTimeMillis() + 500;
	}
	
	public boolean isDoEnemysAnimation() {
		return DoEnemysAnimation;
	}
	
	public void setDoEnemysAnimation(boolean doEnemysAnimation) {
		attackEffectEnemy.setVisible(true);
		DoEnemysAnimation = doEnemysAnimation;
		fEnemyTimerEffect = System.currentTimeMillis() + 1500;
		fEnemyTimer = System.currentTimeMillis() + 500;
	}
	
	public static int randInt(int min, int max)
	{
	    Random rand = new Random();
	    int randomNum = rand.nextInt((max - min) + 1) + min;

	    return randomNum;
	}
	
	// Convert Buffered image into image
}
