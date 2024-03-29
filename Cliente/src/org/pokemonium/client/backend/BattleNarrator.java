package org.pokemonium.client.backend;

import java.util.ArrayList;
import java.util.List;

import org.pokemonium.client.GameClient;

/**
 * Narrates the battles
 * 
 * @author Myth1c
 */
public class BattleNarrator
{
	// private BattleCanvas m_canvas;
	private boolean m_isBattling;
	// private BattleSpeechFrame m_narrator;
	int m_newHPValue, m_exp, m_dmg, m_earnings, m_level, m_expRemaining;
	// Lines for REGEX needed for l10n
	String m_pokeName, m_move, m_trainer, m_foundItem;
	List<String> m_translator = new ArrayList<String>();

	/**
	 * Default constructor
	 */
	public BattleNarrator()
	{
		m_translator = Translator.translate("_BATTLE");
	}

	/**
	 * Adds speech to the narrator and waits for it to be read before the next action is taken
	 * 
	 * @param msg
	 */
	public void addSpeech(String msg)
	{
		String newMsg = parsel10n(msg);
		GameClient.getInstance().getHUD().getBattleSpeechFrame().addSpeech(parsel10n(newMsg));
		while(!GameClient.getInstance().getHUD().getBattleSpeechFrame().getCurrentLine().equalsIgnoreCase(newMsg))
			;
		while(!GameClient.getInstance().getHUD().getBattleSpeechFrame().getAdvancedLine().equalsIgnoreCase(newMsg))
			;
	}

	/**
	 * Stops the timeline
	 */
	public void endBattle()
	{
		GameClient.getInstance().getHUD().removeBattleSpeechFrame();
	}

	/**
	 * Informs that a pokemon gained experience
	 * 
	 * @param data
	 */
	public void informExperienceGained(String[] data)
	{
		m_pokeName = data[0];
		m_exp = (int) Double.parseDouble(data[1]);
		m_expRemaining = (int) Double.parseDouble(data[2]);
		BattleManager.getInstance().getBattleWindow().getCanvas().updatePlayerXPValue(BattleManager.getInstance().getCurPoke().getExp() + m_exp);
		addSpeech(m_translator.get(3));
	}

	/**
	 * Informs a pokemon fainted
	 * 
	 * @param poke
	 */
	public void informFaintedPoke(String poke)
	{
		m_pokeName = poke;
		
		try
		{
			for(int i = 0; i < GameClient.getInstance().getOurPlayer().getPokemon().length; i++)
			{
				int faintedAmount = 0;
				
				if(GameClient.getInstance().getOurPlayer().getPokemon()[i] != null && GameClient.getInstance().getOurPlayer().getPokemon()[i].getCurHP() <= 0)
				{
					faintedAmount++;
				}
				
				if(faintedAmount < i)
				{
					// BattleManager.getInstance().getBattleWindow().disableAllPokemon();
					BattleManager.getInstance().getBattleWindow().showPokePane(true, true);
					addSpeech(m_translator.get(0));
					break;
				}
			}
		}
		catch (Exception ex)
		{
			for(int i = 0; i < GameClient.getInstance().getOurPlayer().getPokemon().length; i++)
			{
				int faintedAmount = 0;
				
				if(GameClient.getInstance().getOurPlayer().getPokemon()[i] != null && GameClient.getInstance().getOurPlayer().getPokemon()[i].getCurHP() <= 0)
				{
					faintedAmount++;
				}
				
				if(faintedAmount < i)
				{
					// BattleManager.getInstance().getBattleWindow().disableAllPokemon();
					BattleManager.getInstance().getBattleWindow().showPokePane(true, true);
					addSpeech(m_translator.get(0));
					break;
				}
			}
		}
	}

