package org.pokemonium.client.ui.frames.battle;

import java.util.HashMap;

import org.pokemonium.client.ui.components.Image;

import de.matthiasmann.twl.Button;
import de.matthiasmann.twl.Label;
import de.matthiasmann.twl.Widget;

public class BattleDialog extends Widget
{
	private BattleCanvas canvas;
	private BattleControlFrame control;

	public BattleDialog()
	{
		canvas = new BattleCanvas();
		control = new BattleControlFrame();
		add(canvas);
		add(control);
	}

	public BattleCanvas getCanvas()
	{
		return canvas;
	}

	public BattleControlFrame getControlFrame()
	{
		return control;
	}

	public void useMove(int i)
	{
		control.useMove(i);
	}

	public void showAttack()
	{
		control.showAttack();
	}

	public void disableMoves()
	{
		control.disableMoves();
	}

	public void showPokePane(boolean b)
	{
		control.showPokePane(b);
	}
	
	public void disableAllPokemon()
	{
		for(Button btn : control.getPokeButtons())
		{
			btn.setEnabled(false);
		}
	}

	public void showPokePane(boolean b, boolean c) {
		if ( b == true )
		{
			for(Button btn : control.getPokeButtons())
			{
				btn.setEnabled(false);
			}
		}
		control.showPokePane(c);
	}
	
	public void setWild(boolean m_isWild)
	{
		control.setWild(m_isWild);
	}

	public void enableMoves()
	{
		control.enableMoves();
	}

	public Button getMoveButton(int i)
	{
		return control.getMoveButtons().get(i);
	}

	public Label getPPLabel(int i)
	{
		return control.getPPLabels().get(i);
	}

	public Label getMoveTypeLabel(int i)
	{
		return control.getMoveTypeLabels().get(i);
	}

	public Button getPokeButton(int i)
	{
		return control.getPokeButtons().get(i);
	}

	public Image getPokeStatus(int i)
	{
		return control.getPokeStatus().get(i);
	}

	public Label getPokeInfo(int i)
	{
		return control.getPokeInfo().get(i);
	}

	public HashMap<String, de.matthiasmann.twl.renderer.Image> getStatusIcons()
	{
		return control.getStatusIcons();
	}

	@Override
	public void layout()
	{
		/* Battle window size */
		canvas.setPosition(getInnerX() + 100, getInnerY() + 50);
		canvas.setSize(600, 300);
		control.setPosition(getInnerX() + 299, getInnerY() + canvas.getHeight() + 100);
		control.setSize(500, 120);

		setSize(600 + 550, 300 + 120 + 150);
	}
}
