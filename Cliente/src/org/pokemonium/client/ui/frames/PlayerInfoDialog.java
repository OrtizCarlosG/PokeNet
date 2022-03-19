package org.pokemonium.client.ui.frames;

import org.pokemonium.client.GameClient;
import org.pokemonium.client.backend.FileLoader;
import org.pokemonium.client.backend.entity.Player;
import org.pokemonium.client.backend.entity.Player.Direction;
import org.pokemonium.client.constants.ServerPacket;
import org.pokemonium.client.protocol.ClientMessage;
import org.pokemonium.client.ui.components.Image;
import org.pokemonium.client.ui.components.ImageButton;

import de.matthiasmann.twl.Color;
import de.matthiasmann.twl.Label;
import de.matthiasmann.twl.Widget;
import de.matthiasmann.twl.theme.ThemeManager;

/**
 * A frame that shows trainer information and stats
 * 
 * @author Myth1c
 */
public class PlayerInfoDialog extends Widget
{
	final boolean ALL_REGIONS = false;	// set this to TRUE/refactor as regions
										// are completed
	private ImageButton[] m_kanto;
	private boolean[] b_kanto;
	private Image[] I_kanto;
	
	private ImageButton[] m_johto;
	private boolean[] b_johto;
	private Image[] I_johto;
	
	private ImageButton[] m_hoenn;
	private boolean[] b_hoenn;
	private Image[] I_hoenn;
	
	private ImageButton[] m_sinnoh;
	private boolean[] b_sinnoh;
	private Image[] I_sinnoh;
	
	private ImageButton[] m_orange;
	private boolean[] b_orange;
	private Image[] I_orange;
	
	private ImageButton[] m_extras;
	private boolean[] b_extras;
	private Image[] I_extras;
	
	private Image m_playerImage;

	private Label m_kantoLbl;
	private Label m_johtoLbl;
	private Label m_hoennLbl;
	private Label m_sinnohLbl;
	private Label m_orangeLbl;
	private Label m_extrasLbl;

	private Label m_trainerEXP;
	private Label m_breedingEXP;
	private Label m_fishingEXP;
	private Label m_coordinatingEXP;
	private Label m_flyexplanation;
	
	private ImageButton m_flyButton;
	private Label m_flylabel;

	private int maxLblWidth;
	private int iButtonIndex;
	
	private boolean bNeedFly = false;
	
	/**
	 * Default constructor
	 */
	public PlayerInfoDialog()
	{
		m_kanto = new ImageButton[8];
		b_kanto = new boolean[8];
		I_kanto = new Image[8];
		
		m_johto = new ImageButton[8];
		b_johto = new boolean[8];
		I_johto = new Image[8];
		
		// if (ALL_REGIONS)
		m_hoenn = new ImageButton[8];
		b_hoenn = new boolean[8];
		I_hoenn = new Image[8];
		
		// if (ALL_REGIONS)
		m_sinnoh = new ImageButton[8];
		b_sinnoh = new boolean[8];
		I_sinnoh = new Image[8];
		
		if(ALL_REGIONS)
		{
			m_orange = new ImageButton[4];
			b_orange = new boolean[8];
			I_orange = new Image[4];
		}
		if(ALL_REGIONS)
		{
			m_extras = new ImageButton[6];
			b_extras = new boolean[8];
			I_extras = new Image[6];
		}
		
		initGUI();
	}

