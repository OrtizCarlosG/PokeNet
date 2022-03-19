using System.Collections;
using System.Collections.Generic;
using System.Net;
using System.Windows.Forms;

namespace PokemonOnline.Connections
{
    public class ClientHandle
    {
        public static void getID(Packet _packet)
        {
            int _myId = _packet.ReadInt();
            Client.myId = _myId;
        }

        public static void loginResult(Packet _packet)
        {
            bool _result = _packet.ReadBool();
            if (!_result)
            {
             
            }
            else
            {
                
            }
        }


    }
}
