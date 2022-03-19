using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using PokemonOnline.Class;

namespace PokemonOnline.Connections
{
    public class ClientSend
    {
        /// <summary>Sends a packet to the server via TCP.</summary>
        /// <param name="_packet">The packet to send to the sever.</param>
        private static void SendTCPData(Packet _packet)
        {
            _packet.WriteLength();
            Client.tcp.SendData(_packet);
        }

        #region Packets
        /// <summary>Lets the server know that the welcome message was received.</summary>
        public static void LoginPacket(string _user, string _password)
        {
            using (Packet _packet = new Packet((int)ClientPackets.login))
            {
                _packet.Write(Client.myId);
                _packet.Write(Globals.getCPUId());
                _packet.Write(_user);
                _packet.Write(_password);

                SendTCPData(_packet);
            }
        }
        #endregion
    }
}