	/**
	 * Initializes the interface
	 */
	@SuppressWarnings("all")
	private void initGUI()
	{
		// Player Image
		m_playerImage = new Image(FileLoader.toTWLImage(Player.getSpriteFactory().getSprite(Direction.Down, false, false, GameClient.getInstance().getOurPlayer().getSprite())));
		add(m_playerImage);
		
		// Trainer data labels
		m_trainerEXP = new Label("Entrenador Lv:     " + GameClient.getInstance().getOurPlayer().getTrainerLevel());
		m_breedingEXP = new Label("Breeding Lv:       " + GameClient.getInstance().getOurPlayer().getBreedingLevel());
		m_fishingEXP = new Label("Pesca Lv:          " + GameClient.getInstance().getOurPlayer().getFishingLevel());
		m_coordinatingEXP = new Label("Coordinating Lv:  " + GameClient.getInstance().getOurPlayer().getCoordinatingLevel());

		add(m_trainerEXP);
		add(m_breedingEXP);
		add(m_fishingEXP);
		add(m_coordinatingEXP);

		// Start the badge labels
		m_kantoLbl = new Label("Kanto:");
		m_johtoLbl = new Label("Johto:");
		// if (ALL_REGIONS)
		m_hoennLbl = new Label("Hoenn:");
		// if (ALL_REGIONS)
		m_sinnohLbl = new Label("Sinnoh:");
		if(ALL_REGIONS)
			m_orangeLbl = new Label("Orange Islands:");
		if(ALL_REGIONS)
			m_extrasLbl = new Label("Others:");
		
		// Fly explanation
		m_flyexplanation = new Label("Para viajar a otra ciudad\nselecciona una medalla\ny luego presiona VUELO.\n\n");
		
		// button
		ThemeManager theme = GameClient.getInstance().getTheme();
		m_flyButton = new ImageButton(theme.getImage("topbar_fly"));
		m_flylabel = new Label("Selecciona una medalla");
		
		// Add Labels to Content Pane
		add(m_kantoLbl);
		add(m_johtoLbl);
		add(m_hoennLbl);
		add(m_sinnohLbl);
		add(m_flyexplanation);
		
		add(m_flyButton);
		add(m_flylabel);
		
		if(ALL_REGIONS)
			add(m_orangeLbl);
		if(ALL_REGIONS)
			add(m_extrasLbl);
		
		// Apply width
		maxLblWidth = ((ALL_REGIONS) ? m_orangeLbl : m_kantoLbl).computeTextWidth();
		
		// Load Images
		loadImages();
		
		/* 6 rows, 20 pixels each + title bar height */
		if(ALL_REGIONS)
		{
			setSize(160 + 2 + 60, 315);
			m_flyButton.setSize(50, 35);
		}
		else
		{
			setSize(160 + 2 + 60, 285);
			m_flyButton.setSize(50, 35);
		}
		
		showBadges();
	}

	/**
	 * Loads the status icons
	 */
	public void loadImages()
	{
		String respath = System.getProperty("res.path");
		if(respath == null)
			respath = "";
		String m_path = respath + "res/badges/";
		// Kanto Badges
		for(int i = 0; i < 8; i++)
		{
			// KANTO
			m_kanto[i] = new ImageButton(FileLoader.loadImage(m_path + "kanto" + (i + 1) + ".png"));
			I_kanto[i] = new Image(FileLoader.loadImage(m_path + "kanto" + (i + 1) + ".png"));

			// JOHTO
			m_johto[i] = new ImageButton(FileLoader.loadImage(m_path + "johto" + (i + 1) + ".png"));
			I_johto[i] = new Image(FileLoader.loadImage(m_path + "johto" + (i + 1) + ".png"));
			
			// HOENN
			// if (ALL_REGIONS) {
			m_hoenn[i] = new ImageButton(FileLoader.loadImage(m_path + "hoenn" + (i + 1) + ".png"));
			I_hoenn[i] = new Image(FileLoader.loadImage(m_path + "hoenn" + (i + 1) + ".png"));
			
			// }
			// SINNOH
			// if (ALL_REGIONS) {
			m_sinnoh[i] = new ImageButton(FileLoader.loadImage(m_path + "sinnoh" + (i + 1) + ".png"));
			I_sinnoh[i] = new Image(FileLoader.loadImage(m_path + "sinnoh" + (i + 1) + ".png"));
			
			// }
			// ORANGE ISLANDS
			if(ALL_REGIONS)
			{
				if(i < 4)
				{
					m_orange[i] = new ImageButton(FileLoader.loadImage(m_path + "orange" + (i + 1) + ".png"));
					I_orange[i] = new Image(FileLoader.loadImage(m_path + "orange" + (i + 1) + ".png"));
					add(m_orange[i]);
				}
			}
			// Extra badges ???
			if(ALL_REGIONS)
			{
				if(i < 6)
				{
					m_extras[i] = new ImageButton(FileLoader.loadImage(m_path + "extra" + (i + 1) + ".png"));
					I_extras[i] = new Image(FileLoader.loadImage(m_path + "extra" + (i + 1) + ".png"));
					add(m_extras[i]);
				}
			}
			add(m_kanto[i]);
			add(m_johto[i]);
			add(m_hoenn[i]);
			add(m_sinnoh[i]);
		}
	}

