using System;
using System.Collections.Generic;
using System.Linq;
using System.Management;
using System.Text;
using System.Threading.Tasks;

namespace PokemonOnline.Class
{
    class Globals
    {

        private static ConnectionHandler _connection = new ConnectionHandler();
        public static void setConnection(ConnectionHandler _newConnection)
        {
            _connection = _newConnection;
        }

        public static ConnectionHandler getConnection()
        {
            return _connection;
        }
        public static string getCPUId()
        {
            string cpuInfo = string.Empty;
            ManagementClass mc = new ManagementClass("win32_processor");
            ManagementObjectCollection moc = mc.GetInstances();

            foreach (ManagementObject mo in moc)
            {
                if (cpuInfo == "")
                {
                    //Get only the first CPU's ID
                    cpuInfo = mo.Properties["processorID"].Value.ToString();
                    break;
                }
            }
            return cpuInfo;
        }
    }
}
