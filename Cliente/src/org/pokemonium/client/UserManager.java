package org.pokemonium.client;

import org.pokemonium.client.constants.Language;
import org.pokemonium.client.constants.ServerPacket;
import org.pokemonium.client.protocol.ClientMessage;

/**
 * Generates packets and sends them to the server
 * 
 * @author shadowkanji
 */
public class UserManager
{
	/**
	 * Sends a password change packet
	 * 
	 * @param username
	 * @param newPassword
	 * @param oldPassword
	 */
	public void changePassword(String username, String newPassword, String oldPassword)
	{
		ClientMessage message = new ClientMessage(ServerPacket.CHANGE_PASSWORD);
		//message.addString(username + "," + getPasswordHash(username, newPassword) + "," + getPasswordHash(username, oldPassword));
		message.addString(username + "," + newPassword + "," + oldPassword);
		GameClient.getInstance().getSession().send(message);
	}

	/**
	 * Sends a login packet to server and chat server
	 * 
	 * @param username
	 * @param password
	 */
	public void login(String username, String password)
	{
		byte language = 0;
		switch(GameClient.getInstance().getLanguage()) {
		case Language.ENGLISH:
			language = 0;
			break;
		case Language.PORTUGESE:
			language = 1;
			break;
		case Language.ITALIAN:
			language = 2;
			break;
		case Language.FRENCH:
			language = 3;
			break;
		case Language.FINNISH:
			language = 4;
			break;
		case Language.SPANISH:
			language = 5;
			break;
		case Language.DUTCH:
			language = 6;
			break;
		case Language.GERMAN:
			language = 7;
			break;
		default:
			language = 0;
			break;
		}

		ClientMessage message = new ClientMessage(ServerPacket.LOGIN);
		message.addString(language + username + "," + password);
		GameClient.getInstance().getSession().send(message);
	}

	/**
	 * Sends a registration packet
	 * 
	 * @param username
	 * @param password
	 * @param email
	 * @param dob
	 * @param starter
	 */
	public void register(String username, String password, String email, String dob, int starter, int sprite, int region)
	{
		ClientMessage message = new ClientMessage(ServerPacket.REGISTRATION);
		message.addInt(region);
		message.addString(username + "," + password + "," + email + "," + dob + "," + starter + "," + sprite);
		GameClient.getInstance().getSession().send(message);
	}
}