	/**
	 * Shows badges (darkens ones the player does not have)
	 * 
	 * @throws NullPointerException
	 */
	public void showBadges() throws NullPointerException
	{
		// Clean fly zone
		iButtonIndex = 0;
		
		// Get badges
		int[] badges = GameClient.getInstance().getOurPlayer().getBadges();
		try
		{
			for(int i = 0; i < badges.length; i++)
			{
				if(i < 8)
				{
					// Kanto
					if(badges[i] == 0)
					{
						m_kanto[i].setImage(I_kanto[i].getImage().createTintedVersion(Color.BLACK));
						b_kanto[i] = false;
					}
					else
						b_kanto[i] = true;
				}
				else if(i < 16)
				{
					// Johto
					if(badges[i] == 0)
					{
						m_johto[i - 8].setImage(I_johto[i - 8].getImage().createTintedVersion(Color.BLACK));
						b_johto[i - 8] = false;
					}
					else
						b_johto[i - 8] = true;
				}
				else if(i < 24)
				{
					// Hoenn
					// if (ALL_REGIONS) {
					if(badges[i] == 0)
					{
						m_hoenn[i - 16].setImage(I_hoenn[i - 16].getImage().createTintedVersion(Color.BLACK));
						b_hoenn[i - 16] = false;
					}
					else
						b_hoenn[i - 16] = true;
					// }
				}
				else if(i < 32)
				{
					// Sinnoh
					// if (ALL_REGIONS) {
					if(badges[i] == 0)
					{
						m_sinnoh[i - 24].setImage(I_sinnoh[i - 24].getImage().createTintedVersion(Color.BLACK));
						b_sinnoh[i - 24] = false;
					}
					else
						b_sinnoh[i - 24] = true;
					// }
				}
				else if(i < 36)
				{
					// Orange Islands
					if(ALL_REGIONS)
					{
						if(badges[i] == 0)
						{
							m_orange[i - 32].setImage(I_orange[i - 32].getImage().createTintedVersion(Color.BLACK));
							b_orange[i - 32] = false;
						}
						else
							b_orange[i - 32] = true;
					}
				}
				else if(i < 42)
				{
					// Extras
					if(ALL_REGIONS)
					{
						if(badges[i] == 0)
						{
							m_extras[i - 36].setImage(I_extras[i - 36].getImage().createTintedVersion(Color.BLACK));
							b_extras[i - 36] = false;
						}
						else
							b_extras[i - 36] = true;
					}
				}
				else
				{
					throw new NullPointerException("Bad Badge Number");
				}
			}
		}
		catch(NullPointerException e)
		{
			e.printStackTrace();
			System.err.println("See http://xkcd.com/371/ for details");
		}
	}

	public void updateDialog()
	{
		m_trainerEXP.setText("Trainer Lv:            " + GameClient.getInstance().getOurPlayer().getTrainerLevel());
		m_breedingEXP.setText("Breeding Lv:         " + GameClient.getInstance().getOurPlayer().getBreedingLevel());
		m_fishingEXP.setText("Fishing Lv:            " + GameClient.getInstance().getOurPlayer().getFishingLevel());
		m_coordinatingEXP.setText("Corrdinating Lv: " + GameClient.getInstance().getOurPlayer().getCoordinatingLevel());
		showBadges();
	}

