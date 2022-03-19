package org.pokemonium.client.ui.frames;

import org.pokemonium.client.GameClient;
import org.pokemonium.client.constants.ServerPacket;
import org.pokemonium.client.protocol.ClientMessage;

import de.matthiasmann.twl.Button;
import de.matthiasmann.twl.EditField;
import de.matthiasmann.twl.Label;
import de.matthiasmann.twl.ResizableFrame;

public class ClanDialog extends ResizableFrame {

	private Button m_createclan;
	private Label l_cname;
	private EditField m_clanName, m_clanTag;
	
	
	public ClanDialog(){
		this.setSize(320, 360);
		setTitle("Crear un clan");
		setPosition(420, 220);
		setResizableAxis(ResizableAxis.NONE);
		setDraggable(false);
		
		
		l_cname = new Label("1");
		l_cname.setPosition(4, 31);
		add(l_cname);
		
		
		m_createclan = new Button("Crear clan");
		m_createclan.setCanAcceptKeyboardFocus(false);
		m_createclan.setSize(49, 24);
		m_createclan.setPosition(96, 308 + 22);
		m_createclan.addCallback(new Runnable()
		{
			@Override
			public void run()
			{
				CreateClan();
			}
		});
		add(m_createclan);
		
	}
	
	@Override
	public void layout()
	{
		setPosition(200, 0);
		setSize(360, 360);
	}
	
	private void CreateClan(){
		if(m_clanName.getText() != null && m_clanName.getText().length() >= 4 && m_clanName.getText().length() <= 12)
		{
			if (m_clanTag.getText() != null && m_clanTag.getText().length() > 0 && m_clanTag.getText().length() <= 4)
			{
				ClientMessage message = new ClientMessage(ServerPacket.CREATE_CLAN);
				message.addString(m_clanName + "," + m_clanTag + "");
				GameClient.getInstance().getSession().send(message);
			}
		}
	}
	
}
