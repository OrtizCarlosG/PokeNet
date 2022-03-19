package org.pokemonium.server.backend.entity;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.pokemonium.server.network.MySqlManager;

public class Clan {

	private MySqlManager m_database;
	
	public Clan()
	{
		m_database = MySqlManager.getInstance();
		System.out.println("[SYSTEM] Clan system loaded");
	}
	
	public void InviteToClan(int clanID, Player _invited, Player _invitedBy) throws SQLException{
		if (!isUserInvited(clanID, _invited) && !isUserInClan(_invited) && isUserInClan(_invitedBy))
		{
			ResultSet clanmemberData = m_database.query("SELECT * FROM pn_clanmembers WHERE username = '" + _invitedBy.getUsername() + "'");
			try {
				clanmemberData.first();
				m_database.query("INSERT INTO `pn_claninvites` (`userid`, `clanid`, `invitedbyID`) VALUES ('"+ _invited.getUserID() +"', '" + clanmemberData.getInt("clan_id") + "', '"+ _invitedBy.getUserID() +"');");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public boolean isUserInvited(int clanID, Player _invited) throws SQLException
	{
		boolean _result = false;
		ResultSet inviteData = m_database.query("SELECT * FROM pn_claninvites WHERE userid = '" + _invited.getUserID() + "' AND clan_id = '"+ clanID +"'");
		try {
			if(inviteData.first())
			{
			_result = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return _result;
	}
	
	public boolean isUserInClan(Player _player)
	{
		boolean _result = false;
		ResultSet inviteData = m_database.query("SELECT * FROM pn_clanmembers WHERE username = '" + _player.getUsername() + "'");
		try {
			if(inviteData.first())
			{
			_result = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return _result;
	}
	
	public void removeInvite(int clanID, Player _invited) throws SQLException
	{
		if (isUserInvited(clanID, _invited))
			m_database.query("DELETE * FROM pn_claninvites WHERE userid = '"+ _invited.getUserID() +"' AND clan_id = '"+ clanID +"';");
	}
	public void disbandClan(int clanID, Player _disband)
	{
		if (isUserInClan(_disband))
		{
			m_database.query("DELETE * FROM pn_claninvites WHERE clan_id = '"+ clanID +"';");
			m_database.query("DELETE * FROM pn_clanmembers WHERE clan_id = '"+ clanID +"';");
			m_database.query("DELETE * FROM pn_clans WHERE id = '"+ clanID +"';");
		}
	}
	
	public void createClan(String _clanName, String _clanTag, int red, int green, int blue, Player _creator)
	{
		System.out.println("[CLAN MANAGER] Trying to create clan: "+ _clanName +" by "+ _creator.getUsername() +" ");
		if (!isUserInClan(_creator))
		{
			m_database.query("INSERT INTO `pn_clans` (`clan_Name`, `clan_Tag`, `Red`, `Green`, `Blue`, `money`) VALUES ('"+ _clanName +"', '" + _clanTag + "', '"+ red +"', '"+ green +"', '"+ blue +"', '0');");
			System.out.println("[CLAN MANAGER] clan added: "+ _clanName +" by "+ _creator.getUsername() +" ");
			ResultSet clanID = m_database.query("SELECT * FROM pn_clans WHERE clan_Name = '" + _clanName + "'");
			try {
				clanID.first();
			m_database.query("INSERT INTO `pn_clanmembers` (`username`, `clan_id`, `status`, `rank`) VALUES ('"+ _creator.getUsername() +"', '" + clanID.getInt("id") + "', b'1', '5');");
			} catch (SQLException e)
			{
				
			}
		} else {
			System.out.println("[CLAN MANAGER] Error user: "+ _creator.getUsername() +" is in other clan");
		}
	}
}
