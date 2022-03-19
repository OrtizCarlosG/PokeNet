using System;
using System.Collections.Generic;
using System.Text;
using System.Windows.Forms;

namespace PokemonServer.Class
{
    class ServerSend
    {
        /// <summary>Sends a packet to a client via TCP.</summary>
        /// <param name="_toClient">The client to send the packet the packet to.</param>
        /// <param name="_packet">The packet to send to the client.</param>
        private static void SendTCPData(int _toClient, Packet _packet)
        {
            _packet.WriteLength();
            Server.clients[_toClient].tcp.SendData(_packet);
        }
     
        /// <summary>Sends a packet to all clients via TCP.</summary>
        /// <param name="_packet">The packet to send.</param>
        private static void SendTCPDataToAll(Packet _packet)
        {
            _packet.WriteLength();
            for (int i = 1; i <= Server.MaxPlayers; i++)
            {
                Server.clients[i].tcp.SendData(_packet);
            }
        }
        /// <summary>Sends a packet to all clients except one via TCP.</summary>
        /// <param name="_exceptClient">The client to NOT send the data to.</param>
        /// <param name="_packet">The packet to send.</param>
        private static void SendTCPDataToAll(int _exceptClient, Packet _packet)
        {
            _packet.WriteLength();
            for (int i = 1; i <= Server.MaxPlayers; i++)
            {
                if (i != _exceptClient)
                {
                    Server.clients[i].tcp.SendData(_packet);
                }
            }
        }
        #region Packets
        /// <summary>Sends a welcome message to the given client.</summary>
        /// <param name="_toClient">The client to send the packet to.</param>
        /// <param name="_msg">The message to send.</param>
        public static void sendServerID(int _toClient)
        {
            using (Packet _packet = new Packet((int)ServerPackets.serverID))
            {
                _packet.Write(_toClient);
                SendTCPData(_toClient, _packet);
            }
        }

        public static void sendLoginResult(int _toClient, bool _result)
        {
            using (Packet _packet = new Packet((int)ServerPackets.loginResult))
            {
                _packet.Write(_result);
                SendTCPData(_toClient, _packet);
            }
        }

        public static void sendBanPacket(int _toClient, string reason, bool isPermabanned, string _until)
        {
            using (Packet _packet = new Packet((int)ServerPackets.banResult))
            {
                _packet.Write(reason);
                _packet.Write(isPermabanned);
                _packet.Write(_until);
                SendTCPData(_toClient, _packet);
            }
        }

        public static void sendClientKey(int _toClient, string code)
        {
            using (Packet _packet = new Packet((int)ServerPackets.clientKey))
            {
                _packet.Write(code);

                SendTCPData(_toClient, _packet);
            }
        }

        public static void sendProfilePhoto(int _toClient, string image)
        {
            using (Packet _packet = new Packet((int)ServerPackets.profileImage))
            {
                _packet.Write(image);

                SendTCPData(_toClient, _packet);
            }
        }
        #endregion
    }
}