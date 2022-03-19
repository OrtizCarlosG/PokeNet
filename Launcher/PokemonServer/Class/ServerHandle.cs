using System;
using System.Collections.Generic;
using System.Text;
using MySql.Data.MySqlClient;
using PokemonServer.Class;

namespace PokemonServer.Class
{
    class ServerHandle
    {
        public static void WelcomeReceived(int _fromClient, Packet _packet)
        {
            int _clientIdCheck = _packet.ReadInt();

            Globals.getForm().addToLog($"{Server.clients[_fromClient].tcp.socket.Client.RemoteEndPoint} connected successfully and is now player {_fromClient}.");
            if (_fromClient != _clientIdCheck)
            {
                Globals.getForm().addToLog($"Player (ID: {_fromClient}) has assumed the wrong client ID ({_clientIdCheck})!");
            }
            //Server.clients[_fromClient].SendIntoGame(_username);
        }

        public static void LoginRecived(int _fromClient, Packet _packet)
        {
            int _client = _packet.ReadInt();
            if (!Server.clients[_fromClient]._isLoggedIn)
            {
                string _hwid = _packet.ReadString();
                string _account = _packet.ReadString();
                string _password = _packet.ReadString();
                string _ip = Server.RemovePort(Server.clients[_fromClient].tcp.socket.Client.RemoteEndPoint.ToString());
                if (LoginManager.tryLogin(_account, _password))
                {
                    Server.clients[_fromClient]._isLoggedIn = true;
                    Server.clients[_fromClient]._clientName = _account;
                    ServerSend.sendLoginResult(_fromClient, true);
                    Globals.getForm().addToLog($"User ID {_fromClient} logged in as {_account} {DateTime.Now}");
                }
                else
                {
                    ServerSend.sendLoginResult(_fromClient, false);
                    Globals.getForm().addToLog($"User ID {_fromClient} tryed to log as {_account} IP {_ip} {DateTime.Now}");
                }
              
            }
        }
    }
}