	@Override
	public void layout()
	{
		m_trainerEXP.setPosition(getInnerX() + 85, getInnerY() + 10);
		m_breedingEXP.setPosition(getInnerX() + 85, getInnerY() + 25);
		m_fishingEXP.setPosition(getInnerX() + 85, getInnerY() + 40);
		m_coordinatingEXP.setPosition(getInnerX() + 85, getInnerY() + 55);
		m_playerImage.setPosition(getInnerX() + 2, getInnerY());
		for(int i = 0; i < 8; i++)
		{
			final int j = i;
			int y = getInnerY() + 65;
			m_kanto[i].setPosition(60 + maxLblWidth + (20 * i) + getInnerX(), y);
			m_kanto[i].addCallback(new Runnable()
			{
				@Override
				public void run()
				{
					try
					{
						if ( b_kanto[j] )
						{
							bNeedFly = true;
							iButtonIndex = j + 0;
							m_flylabel.setText("Volar a: " + GameClient.getInstance().getOurPlayer().returnBadgeCity(iButtonIndex));
						}
					}
					catch (Exception ex)
					{
						System.out.println(ex);
					}
				}
			});
			
			
			y = 2 + y + 20;
			m_johto[i].setPosition(60 + maxLblWidth + (20 * i) + getInnerX(), y);
			m_johto[i].addCallback(new Runnable()
			{
				@Override
				public void run()
				{
					try
					{
						if ( b_johto[j] )
						{
							bNeedFly = true;
							iButtonIndex = j + 8;
							m_flylabel.setText("Volar a: " + GameClient.getInstance().getOurPlayer().returnBadgeCity(iButtonIndex));
						}
					}
					catch (Exception ex)
					{
						System.out.println(ex);
					}
				}
			});
			
			y = 2 + y + 20;
			m_hoenn[i].setPosition(60 + maxLblWidth + (20 * i) + getInnerX(), y);
			m_hoenn[i].addCallback(new Runnable()
			{
				@Override
				public void run()
				{
					try
					{
						if ( b_hoenn[j] )
						{
							bNeedFly = true;
							iButtonIndex = j + 16;
							m_flylabel.setText("Volar a: " + GameClient.getInstance().getOurPlayer().returnBadgeCity(iButtonIndex));
						}
					}
					catch (Exception ex)
					{
						System.out.println(ex);
					}
				}
			});
			
			y = 2 + y + 20;
			m_sinnoh[i].setPosition(60 + maxLblWidth + (20 * i) + getInnerX(), y);
			m_sinnoh[i].addCallback(new Runnable()
			{
				@Override
				public void run()
				{
					try
					{
						if ( b_sinnoh[j] )
						{
							bNeedFly = true;
							iButtonIndex = j + 24;
							m_flylabel.setText("Volar a: " + GameClient.getInstance().getOurPlayer().returnBadgeCity(iButtonIndex));
						}
					}
					catch (Exception ex)
					{
						System.out.println(ex);
					}
				}
			});
			
			y = 2 + y + 20;
			if(ALL_REGIONS)
			{
				if(i < 4)
				{
					m_orange[i].setPosition(2 + maxLblWidth + (20 * i) + getInnerX(), y);
				}
				y = 2 + y + 20;
				if(i < 6)
				{
					m_extras[i].setPosition(2 + maxLblWidth + (20 * i) + getInnerX(), y);
				}
			}
		}
		
		// Fly command
		m_flyButton.addCallback(new Runnable()
		{
			@Override
			public void run()
			{
				if ( bNeedFly == true )
				{
					// Remove
					bNeedFly = false;
					
					// Apply none
					m_flylabel.setText("selecciona una medalla");
					
					/* Send message */
					ClientMessage message = new ClientMessage(ServerPacket.PLAYER_FLY_COMMAND);
					message.addInt(iButtonIndex);
					GameClient.getInstance().getSession().send(message);
				}
			}
		});
		
		m_kantoLbl.setPosition(getInnerX() + 5, m_kanto[0].getY() + 8);
		m_johtoLbl.setPosition(getInnerX() + 5, m_johto[0].getY() + 8);
		m_hoennLbl.setPosition(getInnerX() + 5, m_hoenn[0].getY() + 8);
		m_sinnohLbl.setPosition(getInnerX() + 5, m_sinnoh[0].getY() + 8);
		m_flyexplanation.setPosition(getInnerX() + 5, m_sinnoh[0].getY() + 70);
		m_flyButton.setPosition(getInnerX() + 5 + 90, m_sinnoh[0].getY() + 100);
		m_flylabel.setPosition(getInnerX() + 5 + 45, m_sinnoh[0].getY() + 140);
	}
}