	/**
	 * Informs a change in health
	 * 
	 * @param data
	 * @param i
	 */
	public void informHealthChanged(String[] data, int i)
	{
		try
		{
			m_pokeName = data[0];
			m_dmg = Math.abs(Integer.parseInt(data[1]));
			if(i == 0)
			{
				m_pokeName = BattleManager.getInstance().getCurPoke().getName();
				m_newHPValue = BattleManager.getInstance().getCurPoke().getCurHP() + Integer.parseInt(data[1]);
				if(m_newHPValue < 0)
					m_newHPValue = 0;
				else if(m_newHPValue > BattleManager.getInstance().getCurPoke().getMaxHP())
					m_newHPValue = BattleManager.getInstance().getCurPoke().getMaxHP();
				BattleManager.getInstance().getCurPoke().setCurHP(m_newHPValue);
				BattleManager.getInstance().getBattleWindow().getCanvas().updatePlayerHP(BattleManager.getInstance().getCurPoke().getCurHP());
				data[0] = BattleManager.getInstance().getCurPoke().getName();
				
				BattleManager.getInstance().getBattleWindow().getCanvas().setDoPlayerAnimation(true);
			}
			else
			{
				m_pokeName = BattleManager.getInstance().getCurEnemyPoke().getName();
				m_newHPValue = BattleManager.getInstance().getCurEnemyPoke().getCurHP() + Integer.parseInt(data[1]);
				if(m_newHPValue < 0)
					m_newHPValue = 0;
				else if(m_newHPValue > BattleManager.getInstance().getCurEnemyPoke().getMaxHP())
					m_newHPValue = BattleManager.getInstance().getCurEnemyPoke().getMaxHP();
				BattleManager.getInstance().getCurEnemyPoke().setCurHP(m_newHPValue);
				BattleManager.getInstance().getBattleWindow().getCanvas().updateEnemyHP(BattleManager.getInstance().getCurEnemyPoke().getCurHP());
				data[0] = BattleManager.getInstance().getCurEnemyPoke().getName();
				
				BattleManager.getInstance().getBattleWindow().getCanvas().setDoEnemysAnimation(true);
			}
	
			if(i == 1 && m_newHPValue == 0)
				BattleManager.getInstance().getBattleWindow().getCanvas().setEnemyPokeballImage(BattleManager.getInstance().getCurEnemyIndex(), "fainted");
	
			if(Integer.parseInt(data[1]) <= 0)
			{
				//addSpeech(m_translator.get(7));
				addSpeech(m_translator.get(8));
			}
			else
				addSpeech(m_translator.get(9));
		}
		catch ( Exception ex )
		{
			System.out.println("===================================================");
			System.out.println(ex.toString());
			System.out.println("===================================================");
		}
	}

	/**
	 * Informs the player's that the pokemon dropped an item
	 * 
	 * @param item
	 */
	public void informItemDropped(String item)
	{
		m_foundItem = item;
		if(BattleManager.getInstance().isWild())
		{
			m_pokeName = BattleManager.getInstance().getCurEnemyPoke().getName();
			addSpeech(m_translator.get(22));
		}
		else
			addSpeech(m_translator.get(23));
	}

	/**
	 * Informs the player's earnings
	 * 
	 * @param money
	 */
	public void informLevelUp(String poke, int level)
	{
		m_pokeName = poke;
		m_level = level;
		addSpeech(m_translator.get(20));
	}

	/**
	 * Informs a loss on the player's side
	 */
	public void informLoss()
	{
		m_trainer = GameClient.getInstance().getOurPlayer().getUsername();
		addSpeech(m_translator.get(11));
		BattleManager.getInstance().endBattle();
		BattleManager.getInstance().setFinish(true);
		m_isBattling = false;
	}

	/**
	 * Informs the player's earnings
	 * 
	 * @param money
	 */
	public void informMoneyGain(int money)
	{
		m_earnings = money;
		addSpeech(m_translator.get(19));
	}

	/**
	 * Informs that a move is requested
	 */
	public void informMoveRequested()
	{
		BattleManager.getInstance().requestMoves();
		addSpeech(m_translator.get(2));
	}

	/**
	 * Informs a move was used
	 * 
	 * @param data
	 */
	public void informMoveUsed(String pokeName, String Move)
	{
		m_pokeName = pokeName;
		m_move = Move;
		addSpeech(m_translator.get(1));
	}

	public void informNoPP(String move)
	{
		m_move = move;
		BattleManager.getInstance().requestMoves();
		addSpeech(m_translator.get(21));
	}

	/**
	 * Informs if a run was successful
	 * 
	 * @param canRun
	 */
	public void informRun(boolean canRun)
	{
		if(canRun)
		{
			addSpeech(m_translator.get(12));
			GameClient.getInstance().getHUD().getBattleSpeechFrame().advance();
			BattleManager.getInstance().endBattle();
		}
		else
		{
			addSpeech(m_translator.get(13));
			GameClient.getInstance().getHUD().getBattleSpeechFrame().advance();
			informMoveRequested();
		}
	}

