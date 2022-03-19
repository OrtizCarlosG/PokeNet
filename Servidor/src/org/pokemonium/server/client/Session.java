package org.pokemonium.server.client;

import org.jboss.netty.channel.Channel;
import org.pokemonium.server.GameServer;
import org.pokemonium.server.backend.entity.Player;
import org.pokemonium.server.constants.ClientPacket;
import org.pokemonium.server.protocol.ClientMessage;
import org.pokemonium.server.protocol.ServerMessage;

public class Session
{

	private Boolean authenticated;
	private Channel channel;
	private String ipAddress;
	private Player player;

	public Session(Channel channel, String Ip)
	{
		this.channel = channel;
		authenticated = false;
		ipAddress = Ip;

		ServerMessage message = new ServerMessage(this);
		message.init(ClientPacket.SERVER_REVISION.getValue());
		message.addInt(GameServer.REVISION);
		message.sendResponse();
	}

	public void close()
	{
		getChannel().close();
	}

	public Channel getChannel()
	{
		return channel;
	}

	public String getIpAddress()
	{
		return ipAddress;
	}

	public Boolean getLoggedIn()
	{
		return authenticated;
	}

	public Player getPlayer()
	{
		return player;
	}

	public void parseMessage(ClientMessage msg)
	{
		/* If the player is logged in reset his lastPacket time for the idlekicker. */
		if(player != null)
			player.lastPacket = System.currentTimeMillis();
		if(GameServer.getServiceManager().getNetworkService().getConnections().getMessages().contains(msg.getId()))
			GameServer.getServiceManager().getNetworkService().getConnections().getMessages().get(msg.getId()).Parse(this, msg, new ServerMessage(this));
	}

	public void Send(ServerMessage msg)
	{
		channel.write(msg);
	}

	public void setLoggedIn(boolean state)
	{
		authenticated = state;
	}

	public void setPlayer(Player player)
	{
		player.setSession(this);
		this.player = player;
	}
}