	/**
	 * Informs that a pokemon's status was changed
	 * 
	 * @param data
	 */
	public void informStatusChanged(int trainer, String pokeName, String effect)
	{
		m_pokeName = pokeName;
		BattleManager.getInstance().getBattleWindow().getCanvas().setStatus(trainer, effect);
		if(effect.equalsIgnoreCase("poison"))
			addSpeech(m_translator.get(14));
		else if(effect.equalsIgnoreCase("freeze"))
			addSpeech(m_translator.get(15));
		else if(effect.equalsIgnoreCase("burn"))
			addSpeech(m_translator.get(16));
		else if(effect.equalsIgnoreCase("paralysis"))
			addSpeech(m_translator.get(17));
		else if(effect.equalsIgnoreCase("sleep"))
			addSpeech(m_translator.get(18));
		if(trainer == 1)
			BattleManager.getInstance().getBattleWindow().getCanvas().setEnemyPokeballImage(BattleManager.getInstance().getCurEnemyIndex(), "status");
	}

	/**
	 * Informs that a pokemon's status was returned to normal
	 * 
	 * @param data
	 */
	public void informStatusHealed(int trainer, String pokeName, String effect)
	{
		m_pokeName = pokeName;
		BattleManager.getInstance().getBattleWindow().getCanvas().setStatus(trainer, "normal");
		addSpeech(m_translator.get(4));
	}

	/**
	 * Informs that a pokemon was switched out.
	 * 
	 * @param data
	 */
	public void informSwitch(String trainerName, String pokeName, int Trainer, int pIndex)
	{
		m_trainer = trainerName;
		m_pokeName = pokeName;
		BattleManager.getInstance().switchPoke(Trainer, pIndex);
		BattleManager.getInstance().getBattleWindow().getCanvas().setPlayerPokemonSprite();
		BattleManager.getInstance().getBattleWindow().getCanvas().setEnemyPokeSprite();
		BattleManager.getInstance().getBattleWindow().getCanvas().setPlayerInfo();
		BattleManager.getInstance().getBattleWindow().getCanvas().setEnemyInfo();
		addSpeech(m_translator.get(5));
	}

	/**
	 * Informs that a pokemon switch is required
	 */
	public void informSwitchRequested()
	{
		try
		{
			BattleManager.getInstance().getBattleWindow().showPokePane(true);
			addSpeech(m_translator.get(6));
		}
		catch (Exception ex)
		{
			BattleManager.getInstance().getBattleWindow().showPokePane(true);
		}
	}

	/**
	 * Informs a victory on the player's side
	 */
	public void informVictory()
	{
		m_trainer = GameClient.getInstance().getOurPlayer().getUsername();
		addSpeech(m_translator.get(10));
		m_isBattling = false;
	}

	public boolean isBattling()
	{
		return m_isBattling;
	}

	/**
	 * Uses regexes to create the appropriate battle messages for battle
	 * 
	 * @param line
	 */
	public String parsel10n(String line)
	{
		if(line.contains("trainerName"))
			line = line.replaceAll("trainerName", m_trainer);
		if(line.contains("moveName"))
			line = line.replaceAll("moveName", m_move);
		if(line.contains("pokeName"))
			line = line.replace("pokeName", m_pokeName);
		if(line.contains("hpNum"))
			line = line.replaceAll("hpNum", String.valueOf(m_newHPValue));
		if(line.contains("expNum"))
			line = line.replaceAll("expNum", String.valueOf(m_exp));
		if(line.contains("damageNum"))
			line = line.replaceAll("damageNum", String.valueOf(m_dmg));
		if(line.contains("earningsNum"))
			line = line.replaceAll("earningsNum", String.valueOf(m_earnings));
		if(line.contains("levelNum"))
			line = line.replaceAll("levelNum", String.valueOf(m_level));
		if(line.contains("rewardItem"))
			line = line.replaceAll("rewardItem", m_foundItem);
		if(line.contains("expRemaining"))
			line = line.replaceAll("expRemaining", String.valueOf(m_expRemaining));
		return line;
	}

	/**
	 * Shows a custom message sent by the server
	 * 
	 * @param msg
	 */
	public void showMessage(String msg)
	{
		addSpeech(msg);
	}

	/**
	 * Starts the TimeLine's components
	 */
	public void startTimeline()
	{
		BattleManager.getInstance().getBattleWindow().getCanvas().startBattle();
		m_isBattling = true;
		GameClient.getInstance().getHUD().showBattleSpeechFrame();
		GameClient.getInstance().getHUD().getBattleSpeechFrame().setVisible(true);
	}
}
